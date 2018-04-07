package jp.co.daifuku.wms.stockcontrol.schedule;

//#CM10691
/*
 * Created on Sep 8, 2004
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.stockcontrol.report.InventoryCheckWriter;

//#CM10692
/**
 * Designer : Muneendra <BR>
 * Maker 	: Muneendra <BR>
 * <BR>
 * <CODE>InventoryListSCH</CODE> class prints Inventory Check list form.<BR>
 * Receive the contents entered via screen as a parameter and execute the process for printing the inventory check list form.<BR>
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
 * 	Receive the contents entered via screen as a parameter and print the inventory check list form using it as a condition.<BR>
 * 	Return true when the process normally completed.<BR>
 * 	Return false when error occurs during printing the report.<BR>
 * 	Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 * <BR>
 * 	<Parameter> *Mandatory Input<BR>
 * <BR>
 * 	  Consignor code*<br>
 * 	  Location �� <br>
 * 	  Item Code  <br>
 * 	  Print condition(Aggregate Item in Same Location, No aggregation)<br>
 * 	  (Qty Printed/ Qty Not Printed)
 * <BR>
 * 	<Print Process><BR>
 * 	1.Set the value that was set for parameter in the <CODE>StockControlParameter</CODE> class.<BR>
 * 	2.Print the Inventory Check list form using the <CODE>InventoryCheckWriter</CODE> class.<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/13</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/10 07:59:46 $
 * @author  $Author: suresh $
 */
public class InventoryListSCH extends AbstractStockControlSCH
{
	//#CM10693
	// Constructors --------------------------------------------------
	//#CM10694
	/**
	 * Initialize this class.
	 */
	public InventoryListSCH()
	{
		wMessage = null;
	}

	//#CM10695
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

		AreaOperator AreaOperator = new AreaOperator(conn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;

		StockReportFinder stockFinder = new StockReportFinder(conn);
		StockSearchKey searchKey = new StockSearchKey();

		StockControlParameter dispData = new StockControlParameter();

		//#CM10696
		// Search Data
		//#CM10697
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM10698
		// Ensure that the stock qty is 1 or more.
		searchKey.setStockQty(1, ">=");
		//#CM10699
		// Obtain the Area other than ASRS and add it to the search conditions.
		//#CM10700
		// Search for IS NULL if no corresponding area found.
		areaNo = AreaOperator.getAreaNo(areaType);
		searchKey.setAreaNo(areaNo);

		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		if (stockFinder.search(searchKey) == 1)
		{
			//#CM10701
			// Search Data			
			searchKey.KeyClear();
			//#CM10702
			// Stock status(Center Inventory)
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM10703
			// Ensure that the stock qty is 1 or more.
			searchKey.setStockQty(1, ">=");
			//#CM10704
			// Obtain the Consignor Name with the later added date/time.
			searchKey.setRegistDateOrder(1, false);
			//#CM10705
			// Area No
			searchKey.setAreaNo(areaNo);

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

	//#CM10706
	//	 Public methods ------------------------------------------------

	//#CM10707
	/**
	 * Receive the data entered via screen and start the schedule process.<BR>
	 * Receive the parameter in the form of array.<BR>
	 * For details on operations, enable to refer to the Explanation of class.<BR>	  
	 * Return true when the schedule process normally completed, or return false when it failed.
	 * @param conn Database connection object
	 * @param startParams Array of the parameter input via screen.
	 *         Designating a���� value other than instance throws <CODE>ScheduleException</CODE>.<BR>
	 *         Enable to refer to the error contents using <CODE>getMessage()</CODE> method. 
	 * @throws ReadWriteException  Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of checking.
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		//#CM10708
		// Assigne the parameter class to the StockControlParameter class.
		StockControlParameter stockParameter = (StockControlParameter) startParams[0];

		//#CM10709
		// Set a value for the InventoryCheckWriter class. 
		InventoryCheckWriter writer = createWriter(conn, stockParameter);
		//#CM10710
		// Set the message for every print result.
		if (writer.startPrint())
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
	
	//#CM10711
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
		
		//#CM10712
		// Set the search conditions and generate the print process class.
		InventoryCheckWriter writer = createWriter(conn, param);
		//#CM10713
		// Obtain the count of targets.
		int result = writer.count();
		if (result == 0)
		{
			//#CM10714
			// 6003010 = Print data was not found.
			wMessage = "6003010";
		}
		
		return result;
	
	}

	//#CM10715
	// Protected methods ------------------------------------------------
	
	//#CM10716
	/**
	 * Set the Information entered via screen for a print process class and
	 * Generate a print process class.<BR>
	 * 
	 * @param conn Database connection object
	 * @param stockParameter Parameter object that includes search conditions
	 * @return Print Process class
	 */
	protected InventoryCheckWriter createWriter(Connection conn, StockControlParameter stockParameter)
	{
		InventoryCheckWriter writer = new InventoryCheckWriter(conn);
		
		writer.setConsignorCode(stockParameter.getConsignorCode());
		writer.setItemCode(stockParameter.getItemCode());
		writer.setFromLocationNo(stockParameter.getFromLocationNo());
		writer.setToLocationNo(stockParameter.getToLocationNo());
		writer.setPrintCondition1(stockParameter.getPrintCondition1());
		writer.setPrintCondition2(stockParameter.getPrintCondition2());
		return writer;
	}
}
