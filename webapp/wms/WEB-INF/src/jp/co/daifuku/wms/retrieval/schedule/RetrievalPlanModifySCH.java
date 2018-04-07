package jp.co.daifuku.wms.retrieval.schedule;

//#CM724813
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Worker;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.dbhandler.RetrievalStockHandler;

//#CM724814
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this class to modify/delete WEB Picking plan data.  <BR>
 * Receive the contents entered via screen as a parameter, and execute the process to modify/delete the Picking plan data.  <BR>
 * Allow each method contained in this class to receive the Connection object and execute the process for updating the database, but
 * disable to commit nor roll-back the transaction.  <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * <B>1. Process for Initial Display (<CODE>initFind</CODE> method) </B><BR>
 *   Return the <CODE>Parameter</CODE> instance including the corresponding Consignor Code, if only one Consignor Code exists in the Picking Plan Info.  <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
 *   <BR>
 *   <Search conditions>
 *   <DIR>
 *     Status flag: Standby 
 *   </DIR>
 * <BR>
 * <B>2. Process by clicking "Next" button (1)(<CODE>nextCheck</CODE> method) </B><BR>
 *   Receive the contents entered via screen as a parameter, and check the input information. <BR>
 *   Return true if the input is correct, or return false if not correct. <BR>
 *   Use <CODE>getMessage()</CODE> method to obtain the content of the error.<BR>
 *   Check the following matters. <BR>
 *   <DIR>
 *     1.Check the Worker Code and Password. <BR>
 *   </DIR>
 *   <BR>
 *   <Parameter> *Mandatory Input
 *   <DIR>
 *     Worker Code* <BR>
 *     Password * 
 *   </DIR>
 * <BR>
 * <B>3. Process by clicking "Next" button (2)(<CODE>query</CODE> method) </B><BR>
 *   Receive the contents entered via screen as a parameter, and search for the data for output to the preset area through the database, and obtain it and return it. <BR>
 *   If no corresponding data is found, return <CODE>RetrievalSupportParameter</CODE> array with number of elements equal to 0. Or, return null when condition error or similar occurs. <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error.<BR>
 *   Obtain the Work Status Info data with status flag Standby only, and display them in the order of Case Picking Location, Piece Picking Location, and then Customer Code.  <BR>
 *   Invoke this method only when the return value of nextCheckmethod is true. <BR>
 *   Check the following matters. <BR>
 *   <BR>
 *   <DIR>
 *     1.Require that data corresponding to the input info exists in the Picking Plan Info table. 
 *   </DIR>
 *   <BR>
 *   <Search conditions>
 *   <DIR>
 *     - Status flag: Standby <BR>
 *   </DIR>
 *   <BR>
 *   <Parameter> *Mandatory Input
 *   <DIR>
 *     Consignor Code* <BR>
 *     Planned Picking Date* <BR>
 *     Item Code* <BR>
 *     Case Order No. (for display)  <BR>
 *     Piece Order No. (for display)  <BR>
 *     Customer Code (for display)  <BR>
 *     Case Picking Location (for display)  <BR>
 *     Piece Picking Location (for display) 
 *   </DIR>    
 *   <BR>
 *   <Returned data>
 *   <DIR>
 *     Consignor Code <BR>
 *     Consignor Name (with the latest Added Date/Time)  <BR>
 *     Planned Picking Date <BR>
 *     Item Code <BR>
 *     Item Name (Latest Added Date/Time)  <BR>
 *     Case Picking Location<BR>
 *     Piece Picking Location <BR>
 *     Customer Code <BR>
 *     Customer Name <BR>
 *     Case Order No.<BR>
 *     Piece Order No.<BR>
 *     Ticket No. (Shipping Ticket No)  <BR>
 *     Line No. (Shipping ticket line No.)  <BR>
 *     Packed Qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Host planned Case qty  <BR>
 *     Host Planned Piece Qty <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Picking Plan unique key <BR>
 *     Last update date/time <BR>
 *   </DIR>
 * <BR>
 * <B>4. Process by clicking on the Enter button(<CODE>check</CODE> method) </B><BR>
 *   Receive the contents entered via screen as a parameter, and check it and return the check result.  <BR>
 *   True if any proper value is input or if the status flag and the last update date/time of the corresponding data are not changed. 
 *   Return false when condition error or excusive error occurs.  <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error. <BR>
 *   Check the following matters. <BR>
 *   <DIR>
 *     1.Ensure that values are input in the fields of Consignor Code, Planned Picking Date, and Item Code. <BR>
 *     2. If inventory control package is disabled, require to have a value in either Case or Piece Picking Location. <BR>
 *     3. There must be an input in Customer Code when there is an input either Case/Piece Order No. <BR>
 *     4.Require that a value equal to 1 or larger is input in either one of Planned Case Qty or Planned Piece Qty. <BR>
 *     5. Ensure that the Packed qty per Case is 1 or larger if any value is entered in the Case qty. <BR>
 *     6.If the input information is a data with division None, require that the same value is input in both Case Order No. and Piece Order No. <BR>
 *     7.If the input information is a data with division other than None, require that any value is input in both Case Order No. and Piece Order No. or blank in both Case Order No. and Piece Order No. <BR>
 *     8.Ensure not to overflow. <BR>
 *     9.Require the Input data not to have been updated via other terminal. <BR>
 *     <DIR>
 *      - Require that the corresponding Work Status Info exists. <BR>
 *      - Require that the corresponding picking plan info exists. <BR>
 *      - Require that the last update date/time of the corresponding picking plan info is not modified. <BR>
 *     </DIR>
 *     10.Require that there is no data with the same content in the preset area. <BR>
 *     11.Require that no data exists in the Picking Plan Info, with the same content and with status other than Standby or Deleted. <BR>
 *   </DIR>
 *   <BR>
 *   <Parameter> *Mandatory input +Mandatory depending on the condition 
 *   <DIR>
 *     Consignor Code* <BR>
 *     Planned Picking Date* <BR>
 *     Item Code* <BR>
 *     Case Picking Location (Mandatory input in either Case or Piece Picking Location) *<BR>
 *     Piece Picking Location (Mandatory of either Case or Piece Picking Location) *<BR>
 *     Customer Code <BR>
 *     Customer Name <BR>
 *     Case Order No. (Mandatory either Case/Piece Order No.)+ <BR>
 *     Piece Order No. (Mandatory either Case or Piece Order No.) +<BR>
 *     Ticket No. (Shipping Ticket No)  <BR>
 *     Line No. (Shipping ticket line No.)  <BR>
 *     Packed Qty per Case+ <BR>
 *     Packed qty per bundle <BR>
 *     Planned Case Qty (Mandatory of either Host Planned Case or Piece Qty) * <BR>
 *     Planned Piece Qty (Mandatory of either Host Planned Case or Piece Qty) * <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Picking Plan unique key* <BR>
 *     Last update date/time* <BR>
 *   </DIR>
 * <BR>
 * <B>5.Process by clicking on Delete/Delete All button (<CODE>startSCH</CODE> method) </B><BR>
 *   Receive the contents displayed in the preset area, as a parameter, and execute the process to delete the Picking plan data.  <BR>
 *   For data with Inventory package disabled, execute the process for canceling the allocation using the cancel method of StockAllocateOperater. <BR>
 *   Return true when the process normally completed, or return false when the schedule failed to complete due to condition error or other causes.  <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error. <BR>
 *   <BR>
 *   <Update table > 
 *   <DIR>
 *     - Work Status Info<BR>
 *     - Picking Plan Info <BR>
 *     - Inventory Info <BR>
 *   </DIR>
 *   <BR>
 *   <Parameter> *Mandatory Input
 *   <DIR>
 *     Worker Code* <BR>
 *     Password * 
 *     Picking Plan unique key* <BR>
 *     Last update date/time* <BR>
 *     Terminal No.* <BR>
 *     Preset line No. * <BR>
 *   </DIR>
 *   <BR>
 *   <Check Process Condition> 
 *   <DIR>
 *     1. Require not to be in the process of daily update. <BR>
 *     2.Require to locate the data of Picking Plan unique key in the Work Status Info table (Check Exclusion.). <BR>
 *     3.Require to locate the data of Picking Plan unique key in the Picking Plan Info table (Check Exclusion.) <BR>
 *     4.Require the parameter's Last update date/time to be consistent with the Last update date/time in the Picking Plan Info (Check Exclusion.). <BR>
 *   </DIR>
 *   <BR>
 * <B>6. Process by clicking Modify/Add button(<CODE>startSCHgetParams</CODE> method) </B><BR>
 *   Receive the contents displayed in the preset area, as a parameter, and execute the process to modify the Picking plan data.  <BR>
 *   Re-obtain the data to be output to the preset area from database when the process normally completed, and return it.  <BR>
 *   Return null when the schedule failed to complete due to condition error or other causes.  <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error. <BR>
 *   <BR>
 *   <Update table > 
 *   <DIR>
 *     - Work Status Info<BR>
 *     - Picking Plan Info <BR>
 *     - Inventory Info <BR>
 *   </DIR>
 *   <BR>
 *   <Parameter>
 *   <DIR>
 *     Worker Code* <BR>
 *     Password * 
 *     Case Picking Location (for display) <BR>
 *     Piece Picking Location (for display) <BR>
 *     Customer Code (for display)  <BR>
 *     Case Order No. (for display) <BR>
 *     Piece Order No. (for display) <BR>
 *     Consignor Code <BR>
 *     Planned Picking Date <BR>
 *     Item Code <BR>
 *     Case Picking Location<BR>
 *     Piece Picking Location <BR>
 *     Customer Code <BR>
 *     Customer Name <BR>
 *     Case Order No.<BR>
 *     Piece Order No.<BR>
 *     Ticket No. (Shipping Ticket No)  <BR>
 *     Line No. (Shipping ticket line No.)  <BR>
 *     Packed Qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Host planned Case qty  <BR>
 *     Host Planned Piece Qty <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Picking Plan unique key* <BR>
 *     Last update date/time* <BR>
 *     Terminal No.* <BR>
 *     Preset line No. * <BR>
 *   </DIR>
 *   <BR>
 *   <Returned data>
 *   <DIR>
 *     Consignor Code <BR>
 *     Consignor Name (with the latest Added Date/Time)  <BR>
 *     Planned Picking Date <BR>
 *     Item Code <BR>
 *     Item Name (Latest Added Date/Time)  <BR>
 *     Case Picking Location<BR>
 *     Piece Picking Location <BR>
 *     Customer Code <BR>
 *     Customer Name <BR>
 *     Case Order No.<BR>
 *     Piece Order No.<BR>
 *     Ticket No. (Shipping Ticket No)  <BR>
 *     Line No. (Shipping ticket line No.)  <BR>
 *     Packed Qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Host planned Case qty  <BR>
 *     Host Planned Piece Qty <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Picking Plan unique key <BR>
 *     Last update date/time <BR>
 *   </DIR>
 *   <BR>
 *   <Check Process Condition> 
 *   <DIR>
 *     1. Require not to be in the process of daily update. <BR>
 *     2.Require to locate the data of Picking Plan unique key in the Work Status Info table (Check Exclusion.). <BR>
 *     3.Require to locate the data of Picking Plan unique key in the Picking Plan Info table (Check Exclusion.) <BR>
 *     4.Require the parameter's Last update date/time to be consistent with the Last update date/time in the Picking Plan Info (Check Exclusion.). <BR>
 *   </DIR>
 *   <BR>
 *   <Update Process><BR>
 *   <BR>
 *   <DIR>
 *     1.Update the information linked to the Picking Plan unique key of the input info to Deleted.<BR>
 *       <DIR>
 *       - For data with Inventory package disabled, use <CODE>cancel</CODE> method of <CODE>RetrievalAllocateOperator</CODE>. <BR>
 *       - Update the Work Status Info (Inventory package: disabled), Inventory Info (Inventory package: disabled), and Picking Plan Info. 
 *       </DIR>
 *     2.Generate a new Picking Plan Info based on the input info.<BR>
 *       <DIR>
 *       - Use the <CODE>createRetrievalPlan</CODE> method of <CODE>RetrievalPlanOperator</CODE>. <BR>
 *       - Disable to execute any process if there is the same data with status Workingalreaday. 
 *       </DIR>
 *     3.If the Picking Plan Info is generated successfully and the Inventory package is disabled, update the Work Status Info and the Inventory Info via the Plan info. <BR>
 *       <DIR>
 *       - For data with Inventory package disabled, using <CODE>allocate</CODE> method of <CODE>RetrievalAllocateOperator</CODE>, 
 *         update the Work Status Info and the Inventory Info. 
 *       </DIR>
 *     4.Search again the data to be displayed.<BR>
 *       <DIR>
 *       - Search through the Picking Plan Info again to display data in the preset area. <BR>
 *       - Use the <CODE>query</CODE> method. 
 *       </DIR>
 *   </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/20</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2007/02/07 04:20:02 $
 * @author  $Author: suresh $
 */
public class RetrievalPlanModifySCH extends AbstractRetrievalSCH
{

	//#CM724815
	// Class variables -----------------------------------------------
	//#CM724816
	/**
	 * Process name 
	 */
	private static String wProcessName = "RetrievalPlanModifySCH";

	//#CM724817
	// Class method --------------------------------------------------
	//#CM724818
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.4 $,$Date: 2007/02/07 04:20:02 $");
	}
	//#CM724819
	// Constructors --------------------------------------------------
	//#CM724820
	/**
	 * Initialize this class. 
	 */
	public RetrievalPlanModifySCH()
	{
		wMessage = null;
	}

	//#CM724821
	// Public methods ------------------------------------------------
	//#CM724822
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * Return the Consignor Code, if only one Consignor Code exists in the Picking Plan Info. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. 
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * @return Class that implements the <CODE>RetrievalSupportParameter</CODE> interface that contains the search result. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		//#CM724823
		// Returned data 
		RetrievalSupportParameter wParam = new RetrievalSupportParameter();

		//#CM724824
		// Generate instance of handlers for Picking Plan information. 
		RetrievalPlanSearchKey retPlanKey = new RetrievalPlanSearchKey();
		RetrievalPlanHandler retPlanHandler = new RetrievalPlanHandler(conn);
		RetrievalPlan wRetrieval = null;

		try
		{
			//#CM724825
			// Set the search conditions. 
			//#CM724826
			// Status flag: "Standby" 
			retPlanKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
			//#CM724827
			// Schedule process flag 
			if (isStockPack(conn))
			{
				//#CM724828
				// Not Processed 
				retPlanKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=");
			}
			retPlanKey.setConsignorCodeGroup(1);
			retPlanKey.setConsignorCodeCollect("");

			if (retPlanHandler.count(retPlanKey) == 1)
			{
				//#CM724829
				// Obtain the count of the corresponding Consignor Code data. 
				wRetrieval = (RetrievalPlan) retPlanHandler.findPrimary(retPlanKey);

				//#CM724830
				// If the count is one: 
				if (wRetrieval != null)
				{
					//#CM724831
					// Obtain the corresponding Consignor Code and set it for the return parameter. 
					wParam.setConsignorCode(wRetrieval.getConsignorCode());
				}
			}

		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

		return wParam;
	}

	//#CM724832
	/**
	 * Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0. <BR>
	 *          If the number of search results exceeds the Max count of data allowable to display, or if error occurs with the input condition, return null. <BR>
	 *          Returning array with the number of elements 0 or null enables to obtain the error content as a message using the <CODE>getMessage</CODE> method. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		//#CM724833
		// Returned data 
		RetrievalSupportParameter[] resultParam = null;

		RetrievalSupportParameter param = (RetrievalSupportParameter) searchParam;

		//#CM724834
		// Search for the corresponding info through the DnRetrievalPlan. 
		RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn);
		RetrievalPlanSearchKey planKey = new RetrievalPlanSearchKey();
		RetrievalPlan[] retrieval = null;

		//#CM724835
		// Set the search conditions.
		planKey = setConditionSearchKey(conn, param);
		//#CM724836
		// Set the Display order. 
		planKey.setCaseLocationOrder(1, true);
		planKey.setPieceLocationOrder(2, true);
		planKey.setCustomerCodeOrder(3, true);

		//#CM724837
		// Obtain the count of data to be displayed. 
		if(!canLowerDisplay(planHandler.count(planKey)))
		{
			return returnNoDisplayParameter();
		}
						
		//#CM724838
		// Obtain the content to be displayed. 
		retrieval = (RetrievalPlan[]) planHandler.find(planKey);

		//#CM724839
		// Consignor Name
		String consignorName = "";
		//#CM724840
		// Item Name
		String itemName = "";
		//#CM724841
		// Obtain the Consignor and Picking destination name with the latest Added Date/Time 
		RetrievalSupportParameter nameParam = getDisplayName(conn, param);
		if (nameParam != null)
		{
			consignorName = nameParam.getConsignorName();
			itemName = nameParam.getItemName();
		}

		//#CM724842
		// Set the search result in the returned data. 
		Vector resultVec = new Vector();
		for (int i = 0; i < retrieval.length; i++)
		{
			RetrievalSupportParameter wTemp = new RetrievalSupportParameter();
			//#CM724843
			// Consignor Code
			wTemp.setConsignorCode(retrieval[i].getConsignorCode());
			//#CM724844
			// Consignor Name
			wTemp.setConsignorName(consignorName);
			//#CM724845
			// Planned Picking Date
			wTemp.setRetrievalPlanDate(retrieval[i].getPlanDate());
			//#CM724846
			// Item Code
			wTemp.setItemCode(retrieval[i].getItemCode());
			//#CM724847
			// Item Name
			wTemp.setItemName(itemName);
			//#CM724848
			// Case Picking Location
			wTemp.setCaseLocation(retrieval[i].getCaseLocation());
			//#CM724849
			// Piece Picking Location 
			wTemp.setPieceLocation(retrieval[i].getPieceLocation());
			//#CM724850
			// Customer Code
			wTemp.setCustomerCode(retrieval[i].getCustomerCode());
			//#CM724851
			// Customer Name
			wTemp.setCustomerName(retrieval[i].getCustomerName1());
			//#CM724852
			// Case Order No.
			wTemp.setCaseOrderNo(retrieval[i].getCaseOrderNo());
			//#CM724853
			// Piece Order No.
			wTemp.setPieceOrderNo(retrieval[i].getPieceOrderNo());
			//#CM724854
			// Ticket No. (Shipping Ticket No) 
			wTemp.setShippingTicketNo(retrieval[i].getShippingTicketNo());
			//#CM724855
			// Shipping ticket line No. (Shipping ticket line No.) 
			wTemp.setShippingLineNo(retrieval[i].getShippingLineNo());
			//#CM724856
			// Packed Qty per Case
			wTemp.setEnteringQty(retrieval[i].getEnteringQty());
			//#CM724857
			// Packed qty per bundle
			wTemp.setBundleEnteringQty(retrieval[i].getBundleEnteringQty());
			//#CM724858
			// Host planned Case qty 
			wTemp.setPlanCaseQty(DisplayUtil.getCaseQty(retrieval[i].getPlanQty(), retrieval[i].getEnteringQty()));
			//#CM724859
			// Host Planned Piece Qty
			wTemp.setPlanPieceQty(DisplayUtil.getPieceQty(retrieval[i].getPlanQty(), retrieval[i].getEnteringQty()));
			//#CM724860
			// Case ITF
			wTemp.setITF(retrieval[i].getItf());
			//#CM724861
			// Bundle ITF
			wTemp.setBundleITF(retrieval[i].getBundleItf());
			//#CM724862
			// Picking Plan unique key
			wTemp.setRetrievalPlanUkey(retrieval[i].getRetrievalPlanUkey());
			//#CM724863
			// Last update date/time
			wTemp.setLastUpdateDate(retrieval[i].getLastUpdateDate());
			resultVec.addElement(wTemp);
		}
		
		resultParam = new RetrievalSupportParameter[resultVec.size()];
		resultVec.copyInto(resultParam);
		
		//#CM724864
		// 6001013 = Data is shown. 
		wMessage = "6001013";
		return resultParam;
	}

	//#CM724865
	/** 
	 * Check whether the contents of the parameter is proper or not.<BR>
	 * Use <CODE>getMessage()</CODE> method to obtain the content of the error.<BR>
	 * @param conn Instance to maintain database connection. 
	 * @param checkParam <CODE>RetrievalSupportParameter</CODE> class instance that contains the info to be checked for input
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * @return	Result of checking (No error: true,  Error occurring: false) 
	 * @throws	ReadWriteException Announce when error occurs on the database connection.
	 * @throws	ScheduleException Announce it when unexpected exception occurs in the process of scheduling. 
	 */
	public boolean check(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		if (isStockPack(conn))
		{
			return inputCheckStockOn(conn, checkParam);
		}
		else
		{
			return inputCheckStockOff(conn, checkParam);
		}
	}

	//#CM724866
	/** 
	 * Receive the Input data and the info in the preset area, as a parameter, and 
	 * execute input check, overflow check, exclusion check, and check for double entry across the preset area or double entry across the DB, and return the check result.  <BR>
	 * Return true if the input is correct, or return false if input error occurs.  <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param checkParam <CODE>RetrievalSupportParameter</CODE> class instance with the input contents <BR>
	 *        Designating any instance other than <CODE>RetrievalSupportParameter</CODE> instance throws <CODE>ScheduleException</CODE>. <BR>
	 *        Use <CODE>getMessage()</CODE> method to refer to the content of the error.
	 * @param inputParams Array of the Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has Contents of Preset area  <BR>
	 *        Designating any instance other than <CODE>RetrievalSupportParameter</CODE> instance throws <CODE>ScheduleException</CODE>. <BR>
	 *        Use <CODE>getMessage()</CODE> method to refer to the content of the error.
	 * @return true if the schedule process completed normally, or false if the schedule process failed. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams) throws ReadWriteException, ScheduleException
	{
		
			//#CM724867
			// Contents of the input area 
			RetrievalSupportParameter inputParam = (RetrievalSupportParameter) checkParam;
			//#CM724868
			// Contents of Preset area 
			RetrievalSupportParameter[] listParam = (RetrievalSupportParameter[]) inputParams;

			//#CM724869
			// Ensure that values are input in the fields of Consignor Code, Planned Picking Date, and Item Code. 
			if (StringUtil.isBlank(inputParam.getConsignorCode())
				|| StringUtil.isBlank(inputParam.getRetrievalPlanDate())
				|| StringUtil.isBlank(inputParam.getItemCode()))
			{
				throw new ScheduleException();
			}

			//#CM724870
			// If inventory control package is disabled, require to input a value in the Location. (mandatory) 
			if (!isStockPack(conn))
			{
				//#CM724871
				// There must be an input either Case/Piece Picking Location. 
				if (StringUtil.isBlank(inputParam.getCaseLocation()) && StringUtil.isBlank(inputParam.getPieceLocation()))
				{
					//#CM724872
					// 6023323=Enter {0} or {1}. 
					//#CM724873
					// LBL-W0017=Case Picking Location LBL-W0152=Piece Picking Location 
					wMessage = "6023323" + wDelim + DisplayText.getText("LBL-W0017") + wDelim + DisplayText.getText("LBL-W0152");
					return false;
				}
			}

			//#CM724874
			// There must be an input in Customer Code when there is an input either Case/Piece Order No. 
			if (!StringUtil.isBlank(inputParam.getCaseOrderNo()) || !StringUtil.isBlank(inputParam.getPieceOrderNo()))
			{
				if (StringUtil.isBlank(inputParam.getCustomerCode()))
				{
					//#CM724875
					// 6023007=Please enter the customer code. 
					wMessage = "6023007";
					return false;
				}
			}

			//#CM724876
			// Require that a value equal to 1 or larger is input in either one of Planned Case Qty or Planned Piece Qty. 
			if (inputParam.getPlanCaseQty() <= 0 && inputParam.getPlanPieceQty() <= 0)
			{
				//#CM724877
				// 6023383=Enter 1 or greater value for Host Planned Case Qty. or Host Planned Piece Qty. 
				wMessage = "6023383";
				return false;
			}

			//#CM724878
			// Ensure that the Packed qty per Case is 1 or larger if any value is entered in the Case qty. 
			if (inputParam.getPlanCaseQty() > 0 && inputParam.getEnteringQty() <= 0)
			{
				//#CM724879
				// 6023019=Please enter 1 or greater in the packed quantity per case. 
				wMessage = "6023019";
				return false;
			}

			//#CM724880
			// Obtain the Case/Piece division of the input information and set it for the parameter for convenience. 
			RetrievalPlanOperator planOperator = new RetrievalPlanOperator(conn);
			inputParam.setTotalPlanQty(inputParam.getEnteringQty() * inputParam.getPlanCaseQty() + inputParam.getPlanPieceQty());
			inputParam.setCasePieceflg(planOperator.getCasePieceFlag(inputParam));

			//#CM724881
			// For data with division None, regard it as error if both its Case Order No. and its Piece Order No. are input and these values are different. 
			if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				if ((!StringUtil.isBlank(inputParam.getCaseOrderNo()) && !StringUtil.isBlank(inputParam.getPieceOrderNo()))
					&& !inputParam.getCaseOrderNo().equals(inputParam.getPieceOrderNo()))
				{
					//#CM724882
					// 6023338=You cannot enter different values in Case Order No. and Piece Order No. 
					wMessage = "6023338";
					return false;
				}
			}
			//#CM724883
			// For data with division other than None (Mixed, Case, or Piece), regard as error if value is input in only one of divisions for the Order No. 
			else
			{
				if ((!StringUtil.isBlank(inputParam.getCaseOrderNo()) && StringUtil.isBlank(inputParam.getPieceOrderNo()))
					|| (StringUtil.isBlank(inputParam.getCaseOrderNo()) && !StringUtil.isBlank(inputParam.getPieceOrderNo())))
				{
					//#CM724884
					// 6023358=Both Case Order No. and Piece Order No. must be entered. 
					wMessage = "6023358";
					return false;
				}
			}

			//#CM724885
			// Check for overflow. 
			if ((long) inputParam.getEnteringQty() * (long) inputParam.getPlanCaseQty() + (long) inputParam.getPlanPieceQty() > (long) WmsParam.MAX_TOTAL_QTY)
			{
				//#CM724886
				// 6023058=Please enter {1} or smaller for {0}. 
				wMessage = "6023058" + wDelim + DisplayText.getText("LBL-W0379") + wDelim + MAX_TOTAL_QTY_DISP;
				return false;
			}

			//#CM724887
			// Check for exclusion. 
			if (!check(conn, inputParam))
			{
				//#CM724888
				// Close the process when error via other terminal occurs. 
				//#CM724889
				// 6003006=Unable to process this data. It has been updated via other work station. 
				wMessage = "6003006";
				return false;
			}

			//#CM724890
			// Check for duplication across the preset area. (Set Message within a method) 
			if (listParam != null)
			{
				if (!dupplicationCheck(inputParam, listParam))
					return false;
			}

			//#CM724891
			// Check for duplication across the DB. (Check the Picking Plan Info) 
			RetrievalPlan[] retrieval = planOperator.findRetrievalPlan(inputParam);
			if (retrieval != null)
			{
				for (int i = 0; i < retrieval.length; i++)
				{
					
					//#CM724892
					// Disable to add any data with status Partially Completed. 
					if (retrieval[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART))
					{
						//#CM724893
						// 6023090=The data already exists. Unable to input. 
						wMessage = "6023090";
						return false;
					}
					//#CM724894
					// Disable to add any data with status Started or Working. 
					else if (
						(retrieval[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_START))
							|| (retrieval[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_NOWWORKING)))
					{
						//#CM724895
						// 6023090=The data already exists. Unable to input. 
						wMessage ="6023090";
						return false;
					}
					//#CM724896
					// For data with status "Completed", disable to add it. 
					else if (retrieval[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_COMPLETION)
							|| retrieval[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_UNSTART))
					{
						wMessage ="6023090";
						return false;
					}
					
				}
			}

			//#CM724897
			// 6001019=Entry was accepted. 
			wMessage = "6001019";
			return true;

	}

	//#CM724898
	/**
	 * Receive the contents entered via screen as a parameter, and check it.  <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * @param conn Database connection object
	 * @param checkParam <CODE>RetrievalSupportParameter</CODE> class instance with the input contents <BR>
	 *        Designating any instance other than <CODE>RetrievalSupportParameter</CODE> instance throws <CODE>ScheduleException</CODE>. <BR>
	 *        Use <CODE>getMessage()</CODE> method to refer to the content of the error.
	 * @return true when the schedule process normally completed, or false when it failed or not allowed. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling. 
	 */
	public boolean nextCheck(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter param = (RetrievalSupportParameter) checkParam;
		//#CM724899
		// Check the Worker code and password 
		if (!checkWorker(conn, param))
		{
			return false;
		}

		return true;
	}

	//#CM724900
	/**
	 * Receive the contents entered via screen as a parameter, and start the schedule to delete the Picking plan data. <BR>
	 * Assume that two or more data may be input, including setting via preset area. So, require the parameter to receive them as an array. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> instance throws <CODE>ScheduleException</CODE>. <BR>
	 *         Use <CODE>getMessage()</CODE> method to refer to the content of the error.
	 * @return Result of delete (Normal: true, Error: false) 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		boolean registFlag = false;

		//#CM724901
		// Allow this flag to determine whether Processing Load flag is updated in its own class. 
		boolean updateLoadDataFlag = false;

		try
		{
			//#CM724902
			// Check the Daily Update Processing. 
			if (isDailyUpdate(conn))
			{
				return registFlag;
			}
			//#CM724903
			// Execute the process for checking the Load flag.  false: Loading in process 	
			if (isLoadingData(conn))
			{				
				return registFlag;
			}
			//#CM724904
			// Update "Load" flag: "Loading in process" 
			if (!updateLoadDataFlag(conn, true))
			{
				return false;
			}
			doCommit(conn,wProcessName);
			updateLoadDataFlag = true;
			
			//#CM724905
			// Delete the Plan data. 
			if (isStockPack(conn))
			{
				registFlag = deleteRetrievalPlanStockOn(conn, startParams);
				return registFlag;
			}
			else
			{
				registFlag = deleteRetrievalPlanStockOff(conn, startParams);
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
			//#CM724906
			// Failing to add rolls back the transaction. 
			if (!registFlag)
			{		
				doRollBack(conn,wProcessName);		
			}
	
			//#CM724907
			// If Processing Extraction flag was updated in its own class,
			//#CM724908
			// update the Loading in-process flag to 0: Stopping. 
			if( updateLoadDataFlag )
			{
				//#CM724909
				// Update the Loading-in-progress flag: Stopping 
				updateLoadDataFlag(conn, false);
				doCommit(conn,wProcessName);
			}
		}
	}

	//#CM724910
	/**
	 * Receive the contents entered via screen as a parameter, and start the schedule to modify the Picking plan data. <BR>
	 * Assume that two or more data may be input, including setting via preset area. So, require the parameter to receive them as an array. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * Re-obtain the data to be output to the preset area from database when the process normally completed, and return it.  <BR>
	 * Return null when the schedule failed to complete due to condition error or other causes. 
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 *         Designating any instance other than RetrievalSupportParameter instance throws <CODE>ScheduleException</CODE>. <BR>
	 *         Use <CODE>getMessage()</CODE> method to refer to the content of the error.
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0. <BR>
	 *          Return null when the schedule failed to complete due to condition error or other causes. <BR>
	 *          Returning array with the number of elements 0 or null enables to obtain the error content as a message using the <CODE>getMessage</CODE> method. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		//#CM724911
		// For judgment of commit and rollback of DB 
		boolean registFlag = false;

		//#CM724912
		// Allow this flag to determine whether Processing Load flag is updated in its own class. 
		boolean updateLoadDataFlag = false;

		try
		{
			//#CM724913
			// Check the Daily Update Processing. 
			if (isDailyUpdate(conn))
			{
				return null;
			}
			//#CM724914
			// Execute the process for checking the Load flag.  null: Loading in process 
			if (isLoadingData(conn))
			{
				return null;
			}
			//#CM724915
			// Update "Load" flag: "Loading in process" 
			if (!updateLoadDataFlag(conn, true))
			{
				return null;
			}
			doCommit(conn,wProcessName);
			updateLoadDataFlag = true;

			//#CM724916
			// Delete the Plan data. 
			if (isStockPack(conn))
			{
				registFlag = modifyRetrievalPlanStockOn(conn, startParams);
			}
			else
			{
				registFlag = modifyRetrievalPlanStockOff(conn, startParams);
			}
			
			//#CM724917
			// Input information of the parameter
			RetrievalSupportParameter[] param = (RetrievalSupportParameter[]) startParams;
			
			if (registFlag)
			{
				//#CM724918
				// Search through the Picking Plan Info again. 
				RetrievalSupportParameter[] viewParam = (RetrievalSupportParameter[]) this.query(conn, param[0]);
				//#CM724919
				// 6001004=Modified. 
				wMessage = "6001004";
				return viewParam;
			}
			else
			{
				return null;
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
			//#CM724920
			// Failing to add rolls back the transaction. 
			if (!registFlag)
			{
				doRollBack(conn,wProcessName);
			}
			
			//#CM724921
			// If Processing Extraction flag was updated in its own class,
			//#CM724922
			// update the Loading in-process flag to 0: Stopping. 
			if( updateLoadDataFlag )
			{
				//#CM724923
				// Update the Loading-in-progress flag: Stopping 
				updateLoadDataFlag(conn, false);
				doCommit(conn,wProcessName);
			}
		}

	}

	//#CM724924
	// Package methods -----------------------------------------------

	//#CM724925
	// Protected methods ---------------------------------------------

	//#CM724926
	/**
	 * Search for the target Work Status Info and Picking Plan Info to be updated, and lock it. <BR>
	 * Return true if the target data to update is normally searched and locked, or return false if the search failed due to error caused by other terminal. <BR>
	 * Regard the following cases as error via other terminal. <BR>
	 * <DIR>
	 *   - If there is no search result after searching through the Work Status Info: <BR>
	 *   - If no search result was obtained after searching through the Picking Plan Info <BR>
	 *   - Search through the Picking Plan Info. If the data displayed on screen is different from the Last update date/time: 
	 * </DIR>
	 * @param conn Instance to maintain database connection. 
	 * @param inputParam <CODE>RetrievalSupportParameter</CODE> that contains the target data to be updated
	 * @return Determine whether successfully searched the data to be updated and locked it. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected boolean lock(Connection conn, RetrievalSupportParameter inputParam) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM724927
			// Search through the DnWorkInfo. 
			WorkingInformationHandler winfoHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey winfoKey = new WorkingInformationSearchKey();
			WorkingInformation[] winfo = null;

			//#CM724928
			// Set the search conditions. 
			//#CM724929
			// Picking Plan unique key 
			winfoKey.setPlanUkey(inputParam.getRetrievalPlanUkey());

			//#CM724930
			// Search for the Work Status Info and lock it. 
			winfo = (WorkingInformation[]) winfoHandler.findForUpdate(winfoKey);

			//#CM724931
			// If there is no search result (data was deleted) 
			if (winfo == null || winfo.length == 0)
			{
				return false;
			}

			//#CM724932
			// Search through the Picking Plan Info. 
			RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn);
			RetrievalPlanSearchKey planKey = new RetrievalPlanSearchKey();
			RetrievalPlan retPlan = null;
			//#CM724933
			// Set the search conditions. 
			//#CM724934
			// Picking Plan unique key
			planKey.setRetrievalPlanUkey(inputParam.getRetrievalPlanUkey());

			//#CM724935
			// Search and lock the Plan Info. 
			retPlan = (RetrievalPlan) planHandler.findPrimaryForUpdate(planKey);

			//#CM724936
			// If there is no search result (data was deleted) 
			if (retPlan == null)
			{
				return false;
			}
			//#CM724937
			// Last update date/time does not correspond (updated via other terminal) 
			if (!retPlan.getLastUpdateDate().equals(inputParam.getLastUpdateDate()))
				return false;

		}
		catch (NoPrimaryException e)
		{
			//#CM724938
			// 6026020=Multiple records are found in a search by the unique key. {0} 
			RmiMsgLogClient.write("6026020" + wDelim + "DnRetrievalPlan", wProcessName);
			//#CM724939
			// 6006040=Data mismatch occurred. See log. {0} 
			wMessage = "6006040" + wProcessName;
			throw new ScheduleException(e.getMessage());
		}

		return true;

	}
	
	//#CM724940
	/**
	 * Search for the target Picking Plan Info to be updated, and lock it. <BR>
	 * Return true if the target data to update is normally searched and locked, or return false if the search failed due to error caused by other terminal. <BR>
	 * Regard the following cases as error via other terminal. <BR>
	 * <DIR>
	 *   - If no search result was obtained after searching through the Picking Plan Info <BR>
	 *   - Search through the Picking Plan Info. If the data displayed on screen is different from the Last update date/time: 
	 * </DIR>
	 * @param conn Instance to maintain database connection. 
	 * @param inputParam <CODE>RetrievalSupportParameter</CODE> that contains the target data to be updated
	 * @return Determine whether successfully searched the data to be updated and locked it. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected boolean lockPlan(Connection conn, RetrievalSupportParameter inputParam) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM724941
			// Search through the Picking Plan Info. 
			RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn);
			RetrievalPlanSearchKey planKey = new RetrievalPlanSearchKey();
			RetrievalPlan retPlan = null;
			//#CM724942
			// Set the search conditions. 
			//#CM724943
			// Picking Plan unique key
			planKey.setRetrievalPlanUkey(inputParam.getRetrievalPlanUkey());

			//#CM724944
			// Search and lock the Plan Info. 
			retPlan = (RetrievalPlan) planHandler.findPrimaryForUpdate(planKey);

			//#CM724945
			// If there is no search result (data was deleted) 
			if (retPlan == null)
			{
				return false;
			}
			//#CM724946
			// Last update date/time does not correspond (updated via other terminal) 
			if (!retPlan.getLastUpdateDate().equals(inputParam.getLastUpdateDate()))
				return false;

		}
		catch (NoPrimaryException e)
		{
			//#CM724947
			// 6026020=Multiple records are found in a search by the unique key. {0} 
			RmiMsgLogClient.write("6026020" + wDelim + "DnRetrievalPlan", wProcessName);
			//#CM724948
			// 6006040=Data mismatch occurred. See log. {0} 
			wMessage = "6006040" + wProcessName;
			throw new ScheduleException(e.getMessage());
		}

		return true;

	}

	//#CM724949
	/**
	 * Designate the Worker Code via Worker info table and obtain the Worker name. 
	 * @param conn Instance to maintain database connection. 
	 * @param pWorkerCode Worker Code to be searched 
	 * @return Worker Name
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected String getWorkerName(Connection conn, String pWorkerCode) throws ReadWriteException
	{
		String workerName = null;

		//#CM724950
		// Obtain the Worker info from DmWorker. 
		WorkerHandler workerHandler = new WorkerHandler(conn);
		WorkerSearchKey workerKey = new WorkerSearchKey();
		Worker worker = null;

		try
		{
			//#CM724951
			// Set the Search key and obtain the Worker name. 
			workerKey.setWorkerCode(pWorkerCode);
			worker = (Worker) workerHandler.findPrimary(workerKey);
			if (worker != null)
				workerName = worker.getName();
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

		return workerName;
	}

	//#CM724952
	// Private methods -----------------------------------------------

	//#CM724953
	/**
	 * Set the search condition for the search key to display the second display via the first display. 
	 * @param conn Instance to maintain database connection. 
	 * @param param <CODE>RetrievalSupportParameter</CODE> that contains search conditions 
	 * @return search key
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	private RetrievalPlanSearchKey setConditionSearchKey(Connection conn, RetrievalSupportParameter param) throws ReadWriteException, ScheduleException
	{
		RetrievalPlanSearchKey planKey = new RetrievalPlanSearchKey();
		//#CM724954
		// Set the search conditions. 
		//#CM724955
		// Consignor Code
		planKey.setConsignorCode(param.getConsignorCode());
		//#CM724956
		// Planned Picking Date
		planKey.setPlanDate(param.getRetrievalPlanDate());
		//#CM724957
		// Item Code
		planKey.setItemCode(param.getItemCode());
		//#CM724958
		// Case Order No. (for display) 
		if (!StringUtil.isBlank(param.getCaseOrderNoDisp()))
		{
			planKey.setCaseOrderNo(param.getCaseOrderNoDisp());
		}
		//#CM724959
		// Piece Order No. (for display) 
		if (!StringUtil.isBlank(param.getPieceOrderNoDisp()))
		{
			planKey.setPieceOrderNo(param.getPieceOrderNoDisp());
		}
		//#CM724960
		// Customer Code (for display) 
		if (!StringUtil.isBlank(param.getCustomerCodeDisp()))
		{
			planKey.setCustomerCode(param.getCustomerCodeDisp());
		}
		//#CM724961
		// Case Picking Location (for display) 
		if (!StringUtil.isBlank(param.getCaseLocationDisp()))
		{
			planKey.setCaseLocation(param.getCaseLocationDisp());
		}
		//#CM724962
		// Piece Picking Location (for display) 
		if (!StringUtil.isBlank(param.getPieceLocationDisp()))
		{
			planKey.setPieceLocation(param.getPieceLocationDisp());
		}
		//#CM724963
		// Status flag: Standby 
		planKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
		
		//#CM724964
		// Schedule process flag 
		if (isStockPack(conn))
		{
			//#CM724965
			// Not Processed 
			planKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=");
		}

		return planKey;
	}

	//#CM724966
	/**
	 * Obtain the respective names with the latest Added Date/Time based on the input condition.<BR>
	 * <BR>
	 * <Obtain name > 
	 * <DIR>
	 *  - Consignor Name
	 *  - Item Name
	 * </DIR>
	 * @param conn Instance to maintain database connection. 
	 * @param inputParam <CODE>RetrievalSupportParameter</CODE> that contains search conditions 
	 * @return <CODE>RetrievalSupportParameter</CODE> that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected RetrievalSupportParameter getDisplayName(Connection conn, RetrievalSupportParameter inputParam) throws ReadWriteException, ScheduleException
	{
		//#CM724967
		// Returned data 
		RetrievalSupportParameter resultParam = null;
		RetrievalPlanReportFinder nameFinder = new RetrievalPlanReportFinder(conn);
		//#CM724968
		// Set the Search key.
		RetrievalPlanSearchKey nameKey = setConditionSearchKey(conn, inputParam);
		nameKey.setConsignorNameCollect("");
		nameKey.setItemName1Collect("");
		nameKey.setRegistDateOrder(1, false);
		//#CM724969
		// Execute the search.
		nameFinder.open();
		RetrievalPlan[] retrieval = null;
		if (nameFinder.search(nameKey) > 0)
		{
			retrieval = (RetrievalPlan[]) nameFinder.getEntities(1);
			if (retrieval != null && retrieval.length != 0)
			{
				resultParam = new RetrievalSupportParameter();
				resultParam.setConsignorName(retrieval[0].getConsignorName());
				resultParam.setItemName(retrieval[0].getItemName1());
			}
		}
		nameFinder.close();
		return resultParam;
	}

	//#CM724970
	/**
	 * Verify the possibility to input the input data in the preset. <BR>
	 * If there is the same data, disable to input it and return false. <BR>
	 * If there is no same data and its input is accepted, return true. 
	 * @param inputParam <CODE>RetrievalSupportParameter</CODE> that contains the input info of the Input area
	 * @param listParam <CODE>RetrievalSupportParameter</CODE> array containing info of Preset area 
	 * @return Result of check (Enabled to input: true, Disabled to input: false) 
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	protected boolean dupplicationCheck(RetrievalSupportParameter inputParam, RetrievalSupportParameter[] listParam) throws ScheduleException
	{
		//#CM724971
		// Maintain the Case/Piece division, Location, and Order No. 
		String orgCasePieceFlag = inputParam.getCasePieceflg();
		String orgLocation = "";
		String orgOrderNo = "";
		String newCasePieceFlag = "";
		String newLocation = "";
		String newOrderNo = "";

		RetrievalPlanOperator retPlanOperater = new RetrievalPlanOperator();

		//#CM724972
		// If item data with division Mixed is input: 
		if (orgCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_MIXED))
		{
			for (int i = 0; i < listParam.length; i++)
			{
				//#CM724973
				// Obtain the Case/Piece division for Preset. 
				listParam[i].setTotalPlanQty(listParam[i].getEnteringQty() * listParam[i].getPlanCaseQty() + listParam[i].getPlanPieceQty());
				newCasePieceFlag = retPlanOperater.getCasePieceFlag(listParam[i]);

				//#CM724974
				// For the Item data of preset with the division Case or Mixed, check for its Case Location and its Piece Order No. 
				if (newCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE) || newCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_MIXED))
				{
					if (inputParam.getCaseLocation().equals(listParam[i].getCaseLocation()) && inputParam.getCaseOrderNo().equals(listParam[i].getCaseOrderNo()))
					{
						//#CM724975
						// 6023090=The data already exists. Unable to input. 
						wMessage = "6023090";
						return false;
					}
				}

				//#CM724976
				// For the Item data of preset with the division Piece or Mixed, check for its Piece Location and its Piece Order No. 
				if (newCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE) || newCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_MIXED))
				{
					if (inputParam.getPieceLocation().equals(listParam[i].getPieceLocation()) && inputParam.getPieceOrderNo().equals(listParam[i].getPieceOrderNo()))
					{
						//#CM724977
						// 6023090=The data already exists. Unable to input. 
						wMessage = "6023090";
						return false;
					}
				}

			}
		}
		//#CM724978
		// If item data with division Case is input: 
		else if (orgCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
		{
			for (int i = 0; i < listParam.length; i++)
			{
				//#CM724979
				// Obtain the Case/Piece division for Preset. 
				listParam[i].setTotalPlanQty(listParam[i].getEnteringQty() * listParam[i].getPlanCaseQty() + listParam[i].getPlanPieceQty());
				newCasePieceFlag = retPlanOperater.getCasePieceFlag(listParam[i]);

				//#CM724980
				// For the Item data of preset with the division Case or Mixed, check for its Case Location and its Piece Order No. 
				if (newCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE) || newCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_MIXED))
				{
					if (inputParam.getCaseLocation().equals(listParam[i].getCaseLocation()) && inputParam.getCaseOrderNo().equals(listParam[i].getCaseOrderNo()))
					{
						//#CM724981
						// 6023090=The data already exists. Unable to input. 
						wMessage = "6023090";
						return false;
					}
				}
			}
		}
		//#CM724982
		// If item data with division Piece is input: 
		else if (orgCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			for (int i = 0; i < listParam.length; i++)
			{
				//#CM724983
				// Obtain the Case/Piece division for Preset. 
				listParam[i].setTotalPlanQty(listParam[i].getEnteringQty() * listParam[i].getPlanCaseQty() + listParam[i].getPlanPieceQty());
				newCasePieceFlag = retPlanOperater.getCasePieceFlag(listParam[i]);

				//#CM724984
				// For the Item data of preset with the division Piece or Mixed, check for its Piece Location and its Piece Order No. 
				if (newCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE) || newCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_MIXED))
				{
					if (inputParam.getPieceLocation().equals(listParam[i].getPieceLocation()) && inputParam.getPieceOrderNo().equals(listParam[i].getPieceOrderNo()))
					{
						//#CM724985
						// 6023090=The data already exists. Unable to input. 
						wMessage = "6023090";
						return false;
					}
				}
			}
		}
		//#CM724986
		// If item data with division None is input: 
		else if (orgCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM724987
			// compute the Location No of Input data. 
			if (!StringUtil.isBlank(inputParam.getCaseLocation()))
				orgLocation = inputParam.getCaseLocation();
			else
				orgLocation = inputParam.getPieceLocation();

			//#CM724988
			// Compute the Order No. of Input data. 
			if (!StringUtil.isBlank(inputParam.getCaseOrderNo()))
				orgOrderNo = inputParam.getCaseOrderNo();
			else
				orgOrderNo = inputParam.getPieceOrderNo();

			for (int i = 0; i < listParam.length; i++)
			{
				//#CM724989
				// Compute the Location No. of the preset area.
				if (!StringUtil.isBlank(inputParam.getCaseLocation()))
					newLocation = listParam[i].getCaseLocation();
				else
					newLocation = listParam[i].getPieceLocation();

				//#CM724990
				// Compute the Order No. in the preset area.
				if (!StringUtil.isBlank(inputParam.getCaseOrderNo()))
					newLocation = listParam[i].getCaseOrderNo();
				else
					newLocation = listParam[i].getPieceOrderNo();

				if (orgLocation.equals(newLocation) && orgOrderNo.equals(newOrderNo))
				{
					//#CM724991
					// 6023090=The data already exists. Unable to input. 
					wMessage = "6023090";
					return false;
				}
			}
		}
		//#CM724992
		// Otherwise, announce ScheduleException.
		else
		{
			//#CM724993
			// 6004001=Invalid Data was found. 
			RmiMsgLogClient.write("6004001", wProcessName);
			throw new ScheduleException("6004001");
		}

		return true;

	}
	
	//#CM724994
	/**
	 * Allow this method to check for input when Inventory package is enabled. 
	 * @param	conn Instance to maintain database connection. 
	 * @param	checkParam <CODE>RetrievalSupportParameter</CODE> class instance that contains the info to be checked for input
	 * @return	Result of checking
	 * @throws	ReadWriteException Announce when error occurs on the database connection.
	 * @throws	ScheduleException Announce it when unexpected exception occurs in the process of scheduling. 
	 */
	private boolean inputCheckStockOn(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		try
		{
			RetrievalSupportParameter inputParam = (RetrievalSupportParameter) checkParam;
			//#CM724995
			// Search through the DnRetrievalPlan. 
			RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn);
			RetrievalPlanSearchKey planKey = new RetrievalPlanSearchKey();
			RetrievalPlan retPlan = null;
			//#CM724996
			// Set the search conditions. 
			//#CM724997
			// Picking Plan unique key
			planKey.setRetrievalPlanUkey(inputParam.getRetrievalPlanUkey());

			//#CM724998
			// Search through the Plan Info. 
			retPlan = (RetrievalPlan) planHandler.findPrimary(planKey);

			//#CM724999
			// If there is no search result (data was deleted) 
			if (retPlan == null)
			{
				return false;
			}
			//#CM725000
			// Last update date/time does not correspond (updated via other terminal) 
			if (!retPlan.getLastUpdateDate().equals(inputParam.getLastUpdateDate()))
				return false;
		}
		catch (NoPrimaryException e)
		{
			//#CM725001
			// 6026020=Multiple records are found in a search by the unique key. {0} 
			RmiMsgLogClient.write("6026020" + wDelim + "DnRetrievalPlan", wProcessName);
			//#CM725002
			// 6006040=Data mismatch occurred. See log. {0} 
			wMessage = "6006040" + wProcessName;
			throw new ScheduleException(e.getMessage());
		}	

		return true;
	}
	
	//#CM725003
	/**
	 * Allow this method to check for input when Inventory package is disabled. 
	 * @param	conn Instance to maintain database connection. 
	 * @param	checkParam <CODE>RetrievalSupportParameter</CODE> class instance that contains the info to be checked for input
	 * @return	Result of checking
	 * @throws	ReadWriteException Announce when error occurs on the database connection.
	 * @throws	ScheduleException Announce it when unexpected exception occurs in the process of scheduling. 
	 */
	private boolean inputCheckStockOff(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		try
		{
			RetrievalSupportParameter inputParam = (RetrievalSupportParameter) checkParam;
			//#CM725004
			// Search through the DnWorkInfo. 
			WorkingInformationHandler winfoHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey winfoKey = new WorkingInformationSearchKey();
			WorkingInformation[] winfo = null;

			//#CM725005
			// Set the search conditions. 
			//#CM725006
			// Picking Plan unique key 
			winfoKey.setPlanUkey(inputParam.getRetrievalPlanUkey());

			//#CM725007
			// Search through the Work Status Info.
			winfo = (WorkingInformation[]) winfoHandler.find(winfoKey);

			//#CM725008
			// If there is no search result (data was deleted) 
			if (winfo == null || winfo.length == 0)
			{
				return false;
			}

			//#CM725009
			// Search through the DnRetrievalPlan. 
			RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn);
			RetrievalPlanSearchKey planKey = new RetrievalPlanSearchKey();
			RetrievalPlan retPlan = null;
			//#CM725010
			// Set the search conditions. 
			//#CM725011
			// Picking Plan unique key
			planKey.setRetrievalPlanUkey(inputParam.getRetrievalPlanUkey());

			//#CM725012
			// Search through the Plan Info. 
			retPlan = (RetrievalPlan) planHandler.findPrimary(planKey);

			//#CM725013
			// If there is no search result (data was deleted) 
			if (retPlan == null)
			{
				return false;
			}
			//#CM725014
			// Last update date/time does not correspond (updated via other terminal) 
			if (!retPlan.getLastUpdateDate().equals(inputParam.getLastUpdateDate()))
				return false;

		}
		catch (NoPrimaryException e)
		{
			//#CM725015
			// 6026020=Multiple records are found in a search by the unique key. {0} 
			RmiMsgLogClient.write("6026020" + wDelim + "DnRetrievalPlan", wProcessName);
			//#CM725016
			// 6006040=Data mismatch occurred. See log. {0} 
			wMessage = "6006040" + wProcessName;
			throw new ScheduleException(e.getMessage());
		}	

		return true;
	}
	
	
	//#CM725017
	/**
	 * Allow this method to execute the process for deleting the data with Inventory package enabled. 
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 * @return Result of delete (Normal: true, Error: false) 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws Exception Announce when all of exceptions occur. 
	 */
	private boolean deleteRetrievalPlanStockOn(Connection conn, Parameter[] startParams) throws ReadWriteException, Exception
	{
		boolean registFlag = false;

		//#CM725018
		// Input information of the parameter
		RetrievalSupportParameter[] inputParam = (RetrievalSupportParameter[]) startParams;

		//#CM725019
		// Generate instances of Plan Info. 
		RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn);
		RetrievalPlanAlterKey planAltKey = new RetrievalPlanAlterKey();

		for (int i = 0; i < inputParam.length; i++)
		{
			//#CM725020
			// Check for exclusion. 
			if (!lockPlan(conn, inputParam[i]))
			{
				//#CM725021
				// Close the process when error via other terminal occurs. 
				//#CM725022
				// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + inputParam[i].getRowNo();
				return registFlag;
			}

			//#CM725023
			// Delete the Picking Plan Info. 
			planAltKey.KeyClear();
			planAltKey.setRetrievalPlanUkey(inputParam[i].getRetrievalPlanUkey());
			planAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE);
			planAltKey.updateLastUpdatePname(wProcessName);
			planHandler.modify(planAltKey);
		}

		//#CM725024
		// 6001005=Deleted. 
		wMessage = "6001005";
		registFlag = true;
		return registFlag;
	}
	
	//#CM725025
	/**
	 * Allow this method to execute the process for deleting the data with Inventory package disabled. 
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 * @return Result of delete (Normal: true, Error: false) 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws Exception Announce when all of exceptions occur. 
	 */
	private boolean deleteRetrievalPlanStockOff(Connection conn, Parameter[] startParams) throws ReadWriteException, Exception
	{
		boolean registFlag = false;

		//#CM725026
		// Check for exclusion of all the target data. 
		if (!lockPlanUkeyAll(conn, startParams))
		{
			//#CM725027
			// 6003006=Unable to process this data. It has been updated via other work station. 
			wMessage = "6003006";
			return registFlag;
		}
		//#CM725028
		// Input information of the parameter
		RetrievalSupportParameter[] inputParam = (RetrievalSupportParameter[]) startParams;

		String workerName = getWorkerName(conn, inputParam[0].getWorkerCode());

		//#CM725029
		// Generate instances of Plan Info. 
		RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn);
		RetrievalPlanAlterKey planAltKey = new RetrievalPlanAlterKey();

		for (int i = 0; i < inputParam.length; i++)
		{
			//#CM725030
			// Check for exclusion. 
			if (!lock(conn, inputParam[i]))
			{
				//#CM725031
				// Close the process when error via other terminal occurs. 
				//#CM725032
				// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + inputParam[i].getRowNo();
				return registFlag;
			}

			//#CM725033
			// Cancel the allocation (Work Status Info, Inventory Info, and Picking Plan Info) 
			planAltKey.KeyClear();
			planAltKey.setRetrievalPlanUkey(inputParam[i].getRetrievalPlanUkey());
			planAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE);
			planAltKey.updateLastUpdatePname(wProcessName);
			planHandler.modify(planAltKey);

			RetrievalAllocateOperator allocateOperater = new RetrievalAllocateOperator(conn);
			if (!allocateOperater
				.cancel(conn, inputParam[i].getRetrievalPlanUkey(), inputParam[0].getWorkerCode(), workerName, inputParam[0].getTerminalNumber(), wProcessName))
			{
				//#CM725034
				// Close the process when error via other terminal occurs. 
				//#CM725035
				// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + inputParam[i].getRowNo();
				return registFlag;
			}
		}

		//#CM725036
		// 6001005=Deleted. 
		wMessage = "6001005";
		registFlag = true;
		return registFlag;

	}
	
	//#CM725037
	/**
	 * Allow this method to execute the process for modifying the data with Inventory package enabled. 
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 * @return Modified result (Normal: true, Error: false) 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws Exception Announce when all of exceptions occur. 
	 */
	private boolean modifyRetrievalPlanStockOn(Connection conn, Parameter[] startParams) throws ReadWriteException, Exception
	{
		boolean registFlag = false;

		//#CM725038
		// Input information of the parameter
		RetrievalSupportParameter[] param = (RetrievalSupportParameter[]) startParams;

		//#CM725039
		// Obtain the batch No. and Worker name. 
		SequenceHandler sequenceHandler = new SequenceHandler(conn);
		String batch_seqno = sequenceHandler.nextRetrievalPlanBatchNo();
		String workerName = getWorkerName(conn, param[0].getWorkerCode());

		RetrievalPlanOperator planOperator = new RetrievalPlanOperator(conn);

		RetrievalPlanAlterKey planAltKey = new RetrievalPlanAlterKey();
		RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn);
		
		//#CM725040
		// Lock the Picking plan data. 
		planOperator.lockRetrievalPlan();

		for (int i = 0; i < param.length; i++)
		{
			//#CM725041
			// Check for exclusion. 
			if (!lockPlan(conn, param[i]))
			{
				//#CM725042
				// Close the process when error via other terminal occurs. 
				//#CM725043
				// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + param[i].getRowNo();
				return registFlag;
			}
			//#CM725044
			// Set the Planned total qty. 
			param[i].setTotalPlanQty(param[i].getEnteringQty() * param[i].getPlanCaseQty() + param[i].getPlanPieceQty());
			//#CM725045
			// Set it by determining Case/Piece division. 
			param[i].setCasePieceflg(planOperator.getCasePieceFlag(param[i]));
			//#CM725046
			// Delete the source picking plan info. 
			planAltKey.KeyClear();
			planAltKey.setRetrievalPlanUkey(param[i].getRetrievalPlanUkey());
			planAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE);
			planAltKey.updateLastUpdatePname(wProcessName);
			planHandler.modify(planAltKey);
			
			//#CM725047
			// Set the data to generate a Picking Plan Info. 
			//#CM725048
			// Total Planned Picking qty 
			param[i].setTotalPlanQty(param[i].getEnteringQty() * param[i].getPlanCaseQty() + param[i].getPlanPieceQty());

			//#CM725049
			// Search for the same info. 
			//#CM725050
			// For the same data Disable to add the same data except for "Deleted". 
			RetrievalPlan[] retrieval = null;
			retrieval = planOperator.findRetrievalPlanForUpdate(param[i], true);
			if (retrieval != null)
			{
				//#CM725051
				// 6023273=No.{0}{1}
				//#CM725052
				// 6023090=The data already exists. Unable to input. 
				wMessage = "6023273" + wDelim + param[i].getRowNo() + wDelim + MessageResource.getMessage("6023090");
				return registFlag;	
			}

			//#CM725053
			// Batch No. 
			param[i].setBatchNo(batch_seqno);
			//#CM725054
			// Worker Code
			param[i].setWorkerCode(param[0].getWorkerCode());
			//#CM725055
			// Worker Name
			param[i].setWorkerName(workerName);
			//#CM725056
			// Terminal No.
			param[i].setTerminalNumber(param[0].getTerminalNumber());
			//#CM725057
			// Entry type 
			//#CM725058
			// Allow this screen, which is of loaded image, to allocate in the same pattern as loading. 
			//#CM725059
			// In order to allocate allow the the AllocateOperator class to execute allocation for loading, 
			//#CM725060
			// set REGIST_KIND_HOST for Entry type. 
			//#CM725061
			// Host creates Entry type in the Picking Plan Info. It is acceptable. 
			param[i].setRegistKind(RetrievalPlan.REGIST_KIND_HOST);

			//#CM725062
			// Generate the Picking Plan Info. 
			RetrievalPlan newPlan = null;
			newPlan = planOperator.createRetrievalPlan(param[i], wProcessName);
			if (newPlan == null)
			{
				//#CM725063
				// Close the process when error via other terminal occurs. 
				//#CM725064
				// 6023273=No.{0}{1}
				//#CM725065
				// 6006007=Cannot add. The same data already exists. 
				wMessage = "6023273" + wDelim + param[i].getRowNo() + wDelim + MessageResource.getMessage("6006007");
				return registFlag;
			}
		}

		registFlag = true;
		
		return registFlag;

	}
	
	//#CM725066
	/**
	 * Allow this method to execute the process for modifying the data with Inventory package disabled. 
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 * @return Modified result (Normal: true, Error: false) 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws Exception Announce when all of exceptions occur. 
	 */
	private boolean modifyRetrievalPlanStockOff(Connection conn, Parameter[] startParams) throws ReadWriteException, Exception
	{
		boolean registFlag = false;
		
		//#CM725067
		// Check for exclusion of all the target data. 
		if (!this.lockPlanUkeyAll(conn, startParams))
		{
			//#CM725068
			// 6003006=Unable to process this data. It has been updated via other work station. 
			wMessage = "6003006";
			return registFlag;
		}
		//#CM725069
		// Lock the Inventory Info to be used for allocation. 
		RetrievalStockHandler stHandle = new RetrievalStockHandler(conn);
		if (!stHandle.lockPlanData(startParams))
		{
			//#CM725070
			// 6003006=Unable to process this data. It has been updated via other work station. 
			wMessage = "6003006";
			return registFlag;
		}

		//#CM725071
		// Input information of the parameter
		RetrievalSupportParameter[] param = (RetrievalSupportParameter[]) startParams;

		//#CM725072
		// Obtain the batch No. and Worker name. 
		SequenceHandler sequenceHandler = new SequenceHandler(conn);
		String batch_seqno = sequenceHandler.nextRetrievalPlanBatchNo();
		String workerName = getWorkerName(conn, param[0].getWorkerCode());
		//#CM725073
		// Allocated qty 
		int allocateQty = 0;

		RetrievalAllocateOperator allocateOperator = new RetrievalAllocateOperator(conn);
		RetrievalPlanOperator planOperator = new RetrievalPlanOperator(conn);

		RetrievalPlanAlterKey planAltKey = new RetrievalPlanAlterKey();
		RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn);

		for (int i = 0; i < param.length; i++)
		{
			//#CM725074
			// Check for exclusion. 
			if (!lock(conn, param[i]))
			{
				//#CM725075
				// Close the process when error via other terminal occurs. 
				//#CM725076
				// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + param[i].getRowNo();
				return registFlag;
			}
			//#CM725077
			// Set the Planned total qty. 
			param[i].setTotalPlanQty(param[i].getEnteringQty() * param[i].getPlanCaseQty() + param[i].getPlanPieceQty());
			//#CM725078
			// Set it by determining Case/Piece division. 
			param[i].setCasePieceflg(planOperator.getCasePieceFlag(param[i]));
			//#CM725079
			// Delete the Picking Plan Info. 
			planAltKey.KeyClear();
			planAltKey.setRetrievalPlanUkey(param[i].getRetrievalPlanUkey());
			planAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE);
			planAltKey.updateLastUpdatePname(wProcessName);
			planHandler.modify(planAltKey);
			
			//#CM725080
			// Set the data to generate a Picking Plan Info. 
			//#CM725081
			// Total Planned Picking qty 
			param[i].setTotalPlanQty(param[i].getEnteringQty() * param[i].getPlanCaseQty() + param[i].getPlanPieceQty());

			//#CM725082
			// Search for the same info. 
			//#CM725083
			// For the same data Disable to add the same data except for "Deleted". 
			RetrievalPlan[] retrieval = null;
			retrieval = planOperator.findRetrievalPlanForUpdate(param[i], true);
			if (retrieval != null)
			{
				//#CM725084
				// 6023273=No.{0}{1}
				//#CM725085
				// 6023090=The data already exists. Unable to input. 
				wMessage = "6023273" + wDelim + param[i].getRowNo() + wDelim + MessageResource.getMessage("6023090");
				return registFlag;	
			}
			
			//#CM725086
			// Cancel all the allocation in the source info. 
			if (!allocateOperator.cancel(conn, param[i].getRetrievalPlanUkey(), param[0].getWorkerCode(), workerName, param[0].getTerminalNumber(), wProcessName))
			{
				//#CM725087
				// Close the process when error via other terminal occurs. 
				//#CM725088
				// 6023209 = No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + param[i].getRowNo();
				return registFlag;
			}

			//#CM725089
			// Batch No. 
			param[i].setBatchNo(batch_seqno);
			//#CM725090
			// Worker Code
			param[i].setWorkerCode(param[0].getWorkerCode());
			//#CM725091
			// Worker Name
			param[i].setWorkerName(workerName);
			//#CM725092
			// Terminal No.
			param[i].setTerminalNumber(param[0].getTerminalNumber());
			//#CM725093
			// Entry type 
			//#CM725094
			// Allow this screen, which is of loaded image, to allocate in the same pattern as loading. 
			//#CM725095
			// In order to allocate allow the the AllocateOperator class to execute allocation for loading, 
			//#CM725096
			// set REGIST_KIND_HOST for Entry type. 
			//#CM725097
			// Host creates Entry type in the Picking Plan Info. It is acceptable. 
			param[i].setRegistKind(RetrievalPlan.REGIST_KIND_HOST);

			//#CM725098
			// Generate the Picking Plan Info. 
			RetrievalPlan newPlan = null;
			newPlan = planOperator.createRetrievalPlan(param[i], wProcessName);
			if (newPlan == null)
			{
				//#CM725099
				// Close the process when error via other terminal occurs. 
				//#CM725100
				// 6023273=No.{0}{1}
				//#CM725101
				// 6006007=Cannot add. The same data already exists. 
				wMessage = "6023273" + wDelim + param[i].getRowNo() + wDelim + MessageResource.getMessage("6006007");
				return registFlag;
			}

			//#CM725102
			// Generate allocations. (Inventory Info and Work Status Info) 
			allocateQty =
				allocateOperator.allocate(conn, newPlan.getRetrievalPlanUkey(), param[0].getWorkerCode(), workerName, param[0].getTerminalNumber(), wProcessName);

			if (allocateQty < newPlan.getPlanQty())
			{
				//#CM725103
				// 6023359=No.{0} Shortage occurred. 
				wMessage = "6023359" + wDelim + param[i].getRowNo();
				return registFlag;
			}
		}

		registFlag = true;
		
		return registFlag;

	}

} //end of class
