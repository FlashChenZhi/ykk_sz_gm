package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * This class allows to execute storage status inquiry process. <BR>
 * Receive the contents entered via screen as a parameter and execute process for inquiring the storage status. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   1-1 Return the Planned storage date of the Work Status.<BR>
 * 	 <BR>
 *   [Search conditions] <BR>
 *   <BR>
 *   Work division "Storage"<BR>
 *   Status flag other than Deleted<BR>
 *   Set the planned storage date in ascending order for the parameter array.<BR>
 *   <BR>
 *   [Returned data] <BR>
 *   <BR>
 *   Planned storage date: PlanDateP* <BR>
 *   <BR>
 *   1-2 If only one Consignor Code exists in Work status, return the corresponding Consignor Code.<BR>
 *	     Return null if no corresponding data found, or two or more corresponding data exist.<BR>
 *   <BR>
 *   [Search conditions] <BR>
 *   <BR>
 *   Work division "Storage"<BR>
 *   Status flag other than Deleted<BR>
 *   <BR>
 *   [Returned data] <BR>
 *   <BR>
 *   Consignor code:ConsignorCode <BR>
 *   <BR>
 * </DIR>
 * 2.Process by clicking on Display button ( <CODE>query()</CODE> Method) <BR>
 * <BR>
 * <DIR>Receive the contents entered via screen as a parameter and obtain the storage status inquiry data from database. <BR>
 * If no corresponding data found, return <CODE>Parameter</CODE>
 * array with the number of element equal to 0. Return null when condition error occurs. <BR>
 * Enable to refer to the contents of error using <CODE>getMessage()</CODE> method. <BR>
 * Sum up the work count, case qty, piece qty, and count of consignor by status and return the results. <BR>
 * Sum up the total of each quantity and the progress rate, and return them. <BR>
 * Search for the table in the Work Status(DNWORKINFO). <BR>
 * <BR>
 * [Search conditions] <BR>
 * <BR>
 * Status flag other than Deleted <BR>
 * <BR>
 * [Parameter] *Mandatory Input <BR>
 * <BR>
 * Consignor code <BR>
 * Planned storage date* <BR>
 * <BR>
 * [Returned data] <BR>
 * Obtain the return data in the following process. <BR>
 * <BR>
 * <DIR><BR>
 * 3. Display the result in the following contents. <BR>
 *
 * [Pattern Table] <BR>
 * <BR>
 * Pattern | Not Processed | Processing | Processed | Status(Screen) <BR>
 * ---------------------------------------------------------------- <BR>
 * 1 | * | | | Not Processed <BR>
 * ---------------------------------------------------------------- <BR>
 * 2 | * | * | | Processing <BR>
 * ---------------------------------------------------------------- <BR>
 * 3 | * | * | * | Processing <BR>
 * ---------------------------------------------------------------- <BR>
 * 4 | * | | * | Processing <BR>
 * ---------------------------------------------------------------- <BR>
 * 5 | | * | | Processing <BR>
 * ---------------------------------------------------------------- <BR>
 * 6 | | * | * | Processing <BR>
 * ---------------------------------------------------------------- <BR>
 * 7 | | | * | completed <BR>
 * ---------------------------------------------------------------- <BR>
 * <BR>
 * Divide the following contents into  Not Processed and Processing and Processed depending on the pattern shown above. <BR>
 * Progress rate is  (Process*100)/Total.  <BR>
 * Calculate the total by  Not Processed+Processing+Processed.  <BR>
 * <DIR>Work count <BR>
 * Case Qty <BR>
 * Piece qty <BR>
 * Consignor count <BR>
 * </DIR> <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/04</TD><TD>C.Kaminishizono</TD><TD>Create New</TD></TR>
 * </TABLE> <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/18 06:53:02 $
 * @author $Author: suresh $
 */
public class StorageWorkingInquirySCH extends AbstractStorageSCH
{

	// Class variables -----------------------------------------------
	/**
	 * Work Status:Standby
	 */
	private final int STATUS_UNSTART = 0;

	/**
	 * Work Status:Processing
	 */
	private final int STATUS_NOWWORKING = 1;

	/**
	 * Work Status:Processed
	 */
	private final int STATUS_COMPLETE = 2;

	/**
	 * Work type: Case Qty
	 */
	private final int CASE = 0;
	/**
	 * Work type: Piece Qty
	 */
	private final int PIECE = 1;

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 *
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/18 06:53:02 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public StorageWorkingInquirySCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * This method supports operations to obtain the data required for initial display. <BR>
	 * If only one Consignor Code exists in the Storage Plan info, return the corresponding Consignor Code. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. <BR>
	 *
	 * @param conn Database connection object
	 * @param locale <CODE>Locale</CODE> object for which Area Code is set.
	 * @param searchParam This Class inherits the <CODE>Parameter</CODE> class with search conditions.
	 * @return This Class implements the <CODE>Parameter</CODE> interface containing search results.
	 * @throws ReadWriteException
	 *             Announce when error occurs on the database connection.
	 * @throws ScheduleException
	 *             Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		StorageWorkingInquiryParameter dispData = new StorageWorkingInquiryParameter();

		// Search Planned Storage Date.
		// Work division(Storage)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		// Status flag(other than Deleted)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
		searchKey.setPlanDateGroup(1);
		searchKey.setPlanDateCollect("");
		// Set the planned storage date in ascending order for the parameter array.
		searchKey.setPlanDateOrder(1, true);

		WorkingInformation[] plandate = (WorkingInformation[]) workingHandler.find(searchKey);

		// No corresponding data
		if (plandate == null || plandate.length <= 0)
		{
			return new StorageWorkingInquiryParameter();
		}

		String date[] = new String[plandate.length];
		for (int i = 0; i < plandate.length; i++)
		{
			date[i] = plandate[i].getPlanDate();
		}

		dispData.setPlanDateP(date);

		// Search Consignor Code.
		// Status flag(other than Deleted)
		searchKey.KeyClear();
		// Work division(Storage)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		int count = workingHandler.count(searchKey);

		if (count == 1)
		{
			try
			{
				WorkingInformation consignor = (WorkingInformation) workingHandler.findPrimary(searchKey);

				dispData.setConsignorCode(consignor.getConsignorCode());

			}
			catch (NoPrimaryException e)
			{
				dispData.setConsignorCode(null);

				return dispData;
			}
		}

		return dispData;
	}

	/**
	 * Receive the contents entered via screen as a parameter and obtain the storage status inquiry data from database. <BR>
	 * Sum up the work count, count of items, case qty, piece qty, and Count of consignors by status and then return the results. <BR>
	 * Sum up the total of each quantity and the progress rate, and return them. <BR>* For detailed operations, enable to refer to the section "Explanations of Class ". <BR>
	 *
	 * @param conn Instance to maintain database connection.
	 * @param locale Use this to obtain the Area Code and a value localized to display.
	 * @param searchParam
	 *            <CODE>StorageSupportWorkingInquiryParameter</CODE> class instance with conditions for obtaining the display data.
	 *             Designating any instance other than <CODE>
	 *            StorageSupportWorkingInquiryParameter</CODE>
	 *             throws ScheduleException.
	 * @return array of <CODE>StorageSupportWorkingInquiryParameter</CODE>
	 *         instance with search results. <BR>
	 *         If no corresponding record found, return the array of the number of elements equal to 0. <BR>
	 *         Return null when input condition error occurs. <BR>
	 *         Returning null allows to obtain the error content as a message using <CODE>getMessage()</CODE>
	 *         method. <BR>
	 * @throws ReadWriteException
	 *             Announce when error occurs on the database connection.
	 * @throws ScheduleException
	 *             Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		StorageWorkingInquiryParameter param = (StorageWorkingInquiryParameter) searchParam;

		// Input value
		String consignorCode = param.getConsignorCode();
		String planDate = param.getPlanDate();

		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		// Search Data
		searchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
		if (!StringUtil.isBlank(consignorCode))
		{
			searchKey.setConsignorCode(consignorCode);
		}
		if (!StringUtil.isBlank(planDate))
		{
			searchKey.setPlanDate(planDate);
		}

		if (workingHandler.count(searchKey) == 0)
		{
			// No target data was found.
			wMessage = "6003018";
			return new StorageWorkingInquiryParameter[0];
		}

		// Obtain each quantity.
		long qty[][] = getQty(conn, consignorCode, planDate);
		int workCount[] = getWorkCount(conn, consignorCode, planDate);
		long caseQty[] = qty[CASE];
		long pieceQty[] = qty[PIECE];
		int consignorCount[] = getConsignorCount(conn, consignorCode, planDate);

		// Set a return value.
		StorageWorkingInquiryParameter dispData = new StorageWorkingInquiryParameter();
		// Standby
		dispData.setUnstartWorkCount(workCount[STATUS_UNSTART]);
		dispData.setUnstartCaseCount(caseQty[STATUS_UNSTART]);
		dispData.setUnstartPieceCount(pieceQty[STATUS_UNSTART]);
		dispData.setUnstartConsignorCount(consignorCount[STATUS_UNSTART]);

		// Processing
		dispData.setNowWorkCount(workCount[STATUS_NOWWORKING]);
		dispData.setNowCaseCount(caseQty[STATUS_NOWWORKING]);
		dispData.setNowPieceCount(pieceQty[STATUS_NOWWORKING]);
		dispData.setNowConsignorCount(consignorCount[STATUS_NOWWORKING]);

		// completed
		dispData.setFinishWorkCount(workCount[STATUS_COMPLETE]);
		dispData.setFinishCaseCount(caseQty[STATUS_COMPLETE]);
		dispData.setFinishPieceCount(pieceQty[STATUS_COMPLETE]);
		dispData.setFinishConsignorCount(consignorCount[STATUS_COMPLETE]);

		// Sum Work Qty
		int totalWorkCount = 0;
		long totalCaseQty = 0;
		long totalPieceQty = 0;
		int totalConsignorCount = 0;
		// Calculate the total (Total of Not processed, Processing, and Processed data)
		totalWorkCount = workCount[STATUS_UNSTART] + workCount[STATUS_NOWWORKING] + workCount[STATUS_COMPLETE];
		totalCaseQty = caseQty[STATUS_UNSTART] + caseQty[STATUS_NOWWORKING] + caseQty[STATUS_COMPLETE];
		totalPieceQty = pieceQty[STATUS_UNSTART] + pieceQty[STATUS_NOWWORKING] + pieceQty[STATUS_COMPLETE];
		totalConsignorCount = consignorCount[STATUS_UNSTART] + consignorCount[STATUS_NOWWORKING] + consignorCount[STATUS_COMPLETE];

		dispData.setTotalWorkCount(totalWorkCount);
		dispData.setTotalCaseCount(totalCaseQty);
		dispData.setTotalPieceCount(totalPieceQty);
		dispData.setTotalConsignorCount(totalConsignorCount);

		// Percantage calculation
		dispData.setWorkProgressiveRate(getRate(workCount[STATUS_COMPLETE], totalWorkCount) + "%");
		dispData.setCaseProgressiveRate(getRate(caseQty[STATUS_COMPLETE], totalCaseQty) + "%");
		dispData.setPieceProgressiveRate(getRate(pieceQty[STATUS_COMPLETE], totalPieceQty) + "%");
		dispData.setConsignorProgressiveRate(getRate(consignorCount[STATUS_COMPLETE], totalConsignorCount) + "%");

		// Add to Vector object
		Vector retVector = new Vector();
		retVector.add(dispData);
		StorageWorkingInquiryParameter[] paramArray = new StorageWorkingInquiryParameter[retVector.size()];
		retVector.copyInto(paramArray);

		// 6001013 = Data is shown.
		wMessage = "6001013";
		return paramArray;

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * Set the count of works corresponding to the search conditions in unit of work status and return it.<BR>
	 * int[0]:Standby<BR>
	 * int[1]:Processing<BR>
	 * int[2]:completed<BR>
	 *
	 * @param conn Instance to maintain database connection.
	 * @param consignorcode Consignor code
	 * @param plandate      Planned storage date
	 * @return Work count(Array)
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected int[] getWorkCount(Connection conn, String consignorcode, String plandate) throws ReadWriteException
	{
		WorkingInformationHandler workHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		// Search Data
		// WHERE condition
		searchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		// ORDER BY condition
		searchKey.setPlanUkeyOrder(1, true);
		searchKey.setWorkFormFlagOrder(2, true);
		// GROUP BY condition
		searchKey.setPlanUkeyGroup(1);
		searchKey.setWorkFormFlagGroup(2);
		searchKey.setStatusFlagGroup(3);
		// Item (count) to be obtained
		searchKey.setPlanUkeyCollect("");
		searchKey.setWorkFormFlagCollect("");
		searchKey.setStatusFlagCollect("");

		// Return value
		int[] returnCount = {0, 0, 0};

		// Disable to search if no corresponding info exists.
		if (workHandler.count(searchKey) <= 0)
		{
			return returnCount;
		}

		// Search for the Work status.
		WorkingInformation[] workInfo = (WorkingInformation[]) workHandler.find(searchKey);

		if (workInfo == null || workInfo.length <= 0)
		{
			return returnCount;
		}

		// Aggregate it in unit of lan unique key to search for the work division.
		Vector parentVec = new Vector();
		Vector vec = new Vector();
		for (int i = 0; i < workInfo.length; i++)
		{

			// Process for initial time only
			if (i == 0)
			{
				vec.addElement(workInfo[i]);
				continue;
			}

			// Set for the array, if any difference with the plan unique key and/or work type (the same work).
			if (!workInfo[i].getPlanUkey().equals(workInfo[i - 1].getPlanUkey()) ||
				!workInfo[i].getWorkFormFlag().equals(workInfo[i - 1].getWorkFormFlag()))
			{
				// Set the previous element for the Vector.
				parentVec.addElement((Vector) vec.clone());
				vec.clear();
			}

			// Store the element of this time.
			vec.addElement(workInfo[i]);

		}
		// Last one data
		parentVec.addElement((Vector) vec.clone());

		// Obtain the corresponding count.
		for (int i = 0; i < parentVec.size(); i++)
		{
			Vector child = (Vector) parentVec.get(i);
			WorkingInformation workInfoArray[] = new WorkingInformation[child.size()];
			child.copyInto(workInfoArray);
			// Count-up the corresponding work status.
			returnCount[getStatus(workInfoArray)]++ ;

		}

		parentVec.clear();
		vec.clear();

		return returnCount;

	}

	/**
	 * Set the count of Consignor codes corresponding to the search conditions in unit of work status and return it.<BR>
	 * int[0]:Standby<BR>
	 * int[1]:Processing<BR>
	 * int[2]:completed<BR>
	 *
	 * @param conn Instance to maintain database connection.
	 * @param consignorcode Consignor code
	 * @param plandate      Planned storage date
	 * @return Consignor count(Array)
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected int[] getConsignorCount(Connection conn, String consignorcode, String plandate) throws ReadWriteException
	{
		WorkingInformationHandler workHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		// Search Data
		// WHERE condition
		searchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		// ORDER BY condition
		searchKey.setConsignorCodeOrder(1, true);
		// GROUP BY condition
		searchKey.setConsignorCodeGroup(1);
		searchKey.setStatusFlagGroup(2);
		// Item (count) to be obtained
		searchKey.setConsignorCodeCollect("");
		searchKey.setStatusFlagCollect("");

		// Return value
		int[] returnCount = {0, 0, 0};

		// Disable to search if no corresponding info exists.
		if (workHandler.count(searchKey) <= 0)
		{
			return returnCount;
		}

		// Search for the Work status.
		WorkingInformation[] workInfo = (WorkingInformation[]) workHandler.find(searchKey);

		if (workInfo == null || workInfo.length <= 0)
		{
			return returnCount;
		}

		// Aggregate by Consignor code to identify the work division.
		Vector parentVec = new Vector();
		Vector vec = new Vector();
		for (int i = 0; i < workInfo.length; i++)
		{
			// Process for only initial time
			if (i == 0)
			{
				vec.addElement(workInfo[i]);
				continue;
			}

			// Process fron 2nd time through last time
			// If any difference with Consignor code, set it for the array.
			if (!workInfo[i].getConsignorCode().equals(workInfo[i - 1].getConsignorCode()))
			{
				// Set the previous element for the Vector.
				parentVec.addElement((Vector) vec.clone());
				vec.clear();
			}

			// Store the element of this time.
			vec.addElement(workInfo[i]);

		}
		// Any info with one element has not yet been set for parentVec. Set here it for parentVec.
		parentVec.addElement((Vector) vec.clone());

		// Obtain corresponding count.
		for (int i = 0; i < parentVec.size(); i++)
		{
			Vector child = (Vector) parentVec.get(i);
			WorkingInformation workInfoArray[] = new WorkingInformation[child.size()];
			child.copyInto(workInfoArray);
			// Count-up the corresponding work status.
			returnCount[getStatus(workInfoArray)]++ ;

		}

		parentVec.clear();
		vec.clear();

		return returnCount;

	}

	/**
	 * Designate the Case/Piece division throws and set the work qty corresponding to the search conditions per work status, and return it.<BR>
	 * int[0]:Standby<BR>
	 * int[1]:Processing<BR>
	 * int[2]:completed<BR>
	 *
	 * @param conn Instance to maintain database connection.
	 * @param consignorcode Consignor code
	 * @param plandate      Planned storage date
	 * @param casepiece     Case/Piece division
	 * @return Return qty(Case Qty, Piece qty)
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected long[][] getQty(Connection conn, String consignorcode, String plandate) throws ReadWriteException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		// Search Data
		// WHERE
		searchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}
		// ORDER BY
		// Sort by plan unique key and work type to determine the work status in aspect of Plan information by category.
		searchKey.setPlanUkeyOrder(1, true);
		searchKey.setWorkFormFlagOrder(2, true);
		// Item (count) to be obtained
		searchKey.setPlanUkeyCollect();
		searchKey.setStatusFlagCollect();
		searchKey.setPlanEnableQtyCollect("");
		searchKey.setShortageCntCollect("");
		searchKey.setResultQtyCollect("");
		searchKey.setEnteringQtyCollect("");
		searchKey.setWorkFormFlagCollect("");

		long[][] returnCount = {{0, 0, 0}, {0, 0, 0}};

		// Check for the count.
		if (workingHandler.count(searchKey) <= 0)
		{
			return returnCount;
		}

		WorkingInformation[] working = (WorkingInformation[]) workingHandler.find(searchKey);

		if (working == null || working.length <= 0)
		{
			return returnCount;
		}

		Vector parentVec = new Vector();
		Vector vec = new Vector();

		// In order to obtain all the work together that were divided by Pending/Additional delivery,
		// aggregates the work status by plan unique key.
		for (int i = 0; i < working.length; i++)
		{
			// Store the element to be processed only for the initial time.
			if (i == 0)
			{
				vec.addElement(working[i]);
				continue;
			}

			// Set the plan unique key or a work type for the array, if different.
			if (!working[i].getPlanUkey().equals(working[i - 1].getPlanUkey()) ||
				!working[i].getWorkFormFlag().equals(working[i - 1].getWorkFormFlag()))
			{
				// Set the previous element for the Vector.
				parentVec.addElement((Vector) vec.clone());
				vec.clear();

			}

			// Store the element of this time.
			vec.addElement(working[i]);
		}
		// Any info with one element has not yet be set for Vector. Set here it for Vector.
		parentVec.addElement(vec);

		// Count the work quantity in the corresponding info.
		for (int i = 0; i < parentVec.size(); i++)
		{
			Vector child = (Vector) parentVec.get(i);
			WorkingInformation wiArray[] = new WorkingInformation[child.size()];
			child.copyInto(wiArray);

			// Variable for counting up the work qty by work type.
			int caseQty = 0;
			int pieceQty = 0;
			int nothingQty = 0;
			// In some case, the work may be divided due to Pending/Additional delivery process.
			// Count up the possible work qty, result qty, shortage qty by work type using plan unique key, and calculate the work qty.
			for (int j = 0; j < wiArray.length; j++)
			{
				// For calculation
				int sumQty = 0;
				// For Completed or Partially Completed data, count up the result qty and shortage qty.
				if (wiArray[j].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION) ||
					wiArray[j].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART))
				{
					sumQty = (wiArray[j].getResultQty() + wiArray[j].getShortageCnt());
				}
				// For data with status Standby or Processing, sum up the planned possible qty.
				else
				{
					sumQty = wiArray[j].getPlanEnableQty();
				}

				// Execute count-up by division.
				if (wiArray[j].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_CASE))
				{
					caseQty += sumQty;
				}
				else if (wiArray[j].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_PIECE))
				{
					pieceQty += sumQty;
				}
				else
				{
					nothingQty += sumQty;
				}

			}

			// Calculate each quantity.
			returnCount[CASE][getStatus(wiArray)] += DisplayUtil.getCaseQty(caseQty, wiArray[0].getEnteringQty(), wiArray[0].getWorkFormFlag())
												+ DisplayUtil.getCaseQty(nothingQty, wiArray[0].getEnteringQty(), wiArray[0].getWorkFormFlag());
			returnCount[PIECE][getStatus(wiArray)] += pieceQty
												+ DisplayUtil.getPieceQty(nothingQty, wiArray[0].getEnteringQty(), wiArray[0].getWorkFormFlag());

		}

		parentVec.clear();
		vec.clear();

		return returnCount;

	}


	/**
	 * This method returns the division on the screen from the Storage Work status.<BR>
	 * Storage Work Status. Standby only:Standby<BR>
	 * Storage Work Status. Completed only:Processed<BR>
	 * Other than teh above:In-Process<BR>
	 *
	 * @param pWorkinfo WorkingInformation class instance with plan infor contents.
	 * @return Work Status 0:Not Processed 1:Processing 2:Processed
	 */
	protected int getStatus(WorkingInformation[] pWorkinfo)
	{
		// Standby existing flag
		boolean unstart = false;
		// Processing existing flag
		boolean working = false;
		// Presence flag for completed data
		boolean complete = false;

		// Check for presence of the work status and put up the flag.
		for (int i = 0; i < pWorkinfo.length; i++)
		{
			if (pWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART))
			{
				unstart = true;
			}
			else if(pWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START)
							|| pWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
							|| pWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART))
			{
				working = true;
			}
			else if (pWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
			{
				complete = true;
			}
		}

		// Regard the data only with Standby as Not Processed.
		if (unstart && !working && !complete)
		{
			return STATUS_UNSTART;
		}
		// Regard the data only with Completed as Processed.
		else if (!unstart && !working && complete)
		{
			return STATUS_COMPLETE;
		}
		// In other cases, Processing.
		else
		{
			return STATUS_NOWWORKING;
		}

	}

	// Private methods -----------------------------------------------
	/**
	 * Calculate the work progress rate (String) from the completed qty and the Total qty.
	 * @param pFinishQty Completed qty
	 * @param pTotalQty Total qty
	 * @return Work Progress rate
	 */
	private String getRate(double pFinishQty, double pTotalQty)
	{
		if (pTotalQty <= 0)
		{
			return "0.0";
		}

		double returnRate;

		returnRate = pFinishQty / pTotalQty * 100;
		returnRate = java.lang.Math.round(returnRate * 10.0) / 10.0;

		return Double.toString(returnRate);
	}

}
//end of class
