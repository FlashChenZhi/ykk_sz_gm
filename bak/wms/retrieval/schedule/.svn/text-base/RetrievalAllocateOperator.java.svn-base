package jp.co.daifuku.wms.retrieval.schedule;

//#CM722421
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM722422
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * 1. Process to define the order for obtaining inventory (<CODE>selectStockOrder()</CODE> method)  <BR> 
 * <BR>
 * <DIR>
 *   Return the order for obtaining Inventory Info to be allocated, based on the parameter.  <BR>
 *   Process is divided by Case/Piece division.  <BR>
 *   <Parameter> <BR>
 *   <DIR>
 *     Case/Piece division <BR>
 *     Count of Processes  <BR>
 *   </DIR>
 *   <Returned information >  <BR><DIR>
 *     true if the order to obtain data is set. <BR>
 *     False: When object information ends  <BR>
 *   </DIR>
 *   <Order to obtain data> <BR><DIR>
 *     1-1. Division Case  <BR><DIR>
 *       - In the order of Case --> None --> Piece  <BR></DIR>
 *     1-2. Division Piece  <BR><DIR>
 *       - In the order of Piece --> None --> Case <BR></DIR>
 *     1-3. For data with "None":  <BR><DIR>
 *       - In the order of None --> Piece --> Case  <BR></DIR>
 *     For Expiry Date Control, in the ascending order of Expiry Date  <BR>
 *     Ascending order of Storage Date  <BR>
 *   </DIR>
 *   <BR>
 * </DIR>
 * <BR>
 * 2. Process for obtaining target Inventory Info: Designate exclusivity. (<CODE>getRetrievalStock()</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *   Return the target inventory list for allocation to the parameter.  <BR>
 *   <Parameter> <BR><DIR>
 *     Connection  <BR>
 *     Picking Plan Info instance  <BR>
 *     Case/Piece division <BR>
 *   </DIR>
 *   <Returned information >  <BR><DIR>
 *     Inventory Info instance  <BR>
 *     Note) If the target Inventory Info is not found: return NULL.  <BR>
 *   </DIR>
 *   <Conditions to obtain data> <BR><DIR>
 *     2-1.Data that corresponds to the Consignor Code of the Plan Info  <BR>
 *     2-2.Data that corresponds to the Item Code of the Plan Info  <BR>
 *     2-3.For data with the specified Picking Location in the Plan Info, which corresponds to the designated Location No.  <BR>
 *     2-4. Data only with status flag Center Stock  <BR>
 *     2-5. stock qty - Allocated qty => 1  <BR>
 *   </DIR>
 *   <Order to obtain data> <BR><DIR>
 *     Allow the Process for defining the order for obtaining inventory (selectStockOrder method) to execute this.  <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 3.Allow this class to execute the process for allocation. (<CODE>allocate()</CODE> method)  <BR>
 * <BR>
 * <DIR>
 * Receive the Picking Plan unique key, the Process name, Worker Code, Worker Name, and Terminal No., and execute the process for allocating the inventory and adding the Work Status Info.  <BR>
 * Execute the process for search through the Picking Plan Info using the Picking Plan unique key. <BR>
 * Use the Process name, Worker Code, Worker Name, Terminal No. to add the Work Status Info. <BR>
 * <BR>
 *   <Parameter> <BR>
 *   <DIR>
 *     Connection  <BR>
 *     Picking Plan unique key <BR>
 *     Worker Code <BR>
 *     Worker Name <BR>
 *     Terminal No. <BR>
 *     Process name  <BR>
 *   </DIR>
 *   <Returned information >  <BR>
 *   <DIR>
 *     Allocated qty  <BR>
 *   </DIR>
 *   <Check Process Condition>  <BR>
 *   <DIR>
 *     3-1.Require to add it to the Picking Plan Info (DNRETRIEVALPLAN) using the Picking Plan unique key. <BR>
 *   </DIR>
 *   <Process Content>  <BR>
 *   <DIR>
 *     3-2.Obtain the availability of Inventory package from the WareNavi system definition info (WARENAVI_SYSTEM). <BR>
 *   </DIR>
 *   <Update Content>  <BR>
 *   <DIR>
 *     3-3.Divide the stock into Case and Piece based on the Picking Plan Info (Calculate each qty) and execute the following process for the respective stocks if the requested qty is equal to 0 or more.  <BR>
 *     - For data with Stock package "enabled": <BR>
 *     <DIR>
 *       A-1)Using the (getRetrievalStock)method for obtaining the target Inventory Info, obtain the target Inventory Info.  <BR>
 *       A-2)Compute the Available picking qty from the Inventory Info.  <BR>
 *       A-3) Update Inventory Info.  <BR>
 *       <DIR>
 *         Increase the Allocated qty (using Stock ID as a key)  <BR>
 *       </DIR>
 *       A-4)Add the Work Status Info (DNWORKINFO).  <BR>
 *       <DIR>
 *         Work No.: Number a new one. <BR>
 *         Work type: Picking <BR>
 *         Status flag: Standby  <BR>
 *         Start Work flag: "Started"  <BR>
 *         Stock ID: Stock ID in the Inventory Info updated at the step (A-3) <BR>
 *         Area No. of Inventory Info updated by Area No. (A-3) <BR>
 *         Location No. and Work Result Location: Set the value of Location No. in the Inventory Info that was updated at the step (A-3).  <BR>
 *         Host Planned Qty: Planned Picking Qty of Picking Plan Info  <BR>
 *         Work Planned qty and Workable Qty: Allocated qty that was summed-up at the step (A-3)  <BR>
 *         Work Result qty, Work shortage qty, and Pending Qty: 0 <BR>
 *         Work Type: Case/Piece division of the work to be allocated this time here 
 *         Case/Piece division: Case/Piece division of Inventory Info
 *         TC/DC division: DC  <BR>
 *         Set it to the value of Expire Date in the Inventory Info that was updated at the step (A-3).  <BR>
 *         Set the value of Lot No. in the Inventory Info that was updated at the step (A-3).  <BR>
 *         Set the process name of the parameter for the name of Add process and the name of the Last update process.  <BR>
 *         Set other field items from the Picking Plan Info.  <BR>
 *       </DIR>
 *     </DIR>
 *     - For data with Stock package "disabled": <BR>
 *     <DIR>
 *       B-1)Add the Inventory Info(DMSTOCK).  <BR>
 *       <DIR>
 *         Status flag: Picking Standby <BR>
 *         Stock qty and Allocated qty: Requested qty <BR>
 *         Name of Add Process and Last update process name: Set the process name of the parameter.  <BR>
 *         Set other field items from the Picking Plan Info.  <BR>
 *       </DIR>
 *       B-2)Add the Work Status Info (DNWORKINFO).  <BR>
 *       <DIR>
 *         Work No.: Number a new one. <BR>
 *         Work type: Picking <BR>
 *         Status flag: Standby  <BR>
 *         Start Work flag: "Started"  <BR>
 *         Stock ID: Stock ID in the Inventory Info updated at the step (B-1)  <BR>
 *         Area No. of Inventory Info updated by Area No. (B-1) <BR>
 *         Location No. and Work Result Location: Set the value of Location No. in the Inventory Info that was updated at the step (B-1).  <BR>
 *         Host Planned Qty: Planned Picking Qty of Picking Plan Info  <BR>
 *         Work Planned qty and Workable Qty: Allocated qty that was summed-up at the step (B-1)  <BR>
 *         Work Result qty, Work shortage qty, and Pending Qty: 0 <BR>
 *         Work Type: Case/Piece division of the work to be allocated this time here 
 *         Case/Piece division: Case/Piece division of Inventory Info
 *         TC/DC division: DC  <BR>
 *         Set it to the value of Expire Date in the Inventory Info that was updated at the step (B-1).  <BR>
 *         Set the value of Lot No. in the Inventory Info that was updated at the step (B-1).  <BR>
 *         Set the process name of the parameter for the name of Add process and the name of the Last update process.  <BR>
 *         Set other field items from the Picking Plan Info.  <BR>
 *       </DIR>
 *     </DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 4.Allow this class to execute the process to cancel allocation. (<CODE>cancel()</CODE> method)  <BR>
 * <DIR>
 * Receive the Picking Plan unique key, Process name, Worker Code, Worker Name, and Terminal No., and execute the processes for restoring the inventory allocation and updating (deleting) the Work Status Info.  <BR>
 * Execute the process for search through the Work Status using the Picking Plan unique key. <BR>
 * Use the Process name, Worker Code, Worker Name, Terminal No. to update the Work Status Info. <BR>
 * <BR>
 *   <Parameter> <BR><DIR>
 *     Connection  <BR>
 *     Picking Plan unique key <BR>
 *     Worker Code <BR>
 *     Worker Name <BR>
 *     Terminal No. <BR>
 *     Process name  <BR>
 *   </DIR>
 *   <Returned information >  <BR><DIR>
 *     Result  <BR>
 *   </DIR>
 *   <Check Process Condition>  <BR><DIR>
 *     4-1.Require to add it to the Work Status Info (DNWORKINFO) using the Picking Plan unique key. <BR>
 *   </DIR>
 *   <Process Content>  <BR><DIR>
 *     4-2.Obtain the availability of Inventory package from the WareNavi system definition info (WARENAVI_SYSTEM). <BR>
 *   </DIR>
 *   <Update Content>  <BR><DIR>
 *     C-1) Update the Work Status Info.  <BR><DIR>
 *       <Condition for Update>  <BR><DIR>
 *         Data that corresponds to the Work No. obtained at the step 4-1)  <BR>
 *       </DIR>
 *       <Update Content>  <BR><DIR>
 *         - Delete the Status flag.  <BR>
 *         - Worker Code-Worker Name-Terminal No.: from Parameter  <BR>
 *         - Set the process name of the parameter for the Last update process name.  <BR>
 *       </DIR>
 *     </DIR>
 *     - For data with Stock package "enabled": <BR><DIR>
 *       C-2) Load the Inventory Info together with exclusion process.  <BR><DIR>
 *         Data that corresponds to the stock ID in the Work Status Info obtained at the step 4-1).  <BR>
 *       </DIR>
 *       C-3) Update Inventory Info.  <BR><DIR>
 *         <Condition for Update>  <BR><DIR>
 *           Data that corresponds to the stock ID in the Work Status Info obtained at the step 4-1).  <BR>
 *         </DIR>
 *         <Update Content>  <BR><DIR>
 *           - Subtract Work Planned qty in the Work Status Info from the Allocated qty.  <BR>
 *           - Set the process name of the parameter for the Last update process name.  <BR>
 *         </DIR>
 *       </DIR>
 *     </DIR>
 *     - For data with Stock package "disabled": <BR><DIR>
 *       C-4) Delete the Inventory Info.  <BR><DIR>
 *         <Condition for Delete>  <BR><DIR>
 *           Data that corresponds to the stock ID in the Work Status Info obtained at the step 4-1).  <BR>
 *         </DIR>
 *       </DIR>
 *     </DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 5.Allow this class to execute the process to delete allocation. (<CODE>delete()</CODE> method)  <BR>
 * <DIR>
 * Receive the Picking Plan unique key, the Process name, Worker Code, Worker Name, and Terminal No., and execute the processes for restoring the inventory allocation and updating (deleting) the Work Status Info.  <BR>
 * Execute the process for search through the Work Status using the Picking Plan unique key. <BR>
 * Use the Process name, Worker Code, Worker Name, Terminal No. to update the Work Status Info. <BR>
 * <BR>
 *   <Parameter> <BR><DIR>
 *     Connection  <BR>
 *     Picking Plan unique key <BR>
 *     Worker Code <BR>
 *     Worker Name <BR>
 *     Terminal No. <BR>
 *     Process name  <BR>
 *   </DIR>
 *   <Returned information >  <BR><DIR>
 *     Result  <BR>
 *   </DIR>
 *   <Check Process Condition>  <BR><DIR>
 *     5-1.Require to add it to the Work Status Info (DNWORKINFO) using the Picking Plan unique key. <BR>
 *   </DIR>
 *   <Process Content>  <BR><DIR>
 *     5-2.Obtain the availability of Inventory package from the WareNavi system definition info (WARENAVI_SYSTEM). <BR>
 *   </DIR>
 *   <Update Content>  <BR><DIR>
 *     D-1) Load the Inventory Info together with exclusion process. <BR><DIR>
 *       Data that corresponds to the stock ID in the Work Status Info obtained at the step 5-1).  <BR>
 *     </DIR>
 *     D-2) Delete the Work Status Info.  <BR><DIR>
 *       <Condition for Delete>  <BR><DIR>
 *         Data that corresponds to the Work No. obtained at the step 5-1)  <BR>
 *       </DIR>
 *     </DIR>
 *     - For data with Stock package "enabled": <BR><DIR>
 *       D-3) Update Inventory Info.  <BR><DIR>
 *         <Condition for Update>  <BR><DIR>
 *           Data that corresponds to the stock ID in the Work Status Info obtained at the step 5-1).  <BR>
 *         </DIR>
 *         <Update Content>  <BR><DIR>
 *           - Subtract Work Planned qty in the Work Status Info from the Allocated qty.  <BR>
 *           - Set the process name of the parameter for the Last update process name.  <BR>
 *         </DIR>
 *       </DIR>
 *     </DIR>
 *     - For data with Stock package "disabled": <BR><DIR>
 *       D-4) Delete the Inventory Info.  <BR><DIR>
 *         <Condition for Delete>  <BR><DIR>
 *           Data that corresponds to the stock ID in the Work Status Info obtained at the step 5-1).  <BR>
 *         </DIR>
 *       </DIR>
 *     </DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/22</TD><TD>C.Kaminishizono</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:51 $
 * @author  $Author: suresh $
 */
public class RetrievalAllocateOperator extends Object
{
	//#CM722423
	//	Class variables -----------------------------------------------
	//#CM722424
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;


	//#CM722425
	/**
	 * Stock allocation method 
	 * 0: Determine the condition to extract inventory for the respective planned Case Qty and planned Piece Qty. 
	 * 1: Determine the condition to extract inventory data in the Case/Piece division of Plan Info. 
	 * Content of array: in the order of Entry type 0 to 1 
	 */
	public static final int[] wAlloc_kind = { 0, 1 };

	//#CM722426
	/**
	 * Connection object 
	 */
	protected Connection wConn = null;

	//#CM722427
	/**
	 * Inventory Info handler 
	 */
	protected StockHandler wStockHandler = null;

	//#CM722428
	/**
	 * Inventory Info search key 
	 */
	protected StockSearchKey wStockKey = null;

	//#CM722429
	/**
	 * Update key for Inventory Info 
	 */
	protected StockAlterKey wStockAltKey = null;

	//#CM722430
	/**
	 * Availability of Inventory Control 
	 * True: Enabled,  false: Disabled
	 */
	boolean wExistStockPack = false;

	//#CM722431
	/**
	 * Picking Plan Info handler 
	 */
	protected RetrievalPlanHandler wPlanHandler = null;

	//#CM722432
	/**
	 * Picking Plan Info search key 
	 */
	protected RetrievalPlanSearchKey wPlanKey = null;

	//#CM722433
	/**
	 * Picking Plan Info Update key 
	 */
	protected RetrievalPlanAlterKey wPlanAltKey = null;

	//#CM722434
	/**
	 * Work Status Info handler 
	 */
	protected WorkingInformationHandler wWorkHandler = null;

	//#CM722435
	/**
	 * Work Status Info search key 
	 */
	protected WorkingInformationSearchKey wWorkKey = null;

	//#CM722436
	/**
	 * Work Status Info Update key 
	 */
	protected WorkingInformationAlterKey wWorkAltKey = null;

	//#CM722437
	/**
	 * Sequence handler 
	 */
	protected SequenceHandler wSequenceHandler = null;

	//#CM722438
	// Class method --------------------------------------------------
	//#CM722439
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:51 $");
	}
	//#CM722440
	// Constructors --------------------------------------------------
	//#CM722441
	/**
	 * Initialize this class. 
	 */
	public RetrievalAllocateOperator(Connection conn) throws ScheduleException, ReadWriteException
	{
		wConn = conn;
		wStockHandler = new StockHandler(conn);
		wStockKey = new StockSearchKey();
		wStockAltKey = new StockAlterKey();
		wExistStockPack = isExistStockPackage(conn);

		wPlanHandler = new RetrievalPlanHandler(conn);
		wPlanKey = new RetrievalPlanSearchKey();
		wPlanAltKey = new RetrievalPlanAlterKey();

		wWorkHandler = new WorkingInformationHandler(conn);
		wWorkKey = new WorkingInformationSearchKey();
		wWorkAltKey = new WorkingInformationAlterKey();

		wSequenceHandler = new SequenceHandler(conn);

	}

	//#CM722442
	// Public methods ------------------------------------------------
	//#CM722443
	/**
	 * Execute the process for allocating the inventory in the Picking Plan Info and generating the corresponding Work Status Info.  <BR>
	 * @param conn        Connection for database connection 
	 * @param wplanUkey   Picking Plan unique key
	 * @param pWorkCode   Worker Code
	 * @param pWorkName   Worker Name
	 * @param pTermNo     Terminal No.
	 * @param processname  Process name 
	 * @return allocQty   Completed allocation qty 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	public int allocate(
		Connection conn,
		String wplanUkey,
		String pWorkCode,
		String pWorkName,
		String pTermNo,
		String processname)
		throws ReadWriteException, ScheduleException
	{
		int wTotalallocQty = 0; // 引当完了数

		try
		{
			//#CM722444
			// Load the Picking Plan Info. 
			//#CM722445
			// Based on the picking plan unique key passed as a parameter. 
			wPlanKey.KeyClear();
			wPlanKey.setRetrievalPlanUkey(wplanUkey);

			RetrievalPlan wRetPlan = (RetrievalPlan) wPlanHandler.findPrimary(wPlanKey);

			//#CM722446
			//  If corresponding picking plan info is not found, regard DB as error. 
			if (wRetPlan == null)
			{
				throw new ReadWriteException();
			}

			int wRequestQty = 0;
			int wRequestCaseQty = 0;
			int wRequestPieceQty = 0;
			//#CM722447
			// Allocatable Qty 
			int wPossibleQty = 0;
			//#CM722448
			// Remaining qty to the request 
			int wResQty = 0;
			//#CM722449
			// Allocated qty
			int wAllocQty = 0;

			//#CM722450
			// Check the Case/Piece division and set the quantity. 
			if (wRetPlan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_NOTHING))
			{
				wRequestQty = wRetPlan.getPlanQty();
			}
			else
			{
				//#CM722451
				// Compute the requested Case Qty and the requested Piece Qty based on the Picking Plan Info. 
				wRequestCaseQty =
					DisplayUtil.getCaseQty(wRetPlan.getPlanQty(), wRetPlan.getEnteringQty());
				wRequestPieceQty =
					DisplayUtil.getPieceQty(wRetPlan.getPlanQty(), wRetPlan.getEnteringQty());
			}

			//#CM722452
			// Check for availability of Inventory package. 
			if (wExistStockPack)
			{
				//#CM722453
				// Inventory package enabled 
				int wst_allocKind = 0;
				//#CM722454
				// Compute the method to allocate inventory. (from the array of the internal table as stated above) 
				if (wRetPlan.getRegistKind().equals(RetrievalPlan.REGIST_KIND_HOST))
				{
					wst_allocKind = 0;
				}
				else if (wRetPlan.getRegistKind().equals(RetrievalPlan.REGIST_KIND_WMS))
				{
					wst_allocKind = 1;
				}
				
				//#CM722455
				// Inventory Info instance 
				Stock[] wStock = null;

				if (wst_allocKind == 0)
				{
					//#CM722456
					// 0: Determine the condition to extract inventory for the respective planned Case Qty and planned Piece Qty. 
					//#CM722457
					// If the requested Case data is found: 
					if (wRetPlan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_NOTHING))
					{
						//#CM722458
						// Calculate the remaining qty to the request. 
						wResQty = wRequestQty + wResQty;
						wStock =
							(Stock[]) this.getRetrievalStock(
								conn,
								wRetPlan,
								Stock.CASEPIECE_FLAG_NOTHING);
	
						//#CM722459
						// Allocate all of the obtained inventory to the end. 
						for (int plc = 0; plc < wStock.length; plc++)
						{
							//#CM722460
							// Break this when completing all the allocation to the requested qty. 
							if (wResQty <= 0)
								break;
	
							//#CM722461
							// Compute the Allocatable Qty. 
							wPossibleQty = wStock[plc].getStockQty() - wStock[plc].getAllocationQty();
							if (wPossibleQty >= wResQty)
							{
								wTotalallocQty += wResQty;
								wAllocQty = wResQty;
								wResQty = 0;
							}
							else
							{
								wTotalallocQty += wPossibleQty;
								wAllocQty = wPossibleQty;
								wResQty -= wPossibleQty;
							}
							//#CM722462
							// Process to update Inventory Process 
							wStockAltKey.KeyClear();
							wStockAltKey.setStockId(wStock[plc].getStockId());
							wStockAltKey.updateAllocationQty(
								wStock[plc].getAllocationQty() + wAllocQty);
							wStockAltKey.updateLastUpdatePname(processname);
							wStockHandler.modify(wStockAltKey);
							//#CM722463
							// Process to Submit/Add Work Status Info. 
							if (!createWorkInfo(conn,
								wAllocQty,
								Stock.CASEPIECE_FLAG_NOTHING,
								wRetPlan,
								wStock[plc],
								pWorkCode,
								pWorkName,
								pTermNo,
								processname))
								return wTotalallocQty;
						}
	
					}
					else
					{
						if (wRequestCaseQty > 0)
						{
							//#CM722464
							// Calculate the remaining qty to the request. 
							wResQty = wRequestCaseQty * wRetPlan.getEnteringQty();
							wStock =
								(Stock[]) this.getRetrievalStock(
									conn,
									wRetPlan,
									Stock.CASEPIECE_FLAG_CASE);
	
							//#CM722465
							// Allocate all of the obtained inventory to the end. 
							for (int plc = 0; plc < wStock.length; plc++)
							{
								//#CM722466
								// Break this when completing all the allocation to the requested qty. 
								if (wResQty <= 0)
									break;
	
								//#CM722467
								// Compute the Allocatable Qty. 
								wPossibleQty =
									wStock[plc].getStockQty() - wStock[plc].getAllocationQty();
								if (wPossibleQty >= wResQty)
								{
									wTotalallocQty += wResQty;
									wAllocQty = wResQty;
									wResQty = 0;
								}
								else
								{
									wTotalallocQty += wPossibleQty;
									wAllocQty = wPossibleQty;
									wResQty -= wPossibleQty;
								}
								//#CM722468
								// Process to update Inventory Process 
								wStockAltKey.KeyClear();
								wStockAltKey.setStockId(wStock[plc].getStockId());
								wStockAltKey.updateAllocationQty(
									wStock[plc].getAllocationQty() + wAllocQty);
								wStockAltKey.updateLastUpdatePname(processname);
								wStockHandler.modify(wStockAltKey);
								//#CM722469
								// Process to Submit/Add Work Status Info. 
								if (!createWorkInfo(conn,
									wAllocQty,
									Stock.CASEPIECE_FLAG_CASE,
									wRetPlan,
									wStock[plc],
									pWorkCode,
									pWorkName,
									pTermNo,
									processname))
									return wTotalallocQty;
							}
	
						}
						//#CM722470
						// If the requested Piece data is found: 
						if (wRequestPieceQty > 0)
						{
							//#CM722471
							// Calculate the remaining qty to the request. 
							wResQty = wRequestPieceQty + wResQty;
							wStock =
								(Stock[]) this.getRetrievalStock(
									conn,
									wRetPlan,
									Stock.CASEPIECE_FLAG_PIECE);
	
							//#CM722472
							// Allocate all of the obtained inventory to the end. 
							for (int plc = 0; plc < wStock.length; plc++)
							{
								//#CM722473
								// Break this when completing all the allocation to the requested qty. 
								if (wResQty <= 0)
									break;
	
								//#CM722474
								// Compute the Allocatable Qty. 
								wPossibleQty =
									wStock[plc].getStockQty() - wStock[plc].getAllocationQty();
								if (wPossibleQty >= wResQty)
								{
									wTotalallocQty += wResQty;
									wAllocQty = wResQty;
									wResQty = 0;
								}
								else
								{
									wTotalallocQty += wPossibleQty;
									wAllocQty = wPossibleQty;
									wResQty -= wPossibleQty;
								}
								//#CM722475
								// Process to update Inventory Process 
								wStockAltKey.KeyClear();
								wStockAltKey.setStockId(wStock[plc].getStockId());
								wStockAltKey.updateAllocationQty(
									wStock[plc].getAllocationQty() + wAllocQty);
								wStockAltKey.updateLastUpdatePname(processname);
								wStockHandler.modify(wStockAltKey);
								//#CM722476
								// Process to Submit/Add Work Status Info. 
								if (!createWorkInfo(conn,
									wAllocQty,
									Stock.CASEPIECE_FLAG_PIECE,
									wRetPlan,
									wStock[plc],
									pWorkCode,
									pWorkName,
									pTermNo,
									processname))
									return wTotalallocQty;
							}
						}
					}
				}
				else
				{
					//#CM722477
					// 1: Determine the condition to extract inventory data in the Case/Piece division of Plan Info. 
					//#CM722478
					// Calculate the remaining qty to the request. 
					wResQty = wRetPlan.getPlanQty();
					wStock =
						(Stock[]) this.getRetrievalStock(
							conn,
							wRetPlan,
							wRetPlan.getCasePieceFlag());
	
					//#CM722479
					// Allocate all of the obtained inventory to the end. 
					for (int plc = 0; plc < wStock.length; plc++)
					{
						//#CM722480
						// Break this when completing all the allocation to the requested qty. 
						if (wResQty <= 0)
							break;
	
						//#CM722481
						// Compute the Allocatable Qty. 
						wPossibleQty = wStock[plc].getStockQty() - wStock[plc].getAllocationQty();
						if (wPossibleQty >= wResQty)
						{
							wTotalallocQty += wResQty;
							wAllocQty = wResQty;
							wResQty = 0;
						}
						else
						{
							wTotalallocQty += wPossibleQty;
							wAllocQty = wPossibleQty;
							wResQty -= wPossibleQty;
						}
						//#CM722482
						// Process to update Inventory Process 
						wStockAltKey.KeyClear();
						wStockAltKey.setStockId(wStock[plc].getStockId());
						wStockAltKey.updateAllocationQty(
							wStock[plc].getAllocationQty() + wAllocQty);
						wStockAltKey.updateLastUpdatePname(processname);
						wStockHandler.modify(wStockAltKey);
						//#CM722483
						// Process to Submit/Add Work Status Info. 
						if (!createWorkInfo(conn,
							wAllocQty,
							wRetPlan.getCasePieceFlag(),
							wRetPlan,
							wStock[plc],
							pWorkCode,
							pWorkName,
							pTermNo,
							processname))
							return wTotalallocQty;
					}
					
				}
			}
			else
			{
				//#CM722484
				// Inventory package disabled
				//#CM722485
				// Add the Inventory Info. (Inventory standby for Picking: in the status where Allocated qty has been summed-up) 
				//#CM722486
				// For data with "None": 
				if (wRequestQty > 0)
				{
					//#CM722487
					// Calculate the remaining qty to the request. 
					wResQty = wRequestQty;
					//#CM722488
					// Process to submit/add Inventory Process 
					Stock iStock =
						(Stock) this.createStock(
							conn,
							wResQty,
							Stock.CASEPIECE_FLAG_NOTHING,
							wRetPlan,
							processname);

					wTotalallocQty += wResQty;

					//#CM722489
					// Process to Submit/Add Work Status Info. 
					if (!createWorkInfo(conn,
						wResQty,
						Stock.CASEPIECE_FLAG_NOTHING,
						wRetPlan,
						iStock,
						pWorkCode,
						pWorkName,
						pTermNo,
						processname))
						return wTotalallocQty;
				}
				//#CM722490
				// Mixed or Case 
				if (wRequestCaseQty > 0)
				{
					//#CM722491
					// Calculate the remaining qty to the request. 
					wResQty = wRequestCaseQty * wRetPlan.getEnteringQty();
					//#CM722492
					// Process to submit/add Inventory Process 
					Stock iStock =
						(Stock) this.createStock(
							conn,
							wResQty,
							Stock.CASEPIECE_FLAG_CASE,
							wRetPlan,
							processname);

					wTotalallocQty += wResQty;

					//#CM722493
					// Process to Submit/Add Work Status Info. 
					if (!createWorkInfo(conn,
						wResQty,
						Stock.CASEPIECE_FLAG_CASE,
						wRetPlan,
						iStock,
						pWorkCode,
						pWorkName,
						pTermNo,
						processname))
						return wTotalallocQty;
				}
				//#CM722494
				// Mixed or Piece 
				if (wRequestPieceQty > 0)
				{
					//#CM722495
					// Calculate the remaining qty to the request. 
					wResQty = wRequestPieceQty;
					//#CM722496
					// Process to submit/add Inventory Process 
					Stock iStock =
						(Stock) this.createStock(
							conn,
							wResQty,
							Stock.CASEPIECE_FLAG_PIECE,
							wRetPlan,
							processname);

					wTotalallocQty += wResQty;

					//#CM722497
					// Process to Submit/Add Work Status Info. 
					if (!createWorkInfo(conn,
						wResQty,
						Stock.CASEPIECE_FLAG_PIECE,
						wRetPlan,
						iStock,
						pWorkCode,
						pWorkName,
						pTermNo,
						processname))
						return wTotalallocQty;
				}
			}

			return wTotalallocQty;

		}
		catch (DataExistsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
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

	}

	//#CM722498
	/**
	 * Execute the process for cancel the allocation of the Picking Plan Info.  <BR>
	 * Execute the process for restoring the allocation of Inventory Info and updating the Work Status Info (with status Deleted).  <BR>
	 * @param conn        Connection for database connection 
	 * @param wplanUkey   Picking Plan unique key
	 * @param pWorkCode   Worker Code
	 * @param pWorkName   Worker Name
	 * @param pTermNo     Terminal No.
	 * @param processname  Process name 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	public boolean cancel(
		Connection conn,
		String wplanUkey,
		String pWorkCode,
		String pWorkName,
		String pTermNo,
		String processname)
		throws ReadWriteException, ScheduleException
	{

		try
		{
			//#CM722499
			// Load the Work Status Info (by designating exclusively). 
			wWorkKey.KeyClear();
			//#CM722500
			// Picking Plan unique key matches. 
			wWorkKey.setPlanUkey(wplanUkey);
			//#CM722501
			// Work type (Picking) 
			wWorkKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			//#CM722502
			// For data with Status flag "Standby" or "Started" only: 
			String[] ckStatus =
				{ WorkingInformation.STATUS_FLAG_UNSTART, WorkingInformation.STATUS_FLAG_START };
			wWorkKey.setStatusFlag(ckStatus);

			WorkingInformation[] workinfo =
				(WorkingInformation[]) wWorkHandler.findForUpdate(wWorkKey);

			//#CM722503
			// Announce false if no target Work Status Info was found. 
			if (workinfo.length <= 0)
				return false;

			//#CM722504
			// Execute the process for Work Status Info. 
			for (int plc = 0; plc < workinfo.length; plc++)
			{
				//#CM722505
				// Read the Inventory Info (by designating exclusively). 
				wStockKey.KeyClear();
				//#CM722506
				// Data that corresponds to the stock ID in the Work Status Info. 
				wStockKey.setStockId(workinfo[plc].getStockId());

				Stock stock = (Stock) wStockHandler.findPrimaryForUpdate(wStockKey);

				//#CM722507
				// Update the Work Status Info. 
				wWorkAltKey.KeyClear();
				//#CM722508
				// Data that corresponds to the Work No. 
				wWorkAltKey.setJobNo(workinfo[plc].getJobNo());
				//#CM722509
				// Delete the Status flag. 
				wWorkAltKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_DELETE);
				wWorkAltKey.updateLastUpdatePname(processname);

				wWorkHandler.modify(wWorkAltKey);

				//#CM722510
				// Update the inventory information.
				wStockAltKey.KeyClear();
				//#CM722511
				// Stock ID matches. 
				wStockAltKey.setStockId(stock.getStockId());
				//#CM722512
				// Check for availability of Inventory package. 
				if (!wExistStockPack)
				{
					//#CM722513
					// Inventory package disabled
					//#CM722514
					// Subtract the Workable Qty in the Work Status Info from the stock qty. 
					wStockAltKey.updateStockQty(
						stock.getStockQty() - workinfo[plc].getPlanEnableQty());
					//#CM722515
					// Update the status to "Completed". 
					wStockAltKey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
				}
				else
				{
					//#CM722516
					// Sum-up Workable Qty in the Work Status Info to the Allocatable Qty. 
					wStockAltKey.updateAllocationQty(
						stock.getAllocationQty() + workinfo[plc].getPlanEnableQty());
				}
				//#CM722517
				// Update the last Update process name. 
				wStockAltKey.updateLastUpdatePname(processname);
				wStockHandler.modify(wStockAltKey);
			}
			return true;
		}
		catch (NoPrimaryException e)
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

	}

	//#CM722518
	/**
	 * Execute the process for cancel the allocation of the Picking Plan Info.  <BR>
	 * Execute the process for restoring the allocation of Inventory Info and deleting the Work Status Info.  <BR>
	 * @param conn        Connection for database connection 
	 * @param wplanUkey   Picking Plan unique key
	 * @param pWorkCode   Worker Code
	 * @param pWorkName   Worker Name
	 * @param pTermNo     Terminal No.
	 * @param processname  Process name 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	public boolean delete(
		Connection conn,
		String wplanUkey,
		String pWorkCode,
		String pWorkName,
		String pTermNo,
		String processname)
		throws ReadWriteException, ScheduleException
	{

		try
		{
			//#CM722519
			// Load the Work Status Info (by designating exclusively). 
			wWorkKey.KeyClear();
			//#CM722520
			// Picking Plan unique key matches. 
			wWorkKey.setPlanUkey(wplanUkey);
			//#CM722521
			// Work type (Picking) 
			wWorkKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			//#CM722522
			// For data with Status flag "Standby" or "Started" only: 
			String[] ckStatus =
				{ WorkingInformation.STATUS_FLAG_UNSTART, WorkingInformation.STATUS_FLAG_START };
			wWorkKey.setStatusFlag(ckStatus);

			WorkingInformation[] workinfo =
				(WorkingInformation[]) wWorkHandler.findForUpdate(wWorkKey);

			//#CM722523
			// Announce false if no target Work Status Info was found. 
			if (workinfo.length <= 0)
				return false;

			//#CM722524
			// Execute the process for Work Status Info. 
			for (int plc = 0; plc < workinfo.length; plc++)
			{
				//#CM722525
				// Read the Inventory Info (by designating exclusively). 
				wStockKey.KeyClear();
				//#CM722526
				// Data that corresponds to the stock ID in the Work Status Info. 
				wStockKey.setStockId(workinfo[plc].getStockId());

				Stock stock = (Stock) wStockHandler.findPrimaryForUpdate(wStockKey);

				//#CM722527
				// Delete the Work Status Info. 
				//#CM722528
				// In the loaded Work Status Info (Instance) 

				wWorkHandler.drop(workinfo[plc]);

				//#CM722529
				// Check for availability of Inventory package. 
				if (wExistStockPack)
				{
					//#CM722530
					// Inventory package enabled 
					//#CM722531
					// Update the inventory information.
					wStockAltKey.KeyClear();
					//#CM722532
					// Stock ID matches. 
					wStockAltKey.setStockId(stock.getStockId());
					//#CM722533
					// Subtract Workable Qty in the Work Status Info from the Allocated qty. 
					wStockAltKey.updateAllocationQty(
						stock.getAllocationQty() - workinfo[plc].getPlanEnableQty());
					//#CM722534
					// Update the last Update process name. 
					wStockAltKey.updateLastUpdatePname(processname);

					wStockHandler.modify(wStockAltKey);
				}
				else
				{
					//#CM722535
					// Inventory package disabled
					//#CM722536
					// Delete the Inventory Info. 
					//#CM722537
					// Delete it in the loaded Inventory Info (Instance). 
					wStockHandler.drop(stock);
				}
			}
			return true;
		}
		catch (NoPrimaryException e)
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

	}
	//#CM722538
	// Package methods -----------------------------------------------

	//#CM722539
	// Protected methods ---------------------------------------------
	//#CM722540
	/**
	 * Invoke this method in the process for extracting the target inventory.  <BR>
	 * Based on the count of processes, return the order to obtain the Inventory Info.  <BR>
	 * Process is divided by Case/Piece division.  <BR>
	 * Announce termination as false, after completed the specified number of processes (Max).  <BR>
	 * @param sKey        SearchKey instance to set the order to obtain data 
	 * @param cpflag      Case/Piece division
	 * @param loopcount   Count of Processes 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected boolean selectStockOrder(StockSearchKey sKey, String cpflag, int loopcount)
		throws ReadWriteException
	{
		//#CM722541
		// Define an array to be obtained upon Case allocation. 
		//#CM722542
		// In the order of Case --> None --> Piece 
		String[] wCaseOrder =
			{ Stock.CASEPIECE_FLAG_CASE, Stock.CASEPIECE_FLAG_NOTHING, Stock.CASEPIECE_FLAG_PIECE };

		//#CM722543
		// Define an array to be obtained upon Piece allocation. 
		//#CM722544
		// In the order of Piece --> None --> Case
		String[] wPieceOrder =
			{
				Stock.CASEPIECE_FLAG_PIECE,
				Stock.CASEPIECE_FLAG_NOTHING,
				Stock.CASEPIECE_FLAG_CASE,
			};

		//#CM722545
		// Define an array to be obtained upon allocation of work with division None. 
		//#CM722546
		// In the order of None --> Piece --> Case 
		String[] wNoflagOrder =
			{ Stock.CASEPIECE_FLAG_NOTHING, Stock.CASEPIECE_FLAG_PIECE, Stock.CASEPIECE_FLAG_CASE };

		if (cpflag.equals(Stock.CASEPIECE_FLAG_CASE))
		{
			if (wCaseOrder.length <= loopcount)
				return false;
			sKey.setCasePieceFlag(wCaseOrder[loopcount]);
		}
		else if (cpflag.equals(Stock.CASEPIECE_FLAG_PIECE))
		{
			if (wPieceOrder.length <= loopcount)
				return false;
			sKey.setCasePieceFlag(wPieceOrder[loopcount]);
		}
		else if (cpflag.equals(Stock.CASEPIECE_FLAG_NOTHING))
		{
			if (wNoflagOrder.length <= loopcount)
				return false;
			sKey.setCasePieceFlag(wNoflagOrder[loopcount]);
		}
		else
		{
			return false;
		}
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			sKey.setUseByDateOrder(1, true);
			sKey.setInstockDateOrder(2, true);
		}
		else
		{
			sKey.setInstockDateOrder(1, true);
		}
		return true;
	}

	//#CM722547
	/**
	 * Invoke this method in the allocation process.  <BR>
	 * Return the target Inventory Info that corresponds the Picking Plan Info.  <BR>
	 * Process is divided by Case/Piece division.  <BR>
	 * Announce Stock[0] when target inventory was not found.  <BR>
	 * @param conn        Connection for database connection 
	 * @param wRetPlan    Instance of the target Picking Plan Info 
	 * @param cpflag      Case/Piece division
	 * @return Stock []     Inventory Info instance 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected Stock[] getRetrievalStock(Connection conn, RetrievalPlan wRetPlan, String cpflag)
		throws ReadWriteException
	{
		Vector vec = new Vector();

		for (int ilc = 0;; ilc++)
		{
			//#CM722548
			// Compile the condition to extract the target inventory using the Picking Plan Info. 
			wStockKey.KeyClear();
			//#CM722549
			// Data that corresponds to the Consignor Code of the Plan Info 
			wStockKey.setConsignorCode(wRetPlan.getConsignorCode());
			//#CM722550
			// Data that corresponds to the Item Code of the Plan Info 
			wStockKey.setItemCode(wRetPlan.getItemCode());
			//#CM722551
			// For data with the specified Picking Location in the Plan Info, which corresponds to the designated Location No. 
			if (wRetPlan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_CASE))
			{
				if (!StringUtil.isBlank(wRetPlan.getCaseLocation()))
				{
					wStockKey.setLocationNo(wRetPlan.getCaseLocation());
				}
			}
			else if (wRetPlan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_PIECE))
			{
				if (!StringUtil.isBlank(wRetPlan.getPieceLocation()))
				{
					wStockKey.setLocationNo(wRetPlan.getPieceLocation());
				}
			}
			else if (wRetPlan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_NOTHING))
			{
				if (!StringUtil.isBlank(wRetPlan.getCaseLocation()))
				{
					wStockKey.setLocationNo(wRetPlan.getCaseLocation());
				}
				else if (!StringUtil.isBlank(wRetPlan.getPieceLocation()))
				{
					wStockKey.setLocationNo(wRetPlan.getPieceLocation());
				}
			}
			else if (wRetPlan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_MIX))
			{
				if (cpflag.equals(Stock.CASEPIECE_FLAG_CASE))
				{
					if (!StringUtil.isBlank(wRetPlan.getCaseLocation()))
					{
						wStockKey.setLocationNo(wRetPlan.getCaseLocation());
					}
				}
				else if (cpflag.equals(Stock.CASEPIECE_FLAG_PIECE))
				{
					if (!StringUtil.isBlank(wRetPlan.getPieceLocation()))
					{
						wStockKey.setLocationNo(wRetPlan.getPieceLocation());
					}
				}
			}
			//#CM722552
			// Data only with status flag Center Stock 
			wStockKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);

			//#CM722553
			// Using the Order method for obtaining data, set the loading order. 
			boolean rStatus = this.selectStockOrder(wStockKey, cpflag, ilc);
			//#CM722554
			// Close the loop when completed obtaining all data. 
			if (rStatus == false)
				break;

			//#CM722555
			// Load the Inventory Info using conditions for obtaining data as stated above. (exclusive loading) 
			Stock[] rStock = (Stock[]) wStockHandler.findForUpdate(wStockKey);

			//#CM722556
			// Compile the obtained info into Vector. 
			for (int slc = 0; slc < rStock.length; slc++)
			{
				//#CM722557
				// Skip any information in which (stock qty) - (Allocated qty) <= 1 
				//#CM722558
				// Check here. Disable to use condition to extract data. 
				if (rStock[slc].getStockQty() <= rStock[slc].getAllocationQty())
					continue;
				//#CM722559
				// Store it in the VECTOR area. 
				vec.addElement((Stock) rStock[slc]);
			}

		}
		Stock[] wStock = new Stock[vec.size()];
		vec.copyInto(wStock);

		return (Stock[]) wStock;
	}

	//#CM722560
	/**
	 * Add the Inventory Info table. 
	 * For data with Inventory package disabled, generate the Inventory as Standby for picking.
	 * 
	 * @param  conn        Instance to maintain database connection. 
	 * @param  allocqty    Allocated qty
	 * @param  cpflag      Case/Piece division
	 * @param  retplan     RetrievalPlan class instance with contents of Picking Plan Info
	 * @param  processname Process name 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws DataExistsException Announce this when trying to input any information of which content is the same as one that has been already added. 
	 */
	protected Stock createStock(
		Connection conn,
		int allocqty,
		String cpflag,
		RetrievalPlan retplan,
		String processname)
		throws ReadWriteException, DataExistsException
	{
		try
		{
			Stock stock = new Stock();

			//#CM722561
			// Stock ID
			String stockId_seqno = wSequenceHandler.nextStockId();
			stock.setStockId(stockId_seqno);
			//#CM722562
			// Plan unique key 
			stock.setPlanUkey(retplan.getRetrievalPlanUkey());
			//#CM722563
			// Location No.			
			if (cpflag.equals(Stock.CASEPIECE_FLAG_CASE))
			{
				stock.setLocationNo(retplan.getCaseLocation());
			}
			else if (cpflag.equals(Stock.CASEPIECE_FLAG_PIECE))
			{
				stock.setLocationNo(retplan.getPieceLocation());
			}
			else
			{
				if (!retplan.getCaseLocation().equals(""))
					stock.setLocationNo(retplan.getCaseLocation());
				else
					stock.setLocationNo(retplan.getPieceLocation());
			}
			//#CM722564
			// Item Code
			stock.setItemCode(retplan.getItemCode());
			//#CM722565
			// Item Name
			stock.setItemName1(retplan.getItemName1());
			//#CM722566
			// Inventory status (Standby for Picking) 
			stock.setStatusFlag(Stock.STOCK_STATUSFLAG_RETRIVALRESERVE);
			//#CM722567
			// Stock qty (Picking qty) 
			stock.setStockQty(allocqty);
			//#CM722568
			// Allocatable Qty 
			stock.setAllocationQty(0);
			//#CM722569
			// Storage Planned Qty 
			stock.setPlanQty(0);
			//#CM722570
			// Case/Piece division (Load Size) 
			stock.setCasePieceFlag(cpflag);
			//#CM722571
			// Last Picking Date 
			stock.setLastShippingDate("");
			//#CM722572
			// Expiry Date
			stock.setUseByDate(retplan.getUseByDate());
			//#CM722573
			// Lot No.
			stock.setLotNo(retplan.getLotNo());
			//#CM722574
			// Plan information Comment 
			stock.setPlanInformation(retplan.getPlanInformation());
			//#CM722575
			// Consignor Code
			stock.setConsignorCode(retplan.getConsignorCode());
			//#CM722576
			// Consignor Name
			stock.setConsignorName(retplan.getConsignorName());
			//#CM722577
			// Supplier Code 
			stock.setSupplierCode("");
			//#CM722578
			// Supplier Name 
			stock.setSupplierName1("");
			//#CM722579
			// Packed Qty per Case
			stock.setEnteringQty(retplan.getEnteringQty());
			//#CM722580
			// Packed qty per bundle
			stock.setBundleEnteringQty(retplan.getBundleEnteringQty());
			//#CM722581
			// Case ITF
			stock.setItf(retplan.getItf());
			//#CM722582
			// Bundle ITF
			stock.setBundleItf(retplan.getBundleItf());
			//#CM722583
			// Name of Add Process
			stock.setRegistPname(processname);
			//#CM722584
			// Last update process name
			stock.setLastUpdatePname(processname);

			//#CM722585
			// Registration of data 
			wStockHandler.create(stock);

			return stock;

		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM722586
	/**
	 * Add the Work Status Info table. 
	 * @param  conn        Instance to maintain database connection. 
	 * @param  allocqty    Allocated qty
	 * @param  cpflag      Case/Piece division
	 * @param  retplan     RetrievalPlan class instance with contents of Picking Plan Info
	 * @param  wstock      Stock class instance with contents of Inventory Info
	 * @param  pWorkCode   Worker Code
	 * @param  pWorkName   Worker Name
	 * @param  pTermNo     Terminal No.
	 * @param  processname Process name 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws DataExistsException Announce this when trying to input any information of which content is the same as one that has been already added. 
	 */
	protected boolean createWorkInfo(
		Connection conn,
		int allocqty,
		String cpflag,
		RetrievalPlan retplan,
		Stock wstock,
		String pWorkCode,
		String pWorkName,
		String pTermNo,
		String processname)
		throws ReadWriteException, DataExistsException
	{
		try
		{

			WorkingInformation workinfo = new WorkingInformation();

			//#CM722587
			// Work No.
			String wjobno = wSequenceHandler.nextJobNo();
			workinfo.setJobNo(wjobno);
			//#CM722588
			// Work type (Picking) 
			workinfo.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			//#CM722589
			// Aggregation Work No.
			workinfo.setCollectJobNo(wjobno);
			//#CM722590
			// Status flag (Standby) 
			workinfo.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			//#CM722591
			// Start Work flag ("Started") 
			workinfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			//#CM722592
			// Plan unique key (from Picking Plan Info) 
			workinfo.setPlanUkey(retplan.getRetrievalPlanUkey());
			//#CM722593
			// Stock ID (from the Inventory Info) 
			workinfo.setStockId(wstock.getStockId());
			//#CM722594
			// Area No. (from the Inventory Info  ) 
			workinfo.setAreaNo(wstock.getAreaNo());
			//#CM722595
			// Location No. (from the Inventory Info) 
			workinfo.setLocationNo(wstock.getLocationNo());
			//#CM722596
			// Planned date (from the Picking Plan Info) 
			workinfo.setPlanDate(retplan.getPlanDate());
			//#CM722597
			// Consignor Code (from the Picking Plan Info) 
			workinfo.setConsignorCode(retplan.getConsignorCode());
			//#CM722598
			// Consignor Name (from the Picking Plan Info) 
			workinfo.setConsignorName(retplan.getConsignorName());
			//#CM722599
			// Supplier Code 
			workinfo.setSupplierCode("");
			//#CM722600
			// Supplier Name 
			workinfo.setSupplierName1("");
			//#CM722601
			// Receiving Ticket No. 
			workinfo.setInstockTicketNo("");
			//#CM722602
			// Receiving ticket line 
			workinfo.setInstockLineNo(0);
			//#CM722603
			// Customer Code (from the Picking Plan Info) 
			workinfo.setCustomerCode(retplan.getCustomerCode());
			//#CM722604
			// Customer Name (from the Picking Plan Info) 
			workinfo.setCustomerName1(retplan.getCustomerName1());
			//#CM722605
			// Shipping Ticket No. (from the Picking Plan Info) 
			workinfo.setShippingTicketNo(retplan.getShippingTicketNo());
			//#CM722606
			// Shipping ticket line (from the Picking Plan Info) 
			workinfo.setShippingLineNo(retplan.getShippingLineNo());
			//#CM722607
			// Item Code (from the Picking Plan Info) 
			workinfo.setItemCode(retplan.getItemCode());
			//#CM722608
			// Item Name (from the Picking Plan Info) 
			workinfo.setItemName1(retplan.getItemName1());
			//#CM722609
			// Host Planned Qty (from the Picking Plan Info) 
			workinfo.setHostPlanQty(retplan.getPlanQty());
			//#CM722610
			// Work Planned qty 
			workinfo.setPlanQty(allocqty);
			//#CM722611
			// Workable qty
			workinfo.setPlanEnableQty(allocqty);
			//#CM722612
			// Result qty
			workinfo.setResultQty(0);
			//#CM722613
			// Shortage Qty
			workinfo.setShortageCnt(0);
			//#CM722614
			// Pending Qty
			workinfo.setPendingQty(0);
			//#CM722615
			// Packed Qty per Case (from the Picking Plan Info) 
			workinfo.setEnteringQty(retplan.getEnteringQty());
			//#CM722616
			// Packed qty per bundle (from the Picking Plan Info) 
			workinfo.setBundleEnteringQty(retplan.getBundleEnteringQty());
			//#CM722617
			// Case/Piece division: Work Type 
			if (retplan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_MIX))
			{
				if (cpflag.equals(Stock.CASEPIECE_FLAG_CASE))
				{
					workinfo.setWorkFormFlag(RetrievalPlan.CASEPIECE_FLAG_CASE);
				}
				else if (cpflag.equals(Stock.CASEPIECE_FLAG_PIECE))
				{
					workinfo.setWorkFormFlag(RetrievalPlan.CASEPIECE_FLAG_PIECE);
				}
			}
			else
			{
				workinfo.setWorkFormFlag(retplan.getCasePieceFlag());
			}
			//#CM722618
			// Case/Piece division: Load Size 
			workinfo.setCasePieceFlag(wstock.getCasePieceFlag());
			//#CM722619
			// Case ITF (from the Picking Plan Info) 
			workinfo.setItf(retplan.getItf());
			//#CM722620
			// Bundle ITF (from the Picking Plan Info) 
			workinfo.setBundleItf(retplan.getBundleItf());
			//#CM722621
			// TC/DC division (DC) 
			workinfo.setTcdcFlag(Stock.TCDC_FLAG_DC);
			//#CM722622
			// Expiry Date (from the Inventory Info) 
			workinfo.setUseByDate(wstock.getUseByDate());
			//#CM722623
			// Lot No. (from the Inventory Info) 
			workinfo.setLotNo(retplan.getLotNo());
			//#CM722624
			// Plan information Comment (from the Picking Plan Info) 
			workinfo.setPlanInformation(retplan.getPlanInformation());
			//#CM722625
			// Order No. (from the Picking Plan Info) 
			if (retplan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_MIX))
			{
				if (cpflag.equals(Stock.CASEPIECE_FLAG_CASE))
				{
					workinfo.setOrderNo(retplan.getCaseOrderNo());
				}
				else if (cpflag.equals(Stock.CASEPIECE_FLAG_PIECE))
				{
					workinfo.setOrderNo(retplan.getPieceOrderNo());
				}
			}
			else if (retplan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_CASE))
			{
				workinfo.setOrderNo(retplan.getCaseOrderNo());
			}
			else if (retplan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_PIECE))
			{
				workinfo.setOrderNo(retplan.getPieceOrderNo());
			}
			else if (retplan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_NOTHING))
			{
				if (!StringUtil.isBlank(retplan.getCaseOrderNo()))
				{
					workinfo.setOrderNo(retplan.getCaseOrderNo());
				}
				else
				{
					workinfo.setOrderNo(retplan.getPieceOrderNo());
				}
			}
			//#CM722626
			// Order Date 
			workinfo.setOrderingDate("");
			workinfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
			//#CM722627
			// Batch No. (from the Picking Plan Info) 
			workinfo.setBatchNo(retplan.getBatchNo());
			//#CM722628
			// Plan information Added Date/Time (from the Picking Plan Info) 
			workinfo.setPlanRegistDate(retplan.getRegistDate());
			//#CM722629
			// Name of Add Process
			workinfo.setRegistPname(processname);
			//#CM722630
			// Last update process name
			workinfo.setLastUpdatePname(processname);

			//#CM722631
			// Registration of data 
			wWorkHandler.create(workinfo);

			return true;

		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM722632
	// Private methods -----------------------------------------------
	//#CM722633
	/**
	 * Referring to the System definition table (WARENAVI_SYSTEM), obtain the availability of Inventory Control package. <BR>
	 * Return true if inventory control package is enabled, or return false if it is disabled. 
	 * 
	 * @param conn Database connection object
	 * @return Availability of Inventory package. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected boolean isExistStockPackage(Connection conn)
		throws ReadWriteException, ScheduleException
	{

		WareNaviSystem wms = new WareNaviSystem();

		WareNaviSystemSearchKey skey = new WareNaviSystemSearchKey();
		WareNaviSystemHandler wnhdl = new WareNaviSystemHandler(conn);

		String stockPack = "";

		try
		{
			wms = (WareNaviSystem) wnhdl.findPrimary(skey);
			if (wms == null)
			{
				//#CM722634
				//6006002=Database error occurred.{0}
				RmiMsgLogClient.write(6006039, LogMessage.F_ERROR, "WareNaviSystemHandler", null);
				throw (new ScheduleException("6007039" + wDelim + "WARENAVI_SYSTEM"));
			}

			stockPack = wms.getStockPack();
			if (stockPack.equals(WareNaviSystem.PACKAGE_FLAG_ADDON))
				return true;

		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());			
		}
		
		return false;
	}
}
//#CM722635
//end of class
