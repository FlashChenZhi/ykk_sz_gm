package jp.co.daifuku.wms.stockcontrol.schedule;

//#CM10777
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


//#CM10778
/**
 * Designer : T.Nakajim(UTC) <BR>
 * Maker : T.Nakajima(UTC) <BR>
 * <BR>
 * This class allows to execute the process for Unplanned Picking Result Inquiry. <BR>
 * Execute the process for Unplanned Picking Result inquiry based on the parameter received via screen. <BR>
 * This class executes the following processes. <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method) <BR>
 * <BR>
 *   <DIR>
 *   If only one Consignor code exist in the Result data inquiry <CODE> (DvResultView)</CODE>, return the corresponding Consignor code. <BR>
 *   Return null if no or multiple corresponding data found. <BR>
 *   <BR>
 *   [Search conditions] <BR>
 *   <DIR>
 *   Work division��Unplanned shipment <BR>
 *   </DIR>
 * </DIR>
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
 *   Start picking date : FromWorkDate <BR>
 *   End picking date : ToWorkDate <BR>
 *   Item Code : ItemCode <BR>
 *   customer code : CustomerCode <BR>
 *   Case/Piece division* : CasePieceFlag <BR>
 *   Display order* : DspOrder <BR>
 *   </DIR>
 *   <BR>
 *   [Returned data] <BR>
 *   Consignor code : ConsignorCode <BR>
 *   Consignor name : ConsignorName <BR>
 *   picking date : RetrievalDateString <BR>
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
 *   customer code : CustomerCode <BR>
 *   customer name : CustomerName <BR>
 *   Worker Code : WorkerCode <BR>
 *   Worker Name : WorkerName <BR>
 *   </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/11</TD><TD>T.Nakajima</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/10 07:57:09 $
 * @author  $Author: suresh $
 */
public class NoPlanRetrievalQtyInqirySCH extends AbstractStockControlSCH
{
	
	//#CM10779
	// Class variables -----------------------------------------------

	//#CM10780
	// Class method --------------------------------------------------
	//#CM10781
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/10 07:57:09 $");
	}

	//#CM10782
	// Constructors --------------------------------------------------
	//#CM10783
	/**
	 * Initialize this class.
	 */
	public NoPlanRetrievalQtyInqirySCH()
	{
		wMessage = null;
	}

	//#CM10784
	// Public methods ------------------------------------------------
	
	//#CM10785
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
		
		//#CM10786
		// Generate search conditions.
		//#CM10787
		// Set the work division= Unplanned Picking for search conditions.
		searchKey.setJobType(ResultView.JOB_TYPE_EX_RETRIEVAL);
		searchKey.setConsignorCodeCollect(" ");
		searchKey.setConsignorCodeGroup(1);
		
		//#CM10788
		// Generate instance for a return parameter class.
		StockControlParameter dispData = new StockControlParameter();
		
		//#CM10789
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

	//#CM10790
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
		
		//#CM10791
		// Check the contents of the parameter.
		if (!check(conn, wParam))
		{
			return null;
		}
		
		//#CM10792
		// This class operates the Result data inquiry (DvResultView).
		ResultViewHandler resultviewHandler = new ResultViewHandler(conn);
		//#CM10793
		// This class stores search conditions for preset list.
		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		//#CM10794
		// This class stores search conditions for Consignor name.
		ResultViewSearchKey nameKey = new ResultViewSearchKey();
		
		//#CM10795
		// Generate search conditions.
		//#CM10796
		// Consignor code
		searchKey.setConsignorCode(wParam.getConsignorCode());
		nameKey.setConsignorCode(wParam.getConsignorCode());
		//#CM10797
		// Start picking date
		if (!StringUtil.isBlank(wParam.getFromWorkDate())) 
		{
			searchKey.setWorkDate(wParam.getFromWorkDate(), ">=");
			nameKey.setWorkDate(wParam.getFromWorkDate(), ">=");
		}
		//#CM10798
		// End picking date
		if (!StringUtil.isBlank(wParam.getToWorkDate())) 
		{
			searchKey.setWorkDate(wParam.getToWorkDate(), "<=");
			nameKey.setWorkDate(wParam.getToWorkDate(), "<=");
		}
		//#CM10799
		// Item Code
		if (!StringUtil.isBlank(wParam.getItemCode()))
		{
			searchKey.setItemCode(wParam.getItemCode());
			nameKey.setItemCode(wParam.getItemCode());
		}
		//#CM10800
		// customer code
		if (!StringUtil.isBlank(wParam.getCustomerCode()))
		{
			searchKey.setCustomerCode(wParam.getCustomerCode());
			nameKey.setCustomerCode(wParam.getCustomerCode());
		}
		//#CM10801
		// Case/Piece division Selecting All disables to set anything.
		if(!StringUtil.isBlank(wParam.getCasePieceFlag()))
		{
			//#CM10802
			// Case
			if(wParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
				nameKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
			}
			//#CM10803
			// Piece
			else if(wParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
				nameKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
			}
			//#CM10804
			// None
			else if(wParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				searchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_NOTHING);
				nameKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_NOTHING);
			}
		}
		//#CM10805
		// Work division:Unplanned shipment
		searchKey.setJobType(ResultView.JOB_TYPE_EX_RETRIEVAL);
		nameKey.setJobType(ResultView.JOB_TYPE_EX_RETRIEVAL);
		
		//#CM10806
		// Set display sequence.
		if (wParam.getDspOrder().equals(StockControlParameter.DSPORDER_ITEM))
		{
			//#CM10807
			// Item Code sequence
			//#CM10808
			// Item Code
			searchKey.setItemCodeOrder(1, true);
			//#CM10809
			// picking date
			searchKey.setWorkDateOrder(2, true);
			//#CM10810
			// division
			searchKey.setCasePieceFlagOrder(3, true);
			//#CM10811
			// shipping Location
			searchKey.setResultLocationNoOrder(4, true);
			//#CM10812
			// Defining the expiry date in WmsParam as the item (count) to make the stock unique enables to include it in the display order key.
			if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				//#CM10813
				// Expiry Date
				searchKey.setResultUseByDateOrder(5, true);
				//#CM10814
				// customer code
				searchKey.setCustomerCodeOrder(6, true);
			}
			else
			{
				//#CM10815
				// customer code
				searchKey.setCustomerCodeOrder(5, true);
			}
		}
		else if (wParam.getDspOrder().equals(StockControlParameter.DSPORDER_CUSTOMER_ITEM))
		{
			//#CM10816
			// customer code-Item Code sequence
			//#CM10817
			// customer code
			searchKey.setCustomerCodeOrder(1, true);
			//#CM10818
			// Item Code
			searchKey.setItemCodeOrder(2, true);
			//#CM10819
			// picking date
			searchKey.setWorkDateOrder(3, true);
			//#CM10820
			// division
			searchKey.setCasePieceFlagOrder(4, true);
			//#CM10821
			// shipping Location
			searchKey.setResultLocationNoOrder(5, true);
			//#CM10822
			// Defining the expiry date in WmsParam as the item (count) to make the stock unique enables to include it in the display order key.
			if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				//#CM10823
				// Expiry Date
				searchKey.setResultUseByDateOrder(6, true);
			}
		}
		
		//#CM10824
		// Obtain display count
		if (!canLowerDisplay(resultviewHandler.count(searchKey)))
		{
			return returnNoDisplayParameter();
		}

		//#CM10825
		// Execute the search.
		ResultView[] resultEntity = (ResultView[]) resultviewHandler.find(searchKey);
		
		//#CM10826
		// Obtain the Consignor Name.
		String consignorName = "";
		nameKey.setConsignorNameCollect("");
		//#CM10827
		// Set the sorting order of the added date/time to descending order to obtain the latest Consignor Name/
		nameKey.setRegistDateOrder(1, false);
		
		//#CM10828
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

		//#CM10829
		// Generate instance for a class to store all the compiled search results.
		Vector vec = new Vector();
	    AreaOperator ao = new AreaOperator(conn);
		//#CM10830
		// Display the Unplanned Storage Result Data and compile it.
		for (int i = 0; i < resultEntity.length; i++)
		{
			//#CM10831
			// Generate instance for a class to store compiled search result.
			StockControlParameter dispData = new StockControlParameter();
			
			//#CM10832
			// Consignor code
			dispData.setConsignorCode(resultEntity[i].getConsignorCode());
			//#CM10833
			// Consignor name 
			dispData.setConsignorName(consignorName);
			//#CM10834
			// Work Date(picking date)
			dispData.setRetrievalDateString(resultEntity[i].getWorkDate());
			//#CM10835
			// Item Code
			dispData.setItemCode(resultEntity[i].getItemCode());
			//#CM10836
			// Item Name
			dispData.setItemName(resultEntity[i].getItemName1());
			//#CM10837
			// Case/Piece division 
			dispData.setCasePieceFlagName(DisplayUtil.getPieceCaseValue(resultEntity[i].getWorkFormFlag()));
			//#CM10838
			// shipping Location
			dispData.setLocationNo(resultEntity[i].getResultLocationNo());
			//#CM10839
			// Packed qty per Case
			dispData.setEnteringQty(resultEntity[i].getEnteringQty());
			//#CM10840
			// Packed qty per bundle
			dispData.setBundleEnteringQty(resultEntity[i].getBundleEnteringQty());
			if(resultEntity[i].getWorkFormFlag().equals(ResultView.CASEPIECE_FLAG_PIECE))
			{
				//#CM10841
				// Result Case Qty
				dispData.setResultCaseQty(0);
				//#CM10842
				// Result Piece Qty
				dispData.setResultPieceQty(resultEntity[i].getResultQty());
			}
			else
			{
				//#CM10843
				// Result Case Qty
				dispData.setResultCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getResultQty(), resultEntity[i].getEnteringQty()));
				//#CM10844
				// Result Piece Qty
				dispData.setResultPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getResultQty(), resultEntity[i].getEnteringQty()));
			}
			//#CM10845
			// Case ITF
			dispData.setITF(resultEntity[i].getItf());
			//#CM10846
			// Bundle ITF
			dispData.setBundleITF(resultEntity[i].getBundleItf());
			//#CM10847
			// Expiry Date
			dispData.setUseByDate(resultEntity[i].getResultUseByDate());
			//#CM10848
			// customer code
			dispData.setCustomerCode(resultEntity[i].getCustomerCode());
			//#CM10849
			// customer name
			dispData.setCustomerName(resultEntity[i].getCustomerName1());
			//#CM10850
			// Worker Code
			dispData.setWorkerCode(resultEntity[i].getWorkerCode());
			//#CM10851
			// Worker Name
			dispData.setWorkerName(resultEntity[i].getWorkerName());
			//#CM10852
			// System type
			dispData.setSystemDiskKey(resultEntity[i].getSystemDiscKey());
			//#CM10853
			// Area Name
			dispData.setAreaName(ao.getAreaName(resultEntity[i].getAreaNo()));
			vec.addElement(dispData);

		}
		
		//#CM10854
		// Generate instance for a return parameter class.
		StockControlParameter[] wParamArray = new StockControlParameter[vec.size()];
		//#CM10855
		// Copy the contents of displayed/compiled Unplanned Storage Result Data.
		vec.copyInto(wParamArray);
		
		//#CM10856
		// Set the message.(6001013 = Data is shown.)
		wMessage = "6001013";
		
		return wParamArray;
	}

	//#CM10857
	/** 
	 * Check the input parameter. <BR>
	 * -Check for mandatory input in the Consignor code. <BR>
	 * -Check the values between the Start picking date and the End picking date for larger or smaller. <BR>
	 * @param conn Database connection object
	 * @param checkParam This parameter class includes contents to be checked for input.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 * @return true: if the input content is proper  false: if the input content is not proper
	 */
	public boolean check(Connection conn, Parameter checkParam) throws  ScheduleException
	{
		StockControlParameter wParam = (StockControlParameter) checkParam;
		//#CM10858
		// Check for mandatory input in the Consignor code.
		if (StringUtil.isBlank(wParam.getConsignorCode()))
		{
			//#CM10859
			//6023004=Please enter the consignor code.
			wMessage = "6023004";
			return false;
		}
		//#CM10860
		// Obtain the Start picking date and End picking date from the parameter.
		String wFromRetrievalDate = wParam.getFromWorkDate();
		String wToRetrievalDate = wParam.getToWorkDate();

		//#CM10861
		// Check the value of picking date for larger or smaller.
		if (!StringUtil.isBlank(wFromRetrievalDate) && !StringUtil.isBlank(wToRetrievalDate))
		{
			if (wFromRetrievalDate.compareTo(wToRetrievalDate) > 0)
			{
				//#CM10862
				// 6023107 = Starting picking date must precede the end picking date.
				wMessage = "6023107";
				return false;
			}
		}
		return true;
	}

	//#CM10863
	// Package methods -----------------------------------------------

	//#CM10864
	// Protected methods ---------------------------------------------

	//#CM10865
	// Private methods -----------------------------------------------
}
//#CM10866
//end of class

