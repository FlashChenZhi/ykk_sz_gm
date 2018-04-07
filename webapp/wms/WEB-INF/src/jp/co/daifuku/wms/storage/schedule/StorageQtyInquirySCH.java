package jp.co.daifuku.wms.storage.schedule;

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
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.master.operator.AreaOperator;

/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * This class allows to execute storage result inquiry process. <BR>
 * Receive the contents entered via screen as a parameter and execute process for inquiring the storage result. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *   If only one Consignor Code exists in the Storage Result data inquiry<CODE> (ResultView)</CODE>, return the corresponding Consignor Code. <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR>
 *   <BR>
 *   <Search conditions> <BR>
 *   <BR>
 *   <DIR>
 *   Work division "Storage" <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Process by clicking on Display button (<CODE>Query()</CODE> Method) <BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it. <BR>
 *   Return <CODE>Parameter</CODE> array with the number of elements 0 if no corresponding data found. Or, return null when condition error occurs. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 *   Display the storage date, planned storage date, item code, item name, Case/Piece division name, Location No., Packed qty per case, the Packed qty per bundle, Planned work case qty, <BR>
 *    Planned work piece qty, Result case qty, Result piece qty, Shortage case qty, Shortage piece qty, Expiry date, Case ITF, Bundle ITF, Worker code, and Worker name. <BR>
 *   Obtain only the item (count) of the worker name from the Worker info <CODE> (Dmworker)</CODE>. Obtain other items (count) from the Storage Result data inquiry <CODE> (ResultView)</CODE>. <BR>
 *   <BR>
 *   <Parameter> *Mandatory Input <BR>
 *   <BR>
 *   <DIR>
 *   Consignor code* <BR>
 *   Start Storage Date <BR>
 *   End Storage Date <BR>
 *   Item Code <BR>
 *   Display order1* <BR>
 *   </DIR>
 *   <BR>
 *   <Returned data> <BR>
 *   <BR>
 *   <DIR>
 *   Consignor code <BR>
 *   Consignor name <BR>
 *   Work Date <BR>
 *   Planned date <BR>
 *   Item Code <BR>
 *   Item Name <BR>
 *   Case/Piece division <BR>
 *   Location No <BR>
 *   Packed qty per Case <BR>
 *   Packed qty per bundle <BR>
 *   Planned Work Case Qty <BR>
 *   Planned Work Piece Qty <BR>
 *   Work Result Case Qty <BR>
 *   Work Result Piece Qty <BR>
 *   Work Shortage Case Qty <BR>
 *   Work Shortage Piece Qty <BR>
 *   Work status name <BR>
 *   Expiry Date <BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   Worker Code <BR>
 *   Worker Name <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/28</TD><TD>K.Toda</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/18 06:48:57 $
 * @author  $Author: suresh $
 */
public class StorageQtyInquirySCH extends AbstractStorageSCH
{

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/18 06:48:57 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public StorageQtyInquirySCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------

	/**
	 * This method supports operations to obtain the data required for initial display. <BR>
	 * If only one Consignor Code exists in the Storage Result data inquiry<CODE> (ResultView)</CODE>, return the corresponding Consignor Code. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Database connection object
	 * @param locale <CODE>Locale</CODE> object for which Area Code is set.
	 * @param searchParam This Class inherits the <CODE>Parameter</CODE> class with search conditions
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		ResultViewHandler resultviewHandler = new ResultViewHandler(conn);
		ResultViewSearchKey searchKey = new ResultViewSearchKey();

		// Search Data
		searchKey.setJobType(ResultView.JOB_TYPE_STORAGE);
		searchKey.setConsignorCodeCollect(" ");
		searchKey.setConsignorCodeGroup(1);

		StorageSupportParameter dispData = new StorageSupportParameter();

		if (resultviewHandler.count(searchKey) == 1)
		{
			try
			{
				ResultView[] resultview = (ResultView[]) resultviewHandler.find(searchKey);
				dispData.setConsignorCode(resultview[0].getConsignorCode());
			}
			catch (Exception e)
			{
				return new StorageSupportParameter();
			}
		}

		return dispData;
	}

	/**
	 * Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it. <BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ". <BR>
	 * @param conn Instance to maintain database connection.
	 * @param searchParam Instance of <CODE>StorageParameter</CODE> class with conditions to obtain the display data. <BR>
	 *         Designating any instance other than <CODE>StorageParameter</CODE> throws ScheduleException. <BR>
	 * @return Array of <CODE>StorageParameter</CODE> instance with search result. <BR>
	 *          If no corresponding record found, return the array of the number of elements equal to 0. <BR>
	 *          Return null when input condition error occurs. <BR>
	 *          Returning null allows the <CODE>getMessage()</CODE> method to obtain the error content as a message.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		StorageSupportParameter param = (StorageSupportParameter) searchParam;

		if (!check(conn, param))
		{
			return null;
		}

		ResultViewHandler resultviewHandler = new ResultViewHandler(conn);
		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		ResultViewSearchKey nameKey = new ResultViewSearchKey();

		// Search Data
		// Consignor code
		searchKey.setConsignorCode(param.getConsignorCode());
		nameKey.setConsignorCode(param.getConsignorCode());
		// Planned start storage DatePlanned end storage Date
		if (!StringUtil.isBlank(param.getFromStoragePlanDate()))
		{
			searchKey.setWorkDate(param.getFromStoragePlanDate(), ">=");
			nameKey.setWorkDate(param.getFromStoragePlanDate(), ">=");
		}
		if (!StringUtil.isBlank(param.getToStoragePlanDate()))
		{
			searchKey.setWorkDate(param.getToStoragePlanDate(), "<=");
			nameKey.setWorkDate(param.getToStoragePlanDate(), "<=");
		}
		// Item Code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			searchKey.setItemCode(param.getItemCode());
			nameKey.setItemCode(param.getItemCode());
		}
		// Case/Piece division Selecting All disables to set anything.
		if(!StringUtil.isBlank(param.getCasePieceflg()))
		{
			if(param.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
				nameKey.setItemCode(ResultView.CASEPIECE_FLAG_CASE);
			}
			else if(param.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
				nameKey.setItemCode(ResultView.CASEPIECE_FLAG_PIECE);
			}
			else if(param.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				searchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_NOTHING);
				nameKey.setItemCode(ResultView.CASEPIECE_FLAG_NOTHING);
			}
		}
		// Work division:Storage
		searchKey.setJobType(ResultView.JOB_TYPE_STORAGE);
		nameKey.setJobType(ResultView.JOB_TYPE_STORAGE);

		// Display order
		// Order of Storage Date
		if (param.getDisplayOrder().equals(StorageSupportParameter.DISPLAY_ORDER_WORK_DATE))
		{
			searchKey.setWorkDateOrder(1, true);
			searchKey.setPlanDateOrder(2, true);
		}
		//Order of Planned storage date
		else if (param.getDisplayOrder().equals(StorageSupportParameter.DISPLAY_ORDER_PLAN_DATE))
		{
			searchKey.setPlanDateOrder(1, true);
			searchKey.setWorkDateOrder(2, true);
		}
		searchKey.setItemCodeOrder(3, true);
		searchKey.setWorkFormFlagOrder(4, true);
		searchKey.setRegistDateOrder(5, true);
		searchKey.setResultQtyOrder(6, true);

		// Obtain the count of data to be displayed.
		if (!canLowerDisplay(resultviewHandler.count(searchKey)))
		{
			return returnNoDisplayParameter();
		}

		// Execute the search.
		ResultView[] resultEntity = (ResultView[]) resultviewHandler.find(searchKey);

		// Obtain the Consignor Name.
		String consignorName = "";
		nameKey.setConsignorNameCollect("");
		nameKey.setRegistDateOrder(1, false);

		ResultViewFinder consignorFinder = new ResultViewFinder(conn);
		consignorFinder.open();
		int nameCount = ((ResultViewFinder) consignorFinder).search(nameKey);
		if (nameCount > 0 && nameCount <= WmsParam.MAX_NUMBER_OF_DISP_LISTBOX)
		{
			ResultView resultView[] = (ResultView[]) ((ResultViewFinder) consignorFinder).getEntities(0, 1);

			if (resultView != null && resultView.length != 0)
			{
				consignorName = resultView[0].getConsignorName();
			}
		}
		consignorFinder.close();

		Vector vec = new Vector();
	    AreaOperator ao = new AreaOperator(conn);
		for (int i = 0; i < resultEntity.length; i++)
		{
			StorageSupportParameter dispData = new StorageSupportParameter();

			// Consignor code
			dispData.setConsignorCode(resultEntity[i].getConsignorCode());
			// Consignor name
			dispData.setConsignorName(consignorName);
			// Work Date (Storage Date)
			dispData.setStorageDate(resultEntity[i].getWorkDate());
			// Planned storage date
			dispData.setStoragePlanDate(resultEntity[i].getPlanDate());
			// Item Code
			dispData.setItemCode(resultEntity[i].getItemCode());
			// Item Name
			dispData.setItemName(resultEntity[i].getItemName1());
			// Case/Piece division
			dispData.setCasePieceflgName(DisplayUtil.getPieceCaseValue(resultEntity[i].getWorkFormFlag()));

			// Area
			dispData.setRetrievalArea(resultEntity[i].getAreaNo());
			// Area Name
		    dispData.setRetrievalAreaName(ao.getAreaName(resultEntity[i].getAreaNo()));

			// Storage Location
			dispData.setStorageLocation(resultEntity[i].getResultLocationNo());
			// Packed qty per Case
			dispData.setEnteringQty(resultEntity[i].getEnteringQty());
			// Packed qty per bundle
			dispData.setBundleEnteringQty(resultEntity[i].getBundleEnteringQty());
			// Planned Work Case Qty
			dispData.setPlanCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty(),
					resultEntity[i].getWorkFormFlag()));
			// Planned Work Piece Qty
			dispData.setPlanPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty(),
					resultEntity[i].getWorkFormFlag()));
			// Result Case Qty
			dispData.setResultCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getResultQty(), resultEntity[i].getEnteringQty(),
					resultEntity[i].getWorkFormFlag()));
			// Result Piece Qty
			dispData.setResultPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getResultQty(), resultEntity[i].getEnteringQty(),
					resultEntity[i].getWorkFormFlag()));
			// Shortage Case Qty
			dispData.setShortageQty(DisplayUtil.getCaseQty(resultEntity[i].getShortageCnt(), resultEntity[i].getEnteringQty(),
					resultEntity[i].getWorkFormFlag()));
			// Shortage Piece Qty
			dispData.setShortagePieceQty(DisplayUtil.getPieceQty(resultEntity[i].getShortageCnt(), resultEntity[i].getEnteringQty(),
					resultEntity[i].getWorkFormFlag()));
			// Case ITF
			dispData.setITF(resultEntity[i].getItf());
			// Bundle ITF
			dispData.setBundleITF(resultEntity[i].getBundleItf());
			// Expiry Date
			dispData.setUseByDate(resultEntity[i].getResultUseByDate());
			// Worker Code
			dispData.setWorkerCode(resultEntity[i].getWorkerCode());
			// Worker Name
			dispData.setWorkerName(resultEntity[i].getWorkerName());
			// System type
			dispData.setSystemDiscKey(resultEntity[i].getSystemDiscKey());

			vec.addElement(dispData);
		}

		StorageSupportParameter[] paramArray = new StorageSupportParameter[vec.size()];

		vec.copyInto(paramArray);

		// 6001013 = Data is shown.
		wMessage = "6001013";
		return paramArray;
	}

	/**
	 * Check the value of Planned date for larger or smaller <BR>
	 * @param conn Instance to maintain database connection.<BR>
	 * @param searchParam <CODE>Parameter</CODE> that has display condition<BR>
	 * @return check result (normal : true abnormal : false）
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public boolean check(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		StorageSupportParameter param = (StorageSupportParameter) searchParam;

		// Obtain the Start Storage Date and End Storage Date from the parameter.
		String fromworkdate = param.getFromWorkDate();
		String toworkdate = param.getToWorkDate();

		// Planned Shipping Date Check the value for larger or smaller
		if (!StringUtil.isBlank(fromworkdate) && !StringUtil.isBlank(toworkdate))
		{
			if (fromworkdate.compareTo(toworkdate) > 0)
			{
				// 6023049 = Starting shipping date must precede the end shipping date.
				wMessage = "6023049";
				return false;
			}
		}
		return true;
	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class
