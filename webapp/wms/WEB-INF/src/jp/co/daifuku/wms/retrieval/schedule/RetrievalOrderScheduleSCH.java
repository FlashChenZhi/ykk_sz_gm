//#CM724150
//$Id: RetrievalOrderScheduleSCH.java,v 1.4 2007/02/07 04:20:00 suresh Exp $
package jp.co.daifuku.wms.retrieval.schedule;

//#CM724151
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.retrieval.dbhandler.RetrievalPlanQueryHandler;
import jp.co.daifuku.wms.retrieval.report.RetrievalOrderShortageWriter;

//#CM724152
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * Allow this class to execute the process for starting WEB Order Picking (schedule).  <BR>
 * Receive the contents entered via screen as a parameter, and execute the process for schedule of the Picking by Order.  <BR>
 * Allow each method contained in this class to receive the Connection object and execute the process for updating the database, but
 * disable to commit nor roll-back the transaction.  <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *   Return the Consignor Code, if only one Consignor Code exists in the Picking Work Status Info (Order No.: Added, and Status flag: Standby). <BR> 
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR> 
 *   <BR>
 *   [search conditions]  <BR> 
 *   <DIR>
 *     Order No.: Exists <BR>
 *     Status flag: Standby <BR> 
 *   </DIR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Setup" button (<CODE>startSCH</CODE> method)  <BR><BR>
 * <BR>
 * <DIR>
 *   Receive the content displayed in the input field item, as a parameter, and execute the process for allocating the Order Picking.  <BR>
 *   Return true when the process normally completed, or return false when the schedule failed to complete due to condition error or other causes.  <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error. <BR>
 *   <BR>
 *   [Parameter]  <BR>
 *   <DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Consignor Code* <BR>
 *     Planned Picking Date* <BR>
 *     Order No.* <BR>
 *     Case/Piece division name (Work Type) <BR>
 *     Last update date/time [] <BR>
 *     Plan unique key [] <BR>
 *     Allocation target, storage place * <BR>
 *     Shortage evidence division * <BR>
 *     Terminal No. <BR>
 *   </DIR>
 *   <BR>
 *   [Check Process Condition]  <BR>
 *   -"Check" process for Worker Code- (<CODE>AbstructRetrievalSCH</CODE> class) <BR>
 *   <BR>
 *   - Execute the process for checking the data with Daily Update Processing. - (<CODE>AbstructRetrievalSCH</CODE> class)  <BR>
 *   <BR>
 *   [Update/Add Process]  <BR>
 *   <BR>
 *   -Process to Lock Picking Plan Info -<BR>
 *   <BR>
 *   <DIR>
 *     Based on the Planned Picking Date, Batch No., Order No., Case/Piece division passed as a parameter, lock the Picking Plan Info.  <BR>
 *     1.Obtain the Schedule No. and set it.  <BR>
 *   </DIR>
 *   <BR>
 *   - Execute the process for allocating the inventory in the Picking Allocation process. -<BR>
 *   <Parameter> <BR><DIR>
 *     Connection  <BR>
 *     Schedule division: 1 (Specify Order)  <BR>
 *     Schedule No.  <BR>
 *     Worker Code <BR>
 *     Worker Name <BR>
 *     Select allocation target, storage place  <BR>
 *     Shortage evidence division  <BR>
 *     Terminal No. <BR>
 *     Process name  <BR>
 *   <BR>
 *   <Returned information >  <BR><DIR>
 *     Presence of Shortage  <BR>
 *   </DIR>
 *   <BR>
 *   Execute the following processes only when the allocation process completed normally. 
 *   <BR>
 *   -Update the Picking Plan Info.-<BR>
 *   <BR>
 *   <DIR>
 *     Based on the Planned Picking Date, Batch No., Order No., Case/Piece division passed as a parameter, update the Picking Plan Info.  <BR>
 *     1.Set the Schedule process flag to Processed.
 *   </DIR>
 *   <BR>
 *   -Execute the process for printing the Shortage list in the event of shortage (in the allocation process). -<BR>
 *   <Print Parameter> 
 *     <DIR>
 *       - Schedule No.  <BR>
 *     </DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/04</TD><TD>C.Kaminishizono</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2007/02/07 04:20:00 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderScheduleSCH extends AbstractRetrievalSCH
{
	//#CM724153
	// Class variables -----------------------------------------------
	//#CM724154
	/**
	 * Process name 
	 */
	public static String PROCESSNAME = "RetrievalOrderSCH";

	//#CM724155
	/**
	 * Shortage occurring or not: 
	 */
	private boolean wIsShortage = false;

	//#CM724156
	/**
	 * Batch No. of the generated shortage info 
	 */
	private String wShortageBatch = null;
	
	//#CM724157
	/**
	 * Invoking source screen name 
	 */
	protected String wFunctionName;

	//#CM724158
	// Class method --------------------------------------------------
	//#CM724159
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.4 $,$Date: 2007/02/07 04:20:00 $");
	}

	//#CM724160
	// Constructors --------------------------------------------------
	//#CM724161
	/**
	 * Initialize this class. 
	 */
	public RetrievalOrderScheduleSCH()
	{
		wMessage = null;
	}

	//#CM724162
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * Return the Consignor Code, if only one Consignor Code exists in the Picking Plan Info. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR> 
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. 
	 * @param conn Database connection object
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * @return Class that implements the <CODE>RetrievalSupportParameter</CODE> interface that contains the search result. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter dispData = new RetrievalSupportParameter();
		dispData.setSystemDiscKeyArray(geImportedSystemDiskKey(conn));

		RetrievalPlanHandler wRpHandler = new RetrievalPlanHandler(conn);
		RetrievalPlanSearchKey searchKey = new RetrievalPlanSearchKey();

		//#CM724163
		// Search Data 
		searchKey.KeyClear();
		//#CM724164
		// Data with status flag other than "Completed" or "Deleted" 
		searchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION, "!=");
		searchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");

		searchKey.setCaseOrderNo("", "IS NOT NULL", "((", "", "AND");
		searchKey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_PIECE, "!=", "", "", "AND");
		searchKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
		searchKey.setSchFlag(RetrievalPlan.SCH_FLAG_PIECE_COMPLETION, "=", "", "))", "OR");

		searchKey.setPieceOrderNo("", "IS NOT NULL", "(", "", "AND");
		searchKey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_CASE, "!=", "", "", "AND");
		searchKey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
		searchKey.setSchFlag(RetrievalPlan.SCH_FLAG_CASE_COMPLETION, "=", "", ")))", "AND");

		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		if (wRpHandler.count(searchKey) == 1)
		{
			try
			{
				RetrievalPlan rData = (RetrievalPlan) wRpHandler.findPrimary(searchKey);
				dispData.setConsignorCode(rData.getConsignorCode());
			}
			catch (NoPrimaryException e)
			{
				return dispData;
			}
		}
		return dispData;
	}

	//#CM724165
	/**
	 * Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * @param conn Instance to maintain database connection. <BR>
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0. <BR>
	 *          If the number of search results exceeds the Max count of data allowable to display, or if error occurs with the input condition, return null. <BR>
	 *          Returning array with the number of elements 0 or null enables to obtain the error content as a message using the <CODE>getMessage</CODE> method. <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.<BR>
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. <BR>
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		RetrievalSupportParameter param = (RetrievalSupportParameter) searchParam;

		//#CM724166
		// Check the Worker code and password 
		if (!checkWorker(conn, param))
		{
			return null;
		}

		//#CM724167
		// Obtain the division to be displayed this time here from the Case/Piece division entered via screen.
		String inputCasePiece = null;
		if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
		{
			inputCasePiece = RetrievalPlan.CASEPIECE_FLAG_CASE;
		}
		else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			inputCasePiece = RetrievalPlan.CASEPIECE_FLAG_PIECE;
		}
		else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			inputCasePiece = RetrievalPlan.CASEPIECE_FLAG_NOTHING;
		}
		else
		{
			//#CM724168
			// For data with division All, disable to set anything because of no division. 
		}

		//#CM724169
		// Obtain the target data. 
		RetrievalPlanQueryHandler queryHandler = new RetrievalPlanQueryHandler(conn);
		RetrievalPlan[] dispPlan = null;
		if (!param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
		{
			RetrievalPlanSearchKey skey = new RetrievalPlanSearchKey();
			setSearchKey(skey, param, inputCasePiece);
			
			//#CM724170
			// This screen is intended for aggregated display. Therefore, execute check
			//#CM724171
			// using the Max count of WMSParam SQL search data, instead of using the count of WMSParam displayed data.
			//#CM724172
			// In some case, the count of aggregated result data may exceed 300. 
			int dispCount = queryHandler.count(skey);
			if (dispCount <= 0)
			{
				//#CM724173
				// No target data was found. 
				wMessage = "6003018";
				return new RetrievalSupportParameter[0];
			}
			if (dispCount > WmsParam.MAX_NUMBER_OF_SQL_FIND)
			{
				//#CM724174
				// 6023469=Search resulted in {0} work. Narrow down search since it exceeds {1} 
				String msg = "6023469" 
						+ wDelim + WmsFormatter.getNumFormat(dispCount)
						+ wDelim + MAX_NUMBER_OF_SQL_FIND_DISP;
				wMessage = msg;
				return null;
			}

			dispPlan = queryHandler.find(skey);
		}
		else
		{
			//#CM724175
			// Obtain data by division to commit the order sequence and the display type, and 
			//#CM724176
			// merge them. 
			RetrievalPlanSearchKey casekey = new RetrievalPlanSearchKey();
			setSearchKey(casekey, param, RetrievalPlan.CASEPIECE_FLAG_CASE);

			RetrievalPlanSearchKey piecekey = new RetrievalPlanSearchKey();
			setSearchKey(piecekey, param, RetrievalPlan.CASEPIECE_FLAG_PIECE);

			RetrievalPlanSearchKey nothingkey = new RetrievalPlanSearchKey();
			setSearchKey(nothingkey, param, RetrievalPlan.CASEPIECE_FLAG_NOTHING);
			
			int dispCount = queryHandler.count(casekey)
							+ queryHandler.count(piecekey)
							+ queryHandler.count(nothingkey);
							
			//#CM724177
			// This screen is intended for aggregated display. Therefore, execute check
			//#CM724178
			// using the Max count of WMSParam SQL search data, instead of using the count of WMSParam displayed data, . 
			//#CM724179
			// In some case, the count of aggregated result data may exceed 300. 
			if (dispCount <= 0)
			{
				//#CM724180
				// No target data was found. 
				wMessage = "6003018";
				return new RetrievalSupportParameter[0];
			}
			if (dispCount > WmsParam.MAX_NUMBER_OF_SQL_FIND)
			{
				//#CM724181
				// 6023469=Search resulted in {0} work. Narrow down search since it exceeds {1} 
				String msg = "6023469" 
						+ wDelim + WmsFormatter.getNumFormat(dispCount)
						+ wDelim + MAX_NUMBER_OF_SQL_FIND_DISP;
				wMessage = msg;
				return null;
			}

			RetrievalPlan[] casePlan = queryHandler.find(casekey);
			RetrievalPlan[] piecePlan = queryHandler.find(piecekey);
			RetrievalPlan[] nothingPlan = queryHandler.find(nothingkey);

			dispPlan = margeRetrievalPlan(casePlan, piecePlan, nothingPlan);
		}

		if (dispPlan == null || dispPlan.length == 0)
		{
			//#CM724182
			// No target data was found. 
			wMessage = "6003018";
			return new RetrievalSupportParameter[0];
		}

		//#CM724183
		// Search for Consignor Name with later Added Date/Time 
		Date tempDate = null;
		String consignorName = "";
		for (int i = 0; i < dispPlan.length; i++)
		{
			if (i == 0)
			{
				tempDate = dispPlan[i].getRegistDate();
				consignorName = dispPlan[i].getConsignorName();
			}
			else
			{
				if (dispPlan[i].getRegistDate().compareTo(tempDate) > 0)
				{
					tempDate = dispPlan[i].getRegistDate();
					consignorName = dispPlan[i].getConsignorName();
				}
			}
		}

		//#CM724184
		// Aggregate the returned data. 
		String compBatchNo = null;
		String compOrderNo = null;
		String compCasePiece = null;
		String compCustomer = null;
		Vector tempVec = new Vector();
		for (int i = 0; i < dispPlan.length; i++)
		{
			if (i == 0)
			{
				compBatchNo = dispPlan[i].getHostCollectBatchno();
				compOrderNo = getOrderNo(dispPlan[i], inputCasePiece);
				compCasePiece = dispPlan[i].getCasePieceFlag();
				compCustomer = dispPlan[i].getCustomerCode();
			}
			else if (isDifferentLine(dispPlan[i], compBatchNo, compOrderNo, compCasePiece, compCustomer, inputCasePiece))
			{
				RetrievalSupportParameter tempParam = new RetrievalSupportParameter();

				//#CM724185
				// Display in the Preset area. 
				tempParam.setHostCollectBatchNoL(compBatchNo);
				tempParam.setOrderNoL(compOrderNo);
				if (!StringUtil.isBlank(inputCasePiece))
				{
					//#CM724186
					// Designating any option other than "All":
					tempParam.setCasePieceflgName(DisplayUtil.getPieceCaseValue(inputCasePiece));
					//#CM724187
					// Hidden field item 
					tempParam.setCasePieceFlagL(inputCasePiece);
				}
				else
				{
					//#CM724188
					// Designating "All": 
					tempParam.setCasePieceflgName(DisplayUtil.getPieceCaseValue(compCasePiece));
					//#CM724189
					// Hidden field item 
					tempParam.setCasePieceFlagL(compCasePiece);
				}
				tempParam.setCustomerCode(compCustomer);
				tempParam.setCustomerName(dispPlan[i - 1].getCustomerName1());
				//#CM724190
				// Header 
				tempParam.setConsignorCode(dispPlan[i - 1].getConsignorCode());
				tempParam.setConsignorName(consignorName);
				tempParam.setRetrievalPlanDate(dispPlan[i - 1].getPlanDate());

				tempVec.addElement(tempParam);

				//#CM724191
				// Initialize. 
				compBatchNo = dispPlan[i].getHostCollectBatchno();
				compOrderNo = getOrderNo(dispPlan[i], inputCasePiece);
				compCasePiece = dispPlan[i].getCasePieceFlag();
				compCustomer = dispPlan[i].getCustomerCode();

			}

			if (i == dispPlan.length - 1)
			{
				RetrievalSupportParameter tempParam = new RetrievalSupportParameter();

				//#CM724192
				// Display in the Preset area. 
				tempParam.setHostCollectBatchNoL(dispPlan[i].getHostCollectBatchno());
				tempParam.setOrderNoL(getOrderNo(dispPlan[i], inputCasePiece));
				if (!StringUtil.isBlank(inputCasePiece))
				{
					//#CM724193
					// If displayed by designating any option other than "All": 
					tempParam.setCasePieceflgName(DisplayUtil.getPieceCaseValue(inputCasePiece));
					//#CM724194
					// Hidden field item 
					tempParam.setCasePieceFlagL(inputCasePiece);
				}
				else
				{
					//#CM724195
					// If displayed by designating "All": 
					tempParam.setCasePieceflgName(DisplayUtil.getPieceCaseValue(dispPlan[i].getCasePieceFlag()));
					//#CM724196
					// Hidden field item 
					tempParam.setCasePieceFlagL(dispPlan[i].getCasePieceFlag());
				}
				tempParam.setCustomerCode(dispPlan[i].getCustomerCode());
				tempParam.setCustomerName(dispPlan[i].getCustomerName1());
				//#CM724197
				// Header 
				tempParam.setConsignorName(consignorName);
				tempParam.setConsignorCode(dispPlan[i].getConsignorCode());
				tempParam.setRetrievalPlanDate(dispPlan[i].getPlanDate());

				tempVec.addElement(tempParam);
				break;
			}

		}

		RetrievalSupportParameter[] dispParam = new RetrievalSupportParameter[tempVec.size()];
		tempVec.copyInto(dispParam);

		//#CM724198
		// 6001013 = Data is shown. 
		wMessage = "6001013";
		return dispParam;
	}
	
	//#CM724199
	/**
	 * Check the aggregation conditions and determine whether to aggregate the display or not. 
	 * @param plan Target Plan data to compare the values
	 * @param pBatchNo Host Aggregated Batch No. 
	 * @param pOrderNo Order No.
	 * @param pCasePiece Case/Piece division
	 * @param pCustomerCode Customer Code
	 * @param pInputCasePiece Case/Piece division to be designated via the enabled display 
	 * @return true: Disable aggregated display. false: Enable aggregated display. 
	 */
	protected boolean isDifferentLine(RetrievalPlan plan,String pBatchNo, String pOrderNo, String pCasePiece, String pCustomerCode, String pInputCasePiece)
	{
		if (StringUtil.isBlank(pInputCasePiece))
		{
			if (!pBatchNo.equals(plan.getHostCollectBatchno())
				|| !pOrderNo.equals(getOrderNo(plan, pInputCasePiece))
				|| !pCasePiece.equals(plan.getCasePieceFlag())
				|| !pCustomerCode.equals(plan.getCustomerCode()))
			{
				return true;
			}
		}
		else
		{
			if (!pBatchNo.equals(plan.getHostCollectBatchno())
				|| !pOrderNo.equals(getOrderNo(plan, pInputCasePiece))
				|| !pCustomerCode.equals(plan.getCustomerCode()))
			{
				return true;
			}
		}
		return false;
	}

	//#CM724200
	/**
	 * Obtain introduced Area type from the area table. 
	 * @param conn Database connection
	 * @return Area type 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	protected int[] geImportedSystemDiskKey(Connection conn) throws ReadWriteException, ScheduleException
	{
		AreaHandler areaHndl = new AreaHandler(conn);
		AreaSearchKey areaKey = new AreaSearchKey();
		areaKey.setAreaTypeGroup(1);
		areaKey.setAreaTypeCollect();
		Area[] allArea = (Area[]) areaHndl.find(areaKey);
		if (allArea == null || allArea.length == 0)
		{
			throw new ScheduleException();
		}
		
		int[] discKey = new int[allArea.length];
		for (int i = 0; i < allArea.length; i++)
		{
			if (allArea[i].getAreaType().equals(Integer.toString(Area.SYSTEM_DISC_KEY_WMS)))
			{
				discKey[i] = RetrievalSupportParameter.SYSTEM_DISC_KEY_FLOOR;
			}
			else if (allArea[i].getAreaType().equals(Integer.toString(Area.SYSTEM_DISC_KEY_IDM)))
			{
				discKey[i] = RetrievalSupportParameter.SYSTEM_DISC_KEY_IDM;
			}
			else if (allArea[i].getAreaType().equals(Integer.toString(Area.SYSTEM_DISC_KEY_ASRS)))
			{
				discKey[i] = RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS;
			}
			
		}
		return discKey;
	}

	//#CM724201
	/**
	 * Selecting division All via screen sorts the data, using the Case display info, the Piece display info, and the None display info as arguments, 
	 * in the order of Host Batch No., Order No., Case/Piece division, and then Customer Code. <BR>
	 * Set the Work Type in the Case/Piece division for display in the screen. <BR>
	 * @param casePlan Picking Plan Info for Case display 
	 * @param piecePlan Picking Plan Info for Piece display 
	 * @param nothingPlan Picking Plan Info for "None" display 
	 * @return Merged Picking Plan Info 
	 * @throws ScheduleException Announce it when unexpected error occurs. 
	 */
	protected RetrievalPlan[] margeRetrievalPlan(
		RetrievalPlan[] casePlan,
		RetrievalPlan[] piecePlan,
		RetrievalPlan[] nothingPlan)
		throws ScheduleException
	{
		try
		{
			//#CM724202
			// Merge the data with division "Case" and "None". 
			Vector tempVec1 = new Vector();
			int caseCnt = 0;
			int nothingCnt = 0;
			RetrievalPlan[] mixPlan = null;
			//#CM724203
			// Disable to merge if either one has no data. 
			if (casePlan != null && nothingPlan != null && casePlan.length != 0 && nothingPlan.length != 0)
			{
				String caseCompareStr = null;
				String nothingCompareStr = null;
				while (caseCnt < casePlan.length && nothingCnt < nothingPlan.length)
				{
					caseCompareStr = connectGroup(casePlan[caseCnt], RetrievalPlan.CASEPIECE_FLAG_CASE);
					nothingCompareStr = connectGroup(nothingPlan[nothingCnt], RetrievalPlan.CASEPIECE_FLAG_NOTHING);
					//#CM724204
					// Precede the Case data. 
					if (caseCompareStr.compareTo(nothingCompareStr) < 0)
					{
						//#CM724205
						// Set the Case for displaying. 
						casePlan[caseCnt].setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_CASE);
						tempVec1.addElement(casePlan[caseCnt]);
						caseCnt++;
					}
					//#CM724206
					// Precede the data with division None. 
					else if (caseCompareStr.compareTo(nothingCompareStr) > 0)
					{
						tempVec1.addElement(nothingPlan[nothingCnt]);
						nothingCnt++;
					}
					//#CM724207
					// There is no case that is equal. 
					else
					{
						throw new ScheduleException();
					}

				}
				//#CM724208
				// Set the remaining qty. 
				for (; caseCnt < casePlan.length; caseCnt++)
				{
					//#CM724209
					// Set the Case for displaying. 
					casePlan[caseCnt].setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_CASE);
					tempVec1.addElement(casePlan[caseCnt]);
				}
				for (; nothingCnt < nothingPlan.length; nothingCnt++)
				{
					tempVec1.addElement(nothingPlan[nothingCnt]);
				}
				//#CM724210
				// Commit the merged result. 
				mixPlan = new RetrievalPlan[tempVec1.size()];
				tempVec1.copyInto(mixPlan);

			}
			else if (casePlan != null && casePlan.length != 0)
			{
				for (int i = 0; i < casePlan.length; i++)
				{
					//#CM724211
					// Set the Case for displaying. 
					casePlan[i].setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_CASE);
				}
				mixPlan = casePlan;
			}
			else
			{
				mixPlan = nothingPlan;
			}

			//#CM724212
			// Merge the data merged at the above step and the data with division Piece. 
			Vector tempVec2 = new Vector();
			int mixCnt = 0;
			int pieceCnt = 0;
			RetrievalPlan[] resultPlan = null;
			//#CM724213
			// Disable to merge if either one has no data. 
			if (mixPlan != null && piecePlan != null && mixPlan.length != 0 && piecePlan.length != 0)
			{
				while (mixCnt < mixPlan.length && pieceCnt < piecePlan.length)
				{
					//#CM724214
					// Precede the merged data.
					if (connectGroup(mixPlan[mixCnt], mixPlan[mixCnt].getCasePieceFlag())
						.compareTo(connectGroup(piecePlan[pieceCnt], RetrievalPlan.CASEPIECE_FLAG_PIECE))
						< 0)
					{
						tempVec2.addElement(mixPlan[mixCnt]);
						mixCnt++;
					}
					//#CM724215
					// Precede Piece data.
					else if (
						connectGroup(mixPlan[mixCnt], mixPlan[mixCnt].getCasePieceFlag()).compareTo(
							connectGroup(piecePlan[pieceCnt], RetrievalPlan.CASEPIECE_FLAG_PIECE))
							> 0)
					{
						//#CM724216
						// Set the Case for displaying. 
						piecePlan[pieceCnt].setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_PIECE);
						tempVec2.addElement(piecePlan[pieceCnt]);
						pieceCnt++;
					}
					//#CM724217
					// There is no case that is equal. 
					else
					{
						throw new ScheduleException();
					}

				}
				//#CM724218
				// Set the remaining qty. 
				for (; mixCnt < mixPlan.length; mixCnt++)
				{
					tempVec2.addElement(mixPlan[mixCnt]);
				}
				for (; pieceCnt < piecePlan.length; pieceCnt++)
				{
					//#CM724219
					// Set the Case for displaying. 
					piecePlan[pieceCnt].setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_PIECE);
					tempVec2.addElement(piecePlan[pieceCnt]);
				}
				resultPlan = new RetrievalPlan[tempVec2.size()];
				tempVec2.copyInto(resultPlan);

			}
			else if (mixPlan != null && mixPlan.length != 0)
			{
				resultPlan = mixPlan;
			}
			else
			{
				for (int i = 0; i < piecePlan.length; i++)
				{
					//#CM724220
					// Set the Piece for displaying. 
					piecePlan[i].setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_PIECE);
				}
				resultPlan = piecePlan;
			}

			return resultPlan;
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}

	}

	//#CM724221
	/**
	 * Connect strings to check the values for precedence.<BR>
	 * Connect Host Aggregation key, Order No. (determine by Case/Piece division as argument), 
	 * Case/Piece division, and Customer Code. 
	 * @param plan Picking Plan Info that maintains the string information of the connection targets 
	 * @param casePiece Case/Piece division 
	 * @return String connected to compare the values
	 */
	protected String connectGroup(RetrievalPlan plan, String casePiece)
	{
		return getFillStr(plan.getHostCollectBatchno(), 32)
			.append(getFillStr(getOrderNo(plan, casePiece), 16))
			.append(getFillStr(casePiece, 1))
			.append(getFillStr(plan.getCustomerCode(), 16))
			.toString();

	}

	//#CM724222
	/**
	 * If the length of the string passed to the argument is shorter than the specified Length, 
	 * pad space(s) (' ') to fill the full length and return it. 
	 * @param str Compiled String 
	 * @param fullLength Designated Length 
	 * @return Compiled String 
	 */
	protected StringBuffer getFillStr(String str, int fullLength)
	{
		StringBuffer buf = new StringBuffer(str);

		for (int i = str.length(); i < fullLength; i++)
		{
			buf.append(" ");
		}

		return buf;
	}

	//#CM724223
	/**
	 * Set the search conditions for the search key passed as an argument. 
	 * @param skey Search key to set the search conditions 
	 * @param pParam Parameter via screen that contains search conditions 
	 * @param pDispCasePieceFlag Case/Piece division to be searched this time here (Input via screen) 
	 * @throws ReadWriteException Announce when error occurs while searching through database. 
	 */
	protected void setSearchKey(
		RetrievalPlanSearchKey skey,
		RetrievalSupportParameter pParam,
		String pDispCasePieceFlag)
		throws ReadWriteException
	{
		//#CM724224
		// Data with status flag other than "Completed" or "Deleted" 
		skey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION, "!=");
		skey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		//#CM724225
		// Data with Order No. input
		skey.setCaseOrderNo("", "IS NOT NULL", "(", "", "OR");
		skey.setPieceOrderNo("", "IS NOT NULL", "", ")", "AND");

		//#CM724226
		// Consignor Code
		skey.setConsignorCode(pParam.getConsignorCode());
		//#CM724227
		// Planned Picking Date
		skey.setPlanDate(pParam.getRetrievalPlanDate());
		//#CM724228
		// Batch No.
		if (!StringUtil.isBlank(pParam.getHostCollectBatchNo()))
		{
			skey.setHostCollectBatchno(pParam.getHostCollectBatchNo());
		}

		//#CM724229
		// Pay attention to the number of the following parentheses and their positions. 
		//#CM724230
		// Search for the allocatable Case inventory plan data. 
		if (pDispCasePieceFlag.equals(RetrievalPlan.CASEPIECE_FLAG_CASE))
		{
			//#CM724231
			// (( Case  AND Standby ) OR ( Mixed  AND ( Standby  OR Piece Completed ))) OR
			skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_CASE, "=", "((", "", "AND");
			setSearchCaseOrderNo(skey, pParam.getStartOrderNo(), pParam.getEndOrderNo());
			skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "", ")", "OR");

			skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_MIX, "=", "(", "", "AND");
			setSearchCaseOrderNo(skey, pParam.getStartOrderNo(), pParam.getEndOrderNo());
			skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
			skey.setSchFlag(RetrievalPlan.SCH_FLAG_PIECE_COMPLETION, "=", "", ")))", "OR");

			skey.setHostCollectBatchnoOrder(1, true);
			skey.setCaseOrderNoOrder(2, true);
			skey.setCasePieceFlagOrder(3, true);
			skey.setCustomerCodeOrder(4, true);

		}
		//#CM724232
		// Search for the allocatable Piece inventory plan data. 
		else if (pDispCasePieceFlag.equals(RetrievalPlan.CASEPIECE_FLAG_PIECE))
		{
			//#CM724233
			// ((Piece AND Standby) OR (Mixed AND (Standby OR Case Completed)))  OR
			skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_PIECE, "=", "((", "", "AND");
			setSearchPieceOrderNo(skey, pParam.getStartOrderNo(), pParam.getEndOrderNo());
			skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "", ")", "OR");

			skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_MIX, "=", "(", "", "AND");
			setSearchPieceOrderNo(skey, pParam.getStartOrderNo(), pParam.getEndOrderNo());
			skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
			skey.setSchFlag(RetrievalPlan.SCH_FLAG_CASE_COMPLETION, "=", "", ")))", "OR");

			skey.setHostCollectBatchnoOrder(1, true);
			skey.setPieceOrderNoOrder(2, true);
			skey.setCasePieceFlagOrder(3, true);
			skey.setCustomerCodeOrder(4, true);

		}
		//#CM724234
		// Search for the allocatable inventory plan data by designating None division. 
		else if (pDispCasePieceFlag.equals(RetrievalPlan.CASEPIECE_FLAG_NOTHING))
		{
			//#CM724235
			// (None AND Standby)- AND 
			skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_NOTHING, "=", "(", "", "AND");
			setSearchNothingOrderNo(skey, pParam.getStartOrderNo(), pParam.getEndOrderNo());
			skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "", ")", "AND");

		}

	}

	//#CM724236
	/**
	 * Set the specified range of Case Order No. for the search conditions. 
	 * @param skey Search key to set the search conditions 
	 * @param pStartOrder Start Order No. 
	 * @param pEndOrder End Order No. 
	 * @throws ReadWriteException Announce when error occurs while searching through database. 
	 */
	protected void setSearchCaseOrderNo(RetrievalPlanSearchKey skey, String pStartOrder, String pEndOrder)
		throws ReadWriteException
	{
		//#CM724237
		// Start Order No. 
		if (!StringUtil.isBlank(pStartOrder))
		{
			skey.setCaseOrderNo(pStartOrder, ">=");
		}
		//#CM724238
		// End Order No. 
		if (!StringUtil.isBlank(pEndOrder))
		{
			skey.setCaseOrderNo(pEndOrder, "<=");
		}
	}

	//#CM724239
	/**
	 * Set the specified range of Piece Order No. for the search conditions. 
	 * @param skey Search key to set the search conditions 
	 * @param pStartOrder Start Order No. 
	 * @param pEndOrder End Order No. 
	 * @throws ReadWriteException Announce when error occurs while searching through database. 
	 */
	protected void setSearchPieceOrderNo(RetrievalPlanSearchKey skey, String pStartOrder, String pEndOrder)
		throws ReadWriteException
	{
		//#CM724240
		// Start Order No. 
		if (!StringUtil.isBlank(pStartOrder))
		{
			skey.setPieceOrderNo(pStartOrder, ">=");
		}
		//#CM724241
		// End Order No. 
		if (!StringUtil.isBlank(pEndOrder))
		{
			skey.setPieceOrderNo(pEndOrder, "<=");
		}
	}

	//#CM724242
	/**
	 * Set the specified range of Order No. for the search conditions to search data with None. 
	 * @param skey Search key to set the search conditions 
	 * @param pStartOrder Start Order No. 
	 * @param pEndOrder End Order No. 
	 * @throws ReadWriteException Announce when error occurs while searching through database. 
	 */
	protected void setSearchNothingOrderNo(RetrievalPlanSearchKey skey, String pStartOrder, String pEndOrder)
		throws ReadWriteException
	{
		//#CM724243
		// Start Order No. 
		if (!StringUtil.isBlank(pStartOrder))
		{
			//#CM724244
			// ( Case Order  >= Input value OR Piece Order  >= Input value) AND
			skey.setCaseOrderNo(pStartOrder, ">=", "(", "", "OR");
			skey.setPieceOrderNo(pStartOrder, ">=", "", ")", "AND");
		}
		//#CM724245
		// End Order No. 
		if (!StringUtil.isBlank(pEndOrder))
		{
			//#CM724246
			// (Case Order  < =  the input value  OR Piece Order  < =  the input value)  AND
			skey.setCaseOrderNo(pEndOrder, "<=", "(", "", "OR");
			skey.setPieceOrderNo(pEndOrder, "<=", "", ")", "AND");
		}
	}
	
	//#CM724247
	/**
	 * Receive the contents entered via screen as a parameter, and start the schedule to start the Order Picking. <BR>
	 * Assume that two or more data may be input, including setting via preset area. So, require the parameter to receive them as an array. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> instance throws <CODE>ScheduleException</CODE>. <BR>
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0. <BR>
	 *          Return null when input condition error occurs. <BR>
	 *          Returning array with the number of elements 0 or null enables to obtain the error content as a message using the <CODE>getMessage</CODE> method. <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		wFunctionName = RetrievalSupportParameter.FUNCTION_NAME_ORDER_SCH;
		return startAllocate(conn, startParams);
	}
	
	//#CM724248
	/**
	 * Check each process condition and start the allocation process. <BR>
	 * In the event of shortage, print the Shortage list. <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> instance throws <CODE>ScheduleException</CODE>. <BR>
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0. <BR>
	 *          Return null when input condition error occurs. <BR>
	 *          Returning array with the number of elements 0 or null enables to obtain the error content as a message using the <CODE>getMessage</CODE> method. <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	protected RetrievalSupportParameter[] startAllocate(Connection conn, Parameter[] startParams)
			throws ReadWriteException, ScheduleException
	{
		//#CM724249
		// For judgment of commit and rollback of DB 
		boolean registFlag = false;

		//#CM724250
		// Allow this flag to determine whether Processing Load flag is updated in its own class. 
		boolean updateLoadDataFlag = false;

		try
		{
			//#CM724251
			// Check the Daily Update Processing. 
			if (isDailyUpdate(conn))
			{
				return null;
			}
			//#CM724252
			// "Check" process for "Load" flag
			if (isLoadingData(conn))
			{
				return null;
			}
			//#CM724253
			// Update "Load" flag: "Loading in process" 
			if (!updateLoadDataFlag(conn, true))
			{
				return null;
			}
			//#CM724254
			// Reflect this to DB 
			doCommit(conn, PROCESSNAME);
			updateLoadDataFlag = true;

			RetrievalSupportParameter[] param = (RetrievalSupportParameter[]) startParams;
			//#CM724255
			// Check the Worker code and password 
			if (!checkWorker(conn, param[0]))
			{
				return null;
			}
			//#CM724256
			// Check the Daily Update Processing. 
			if (isDailyUpdate(conn))
			{
				return null;
			}
	
			//#CM724257
			// Check for exclusion of all the target data. 
			if (!lockAll(conn, startParams))
			{
				//#CM724258
				// 6027008=Unable to process this data. Another terminal is updating it. 
				wMessage = "6027008";
				return null;
			}
			//#CM724259
			// Execute the process to lock the Inventory Info. 
			if (!lockStock(conn))
			{
				//#CM724260
				// 6027008=Unable to process this data. Another terminal is updating it. 
				wMessage = "6027008";
				return null;
			}
	
			//#CM724261
			// Set the relevant Plan unique key for the preset area. 
			RetrievalSupportParameter[] allocateParam = getPlanUkey(conn, param);
			if (allocateParam == null)
			{
				return null;
			}
	
			//#CM724262
			// Execute allocation.
			RetrievalSupportParameter[] viewParam = null;
			if (allocate(conn, allocateParam, param[0].getWorkerCode(), param[0].getTerminalNumber()))
			{
				registFlag = true;
				//#CM724263
				// Search through the Picking Plan Info again. 
				viewParam = (RetrievalSupportParameter[]) query(conn, param[0]);
			}
	
			//#CM724264
			// Execute the process for printing the Order Shortage list in the event of shortage. 
			if (wIsShortage)
			{
				startPrint(conn, wShortageBatch, param[0].getCasePieceflg());
			}
			else
			{
				//#CM724265
				// 6021010=Data was committed. 
				wMessage = "6021010";
			}
			//#CM724266
			// Return the latest Picking Plan Info 
			return viewParam;
		}
		catch (ReadWriteException e)
		{
			doRollBack(conn, PROCESSNAME);
			throw new ReadWriteException(e.getMessage());
		}
		catch (Exception e)
		{
			doRollBack(conn, PROCESSNAME);
			throw new ScheduleException(e.getMessage());
		}
		finally
		{
			//#CM724267
			// Failing to add rolls back the transaction. 
			if (!registFlag)
			{
				doRollBack(conn, PROCESSNAME);
			}
			
			//#CM724268
			// If Processing Extraction flag was updated in its own class,
			//#CM724269
			// update the Loading in-process flag to 0: Stopping. 
			if( updateLoadDataFlag )
			{
				//#CM724270
				// Update the Loading-in-progress flag: Stopping 
				updateLoadDataFlag(conn, false);
				doCommit(conn, PROCESSNAME);
			}
		}

	}

	//#CM724271
	/**
	 * Clicking on the Submit button searches for the corresponding Plan unique key through the Preset data. <BR>
	 * Maintain the unique key corresponding to the Preset line in the Vector, and set it in the corresponding line of the array of the argument parameter. <BR>
	 * If there is no corresponding data in the preset line, assume it to have been updated via other terminal and regard it as error via other terminal. 
	 * @param conn Database connection
	 * @param pParam RetrievalSupportParameter containing Preset data 
	 * @return RetrievalSupportParameter for which unique key is set 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected RetrievalSupportParameter[] getPlanUkey(Connection conn, RetrievalSupportParameter[] pParam) throws ReadWriteException
	{
		RetrievalPlanSearchKey skey = new RetrievalPlanSearchKey();
		RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn);
		
		Vector tempParam = new Vector();
		for (int i = 0; i < pParam.length; i++)
		{
			skey.KeyClear();
			//#CM724272
			// Data with status flag other than "Completed" or "Deleted" 
			skey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION, "!=");
			skey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
			//#CM724273
			// Data with Order No. input
			skey.setCaseOrderNo("", "IS NOT NULL", "(", "", "OR");
			skey.setPieceOrderNo("", "IS NOT NULL", "", ")", "AND");

			//#CM724274
			// Consignor Code
			skey.setConsignorCode(pParam[i].getConsignorCode());
			//#CM724275
			// Planned Picking Date
			skey.setPlanDate(pParam[i].getRetrievalPlanDate());
			//#CM724276
			// Host Aggregated Batch No. in the preset area 
			if (!StringUtil.isBlank(pParam[i].getHostCollectBatchNoL()))
			{
				skey.setHostCollectBatchno(pParam[i].getHostCollectBatchNoL());
			}
			//#CM724277
			// Case/Piece division in the preset area 
			if (pParam[i].getCasePieceFlagL().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				//#CM724278
				// (( Case  AND Standby ) OR ( Mixed  AND ( Standby  OR Piece Completed ))) AND
				skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_CASE, "=", "((", "", "AND");
				skey.setCaseOrderNo(pParam[i].getOrderNoL());
				skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "", ")", "OR");

				skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_MIX, "=", "(", "", "AND");
				skey.setCaseOrderNo(pParam[i].getOrderNoL());
				skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
				skey.setSchFlag(RetrievalPlan.SCH_FLAG_PIECE_COMPLETION, "=", "", ")))", "AND");
			}
			else if (pParam[i].getCasePieceFlagL().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				//#CM724279
				// ((Piece AND Standby) OR (Mixed AND (Standby OR Case Completed)))  AND
				skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_PIECE, "=", "((", "", "AND");
				skey.setPieceOrderNo(pParam[i].getOrderNoL());
				skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "", ")", "OR");

				skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_MIX, "=", "(", "", "AND");
				skey.setPieceOrderNo(pParam[i].getOrderNoL());
				skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
				skey.setSchFlag(RetrievalPlan.SCH_FLAG_CASE_COMPLETION, "=", "", ")))", "AND");
			}
			else
			{
				skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_NOTHING);
				skey.setCaseOrderNo(pParam[i].getOrderNoL(), "=", "(", "", "OR");
				skey.setPieceOrderNo(pParam[i].getOrderNoL(), "=", "", ")", "AND");
			}
			if (!StringUtil.isBlank(pParam[i].getCustomerCode()))
			{
				skey.setCustomerCode(pParam[i].getCustomerCode());
			}
			skey.setRetrievalPlanUkeyOrder(1, true);
			skey.setRetrievalPlanUkeyCollect();

			RetrievalPlan[] plan = (RetrievalPlan[]) planHandler.find(skey);
			
			if (plan == null || plan.length == 0)
			{
				//#CM724280
				// 6023209=No.{0} Unable to process this data. It has been updated via other work station. 
				wMessage = "6023209" + wDelim + pParam[i].getRowNo();
				return null;
			}
			else
			{
				Vector temp = new Vector();
				for (int j = 0; j < plan.length; j++)
				{
					temp.addElement(plan[j].getRetrievalPlanUkey());
				}
				pParam[i].setPlanUkeyList(temp);
				
				tempParam.addElement(pParam[i]);

			}
		}
		
		if (tempParam.size() == 0)
		{
			//#CM724281
			// 6003006=Unable to process this data. It has been updated via other work station. 
			wMessage = "6003006";
			return null;
		}
		else
		{
			RetrievalSupportParameter[] allocateList = new RetrievalSupportParameter[tempParam.size()];
			tempParam.copyInto(allocateList);
			return allocateList;
		}
		
		
	}

	//#CM724282
	/**
	 * Pass the Batch No. and Case/Piece division to the class to print the Order Shortage report.<BR>
	 * @param  conn Database connection object
	 * @param  batchno Batch No. 
	 * @param  casepieceflag Case/Piece division
	 */
	protected void startPrint(Connection conn, String batchno, String casepieceflag)
	{
		try
		{
			RetrievalOrderShortageWriter writer = new RetrievalOrderShortageWriter(conn);
			writer.setBatchNo(batchno);
			writer.setCasePiece(casepieceflag);

			//#CM724283
			// Start the printing process. 
			if (writer.startPrint())
			{
				//#CM724284
				// 6021019=Data has been set. (Shortage occurred. Check the list.) 
				wMessage = "6021019";
			}
			else
			{
				//#CM724285
				// 6022036=Setting completed. (shortage occurred). Error printing shortage list. Check the log 
				wMessage = "6022036";
			}
		}
		catch (Exception e)
		{
			//#CM724286
			// 6022036=Setting completed. (shortage occurred). Error printing shortage list. Check the log 
			wMessage = "6022036";
		}

	}

	//#CM724287
	/**
	 * Check for any change via other terminal. <BR>
	 * Lock the target data. Return true if succeeded. Return false if failed. <BR>
	 * The target data is Picking Plan Info table (DNRETRIEVALPLAN). <BR>
	 * @param conn Database connection object
	 * @param startParams <CODE>RetrievalSupportParameter</CODE> that has search conditions. 
	 * @return Return true if locking succeeded, or return false if failed. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	protected boolean lockAll(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter param = (RetrievalSupportParameter) startParams[0];

		try
		{
			//#CM724288
			// Obtain the division to be displayed this time here from the Case/Piece division entered via screen.
			String inputCasePiece = null;
			if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				inputCasePiece = RetrievalPlan.CASEPIECE_FLAG_CASE;
			}
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				inputCasePiece = RetrievalPlan.CASEPIECE_FLAG_PIECE;
			}
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				inputCasePiece = RetrievalPlan.CASEPIECE_FLAG_NOTHING;
			}
			else
			{
				//#CM724289
				// For data with division All, disable to set anything because of no division. 
			}

			RetrievalPlanSearchKey skey = new RetrievalPlanSearchKey();

			if (!param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
			{
				setSearchKey(skey, param, inputCasePiece);
			}
			else
			{
				skey.KeyClear();
				setSearchKey(skey, param, RetrievalPlan.CASEPIECE_FLAG_CASE);
				setSearchKey(skey, param, RetrievalPlan.CASEPIECE_FLAG_PIECE);
				setSearchKey(skey, param, RetrievalPlan.CASEPIECE_FLAG_NOTHING);
			}
			//#CM724290
			// Lock Data. 
			RetrievalPlanHandler planHandl = new RetrievalPlanHandler(conn);
			planHandl.lockNowait(skey);

			return true;
		}
		catch (LockTimeOutException e)
		{
			return false;
		}

	}

	//#CM724291
	/**
	 * Execute the process to lock the Center Inventory in the Inventory Info table (DMSTOCK). <BR>
	 * @param conn Database connection object
	 * @return Return true if locking succeeded, or return false if failed. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	protected boolean lockStock(Connection conn) throws ReadWriteException, ScheduleException
	{
		try
		{
			StockHandler wStHandler = new StockHandler(conn);
			StockSearchKey searchKey = new StockSearchKey();

			//#CM724292
			// Lock the Inventory Info (DMSTOCK). 
			searchKey.KeyClear();
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);

			wStHandler.lockNowait(searchKey);

			//#CM724293
			// Disable to check for count because Shortage may occur even if no Inventory Info exists. 

			return true;
		}
		catch (LockTimeOutException e)
		{
			return false;
		}
	}

	//#CM724294
	/**
	 * Execute the Allocation process. 
	 * @param conn Database connection object
	 * @param param RetrievalSupportParameter that maintains the info input via screen
	 * @param workerCode Worker Code
	 * @param terminalNo Terminal No.
	 * @return Result of executed allocation (allocation succeeded: true, allocation failed: false) 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	protected boolean allocate(Connection conn, RetrievalSupportParameter param[], String workerCode, String terminalNo)
		throws ReadWriteException, ScheduleException
	{
		AbstractAllocator allocator = null;
		boolean containAsrs = false;
		String[] area = param[0].getAllocateField();
		for (int i = 0; i < area.length; i++)
		{
			if (area[i].equals(Integer.toString(RetrievalSupportParameter.SYSTEM_DISC_KEY_ASRS)))
			{
				containAsrs = true;
			}
		}
		//#CM724295
		// If Allocation target includes the Automated Warehouse inventory: 
		if (containAsrs)
		{
			switch (WmsParam.SHORTAGE_RECOVERY_RANGE)
			{
				//#CM724296
				// In unit of Details 
				case WmsParam.SHORTAGE_RECOVERY_RANGE_LINE :
					break;
				//#CM724297
				// In setting unit:
				case WmsParam.SHORTAGE_RECOVERY_RANGE_ALL :
					allocator = new AsrsAllAllocator(conn);
					break;
				default :
					throw new ScheduleException();
			}
		}
		//#CM724298
		// If Allocation target does not include the Automated Warehouse inventory: 
		else
		{
			switch (WmsParam.SHORTAGE_RECOVERY_RANGE)
			{
				//#CM724299
				// In unit of Details 
				case WmsParam.SHORTAGE_RECOVERY_RANGE_LINE :
					break;
				//#CM724300
				// In setting unit:
				case WmsParam.SHORTAGE_RECOVERY_RANGE_ALL :
					allocator = new AllAllocator(conn);
					break;
				default :
					throw new ScheduleException();
			}
		}

		try
		{
			//#CM724301
			// Set the Invoking source screen and start the allocation. 
			allocator.setFunctionName(wFunctionName);
			allocator.setWorker(workerCode);
			allocator.setTerminalNo(terminalNo);
			if (!allocator.allocate(param))
			{
				wShortageBatch = allocator.getShortageBatchNo();
				wIsShortage = true;

				allocator.completeShortage(param[0].getShortageFlag());
				
				if (param[0].getShortageFlag())
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return true;
			}

		}
		catch (ReadWriteException e)
		{
			throw e;
		}
		catch (ScheduleException e)
		{
			throw e;
		}
		finally
		{
			allocator.close();
		}

	}
	
	//#CM724302
	/**
	 * Determine to obtain either Case Order or Piece Order based on the division, and
	 * obtain the Order No. from the Picking Plan Info. 
	 * @param plan Picking Plan Info 
	 * @param workForm Case/Piece division 
	 * @return Order No.
	 */
	private String getOrderNo(RetrievalPlan plan, String workForm)
	{
		String orderNo = null;
		//#CM724303
		// If All is designated for the division via screen, set null in workform typically.
		//#CM724304
		// Reset it and set the division for display upon obtaining the Plan data. 
		if (StringUtil.isBlank(workForm))
		{
			//#CM724305
			// Execute recursive call and obtain the Order No. 
			return getOrderNo(plan, plan.getCasePieceFlag());
		}

		//#CM724306
		// Obtain the Case Order, for data with division Case. 
		if (workForm.equals(RetrievalPlan.CASEPIECE_FLAG_CASE))
		{
			orderNo = plan.getCaseOrderNo();
		}
		//#CM724307
		// For data with division "Piece", obtain the Piece Order
		else if (workForm.equals(RetrievalPlan.CASEPIECE_FLAG_PIECE))
		{
			orderNo = plan.getPieceOrderNo();
		}
		//#CM724308
		// Obtain the data with input in either one if designated "None". 
		else
		{
			if (!StringUtil.isBlank(plan.getCaseOrderNo()))
			{
				orderNo = plan.getCaseOrderNo();
			}
			else
			{
				orderNo = plan.getPieceOrderNo();
			}
		}
		return orderNo;
	}


}
