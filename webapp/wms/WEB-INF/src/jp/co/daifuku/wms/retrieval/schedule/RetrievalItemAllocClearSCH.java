package jp.co.daifuku.wms.retrieval.schedule;

//#CM722908
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM722909
/**
 * Designer : Y.Osawa <BR>
 * Maker : Y.Osawa  <BR>
 * <BR>
 * Allow this class to cancel the WEB Item Picking Allocation.  <BR>
 * Receive the contents entered via screen as a parameter, and execute the process for canceling the Item Picking allocation.  <BR>
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
 *     Order No.: NULL  </DIR></DIR>
 * <BR>
 * 2. Process by clicking "Setup" button (<CODE>startSCH()</CODE> method)  <BR><BR><DIR>
 *   Receive the content displayed in the input field item, as a parameter, and execute the process for canceling the Item Picking allocation.  <BR>
 *   Re-obtain the data to be output to the preset area from database when the process normally completed, and return it.  <BR>
 *   Return null when the schedule failed to complete due to condition error or other causes.  <BR>
 *   If changed the Working data status, search through the RFT Work Status Info and update the electronic statement field item of the working terminal to null. <BR>
 *   Search for the data preservation information under Working. If the corresponding data when such data is found, delete its record.<BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error  <BR>
 *   Update the Work Status Info, Plan info, and then Inventory Info in this sequence. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Terminal No.* <BR>
 *     Consignor Code*<BR>
 *     Planned Picking Date*<BR>
 *     Start Item Code <BR>
 *     End Item Code  <BR>
 *     Case/Piece division*<BR>
 * </DIR>
 * <BR>
 *   <Returned data> <BR><DIR>
 *     true or false  <BR></DIR>
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
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:53 $
 * @author  $Author: suresh $
 */
public class RetrievalItemAllocClearSCH extends RetrievalOrderAllocClearSCH
{

	//#CM722910
	// Class variables -----------------------------------------------
	//#CM722911
	/**
	 * Process name 
	 */
	protected static final String wProcessName = "RetrievalItemAllocClear";

	//#CM722912
	// Class method --------------------------------------------------
	//#CM722913
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:53 $");
	}
	//#CM722914
	// Constructors --------------------------------------------------
	//#CM722915
	/**
	 * Initialize this class. 
	 */
	public RetrievalItemAllocClearSCH()
	{
		wMessage = null;
	}

	//#CM722916
	// Public methods ------------------------------------------------
	//#CM722917
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
		//#CM722918
		// Set the corresponding Consignor code. 
		RetrievalSupportParameter wParam = new RetrievalSupportParameter();

		//#CM722919
		// Generate instance of Work Status Info handlers. 
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformation[] wWorkinfo = null;

		//#CM722920
		// Set the search conditions. 
		//#CM722921
		// System identification key: any key other than AS/RS(2) and Status flag: Standby(0),  OR
		wKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=", "((", "", "AND");
		wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "", ")", "OR");
		//#CM722922
		// System identification key: AS/RS(2) and Status flag: Working. (2) 
		wKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "=", "(", "", "AND");
		wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "", "))", "AND");
		//#CM722923
		// Work type: Picking. (3) 
		wKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM722924
		// Order No.: NULL 
		wKey.setOrderNo("");
		//#CM722925
		// Set the Consignor Code for grouping condition. 
		wKey.setConsignorCodeGroup(1);
		wKey.setConsignorCodeCollect("DISTINCT");
		
		//#CM722926
		// If the count is one: 
		if (wObj.count(wKey) == 1)
		{
			//#CM722927
			// Obtain the count of the corresponding Consignor Code data. 
			wWorkinfo = (WorkingInformation[]) wObj.find(wKey);
	
			if (wWorkinfo != null && wWorkinfo.length == 1)
			{
				//#CM722928
				// Obtain the corresponding Consignor Code and set it for the return parameter. 
				wParam.setConsignorCode(wWorkinfo[0].getConsignorCode());
			}
		}
		return wParam;
	}
	
	//#CM722929
	/**
	 * Generate a search key of Work Status for which the input value of the Input area is set. <BR>
	 * 
	 * @param inputParam   Parameter class that contains the info of the input area. 
	 * @return Generate a search key of Work Status that contains the input value of the Input area. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.<BR>
	 * @throws ScheduleException Announce it when this method is invoked. 
	 */
	protected WorkingInformationSearchKey createWorkingInfoKey(RetrievalSupportParameter inputParam) 
	 			throws ReadWriteException, ScheduleException
	{
		WorkingInformationSearchKey wiKey = new WorkingInformationSearchKey();

		//#CM722930
		// Consignor Code
		if (!StringUtil.isBlank(inputParam.getConsignorCode()))
		{
			wiKey.setConsignorCode(inputParam.getConsignorCode());
		}
		//#CM722931
		// Planned Picking Date
		if (!StringUtil.isBlank(inputParam.getRetrievalPlanDate()))
		{
			wiKey.setPlanDate(inputParam.getRetrievalPlanDate());
		}
		//#CM722932
		// Start Item Code 
		if (!StringUtil.isBlank(inputParam.getStartItemCode()))
		{
			wiKey.setItemCode(inputParam.getStartItemCode(), ">=");
		}
		//#CM722933
		// End Item Code 
		if (!StringUtil.isBlank(inputParam.getEndItemCode()))
		{
			wiKey.setItemCode(inputParam.getEndItemCode(), "<=");
		}
		//#CM722934
		// Order No.: NULL 
		wiKey.setOrderNo("");
		//#CM722935
		// Case/Piece division (Work Type) 
		//#CM722936
		// Any division other than All 
		if (!inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
		{
			//#CM722937
			// Case 
			if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				wiKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			//#CM722938
			// Piece 
			else if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				wiKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			}
			//#CM722939
			// None 
			else if (inputParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				wiKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			}
		}		
	
		return wiKey;
	}
	
}
//#CM722940
//end of class
