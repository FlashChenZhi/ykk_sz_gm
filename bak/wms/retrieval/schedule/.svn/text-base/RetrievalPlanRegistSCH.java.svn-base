package jp.co.daifuku.wms.retrieval.schedule;

//#CM725223
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.retrieval.dbhandler.RetrievalWorkingInformationHandler;

//#CM725224
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda  <BR>
 * <BR>
 * Allow this class to add WEB Picking plan data.  <BR>
 * Receive the contents entered via screen as a parameter, and execute the process to add the Picking plan data.  <BR>
 * Allow each method contained in this class to receive the Connection object and execute the process for updating the database, but
 * disable to commit nor roll-back the transaction.  <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Return the corresponding Consignor Code and Consignor name, if only one Consignor Code exists in the Work Status Info. <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
 * <BR>
 *   <Search conditions> <BR>
 *     <DIR>
 *     Status flag: other than Deleted 
 *     Work type: Picking
 *     </DIR>
 * </DIR>
 * <BR>  
 * 2. Process by clicking on the Enter button(<CODE>check</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter, and check the mandatory input, check for overflow, double entry, and length, and return the check result.  <BR>
 *   Return true if no corresponding data with the same Line No. exists in the Picking Plan Info. Return false when condition error occurs or corresponding data exists.  <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error. <BR>
 *   Check for duplication using Consignor Code, Planned Picking Date, Item Code, Case/Piece division, Picking Location, and Order No. as keys. <BR>
 *   If one or more same line No. of data with Status flag Deleted, return true instead of duplicate error.  <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR>
 * <DIR>
 *     Consignor Code* <BR>
 *     Consignor Name <BR>
 *     Planned Picking Date* <BR>
 *     Item Code* <BR>
 *     Item Name <BR>
 *     Order No. <BR>
 *     Customer Code <BR>
 *     Customer Name <BR>
 *     Ticket No.  <BR>
 *     Line No <BR>
 *     Packed Qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Planned Case Qty  <BR>
 *     Planned Piece Qty  <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Location No.  <BR>
 *     Case/Piece division* <BR>
 * </DIR>
 * <BR>
 *   <Contents of check for mandatory input>  <BR>
 *     1. Ensure that the Packed qty per Case is 1 or larger if any value is entered in the Case qty.  <BR>
 *     2. Require total input value in the planned case and the planned piece to be 1 or more.  <BR>
 *     3.For item with division None or Case, require that the Piece Qty is smaller than the Packed Qty per Case. <BR>
 *     4. If Inventory package is disabled, require to input a value in the Picking Location. <BR>
 * <BR>
 * </DIR>
 * <BR>
 * 3. Process by clicking "Add" button (<CODE>startSCH</CODE> method)  <BR><BR><DIR>
 *   Receive the contents displayed in the preset area, as a parameter, and execute the process to add the Picking plan data.  <BR>
 *   Return true when the process normally completed, or return false when the schedule failed to complete due to condition error or other causes.  <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR>
 * <DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Consignor Code* <BR>
 *     Consignor Name* <BR>
 *     Planned Picking Date* <BR>
 *     Item Code* <BR>
 *     Item Name* <BR>
 *     Order No.* <BR>
 *     Customer Code* <BR>
 *     Customer Name* <BR>
 *     Ticket No. * <BR>
 *     Line No* <BR>
 *     Packed Qty per Case* <BR>
 *     Packed qty per bundle* <BR>
 *     Planned Case Qty * <BR>
 *     Planned Piece Qty * <BR>
 *     Case ITF* <BR>
 *     Bundle ITF* <BR>
 *     Location No. * <BR>
 *     Case/Piece division* <BR>
 *     Entry type * <BR>
 *     Terminal No.* <BR>
 *     Preset line No. * <BR>
 * </DIR>
 * <BR>
 *   <Check Process Condition>  <BR>
 *     1.Require that the Worker Code and the Password are defined in the Worker master.  <BR>
 *       <DIR>
 *       Check only the leading value of the array for the values of Worker code and password.  <BR>
 *       </DIR>
 *     2.Ensure that there is not Picking Plan Info with the same Line No. exists in the database. (Check Exclusion.)  <BR>
 *       <DIR>
 *       Note)Disable to regard any data of Picking Plan Info with Status flag Standby or Deleted as a duplication target data. 
 *       </DIR>
 * <BR>
 *   <Details of processes>  <BR>
 *     1. Using the Consignor Code, Planned Picking Date, Item Code, Case/Piece division, Picking Location, and Order No. as keys, 
 *       search through the Picking Plan Info <CODE>(DNRETRIEVALPLAN)</CODE>.  <BR>
 *     2.For data with Inventory package disabled and with Standby found by searching through Picking Plan Info Search in the process 1 above-stated, 
 *       invoke the cancel method of the Allocation Process class <CODE>(RetrievalAllocateOperator)</CODE>, and execute the process to cancel the allocation.  <BR>
 *     3.Add a new Picking Plan Info <CODE>(DNRETRIEVALPLAN)</CODE> using parameters via screen.  <BR>
 *     4. If Inventory package is disabled, invoke the allocate method of the allocation process class <CODE>(RetrievalAllocator)</CODE>, and execute the process for canceling the allocation.  <BR>
 *     5. Abort the process if the allocated qty does not satisfy the planned qty in the course of the processes at the above-stated steps (1 to 4) for every line.  <BR>
 *     Execute the RollBack Process (Return all the current settings to the previous settings) <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/21</TD><TD>K.Toda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2007/02/07 04:20:03 $
 * @author  $Author: suresh $
 */
public class RetrievalPlanRegistSCH extends AbstractRetrievalSCH
{

	//#CM725225
	// Class variables -----------------------------------------------
	//#CM725226
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM725227
	/**
	 * Process name 
	 */
	private static String wProcessName = "RetrievalPlanRegistSCH";
	
	//#CM725228
	// Class method --------------------------------------------------
	//#CM725229
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.4 $,$Date: 2007/02/07 04:20:03 $");
	}
	//#CM725230
	// Constructors --------------------------------------------------
	//#CM725231
	/**
	 * Initialize this class. 
	 */
	public RetrievalPlanRegistSCH()
	{
		wMessage = null;
	}

	//#CM725232
	// Public methods ------------------------------------------------
	//#CM725233
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * Return the corresponding Consignor Code and Consignor name (the latest Added Date/Time), if only one Consignor Code exists in the Work Status Info. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. 
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * @return Class that implements the <CODE>Parameter</CODE> interface that contains the search result. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		//#CM725234
		// Returned data 
		RetrievalSupportParameter wParam = new RetrievalSupportParameter();

		//#CM725235
		// Generate instance of handlers for Picking Plan information. 
		RetrievalPlanSearchKey retPlanKey = new RetrievalPlanSearchKey();
		RetrievalPlanReportFinder wObj = new RetrievalPlanReportFinder(conn);
		RetrievalPlan[] wRetrieval = null;

		try
		{
			//#CM725236
			// Status flag = other than "Delete " 
			retPlanKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");

			retPlanKey.setConsignorCodeGroup(1);
			retPlanKey.setConsignorCodeCollect("");

			wObj.open();
			if (wObj.search(retPlanKey) == 1)
			{
				//#CM725237
				// Set the search conditions. 
				retPlanKey.KeyClear();
				//#CM725238
				// Status flag = other than "Delete " 
				retPlanKey.setStatusFlag( StoragePlan.STATUS_FLAG_DELETE, "!=" );
				//#CM725239
				// Set the added date/time for the sorting order. 
				retPlanKey.setRegistDateOrder( 1, false );
				
				retPlanKey.setConsignorCodeCollect();
				retPlanKey.setConsignorNameCollect();
				//#CM725240
				// Obtain the Consignor Name with the latest added date/time. 
				if ( wObj.search( retPlanKey ) > 0 )
				{
					wRetrieval = (RetrievalPlan[])wObj.getEntities(1);
					wParam.setConsignorName( wRetrieval[ 0 ].getConsignorName() );
					wParam.setConsignorCode( wRetrieval[ 0 ].getConsignorCode() );
				}
			}
			wObj.close();

		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

		return wParam;
	}

	//#CM725241
	/**
	 * Receive the contents of Preset area input via screen as a parameter, and  
	 * check for mandatory, overflow and duplication and return the checking results.  <BR>
	 * Return true if no corresponding data with the same Line No. exists in the Picking Plan Info. 
	 * Return false if condition error occurs or if the corresponding data exists, as exclusion error.  <BR>
	 * If one or more same line No. of data with Status flag Deleted, return true instead of exclusion error.  <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param checkParam <CODE>RetrievalSupportParameter</CODE> class instance with the input contents <BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> instance throws <CODE>ScheduleException</CODE>. <BR>
	 *         Use <CODE>getMessage()</CODE> method to refer to the content of the error.
	 * @param inputParams Array of the Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has Contents of Preset area <BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> instance throws <CODE>ScheduleException</CODE>. <BR>
	 *         Use <CODE>getMessage()</CODE> method to refer to the content of the error.
	 * @return true when the schedule process normally completed, or false when it failed or not allowed. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams) throws ScheduleException, ReadWriteException
	{
		//#CM725242
		// Contents of the input area 
		RetrievalSupportParameter wParam = (RetrievalSupportParameter) checkParam;
		//#CM725243
		// Contents of Preset area 
		RetrievalSupportParameter[] wParamList = (RetrievalSupportParameter[]) inputParams;

		//#CM725244
		// Check the Worker code and password 
		if (!checkWorker(conn, wParam))
		{
			return false;
		}

		//#CM725245
		// Check the count of data to be displayed. 
		if (wParamList != null && wParamList.length + 1 > WmsParam.MAX_TOTAL_QTY)
		{
			//#CM725246
			// 6023096=More than {0} data exist. Data cannot be entered. 
			wMessage = "6023096" + wDelim + MAX_TOTAL_QTY_DISP;
			return false;
		}

		//#CM725247
		// Check for mandatory input based on the condition. 
		//#CM725248
		// If inventory control package is disabled, require to input a value in the Location. (mandatory) 
		if(!isStockPack(conn))
		{
			//#CM725249
			// Require that any value is input in the Picking Location. 
			if (StringUtil.isBlank(wParam.getRetrievalLocation()))
			{
				//#CM725250
				// 6023178=Please enter the picking location. 
				wMessage = "6023178";
				return false;
			}
		}
			
		//#CM725251
		// Check for input by Case/Piece division. 
		if (!retrievalInputCheck(wParam.getCasePieceflg(), wParam.getEnteringQty(), wParam.getPlanCaseQty(), wParam.getPlanPieceQty()))
		{
			//#CM725252
			// Set Message within a method. 
			return false;
		}

		//#CM725253
		// Execute the overflow check. 
		if ((long) wParam.getEnteringQty() * (long) wParam.getPlanCaseQty() + (long) wParam.getPlanPieceQty() > WmsParam.MAX_TOTAL_QTY)
		{
			//#CM725254
			// 6023058=Please enter {1} or smaller for {0}. 
			wMessage = "6023058" + wDelim + DisplayText.getText("LBL-W0379") + wDelim + MAX_TOTAL_QTY_DISP;
			return false;
		}

		//#CM725255
		// Check for duplication across the preset area. 
		if(wParamList != null)
		{
			if (!duplicationCheck(wParam, wParamList))
			{
				//#CM725256
				// 6023142=Same data has been already entered. 
				wMessage = "6023142";
				return false;
			}
		}

		//#CM725257
		// Check for duplication across the DB. 
		//#CM725258
		// Obtain the search result of the Picking Plan Info. 
		RetrievalPlanOperator planOperator = new RetrievalPlanOperator(conn);
		wParam.setCaseLocation(wParam.getRetrievalLocation());
		wParam.setPieceLocation(wParam.getRetrievalLocation());
		wParam.setCaseOrderNo(wParam.getOrderNo());
		wParam.setPieceOrderNo(wParam.getOrderNo());
		RetrievalPlan[]retrieval = planOperator.findRetrievalPlan(wParam);
		
		if (retrieval != null)
		{
			for (int i = 0;i < retrieval.length;i++)
			{
				//#CM725259
				// For data with "Working": 
				if (retrieval[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_NOWWORKING))
				{
					//#CM725260
					// 6023269=You cannot enter the data. The same data in process of work exists. 
					wMessage = "6023269";
					return false;
				}

				//#CM725261
				// For data with status "Partially Completed": 
				if (retrieval[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART))
				{
					//#CM725262
					// 6023270=You cannot enter the data. The same data which is Partly Received exists. 
					wMessage = "6023270";
					return false;
				}

				//#CM725263
				// For data with "Completed" or "Started": 
				if (retrieval[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_COMPLETION)
				|| retrieval[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_START))
				{
					//#CM725264
					// 6023090=The data already exists. Unable to input. 
					wMessage = "6023090";
					return false;
				}
				
				//#CM725265
				// Check the allocation. 
				if (isStockPack(conn))
				{
					if (retrieval[i].getSchFlag().equals(RetrievalPlan.SCH_FLAG_COMPLETION) ||
						retrieval[i].getSchFlag().equals(RetrievalPlan.SCH_FLAG_CASE_COMPLETION) ||
						retrieval[i].getSchFlag().equals(RetrievalPlan.SCH_FLAG_PIECE_COMPLETION))
					{
						//#CM725266
						// 6023409=The same allocated data already exists. Cannot enter the data. 
						wMessage = "6023409";
						return false;
					}
				}
				
			}
		}	

		//#CM725267
		// 6001019=Entry was accepted. 
		wMessage = "6001019";
		return true;
	}

	//#CM725268
	/**
	 * Receive the contents entered via screen as a parameter, and start the schedule to add the Picking plan data. <BR>
	 * Assume that two or more data may be input, including setting via preset area. So, require the parameter to receive them as an array. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * Return true if the schedule normally completed, or return false if failed. 
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> instance throws <CODE>ScheduleException</CODE>. <BR>
	 *         Use <CODE>getMessage()</CODE> method to refer to the content of the error.
	 * @return true when the schedule process normally completed, or false when it failed or not allowed. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		boolean registFlag = false;

		//#CM725269
		// Allow this flag to determine whether Processing Load flag is updated in its own class. 
		boolean updateLoadDataFlag = false;
		
		try
		{
			//#CM725270
			// Input information of the parameter
			RetrievalSupportParameter[] wParam = (RetrievalSupportParameter[]) startParams;

			if (wParam == null)
			{
				return registFlag;
			}

			//#CM725271
			// Check the Worker code and password 
			if (!checkWorker(conn, wParam[0]))
			{
				return registFlag;
			}
			//#CM725272
			// Check the Daily Update Processing. 
			if (isDailyUpdate(conn))
			{
				return registFlag;
			}
			//#CM725273
			// Execute the process for checking the Load flag.  false: Loading in process 	
			if (isLoadingData(conn))
			{
				return registFlag;
			}
			//#CM725274
			// Update "Load" flag: "Loading in process" 
			if (!updateLoadDataFlag(conn, true))
			{
				return false;
			}
			doCommit(conn,wProcessName);
			updateLoadDataFlag = true;
			
			//#CM725275
			// Generate the Plan data. 
			if (isStockPack(conn))
			{
				//#CM725276
				// Inventory package enabled 
				registFlag = createRetrievalPlanStockOn(conn, wParam);
				return registFlag;
			}
			else
			{
				//#CM725277
				// Inventory package disabled
				registFlag = createRetrievalPlanStockOff(conn, wParam);
				return registFlag;
			}
		}
		catch (ReadWriteException e)
		{
			doRollBack(conn,wProcessName);
			throw new ReadWriteException(e.getMessage());
		}
		catch (Exception e)
		{
			doRollBack(conn,wProcessName);
			throw new ScheduleException(e.getMessage());
		}
		finally
		{
			//#CM725278
			// Failing to add rolls back the transaction. 
			if (!registFlag)
			{
				doRollBack(conn,wProcessName);
			}

			//#CM725279
			// If Processing Extraction flag was updated in its own class,
			//#CM725280
			// update the Loading in-process flag to 0: Stopping. 
			if( updateLoadDataFlag )
			{
				//#CM725281
				// Update the Loading-in-progress flag: Stopping 
				updateLoadDataFlag(conn, false);
				doCommit(conn,wProcessName);
			}
		}
	}

	//#CM725282
	// Package methods -----------------------------------------------

	//#CM725283
	// Protected methods ---------------------------------------------

	//#CM725284
	// Private methods -----------------------------------------------
	//#CM725285
	/**
	 * Verify the possibility to input the input data in the preset. <BR>
	 * If there is the same data, disable to input it and return false. <BR>
	 * If there is no same data and its input is accepted, return true. 
	 * @param inputParam <CODE>RetrievalSupportParameter</CODE> that contains the input info of the Input area
	 * @param listParam <CODE>RetrievalSupportParameter</CODE> containing info of Preset area 
	 * @return Result of check  (Enabled to input: true, Disabled to input: false) 
	 */
	protected boolean duplicationCheck(RetrievalSupportParameter inputParam, RetrievalSupportParameter[] listParam)
	{
		for (int i = 0; i < listParam.length; i++)
		{
			//#CM725286
			// For data with the same Consignor, Planned date, Item Code, Case/Piece division, Location, and Order No., regard these data as the same one. 
			if (listParam[i].getConsignorCode().equals(inputParam.getConsignorCode())
				&& listParam[i].getRetrievalPlanDate().equals(inputParam.getRetrievalPlanDate())
				&& listParam[i].getItemCode().equals(inputParam.getItemCode())
				&& listParam[i].getCasePieceflg().equals(inputParam.getCasePieceflg())
				&& listParam[i].getRetrievalLocation().equals(inputParam.getRetrievalLocation())
				&& listParam[i].getOrderNo().equals(inputParam.getOrderNo()))
			{
				return false;
			}

		}
		
		return true;
	}
	
	//#CM725287
	/**
	 * Generate the Picking Plan Info. <BR>
	 * Use this method only when Inventory package is enabled. 
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws Exception Announce when all of exceptions occur. 
	 */
	protected boolean createRetrievalPlanStockOn(Connection conn, Parameter[] startParams) throws ReadWriteException, Exception
	{
		boolean registFlag = false;

		//#CM725288
		// Input information of the parameter
		RetrievalSupportParameter[] wParam = (RetrievalSupportParameter[]) startParams;
		
		//#CM725289
		// Process to submit/add Plan Info Process 
		RetrievalPlanOperator retOperator = new RetrievalPlanOperator(conn);
		RetrievalPlanHandler wPlanHandler = new RetrievalPlanHandler(conn); 
		RetrievalPlanAlterKey wPlanAltKey = new RetrievalPlanAlterKey();

		//#CM725290
		// Maintain the Batch No. and the Worker Name. 
		SequenceHandler sequenceHandler = new SequenceHandler(conn);
		String batch_seqno = sequenceHandler.nextRetrievalPlanBatchNo();
		String workerName = getWorkerName(conn, wParam[0].getWorkerCode());

		//#CM725291
		// Lock the Picking plan data. 
		retOperator.lockRetrievalPlan();

		for (int loop = 0; loop < wParam.length; loop++)
		{
			//#CM725292
			// Check for exclusion. 
			if (!canUpdate(conn, wParam[loop]))
			{
				//#CM725293
				// Close the process when error via other terminal occurs. 
				//#CM725294
				// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + wParam[loop].getRowNo();
				return false;
			}

			//#CM725295
			// Check for matching to the DB. 
			//#CM725296
			// Obtain the search result of the Picking Plan Info. 
			RetrievalPlanOperator planOperator = new RetrievalPlanOperator(conn);
			//#CM725297
			// Set the Location Order No.
			if (wParam[loop].getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM725298
				// Location (Set the data with divition "None" in the Piece Location.) 
				wParam[loop].setPieceLocation(wParam[loop].getRetrievalLocation());
				//#CM725299
				// Order No. (for data with division None, set it in Piece Order No.) 
				wParam[loop].setPieceOrderNo(wParam[loop].getOrderNo());
			}
			else
			{
				//#CM725300
				// Set the Location (Set the same value for both). 
				wParam[loop].setCaseLocation(wParam[loop].getRetrievalLocation());
				wParam[loop].setPieceLocation(wParam[loop].getRetrievalLocation());
				//#CM725301
				// Order No. (Set the same value for both) 
				wParam[loop].setCaseOrderNo(wParam[loop].getOrderNo());
				wParam[loop].setPieceOrderNo(wParam[loop].getOrderNo());
			}

			RetrievalPlan[] retrieval = null;
			retrieval = planOperator.findRetrievalPlanForUpdate(wParam[loop], true);

			if (retrieval != null)
			{
				for (int i = 0;i < retrieval.length;i++)
				{
					//#CM725302
					// Determine as Standby. 
					if (!checkStatus(retrieval[i].getStatusFlag(), wParam[loop].getRowNo()))
					{
						return registFlag;
					}
					//#CM725303
					// Check the allocation. 
					if (isStockPack(conn))
					{
						if (!retrieval[i].getSchFlag().equals(RetrievalPlan.SCH_FLAG_UNSTART))
						{
							//#CM725304
							// 6023273=No.{0}{1}
							//#CM725305
							// 6023409=The same allocated data already exists. Cannot enter the data. 
							wMessage = "6023273" + wDelim + wParam[loop].getRowNo() + wDelim + MessageResource.getMessage("6023409");
							return false;
						}
					}
					//#CM725306
					// Update the Picking Plan Info to "Deleted". 
					wPlanAltKey.KeyClear();
					wPlanAltKey.setRetrievalPlanUkey(retrieval[i].getRetrievalPlanUkey());
					wPlanAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE);
					wPlanAltKey.updateLastUpdatePname(wProcessName);
					wPlanHandler.modify(wPlanAltKey);
				}
			}

			//#CM725307
			// Set the data for Submit/Add Process. 
			//#CM725308
			// Planned Picking Qty (Planned total qty) 
			wParam[loop].setTotalPlanQty(wParam[loop].getEnteringQty() * wParam[loop].getPlanCaseQty() + wParam[loop].getPlanPieceQty());
			//#CM725309
			// Batch No. 
			wParam[loop].setBatchNo(batch_seqno);
			//#CM725310
			// Worker Code
			wParam[loop].setWorkerCode(wParam[0].getWorkerCode());
			//#CM725311
			// Worker Name
			wParam[loop].setWorkerName(workerName);
			//#CM725312
			// Terminal No.
			wParam[loop].setTerminalNumber(wParam[0].getTerminalNumber());
			//#CM725313
			// Entry type 
			wParam[loop].setRegistKind(SystemDefine.REGIST_KIND_WMS);

			//#CM725314
			// Process for submitting/adding data 
			RetrievalPlan retrievalPlan = retOperator.createRetrievalPlan(wParam[loop], wProcessName);
			if (retrievalPlan == null)
			{
				//#CM725315
				// Close the process when error via other terminal occurs. 
				//#CM725316
				// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + wParam[loop].getRowNo();
				return registFlag;
			}
		}

		//#CM725317
		// 6001003=Added. 
		wMessage = "6001003";
		registFlag = true;
		return registFlag;
	}
	
	//#CM725318
	/**
	 * Generate the Picking Plan Info. <BR>
	 * Use this method only when Inventory package is disabled. 
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws Exception Announce when all of exceptions occur. 
	 */
	private boolean createRetrievalPlanStockOff(Connection conn, Parameter[] startParams) throws ReadWriteException, Exception
	{
		boolean registFlag = false;

		//#CM725319
		// Input information of the parameter
		RetrievalSupportParameter[] wParam = (RetrievalSupportParameter[]) startParams;
		
		RetrievalWorkingInformationHandler wiHandle = new RetrievalWorkingInformationHandler(conn);
		if (!wiHandle.lockPlanData(startParams))
		{
			//#CM725320
			// 6003006=Unable to process this data. It has been updated via other work station. 
			wMessage = "6003006";
			return registFlag;
		}
		
		//#CM725321
		// Process to submit/add Plan Info Process 
		RetrievalPlanOperator retOperator = new RetrievalPlanOperator(conn);
		RetrievalPlanHandler wPlanHandler = new RetrievalPlanHandler(conn); 
		RetrievalPlanAlterKey wPlanAltKey = new RetrievalPlanAlterKey();

		//#CM725322
		// Maintain the Batch No. and the Worker Name. 
		SequenceHandler sequenceHandler = new SequenceHandler(conn);
		String batch_seqno = sequenceHandler.nextRetrievalPlanBatchNo();
		String workerName = getWorkerName(conn, wParam[0].getWorkerCode());

		//#CM725323
		// Lock all the Center Inventory in the Inventory Info. 
		retOperator.lockStockData(conn);

		RetrievalAllocateOperator retAllocOperator = new RetrievalAllocateOperator(conn);
		for (int loop = 0; loop < wParam.length; loop++)
		{
			//#CM725324
			// Check for exclusion. 
			if (!canUpdate(conn, wParam[loop]))
			{
				//#CM725325
				// Close the process when error via other terminal occurs. 
				//#CM725326
				// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + wParam[loop].getRowNo();
				return registFlag;
			}

			//#CM725327
			// Check for matching to the DB. 
			//#CM725328
			// Obtain the search result of the Picking Plan Info. 
			RetrievalPlanOperator planOperator = new RetrievalPlanOperator(conn);
			//#CM725329
			// Set the Location Order No.
			if (wParam[loop].getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM725330
				// Location (Set the data with divition "None" in the Piece Location.) 
				wParam[loop].setPieceLocation(wParam[loop].getRetrievalLocation());
				//#CM725331
				// Order No. (for data with division None, set it in Piece Order No.) 
				wParam[loop].setPieceOrderNo(wParam[loop].getOrderNo());
			}
			else
			{
				//#CM725332
				// Set the Location (Set the same value for both). 
				wParam[loop].setCaseLocation(wParam[loop].getRetrievalLocation());
				wParam[loop].setPieceLocation(wParam[loop].getRetrievalLocation());
				//#CM725333
				// Order No. (Set the same value for both) 
				wParam[loop].setCaseOrderNo(wParam[loop].getOrderNo());
				wParam[loop].setPieceOrderNo(wParam[loop].getOrderNo());
			}

			RetrievalPlan[] retrieval = null;
			retrieval = planOperator.findRetrievalPlanForUpdate(wParam[loop], true);

			if (retrieval != null)
			{
				for (int i = 0;i < retrieval.length;i++)
				{
					//#CM725334
					// Determine as Standby. 
					if (!checkStatus(retrieval[i].getStatusFlag(), wParam[loop].getRowNo()))
					{
						return registFlag;
					}
					//#CM725335
					// Cancel the allocation (Work Status Info and Inventory Info). 
					if (!retAllocOperator.cancel(
							conn,
							retrieval[i].getRetrievalPlanUkey(),
							wParam[loop].getWorkerCode(),
							wParam[loop].getWorkerName(),
							wParam[loop].getTerminalNumber(),
							wProcessName))
					{
						//#CM725336
						// 6006002=Database error occurred.{0}
						RmiMsgLogClient.write("6006002" + MessageResource.DELIM + "RetreivalPlanOperator", this.getClass().getName());
						//#CM725337
						// 6023154=There is no data to update. 
						throw (new ReadWriteException("6023154"));
					}
					
					//#CM725338
					// Update the Picking Plan Info to "Deleted". 
					wPlanAltKey.KeyClear();
					wPlanAltKey.setRetrievalPlanUkey(retrieval[i].getRetrievalPlanUkey());
					wPlanAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE);
					wPlanAltKey.updateLastUpdatePname(wProcessName);
					wPlanHandler.modify(wPlanAltKey);
				}
			}

			//#CM725339
			// Set the data for Submit/Add Process. 
			//#CM725340
			// Planned Picking Qty (Planned total qty) 
			wParam[loop].setTotalPlanQty(wParam[loop].getEnteringQty() * wParam[loop].getPlanCaseQty() + wParam[loop].getPlanPieceQty());
			//#CM725341
			// Batch No. 
			wParam[loop].setBatchNo(batch_seqno);
			//#CM725342
			// Worker Code
			wParam[loop].setWorkerCode(wParam[0].getWorkerCode());
			//#CM725343
			// Worker Name
			wParam[loop].setWorkerName(workerName);
			//#CM725344
			// Terminal No.
			wParam[loop].setTerminalNumber(wParam[0].getTerminalNumber());
			//#CM725345
			// Entry type 
			wParam[loop].setRegistKind(SystemDefine.REGIST_KIND_WMS);

			//#CM725346
			// Process for submitting/adding data 
			RetrievalPlan retrievalPlan = retOperator.createRetrievalPlan(wParam[loop], wProcessName);
			if (retrievalPlan == null)
			{
				//#CM725347
				// Close the process when error via other terminal occurs. 
				//#CM725348
				// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + wParam[loop].getRowNo();
				return registFlag;
			}

			//#CM725349
			// Allocation process (Work Status Info, Inventory Info, and Picking Plan Info) 
			int allocateQty = 0;
			allocateQty = retAllocOperator.allocate(
								conn,
								retrievalPlan.getRetrievalPlanUkey(),
								retrievalPlan.getWorkerCode(),
								retrievalPlan.getWorkerName(),
								wParam[loop].getTerminalNumber(),
								wProcessName);

			if(allocateQty < retrievalPlan.getPlanQty())
			{
				//#CM725350
				// 6023359=No.{0} Shortage occurred. 
				wMessage = "6023359" + wDelim + wParam[loop].getRowNo();
				return registFlag;
			}
		}

		//#CM725351
		// 6001003=Added. 
		wMessage = "6001003";
		registFlag = true;
		return registFlag;
	}
	
	//#CM725352
	/** 
	 * Receive the contents entered via screen as a parameter and return the result of checking for presence of corresponding data. <BR>
	 * Return true if no corresponding data with the same Line No. exists in the Picking Plan Info. 
	 * Return false if condition error occurs or if corresponding data exists.  <BR>
	 * If one or more same line No. of data with Status flag Standby or Completed, return true instead of duplicate error.  <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param checkParam <CODE>RetrievalSupportParameter</CODE> class instance with the input contents <BR>
	 *         Designating any instance other than RetrievalSupportParameter instance throws <CODE>ScheduleException</CODE>. <BR>
	 *         Use <CODE>getMessage()</CODE> method to refer to the content of the error 
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 * @return true when the schedule process normally completed, or false when it failed or not allowed. 
	 */
	protected boolean canUpdate(Connection conn, Parameter checkParam) throws ScheduleException
	{

		//#CM725353
		// Generate instance of handlers for Picking Plan information. 
		RetrievalPlanSearchKey wKey = new RetrievalPlanSearchKey();
		RetrievalPlanHandler wObj = new RetrievalPlanHandler(conn);

		try
		{
			//#CM725354
			// Search conditions for parameters 
			RetrievalSupportParameter wParam = (RetrievalSupportParameter) checkParam;

			//#CM725355
			// Set the search conditions. 
			//#CM725356
			// Consignor Code
			wKey.setConsignorCode(wParam.getConsignorCode());
			//#CM725357
			// Planned Picking Date
			wKey.setPlanDate(wParam.getRetrievalPlanDate());
			//#CM725358
			// Item Code
			wKey.setItemCode(wParam.getItemCode());
			//#CM725359
			// Case/Piece division
			wKey.setCasePieceFlag(wParam.getCasePieceflg());
			//#CM725360
			// Picking Location 
			if (wParam.getCasePieceflg().equals(RetrievalPlan.CASEPIECE_FLAG_CASE))
			{
				wKey.setCaseLocation(wParam.getRetrievalLocation());
			}
			else
			{
				wKey.setPieceLocation(wParam.getRetrievalLocation());
			}
			//#CM725361
			// Order No.
			if (wParam.getCasePieceflg().equals(RetrievalPlan.CASEPIECE_FLAG_CASE))
			{
				wKey.setCaseOrderNo(wParam.getOrderNo());
			}
			else
			{
				wKey.setPieceOrderNo(wParam.getOrderNo());
			}
			
			//#CM725362
			// Status flag (Standby-other than Deleted) 
			wKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART, "!=");
			wKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
			
			//#CM725363
			// Obtain the count of the search result of the Picking Plan Info. 
			if (wObj.count(wKey) > 0)
			{
				return false;
			}
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}

		return true;
	}

	//#CM725364
	/**
	 * Allow this method to determine whether the Status flag is Standby or not. 
	 * @param status Status flag
	 * @param rowNo  Line No
	 * @return Result of check  (Standby: true, any status other than Standby: false) 
	 */
	protected boolean checkStatus(String status, int rowNo)
	{
		boolean registFlag = false;
		
		//#CM725365
		// For data with "Working": 
		if (status.equals(RetrievalPlan.STATUS_FLAG_NOWWORKING))
		{
			//#CM725366
			// 6023273=No.{0}{1}
			//#CM725367
			// 6023269=You cannot enter the data. The same data in process of work exists. 
			wMessage = "6023273" + wDelim + rowNo + wDelim + MessageResource.getMessage("6023269");
			registFlag = false;
		}
		//#CM725368
		// For data with status "Partially Received": 
		if (status.equals(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART))
		{
			//#CM725369
			// 6023273=No.{0}{1}
			//#CM725370
			// 6023270=You cannot enter the data. The same data which is Partly Received exists. 
			wMessage = "6023273" + wDelim + rowNo + wDelim + MessageResource.getMessage("6023270");
			registFlag = false;
		}
		//#CM725371
		// For data with "Completed" or "Started": 
		if (status.equals(RetrievalPlan.STATUS_FLAG_COMPLETION)
		|| status.equals(RetrievalPlan.STATUS_FLAG_START))
		{
			//#CM725372
			// 6023273=No.{0}{1}
			//#CM725373
			// 6023090=The data already exists. Unable to input. 
			wMessage = "6023273" + wDelim + rowNo + wDelim + MessageResource.getMessage("6023090");
			registFlag = false;
		}	
		//#CM725374
		// For data with status "Standby": 
		if (status.equals(RetrievalPlan.STATUS_FLAG_UNSTART))
		{
			registFlag = true;
		}
		return registFlag;
	}
	
}
//#CM725375
//end of class
