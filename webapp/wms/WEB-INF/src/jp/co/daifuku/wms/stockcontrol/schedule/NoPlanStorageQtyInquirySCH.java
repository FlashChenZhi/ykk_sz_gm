package jp.co.daifuku.wms.stockcontrol.schedule;

//#CM11099
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
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM11100
/**
 * Designer : T.Nakajim(UTC) <BR>
 * Maker : T.Nakajima(UTC) <BR>
 * <BR>
 * This class allows to execute the process for Unplanned Storage Result Inquiry. <BR>
 * Execute the process for Unplanned storage result inquiry based on the contents of the parameter received via screen. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method) <BR> 
 * <BR>
 *   <DIR>
 *   If only one Consignor code exist in the Result data inquiry <CODE> (DvResultView)</CODE>, return the corresponding Consignor code. <BR>
 *   Return null if no or multiple corresponding data found. <BR>
 *   <BR>
 *   [Search conditions] <BR>
 *   <DIR>
 *   Work division "Unplanned Storage" <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Process by clicking on Display button (<CODE>Query()</CODE> Method) <BR>
 * <BR>
 *   <DIR>
 *   Search for the Result data inquiry <CODE> (DvResultView)</CODE>based on the contents of the parameter received via screen. <BR>
 *   Return the obtained data as an array of <CODE>Parameter</CODE>. <BR>
 *   If no corresponding data found, return the array of the number of elements[0] <CODE>Parameter</CODE>. <BR>
 *   Return null when condition error occurs. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 * <BR>
 *   [Parameter]*Mandatory Input <BR>
 *   <BR>
 *   <DIR>
 *   Consignor code* : ConsignorCode <BR>
 *   Start Storage Date : FromWorkDate <BR>
 *   End Storage Date : ToWorkDate <BR>
 *   Item Code : ItemCode <BR>
 *   Case/Piece division* : CasePieceFlag <BR>
 *   </DIR>
 *   <BR>
 *   [Returned data] <BR>
 *   Consignor code : ConsignorCode <BR>
 *   Consignor name : ConsignorName <BR>
 *   Storage Date : StorageDateString <BR>
 *   Item Code : ItemCode <BR>
 *   Item Name : ItemName <BR>
 *   division : CasePieceFlag <BR>
 *   Packed qty per Case : EnteringQty <BR>
 *   Packed qty per bundle : BundleEnteringQty <BR>
 *   Result Case Qty : ResultCaseQty <BR>
 *   Result Piece Qty : ResultPieceQty <BR>
 *   Storage Location : LocationNo <BR>
 *   Expiry Date : UseByDate <BR>
 *   Case ITF : ITF <BR>
 *   Bundle ITF : BundleITF <BR>
 *   Worker Code : WorkerCode <BR>
 *   Worker Name : WorkerName <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/07</TD><TD>T.Nakajima</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/10 00:40:50 $
 * @author  $Author: suresh $
 */
public class NoPlanStorageQtyInquirySCH extends AbstractStockControlSCH
{
	
	//#CM11101
	// Class variables -----------------------------------------------

	//#CM11102
	// Class method --------------------------------------------------
	//#CM11103
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/11/10 00:40:50 $");
	}

	//#CM11104
	// Constructors --------------------------------------------------
	//#CM11105
	/**
	 * Initialize this class.
	 */
	public NoPlanStorageQtyInquirySCH()
	{
		wMessage = null;
	}

	//#CM11106
	// Public methods ------------------------------------------------
	
	//#CM11107
	/**
	 * This method supports operations to obtain the data required for initial display. <BR>
	 * If only one Consignor code exist in the Result information <CODE>(DvResultView)</CODE>, return the corresponding Consignor code. <BR>
	 * Return null if no or multiple corresponding data found. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. <BR>
	 * @param conn Database connection object
	 * @param searchParam This Class inherits the <CODE>Parameter</CODE> class with search conditions
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		ResultViewHandler resultviewHandler = new ResultViewHandler(conn);
		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		
		//#CM11108
		// Generate search conditions.
		//#CM11109
		// Set Work division=Unplanned storage in the search conditions
		searchKey.setJobType(ResultView.JOB_TYPE_EX_STORAGE);
		searchKey.setConsignorCodeCollect(" ");
		searchKey.setConsignorCodeGroup(1);
		
		//#CM11110
		// Generate instance for a return parameter class.
		StockControlParameter dispData = new StockControlParameter();
		
		//#CM11111
		// If one data corresponds, search and set the result in the parameter and return it.
		if (resultviewHandler.count(searchKey) == 1)
		{
			try
			{
				ResultView[] resultview = (ResultView[]) resultviewHandler.find(searchKey);
				dispData.setConsignorCode(resultview[0].getConsignorCode());
			}
			catch (Exception e)
			{
				return new StockControlParameter();
			}
		}
		return dispData;
	}

	//#CM11112
	/**
	 * Search for the Result data inquiry <CODE> (DvResultView)</CODE>based on the contents of the parameter received via screen. <BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ". <BR>
	 * @param conn Database connection object
	 * @param searchParam This Class inherits the <CODE>Parameter</CODE> class with search conditions
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		StockControlParameter wParam = (StockControlParameter) searchParam;
		
		//#CM11113
		// This class operates the Result data inquiry (DvResultView).
		ResultViewHandler resultviewHandler = new ResultViewHandler(conn);
		//#CM11114
		// This class stores search conditions for preset list.
		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		//#CM11115
		// This class stores search conditions for Consignor name.
		ResultViewSearchKey nameKey = new ResultViewSearchKey();
		
		//#CM11116
		// Generate search conditions.
		//#CM11117
		// Consignor code
		searchKey.setConsignorCode(wParam.getConsignorCode());
		nameKey.setConsignorCode(wParam.getConsignorCode());
		//#CM11118
		// Start Storage Date
		if (!StringUtil.isBlank(wParam.getFromWorkDate())) 
		{
			searchKey.setWorkDate(wParam.getFromWorkDate(), ">=");
			nameKey.setWorkDate(wParam.getFromWorkDate(), ">=");
		}
		//#CM11119
		// End Storage Date
		if (!StringUtil.isBlank(wParam.getToWorkDate())) 
		{
			searchKey.setWorkDate(wParam.getToWorkDate(), "<=");
			nameKey.setWorkDate(wParam.getToWorkDate(), "<=");
		}
		//#CM11120
		// Item Code
		if (!StringUtil.isBlank(wParam.getItemCode()))
		{
			searchKey.setItemCode(wParam.getItemCode());
			nameKey.setItemCode(wParam.getItemCode());
		}
		//#CM11121
		// Case/Piece division Selecting All disables to set anything.
		if(!StringUtil.isBlank(wParam.getCasePieceFlag()))
		{
			//#CM11122
			// Case
			if(wParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
				nameKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
			}
			//#CM11123
			// Piece
			else if(wParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
				nameKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
			}
			//#CM11124
			// None
			else if(wParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				searchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_NOTHING);
				nameKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_NOTHING);
			}
		}
		//#CM11125
		// Work division:Unplanned storage
		searchKey.setJobType(ResultView.JOB_TYPE_EX_STORAGE);
		nameKey.setJobType(ResultView.JOB_TYPE_EX_STORAGE);
		
		//#CM11126
		// Set display sequence.
		//#CM11127
		// Storage Date
		searchKey.setWorkDateOrder(1, true);
		//#CM11128
		// Item Code
		searchKey.setItemCodeOrder(2, true);
		//#CM11129
		// division
		searchKey.setCasePieceFlagOrder(3, true);
		//#CM11130
		// Storage Location
		searchKey.setResultLocationNoOrder(4, true);
		//#CM11131
		// Defining the expiry date in WmsParam as the item (count) to make the stock unique enables to include it in the display order key.
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			//#CM11132
			// Expiry Date
			searchKey.setResultUseByDateOrder(5, true);
		}
		
		//#CM11133
		// Obtain display count
		if (!canLowerDisplay(resultviewHandler.count(searchKey)))
		{
			return returnNoDisplayParameter();
		}
		
		//#CM11134
		// Execute the search.
		ResultView[] resultEntity = (ResultView[]) resultviewHandler.find(searchKey);

		//#CM11135
		// Obtain the Consignor Name.
		String consignorName = "";
		nameKey.setConsignorNameCollect("");
		//#CM11136
		// Set the sorting order of the added date/time to descending order to obtain the latest Consignor Name/
		nameKey.setRegistDateOrder(1, false);
		
		//#CM11137
		// Execute the search.
		ResultViewFinder consignorFinder = new ResultViewFinder(conn);
		consignorFinder.open();
		int nameCount = ((ResultViewFinder) consignorFinder).search(nameKey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			ResultView resultView[] = (ResultView[]) ((ResultViewFinder) consignorFinder).getEntities(0, 1);

			if (resultView != null && resultView.length != 0)
			{
				consignorName = resultView[0].getConsignorName();
			}
		}
		consignorFinder.close();

		//#CM11138
		// Generate instance for a class to store all the compiled search results.
		Vector vec = new Vector();
	    AreaOperator ao = new AreaOperator(conn);
		//#CM11139
		// Display the Unplanned Storage Result Data and compile it.
		for (int i = 0; i < resultEntity.length; i++)
		{
			//#CM11140
			// Generate instance for a class to store compiled search result.
			StockControlParameter dispData = new StockControlParameter();
			
			//#CM11141
			// Consignor code
			dispData.setConsignorCode(resultEntity[i].getConsignorCode());
			//#CM11142
			// Consignor name 
			dispData.setConsignorName(consignorName);
			//#CM11143
			// Work Date(Storage Date)
			dispData.setStorageDateString(resultEntity[i].getWorkDate());
			//#CM11144
			// Item Code
			dispData.setItemCode(resultEntity[i].getItemCode());
			//#CM11145
			// Item Name
			dispData.setItemName(resultEntity[i].getItemName1());
			//#CM11146
			// Case/Piece division 
			dispData.setCasePieceFlagName(DisplayUtil.getPieceCaseValue(resultEntity[i].getWorkFormFlag()));
			//#CM11147
			// Storage Location
			dispData.setLocationNo(resultEntity[i].getResultLocationNo());
			//#CM11148
			// Packed qty per Case
			dispData.setEnteringQty(resultEntity[i].getEnteringQty());
			//#CM11149
			// Packed qty per bundle
			dispData.setBundleEnteringQty(resultEntity[i].getBundleEnteringQty());
			//#CM11150
			// Result Case Qty
			dispData.setResultCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getResultQty(), resultEntity[i].getEnteringQty(), resultEntity[i].getCasePieceFlag()));
			//#CM11151
			// Result Piece Qty
			dispData.setResultPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getResultQty(), resultEntity[i].getEnteringQty(), resultEntity[i].getCasePieceFlag()));
			//#CM11152
			// Case ITF
			dispData.setITF(resultEntity[i].getItf());
			//#CM11153
			// Bundle ITF
			dispData.setBundleITF(resultEntity[i].getBundleItf());
			//#CM11154
			// Expiry Date
			dispData.setUseByDate(resultEntity[i].getResultUseByDate());
			//#CM11155
			// Worker Code
			dispData.setWorkerCode(resultEntity[i].getWorkerCode());
			//#CM11156
			// Worker Name
			dispData.setWorkerName(resultEntity[i].getWorkerName());
			//#CM11157
			// System type
			dispData.setSystemDiskKey(resultEntity[i].getSystemDiscKey());
			//#CM11158
			// Area Name
			dispData.setAreaName(ao.getAreaName(resultEntity[i].getAreaNo()));
			vec.addElement(dispData);
		}
		
		//#CM11159
		// Generate instance for a return parameter class.
		StockControlParameter[] wParamArray = new StockControlParameter[vec.size()];
		//#CM11160
		// Copy the contents of displayed/compiled Unplanned Storage Result Data.
		vec.copyInto(wParamArray);
		
		//#CM11161
		// Set the message.(6001013 = Data is shown.

		wMessage = "6001013";
		
		return wParamArray;
	}

	//#CM11162
	// Package methods -----------------------------------------------

	//#CM11163
	// Protected methods ---------------------------------------------

	//#CM11164
	// Private methods -----------------------------------------------
}
//#CM11165
//end of class
