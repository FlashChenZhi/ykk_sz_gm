//#CM574918
//$Id: StorageWriter.java,v 1.6 2006/12/13 09:04:07 suresh Exp $
package jp.co.daifuku.wms.storage.report;
//#CM574919
/*
 * Created on 2004/09/21 Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is
 * subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.storage.dbhandler.StorageWorkingInformationHandler;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;
//#CM574920
/**
 * Designer : T.Yamashita <BR>
 * Maker : T.Yamashita <BR>
 * <BR>
 * This class creates storage work (Storage direction) list report data file and calls the print process<BR>
 * With the conditions specified in the schedule class, this class searches the work info table
 * and creates data file for printing <BR>
 * <BR>
 * This class processes the following<BR>
 * <BR>
 * Process that creates the report data file (<CODE>startPrint</CODE> method) <BR>
 * <DIR>
 *   Search work info<BR>
 *   Data file for printing is not created if target data does not exist.<BR>
 *   If target data exists, this class creates data file for printing and calls print process.<BR>
 * <BR>
 *   <Search condition> *Required Input
 *   <DIR>
 * 		Consignor code*<BR>
 * 		Start Storage Plan Date<BR>
 * 		End Storage Plan Date<BR>
 * 		Item code<BR>
 * 		Case piece type *<BR>
 * 		Work Status*<BR>
 * 		Work type :Storage * <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/30</TD><TD>T.Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.6 $, $Date: 2006/12/13 09:04:07 $
 * @author  $Author: suresh $
 */
public class StorageWriter extends CSVWriter
{
	//#CM574921
	// Class fields --------------------------------------------------

	//#CM574922
	// Class variables -----------------------------------------------
	//#CM574923
	/**
	 * Consignor code
	 */
	protected String wConsignorCode;

	//#CM574924
	/**
	 * Start Storage Plan Date
	 */
	protected String wFromPlanDate;

	//#CM574925
	/**
	 * End Storage Plan Date
	 */
	protected String wToPlanDate;

	//#CM574926
	/**
	 * Item code
	 */
	protected String wItemCode;

	//#CM574927
	/**
	 * Case piece type 
	 */
	protected String wCasePieceFlag = "";

	//#CM574928
	/**
	 * Status flag
	 */
	protected String wStatusFlag = "";
	
	//#CM574929
	/**
	 * Job No. 
	 */
	protected String wJobNo[] = null ;


	//#CM574930
	// Class method --------------------------------------------------
	//#CM574931
	/**
	 * Returns version of this class
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.6 $,$Date: 2006/12/13 09:04:07 $");
	}

	//#CM574932
	// Constructors --------------------------------------------------
	//#CM574933
	/**
	 * Construct StorageWriter object<BR>
	 * Set connection and locale<BR>
	 * @param conn <CODE>Connection</CODE> database connection object
	 */
	public StorageWriter(Connection conn)
	{
		super(conn);
	}

	//#CM574934
	// Public methods ------------------------------------------------
	//#CM574935
	/**
	 * Set search value to consignor code
	 * @param pConsignorcode String Consignor code
	 */
	public void setConsignorCode(String pConsignorcode)
	{
		wConsignorCode = pConsignorcode;
	}

	//#CM574936
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM574937
	/**
	 * Set search value to Start Storage Plan Date
	 * @param pFromPlanDate String Storage Plan Date
	 */
	public void setFromPlanDate(String pFromPlanDate)
	{
		wFromPlanDate = pFromPlanDate;
	}

	//#CM574938
	/**
	 * Fetch Start Storage Plan Date
	 * @return Start Storage Plan Date
	 */
	public String getFromPlanDate()
	{
		return wFromPlanDate;
	}

	//#CM574939
	/**
	 * Set search value to End Storage Plan Date
	 * @param pToPlanDate String Storage Plan Date
	 */
	public void setToPlanDate(String pToPlanDate)
	{
		wToPlanDate = pToPlanDate;
	}

	//#CM574940
	/**
	 * Fetch End Storage Plan Date
	 * @return End Storage Plan Date
	 */
	public String getToPlanDate()
	{
		return wToPlanDate;
	}

	//#CM574941
	/**
	 * Set search value to Item code
	 * @param pItemCode String Item code
	 */
	public void setItemCode(String pItemCode)
	{
		wItemCode = pItemCode;
	}

	//#CM574942
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return wItemCode;
	}

	//#CM574943
	/**
	 * Set Case piece type
	 * @param casePieceFlag String Case piece type 
	 */
	public void setCasePieceFlag(String casePieceFlag)
	{
		wCasePieceFlag = casePieceFlag;
	}
	//#CM574944
	/**
	 * Fetch Case piece type
	 * @return Case piece type 
	 */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}

	//#CM574945
	/**
	 * Set Status flag
	 * @param statusFlag String Status flag
	 */
	public void setStatusFlag(String statusFlag)
	{
		wStatusFlag = statusFlag;
	}
	//#CM574946
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return wStatusFlag;
	}	

	//#CM574947
	/**
	 * Set value to Job No.
	 * @param jobNo String[] Job No. 
	 */
	public void setJobNo(String[] jobNo)
	{
		wJobNo = jobNo;
	}

	//#CM574948
	/**
	 * Fetch Job No.
	 * @return Job No. 
	 */
	public String[] getJobNo()
	{
		return wJobNo;
	}
	
	//#CM574949
	/**
	 * Fetch print data count<BR>
	 * Used to decide whether to call the print process or not based on search result<BR>
	 * @return print data count
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	public int count() throws ReadWriteException
	{
		StorageWorkingInformationHandler storageWorkHandle = new StorageWorkingInformationHandler(wConn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		//#CM574950
		// Set search conditions and fetch count
		setWorkInfoSearchKey(searchKey);
		return storageWorkHandle.count(searchKey);

	}

	//#CM574951
	/**
	 * Create Storage work list CSV file and call print<BR>
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

		WorkingInformationReportFinder wInfoReportFinder = new WorkingInformationReportFinder(wConn);

		try
		{
			//#CM574952
			// execute search
			WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();

			//#CM574953
			// set search conditions
			setWorkInfoSearchKey(workInfoSearchKey);
			//#CM574954
			// set search order
			if (!StringUtil.isBlank(wStatusFlag) && !wStatusFlag.equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
			{
				workInfoSearchKey.setConsignorCodeOrder(1, true);
				workInfoSearchKey.setPlanDateOrder(2, true);
				workInfoSearchKey.setItemCodeOrder(3, true);
				workInfoSearchKey.setWorkFormFlagOrder(4, true);
				workInfoSearchKey.setLocationNoOrder(5, true);
			}
			else
			{
				workInfoSearchKey.setConsignorCodeOrder(1, true);
				workInfoSearchKey.setPlanDateOrder(2, true);
				workInfoSearchKey.setItemCodeOrder(3, true);
				workInfoSearchKey.setWorkFormFlagOrder(4, true);
				workInfoSearchKey.setResultLocationNoOrder(5, true);
			}


			//#CM574955
			// Don't call print process if data does not exist
			if (wInfoReportFinder.search(workInfoSearchKey) <= 0)
			{
				//#CM574956
				// 6003010 = Print data was not found.
				wMessage = "6003010";
				return false;
			}

			//#CM574957
			// create output file
			if (!createPrintWriter(FNAME_STRAGE_WORK))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_STRAGE_WORK);

			//#CM574958
			// Fetch name
			String consignorName = "";
			//#CM574959
			// Create data file until the search results are done
			WorkingInformation[] workInfo = null;
			while (wInfoReportFinder.isNext())
			{
				workInfo = (WorkingInformation[]) wInfoReportFinder.getEntities(100);

				//#CM574960
				// Create contents of the data file output
				for (int i = 0; i < workInfo.length; i++)
				{
					wStrText.append(re + "");

					//#CM574961
					// Consignor code
					wStrText.append(ReportOperation.format(workInfo[i].getConsignorCode()) + tb);
					//#CM574962
					// Consignor name
					if (i == 0 || !wConsignorCode.equals(workInfo[i].getConsignorCode()))
					{
						//#CM574963
						// Change consignor code
						wConsignorCode = workInfo[i].getConsignorCode();
						//#CM574964
						// Fetch consignor name
						consignorName = getConsignorName();
					}
					wStrText.append(ReportOperation.format(consignorName) + tb);
					//#CM574965
					// Storage Plan Date
					wStrText.append(ReportOperation.format(workInfo[i].getPlanDate()) + tb);
					//#CM574966
					// Item code
					wStrText.append(ReportOperation.format(workInfo[i].getItemCode()) + tb);
					//#CM574967
					// Item name
					wStrText.append(ReportOperation.format(workInfo[i].getItemName1()) + tb);
					
					//#CM574968
					// Case piece type name
					//#CM574969
					// Fetch Case piece type name from Case piece type (work type)
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(workInfo[i].getWorkFormFlag())) + tb);
						
					//#CM574970
					// Storage location
					if(!StringUtil.isBlank(workInfo[i].getResultLocationNo())) 
					{
					    wStrText.append(ReportOperation.format(workInfo[i].getResultLocationNo()) + tb);
					}
					else 
					{
					    wStrText.append(ReportOperation.format(workInfo[i].getLocationNo()) + tb);
					}
					
					//#CM574971
					// Packed qty per case
					wStrText.append(workInfo[i].getEnteringQty() + tb);
					//#CM574972
					// Packed qty per bundle
					wStrText.append(workInfo[i].getBundleEnteringQty() + tb);
					//#CM574973
					// Work plan total qty
					wStrText.append(workInfo[i].getPlanEnableQty() + tb);
					
					//#CM574974
					// Work Plan case qty
					wStrText.append(DisplayUtil.getCaseQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty(),
							workInfo[i].getWorkFormFlag()) + tb);
					//#CM574975
					// Work Plan piece qty
					wStrText.append(DisplayUtil.getPieceQty(workInfo[i].getPlanEnableQty(), workInfo[i].getEnteringQty(),
							workInfo[i].getWorkFormFlag()) + tb);
					//#CM574976
					// Work Result case qty
					wStrText.append(DisplayUtil.getCaseQty(workInfo[i].getResultQty(), workInfo[i].getEnteringQty(),
							workInfo[i].getWorkFormFlag()) + tb);
					//#CM574977
					// Work Result piece qty
					wStrText.append(DisplayUtil.getPieceQty(workInfo[i].getResultQty(), workInfo[i].getEnteringQty(),
							workInfo[i].getWorkFormFlag()) + tb);
					
					//#CM574978
					// Expiry date
					if(!StringUtil.isBlank(workInfo[i].getResultLocationNo())) 
					{
						wStrText.append(workInfo[i].getResultUseByDate() + tb);
					}
					else 
					{
						wStrText.append(workInfo[i].getUseByDate() + tb);
					}
					//#CM574979
					// Case ITF
					wStrText.append(workInfo[i].getItf() + tb);
					//#CM574980
					// Bundle ITF
					wStrText.append(workInfo[i].getBundleItf());

					//#CM574981
					// Output data to file
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}

			//#CM574982
			// Close the stream
			wPWriter.close();

			//#CM574983
			// Execute UCXSingle. (Print)
			if (!executeUCX(JOBID_STRAGE_WORK))
			{
				//#CM574984
				// Print failed. Refer to the log
				return false;
			}

			//#CM574985
			// If print succeeds, move the data file to backup folder
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException re)
		{
			//#CM574986
			// 6007034 = Print failed. Refer to the log
			setMessage("6007034");

			return false;
		}
		finally
		{
			try
			{
				//#CM574987
				// Close the database cursor
				wInfoReportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM574988
				// Database error occurred. Refer to the log
				setMessage("6007002");
				return false;			
			}
		}

		return true;
	}

	//#CM574989
	// Package methods -----------------------------------------------

	//#CM574990
	// Protected methods ---------------------------------------------
	
	//#CM574991
	/**
	 * This method sets the search condition to work info search key
	 * @param searchKey WorkingInformationSearchKey work info search key
	 * @return search key with search conditions
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	protected WorkingInformationSearchKey setWorkInfoSearchKey(WorkingInformationSearchKey searchKey) throws ReadWriteException
	{
		//#CM574992
		// Set search key
		//#CM574993
		// Call from storage setting screen
		//#CM574994
		// JOB_NO
		if (wJobNo != null)
		{
			if (wJobNo.length > 0)
			{
			    searchKey.setJobNo(wJobNo);
			}
		}
		//#CM574995
		// Call from storage work list screen 
		//#CM574996
		// Consignor code, Start Storage Plan Date, End Storage Plan Date, Item code, Case piece type (array) (array), StatusClassification (array)
		else
		{
			if (!StringUtil.isBlank(wConsignorCode))
			{
				searchKey.setConsignorCode(wConsignorCode);
			}
			if (!StringUtil.isBlank(wFromPlanDate))
			{
				searchKey.setPlanDate(wFromPlanDate, ">=");
			}
			if (!StringUtil.isBlank(wToPlanDate))
			{
				searchKey.setPlanDate(wToPlanDate, "<=");
			}
			if (!StringUtil.isBlank(wItemCode))
			{
				searchKey.setItemCode(wItemCode);
			}
			if (!StringUtil.isBlank(wCasePieceFlag))
			{
				if (!wCasePieceFlag.equals(StorageSupportParameter.CASEPIECE_FLAG_ALL))
				{
					searchKey.setWorkFormFlag(wCasePieceFlag);
				}			
			}
			if (!StringUtil.isBlank(wStatusFlag))
			{
				//#CM574997
				// Standby
				if (!wStatusFlag.equals(StorageSupportParameter.STATUS_FLAG_ALL ))
				{
					searchKey.setStatusFlag(wStatusFlag);
				}
				//#CM574998
				// All
				else if (wStatusFlag.equals(StorageSupportParameter.STATUS_FLAG_ALL ))
				{				
					String[] stat = {
						WorkingInformation.STATUS_FLAG_UNSTART,
						WorkingInformation.STATUS_FLAG_NOWWORKING,
						WorkingInformation.STATUS_FLAG_COMPLETION
					};
					searchKey.setStatusFlag(stat);
				}
			}
		}
		searchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		//#CM574999
		// Except ASRS work
		searchKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
		return searchKey;
	}

	//#CM575000
	/**
	 * Method to fetch the most recently registered consignor name<BR>
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	protected String getConsignorName() throws ReadWriteException
	{
		String consignorName = "";

		//#CM575001
		// Set search key
		WorkingInformationSearchKey nameKey = new WorkingInformationSearchKey();
		WorkingInformationReportFinder nameFinder = new WorkingInformationReportFinder(wConn);

		setWorkInfoSearchKey(nameKey);
		nameKey.setConsignorNameCollect();
		nameKey.setRegistDateOrder(1, false);
		//#CM575002
		// Execute search
		nameFinder.open();
		if (((WorkingInformationReportFinder) nameFinder).search(nameKey) > 0)
		{
			WorkingInformation winfo[] = (WorkingInformation[]) ((WorkingInformationReportFinder) nameFinder).getEntities(1);

			if (winfo != null && winfo.length != 0)
			{
				consignorName = winfo[0].getConsignorName();
			}
		}
		nameFinder.close();

		return consignorName;
	}
	//#CM575003
	// Private methods -----------------------------------------------
}
