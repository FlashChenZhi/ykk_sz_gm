package jp.co.daifuku.wms.retrieval.schedule;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM726045
/*
 * Created on 2004/10/18
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
//#CM726046
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * Allow this class to inquire the picking status.  <BR>
 * Receive the contents entered via screen as a parameter and inquire the picking status.  <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind</CODE> method) <BR> 
 * <BR>
 * <DIR>
 *   1-1 Return the Planned Picking Date in the Work Status Info. <BR> 
 * 	 <BR>
 *   [search conditions]  <BR>
 *   <BR>
 *   Data with work type "Picking" <BR>
 *   Status flag other than Deleted<BR>
 *   Set the data for the parameter array in the order of Planned Picking Date. <BR> 
 *   <BR> 
 *   [Returned data]  <BR>
 *   <BR>
 *   Planned Picking Date* <BR>
 *   <BR>
 *   1-2 For Work Status Info with only one Consignor Code exists, return the corresponding Consignor Code. <BR> 
 *	     Return null if no corresponding data found, or two or more corresponding data exist. <BR> 
 *   <BR>
 *   [search conditions]  <BR> 
 *   <BR>
 *   Data with work type "Picking" <BR>
 *   Status flag other than Deleted<BR>
 *   <BR>
 *   [Returned data]  <BR>
 *   <BR>
 *   Consignor Code <BR>
 *   <BR>
 * </DIR>
 * 2. Process by clicking "Display" button ( <CODE>query</CODE> method)  <BR>
 * <BR>
 * <DIR> Receive the contents entered via screen as a parameter, and obtain the data for inquiring the picking status from the database.  <BR>
 * If no corresponding data is found, return <CODE>Parameter</CODE> array with number of elements equal to 0. 
 * Return null when condition error occurs.  <BR>
 * Use <CODE>getMessage()</CODE> method to refer to the content of the error. <BR>
 * Sum up the work count, case qty, piece qty, and count of consignor, by Order/Item Count and Status, and return the results.  <BR>
 * Sum up the total of each quantity and the progress rate, and return them.  <BR>
 * Search through the Work Status Info table. (DNWORKINFO)  <BR>
 * <BR>
 * [search conditions]  <BR>
 * <BR>
 * Status flag other than Deleted <BR>
 * <BR>
 * [Parameter]  *Mandatory Input <BR>
 * <BR>
 * Consignor Code <BR>
 * Planned Picking Date* <BR>
 * <BR>
 * [Returned data]  <BR>
 * Obtain the return data in the following process.  <BR>
 * <BR>
 * - Loop of Order/Item(0: Order  1: Item)  <BR>
 * <BR>
 * </DIR>
 * <BR>
 * 3. Ensure to display the following parameters as the result.  <BR>
 * <BR>
 * 3. a)- Order aggregation Work 
 * <DIR>
 * Order Count  <BR>
 * Work Count  <BR>
 * Case Qty  <BR>
 * Piece Qty  <BR>
 * Number of consignors  <BR>
 * </DIR>
 * <BR>
 * 3.b) Item aggregation Work 
 * <DIR>
 * Work Count  <BR>
 * Case Qty  <BR>
 * Piece Qty  <BR>
 * Number of consignors  <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/18</TD><TD>suresh kayamboo</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @author suresh kayamboo
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:20:05 $
 */
public class RetrievalWorkingInquirySCH extends AbstractRetrievalSCH
{
	//#CM726047
	//Constants----------------------------------------------------

	//#CM726048
	//Attributes---------------------------------------------------
	
	//#CM726049
	/**
	 * Work Status: Not Processed 
	 */
	public final String UNSTART = "0";
	
	//#CM726050
	/**
	 * Work Status: Working 
	 */
	public final String NOW_WORKING = "1";
	
	//#CM726051
	/**
	 * Work Status: Processed 
	 */
	public final String COMPLETE = "2";

	//#CM726052
	//Static-------------------------------------------------------

	//#CM726053
	//Constructors-------------------------------------------------
	//#CM726054
	/**
	 * Initialize this class. 
	 */
	public RetrievalWorkingInquirySCH()
	{
		wMessage = null;
	}

	//#CM726055
	//Public-------------------------------------------------------
	//#CM726056
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:20:05 $");
	}

	//#CM726057
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * Allow this method to obtain the Planned Picking Date and the Consignor Code and return the values. <BR>
	 * Obtain the Consignor Code if only one Consignor Code exists in Work Status Info, and return the corresponding Consignor Code. <BR>
	 * Set null if no corresponding data exists or if two or more data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. <BR>
	 * @param conn Database connection object
	 * @param searchParam Class that inherits the <CODE>Parameter</CODE> class with search conditions 
	 * @return Class that implements the <CODE>Parameter</CODE> interface that contains the search result. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		RetrievalWorkingInquiryParameter dispData = new RetrievalWorkingInquiryParameter();

		//#CM726058
		// Search for the Planned Picking Date.
		//#CM726059
		// Work type (Picking) 
		searchKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM726060
		// Status flag (other than Deleted) 
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
		searchKey.setPlanDateGroup(1);
		searchKey.setPlanDateCollect("");
		//#CM726061
		// Set the data for the parameter array in the order of Planned Picking Date. 
		searchKey.setPlanDateOrder(1, true);

		WorkingInformation[] plandate = (WorkingInformation[]) workingHandler.find(searchKey);

		//#CM726062
		// No corresponding data
		if (plandate == null || plandate.length <= 0)
		{
			return new RetrievalWorkingInquiryParameter();
		}

		String date[] = new String[plandate.length];
		for (int i = 0; i < plandate.length; i++)
		{
			date[i] = plandate[i].getPlanDate();
		}

		dispData.setPlanDateP(date);

		//#CM726063
		// Search Consignor Code. 
		//#CM726064
		// Status flag (other than Deleted) 
		searchKey.KeyClear();
		//#CM726065
		// Work type (Picking) 
		searchKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
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

	//#CM726066
	/**
	 * Receive the contents entered via screen as a parameter, and obtain the data for inquiring the picking status from the database.  <BR>
	 * Aggregate Order Count, Work Count, Case Qty, Piece Qty, and Number of consignors, by status and return it.  <BR>
	 * Use this data to calculate the Work Count, Case Qty, Piece Qty, and Number of consignors. <BR>
	 * If there is only Standby data, set it to Standby.  <BR>
	 * If the status of all the existing data is Completed, set it to Processed.  <BR>
	 * Otherwise, set it to "Working".  <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalWorkingInquiryParameter</CODE> class instance with conditions for obtaining the data to be displayed
	 * 			Designating any instance other than <CODE>RetrievalWorkingInquiryParameter</CODE> throws ScheduleException. 
	 * @return Array of the <CODE>RetrievalWorkingInquiryParameter</CODE> instance with search result. <BR>
	 *         If no corresponding record is found, return the array with number of elements equal to 0.  <BR>
	 *         Return null when input condition error occurs.  <BR>
	 *         Returning null enables to obtain the error content as a message using the <CODE>getMessage()</CODE> method.  <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		RetrievalWorkingInquiryParameter param = (RetrievalWorkingInquiryParameter) searchParam;

		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		//#CM726067
		// Search Data 
		//#CM726068
		// Work type (Picking) 
		searchKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM726069
		// Status flag (other than Deleted) 
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");

		//#CM726070
		// Consignor Code
		String consignorcode = param.getConsignorCode();
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		//#CM726071
		// Planned Picking Date
		String plandate = param.getPlanDate();
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}

		//#CM726072
		// If no corresponding info is not found when searching using the input condition, disable to display. 
		if (workingHandler.count(searchKey) == 0)
		{
			//#CM726073
			// No target data was found. 
			wMessage = "6003018";

			return new RetrievalWorkingInquiryParameter[0];
		}

		//#CM726074
		// Order No.  = 0 
		//#CM726075
		// Item  = 1 
		Vector myVector = new Vector();
		for (int j = 0; j < 2; j++)
		{

			//#CM726076
			// It is not suren whether this is the most efficient method to obtain the data that contains two or more queries existing in the table. 
			int totalOrderNo = 0;
			int totalWorkNo = 0;
			long totalCaseNo = 0;
			long totalPieceNo = 0;
			int totalConsignorNo = 0;

			RetrievalWorkingInquiryParameter rWParam = new RetrievalWorkingInquiryParameter();

			//#CM726077
			// Hash table to obtain work status (Consignor and Order) 
			Hashtable consignorTable = new Hashtable();
			Hashtable orderTable = new Hashtable();

			if (j == 0) 
			//#CM726078
			// Set the search conditions for work of Order Picking. 
			{	    
				searchKey.setOrderNoCollect("");
				searchKey.setConsignorCodeCollect("");
				searchKey.setStatusFlagCollect("");
				searchKey.setWorkFormFlagCollect("");
				searchKey.setPlanEnableQtyCollect("");
				searchKey.setEnteringQtyCollect("");
				searchKey.setOrderNo("", "IS NOT NULL");
			}
			else
			//#CM726079
			// Set the search conditions for work of Item Picking. 
			{
				searchKey.KeyClear();
				//#CM726080
				// Consignor Code
				if (!StringUtil.isBlank(param.getConsignorCode()))
				{
					searchKey.setConsignorCode(consignorcode);
				}
				//#CM726081
				// Planned Picking Date
				if (!StringUtil.isBlank(param.getPlanDate()))
				{
					searchKey.setPlanDate(plandate);
				}
				searchKey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL) ;
				searchKey.setOrderNoCollect("");
				searchKey.setConsignorCodeCollect("");
				searchKey.setWorkFormFlagCollect("");
				searchKey.setStatusFlagCollect("");
				searchKey.setPlanEnableQtyCollect("");
				searchKey.setEnteringQtyCollect("");
				searchKey.setOrderNo("", "IS NULL");
			}

			//#CM726082
			// Obtain all values by a single query. 
			WorkingInformation[] workInfo = (WorkingInformation[]) workingHandler.find(searchKey);

			int caseNo_U = 0;
			int caseNo_N = 0;
			int caseNo_F = 0;
			int pieceNo_U = 0;
			int pieceNo_N = 0;
			int pieceNo_F = 0;
			int workNo_U = 0;
			int workNo_N = 0;
			int workNo_F = 0;

			if (workInfo != null && workInfo.length > 0)
			{
				for (int i = 0; i < workInfo.length; i++)
				{
					//#CM726083
					// Obtain the Case Qty and Piece Qty. 
					if (workInfo[i].getPlanEnableQty() > 0)
					{
						//#CM726084
						// Count it up as a Not Processed data. 
						if (workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART))
						{
							if (workInfo[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE) || workInfo[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_NOTHING))
							{
								caseNo_U += DisplayUtil.getCaseQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty());
								pieceNo_U += DisplayUtil.getPieceQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty());
							}
							else if (workInfo[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_PIECE))
							{
								caseNo_U += 0;
								pieceNo_U += workInfo[i].getPlanEnableQty();
							}
						}
						//#CM726085
						// Count it up as a Working data. 
						else if (
							workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START)
								|| workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
								|| workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART))
						{
							if (workInfo[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE) || workInfo[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_NOTHING))
							{
								caseNo_N += DisplayUtil.getCaseQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty());
								pieceNo_N += DisplayUtil.getPieceQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty());
							}
							else if (workInfo[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_PIECE))
							{
								caseNo_N += 0;
								pieceNo_N += workInfo[i].getPlanEnableQty();
							}
						}
						//#CM726086
						// Count it up as a Processed data. 
						else if (workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
						{
							if (workInfo[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE) || workInfo[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_NOTHING))
							{
								caseNo_F += DisplayUtil.getCaseQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty());
								pieceNo_F += DisplayUtil.getPieceQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty());
							}
							else if (workInfo[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_PIECE))
							{
								caseNo_F += 0;
								pieceNo_F += workInfo[i].getPlanEnableQty();
							}
						}
					}

					//#CM726087
					//Obtain the Work Count and set the Order Count, for Order Picking. 
					if (!StringUtil.isBlank(workInfo[i].getOrderNo()))
					{
						//#CM726088
						// For data with status "Standby": 
						if (workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART))
						{
							//#CM726089
							// Count up the Work Count to Standby. 
							workNo_U += 1;

							//#CM726090
							// Set the status of the corresponding Order No. 
							//#CM726091
							// If Hash table contains the same Order No.:
							if (orderTable.containsKey(workInfo[i].getOrderNo()))
							{
								//#CM726092
								// If the value has been set to Processed, reset it and set it to Working. 
								String temp = (String) orderTable.get(workInfo[i].getOrderNo());
								if (temp.equals(COMPLETE))
								{
									orderTable.put(workInfo[i].getOrderNo(), NOW_WORKING);
								}
							}
							//#CM726093
							// If there is no same Order No., set it to Not Processed.
							else
							{
								orderTable.put(workInfo[i].getOrderNo(), UNSTART);
							}
					
						}
						//#CM726094
						// For data with "Started", "Working" or "Partially Completed": 
						else if (
							workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START)
								|| workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
								|| workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART))
						{
							//#CM726095
							// Count up the Work Count as a Working data. 
							workNo_N += 1;
							
							//#CM726096
							// Set the status of the corresponding Order No. to Working. 
							orderTable.put(workInfo[i].getOrderNo(), NOW_WORKING);

						}
						//#CM726097
						// For data with work status "Completed": 
						else if (workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
						{
							//#CM726098
							// Count up the Work Count to Processed. 
							workNo_F += 1;

							//#CM726099
							// Set the status of the corresponding Order No. 
							//#CM726100
							// For data with work status Completed and other status, change its status to Working. 
							if (orderTable.containsKey(workInfo[i].getOrderNo()))
							{
								String temp = (String) orderTable.get(workInfo[i].getOrderNo());
								if (temp.equals(UNSTART))
								{
									orderTable.put(workInfo[i].getOrderNo(), NOW_WORKING);
								}
							}
							//#CM726101
							// If there is no same Order No., set it to Completed.
							else
							{
								orderTable.put(workInfo[i].getOrderNo(), COMPLETE);
							}
						}
						
						
					}

					//#CM726102
					// Obtain the count of Consignor data. 
					if (!StringUtil.isBlank(workInfo[i].getConsignorCode()))
					{
						//#CM726103
						// For data with work status "Standby": 
						if (workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART))
						{
							//#CM726104
							// Verify that the same Consignor Code is displayed in the status UNSTART (Standby). 
							//#CM726105
							// If Consignor Code of the status NOW_WORKING (Working) increment is in status NOW_WORKING (Working), 
							//#CM726106
							// delete Consignor Code from the status UNSTART(Standby). 
							//#CM726107
							// If Hash table contains the same Consignor Code:
							if (consignorTable.containsKey(workInfo[i].getConsignorCode()))
							{
								//#CM726108
								// If the value has been set to Processed, reset it and set it to Working. 
								String temp = (String) consignorTable.get(workInfo[i].getConsignorCode());
								if (temp.equals(COMPLETE))
								{
									consignorTable.put(workInfo[i].getConsignorCode(), NOW_WORKING);
								}
							}
							//#CM726109
							// If there is no same Consignor Code, set it to Not Processed.
							else
							{
								consignorTable.put(workInfo[i].getConsignorCode(), UNSTART);
							}
							
						}
						//#CM726110
						// For data with work status "Started", "Working" or "Partially Completed": 
						else if (
							workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START)
								|| workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
								|| workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART))
						{
							//#CM726111
							// Working 
							consignorTable.put(workInfo[i].getConsignorCode(), NOW_WORKING);
						}
						//#CM726112
						// For data with work status "Completed": 
						else if (workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
						{
							//#CM726113
							// Completed 
							//#CM726114
							// Verify that the same Consignor Code is displayed in the status UNSTART (Standby). 
							//#CM726115
							// If Consignor Code of the status 1 increment is in status 1(Working),
							//#CM726116
							// delete the Consignor Code from the data in work status 0 (Standby). 
							//#CM726117
							// For data with work status Completed and other status, change its status to Working. 
							if (consignorTable.containsKey(workInfo[i].getConsignorCode()))
							{
								String temp = (String) consignorTable.get(workInfo[i].getConsignorCode());
								if (temp.equals(UNSTART))
								{
									consignorTable.put(workInfo[i].getConsignorCode(), NOW_WORKING);
								}
							}
							//#CM726118
							// If there is no same Consignor Code, set it to Completed.
							else
							{
								consignorTable.put(workInfo[i].getConsignorCode(), COMPLETE);
							}
						}
					}

					//#CM726119
					// Obtain the count of Work data for Item Picking. 
					if (j == 1)
					{
						if (StringUtil.isBlank(workInfo[i].getOrderNo()))
						{
							//#CM726120
							// For data with work status Standby, count up the data with Not Processed. 
							if (workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART))
							{
								workNo_U += 1;
							}
							//#CM726121
							// For data with work status Started, Working or Partially Completed, count up the Working data. 
							else if (
								workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START)
									|| workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
									|| workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART))
							{
								workNo_N += 1;
							}
							//#CM726122
							// For data with work status Completed, count up Processed data. 
							else if (workInfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
							{
								workNo_F += 1;
							}
						}
					}

				}

			}

			//#CM726123
			// Obtain the Number of consignors from the Status flag.
			Enumeration conEnum = consignorTable.keys();
			int miConCount = 0;
			int nowConCount = 0;
			int comConCount = 0;

			//#CM726124
			// Count the the number of data in the information stored in Hash table. 
			while (conEnum.hasMoreElements())
			{
				String key = (String) conEnum.nextElement();
				String value = (String) consignorTable.get(key);
				if (value != null)
				{
					if (value.equals(UNSTART))
					{
						miConCount += 1;
					}
					else if (value.equals(NOW_WORKING))
					{
						nowConCount += 1;
					}
					else if (value.equals(COMPLETE))
					{
						comConCount += 1;
					}
				}
			}

			//#CM726125
			// Obtain the Count of Order from Status flag. 
			Enumeration orderEnum = orderTable.keys();
			int miOrderCount = 0;
			int nowOrderCount = 0;
			int comOrderCount = 0;

			//#CM726126
			// Count the the number of data in the information stored in Hash table. 
			while (orderEnum.hasMoreElements())
			{
				String key = (String) orderEnum.nextElement();
				String value = (String) orderTable.get(key);
				if (value != null)
				{
					if (value.equals(UNSTART))
					{
						miOrderCount += 1;
					}
					else if (value.equals(NOW_WORKING))
					{
						nowOrderCount += 1;
					}
					else if (value.equals(COMPLETE))
					{
						comOrderCount += 1;
					}
				}
			}

			//#CM726127
			// Set the display data for the return data.  

			//#CM726128
			// Not Processed 
			rWParam.setUnstartOrderCount(miOrderCount);
			rWParam.setUnstartWorkCount(workNo_U);
			rWParam.setUnstartCaseCount(caseNo_U);
			rWParam.setUnstartPieceCount(pieceNo_U);
			rWParam.setUnstartConsignorCount(miConCount);

			//#CM726129
			// Working 
			rWParam.setNowOrderCount(nowOrderCount);
			rWParam.setNowWorkCount(workNo_N);
			rWParam.setNowCaseCount(caseNo_N);
			rWParam.setNowPieceCount(pieceNo_N);
			rWParam.setNowConsignorCount(nowConCount);

			//#CM726130
			// Completed 
			rWParam.setFinishOrderCount(comOrderCount);
			rWParam.setFinishWorkCount(workNo_F);
			rWParam.setFinishCaseCount(caseNo_F);
			rWParam.setFinishPieceCount(pieceNo_F);
			rWParam.setFinishConsignorCount(comConCount);

			//#CM726131
			// Total 
			totalOrderNo = miOrderCount + nowOrderCount + comOrderCount;
			rWParam.setOrderTotal(totalOrderNo);
			totalWorkNo = workNo_U + workNo_N + workNo_F;
			rWParam.setWorkTotal(totalWorkNo);
			totalCaseNo = caseNo_U + caseNo_N + caseNo_F;
			rWParam.setCaseTotal(totalCaseNo);
			totalPieceNo = pieceNo_U + pieceNo_N + pieceNo_F;
			rWParam.setPieceTotal(totalPieceNo);
			totalConsignorNo = miConCount + nowConCount + comConCount;
			rWParam.setConsignorTotal(totalConsignorNo);

			//#CM726132
			// Progress rate 
			//#CM726133
			// Percentage calculation 
			rWParam.setOrderRate(getRate(comOrderCount, orderTable.size()) + "%");
			rWParam.setWorkRate(getRate(workNo_F, totalWorkNo) + "%");
			rWParam.setCaseRate(getRate(caseNo_F, totalCaseNo) + "%");
			rWParam.setPieceRate(getRate(pieceNo_F, totalPieceNo) + "%");
			rWParam.setConsignorRate(getRate(comConCount, consignorTable.size()) + "%");

			//#CM726134
			// Add it to the Vector object. 
			myVector.add(rWParam);
		}

		RetrievalWorkingInquiryParameter[] paramArray = new RetrievalWorkingInquiryParameter[myVector.size()];
		myVector.copyInto(paramArray);
		wMessage = "6001013";

		return paramArray;

	}
	
	//#CM726135
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
//#CM726136
//end of class
