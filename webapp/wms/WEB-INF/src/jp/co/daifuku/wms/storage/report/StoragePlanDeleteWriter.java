//#CM574659
//$Id: StoragePlanDeleteWriter.java,v 1.6 2006/12/13 09:04:08 suresh Exp $
package jp.co.daifuku.wms.storage.report;
//#CM574660
/*
 * Created on 2004/09/21 Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is
 * subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
//#CM574661
/**
 * Designer : T.Yamashita <BR>
 * Maker : T.Yamashita <BR>
 * <BR>
 * This class creates the storage plan delete list report data file and calls the print process<BR>
 * With the conditions specified in the schedule class, this class searches the storage plan info (dnstorageplan) table
 * and creates data file for printing <BR>
 * <BR>
 * This class processes the following<BR>
 * <BR>
 * Process that creates the report data file (<CODE>startPrint</CODE> method) <BR>
 * <DIR>
 *   Search storage plan info<BR>
 *   Data file for printing is not created if target data does not exist.<BR>
 *   If target data exists, this class creates data file for printing and calls print process.<BR>
 * <BR>
 *   <Search condition> *Required Input
 *   <DIR>
 *     Consignor code*<BR>
 *     Storage Plan Date*<BR>
 *     Item code<BR>
 *     Update date/time (data here afterwards)<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/30</TD><TD>T.Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.6 $, $Date: 2006/12/13 09:04:08 $
 * @author  $Author: suresh $
 */
public class StoragePlanDeleteWriter extends CSVWriter
{
	//#CM574662
	//private member variable declarations
	//#CM574663
	/**
	 * Consignor code
	 */
	protected String wConsignorCode = null ;

	//#CM574664
	/**
	 * Storage Plan Date
	 */
	protected String wPlanDate[] = null ;

	//#CM574665
	/**
	 * Item code
	 */
	protected String wItemCode[] = null ;

	//#CM574666
	/**
	 * Update date/time 
	 */
	protected Date wLastUpdateDate = null ;

	//#CM574667
	/**
	 * Returns version of this class
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.6 $,$Date: 2006/12/13 09:04:08 $");
	}
	
	//#CM574668
	/**
	 * Construct StoragePlanDeleteWriter object<BR>
	 * Set connection and locale<BR>
	 * @param conn <CODE>Connection</CODE> database connection object
	 */
	public StoragePlanDeleteWriter(Connection conn)
	{
		super(conn);
	}
	//#CM574669
	/**
	 * Set value for Consignor code
	 * @param consignorcode String Consignor code
	 */
	public void setConsignorCode(String consignorcode)
	{
		wConsignorCode = consignorcode;
	}

	//#CM574670
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM574671
	/**
	 * Set value for Storage Plan Date
	 * @param plandate String[] Storage Plan Date
	 */
	public void setPlanDate(String[] plandate)
	{
		wPlanDate = plandate;
	}

	//#CM574672
	/**
	 * Fetch Storage Plan Date
	 * @return Storage Plan Date
	 */
	public String[] getPlanDate()
	{
		return wPlanDate;
	}

	//#CM574673
	/**
	 * Set value for Item code
	 * @param itemCode String[] Item code
	 */
	public void setItemCode(String[] itemCode)
	{
		wItemCode = itemCode;
	}

	//#CM574674
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String[] getItemCode()
	{
		return wItemCode;
	}

	//#CM574675
	/**
	 * Set value for Update date/time
	 * @param lastupdatedate Date Update date/time 
	 */
	public void setLastUpdateDate(Date lastupdatedate)
	{
		wLastUpdateDate = lastupdatedate;
	}

	//#CM574676
	/**
	 * Fetch Update date/time
	 * @return Update date/time 
	 */
	public Date getLastUpdateDate()
	{
		return wLastUpdateDate;
	}

	//#CM574677
	/**
	 * Create storage plan delete list csv file and call print<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set search conditions and execute search<BR>
	 * 		2.If the search result is less than 1, don't print<BR>
	 * 		3.Fetch search result in lots of 100<BR>
	 * 		4.Print<BR>
	 * 		5.If print succeeds, move the data file to backup folder<BR>
	 * 		6.Return whether the print is successful or not<BR>
	 * </DIR>
	 * @return execution result(Print success:true Print failed:false)
	 */
	public boolean startPrint()
	{
		StoragePlanReportFinder storagePlanReportFinder =  new StoragePlanReportFinder(wConn);

		try
		{
			StoragePlanSearchKey storagePlanSearchKey = new StoragePlanSearchKey(); 
			boolean pHeader_Flag = true ;
			boolean pPrintData_Flag = false ;
			
			//#CM574678
			// Fetch search condition
			//#CM574679
			// Repeat process for the data received in parameter
			for (int cdt_cnt = 0; cdt_cnt < wPlanDate.length ; cdt_cnt++)
			{
				//#CM574680
				// set search condition
				storagePlanSearchKey.KeyClear() ;
				//#CM574681
				// Consignor code
				storagePlanSearchKey.setConsignorCode(wConsignorCode);
				//#CM574682
				// Storage Plan Date
				if(wPlanDate[cdt_cnt] != null)
				{
					storagePlanSearchKey.setPlanDate(wPlanDate[cdt_cnt]);
				}
				//#CM574683
				// Item code
				if(wItemCode[cdt_cnt] != null)
				{
					storagePlanSearchKey.setItemCode(wItemCode[cdt_cnt]);
				}
				//#CM574684
				// Update date/time 
				if(wLastUpdateDate!= null)
				{
					storagePlanSearchKey.setLastUpdateDate(wLastUpdateDate, ">=");
				}
				
				//#CM574685
				// Print order (Plan date, Item code, Case storage location, Piece storage location)
				storagePlanSearchKey.setPlanDateOrder(1,true);
				storagePlanSearchKey.setItemCodeOrder(2,true);
				storagePlanSearchKey.setCaseLocationOrder(3,true);
				storagePlanSearchKey.setPieceLocationOrder(4,true);
			
				//#CM574686
				// Open cursor
				storagePlanReportFinder.open();

				//#CM574687
				// No target data
				if (storagePlanReportFinder.search(storagePlanSearchKey) <= 0 )
				{
					continue;
				}
				
				//#CM574688
				// Process only the first time
				if (pHeader_Flag == true)
				{
					//#CM574689
					// Create output file
					if (!createPrintWriter(FNAME_STRAGE_DELETE))
					{
						return false;
					}

					pHeader_Flag = false ;
                    
                    // 出力ファイルにヘッダーを作成
                    wStrText.append(HEADER_STRAGE_DELETE);
				}
				
				//#CM574690
				// Create data file for every 100 records until all the search result is completed
				StoragePlan[] storagePlan = null ;
				while(storagePlanReportFinder.isNext())
				{
					//#CM574691
					// Fetch upto 100 records from search result
					storagePlan = (StoragePlan[])storagePlanReportFinder.getEntities(100);

					for (int i = 0; i < storagePlan.length; i++)
					{

						wStrText.append(re + "");
		
						//#CM574692
						// Consignor code
						wStrText.append(ReportOperation.format(storagePlan[i].getConsignorCode()) + tb);
						//#CM574693
						// Consignor name
						wStrText.append(ReportOperation.format(storagePlan[i].getConsignorName()) + tb);
						//#CM574694
						// Storage Plan Date
						wStrText.append(ReportOperation.format(storagePlan[i].getPlanDate()) + tb);
						//#CM574695
						// Item code
						wStrText.append(ReportOperation.format(storagePlan[i].getItemCode()) + tb);
						//#CM574696
						// Item name
						wStrText.append(ReportOperation.format(storagePlan[i].getItemName1()) + tb);
						//#CM574697
						// Case storage location
						wStrText.append(ReportOperation.format(storagePlan[i].getCaseLocation()) + tb);
						//#CM574698
						// Piece storage location
						wStrText.append(ReportOperation.format(storagePlan[i].getPieceLocation()) + tb);
						//#CM574699
						// Packed qty per case
						wStrText.append(ReportOperation.format(Integer.toString(storagePlan[i].getEnteringQty())) + tb);
						//#CM574700
						// Packed qty per bundle
						wStrText.append(storagePlan[i].getBundleEnteringQty() + tb);
						//#CM574701
						// Total host plan qty
						wStrText.append(storagePlan[i].getPlanQty() + tb);
						//#CM574702
						// Host plan case qty
						wStrText.append(DisplayUtil.getCaseQty(storagePlan[i].getPlanQty(), storagePlan[i].getEnteringQty(), storagePlan[i].getCasePieceFlag()) + tb);
						//#CM574703
						// Host plan piece qty
						wStrText.append(DisplayUtil.getPieceQty(storagePlan[i].getPlanQty(), storagePlan[i].getEnteringQty(), storagePlan[i].getCasePieceFlag()) + tb);
						//#CM574704
						// Case ITF
						wStrText.append(storagePlan[i].getItf() + tb);
						//#CM574705
						// Bundle ITF
						wStrText.append(storagePlan[i].getBundleItf());
						//#CM574706
						// write
						wPWriter.print(wStrText);
						
						wStrText.setLength(0);
						
						//#CM574707
						// cancel flag if there is atleast one data write
						pPrintData_Flag = true;
					}
				}
			}
			
			if(wPWriter != null)
			{
				//#CM574708
				// Close the stream
				wPWriter.close();
			}
			
			if (pPrintData_Flag == false)
			{
				//#CM574709
				// Print data was not found.
				wMessage = "6003010";
				return false;
			}



			//#CM574710
			// Execute UCXSingle
		   if (!executeUCX(JOBID_STRAGE_DELETE))
			{
				//#CM574711
				// Print failed. Refer to the log
				return false;
			}
			//#CM574712
			// Move data file to backup folder
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)		
		{
			//#CM574713
			// Print failed. Refer to the log
			setMessage("6007034");

			return false;
		}
		finally
		{
			try
			{
				//#CM574714
				// Close the database cursor
				storagePlanReportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM574715
				// Database error occurred. Refer to the log
				setMessage("6007002");
				return false;			
			}
		}

		return true;
	}
}
