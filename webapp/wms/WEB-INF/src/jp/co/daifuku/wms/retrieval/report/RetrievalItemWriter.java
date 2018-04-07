// $Id: RetrievalItemWriter.java,v 1.6 2007/02/07 04:19:36 suresh Exp $
package jp.co.daifuku.wms.retrieval.report;
//#CM591558
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM591559
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * Allow this class to generate a data file for Item Picking report and print it. <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1.Process for printing and compiling(<CODE>startPrint()</CODE>method) <BR>
 * <DIR>
 *   Generate a CSV file to be used in the Print process. <BR>
 * <BR>
 *   <DIR>
 *   <Parameter> <BR>
 *     <DIR>
 *     Work No. <BR>
 *     Work division: "Picking" <BR>
 *     </DIR>
 *   <DIR>
 *   Consignor Code<BR>
 *   Planned Picking Date<BR>
 *   Item Code<BR>
 *   Case/Piece division<BR><BR>
 *   <DIR>
 *     All<BR>
 *     Case<BR>
 *     Piece<BR>
 *     "None"<BR>
 *   </DIR>
 *  <BR>
 *  Work Status<BR><BR>
 *  <DIR>
 * 	All<BR>
 *  Standby<BR>
 *  Start<BR>
 *  Processing<BR>
 *  Completed<BR>
 * 
 *  </DIR>
 *   </DIR>
 *   <BR>
 *   <Returned data> <BR>
 *     <DIR>
 *     Printing status <BR>
 *     </DIR>
 *   </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/25</TD><TD>K.Toda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.6 $, $Date: 2007/02/07 04:19:36 $
 * @author  $Author: suresh $
 */
public class RetrievalItemWriter extends CSVWriter
{
	//#CM591560
	// Class fields --------------------------------------------------

	//#CM591561
	// Class variables -----------------------------------------------
	//#CM591562
	/**
	 * Work No. (Array) 
	 */
	private String[] wJobNo;

	//#CM591563
	/**
	 * Case/Piece division
	 */
	private String wCasePieceFlag;

	//#CM591564
	/**
	 * Consignor Code
	 */
	private String wConsignorCode;
	
	//#CM591565
	/**
	 * Planned Picking Date
	 */
	private String wRetrievalPlanDate;
	
	//#CM591566
	/**
	 * Item Code
	 */
	private String wItemCode;
	
	//#CM591567
	/**
	 * Work Status
	 */
	private String wWorkStatus;
	
	//#CM591568
	// Class method --------------------------------------------------
	//#CM591569
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.6 $,$Date: 2007/02/07 04:19:36 $");
	}

	//#CM591570
	// Constructors --------------------------------------------------
	//#CM591571
	/**
	 * Construct the RetrievalItemWriter object.
	 * @param conn <CODE>Connection</CODE> Database connection object
	 */
	public RetrievalItemWriter(Connection conn)
	{
		super(conn);
	}

	//#CM591572
	// Public methods ------------------------------------------------
	//#CM591573
	/**
	 * Set a value in the Work No. (Array).
	 * @param jobNo Work No. (Array) to be set
	 */
	public void setJobNo(String[] jobNo)
	{
		wJobNo = jobNo;
	}

	//#CM591574
	/**
	 * Obtain the Work No. (Array).
	 * @return Work No. (Array) 
	 */
	public String[] getJobNo()
	{
		return wJobNo;
	}

	//#CM591575
	/**
	 * Set a value in the Case/Piece division.
	 * @param jobNo Case/Piece division to be set
	 */
	public void setCasePieceFlag(String casePieceFlag)
	{
		wCasePieceFlag = casePieceFlag;
	}

	//#CM591576
	/**
	 * Case Obtain the Piece division.
	 * @return Case/Piece division
	 */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}

    //#CM591577
    /**
     * Obtain the Consignor code.
     * @return Consignor Code
     */
    public String getConsignorCode()
    {
        return wConsignorCode;
    }
    
    //#CM591578
    /**
     * Set the Consignor code.
     * @param Consignor Code to be set
     */
    public void setConsignorCode(String consignorCode)
    {
        wConsignorCode = consignorCode;
    }
    
    //#CM591579
    /**
     * Obtain the item code.
     * @return Item Code
     */
    public String getItemCode()
    {
        return wItemCode;
    }
    
    //#CM591580
    /**
     * Set the Item Code.
     * @param Item Code to be set
     */
    public void setItemCode(String itemCode)
    {
        wItemCode = itemCode;
    }
    
    //#CM591581
    /**
     * Obtain the Planned Picking Date.
     * @return Planned Picking Date
     */
    public String getRetrievalPlanDate()
    {
        return wRetrievalPlanDate;
    }
    
    //#CM591582
    /**
     * Set the Planned Picking date.
     * @param Planned Picking Date to be set
     */
    public void setRetrievalPlanDate(String retrievalPlanDate)
    {
        wRetrievalPlanDate = retrievalPlanDate;
    }
    
    //#CM591583
    /**
     * Obtain the work status.
     * @return Work Status
     */
    public String getWorkStatus()
    {
        return wWorkStatus;
    }
    
    //#CM591584
    /**
     * Set the work status.
     * @param Work Status to be set
     */
    public void setWorkStatus(String workStatus)
    {
        wWorkStatus = workStatus;
    }
    
	//#CM591585
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
		WorkingInformationHandler wih = new WorkingInformationHandler(wConn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		//#CM591586
		// Set the search conditions and obtain the count.
		setWorkInfoSearchKey(searchKey);
		return wih.count(searchKey);

	}
	
	//#CM591587
	/**
	 * Generate a CSV file for Item Picking Report and invoke the class for printing. <BR>
	 * Search through the Picking Plan Info <CODE>(Dnretrievalplan)</CODE> using the search conditions passed as a parameter. <BR>
	 * Disable to print if the search result count is less than 1 (one). <BR>
	 * Return true if succeeded in printing. Return false if failed. <BR>
	 * <BR>
	 * @return boolean Result whether the printing succeeded or not.
	 */
	public boolean startPrint()
	{
		WorkingInformationReportFinder winfoFinder = new WorkingInformationReportFinder(wConn);
		try
		{
			WorkingInformationSearchKey winfoKey = new WorkingInformationSearchKey();

			//#CM591588
			// Set the search conditions.
			setWorkInfoSearchKey(winfoKey);
			//#CM591589
			// Printing order
			winfoKey.setPlanDateOrder(1, true);
			winfoKey.setLocationNoOrder(2, true);
			winfoKey.setItemCodeOrder(3, true);
			winfoKey.setWorkFormFlagOrder(4, true);
            winfoKey.setResultUseByDateOrder(5, true);	
            winfoKey.setUseByDateOrder(6, true);
			//#CM591590
			// Execute searching.
			//#CM591591
			// If no data exists, disable to execute print process.
			if (winfoFinder.search(winfoKey) <= 0)
			{
				//#CM591592
				// 6003010=No print data found.
				wMessage = "6003010";
				return false;
			}

			//#CM591593
			//Generate a file for output.
			if(!createPrintWriter(FNAME_RETRIEVAL_ITEMWORK))
			{
				return false;
			}
			
			//#CM591594
			// Variable for obtaining a name
			String wConsignorName = "";

			//#CM591595
			// Generate a data file per 100 search results until there remains no result data.
			WorkingInformation[] workInfo = null;
			while (winfoFinder.isNext())
			{
				//#CM591596
				// Obtain up to 100 search results.
				workInfo = (WorkingInformation[]) winfoFinder.getEntities(100);

				for (int i = 0; i < workInfo.length; i++)
				{
					wStrText.append(re + "");

					//#CM591597
					// Case/Piece division (input via screen)
					if (wCasePieceFlag.equals(SystemDefine.CASEPIECE_FLAG_CASE))
					{
						wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_CASE)) + tb);
					}
					else if (wCasePieceFlag.equals(SystemDefine.CASEPIECE_FLAG_PIECE))
					{
						wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_PIECE)) + tb);
					}
					else if (wCasePieceFlag.equals(SystemDefine.CASEPIECE_FLAG_NOTHING))
					{
						wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_NOTHING)) + tb);
					}
					else
					{
						//#CM591598
						// RDB-W0007=All
						wStrText.append(ReportOperation.format(DisplayText.getText("RDB-W0007")) + tb);
					}
                    
                    
					//#CM591599
					// Consignor Code
					wStrText.append(ReportOperation.format(workInfo[i].getConsignorCode()) + tb);
					//#CM591600
					// Consignor Name
					if (i == 0 || (i > 0 && !workInfo[i - 1].getConsignorCode().equals(workInfo[i].getConsignorCode())))
					{
						//#CM591601
						// Obtain the Consignor Name.
						wConsignorName = getConsignorName(workInfo[i].getConsignorCode());
					}
					wStrText.append(ReportOperation.format(wConsignorName) + tb);
					//#CM591602
					// Planned Picking Date
					wStrText.append(ReportOperation.format(workInfo[i].getPlanDate()) + tb);
					//#CM591603
					// Picking Location
					wStrText.append(ReportOperation.format(workInfo[i].getLocationNo()) + tb);
					//#CM591604
					// Item Code
					wStrText.append(ReportOperation.format(workInfo[i].getItemCode()) + tb);
					//#CM591605
					// Item Name
					wStrText.append(ReportOperation.format(workInfo[i].getItemName1()) + tb);
					//#CM591606
					// Case/Piece division
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(workInfo[i].getWorkFormFlag())) + tb);
					//#CM591607
					// Planned total qty
					wStrText.append(workInfo[i].getPlanEnableQty() + tb);
					//#CM591608
					// Packed Qty per Case
					wStrText.append(workInfo[i].getEnteringQty() + tb);
					//#CM591609
					// Packed qty per bundle
					wStrText.append(workInfo[i].getBundleEnteringQty() + tb);
					//#CM591610
					// Planned Work Case Qty
					wStrText.append(DisplayUtil.getCaseQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty(),workInfo[i].getWorkFormFlag()) + tb);
					//#CM591611
					// Planned Work Piece Qty
					wStrText.append(DisplayUtil.getPieceQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty(),workInfo[i].getWorkFormFlag()) + tb);
					
					//#CM591612
					// Expiry Date
					wStrText.append(ReportOperation.format(workInfo[i].getUseByDate()) + tb);
					//#CM591613
					// Case ITF
					wStrText.append(ReportOperation.format(workInfo[i].getItf()) + tb);
					//#CM591614
					// Bundle ITF
					wStrText.append(ReportOperation.format(workInfo[i].getBundleItf()));

					//#CM591615
					// Writing
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}

			//#CM591616
			// Close the stream.
			wPWriter.close();

			//#CM591617
			// Execute UCXSingle.
			if (!executeUCX(JOBID_RETRIEVAL_ITEMWORK))
			{
				//#CM591618
				// Failed to print. See log.
				return false;
			}

			//#CM591619
			// Move the data file to the backup folder.
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)
		{
			//#CM591620
			// Failed to print. See log.
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM591621
				// Execute processes for closing the cursor for the opened database.
				winfoFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM591622
				// Database error occurred. See log.
				setMessage("6007002");
				return false;
			}
		}

		return true;

	}

	//#CM591623
	// Package methods -----------------------------------------------

	//#CM591624
	// Protected methods ---------------------------------------------

	//#CM591625
	// Private methods -----------------------------------------------
	//#CM591626
	/**
	 * Allow this method to set the search conditions for the Work Status search key.<BR>
	 * @param wKey WorkingInformationSearchKey Search key for Work Status
	 * @return Search key for Work Status
	 * @throws ReadWriteException Announce in the event of error in accessing to the database.
	 */
	private void setWorkInfoSearchKey(WorkingInformationSearchKey wKey) throws ReadWriteException
	{
		//#CM591627
		// Set the search conditions.
		wKey.KeyClear();
		wKey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL) ;
		if(wJobNo != null)
		{
	   		//#CM591628
	   		// Work No.
	    	wKey.setJobNo(wJobNo);
		}
		else
		{
		    if(!StringUtil.isBlank(wConsignorCode))
		    {
		        wKey.setConsignorCode(wConsignorCode) ;
		    }
		    if(!StringUtil.isBlank(wRetrievalPlanDate))
		    {
		        wKey.setPlanDate(wRetrievalPlanDate) ;
		    }
		    if(!StringUtil.isBlank(wItemCode))
		    {
		        wKey.setItemCode(wItemCode) ;
		    }
		    if(!StringUtil.isBlank(wCasePieceFlag))
		    {
		    	if(!wCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
		    	{
		        	wKey.setWorkFormFlag(wCasePieceFlag) ;
		    	}
		    }
		    if(!StringUtil.isBlank(wWorkStatus))
		    {
				if(wWorkStatus.equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
				{
					wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART) ;
				}
				else if(wWorkStatus.equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
				{
					wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START) ;
				}
				else if(wWorkStatus.equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
				{
					wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING) ;
				}
				else if(wWorkStatus.equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART) ;
				}
				else if(wWorkStatus.equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
				{
					wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION) ;
				}
				else
				{
					String[] status = 
					{
						WorkingInformation.STATUS_FLAG_UNSTART,
						WorkingInformation.STATUS_FLAG_START,
						WorkingInformation.STATUS_FLAG_NOWWORKING,
						WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART,
						WorkingInformation.STATUS_FLAG_COMPLETION
					};
 					wKey.setStatusFlag(status) ;
				}
		    }

			//#CM591629
			// Order No.
			wKey.setOrderNo("", "IS NULL");
		}
		//#CM591630
		// Exclude ASRS Work.
	    wKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
		//#CM591631
		// Start Work flag = "Started" (1)
		wKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
	}

	//#CM591632
	/**
	 * Allow this method to obtain the Consignor name.<BR><BR>
	 * Some data may have two or more Consignor Names for Consignor Code.
	 *  Therefore, return the Customer Name with the latest record of the Added Date/Time.<BR>
	 * @param wConsignorCode Consignor Code
	 * @return Consignor Name
	 * @throws ReadWriteException Announce in the event of error in accessing to the database.
	 */
	private String getConsignorName(String wConsignorCode) throws ReadWriteException
	{
		String wConsignorName = "";

		//#CM591633
		// Generate instance of Work Status Finders.
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationReportFinder wObj = new WorkingInformationReportFinder(wConn);
		WorkingInformation[] wWorkinfo = null;

		//#CM591634
		// Set the search conditions.
		setWorkInfoSearchKey(wKey);
		wKey.setConsignorCode(wConsignorCode);
		wKey.setConsignorNameCollect("");
		wKey.setRegistDateOrder(1, false);

		//#CM591635
		// Execute the search.
		wObj.open();
		
		if (wObj.search(wKey) > 0)
		{
			wWorkinfo = (WorkingInformation[]) ((WorkingInformationReportFinder) wObj).getEntities(1);

			if (wWorkinfo != null && wWorkinfo.length != 0)
			{
				wConsignorName = wWorkinfo[0].getConsignorName();
			}
		}
		wObj.close();

		return wConsignorName;
	}

}
//#CM591636
//end of class
