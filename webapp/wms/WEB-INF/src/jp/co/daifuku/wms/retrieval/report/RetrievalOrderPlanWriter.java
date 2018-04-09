// $Id: RetrievalOrderPlanWriter.java,v 1.6 2007/02/07 04:19:36 suresh Exp $
package jp.co.daifuku.wms.retrieval.report;
//#CM591637
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM591638
/**
 * Designer : Muneendra <BR>
 * Maker : Muneendra <BR>
 * <BR> 
 * Allow the <CODE>RetrievalOrderPlanWriter</CODE> class to generate a data file for Order Picking Plan report and execute printing.<BR>
 * Search for Order Picking Plan Info using the conditions set in the <CODE>RetrievalOrderPlanListSCH</CODE> class, and<BR>
 * generate a Report file for Order Picking Plan report from the search result.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * Execute the process for generating a data file for report. (<CODE>startPrint()</CODE>method)<BR>
 *	<DIR>
 *	1.Search for the count of Picking Plan Info using the conditions set in the <CODE>RetrievalOrderPlanListSCH</CODE> class.<BR>
 *	2.Generate data file for report if one or more result found. If zero, return false and close.<BR>
 *	3.Execute the process for printing.<BR>
 *  4.Return true if print process normally completed.<BR>
 *    Return false when error occurs in the process of printing.<BR>
 *<BR>
 * 	<Parameter>*Mandatory Input<BR>
 *	 	Consignor Code* <BR>
 *      Start Planned Picking Date<BR>
 * 		End Planned Picking Date<BR>
 *      Customer Code<BR>
 *      Case Order No.<BR>
 * 		Piece Order No.<BR>
 * 		Work Status<BR>
 *     <BR>		
 *	<Data to be printed><BR>
 * 		Consignor Code <BR>
 * 		Consignor Name <BR>
 * 		Planned Picking Date<BR>
 * 		Case Order No.<BR>
 *      Piece Order No.<BR>  			
 * 		Item Code<BR>
 * 		Item Name<BR>
 *      Case/Piece division
 *		Packed Qty per Case<BR>
 *		Packed qty per bundle<BR>
 *		Host Planned Case QTY: Planned Picking Qty /Packed Qty per Case<BR>
 *		Host Planned Piece qty: Planned Picking qty % Packed qty per Case<BR>
 *		Result Case Qty: Picking Result qty /Packed Qty per Case<BR>
 *		Result Piece qty: Picking Result qty % Packed qty per Case<BR>
 *	 	Case Location<BR>
 *		Piece Location<BR>
 *      Work Status<BR>
 *     <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/19</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.6 $, $Date: 2007/02/07 04:19:36 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderPlanWriter extends CSVWriter
{
	//#CM591639
	// Class fields --------------------------------------------------

	//#CM591640
	// Class variables -----------------------------------------------
	//#CM591641
	/**
	 * Consignor Code
	 */
	private String wConsignorCode;
	//#CM591642
	/**
	 * Start Planned Picking Date
	 */
	private String wFromRetrievalDate;

	//#CM591643
	/**
	 * End Planned Picking Date
	 */
	private String wToRetrievalDate;

	//#CM591644
	/**
	 * Customer Code
	 */
	private String wCustomerCode;

	//#CM591645
	/**
	 * Case Order No.
	 */
	private String wCaseOrderNo;

	//#CM591646
	/**
	 * Piece Order No.
	 */
	private String wPieceOrderNo;

	//#CM591647
	/**
	 * Work Status
	 */
	private String wWorkStatus;

	//#CM591648
	/**
	 * Order No.
	 */
	private String wOrderNo;

	//#CM591649
	// Class method --------------------------------------------------
	//#CM591650
	/**
	 * Return the version of this class.
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.6 $,$Date: 2007/02/07 04:19:36 $");
	}

	//#CM591651
	// Constructors --------------------------------------------------
	//#CM591652
	/**
	 * Construct the RetrievalOrderPlanWriter object.
	 * 
	 * @param conn<CODE>Connection</CODE>
	 * @param locale<CODE>Locale</CODE>
	 */
	public RetrievalOrderPlanWriter(Connection conn)
	{
		super(conn);
	}

	//#CM591653
	// Public methods ------------------------------------------------
	//#CM591654
	/**
	 * Set the Consignor code.
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		wConsignorCode = arg;
	}

	//#CM591655
	/**
	 * Obtain the Consignor code.
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM591656
	/**
	 * Set the Start Planned Picking date.
	 * @param arg Start Planned Picking Date to be set
	 */
	public void setFromRetrievalDate(String arg)
	{
		wFromRetrievalDate = arg;
	}

	//#CM591657
	/**
	 * Obtain the Start Picking Date.
	 * @return Start Planned Picking Date
	 */
	public String getFromRetrievalDate()
	{
		return wFromRetrievalDate;
	}

	//#CM591658
	/**
	 * Set the End Planned Picking date.
	 * @param arg End Planned Picking Date to be set
	 */
	public void setToRetrievalDate(String arg)
	{
		wToRetrievalDate = arg;
	}

	//#CM591659
	/**
	 * Obtain the End Planned Picking Date.
	 * @return End Planned Picking Date
	 */
	public String getToRetrievalDate()
	{
		return wToRetrievalDate;
	}

	//#CM591660
	/**
	 * Set the customer code.
	 * @param Customer Code to be set
	 */
	public void setCustomerCode(String arg)
	{
		wCustomerCode = arg;
	}

	//#CM591661
	/**
	 * Obtain the customer code.
	 * @return Customer Code
	 */
	public String getCustomerCode()
	{
		return wCustomerCode;
	}

	//#CM591662
	/**
	 * Set the Case Order No.
	 * @param Case Order No. to be set
	 */
	public void setCaseOrderNo(String caseOrderNo)
	{
		wCaseOrderNo = caseOrderNo;
	}
	//#CM591663
	/**
	 * Obtain the Case Order No.
	 * @return Case Order No.
	 */
	public String getCaseOrderNo()
	{
		return wCaseOrderNo;
	}

	//#CM591664
	/**
	 * Piece Set the Order No.
	 * @param Piece Order No. to be set
	 */
	public void setPieceOrderNo(String pieceOrderNo)
	{
		wPieceOrderNo = pieceOrderNo;
	}

	//#CM591665
	/**
	 * Obtain the Piece Order No.
	 * @return Piece Order No.
	 */
	public String getPieceOrderNo()
	{
		return wPieceOrderNo;
	}
	//#CM591666
	/**
	 * Set the Work status flag.
	 * @param workStatus Work Status flag
	 */
	public void setWorkStatus(String workStatus)
	{
		wWorkStatus = workStatus;
	}

	//#CM591667
	/**
	 * Obtain the Work Status flag.
	 * @return Work Status flag
	 */
	public String getWorkStatus()
	{
		return wWorkStatus;
	}

	//#CM591668
	/**
	 * Set the Order No.
	 * @param Order No. to be set 
	 */
	public void setOrderNo(String arg)
	{
		wOrderNo = arg;
	}

	//#CM591669
	/**
	 * Obtain the Order No.
	 * @return Order No.
	 */
	public String getOrderNo()
	{
		return wOrderNo;
	}

	//#CM591670
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
		RetrievalPlanHandler instockHandle = new RetrievalPlanHandler(wConn);
		RetrievalPlanSearchKey searchKey = new RetrievalPlanSearchKey();
		//#CM591671
		// Set the search conditions and obtain the count.
		setRetrievalPlanSearchKey(searchKey);
		return instockHandle.count(searchKey);

	}

	//#CM591672
	/**
	 * Generate a CSV file for Order Picking Plan report and execute printing. <BR>
	 * Generate data file for report if one or more result found. If zero, return false and close. <BR>
	 * Execute the process for printing. <BR>
	 * Return true if print process normally completed. <BR>
	 * Return false when error occurs in the process of printing. <BR>
	 * @return boolean Result whether the printing succeeded or not.
	 */
	public boolean startPrint()
	{
		RetrievalPlanReportFinder reportFinder = null;

		try
		{
			RetrievalPlanSearchKey searchkey = new RetrievalPlanSearchKey();
			setRetrievalPlanSearchKey(searchkey);

			//#CM591673
			// Set the sequence to obtain info.		
			searchkey.setConsignorCodeCollect("");
			searchkey.setConsignorNameCollect("");
			searchkey.setPlanDateCollect("");
			searchkey.setCustomerCodeCollect("");
			searchkey.setCustomerName1Collect("");
			searchkey.setCaseOrderNoCollect("");
			searchkey.setPieceOrderNoCollect("");
			searchkey.setItemCodeCollect("");
			searchkey.setItemName1Collect("");
			searchkey.setCasePieceFlagCollect("");
			searchkey.setEnteringQtyCollect("");
			searchkey.setBundleEnteringQtyCollect("");
			searchkey.setPlanQtyCollect("");
			searchkey.setResultQtyCollect("");
			searchkey.setCaseLocationCollect("");
			searchkey.setPieceLocationCollect("");
			searchkey.setStatusFlagCollect("");

			//#CM591674
			// Set the sorting order.		
			searchkey.setPlanDateOrder(1, true);
			searchkey.setCustomerCodeOrder(2, true);
			searchkey.setCaseOrderNoOrder(3, true);
			searchkey.setPieceOrderNoOrder(4, true);
			searchkey.setItemCodeOrder(5, true);
			searchkey.setCasePieceFlagOrder(6, true);
			searchkey.setCaseLocationOrder(7, true);
			searchkey.setPieceLocationOrder(8, true);
			//#CM591675
			// Open the cursor.
			reportFinder = new RetrievalPlanReportFinder(wConn);
			//#CM591676
			// No target data
			if (reportFinder.search(searchkey) <= 0)
			{
				//#CM591677
				// No print data found.
				wMessage = "6003010";
				reportFinder.close();
				return false;
			}

			//#CM591678
			// the latest Obtain the Consignor Name.
			String consignorName = getConsignorName();

			String customerCode = "";
			String customerName = "";

			//#CM591679
			//Generate a file for output.
			if (!createPrintWriter(FNAME_RETRIEVAL_ORDERPLAN))
			{
				return false;
			}

			RetrievalPlan retrievalPlan[] = null;
			//#CM591680
			// 	Generate a data file per 100 search results until there remains no result data.
			while (reportFinder.isNext())
			{
				//#CM591681
				// Obtain up to 100 search results.
				retrievalPlan = (RetrievalPlan[]) reportFinder.getEntities(100);
				for (int i = 0; i < retrievalPlan.length; i++)
				{

					wStrText.append(re + "");
					//#CM591682
					// Consignor Code
					wStrText.append(ReportOperation.format(retrievalPlan[i].getConsignorCode()) + tb);
					//#CM591683
					// Consignor Name
					wStrText.append(ReportOperation.format(consignorName) + tb);
					//#CM591684
					// Planned date
					wStrText.append(ReportOperation.format(retrievalPlan[i].getPlanDate()) + tb);
					//#CM591685
					// Customer Code
					wStrText.append(ReportOperation.format(retrievalPlan[i].getCustomerCode()) + tb);
					//#CM591686
					// Latest Customer Name are to be obtained.
					if (!retrievalPlan[i].getCustomerCode().equals(customerCode))
					{
						//#CM591687
						// Initialize the Customer Name.
						customerName = getCustomerName(retrievalPlan[i].getCustomerCode());
						//#CM591688
						// Set the conditions for obtaining the next Customer Name.
						customerCode = retrievalPlan[i].getCustomerCode();
					}

					//#CM591689
					// Customer Name
					wStrText.append(ReportOperation.format(customerName) + tb);
					//#CM591690
					// Case Order No.
					wStrText.append(ReportOperation.format(retrievalPlan[i].getCaseOrderNo()) + tb);
					//#CM591691
					// Piece Order No.
					wStrText.append(ReportOperation.format(retrievalPlan[i].getPieceOrderNo()) + tb);
					//#CM591692
					// Item Code
					wStrText.append(ReportOperation.format(retrievalPlan[i].getItemCode()) + tb);
					//#CM591693
					// Item Name
					wStrText.append(ReportOperation.format(retrievalPlan[i].getItemName1()) + tb);
					//#CM591694
					// Case/Piece division
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(retrievalPlan[i].getCasePieceFlag())) + tb);
					int entering_Qty = retrievalPlan[i].getEnteringQty();

					//#CM591695
					// Packed Qty per Case
					wStrText.append(ReportOperation.format(Integer.toString(entering_Qty)) + tb);
					//#CM591696
					// Packed qty per bundle
					wStrText.append(ReportOperation.format(Integer.toString(retrievalPlan[i].getBundleEnteringQty())) + tb);

					//#CM591697
					// Planned Case QTY				
					wStrText.append(
						DisplayUtil.getCaseQty(retrievalPlan[i].getPlanQty(), retrievalPlan[i].getEnteringQty(), retrievalPlan[i].getCasePieceFlag())
							+ tb);
					//#CM591698
					// Planned Piece QTY
					wStrText.append(
						DisplayUtil.getPieceQty(
							retrievalPlan[i].getPlanQty(),
							retrievalPlan[i].getEnteringQty(),
							retrievalPlan[i].getCasePieceFlag())
							+ tb);
					//#CM591699
					// Result Case QTY
					wStrText.append(
						DisplayUtil.getCaseQty(
							retrievalPlan[i].getResultQty(),
							retrievalPlan[i].getEnteringQty(),
							retrievalPlan[i].getCasePieceFlag())
							+ tb);
					//#CM591700
					// Result Piece QTY
					wStrText.append(
						DisplayUtil.getPieceQty(
							retrievalPlan[i].getResultQty(),
							retrievalPlan[i].getEnteringQty(),
							retrievalPlan[i].getCasePieceFlag())
							+ tb);
					//#CM591701
					// Case Picking Location
					wStrText.append(ReportOperation.format(retrievalPlan[i].getCaseLocation()) + tb);
					//#CM591702
					// Piece Picking Location
					wStrText.append(ReportOperation.format(retrievalPlan[i].getPieceLocation()) + tb);
					//#CM591703
					// Status
					wStrText.append(ReportOperation.format(DisplayUtil.getRetrievalPlanStatusValue(retrievalPlan[i].getStatusFlag())));
					//#CM591704
					// Writing
					wPWriter.print(wStrText);

					wStrText.setLength(0);
				}
			}
			//#CM591705
			// Close the stream.
			wPWriter.close();

			//#CM591706
			// Execute UCXSingle.
			if (!executeUCX(JOBID_RETRIEVAL_ORDERPLAN))
			{
				//#CM591707
				// Failed to print. See log. 
				return false;
			}

			//#CM591708
			// Move the data file to the backup folder.
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)
		{
			//#CM591709
			// Failed to print. See log.
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM591710
				// Execute processes for closing the cursor for the opened database.
				reportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM591711
				// Database error occurred. See log.
				setMessage("6007002");
				return false;
			}
		}
		return true;
	}

	//#CM591712
	// Package methods -----------------------------------------------

	//#CM591713
	// Protected methods ---------------------------------------------
	//#CM591714
	/**
	 * Set the search conditions.<BR>
	 * @param searchKey search key
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void setRetrievalPlanSearchKey(RetrievalPlanSearchKey searchkey) throws ReadWriteException
	{

		//#CM591715
		// Status flag other than Deleted
		searchkey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");

		//#CM591716
		// Consignor Code (mandatory)
		searchkey.setConsignorCode(wConsignorCode);

		//#CM591717
		// Start Planned Picking Date
		if (!StringUtil.isBlank(wFromRetrievalDate))
		{
			searchkey.setPlanDate(wFromRetrievalDate, ">=");
		}
		//#CM591718
		// End Planned Picking Date
		if (!StringUtil.isBlank(wToRetrievalDate))
		{
			searchkey.setPlanDate(wToRetrievalDate, "<=");
		}
		//#CM591719
		// Customer Code
		if (!StringUtil.isBlank(wCustomerCode))
		{
			searchkey.setCustomerCode(wCustomerCode);
		}
		//#CM591720
		// Item Code
		if (!StringUtil.isBlank(wOrderNo))
		{
			searchkey.setItemCode(wOrderNo);
		}
		//#CM591721
		// Work Status
		if (!StringUtil.isBlank(wWorkStatus))
		{
			if (!wWorkStatus.equals(RetrievalSupportParameter.STATUS_FLAG_ALL))
			{
				searchkey.setStatusFlag(wWorkStatus);
			}
		}
		//#CM591722
		// For data with blank Case Order and blank Piece Order:
		if (!StringUtil.isBlank(wCaseOrderNo) && !StringUtil.isBlank(wPieceOrderNo))
		{
			searchkey.setCaseOrderNo(wCaseOrderNo, "=", "(", "", "OR");
			searchkey.setPieceOrderNo(wPieceOrderNo, "=", "", ")", "AND");
		}

		//#CM591723
		// Case Order No.
		if (!StringUtil.isBlank(wCaseOrderNo) && StringUtil.isBlank(wPieceOrderNo))
		{
			searchkey.setCaseOrderNo(wCaseOrderNo);
		}

		//#CM591724
		// Piece Order No.			
		if (StringUtil.isBlank(wCaseOrderNo) && !StringUtil.isBlank(wPieceOrderNo))
		{
			searchkey.setPieceOrderNo(wPieceOrderNo);
		}

		//#CM591725
		// Setting no Case Order No. nor Piece Order No. results in attaching "IS NOT NULL".
		if (StringUtil.isBlank(wCaseOrderNo) && StringUtil.isBlank(wPieceOrderNo))
		{
			//#CM591726
			// For data at least with Case Order or Piece Order:
			searchkey.setCaseOrderNo("", "IS NOT NULL", "(", "", "OR");
			searchkey.setPieceOrderNo("", "IS NOT NULL", "", ")", "AND");
		}

	}

	//#CM591727
	/**
	 * Obtain the the latest Consignor Name.
	 * @return Consignor Name
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected String getConsignorName() throws ReadWriteException
	{
		String wConsignorName = "";
		RetrievalPlanReportFinder nameFinder = null;

		//#CM591728
		// For obtaining the latest Consignor Name
		RetrievalPlanSearchKey namekey = new RetrievalPlanSearchKey();
		setRetrievalPlanSearchKey(namekey);

		namekey.setConsignorNameCollect("");
		namekey.setRegistDateOrder(1, false);

		nameFinder = new RetrievalPlanReportFinder(wConn);
		nameFinder.open();
		if (nameFinder.search(namekey) > 0)
		{
			RetrievalPlan retPlan[] = (RetrievalPlan[]) nameFinder.getEntities(1);

			if (retPlan != null && retPlan.length != 0)
			{
				wConsignorName = retPlan[0].getConsignorName();
			}
		}
		nameFinder.close();

		return wConsignorName;
	}

	//#CM591729
	/**
	 * Obtain the the latest Customer Name.
	 * @param customerCode Customer Code
	 * @return Customer Name
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected String getCustomerName(String customerCode) throws ReadWriteException
	{
		String customerName = "";
		RetrievalPlanReportFinder nameFinder = null;

		//#CM591730
		// For obtaining the latest Consignor Name
		RetrievalPlanSearchKey namekey = new RetrievalPlanSearchKey();
		setRetrievalPlanSearchKey(namekey);

		namekey.setCustomerCode(customerCode);
		namekey.setCustomerName1Collect("");
		namekey.setRegistDateOrder(1, false);
		//#CM591731
		// Start Search
		nameFinder = new RetrievalPlanReportFinder(wConn);
		nameFinder.open();

		if (nameFinder.search(namekey) > 0)
		{
			RetrievalPlan retPlan[] = (RetrievalPlan[]) nameFinder.getEntities(1);
			if (retPlan != null && retPlan.length != 0)
			{
				customerName = retPlan[0].getCustomerName1();
			}
		}
		nameFinder.close();
		return customerName;

	}

	//#CM591732
	// Private methods -----------------------------------------------

}
//#CM591733
//end of class
