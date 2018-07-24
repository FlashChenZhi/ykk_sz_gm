// $Id: RetrievalOrderWriter.java,v 1.6 2007/02/07 04:19:37 suresh Exp $
package jp.co.daifuku.wms.retrieval.report;

//#CM591886
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Vector;

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

//#CM591887
/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida <BR>
 * <BR>
 * Allow this class to generate a data file for Order Picking Plan report and invoke the class for printing.<BR>
 * Search through the Work Status using the content set in the shchedule class, and generte a data file for the report.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * Execute the process for generating a data file for report. (<CODE>startPrint()</CODE>method)<BR>
 * <DIR>
 *   Search for the Work status.<BR>
 *   Disable to generate data file for report if no corresponding data exists.<BR>
 *   Generate a data file for report if corresponding data exists, and invoke the class for executing printing.<BR>
 * <BR>
 *   <Search conditions> <DIR>
 *     Work division: Shipping <BR>
 *     Start Work flag: "Started" (1) <BR>
 *     Work No. (vector)  <BR>
 *     Case/Piece division <BR>
 *     Work Status<BR></DIR>
 * <BR>
 *   <Field items to be extracted> <DIR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     Planned Picking Date <BR>
 *     Order No. <BR>
 *     Customer Code <BR>
 *     Customer Name <BR>
 *     Picking Location <BR>
 *     Item Code <BR>
 *     Item Name <BR>
 *     Case/Piece division <BR>
 *     Packed Qty per Case <BR>
 *     Packed qty per bundle <BR>
 *     Total Possible Picking qty <BR>
 *     Planned Work Case Qty <BR>
 *     Planned Work Piece Qty <BR>
 *     Expiry Date<BR>
 *     Case ITF<BR>
 *     Bundle ITF<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>S.Yoshida</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.6 $, $Date: 2007/02/07 04:19:37 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderWriter extends CSVWriter
{
	//#CM591888
	// Class fields --------------------------------------------------

	//#CM591889
	// Class variables -----------------------------------------------
	//#CM591890
	/**
	 * Work No. (vector) 
	 */
	private Vector wJobNo;

	//#CM591891
	/**
	 * Case/Piece division
	 */
	protected String wCasePieceFlag;

	//#CM591892
	/**
	 * Work Status
	 */
	private String wWorkStatus;
	
	//#CM591893
	// Class method --------------------------------------------------
	//#CM591894
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.6 $,$Date: 2007/02/07 04:19:37 $");
	}

	//#CM591895
	// Constructors --------------------------------------------------
	//#CM591896
	/**
	 * Construct the RetrievalOrderStartWriter object.<BR>
	 * Set the connection and the locale.<BR>
	 * @param conn <CODE>Connection</CODE> Database connection object
	 */
	public RetrievalOrderWriter(Connection conn)
	{
		super(conn);
	}

	//#CM591897
	// Public methods ------------------------------------------------
	//#CM591898
	/**
	 * Set a value to be searched in the Work No.
	 * @param pJobNo Work No. to be set (vector) 
	 */
	public void setJobNo(Vector pJobNo)
	{
		wJobNo = pJobNo;
	}

	//#CM591899
	/**
	 * Obtain the Work No.
	 * @return Work No. (vector) 
	 */
	public Vector getJobNo()
	{
		return wJobNo;
	}

	//#CM591900
	/**
	 * Set the search value for Case/Piece division.
	 * @param pCasePieceFlag Case/Piece division to be set
	 */
	public void setCasePieceFlag(String pCasePieceFlag)
	{
		wCasePieceFlag = pCasePieceFlag;
	}

	//#CM591901
	/**
	 * Obtain the Case/Piece division.
	 * @return Case/Piece division
	 */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}
	
	//#CM591902
	/**
	 * Obtain the work status.
	 * @return Work Status
	 */
	public String getWorkStatus()
	{
		return wWorkStatus;
	}
	
	//#CM591903
	/**
	 * Set the work status.
	 * @param Work Status to be set
	 */
	public void setWorkStatus(String workStatus)
	{
		wWorkStatus = workStatus;
	}

	//#CM591904
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
		WorkingInformationHandler instockHandle = new WorkingInformationHandler(wConn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		//#CM591905
		// Set the search conditions and obtain the count.
		setWorkInfoSearchKey(searchKey);
		return instockHandle.count(searchKey);

	}
	
	//#CM591906
	/**
	 * Generate a CSV file for Order Picking Report and invoke the class for printing.<BR>
	 * Search for the Work status using the search conditions input via screen.
	 * Disable to print if the search result count is less than 1 (one).<BR>
	 * Return true if succeeded in printing. Return false if failed.<BR>
	 * 
	 * @return boolean Result whether the printing succeeded or not.
	 */
	public boolean startPrint()
	{
		
		//#CM591907
		// Generate instance of Work Status Finders.
		WorkingInformationReportFinder wObj = new WorkingInformationReportFinder(wConn);		
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation[] wWorkinfo = null;

		try
		{
			setWorkInfoSearchKey(wKey);
			//#CM591908
			// Set the Display order.
			wKey.setConsignorCodeOrder(1, true);
			wKey.setPlanDateOrder(2, true);
			wKey.setCustomerCodeOrder(3, true);
			wKey.setOrderNoOrder(4, true);
			wKey.setLocationNoOrder(5, true);
			wKey.setWorkFormFlagOrder(6, true);
			wKey.setItemCodeOrder(7, true);
			wKey.setResultUseByDateOrder(8, true);	
			wKey.setUseByDateOrder(9, true);
			

			//#CM591909
			// Execute searching.
			//#CM591910
			// If no data exists, disable to execute print process.
			if (wObj.search(wKey) <= 0)
			{
				//#CM591911
				// 6003010=No print data found.
				wMessage = "6003010";
				return false;
			}

			//#CM591912
			//Generate a file to be output.
			if(!createPrintWriter(FNAME_RETRIEVAL_ORDERWORK))
			{
				return false;
			}
			//#CM591913
			// Variable for obtaining a name
			String wConsignorName = "";
			String wCustomerName = "";
			
			//#CM591914
			// Generate contents of data files until there is no longer the search result.
			while (wObj.isNext())
			{
				//#CM591915
				// Output every 100 search results.
				wWorkinfo = (WorkingInformation[]) wObj.getEntities(100);

				//#CM591916
				// Generate the contents to be output to the file.
				for (int i = 0; i < wWorkinfo.length; i++)
				{
					wStrText.append(re + "");

                    //#CM591917
                    // Case/Piece division (input via screen)
					if (wCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
					{
						wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_CASE)) + tb);
					}
					else if (wCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
					{
						wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_PIECE)) + tb);
					}
					else if (wCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
					{
						wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_NOTHING)) + tb);
					}
					else
					{
						//#CM591918
						// RDB-W0007=All
						wStrText.append(ReportOperation.format(DisplayText.getText("RDB-W0007")) + tb);
					}
                    
					//#CM591919
					// Consignor Code
					wStrText.append(ReportOperation.format(wWorkinfo[i].getConsignorCode()) + tb);
					//#CM591920
					// Consignor Name
					if (i == 0 || 
					    (i > 0 && !wWorkinfo[i - 1].getConsignorCode().equals(wWorkinfo[i].getConsignorCode())))
					{
						//#CM591921
						// Obtain the Consignor Name.
						wConsignorName = getConsignorName(wWorkinfo[i].getConsignorCode());
					}
					wStrText.append(ReportOperation.format(wConsignorName) + tb);
					//#CM591922
					// Planned Picking Date
					wStrText.append(ReportOperation.format(wWorkinfo[i].getPlanDate()) + tb);
					//#CM591923
					// Customer Code
					wStrText.append(ReportOperation.format(wWorkinfo[i].getCustomerCode()) + tb);
					//#CM591924
					// Customer Name
					if (i == 0 || 
					    (i > 0 && !wWorkinfo[i - 1].getCustomerCode().equals(wWorkinfo[i].getCustomerCode())))
					{
						//#CM591925
						// Obtain the Customer Name.
						wCustomerName = getCustomerName(wWorkinfo[i].getCustomerCode());
					}
					wStrText.append(ReportOperation.format(wCustomerName) + tb);
					//#CM591926
					// Order No.
					wStrText.append(ReportOperation.format(wWorkinfo[i].getOrderNo()) + tb);
					//#CM591927
					// Picking Location
					wStrText.append(ReportOperation.format(wWorkinfo[i].getLocationNo()) + tb);
					//#CM591928
					// Item Code
					wStrText.append(ReportOperation.format(wWorkinfo[i].getItemCode()) + tb);
					//#CM591929
					// Item Name
					wStrText.append(ReportOperation.format(wWorkinfo[i].getItemName1()) + tb);
					//#CM591930
					// Case/Piece division
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(wWorkinfo[i].getWorkFormFlag())) + tb);
					//#CM591931
					// Total Workable qty
					wStrText.append(wWorkinfo[i].getPlanEnableQty() + tb);
					//#CM591932
					// Packed Qty per Case
					wStrText.append(wWorkinfo[i].getEnteringQty() + tb);
					//#CM591933
					// Packed qty per bundle
					wStrText.append(wWorkinfo[i].getBundleEnteringQty() + tb);
					//#CM591934
					// Planned Work Case Qty
					wStrText.append(DisplayUtil.getCaseQty(wWorkinfo[i].getPlanEnableQty(), wWorkinfo[i].getEnteringQty(),wWorkinfo[i].getWorkFormFlag()) + tb);
					//#CM591935
					// Planned Work Piece Qty
					wStrText.append(DisplayUtil.getPieceQty(wWorkinfo[i].getPlanEnableQty(), wWorkinfo[i].getEnteringQty(),wWorkinfo[i].getWorkFormFlag()) + tb);
					
					//#CM591936
					// Expiry Date
					if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
					{
						wStrText.append(ReportOperation.format(wWorkinfo[i].getResultUseByDate()) + tb);
					}
					else
					{
						wStrText.append(ReportOperation.format(wWorkinfo[i].getUseByDate()) + tb);
					}
					
					//#CM591937
					// Case ITF
					wStrText.append(ReportOperation.format(wWorkinfo[i].getItf()) + tb);
					//#CM591938
					// Bundle ITF
					wStrText.append(ReportOperation.format(wWorkinfo[i].getBundleItf()));
					
					//#CM591939
					// Output the data to the file.
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}

			//#CM591940
			// Close the stream.
			wPWriter.close();

			//#CM591941
			// Execute UCXSingle (Execute printing).
			if (!executeUCX(JOBID_RETRIEVAL_ORDERWORK))
			{
				//#CM591942
				// Failed to print. See log.
				return false;
			}

			//#CM591943
			// If succeeded in printing, move the data file to the backup folder.
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException re)
		{
			//#CM591944
			// 6007034=Printing failed. See log.
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM591945
				// Execute processes for closing the cursor for the opened database.
				wObj.close();
			}
			catch (ReadWriteException e)
			{
				//#CM591946
				// Database error occurred. See log.
				setMessage("6007002");
				return false;			
			}
			
		}

		return true;
	}

	//#CM591947
	// Package methods -----------------------------------------------

	//#CM591948
	// Protected methods ---------------------------------------------
	//#CM591949
	/**
	 * Allow this method to obtain the Consignor name.<BR><BR>
	 * Some data may have two or more Consignor Names for Consignor Code.
	 *  Therefore, return the Customer Name with the latest record of the Added Date/Time.<BR>
	 * @param wConsignorCode Consignor Code
	 * @return Consignor Name
	 * @throws ReadWriteException Announce in the event of error in accessing to the database.
	 */
	protected String getConsignorName(String wConsignorCode) throws ReadWriteException
	{
		
		String wConsignorName = "";

		//#CM591950
		// Generate instance of Work Status Finders.
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationReportFinder wObj = new WorkingInformationReportFinder(wConn);
		WorkingInformation[] wWorkinfo = null;

		//#CM591951
		// Set the search conditions.
		setWorkInfoSearchKey(wKey);
		wKey.setConsignorCode(wConsignorCode);
		wKey.setConsignorNameCollect("");
		//#CM591952
		// Set the Order sequence.
		wKey.setRegistDateOrder(1, false);
		
		//#CM591953
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

	//#CM591954
	/**
	 * Allow this method to obtain the Customer Name.<BR><BR>
	 * Some data may have two or more Customer Names for Customer Code.
	 *  Therefore, return the Customer Name with the latest record of the Added Date/Time.<BR>
	 * @param wCustomerCode Customer Code
	 * @return Customer Name
	 * @throws ReadWriteException Announce in the event of error in accessing to the database.
	 */
	protected String getCustomerName(String wCustomerCode) throws ReadWriteException
	{
		String wCustomerName = "";

		//#CM591955
		// Generate instance of Work Status Finders.
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationReportFinder wObj = new WorkingInformationReportFinder(wConn);
		WorkingInformation[] wWorkinfo = null;

		//#CM591956
		// Set the search conditions.
		setWorkInfoSearchKey(wKey);
		wKey.setCustomerCode(wCustomerCode);
		wKey.setCustomerName1Collect("");
		//#CM591957
		// Set the Order sequence.
		wKey.setRegistDateOrder(1, false);
		
		//#CM591958
		// Execute the search.
		wObj.open();
		
		if (wObj.search(wKey) > 0)
		{
			wWorkinfo = (WorkingInformation[]) ((WorkingInformationReportFinder) wObj).getEntities(1);

			if (wWorkinfo != null && wWorkinfo.length != 0)
			{
				wCustomerName = wWorkinfo[0].getCustomerName1();
			}
		}
		wObj.close();

		return wCustomerName;
	}
	//#CM591959
	// Private methods -----------------------------------------------
	//#CM591960
	/**
	 * Allow this method to set the search conditions for the Work Status search key.<BR>
	 * @param wKey WorkingInformationSearchKey Search key for Work Status
	 * @return Search key for Work Status
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected WorkingInformationSearchKey setWorkInfoSearchKey(WorkingInformationSearchKey wKey) throws ReadWriteException
	{
		wKey.KeyClear();
		//#CM591961
		// Set the search conditions.
		//#CM591962
		// Work division = Picking(3)
		wKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM591963
		// Start Work flag = "Started" (1)
		wKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		//#CM591964
		// Work No. (Array) 
		String[] wJobNoArray = new String[wJobNo.size()];
		wJobNo.copyInto(wJobNoArray);
		wKey.setJobNo(wJobNoArray);
		//#CM591965
		// Case/Piece division
		if (!StringUtil.isBlank(wCasePieceFlag) && !wCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
		{
			if (wCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			else if (wCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			}
			else if (wCasePieceFlag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			}
		}
		//#CM591966
		// Work Status		
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
					WorkingInformation.STATUS_FLAG_COMPLETION
				};
				wKey.setStatusFlag(status);
			}
		}
		//#CM591967
		// Exclude ASRS Work.
	    wKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");

		return wKey;
	}
	

}
//#CM591968
//end of class
