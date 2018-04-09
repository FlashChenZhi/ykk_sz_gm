package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.entity.StoragePlan;

/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 *<BR>
 * This class allows to execute storage plan inquiry process.<BR>
 * Receive the contents entered via screen as a parameter and execute process for inquiring the storage plan.<BR>
 * This class executes the following processes.<BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   If only one Consignor Code exists in the Storage Plan info, return the corresponding Consignor Code.<BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist.<BR>
 *   <BR>
 *   [Search conditions] <BR>
 *   <BR>
 *   For data with status flag Standby, Processing, Partially Completed, or Completed<BR>
 * </DIR>
 * <BR>
 * 2.Process by clicking on Display button (<CODE>Query()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it.<BR>
 *   Return <CODE>Parameter</CODE> array with the number of elements 0 if no corresponding data found. Or, return null when condition error occurs.<BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 *   Display in the order of Planned storage date, item code, and then status.<BR>
 *   Table to search is the storage planned table(DNSTORAGEPLAN)<BR>
 *   Disable to display if the search target count exceeds 1000 (MAX_NUMBER_OF_DISP defined in WMSParam).<BR>
 *   Obtain the Consignor name with the later added date/time needed to display in the list cell header.<BR>
 *   <BR>
 *   [Search conditions] <BR>
 *   <BR>
 *   For data with status flag Standby, Processing, Partially Completed, or Completed<BR>
 *   <BR>
 *   [Parameter] *Mandatory Input<BR>
 *   <BR>
 *   Consignor code* <BR>
 *   Planned start storage Date <BR>
 *   Planned end storage Date <BR>
 *   Item Code <BR>
 *   Status flag* <BR>
 *   <BR>
 *   [Returned data] <BR>
 *   <BR>
 *   Consignor code <BR>
 *   Consignor name <BR>
 *   Planned storage date <BR>
 *   Item Code <BR>
 *   Item Name <BR>
 *   Case/Piece division name <BR>
 *   Case Item Storage Location <BR>
 *   Piece Item Storage Location <BR>
 *   Packed qty per Case <BR>
 *   Packed qty per bundle <BR>
 *   Host planned Case qty <BR>
 *   Host Planned Piece Qty <BR>
 *   Result Case Qty <BR>
 *   Result Piece Qty <BR>
 *   Status name <BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/27</TD><TD>K.Toda</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/18 06:43:51 $
 * @author  $Author: suresh $
 */
public class StoragePlanInquirySCH extends AbstractStorageSCH
{

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/18 06:43:51 $");
	}
	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public StoragePlanInquirySCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------

	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor Code exists in the Storage Plan info, return the corresponding Consignor Code.<BR>
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
		StoragePlanHandler storageHandler = new StoragePlanHandler(conn);
		StoragePlanSearchKey searchKey = new StoragePlanSearchKey();

		// Search Data
		// Status flag (Standby, Processing, Partially Completed, or Completed)
		String[] statusflg = { StoragePlan.STATUS_FLAG_UNSTART, StoragePlan.STATUS_FLAG_NOWWORKING, StoragePlan.STATUS_FLAG_COMPLETE_IN_PART, StoragePlan.STATUS_FLAG_COMPLETION };
		searchKey.setStatusFlag(statusflg);
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		StorageSupportParameter dispData = new StorageSupportParameter();

		if (storageHandler.count(searchKey) == 1)
		{
			try
			{
				StoragePlan working = (StoragePlan)storageHandler.findPrimary(searchKey);
				dispData.setConsignorCode(working.getConsignorCode());
			}
			catch (NoPrimaryException e)
			{
				return new StorageSupportParameter();
			}
		}

		return dispData;
	}

	/**
	 * Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Instance to maintain database connection.
	 * @param locale Use this to obtain the Area Code and a value localized to display.
	 * @param searchParam Instance of <CODE>StorageSupportParameter</CODE> class with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StorageSupportParameter</CODE> throws ScheduleException.<BR>
	 * @return Array of <CODE>StorageSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record found, return the array of the number of elements equal to 0.<BR>
	 *          Return null if the search result count exceeds 1000 or when input condition error occurs.<BR>
	 *          Returning array with element qty 0 (zero) or null allows to obtain the error contents as a message using <CODE>getMessage()</CODE> method.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		StorageSupportParameter param = (StorageSupportParameter)searchParam;
		if (!check(conn, param))
		{
			return null;
		}

		StoragePlanHandler storageHandler = new StoragePlanHandler(conn);
		StoragePlanSearchKey searchKey = new StoragePlanSearchKey();
		StoragePlanSearchKey namesearchKey = new StoragePlanSearchKey();

		//Set the Search key.
		searchKey = setStoragePlanSearchKey(param);

		// Sort by Planned Storage Date, Item Code, case/Piece division, Case Storage Location, Piece Storage Location in ascending order
		searchKey.setPlanDateOrder(1, true);
		searchKey.setItemCodeOrder(2, true);
		searchKey.setCasePieceFlagOrder(3, true);
		searchKey.setCaseLocationOrder(4, true);
		searchKey.setPieceLocationOrder(5, true);

		if (!canLowerDisplay(storageHandler.count(searchKey)))
		{
			return returnNoDisplayParameter();
		}

		StoragePlan[] resultEntity = (StoragePlan[])storageHandler.find(searchKey);

		// Obtain the Consignor Names and customer names with later added date/time.
		//Set the Search key.
		namesearchKey = setStoragePlanSearchKey(param);

		namesearchKey.setRegistDateOrder(1, false);
		namesearchKey.setConsignorNameCollect();
		StoragePlan[] storage = (StoragePlan[])storageHandler.find(namesearchKey);
		String consignorname = "";
		if(storage != null && storage.length != 0)
		{
			consignorname = storage[0].getConsignorName();
		}

		Vector vec = new Vector();

		for (int loop = 0; loop < resultEntity.length; loop++)
		{
			StorageSupportParameter dispData = new StorageSupportParameter();
			// Consignor code
			dispData.setConsignorCode(resultEntity[loop].getConsignorCode());
			// Consignor Name (Consignor Name with later Added Date/Time)
			dispData.setConsignorName(consignorname);
			// Planned storage date
			dispData.setStoragePlanDate(resultEntity[loop].getPlanDate());
			// Item Code
			dispData.setItemCode(resultEntity[loop].getItemCode());
			// Item Name
			dispData.setItemName(resultEntity[loop].getItemName1());
			// Case/Piece division
			dispData.setCasePieceflgName(DisplayUtil.getPieceCaseValue(resultEntity[loop].getCasePieceFlag()));
			// Case Storage Location
			dispData.setCaseLocation(resultEntity[loop].getCaseLocation());
			// Piece Storage Location
			dispData.setPieceLocation(resultEntity[loop].getPieceLocation());
			// Packed qty per Case
			dispData.setEnteringQty(resultEntity[loop].getEnteringQty());
			// Packed qty per bundle
			dispData.setBundleEnteringQty(resultEntity[loop].getBundleEnteringQty());
			// Host planned Case qty
			dispData.setPlanCaseQty(DisplayUtil.getCaseQty(resultEntity[loop].getPlanQty(), resultEntity[loop].getEnteringQty(),
					resultEntity[loop].getCasePieceFlag()));
			// Host Planned Piece Qty
			dispData.setPlanPieceQty(DisplayUtil.getPieceQty(resultEntity[loop].getPlanQty(), resultEntity[loop].getEnteringQty(),
					resultEntity[loop].getCasePieceFlag()));
			// Result Case Qty
			dispData.setResultCaseQty(DisplayUtil.getCaseQty(resultEntity[loop].getResultQty(), resultEntity[loop].getEnteringQty(),
					resultEntity[loop].getCasePieceFlag()));
			// Result Piece Qty
			dispData.setResultPieceQty(DisplayUtil.getPieceQty(resultEntity[loop].getResultQty(), resultEntity[loop].getEnteringQty(),
					resultEntity[loop].getCasePieceFlag()));
			// Work status name
			dispData.setWorkStatusName(DisplayUtil.getStoragePlanStatusValue(resultEntity[loop].getStatusFlag()));
			// Case ITF
			dispData.setITF(resultEntity[loop].getItf());
			// Bundle ITF
			dispData.setBundleITF(resultEntity[loop].getBundleItf());

			vec.addElement(dispData);
		}

		StorageSupportParameter[] paramArray = new StorageSupportParameter[vec.size()];

		vec.copyInto(paramArray);

		// 6001013 = Data is shown.
		wMessage = "6001013";
		return paramArray;
	}

	/**
	 * Check the input area contents<BR>
	 * Check the maximum/minimum limit for Storage Plan date
	 * @param conn Instance to store database connection
	 * @param searchParam <CODE>StorageSupportParameter</CODE> class instance that holds the display data fetch condition<BR>
	 *         <CODE>StorageSupportParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return Check result (normal：true abnormal：false)
	 * @throws ReadWriteException If abnormal error occurs during database access
	 * @throws ScheduleException If unexpected error occurs during the process
	 */
	public boolean check(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		StorageSupportParameter param = (StorageSupportParameter)searchParam;

		// Obtain the Planned start storage Date and Planned end storage date from the parameter.
		String fromplandate = param.getFromStoragePlanDate();
		String toplandate = param.getToStoragePlanDate();

		// Planned storage date Check the value for larger or smaller
		if (!StringUtil.isBlank(fromplandate) && !StringUtil.isBlank(toplandate))
		{
			if (fromplandate.compareTo(toplandate) > 0)
			{
				// 6023045 = Starting planned shipping date must precede the end planned shipping date.
				wMessage = "6023199";
				return false;
			}
		}

		return true;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * This method sets the search conditions for the search key of Storage Plan info.<BR>
	 *
	 * @param sKey StoragePlan Search Key
	 * @return StoragePlan Search Key
	 * @throws ReadWriteException If abnormal error occurs during database access
	 */
	private StoragePlanSearchKey setStoragePlanSearchKey(StorageSupportParameter pParam)
			throws ReadWriteException
	{
		StoragePlanSearchKey sKey = new StoragePlanSearchKey();

		// Search Data
		// Consignor code
		sKey.setConsignorCode(pParam.getConsignorCode());

		// Planned start storage DatePlanned end storage Date
		String fromplandate = pParam.getFromStoragePlanDate();
		String toplandate = pParam.getToStoragePlanDate();
		if (!StringUtil.isBlank(fromplandate))
		{
			sKey.setPlanDate(fromplandate, ">=");
		}
		if (!StringUtil.isBlank(toplandate))
		{
			sKey.setPlanDate(toplandate, "<=");
		}
		// Item Code
		String itemcode = pParam.getItemCode();
		if (!StringUtil.isBlank(itemcode))
		{
			sKey.setItemCode(itemcode);
		}
		// Case/Piece division Selecting All disables to designate anything.
		if(!StringUtil.isBlank(pParam.getCasePieceflg()))
		{
			// Case division
			if(pParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
			{
				sKey.setCasePieceFlag(StoragePlan.CASEPIECE_FLAG_CASE);
			}
			// Piece division
			else if(pParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				sKey.setCasePieceFlag(StoragePlan.CASEPIECE_FLAG_PIECE);
			}
			// None
			else if(pParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				sKey.setCasePieceFlag(StoragePlan.CASEPIECE_FLAG_NOTHING);
			}
			// Mixed
			else if(pParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_MIXED))
			{
				sKey.setCasePieceFlag(StoragePlan.CASEPIECE_FLAG_MIX);
			}
		}

		// Status flag (Obtain either one of All, Standby, Processing, Pending (Partially Completed), or Completed from parameters)
		if (!pParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_ALL))
		{
			// Standby
			if(pParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
			{
				sKey.setStatusFlag(StoragePlan.STATUS_FLAG_UNSTART);
			}
			// Processing
			else if(pParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_WORKING))
			{
				sKey.setStatusFlag(StoragePlan.STATUS_FLAG_NOWWORKING);
			}
			// Pending (Partially Completed)
			else if(pParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
			{
				sKey.setStatusFlag(StoragePlan.STATUS_FLAG_COMPLETE_IN_PART);
			}
			// completed
			else if(pParam.getWorkStatus().equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
			{
				sKey.setStatusFlag(StoragePlan.STATUS_FLAG_COMPLETION);
			}
		}
		// All
		else
		{
			String[] statusflg = { StoragePlan.STATUS_FLAG_UNSTART, StoragePlan.STATUS_FLAG_NOWWORKING, StoragePlan.STATUS_FLAG_COMPLETE_IN_PART, StoragePlan.STATUS_FLAG_COMPLETION };
			sKey.setStatusFlag(statusflg);
		}

		return sKey;
	}
}
//end of class
