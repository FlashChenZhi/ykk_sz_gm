// $Id: RetrievalOrderQtyWriter.java,v 1.6 2007/02/07 04:19:36 suresh Exp $
package jp.co.daifuku.wms.retrieval.report;

//#CM591734
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.ResultViewHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultViewReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM591735
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * 
 * Allow the <CODE>RetrievalOrderQtyWriter</CODE> class to generate a data file for Order Picking Result report and execute printing.<BR>
 * Search for Result info (with Picking and Order No.) using the conditions set in the <CODE>RetrievalOrderQtyListSCH</CODE> class, and<BR>
 * generate a Report file for Order Picking Result report from the search result.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * Execute the process for generating a data file for report. (<CODE>startPrint()</CODE>method)<BR>
 *	<DIR>
 *		1.Search for the count of Result info (Picking Order No.) using the conditions set in the <CODE>RetrievalOrderQtyListSCH</CODE> class.<BR>
 *		2.Generate data file for report if one or more result found. If zero, return false and close.<BR>
 *		3.Execute the process for printing.<BR>
 *		4.Return true if print process normally completed.<BR>
 *		  Return false when error occurs in the process of printing.<BR>
 *		<BR>
 * 		<Parameter>*Mandatory Input<BR>
 * 		<DIR>
 * 			Consignor Code*<BR>
 *	 		Start Picking Date<BR>
 * 			End Picking Date<BR>
 * 			Customer Code<BR>
 * 			Order No.<BR>
 * 			Case/Piece division<BR>
 * 		</DIR>
 * 		<BR>
 * 		<Search conditions><BR>
 * 		<DIR>
 * 			Consignor Code*<BR>
 * 			Start Picking Date<BR>
 * 			End Picking Date<BR>
 * 			Customer Code<BR>
 * 			Order No. is not NULL.<BR>
 * 			Case/Piece division<BR>
 *          Work division = Picking<BR>
 * 			Status = other than Deleted<BR>
 * 		</DIR>
 * 		<BR>
 *		<Data to be printed><BR>
 *		<DIR>
 *			"Print" field item: DB field item<BR>
 *			Consignor: Consignor Code + Consignor Name<BR>
 *			Picking Date: Work date<BR>
 *			Planned Picking Date: Planned date<BR>
 *			Customer: Customer Code + Customer Name<BR>
 *			Order No.: Order No.<BR>
 *			Item Code: Item Code<BR>
 *			Item Name: Item Name<BR>
 *			Division: Case/Piece division<BR>
 *			Packed Qty per Case: Packed Qty per Case<BR>
 *			Packed qty per bundle: Packed qty per bundle<BR>
 *		</DIR>
 *		<BR>
 *		For data with Case/Piece division "Case":<BR>
 *		<DIR>
 *			Planned Work Case Qty: Planned qty / Packed Qty per Case<BR>
 *			Planned Work Piece qty: Planned qty % Packed qty per Case<BR>
 *			Result Case QTY: Work Result qty / Packed Qty per Case<BR>
 *			Result Piece qty: Work Result qty % Packed qty per Case<BR>
 *			Shortage Case Qty: Work shortage qty/Packed Qty per Case<BR>
 *			Shortage Piece qty: Work Shortage qty % Packed qty per Case<BR>
 *			Picking Location: Location No.<BR>
 *          Expiry Date: Expiry Date<BR>
 *		</DIR>
 *		<BR>
 *		For data with Case/Piece division "Piece" or "None":<BR>
 *		<DIR>
 *			Planned Work Case Qty: 0<BR>
 *			Planned Work Piece Qty: Planned qty<BR>
 *			Result Case Qty: 0<BR>
 *			Result Piece Qty: Work Result qty<BR>
 *			Shortage Case Qty: 0<BR>
 *			Shortage Piece Qty: Work shortage qty<BR>
 *			Picking Location: Location No.<BR>
 *          Expiry Date: Expiry Date<BR>
 *		</DIR>
 * 	</DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/20</TD><TD>K.Matsuda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.6 $, $Date: 2007/02/07 04:19:36 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderQtyWriter extends CSVWriter
{
	//#CM591736
	// Class fields --------------------------------------------------

	//#CM591737
	// Class variables -----------------------------------------------
	//#CM591738
	/**
	 * Consignor Code
	 */
	private String wConsignorCode = null;

	//#CM591739
	/**
	 * Start Picking Date
	 */
	private String wFromWorkDate = null;

	//#CM591740
	/**
	 * End Picking Date
	 */
	private String wToWorkDate = null;

	//#CM591741
	/**
	 * Customer Code	
	 */
	private String wCustomerCode = null;

	//#CM591742
	/**
	 * Order No.
	 */
	private String wOrderNo = null;

	//#CM591743
	/**
	 * Case/Piece division
	 */
	private String wCasePieceFlag = null;

	//#CM591744
	/**
	 * Consignor Name to be displayed
	 */
	protected String wConsignorName = null;

	//#CM591745
	/**
	 * Customer Name to be displayed
	 */
	protected String wCustomerName = null;

	//#CM591746
	// Class method --------------------------------------------------
	//#CM591747
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.6 $,$Date: 2007/02/07 04:19:36 $");
	}

	//#CM591748
	// Constructors --------------------------------------------------
	//#CM591749
	/**
	 * Construct the RetrievalOrderQtyWriter object.
	 * @param conn <CODE>Connection</CODE>
	 */
	public RetrievalOrderQtyWriter(Connection conn)
	{
		super(conn);
	}

	//#CM591750
	// Public methods ------------------------------------------------
	//#CM591751
	/**
	 * Set a value in the Consignor Code.
	 * @param consignorcode Consignor code to be set
	 */
	public void setConsignorCode(String consignorcode)
	{
		wConsignorCode = consignorcode;
	}

	//#CM591752
	/**
	 * Obtain the Consignor code.
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM591753
	/**
	 * Set a value in the Start Picking Date.
	 * @param fromWorkDate Start Picking Date to be set
	 */
	public void setFromWorkDate(String fromWorkDate)
	{
		wFromWorkDate = fromWorkDate;
	}

	//#CM591754
	/**
	 * Obtain the Start Picking Date.
	 * @return Start Picking Date
	 */
	public String getFromWorkDate()
	{
		return wFromWorkDate;
	}

	//#CM591755
	/**
	 * Set a value in the End Picking Date.
	 * @param toWorkDate End Picking Date to be set
	 */
	public void setToWorkDate(String toWorkDate)
	{
		wToWorkDate = toWorkDate;
	}

	//#CM591756
	/**
	 * Obtain the End Picking Date.
	 * @return End Picking Date
	 */
	public String getToWorkDate()
	{
		return wToWorkDate;
	}

	//#CM591757
	/**
	 * Set a value in the Customer Code.
	 * @param customercode Customer Code to be set
	 */
	public void setCustomerCode(String customercode)
	{
		wCustomerCode = customercode;
	}

	//#CM591758
	/**
	 * Obtain the customer code.
	 * @return Customer Code
	 */
	public String getCustomerCode()
	{
		return wCustomerCode;
	}

	//#CM591759
	/**
	 * Set a value in the Order No.
	 * @param orderNo Order No. to be set
	 */
	public void setOrderNo(String orderNo)
	{
		wOrderNo = orderNo;
	}

	//#CM591760
	/**
	 * Obtain the Order No.
	 * @return Order No.
	 */
	public String getOrderNo()
	{
		return wOrderNo;
	}

	//#CM591761
	/**
	 * Set a value in the Case/Piece division.
	 * @param casePieceFlag Case/Piece division to be set
	 */
	public void setCasePieceFlag(String casePieceFlag)
	{
		wCasePieceFlag = casePieceFlag;
	}

	//#CM591762
	/**
	 * Case Obtain the Piece division.
	 * @return Case/Piece division
	 */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}

	//#CM591763
	/**
	 * Obtain the count of the print data.<BR>
	 * Use this to determine to execute the process for printing by this search result.<BR>
	 * Use this method via the schedule class for processing the screen.<BR>
	 * 
	 * @return Count of printed data.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int count() throws ReadWriteException
	{
		ResultViewHandler instockHandle = new ResultViewHandler(wConn);
		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		//#CM591764
		// Set the search conditions and obtain the count.
		setResultViewSearchKey(searchKey);
		return instockHandle.count(searchKey);

	}

	//#CM591765
	/**
	 * Generate a CSV file for Order Picking Result report and execute printing.<BR>
	 * Search through the count of Result info using the committed condition.<BR>
	 * Generate data file for report if one or more result found. If zero, return false and close.<BR>
	 * Execute the process for printing.<BR>
	 * Return true if print process normally completed.<BR>
	 * Return false when error occurs in the process of printing.<BR>
	 * @return boolean Result whether the printing succeeded or not.
	 */
	public boolean startPrint()
	{
		ResultViewReportFinder resultViewReportFinder = new ResultViewReportFinder(wConn);
		ResultViewSearchKey resultViewSearchKey = new ResultViewSearchKey();
		ResultView[] result = null;

		try
		{
			//#CM591766
			// Set the search conditions.
			setResultViewSearchKey(resultViewSearchKey);

			resultViewSearchKey.setWorkDateOrder(1, true);
			resultViewSearchKey.setPlanDateOrder(2, true);
			resultViewSearchKey.setCustomerCodeOrder(3, true);
			resultViewSearchKey.setOrderNoOrder(4, true);
			resultViewSearchKey.setItemCodeOrder(5, true);
			resultViewSearchKey.setWorkFormFlagOrder(6, true);
			resultViewSearchKey.setLocationNoOrder(7, true);
			resultViewSearchKey.setRegistDateOrder(8, true);
			resultViewSearchKey.setResultQtyOrder(9, true);

			//#CM591767
			// Open the cursor.
			resultViewReportFinder.open();

			//#CM591768
			// No target data
			if (resultViewReportFinder.search(resultViewSearchKey) <= 0)
			{
				//#CM591769
				// No print data found.
				wMessage = "6003010";
				return false;
			}

			//#CM591770
			// Commit the consignor name.
			getDisplayConsignorName();

			if (wConsignorName == null)
			{
				//#CM591771
				// No print data found.
				wMessage = "6003010";
				return false;
			}

			//#CM591772
			//Generate a file to be output.
			if (!createPrintWriter(FNAME_RETRIEVAL_ORDERQTY))
			{
				return false;
			}
			//#CM591773
			// For comparing the Customer Code
			String tempCustomerCode = "";

			//#CM591774
			// Generate a data file per 100 search results until there remains no result data.
			while (resultViewReportFinder.isNext())
			{
				//#CM591775
				// Obtain up to 100 search results.
				result = (ResultView[]) resultViewReportFinder.getEntities(100);

				for (int i = 0; i < result.length; i++)
				{
					wStrText.append(re + "");

					//#CM591776
					// Consignor Code
					wStrText.append(ReportOperation.format(result[i].getConsignorCode()) + tb);
					//#CM591777
					// Consignor Name
					wStrText.append(ReportOperation.format(wConsignorName) + tb);
					//#CM591778
					// Picking Date
					wStrText.append(ReportOperation.format(result[i].getWorkDate()) + tb);
					//#CM591779
					// Planned Picking Date
					wStrText.append(ReportOperation.format(result[i].getPlanDate()) + tb);
					//#CM591780
					// Customer Code
					wStrText.append(ReportOperation.format(result[i].getCustomerCode()) + tb);
					//#CM591781
					// Customer Name
					if (!tempCustomerCode.equals(result[i].getCustomerCode()))
					{
						//#CM591782
						// Maintain it for the next looping.
						tempCustomerCode = result[i].getCustomerCode();
						//#CM591783
						// Maintain the Customer Code for search.						
						this.setCustomerCode(result[i].getCustomerCode());
						//#CM591784
						// Obtain the Customer Name.
						getDisplayCustomerName();
					}
					wStrText.append(ReportOperation.format(wCustomerName) + tb);
					//#CM591785
					// Order No.
					wStrText.append(ReportOperation.format(result[i].getOrderNo()) + tb);
					//#CM591786
					// Item Code
					wStrText.append(ReportOperation.format(result[i].getItemCode()) + tb);
					//#CM591787
					// Item Name
					wStrText.append(ReportOperation.format(result[i].getItemName1()) + tb);
					//#CM591788
					// Division
					wStrText.append(
						ReportOperation.format(DisplayUtil.getPieceCaseValue(result[i].getWorkFormFlag())) + tb);
					//#CM591789
					// Packed Qty per Case
					wStrText.append(result[i].getEnteringQty() + tb);
					//#CM591790
					// Packed qty per bundle
					wStrText.append(result[i].getBundleEnteringQty() + tb);
					//#CM591791
					// Planned Work Case Qty
					wStrText.append(
						DisplayUtil.getCaseQty(
							result[i].getPlanQty(),
							result[i].getEnteringQty(),
							result[i].getWorkFormFlag())
							+ tb);
					//#CM591792
					// Planned Work Piece Qty
					wStrText.append(
						DisplayUtil.getPieceQty(
							result[i].getPlanQty(),
							result[i].getEnteringQty(),
							result[i].getWorkFormFlag())
							+ tb);
					//#CM591793
					// Result Case QTY
					wStrText.append(
						DisplayUtil.getCaseQty(
							result[i].getResultQty(),
							result[i].getEnteringQty(),
							result[i].getWorkFormFlag())
							+ tb);
					//#CM591794
					// Result Piece QTY
					wStrText.append(
						DisplayUtil.getPieceQty(
							result[i].getResultQty(),
							result[i].getEnteringQty(),
							result[i].getWorkFormFlag())
							+ tb);
					//#CM591795
					// Shortage Case Qty
					wStrText.append(
						DisplayUtil.getCaseQty(
							result[i].getShortageCnt(),
							result[i].getEnteringQty(),
							result[i].getWorkFormFlag())
							+ tb);
					//#CM591796
					// Shortage Piece Qty
					wStrText.append(
						DisplayUtil.getPieceQty(
							result[i].getShortageCnt(),
							result[i].getEnteringQty(),
							result[i].getWorkFormFlag())
							+ tb);
					//#CM591797
					// Picking Location 
					wStrText.append(
						WmsFormatter.toDispLocation(result[i].getResultLocationNo(), result[i].getSystemDiscKey())
							+ tb);
					//#CM591798
					// Expiry Date
					wStrText.append(ReportOperation.format(result[i].getResultUseByDate()));

					//#CM591799
					// Writing
					wPWriter.print(wStrText);

					wStrText.setLength(0);
				}
			}

			//#CM591800
			// Close the stream.
			wPWriter.close();

			//#CM591801
			// Execute UCXSingle.
			if (!executeUCX(JOBID_RETRIEVAL_ORDERQTY))
			{
				//#CM591802
				// Failed to print. See log.
				return false;
			}

			//#CM591803
			// Move the data file to the backup folder.
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)
		{
			//#CM591804
			// Failed to print. See log.
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM591805
				// Execute processes for closing the cursor for the opened database.
				resultViewReportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM591806
				// Database error occurred. See log.
				setMessage("6007002");
				return false;
			}
		}

		return true;
	}

	//#CM591807
	// Package methods -----------------------------------------------

	//#CM591808
	// Protected methods ---------------------------------------------

	//#CM591809
	// Private methods -----------------------------------------------
	//#CM591810
	/**
	 * Obtain the Consignor name to display in the List.<BR>
	 * Search for the Result information with the latest Added Date/Time using the search condition for data to be printed, and <BR>
	 * obtain the Consignor Name of the leading data.<BR>
	 * 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void getDisplayConsignorName() throws ReadWriteException
	{
		//#CM591811
		// Generate the Finder instance.
		ResultViewReportFinder consignorFinder = new ResultViewReportFinder(wConn);
		ResultViewSearchKey resultViewSearchKey = new ResultViewSearchKey();

		//#CM591812
		// Set the search conditions.
		setResultViewSearchKey(resultViewSearchKey);
		resultViewSearchKey.setRegistDateOrder(1, false);

		//#CM591813
		// Search Consignor Name
		consignorFinder.open();
		if (consignorFinder.search(resultViewSearchKey) > 0)
		{
			ResultView[] result = (ResultView[]) consignorFinder.getEntities(1);

			if (result != null && result.length != 0)
			{
				wConsignorName = result[0].getConsignorName();
			}
		}

		consignorFinder.close();

	}

	//#CM591814
	/**
	 * Obtain the customer name to display in the list.<BR>
	 * Search for the Result information with the latest Added Date/Time using the search condition for data to be printed, and <BR>
	 * return the Customer Name of the leading data.<BR>
	 * 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void getDisplayCustomerName() throws ReadWriteException
	{
		//#CM591815
		// Generate the Finder instance.
		ResultViewReportFinder customerFinder = new ResultViewReportFinder(wConn);
		ResultViewSearchKey resultViewSearchKey = new ResultViewSearchKey();

		//#CM591816
		//Set the search conditions.
		setResultViewSearchKey(resultViewSearchKey);
		resultViewSearchKey.setRegistDateOrder(1, false);

		//#CM591817
		// Search Consignor Name
		customerFinder.open();
		if (customerFinder.search(resultViewSearchKey) > 0)
		{
			ResultView[] result = (ResultView[]) customerFinder.getEntities(1);

			if (result != null && result.length != 0)
			{
				wCustomerName = result[0].getCustomerName1();
			}
		}
		customerFinder.close();
	}

	//#CM591818
	/**
	 * Set the search conditions.<BR>
	 * @param resultViewSearchKey search key
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void setResultViewSearchKey(ResultViewSearchKey resultViewSearchKey) throws ReadWriteException
	{
		//#CM591819
		//Set the search conditions.
		if (!StringUtil.isBlank(wConsignorCode))
		{
			resultViewSearchKey.setConsignorCode(wConsignorCode);
		}
		if (!StringUtil.isBlank(wFromWorkDate))
		{
			resultViewSearchKey.setWorkDate(wFromWorkDate, ">=");
		}
		if (!StringUtil.isBlank(wToWorkDate))
		{
			resultViewSearchKey.setWorkDate(wToWorkDate, "<=");
		}
		if (!StringUtil.isBlank(wCustomerCode))
		{
			resultViewSearchKey.setCustomerCode(wCustomerCode);
		}
		if (!StringUtil.isBlank(wOrderNo))
		{
			resultViewSearchKey.setOrderNo(wOrderNo);
		}
		else
		{
			resultViewSearchKey.setOrderNo("", "IS NOT NULL");
		}
		//#CM591820
		// Case/Piece division (Work Type) 
		if (!StringUtil.isBlank(wCasePieceFlag))
		{
			//#CM591821
			// Set for each Case/Piece division selected via screen. * Selecting "All" disables to set it.
			//#CM591822
			// Case
			if (wCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				resultViewSearchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
			}
			//#CM591823
			// Piece
			else if (wCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				resultViewSearchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
			}
			//#CM591824
			// "None"
			else if (wCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				resultViewSearchKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_NOTHING);
			}
		}
		resultViewSearchKey.setJobType(ResultView.JOB_TYPE_RETRIEVAL);
		resultViewSearchKey.setStatusFlag(ResultView.STATUS_FLAG_DELETE, "!=");

	}
}
//#CM591825
//end of class
