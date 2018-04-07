package jp.co.daifuku.wms.retrieval.schedule;

//#CM724060
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
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM724061
/**
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue <BR>
 * <BR>
 * Allow this class to execute the process for inquiring the Order Picking Result.  <BR>
 * Receive the contents entered via screen as a parameter, and execute the process to inquire the Order Picking Result.  <BR>
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
 *   Data with Order No. other than NULL <BR>
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
 *   Customer Code <BR>
 *   Order No.<BR>
 *   Case/Piece division <BR>
 *   Display order <BR>
 *   </DIR>
 *   <BR>
 *   <Search conditions> <BR> 
 *   <DIR>
 *   Data with work type "Picking"  <BR>
 *   Data with Order No. other than NULL <BR>
 *   </DIR>
 *   <Returned data> <BR>
 *   <BR>
 *   <DIR>
 *   Consignor Code <BR>
 *   Consignor Name <BR>
 *   Picking Date <BR>
 *   Planned Picking Date <BR>
 *   Customer Code <BR>
 *   Customer Name  <BR>
 *   Order No.<BR>
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
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:59 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderQtyInquirySCH extends AbstractRetrievalSCH
{

	//#CM724062
	// Class variables -----------------------------------------------

	//#CM724063
	// Class method --------------------------------------------------
	//#CM724064
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:59 $");
	}

	//#CM724065
	// Constructors --------------------------------------------------
	//#CM724066
	/**
	 * Initialize this class. 
	 */
	public RetrievalOrderQtyInquirySCH()
	{
		wMessage = null;
	}

	//#CM724067
	// Public methods ------------------------------------------------

	//#CM724068
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
		//#CM724069
		// Work type: Picking
		searchKey.setJobType(ResultView.JOB_TYPE_RETRIEVAL);
		//#CM724070
		// Data with Order No. input
		searchKey.setOrderNo("", "IS NOT NULL");
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
	
	//#CM724071
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
		if (!check(conn, param))
		{
			return null;
		}

		ResultViewHandler resultviewHandler = new ResultViewHandler(conn);
		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		ResultViewSearchKey nameKey = new ResultViewSearchKey();

		//#CM724072
		// Search Data 
		//#CM724073
		// Work type: Picking
		searchKey.setJobType(ResultView.JOB_TYPE_RETRIEVAL);
		//#CM724074
		// Data with Order No. input
		searchKey.setOrderNo("", "IS NOT NULL");
		//#CM724075
		// Work type: Picking
		nameKey.setJobType(ResultView.JOB_TYPE_RETRIEVAL);
		//#CM724076
		// Data with Order No. input
		nameKey.setOrderNo("", "IS NOT NULL");

		//#CM724077
		// Consignor Code
		searchKey.setConsignorCode(param.getConsignorCode());
		nameKey.setConsignorCode(param.getConsignorCode());
		//#CM724078
		// Start Picking Date
		if (!StringUtil.isBlank(param.getFromRetrievalDate()))
		{
			searchKey.setWorkDate(param.getFromRetrievalDate(), ">=");
			nameKey.setWorkDate(param.getFromRetrievalDate(), ">=");
		}
		//#CM724079
		// End Picking Date
		if (!StringUtil.isBlank(param.getToRetrievalDate()))
		{
			searchKey.setWorkDate(param.getToRetrievalDate(), "<=");
			nameKey.setWorkDate(param.getToRetrievalDate(), "<=");
		}
		//#CM724080
		// Customer Code
		if (!StringUtil.isBlank(param.getCustomerCode()))
		{
			searchKey.setCustomerCode(param.getCustomerCode());
			nameKey.setCustomerCode(param.getCustomerCode());
		}
		//#CM724081
		// Order No.
		if (!StringUtil.isBlank(param.getOrderNo()))
		{
			searchKey.setOrderNo(param.getOrderNo());
			nameKey.setOrderNo(param.getOrderNo());
		}
		//#CM724082
		// Case/Piece division
		if (!StringUtil.isBlank(param.getCasePieceflg()))
		{
			//#CM724083
			// Case 
			if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
				nameKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
			}
			//#CM724084
			// Case 
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
				nameKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
			}
			//#CM724085
			// None 
			else if(param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				searchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_NOTHING);
				nameKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_NOTHING);
			}
		}

		//#CM724086
		// In the order of Picking Date 
		if (param.getDisplayOrder().equals(RetrievalSupportParameter.DISPLAY_ORDER_WORK_DATE)) 
		{
			searchKey.setWorkDateOrder(1, true);
			searchKey.setPlanDateOrder(2,true);
		}
		//#CM724087
		// In the order of Planned Picking Date 
		else if (param.getDisplayOrder().equals(RetrievalSupportParameter.DISPLAY_ORDER_PLAN_DATE)) 
		{
			searchKey.setPlanDateOrder(1, true);
			searchKey.setWorkDateOrder(2,true);
		}
		searchKey.setCustomerCodeOrder(3, true);
		searchKey.setOrderNoOrder(4, true);
		searchKey.setItemCodeOrder(5, true);
		searchKey.setWorkFormFlagOrder(6, true);
		searchKey.setResultLocationNoOrder(7, true);
		searchKey.setRegistDateOrder(8, true);
		searchKey.setResultQtyOrder(9, true);
		//#CM724088
		// Obtain the count of data to be displayed. 
		if(!canLowerDisplay(resultviewHandler.count(searchKey)))
		{
			return returnNoDisplayParameter();
		}
				
		//#CM724089
		// Execute the search.
		ResultView[] resultEntity = (ResultView[]) resultviewHandler.find(searchKey);
		if (resultEntity == null || resultEntity.length <= 0)
		{
			//#CM724090
			// No target data was found. 
			wMessage = "6003018";
			return new RetrievalSupportParameter[0];
		}

		//#CM724091
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
		//#CM724092
		// Compile the displayed Picking result data. 
		AreaOperator ao = new AreaOperator(conn);
		for (int loop = 0; loop < resultEntity.length; loop++)
		{
			RetrievalSupportParameter dispData = new RetrievalSupportParameter();
			//#CM724093
			// Consignor Code
			dispData.setConsignorCode(resultEntity[loop].getConsignorCode());
			//#CM724094
			// Consignor Name
			dispData.setConsignorName(consignorName);
			//#CM724095
			// Work date(Picking Date) 
			dispData.setRetrievalDate(resultEntity[loop].getWorkDate());
			//#CM724096
			// Planned Picking Date
			dispData.setRetrievalPlanDate(resultEntity[loop].getPlanDate());
			//#CM724097
			// Customer Code
			dispData.setCustomerCode(resultEntity[loop].getCustomerCode());
			//#CM724098
			// Customer Name
			dispData.setCustomerName(resultEntity[loop].getCustomerName1());
			//#CM724099
			// Order No.
			dispData.setOrderNo(resultEntity[loop].getOrderNo());
			//#CM724100
			// Item Code
			dispData.setItemCode(resultEntity[loop].getItemCode());
			//#CM724101
			// Item Name
			dispData.setItemName(resultEntity[loop].getItemName1());
			//#CM724102
			// Case/Piece division
			dispData.setCasePieceflgName(DisplayUtil.getPieceCaseValue(resultEntity[loop].getWorkFormFlag()));
			//#CM724103
			// Packed Qty per Case
			dispData.setEnteringQty(resultEntity[loop].getEnteringQty());
			//#CM724104
			// Packed qty per bundle
			dispData.setBundleEnteringQty(resultEntity[loop].getBundleEnteringQty());	
			//#CM724105
			// Planned Work Case Qty
			dispData.setPlanCaseQty(DisplayUtil.getCaseQty(resultEntity[loop].getPlanEnableQty(), resultEntity[loop].getEnteringQty(),resultEntity[loop].getWorkFormFlag()));
			//#CM724106
			// Planned Work Piece Qty
			dispData.setPlanPieceQty(DisplayUtil.getPieceQty(resultEntity[loop].getPlanEnableQty(), resultEntity[loop].getEnteringQty(),resultEntity[loop].getWorkFormFlag()));
			//#CM724107
			// Result Case Qty 
			dispData.setResultCaseQty(DisplayUtil.getCaseQty(resultEntity[loop].getResultQty(), resultEntity[loop].getEnteringQty(),resultEntity[loop].getWorkFormFlag()));
			//#CM724108
			// Result Piece Qty 
			dispData.setResultPieceQty(DisplayUtil.getPieceQty(resultEntity[loop].getResultQty(), resultEntity[loop].getEnteringQty(),resultEntity[loop].getWorkFormFlag()));
			//#CM724109
			// Shortage Case Qty
			dispData.setShortageCaseQty(DisplayUtil.getCaseQty(resultEntity[loop].getShortageCnt(), resultEntity[loop].getEnteringQty(),resultEntity[loop].getWorkFormFlag()));
			//#CM724110
			// Shortage Piece Qty
			dispData.setShortagePieceQty(DisplayUtil.getPieceQty(resultEntity[loop].getShortageCnt(), resultEntity[loop].getEnteringQty(),resultEntity[loop].getWorkFormFlag()));

			//#CM724111
			// Picking area 
			dispData.setRetrievalArea(resultEntity[loop].getAreaNo());
			//#CM724112
			// Area Name 
			dispData.setRetrievalAreaName(ao.getAreaName(resultEntity[loop].getAreaNo()));
			
			//#CM724113
			// Picking Location 
			dispData.setRetrievalLocation(resultEntity[loop].getResultLocationNo());
			//#CM724114
			// Expiry Date
			dispData.setUseByDate(resultEntity[loop].getResultUseByDate());
			//#CM724115
			// Case ITF
			dispData.setITF(resultEntity[loop].getItf());
			//#CM724116
			// Bundle ITF
			dispData.setBundleITF(resultEntity[loop].getBundleItf());
			//#CM724117
			// Worker Code
			dispData.setWorkerCode(resultEntity[loop].getWorkerCode());
			//#CM724118
			// Worker Name
			dispData.setWorkerName(resultEntity[loop].getWorkerName());
			//#CM724119
			// System identification key 
			dispData.setSystemDiscKey(resultEntity[loop].getSystemDiscKey());
			vec.addElement(dispData);
		}

		RetrievalSupportParameter[] paramArray = new RetrievalSupportParameter[vec.size()];

		vec.copyInto(paramArray);

		//#CM724120
		// 6001013 = Data is shown. 
		wMessage = "6001013";
		return paramArray;
	}

	//#CM724121
	/** 
	 * Check the input parameter.  <BR>
	 * - Check for mandatory input in the Consignor code. <BR>
	 * - Check the values for precedence between Start Picking Date and End Picking Date.<BR>
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 * @return true: The input content is normal.  false: The input content is not normal.
	 */
	public boolean check(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter param = (RetrievalSupportParameter) searchParam;
		if (StringUtil.isBlank(param.getConsignorCode()))
		{
			//#CM724122
			// 6023004=Please enter the consignor code. 
			wMessage = "6023004";
			return false;
		}
		//#CM724123
		// Obtain the Start Picking Date and the End Picking Date from parameter.
		String fromdate = param.getFromRetrievalDate();
		String todate = param.getToRetrievalDate();

		//#CM724124
		// Check the value of Planned Picking Date for precedence. 
		if (!StringUtil.isBlank(fromdate) && !StringUtil.isBlank(todate))
		{
			if (fromdate.compareTo(todate) > 0)
			{
				//#CM724125
				// 6023107=Starting picking date must precede the end picking date. 
				wMessage = "6023107";
				return false;
			}
		}
		return true;
	}

	
}
//#CM724126
//end of class
