package jp.co.daifuku.wms.retrieval.schedule;

//#CM722941
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkerHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.entity.Worker;

//#CM722942
/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida  <BR>
 * <BR>
 * Allow this class to complete WEB Item Picking.  <BR>
 * Receive the contents entered via screen as a parameter, and execute the process to complete the Item Picking.  <BR>
 * Allow each method contained in this class to receive a connection object and executes the process for updating the database, but <BR>
 * disable to commit nor roll-back the transaction.  <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind()</CODE> method)  <BR><BR><DIR>
 *   For Work Status Info with only one Consignor Code exists, return the corresponding Consignor Code.  <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
 * <BR>
 *   <Search conditions> <BR><DIR>
 *     Work type: Picking. (3)  <BR>
 *     Status flag:"Started" (1) <BR>
 *     Start Work flag: "Started" (1) <BR>
 *     Order No.: NULL  </DIR></DIR>
 * <BR>
 * 2. Process by clicking "Display" button (<CODE>query()</CODE> method) <BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it. <BR>
 *   If no corresponding data is found, return <CODE>Parameter</CODE> array with number of elements equal to 0. Return null when condition error occurs. <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
 *   Display data in the order of Item Code and then Case/Piece division.  <BR>
 * <BR>
 *   <Search conditions> <BR><DIR>
 *     Work type: Picking. (3)  <BR>
 *     Status flag:"Started" (1) <BR>
 *     Start Work flag: "Started" (1) <BR>
 *     Order No.: NULL  <BR></DIR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR><DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Consignor Code* <BR>
 *     Planned Picking Date* <BR>
 *     Item Code <BR>
 *     Case/Piece division* <BR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Planned Picking Date <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Work place  <BR>
 *     Case/Piece division <BR>
 *     Case/Piece division name  <BR>
 *     Planned total qty <BR>
 *     Packed Qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Planned Case Qty ( Work Planned qty /Packed Qty per Case) <BR>
 *     Planned Piece Qty (Work Planned qty %Packed Qty per Case)  <BR>
 *     Result Case Qty (Work Planned qty /Packed Qty per Case)  <BR>
 *     Result Piece Qty (Work Planned qty %Packed Qty per Case)  <BR>
 *     Location  <BR>
 *     Expiry Date <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Work No. <BR>
 *     Last update date/time <BR></DIR></DIR>
 * <BR>
 * 3.Process by clicking on "Complete Picking" button (<CODE>startSCHgetParams()</CODE> method)  <BR><BR><DIR>
 *   Receive the contents displayed in the preset area, as a parameter, and execute the process to complete the Item Picking.  <BR>
 *   Return true when the process normally completed, or return false when the schedule failed to complete due to condition error or other causes.  <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error  <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Consignor Code* <BR>
 *     Planned Picking Date* <BR>
 *     Item Code* <BR>
 *     Case/Piece division* <BR>
 *     Result Case Qty * <BR>
 *     Result Piece Qty * <BR>
 *     Location * <BR>
 *     Expiry Date <BR>
 *     Picking Result display type * <BR>
 *     Terminal No.* <BR>
 *     Work No.* <BR>
 *     Last update date/time* <BR></DIR>
 * <BR>
 *   <Process for completing Picking >  <BR>
 * <DIR>
 *   <Check Process Condition>  <BR>
 *     1.Require that the Worker Code and the Password are defined in the Worker master.  <BR><DIR>
 *       Check only the leading value of the array for the values of Worker code and password.  <BR></DIR>
 *     2. Ensure that the Work Status Info table of work No. exist in the database.  <BR>
 *     3.Require the parameter's Last update date/time to be consistent with the Last update date/time in the Work Status Info table (Check Exclusion.).  <BR>
 *     4.For data file with Packed Qty per Case as a parameter 0, require that 0 is input in the Picking Case Qty.  <BR>
 *     5.Require that any value smaller than the Planned picking qty is input in Picking Case Qty and Picking Piece Qty.  <BR>
 * <BR>
 *   <Update/Add Process>  <BR>
 *     - Update the Work Status Info Table (DNWORKINFO)  <BR>
 *       Update the Work Status Info linked to the parameter's Work No., based on the contents of the received parameter.  <BR>
 *       1. Update status flag to "Completed" (4).  <BR>
 *       2.Update the Work Result qty to the value obtained from increasing the Picking qty of parameter.  <BR>
 *       3. For shortage, update the work shortage qty to the value obtained by subtracting the Picking qty of parameter from the workable picking qty of the work info.  <BR>
 *       4.If Expiry Date is set for the parameter, update the Expiry Date to the value in this content. <BR>
 *       5.Update the Work Result Location to the value of Location No. of the Work Status Info.  <BR>
 *       6. Update the Worker code, Worker name, Terminal No., and the last Update process name.  <BR>
 * <BR>
 *     -Update the Picking Plan Info table (DNRETRIEVALPLAN).  <BR>
 *       Update the Picking Plan Info linked to the Plan unique key of the updated Work Status Info based on the content of the received parameter. <BR>
 *       1.If all status flags of the Work Status linked to the Picking Plan unique key are Completed, update the status flag of the Picking Plan Info to Completed(4).  <BR>
 *         For data with Partially Completed, update the status flag of the Picking Plan Info to Partially Completed (3). For Working data, keep the status as it is. <BR>
 *       2.Update the Picking Result qty to the value obtained by summing-up the Picking qty of the Work Status Info.  <BR>
 *       3.For data with Shortage, update the value to the value obtained by summing-up the Shortage Qty in the Work Status Info.  <BR>
 *       4. Update the last Update process name.  <BR>
 * <BR>
 *     - Update Inventory Info Table (DMSTOCK). (Execute it in the process for completing Picking)  <BR>
 *       Update the Inventory Info linked to the Stock ID of the updated Work Status Info based on the content of the received parameter. <BR>
 *       1.Update the stock qty to the value obtained by decreasing the Picking qty of parameter.  <BR>
 *       2. Update the Allocation qty to the value resulted from the calculation by decreasing the total of the Picking qty and the shortage qty as parameters.  <BR>
 *       3.For data with shortage and also no Inventory Control package, update the Inventory status to Completed (9) when stock qty becomes 0.  <BR>
 *       4. Update the last Update process name.  <BR>
 *       For detail, refer to the <code>RetrievalCompleteOperator</code>.  <BR>
 * <BR>
 *     - Add Result Sent Information Table (DNHOSTSEND)  (Execute in Picking Process for completing.)  <BR>
 *       Add the Result Sent Information based on the Inventory Info contents updated this time.  <BR>
 *       For detail, refer to the <code>RetrievalCompleteOperator</code>.  <BR>
 * <BR>
 *     - Update/Add Worker Result Data Inquiry Table (DNWORKERRESULT).  <BR>
 *       Update or add the Worker Result data inquiry based on the contents in the parameter.  <BR></DIR>
 * </DIR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>S.Yoshida</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:53 $
 * @author  $Author: suresh $
 */
public class RetrievalItemCompleteSCH extends AbstractRetrievalSCH
{

	//#CM722943
	// Class variables -----------------------------------------------
	//#CM722944
	/**
	 * Process name 
	 */
	private static String wProcessName = "RetrievalItemCompleteSCH";

	//#CM722945
	// Class method --------------------------------------------------
	//#CM722946
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:53 $");
	}

	//#CM722947
	// Constructors --------------------------------------------------
	//#CM722948
	/**
	 * Initialize this class. 
	 */
	public RetrievalItemCompleteSCH()
	{
		wMessage = null;
	}

	//#CM722949
	// Public methods ------------------------------------------------
	//#CM722950
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * For Work Status Info with only one Consignor Code exists, return the corresponding Consignor Code. <BR>
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

		//#CM722951
		// Set the corresponding Consignor code. 
		RetrievalSupportParameter wParam = new RetrievalSupportParameter();

		//#CM722952
		// Generate instance of Work Status Info handlers. 
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation[] wWorkinfo = null;

		try
		{
			//#CM722953
			// Set the search conditions. 
			//#CM722954
			// Work type=Picking(3)
			wKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			//#CM722955
			// Start Work flag="Started" (1)
			wKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			//#CM722956
			// Status flag = "Started" (1) 
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
			//#CM722957
			// Order No. = NULL 
			wKey.setOrderNo("");
			//#CM722958
			// Set the Consignor Code for grouping condition. 
			wKey.setConsignorCodeGroup(1);
			wKey.setConsignorCodeCollect();

			//#CM722959
			// If the search result count is one: 
			if (wObj.count(wKey) == 1)
			{
				//#CM722960
				// Obtain the corresponding Consignor Code. 
				wWorkinfo = (WorkingInformation[]) wObj.find(wKey);

				if (wWorkinfo != null && wWorkinfo.length == 1)
				{
					//#CM722961
					// Obtain the corresponding Consignor Code and set it for the return parameter. 
					wParam.setConsignorCode(wWorkinfo[0].getConsignorCode());
				}
			}
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

		return wParam;
	}

	//#CM722962
	/**
	 * Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0. <BR>
	 *          Return null if the search result count exceeds 1000 or when error occurs with input condition. <BR>
	 *          Returning array with number of elements 0 (zero) or null allows to obtain the error contents as a message using <CODE>getMessage()</CODE> method. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		//#CM722963
		// search result
		RetrievalSupportParameter[] wRParam = null;

		//#CM722964
		// Consignor Name
		String wConsignorName = "";

		//#CM722965
		// Generate instance of Work Status Info handlers. 
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey wNameKey = new WorkingInformationSearchKey();
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformation[] wWorkinfo = null;
		WorkingInformation[] wWorkinfoName = null;

		try
		{
			//#CM722966
			// Search conditions for parameters 
			RetrievalSupportParameter wParam = (RetrievalSupportParameter) searchParam;

			//#CM722967
			// Check the Worker code and password 
			if (!checkWorker(conn, wParam))
			{
				return null;
			}

			//#CM722968
			// Set the search conditions. 
			//#CM722969
			// Work type=Picking(3)
			wKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			wNameKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			//#CM722970
			// Start Work flag="Started" (1)
			wKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			wNameKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			//#CM722971
			// Status flag = "Started" (1) 
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
			wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
			//#CM722972
			// Order No. = NULL 
			wKey.setOrderNo("");
			wNameKey.setOrderNo("");
			//#CM722973
			// Consignor Code
			if (!StringUtil.isBlank(wParam.getConsignorCode()))
			{
				wKey.setConsignorCode(wParam.getConsignorCode());
				wNameKey.setConsignorCode(wParam.getConsignorCode());
			}
			//#CM722974
			// Planned Picking Date
			if (!StringUtil.isBlank(wParam.getRetrievalPlanDate()))
			{
				wKey.setPlanDate(wParam.getRetrievalPlanDate());
				wNameKey.setPlanDate(wParam.getRetrievalPlanDate());
			}
			//#CM722975
			// Item Code
			if (!StringUtil.isBlank(wParam.getItemCode()))
			{
				wKey.setItemCode(wParam.getItemCode());
				wNameKey.setItemCode(wParam.getItemCode());
			}
			//#CM722976
			// Case/Piece division 
			if (!StringUtil.isBlank(String.valueOf(wParam.getCasePieceflg()))
				&& !wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
			{
				if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
				{
					wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
					wNameKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
				}
				else if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
				{
					wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
					wNameKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
				}
				else if (wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
				{
					wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
					wNameKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
				}
			}
			//#CM722977
			// Set the sorting order: in the order of Item Code, Case/Piece division, and then Location. 
			wKey.setItemCodeOrder(1, true);
			wKey.setWorkFormFlagOrder(2, true);
			wKey.setLocationNoOrder(3, true);

			if (!canLowerDisplay(wObj.count(wKey)))
			{
				return returnNoDisplayParameter();
			}
			//#CM722978
			// Obtain the Picking Plan Info. 
			wWorkinfo = (WorkingInformation[]) wObj.find(wKey);

			//#CM722979
			// Obtain the Consignor Name with the latest Added Date/Time. 
			wNameKey.setRegistDateOrder(1, false);
			wWorkinfoName = (WorkingInformation[]) wObj.find(wNameKey);
			if (wWorkinfoName != null && wWorkinfoName.length > 0)
			{
				wConsignorName = wWorkinfoName[0].getConsignorName();
			}

			//#CM722980
			// Generate a Vector instance 
			Vector vec = new Vector();

			//#CM722981
			// Set the search result for the return parameter. 
			for (int i = 0; i < wWorkinfo.length; i++)
			{
				RetrievalSupportParameter wTemp = new RetrievalSupportParameter();
				//#CM722982
				// Consignor Code
				wTemp.setConsignorCode(wWorkinfo[i].getConsignorCode());
				//#CM722983
				// Consignor Name
				wTemp.setConsignorName(wConsignorName);
				//#CM722984
				// Planned Picking Date
				wTemp.setRetrievalPlanDate(wWorkinfo[i].getPlanDate());
				//#CM722985
				// Item Code
				wTemp.setItemCode(wWorkinfo[i].getItemCode());
				//#CM722986
				// Item Name
				wTemp.setItemName(wWorkinfo[i].getItemName1());
				//#CM722987
				// Work place 
				if (wWorkinfo[i].getSystemDiscKey() == (RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR))
				{
					wTemp.setSystemDiscKey(RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR);
					wTemp.setSystemDiscKeyName(
						DisplayUtil.getSystemDiscKeyValue(RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR));
				}
				else if (wWorkinfo[i].getSystemDiscKey() == (RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS))
				{
					wTemp.setSystemDiscKey(RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS);
					wTemp.setSystemDiscKeyName(
						DisplayUtil.getSystemDiscKeyValue(RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS));
				}
				//#CM722988
				// Case/Piece division 
				wTemp.setCasePieceflg(wWorkinfo[i].getWorkFormFlag());
				//#CM722989
				// Case/Piece division (name) 
				wTemp.setCasePieceflgName(DisplayUtil.getPieceCaseValue(wWorkinfo[i].getWorkFormFlag()));
				//#CM722990
				// Total Picking qty 
				wTemp.setTotalPlanQty(wWorkinfo[i].getPlanEnableQty());
				//#CM722991
				// Packed Qty per Case
				wTemp.setEnteringQty(wWorkinfo[i].getEnteringQty());
				//#CM722992
				// Packed qty per bundle
				wTemp.setBundleEnteringQty(wWorkinfo[i].getBundleEnteringQty());

				//#CM722993
				// Planned Case Qty 
				wTemp.setPlanCaseQty(
					DisplayUtil.getCaseQty(
						wWorkinfo[i].getPlanEnableQty(),
						wWorkinfo[i].getEnteringQty(),
						wWorkinfo[i].getWorkFormFlag()));
				//#CM722994
				// Planned Piece Qty 
				wTemp.setPlanPieceQty(
					DisplayUtil.getPieceQty(
						wWorkinfo[i].getPlanEnableQty(),
						wWorkinfo[i].getEnteringQty(),
						wWorkinfo[i].getWorkFormFlag()));

				//#CM722995
				// Picking Location 
				wTemp.setRetrievalLocation(wWorkinfo[i].getLocationNo());
				//#CM722996
				// Expiry Date
				wTemp.setUseByDate(wWorkinfo[i].getUseByDate());
				//#CM722997
				// Case ITF
				wTemp.setITF(wWorkinfo[i].getItf());
				//#CM722998
				// Bundle ITF
				wTemp.setBundleITF(wWorkinfo[i].getBundleItf());
				//#CM722999
				// Work No.
				wTemp.setJobNo(wWorkinfo[i].getJobNo());
				//#CM723000
				// Last update date/time
				wTemp.setLastUpdateDate(wWorkinfo[i].getLastUpdateDate());
				vec.addElement(wTemp);
			}

			wRParam = new RetrievalSupportParameter[vec.size()];
			vec.copyInto(wRParam);

			//#CM723001
			// 6001013 = Data is shown. 
			wMessage = "6001013";
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

		return wRParam;
	}

	//#CM723002
	/** 
	 * Receive the contents displayed in the preset area, as a parameter, and execute the process to complete the Item Picking.  <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of parameter object that contains setting info
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 * @return Array of parameter object that contains search result.
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		//#CM723003
		// Generate instance of Worker info handlers. 
		WorkerHandler wObj = new WorkerHandler(conn);
		WorkerSearchKey wKey = new WorkerSearchKey();
		Worker wWorker[] = null;
		int wWorkQty = 0;
		//#CM723004
		// Generate the instance of the class for the process to complete Picking. 
		RetrievalCompleteOperator wCompObj = new RetrievalCompleteOperator();

		try
		{
			RetrievalSupportParameter[] wParam = (RetrievalSupportParameter[]) startParams;

			//#CM723005
			// Check the Worker code and password 
			if (!checkWorker(conn, wParam[0]))
			{
				return null;
			}

			//#CM723006
			// Check the Daily Update Processing. 
			if (isDailyUpdate(conn))
			{
				return null;
			}
			//#CM723007
			// Check for exclusion of all the target data. 
			if (!lockAll(conn, wParam))
			{
				//#CM723008
				// 6003006=Unable to process this data. It has been updated via other work station. 
				wMessage = "6003006";
				return null;
			}
			//#CM723009
			// Check for input and exclusion of the preset area. 
			if (!checkList(conn, wParam))
			{
				return null;
			}

			//#CM723010
			// Obtain the Worker name.
			//#CM723011
			// Set the search conditions. 
			//#CM723012
			// Worker Code
			wKey.setWorkerCode(wParam[0].getWorkerCode());
			//#CM723013
			// Delete flag 
			wKey.setDeleteFlag(Worker.DELETE_FLAG_OPERATION);
			//#CM723014
			// Obtain the search result of the Worker info. 
			wWorker = (Worker[]) wObj.find(wKey);
			//#CM723015
			// Set the Worker Name. 
			wParam[0].setWorkerName(wWorker[0].getName());

			for (int i = 0; i < wParam.length; i++)
			{
				//#CM723016
				// Update Work Status Info.
				WorkingInformation workInfo = (WorkingInformation) updateWorkinfo(conn, wParam[i], wParam[0]);

				//#CM723017
				// Total the Work Quantity. 
				wWorkQty += workInfo.getResultQty();

				//#CM723018
				// Update the Picking Plan Info.
				updateRetrievalPlan(conn, workInfo.getPlanUkey(), workInfo.getResultQty(), workInfo.getShortageCnt());

				//#CM723019
				// Execute the process for completing,  update the inventory, and generate the Result Sent Information.  
				wCompObj.complete(conn, wParam[i].getJobNo(), wProcessName);
			}

			//#CM723020
			// Update or add the Worker Result. 
			updateWorkerResult(
				conn,
				wParam[0].getWorkerCode(),
				wParam[0].getTerminalNumber(),
				WorkingInformation.JOB_TYPE_RETRIEVAL,
				wWorkQty);

			//#CM723021
			// Succeeded in scheduling. 
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) this.query(conn, wParam[0]);

			//#CM723022
			// 6021010=Data was committed. 
			wMessage = "6021010";

			return viewParam;
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

	}

	//#CM723023
	// Package methods -----------------------------------------------

	//#CM723024
	// Protected methods ---------------------------------------------
	//#CM723025
	/**
	 * Receive the contents entered via screen as a parameter and check for input and exclusion in the preset area. 
	 * @param conn Instance to maintain database connection. 
	 * @param checkParam RetrievalSupportParameter class instance with contents that were input via screen. 
	 * @return Return true when no input error occurs, or return false when input error occurs. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected boolean checkList(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{

		//#CM723026
		// Generate instance of Work Status Info handlers. 
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation wWorkinfo = null;

		try
		{
			//#CM723027
			// Contents of Preset area 
			RetrievalSupportParameter[] wParam = (RetrievalSupportParameter[]) checkParam;
			//#CM723028
			// entered Picking qty 
			long wResultQty = 0;
			//#CM723029
			// Planned Picking Qty 
			long wPlanQty = 0;

			for (int i = 0; i < wParam.length; i++)
			{
				//#CM723030
				// Set the search conditions. 
				wKey.KeyClear();
				//#CM723031
				// Work No.
				wKey.setJobNo(wParam[i].getJobNo());
				//#CM723032
				// Obtain the search result of the Work Status Info. 
				wWorkinfo = (WorkingInformation) wObj.findPrimaryForUpdate(wKey);

				if (wWorkinfo != null)
				{
					//#CM723033
					// When Packed Qty per Case is 0, Check for input of Picking Case Number 
					if (wParam[i].getEnteringQty() == 0 && wParam[i].getResultCaseQty() > 0)
					{
						//#CM723034
						// 6023299=No.{0} If Packed Qty. per Case is 0, you cannot enter Picking Case Qty. 
						wMessage = "6023299" + wDelim + wParam[i].getRowNo();
						return false;
					}

					//#CM723035
					// Compute the Picking qty. 
					wResultQty =
						(long) wParam[i].getResultCaseQty() * (long) wParam[i].getEnteringQty()
							+ (long) wParam[i].getResultPieceQty();
					//#CM723036
					// Calculate the Picking Planned Picking qty. 
					wPlanQty = (long) wWorkinfo.getPlanEnableQty();

					if (wResultQty > 0)
					{
						//#CM723037
						// Picking qty > Planned Picking Qty: 
						if (wResultQty > wPlanQty)
						{
							//#CM723038
							// 6023155=Completed quantity exceeded the expected quantity. Please check and reenter. 
							wMessage = "6023155" + wDelim + wParam[i].getRowNo();
							return false;
						}

						//#CM723039
						// Check for overflow. 
						if (wResultQty > (long) WmsParam.MAX_TOTAL_QTY)
						{
							//#CM723040
							// 6023272=No.{0} Please enter {2} or smaller for {1}. 
							wMessage =
								"6023272"
									+ wDelim
									+ wParam[i].getRowNo()
									+ wDelim
									+ DisplayText.getText("LBL-W0198")
									+ wDelim
									+ MAX_TOTAL_QTY_DISP;
							return false;
						}
					}
					//#CM723041
					// Check Exclusion. 
					if (!wParam[i].getLastUpdateDate().equals(wWorkinfo.getLastUpdateDate()))
					{
						//#CM723042
						// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
						wMessage = "6023209" + wDelim + wParam[i].getRowNo();
						return false;
					}
				}
				else
				{
					//#CM723043
					// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
					wMessage = "6023209" + wDelim + wParam[i].getRowNo();
					return false;
				}
			}
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		return true;
	}

	//#CM723044
	/**
	 * Execute the process for updating the Work Status Info. 
	 * @param conn Instance to maintain database connection. 
	 * @param param      RetrievalSupportParameter class instance with contents that were input via screen. 
	 * @param inputParam RetrievalSupportParameter class instance with contents that were input via screen. 
	 *                   (Maintain the leading value of the array) 
	 * @return Instance of WorkingInformation class with contents of the Work Status Info after update. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected WorkingInformation updateWorkinfo(
		Connection conn,
		RetrievalSupportParameter param,
		RetrievalSupportParameter inputParam)
		throws ReadWriteException, ScheduleException
	{

		//#CM723045
		// Generate instance of Work Status Info handlers. 
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformationAlterKey wAKey = new WorkingInformationAlterKey();
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation wWorkinfo = null;
		//#CM723046
		// Work Result qty
		int wResultQty = 0;

		try
		{
			//#CM723047
			// Set the search conditions. 
			//#CM723048
			// Work No.
			wKey.setJobNo(param.getJobNo());
			//#CM723049
			// Obtain the search result of the locked Work Status Info. 
			wWorkinfo = (WorkingInformation) wObj.findPrimary(wKey);

			if (wWorkinfo != null)
			{
				//#CM723050
				// Set the update condition. 
				//#CM723051
				// Work No.
				wAKey.setJobNo(param.getJobNo());

				//#CM723052
				// Set the update value. 
				//#CM723053
				// Status flag
				wAKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
				//#CM723054
				// Calculate the work result qty. 
				wResultQty = param.getEnteringQty() * param.getResultCaseQty() + param.getResultPieceQty();
				//#CM723055
				// Work Result Qty (Sum up the Work Result qty of parameter) 
				wAKey.updateResultQty(wResultQty);
				//#CM723056
				// Shortage Qty (subtract the Work Result qty from the workable Qty) 
				wAKey.updateShortageCnt(wWorkinfo.getPlanEnableQty() - wResultQty);
				//#CM723057
				// Expiry Date
				wAKey.updateResultUseByDate(param.getUseByDate());
				//#CM723058
				// Work Result Location 
				wAKey.updateResultLocationNo(wWorkinfo.getLocationNo());
				//#CM723059
				// Terminal No.
				wAKey.updateTerminalNo(inputParam.getTerminalNumber());
				//#CM723060
				// Worker Code
				wAKey.updateWorkerCode(inputParam.getWorkerCode());
				//#CM723061
				// Worker Name
				wAKey.updateWorkerName(inputParam.getWorkerName());
				//#CM723062
				// Last update process name
				wAKey.updateLastUpdatePname(wProcessName);

				//#CM723063
				// Update Work Status Info.
				wObj.modify(wAKey);

				return (WorkingInformation) wObj.findPrimary(wKey);
			}
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

		return null;
	}

	//#CM723064
	/**
	 * Execute the process for updating the Picking Plan Info. 
	 * @param conn      Instance to maintain database connection. 
	 * @param planUKey  Picking Plan unique key to be updated 
	 * @param resultQty Result qty of the Work Status Info 
	 * @param shortage  Shortage Qty in the Work Status Info 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void updateRetrievalPlan(Connection conn, String planUKey, int resultQty, int shortage)
		throws ReadWriteException
	{

		try
		{
			//#CM723065
			// Obtain the search result of the locked Picking Plan Info. 
			RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn);
			RetrievalPlanAlterKey planAlterKey = new RetrievalPlanAlterKey();
			RetrievalPlanSearchKey planSearchKey = new RetrievalPlanSearchKey();
			RetrievalPlan plan = null;
			planSearchKey.setRetrievalPlanUkey(planUKey);
			plan = (RetrievalPlan) planHandler.findPrimaryForUpdate(planSearchKey);

			if (plan != null)
			{
				//#CM723066
				// Set the update condition. 
				//#CM723067
				// Shipping Plan unique key 
				planAlterKey.setRetrievalPlanUkey(planUKey);

				//#CM723068
				// Set the update value. 
				//#CM723069
				// Picking Result qty (Sum-up the Picking Result qty in the Work Status Info) 
				planAlterKey.updateResultQty(plan.getResultQty() + resultQty);
				//#CM723070
				// Shortage Qty (sum-up the Shortage Qty in the Work Status Info) 
				planAlterKey.updateShortageCnt(plan.getShortageCnt() + shortage);
				//#CM723071
				// Last update process name
				planAlterKey.updateLastUpdatePname(wProcessName);
				//#CM723072
				// Work status:
				//#CM723073
				// For data with Planned qty equal to Completed qty, regard it as Completed. 
				//#CM723074
				// Otherwise: 
				//#CM723075
				// For Work Status Info with one or more data Working, regard it as Working. 
				//#CM723076
				// For Work Status Info with one or more data Started and no data Working, regard it as Started. 
				//#CM723077
				// For Work Status Info with no data Started and no data Working and one or more data Completed, regard it as Partially Completed. 
				String status = null;
				if (plan.getResultQty() + resultQty + plan.getShortageCnt() + shortage == plan.getPlanQty())
				{
					status = RetrievalPlan.STATUS_FLAG_COMPLETION;
				}
				else
				{
					WorkingInformationHandler wih = new WorkingInformationHandler(conn);
					WorkingInformationSearchKey wiKey = new WorkingInformationSearchKey();

					wiKey.setPlanUkey(planUKey);
					wiKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
					WorkingInformation[] winfo = (WorkingInformation[]) wih.find(wiKey);
					boolean existComplete = false;
					boolean existStart = false;
					boolean existNowWorking = false;
					for (int i = 0; i < winfo.length; i++)
					{
						if (winfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START))
						{
							existStart = true;
						}
						else if (winfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
						{
							existNowWorking = true;
						}
						else if (winfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
						{
							existComplete = true;
						}
					}

					if (existNowWorking)
					{
						status = RetrievalPlan.STATUS_FLAG_NOWWORKING;
					}
					else if (existStart)
					{
						status = RetrievalPlan.STATUS_FLAG_START;
					}
					else if (existComplete)
					{
						status = RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART;
					}
					else
					{
						//#CM723078
						// This status must be impossible.
					}

				}
				planAlterKey.updateStatusFlag(status);
				planHandler.modify(planAlterKey);

			}
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM723079
	// Private methods -----------------------------------------------
}
//#CM723080
//end of class
