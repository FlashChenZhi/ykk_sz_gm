package jp.co.daifuku.wms.stockcontrol.schedule;

//#CM10717
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.stockcontrol.report.ItemWorkingInquiryWriter;

//#CM10718
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * This class allows to execute process for stock inquiry by item. <BR>
 * Receive the contents entered via screen as a parameter and execute the process for inquiring the stock by item. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method) <BR> 
 * <BR>
 * <DIR>
 *   If only one Consignor code exist in the inventory information <CODE> (Stock)</CODE>, return the corresponding Consignor code. <BR> 
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR> 
 *   <BR>
 *   <Search conditions> <BR> 
 *   <BR>
 *   Inventory Status is in Center Inventory <BR>
 *   Stock qty is 1 or larger <BR>
 * </DIR>
 * <BR>
 * 2.Process by clicking on Print button (<CODE>startSCH()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and , <BR>
 *   If one or more print target data exist, <BR>
 *   Print the Stock List by item using the <CODE>ItemWorkingInquiryWriter</CODE> class<BR>
 *   <BR>
 *   <Parameter> *Mandatory Input <BR>
 *   <BR>
 *   <DIR>
 *   Consignor code* <BR>
 *   Start Item Code <BR>
 *   End Item Code <BR>
 *   Case/Piece division* <BR>
 *   </DIR>
 *   <BR>
 *   <Returned data> <BR>
 *   <BR>
 *   <DIR>
 *   Search qty <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>K.Toda</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version	$Revision: 1.2 $, $Date: 2006/10/10 07:58:57 $
 * @author		$Author: suresh $
 */
public class ItemWorkingInquirySCH extends AbstractStockControlSCH
{

	//#CM10719
	// Class variables -----------------------------------------------

	//#CM10720
	// Class method --------------------------------------------------
	//#CM10721
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $, $Date: 2006/10/10 07:58:57 $");
	}

	//#CM10722
	// Constructors --------------------------------------------------
	//#CM10723
	/**
	 * Initialize this class.
	 */
	public ItemWorkingInquirySCH()
	{
		wMessage = null;
	}

	//#CM10724
	// Public methods ------------------------------------------------

	//#CM10725
	/**
	 * This method supports operations to obtain the data required for initial display. <BR>
	 * If only one Consignor code exist in the inventory information <CODE> (Stock)</CODE>, return the corresponding Consignor code. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Database connection object
	 * @param searchParam This Class inherits the <CODE>Parameter</CODE> class with search conditions
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
	
		StockReportFinder stockFinder = new StockReportFinder(conn);
		StockSearchKey searchKey = new StockSearchKey();

		StockControlParameter dispData = new StockControlParameter();

		//#CM10726
		// Search Data
		//#CM10727
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM10728
		// Ensure that the stock qty is 1 or more.
		searchKey.setStockQty(1, ">=");

		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		if (stockFinder.search(searchKey) == 1)
		{
			//#CM10729
			// Search Data			
			searchKey.KeyClear();
			//#CM10730
			// Stock status(Center Inventory)
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM10731
			// Ensure that the stock qty is 1 or more.
			searchKey.setStockQty(1, ">=");
			//#CM10732
			// Obtain the Consignor Name with the later added date/time.
			searchKey.setRegistDateOrder(1, false);
			
			searchKey.setConsignorCodeCollect();
			searchKey.setConsignorNameCollect();

			if (stockFinder.search(searchKey) > 0)
			{
				Stock[] consignorname = (Stock[]) stockFinder.getEntities(1);

				dispData.setConsignorCode(consignorname[0].getConsignorCode());
				dispData.setConsignorName(consignorname[0].getConsignorName());
				
			}
		}
		stockFinder.close();

		return dispData;
	}

	//#CM10733
	/**
	 * Receive the contents entered via screen as a parameter and , <BR>
	 * Using the ItemWorkingInquiryWriter class, execute the process for printing stock list by item. <BR>    
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>StockControlParameter</CODE> class instance with commitment contents.
	 *         Designating any instance other than <CODE>StockControlParameter</CODE> throws ScheduleException.<BR>
	 *         Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			StockControlParameter stockParam = null;
		
			//#CM10734
			// Enable to pass only one parameter. Accordingly, obtain the leading data.
			stockParam = (StockControlParameter) startParams[0];
		
			//#CM10735
			// Generate Writer class instance
			ItemWorkingInquiryWriter writer = createWriter(conn, stockParam);
			
			//#CM10736
			// Set the message for every print result.
			if(writer.startPrint())
			{
				wMessage = "6001010";
				return true;
			}
			else
			{
				wMessage = writer.getMessage();
				return false;
			}
		}
		catch(Exception e)
		{
			//#CM10737
			// Write the error detail into the log file.
			RmiMsgLogClient.write(new TraceHandler(6027005, e), "ItemWorkingInquirySCH");
			//#CM10738
			// Set the message. 6027005 = Internal error occurred. See log.
			wMessage = "6027005";
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM10739
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
		
		//#CM10740
		// Set the search conditions and generate the print process class.
		ItemWorkingInquiryWriter writer = createWriter(conn, param);
		//#CM10741
		// Obtain the count of targets.
		int result = writer.count();
		if (result == 0)
		{
			//#CM10742
			// 6003010 = Print data was not found.
			wMessage = "6003010";
		}
		
		return result;
	
	}

	//#CM10743
	// Package methods -----------------------------------------------

	//#CM10744
	// Protected methods ---------------------------------------------
	
	//#CM10745
	/**
	 * Set the Information entered via screen for a print process class and
	 * Generate a print process class.<BR>
	 * 
	 * @param conn Database connection object
	 * @param stockParam Parameter object that includes search conditions
	 * @return Print Process class
	 */	
	protected ItemWorkingInquiryWriter createWriter(Connection conn, StockControlParameter stockParam)
	{
		ItemWorkingInquiryWriter writer = new ItemWorkingInquiryWriter(conn);
		
		//#CM10746
		// Set the value for Writer class of the parameter.
		writer.setConsignorCode(stockParam.getConsignorCode());
		writer.setFromItemCode(stockParam.getFromItemCode());
		writer.setToItemCode(stockParam.getToItemCode());
		//#CM10747
		// Case/Piece division All disable to set anything.
		if(stockParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
		{
			writer.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
		}
		else if(stockParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
		{
			writer.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
		}
		else if(stockParam.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			writer.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
		}
		return writer;
	}

	//#CM10748
	// Private methods -----------------------------------------------
}
//#CM10749
//end of class
