package jp.co.daifuku.wms.retrieval.schedule;

//#CM723232
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultViewReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM723233
/**
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue <BR>
 * <BR>
 * Allow this class to execute the process for inquiring the Item Picking Result.  <BR>
 * Receive the contents entered via screen as a parameter, and execute the process to inquire the Item Picking Result.  <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind()</CODE> method)  <BR> 
 * <BR>
 * <DIR>
 *   Return the Consignor Code, if only one Consignor Code exists in the Result info <CODE>(ResultView)</CODE>.  <BR> 
 *   Return null if no corresponding data found, or two or more corresponding data exist.  <BR> 
 *   <BR>
 *   <Search conditions> <BR> 
 *   <DIR>
 *   Data with work type "Picking"  <BR>
 *   Order No.: NULL <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Display" button (<CODE>query()</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it.  <BR>
 *   If no corresponding data is found, return <CODE>Parameter</CODE> array with number of elements equal to 0. Return null when condition error occurs.  <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error  <BR>
 *   Search through the Result info table <CODE>(ResultView)</CODE>. <BR>
 *   Disable to display if the search target count exceeds 1000 (MAX_NUMBER_OF_DISP defined in WMSParam). <BR>
 *   <BR>
 *   <Parameter> *Mandatory Input <BR>
 *   <BR>
 *   <DIR>
 *   Consignor Code* <BR>
 *   Start Picking Date <BR>
 *   End Picking Date <BR>
 *   Item Code <BR>
 *   Case/Piece division <BR>
 *   Display order <BR>
 *   </DIR>
 *   <BR>
 *   <Search conditions> <BR> 
 *   <DIR>
 *   Data with work type "Picking"  <BR>
 *   Order No.: NULL <BR>
 *   </DIR>
 *   <Returned data> <BR>
 *   <BR>
 *   <DIR>
 *   Consignor Code <BR>
 *   Consignor Name <BR>
 *   Picking Date <BR>
 *   Planned Picking Date <BR>
 *   Item Code <BR>
 *   Item Name <BR>
 *   Case/Piece division (name) <BR>
 *   Packed Qty per Case <BR>
 *   Packed qty per bundle <BR>
 *   Planned Work Case Qty <BR>
 *   Planned Work Piece Qty <BR>
 *   Work Result Case Qty  <BR>
 *   Work Result Piece Qty  <BR>
 *   Work Shortage Case Qty  <BR>
 *   Work Shortage Piece Qty  <BR>
 *   Picking Location <BR>
 *   Expiry Date <BR>
 *   Case ITF<BR>
 *   Piece ITF <BR>
 *   Worker Code <BR>
 *   Worker Name <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/19</TD><TD>M.Inoue</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:55 $
 * @author  $Author: suresh $
 */
public class RetrievalItemQtyInquirySCH extends AbstractRetrievalSCH
{

	//#CM723234
	// Class variables -----------------------------------------------

	//#CM723235
	// Class method --------------------------------------------------
	//#CM723236
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:55 $");
	}

	//#CM723237
	// Constructors --------------------------------------------------
	//#CM723238
	/**
	 * Initialize this class. 
	 */
	public RetrievalItemQtyInquirySCH()
	{
		wMessage = null;
	}

	//#CM723239
	// Public methods ------------------------------------------------

	//#CM723240
	/**
	 * Allow this method to support the operation to obtain the data required for initial display.  <BR>
	 * Return the Consignor Code, if only one Consignor Code exists in the Picking Result info <CODE>(ResultView)</CODE>  <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.  
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. 
	 * @param conn Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions. 
	 * @return Class that implements the <CODE>Parameter</CODE> interface that contains the search result. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		ResultViewHandler resultviewHandler = new ResultViewHandler(conn);
		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		//#CM723241
		// Work type: Picking
		searchKey.setJobType(ResultView.JOB_TYPE_RETRIEVAL);
		//#CM723242
		// No value entered in Order No. 
		searchKey.setOrderNo("");
		searchKey.setConsignorCodeCollect("");
		searchKey.setConsignorCodeGroup(1);

		RetrievalSupportParameter dispData = new RetrievalSupportParameter();

		if (resultviewHandler.count(searchKey) == 1)
		{
			try
			{
				ResultView[] resultview = (ResultView[]) resultviewHandler.find(searchKey);
				dispData.setConsignorCode(resultview[0].getConsignorCode());
			}
			catch (Exception e)
			{
				return new RetrievalSupportParameter();
			}
		}

		return dispData;
	}

	//#CM723243
	/**
	 * Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it.  <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class .  <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed <BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException.  <BR>
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result. <BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0.  <BR>
	 *          Return null when input condition error occurs.  <BR>
	 *          Returning null enables to obtain the error content as a message using the <CODE>getMessage()</CODE> method. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter param = (RetrievalSupportParameter) searchParam;
		
		//#CM723244
		// Check for mandatory input parameter. 
		if (!check(conn, param))
		{
			return null;
		}

		ResultViewHandler resultviewHandler = new ResultViewHandler(conn);
		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		ResultViewSearchKey nameKey = new ResultViewSearchKey();

		//#CM723245
		// Search Data 
		//#CM723246
		// Work type: Picking
		searchKey.setJobType(ResultView.JOB_TYPE_RETRIEVAL);
		//#CM723247
		// There is no Order No. 
		searchKey.setOrderNo("");
		//#CM723248
		// Work type: Picking
		nameKey.setJobType(ResultView.JOB_TYPE_RETRIEVAL);
		//#CM723249
		// There is no Order No. 
		nameKey.setOrderNo("");
		
		//#CM723250
		// Consignor Code
		searchKey.setConsignorCode(param.getConsignorCode());
		nameKey.setConsignorCode(param.getConsignorCode());
		//#CM723251
		// Start Picking Date
		if (!StringUtil.isBlank(param.getFromRetrievalDate()))
		{
			searchKey.setWorkDate(param.getFromRetrievalDate(), ">=");
			nameKey.setWorkDate(param.getFromRetrievalDate(), ">=");
		}
		//#CM723252
		// End Picking Date
		if (!StringUtil.isBlank(param.getToRetrievalDate()))
		{
			searchKey.setWorkDate(param.getToRetrievalDate(), "<=");
			nameKey.setWorkDate(param.getToRetrievalDate(), "<=");
		}
		//#CM723253
		// Item Code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			searchKey.setItemCode(param.getItemCode());
			nameKey.setItemCode(param.getItemCode());
		}
		//#CM723254
		// Case/Piece division
		if (!StringUtil.isBlank(param.getCasePieceflg()))
		{
			//#CM723255
			// Case 
			if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
				nameKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			//#CM723256
			// Case 
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
				nameKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			}
			//#CM723257
			// None 
			else if(param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				searchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
				nameKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			}
		}

		//#CM723258
		// In the order of Picking Date 
		if (param.getDisplayOrder().equals(RetrievalSupportParameter.DISPLAY_ORDER_WORK_DATE))
		{
			searchKey.setWorkDateOrder(1, true);
			searchKey.setPlanDateOrder(2,true);
		}
		//#CM723259
		// In the order of Planned Picking Date 
		else if (param.getDisplayOrder().equals(RetrievalSupportParameter.DISPLAY_ORDER_PLAN_DATE))
		{
			searchKey.setPlanDateOrder(1, true);
			searchKey.setWorkDateOrder(2,true);
		}
		searchKey.setItemCodeOrder(3, true);
		searchKey.setWorkFormFlagOrder(4, true);
		searchKey.setResultLocationNoOrder(5, true);
		searchKey.setRegistDateOrder(6, true);
		searchKey.setResultQtyOrder(7, true);

		if(!canLowerDisplay(resultviewHandler.count(searchKey)))
		{
			return returnNoDisplayParameter();
		}
		//#CM723260
		// Execute the search.
		ResultView[] resultEntity = (ResultView[]) resultviewHandler.find(searchKey);
		if (resultEntity == null || resultEntity.length <= 0)
		{
			//#CM723261
			// No target data was found. 
			wMessage = "6003018";
			return new RetrievalSupportParameter[0];
		}

		//#CM723262
		// Obtain the Consignor Name.
		String consignorName = "";
		nameKey.setConsignorNameCollect("");
		nameKey.setRegistDateOrder(1, false);

		ResultViewReportFinder consignorFinder = new ResultViewReportFinder(conn);
		consignorFinder.open();
		int nameCount = ((ResultViewReportFinder) consignorFinder).search(nameKey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			ResultView resultView[] = (ResultView[]) ((ResultViewReportFinder) consignorFinder).getEntities(1);

			if (resultView != null && resultView.length != 0)
			{
				consignorName = resultView[0].getConsignorName();
			}
		}
		consignorFinder.close();

		Vector vec = new Vector();
		AreaOperator ao = new AreaOperator(conn);
		//#CM723263
		// Compile the displayed Picking result data. 
		for (int loop = 0; loop < resultEntity.length; loop++)
		{
			RetrievalSupportParameter dispData = new RetrievalSupportParameter();
			//#CM723264
			// Consignor Code
			dispData.setConsignorCode(resultEntity[loop].getConsignorCode());
			//#CM723265
			// Consignor Name
			dispData.setConsignorName(consignorName);
			//#CM723266
			// Work date(Picking Date) 
			dispData.setRetrievalDate(resultEntity[loop].getWorkDate());
			//#CM723267
			// Planned Picking Date
			dispData.setRetrievalPlanDate(resultEntity[loop].getPlanDate());
			//#CM723268
			// Item Code
			dispData.setItemCode(resultEntity[loop].getItemCode());
			//#CM723269
			// Item Name
			dispData.setItemName(resultEntity[loop].getItemName1());
			//#CM723270
			// Case/Piece division
			dispData.setCasePieceflgName(DisplayUtil.getPieceCaseValue(resultEntity[loop].getWorkFormFlag()));
			//#CM723271
			// Packed Qty per Case
			dispData.setEnteringQty(resultEntity[loop].getEnteringQty());
			//#CM723272
			// Packed qty per bundle
			dispData.setBundleEnteringQty(resultEntity[loop].getBundleEnteringQty());
			//#CM723273
			// Planned Work Case Qty
			dispData.setPlanCaseQty(DisplayUtil.getCaseQty(resultEntity[loop].getPlanEnableQty(), resultEntity[loop].getEnteringQty(), resultEntity[loop].getWorkFormFlag()));
			//#CM723274
			// Planned Work Piece Qty
			dispData.setPlanPieceQty(DisplayUtil.getPieceQty(resultEntity[loop].getPlanEnableQty(), resultEntity[loop].getEnteringQty(), resultEntity[loop].getWorkFormFlag()));
			//#CM723275
			// Result Case Qty 
			dispData.setResultCaseQty(DisplayUtil.getCaseQty(resultEntity[loop].getResultQty(), resultEntity[loop].getEnteringQty(), resultEntity[loop].getWorkFormFlag()));
			//#CM723276
			// Result Piece Qty 
			dispData.setResultPieceQty(DisplayUtil.getPieceQty(resultEntity[loop].getResultQty(), resultEntity[loop].getEnteringQty(), resultEntity[loop].getWorkFormFlag()));
			//#CM723277
			// Shortage Case Qty
			dispData.setShortageCaseQty(DisplayUtil.getCaseQty(resultEntity[loop].getShortageCnt(), resultEntity[loop].getEnteringQty(), resultEntity[loop].getWorkFormFlag()));
			//#CM723278
			// Shortage Piece Qty
			dispData.setShortagePieceQty(DisplayUtil.getPieceQty(resultEntity[loop].getShortageCnt(), resultEntity[loop].getEnteringQty(), resultEntity[loop].getWorkFormFlag()));

			//#CM723279
			// Picking area 
			dispData.setRetrievalArea(resultEntity[loop].getAreaNo());
			//#CM723280
			// Picking area 
			dispData.setRetrievalAreaName(ao.getAreaName(resultEntity[loop].getAreaNo()));
			//#CM723281
			// Picking Location 
			dispData.setRetrievalLocation(resultEntity[loop].getResultLocationNo());
			//#CM723282
			// Expiry Date
			dispData.setUseByDate(resultEntity[loop].getResultUseByDate());
			//#CM723283
			// Case ITF
			dispData.setITF(resultEntity[loop].getItf());
			//#CM723284
			// Bundle ITF
			dispData.setBundleITF(resultEntity[loop].getBundleItf());
			//#CM723285
			// Worker Code
			dispData.setWorkerCode(resultEntity[loop].getWorkerCode());
			//#CM723286
			// Worker Name
			dispData.setWorkerName(resultEntity[loop].getWorkerName());
			//#CM723287
			// System identification key 
			dispData.setSystemDiscKey(resultEntity[loop].getSystemDiscKey());
			vec.addElement(dispData);
		}

		RetrievalSupportParameter[] paramArray = new RetrievalSupportParameter[vec.size()];

		vec.copyInto(paramArray);

		//#CM723288
		// 6001013 = Data is shown. 
		wMessage = "6001013";
		return paramArray;
	}

	//#CM723289
	/** 
	 * Check the input parameter.  <BR>
	 * - Check for mandatory input in the Consignor code. <BR>
	 * - Check the values for precedence between Start Picking Date and End Picking Date.<BR>
	 * - Check "Processing Daily Maintenance" <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 * @return true if the input contents is normal, otherwise false 
	 */
	public boolean check(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter param = (RetrievalSupportParameter) searchParam;
		if (StringUtil.isBlank(param.getConsignorCode()))
		{
			//#CM723290
			// 6023004=Please enter the consignor code. 
			wMessage = "6023004";
			return false;
		}
		//#CM723291
		// Obtain the Start Picking Date and the End Picking Date from parameter.
		String fromdate = param.getFromRetrievalDate();
		String todate = param.getToRetrievalDate();

		//#CM723292
		// Check the value of Planned Picking Date for precedence. 
		if (!StringUtil.isBlank(fromdate) && !StringUtil.isBlank(todate))
		{
			if (fromdate.compareTo(todate) > 0)
			{
				//#CM723293
				// 6023107=Starting picking date must precede the end picking date. 
				wMessage = "6023107";
				return false;
			}
		}
		return true;
	}
	
}
//#CM723294
//end of class
