//#CM699598
//$Id: WorkQtyInquirySCH.java,v 1.2 2006/11/13 06:03:03 suresh Exp $

//#CM699599
/*
 * Created on 2004/08/05
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.system.report.WorkerQtyInquiryWriter;
//#CM699600
/**
 * Designer : suresh kayamboo<BR>
 * Maker 	: suresh kayamboo<BR> 
 *
 * <CODE>WorkQtyInquirySCH</CODE><BR>
 * Allow this class to invoke the Result Inquiry by Worker.<BR>
 * Receive the contents entered via screen as a parameter and execute the process for inquiring the Result by Worker.<BR>
 * 
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Initial Display Process<BR>
 *   Return the work type according to the introduced package.<BR>
 * <BR>
 * <DIR>
 * 	<Parameter> Returned data<BR>
 * </DIR>
 * 		Work type <BR>
 * 		<DIR>	
 * 		[ All (All)<BR>
 *			Receiving (Arrival)<BR>
 *			Storage (Storage)<BR>
 *			Picking (Retrieval)<BR>
 *			Sorting (Sorting)<BR>
 *			Shipping (Shipping)<BR>
 *			Inventory Check (Location)<BR>
 *			Increase Inventory Check <BR>
 *			Decrease Inventory Check <BR>
 *			Relocation for Storage (RelocateforStorage)<BR>
 *			Relocation for Retrieval (RelocateforRetrieval)<BR>
 *			Unplanned Storage (ExceptionalStorage)<BR>
 *			Unplanned Picking (ExceptionalRetrieval)<BR>
 *			Increase in Inventory Maintenance <BR>
 *			Decrease in Inventory Maintenance <BR>
 *		]<BR>		
 * </DIR>
 * <BR>
 * 2.Process by clicking "Print" button<BR>
 * <DIR>
 * 		Pass the <CODE>startSCH</CODE> input parameter to <CODE>startParams</CODE>.<BR>
 * 		Print the result that was passed to the input parameter. The result parameters are:<BR>
 * 		
 * 		Case : 1 (to display the total within the period)<BR>
 * 		<DIR>
 * 			gather the following parameters as one record in a HTML table and print it.<BR>
 *			
 *			Worker Code (WorkerCode)<BR>
 *			Worker Name (WorkerName)<BR>
 *			Work type (JobType) <BR>
 *			Work Time (WorkTime) in HHH:MM:SS format<BR>
 *			Work Quantity (WorkQty) <BR>
 *			Work Count (WorkCnt) <BR>
 *			Work Quantity/h (WorkQty/hour) <BR>
 *			Work Count/h (WorkCnt/hour) <BR>
 *			
 *		</DIR>
 *		<BR>
 *		
 *		Case : 2 (to display the daily total)<BR>
 *		<DIR>
 * 			Print the following parameters.<BR>
 *			
 *			Work date (WorkDate) in YYYY/mm/DD format<BR>
 *			Worker Code (WorkerCode)<BR>
 *			Worker Name (WorkerName)<BR>
 *			Work type (JobType) <BR>
 *			Start Time (StartTime) in HH:MM:SS format<BR>
 *			End Time (EndTime) in HH:MM:SS format<BR>
 *			Work Time (WorkTime) in HHH:MM:SS format<BR>
 *			Work Quantity (WorkQty) <BR>
 *			Work Count (WorkCnt) <BR>
 *			Work Quantity/h (WorkQty/hour) <BR>
 *			Work Count/h (WorkCnt/hour) <BR>
 *		</DIR>
 *		<BR>
 *
 *		Case : 3 (to display the detail)<BR>
 *		<DIR>
 * 			Display the following parameters in the screen.<BR>
 *			
 *			Work date (WorkDate)<BR>
 *			Worker Code (WorkerCode)<BR>
 *			Worker Name (WorkerName)<BR>
 *			Work type (JobType) <BR>
 *			Start Time (StartTime) <BR>
 *			End Time (EndTime) <BR>
 *			Work Time (WorkTime) in HH:MM:SS<BR>
 *			Work Quantity (WorkQty) <BR>
 *			Work Count (WorkCnt) <BR>
 *			Work Quantity/h (WorkQty/hour) <BR>
 *			Work Count/h (WorkCnt/hour) <BR>
 *			RFTNo	(TerminalNo) <BR>
 *		</DIR>
 *		<BR>
 * 		Return <CODE>startSCH</CODE> true if normal. Or, return false if error.<BR>
 * </DIR> 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>suresh kayamboo</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:03 $
 * @author  $Author: suresh $
 */
public class WorkQtyInquirySCH extends AbstractSystemSCH
{
	//#CM699601
	/**
	 * Return the version of this class.
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $. $Date: 2004/08/16 ");
	}

	//#CM699602
	/**
	 * Initialize this class.
	 */
	public WorkQtyInquirySCH()
	{
		wMessage = null;
	}

	//#CM699603
	/**
	 * Obtain the data to be displayed on screen. Allow this method to support the operation for initial display etc.<BR>
	 * Search conditions passes a class that inherits the<CODE>Parameter</CODE> class.<BR>
	 * Search through the work type corresponding to each package.<BR>
	 * Receiving package 	: Receiving 01<BR>
	 * Shipping package 	: Shipping 05<BR>
	 * Sorting package	: Sorting 04<BR>
	 * Picking package	: Picking 03<BR>
	 * Storage package	: Storage 02,Relocation for Storage 12,Relocation for Retrieval 11,Inventory Check 40 (Increase Inventory Check 41,Decrease Inventory Check 42)<BR>
	 * Inventory package	: Unplanned Storage 21,Unplanned Picking 22<BR>
	 * Generals				: All, Maintenance (Increase in Maintenance 31,Decrease in Maintenance 32)<BR>
	 * @param conn        Database connection object<BR>
	 * @param searchParam Array of the instance of the <CODE>SystemParameter</CODE> class with contents of setting.<BR>
	 * @return Array of work type.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public Parameter[] query(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		SystemParameter[] sysParam = null;
		Vector vec = new Vector();
		boolean flag = false;

		//#CM699604
		// Generate an instance of System definition info handlers.
		WareNaviSystemHandler wareNaviSystemHandler = new WareNaviSystemHandler(conn);
		WareNaviSystemSearchKey wareNaviSystemSearchKey = new WareNaviSystemSearchKey();
		WareNaviSystem wareNaviSystem = null;
		try
		{
			wareNaviSystem =
				(WareNaviSystem) wareNaviSystemHandler.findPrimary(wareNaviSystemSearchKey);
			//#CM699605
			// All
			vec.addElement(SystemParameter.SELECTWORKDETAIL_ALL); 

			if ((wareNaviSystem != null) && !wareNaviSystem.equals(""))
			{
				if (isInstockPack(conn))
				{
					//#CM699606
					// Receiving
					vec.addElement(SystemParameter.SELECTWORKDETAIL_INSTOCK);			
				}
				if (isStoragePack(conn))
				{
					//#CM699607
					// Storage
					vec.addElement(SystemParameter.SELECTWORKDETAIL_STORAGE);
					flag = true;
				}
				if (isRetrievalPack(conn))
				{
					//#CM699608
					// Picking
					vec.addElement(SystemParameter.SELECTWORKDETAIL_RETRIEVAL);
				}
				if (isSortingPack(conn))
				{
					//#CM699609
					// Sorting
					vec.addElement(SystemParameter.SELECTWORKDETAIL_SORTING);
				}
				if (isShippingPack(conn))
				{
					//#CM699610
					// Shipping
					vec.addElement(SystemParameter.SELECTWORKDETAIL_SHIPPING);
				}
				if (flag)
				{
					//#CM699611
					// Inventory Check
					vec.addElement(SystemParameter.SELECTWORKDETAIL_INVENTORY);
					//#CM699612
					// Increase Inventory Check
					vec.addElement(SystemParameter.SELECTWORKDETAIL_INVENTORY_INCREASE);
					//#CM699613
					// Decrease Inventory Check
					vec.addElement(SystemParameter.SELECTWORKDETAIL_INVENTORY_DECREASE);
					//#CM699614
					// Relocation for Storage
					vec.addElement(SystemParameter.SELECTWORKDETAIL_MOVEMENT_STORAGE);
					//#CM699615
					// Relocation for Retrieval
					vec.addElement(SystemParameter.SELECTWORKDETAIL_MOVEMENT_RETRIEVAL);
				}
				if (isStockPack(conn))
				{
					//#CM699616
					// Unplanned Storage
					vec.addElement(SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDSTORAGE);
					//#CM699617
					// Unplanned Picking
					vec.addElement(SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDRETRIEVAL);
					//#CM699618
					// Increase in Inventory Maintenance
					vec.addElement(SystemParameter.SELECTWORKDETAIL_MAINTENANCE_INCREASE);
					//#CM699619
					// Decrease in Inventory Maintenance
					vec.addElement(SystemParameter.SELECTWORKDETAIL_MAINTENANCE_DECREASE);
				}

				wMessage = "6001013";
			}
		}
		catch (NoPrimaryException se)
		{
			wMessage = "6006001" + "";
			throw new ScheduleException("This method is not supported");
		}

		sysParam = new SystemParameter[vec.size()];

		for (int i = 0; i < vec.size(); i++)
		{
			sysParam[i] = new SystemParameter();
			sysParam[i].setSelectWorkDetail(vec.get(i).toString());
		}

		return sysParam;
	}

	//#CM699620
	/**
	 * Receive the contents entered via screen as a parameter and pass the parameter to the class for generating a file of Result list by worker.<BR>
	 * Disables to print with no print data.<BR>
	 * Receive true if printing succeeded or false if failed, from the class for the process of printing a work report and return the result.
	 * <BR>
	 * Use the <CODE>getMessage()</CODE> method to refer the content of error.<BR>
	 * 
	 * @param conn        Database connection object
	 * @param startParams Array of the instance of the <CODE>SystemParameter</CODE> class with contents of setting.
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		boolean isOk = false;

		//#CM699621
		// Translate the type of startParams.
		SystemParameter[] sysParam = (SystemParameter[]) startParams;

		if ((sysParam == null) || (sysParam.length == 0))
		{
			wMessage = "6003010";
			return false;
		}

		//#CM699622
		// Generate an instance for the class that generates a file of the Result list by worker.
		WorkerQtyInquiryWriter wQiw = createWriter(conn, sysParam[0]);

		//#CM699623
		// Execute printing.
		isOk = wQiw.startPrint();

		if (isOk)
		{
			wMessage = "6001010";
		}
		else
		{
			wMessage = wQiw.getMessage();
		}
		return isOk;
	}
	
	//#CM699624
	/** 
	 * Obtain the Count of print targets based on the info entered via screen.<BR>
	 * If no target data found, or if input error found, return 0 (count).<BR>
	 * When 0 data is found, obtain the error message using <CODE>getMessage</CODE> in the process by the invoking source.
	 * <BR>
	 * 
	 * @param conn       Database connection object
	 * @param countParam <CODE>Parameter</CODE> object that includes search conditions.
	 * @return Count of print targets
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs.
	 */
	public int count(Connection conn, Parameter countParam) throws ReadWriteException, ScheduleException
	{
		SystemParameter param = (SystemParameter) countParam;
		
		//#CM699625
		// Set the search conditions and generate the print process class.
		WorkerQtyInquiryWriter writer = createWriter(conn, param);
		//#CM699626
		// Obtain the count of target data.
		int result = writer.count();
		if (result == 0)
		{
			//#CM699627
			// 6003010=No print data found.
			wMessage = "6003010";
		}
		
		return result;
	
	}
	
	//#CM699628
	// Protected methods ------------------------------------------------
	
	//#CM699629
	/** 
	 * Set the Information entered via screen for a print process class and
	 * generate a print process class.<BR>
	 * @param conn      Database connection object
	 * @param parameter Parameter object that includes search conditions
	 * @return A class to execute printing process.
	 */
	protected WorkerQtyInquiryWriter createWriter(Connection conn, SystemParameter param)
	{
		//#CM699630
		// Set the print condition input via screen for print process class.
		WorkerQtyInquiryWriter writer = new WorkerQtyInquiryWriter(conn);
		
		writer.setWorkType(param.getSelectWorkDetail());
		writer.setStartWorkDate(param.getFromWorkDate());
		writer.setEndWorkDate(param.getToWorkDate());
		writer.setWorkerCode(param.getWorkerCode());
		writer.setDispType(param.getSelectAggregateCondition());
		
		return writer;
	}
}
//#CM699631
//end of class
