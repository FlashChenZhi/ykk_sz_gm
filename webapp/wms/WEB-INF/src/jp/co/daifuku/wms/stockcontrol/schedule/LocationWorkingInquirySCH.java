package jp.co.daifuku.wms.stockcontrol.schedule;

//#CM10750
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.stockcontrol.report.StockWorkingInquiryWriter;

//#CM10751
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * This class executes processes for stock inquiry by WEB location. <BR>
 * Receive the contents entered via screen as a parameter and execute the process for inquiring the stock by location. <BR>
 * Each method in this class receives a connection object and executes the process for updating the database. <BR>
 * However, each method disables to commit and roll back of transactions. <BR> 
 * This class executes the following processes.<BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method) <BR> 
 * <BR>
 * <DIR>
 *   If only one Consignor code exist in the inventory information, return the corresponding Consignor code.<BR> 
 *   Return null if no corresponding data found, or two or more corresponding data exist.<BR> 
 *   <BR>
 *   [Search conditions] <BR>
 *   <DIR>
 *     Stock status��Center Inventory<BR>
 *     Stock qty is 1 or larger <BR>
 *     Inventory other than AS/RS <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Process by clicking on Print button (<CODE>startSCH()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and startup the process for printing the stock list by location. <BR>
 *   Check for presence of print target info in the parameter contents. If no target data, throw error.<BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 *   <BR>
 *   [Parameter] *Mandatory Input<BR>
 *   <DIR>
 *     Consignor code* <BR>
 *     Start Location <BR>
 *     End Location <BR>
 *     Item Code <BR>
 *     Case/Piece division <BR>
 *   </DIR>
 *   <BR>
 *   [Returned data] <BR>
 *   <DIR>
 *     Result notification <BR>
 *   </DIR>
 *   <BR>
 *   [Check for process condition] <BR>
 *   2-1.Obtain the count of the target info from the inventory information using the following conditions. <BR>
 *   <DIR>
 *     [Search conditions] <BR> 
 *     <DIR>
 *       condition designated in the parameter <BR>
 *       Corresponding to the Consignor code. <BR>
 *       Equal to Start Location No. or larger <BR>
 *       Equal to End Location No. or smaller <BR>
 *       Concordant with the Item Code <BR>
 *       Concordant with the Case/Piece division <BR>
 *       Stock status��Center Inventory<BR>
 *       Stock qty is 1 or larger <BR>
 *     	 Inventory other than AS/RS <BR>
 *     </DIR>
 *     If no target count mentioned above, restore "No target data". (by message)
 *   </DIR>
 *   <BR>
 *   [Print Process] <BR>
 *   Print the Stock List by location using the <CODE>StockWorkingInquiryWriter</CODE> class.<BR>
 *   <DIR>
 *     <BR>
 *     [Parameter] *Mandatory Input<BR>
 *     <DIR>
 *       Consignor code* <BR>
 *       Start Location <BR>
 *       End Location <BR>
 *       Item Code <BR>
 *       Case/Piece division <BR>
 *     </DIR>
 *     <BR>
 *     [Returned data] <BR>
 *     <DIR>
 *       Printing result* <BR>
 *     </DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * Disable to use the following methods in this process. <BR>
 * Invoking this throws <CODE>ScheduleException</CODE>. <BR>
 * <DIR>
 *   Process by clicking on Display button (<CODE>Query()</CODE> Method)<BR>
 *   Method for updating the info and starting-up the list process: with return info (<CODE>startSCHgetParams()</CODE> method) <BR> 
 *   This method allows to check the shift to the next screen (<CODE>nextCheck()</CODE> method) <BR> 
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/14</TD><TD>C.Kaminishizono</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/10 07:58:23 $
 * @author  $Author: suresh $
 */
public class LocationWorkingInquirySCH extends AbstractStockControlSCH
{

	//#CM10752
	// Class variables -----------------------------------------------

	//#CM10753
	/**
	 * Class Name(Delete Picking Plan)
	 */
	public static String CLASS_SHIPPING = "LocationWorkingInquirySCH";

	//#CM10754
	// Class method --------------------------------------------------
	//#CM10755
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/10 07:58:23 $");
	}

	//#CM10756
	// Constructors --------------------------------------------------
	//#CM10757
	/**
	 * Initialize this class.
	 */
	public LocationWorkingInquirySCH()
	{
		wMessage = null;
	}

	//#CM10758
	// Public methods ------------------------------------------------

	//#CM10759
	/**
	 * This method supports operations to obtain the data required for initial display. <BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ". <BR>
	 * Invoking this process in unavailable case throws <CODE>ScheduleException</CODE>. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. <BR>
	 * @param conn Database connection object
	 * @param searchParam This Class inherits the <CODE>Parameter</CODE> class with search conditions
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();

		StockControlParameter dispData = new StockControlParameter();

		AreaOperator AreaOperator = new AreaOperator(conn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
		
		//#CM10760
		// Search Data
		//#CM10761
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM10762
		// Ensure that the stock qty is 1 or more.
		searchKey.setStockQty(1, ">=");
		//#CM10763
		// Obtain the Area other than ASRS and add it to the search conditions.
		//#CM10764
		// Search for IS NULL if no corresponding area found.
		areaNo = AreaOperator.getAreaNo(areaType);
		searchKey.setAreaNo(areaNo);

		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		int count = stockHandler.count(searchKey);
		if (count == 1)
		{
			try
			{
				Stock[] stock = (Stock[]) stockHandler.find(searchKey);
				dispData.setConsignorCode(stock[0].getConsignorCode());
			}
			catch (Exception e)
			{
				return new StockControlParameter();
			}
		}

		return dispData;	
	}

	//#CM10765
	/**
	 * Receive the contents entered via screen as a parameter and start-up the processes for updating the mandatory info and list. <BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ". <BR>
	 * Invoking this process in unavailable case throws <CODE>ScheduleException</CODE>. <BR>
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>StockControlParameter</CODE> class instance with commitment contents. <BR>
	 *         Designating any instance other than <CODE>StockControlParameter</CODE> throws ScheduleException. <BR>
	 *         Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		StockControlParameter rparam = (StockControlParameter) startParams[0];

		//#CM10766
		// Stock list by location
		StockWorkingInquiryWriter sdWriter = createWriter(conn, rparam);

		if (sdWriter.startPrint())
		{
			//#CM10767
			// Printing has been normally completed.
			wMessage = "6001010";
			return true;
		}
		else
		{
			wMessage = sdWriter.getMessage();
			return false;
		}

	}
	
	//#CM10768
	/**
	 * Obtain the Count of print targets based on the info entered via screen.<BR>
	 * If no target data found, or if input error found, return 0 (count).<BR>
	 * When 0 count is found, use <CODE>getMessage</CODE> in the process by the invoking source.
	 * Obtain the error message.<BR>
	 * 
	 * @param conn Database connection object
	 * @param countParam <CODE>Parameter</CODE> object that includes search conditions
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 * @return Count of print targets
	 */
	public int count(Connection conn, Parameter countParam) throws ReadWriteException, ScheduleException
	{
		StockControlParameter param = (StockControlParameter) countParam;
		
		//#CM10769
		// Set the search conditions and generate the print process class.
		StockWorkingInquiryWriter writer = createWriter(conn, param);
		//#CM10770
		// Obtain the count of targets.
		int result = writer.count();
		if (result == 0)
		{
			//#CM10771
			// 6003010 = Print data was not found.
			wMessage = "6003010";
		}
		
		return result;
	
	}

	//#CM10772
	// Package methods -----------------------------------------------

	//#CM10773
	// Protected methods ---------------------------------------------
	
	//#CM10774
	/**
	 * Set the Information entered via screen for a print process class and
	 * Generate a print process class.<BR>
	 * 
	 * @param conn Database connection object
	 * @param parameter Parameter object that includes search conditions
	 * @return Print Process class
	 */
	protected StockWorkingInquiryWriter createWriter(Connection conn, StockControlParameter rparam)
	{
		StockWorkingInquiryWriter sdWriter = new StockWorkingInquiryWriter(conn);
		
		sdWriter.setConsignorCode(rparam.getConsignorCode());
		sdWriter.setFromLocationNo(rparam.getFromLocationNo());
		sdWriter.setToLocationNo(rparam.getToLocationNo());
		sdWriter.setItemCode(rparam.getItemCode());
		if (rparam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
		{
			sdWriter.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
		}
		else if (rparam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
		{
			sdWriter.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
		}
		else if (rparam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			sdWriter.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
		}
		return sdWriter;
	}

	//#CM10775
	// Private methods -----------------------------------------------

}
//#CM10776
// end of class
