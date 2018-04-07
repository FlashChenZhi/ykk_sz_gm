package jp.co.daifuku.wms.retrieval.schedule;

//#CM723404
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
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
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.report.RetrievalItemWriter;

//#CM723405
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this class to execute the process for starting Item Picking. <BR>
 * Receive the contents entered via screen as a parameter, and execute the process to start the Picking. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * <B>1. Process for Initial Display (<CODE>initFind()</CODE> method) </B><BR>
 * <DIR>
 *   For Work Status Info with only one Consignor Code exists, return the corresponding Consignor Code. <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR>
 * <BR>
 *   <Search conditions>
 *   <DIR>
 *     Work type: Picking<BR>
 *     Status flag: Standby <BR>
 *     Order No.: Blank <BR>
 *   </DIR>
 * </DIR>
 * 
 * <B>2. Process by clicking "Display" button (<CODE>query()</CODE> method) </B><BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter, and
 *   obtain the data for output to the preset area from the Work Status Info table and return it. <BR>
 *   If no corresponding data is found, return the Parameter array with number of elements equal to 0. 
 *   Return null when condition error occurs. <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
 * <BR>
 *   <Search conditions>
 *   <DIR>
 *     Work type: Picking<BR>
 *     Status flag: Standby <BR>
 *     Order No.: Blank <BR>
 *   </DIR>
 *   <BR>
 *   <Parameter> *Mandatory Input
 *   <DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Consignor Code* <BR>
 *     Planned Picking Date* <BR>
 *     Item Code <BR>
 *     Case/Piece division  <BR>
 *   </DIR>
 *   <BR>
 *   <Returned data>
 *   <DIR>
 *     Consignor Code <BR>
 *     Consignor Name (with the latest Added date)  <BR>
 *     Planned Picking Date <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Case/Piece division (String for display)  <BR>
 *     Total Picking qty  <BR>
 *     Packed Qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Planned Work Case Qty <BR>
 *     Planned Work Piece Qty <BR>
 *     Picking Location  <BR>
 *     Expiry Date <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Work No. <BR>
 *     Last update date/time <BR>
 *   </DIR>
 *   <BR>
 *   <Process Content> 
 *   <DIR>
 *     1.Check the Worker Code and Password. <BR>
 *     2. Obtain the count of displayed data <BR>
 *     3. Require that the target data exists. <BR>
 *     4. Count of displayed data shall be less than Max count of data acceptable to display. <BR>
 *     5. Obtain the data to be displayed <BR>
 *     6.Obtain the Consignor Name with the latest Added Date/Time.<BR>
 *     7.Set the Returned data.<BR>
 *   </DIR>
 * </DIR>
 * 
 * 
 * <B>3. Process by clicking on "Start Picking" button (<CODE>startSCHgetParams()</CODE> method) </B><BR>
 * <BR>
 * <DIR>
 *   Receive the contents displayed in the preset area, as a parameter, and execute the process to start the Picking.  <BR>
 *   Re-obtain the data to be output to the preset area from database when the process normally completed, and return it.  <BR>
 *   Return null when the schedule failed to complete due to condition error or other causes.  <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error  <BR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR>
 *   <DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Consignor Code* (for search through Preset again)  <BR>
 *     Planned Picking Date* (for search through Preset again)  <BR>
 *     Item Code (for search through Preset again)  <BR>
 *     Case/Piece division * (for search through Preset again)  <BR>
 *     Work No.* <BR>
 *     Last update date/time* <BR>
 *     Preset line No. * <BR>
 *     Terminal No.* <BR>
 *     Options of Print Report type * <BR>
 *   </DIR>
 *   <BR>
 *   <Process Content> 
 *   <DIR>
 *     1. Require not to be in the process of daily update. <BR>
 *     2.Check the Worker Code and Password. <BR>
 *     3. Ensure that the Work Status Info table of work No. exist in the database.  <BR>
 *     4. Execute Update process per data in the preset. <BR>
 *     <DIR>
 *       1.Require the parameter's Last update date/time to be consistent with the Last update date/time in the Work Status Info table (Check Exclusion.).  <BR>
 *       2.Update Work Status Info.
 *       <DIR>
 *         - Status flag: "Started" <BR>
 *         - Last update date/time<BR>
 *         - Last update process name<BR>
 *       </DIR>
 *       3.Update the Picking Plan Info.
 *       <DIR>
 *         - Status flag: "Started" <BR>
 *         - Last update date/time<BR>
 *         - Last update process name<BR>
 *       </DIR>
 *     </DIR>
 *     5. Re-obtaining of the data to be displayed <BR>
 *     6.If Print Work Report ON is selected, execute the Print process. <BR>
 *       <Print Parameter> 
 *       <DIR>
 *         - Consignor Code<BR>
 *         - Planned Picking Date<BR>
 *         - Case/Piece division <BR>
 *       </DIR>
 *   </DIR>
 * </DIR>
 * 
 * 
 * <BR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:56 $
 * @author  $Author: suresh $
 */
public class RetrievalItemStartSCH extends AbstractRetrievalSCH
{

	//#CM723406
	// Class variables -----------------------------------------------
	//#CM723407
	/**
	 * Process name 
	 */
	public static final String wProcessName = "RetrievalItemStartSCH";

	//#CM723408
	// Class method --------------------------------------------------
	//#CM723409
	/**
	 * Return the version of this class.
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:56 $");
	}
	//#CM723410
	// Constructors --------------------------------------------------
	//#CM723411
	/**
	 * Initialize this class. 
	 */
	public RetrievalItemStartSCH()
	{
		wMessage = null;
	}

	//#CM723412
	// Public methods ------------------------------------------------
	//#CM723413
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * For Work Status Info with only one Consignor Code exists, return the corresponding Consignor Code. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for searchParam.  <BR>
	 * <BR>
	 * 
	 * @param conn Connection Database connection object
	 * @param searchParam Parameter Class that inherits <CODE>Parameter</CODE> class with search conditions. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 * @return Class that implements the <CODE>Parameter</CODE> interface that contains the search result. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
		RetrievalSupportParameter param = new RetrievalSupportParameter();

		try
		{
			//#CM723414
			// Work type: Picking
			workInfoSearchKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			//#CM723415
			// Status flag: Standby 
			workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			//#CM723416
			// Order No. is the blank one. 
			workInfoSearchKey.setOrderNo("", "");
			//#CM723417
			// Use consignor code as GROUP BY condition. 
			workInfoSearchKey.setConsignorCodeGroup(1);
			workInfoSearchKey.setConsignorCodeCollect("");

			if (workInfoHandler.count(workInfoSearchKey) == 1)
			{
				//#CM723418
				// Search for the Consignor Code. 
				WorkingInformation workInfo = (WorkingInformation) workInfoHandler.findPrimary(workInfoSearchKey);

				//#CM723419
				// Set the search result for the return value. 
				param.setConsignorCode(workInfo.getConsignorCode());
			}
		}
		catch (NoPrimaryException pe)
		{
			return param;
		}
		return param;

	}

	//#CM723420
	/**
	 * Receive the contents entered via screen as a parameter, and
	 * obtain the data for output to the preset area from the Work Status Info and return it. <BR>
	 * 
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * 
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0. <BR>
	 *          If the number of search results exceeds the defined number or error occurs with the input condition, return null. <BR>
	 *          Returning array with number of elements 0 (zero) or null allows to obtain the error contents as a message using <CODE>getMessage()</CODE> method. 
	 * 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		//#CM723421
		// Input data 
		RetrievalSupportParameter param = null;

		param = (RetrievalSupportParameter) searchParam;
		
		//#CM723422
		// Check the Worker code and password 
		if (!checkWorker(conn, param))
		{
			return null;
		}

		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey workKey = new WorkingInformationSearchKey();
		WorkingInformation[] workInfo = null;

		//#CM723423
		// Set the search conditions.
		//#CM723424
		// Work type: Picking
		workKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM723425
		// Work Status: Standby 
		workKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		//#CM723426
		// Order No. is the blank one. 
		workKey.setOrderNo("", "");
		//#CM723427
		// Condition to input via screen (** Disable to set the search key for data Case/Piece division "All") 
		workKey.setConsignorCode(param.getConsignorCode());
		workKey.setPlanDate(param.getRetrievalPlanDate());
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			workKey.setItemCode(param.getItemCode());
		}
		if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
		{
			workKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
		}
		else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			workKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
		}
		else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			workKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
		}
		//#CM723428
		// Sorting order: Item Code, Item Name, and then Case/Piece division 
		workKey.setItemCodeOrder(1, true);
		workKey.setItemName1Order(2, true);
		workKey.setWorkFormFlagOrder(3, true);

		//#CM723429
		// Check the Count of displayed data. 
		if(!canLowerDisplay(workInfoHandler.count(workKey)))
		{
			return returnNoDisplayParameter();
		}
				
		//#CM723430
		// Obtain the instance from the Work Status Info. 
		workInfo = (WorkingInformation[]) workInfoHandler.find(workKey);

		if (workInfo.length <= 0)
		{
			//#CM723431
			// No target data was found. 
			wMessage = "6003018";
			return new RetrievalSupportParameter[0];
		}

		//#CM723432
		// Obtain the Consignor Name with later Added Date/Time. 
		Date tempDate = null;
		String consignorName = "";
		for (int i = 0; i < workInfo.length; i++)
		{
			if (i == 0)
			{
				tempDate = workInfo[i].getRegistDate();
				consignorName = workInfo[i].getConsignorName();
			}
			else
			{
				if (workInfo[i].getRegistDate().compareTo(tempDate) > 0)
				{
					tempDate = workInfo[i].getRegistDate();
					consignorName = workInfo[i].getConsignorName();
				}
			}
		}

		//#CM723433
		// Set the search result for the return parameter. 
		//#CM723434
		// Returned data 
		RetrievalSupportParameter[] resultArray = null;
		Vector resultVec = new Vector();
		for (int i = 0; i < workInfo.length; i++)
		{
			RetrievalSupportParameter dispParam = new RetrievalSupportParameter();

			dispParam.setConsignorCode(workInfo[i].getConsignorCode());
			dispParam.setConsignorName(consignorName);
			dispParam.setRetrievalPlanDate(workInfo[i].getPlanDate());
			dispParam.setItemCode(workInfo[i].getItemCode());
			dispParam.setItemName(workInfo[i].getItemName1());
			dispParam.setCasePieceflgName(DisplayUtil.getPieceCaseValue(workInfo[i].getWorkFormFlag()));
			dispParam.setTotalPlanQty(workInfo[i].getPlanEnableQty());
			dispParam.setEnteringQty(workInfo[i].getEnteringQty());
			dispParam.setBundleEnteringQty(workInfo[i].getBundleEnteringQty());
			//#CM723435
			// Describe depending on the Case/Piece division.
			if(workInfo[i].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_PIECE))
			{
				//#CM723436
				// For data with division Piece, represent all the total Planned qty in piece.
				dispParam.setPlanCaseQty(0);
				dispParam.setPlanPieceQty(workInfo[i].getPlanEnableQty());
			}
			else
			{
				//#CM723437
				// For data with division other than Piece, calculate each planned qty by converting it into the packed qty.
				dispParam.setPlanCaseQty(DisplayUtil.getCaseQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty()));
				dispParam.setPlanPieceQty(DisplayUtil.getPieceQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty()));
			}
			dispParam.setRetrievalLocation(workInfo[i].getLocationNo());
			dispParam.setUseByDate(workInfo[i].getUseByDate());
			dispParam.setITF(workInfo[i].getItf());
			dispParam.setBundleITF(workInfo[i].getBundleItf());
			dispParam.setJobNo(workInfo[i].getJobNo());
			dispParam.setLastUpdateDate(workInfo[i].getLastUpdateDate());

			resultVec.add(dispParam);
		}
		resultArray = new RetrievalSupportParameter[resultVec.size()];
		resultVec.copyInto(resultArray);

		//#CM723438
		// 6001013 = Data is shown. 
		wMessage = "6001013";

		return resultArray;
	}
	

	//#CM723439
	/**
	 * Execute the process for Execute the process for starting Item Picking. <BR>
	 * Receive the contents displayed in the preset area, as a parameter, and execute the process to start the Item Picking.  <BR>
	 * Re-obtain the data to be output to the preset area from database when the process normally completed, and return it.  <BR>
	 * Return null when the schedule failed to complete due to condition error or other causes.  <BR>
	 * Use <CODE>getMessage()</CODE> method to refer to the content of the error  <BR>
	 * 
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 *         Designating any instance other than RetrievalSupportParameter instance throws <CODE>ScheduleException</CODE>. <BR>
	 *         Use <CODE>getMessage()</CODE> method to refer to the content of the error 
	 * 
	 * @return Result of repeated search if completed normally, or null if failed to complete normally. 
	 * 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		//#CM723440
		// Check the Daily Update Processing. 
		if (isDailyUpdate(conn))
		{
			return null;
		}	
		//#CM723441
		// Content of update 
		RetrievalSupportParameter[] inputParam = (RetrievalSupportParameter[]) startParams;

		//#CM723442
		// Check the Worker code and password 
		if (!checkWorker(conn, inputParam[0]))
		{
			return null;
		}

		//#CM723443
		// Check for exclusion of all the target data. 
		if (!lockAll(conn, startParams))
		{
			//#CM723444
			// 6003006=Unable to process this data. It has been updated via other work station. 
			wMessage = "6003006";
			return null;
		}
			
		//#CM723445
		// Maintain the Work No. which has been set. (for printing) 
		Vector jobNoVec = new Vector();
		for(int i = 0; i < inputParam.length; i++)
		{
			//#CM723446
			// Check Exclusion. 
			if (!lock(conn, inputParam[i]))
			{
				return null;
			}
			//#CM723447
			// Update Work Status Info.
			if (!updateWorkinginfo(conn, inputParam[i].getJobNo()))
			{
				return null;
			}
			//#CM723448
			// Update the Picking Plan Info.
			if (!updateRetrievalPlan(conn, inputParam[i].getJobNo()))
			{
				return null;
			}
			
			jobNoVec.addElement(inputParam[i].getJobNo());

		}
			
		String[] jobNoArray = new String[jobNoVec.size()];
		jobNoVec.copyInto(jobNoArray);

		//#CM723449
		// Obtain the data to be displayed again. 
		RetrievalSupportParameter[] resultArray = (RetrievalSupportParameter[]) this.query(conn, inputParam[0]);

		//#CM723450
		// 6021010=Data was committed. 
		wMessage = "6021010";

		//#CM723451
		// To print the Work report: 
		if (inputParam[0].getRetrievalListFlg())
		{
			startPrint(conn, jobNoArray, inputParam[0].getCasePieceflg());
		}

		//#CM723452
		// Return the data to be displayed again. 
		return resultArray;
	}

	//#CM723453
	/**
	 * Receive the contents entered via screen as a parameter, and
	 * execute input check and exclusion check in the preset area. <BR>
	 * Lock the target data together. 
	 * 
	 * @param conn Instance to maintain database connection. 
	 * @param checkParam RetrievalSupportParameter class instance with contents that were input via screen. 
	 * @return Return true if lock target exists, or return false if not. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected boolean lock(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{

		//#CM723454
		// Generate instance of Work Status Info handlers. 
		WorkingInformationHandler winfoHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey winfoKey = new WorkingInformationSearchKey();
		WorkingInformation workInfo = null;
		try
		{
			RetrievalSupportParameter param = (RetrievalSupportParameter) checkParam;

			//#CM723455
			// Set the search conditions. 
			winfoKey.KeyClear();
			//#CM723456
			// Work No.
			winfoKey.setJobNo(param.getJobNo());
			//#CM723457
			// Obtain the search result of the Work Status Info and lock it. 
			workInfo = (WorkingInformation) winfoHandler.findPrimaryForUpdate(winfoKey);

			if (workInfo == null)
			{
				//#CM723458
				// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + param.getRowNo();
				return false;
			}
			//#CM723459
			// Data with status flag other than Standby (due to loading or maintenace executed which may cause to change the status) 
			if (!(workInfo.getStatusFlag()).equals(WorkingInformation.STATUS_FLAG_UNSTART))
			{
				//#CM723460
				// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + param.getRowNo();
				return false;
			}

			//#CM723461
			// Check Exclusion. 
			if (!param.getLastUpdateDate().equals(workInfo.getLastUpdateDate()))
			{
				//#CM723462
				// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + param.getRowNo();
				return false;
			}
		}		
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		return true;
	}

	//#CM723463
	/**
	 * Update the Work Status Info table. 
	 * @param conn Database connection object
	 * @param jobno      Work No.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 * @return Return true if the schedule process completed normally, or return false if failed. 
	 */
	protected boolean updateWorkinginfo(Connection conn, String jobno) throws ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
			WorkingInformationAlterKey alterKey = new WorkingInformationAlterKey();
			
			//#CM723464
			// Set the update condition. 
			alterKey.setJobNo(jobno);
			//#CM723465
			// "Started" 
			alterKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_START);
			alterKey.updateLastUpdatePname(wProcessName);
			//#CM723466
			// Update the data. 
			workingHandler.modify(alterKey);
			
			return true;
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM723467
	/**
	 * Update the work status in the Picking Plan Info table to Started. <BR>
	 * If the work has been started, disable to change it. <BR>
	 * @param conn Database connection object<BR>
	 * @param jobno     Work No.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 * @return Return true if the schedule process completed normally, or return false if failed. 
	 */
	protected boolean updateRetrievalPlan(Connection conn, String jobno) throws ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey workingsearchKey = new WorkingInformationSearchKey();

			//#CM723468
			// Search Data 
			//#CM723469
			// Work No.
			workingsearchKey.setJobNo(jobno);
			WorkingInformation working = (WorkingInformation)workingHandler.findPrimary(workingsearchKey);
			
			if (working != null)
			{
				String planukey = working.getPlanUkey();
				RetrievalPlanHandler retHandler = new RetrievalPlanHandler(conn);
				RetrievalPlanSearchKey retsearchKey = new RetrievalPlanSearchKey();
				RetrievalPlanAlterKey alterKey = new RetrievalPlanAlterKey();
				//#CM723470
				// Picking Plan unique key
				retsearchKey.setRetrievalPlanUkey(planukey);
				RetrievalPlan retPlan = (RetrievalPlan)retHandler.findPrimaryForUpdate(retsearchKey);
				if (retPlan != null)
				{
					if (retPlan.getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_UNSTART)
					|| retPlan.getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART))
					{
						alterKey.setRetrievalPlanUkey(planukey);
						alterKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_START);
						alterKey.updateLastUpdatePname(wProcessName);
						//#CM723471
						// Update Data. 
						retHandler.modify(alterKey);
					}
				}
				else
				{
					//#CM723472
					// 6006040=Data mismatch occurred. See log. {0} 
					RmiMsgLogClient.write("6006040" + wDelim + planukey, this.getClass().getName());
					//#CM723473
					// Throw ScheduleException here. (Not required to set any error message) 
					throw (new ScheduleException());
				}
				return true;
			}
			else
			{
				//#CM723474
				// 6006040=Data mismatch occurred. See log. {0} 
				RmiMsgLogClient.write("6006040" + wDelim + jobno, this.getClass().getName());
				//#CM723475
				// Throw ScheduleException here. (Not required to set any error message) 
				throw (new ScheduleException());
			}
		}
		catch (NotFoundException e)
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
	}

	//#CM723476
	/**
	 * Pass the Work No. to the class to print the Item Picking report.<BR>
	 * Disables to print if no print data. <BR>
	 * Receive true from the Item Picking report print process class if succeeded in printing, or receive false when failed, and<BR>
	 * return the result. <BR>
	 * Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
	 * @param  conn Connection Database connection object
	 * @param  jobNoArray Work No. (Array) 
	 * @param  casepieceflag Case Piece flag
	 */
	protected void startPrint(Connection conn, String[] jobNoArray, String casePieceFlag)
	{
		try
		{
			RetrievalItemWriter writer = new RetrievalItemWriter(conn);
			writer.setJobNo(jobNoArray);
			writer.setCasePieceFlag(casePieceFlag);

			//#CM723477
			// Start the printing process. 
			if (writer.startPrint())
			{
				//#CM723478
				// 6021012=Data had been set and the list was printed successfully. 
				wMessage = "6021012";
			}
			else
			{
				//#CM723479
				// 6007042=Printing failed after setup. Please refer to log. 
				wMessage = "6007042";
			}
		}
		catch (Exception e)
		{
			//#CM723480
			// 6007042=Printing failed after setup. Please refer to log. 
			wMessage = "6007042";
		}
	}
}
//#CM723481
//end of class
