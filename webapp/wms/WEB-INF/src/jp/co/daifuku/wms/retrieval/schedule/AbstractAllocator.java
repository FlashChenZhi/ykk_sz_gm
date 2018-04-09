//#CM722007
//$Id: AbstractAllocator.java,v 1.3 2007/02/07 04:19:49 suresh Exp $
package jp.co.daifuku.wms.retrieval.schedule;

//#CM722008
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.ShortageInfo;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.utility.WareNaviSystemManager;
import jp.co.daifuku.wms.base.utility.WorkerManager;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM722009
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this abstract class to allocate and cancel the allocation. <BR>
 * Use processes of allocation or its cancel from the class that inherits this class, as needed. <BR>
 * <BR>
 * 1. Allocation Process (<CODE>allocate(pPlanUKey, pWorkForm, AreaNo [] )</CODE> method)  <BR> 
 * <Parameter> <BR>
 * <DIR>
 *   Picking Plan unique key <BR>
 *   Work Type <BR>
 *   Allocation target area  <BR>
 * </DIR>
 * <BR>
 * Execute the Allocation process using the Picking Plan unique key. <BR>
 * In the event of shortage, sum up the Shortage Qty to the Picking Plan Info, and update the status to Partially Completed or Completed. 
 * At this moment, generate a Work Status Info of work completed with shortage as the status Completed. 
 * Even if shortage occurs, continue the process and generate a shortage info. 
 * 
 * <Process sequence> 
 * 1.Obtain the Allocated qty from the Picking Plan unique key and the Work Type. <BR>
 * 2.Obtain the inventory from the Picking Plan and the Work Type. (Note) <BR>
 * 3. Update the Inventory Info to be allocated.  <BR>
 * 4.Generate a Work Status based on the Allocated Inventory Info and the Picking Plan Info.  <BR>
 * 5.Update the Picking Plan Info.  <BR>
 * 6. For shortage resulted from allocation, generate a a shortage info. <BR>
 * 
 * (Note) Designate the Work Type to determine the inventory allocation order.  <BR>
 * <Order to obtain data> <BR>
 * <DIR>
 *   1. Case  <BR>
 *     Case --> None --> Piece  <BR>
 *   2. Piece  <BR>
 *     Piece --> None --> Case  <BR>
 *   3. None  <BR>
 *     None --> Piece --> Case  <BR>
 * </DIR>
 *   
 * 2.Rules for selecting location:<BR>
 * <DIR>
 * Select the allocation target location from the Picking Plan Info according to the following rules. <BR>
 * 1.For Plan data with division Case: <BR>
 *   Obtain the CaseLocation. <BR>
 * 2.For Plan data with division Piece: <BR>
 *   Obtain the PieceLocation. <BR>
 * 3.For Plan data with division None: <BR>
 *   Obtain the data with input of either CaseLocation or PieceLocation <BR>
 * 4.For Plan data with division Mixed: <BR>
 *   If the work data to be created this time here is Case, obtain its CaseLocation. <BR>
 *   If the work data to be created this time here is Piece, obtain its PieceLocation. <BR>
 * </DIR>
 * 
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/03/16</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:49 $
 * @author  $Author: suresh $
 */
public abstract class AbstractAllocator extends Object
{
	//#CM722010
	// Class variables -----------------------------------------------
	//#CM722011
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;
	
	//#CM722012
	/**
	 * Process name 
	 */
	protected String CLASS_NAME = "RetrievalAllocator";

	//#CM722013
	/**
	 * Connection object 
	 */
	protected Connection wConn = null;

	//#CM722014
	/**
	 * Connection for generating a shortage info 
	 */
	protected Connection wShortageConn = null;

	//#CM722015
	/**
	 * Inventory Info handler 
	 */
	protected StockHandler wStkHandler = null;

	//#CM722016
	/**
	 * Inventory Info search key 
	 */
	protected StockSearchKey wStkKey = null;

	//#CM722017
	/**
	 * Update key for Inventory Info 
	 */
	protected StockAlterKey wStkAltKey = null;

	//#CM722018
	/**
	 * Picking Plan Info handler 
	 */
	protected RetrievalPlanHandler wPlanHandler = null;

	//#CM722019
	/**
	 * Picking Plan Info search key 
	 */
	protected RetrievalPlanSearchKey wPlanKey = null;

	//#CM722020
	/**
	 * Picking Plan Info Update key 
	 */
	protected RetrievalPlanAlterKey wPlanAltKey = null;

	//#CM722021
	/**
	 * Work Status Info handler 
	 */
	protected WorkingInformationHandler wWiHandler = null;

	//#CM722022
	/**
	 * Work Status Info search key 
	 */
	protected WorkingInformationSearchKey wWiKey = null;

	//#CM722023
	/**
	 * Work Status Info Update key 
	 */
	protected WorkingInformationAlterKey wWiAltKey = null;

	//#CM722024
	/**
	 * Shortage info handler 
	 */
	protected ShortageInfoHandler wShortageHandler = null;
	
	//#CM722025
	/**
	 * Handler for Host Sending Result to be generated in the event of shortage. 
	 */
	protected HostSendHandler wHostSendHandler = null;

	//#CM722026
	/**
	 * Sequence handler 
	 */
	protected SequenceHandler wSeqHandler = null;
	
	//#CM722027
	/**
	 * WareNaviSystem operation class 
	 */
	protected WareNaviSystemManager wWmsManager = null;
	
	//#CM722028
	/**
	 * Maintain the searched values in the Area type using Area No. as a key. 
	 */
	protected HashMap wAreaTypeMap = new HashMap();

	//#CM722029
	/**
	 * Batch No. to be used to generate the shortage info (Number it from the Sequence object) 
	 */
	private String wShortageBatchNo_seq = null;
	
	//#CM722030
	/**
	 * Invoking source screen name 
	 */
	private String wFunctionName;
	
	//#CM722031
	/**
	 * Worker Code
	 */
	private String wWorkerCode;
	
	//#CM722032
	/**
	 * Worker Name
	 */
	private String wWorkerName;
	
	//#CM722033
	/**
	 * Terminal No. and RFT No. 
	 */
	private String wTerminalNo;
	
	//#CM722034
	// Class method --------------------------------------------------
	//#CM722035
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:49 $");
	}
	
	//#CM722036
	// Constructors --------------------------------------------------
	//#CM722037
	/**
	 * Initialize this class. <BR>
	 * Generate handlers used for allocation, or handlers to obtain Sequence object. <BR>
	 * Obtain the connection for generating a shortage info, and number the Batch No. 
	 * @param conn Database connection
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	public AbstractAllocator(Connection conn) throws ScheduleException, ReadWriteException
	{
		wConn = conn;
		wStkHandler = new StockHandler(conn);
		wStkKey = new StockSearchKey();
		wStkAltKey = new StockAlterKey();

		wPlanHandler = new RetrievalPlanHandler(conn);
		wPlanKey = new RetrievalPlanSearchKey();
		wPlanAltKey = new RetrievalPlanAlterKey();

		wWiHandler = new WorkingInformationHandler(conn);
		wWiKey = new WorkingInformationSearchKey();
		wWiAltKey = new WorkingInformationAlterKey();
		
		wSeqHandler = new SequenceHandler(conn);
		
		wShortageBatchNo_seq = wSeqHandler.nextNoPlanBatchNo();
		try
		{
			wShortageConn = WmsParam.getConnection();
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		wShortageHandler = new ShortageInfoHandler(wShortageConn);
		
		wHostSendHandler = new HostSendHandler(conn);
		
		wWmsManager = new WareNaviSystemManager(conn);

	}
	
	//#CM722038
	// Public methods ------------------------------------------------
	//#CM722039
	/**
	 * Describe the Allocation process. <BR>
	 * Allow the class that implements this class to implement it. <BR>
	 * 
	 * @param param Parameter class that maintains the info input in the screen. 
	 * @return Check whether allocation process succeeded or not. If shortage occurs, return false. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	abstract public boolean allocate(RetrievalSupportParameter[] param) throws ReadWriteException, ScheduleException;
	//#CM722040
	/**
	 * Describe the Restoration process from the shortage. <BR>
	 * Allow the class that implements this class to implement it. <BR>
	 * 
	 * @param isCompleteShortage Flag whether to complete or not when shortage occurs.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	abstract public void completeShortage(boolean isCompleteShortage) throws ReadWriteException, ScheduleException;
	//#CM722041
	/**
	 * Describe the Close Process. <BR>
	 * Allow the class that implements this class to implement it. <BR>
	 * <B>Require the Invoking source class to implement this method. <BR></B>
	 * 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	abstract public void close() throws ReadWriteException, ScheduleException;
	
	//#CM722042
	/**
	 * Obtain the batch No. of the shortage info generated using this setting. 
	 * @return Batch No. of data with shortage 
	 */
	public String getShortageBatchNo()
	{
		return wShortageBatchNo_seq;
	}
	
	//#CM722043
	/**
	 * Set the Invoking source screen name. 
	 * @param arg Invoking source screen name to be set
	 */
	public void setFunctionName(String arg)
	{
		wFunctionName = arg;
	}
	
	//#CM722044
	/**
	 * Return the Invoking source screen name. 
	 * @return Invoking source screen name 
	 */
	protected String getFunctionName()
	{
		return wFunctionName;
	}
	
	//#CM722045
	/**
	 * Set the Worker code. 
	 * Set the Worker Name together. 
	 * @param arg Worker code to be set 
	 */
	public void setWorker(String arg) throws ReadWriteException, ScheduleException
	{
		wWorkerCode = arg;
		WorkerManager worker = new WorkerManager(wConn);
		wWorkerName = worker.getName(wWorkerCode);
	}
	
	//#CM722046
	/**
	 * Return the Worker Code. 
	 * @return Worker Code
	 */
	protected String getWorkerCode()
	{
		return wWorkerCode;
	}
	
	//#CM722047
	/**
	 * Return the Worker Name. 
	 * @return Worker Name
	 */
	protected String getWorkerName()
	{
		return wWorkerName;
	}
	
	//#CM722048
	/**
	 * Set the Terminal No. and the RFT No. 
	 * @param arg Terminal No. and RFT No. 
	 */
	public void setTerminalNo(String arg)
	{
		wTerminalNo = arg;
	}
	//#CM722049
	/**
	 * Obtain Terminal No. and RFT No. 
	 * @return Terminal No. and RFT No. 
	 */
	protected String getTerminalNo()
	{
		return wTerminalNo;
	}
	
	//#CM722050
	// Protected methods ---------------------------------------------
	//#CM722051
	/**
	 * Execute the Allocation process. <BR>
	 * Obtain the currently requested allocation qty from the picking plan based on the designated work type. <BR>
	 * If Work Type is not designated, invoke this method again and, in the order of Case Work and then Piece Work,
	 * execute allocation. <BR>
	 * Announce ScheduleException if this time Planned allocation qty is 0 <BR>
	 * Obtain the Inventory from the designated area and allocate it. <BR>
	 * Allow the allocation process to update Inventory Info and generate Work Status Info. <BR>
	 * Update the Picking Plan Info targeted here based on the allocation result. <BR>
	 * For shortage resulted from allocation, generate a shortage info and return false. <BR>
	 * 
	 * @param pPlanUKey Picking Plan unique key of the Picking Plan to be allocated
	 * @param pWorkForm Work Type to be allocated (null enabled only for data with Mixed. Otherwise, trigger Exception) 
	 * @param pAreaNo Allocation target Area No. 
	 * @return Return true if allocated, or return false if shortage occurs in all. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected boolean allocate(String pPlanUKey, String pWorkFormFlag, String pAreaNo[]) 
								throws ReadWriteException, ScheduleException
	{
		//#CM722052
		// Obtain the Plan Info. 
		RetrievalPlan plan = getRetrievalPlan(pPlanUKey);
		
		//#CM722053
		// Compute the Shortage Qty to this plan. 
		int shortageQty = getShortageQty(plan, pWorkFormFlag, pAreaNo);
		//#CM722054
		// Compute the allocation quantity to be allocated this time. 
		int planQty = getPlanAllocateQty(plan, pWorkFormFlag);
		
		//#CM722055
		// In the event of all shortage, disable the allocation process and complete the Picking Plan Info with shortage. 
		if(planQty == shortageQty)
		{
			//#CM722056
			// Update the Picking Plan Info. 
			allocateRetrievalPlan(plan, pWorkFormFlag, shortageQty);
		}
		//#CM722057
		// Allocate except for All Shortage. 
		else
		{
			//#CM722058
			// For the data with division Mixed, designate the Work Type, and execute recursive call and allocation process. 
			if (pWorkFormFlag.equals(RetrievalPlan.CASEPIECE_FLAG_MIX))
			{
				if (plan.getSchFlag().equals(RetrievalPlan.SCH_FLAG_COMPLETION))
				{
					throw new ScheduleException();
				}
				if (plan.getSchFlag().equals(RetrievalPlan.SCH_FLAG_PIECE_COMPLETION))
				{
					shortageQty = allocate(plan, WorkingInformation.CASEPIECE_FLAG_CASE, pAreaNo);
				}
				if (plan.getSchFlag().equals(RetrievalPlan.SCH_FLAG_CASE_COMPLETION))
				{
					shortageQty = allocate(plan, WorkingInformation.CASEPIECE_FLAG_PIECE, pAreaNo);
				}
			}
			//#CM722059
			// For data with division other than Mixed, allocate it based on its Work Type. 
			else
			{
				shortageQty = allocate(plan, pWorkFormFlag, pAreaNo);
			}
		}
		
		//#CM722060
		// In the event of shortage 
		if (shortageQty > 0)
		{
			//#CM722061
			// If not allocatable: 
			//#CM722062
			// For data with Shortage, generate a shortage info, Work Status Info, and Result info. 
			executeShortage(plan, planQty, shortageQty, pWorkFormFlag);
			
			return false;
		}

		return true;		
	}
	
	//#CM722063
	/**
	 * Determine the quantity to be allocated this time based on the Picking Plan Info and Work Type passed as an argument, and
	 * return the shortage qty. 
	 * If all qty is allocatable, return 0. 
	 * 
	 * @param pPlan Picking Plan Info to be allocated
	 * @param pWorkFormFlag Work Type of the Work Status Info to be generated 
	 * @param pAreaNo Allocation target area 
	 * @return Shortage qty of allocation 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected int getShortageQty(RetrievalPlan pPlan,
								String pWorkFormFlag,
								String pAreaNo[])
								throws ReadWriteException, ScheduleException
	{
		if (pWorkFormFlag.equals(RetrievalPlan.CASEPIECE_FLAG_MIX)
		  && !StringUtil.isBlank(pPlan.getCaseLocation())
		  && !StringUtil.isBlank(pPlan.getPieceLocation()))
		{
			int shortageCase = 0;
			int shortagePiece = 0;
			//#CM722064
			// For data with division Mixed, search twice for Case allocation and Piece allocation of which necessary inventory are different.
			//#CM722065
			// To determine whether Case is allocatable or not: 
			shortageCase = getShortageQty(pPlan, WorkingInformation.CASEPIECE_FLAG_CASE, pAreaNo);
			shortagePiece = getShortageQty(pPlan, WorkingInformation.CASEPIECE_FLAG_PIECE, pAreaNo);
			
			return (shortageCase + shortagePiece);
			
		}
		else
		{
			wStkKey.KeyClear();
			//#CM722066
			// Set the search conditions.
			//#CM722067
			// Obtain all the corresponding inventory without specifying the Case/Piece division 
			setAllocatableStockSearchKey(wStkKey, pPlan, pWorkFormFlag, null, pAreaNo);
			//#CM722068
			// Obtain the Total Allocatable Qty. 
			wStkKey.setAllocationQtyCollect("SUM");

			//#CM722069
			// Obtain only one which was summarized, but 
			//#CM722070
			// allow findPrimary not to accept it. Therefore, find it and obtain the result from the leading line. 
			Stock[] stk = (Stock[]) wStkHandler.find(wStkKey);

			int allocatableQty = 0;
			if (stk == null)
			{
				allocatableQty = 0;
			}
			else
			{
				allocatableQty = stk[0].getAllocationQty();
			}
		
			int planQty = getPlanAllocateQty(pPlan, pWorkFormFlag);
			if (allocatableQty < planQty)
			{
				return (planQty - allocatableQty);
			}
			else
			{
				return 0;
			}
		
		}
	}

	//#CM722071
	/**
	 * Execute the Allocation process. <BR>
	 * Before executing this method, ensure that the data to be passed as an argument is allocatable and 
	 * lock the target plan and inventory. If failed to allocate, update the Plan Info to Shortage. <BR>
	 * Obtain the currently requested allocation qty from the picking plan based on the designated work type. 
	 * Designate the Work Type to [Case ], [Piece ], or [None] designated in the Work Status Info. <BR>
	 * Announce ScheduleException if this time Planned allocation qty is 0 <BR>
	 * Obtain the Inventory from the designated area and allocate it. <BR>
	 * Allow the allocation process to update Inventory Info and generate Work Status, and update the Picking Plan Info allocated here based on the allocation result. <BR>
	 * Return the remaining allocated qty resulted from the allocation. <BR>
	 * 
	 * @param pPlan Picking Plan Info to be allocated
	 * @param pWorkFormFlag Work Type to be allocated
	 * @param pAreaNo Allocation target Area No. 
	 * @return Remaining Allocated qty 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected int allocate(RetrievalPlan pPlan, String pWorkFormFlag, String pAreaNo[]) 
								throws ReadWriteException, ScheduleException
	{
		//#CM722072
		// Obtain the requested allocation qty from the Picking Plan Info and this allocation division
		int allocateQty = getPlanAllocateQty(pPlan, pWorkFormFlag);
		if (allocateQty == 0)
		{
			throw new ScheduleException();
		}
		
		//#CM722073
		// Set the Allocated qty this time here for the remaining Allocated qty. 
		int remainingQty = allocateQty;
	
		//#CM722074
		// Execute the allocation process. 
		//#CM722075
		// Allocate the Inventory Info and generate its Work Status Info. 
		//#CM722076
		// The inventory allocation order depends on Work Type.
		if (pWorkFormFlag.equals(WorkingInformation.CASEPIECE_FLAG_CASE))
			remainingQty = allocateCaseOrder(pPlan, pAreaNo, allocateQty);
		else if (pWorkFormFlag.equals(WorkingInformation.CASEPIECE_FLAG_PIECE))
			remainingQty = allocatePieceOrder(pPlan, pAreaNo, allocateQty);
		else
			remainingQty = allocateNothingOrder(pPlan, pAreaNo, allocateQty);
		
		//#CM722077
		// Update the Picking Plan Info. 
		allocateRetrievalPlan(pPlan, pWorkFormFlag, remainingQty);
		
		return remainingQty;

	}
	
	//#CM722078
	/**
	 * Obtain the Planned Allocation Qty. <BR>
	 * Obtain the Qty from the Plan Info, as well as the Work Type of the current allocation target. <BR>
	 * <DIR>
	 * 1.Obtain the Planned qty from the Work Type.<BR>
	 * 2.Subtract the actually allocated qty from the quantity computed in the step 1. <BR>
	 * </DIR>
	 * 
	 * @param pPlan Picking Plan Info 
	 * @param pWorkFormFlag Work Type
	 * @return Planned Allocation Qty 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected int getPlanAllocateQty (RetrievalPlan pPlan, String pWorkFormFlag)
				throws ReadWriteException, 	ScheduleException
	{
		try
		{
			//#CM722079
			// Obtain the Planned qty from the Work Type.
			int planQty = getPlanQty(pPlan, pWorkFormFlag);
			
			//#CM722080
			// Assuming that the work may be separated due to cancel of allocation, 
			//#CM722081
			// exclude the qty existing in the Work Status Info from the allocation qty executed here. 
			
			//#CM722082
			// Obtain the quantity of stock that already exists in the Work Status Info as the allocation target this time.
			wWiKey.KeyClear();
			wWiKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
			wWiKey.setPlanEnableQtyCollect("SUM");
			wWiKey.setPlanUkey(pPlan.getRetrievalPlanUkey());
			wWiKey.setWorkFormFlag(pWorkFormFlag);
			wWiKey.setPlanUkeyGroup(1);
	
			int workQty = 0;
			if (wWiHandler.count(wWiKey) > 0)
			{			
				WorkingInformation winfos = (WorkingInformation) wWiHandler.findPrimary(wWiKey);
				workQty = winfos.getPlanEnableQty();
			}
			
			planQty -= workQty;
	
			return planQty;
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}		

	}
	
	//#CM722083
	/**
	 * Obtain the Planned qty by Work Type. <BR>
	 * Obtain the Qty from the Plan Info, as well as the Work Type of the current allocation target. <BR>
	 * <DIR>
	 * 1.For Plan data with division Mixed: <BR>
	 * 1-1.For data with Work Type "Case": <BR>
	 *   Return Work Planned qty  - Work Planned qty  % Packed qty <BR>
	 * 1-2.For data with Work Type "Piece": <BR>
	 *   Return "Work Planned qty  % packed qty". <BR>
	 * 2. Otherwise: <BR>
	 *   Return the Work Planned qty. <BR>
	 * </DIR>
	 * 
	 * @param pPlan Picking Plan Info 
	 * @param pWorkFormFlag Work Type
	 * @return Planned Allocation Qty 
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected int getPlanQty (RetrievalPlan pPlan, String pWorkFormFlag)
				throws ScheduleException
	{
		int planQty = 0;
		//#CM722084
		// Return the Planned qty if the Plan data is other than Mixed. 
		if (pPlan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_MIX))
		{
			//#CM722085
			// For data with division Mixed, disable to check for zero as a divisor because packed qyt is always set for such data.
			if (pWorkFormFlag.equals(WorkingInformation.CASEPIECE_FLAG_CASE))
			{
				planQty = pPlan.getPlanQty() - pPlan.getPlanQty() % pPlan.getEnteringQty();
			}
			else if (pWorkFormFlag.equals(WorkingInformation.CASEPIECE_FLAG_PIECE))
			{
				planQty = pPlan.getPlanQty() % pPlan.getEnteringQty();
			}
			
		}
		else
		{
			planQty = pPlan.getPlanQty();
		}
		
		return planQty;
			
	}
	
	//#CM722086
	/**
	 * Allocate the Case Work. <BR>
	 * Obtain the inventory in the order of Case, None, and then Piece, and allocate it. <BR>
	 * If the allocation results in shortage of stock qty, return the shortage qty. <BR>
	 * 
	 * @param pPlan Picking Plan Info to be allocated
	 * @param pAreaNo Allocation target area 
	 * @param pAllocateQty Requested allocation qty 
	 * @return Remaining allocation qty 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected int allocateCaseOrder(
							RetrievalPlan pPlan, 
							String pAreaNo[], 
							int pAllocateQty) throws ReadWriteException, ScheduleException
	{
		//#CM722087
		// Remaining allocation qty 
		int remainingQty = pAllocateQty;

		//#CM722088
		// Continue the allocation process until the Remaining allocation qty becomes 0. 
		Stock[] stocks = null;

		stocks = getAllocatableStocks(pPlan, WorkingInformation.CASEPIECE_FLAG_CASE, Stock.CASEPIECE_FLAG_CASE, pAreaNo);
		remainingQty = allocateStock(stocks, remainingQty, pPlan, WorkingInformation.CASEPIECE_FLAG_CASE);
		if (remainingQty == 0)
		{
			return 0;
		}

		stocks = getAllocatableStocks(pPlan, WorkingInformation.CASEPIECE_FLAG_CASE, Stock.CASEPIECE_FLAG_NOTHING, pAreaNo);
		remainingQty = allocateStock(stocks, remainingQty, pPlan, WorkingInformation.CASEPIECE_FLAG_CASE);
		if (remainingQty == 0)
		{
			return 0;
		}
		
		stocks = getAllocatableStocks(pPlan, WorkingInformation.CASEPIECE_FLAG_CASE, Stock.CASEPIECE_FLAG_PIECE, pAreaNo);
		remainingQty = allocateStock(stocks, remainingQty, pPlan, WorkingInformation.CASEPIECE_FLAG_CASE);
		if (remainingQty == 0)
		{
			return 0;
		}

		return remainingQty;
	}
	
	//#CM722089
	/**
	 * Allocate the Piece Work. <BR>
	 * Obtain the inventory in the order of Piece, None, and then Case, and allocated it. <BR>
	 * If the allocation results in shortage of stock qty, return the shortage qty. <BR>
	 * 
	 * @param pPlan Picking Plan Info to be allocated
	 * @param pAreaNo Allocation target area 
	 * @param pAllocateQty Requested allocation qty 
	 * @return Remaining allocation qty 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected int allocatePieceOrder(
							RetrievalPlan pPlan, 
							String pAreaNo[], 
							int pAllocateQty) throws ReadWriteException, ScheduleException
	{
		//#CM722090
		// Remaining allocation qty 
		int remainingQty = pAllocateQty;

		//#CM722091
		// Continue the allocation process until the Remaining allocation qty becomes 0. 
		Stock[] stocks = null;

		stocks = getAllocatableStocks(pPlan, WorkingInformation.CASEPIECE_FLAG_PIECE, Stock.CASEPIECE_FLAG_PIECE, pAreaNo);
		remainingQty = allocateStock(stocks, remainingQty, pPlan, WorkingInformation.CASEPIECE_FLAG_PIECE);
		if (remainingQty == 0)
		{
			return 0;
		}

		stocks = getAllocatableStocks(pPlan, WorkingInformation.CASEPIECE_FLAG_PIECE, Stock.CASEPIECE_FLAG_NOTHING, pAreaNo);
		remainingQty = allocateStock(stocks, remainingQty, pPlan, WorkingInformation.CASEPIECE_FLAG_PIECE);
		if (remainingQty == 0)
		{
			return 0;
		}
		
		stocks = getAllocatableStocks(pPlan, WorkingInformation.CASEPIECE_FLAG_PIECE, Stock.CASEPIECE_FLAG_CASE, pAreaNo);
		remainingQty = allocateStock(stocks, remainingQty, pPlan, WorkingInformation.CASEPIECE_FLAG_PIECE);
		if (remainingQty == 0)
		{
			return 0;
		}

		return remainingQty;
	}
	
	//#CM722092
	/**
	 * Allocate the work with division "None". <BR>
	 * Obtain the inventory in the order of None, Piece, and then Case, and allocate it. <BR>
	 * If the allocation results in shortage of stock qty, return the shortage qty. <BR>
	 * 
	 * @param pPlan Picking Plan Info to be allocated
	 * @param pAreaNo Allocation target area 
	 * @param pAllocateQty Requested allocation qty 
	 * @return Remaining allocation qty 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected int allocateNothingOrder(
							RetrievalPlan pPlan, 
							String pAreaNo[], 
							int pAllocateQty) throws ReadWriteException, ScheduleException
	{
		//#CM722093
		// Remaining allocation qty 
		int remainingQty = pAllocateQty;

		//#CM722094
		// Continue the allocation process until the Remaining allocation qty becomes 0. 
		Stock[] stocks = null;

		stocks = getAllocatableStocks(pPlan, WorkingInformation.CASEPIECE_FLAG_NOTHING, Stock.CASEPIECE_FLAG_NOTHING, pAreaNo);
		remainingQty = allocateStock(stocks, remainingQty, pPlan, WorkingInformation.CASEPIECE_FLAG_NOTHING);
		if (remainingQty == 0)
		{
			return 0;
		}
		
		stocks = getAllocatableStocks(pPlan, WorkingInformation.CASEPIECE_FLAG_NOTHING, Stock.CASEPIECE_FLAG_PIECE, pAreaNo);
		remainingQty = allocateStock(stocks, remainingQty, pPlan, WorkingInformation.CASEPIECE_FLAG_NOTHING);
		if (remainingQty == 0)
		{
			return 0;
		}

		stocks = getAllocatableStocks(pPlan, WorkingInformation.CASEPIECE_FLAG_NOTHING, Stock.CASEPIECE_FLAG_CASE, pAreaNo);
		remainingQty = allocateStock(stocks, remainingQty, pPlan, WorkingInformation.CASEPIECE_FLAG_NOTHING);
		if (remainingQty == 0)
		{
			return 0;
		}

		return remainingQty;
	}
	
	//#CM722095
	/**
	 * Obtain the allocatable Inventory Info. <BR>
	 * Extract the condition to search for inventory, from the Picking Plan Info of the argument, and search for the inventory. <BR>
	 * Based on the designated Work Type (pWorkFormFlag),
	 * determine which to use Case location or Piece location as a condition for searching. <BR>
	 * <BR>
	 * [search conditions] 
	 * <DIR>
	 *   Area No.<BR>
	 *   Consignor Code<BR>
	 *   Item Code<BR>
	 *   Location (based on the Work Type, determine to use either Case location or Piece location as a condition) <BR>
	 *   Case/Piece division <BR>
	 *   Center Inventory <BR>
	 *   Allocatable Qty: 1 or more <BR>
	 * </DIR>
	 * Sequence of Order
	 * <DIR>
	 *   1. If expiry date control is enabled, <BR>
	 *   In the order of Expiry Date and Storage Date <BR>
	 *   2. If expiry date control is disabled, <BR>
	 *   In the order of Storage Date <BR>
	 * </DIR>
	 * 
	 * @param pPlan Picking Plan Info to be allocated
	 * @param pWorkFormFlag Work Type of the Work Status Info to be generated 
	 * @param pCasePieceFlag Case/Piece division to be searched 
	 * @param pAreaNo Allocation target area 
	 * @return Allocation target Inventory Info 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected Stock[] getAllocatableStocks(RetrievalPlan pPlan,
								String pWorkFormFlag,
								String pCasePieceFlag,
								String pAreaNo[])
								throws ReadWriteException
	{
		wStkKey.KeyClear();
		//#CM722096
		// Set the search conditions.
		setAllocatableStockSearchKey(wStkKey, pPlan, pWorkFormFlag, pCasePieceFlag, pAreaNo);
		//#CM722097
		// Set the Searching Order. 
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			//#CM722098
			// If expiry date control is enabled, obtain the inventory in the order of Expiry Date. 
			wStkKey.setUseByDateOrder(1, true);
			wStkKey.setInstockDateOrder(2, true);
		}
		else
		{
			//#CM722099
			// First-in-first-out basis if expiry date control is disabled. 
			wStkKey.setInstockDateOrder(1, true);
		}
		
		return (Stock[]) wStkHandler.find(wStkKey); 
		
	}

	//#CM722100
	/**
	 * Set the Search key to search for the target Inventory Info to be allocated. <BR>
	 * Extract the condition to search for inventory, from the Picking Plan Info of the argument, and search for the inventory. <BR>
	 * <B> For data with division Mixed, only when Location is not designated (None),
	 * enable to designate Picking Plan to "Mixed" Work Type (pWorkFormFlag). If the Work Type (pWorkFormFlag) is designated to Mixed when Location is designated, 
	 * obtain wrong result. For Plan data for which Location is designated, 
	 * search twice respectively for data with division Case and with division Piece. </B>
	 * <BR>
	 * [search conditions] 
	 * <DIR>
	 *   Area No.<BR>
	 *   Consignor Code<BR>
	 *   Item Code<BR>
	 *   Location (based on the Work Type, determine to use either Case location or Piece location as a condition) <BR>
	 *   Case/Piece division <BR>
	 *   Center Inventory <BR>
	 *   Allocatable Qty: 1 or more <BR>
	 * </DIR>
	 * 
	 * @param stkKey Search key to search Inventory Info. 
	 * @param pPlan Picking Plan Info to be allocated
	 * @param pWorkFormFlag Work Type of the Work Status Info to be generated 
	 * @param pCasePieceFlag Case/Piece division to be searched 
	 * @param pAreaNo Allocation target area 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void setAllocatableStockSearchKey(
								StockSearchKey stkKey,
								RetrievalPlan pPlan,
								String pWorkFormFlag,
								String pCasePieceFlag,
								String pAreaNo[])
								throws ReadWriteException
	{
		if (pAreaNo != null)
		{
			stkKey.setAreaNo(pAreaNo);
		}
		if (!StringUtil.isBlank(pPlan.getConsignorCode()))
		{
			stkKey.setConsignorCode(pPlan.getConsignorCode());
		}
		if (!StringUtil.isBlank(pPlan.getItemCode()))
		{
			stkKey.setItemCode(pPlan.getItemCode());
		}
		//#CM722101
		// Determine the location to be searched based on the Case/Piece division of the Picking Plan Info. 
		String location = null;
		if (pWorkFormFlag.equals(WorkingInformation.CASEPIECE_FLAG_CASE))
		{
			location = pPlan.getCaseLocation();
		}
		else if (pWorkFormFlag.equals(WorkingInformation.CASEPIECE_FLAG_PIECE))
		{
			location = pPlan.getPieceLocation();
		}
		else if (pWorkFormFlag.equals(WorkingInformation.CASEPIECE_FLAG_NOTHING))
		{
			//#CM722102
			// Search for Location of either of division input if designated None. 
			if (!StringUtil.isBlank(pPlan.getCaseLocation()))
			{
				location = pPlan.getCaseLocation();
			}
			else
			{
				location = pPlan.getPieceLocation();
			}
		}
		else
		{
			//#CM722103
			// For data with division Mixed, if the plan is generated as a Location storage, designate the Work Type to 
			//#CM722104
			// search for the inventory. Therefore, disable to pass through here. 
			//#CM722105
			// Therefore, the location is not specified for the data that passes here in the Mixed status.
		}
		if (!StringUtil.isBlank(location))
		{
			stkKey.setLocationNo(location);
		}
		//#CM722106
		// Search through all divisions of data All if the Case/Piece division is not input. 
		if (!StringUtil.isBlank(pCasePieceFlag))
		{
			stkKey.setCasePieceFlag(pCasePieceFlag);
		}
		stkKey.setAllocationQty(1, ">=");
		stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		
	}

	//#CM722107
	/**
	 * Allocate the Inventory Info and generate its Work Status Info. <BR>
	 * Subtract the allocated qty from the Inventory Info, and generate a Work Status Info of the allocated data. <BR>
	 * Close this process, when completing all the allocation for the requested qty, and return 0. 
	 * Return the remaining qty if failed to allocate some of the requested qty. <BR>
	 * Example) 
	 * <DIR>
	 *     Requested allocation qty : 100<BR>
	 *     Total Stock Qty : 80<BR>
	 *     Returned value: 20 <BR>
	 * </DIR>
	 * 
	 * @param pStocks Allocation target inventory
	 * @param pAllocateQty Requested allocation qty 
	 * @param pPlan Picking Plan Info to be allocated
	 * @param pWorkFormFlag Work Type of the Work Status Info to be generated 
	 * @return Remaining allocation qty (0 when all qty was allocated successfully) 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected int allocateStock(Stock[] pStocks, int pAllocateQty, RetrievalPlan pPlan, String pWorkFormFlag)
				throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM722108
			// Remaining allocation qty 
			int remainingQty = pAllocateQty;
			for (int i = 0; i < pStocks.length; i++)
			{
				//#CM722109
				// Compute the allocation quantity to be allocated this time. 
				int allocateQty = 0;
				if (pStocks[i].getAllocationQty() >= remainingQty)
				{
					//#CM722110
					// If the Allocatable Qty is more than the remaining allocation qty, allocate the remaining allocation qty for the curren allocation.
					allocateQty = remainingQty;
				}
				else
				{
					//#CM722111
					// If the Allocatable Qty is less than the remaining allocation qty, allocate the Allocatable Qty for the current allocation.
					allocateQty = pStocks[i].getAllocationQty();
				}
				//#CM722112
				// Subtract the actually allocated qty this time here from the Remaining allocation qty. 
				remainingQty -= allocateQty;
				
				//#CM722113
				// Update the inventory. 
				//#CM722114
				// Subtract the currently allocated qty from the Allocatable Qty. 
				wStkAltKey.KeyClear();
				wStkAltKey.setStockId(pStocks[i].getStockId());
				wStkAltKey.updateAllocationQty(pStocks[i].getAllocationQty() - allocateQty);
				wStkAltKey.updateLastUpdatePname(CLASS_NAME);
				wStkHandler.modify(wStkAltKey);
				
				//#CM722115
				// Generate the Work Status Info. 
				createWorkInfo(pPlan, pStocks[i], allocateQty, pWorkFormFlag);
				
				//#CM722116
				// Completing allocations closes the inventory loop. 
				if (remainingQty == 0)
				{
					break;
				}
			}
			return remainingQty;
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM722117
	/**
	 * Generate the Work Status Info. <BR>
	 * Return the Work No. of the generated Work Status Info. 
	 * 
	 * @param pPlan Picking Plan Info 
	 * @param pStk Allocated Inventory Info 
	 * @param pAllocateQty Allocated qty
	 * @param pWorkFormFlag Work Type
	 * @return Work No. generated this time here 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected String createWorkInfo(RetrievalPlan pPlan, Stock pStk, int pAllocateQty, String pWorkFormFlag)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM722118
			// Obtain the Work No. from the Sequence object. 
			String jobno_seq = wSeqHandler.nextJobNo();
			WorkingInformation winfo = new WorkingInformation();
			
			winfo.setJobNo(jobno_seq);
			winfo.setCollectJobNo(jobno_seq);
			//#CM722119
			// Work type: Picking
			winfo.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			//#CM722120
			// Status flag: Standby 
			winfo.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			//#CM722121
			// Start Work flag: "Started" 
			winfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			//#CM722122
			// Case/Piece division (Work Type) 
			winfo.setWorkFormFlag(pWorkFormFlag);
			//#CM722123
			// Workable qty
			winfo.setPlanEnableQty(pAllocateQty);
			//#CM722124
			// TC/DC division: DC 
			winfo.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
			//#CM722125
			// Flag to report standby work: Not Reported 
			winfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);

			//#CM722126
			// Set it from the Picking Plan Info. 
			copyPlanDataToWorkInfo(winfo, pPlan, pWorkFormFlag);
			
			//#CM722127
			// Set it via Inventory Info. 
			//#CM722128
			// This causes to overwrite the Location. Set it after setting the Picking Plan Info. 
			//#CM722129
			// Stock ID
			winfo.setStockId(pStk.getStockId());
			//#CM722130
			// Area No.
			winfo.setAreaNo(pStk.getAreaNo());
			//#CM722131
			// Location No.
			winfo.setLocationNo(pStk.getLocationNo());
			//#CM722132
			// Case/Piece division: Load Size 
			winfo.setCasePieceFlag(pStk.getCasePieceFlag());
			//#CM722133
			// Expiry Date
			winfo.setUseByDate(pStk.getUseByDate());
			//#CM722134
			// System identification key 
			winfo.setSystemDiscKey(getAreaType(pStk.getAreaNo()));

			//#CM722135
			// Name of Add Process
			winfo.setRegistPname(CLASS_NAME);
			//#CM722136
			// Last update process name
			winfo.setLastUpdatePname(CLASS_NAME);

			//#CM722137
			// Registration of data 
			wWiHandler.create(winfo);
			
			return jobno_seq;

		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM722138
	/**
	 * Before generating a Work Status Info, map the Picking Plan Info onto the Work Status Info. 
	 * 
	 * @param pWinfo Work Status Info
	 * @param pPlan Picking Plan Info 
	 * @param pWorkFormFlag Work Type
	 * @throws ScheduleException Announce it when unexpected error occurs. 
	 */
	protected void copyPlanDataToWorkInfo(WorkingInformation pWinfo, RetrievalPlan pPlan, String pWorkFormFlag)
			throws ScheduleException
	{
		//#CM722139
		// Set it from the Picking Plan Info. 
		//#CM722140
		// Work Planned qty 
		pWinfo.setPlanQty(getPlanQty(pPlan, pWorkFormFlag));
		//#CM722141
		// Plan unique key 
		pWinfo.setPlanUkey(pPlan.getRetrievalPlanUkey());
		//#CM722142
		// Planned date
		pWinfo.setPlanDate(pPlan.getPlanDate());
		//#CM722143
		// Consignor Code
		pWinfo.setConsignorCode(pPlan.getConsignorCode());
		//#CM722144
		// Consignor Name
		pWinfo.setConsignorName(pPlan.getConsignorName());
		//#CM722145
		// Customer Code
		pWinfo.setCustomerCode(pPlan.getCustomerCode());
		//#CM722146
		// Customer Name
		pWinfo.setCustomerName1(pPlan.getCustomerName1());
		//#CM722147
		// Shipping Ticket No 
		pWinfo.setShippingTicketNo(pPlan.getShippingTicketNo());
		//#CM722148
		// Shipping ticket line No. 
		pWinfo.setShippingLineNo(pPlan.getShippingLineNo());
		//#CM722149
		// Item Code
		pWinfo.setItemCode(pPlan.getItemCode());
		//#CM722150
		// Item Name
		pWinfo.setItemName1(pPlan.getItemName1());
		//#CM722151
		// Host Planned Qty 
		pWinfo.setHostPlanQty(pPlan.getPlanQty());
		//#CM722152
		// Packed Qty per Case
		pWinfo.setEnteringQty(pPlan.getEnteringQty());
		//#CM722153
		// Packed qty per bundle
		pWinfo.setBundleEnteringQty(pPlan.getBundleEnteringQty());
		//#CM722154
		// Case ITF
		pWinfo.setItf(pPlan.getItf());
		//#CM722155
		// Bundle ITF
		pWinfo.setBundleItf(pPlan.getBundleItf());
		//#CM722156
		// Lot No. 
		pWinfo.setLotNo(pPlan.getLotNo());
		//#CM722157
		// Plan information Comment 
		pWinfo.setPlanInformation(pPlan.getPlanInformation());
		//#CM722158
		// Order No.
		if (pPlan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_MIX))
		{
			if (pWorkFormFlag.equals(WorkingInformation.CASEPIECE_FLAG_CASE))
			{
				pWinfo.setLocationNo(pPlan.getCaseLocation());
				pWinfo.setOrderNo(pPlan.getCaseOrderNo());
			}
			else
			{
				pWinfo.setLocationNo(pPlan.getPieceLocation());
				pWinfo.setOrderNo(pPlan.getPieceOrderNo());
			}
		}
		else if (pPlan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_CASE))
		{
			pWinfo.setLocationNo(pPlan.getCaseLocation());
			pWinfo.setOrderNo(pPlan.getCaseOrderNo());
		}
		else if (pPlan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_PIECE))
		{
			pWinfo.setLocationNo(pPlan.getPieceLocation());
			pWinfo.setOrderNo(pPlan.getPieceOrderNo());
		}
		else
		{
			if (!StringUtil.isBlank(pPlan.getCaseOrderNo()))
			{
				pWinfo.setLocationNo(pPlan.getCaseLocation());
				pWinfo.setOrderNo(pPlan.getCaseOrderNo());
			}
			else
			{
				pWinfo.setLocationNo(pPlan.getPieceLocation());
				pWinfo.setOrderNo(pPlan.getPieceOrderNo());
			}
		}
		//#CM722159
		// Batch No. 
		pWinfo.setBatchNo(pPlan.getBatchNo());
		//#CM722160
		// Plan information Added Date/Time 
		pWinfo.setPlanRegistDate(pPlan.getRegistDate());
			
	}
	
	//#CM722161
	/**
	 * Update the allocated Picking Plan Info. <BR>
	 * Update the schedule flag, as well as the Shortage Qty and Status flag if shortage. <BR>
	 * Update conditions as follows: <BR>
	 * <BR>
	 * [Schedule flag ] 
	 * <DIR>
	 * 1.For Plan data with division other than Mixed: <BR>
	 *   Completed <BR>
	 * 2.For Plan data with Mixed and the schedule flag other than Standby: <BR>
	 *   Completed <BR>
	 * 3.For Plan data with Mixed and the schedule flag Standby: <BR>
	 *   a. For data that is going to be allocated this time here: <BR>
	 *     Case Completed <BR>
	 *   b. Otherwise: <BR>
	 *     Piece Completed <BR>
	 * </DIR>
	 * <BR>
	 * Unless shortage occurs, disable to execute the following processes. <BR>
	 * [Shortage Qty] <BR>
	 * <DIR>
	 * Shortage Qty in the Picking Plan Info + Shortage Qty this time here 
	 * </DIR>
	 * [Status flag] <BR>
	 * <DIR>
	 * 1. Total of Result qty in the Picking Plan Info + Shortage Qty + Shortage Qty this time here is equal to the Work Planned qty: <BR>
	 *   Completed <BR>
	 * 2. Otherwise: <BR>
	 *   Partially Completed <BR>
	 * </DIR>
	 * 
	 * @param pPlan Picking Plan Info to be allocated
	 * @param pWorkFormFlag Work Type
	 * @param shortageQty Shortage Qty
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void allocateRetrievalPlan(RetrievalPlan pPlan, 
										String pWorkFormFlag,
										int pShortageQty) throws ReadWriteException
	{
		try
		{
			wPlanAltKey.KeyClear();
			wPlanAltKey.setRetrievalPlanUkey(pPlan.getRetrievalPlanUkey());
			//#CM722162
			// For Plan data with division other than Mixed, update the allocation flag to Completed. 
			if (!pPlan.getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_MIX))
			{
				wPlanAltKey.updateSchFlag(RetrievalPlan.SCH_FLAG_COMPLETION);
			}
			else
			{
				if (!pPlan.getSchFlag().equals(RetrievalPlan.SCH_FLAG_UNSTART))
				{
					//#CM722163
					// For Plan data with division Mixed of which Case or Piece has been already allocated, update the status to Allocation Completed. 
					wPlanAltKey.updateSchFlag(RetrievalPlan.SCH_FLAG_COMPLETION);
				}
				else
				{
					//#CM722164
					// For data with no designated Work Type, regard its allocation as Completed.
					if (pWorkFormFlag == null)
					{
						wPlanAltKey.updateSchFlag(RetrievalPlan.SCH_FLAG_COMPLETION);
					}
					//#CM722165
					// If the current allocation is Case, regard the Case allocation as Completed. 
					else if (pWorkFormFlag.equals(WorkingInformation.CASEPIECE_FLAG_CASE))
					{
						wPlanAltKey.updateSchFlag(RetrievalPlan.SCH_FLAG_CASE_COMPLETION);
					}
					//#CM722166
					// If the current allocation is Piece, regard the Piece allocation as Completed. 
					else
					{
						wPlanAltKey.updateSchFlag(RetrievalPlan.SCH_FLAG_PIECE_COMPLETION);
					}
				}
			}
			//#CM722167
			// For data with "Shortage" 
			if (pShortageQty > 0)
			{
				//#CM722168
				// For data with Shortage, update the Shortage Qty and the status to complete it with Shortage.
				wPlanAltKey.updateShortageCnt(pPlan.getShortageCnt() + pShortageQty);
				if ((pPlan.getResultQty() + pPlan.getShortageCnt() + pShortageQty) == pPlan.getPlanQty())
				{
					wPlanAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
				}
				else
				{
					//#CM722169
					// For data with work status Standby only, update it to Partially Completed. 
					//#CM722170
					// Keep status as it is when started or Working other Work. 
					if (pPlan.getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_UNSTART))
					{
						wPlanAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART);
					}
				}
			}
			wPlanAltKey.updateLastUpdatePname(CLASS_NAME);
			wPlanHandler.modify(wPlanAltKey);
		
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM722171
	/**
	 * Execute the process for shortage. 
	 * Generate shortage info, Work Status Info for the work of shortage, and Host Sending Result Information of the work of shortage. 
	 * @param pPlan Picking Plan Info to be allocated 
	 * @param pAllocateQty Planned Allocation Qty 
	 * @param pShortageCnt Shortage Qty
	 * @param pWorkFormFlag Work Type of the data to have been allocated this time 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected void executeShortage(RetrievalPlan pPlan, int pAllocateQty, int pShortageCnt, String pWorkFormFlag)
				throws ReadWriteException, ScheduleException
	{
		//#CM722172
		// Generate the shortage info. 
		createShortage(pPlan, pAllocateQty, pShortageCnt, pWorkFormFlag);
		//#CM722173
		// Generate the Work Status Info. 
		WorkingInformation winfo = createShortageWorkInfo(pPlan, pWorkFormFlag, pShortageCnt);
		//#CM722174
		// Genrate Host Sending Result info 
		createHostSend(winfo);
		
	}
	
	//#CM722175
	/**
	 * Generate the shortage info. <BR>
	 * Allocate the Batch No. that was automatically obtained when generating this class.
	 * 
	 * @param pPlan Picking Plan Info 
	 * @param pAllocateQty Requested allocation qty 
	 * @param pShortageCnt Shortage Qty
	 * @param pWorkFormFlag Work Type
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void createShortage(RetrievalPlan pPlan, int pAllocateQty, int pShortageCnt, String pWorkFormFlag) throws ReadWriteException
	{
		ShortageInfo shortageInfo = new ShortageInfo();
		
		try
		{
			//#CM722176
			// Work type: Picking
			shortageInfo.setJobType(ShortageInfo.JOB_TYPE_RETRIEVAL);
			//#CM722177
			// Planned Allocation Qty 
			shortageInfo.setPlanQty(pAllocateQty);
			//#CM722178
			// Allocatable Qty 
			shortageInfo.setEnableQty(pAllocateQty - pShortageCnt);
			//#CM722179
			// Shortage Qty
			shortageInfo.setShortageCnt(pShortageCnt);
			//#CM722180
			// Work Type
			shortageInfo.setCasePieceFlag(pWorkFormFlag);
			//#CM722181
			// Batch No. (Schedule No.) 
			shortageInfo.setBatchNo(wShortageBatchNo_seq);

			//#CM722182
			// Copy other data from the Picking Plan Info. 
			shortageInfo.setPlanUkey(pPlan.getRetrievalPlanUkey());
			shortageInfo.setPlanDate(pPlan.getPlanDate());
			shortageInfo.setConsignorCode(pPlan.getConsignorCode());
			shortageInfo.setConsignorName(pPlan.getConsignorName());
			shortageInfo.setCustomerCode(pPlan.getCustomerCode());
			shortageInfo.setCustomerName1(pPlan.getCustomerName1());
			shortageInfo.setShippingTicketNo(pPlan.getShippingTicketNo());
			shortageInfo.setShippingLineNo(pPlan.getShippingLineNo());
			shortageInfo.setItemCode(pPlan.getItemCode());
			shortageInfo.setItemName1(pPlan.getItemName1());
			shortageInfo.setEnteringQty(pPlan.getEnteringQty());
			shortageInfo.setBundleEnteringQty(pPlan.getBundleEnteringQty());
			shortageInfo.setItf(pPlan.getItf());
			shortageInfo.setBundleItf(pPlan.getBundleItf());
			shortageInfo.setPieceLocation(pPlan.getPieceLocation());
			shortageInfo.setCaseLocation(pPlan.getCaseLocation());
			shortageInfo.setPieceOrderNo(pPlan.getPieceOrderNo());
			shortageInfo.setCaseOrderNo(pPlan.getCaseOrderNo());
			shortageInfo.setUseByDate(pPlan.getUseByDate());
			shortageInfo.setLotNo(pPlan.getLotNo());
			shortageInfo.setPlanInformation(pPlan.getPlanInformation());
			shortageInfo.setWorkerCode(pPlan.getWorkerCode());
			shortageInfo.setWorkerName(pPlan.getWorkerName());
			shortageInfo.setTerminalNo(pPlan.getTerminalNo());
			shortageInfo.setRegistKind(pPlan.getRegistKind());
			shortageInfo.setRegistDate(pPlan.getRegistDate());
			
			//#CM722183
			// Set the Process name. 
			shortageInfo.setRegistPname(CLASS_NAME);
			shortageInfo.setLastUpdatePname(CLASS_NAME);
			
			wShortageHandler.create(shortageInfo);			
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
	}
	
	//#CM722184
	/**
	 * Generate a Work Status for the data with Completed with Shortage after shortage occurred. 
	 * Generate a Work Status in the following status. 
	 * 
	 * <table>
	 * <tr><td>Status flag</td><td>: </td><td> Completed </td></tr>
	 * <tr><td>Area No.</td><td>: </td><td> Area No. of Flat Storage to be provided by the system. </td></tr>
	 * <tr><td> System identification key </td><td>: </td><td> Flat Storage </td></tr>
	 * <tr><td>Location No.</td><td>: </td><td> Location of Picking Plan Info </td></tr>
	 * <tr><td>Workable qty</td><td>: </td><td> Current Shortage Qty </td></tr>
	 * <tr><td>Work shortage qty</td><td>: </td><td> Current Shortage Qty </td></tr>
	 * </table>
	 * 
	 * @param pPlan Picking Plan Info to be allocated 
	 * @param pWorkFormFlag Work Type of the data to have been allocated this time 
	 * @param pShortageCnt Shortage Qty
	 * @return Generated Work Status Info 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected WorkingInformation createShortageWorkInfo(RetrievalPlan pPlan, String pWorkFormFlag, int pShortageCnt)
			throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM722185
			// Obtain the Work No. from the Sequence object. 
			String jobno_seq = wSeqHandler.nextJobNo();
			WorkingInformation winfo = new WorkingInformation();
			
			winfo.setJobNo(jobno_seq);
			winfo.setCollectJobNo(jobno_seq);
			//#CM722186
			// Work type: Picking
			winfo.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			//#CM722187
			// Status flag: Completed 
			winfo.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			//#CM722188
			// Start Work flag: "Started" 
			winfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			//#CM722189
			// Case/Piece division
			winfo.setCasePieceFlag(pWorkFormFlag);
			//#CM722190
			// Case/Piece division (Work Type) 
			winfo.setWorkFormFlag(pWorkFormFlag);
			//#CM722191
			// TC/DC division: DC 
			winfo.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
			//#CM722192
			// Flag to report standby work: Not Reported 
			winfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
			//#CM722193
			// Area No. of Flat Storage. 
			winfo.setAreaNo(WmsParam.FLOOR_AREA_NO);
			//#CM722194
			// System identification key: Flat Storage 
			winfo.setSystemDiscKey(WorkingInformation.SYSTEM_DISC_KEY_WMS);
			//#CM722195
			// Worker 
			winfo.setWorkerCode(getWorkerCode());
			winfo.setWorkerName(getWorkerName());
			//#CM722196
			// Terminal 
			winfo.setTerminalNo(getTerminalNo());

			//#CM722197
			// Workable qty
			winfo.setPlanEnableQty(pShortageCnt);
			//#CM722198
			// Shortage Qty
			winfo.setShortageCnt(pShortageCnt);

			//#CM722199
			// Set it from the Picking Plan Info. 
			copyPlanDataToWorkInfo(winfo, pPlan, pWorkFormFlag);
			
			//#CM722200
			// Name of Add Process
			winfo.setRegistPname(CLASS_NAME);
			//#CM722201
			// Last update process name
			winfo.setLastUpdatePname(CLASS_NAME);

			//#CM722202
			// Registration of data 
			wWiHandler.create(winfo);
			
			return winfo;
			
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
	}
	
	//#CM722203
	/**
	 * Generate a Host Sending Result info based on the Work Status Info. 
	 * 
	 * @param pWinfo Work Status Info
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void createHostSend(WorkingInformation pWinfo) throws ReadWriteException
	{
		HostSend hostSend = new HostSend(pWinfo, wWmsManager.getWorkDate(), CLASS_NAME);
		try
		{
			wHostSendHandler.create(hostSend);
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM722204
	/**
	 * Obtain the Area type from the area info based on the Area No. <BR>
	 * Once the area has been searched through, maintain the result in HashMap and disable to search through it again.
	 * 
	 * @param areaNo Area No.
	 * @return Area type 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected int getAreaType (String areaNo) throws ReadWriteException, ScheduleException
	{
		//#CM722205
		// If no value is maintained in HashMap for maintaining area, 
		//#CM722206
		// search through the Area table and maintain the Area type in HashMap using Area No. as a key.
		if (wAreaTypeMap.isEmpty())
		{
			//#CM722207
			// Search for the area. 
			AreaHandler areaHndl = new AreaHandler(wConn);
			AreaSearchKey areaKey = new AreaSearchKey();
			Area[] area = null;
			areaKey.KeyClear();
			areaKey.setAreaNoCollect();
			areaKey.setAreaTypeCollect();
			area = (Area[]) areaHndl.find(areaKey);
			
			//#CM722208
			// Maintain the value in HashMap. 
			for (int i = 0; i < area.length; i++)
			{
				wAreaTypeMap.put(area[i].getAreaNo(), area[i].getAreaType());
			}
			
		}
		
		try
		{
			return Integer.parseInt((String) wAreaTypeMap.get(areaNo));
		}
		catch (NumberFormatException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		
	}
	
	//#CM722209
	/**
	 * Obtain the Area No. from the allocation target area designated via screen.<BR>
	 * If the area for allocation place designated via screen is not added in the system 
	 * (if it is not found in DmArea), return null. 
	 * 
	 * @param allocateField Allocation target area that was designated via screen 
	 * @return Allocation Target Area No. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected String[] getAreaNo(String[] allocateField) throws ReadWriteException
	{
		int[] areaType = new int[allocateField.length];
		
		for (int i = 0; i < allocateField.length; i++)
		{
			if (Integer.parseInt(allocateField[i]) == RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS)
			{
				areaType[i] = Area.SYSTEM_DISC_KEY_ASRS;
			}
			else if (Integer.parseInt(allocateField[i]) == RetrievalSupportParameter.SYSTEM_DISC_KEY_IDM)
			{
				areaType[i] = Area.SYSTEM_DISC_KEY_IDM;
			}
			else
			{
				areaType[i] = Area.SYSTEM_DISC_KEY_WMS;
			}
		}
		
		String[] areaNo = null;
		try
		{
			AreaOperator areaOpe = new AreaOperator(wConn);
			areaNo = areaOpe.getAreaNo(areaType);
		}
		catch (ScheduleException e)
		{
			//#CM722210
			// If the corresponding area is not introduced to the system: 
			//#CM722211
			// Disable to execute any process here. 
			//#CM722212
			// Determine to process error at the invoking source. 
		}
		
		return areaNo;
	}
	
	//#CM722213
	/**
	 * Search through the Picking Plan Info using the Picking Plan unique key of argument. <BR>
	 * If the corresponding data is not found, return null. 
	 * 
	 * @param pPlanUkey Picking Plan unique key
	 * @return Picking Plan Info 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected RetrievalPlan getRetrievalPlan(String pPlanUkey)
				throws ReadWriteException
	{
		RetrievalPlan plan = null;
		try
		{
			wPlanKey.KeyClear();
			wPlanKey.setRetrievalPlanUkey(pPlanUkey);
			plan = (RetrievalPlan) wPlanHandler.findPrimary(wPlanKey);
		
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
		return plan;
		
	}
	
	//#CM722214
	// Private methods -----------------------------------------------
}
//#CM722215
//end of class
