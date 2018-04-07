//#CM574716
//$Id: StoragePlanWriter.java,v 1.6 2006/12/13 09:04:08 suresh Exp $
package jp.co.daifuku.wms.storage.report;

//#CM574717
/*
 * Created on 2004/09/21 Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is
 * subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

import java.sql.Connection;

//#CM574718
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * This class creates storage plan list report data file and calls the print process<BR>
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
 *     Consignor code *<BR>
 *     Start Storage Plan Date<BR>
 *     End Storage Plan Date<BR>
 *     Item code<BR>
 *     Case piece type *<BR>
 *     Work Status *<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/18</TD><TD>K.Matsuda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author suresh kayamboo
 * @version $Revision 1.2 2004/09/21
 */
public class StoragePlanWriter extends CSVWriter
{
    //#CM574719
    //Constants---------------------------------------

    //#CM574720
    //Attributes--------------------------------------

    //#CM574721
    //private member variable declarations

    //#CM574722
    /**
     * Consignor code
     */
    private String wConsignorCode = "";

    //#CM574723
    /**
     * Start Storage Plan Date
     */
    private String wStartStoragePlanDate = "";

    //#CM574724
    /**
     * End Storage Plan Date
     */
    private String wEndStoragePlanDate = "";

    //#CM574725
    /**
     * Item code
     */
    private String wItemCode = "";

    //#CM574726
    /**
     * Case piece type 
     */
    private String wCasePieceFlag = "";

    //#CM574727
    /**
     * Status flag
     */
    private String wStatusFlag = "";

    //#CM574728
    /**
     * Consignor name
     */
    private String wConsignorName = "";

    //#CM574729
    //Static-----------------------------------------------------

    //#CM574730
    //Constructor------------------------------------------------
	//#CM574731
	/**
	 * Construct StoragePlanWriter object<BR>
	 * Set connection and locale<BR>
	 * @param conn <CODE>Connection</CODE> database connection object
	 */
    public StoragePlanWriter(Connection conn)
    {
        super(conn);
    }

    //#CM574732
    //Public-------------------------------------------------------
	//#CM574733
	/**
	 * Returns version of this class
	 * @return Version and Date
	 */
    public static String getVersion()
    {
        return ("$Revision: 1.6 $,$Date: 2006/12/13 09:04:08 $");
    }

	//#CM574734
	/**
	 * Set value to consignor code
	 * @param consignorCode String Consignor code
	 */
    public void setConsignorCode(String consignorCode)
    {
        wConsignorCode = consignorCode;
    }

	//#CM574735
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
    public String getConsignorCode()
    {
        return wConsignorCode;
    }

	//#CM574736
	/**
	 * Set value to Start Storage Plan Date
	 * @param startStoragePlanDate String Storage Plan Date
	 */
    public void setStartStoragePlanDate(String startStoragePlanDate)
    {
        wStartStoragePlanDate = startStoragePlanDate;
    }

	//#CM574737
	/**
	 * Fetch Start Storage Plan Date
	 * @return Start Storage Plan Date
	 */
    public String getStartStoragePlanDate()
    {
        return wStartStoragePlanDate;
    }

	//#CM574738
	/**
	 * Set value to End Storage Plan Date
	 * @param endStoragePlanDate String Storage Plan Date
	 */
    public void setEndStoragePlanDate(String endStoragePlanDate)
    {
        wEndStoragePlanDate = endStoragePlanDate;
    }

	//#CM574739
	/**
	 * Fetch End Storage Plan Date
	 * @return End Storage Plan Date
	 */
    public String getEndStoragePlanDate()
    {
        return wEndStoragePlanDate;
    }

	//#CM574740
	/**
	 * Set value to Item code
	 * @param itemCode String Item code
	 */
    public void setItemCode(String itemCode)
    {
        wItemCode = itemCode;
    }

	//#CM574741
	/**
	 * Fetch Item code
	 * @return Item code
	 */
    public String getItemCode()
    {
        return wItemCode;
    }

    //#CM574742
    /**
     * Set value to Case piece type
     * @param casePieceFlag String Case piece type 
     */
    public void setCasePieceFlag(String casePieceFlag)
    {
        wCasePieceFlag = casePieceFlag;
    }

    //#CM574743
    /**
     * Fetch Case piece type
     * @return Case piece type 
     */
    public String getCasePieceFlag()
    {
        return wCasePieceFlag;
    }

    //#CM574744
    /**
     * Set value to Status flag
     * @param statusFlag String Status flag
     */
    public void setStatusFlag(String statusFlag)
    {
        wStatusFlag = statusFlag;
    }

    //#CM574745
    /**
     * Fetch Status flag
     * @return Status flag
     */
    public String getStatusFlag()
    {
        return wStatusFlag;
    }
    
	//#CM574746
	/**
	 * Fetch print data count<BR>
	 * Used to decide whether to call the print process or not based on search result<BR>
	 * @return print data count
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	public int count() throws ReadWriteException
	{
		StoragePlanHandler storageHandle = new StoragePlanHandler(wConn);
		StoragePlanSearchKey searchKey = new StoragePlanSearchKey();
		//#CM574747
		// Set search conditions and fetch count
		setStoragePlanSearchKey(searchKey);
		return storageHandle.count(searchKey);

	}

	//#CM574748
	/**
	 * Create storage plan list csv file and call print<BR>
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
        StoragePlanReportFinder sFinder = new StoragePlanReportFinder(wConn);
        try
        {
            //#CM574749
            // execute search
            StoragePlanSearchKey sKey = new StoragePlanSearchKey();

            //#CM574750
            // Set search key
            sKey = setStoragePlanSearchKey(sKey);
			//#CM574751
			//COLLECT FIELD NAMES
			//#CM574752
			//Consignor code
			sKey.setConsignorCodeCollect("");
			//#CM574753
			//Consignor name
			sKey.setConsignorNameCollect("");
			//#CM574754
			//Storage Plan Date
			sKey.setPlanDateCollect("");
			//#CM574755
			//Item code
			sKey.setItemCodeCollect("");
			//#CM574756
			//Item name
			sKey.setItemName1Collect("");
			//#CM574757
			//Case piece type 
			sKey.setCasePieceFlagCollect("");
			//#CM574758
			//Case storage location
			sKey.setCaseLocationCollect("");
			//#CM574759
			//Piece storage location
			sKey.setPieceLocationCollect("");
			//#CM574760
			//Packed qty per case
			sKey.setEnteringQtyCollect("");
			//#CM574761
			//Packed qty per bundle
			sKey.setBundleEnteringQtyCollect("");
			//#CM574762
			//Plan qty
			sKey.setPlanQtyCollect("SUM");
			//#CM574763
			//Result qty
			sKey.setResultQtyCollect("SUM");
			//#CM574764
			//Status
			sKey.setStatusFlagCollect("");
			//#CM574765
			//Case ITF
			sKey.setItfCollect("");
			//#CM574766
			//Bundle ITF
			sKey.setBundleItfCollect("");

			//#CM574767
			//GROUP BY clause
			//#CM574768
			//Consignor code
			sKey.setConsignorCodeGroup(1);
			//#CM574769
			//Consignor name
			sKey.setConsignorNameGroup(2);
			//#CM574770
			//Plan date
			sKey.setPlanDateGroup(3);
			//#CM574771
			//Item code
			sKey.setItemCodeGroup(4);
			//#CM574772
			//Item name
			sKey.setItemName1Group(5);
			//#CM574773
			//Classification
			sKey.setCasePieceFlagGroup(6);
			//#CM574774
			//Case location
			sKey.setCaseLocationGroup(7);
			//#CM574775
			//Piece location
			sKey.setPieceLocationGroup(8);
			//#CM574776
			//Case qty
			sKey.setEnteringQtyGroup(9);
			//#CM574777
			//Bundle qty
			sKey.setBundleEnteringQtyGroup(10);
			//#CM574778
			//Status
			sKey.setStatusFlagGroup(11);
			//#CM574779
			//Case ITF
			sKey.setItfGroup(12);
			//#CM574780
			//Piece ITF
			sKey.setBundleItfGroup(13);

			//#CM574781
			//ORDER BY clause
			//#CM574782
			//Plan date
			sKey.setPlanDateOrder(1, true);
			//#CM574783
			//Item code
			sKey.setItemCodeOrder(2, true);
			//#CM574784
			//Classification
			sKey.setCasePieceFlagOrder(3, true);
			//#CM574785
			//Case location
			sKey.setCaseLocationOrder(4, true);
			//#CM574786
			//Piece location
			sKey.setPieceLocationOrder(5, true);
            
            //#CM574787
            //Check data using search key
            //#CM574788
            // Don't call print process if data does not exist
            if (sFinder.search(sKey) <= 0)
            {
                //#CM574789
                // 6003010 = Print data was not found.
                wMessage = "6003010";
                return false;
            }
			//#CM574790
			// create output file
			if (!createPrintWriter(FNAME_STRAGE_PLAN))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_STRAGE_PLAN);
            
            //#CM574791
            // to fetch consignor name
            getDisplayConsignorName();
            
			//#CM574792
			// Create data file until the search results are done
            StoragePlan[] storagePlan = null;
            while (sFinder.isNext())
            {
                //#CM574793
                // Output search result in lots of 100
                storagePlan = (StoragePlan[]) sFinder.getEntities(100);
                //#CM574794
                // Create contents of the data file output
                for (int i = 0; i < storagePlan.length; i++)
                {
					wStrText.append(re + "");

                    //#CM574795
                    // Consignor code
                    wStrText.append(ReportOperation.format(storagePlan[i].getConsignorCode()) + tb);
                    //#CM574796
                    // Consignor name
                    wStrText.append(ReportOperation.format(wConsignorName) + tb);
                    //#CM574797
                    // Storage Plan Date
                    wStrText.append(ReportOperation.format(storagePlan[i].getPlanDate()) + tb);
                    //#CM574798
                    // Item code
                    wStrText.append(ReportOperation.format(storagePlan[i].getItemCode()) + tb);
                    //#CM574799
                    // Item name
                    wStrText.append(ReportOperation.format(storagePlan[i].getItemName1()) + tb);
                    //#CM574800
                    // Classification
                    wStrText.append(ReportOperation.format(DisplayUtil.getPieceCaseValue(storagePlan[i].getCasePieceFlag())) + tb); 
                    //#CM574801
                    //Case location
                    wStrText.append(ReportOperation.format(storagePlan[i].getCaseLocation()) + tb);
                    //#CM574802
                    //Piece location
                    wStrText.append(ReportOperation.format(storagePlan[i].getPieceLocation()) + tb);
                    //#CM574803
                    // Packed qty per case
                    wStrText.append(storagePlan[i].getEnteringQty() + tb);
                    //#CM574804
                    // Packed qty per bundle
                    wStrText.append(storagePlan[i].getBundleEnteringQty() + tb);

					//#CM574805
					//Plan case qty
                    wStrText.append(DisplayUtil.getCaseQty(storagePlan[i].getPlanQty(),storagePlan[i].getEnteringQty(),
                    		storagePlan[i].getCasePieceFlag()) + tb);
					//#CM574806
					//Plan piece qty
                    wStrText.append(DisplayUtil.getPieceQty(storagePlan[i].getPlanQty(),storagePlan[i].getEnteringQty(),
                    		storagePlan[i].getCasePieceFlag()) + tb);
					//#CM574807
					//Result case qty
                    wStrText.append(DisplayUtil.getCaseQty(storagePlan[i].getResultQty(),storagePlan[i].getEnteringQty(),
							storagePlan[i].getCasePieceFlag()) + tb); 
					//#CM574808
					//Result piece qty
                    wStrText.append(DisplayUtil.getPieceQty(storagePlan[i].getResultQty(),storagePlan[i].getEnteringQty(),
							storagePlan[i].getCasePieceFlag()) + tb);
                    //#CM574809
                    //Status
                    wStrText.append(DisplayUtil.getWorkingStatusValue(storagePlan[i].getStatusFlag()) + tb);
                    //#CM574810
                    // Case ITF
                    wStrText.append(ReportOperation.format(storagePlan[i].getItf()) + tb);
                    //#CM574811
                    // Piece ITF
                    wStrText.append(ReportOperation.format(storagePlan[i].getBundleItf()) + tb);
                    //#CM574812
                    // Output data to file
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
                }
            }
            //#CM574813
            // Close the stream
            wPWriter.close();
            
            //#CM574814
            // Execute UCXSingle. (Print)
           
            if(!executeUCX(JOBID_STRAGE_PLAN)) 
            {
            	//#CM574815
            	//Print failed. Refer to the log
                return false;
            }
             
            //#CM574816
            // If print succeeds, move the data file to backup folder
            ReportOperation.createBackupFile(wFileName);
        }
        catch (ReadWriteException e)
        {
            //#CM574817
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
                //#CM574818
                // 6007034 = Print failed. Refer to the log
                setMessage("6007034");
                return false;
            }
        }
        return true;
    }

    //#CM574819
    //Protected--------------------------------------------------------------

    //#CM574820
    //Private----------------------------------------------------------------

	//#CM574821
	/**
	 * This method sets the search condition to storage plan info search key
	 * @param sKey StoragePlanSearchKey search key for 
	 * @return search key with search conditions
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
    private StoragePlanSearchKey setStoragePlanSearchKey(StoragePlanSearchKey sKey)
            throws ReadWriteException
    {
        //#CM574822
        //Consignor code
        if (!StringUtil.isBlank(wConsignorCode))
        {
            sKey.setConsignorCode(this.getConsignorCode()); //荷主コード
        }
        //#CM574823
        //Start Storage Plan Date
        if (!StringUtil.isBlank(wStartStoragePlanDate))
        {
            sKey.setPlanDate(this.getStartStoragePlanDate(), ">=");
        }
        //#CM574824
        //End Storage Plan Date
        if (!StringUtil.isBlank(wEndStoragePlanDate))
        {
            sKey.setPlanDate(this.getEndStoragePlanDate(), "<=");
        }
        //#CM574825
        //Item code
        if (!StringUtil.isBlank(wItemCode))
        {
            sKey.setItemCode(this.getItemCode());
        }
        //#CM574826
        //Case piece type 
        if (!StringUtil.isBlank(wCasePieceFlag) && !this.getCasePieceFlag().equals(StorageSupportParameter.CASEPIECE_FLAG_ALL))
        {
            sKey.setCasePieceFlag(this.getCasePieceFlag());
        }
        //#CM574827
        //Status flag
        if (!StringUtil.isBlank(wStatusFlag) && !this.getStatusFlag().equals(StorageSupportParameter.STATUS_FLAG_ALL))
        {
            sKey.setStatusFlag(this.getStatusFlag());
        }
        else
        {                
            //#CM574828
            // All
            sKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE,"<>") ;
        }

        return sKey;
    }

	//#CM574829
	/**
	 * Method to fetch the most recently registered consignor name<BR>
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
    private void getDisplayConsignorName() throws ReadWriteException
    {
        //#CM574830
        // Create finder instance
        StoragePlanReportFinder consignorFinder = new StoragePlanReportFinder(wConn);
        StoragePlanSearchKey storagePlanSearchKey = new StoragePlanSearchKey();

		//#CM574831
		// Set search key
		storagePlanSearchKey = setStoragePlanSearchKey(storagePlanSearchKey);

        storagePlanSearchKey.setRegistDateOrder(1, false);

        //#CM574832
        // Search consignor name
        consignorFinder.open();
        int nameCount = consignorFinder.search(storagePlanSearchKey);
        if (nameCount > 0)
        {
            StoragePlan[] storagePlan = (StoragePlan[]) consignorFinder.getEntities(1);

            if (storagePlan != null && storagePlan.length != 0)
            {
                wConsignorName = storagePlan[0].getConsignorName();
            }
        }
        consignorFinder.close();
    }

}
