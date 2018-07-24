package jp.co.daifuku.wms.retrieval.schedule;

//#CM723568
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.dbhandler.RetrievalWorkingInformationHandler;
import jp.co.daifuku.wms.retrieval.dbhandler.RetrievalWorkingInformationReportFinder;

//#CM723569
/**
 * Designer : Y.Osawa <BR>
 * Maker : Y.Osawa  <BR>
 * <BR>
 * Allow this class to cancel the WEB Order Picking Allocation.  <BR>
 * Receive the contents entered via screen as a parameter, and execute the process to cancel the Order Picking allocation.  <BR>
 * Allow each methods contained in this class to receive a connection object and executes the process for updating the database, but <BR>
 * disable to commit nor roll-back the transaction.  <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind()</CODE> method)  <BR><BR><DIR>
 *   Return the corresponding Consignor Code, if only one Consignor Code exists in the Work Status Info(Picking).  <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
 * <BR>
 *   <Search conditions> <BR><DIR>
 *     (System identification key: any key other than AS/RS (2),  AND Status flag: Standby (0) OR<BR>
 *     System identification key: AS/RS (2) AND Status flag: Working (2)) <BR>
 *     Work type: Picking. (3)  <BR>
 *     Order No.: other than NULL  </DIR></DIR>
 * <BR>
 * 2. Process by clicking "Setup" button (<CODE>startSCH()</CODE> method)  <BR><BR><DIR>
 *   Receive the content displayed in the input field item, as a parameter, and execute the process for canceling the Order Picking allocation.  <BR>
 *   Return true if the process normally completed, or return false if failed. 
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error  <BR>
 *   Update Plan Info, Work Status Info, Inventory Info, Transport info, and then Pallet Info in this sequence. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Consignor Code* <BR>
 *     Planned Picking Date* <BR>
 *     Start Order No.  <BR>
 *     End Order No.  <BR>
 *     Case/Piece division* <BR></DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     true or false <BR></DIR>
 * <BR>
 *   [Check Process Condition]  <BR>
 *   -"Check" process for Worker Code-(<CODE>AbstructRetrievalSCH()</CODE> class) <BR>
 *   <BR>
 *   - Execute the process for checking the data with Daily Update Processing. -(<CODE>AbstructRetrievalSCH()</CODE> class)  <BR>
 *   <BR>
 *   -"Check" process for "Load" flag-(<CODE>AbstructRetrievalSCH()</CODE> class)  <BR>
 *   <BR>
 *   - Off-line check (AS/RS)Process-(<CODE>AbstructRetrievalSCH()</CODE> class)  <BR>
 *   <BR>
 *   [Update Process]  <BR>
 *   <BR>
 *   -Process to lock the Work Status Info and the Inventory Info. -<BR>
 *   <BR>
 *   <DIR>
 *     Based on the Consignor Code, Planned Picking Date, Batch No., Order No., Case/Piece division passed as a parameter, 
 *     lock the Work Status Info and the Inventory Info.  <BR>
 *   </DIR>
 *   <BR>
 *   -Obtain the Work Status Info (to update the Picking Plan) -<BR>
 *   <BR>
 *   <DIR>
 *     Based on the Consignor Code, Planned Picking Date, Batch No., Order No., Case/Piece division passed as a parameter, 
 *     obtain the Work Status Info by each plan unique key, and update the picking plan.  <BR>
 *   </DIR>
 *   <BR>
 *   -Update the Picking Plan Info.-<BR>
 *   <BR>
 *   <DIR>
 *     Based on the plan unique key passed as a parameter, execute the process for updating the picking plan.  <BR>
 *         1.Update the schedule flag.<BR>
 *         2.Update the Status flag. <BR>
 *         3.Decrease the Schedule Shortage Qty. <BR>
 *   </DIR>
 *   <BR>
 *   - Obtain the Work Status Info (for canceling the allocation). -<BR>
 *   <BR>
 *   <DIR>
 *     Based on the Consignor Code, Planned Picking Date, Batch No., Order No., Case/Piece division passed as a parameter, 
 *     obtain the Work Status Info in the order of the work No., and cancel the allocation by each work No.  <BR>
 *   </DIR>
 *   <BR>
 *   - Process to cancel allocation of Picking -<BR>
 *   <BR>
 *   <DIR>
 *     Based on the work status passed as a parameter, execute the process for canceling the allocation.  <BR>
 *         1.Delete the Work Status Info. <BR>
 *         2.Update the Inventory Info.<BR>
 *         3. Delete transport info (Only for work data of AS/RS) <BR>
 *         4.Update the Pallet information (Only AS/RS work data). <BR>
 *   </DIR>
 *   <BR>
 *   <Parameter> <BR><DIR>
 *     Connection  <BR>
 *     Work Status Info <BR>
 *   </DIR>
 *   <BR>
 *   <Returned data> <BR><DIR>
 *     true or false <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/19</TD><TD>S.Yoshida</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:57 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderAllocClearSCH extends AbstractRetrievalSCH
{

	//#CM723570
	// Class variables -----------------------------------------------
	//#CM723571
	/**
	 * Process name 
	 */
	protected static final String wProcessName = "RetrievalOrderAllocClear";

	//#CM723572
	/**
	 * Case/Piece division (Case) 
	 */
	public static final int CASEPIECE_FLAG_CASE_POSITION = 0 ;
	
	//#CM723573
	/**
	 * Case/Piece division (Piece) 
	 */
	public static final int CASEPIECE_FLAG_PIECE_POSITION = 1 ;

	//#CM723574
	/**
	 * Shortage Qty (Schedule Shortage) 
	 */
	public static final int SCHEDULE_SHORTAGE_CNT_POSITION = 0 ;
	
	//#CM723575
	/**
	 * Shortage Qty (Shortage resulting from completing work) 
	 */
	public static final int COMPLETE_SHORTAGE_CNT_POSITION = 1 ;

	//#CM723576
	// Class method --------------------------------------------------
	//#CM723577
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:57 $");
	}
	//#CM723578
	// Constructors --------------------------------------------------
	//#CM723579
	/**
	 * Initialize this class. 
	 */
	public RetrievalOrderAllocClearSCH()
	{
		wMessage = null;
	}

	//#CM723580
	// Public methods ------------------------------------------------
	//#CM723581
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * For Work Status Info with only one Consignor Code exists, return the corresponding Consignor Code. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.  <BR>
	 * @param conn Instance to maintain database connection.  <BR>
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * @return Class that implements the <CODE>Parameter</CODE> interface that contains the search result.  <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection. <BR>
	 * @throws ScheduleException Announce when unexpected exception occurs in this method.  <BR>
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		//#CM723582
		// Set the corresponding Consignor code. 
		RetrievalSupportParameter wParam = new RetrievalSupportParameter();

		//#CM723583
		// Generate instance of Work Status Info handlers. 
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformation[] wWorkinfo = null;

		//#CM723584
		// Set the search conditions. 
		//#CM723585
		// System identification key: any key other than AS/RS(2) and Status flag: Standby(0),  OR
		wKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=", "((", "", "AND");
		wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "", ")", "OR");
		//#CM723586
		// System identification key: AS/RS(2) and Status flag: Working. (2) 
		wKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "=", "(", "", "AND");
		wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "", "))", "AND");
		//#CM723587
		// Work type: Picking. (3) 
		wKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM723588
		// Order No.: other than NULL 
		wKey.setOrderNo("", "IS NOT NULL");
		//#CM723589
		// Set the Consignor Code for grouping condition. 
		wKey.setConsignorCodeGroup(1);
		wKey.setConsignorCodeCollect("DISTINCT");
		
		//#CM723590
		// If the count is one: 
		if (wObj.count(wKey) == 1)
		{
			//#CM723591
			// Obtain the count of the corresponding Consignor Code data. 
			wWorkinfo = (WorkingInformation[]) wObj.find(wKey);
	
			if (wWorkinfo != null && wWorkinfo.length == 1)
			{
				//#CM723592
				// Obtain the corresponding Consignor Code and set it for the return parameter. 
				wParam.setConsignorCode(wWorkinfo[0].getConsignorCode());
			}
		}
		return wParam;
	}
	
	//#CM723593
	/**
	 * Receive the contents entered via screen as a parameter, and start the schedule to cancel the allocation. <BR>
	 * After checking for input and status, execute the process for updating the Picking Plan Info and canceling the allocation. <BR>
	 * Return true if the schedule normally completed, or return false if failed.  <BR>
	 * @param conn Instance to maintain database connection.  <BR>
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 *         Designating any instance other than RetrievalSupportParameter instance throws <CODE>ScheduleException</CODE>. <BR>
	 *         Use <CODE>getMessage()</CODE> method to refer to the content of the error  <BR>
	 * @return Return true if the schedule process completed successfully, or return false if failed.  <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection. <BR>
	 * @throws ScheduleException Announce when unexpected exception occurs in this method.  <BR>
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter[] wParam = (RetrievalSupportParameter[])startParams;

		try
		{
			//#CM723594
			// Check the Worker code and password 
			if (!checkWorker(conn, wParam[0]))
			{
				return false;
			}
			//#CM723595
			// Check the Daily Update Processing. 
			if (isDailyUpdate(conn))
			{
				return false;
			}	
			//#CM723596
			// "Check" process for "Load" flag
			if (isLoadingData(conn))
			{
				return false;
			}	
			//#CM723597
			// Off-line check (AS/RS) 
			if (!checkOffline(conn, wParam[0]))
			{
				return false;
			}
		
			//#CM723598
			// Execute the process to lock the Work Status Info and the Inventory Info. 
			if (!lockWorkingInfoStockData(conn, wParam[0]))
			{
				//#CM723599
				// 6027008=Unable to process this data. Another terminal is updating it. 
				wMessage = "6027008";
				return false;
			}
			
			//#CM723600
			// Search through the Work Status Info.
			RetrievalWorkingInformationReportFinder wiFinder = new RetrievalWorkingInformationReportFinder(conn);
			WorkingInformation[] workinfo = null;

			//#CM723601
			// Set the search conditions. 
			//#CM723602
			// Generate the search key for the parameter. 
			WorkingInformationSearchKey wiKey = createWorkingInfoKey(wParam[0]);
			wiKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			CarryInformationSearchKey cKey = new CarryInformationSearchKey();
			//#CM723603
			// Set the field item to be obtained. 
			wiKey.setPlanUkeyCollect();
			//#CM723604
			// Set the grouping condition. 
			wiKey.setPlanUkeyGroup(1);
			//#CM723605
			// Set the Searching Order. 
			wiKey.setPlanUkeyOrder(1, true);

			//#CM723606
			// Execute searching.
			//#CM723607
			// Disable to execute any process if there is no data. 
			if (wiFinder.searchAllocClear(wiKey, cKey) <= 0)
			{
				//#CM723608
				// 6023154=There is no data to update. 
				wMessage = "6023154";
				return false;
			}

			//#CM723609
			// Generate contents of data files until there is no more search result. 
			while (wiFinder.isNext())
			{
				//#CM723610
				// Output every 100 search results.
				workinfo = (WorkingInformation[]) wiFinder.getEntities(100);

				//#CM723611
				// Execute the process for the corresponding Plan Info data. 
				for (int i = 0; i < workinfo.length; i++)
				{
					//#CM723612
					// Update the Picking Plan Info.
					if (!allocateClearRetrievalPlan(conn, workinfo[i].getPlanUkey(), wParam[0]))
					{
						return false;
					}	
				}
			}
		
			//#CM723613
			// Search through the Work Status Info.
			//#CM723614
			// Set the field item to be obtained. 
			wiKey.setPlanUkeyCollect();
			//#CM723615
			// Set the search conditions. 
			//#CM723616
			// Generate the search key for the parameter. 
			wiKey.KeyClear();
			wiKey = createWorkingInfoKey(wParam[0]);
			wiKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			//#CM723617
			// Set the Searching Order. 
			wiKey.setJobNoOrder(1, true);

			//#CM723618
			// Execute searching.
			//#CM723619
			// Disable to execute any process if there is no data. 
			if (wiFinder.searchAllocClear(wiKey, cKey) <= 0)
			{
				//#CM723620
				// 6023154=There is no data to update. 
				wMessage = "6023154";
				return false;
			}

			//#CM723621
			// Generate contents of data files until there is no more search result. 
			while (wiFinder.isNext())
			{
				//#CM723622
				// Output every 100 search results.
				workinfo = (WorkingInformation[]) wiFinder.getEntities(100);

				for (int i = 0; i < workinfo.length; i++)
				{
					//#CM723623
					// Clear the allocation. 
					if (!allocateClear(conn, workinfo[i]))
					{
						return false;
					}				
				}
			}

			//#CM723624
			// 6021010=Data was committed. 
			wMessage = "6021010";

			return true;
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
	}
	
	//#CM723625
	// Package methods -----------------------------------------------

	//#CM723626
	// Protected methods ---------------------------------------------

	//#CM723627
	/**
	 * Generate a search key of Work Status for which the input value of the Input area is set. <BR>
	 * 
	 * @param inputParam   Parameter class that contains the info of the input area. 
	 * @return Generate a search key of Work Status that contains the input value of the Input area.  <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection. <BR>
	 * @throws ScheduleException Announce when unexpected exception occurs in this method.  <BR>
	 */
	protected WorkingInformationSearchKey createWorkingInfoKey(RetrievalSupportParameter inputParam) 
	 			throws ReadWriteException, ScheduleException
	{
		WorkingInformationSearchKey wiKey = new WorkingInformationSearchKey();

		//#CM723628
		// Consignor Code
		if (!StringUtil.isBlank(inputParam.getConsignorCode()))
		{
			wiKey.setConsignorCode(inputParam.getConsignorCode());
		}
		//#CM723629
		// Planned Picking Date
		if (!StringUtil.isBlank(inputParam.getRetrievalPlanDate()))
		{
			wiKey.setPlanDate(inputParam.getRetrievalPlanDate());
		}
		//#CM723630
		// Start Order No. 
		if (!StringUtil.isBlank(inputParam.getStartOrderNo()))
		{
			wiKey.setOrderNo(inputParam.getStartOrderNo(), ">=");
		}
		//#CM723631
		// End Order No. 
		if (!StringUtil.isBlank(inputParam.getEndOrderNo()))
		{
			wiKey.setOrderNo(inputParam.getEndOrderNo(), "<=");
		}
		//#CM723632
		// For data with both Start and End Order No. blank: 
		if(StringUtil.isBlank(inputParam.getStartOrderNo()) && StringUtil.isBlank(inputParam.getEndOrderNo()))
		{
			wiKey.setOrderNo("", "IS NOT NULL");
		}
		//#CM723633
		// Case/Piece division (Work Type) 
		//#CM723634
		// Any division other than All 
		if (!inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
		{
			//#CM723635
			// Case 
			if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				wiKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			//#CM723636
			// Piece 
			else if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				wiKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			}
			//#CM723637
			// None 
			else if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				wiKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			}
		}		
	
		return wiKey;
	}

	//#CM723638
	/**
	 * Execute off-line check. <BR>
	 * If AS/RS is introduced and there is also one or more AS/RS data in the corresponding allocation data, disable on-line process. <BR>
	 * @param conn 	Database connection object <BR>
	 * @param InputParam   Parameter class that contains info to be checked for input.  <BR>
	 * @return Return true if succeeded in checking for parameter or return false if failed.  <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection. <BR>
	 * @throws ScheduleException Announce when unexpected exception occurs in this method.  <BR>
	 */
	protected boolean checkOffline(Connection conn, RetrievalSupportParameter InputParam) throws ReadWriteException, ScheduleException
	{
		//#CM723639
		// Search for the Area information. 
		AreaHandler areaHandle = new AreaHandler(conn);
		AreaSearchKey areaKey = new AreaSearchKey();
		
		//#CM723640
		// Area type(2:AS/RS)  			
		areaKey.setAreaTypeCollect("DISTINCT");
		areaKey.setAreaType(Integer.toString(Area.SYSTEM_DISC_KEY_ASRS));
		//#CM723641
		// Obtain the count of Area info. 
		if (areaHandle.count(areaKey) == 0)
		{
			//#CM723642
			// If AS/RS is not introduced: 
			return true;
		}
		
		//#CM723643
		// Search through the Work Status Info.
		WorkingInformationHandler wiHandle = new WorkingInformationHandler(conn);
		//#CM723644
		// Generate the search key for the parameter. 
		WorkingInformationSearchKey wiKey = createWorkingInfoKey(InputParam);
		//#CM723645
		// Set the search conditions. 
		//#CM723646
		// Work type=Picking(3)
		wiKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM723647
		// System identification key: AS/RS(2) and Status flag: Working. (2) 
		wiKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS);
		wiKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);

		//#CM723648
		// Obtain the count of Work Status Info. 
		if (wiHandle.count(wiKey) == 0)
		{
			//#CM723649
			// Disable to target the AS/RS work data. 
			return true;
		}			

		//#CM723650
		// Disable to set it when the status of group control information is Online. 
		GroupControllerHandler grpHandle = new GroupControllerHandler(conn);
		GroupControllerSearchKey grpKey = new GroupControllerSearchKey();
		
		grpKey.setStatus(GroupController.STATUS_OFFLINE, "!=");
		if (grpHandle.count(grpKey) > 0)
		{
			//#CM723651
			// 6023480=Setting can't be done since the system is online 
			wMessage = "6023480";
			return false;
		}
		
		return true;
	}

	//#CM723652
	/**
	 * Execute the process to lock all the information in the Work Status Info (DNWORKINFO) and the Inventory Info (DMSTOCK). <BR>
	 * @param conn  Database connection object <BR>
	 * @param InputParam   Parameter class that contains the info of the input area.  <BR>
	 * @return Return true if lock process succeeded, or return false if failed.  <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection. <BR>
	 * @throws ScheduleException Announce when unexpected exception occurs in this method.  <BR>
	 */
	protected boolean lockWorkingInfoStockData(Connection conn, RetrievalSupportParameter InputParam) throws ReadWriteException, ScheduleException
	{
		RetrievalWorkingInformationHandler wiHandle = new RetrievalWorkingInformationHandler(conn);
		//#CM723653
		// Generate the search key for the parameter. 
		WorkingInformationSearchKey wiKey = createWorkingInfoKey(InputParam);
		//#CM723654
		// System identification key: any key other than AS/RS(2) and Status flag: Standby(0),  OR
		wiKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=", "((", "", "AND");
		wiKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "", ")", "OR");
		//#CM723655
		// System identification key: AS/RS(2) and Status flag: Working. (2) 
		wiKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "=", "(", "", "AND");
		wiKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "", "))", "AND");
		//#CM723656
		// Work type: Picking. (3) 
		wiKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		
		wiHandle.lock(wiKey);

		return true;
	}

	//#CM723657
	/**
	 * Update the Picking Plan Info in which allocation is to be cancelled. <BR>
	 * Update the schedule flag, Status flag, and the Shortage Qty. <BR>
	 * Update conditions as follows: <BR>
	 * <BR>
	 * [Schedule flag ] 
	 * <DIR>
	 * 1.If data meets all of the following conditions: <BR>
	 *     1-1. Picking Plan Info with Case/Piece flag other than Mixed 
	 *     1-2. Picking Plan Info with status other than Working or Partially Completed 
	 *     1-3.Shortage Qty in the Picking Plan Info = 0 
	 *   Not Processed <BR>
	 * 2.To cancel the allocation only for Case data of plan data with division Mixed and status Processed: <BR>
	 *   Piece Processed <BR>
	 * 3.To cancel the allocation only for Piece data of plan data with division Mixed and status Processed: <BR>
	 *   Case Processed <BR>
	 * 4.For Plan data with division Mixed and also other than 2.and 3.: <BR>
	 *   Not Processed <BR>
	 * 5.For Plan data with division other than Mixed: <BR>
	 *   Not Processed <BR>
	 * </DIR>
	 * [Status flag] <BR>
	 * <DIR>
	 * 1.For data with Status flag Working or Partially Completed: <BR>
	 *   Search through the Work Status Info. If there is no data with status Working except for AS/RS, nor data with status Working on AS/RS and transport status between Standby for response and Picking-up Completed, nor data wiith Completed, restore it to <BR>
	 *   Before processing only either one type of the Mixed data, search through the Work Status Info of another type and regard it as Partially Completed if schedule shortage occurs. <BR>
	 *   Regard the data with AS/RS inventory allocated while working as Partially Completed in the event of shortage.<BR>
	 * </DIR>
	 * <BR>
	 * [Shortage Qty] <BR>
	 * <DIR>
	 * 1. Shortage occurring: <BR>
	 *   Search through the Work Status Info and obtain the Shortage Qty on schedule. <BR>
	 *   Shortage Qty in the Picking Plan Info = (Shortage Qty in the Picking Plan Info) - (Obtained Schedule Shortage Qty) <BR>
	 * </DIR>
	 * 
	 * @param conn 	Database connection object
	 * @param planUkey Picking Plan unique key
	 * @param inputParam   Parameter class that contains the info of the input area. 
	 * @return Return true if the update process succeed, or return false if failed. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce when unexpected exception occurs in this method. 
	 */
	protected boolean allocateClearRetrievalPlan(Connection conn, String planUkey, RetrievalSupportParameter inputParam) 
					throws ReadWriteException, ScheduleException
	{
		try
		{			
			RetrievalPlanHandler rHandler = new RetrievalPlanHandler(conn);
			RetrievalPlanSearchKey rKey = new RetrievalPlanSearchKey();
			RetrievalPlanAlterKey rAlterKey = new RetrievalPlanAlterKey();

			//#CM723658
			// Search through the Picking Plan Info.
			//#CM723659
			// Set the row to be obtained. 
			//#CM723660
			// Picking Plan unique key
			rKey.setRetrievalPlanUkeyCollect();
			//#CM723661
			// Status flag
			rKey.setStatusFlagCollect();
			//#CM723662
			// Planned qty 
			rKey.setPlanQtyCollect();
			//#CM723663
			// Shortage Qty
			rKey.setShortageCntCollect();
			//#CM723664
			// Case/Piece division 
			rKey.setCasePieceFlagCollect();
			//#CM723665
			// Schedule flag 
			rKey.setSchFlagCollect();
			//#CM723666
			// Set the search conditions. 
			//#CM723667
			// Picking Plan unique key
			rKey.setRetrievalPlanUkey(planUkey);
			
			RetrievalPlan retrievalPlanBuff = (RetrievalPlan) rHandler.findPrimary(rKey);
			//#CM723668
			// If data meets all of the following conditions, disable to search. 
			//#CM723669
			// 1. Picking Plan Info with Case/Piece flag other than Mixed 
			//#CM723670
			// 2. Picking Plan Info with status other than Working or Partially Completed 
			//#CM723671
			// 3.Shortage Qty in the Picking Plan Info = 0 
			if ((!retrievalPlanBuff.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_MIX))
			&& (!retrievalPlanBuff.getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_NOWWORKING))
			&& (!retrievalPlanBuff.getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART))
			&& (retrievalPlanBuff.getShortageCnt() == 0))
			{					
				//#CM723672
				// Schedule flag: Not Processed(0) 
				rAlterKey.updateSchFlag(RetrievalPlan.SCH_FLAG_UNSTART);					
			}
			else
			{
				//#CM723673
				// Case/Piece division of which allocation is to be cancelled.
				String workFormFlag = "";
				//#CM723674
				// For data with Case/Piece flag of Picking Plan Info Mixed, return the schedule flag based on the Work Status Info. 
				if (retrievalPlanBuff.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_MIX))
				{
					//#CM723675
					// Obtain the Case/Piece division of which allocation is to be cancelled. 
					workFormFlag = getMixCasePieceFlag(conn, retrievalPlanBuff, inputParam);
					//#CM723676
					// Case data only exist and the source schedule flag is Processed (3):
					if((workFormFlag.equals(SystemDefine.CASEPIECE_FLAG_CASE))
					&& (retrievalPlanBuff.getSchFlag().equals(RetrievalPlan.SCH_FLAG_COMPLETION)))
					{
						//#CM723677
						// Schedule flag : Piece Processed (2)
						rAlterKey.updateSchFlag(RetrievalPlan.SCH_FLAG_PIECE_COMPLETION);					
					}
					//#CM723678
					// If Piece data only exist and the source schedule flag is Processed (3):
					else if((workFormFlag.equals(SystemDefine.CASEPIECE_FLAG_PIECE))
					&& (retrievalPlanBuff.getSchFlag().equals(RetrievalPlan.SCH_FLAG_COMPLETION)))
					{
						//#CM723679
						// Schedule flag : Case Processed (1)
						rAlterKey.updateSchFlag(RetrievalPlan.SCH_FLAG_CASE_COMPLETION);
					}
					//#CM723680
					// Others 
					else
					{						
						//#CM723681
						// Schedule flag: Not Processed(0) 
						rAlterKey.updateSchFlag(RetrievalPlan.SCH_FLAG_UNSTART);					
					}					
				}
				//#CM723682
				// Update the data with division other than "Mixed" to "Not Processed" (0). 
				else
				{
					//#CM723683
					// Schedule flag: Not Processed(0) 
					rAlterKey.updateSchFlag(RetrievalPlan.SCH_FLAG_UNSTART);					
					//#CM723684
					// Set the Case/Piece division for all data searched to obtain Status flag or update Shortage Qty. 
					workFormFlag = RetrievalPlan.CASEPIECE_FLAG_MIX;
				}
				
				//#CM723685
				// For Picking Plan Info data with status Working or Partially Completed, determine the Process flag based on the Work Status Info. 
				if ((retrievalPlanBuff.getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_NOWWORKING))
				|| (retrievalPlanBuff.getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART)))
				{
					String updateStatusFlag = "";
					//#CM723686
					// Obtain the Status flag to be updated.
					updateStatusFlag = getStatusFlag(conn, retrievalPlanBuff);
					//#CM723687
					// If any changed from the current Status flag, update it. 
					if(!updateStatusFlag.equals(retrievalPlanBuff.getStatusFlag()))
					{
						//#CM723688
						// Status flag
						rAlterKey.updateStatusFlag(updateStatusFlag);						
					}
				}
				//#CM723689
				// For data with Shortage Qty in the Picking Plan Info >0 (larger than 0), return the schedule flag based on the Work Status Info. 
				if (retrievalPlanBuff.getShortageCnt() > 0)
				{
					//#CM723690
					// Obtain the Shortage Qty. 
					int[] shortageCnt = getShortageCnt(conn, retrievalPlanBuff, workFormFlag);
					//#CM723691
					// Return the Schedule Shortage Qty from the Shortage Qty of Picking Plan Info. 
					shortageCnt[SCHEDULE_SHORTAGE_CNT_POSITION] = retrievalPlanBuff.getShortageCnt() - shortageCnt[SCHEDULE_SHORTAGE_CNT_POSITION];
					rAlterKey.updateShortageCnt(shortageCnt[SCHEDULE_SHORTAGE_CNT_POSITION]);
				}
			}

			rAlterKey.setRetrievalPlanUkey(planUkey);
			rAlterKey.updateLastUpdatePname(wProcessName);
			rHandler.modify(rAlterKey);
			
			return true;
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}		
	}
			
	//#CM723692
	/**
	 * Receive the contents entered via screen as a parameter, and execute the process to cancel the allocation. <BR>
	 * <BR>
	 * [Delete Work Status Info] 
	 * <DIR>
	 *     Delete the corresponding Work Status Info. <BR>
	 * </DIR>
	 * [Inventory Info Update] <BR>
	 * <DIR>
	 *     Sum-up the Available picking qty. <BR>
	 * </DIR>
	 * <BR>
	 * Transport info deletion (Only work data of AS/RS). <BR>
	 * <DIR>
	 *     If any linked Work Status is not found, delete the Transport info. <BR>
	 * </DIR>
	 * Pallet information Update (Only work data of AS/RS). <BR>
	 * <DIR>
	 *     If any linked Work Status is not found, update the Inventory status flag and the allocation flag. <BR>
	 * </DIR>
	 * @param conn Instance to maintain database connection.  <BR>
	 * @param workInfo Update the Work Status Info  <BR>
	 * @return Return true if process to cancel allocation succeeded, or return false if failed.  <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection. <BR>
	 * @throws ScheduleException Announce when unexpected exception occurs in this method.  <BR>
	 */
	protected boolean allocateClear(Connection conn, WorkingInformation workInfo) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM723693
			// Delete the Work Status Info. 
			WorkingInformationHandler wiHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey wiKey = new WorkingInformationSearchKey();
			//#CM723694
			// Set the search conditions. 
			//#CM723695
			// Work No.
			wiKey.setJobNo(workInfo.getJobNo());
			if (wiHandler.count(wiKey) > 0)
			{
				wiHandler.drop(wiKey);
			}

			//#CM723696
			// Update Inventory Info. 
			//#CM723697
			// Update the Available picking qty of the inventory linked to the Work Status Info. 
			StockHandler sHandler = new StockHandler(conn);
			StockSearchKey sKey = new StockSearchKey();
			StockAlterKey sAlterKey = new StockAlterKey();
			//#CM723698
			// Set the search conditions. 
			//#CM723699
			// Stock ID
			sKey.setStockId(workInfo.getStockId());
			//#CM723700
			// Set the field item to be obtained. 
			//#CM723701
			// Available picking qty 
			sKey.setAllocationQtyCollect();
			//#CM723702
			// Pallet ID 
			sKey.setPaletteidCollect();
			//#CM723703
			// Process to search Inventory Info.
			Stock stk = (Stock) sHandler.findPrimary(sKey);
			//#CM723704
			// Set the search conditions. 
			sAlterKey.setStockId(workInfo.getStockId());
			//#CM723705
			// Sum-up the Available picking qty. 
			sAlterKey.updateAllocationQty(stk.getAllocationQty() + workInfo.getPlanEnableQty());
			sAlterKey.updateLastUpdatePname(wProcessName);
			//#CM723706
			// Process to update Inventory Info 
			sHandler.modify(sAlterKey);
		
			//#CM723707
			// For AS/RS data, update the transport data and the pallet data. 
			if(workInfo.getSystemDiscKey() == SystemDefine.SYSTEM_DISC_KEY_ASRS)
			{
				//#CM723708
				// Search through the Work Status Info.
				//#CM723709
				// If there is one or more data with the same System Connection key as well as with Status flag neither Completed nor Deleted, disable to update such data. 
				wiKey.KeyClear();
				//#CM723710
				// System Connection key 
				wiKey.setSystemConnKey(workInfo.getSystemConnKey());
				//#CM723711
				// System identification key : AS/RS(2)
				wiKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS);
				//#CM723712
				// Status flag: Neither Completed (4) nor Deleted (9) 
				wiKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION, "!=", "(", "", "AND");
				wiKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=", "", ")", "AND");
				if (wiHandler.count(wiKey) == 0)
				{
					//#CM723713
					// Delete the Transport data. 
					CarryInformationHandler cHandler = new CarryInformationHandler(conn);
					CarryInformationSearchKey cKey = new CarryInformationSearchKey();
					//#CM723714
					// Set the search conditions. 
					//#CM723715
					// Transport Key 
					cKey.setCarryKey(workInfo.getSystemConnKey());
					if (cHandler.count(cKey) > 0)
					{
						cHandler.drop(cKey);
					}
	
					//#CM723716
					// Update the Pallet data. 
					//#CM723717
					// Update the status of the Pallet linked to the Inventory Info. 
					PaletteHandler pHandler = new PaletteHandler(conn);
					PaletteAlterKey pAlterKey = new PaletteAlterKey();
					//#CM723718
					// Set the search conditions. 
					//#CM723719
					// Pallet ID 
					pAlterKey.setPaletteId(stk.getPaletteid());
					//#CM723720
					// Set the content to be updated. 
					//#CM723721
					// Inventory status flag: Occupied location (2) 
					pAlterKey.updateStatus(Palette.REGULAR);
					//#CM723722
					// Allocation flag: Not Allocated (0) 
					pAlterKey.updateAllocation(Palette.NOT_ALLOCATED);
					//#CM723723
					// Process to update Pallet Info 
					pHandler.modify(pAlterKey);			
				}
			}		
		}		
		catch (ReadWriteException e)
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
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}		
		
		return true;
	}
	
	//#CM723724
	/**
	 * For data with Mixed, obtain the Case/Piece division of the target data. <BR>
	 * Search for allocatable Work Status Info, and obtain the divisions of the target data: only Case, only Piece, or both. <BR>
	 * 1.To cancel the allocation of Case data only: <BR>
	 *   Case <BR>
	 * 2.To cancel the allocation of Case data only: <BR>
	 *   Piece <BR>
	 * 3. Otherwise (any case other than 1 and 2) <BR>
	 *   Mixed <BR>
	 * @param conn 	Database connection object <BR>
	 * @param retrievalPlan   Picking Plan Info  <BR>
	 * @param inputParam   Parameter class that contains the info of the input area.  <BR>
	 * @return Return the obtained Case/Piece division.  <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection. <BR>
	 * @throws ScheduleException Announce when unexpected exception occurs in this method.  <BR>
	 */
	protected String getMixCasePieceFlag(Connection conn, RetrievalPlan retrievalPlan, RetrievalSupportParameter inputParam) throws ReadWriteException, ScheduleException
	{
		RetrievalWorkingInformationHandler rwiHandler = new RetrievalWorkingInformationHandler(conn);
		CarryInformationSearchKey cKey = new CarryInformationSearchKey();

		String flag = "";
		
		//#CM723725
		// Search for work status possible to cancel the allocation and determine the schedule flag. 
		//#CM723726
		// Generate the search key for the parameter. 
		WorkingInformationSearchKey wiKey = createWorkingInfoKey(inputParam);
		//#CM723727
		// Set the row to be obtained. 
		//#CM723728
		// Case/Piece division (Work Type)  
		wiKey.setWorkFormFlagCollect();
		//#CM723729
		// Set the search conditions. 
		//#CM723730
		// Plan unique key 
		wiKey.setPlanUkey(retrievalPlan.getRetrievalPlanUkey());
		//#CM723731
		// Work type: Picking
		wiKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM723732
		// Search through the Work Status Info.
		WorkingInformation[] workInfoBuff = (WorkingInformation[]) rwiHandler.findAllocClear(wiKey, cKey);					

		boolean [] workForm = {false, false};
		for (int i = 0; i < workInfoBuff.length; i++)
		{
			//#CM723733
			// Case 
			if(workInfoBuff[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE))
			{
				workForm[CASEPIECE_FLAG_CASE_POSITION] = true;
			}
			//#CM723734
			// Piece 
			else if(workInfoBuff[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_PIECE))
			{
				workForm[CASEPIECE_FLAG_PIECE_POSITION] = true;
			}
		}
		
		//#CM723735
		// "Case" data only exist:
		if((workForm[CASEPIECE_FLAG_CASE_POSITION])
		&& (!workForm[CASEPIECE_FLAG_PIECE_POSITION]))
		{
			flag = SystemDefine.CASEPIECE_FLAG_CASE;
		}
		//#CM723736
		// All existing data with division "Piece" 
		else if((!workForm[CASEPIECE_FLAG_CASE_POSITION])
		&& (workForm[CASEPIECE_FLAG_PIECE_POSITION]))
		{
			flag = SystemDefine.CASEPIECE_FLAG_PIECE;
		}
		//#CM723737
		// Both Case and Piece data exist. 
		else
		{						
			flag = SystemDefine.CASEPIECE_FLAG_MIX;
		}					

		return flag;
	}

	//#CM723738
	/**
	 * Obtain the Status flag to be updated.<BR>
	 * 1.Search through the Work Status Info and the Transport info, and determine the Status flag. <BR>
	 *     1-1. If "Started" data exists: <BR>
	 *         Store the presence of data with status Started. <BR>
	 *     1-2. If data with status Working other than AS/RS exists: <BR>
	 *         Store the presence of data with status Working exists. <BR>
	 *     1-3.If there is one or more data with the Status flag Working of AS/RS and there is also the data with the corresponding Transport info other than Allocated and Started: <BR>
	 *         Store the presence of data with status Working exists. <BR>
	 *     1-4. If "Partially Completed" or "Completed" data exists: <BR>
	 *         Store the presence of data with status "Completed" exists. <BR>
	 * 2.Commit the status. <BR>
	 *    2-1. If existence of the data with status Working is stored in memory at 1 <BR>
	 *         Regard it as a Working data. <BR>
	 *    2-2. If the existence of Started data is stored in memory at 1 <BR>
	 *         Regard it as "Started". <BR>
	 *    2-3. If the existence of "Completed" data is stored in memory at 1 <BR>
	 *         Regard it as "Partially Completed". <BR>
	 *    2-4. Otherwise: <BR>
	 *         Regard it as Standby. <BR>
	 * @param conn 	Database connection object <BR>
	 * @param retrievalPlan   Picking Plan Info <BR>
	 * @return Return the Status flag to be updated.<BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.<BR>
	 * @throws ScheduleException Announce when unexpected exception occurs in this method. <BR>
	 */
	protected String getStatusFlag(Connection conn, RetrievalPlan retrievalPlan) throws ReadWriteException, ScheduleException
	{
		WorkingInformationHandler wiHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wiKey = new WorkingInformationSearchKey();

		//#CM723739
		// Search for the work status linked to the Plan Info to be processed and determine the status flag.. 
		wiKey.KeyClear();

		//#CM723740
		// set WHERE 
		//#CM723741
		// Plan unique key 
		wiKey.setPlanUkey(retrievalPlan.getRetrievalPlanUkey());
		//#CM723742
		// Status flag
		//#CM723743
		// Search for data with Started, Working, Partially Completed, or Completed. 
		String[] status = {WorkingInformation.STATUS_FLAG_START,
						   WorkingInformation.STATUS_FLAG_NOWWORKING,
						   WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART,
						   WorkingInformation.STATUS_FLAG_COMPLETION};
		wiKey.setStatusFlag(status);

		//#CM723744
		// Set the COLLECT conditions. 
		//#CM723745
		// Status flag
		wiKey.setStatusFlagCollect();
		//#CM723746
		// System Connection key 
		wiKey.setSystemConnKeyCollect();
		//#CM723747
		// System identification key 
		wiKey.setSystemDiscKeyCollect();
		//#CM723748
		// Case/Piece division (Work Type) 
		wiKey.setWorkFormFlagCollect();

		//#CM723749
		// Set the ORDER BY condition. 
		//#CM723750
		// Search for data in the order of Started, Working, Partially Completed, and then Completed. 
		wiKey.setStatusFlagOrder(1, true);
		//#CM723751
		// System identification key 
		wiKey.setSystemDiscKeyOrder(2, true);
		
		//#CM723752
		// Search through workinginformation. 
		WorkingInformation[] workInfoBuff = (WorkingInformation[]) wiHandler.find(wiKey);
		
		//#CM723753
		// Determine the Status flag based on the searched data. 
		//#CM723754
		// "Started" flag 
		boolean existStart = false;
		boolean existNoWorking = false;
		boolean existComp = false;
		for (int i = 0; i < workInfoBuff.length; i++)
		{
			//#CM723755
			// If there is one or more data with Status flag Started (not subject to cancel of allocation): 
			if(workInfoBuff[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START))
			{
				existStart = true;
			}			
			//#CM723756
			// If one or more data with Status flag Working exists: 
			else if(workInfoBuff[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
			{
				//#CM723757
				// If there is one or more data with the Status flag Working other than AS/RS (subject to cancel of allocation): 
				if(workInfoBuff[i].getSystemDiscKey() != WorkingInformation.SYSTEM_DISC_KEY_ASRS)
				{
					existNoWorking = true;
				}
				//#CM723758
				// If there is one or more data with the Status flag Working of AS/RS (not subject to cancel of allocation): 
				else
				{
					//#CM723759
					// Once the carry info has thrown a carry instruction, disable to cancel the allocation. 
					//#CM723760
					// Therefore. leave the work status as "Working". 
					CarryInformationHandler cHandler = new CarryInformationHandler(conn);
					CarryInformationSearchKey cKey = new CarryInformationSearchKey();
					cKey.setCarryKey(workInfoBuff[i].getSystemConnKey());
					cKey.setCmdStatus(CarryInformation.CMDSTATUS_ALLOCATION, "!=");
					cKey.setCmdStatus(CarryInformation.CMDSTATUS_START, "!=");
					if (cHandler.count(cKey) > 0)
					{
						existNoWorking = true;
					}
				}
			}
			//#CM723761
			// If there is one or more data with Status flag Completed (not subject to cancel of allocation): 
			else if(workInfoBuff[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
			{
				existComp = true;
			}
		}
		
		//#CM723762
		// If one or more Working data remains after canceling the allocation, regard the Plan Info as Working. 
		if (existNoWorking)
		{
			return RetrievalPlan.STATUS_FLAG_NOWWORKING;
		}
		//#CM723763
		// If one or more started data remains after canceling the allocation, regard the Plan Info as Started. 
		else if(existStart)
		{
			return RetrievalPlan.STATUS_FLAG_START;
		}
		//#CM723764
		// If one or more completed data remains after canceling the allocation, regard the Plan Info as Partially Completed. 
		//#CM723765
		// (Ensure that there is no data with all Completed since the allocation is going to be cancelled) 
		else if (existComp)
		{
			return RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART;
		}
		else
		{
			//#CM723766
			// Standby (0) 
			return RetrievalPlan.STATUS_FLAG_UNSTART;
		}
	}

	//#CM723767
	/**
	 * Obtain the Shortage Qty. <BR>
	 * Search through the Work Status Info by Case/Piece division, and obtain the total qty of the Planned Work qty and workable Qty. <BR>
	 * To cancel the allocation of either one of mixed categories, obtain the Shortage Qty of the designated Case/Piece division only. <BR>
	 * Ensure that Schedule Shortage Qty = Work Planned qty - workable Qty <BR>
	 * Shortage Qty Shortage Qty resulting from completing picking = Work shortage qty <BR>
	 * @param conn 	Database connection object <BR>
	 * @param retrievalPlan   Picking Plan Info <BR>
	 * @param workFormFlag   Case/Piece division <BR>
	 * @return Return the array of Shortage Qty (Schedule Shortage Qty and Shortage Qty resulting from completing picking). <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.<BR>
	 * @throws ScheduleException Announce when unexpected exception occurs in this method. <BR>
	 */
	protected int[] getShortageCnt(Connection conn, RetrievalPlan retrievalPlan, String workFormFlag) throws ReadWriteException, ScheduleException
	{
		//#CM723768
		// Array of Shortage Qty (Schedule Shortage Qty and Shortage Qty Shortage Qty resulting from completing picking) 
		int[] shortageCnt = {0, 0};
		
		WorkingInformationHandler wiHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wiKey = new WorkingInformationSearchKey();

		//#CM723769
		// Set the row to be obtained. 
		//#CM723770
		// Work Planned qty 
		wiKey.setPlanQtyCollect("MAX");
		//#CM723771
		// Workable qty
		wiKey.setPlanEnableQtyCollect("SUM");
		//#CM723772
		// Work shortage qty
		wiKey.setShortageCntCollect("SUM");

		//#CM723773
		// Set the search conditions. 
		//#CM723774
		// Plan unique key 
		wiKey.setPlanUkey(retrievalPlan.getRetrievalPlanUkey());
		//#CM723775
		// For data with Case/Piece division as a parameter other than Mixed (Search All), designate the Case/Piece division. 
		if(!workFormFlag.equals(SystemDefine.CASEPIECE_FLAG_MIX))
		{
			//#CM723776
			// Case/Piece division 
			wiKey.setWorkFormFlag(workFormFlag);			
		}

		//#CM723777
		// Set the grouping order. 
		//#CM723778
		// Plan unique key 
		wiKey.setPlanUkeyGroup(1);
		//#CM723779
		// Case/Piece division 
		wiKey.setWorkFormFlagGroup(2);

		//#CM723780
		// Search through the Work Status Info.
		WorkingInformation[] workInfoBuff = (WorkingInformation[]) wiHandler.find(wiKey);

		for (int i = 0; i < workInfoBuff.length; i++)
		{
			//#CM723781
			// Obtain the Schedule Shortage Qty. 
			shortageCnt[SCHEDULE_SHORTAGE_CNT_POSITION] = shortageCnt[SCHEDULE_SHORTAGE_CNT_POSITION] + workInfoBuff[i].getPlanQty() - workInfoBuff[i].getPlanEnableQty();
			//#CM723782
			// Obtain the Shortage Qty Shortage Qty resulting from completing picking. 
			shortageCnt[COMPLETE_SHORTAGE_CNT_POSITION] = shortageCnt[COMPLETE_SHORTAGE_CNT_POSITION] + workInfoBuff[i].getShortageCnt();
		}

		return shortageCnt;
	}

	//#CM723783
	// Private methods -----------------------------------------------
}
//#CM723784
//end of class
