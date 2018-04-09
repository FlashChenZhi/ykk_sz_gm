// $Id: StartInventoryCheckWriter.java,v 1.5 2006/12/13 09:03:17 suresh Exp $

//#CM43676
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.report;

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.dbhandler.AsWorkingInformationReportFinder;
import jp.co.daifuku.wms.asrs.entity.AsWorkingInformation;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;

//#CM43677
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * < CODE>StartInventoryCheckWriter</CODE > class makes the data file for the slit of the stock confirmation setting list, and executes the print.  <BR>
 * Retrieve work information (DNWORKINFO) based on the condition set in < CODE>StartInventoryCheckSCH</CODE > class.
 * Make the result as a slit file for the stock confirmation setting list.  <BR>
 * This class processes it as follows. <BR>
 * <BR>
 * Data file making processing for slit(< CODE>startPrint() </ CODE > method) <BR>
 * <DIR>
 *	1.Retrieve the number of cases of work information (DNWORKINFO) on the condition set from < CODE>StartInventoryCheckSCH</CODE > class. <BR>
 *	2.Make the slit file if the result is one or more. Return false and end in case of 0. <BR>
 *	3.Execute the printing job. <BR>
 *	4.Return true when the printing job is normal.  <BR>
 *    Return false when the error occurs in the printing job.  <BR>
 * <BR>
 * 	<Parameter> * Mandatory Input <BR><DIR>
 * 	  Batch No. * <BR></DIR>
 * <BR>
 * 	<Search condition> <BR><DIR>
 *    Batch No. <BR></DIR>
 * <BR>
 *  <retrieval order> <BR><DIR>
 *    1.Ascending order of Work result Location <BR>
 *    2.Ascending order of Item Code <BR>
 *    3.Ascending order of Case/Piece flag <BR></DIR>
 * <BR>
 *	<Print data> <BR><DIR>
 *	  Print entry: DB item<BR>
 *	  Consignor			:Consignor Code + Consignor Name <BR>
 *	  Item Code	:Item Code <BR>
 *	  Item Name		:Item Name <BR>
 *    Flag			:Case/Piece flag <BR>
 *    Retrieval Shelf		:Work resultLocation <BR>
 *    Qty per case	:Qty per case <BR>
 *    Qty per bundle	:Qty per bundle <BR>
 *    Retrieval Case qty	:Actual work qty /Qty per case <BR>
 *    Retrieval Piece qty	:Actual work qty %Qty per case <BR>
 *    Case ITF	:Case ITF <BR>
 *    Bundle ITF	:Bundle ITF <BR></DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/07</TD><TD>K.Toda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 09:03:17 $
 * @author  $Author: suresh $
 */
public class StartInventoryCheckWriter extends CSVWriter
{
	//#CM43678
	/**
	 * Batch No.
	 */
	private String wBatchNo;

	//#CM43679
	/**
	 * Station No.
	 */
	private String wStationNo;

	//#CM43680
	// Class method --------------------------------------------------
	//#CM43681
	/**
	 * Return the version of this class. 
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 09:03:17 $");
	}

	//#CM43682
	// Constructors --------------------------------------------------
	//#CM43683
	/**
	 * Construct the StartInventoryCheckWriter object. <BR>
	 * Set the connection. <BR>
	 * 
	 * @param conn <CODE>Connection</CODE> Connection object with database
	 */
	public StartInventoryCheckWriter(Connection conn)
	{
		super(conn);
	}

	//#CM43684
	// Public methods ------------------------------------------------
	//#CM43685
	/**
	 * Acquire Batch No..
	 * @return Batch No.
	 */
	public String getBatchNumber()
	{
		return wBatchNo;
	}
	
	//#CM43686
	/**
	 * Set Batch No..
	 * @param batchNumber Batch No to be set
	 */
	public void setBatchNumber(String batchNumber)
	{
		wBatchNo = batchNumber;
	}

	//#CM43687
	/**
	 * Acquire Station No. 
	 * @return Station No.
	 */
	public String getStationNumber()
	{
		return wStationNo;
	}

	//#CM43688
	/**
	 * Station No is set.
	 * @param stationNumber Station No to be set.
	 */
	public void setStationNumber(String stationNumber)
	{
		wStationNo = stationNumber;
	}

	//#CM43689
	/**
	 * Make the CSV file for the ASRS schedule going out warehouse work list, and call the print execution class. <BR>
	 * Retrieve work information on the search condition input on the screen. <BR>
	 * Do not print when the retrieval result is less than one. <BR>
	 * @return True when the printing job ends normally and False when failing.
	 */
	public boolean startPrint()
	{
		AsWorkingInformationReportFinder reportFinder = new AsWorkingInformationReportFinder(wConn);
		
		try
		{
			//#CM43690
			// Return the error when Batch  No. does not exist. 
			if(wBatchNo==null)
			{
				wMessage = "6007034";
				return false;
			}
			
			//#CM43691
			// Object generation
			WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
			CarryInformationSearchKey carryInfoSearchKey = new CarryInformationSearchKey();
			StockSearchKey stockSearchKey = new StockSearchKey();

			SearchKey[] _listSearchKey = new SearchKey[3];
			_listSearchKey[0] = searchKey;
			_listSearchKey[1] = carryInfoSearchKey;
			_listSearchKey[2] = stockSearchKey;
			
			//#CM43692
			// Batch  No. is set and work is set. 
			searchKey.setBatchNo(wBatchNo);
			searchKey.setJobType(SystemDefine.JOB_TYPE_ASRS_INVENTORY_CHECK);
	
			//#CM43693
			// The order of the information acquisition is set. 
			searchKey.setAreaNoCollect("");
			searchKey.setConsignorCodeCollect("");
			searchKey.setConsignorNameCollect("");
			searchKey.setItemCodeCollect("");
			searchKey.setItemName1Collect("");
			searchKey.setCasePieceFlagCollect("");
			carryInfoSearchKey.setWorkNumberCollect("");
			carryInfoSearchKey.setDestStationNumberCollect("");
			searchKey.setLocationNoCollect("");
			searchKey.setEnteringQtyCollect("");
			searchKey.setBundleEnteringQtyCollect("");
			searchKey.setBundleItfCollect("");
			searchKey.setItfCollect("");
			searchKey.setUseByDateCollect("");
			stockSearchKey.setStockQtyCollect("");
				
			//#CM43694
			// The order of sorting is set. 
			carryInfoSearchKey.setDestStationNumberOrder(1, true);
			carryInfoSearchKey.setWorkNumberOrder(2, true);
			searchKey.setConsignorCodeOrder(3, true);
			searchKey.setLocationNoOrder(4, true);
			searchKey.setItemCodeOrder(5, true);
			searchKey.setCasePieceFlagOrder(6, true);

			//#CM43695
			// Cursor opening
			int count = reportFinder.searchStock((SearchKey[]) _listSearchKey);
			//#CM43696
			// Object data none
			if (count <= 0)
			{
				//#CM43697
				// There was no print data. 
				wMessage = "6003010";
				reportFinder.close();
				return false;
			}
				
			//#CM43698
			// The output file is made. 
			if (!createPrintWriter(FNAME_INVENTORYCHECK))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_INVENTORYCHECK);
				
			AsWorkingInformation workInfo[] = null ;
				
			//#CM43699
			// Make the data file 100 until the retrieval result is lost. 
			while (reportFinder.isNext())
			{
				//#CM43700
				// It acquires it from the retrieval result to 100. 
				workInfo = (AsWorkingInformation[]) reportFinder.getEntities(100);
				for (int i = 0; i < workInfo.length; i++)
				{
					wStrText.append(re + "");
					//#CM43701
					// Station No.
					wStrText.append(ReportOperation.format(workInfo[i].getDestStationNumber()) + tb);
					//#CM43702
					// Consignor Code
					wStrText.append(ReportOperation.format(workInfo[i].getConsignorCode()) + tb);
					//#CM43703
					// Consignor Name
					wStrText.append(ReportOperation.format(workInfo[i].getConsignorName()) + tb);
					//#CM43704
					// Work No.
					wStrText.append(ReportOperation.format(workInfo[i].getWorkNumber()) + tb);
					//#CM43705
					// Shelf No..
					String LocationNo = DisplayText.formatLocation(workInfo[i].getLocationNo());
					wStrText.append(ReportOperation.format(LocationNo) + tb);
					//#CM43706
					// Item Code
					wStrText.append(ReportOperation.format(workInfo[i].getItemCode()) + tb);
					//#CM43707
					// Item Name
					wStrText.append(ReportOperation.format(workInfo[i].getItemName1()) + tb);
					//#CM43708
					// Case/Piece flag
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(workInfo[i].getCasePieceFlag())) + tb);
					//#CM43709
					// Qty per case
					wStrText.append(ReportOperation.format(Integer.toString(workInfo[i].getEnteringQty()))+ tb);
					//#CM43710
					// Qty per bundle
					wStrText.append(ReportOperation.format(Integer.toString(workInfo[i].getBundleEnteringQty()))+ tb);					
					//#CM43711
					// Results case
					wStrText.append(DisplayUtil.getCaseQty(workInfo[i].getStockQty(), workInfo[i].getEnteringQty(), workInfo[i].getCasePieceFlag()) + tb);
					//#CM43712
					// Results piece
					wStrText.append(DisplayUtil.getPieceQty(workInfo[i].getStockQty(), workInfo[i].getEnteringQty(), workInfo[i].getCasePieceFlag()) + tb);
					//#CM43713
					// Case ITF
					wStrText.append(ReportOperation.format(workInfo[i].getItf()) + tb);
					//#CM43714
					// Bundle ITF
					wStrText.append(ReportOperation.format(workInfo[i].getBundleItf()) + tb);
					//#CM43715
					// Expiry date
					wStrText.append(ReportOperation.format(workInfo[i].getUseByDate()));
					//#CM43716
					// Entry
					wPWriter.print(wStrText);
					wStrText.setLength(0);
				}
			}
			//#CM43717
			// Close the stream.
			wPWriter.close();
			
			//#CM43718
			// UCXSingle is executed. 
			if (!executeUCX(JOBID_INVENTORYCHECK)) 
			{
				//#CM43719
				//It failed in the print. Refer to the log.  
				return false;
			}
		
			//#CM43720
			// The data file is moved to the backup folder. 
			ReportOperation.createBackupFile(wFileName);
		}
		catch (ReadWriteException e)
		{
			//#CM43721
			// It failed in the print. Refer to the log. 
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM43722
				// Do the close processing of the opening data base cursor. 
				reportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM43723
				// The data base error occurred. Refer to the log. 
				setMessage("6007002");
				return false;			
			}
		}
		return true;
	}
	
	//#CM43724
	// Package methods -----------------------------------------------

	//#CM43725
	// Protected methods ---------------------------------------------

	//#CM43726
	// Private methods -----------------------------------------------

}
//#CM43727
//end of class
