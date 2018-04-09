package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.MovementAlterKey;
import jp.co.daifuku.wms.base.dbhandler.MovementHandler;
import jp.co.daifuku.wms.base.dbhandler.MovementSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkerHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerSearchKey;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Worker;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdMessage;
import jp.co.daifuku.wms.storage.report.MovementWriter;

/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida  <BR>
 * <BR>
 * This class executes WEB stock relocation maintenance. <BR>
 * Receive the contents entered via screen as a parameter and execute the process for stock relocation maintenance. <BR>
 * Each method in this class receives a connection object and executes the process for updating the database. <BR>
 * However, each method disables to commit and roll back of transactions. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method) <BR><BR><DIR>
 *   If only one Consignor Code exists in relocation Work status, return the corresponding Consignor Code. <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR>
 * <BR>
 *   <Search conditions> <BR><DIR>
 *     Work division:Relocation(12) <BR>
 *     Status flag: Completed (3)Deleted(9)others <BR></DIR></DIR>
 * <BR>
 * 2.Process by clicking on Display button (<CODE>Query()</CODE> Method)<BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it.<BR>
 *   Return <CODE>Parameter</CODE> array with the number of elements 0 if no corresponding data found. Or, return null when condition error occurs.<BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 *   Designating the display orderr value to Item code order allows to display in the order of location of the relocasion source of item codes. <BR>
 *   If the value for the display order is the order of relocation source location, display the relocation source location, item code in this order. <BR>
 *   Classify the data into groups using Aggregation work No, Consignor code, Item code, and Location No.,  <BR>
 *    Work result location, and Status flag of the Relocation work status, and display it. <BR>
 * <BR>
 *   <Search conditions> <BR><DIR>
 *     Work division:Relocation(12) <BR>
 *     Status flag: <BR><DIR>
 *       For data with Display Completed Relocation Data division True, status flag other than Deleted (9)  <BR>
 *       For data with Display Completed Relocation Data division False, status flag other than Completed (3) and Deleted (9) <BR></DIR></DIR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR><DIR>
 *     Worker Code <BR>
 *     Password <BR>
 *     Consignor code* <BR>
 *     Item Code <BR>
 *     Display order* <BR>
 *     Display Completed Relocation Data division* <BR>
 *     Relocation work list print type* <BR></DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     Consignor code <BR>
 *     Consignor name <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     relocation source Location No. <BR>
 *     relocation target Location No. <BR>
 *     Packed qty per Case <BR>
 *     Planned Case Qty <BR>
 *     Planned Piece Qty <BR>
 *     Result Case Qty <BR>
 *     Result Piece Qty <BR>
 *     Work Status(list cell) <BR>
 *     Expiry Date <BR>
 *     Picking Worker Code <BR>
 *     Picking Worker Name <BR>
 *     Work No.(Vector) <BR>
 *     Aggregation Work No. <BR>
 *     Last update date/time(Vector) <BR></DIR></DIR>
 * <BR>
 * 3.Process by clicking on Modify/Add button(<CODE>startSCHgetParams()</CODE>Method) <BR><BR><DIR>
 *   Receive the contents displayed in the preset area as a parameter and execute the process to add or modify it. <BR>
 *   If the Relocation work list print division of the parameter is True when completed the maintenance process, start up the class for printing the Relocation work list. <BR>
 *   Re-obtain the data to be output to the preset area from database when the process normally completed, and return it. <BR>
 *   Return null when the schedule failed to complete due to condition error or other causes. <BR>
 *   Search through the RFT Work status using the actually worked Machine No. to change the status from Storage in Process to Standby Storage or to cancel the Relocation, and update the electronic statement item (count) of the target data to Null.<BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Consignor code* <BR>
 *     Item Code <BR>
 *     Display order* <BR>
 *     Display Completed Relocation Data division* <BR>
 *     Relocation work list print type* <BR>
 *     Relocation Cancel division* <BR>
 *     relocation source Location No. <BR>
 *     relocation target Location No.* <BR>
 *     Packed qty per Case <BR>
 *     Planned Case Qty* <BR>
 *     Planned Piece Qty* <BR>
 *     Result Case Qty* <BR>
 *     Result Piece Qty* <BR>
 *     Work Status(list cell)* <BR>
 *     Preset line No.* <BR>
 *     Terminal No.* <BR>
 *     Work No.(Vector)* <BR>
 *     Aggregation Work No.* <BR>
 *     Last update date/time(Vector)* <BR></DIR>
 * <BR>
 *   <Complete Relocation Process>(Process for Standby Storage to Completed Relocation) <BR>
 * <DIR>
 *   <Check for process condition> <BR>
 *     1.Ensure to define Worker code and password in the Worker master. <BR><DIR>
 *       Check only the leading value of the array for the values of Worker code and password. <BR></DIR>
 *     2.Ensure that Relocation work status table of the work No. exists in the database. <BR>
 *     3.Require to correspond the last update date/time of the parameter to the last update date/time of the Relocation work status table (exclusion check) <BR>
 *     4.Require to input 0 for a value of the relocation case qty if the Packed qty per case of the parameter is 0. <BR>
 *     5.Require to input any value equal to planned qty or smaller for Relocation case qty or Relocation piece qty. <BR>
 *     6.Shelf table check-(<CODE>isAsrsLocation()</CODE>Method)<BR>
 *     7.Ensure that the source status flag is only the status Standby Storage. <BR>
 * <BR>
 *   <Update/Add Process> Note) Disables maintenance when in the case of Completed Relocation to Completed Relocation.<BR>
 *     -Update Relocation Work Status Table (DNMOVEMENT). <BR>
 *       Update the Aggregation Work No. and status flag of the parameter: Relocation work status linked with Standby Storage (1) to the contents as below. <BR>
 *       1.Update the status flag to Completed (3). <BR>
 *       2.Update the Planned Work qty and work result qty to the values of Relocated qty of the parameter. <BR>
 *       3.Update the work result location to the value of Relocation target location of the parameter. <BR>
 *       4.Update the Expiry date (ResultUseByDate) to a value of the expiry date of the parameter. <BR>
 *       5.Update the Worker code, Worker name, Terminal No., and the last Update process name. <BR>
 *       6.If any remaining work exists, create a new Relocation work status Standby Storage based on the contents of Relocation work status of division source. <BR>
 * <BR>
 *     -Update Inventory Information Table (DMSTOCK). <BR>
 *       Execute in <code>MovementCompleteOperator</code> (<CODE>complete()</CODE> method). <BR>
 *       For details, enable to refer to <code>MovementCompleteOperator</code>. <BR></DIR>
 * <BR>
 *   < Status change process> ( "Storage in Process" to "Standby Storage" process), <BR>
 * <DIR>
 *   <Check for process condition> <BR>
 *     1.Ensure to define Worker code and password in the Worker master. <BR><DIR>
 *       Check only the leading value of the array for the values of Worker code and password. <BR></DIR>
 *     2.Ensure that Relocation work status table of the work No. exists in the database. <BR>
 *     3.Require to correspond the last update date/time of the parameter to the last update date/time of the Relocation work status table (exclusion check) <BR>
 * <BR>
 *   <Update Process> <BR>
 *     -Update Relocation Work Status Table (DNMOVEMENT). <BR>
 *       Update the Relocation work status linked with Work No. parameter to the contents as below. <BR>
 *       1.Update the status flag to Standby Storage (1). <BR>
 *       2.Update the last Update process name. <BR></DIR>
 * <BR>
 *   <Process by Cancel Relocation> <BR>
 * <DIR>
 *   <Check for process condition> <BR>
 *     1.Ensure to define Worker code and password in the Worker master. <BR><DIR>
 *       Check only the leading value of the array for the values of Worker code and password. <BR></DIR>
 *     2.Ensure that Relocation work status table of the work No. exists in the database. <BR>
 *     3.Require to correspond the last update date/time of the parameter to the last update date/time of the Relocation work status table (exclusion check) <BR>
 *     4.Ensure to enable the relocation Cancel division only for status flag of the parameter with Standby Storage (1). <BR>
 *     5.Ensure not to modify the relocation qty. <BR>
 *     6.Ensure to input nothing in the relocation target location of the parameter. <BR>
 * <BR>
 *   <Update Process> <BR>
 *     -Update Relocation Work Status Table (DNMOVEMENT). <BR>
 *       Update the Relocation work status linked with Work No. parameter to the contents as below. <BR>
 *       1.Update the status flag to Deleted (9). <BR>
 *       2.Update the last Update process name. <BR>
 * <BR>
 *     -Update Inventory Information Table (DMSTOCK). <BR>
 *       Update the inventory information linked with Stock ID of the updated Relocation work status (plan unique key, Location No., Item Code, Case/Piece division, and expiry date) based on the contents of the Received parameter. <BR>
 *       1.Update it to the value resulted from the calculation by decreasing the allocated qty from the planned relocation qty of the parameter. <BR>
 *       2.Update the last Update process name. <BR></DIR>
 * <BR>
 *   <Print Process> <BR>
 * <DIR>
 *     Pass the Work No. of the Relocation work status to be printed, as a parameter, to the stock Relocation work list print process class.<BR>
 *     Search for the printing contents in the Writer class.<BR>
 *     If succeeded in printing, receive true from the class for printing the Stock Relocation work list. If failed, receive false.<BR>
 *     Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR></DIR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/13</TD><TD>S.Yoshida</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/16 08:01:04 $
 * @author  $Author: suresh $
 */
public class StockMoveMaintenanceSCH extends AbstractStorageSCH
{

	// Class variables -----------------------------------------------
	/**
	 * Process name
	 */
	private static String wProcessName = "StockMoveMaintenanceSCH";

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/16 08:01:04 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public StockMoveMaintenanceSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor Code exists in Relocation Work status, return the corresponding Consignor Code.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Instance to maintain database connection.
	 * @param searchParam Instance of <CODE>StorageSupportParameter</CODE> class with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StorageSupportParameter</CODE> throws ScheduleException.<BR>
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		//Set the corresponding Consignor code.
		StorageSupportParameter wParam = new StorageSupportParameter();

		// Generate relocation work info instance of handlers.
		MovementHandler wObj = new MovementHandler(conn);
		MovementSearchKey wKey = new MovementSearchKey();
		Movement[] wMove = null;

		try
		{
			// Set the search conditions.
			// Work division=Relocation(12)
			wKey.setJobType(Movement.JOB_TYPE_MOVEMENT_STORAGE);
			// Status flag= other than Deleted (9)
			wKey.setStatusFlag(Movement.STATUS_FLAG_DELETE, "!=");
			// Set the Consignor Code for grouping condition
			wKey.setConsignorCodeGroup(1);
			wKey.setConsignorCodeCollect("DISTINCT");

			// If the search result count is one:
			if (wObj.count(wKey) == 1)
			{
				// Obtain the corresponding Consignor Code.
				wMove = (Movement[]) wObj.find(wKey);

				if (wMove != null && wMove.length == 1)
				{
					// Obtain the corresponding Consignor Code and set it for the return parameter.
					wParam.setConsignorCode(wMove[0].getConsignorCode());
				}
			}
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

		return wParam;
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
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		// search result
		StorageSupportParameter[] wRParam = null;

		// Consignor name
		String wConsignorName = "";

		// Generate relocation work info instance of handlers.
		MovementHandler wObj = new MovementHandler(conn);
		MovementSearchKey wKey = new MovementSearchKey();
		MovementSearchKey wNameKey = new MovementSearchKey();
		Movement[] wMove = null;
		Movement[] wMoveName = null;

		try
		{
			// Search conditions for parameters
			StorageSupportParameter wParam = (StorageSupportParameter)searchParam;

			// Check for input in the Worker code.
			if (!checkWorker(conn, wParam))
			{
				return null;
			}

			// Set the search conditions.
			// Work division:Relocate (12)
			wKey.setJobType(Movement.JOB_TYPE_MOVEMENT_STORAGE);
			wNameKey.setJobType(Movement.JOB_TYPE_MOVEMENT_STORAGE);
			// Status flag: Delete other than (9)
			wKey.setStatusFlag(Movement.STATUSFLAG_DELETE, "!=");
			wNameKey.setStatusFlag(Movement.STATUSFLAG_DELETE, "!=");
			if (!wParam.getMoveCompDisplayFlg())
			{
				// Status flag: Completed other than (3)
				wKey.setStatusFlag(Movement.STATUSFLAG_COMPLETION, "!=");
				wNameKey.setStatusFlag(Movement.STATUSFLAG_COMPLETION, "!=");
			}
			// Consignor code
			if (!StringUtil.isBlank(wParam.getConsignorCode()))
			{
				wKey.setConsignorCode(wParam.getConsignorCode());
				wNameKey.setConsignorCode(wParam.getConsignorCode());
			}
			// Item Code
			if (!StringUtil.isBlank(wParam.getItemCode()))
			{
				wKey.setItemCode(wParam.getItemCode());
				wNameKey.setItemCode(wParam.getItemCode());
			}
			// In the order of Item Code,
			if (!StringUtil.isBlank(wParam.getDisplayOrder()) &&
			    wParam.getDisplayOrder().equals(StorageSupportParameter.DISPLAY_ORDER_ITEM_CODE))
			{
				// Set the sorting order to the order of Item Code and then Location.
				wKey.setItemCodeOrder(1, true);
				wKey.setLocationNoOrder(2, true);
			}
			// In the order of Relocation Source Location
			else
			{
				// Set the Sorting order to the order of Location and then Item Code.
				wKey.setLocationNoOrder(1, true);
				wKey.setItemCodeOrder(2, true);
			}
			// Add a status flag to the sorting order to display the aggregation.
			wKey.setStatusFlagOrder(3, true);

			// Check the capability to display in the preset area.
			if (!canLowerDisplay(wObj.count(wKey)))
			{
				return returnNoDisplayParameter();
			}

			// Obtain the Relocation work status.
			wMove = (Movement[]) wObj.find(wKey);

			// Obtain the Consignor name with the latest added date.
			wNameKey.setRegistDateOrder(1, false);
			wMoveName = (Movement[]) wObj.find(wNameKey);
			if (wMoveName != null && wMoveName.length > 0)
			{
				wConsignorName = wMoveName[0].getConsignorName();
			}

			// Generate of Vector instance
			Vector vec = new Vector();
			Vector wTempJobNo = new Vector();
			Vector wTempLastUpdateDate = new Vector();

			// Storage parameter
			StorageSupportParameter wTemp = null;
			// Planned Qty
			int wPlanQty = 0;
			// Relocated qty
			int wMoveQty = 0;

			// Set the search result for the return parameter.
			for (int i = 0; i < wMove.length; i ++)
			{
				// Set the Work No.
				wTempJobNo.addElement(wMove[i].getJobNo());
				// Set the last update date/time.
				wTempLastUpdateDate.addElement(wMove[i].getLastUpdateDate());
				// Sum up the planned qty.
				wPlanQty += wMove[i].getPlanQty();
				// Sum up the Relocated qty.
				wMoveQty += wMove[i].getResultQty();

				//  Classify into groups by Aggregation Work No., Relocation Source Location, Relocation Target Location, and Status flag
				if (i == wMove.length - 1 ||
				    (i < wMove.length - 1 &&
				     (!wMove[i].getCollectJobNo().trim().equals(wMove[i + 1].getCollectJobNo().trim()) ||
				      !wMove[i].getLocationNo().trim().equals(wMove[i + 1].getLocationNo().trim()) ||
				      !wMove[i].getResultLocationNo().trim().equals(wMove[i + 1].getResultLocationNo().trim()) ||
				      !wMove[i].getStatusFlag().trim().equals(wMove[i + 1].getStatusFlag().trim()))))
				{
					wTemp = new StorageSupportParameter();
					// Consignor code
					wTemp.setConsignorCode(wMove[i].getConsignorCode());
					// Consignor name
					wTemp.setConsignorName(wConsignorName);
					// Item Code
					wTemp.setItemCode(wMove[i].getItemCode());
					// Item Name
					wTemp.setItemName(wMove[i].getItemName1());
					// Relocation Source Location
					wTemp.setSourceLocationNo(wMove[i].getLocationNo());
					// Relocation Target Location
					wTemp.setDestLocationNo(wMove[i].getResultLocationNo());
					// Packed qty per Case
					wTemp.setEnteringQty(wMove[i].getEnteringQty());
					// Packed qty per bundle
					wTemp.setBundleEnteringQty(wMove[i].getBundleEnteringQty());
					// Planned Case Qty
					wTemp.setPlanCaseQty(DisplayUtil.getCaseQty(wPlanQty, wMove[i].getEnteringQty()));
					// Planned Piece Qty
					wTemp.setPlanPieceQty(DisplayUtil.getPieceQty(wPlanQty, wMove[i].getEnteringQty()));
					// If Status flag=Completed Relocation
					if (wMove[i].getStatusFlag().equals(Movement.STATUSFLAG_COMPLETION))
					{
						// Relocation Case Qty
						wTemp.setResultCaseQty(DisplayUtil.getCaseQty(wMoveQty, wMove[i].getEnteringQty()));
						// Relocation Piece Qty
						wTemp.setResultPieceQty(DisplayUtil.getPieceQty(wMoveQty, wMove[i].getEnteringQty()));
					}
					else
					{
						// Relocation Case Qty
						wTemp.setResultCaseQty(DisplayUtil.getCaseQty(wPlanQty, wMove[i].getEnteringQty()));
						// Relocation Piece Qty
						wTemp.setResultPieceQty(DisplayUtil.getPieceQty(wPlanQty, wMove[i].getEnteringQty()));
					}
					// Status flag
					if (wMove[i].getStatusFlag().equals(Movement.STATUSFLAG_COMPLETION))
					{
						// completed
						wTemp.setStatusFlagL(StorageSupportParameter.STATUS_FLAG_COMPLETION);
					}
					else if (wMove[i].getStatusFlag().equals(Movement.STATUSFLAG_NOWWORKING))
					{
						// Storage In Process
						wTemp.setStatusFlagL(StorageSupportParameter.STATUS_FLAG_WORKING);
					}
					else if (wMove[i].getStatusFlag().equals(Movement.STATUSFLAG_UNSTART))
					{
						// Standby Storage
						wTemp.setStatusFlagL(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
					}
					// Expiry Date
					wTemp.setUseByDate(wMove[i].getUseByDate());
					// Picking Worker Code
					wTemp.setRetrievalWorkerCode(wMove[i].getRetrievalWorkerCode());
					// Picking Worker Name
					wTemp.setRetrievalWorkerName(wMove[i].getRetrievalWorkerName());
					// Work No.(Vector)
					wTemp.setJobNoList(wTempJobNo);
					// Aggregation Work No.
					wTemp.setCollectJobNo(wMove[i].getCollectJobNo());
					// Last update date/time(Vector)
					wTemp.setLastUpdateDateList(wTempLastUpdateDate);
					vec.addElement(wTemp);

					// Initialize the Work No. List.
					wTempJobNo = new Vector();
					// Initialize the last updated date/time list.
					wTempLastUpdateDate = new Vector();
					// Initialize the planned qty.
					wPlanQty = 0;
					//Initialize the Relocated qty.
					wMoveQty = 0;
				}
			}

			wRParam = new StorageSupportParameter[vec.size()];
			vec.copyInto(wRParam);

			// 6001013 = Data is shown.
			wMessage = "6001013";
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

		return wRParam;
	}

	/**
	 * Receive the contents entered via screen as a parameter and start the schedule for Stock Relocation maintenance.<BR>
	 * Assume that two or more data may be input via preset area or others. So, require the parameter to receive them in the form of array.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * Return true if the schedule normally completed, or return false if failed.
	 * Search through the RFT Work status using the actually worked Machine No. to change the status from Storage in Process to Standby Storage or to cancel the Relocation, and update the electronic statement item (count) of the target data to Null.<BR>
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>StorageSupportParameter</CODE> class instance with contents of commitment. <BR>
	 *         Designating any instance other than StorageSupportParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *         Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @return <CODE>StorageSupportParameter</CODE> instance array that holds the search result<BR>
	 *          If the target record does not exist, return 0<BR>
	 *          Return null if there is any error in input conditions or the search result exceeds maximum<BR>
s	 *          Using <CODE>getMessage</CODE> method, the error details is fetched
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{

		// Generate relocation work info instance of handlers.
		MovementHandler wObj = new MovementHandler(conn);
		MovementSearchKey wKey = new MovementSearchKey();
		MovementAlterKey wAKey = new MovementAlterKey();
		Movement wMove = null;
		// Generate inventory info instance of handlers.
		StockHandler wSObj = new StockHandler(conn);
		StockSearchKey wSKey = new StockSearchKey();
		StockAlterKey wASKey = new StockAlterKey();
		Stock wStock = null;
		// Print Work No.
		Vector wPrintJobNo = new Vector();
		Vector wTempJobNo = new Vector();
		// Update flag.
		boolean wUpdateFlag = false;

		try
		{
			StorageSupportParameter[] wParam = (StorageSupportParameter[]) startParams;

			// Check the Worker code and password
			if (!checkWorker(conn, wParam[0]))
			{
				return null;
			}

			// Check the Daily Update Processing.
			if (isDailyUpdate(conn))
			{
				return null;
			}

			// Check for input and exclusion of the preset area.
			if (!checkList(conn, wParam))
			{
				return null;
			}

			// Terminal list of data of which status "Processing" was cancelled.
			ArrayList terminalList = new ArrayList();

			for (int i = 0; i < wParam.length; i ++)
			{
				//Execute process for "Standby Storage" to "Completed Relocation
				if (wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
				{
					// Calculate the Relocation result qty.
					int wResultQty = wParam[i].getResultCaseQty() * wParam[i].getEnteringQty() + wParam[i].getResultPieceQty();
					// Execute the Relocation Complete process.
					wTempJobNo = movementComplete(conn, wParam[i].getCollectJobNo(), wResultQty,
												  wParam[i].getDestLocationNo(), wParam[0].getWorkerCode(), wParam[0].getTerminalNumber());

					// Set the Work No. for printing.
					wPrintJobNo.addAll(wTempJobNo);
					// Update the update flag.
					wUpdateFlag = true;
				}

				for (int j = 0; j < wParam[i].getJobNoList().size(); j ++)
				{
					// Set the search conditions.
					wKey.KeyClear();
					// Work No.
					wKey.setJobNo(wParam[i].getJobNoList().get(j).toString());
					// Obtain the relocation search result of the Work status.
					wMove = (Movement) wObj.findPrimary(wKey);

					//Execute process for "Storage In Process" to "Standby Storage ".
					if (wMove.getStatusFlag().equals(Movement.STATUSFLAG_NOWWORKING) &&
						wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
					{
						// Set the update condition.
						wAKey.KeyClear();
						// Work No.
						wAKey.setJobNo(wParam[i].getJobNoList().get(j).toString());

						// Set the update value.
						// Status flag(Standby Storage)
						wAKey.updateStatusFlag(Movement.STATUSFLAG_UNSTART);
						wAKey.updateWorkerCode("");
						wAKey.updateWorkerName("");
						wAKey.updateTerminalNo("");
						// Last update process name
						wAKey.updateLastUpdatePname(wProcessName);

						// Update the Relocation work status.
						wObj.modify(wAKey);

						// Set the Work No. for printing.
						wPrintJobNo.addElement(wParam[i].getJobNoList().get(j).toString());
						// Update the update flag.
						wUpdateFlag = true;

						String rftNo = wMove.getTerminalNo();
						// If the status before change is Storage in process and terminal No. is not blank,
						if (wMove.getStatusFlag().equals(Movement.STATUSFLAG_NOWWORKING)
								&& ! StringUtil.isBlank(rftNo))
						{
							// this deletes the Processing data file. Therefore,
							// generate the RFT Machine No. List.
							if (! terminalList.contains(rftNo))
							{
								terminalList.add(rftNo);
							}
						}
					}

					// Execute the Relocation Cancel process.
					if (wParam[i].getMoveCancelFlg() &&
						wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
					{
						// Set the update condition.
						wAKey.KeyClear();
						// Work No.
						wAKey.setJobNo(wParam[i].getJobNoList().get(j).toString());

						// Set the update value.
						// Status flag (Delete)
						wAKey.updateStatusFlag(Movement.STATUSFLAG_DELETE);
						// Last update process name
						wAKey.updateLastUpdatePname(wProcessName);

						// Update the Relocation work status.
						wObj.modify(wAKey);
						// Update the update flag.
						wUpdateFlag = true;

						// Set the search conditions.
						wSKey.KeyClear();
						// Stock ID
						wSKey.setStockId(wMove.getStockId());
						// Consignor code
						wSKey.setConsignorCode(wMove.getConsignorCode());
						// Item Code
						wSKey.setItemCode(wMove.getItemCode());
						// Location No.
						wSKey.setLocationNo(wMove.getLocationNo());
						// Case/Piece division
						wSKey.setCasePieceFlag(wMove.getCasePieceFlag());
						// Expiry Date
						if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY &&
							!StringUtil.isBlank(wMove.getResultUseByDate()))
						{
							wSKey.setUseByDate(wMove.getResultUseByDate());
						}
						// Obtain the search result of the locked inventory information.
						wStock = (Stock) wSObj.findPrimaryForUpdate(wSKey);

						if (wStock != null)
						{
							// Set the update condition.
							wASKey.KeyClear();
							// Stock ID
							wASKey.setStockId(wMove.getStockId());
							// Consignor code
							wASKey.setConsignorCode(wMove.getConsignorCode());
							// Item Code
							wASKey.setItemCode(wMove.getItemCode());
							// Location No.
							wASKey.setLocationNo(wMove.getLocationNo());
							// Case/Piece division
							wASKey.setCasePieceFlag(wMove.getCasePieceFlag());
							// Expiry Date
							if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY &&
								!StringUtil.isBlank(wMove.getResultUseByDate()))
							{
								wASKey.setUseByDate(wMove.getResultUseByDate());
							}

							// Set the update value.
							// Allocatable qty (allocatable qty + relocation qty)
							wASKey.updateAllocationQty(wStock.getAllocationQty() + wMove.getPlanQty());
							// Last update process name
							wASKey.updateLastUpdatePname(wProcessName);

							// Update the inventory information.
							wSObj.modify(wASKey);

							// Set the Work No. for printing.
							wPrintJobNo.addElement(wParam[i].getJobNoList().get(j).toString());
							// Update the update flag.
							wUpdateFlag = true;
						}

						String rftNo = wMove.getTerminalNo();
						// If the status before change is Storage in process and terminal No. is not blank,
						if (wMove.getStatusFlag().equals(Movement.STATUSFLAG_NOWWORKING)
								&& ! StringUtil.isBlank(rftNo))
						{
							// this deletes the Processing data file. Therefore,
							// generate the RFT Machine No. List.
							if (! terminalList.contains(rftNo))
							{
								terminalList.add(rftNo);
							}
						}
					}
				}
			}

			// Initialize the PackageManager required in the process to delete a processing data file.
			PackageManager.initialize(conn);
			// Delete the Processing data file.
			IdMessage.deleteWorkingDataFile(terminalList,
					WorkingInformation.JOB_TYPE_MOVEMENT_STORAGE,
					"",
					wProcessName,
					conn);

			// Obtain the Relocation work status after updated.
			StorageSupportParameter[] viewParam = (StorageSupportParameter[]) this.query(conn, wParam[0]);

			if (!wUpdateFlag)
			{
				// 6023154 = There is no data to update.
				wMessage = "6023154";
			}
			else if (!wParam[0].getMoveWorkListFlag())
			{
				// 6021010 = Data was committed.
				wMessage = "6021010";
			}
			else
			{
				// Execute the process for printing a Relocation work list.
				try
				{
					// Generate Stock Relocation work list print class instance.
					MovementWriter iWriter = new MovementWriter(conn);

					// Set the parameter.
					// Work No.(Vector)
					iWriter.setJobNo(wPrintJobNo);

					// Start the printing process.
					if (iWriter.startPrint())
					{
						// 6021011 = Data had been set. Work list was printed.
						wMessage = "6021011";
					}
					else
					{
						// Display the error message.
						wMessage = iWriter.getMessage();
					}
				}
				catch (Exception e)
				{
					// 6007042=Printing failed after setup. Please refer to log.
					wMessage = "6007042";
					return viewParam;
				}
			}

			// Return the Relocation work status after updated.
			return viewParam;

		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (OverflowException e)
		{
			// 6023344=Cannot relocate. Stock Qty. in Destination Location will exceed {0}.
			wMessage = "6023344" + wDelim + MAX_STOCK_QTY_DISP;
			return null;
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * Receive the contents entered via screen as a parameter and check for input and exclusion in the preset area.
	 * @param conn Instance to maintain database connection.
	 * @param param StorageSupportParameter class instance with contents that were input via screen.
	 * @return Return true when no input error occurs, or return false when input error occurs.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	protected boolean checkList(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{
        // Generate relocation work info instance of handlers.
        MovementHandler wObj = new MovementHandler(conn);
        MovementSearchKey wKey = new MovementSearchKey();
        Movement wMove = null;

		try
		{
			// Contents of Preset area
			StorageSupportParameter[] wParam = (StorageSupportParameter[]) checkParam;
			// Entered Relocated qty
			long wResultQty = 0;
			// Planned Qty
			long wPlanQty = 0;
			// Relocation Result qty
			long wMoveResultQty = 0;
			// Location No
			String wLocationNo = "";

			// Lock all the Relocation work status in the preset together.
			wKey.KeyClear();
			Vector jobVec = new Vector();
			for(int i = 0 ; i < wParam.length; i ++)
			{
				for (int j = 0; j < wParam[i].getJobNoList().size(); j ++)
				{
					jobVec.addElement(wParam[i].getJobNoList().get(j).toString());
				}
			}
			String jobArry[] = new String[jobVec.size()];
			jobVec.copyInto(jobArry);
			wKey.setJobNo(jobArry);
			wObj.findForUpdate(wKey);
			// Lock all the inventory information in the preset together.
			lockAll(conn, checkParam);

			for (int i = 0; i < wParam.length; i ++)
			{

				wPlanQty = 0;
				wMoveResultQty = 0;

				for (int j = 0; j < wParam[i].getJobNoList().size(); j ++)
				{
					// Set the search conditions.
					wKey.KeyClear();
					// Work No.
					wKey.setJobNo(wParam[i].getJobNoList().get(j).toString());
					// Obtain the search result of the locked Relocation work status
					wMove = (Movement) wObj.findPrimaryForUpdate(wKey);

					if (wMove != null)
					{
						// Check Exclusion.
						if (!wParam[i].getLastUpdateDateList().get(j).equals(wMove.getLastUpdateDate()))
						{
							// 6023209 = No.{0} The data has been updated via other terminal.
							wMessage = "6023209" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// Calculate the Relocated qty.
						wResultQty = (long)wParam[i].getResultCaseQty() * (long)wMove.getEnteringQty() + (long)wParam[i].getResultPieceQty();
						// Calculates the Relocation planned qty.
						wPlanQty += (long)wMove.getPlanQty();
						// Calculate the Relocation result qty.
						wMoveResultQty += (long)wMove.getResultQty();

						// Relocation Target LocationにRelocation Source Locationが入力された場合エラー
						if (!StringUtil.isBlank(wParam[i].getDestLocationNo()) &&
						    wParam[i].getDestLocationNo().trim().equals(wParam[i].getSourceLocationNo().trim()))
						{
							// 6023303 = No.{0} Relocation Target LocationNoにRelocation Source LocationNoを入力することはできません。
							wMessage = "6023303" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// Obtain the location.
						wLocationNo = wParam[i].getDestLocationNo();

						if (!StringUtil.isBlank(wLocationNo))
						{
							LocateOperator locateOperator = new LocateOperator(conn);
							if (locateOperator.isAsrsLocation(wLocationNo))
							{
								// 6023443=No.{0} The specified location is in automatic warehouse. Cannot enter.
								wMessage = "6023443" + wDelim + wParam[i].getRowNo();
								return false;
							}
						}

						// Changing status from Standby Storage to Processing Storage triggers error.
						if (wMove.getStatusFlag().equals(Movement.STATUSFLAG_UNSTART) &&
						    wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_WORKING))
						{
							// 6023324 = No.{0} If status is "Standby for Storage"
							wMessage = "6023324" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// Check for input when canceling the Relocation.
						if (wParam[i].getMoveCancelFlg())
						{
							// Check for omitted input in the Relocation target location.
							if (!StringUtil.isBlank(wParam[i].getDestLocationNo()))
							{
								// 6023246 = No.{0} Destination location No. cannot be entered if the relocation is canceled.
								wMessage = "6023246" + wDelim + wParam[i].getRowNo();
								return false;
							}

							// Regard as error if the Relocated qty is modified.
							if (j == wParam[i].getJobNoList().size() - 1 && wResultQty != wPlanQty)
							{
								// 6023247 = No.{0} Relocation quantity cannot be changed if the relocation is canceled.
								wMessage = "6023247" + wDelim + wParam[i].getRowNo();
								return false;
							}

							// Regard the data with status Storage in Process as error.
							if (wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_WORKING))
							{
								//6023301 = No.{0} If status is Storage in Process
								wMessage = "6023301" + wDelim + wParam[i].getRowNo();
								return false;
							}

							// Regard the data with status Completed Relocation as error.
							if (wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
							{
								// 6023302 = No.{0} If status is Relocation Completed
								wMessage = "6023302" + wDelim + wParam[i].getRowNo();
								return false;
							}
						}

						// Inputting the Relocation target location with status "Standby Storage" triggers error.
						if(wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED) &&
							!StringUtil.isBlank(wParam[i].getDestLocationNo()))
						{
							// 6023370 = No. {0} When status is Waiting for Storage
							wMessage = "6023370" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// Check for input in the mode of Completed Relocation.
						if (wParam[i].getStatusFlagL().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
						{
							// Trigger error if the status flag of the source has any status other than Standby Storage.
							if (!wMove.getStatusFlag().equals(Movement.STATUSFLAG_UNSTART))
							{
								// 6023304 = No.{0} If status is Storage in Process
								wMessage = "6023304" + wDelim + wParam[i].getRowNo();
								return false;
							}

							// Check for mandatory input in Relocation target location.
							if (StringUtil.isBlank(wParam[i].getDestLocationNo()))
							{
								// 6023248 = No.{0} Please enter the destined location No. of relocation.
								wMessage = "6023248" + wDelim + wParam[i].getRowNo();
								return false;
							}

							// Check for input in the Relocation Case qty when the Packed qty per Case is 0.
							if (wMove.getEnteringQty() == 0 && wParam[i].getResultCaseQty() > 0)
							{
								// 6023300 = No.{0} If Packed Qty. per Case is 0
								wMessage = "6023300" + wDelim + wParam[i].getRowNo();
								return false;
							}

							// Regard as error if Relocated qty=0
							if (wResultQty == 0)
							{
								// 6023360 = No.{0} Please enter 1 or greater value in Relocation Case Qty. or Relocation Piece Qty.
								wMessage = "6023360" + wDelim + wParam[i].getRowNo();
								return false;
							}

							if (wResultQty > 0)
							{
								// If Relocated qty > planned qty,
								if (j == wParam[i].getJobNoList().size() - 1 && wResultQty > wPlanQty)
								{
									// 6023245 = No.{0} Relocation quantity cannot be increased.
									wMessage = "6023245" + wDelim + wParam[i].getRowNo();
									return false;
								}

								// Check Overflow.
								if (wResultQty > (long)WmsParam.MAX_STOCK_QTY)
								{
									// 6023272 = No.{0} Please enter {2} or smaller for {1}.
									wMessage = "6023272" + wDelim + wParam[i].getRowNo() + wDelim + DisplayText.getText("LBL-W0120") + wDelim + MAX_STOCK_QTY_DISP;
									return false;
								}
							}
						}
					}
					else
					{
						// 6023209 = No.{0} The data has been updated via other terminal.
						wMessage = "6023209" + wDelim + wParam[i].getRowNo();
						return false;
					}
				}
			}
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		return true;
	}

	/**
	 * Receive the contents entered via screen as a parameter and check for input and exclusion in the preset area.
	 * @param conn Instance to maintain database connection.
	 * @param param RetrievalSupportParameter class instance with contents that were input via screen.
	 * @return Return true when no input error occurs, or return false when input error occurs.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	protected boolean lockAll(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{
		try
		{
			StockSearchKey stockKey = new StockSearchKey();
			StockHandler stockHandler = new StockHandler(conn);
			Stock resultStock[] = null;
			StorageSupportParameter[] wParam = (StorageSupportParameter[]) checkParam;
			// Joint the conditions per one stock with "OR" and lock all the preset data.
			for (int i = 0 ; i < wParam.length ; i++)
			{
				// Set the search conditions.
				if (i == 0)
				{
					stockKey.setConsignorCode(wParam[i].getConsignorCode(), "=", "((", "", "AND");
				}
				else
				{
					stockKey.setConsignorCode(wParam[i].getConsignorCode(), "=", "(", "", "AND");
				}

				stockKey.setLocationNo(wParam[i].getSourceLocationNo(),"=","(","","OR");
				stockKey.setLocationNo(wParam[i].getDestLocationNo(),"=","",")","AND");

				// If expiry date control is enabled, include expiry date in the search conditions.
				if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
				{
					// Accept blank even if Expiry Date Control is enabled.
					stockKey.setUseByDate(wParam[i].getUseByDate(),"=", "","","AND");
				}
				if (i == wParam.length -1)
				{
					stockKey.setItemCode(wParam[i].getItemCodeL(), "=", "", "))", "AND");
				}
				else
				{
					stockKey.setItemCode(wParam[i].getItemCodeL(), "=", "", ")", "OR");
				}
			}

			// Require to be Center Inventory.
			stockKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			stockKey.setStockIdCollect();

			resultStock = (Stock[]) stockHandler.findForUpdate(stockKey);

			// Return stock class array of the number of elements equal to 0 if no search result.
			if (resultStock == null || resultStock.length == 0)
			{
				return false;
			}

			return true;
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	/**
	 * Execute processes for completing relocation of work status and inventory information.
	 * Update the Relocation work status linked with Aggregation Work No. and Status flag "Standby Storage (1)" of the parameter to "Completed (3)" for the Relocated qty of the parameter.
	 * Create a new Relocation work status with Standby Storage (1) if any remaining work is found.
	 * Relocation Complete process of inventory information in MovementCompleteOperator.
	 * @param conn Instance to maintain database connection.
	 * @param wCollectJobNo Aggregation Work No.
	 * @param wResultQty    Relocation Result qty
	 * @param wLocationNo   Work Result LocationNo.
	 * @param wWorkerCode   Worker Code
	 * @param wTerminalNo   Terminal No.
	 * @return Result flag Normal closing: True When error occurs: False
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	protected Vector movementComplete(Connection conn, String wCollectJobNo, int wResultQty,
									   String wLocationNo, String wWorkerCode, String wTerminalNo) throws ReadWriteException, ScheduleException, OverflowException
	{

		// Generate relocation work info instance of handlers.
		MovementHandler wObj = new MovementHandler(conn);
		MovementAlterKey wAKey = new MovementAlterKey();
		MovementSearchKey wKey = new MovementSearchKey();
		Movement[] wMove = null;
		// Generate Worker info instance of handlers.
		WorkerHandler wWObj = new WorkerHandler(conn);
		WorkerSearchKey wWKey = new WorkerSearchKey();
		Worker[] wWorker = null;
		// Generate SequenceHandler handler instance.
		SequenceHandler wSequence = new SequenceHandler(conn);
		// Total Relocated qty
		int wTotalMoveQty = 0;
		//Updated relocation qty
		int wMoveQty = 0;
		// Work No. List for printing
		Vector wPrintJobNo = new Vector();

		try
		{
			// Set the search conditions.
			// Worker Code
			wWKey.setWorkerCode(wWorkerCode);
			// Delete flag
			wWKey.setDeleteFlag(Worker.DELETE_FLAG_OPERATION);
			// Obtain the Worker info.
			wWorker = (Worker[]) wWObj.find(wWKey);

			// Set the search conditions.
			// Aggregation Work No.
			wKey.setCollectJobNo(wCollectJobNo);
			// Status flag：Standby Storage(1)
			wKey.setStatusFlag(Movement.STATUSFLAG_UNSTART);
			// Set the sorting order to the order of Case/Piece division.
			wKey.setCasePieceFlagOrder(1, true);

			// Obtain the Relocation work status.
			wMove = (Movement[]) wObj.find(wKey);

			// Close the process if no Relocation work status found.
			if (wMove == null || (wMove != null && wMove.length == 0))
			{
				return null;
			}

			// Calculate the total relocated qty.
			for (int i = 0; i < wMove.length; i ++)
			{
				wTotalMoveQty += wMove[i].getPlanQty();
			}

			for (int i = 0; i < wMove.length; i ++)
			{

				// Compare the values between the Relocated qty and the Relocation Storage Result qty and calculate the Relocation update qty.
				if (wResultQty > wMove[i].getPlanQty())
				{
					wMoveQty = wMove[i].getPlanQty();
				}
				else
				{
					wMoveQty = wResultQty;
				}

				// Set the update condition.
				wAKey.KeyClear();
				// Work No.
				wAKey.setJobNo(wMove[i].getJobNo());

				// Set the update value.
				// Work Planned Qty
				wAKey.updatePlanQty(wMoveQty);
				// Work Result qty
				wAKey.updateResultQty(wMoveQty);
				// Expiry Date
				wAKey.updateResultUseByDate(wMove[i].getUseByDate());
				// Work Result LocationNo.
				wAKey.updateResultLocationNo(wLocationNo);
				// Status flag：completed(3)
				wAKey.updateStatusFlag(Movement.STATUSFLAG_COMPLETION);
				// Worker Code
				wAKey.updateWorkerCode(wWorkerCode);
				// Worker Name
				wAKey.updateWorkerName(wWorker[0].getName());
				// Terminal No.
				wAKey.updateTerminalNo(wTerminalNo);
				// Last update process name
				wAKey.updateLastUpdatePname(wProcessName);

				// Update the Relocation work status.
				wObj.modify(wAKey);

				// Execute the process for completing the Stock Relocation.
				MovementCompleteOperator wMoveObj = new MovementCompleteOperator();
				wMoveObj.complete(conn, wMove[i].getJobNo(), wProcessName);

				// Set the Work No. for printing.
				wPrintJobNo.addElement(wMove[i].getJobNo());

                // Update of total relocated qty.
                wTotalMoveQty -= wMoveQty;
                // Update of Relocation result qty.
                wResultQty -= wMoveQty;

                // When any remaining work is found,
				if (wMove[i].getPlanQty() - wMoveQty > 0)
                {
                	// Work No.
                    String job_seqno = wSequence.nextJobNo(); //wSequence.nextMoveJobNo();
                	wMove[i].setJobNo(job_seqno);
                	// Status flag：Standby Storage(1)
                	wMove[i].setStatusFlag(Movement.STATUSFLAG_UNSTART);
                	// Work Planned Qty
                	wMove[i].setPlanQty(wMove[i].getPlanQty() - wMoveQty);
                	// Name of Add Process
                	wMove[i].setRegistPname(wProcessName);
                	// Last update process name
                	wMove[i].setLastUpdatePname(wProcessName);

                	// Create a new Relocation work status of Standby Storage.
                	wObj.create(wMove[i]);
                }

                // Go out from the loop when Relocation result qty becomes 0.
                if (wResultQty <= 0)
                {
                	break;
                }
			}

			return wPrintJobNo;
		}
		catch (DataExistsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}

	}

	// Private methods -----------------------------------------------
}
//end of class
