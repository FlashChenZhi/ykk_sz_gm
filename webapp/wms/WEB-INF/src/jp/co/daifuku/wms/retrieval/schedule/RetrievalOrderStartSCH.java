//#CM724309
//$Id: RetrievalOrderStartSCH.java,v 1.3 2007/02/07 04:20:00 suresh Exp $

//#CM724310
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.retrieval.schedule;

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
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.report.RetrievalOrderWriter;

//#CM724311
/**
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue <BR>
 * <BR>
 * Allow this class to start Order Picking. <BR>
 * Receive the contents entered via screen as a parameter, and execute the process to start the Order Picking. <BR>
 * Allow each methods contained in this class to receive a connection object and executes the process for updating the database, but<BR>
 * disable to commit nor roll-back the transaction. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind()</CODE> method) <BR> 
 * <BR>
 * <DIR>
 *   If only one Consignor Code exists in the Work Status Info (Order No.: Added, Work type: Picking, and Status flag: Standby), return the corresponding Consignor Code, <BR> 
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR> 
 *   <BR>
 *   [search conditions]  <BR> 
 *   <DIR>
 *     Order No.: Exists <BR>
 *     Work type: Picking<BR>
 *     Status flag: Standby <BR> 
 *   </DIR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Display" button (<CODE>query()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it. <BR>
 *   If no corresponding data is found, return <CODE>Parameter</CODE> array with number of elements equal to 0. Return null when condition error occurs. <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
 *   Search through the Work Status Info table. (DNWORKINFO) <BR>
 *   Disable to display if the search target count exceeds 1000 (MAX_NUMBER_OF_DISP defined in WMSParam). <BR>
 *   <BR>
 *   [search conditions]  <BR> 
 *   <DIR>
 *     For data with Status flag "Standby" <BR> 
 *     Data with work type "Picking" <BR>
 *     Order No.: other than NULL <BR>
 *   </DIR>
 *   <BR>
 *   [Parameter]  *Mandatory Input<BR>
 *   <DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Consignor Code* <BR>
 *     Planned Picking Date* <BR>
 *     Order No. <BR>
 *     Case/Piece division <BR>
 *   </DIR>
 *   <BR>
 *   [Sorting condition ] <BR>
 *   <DIR>
 *     Consignor Code<BR>
 *     Planned Picking Date<BR>
 *     Order No.<BR>
 *     Division <BR>
 *     Work No.<BR>
 *   </DIR>
 *   <BR> 
 *   [Returned data]  +Aggregation Conditions<BR>
 *   <DIR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Planned Picking Date <BR>
 *     Order No. +<BR>
 *     Case/Piece division (Work Type)  +<BR>
 *     Display the Customer Name (data with the latest data of the aggregated data) <BR>
 *     Last update date/time <BR>
 *     Work No.<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 3. Process by clicking "Add" button (<CODE>startSCHgetParams()</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *   Receive the contents displayed in the preset area, as a parameter, and execute the process to start the Order Picking.  <BR>
 *   If the Report Print Type of the parameter is true upon completion of the process, boot the class to print Order Picking report. <BR>
 *   Return true when the process normally completed, or return false when the schedule failed to complete due to condition error or other causes.  <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error  <BR>
 *   <BR>
 *   [Parameter]  *Mandatory Input<BR>
 *   <DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Consignor Code <BR>
 *     Planned Picking Date <BR>
 *     Order No. <BR>
 *     Case/Piece division name (Work Type) <BR>
 *     Last update date/time <BR>
 *     Work No.<BR>
 *     Print type of Order Picking report <BR>
 *     Line No. <BR>
 *   </DIR>
 *   <BR>
 *   Update the table in the order of Work Status Info and Picking Plan Info to prevent from dead-locking. <BR>
 *   <BR>
 *   - Update the Work Status Info Table (DNWORKINFO) <BR> 
 *   <DIR>
 *   1.Update the Status flag of the Work Status Info to Started. <BR>
 *   2. Update the last Update process name. <BR>
 *   3.Update the latest update date/time. <BR>
 *   </DIR>
 *   <BR>
 *   -Update the Picking Plan Info table (DNRETRIEVALPLAN).  <BR>
 *   <DIR>
 *   1.Update the Update the status flag of Picking Plan Info. Disable to update its data with status flag Partially Completed or Started only. <BR>
 *   2. Update the last Update process name. <BR> 
 *   3.Update the latest update date/time. <BR>
 *   </DIR>
 *   <BR>
 *   [Print Process (if selected to Print Work Report ON)]  <BR>
 *   <DIR>
 *   Pass the Work No, Search conditions, and Case/Piece division to the class to print the Order Picking report.<BR>
 *	 Disables to print if no print data. <BR>
 *   </DIR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/18</TD><TD>M.Inoue</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:20:00 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderStartSCH extends AbstractRetrievalSCH
{
	//#CM724312
	// Class variables -----------------------------------------------
	//#CM724313
	/**
	 * Class Name (Start Order Picking) 
	 */
	public static String PROCESSNAME = "RetrievalOrderStartSCH";

	//#CM724314
	// Class method --------------------------------------------------
	//#CM724315
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:20:00 $");
	}
	
	//#CM724316
	// Constructors --------------------------------------------------
	//#CM724317
	/**
	 * Initialize this class. 
	 */
	public RetrievalOrderStartSCH()
	{
		wMessage = null;
	}

	//#CM724318
	// Public methods ------------------------------------------------

	//#CM724319
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * For Work Status Info with only one Consignor Code exists, return the corresponding Consignor Code. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.  
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. 
	 * @param conn Database connection object
	 * @param searchParam Set null because of no use. <BR>
	 * @return Class that implements the <CODE>Parameter</CODE> interface that contains the search result. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		//#CM724320
		// Search Data 
		//#CM724321
		// Work type (Picking) 
		searchKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM724322
		// Status flag (Standby) 
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		//#CM724323
		// Data with Order No. input
		searchKey.setOrderNo("", "IS NOT NULL");
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		RetrievalSupportParameter dispData = new RetrievalSupportParameter();
		if (workingHandler.count(searchKey) == 1)
		{
			try
			{
				WorkingInformation working = (WorkingInformation)workingHandler.findPrimary(searchKey);
				dispData.setConsignorCode(working.getConsignorCode());
			}
			catch (NoPrimaryException e)
			{
				return new RetrievalSupportParameter();
			}
		}
		return dispData;
	}

	//#CM724324
	/**
	 * Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it. 
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0. <BR>
	 *          Return null if the search result count exceeds 1000 or when error occurs with input condition. <BR>
	 *          Returning array with number of elements 0 (zero) or null allows to obtain the error contents as a message using <CODE>getMessage()</CODE> method. <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		RetrievalSupportParameter param = (RetrievalSupportParameter)searchParam;
		
		//#CM724325
		// Check the Worker code and password 
		if (!checkWorker(conn, param))
		{
			 return null;
		}
		//#CM724326
		// Check for mandatory input. 
		if (StringUtil.isBlank(param.getWorkerCode())
		||	StringUtil.isBlank(param.getPassword())
		||	StringUtil.isBlank(param.getConsignorCode())
		||	StringUtil.isBlank(param.getRetrievalPlanDate()))
		{
			throw (new ScheduleException("mandatory error!"));
		}
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey countSearchKey = new WorkingInformationSearchKey();

		//#CM724327
		// Search Data 
		//#CM724328
		// Work type (Picking) 
		searchKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		countSearchKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM724329
		// Status flag (Standby) 
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		countSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		//#CM724330
		// Consignor Code
		searchKey.setConsignorCode(param.getConsignorCode());
		countSearchKey.setConsignorCode(param.getConsignorCode());
		//#CM724331
		// Planned Picking Date
		searchKey.setPlanDate(param.getRetrievalPlanDate());
		countSearchKey.setPlanDate(param.getRetrievalPlanDate());
		//#CM724332
		// Order No.
		String orderno = param.getOrderNo();
		if (!StringUtil.isBlank(orderno))
		{
			searchKey.setOrderNo(orderno);
			countSearchKey.setOrderNo(orderno);
		}
		else
		{
			//#CM724333
			// Data with Order No. input
			searchKey.setOrderNo("", "IS NOT NULL");
			countSearchKey.setOrderNo("", "IS NOT NULL");
		}
		
		//#CM724334
		// Case/Piece division (Work Type) 
		//#CM724335
		// Obtain either one of All, Case, Piece, or None from parameter.
		if (!param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
		{
			//#CM724336
			// Case 
			if(param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
				countSearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			//#CM724337
			// Piece 
			else if(param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
				countSearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			}
			//#CM724338
			// None 
			else if(param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				searchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
				countSearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			}
		}
		
		//#CM724339
		// Sort in the ascending order of Order No., Case/Piece division, Customer Code, Added Date/Time, and Work No. 
		searchKey.setOrderNoOrder(1, true);
		searchKey.setWorkFormFlagOrder(2, true);
		searchKey.setCustomerCodeOrder(3, true);
		searchKey.setRegistDateOrder(4, false);
		searchKey.setJobNoOrder(5, true);
		
		//#CM724340
		// Set the aggregation. 
		countSearchKey.setOrderNoGroup(1);
		countSearchKey.setOrderingDateCollect("");
		countSearchKey.setWorkFormFlagGroup(2);
		countSearchKey.setWorkFormFlagCollect("");
		countSearchKey.setCustomerCodeGroup(3);
		countSearchKey.setCustomerCodeCollect("");
		
		if(!canLowerDisplay(workingHandler.count(countSearchKey)))
		{
			return returnNoDisplayParameter();
		}
						
		//#CM724341
		// This screen is intended for aggregated display. Therefore, not only using the count of WMSParam displayed data, 
		//#CM724342
		// also using the Max count of WMSParam SQL search data, execute check. 
		int selectCount = workingHandler.count(searchKey);
		if (selectCount > WmsParam.MAX_NUMBER_OF_SQL_FIND)
		{
			//#CM724343
			// 6023469=Search resulted in {0} work. Narrow down search since it exceeds {1} 
			String msg = "6023469" 
					+ wDelim + WmsFormatter.getNumFormat(selectCount)
					+ wDelim + MAX_NUMBER_OF_SQL_FIND_DISP;
			wMessage = msg;
			return null;
		}

		WorkingInformation[] resultEntity = (WorkingInformation[])workingHandler.find(searchKey);
		if (resultEntity.length <= 0)
		{
			//#CM724344
			// No target data was found. 
			wMessage = "6003018";
			return new RetrievalSupportParameter[0];
		}
		//#CM724345
		// Search for Consignor Name with later Added Date/Time 
		Date tempDate = null;
		String consignorName = "";
		for (int i = 0; i < resultEntity.length; i++)
		{
			if (i == 0)
			{
				tempDate = resultEntity[i].getRegistDate();
				consignorName = resultEntity[i].getConsignorName();
			}
			else
			{
				if (resultEntity[i].getRegistDate().compareTo(tempDate) > 0)
				{
					tempDate = resultEntity[i].getRegistDate();
					consignorName = resultEntity[i].getConsignorName();
				}
			}
		}

		Vector vec = new Vector();
		String tempOrderNo = "";
		String tempWorkFormFlag = "";
		String tempCustomerCode = "";
		Vector jobnoVec = new Vector();
		Vector lastUpdateVec = new Vector();
		int i = 0;
		for (; i < resultEntity.length; i++)
		{

			if (i == 0)
			{
				tempOrderNo = resultEntity[i].getOrderNo();
				tempWorkFormFlag = resultEntity[i].getWorkFormFlag();
				tempCustomerCode = resultEntity[i].getCustomerCode();
			}
			if (!tempOrderNo.equals(resultEntity[i].getOrderNo())
			||	!tempWorkFormFlag.equals(resultEntity[i].getWorkFormFlag())
			||	!tempCustomerCode.equals(resultEntity[i].getCustomerCode())
			)
			{
				RetrievalSupportParameter dispData = new RetrievalSupportParameter();
				//#CM724346
				// Consignor Code
				dispData.setConsignorCode(resultEntity[i-1].getConsignorCode());
				//#CM724347
				// Consignor Name (Consignor Name with later Added Date/Time) 
				dispData.setConsignorName(consignorName);
				//#CM724348
				// Planned Picking Date
				dispData.setRetrievalPlanDate(resultEntity[i-1].getPlanDate());
				//#CM724349
				// Order No.
				dispData.setOrderNo(resultEntity[i-1].getOrderNo());
				//#CM724350
				// Division (Name) 
				dispData.setCasePieceflgName(DisplayUtil.getPieceCaseValue(resultEntity[i-1].getWorkFormFlag()));
				//#CM724351
				// Customer Code
				dispData.setCustomerCode(resultEntity[i-1].getCustomerCode());
				//#CM724352
				// Customer Name
				dispData.setCustomerName(resultEntity[i-1].getCustomerName1());
				//#CM724353
				// Work No.
				Vector cpyJobNo = new Vector();
				cpyJobNo = (Vector)jobnoVec.clone();
				dispData.setJobNoList(cpyJobNo);
				//#CM724354
				// Last update date/time
				Vector cpyLUDate = new Vector();
				cpyLUDate = (Vector)lastUpdateVec.clone();
				dispData.setLastUpdateDateList(cpyLUDate);

				vec.addElement(dispData);
				jobnoVec.clear();
				lastUpdateVec.clear();
			}
			jobnoVec.addElement(resultEntity[i].getJobNo());
			lastUpdateVec.addElement(resultEntity[i].getLastUpdateDate());
			tempOrderNo = resultEntity[i].getOrderNo();
			tempWorkFormFlag = resultEntity[i].getWorkFormFlag();
			tempCustomerCode = resultEntity[i].getCustomerCode();
		}
		RetrievalSupportParameter dispData = new RetrievalSupportParameter();
		//#CM724355
		// Consignor Code
		dispData.setConsignorCode(resultEntity[i-1].getConsignorCode());
		//#CM724356
		// Consignor Name (Consignor Name with later Added Date/Time) 
		dispData.setConsignorName(consignorName);
		//#CM724357
		// Planned Picking Date
		dispData.setRetrievalPlanDate(resultEntity[i-1].getPlanDate());
		//#CM724358
		// Order No.
		dispData.setOrderNo(resultEntity[i-1].getOrderNo());
		//#CM724359
		// Division (Name) 
		dispData.setCasePieceflgName(DisplayUtil.getPieceCaseValue(resultEntity[i-1].getWorkFormFlag()));
		//#CM724360
		// Customer Code
		dispData.setCustomerCode(resultEntity[i-1].getCustomerCode());
		//#CM724361
		// Customer Name
		dispData.setCustomerName(resultEntity[i-1].getCustomerName1());
		//#CM724362
		// Work No.
		Vector cpyJobNo = new Vector();
		cpyJobNo = (Vector)jobnoVec.clone();
		dispData.setJobNoList(cpyJobNo);
		//#CM724363
		// Last update date/time
		Vector cpyLUDate = new Vector();
		cpyLUDate = (Vector)lastUpdateVec.clone();
		dispData.setLastUpdateDateList(cpyLUDate);
		jobnoVec.clear();
		lastUpdateVec.clear();
		vec.addElement(dispData);
		
		
		RetrievalSupportParameter[] paramArray = new RetrievalSupportParameter[vec.size()];
		vec.copyInto(paramArray);

		//#CM724364
		// 6001013 = Data is shown. 
		wMessage = "6001013";
		return paramArray;
	}
	
	//#CM724365
	/**
	 * Receive the contents entered via screen as a parameter, and start the schedule to start the Order Picking. <BR>
	 * Assume that two or more data may be input, including setting via preset area. So, require the parameter to receive them as an array. <BR>
	 * Return true if the schedule normally completed, or return false if failed. 
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 *         Use <CODE>getMessage()</CODE> method to refer to the content of the error 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter[] wParam = (RetrievalSupportParameter[])startParams;
		//#CM724366
		// Check the Worker code and password 
		if (!checkWorker(conn, wParam[0]))
		{
			return null;
		}
		//#CM724367
		// Check the Daily Update Processing. 
		if (isDailyUpdate(conn))
		{
			return null;
		}	
		
		Vector jobnoVec = new Vector();

		//#CM724368
		// Check for exclusion of all the target data. 
		if (!lockSummarilyAll(conn, startParams))
		{
			//#CM724369
			// 6003006=Unable to process this data. It has been updated via other work station. 
			wMessage = "6003006";
			return null;
		}

		for (int i = 0; i < wParam.length; i++)
		{
			String jobNoArray[] = new String[wParam[i].getJobNoList().size()];
			wParam[i].getJobNoList().copyInto(jobNoArray);
				
			Date LastUpdateArray[] = new Date[wParam[i].getLastUpdateDateList().size()];
			wParam[i].getLastUpdateDateList().copyInto(LastUpdateArray);
			for (int j = 0 ; j < jobNoArray.length ; j++)
			{
				//#CM724370
				// Lock & Exclusion check 
				if (!lock(conn, jobNoArray[j], LastUpdateArray[j]))
				{
					//#CM724371
					// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
					wMessage = "6023209" + wDelim + wParam[i].getRowNo();
					return null;
				}
				//#CM724372
				// Update Work Status Info.
				if (!updateWorkinginfo(conn, jobNoArray[j]))
				{
					return null;
				}
				//#CM724373
				// Update the Picking Plan Info.
				if (!updateRetrievalPlan(conn, jobNoArray[j]))
				{
					return null;
				}
				jobnoVec.addElement(jobNoArray[j]);
			}
		}
		//#CM724374
		// Search through the Picking Plan Info again. 
		RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[])this.query(conn, wParam[0]);
		//#CM724375
		// 6021010=Data was committed. 
		wMessage = "6021010";
			
		//#CM724376
		// To print the Work report: 
		if (wParam[0].getRetrievalListFlg())
		{
			startPrint(conn, jobnoVec, wParam[0].getCasePieceflg());
		}
			
		//#CM724377
		// Return the latest Picking Plan Info 
		return viewParam;
	
	}
	
	//#CM724378
	// Package methods -----------------------------------------------

	//#CM724379
	// Protected methods ---------------------------------------------

	//#CM724380
	/**
	 * Check for any change via other terminal. <BR>
	 * Compare the values between the last update date/time obtained from the current DB and the last update date/time set for the parameter. <BR>
	 * Compare both data. If both values of the last updated date/time are equal, regard such data as data not modified via other terminal. <BR>
	 * If not equal, regard the data as changed/modified via other terminal. <BR>
	 * Require to compare the data in the Work Status Info table (DNWORKINFO). <BR>
	 * @param conn       Instance to maintain database connection. 
	 * @param jobno      Work No.
	 * @param lastupdate Last update date/time
	 * @return Return true when changed already via other terminal, or return false when not changed yet. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	protected boolean lock(Connection conn, String jobno, Date lastupdate) throws ReadWriteException, ScheduleException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		//#CM724381
		// Lock Data. 
		//#CM724382
		// Work No.
		searchKey.setJobNo(jobno);
		WorkingInformation[] working = (WorkingInformation[])workingHandler.findForUpdate(searchKey);

		//#CM724383
		// Work No. does not exist in a data (in the case where data was deleted) 
		if (working == null || working.length == 0)
		{
			return false;
		}
		//#CM724384
		// For data with Status flag other than Standby (due to loading or maintenace executed which may cause to change the status) 
		if (!(working[0].getStatusFlag()).equals(WorkingInformation.STATUS_FLAG_UNSTART))
		{
			return false;
		}

		//#CM724385
		// Compare the values between the last update date/time obtained from the current DB and the last update date/time set for the parameter. 
		//#CM724386
		// If not equal, regard the data as changed/modified via other terminal. 
		if (!working[0].getLastUpdateDate().equals(lastupdate))
		{
			return false;
		}

		return true;
	}
	
	//#CM724387
	/**
	 * Pass the Work No. and Case Piece flag to the class to print the Order Picking report.<BR>
	 * Disables to print if no print data. <BR>
	 * If successfully printed, receive true from the class for printing the storage instruction. If failed, receive false, and<BR>
	 * return the result. <BR>
	 * Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
	 * @param  conn Connection Instance to maintain database connection. 
	 * @param  batchno         Batch No. 
	 * @param  casepieceflag   Case/Piece division
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process. 
	 */
	protected void startPrint(Connection conn, Vector batchno, String casepieceflag)
	{
		try
		{
			RetrievalOrderWriter writer = new RetrievalOrderWriter(conn);
			writer.setJobNo(batchno);
			writer.setCasePieceFlag(casepieceflag);

			//#CM724388
			// Start the printing process. 
			if (writer.startPrint())
			{
				//#CM724389
				// 6021012=Data had been set and the list was printed successfully. 
				wMessage = "6021012";
			}
			else
			{
				//#CM724390
				// 6007042=Printing failed after setup. Please refer to log. 
				wMessage = "6007042";
			}
		}
		catch (Exception e)
		{
			//#CM724391
			// 6007042=Printing failed after setup. Please refer to log. 
			wMessage = "6007042";
		}

	}

	//#CM724392
	/**
	 * Update the Work Status Info table. 
	 * @param conn       Instance to maintain database connection. 
	 * @param jobno      Work No.
	 * @return Return true if the update process normally completed, otherwise return false. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	protected boolean updateWorkinginfo(Connection conn, String jobno) throws ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
			WorkingInformationAlterKey alterKey = new WorkingInformationAlterKey();
			alterKey.setJobNo(jobno);
			//#CM724393
			// "Started" 
			alterKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_START);
			alterKey.updateLastUpdatePname(PROCESSNAME);
			//#CM724394
			// Update Data. 
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

	//#CM724395
	/**
	 * Update the work status in the Picking Plan Info table to Started. <BR>
	 * If the work has been started, disable to change it. <BR>
	 * @param conn      Instance to maintain database connection. 
	 * @param jobno     Work No.
	 * @return Return true if the update process normally completed, otherwise return false. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.<BR>
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	protected boolean updateRetrievalPlan(Connection conn, String jobno) throws ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey workingsearchKey = new WorkingInformationSearchKey();

			//#CM724396
			// Search Data 
			//#CM724397
			// Work No.
			workingsearchKey.setJobNo(jobno);
			WorkingInformation working = (WorkingInformation)workingHandler.findPrimary(workingsearchKey);
			
			if (working != null)
			{
				String planukey = working.getPlanUkey();
				RetrievalPlanHandler retHandler = new RetrievalPlanHandler(conn);
				RetrievalPlanSearchKey retsearchKey = new RetrievalPlanSearchKey();
				RetrievalPlanAlterKey alterKey = new RetrievalPlanAlterKey();
				//#CM724398
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
						alterKey.updateLastUpdatePname(PROCESSNAME);
						//#CM724399
						// Update Data. 
						retHandler.modify(alterKey);
					}
				}
				else
				{
					//#CM724400
					// 6006040=Data mismatch occurred. See log. {0} 
					RmiMsgLogClient.write("6006040" + wDelim + planukey, this.getClass().getName());
					//#CM724401
					// Throw ScheduleException here. (Not required to set any error message) 
					throw (new ScheduleException());
				}
				return true;
			}
			else
			{
				//#CM724402
				// 6006040=Data mismatch occurred. See log. {0} 
				RmiMsgLogClient.write("6006040" + wDelim + jobno, this.getClass().getName());
				//#CM724403
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

	//#CM724404
	// Private methods -----------------------------------------------
}
//#CM724405
//end of class
