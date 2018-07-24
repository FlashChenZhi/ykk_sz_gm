// $Id: RetrievalItemScheduleSCH.java,v 1.3 2007/02/07 04:19:56 suresh Exp $
package jp.co.daifuku.wms.retrieval.schedule;

//#CM723327
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.retrieval.report.RetrievalItemShortageWriter;

//#CM723328
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * Allow this class to execute the process to set WEB Item Picking (schedule).  <BR>
 * Receive the contents entered via screen as a parameter, and execute the process for schedule of Picking by Item.  <BR>
 * Allow each methods contained in this class to receive a connection object and executes the process for updating the database, but <BR>
 * disable to commit nor roll-back the transaction.  <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind()</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *   Return the Consignor Code, if only one Consignor Code exists in the Picking Plan Info (Order No.: Blank, and Schedule process flag: Not Processed). <BR> 
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR> 
 *   <BR>
 *   [search conditions]  <BR> 
 *   <DIR>
 *     Data with status flag other than "Completed" or "Deleted" <BR> 
 *     Order No.: Blank <BR>
 *     Schedule flag: other than "Allocation Completed" <BR> 
 *   </DIR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Display" button (<CODE>query()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it. <BR>
 *   If no corresponding data is found, return <CODE>Parameter</CODE> array with number of elements equal to 0. Return null when condition error occurs. <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
 *   Search through the Picking Plan Info table. (DNRETREIVALPLAN) <BR>
 *   Disable to display if the search target count exceeds 1000 (MAX_NUMBER_OF_DISP defined in WMSParam). <BR>
 *   <BR>
 *   [search conditions]  <BR> 
 *   <DIR>
 *     Data with status flag other than "Completed" or "Deleted" <BR> 
 *     Order No. is a blank. <BR>
 *   </DIR>
 *   <BR>
 *   [Parameter]  *Mandatory Input<BR>
 *   <DIR>
 *     Worker Code* <BR>
 *     Password * <BR>
 *     Consignor Code* <BR>
 *     Planned Picking Date* <BR>
 *     Batch No. <BR>
 *     Start Item Code  <BR>
 *     End Item Code  <BR>
 *     Case/Piece division  <BR>
 *   </DIR>
 *   <BR>
 *   [Sorting condition ]  <BR>
 *   <DIR>
 *     Batch No. <BR>
 *     Item Code <BR>
 *     Case/Piece division <BR>
 *     Case Location <BR>
 *     Piece Location  <BR>
 *   </DIR>
 *   <BR>
 *   [Check Process Condition]  <BR>
 *   <BR>
 *   -"Check" process for Worker Code-(<CODE>AbstructRetrievalSCH()</CODE> class) <BR>
 *   <BR>
 *   <Returned data>
 *   <DIR>
 *     Consignor Code <BR>
 *     Consignor Name (with the latest Added date)  <BR>
 *     Planned Picking Date <BR>
 *     Batch No. <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Case/Piece division (String for display)  <BR>
 *     Picking Location  <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 3. Process by clicking "Setup" button (<CODE>startSCHgetParams()</CODE> method)  <BR>
 * <BR>
 * Invoke the process for starting Order Picking. Refer to Start Order Picking. 
 * </FONT>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/04</TD><TD>C.Kaminishizono</TD><TD>New creation</TD></TR>
 * <TR><TD>2006/03/22</TD><TD>Y.Okamura</TD><TD>Change was made to succeed Order Picking start </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:56 $
 * @author  $Author: suresh $
 */
public class RetrievalItemScheduleSCH extends RetrievalOrderScheduleSCH
{
	//#CM723329
	/**
	 * Class Name: Start Item Picking 
	 */
	public static String PROCESSNAME = "RetrievalItemScheduleSCH";

	//#CM723330
	// Class method --------------------------------------------------
	//#CM723331
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:56 $");
	}
	
	//#CM723332
	// Constructors --------------------------------------------------
	//#CM723333
	/**
	 * Initialize this class. 
	 * @param conn Database connection object
	 */
	public RetrievalItemScheduleSCH()
	{
		wMessage = null;
	}

	//#CM723334
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * Return the Consignor Code, if only one Consignor Code exists in the Picking Plan Info. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.  
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. 
	 * @param conn Database connection object
	 * @param searchParam Instance of <CODE>StoragePlanParameter</CODE> class with conditions to obtain the display data. <BR>
	 *         Designating any instance other than <CODE>StoragePlanParameter</CODE> throws ScheduleException. <BR>
	 * @return Class that implements the <CODE>Parameter</CODE> interface that contains the search result. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter dispData = new RetrievalSupportParameter();
		dispData.setSystemDiscKeyArray(geImportedSystemDiskKey(conn));

		RetrievalPlanHandler wRpHandler = new RetrievalPlanHandler(conn);
		RetrievalPlanSearchKey searchKey = new RetrievalPlanSearchKey();
		//#CM723335
		// Search Data 
		searchKey.KeyClear();
		//#CM723336
		// Data with status flag other than "Completed" or "Deleted" 
		searchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION, "!=");
		searchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		//#CM723337
		// Schedule flag: other than "Allocation Completed" 
		searchKey.setSchFlag(RetrievalPlan.SCH_FLAG_COMPLETION, "!=");
		//#CM723338
		// Require that the Order is null. 
		searchKey.setCaseOrderNo("");
		searchKey.setPieceOrderNo("");
		
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		if (wRpHandler.count(searchKey) == 1)
		{
			try
			{
				RetrievalPlan rData = (RetrievalPlan)wRpHandler.findPrimary(searchKey);
				dispData.setConsignorCode(rData.getConsignorCode());
			}
			catch (NoPrimaryException e)
			{
				return dispData;
			}
		}
		return dispData;
	}
	
	//#CM723339
	/**
	 * Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * @param conn Instance to maintain database connection. <BR>
	 * @param searchParam Instance of <CODE>StoragePlanParameter</CODE> class with conditions to obtain the display data. <BR>
	 *         Designating any instance other than <CODE>StoragePlanParameter</CODE> throws ScheduleException. <BR>
	 * @return Array of <CODE>StoragePlanParameter</CODE> instance with search result. <BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0. <BR>
	 *          Return null if the search result count exceeds 1000 or when error occurs with input condition. <BR>
	 *          Returning array with number of elements 0 (zero) or null allows to obtain the error contents as a message using <CODE>getMessage()</CODE> method. <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.<BR>
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. <BR>
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		RetrievalSupportParameter param = (RetrievalSupportParameter)searchParam;
		
		//#CM723340
		// Check the Worker code and password 
		if (!checkWorker(conn, param))
		{
			 return null;
		}
		
		//#CM723341
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
			//#CM723342
			// For data with division All, disable to set anything because of no division. 
		}

		//#CM723343
		// Obtain the target data. 
		RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn);
		RetrievalPlan[] dispPlan = null;
		if (!param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
		{
			RetrievalPlanSearchKey skey = new RetrievalPlanSearchKey();
			setSearchKey(
				skey,
				param,
				inputCasePiece);
			
			//#CM723344
			// Check the Count of displayed data. 
			if(!canLowerDisplay(planHandler.count(skey)))
			{
				return returnNoDisplayParameter();
			}
			
			dispPlan = (RetrievalPlan[]) planHandler.find(skey);
		}
		else
		{
			//#CM723345
			// Obtain data by division to commit the order sequence and the display type, and 
			//#CM723346
			// merge them. 
			RetrievalPlanSearchKey casekey = new RetrievalPlanSearchKey();
			setSearchKey(
				casekey,
				param,
				RetrievalPlan.CASEPIECE_FLAG_CASE);

			RetrievalPlanSearchKey piecekey = new RetrievalPlanSearchKey();
			setSearchKey(
				piecekey,
				param,
				RetrievalPlan.CASEPIECE_FLAG_PIECE);

			RetrievalPlanSearchKey nothingkey = new RetrievalPlanSearchKey();
			setSearchKey(
				nothingkey,
				param,
				RetrievalPlan.CASEPIECE_FLAG_NOTHING);
			
			int dispCount = planHandler.count(casekey)
							+ planHandler.count(piecekey)
							+ planHandler.count(nothingkey);
			
			//#CM723347
			// Check the Count of displayed data. 
			if(!canLowerDisplay(dispCount))
			{
				return returnNoDisplayParameter();
			}
			
			RetrievalPlan[] casePlan = (RetrievalPlan[]) planHandler.find(casekey);
			RetrievalPlan[] piecePlan = (RetrievalPlan[]) planHandler.find(piecekey);
			RetrievalPlan[] nothingPlan = (RetrievalPlan[]) planHandler.find(nothingkey);
			
			dispPlan = margeRetrievalPlan(casePlan, piecePlan, nothingPlan);
		}

		if (dispPlan.length <= 0)
		{
			//#CM723348
			// No target data was found. 
			wMessage = "6003018";
			return new RetrievalSupportParameter[0];
		}

		//#CM723349
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

		Vector vec = new Vector();
		String compBatchNo = "";
		String compItemCode = "";
		String compCasePieceFlag = "";
		String compLocationNo = "";
		
		for (int i = 0 ; i < dispPlan.length; i++)
		{
			if (i == 0)
			{
				compBatchNo = dispPlan[i].getHostCollectBatchno();
				compItemCode = dispPlan[i].getItemCode();
				compCasePieceFlag = dispPlan[i].getCasePieceFlag();
				compLocationNo = getLocationNo(dispPlan[i], inputCasePiece);
			}
			
			if (!compBatchNo.equals(dispPlan[i].getHostCollectBatchno())
			|| !compItemCode.equals(dispPlan[i].getItemCode())
			|| !compCasePieceFlag.equals(dispPlan[i].getCasePieceFlag())
			|| !compLocationNo.equals(getLocationNo(dispPlan[i], inputCasePiece)))
			{
				RetrievalSupportParameter tempParam = new RetrievalSupportParameter();
					
				//#CM723350
				// Consignor Code
				tempParam.setConsignorCode(dispPlan[i-1].getConsignorCode());
				//#CM723351
				// Consignor Name (Consignor Name with later Added Date/Time) 
				tempParam.setConsignorName(consignorName);
				//#CM723352
				// Planned Picking Date
				tempParam.setRetrievalPlanDate(dispPlan[i-1].getPlanDate());
				//#CM723353
				// Batch No. (Host Aggregated Unit key) 
				tempParam.setHostCollectBatchNo(dispPlan[i-1].getHostCollectBatchno());
				//#CM723354
				// Item Code
				tempParam.setItemCode(dispPlan[i-1].getItemCode());
				//#CM723355
				// Item Name
				tempParam.setItemName(dispPlan[i-1].getItemName1());
				//#CM723356
				// Division (Name) 
				if (!StringUtil.isBlank(inputCasePiece))
				{
					tempParam.setCasePieceflgName(DisplayUtil.getPieceCaseValue(inputCasePiece));
					//#CM723357
					// Division 
					tempParam.setCasePieceFlagL(inputCasePiece);
				}
				else
				{
					tempParam.setCasePieceflgName(DisplayUtil.getPieceCaseValue(dispPlan[i-1].getCasePieceFlag()));
					//#CM723358
					// Division 
					tempParam.setCasePieceFlagL(dispPlan[i-1].getCasePieceFlag());
				}
				//#CM723359
				// Picking Location No 
				tempParam.setRetrievalLocation(getLocationNo(dispPlan[i-1], inputCasePiece));

				vec.addElement(tempParam);
				
				compBatchNo = dispPlan[i].getHostCollectBatchno();
				compItemCode = dispPlan[i].getItemCode();
				compCasePieceFlag = dispPlan[i].getCasePieceFlag();
				compLocationNo = getLocationNo(dispPlan[i], inputCasePiece);
			}
			
			if (i == dispPlan.length - 1)
			{
				RetrievalSupportParameter tempParam = new RetrievalSupportParameter();
					
				//#CM723360
				// Consignor Code
				tempParam.setConsignorCode(dispPlan[i].getConsignorCode());
				//#CM723361
				// Consignor Name (Consignor Name with later Added Date/Time) 
				tempParam.setConsignorName(consignorName);
				//#CM723362
				// Planned Picking Date
				tempParam.setRetrievalPlanDate(dispPlan[i].getPlanDate());
				//#CM723363
				// Batch No. (Host Aggregated Unit key) 
				tempParam.setHostCollectBatchNo(dispPlan[i].getHostCollectBatchno());
				//#CM723364
				// Item Code
				tempParam.setItemCode(dispPlan[i].getItemCode());
				//#CM723365
				// Item Name
				tempParam.setItemName(dispPlan[i].getItemName1());
				//#CM723366
				// Division (Name) 
				if (!StringUtil.isBlank(inputCasePiece))
				{
					tempParam.setCasePieceflgName(DisplayUtil.getPieceCaseValue(inputCasePiece));
					//#CM723367
					// Division 
					tempParam.setCasePieceFlagL(inputCasePiece);
				}
				else
				{
					tempParam.setCasePieceflgName(DisplayUtil.getPieceCaseValue(dispPlan[i].getCasePieceFlag()));
					//#CM723368
					// Division 
					tempParam.setCasePieceFlagL(dispPlan[i].getCasePieceFlag());
				}
				//#CM723369
				// Picking Location No 
				tempParam.setRetrievalLocation(getLocationNo(dispPlan[i], inputCasePiece));
				vec.addElement(tempParam);
			}				
		}

		RetrievalSupportParameter[] paramArray = new RetrievalSupportParameter[vec.size()];
		vec.copyInto(paramArray);

		//#CM723370
		// 6001013 = Data is shown. 
		wMessage = "6001013";
		return paramArray;
	}
	
	//#CM723371
	/**
	 * Receive the contents entered via screen as a parameter, and start the schedule to start the Order Picking. <BR>
	 * Assume that two or more data may be input, including setting via preset area. So, require the parameter to receive them as an array. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * Return true if the schedule normally completed, or return false if failed. 
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents <BR>
	 *         Designating any instance other than RetrievalSupportParameter instance throws <CODE>ScheduleException</CODE>. <BR>
	 *         Use <CODE>getMessage()</CODE> method to refer to the content of the error 
	 * @return Array of parameter object that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		wFunctionName = RetrievalSupportParameter.FUNCTION_NAME_ITEM_SCH;
		return startAllocate(conn, startParams);
	}
	
	//#CM723372
	/**
	 * Pass the Batch No. and Case Piece flag to the class to print the Order Shortage report.<BR>
	 * Disables to print if no print data. <BR>
	 * Receive true if printing succeeded or false if failed, from the class for the process of printing report and<BR>
	 * return the result. <BR>
	 * Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
	 * @param  conn Connection Database connection object
	 * @param  batchno Batch No. 
	 * @param  casepieceflag Case/Piece division
	 */
	protected void startPrint(Connection conn, String batchno, String casepieceflag)
	{
		try
		{
			RetrievalItemShortageWriter writer = new RetrievalItemShortageWriter(conn);
			writer.setBatchNo(batchno);
			writer.setCasePiece(casepieceflag);

			//#CM723373
			// Start the printing process. 
			if (writer.startPrint())
			{
				//#CM723374
				// 6021019=Data has been set. (Shortage occurred. Check the list.) 
				wMessage = "6021019";
			}
			else
			{
				//#CM723375
				// 6022036=Setting completed. (shortage occurred). Error printing shortage list. Check the log 
				wMessage = "6022036";
			}
		}
		catch (Exception e)
		{
			//#CM723376
			// 6022036=Setting completed. (shortage occurred). Error printing shortage list. Check the log 
			wMessage = "6022036";
		}

	}

	//#CM723377
	/**
	 * Connect strings to check the values for precedence.
	 * Connect the Host aggregation key, the Item Code, the Case/Piece division, and the Location No.
	 * (Determine it based on the Case/Piece division as an argument).
	 * 
	 * @param plan Picking Plan Info that maintains the string information of the connection targets 
	 * @param casePiece Case/Piece division 
	 * @return String connected to compare the values
	 */
	protected String connectGroup(RetrievalPlan plan, String casePiece)
	{
		return getFillStr(plan.getHostCollectBatchno(), 32)
						.append(getFillStr(plan.getItemCode(), 16))
						.append(getFillStr(casePiece, 1))
						.append(getFillStr(getLocationNo(plan, casePiece), 16)).toString();

	}

	//#CM723378
	/**
	 * Set the search conditions for the search key passed as an argument. 
	 * 
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
		//#CM723379
		// Data with status flag other than "Completed" or "Deleted" 
		skey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION, "!=");
		skey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		//#CM723380
		// Require that the Order is null. 
		skey.setCaseOrderNo("", "IS NULL");
		skey.setPieceOrderNo("", "IS NULL");
		
		if (pDispCasePieceFlag.equals(RetrievalPlan.CASEPIECE_FLAG_CASE))
		{
			//#CM723381
			// (Case  AND Not Allocated)- AND (Mixed AND (Not Allocated OR Piece Completed))  AND
			skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_CASE, "=", "((", "", "AND");
			skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "", ")", "OR");
			skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_MIX, "=", "(", "", "AND");
			skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
			skey.setSchFlag(RetrievalPlan.SCH_FLAG_PIECE_COMPLETION, "=", "", ")))", "AND");
		}
		else if (pDispCasePieceFlag.equals(RetrievalPlan.CASEPIECE_FLAG_PIECE))
		{
			//#CM723382
			// (Piece AND Not Allocated)- AND (Mixed AND (Not Allocated OR Case Completed)) AND 
			skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_PIECE, "=", "((", "", "AND");
			skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "", ")", "OR");
			skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_MIX, "=", "(", "", "AND");
			skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
			skey.setSchFlag(RetrievalPlan.SCH_FLAG_CASE_COMPLETION, "=", "", ")))", "AND");
		}
		else if (pDispCasePieceFlag.equals(RetrievalPlan.CASEPIECE_FLAG_NOTHING))
		{
			//#CM723383
			// ("Piece" AND "Not Allocated")- AND 
			skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_NOTHING, "=", "(", "", "AND");
			skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "", ")", "AND");
		}
		
		//#CM723384
		// Set the search conditions input via screen 
		skey.setConsignorCode(pParam.getConsignorCode());
		skey.setPlanDate(pParam.getRetrievalPlanDate());
		if (!StringUtil.isBlank(pParam.getHostCollectBatchNo()))
		{
			skey.setHostCollectBatchno(pParam.getHostCollectBatchNo());
		}
		if (!StringUtil.isBlank(pParam.getStartItemCode()))
		{
			skey.setItemCode(pParam.getStartItemCode(), ">=");
		}
		if (!StringUtil.isBlank(pParam.getEndItemCode()))
		{
			skey.setItemCode(pParam.getEndItemCode(), "<=");
		}
		
		//#CM723385
		// Sort using Host Aggregated Batch No., Item Code, Case/Piece division, and Case location or Piece location. 
		skey.setHostCollectBatchnoOrder(1, true);
		skey.setItemCodeOrder(2, true);
		skey.setCasePieceFlagOrder(3, true);
		skey.setCaseLocationOrder(4, true);
		skey.setPieceLocationOrder(5, true);

	}

	//#CM723386
	/**
	 * Clicking on the Submit button searches for the corresponding Plan unique key through the Preset data. 
	 * Maintain the unique key corresponding to the Preset line in the Vector, and set it in the corresponding line of the array of the argument parameter. 
	 * 
	 * @param conn Database connection
	 * @param pParam RetrievalSupportParameter containing Preset data 
	 * @return Array of RetrievalSupportParameter object
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
			//#CM723387
			// Search Data 
			skey.KeyClear();
			//#CM723388
			// Data with status flag other than "Completed" or "Deleted" 
			skey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION, "!=");
			skey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
			//#CM723389
			// Require that the Order is null. 
			skey.setCaseOrderNo("", "IS NULL");
			skey.setPieceOrderNo("", "IS NULL");
		
			skey.setConsignorCode(pParam[i].getConsignorCode());
			skey.setPlanDate(pParam[i].getRetrievalPlanDate());
			if (!StringUtil.isBlank(pParam[i].getHostCollectBatchNoL()))
			{
				skey.setHostCollectBatchno(pParam[i].getHostCollectBatchNoL());
			}
			skey.setItemCode(pParam[i].getItemCodeL());

			//#CM723390
			// Set the search conditions of the preset area.
			//#CM723391
			// Pay attention to the number of parentheses and the setting order of keys as follows: 
			if (pParam[i].getCasePieceFlagL().equals(RetrievalPlan.CASEPIECE_FLAG_CASE))
			{
				
				//#CM723392
				// (Case Location  AND ((Case  AND Not Allocated) OR (Mixed AND (Not Allocated OR Piece Completed))))  AND
				skey.setCaseLocation(pParam[i].getRetrievalLocation(), "=", "(", "", "AND");
				
				skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_CASE, "=", "((", "", "AND");
				skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "", ")", "OR");
				
				skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_MIX, "=", "(", "", "AND");
				skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
				skey.setSchFlag(RetrievalPlan.SCH_FLAG_PIECE_COMPLETION, "=", "", "))))", "AND");
			}
			else if (pParam[i].getCasePieceFlagL().equals(RetrievalPlan.CASEPIECE_FLAG_PIECE))
			{
				//#CM723393
				// (Piece Location  AND ((Piece AND Not Allocated) OR (Mixed AND (Not Allocated OR Case Completed))))- AND 
				skey.setPieceLocation(pParam[i].getRetrievalLocation(), "=", "(", "", "AND");
				skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_PIECE, "=", "((", "", "AND");
				skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "", ")", "OR");
				
				skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_MIX, "=", "(", "", "AND");
				skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "(", "", "OR");
				skey.setSchFlag(RetrievalPlan.SCH_FLAG_CASE_COMPLETION, "=", "", "))))", "AND");
			}
			else if (pParam[i].getCasePieceFlagL().equals(RetrievalPlan.CASEPIECE_FLAG_NOTHING))
			{
				//#CM723394
				// ((Case Location  OR Piece Location) AND None  AND Not Allocated)  AND
				skey.setCaseLocation(pParam[i].getRetrievalLocation(), "=", "((", "", "OR");
				skey.setPieceLocation(pParam[i].getRetrievalLocation(), "=", "", ")", "AND");
				skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_NOTHING);
				skey.setSchFlag(RetrievalPlan.SCH_FLAG_UNSTART, "=", "", ")", "AND");
			}
		
			skey.setRetrievalPlanUkeyOrder(1, true);
			skey.setRetrievalPlanUkeyCollect();

			RetrievalPlan[] plan = (RetrievalPlan[]) planHandler.find(skey);
			if (plan == null || plan.length == 0)
			{
				//#CM723395
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
			//#CM723396
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

	//#CM723397
	/**
	 * Determine to obtain either Case Order or Piece Order based on the division, and
	 * obtain the Order No. from the Picking Plan Info. 
	 * @param plan Picking Plan Info 
	 * @param workForm Case/Piece division 
	 * @return Order No.
	 */
	private String getLocationNo (RetrievalPlan plan, String workForm)
	{
		String locationNo = null;
		//#CM723398
		// If All is designated for the division via screen, set null in workform typically.
		//#CM723399
		// Reset it and set the division for display upon obtaining the Plan data. 
		if (StringUtil.isBlank(workForm))
		{
			//#CM723400
			// Execute recursive call and obtain the Order No. 
			return getLocationNo(plan, plan.getCasePieceFlag());
		}
		
		//#CM723401
		// Obtain the Case Order, for data with division Case. 
		if (workForm.equals(RetrievalPlan.CASEPIECE_FLAG_CASE))
		{
			locationNo = plan.getCaseLocation(); 
		}
		//#CM723402
		// For data with division "Piece", obtain the Piece Order
		else if (workForm.equals(RetrievalPlan.CASEPIECE_FLAG_PIECE))
		{
			locationNo = plan.getPieceLocation(); 
		}
		//#CM723403
		// Obtain the data with input in either one if designated "None". 
		else
		{
			if (!StringUtil.isBlank(plan.getCaseLocation()))
			{
				locationNo = plan.getCaseLocation(); 
			}
			else
			{
				locationNo = plan.getPieceLocation(); 
			}
		}
		return locationNo;
	}
	
}
