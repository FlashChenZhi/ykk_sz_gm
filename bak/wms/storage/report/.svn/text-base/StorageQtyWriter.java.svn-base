//#CM574833
//$Id: StorageQtyWriter.java,v 1.6 2006/12/13 09:04:07 suresh Exp $
package jp.co.daifuku.wms.storage.report;
//#CM574834
/*
 * Created on 2004/09/29 Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is
 * subject to license terms.
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
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM574835
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * This class creates storage result list report data file and calls the print process<BR>
 * With the conditions specified in the schedule class, this class searches the result info table
 * and creates data file for printing<BR>
 * This class processes the following<BR>
 * <BR>
 * Process that creates the report data file (<CODE>startPrint</CODE> method) <BR>
 * <DIR>
 *   Search result info (ResultView)<BR>
 *   Data file for printing is not created if target data does not exist.<BR>
 *   If target data exists, this class creates data file for printing and calls print process.<BR>
 * <BR>
 *   <Search condition> 
 *   <DIR>
 * 		Consignor code*<BR>
 * 		Start storage date <BR>
 * 		End storage date <BR>
 * 		Item code <BR>
 * 		Case piece type * <BR>
 * 		Work type:Storage * <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/5</TD><TD>K.Toda</TD><TD>Create new</TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author suresh kayamboo
 * @version $Revision 1.2 2004/09/29
 */
public class StorageQtyWriter extends CSVWriter
{
	//#CM574836
	//Constants----------------------------------------
	
	//#CM574837
	//Attributes---------------------------------------

    //#CM574838
    /**
     * Consignor code
     */
	private String wConsignorCode = "";

    //#CM574839
    /**
     * Start storage date
     */
	private String wStartStorageWorkDate = "";

    //#CM574840
    /**
     * End storage date
     */
	private String wEndStorageWorkDate = "";

    //#CM574841
    /**
     * Item code
     */
	private String wItemCode = "";

    //#CM574842
    /**
     * Case piece type 
     */
	private String wCasePieceFlag = "";

    //#CM574843
    /**
     * Status flag
     */
	private String wStatusFlag = "";

    //#CM574844
    /**
     * Consignor name
     */
	private String wConsignorName = "";
	
	//#CM574845
	//Static-------------------------------------------
	
	//#CM574846
	//Constructors-------------------------------------
	//#CM574847
	/**
	 * Construct StorageQtyWriter object <BR>
	 * Set connection and locale<BR>
	 * @param conn <CODE>Connection</CODE> database connection object
	 */
	public StorageQtyWriter(Connection conn)
	{
		super(conn);
	}
	//#CM574848
	//Public--------------------------------------------
	//#CM574849
	/**
	 * Returns version of this class
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.6 $,$Date: 2006/12/13 09:04:07 $");
	}
	
	//#CM574850
	/**
	 * Set value to Consignor code
	 * @param consignorCode String Consignor code
	 */
	public void setConsignorCode(String consignorCode)
	{
		wConsignorCode = consignorCode;
	}
	
	//#CM574851
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}
	
	//#CM574852
	/**
	 * Set value to Start storage date
	 * @param startStorageWorkDate String Storage date
	 */
	public void setStartStorageWorkDate(String startStorageWorkDate)
	{
		wStartStorageWorkDate = startStorageWorkDate;
	}
	
	//#CM574853
	/**
	 * Fetch Start storage date
	 * @return Start storage date
	 */
	public String getStartStorageWorkDate()
	{
		return wStartStorageWorkDate;
	}
	
	//#CM574854
	/**
	 * Set value to End storage date
	 * @param endStorageWorkDate String Storage date
	 */
	public void setEndStorageWorkDate(String endStorageWorkDate)
	{
		wEndStorageWorkDate = endStorageWorkDate;
	}
	
	//#CM574855
	/**
	 * Fetch End storage date
	 * @return End storage date
	 */
	public String getEndStorageWorkDate()
	{
		return wEndStorageWorkDate;
	}
	
	//#CM574856
	/**
	 * Set value to Item code
	 * @param itemCode String Item code
	 */
	public void setItemCode(String itemCode)
	{
		wItemCode = itemCode;
	}
	
	//#CM574857
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return wItemCode;
	}
	
    //#CM574858
    /**
     * Set value to Case piece type
     * @param casePieceFlag String Case piece type
     */
	public void setCasePieceFlag(String casePieceFlag)
	{
		wCasePieceFlag = casePieceFlag;
	}
	
    //#CM574859
    /**
     * Fetch Case piece type
     * @return Case piece type
     */
	public String getCasePieceFlag()
	{
		return wCasePieceFlag;
	}
	
    //#CM574860
    /**
     * Set value to Status flag
     * @param statusFlag String Status flag
     */
	public void setStatusFlag(String statusFlag)
	{
		wStatusFlag = statusFlag;
	}
	
    //#CM574861
    /**
     * Fetch Status flag
     * @return Status flag
     */
	public String getStatusFlag()
	{
		return wStatusFlag;
	}
	
	//#CM574862
	/**
	 * Fetch print data count<BR>
	 * Used to decide whether to call the print process or not based on search result<BR>
	 * @return print data count
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	public int count() throws ReadWriteException
	{
		ResultViewHandler storageResultHandle = new ResultViewHandler(wConn);
		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		//#CM574863
		// Set search conditions and fetch count
		setStoragePlanResultViewSearchKey(searchKey);
		
		return storageResultHandle.count(searchKey);

	}

	//#CM574864
	/**
	 * Create result list csv file and call print<BR>
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
		//#CM574865
		// Set ReportFinder instance
		ResultViewReportFinder sFinder = new ResultViewReportFinder(wConn);
		try
		{
			//#CM574866
			// execute search
			//#CM574867
			// Set search key instance
			ResultViewSearchKey sKey = new ResultViewSearchKey();
			//#CM574868
			// Set search key
			sKey = setStoragePlanResultViewSearchKey(sKey);

			//#CM574869
			// Display order
			sKey.setWorkDateOrder(1, true);
			sKey.setPlanDateOrder(2, true);
			sKey.setItemCodeOrder(3, true);
			sKey.setWorkFormFlagOrder(4, true);
			sKey.setRegistDateOrder(5, true);
			sKey.setResultQtyOrder(6, true);
			//#CM574870
			// Data check using search key
			//#CM574871
			// Don't call print process if data does not exist
			
			if (sFinder.search(sKey) <= 0)
			{
				//#CM574872
				// 6003010 = Print data was not found.
				wMessage = "6003010";
				return false;
			}
			
			//#CM574873
			// create output file
			if (!createPrintWriter(FNAME_STRAGE_QTY))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_STRAGE_QTY);
			
			//#CM574874
			// Fetch consignor name
			getDisplayConsignorName() ;
			
			//#CM574875
			// Create data file until the search results are done
			ResultView[] resultView = null;
			while (sFinder.isNext())
			{
				//#CM574876
				// Output search result in lots of 100
				resultView = (ResultView[]) sFinder.getEntities(100);
				//#CM574877
				// Create contents of the data file output
				for (int i = 0; i < resultView.length; i++)
				{
					wStrText.append(re + "");
					
					//#CM574878
					// Consignor code
					wStrText.append(ReportOperation.format(resultView[i].getConsignorCode()) + tb);
					//#CM574879
					// Consignor name
					wStrText.append(ReportOperation.format(wConsignorName) + tb);
					//#CM574880
					// Storage date
					wStrText.append(ReportOperation.format(resultView[i].getWorkDate()) + tb);
					//#CM574881
					// Storage Plan Date
					wStrText.append(ReportOperation.format(resultView[i].getPlanDate()) + tb);
					//#CM574882
					// Item code
					wStrText.append(ReportOperation.format(resultView[i].getItemCode()) + tb);
					//#CM574883
					// Item name
					wStrText.append(ReportOperation.format(resultView[i].getItemName1()) + tb);
					//#CM574884
					// Classification
					wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(resultView[i].getWorkFormFlag())) + tb);
					//#CM574885
					// Storage location
					wStrText.append(ReportOperation.format(WmsFormatter.toDispLocation(
					        resultView[i].getResultLocationNo()
			                , resultView[i].getSystemDiscKey())) + tb);
					//#CM574886
					// Packed qty per case
					wStrText.append(resultView[i].getEnteringQty() + tb);
					//#CM574887
					// Packed qty per bundle
					wStrText.append(resultView[i].getBundleEnteringQty() + tb);
					//#CM574888
					// Plan case qty
					wStrText.append(DisplayUtil.getCaseQty(resultView[i].getPlanEnableQty(),resultView[i].getEnteringQty(),
							resultView[i].getWorkFormFlag()) + tb);
					//#CM574889
					// Plan piece qty
					wStrText.append(DisplayUtil.getPieceQty(resultView[i].getPlanEnableQty(),resultView[i].getEnteringQty(),
							resultView[i].getWorkFormFlag()) + tb);
					//#CM574890
					// Result case qty
					wStrText.append(DisplayUtil.getCaseQty(resultView[i].getResultQty(),resultView[i].getEnteringQty(),
							resultView[i].getWorkFormFlag()) + tb);
					//#CM574891
					// Result bundle qty
					wStrText.append(DisplayUtil.getPieceQty(resultView[i].getResultQty(),resultView[i].getEnteringQty(),
							resultView[i].getWorkFormFlag()) + tb);
					//#CM574892
					// Shortage case qty
					wStrText.append(DisplayUtil.getCaseQty(resultView[i].getShortageCnt(),resultView[i].getEnteringQty(),
							resultView[i].getWorkFormFlag()) + tb);
					//#CM574893
					// Shortage bundle qty
					wStrText.append(DisplayUtil.getPieceQty(resultView[i].getShortageCnt(),resultView[i].getEnteringQty(),
							resultView[i].getWorkFormFlag()) + tb);
					//#CM574894
					// ITF
					wStrText.append(ReportOperation.format(resultView[i].getItf()) + tb);
					//#CM574895
					// Bundle ITF
					wStrText.append(ReportOperation.format(resultView[i].getBundleItf()) + tb);
					//#CM574896
					// Expiry date
					wStrText.append(ReportOperation.format(resultView[i].getResultUseByDate()) + tb);
					
					//#CM574897
					// Output data to file
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}
			//#CM574898
			// Close the stream
			wPWriter.close();
			//#CM574899
			// Execute UCXSingle. (Print)
			if(!executeUCX(JOBID_STRAGE_QTY))
			{ 
			    //#CM574900
			    //Print failed. Refer to the log
			    return false;
			}
			
			//#CM574901
			// If print succeeds, move the data file to backup folder
			ReportOperation.createBackupFile(wFileName);
		}
		catch (ReadWriteException e)
		{
			//#CM574902
			// 6007034 = Print failed. Refer to the log
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				sFinder.close();
			}
			catch (ReadWriteException re)
			{
				//#CM574903
				// 6007034 = Print failed. Refer to the log
				setMessage("6007034");
				return false;
			}
		}
		return true;
	}
	
	//#CM574904
	// Protected----------------------------------------------------------
	
	//#CM574905
	// Private------------------------------------------------------------
	//#CM574906
	/**
	 * This method sets search condition to result info search key
	 * @param sKey ResultViewSearchKey result info search key
	 * @return search key with search conditions
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	private ResultViewSearchKey setStoragePlanResultViewSearchKey(ResultViewSearchKey sKey)
			throws ReadWriteException
	{	    
		//#CM574907
		//Consignor code
		if (!StringUtil.isBlank(wConsignorCode))
		{
			sKey.setConsignorCode(this.getConsignorCode());
		}
		//#CM574908
		//Start storage date
		if (!StringUtil.isBlank(wStartStorageWorkDate))
		{
			sKey.setWorkDate(this.getStartStorageWorkDate(), ">=");
		}
		//#CM574909
		//End storage date
		if (!StringUtil.isBlank(wEndStorageWorkDate))
		{
			sKey.setWorkDate(this.getEndStorageWorkDate(), "<=");
		}
		//#CM574910
		//Item code
		if (!StringUtil.isBlank(wItemCode))
		{
			sKey.setItemCode(this.getItemCode());
		}
		//#CM574911
		//Case piece type 
		if (!StringUtil.isBlank(wCasePieceFlag) && !this.getCasePieceFlag().equals(StorageSupportParameter.CASEPIECE_FLAG_ALL))
		{
			sKey.setWorkFormFlag(this.getCasePieceFlag());
		}
		//#CM574912
		//Work type (Storage )
		sKey.setJobType(ResultView.JOB_TYPE_STORAGE) ;
		
		return sKey;
	}

	//#CM574913
	/**
	 * Method to fetch the most recently registered consignor name<BR>
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	private void getDisplayConsignorName() throws ReadWriteException
	{    
		//#CM574914
		// Create finder instance
		ResultViewReportFinder consignorFinder = new ResultViewReportFinder(wConn);
		ResultViewSearchKey sKey = new ResultViewSearchKey();
		
		//#CM574915
		// Set search key
		sKey = setStoragePlanResultViewSearchKey(sKey);
		
		sKey.setRegistDateOrder(1, false);
		
		//#CM574916
		// Search consignor name
		consignorFinder.open();
		if (consignorFinder.search(sKey) > 0)
		{
			ResultView[] resultView = (ResultView[]) consignorFinder.getEntities(1);

			if (resultView != null && resultView.length != 0)
			{
				wConsignorName = resultView[0].getConsignorName();
			}
		}
		consignorFinder.close();		
	}

	//#CM574917
	//Inner Classes----------------------------------------------------
}
