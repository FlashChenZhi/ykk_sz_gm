package jp.co.daifuku.wms.storage.schedule;

//#CM544
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.entity.InventoryCheck;

//#CM545
/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida  <BR>
 * <BR>
 * This class deletes WEB inventory check info Class. <BR>
 * Receive the contents entered via screen as a parameter and execute the process for deleting the inventory check info. <BR>
 * Each method in this class receives a connection object and executes the process for updating the database. <BR>
 * However, each method disables to commit and roll back of transactions. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method) <BR><BR><DIR>
 *   If only one Consignor Code exists in Inventory Check Work status, return the corresponding Consignor Code. <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR>
 * <BR>
 *   <Search conditions> <BR><DIR>
 *     Process flag:Not Submitted(0) <BR></DIR></DIR>
 * <BR>
 * 2.Process by clicking on Delete Inventory Check Data button (<CODE>startSCH()</CODE>Method) <BR><BR><DIR>
 *   Receive the contents entered via screen and execute the process for deleting the inventory check. <BR>
 *   Return true when the process normally completed, or return false when failed to schedule completely due to condition error or other causes. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 *     Consignor code* <BR>
 *     Start Location <BR>
 *     End Location <BR></DIR>
 * <BR>
 *   <Delete Process for Inventory Check Information> <BR>
 * <DIR>
 *   <Check for process condition> <BR>
 *     1.Ensure to define Worker code and password in the Worker master. <BR><DIR>
 *       Check only the leading value of the array for the values of Worker code and password. <BR></DIR>
 * <BR>
 *   <Update/Add Process> <BR>
 *     -Update of Inventory Check Work Status table (DNINVENTORYCHECK). <BR>
 *       Update the Inventory Check work status linked with Consignor Code and Location No. of the parameter based on the contents of the Received parameter. <BR>
 *       1.Update the process flag to Deleted (9). <BR>
 *       2.Update the Worker code, Worker name, Terminal No., and the last Update process name. <BR>
 * </DIR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/12</TD><TD>S.Yoshida</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/11 06:34:31 $
 * @author  $Author: suresh $
 */
public class InventoryDeleteSCH extends AbstractStorageSCH
{

	//#CM546
	// Class variables -----------------------------------------------
	//#CM547
	/**
	 * Process name
	 */
	private static String wProcessName = "InventoryDeleteSCH";
	
	//#CM548
	// Class method --------------------------------------------------
	//#CM549
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/11 06:34:31 $");
	}

	//#CM550
	// Constructors --------------------------------------------------
	//#CM551
	/**
	 * Initialize this class.
	 */
	public InventoryDeleteSCH()
	{
		wMessage = null;
	}

	//#CM552
	// Public methods ------------------------------------------------
	//#CM553
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor Code exists in Inventory Check Work status, return the corresponding Consignor Code.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Instance to maintain database connection.
	 * @param searchParam Instance of <CODE>StorageSupportParameter</CODE> class with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StorageSupportParameter</CODE> throws ScheduleException.<BR>
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		
		//#CM554
		// Set the corresponding Consignor code.
		StorageSupportParameter wParam = new StorageSupportParameter();
		
		//#CM555
		// Generate inventory check work status instance of handlers.
		InventoryCheckHandler wObj = new InventoryCheckHandler(conn);
		InventoryCheckSearchKey wKey = new InventoryCheckSearchKey();
		InventoryCheck[] wInventory = null;
		
		try
		{
			//#CM556
			// Set the search conditions.
			//#CM557
			// Process flag=Not Submitted (0)
			wKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
			//#CM558
			// Set the Consignor Code for grouping condition.
			wKey.setConsignorCodeGroup(1);
			wKey.setConsignorCodeCollect("DISTINCT");
			
			if (wObj.count(wKey) == 1)
			{
				//#CM559
				// Obtain the corresponding Consignor Code.
				wInventory = (InventoryCheck[]) wObj.find(wKey);
			
				//#CM560
				// If the search result count is one: 
				if (wInventory != null && wInventory.length == 1)
				{
					//#CM561
					// Obtain the corresponding Consignor Code and set it for the return parameter.
					wParam.setConsignorCode(wInventory[0].getConsignorCode());
				}
			}
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
		return wParam;
	}

	//#CM562
	/**
	 * Receive the contents entered via screen as a parameter and start the schedule for deleting the Inventory Check Information.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * Return true if the schedule normally completed, or return false if failed.
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>StorageSupportParameter</CODE> class instance with contents of commitment. <BR>
	 *        Designating any instance other than StorageSupportParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		
		//#CM563
		// Generate inventory check work status instance of handlers.
		InventoryCheckHandler wObj = new InventoryCheckHandler(conn);
		InventoryCheckAlterKey wAKey = new InventoryCheckAlterKey();
		InventoryCheckSearchKey wKey = new InventoryCheckSearchKey();
		InventoryCheck[] wInventory = null;
		
		try
		{
			StorageSupportParameter[] wParam = (StorageSupportParameter[]) startParams;
			
			//#CM564
			// Check the Worker code and password
			if (!checkWorker(conn, wParam[0]))
			{
				return false;
			}
			
			//#CM565
			// Check the Daily Update Processing.
			if (isDailyUpdate(conn))
			{
				return false;
			}
			
			//#CM566
			// Trigger error if no value input in Consignor Code after inputting the Location No.
			if ((!StringUtil.isBlank(wParam[0].getFromLocation())
			||   !StringUtil.isBlank(wParam[0].getToLocation()))
			&&  StringUtil.isBlank(wParam[0].getConsignorCode()))
			{
				//#CM567
				// 6023031 = Please enter the consignor code.
				wMessage = "6023031";
				return false;
			}
			
			//#CM568
			// Set the Delete condition.
			//#CM569
			// Consignor code
			if (!StringUtil.isBlank(wParam[0].getConsignorCode()))
			{
				wKey.setConsignorCode(wParam[0].getConsignorCode());
				wAKey.setConsignorCode(wParam[0].getConsignorCode());
			}
			//#CM570
			// Start Location No..
			if (!StringUtil.isBlank(wParam[0].getConsignorCode()) && !StringUtil.isBlank(wParam[0].getFromLocation()))
			{
				wKey.setLocationNo(wParam[0].getFromLocation(), ">=");
				wAKey.setLocationNo(wParam[0].getFromLocation(), ">=");
			}
			//#CM571
			// End Location No..
			if (!StringUtil.isBlank(wParam[0].getConsignorCode()) && !StringUtil.isBlank(wParam[0].getToLocation()))
			{
				wKey.setLocationNo(wParam[0].getToLocation(), "<=");
				wAKey.setLocationNo(wParam[0].getToLocation(), "<=");
			}
			//#CM572
			// Process flag
			wKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
			wAKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
			
			//#CM573
			// Obtain the count.
			wInventory = (InventoryCheck[]) wObj.findForUpdate(wKey);
			
			if (wInventory == null || (wInventory != null && wInventory.length == 0))
			{
				//#CM574
				// 6003014 = There was no target data to delete.
				wMessage = "6003014";
				return false;
			}
			
			//#CM575
			// Set the update value.
			//#CM576
			// Process flag
			wAKey.updateStatusFlag(InventoryCheck.STATUS_FLAG_DELETE);
			//#CM577
			// Last update process name
			wAKey.updateLastUpdatePname(wProcessName);
			
			//#CM578
			// Update Inventory Check Information
			wObj.modify(wAKey);
			
			//#CM579
			// 6001005 = Deleted.
			wMessage = "6001005";
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		
		return true;
	}

	//#CM580
	// Package methods -----------------------------------------------

	//#CM581
	// Protected methods ---------------------------------------------

	//#CM582
	// Private methods -----------------------------------------------
}
//#CM583
//end of class
