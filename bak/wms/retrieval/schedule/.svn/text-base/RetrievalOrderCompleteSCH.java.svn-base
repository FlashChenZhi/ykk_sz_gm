package jp.co.daifuku.wms.retrieval.schedule;

//#CM723785
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

//#CM723786
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * Allow this class to execute the process for completing Picking Work by Order. <BR>
 * Receive the contents entered via screen as a parameter, and execute the process to complete the Order Picking Work. <BR>
 * Allow each methods contained in this class to receive a connection object and executes the process for updating the database, but <BR>
 * disable to commit nor roll-back the transaction.  <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind()</CODE> method) <BR> 
 * <BR>
 * <DIR>
 *   For Work Status Info with only one Consignor Code exists, return the corresponding Consignor Code.  <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
 *   <BR>
 *   [search conditions]  <BR><DIR>
 *     Only data with work type "Picking"  <BR>
 *     For data with Start Work flag "Started":  <BR>
 *     For data with Status flag "Started" only:  <BR></DIR>
 *   <BR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Display" button (<CODE>query()</CODE> method) <BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it. <BR>
 *   If no corresponding data is found, return <CODE>Parameter</CODE> array with number of elements equal to 0. Return null when condition error occurs. <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR><DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Consignor Code* <BR>
 *     Planned Picking Date* <BR>
 *     Order No.* <BR>
 *     Selected Case/Piece division* <BR>
 *     </DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Planned Picking Date <BR>
 *     Order No. <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Work place  <BR>
 *     Case/Piece division <BR>
 *     Case/Piece division name  <BR>
 *     Total Picking qty (workable Qty)  <BR>
 *     Packed Qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Planned Case Qty (workable Qty/Packed Qty per Case)  <BR>
 *     Planned Piece Qty (workable Qty%Packed Qty per Case)  <BR>
 *     Picking Case Qty (Planned qty /Packed Qty per Case)  <BR>
 *     Picking Piece Qty (Planned qty %Packed Qty per Case)  <BR>
 *     Picking Location  <BR>
 *     Expiry Date <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Work No. <BR>
 *     Last update date/time <BR></DIR>
 * <BR>
 *   <Display order>  <BR><DIR>
 *     Ascending order of Item Code, Ascending order of Location No., Ascending order of Case/Piece division, and Ascending order of Expiry Date  <BR></DIR>
 * <BR>
 *   <Check Process Condition>  <BR>
 *     1.Require that the Worker Code and the Password are defined in the Worker master.  <BR><DIR>
 *       Check only the leading value of the array for the values of Worker code and password.  <BR></DIR>
 * <BR>
 *   <Conditions to search through Work Status Info>  <BR><DIR>
 *     Data that corresponds to the entered Consignor Code.  <BR>
 *     Data that corresponds to the entered Planned Picking Date.  <BR>
 *     Data that corresponds to the entered Order No.  <BR>
 *     Data that corresponds to the entered Case/Piece division  <BR>
 *     Only data with work type "Picking"  <BR>
 *     For data with Status flag "Started" only:  <BR></DIR>
 * <BR>
 *   <Display condition>  <BR><DIR>
 *     Only when the Picking qty display type is enabled to display, compile the Picking Case and the Picking Piece.  <BR>
 *     Otherwise, compile 0.  <BR></DIR>
 * <BR></DIR>
 * 3.Process by clicking on "Complete Picking" button (<CODE>startSCHgetParams()</CODE> method)  <BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and start-up the processes for updating the mandatory info and listing.  <BR>
 *   Obtain the data to be output to the preset area from the database after this process normally completed, and return it.  <BR>
 *   Return null when condition error or exclusion error occurs.  <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error  <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Consignor Code* <BR>
 *     Planned Picking Date* <BR>
 *     Order No.* <BR>
 *     Selected Case/Piece division* <BR>
 *     Work No.* <BR>
 *     Last update date/time * <BR>
 *     Picking Case Qty * <BR>
 *     Picking Piece Qty * <BR>
 *     Expiry Date* <BR>
 *     Preset line No. * <BR>
 *     Terminal No. <BR>
 *     Item Code <BR>
 *     Total Picking qty  <BR>
 *     Packed Qty per Case <BR>
 *     Planned Work Case Qty <BR>
 *     Picking Location  <BR>
 *     Case ITF <BR>
 *     Item Name <BR>
 *     Packed qty per bundle <BR>
 *     Planned Work Piece Qty <BR>
 *     Bundle ITF <BR>
 * </DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Planned Picking Date <BR>
 *     Order No. <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Work place  <BR>
 *     Case/Piece division <BR>
 *     Case/Piece division name  <BR>
 *     Total Picking qty (workable Qty)  <BR>
 *     Packed Qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Planned Case Qty (workable Qty/Packed Qty per Case)  <BR>
 *     Planned Piece Qty (workable Qty%Packed Qty per Case)  <BR>
 *     Picking Case Qty (Planned qty /Packed Qty per Case)  <BR>
 *     Picking Piece Qty (Planned qty %Packed Qty per Case)  <BR>
 *     Picking Location  <BR>
 *     Expiry Date <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Work No. <BR>
 *     Last update date/time <BR></DIR>
 * <BR>
 *   <Check Process Condition>  <BR><DIR>
 *     1.Require that the Worker Code and the Password are defined in the Worker master.  <BR><DIR>
 *       Check only the leading value of the array for the values of Worker code and password.  <BR></DIR>
 *     2. Ensure that the Work Status Info table of work No. exist in the database.  <BR>
 *     3.Require the parameter's Last update date/time to be consistent with the Last update date/time in the Work Status Info table (Check Exclusion.).  <BR>
 *   </DIR>
 * <BR>
 *   <Contents of check for mandatory input>  <BR><DIR>
 *     1. When Packed Qty per Case is 0 or smaller, accept 0 or no-input only as the value of the number of Picking Case. <BR>
 *     2.Require that the value of Picking qty (Total) is smaller than the value of Planned qty (Total).  <BR></DIR>
 * <BR>
 *   <Update Process> <BR><DIR>
 *     - Update the Work Status Info Table (DNWORKINFO)  <BR><DIR>
 *       Update status flag to "Completed".  <BR>
 *       Update the Work Result qty to the value obtained from increasing the Picking qty of parameter.  <BR>
 *       For shortage, update the work shortage qty to the value obtained by subtracting the Picking qty of parameter from the workable picking qty of the work info.  <BR>
 *       If Expiry Date is set for the parameter, update the Expiry Date to the value in this content. <BR>
 *       Update the Work Result Location to the value of Location No. of the Work Status Info.  <BR>
 *       Update the Worker code, Worker name, Terminal No., and the last Update process name.  <BR></DIR>
 *     -Update the Picking Plan Info (DNRETRIEVALPLAN). <BR><DIR>
 *       Update the target Inventory Info using the Plan unique key of the Work Status Info.  <BR>
 *       Sum-up the entered Picking qty (Total) to the Picking Result qty. 
 *       Sum-up the difference between the Planned qty (Total) and the entered Picking qty (Total) to the Picking Shortage Qty.  <BR>
 *       Update the Status flag.  <BR><DIR>
 *         Search through the Work Status Info using Plan unique key.  <BR>
 *         - For data with Status flag Working, update its status to Working.  <BR>
 *         - For data with Status flag Standby, update it to Partially Completed.  <BR>
 *         - Otherwise, update the data to "Completed".  <BR></DIR>
 *       Update the last Update process name.  <BR></DIR>
 *     - Update Inventory Info Table (DMSTOCK).  <BR>
 *     - Add Result Sent Information Table (DNHOSTSEND)  <BR>
 *     - Add and Update of Worker Result data inquiry table (DNWORKERRESULT).  <BR><DIR>
 *      Note)Allow the Complete Picking Process to update the Inventory Info (DMSTOCK) and add the Result data (HOSTSEND). <BR>
 *       Add/Update the Worker Result Information based on the Work Status Info and the Picking qty input (Total).  <BR></DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/19</TD><TD>C.Kaminishizono</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:57 $
 * @author  $Author: suresh $
 */

public class RetrievalOrderCompleteSCH extends AbstractRetrievalSCH
{

	//#CM723787
	// Class variables -----------------------------------------------

	//#CM723788
	/**
	 * Class Name (Inventory Check) 
	 */
	public static String wProcessName = "RetrievalOrderCompSCH";

	//#CM723789
	// Class method --------------------------------------------------
	//#CM723790
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:57 $");
	}
	//#CM723791
	// Constructors --------------------------------------------------
	//#CM723792
	/**
	 * Initialize this class. 
	 */
	public RetrievalOrderCompleteSCH()
	{
		wMessage = null;
	}

	//#CM723793
	// Public methods ------------------------------------------------

	//#CM723794
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * If only one Consignor Code exists in Inventory Check Work Status Info, return the corresponding Consignor Code. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. <BR>
	 * @param conn Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions. 
	 * @return Class that implements the <CODE>Parameter</CODE> interface that contains the search result. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey workInfosearchKey = new WorkingInformationSearchKey();
		RetrievalSupportParameter param = new RetrievalSupportParameter();

		try
		{
			//#CM723795
			// Search through the Order Picking Work Status Info 
			workInfosearchKey.KeyClear();
			workInfosearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
			workInfosearchKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			workInfosearchKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			workInfosearchKey.setOrderNo("*");
			workInfosearchKey.setConsignorCodeGroup(1);
			workInfosearchKey.setConsignorCodeCollect("");

			if (workInfoHandler.count(workInfosearchKey) == 1)
			{
				//#CM723796
				// Search for the Consignor Code. 
				WorkingInformation wWorkInfo = (WorkingInformation) workInfoHandler.findPrimary(workInfosearchKey);
				//#CM723797
				// Set the search result for the return value. 
				param.setConsignorCode(wWorkInfo.getConsignorCode());
			}

		}
		catch (NoPrimaryException pe)
		{
			return param;
		}
		return param;
	}

	//#CM723798
	/**
	 * Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it.  <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class .  <BR>
	 * Invoking this process in unavailable case throws <CODE>ScheduleException</CODE>.  <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. 
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0. <BR>
	 *          Return null when input condition error occurs. <BR>
	 *          Returning null enables to obtain the error content as a message using the <CODE>getMessage()</CODE> method. <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter sparam = (RetrievalSupportParameter) searchParam;

		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey workInfosearchKey = new WorkingInformationSearchKey();

		//#CM723799
		// Check the Worker code and password 
		if (!checkWorker(conn, sparam))
		{
			return new RetrievalSupportParameter[0];
		}

		//#CM723800
		// Obtain the Consignor Name. 
		WorkingInformationSearchKey namesearchKey = new WorkingInformationSearchKey();
		//#CM723801
		// Obtain the target Work Status Info from the input info.
		workInfosearchKey.KeyClear();
		namesearchKey.KeyClear();

		//#CM723802
		// Consignor Code
		if (!StringUtil.isBlank(sparam.getConsignorCode()))
		{
			workInfosearchKey.setConsignorCode(sparam.getConsignorCode());
			namesearchKey.setConsignorCode(sparam.getConsignorCode());
		}

		//#CM723803
		// Planned Picking Date
		if (!StringUtil.isBlank(sparam.getRetrievalPlanDate()))
		{
			workInfosearchKey.setPlanDate(sparam.getRetrievalPlanDate());
			namesearchKey.setPlanDate(sparam.getRetrievalPlanDate());
		}

		//#CM723804
		// Order No.
		if (!StringUtil.isBlank(sparam.getOrderNo()))
		{
			workInfosearchKey.setOrderNo(sparam.getOrderNo());
			namesearchKey.setOrderNo(sparam.getOrderNo());
		}

		//#CM723805
		// Case/Piece division
		if (!StringUtil.isBlank(sparam.getCasePieceflg()))
		{
			if (sparam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				workInfosearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
				namesearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			else if (sparam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				workInfosearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
				namesearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			}
			else if (sparam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				workInfosearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
				namesearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			}
		}

		//#CM723806
		// Data with work type "Picking" 
		workInfosearchKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM723807
		// For data with Status flag "Started" only: 
		workInfosearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
		workInfosearchKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);

		//#CM723808
		// Set the Display order.
		workInfosearchKey.setItemCodeOrder(1, true);
		workInfosearchKey.setWorkFormFlagOrder(2, true);
		workInfosearchKey.setLocationNoOrder(3, true);
		workInfosearchKey.setUseByDateOrder(4, true);

		//#CM723809
		// Obtain the target info. 
		WorkingInformation[] wWorkInfo = (WorkingInformation[]) workInfoHandler.find(workInfosearchKey);

		//#CM723810
		// Ensure to enable to display the target data in the preset area.
		if (!canLowerDisplay(workInfoHandler.count(workInfosearchKey)))
		{
			return returnNoDisplayParameter();
		}

		//#CM723811
		// Data with work type "Picking" 
		namesearchKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM723812
		// For data with Status flag "Started" only: 
		namesearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
		namesearchKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		namesearchKey.setRegistDateOrder(1, false);
		WorkingInformation[] wName = (WorkingInformation[]) workInfoHandler.find(namesearchKey);
		String consignorname = wName[0].getConsignorName();

		Vector vec = new Vector();

		for (int lc = 0; lc < wWorkInfo.length; lc++)
		{
			RetrievalSupportParameter wparam = new RetrievalSupportParameter();

			//#CM723813
			// Compile it in the Work Status Info. 
			//#CM723814
			// Consignor Code
			wparam.setConsignorCode(wWorkInfo[lc].getConsignorCode());
			//#CM723815
			// Consignor Name
			wparam.setConsignorName(consignorname);
			//#CM723816
			// Planned Picking Date
			wparam.setRetrievalPlanDate(wWorkInfo[lc].getPlanDate());
			//#CM723817
			// Order No.
			wparam.setOrderNo(wWorkInfo[lc].getOrderNo());
			//#CM723818
			// Item Code
			wparam.setItemCode(wWorkInfo[lc].getItemCode());
			//#CM723819
			// Item Name
			wparam.setItemName(wWorkInfo[lc].getItemName1());
			//#CM723820
			// Work place 
			if (wWorkInfo[lc].getSystemDiscKey() == (RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR))
			{
				wparam.setSystemDiscKey(RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR);
				wparam.setSystemDiscKeyName(
					DisplayUtil.getSystemDiscKeyValue(RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR));
			}
			else if (wWorkInfo[lc].getSystemDiscKey() == (RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS))
			{
				wparam.setSystemDiscKey(RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS);
				wparam.setSystemDiscKeyName(
					DisplayUtil.getSystemDiscKeyValue(RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS));
			}
			//#CM723821
			// Case/Piece division
			wparam.setCasePieceflg(wWorkInfo[lc].getWorkFormFlag());
			//#CM723822
			// Case/Piece division name 
			wparam.setCasePieceflgName(DisplayUtil.getPieceCaseValue(wWorkInfo[lc].getWorkFormFlag()));
			//#CM723823
			// Total Picking qty (workable Qty) 
			wparam.setTotalRetrievalQty(wWorkInfo[lc].getPlanEnableQty());
			//#CM723824
			// Packed Qty per Case
			wparam.setEnteringQty(wWorkInfo[lc].getEnteringQty());
			//#CM723825
			// Packed qty per bundle
			wparam.setBundleEnteringQty(wWorkInfo[lc].getBundleEnteringQty());
			//#CM723826
			// Planned Case Qty (workable Qty/Packed Qty per Case) 
			wparam.setPlanCaseQty(
				DisplayUtil.getCaseQty(
					wWorkInfo[lc].getPlanEnableQty(),
					wWorkInfo[lc].getEnteringQty(),
					wWorkInfo[lc].getWorkFormFlag()));
			//#CM723827
			// Planned Piece Qty (workable Qty%Packed Qty per Case) 
			wparam.setPlanPieceQty(
				DisplayUtil.getPieceQty(
					wWorkInfo[lc].getPlanEnableQty(),
					wWorkInfo[lc].getEnteringQty(),
					wWorkInfo[lc].getWorkFormFlag()));
			//#CM723828
			// Picking Case Qty 
			wparam.setRetrievalCaseQty(
				DisplayUtil.getCaseQty(
					wWorkInfo[lc].getPlanQty(),
					wWorkInfo[lc].getEnteringQty(),
					wWorkInfo[lc].getWorkFormFlag()));
			//#CM723829
			// Picking Piece Qty 
			wparam.setRetrievalPieceQty(
				DisplayUtil.getPieceQty(
					wWorkInfo[lc].getPlanQty(),
					wWorkInfo[lc].getEnteringQty(),
					wWorkInfo[lc].getWorkFormFlag()));
			//#CM723830
			// Location No.
			wparam.setRetrievalLocation(wWorkInfo[lc].getLocationNo());
			//#CM723831
			// Expiry Date
			wparam.setUseByDate(wWorkInfo[lc].getUseByDate());
			//#CM723832
			// Case ITF
			wparam.setITF(wWorkInfo[lc].getItf());
			//#CM723833
			// Bundle ITF
			wparam.setBundleITF(wWorkInfo[lc].getBundleItf());
			//#CM723834
			// Work No.
			wparam.setJobNo(wWorkInfo[lc].getJobNo());
			//#CM723835
			// Last update date/time
			wparam.setLastUpdateDate(wWorkInfo[lc].getLastUpdateDate());
			//#CM723836
			// Store it in the VECTOR area. 
			vec.addElement(wparam);
		}

		//#CM723837
		// Parameter area for return 
		RetrievalSupportParameter[] rparam = new RetrievalSupportParameter[vec.size()];
		vec.copyInto(rparam);

		//#CM723838
		// 6001013 = Data is shown. 
		wMessage = "6001013";

		return rparam;
	}

	//#CM723839
	/** 
	 * Receive the contents entered via screen as a parameter and start-up the processes for updating the mandatory info and listing.  <BR>
	 * Obtain the data to be output to the preset area from the database after this process normally completed, and return it.  <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class .  <BR>
	 * Invoking this process in unavailable case throws <CODE>ScheduleException</CODE>.  <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param startParam Array of parameter object that contains setting info
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when this method is invoked. 
	 * @return Array of parameter object that contains search result.
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter[] param = (RetrievalSupportParameter[]) startParams;

		try
		{
			//#CM723840
			// Check the Worker code and password 
			if (!checkWorker(conn, param[0]))
			{
				return null;
			}
			//#CM723841
			// Check Daily Update Processing 
			if (isDailyUpdate(conn))
			{
				return null;
			}

			//#CM723842
			// Check for exclusion of all the target data. 
			if (!lockAll(conn, startParams))
			{
				//#CM723843
				// 6003006=Unable to process this data. It has been updated via other work station. 
				wMessage = "6003006";
				return null;
			}

			//#CM723844
			// Obtain the Worker name. 
			param[0].setWorkerName(getWorkerName(conn, param[0].getWorkerCode()));

			//#CM723845
			// Generate the instance of the class for the process to complete Picking. 
			RetrievalCompleteOperator compObj = new RetrievalCompleteOperator();

			WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey workInfosearchKey = new WorkingInformationSearchKey();

			int workQty = 0;
			for (int i = 0; i < startParams.length; i++)
			{
				RetrievalSupportParameter sparam = (RetrievalSupportParameter) startParams[i];

				WorkingInformation winfo = null;
				//#CM723846
				// Obtain the Work Status Info. 
				workInfosearchKey.KeyClear();
				workInfosearchKey.setJobNo(sparam.getJobNo());
				winfo = (WorkingInformation) workInfoHandler.findPrimaryForUpdate(workInfosearchKey);

				//#CM723847
				// Check for input. 
				if (!checkList(winfo, sparam))
				{
					return null;
				}

				//#CM723848
				// Update the Work Status Info. 
				updateWorkinfo(conn, winfo, sparam, param[0]);
				
				//#CM723849
				// Obtain the completed Work Status Info. 
				workInfosearchKey.KeyClear();
				workInfosearchKey.setJobNo(sparam.getJobNo());
				winfo = (WorkingInformation) workInfoHandler.findPrimaryForUpdate(workInfosearchKey);
				
				//#CM723850
				// Update the Plan Info. 
				updateRetrievalPlan(conn, winfo);

				//#CM723851
				// Process for completing  
				compObj.complete(conn, winfo.getJobNo(), wProcessName);

				//#CM723852
				// Total the Work Quantity. 
				workQty = addWorkQty(workQty, winfo.getResultQty());
				
			}
			
			//#CM723853
			// Execute the process to add or update the Worker Result info. 
			updateWorkerResult(
				conn,
				param[0].getWorkerCode(),
				param[0].getWorkerName(),
				getWorkDate(conn),
				param[0].getTerminalNumber(),
				WorkingInformation.JOB_TYPE_RETRIEVAL,
				workQty);

			//#CM723854
			// Succeeded in scheduling. 
			RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) this.query(conn, param[0]);
			//#CM723855
			// 6001014=Completed. 
			wMessage = "6001014";

			return viewParam;

		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}

	}

	//#CM723856
	/**
	 * Check for input based on the Work Status Info to be updated this time here and the input value. 
	 * Return true if the input value is correct, or return false if not correct. 
	 * 
	 * @param winfo Work Status Info to be updated this time here 
	 * @param param Data entered via screen 
	 * @return Result of checking
	 */
	protected boolean checkList(WorkingInformation winfo, RetrievalSupportParameter param)
		throws ReadWriteException
	{
		if (winfo == null)
		{
			//#CM723857
			// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
			wMessage = "6023209" + wDelim + param.getRowNo();
			return false;
		}

		//#CM723858
		// Check whether Work Status Info was updated in other process or not. 
		if (!winfo.getLastUpdateDate().equals(param.getLastUpdateDate()))
		{
			//#CM723859
			// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
			wMessage = "6023209" + wDelim + param.getRowNo();
			return false;
		}

		//#CM723860
		// Check for mandatory input in the Case Qty when entering the Packed qty per Case.  
		if (param.getRetrievalCaseQty() > 0 && winfo.getEnteringQty() == 0)
		{
			//#CM723861
			// 6023299=No.{0} If Packed Qty. per Case is 0, you cannot enter Picking Case Qty. 
			wMessage = "6023299" + wDelim + param.getRowNo();
			return false;
		}

		//#CM723862
		// Compare the values between Picking qty (Total) and Work Planned qty. 
		if ((param.getRetrievalCaseQty() * winfo.getEnteringQty() + param.getRetrievalPieceQty())
			> winfo.getPlanEnableQty())
		{
			//#CM723863
			// 6023211=No.{0} Please enter the planned quantity or less for the picking quantity at the completion of picking. 
			wMessage = "6023211" + wDelim + param.getRowNo();
			return false;
		}

		return true;

	}
	
	//#CM723864
	/**
	 * Complete the Work Status Info. 
	 * 
	 * @param conn Connection 
	 * @param winfo Work Status Info before update 
	 * @param param      RetrievalSupportParameter class instance with contents that were input via screen. 
	 * @param inputParam RetrievalSupportParameter class instance with contents that were input via screen. 
	 *                   (Maintain the leading value of the array) 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void updateWorkinfo(Connection conn, WorkingInformation winfo, RetrievalSupportParameter param, RetrievalSupportParameter inputParam)
		throws ReadWriteException
	{
		try
		{
			int compQty = param.getRetrievalCaseQty() * winfo.getEnteringQty() + param.getRetrievalPieceQty();
			int shortCnt = winfo.getPlanEnableQty() - compQty;
				
			WorkingInformationAlterKey wiAltKey = new WorkingInformationAlterKey();
			WorkingInformationHandler wih = new WorkingInformationHandler(conn);
			//#CM723865
			// Update the Work Status Info. (using the Work No.) 
			//#CM723866
			// Update status flag to "Completed". 
			//#CM723867
			// Count-up the Result qty and the Shortage Qty. 
			//#CM723868
			// Set the Result Expiry Date and Result Picking Location. 
			//#CM723869
			// Set the Worker Code, the Worker Name, and the Terminal No. 
			wiAltKey.KeyClear();
			wiAltKey.setJobNo(winfo.getJobNo());
			wiAltKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			wiAltKey.updateResultQty(winfo.getResultQty() + compQty);
			wiAltKey.updateShortageCnt(winfo.getShortageCnt() + shortCnt);
			wiAltKey.updateResultLocationNo(winfo.getLocationNo());
			wiAltKey.updateResultUseByDate(param.getUseByDate());
			wiAltKey.updateTerminalNo(inputParam.getTerminalNumber());
			wiAltKey.updateWorkerCode(inputParam.getWorkerCode());
			wiAltKey.updateWorkerName(inputParam.getWorkerName());
			wiAltKey.updateLastUpdatePname(wProcessName);

			wih.modify(wiAltKey);
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
	
	//#CM723870
	/**
	 * Process for completing the Picking Plan Info.
	 * 
	 * @param conn Connection 
	 * @param winfo Completed Work Status Info 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void updateRetrievalPlan(Connection conn, WorkingInformation winfo)
		throws ReadWriteException
	{
		RetrievalPlanHandler rePlanHandler = new RetrievalPlanHandler(conn);
		RetrievalPlanSearchKey rePlansearchKey = new RetrievalPlanSearchKey();
		RetrievalPlanAlterKey rePlanalterKey = new RetrievalPlanAlterKey();
		
		try
		{
			//#CM723871
			// Update the Picking Plan Info (using Plan unique key). 
			//#CM723872
			// Count-up the Result qty and the Shortage Qty. 
			//#CM723873
			// Update the Status flag. 
			//#CM723874
			// Load the Picking Work Status Info. 
			rePlansearchKey.KeyClear();
			rePlansearchKey.setRetrievalPlanUkey(winfo.getPlanUkey());
			RetrievalPlan plan = (RetrievalPlan) rePlanHandler.findPrimary(rePlansearchKey);
			
			
			//#CM723875
			// Work status:
			//#CM723876
			// For data with Planned qty equal to Completed qty, regard it as Completed. 
			//#CM723877
			// Otherwise: 
			//#CM723878
			// For Work Status Info with one or more data Working, regard it as Working. 
			//#CM723879
			// For Work Status Info with one or more data Started and no data Working, regard it as Started. 
			//#CM723880
			// For Work Status Info with no data Started and no data Working and one or more data Completed, regard it as Partially Completed. 
			String status = null;
			if (plan.getResultQty() + winfo.getResultQty() + plan.getShortageCnt() + winfo.getShortageCnt() == plan.getPlanQty())
			{
				status = RetrievalPlan.STATUS_FLAG_COMPLETION;
			}
			else
			{
				WorkingInformationHandler wih = new WorkingInformationHandler(conn);
				WorkingInformationSearchKey wiKey = new WorkingInformationSearchKey();
					
				wiKey.setPlanUkey(winfo.getPlanUkey());
				wiKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
				WorkingInformation[] winfos = (WorkingInformation[]) wih.find(wiKey);
				boolean existComplete = false;
				boolean existStart = false;
				boolean existNowWorking = false;
				for (int i = 0; i < winfos.length; i++)
				{
					if (winfos[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START))
					{
						existStart = true;
					}
					else if (winfos[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
					{
						existNowWorking = true;
					}
					else if (winfos[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
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
					//#CM723881
					// This status must be impossible.
				}

			}	
	
			//#CM723882
			// Update the Plan Info. 
			rePlanalterKey.KeyClear();
			rePlanalterKey.setRetrievalPlanUkey(winfo.getPlanUkey());
			rePlanalterKey.updateResultQty(plan.getResultQty() + winfo.getResultQty());
			rePlanalterKey.updateShortageCnt(plan.getShortageCnt() + winfo.getShortageCnt());
			rePlanalterKey.updateStatusFlag(status);
			rePlanalterKey.updateLastUpdatePname(wProcessName);
			rePlanHandler.modify(rePlanalterKey);
			
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
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

}
//#CM723883
//end of class
