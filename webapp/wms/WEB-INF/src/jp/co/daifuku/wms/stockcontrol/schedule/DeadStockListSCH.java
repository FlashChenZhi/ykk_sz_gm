package jp.co.daifuku.wms.stockcontrol.schedule;
//#CM10666
/*
 * Created on 2004/09/09
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM10667
//application imports
import java.sql.Connection;
import java.util.Calendar;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.stockcontrol.report.DeadStockWriter;

//#CM10668
/**
 * Designer : suresh kayamboo<BR>
 * Maker 	: suresh kayamboo<BR> 
 * <BR>
 * <CODE>DeadStockListSCH</CODE> prints the Old inventory list.<BR>
 * Receive the contents entered via screen as a parameter and  executes the process for printing the Old inventory list.<BR>
 * Each method in this class receives a connection object and executes the process for updating the database.<BR>
 * However, each method disables to commit and roll back of transactions.<BR>
 * This class executes the following processes.<BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method)
 * <DIR>
 * 	Search for Consignor Code through the Inventory information. If only the same Consignor Code exists:<BR>
 * 	Return the Consignor Code.<BR>
 * 	Return null if two or more Consignor Code exist.<BR>
 * </DIR>
 * <BR>
 * 2.Process by clicking on Print button (<CODE>startSCH()</CODE>Method)<BR>
 * <DIR>
 * 	Receive the contents entered via screen as a parameter and print the Old inventory List using it as a condition.<BR>
 * 	Return true when the process normally completed.<BR>
 * 	Return false when error occurs during printing the report.<BR>
 * 	Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 * <BR>
 * 	<Parameter> *Mandatory Input<BR>
 * <BR>
 * 	Consignor code*<BR>
 * 	Storage Date*<BR>
 * <BR>
 * 	<Print Process><BR>
 * 	1.Set the value that was set for parameter in the <CODE>StockControlParameter</CODE> class.<BR>
 * 	2.Print the Old inventory List using <CODE>DeadStockWriter</CODE> class.<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/29</TD><TD>suresh kayamboo</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author suresh kayamboo
 * @version $Revision 1.2 2004/09/09
 */
public class DeadStockListSCH extends AbstractStockControlSCH
{	
	//#CM10669
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * For example,initial display of consignor codes, initial display of button to extract external data.<BR>
	 * Search condition passes a class that inherits the <CODE>Parameter</CODE> class.<BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Database connection object
	 * @param searchParam This Class inherits the <CODE>Parameter</CODE> class with search conditions
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{

		StockReportFinder stockFinder = new StockReportFinder(conn);
		StockSearchKey searchKey = new StockSearchKey();

		StockControlParameter dispData = new StockControlParameter();

		//#CM10670
		// Search Data
		//#CM10671
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM10672
		// Ensure that the stock qty is 1 or more.
		searchKey.setStockQty(1, ">=");

		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		if (stockFinder.search(searchKey) == 1)
		{
			//#CM10673
			// Search Data			
			searchKey.KeyClear();
			//#CM10674
			// Stock status(Center Inventory)
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM10675
			// Ensure that the stock qty is 1 or more.
			searchKey.setStockQty(1, ">=");
			//#CM10676
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

	//#CM10677
	/**
	 * Start the schedule. According to the contents set in the parameter array designated in the <CODE>startParams</CODE>, <BR>
	 * execute the process for the schedule. The implement for scheduling depends on the class implementing this interface.<BR>
	 * Return true when the schedule process completed successfully.<BR>
	 * Return false if failed to schedule due to condition error or other causes.<BR>
	 * In such case,enable to obtain the contents using <CODE>getMessage()</CODE> method.
	 * @param conn Database connection object
	 * @param startParams Search condition object to search across the database.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		StockControlParameter sParam = (StockControlParameter)startParams[0] ;
		
		DeadStockWriter dsW = createWriter(conn, sParam);

		//#CM10678
		// Execute printing.
		if(dsW.startPrint())
		{
			//#CM10679
			// 6001010=Print completed successfully.
			wMessage="6001010" ;
			return false;
		}
		else
		{
			wMessage = dsW.getMessage();
			return true;
		}	
	}
	
	//#CM10680
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
		
		//#CM10681
		// Set the search conditions and generate the print process class.
		DeadStockWriter writer = createWriter(conn, param);
		//#CM10682
		// Obtain the count of targets.
		int result = writer.count();
		if (result == 0)
		{
			//#CM10683
			// 6003010 = Print data was not found.
			wMessage = "6003010";
		}
		
		return result;
	
	}

	//#CM10684
	// Protected methods ------------------------------------------------
	
	//#CM10685
	/**
	 * Set the Information entered via screen for a print process class and
	 * Generate a print process class.<BR>
	 * 
	 * @param conn Database connection object
	 * @param sParam Parameter object that includes search conditions
	 * @return Print Process class
	 */
	protected DeadStockWriter createWriter(Connection conn, StockControlParameter sParam)
	{
		DeadStockWriter dsW = new DeadStockWriter(conn) ;
						
		
		//#CM10686
		// Set the Consignor code.			
		dsW.setConsignorCode(sParam.getConsignorCode());
		
		
		//#CM10687
		// Set the storage date.
		//#CM10688
		// Set forward the date one day from the date input via screen and obtain the stock with a value of the storage date/time smaller than it.
		//#CM10689
		// Set the time to 00: 00: 00.
		Calendar compareDate = Calendar.getInstance();
			
		compareDate.setTime(sParam.getStorageDate());
		compareDate.set(Calendar.HOUR_OF_DAY, 0);
		compareDate.set(Calendar.MINUTE, 0);
		compareDate.set(Calendar.SECOND, 0);
		compareDate.add(Calendar.DAY_OF_MONTH, 1);
			
			
		//#CM10690
		//Set the storage date for DeadStockWriter.
		dsW.setStorageDate(compareDate.getTime()) ;					
		return dsW;
	}
}
