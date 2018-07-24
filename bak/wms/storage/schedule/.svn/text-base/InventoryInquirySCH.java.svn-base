package jp.co.daifuku.wms.storage.schedule;

//#CM584
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.storage.report.InventoryInquiryWriter;

//#CM585
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * This class invokes the class for printing the Inventory Check Work List list.<BR>
 * Receive the contents entered via screen as a parameter and invoke the class for printing the list.<BR>
 * This class executes the following processes.<BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method)<BR>
 * <DIR>
 *   If only one Consignor Code exists in Inventory Check Work status, return the corresponding Consignor Code.<BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist.<BR>
 * <BR>
 *   <Search conditions>
 *   <DIR>
 *     Process flag:Not Submitted<BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.Process by clicking on Print button (<CODE>startSCH()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and pass the parameter to the class for printing the inventory check work list.<BR>
 *   Search for the printing contents in the Writer class.<BR>
 *   If successfully printed, receive true from the class for printing the work list. If failed, receive false<BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR>
 *   <DIR>
 *     Consignor code* <BR>
 *     Start Location <BR>
 *     End picking location <BR>
 *     Item Code <BR>
 *     Status <BR>
 *   </DIR>
 *   <BR>
 *   <Check for process condition>
 *   <DIR>
 *     None<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/07</TD><TD>Y.Okamura</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/11 06:35:57 $
 * @author  $Author: suresh $
 */
public class InventoryInquirySCH extends AbstractStorageSCH
{

	//#CM586
	// Class variables -----------------------------------------------
	//#CM587
	// Class method --------------------------------------------------
	//#CM588
	/**
	 * Return the version of this class.
	 * 
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/11 06:35:57 $");
	}
	//#CM589
	// Constructors --------------------------------------------------
	//#CM590
	/**
	 * Initialize this class.
	 */
	public InventoryInquirySCH()
	{
		wMessage = null;
	}

	//#CM591
	// Public methods ------------------------------------------------
	//#CM592
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor Code exists in Inventory Check Work status, return the corresponding Consignor Code.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.<BR>
	 * Requiring no search conditions sets null for searchParam. <BR>
	 * <BR>
	 * 
	 * @param conn Connection Database connection object
	 * @param searchParam Parameter This Class inherits the <CODE>Parameter</CODE> class with search conditions
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		StorageSupportParameter param = new StorageSupportParameter();
		InventoryCheckSearchKey inventoryKey = new InventoryCheckSearchKey();
		InventoryCheckHandler inventoryHandler = new InventoryCheckHandler(conn);

		try
		{
			//#CM593
			// Process flag:Not Submitted
			inventoryKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
			//#CM594
			// Use the Consignor Code for aggregation condition and obtaining condition.
			inventoryKey.setConsignorCodeGroup(1);
			inventoryKey.setConsignorCodeCollect("");

			if (inventoryHandler.count(inventoryKey) == 1)
			{
				//#CM595
				// Search for the Consignor Code.
				InventoryCheck inventoryCheck = (InventoryCheck) inventoryHandler.findPrimary(inventoryKey);

				//#CM596
				// Set the search result for the return value.
				param.setConsignorCode(inventoryCheck.getConsignorCode());
			}

		}
		catch (NoPrimaryException pe)
		{
			return param;
		}
		return param;

	}

	//#CM597
	/**
	 * Receive the contents entered via screen as a parameter and pass the parameter to the class for printing the View list.<BR>
	 * Disables to print with no print data.<BR>
	 * If successfully printed, receive true from the class for printing the Work list. If failed, receive false 
	 * Return the result.<BR>
	 * Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
	 * 
	 * @param conn Connection Database connection object
	 * @param startParams Parameter[] Array of <CODE>StorageSupportParameter</CODE> class instance with contents of commitment.<BR>
	 * Designating any instance other than <CODE>StorageSupportParameter</CODE> instance throws <CODE>ScheduleException</CODE>.
	 * 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 * 
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		StorageSupportParameter param = null;

		//#CM598
		// Enable to pass only one parameter. Accordingly, obtain the leading data.
		param = (StorageSupportParameter) startParams[0];

		//#CM599
		// Set the print condition input via screen for print process class.
		InventoryInquiryWriter writer = createWriter(conn, param);

		//#CM600
		// Start the printing process.
		if (writer.startPrint())
		{
			//#CM601
			// 6001010 = Print completed successfully.
			wMessage = "6001010";
			return true;
		}
		else
		{
			//#CM602
			// Display the error message.
			wMessage = writer.getMessage();
			return false;
		}
	}
	
	//#CM603
	/**
	 * Obtain the Count of print targets based on the info entered via screen.<BR>
	 * If no target data found, or if input error found, return 0 (count).<BR>
	 * When 0 count is found, use <CODE>getMessage</CODE> in the process by the invoking source and
	 * obtain the error message.<BR>
	 * 
	 * @param conn Database connection object
	 * @param countParam <CODE>Parameter</CODE> object that includes search conditions
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 * @return Count of print targets
	 */
	public int count(Connection conn, Parameter countParam) throws ReadWriteException, ScheduleException
	{
		StorageSupportParameter param = (StorageSupportParameter) countParam;
		
		//#CM604
		// Set the search conditions and generate the print process class.
		InventoryInquiryWriter writer = createWriter(conn, param);
		//#CM605
		// Obtain the target count.
		int result = writer.count();
		if (result == 0)
		{
			//#CM606
			// 6003010 = Print data was not found.
			wMessage = "6003010";
		}
		
		return result;
	
	}

	//#CM607
	// Package methods -----------------------------------------------

	//#CM608
	// Protected methods ---------------------------------------------

	//#CM609
	/**
	 * Set the Information entered via screen for a print process class and
	 * Generate a print process class.<BR>
	 * 
	 * @param conn Database connection object
	 * @param parameter Parameter object that includes search conditions
	 * @return Print Process class
	 */
	protected InventoryInquiryWriter createWriter(Connection conn, StorageSupportParameter param)
	{
		InventoryInquiryWriter writer = new InventoryInquiryWriter(conn);
		writer.setConsignorCode(param.getConsignorCode());
		writer.setFromLocation(param.getFromLocation());
		writer.setToLocation(param.getToLocation());
		writer.setItemCode(param.getItemCode());
		writer.setStatusFlag(param.getDispStatus());
		return writer;
	}


	//#CM610
	// Private methods -----------------------------------------------

}
//#CM611
//end of class
