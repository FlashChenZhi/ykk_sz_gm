package jp.co.daifuku.wms.retrieval.schedule;

//#CM722636
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM722637
/**
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue <BR>
 * <BR>
 * Allow this class to execute the process to complete Picking. <BR>
 * Receive the work No. and the process name and execute the process to complete the storage. (<CODE>complete()</CODE> method)  <BR>
 * Search through the Work Status based on the Work No. and update the Inventory Info. <BR>
 * Use a Process name to add each table. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * <DIR>
 *   1. Update the Center Inventory Info Table (DMSTOCK).  <BR>
 *     <DIR>
 *     [Inventory package enabled]<BR>
 *     Delete the data of which stock qty becomes 0 resulting from picking. 
 *     If the inventory remains, update the stock qty and the Last update process name.  <BR>
 *     Stock qty  :  Available picking qty(-) <BR>
 *     Last update process name :  Completed Class Name <BR>
 *     <BR>
 *     [Stock package "disabled"]<BR>
 *     Update the Stock qty, the Inventory status, and the Last update process name.  <BR>
 *     Stock qty  :  Available picking qty(-) <BR>
 *     Inventory status :  Completed (only when the stock qty become 0 resulting from picking) <BR>
 *     Last update process name :  Completed Class Name <BR>
 *     </DIR>
 *   <BR>
 *   3. Add Result Sent Information Table (DNHOSTSEND) <BR>  
 *     <DIR>
 *     Generate entity of the Result Sent Information from Entity of Work Status Info. <BR> 
 *     Set the Work Date (System definition date) and process name. <BR>
 *     </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/22</TD><TD>M.Inoue</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:52 $
 * @author  $Author: suresh $
 */
public class RetrievalCompleteOperator extends AbstractRetrievalSCH
{
	//#CM722638
	//	Class variables -----------------------------------------------
	//#CM722639
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM722640
	// Class method --------------------------------------------------
	//#CM722641
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:52 $");
	}
	//#CM722642
	// Constructors --------------------------------------------------
	//#CM722643
	/**
	 * Initialize this class. 
	 */
	public RetrievalCompleteOperator()
	{
	}

	//#CM722644
	// Public methods ------------------------------------------------
	//#CM722645
	/**
	 * Process to complete Picking <BR>
	 * Search through the Work Status Info table based on Work No. and update the Inventory Info table. <BR>
	 * Generate a result in the Result Sent Information table. <BR>
	 * @param conn        Database connection object
	 * @param jobno       Work No.
	 * @param processname Process name 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	public boolean complete(Connection conn, String jobno, String processname) throws ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey workingsearchKey = new WorkingInformationSearchKey();

			//#CM722646
			// 
			//#CM722647
			// Search through the Work Status Info using the Work No. as a key to obtain the target Stock ID from the Work Status Info. 
			workingsearchKey.setJobNo(jobno);
			WorkingInformation workinfo = (WorkingInformation) workingHandler.findPrimary(workingsearchKey);
			if (workinfo == null)
			{
				//#CM722648
				// 6006040=Data mismatch occurred. See log. {0} 
				RmiMsgLogClient.write("6006040" + wDelim + jobno, "RetrievalCompleteOperator");
				//#CM722649
				// Throw ScheduleException here. (Not required to set any error message) 
				throw (new ScheduleException());
			
			}
			//#CM722650
			// Stock ID
			String stockid = workinfo.getStockId();									

			//#CM722651
			/******************************************
			 * Update Inventory Info Table (DMSTOCK). 
			 * Process depending on the state of Inventory package (enabled or disabled) 
			 ******************************************/
			StockHandler stockHandler = new StockHandler(conn);
			StockAlterKey stockalterKey = new StockAlterKey();
			StockSearchKey stocksearchKey = new StockSearchKey();

			//#CM722652
			// Lock the inventory of the corresponding Stock ID. 
			stocksearchKey.setStockId(stockid);
			Stock stock = (Stock)stockHandler.findPrimaryForUpdate(stocksearchKey);
			//#CM722653
			// If there is no corresponding data, announce Exception. 
			if (stock == null)
			{
				//#CM722654
				// 6006040=Data mismatch occurred. See log. {0} 
				RmiMsgLogClient.write("6006040" + wDelim + stockid, "RetrievalCompleteOperator");
				//#CM722655
				// Throw ScheduleException here. (Not required to set any error message) 
				throw (new ScheduleException());
			}
			
			//#CM722656
			// Compute the Picking result qty (Stock qty,  Available picking qty(-)) 
			//#CM722657
			// If shortage occurs at the stage of picking, determine the status of the item as impossible to retrieve (due to damage, etc.), and 
			//#CM722658
			// decrease the stock qty by the quantity same as the Work Planned qty. 
			int resultStockQty = stock.getStockQty() - workinfo.getPlanEnableQty();
			//#CM722659
			// For data with Stock package "enabled":
			if (isStockPack(conn))
			{
				//#CM722660
				// Delete the Inventory Info of which stock qty becomes 0 resulting from picking. 
				if(resultStockQty == 0)
				{
					stocksearchKey.KeyClear();
					stocksearchKey.setStockId(stock.getStockId());
					stockHandler.drop(stocksearchKey);
				}
				//#CM722661
				// Update the Quantity if the stock qty remains. 
				else
				{
					stockalterKey.KeyClear();
					stockalterKey.setStockId(stockid);
					stockalterKey.updateStockQty(resultStockQty);
					stockalterKey.updateLastUpdatePname(processname);
					stockHandler.modify(stockalterKey);

				}
				
				//#CM722662
				// Update the Information of Location where Picking work has been done.
				LocateOperator lOperator = new LocateOperator(conn);
				lOperator.modifyLocateStatus(stock.getLocationNo(), "RetrievalCompleteOperator");

			}
			//#CM722663
			// For data with Stock package "disabled":
			else
			{
				stockalterKey.KeyClear();
				stockalterKey.setStockId(stockid);
				stockalterKey.updateStockQty(resultStockQty);
				//#CM722664
				// Update the Inventory Info of which stock qty becomes 0 resulting from picking. 
				if ((stock.getStockQty() - workinfo.getPlanEnableQty()) == 0)
				{
					stockalterKey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
				}
				stockalterKey.updateLastUpdatePname(processname);
				stockHandler.modify(stockalterKey);

			}

			//#CM722665
			/*****************************************
			 * Add Result Sent Information Table (DNHOSTSEND) 
			 *****************************************/
			//#CM722666
			// Generate entity of the Result Sent Information from Entity of Work Status Info. 
			//#CM722667
			// Set the Work Date (System definition date) and process name. 
			HostSendHandler hostsendHandler = new HostSendHandler(conn);
			HostSend hostsend = new HostSend(workinfo, getWorkDate(conn), processname);
			//#CM722668
			// Registration of data 
			hostsendHandler.create(hostsend);
			
			return true;
		}
		catch (DataExistsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM722669
	// Package methods -----------------------------------------------

	//#CM722670
	// Protected methods ---------------------------------------------
	
	//#CM722671
	// Private methods -----------------------------------------------
}
//#CM722672
//end of class
