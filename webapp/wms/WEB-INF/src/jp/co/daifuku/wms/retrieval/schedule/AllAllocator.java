//#CM722370
//$Id: AllAllocator.java,v 1.3 2007/02/07 04:19:51 suresh Exp $
package jp.co.daifuku.wms.retrieval.schedule;

//#CM722371
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;

//#CM722372
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * If the Shortage restoration division of WmsParam is defined as Setting Unit, use this class. <BR>
 * For data with shortage restoration division Setting Unit, in the event of shortage,
 * cancell all the allocations displayed in the preset area. <BR>
 * Even if shortage occurs, disable to suspend the process and obtain all information of shortage. <BR>
 * For shortage resulted from allocation, allow the invoking source to determine whether to complete the preset area with shortage. 
 * Once completed with shortage, update the Picking Plan Info to Completed and generate the Work Status for the Shortage as a status Completed. <BR>
 * To complete with shortage, invoke the completeShortage() method. 
 * Complete the Plan Info corresponding to the all in the preset area, with shortage, and generate its. shortage info. <BR>
 * If not completed with shortage, generate a shortage info only. <BR>
 * <B> Allow this class to maintain handler only for generating shortage info in the other Connection. Remember this. </B>
 * 
 * <BR>
 * Allow this class to execute the following processes. <BR>
 * <U>1. Allocation Process (<CODE>allocate(RetrievalSupportParameter []  param)</CODE>)</U><BR>
 * <DIR>
 *   Execute the process for allocating the data one by one using the Picking Plan unique key maintained in the Preset area as a key. 
 * </DIR>
 * 
 * <U>2. Process for completing with shortage in the event of shortage (<CODE>completeShortage(boolean isCompleteShortage)</CODE>)</U><BR>
 * <DIR>
 *   Roll-back the allocation info allocated by allocate, and complete the corresponding Plan data with shortage. 
 * </DIR>
 * 
 * <U>3.CloseProcess (<CODE>close()</CODE>) </U><BR>
 * <DIR>
 *   Rollback and close the connection for generating a shortage info 
 *   Process here because it is not the Connection passed by Business 
 * </DIR>
 * 
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/02/27</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:51 $
 * @author  $Author: suresh $
 */
public class AllAllocator extends AbstractAllocator
{
	//#CM722373
	// Class variables -----------------------------------------------

	//#CM722374
	// Class method --------------------------------------------------
	//#CM722375
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:51 $");
	}
	//#CM722376
	// Constructors --------------------------------------------------
	//#CM722377
	/**
	 * Initialize this class. 
	 * Generate a handler for generating a shortage info via another connection. 
	 */
	public AllAllocator(Connection conn) throws ScheduleException, ReadWriteException
	{
		super(conn);
		
	}
	
	//#CM722378
	// Public methods ------------------------------------------------
	//#CM722379
	/**
	 * Execute the Allocation process. <BR>
	 * Allocate the Plan data aggregated in the relevant line on the basis of count of data in the preset area.
	 * Generate a Work Status using the Case/Piece division passed via screen. 
	 * 
	 * @param param Parameter class that maintains the info input in the screen. 
	 * @return Check whether allocation process succeeded or not. If shortage occurs, return false. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	public boolean allocate(RetrievalSupportParameter[] param) throws ReadWriteException, ScheduleException
	{
		boolean isShortage = false;
		//#CM722380
		// Obtain the Area No. of the allocation target.
		String[] areaNo = getAreaNo(param[0].getAllocateField());
			
		//#CM722381
		// Data in the Preset area 
		for (int i = 0; i < param.length; i++)
		{
			//#CM722382
			// Obtain the Work Type of the allocation target.
			String workFormFlag = param[i].getCasePieceFlagL();

			//#CM722383
			// Obtain the Plan unique key of the allocation target.
			Vector vecPlanUkey= param[i].getPlanUkeyList();
			String[] planUkey = new String[vecPlanUkey.size()];
			vecPlanUkey.copyInto(planUkey);
		
			//#CM722384
			// Allocate per unique key. 
			for (int j = 0; j < planUkey.length; j++)
			{
				//#CM722385
				// Execute the allocation process. 
				if (!allocate(planUkey[j], workFormFlag, areaNo))
				{
					isShortage = true;
				}
			}
		
		}
	
		//#CM722386
		// For data with Shortage, set the Plan unique key to completed with shortage at the moment when shortage occurred,. 
		if (isShortage)
		{
			return false;
		}
		
		return true;
		
	}
	
	//#CM722387
	/**
	 * Execute the Restoration process in the event of shortage. <BR>
	 * To complete with shortage, commit the respective info. Commit always shortage info. <BR>
	 * 
	 * @param isCompleteShortage Determine whether to complete with shortage or not 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public void completeShortage(boolean isCompleteShortage) throws ScheduleException, ReadWriteException
	{
		//#CM722388
		// Data with "Completed with Shortage" 
		if (isCompleteShortage)
		{
			try
			{
				//#CM722389
				// Reflect the Work Status Info, Inventory Info, Picking Plan Info, Transport Info, and Pallet Info on DB. 
				wConn.commit();
			}
			catch (SQLException e)
			{
				throw new ReadWriteException(e.getMessage());
			}
		}
		
		//#CM722390
		// Reflect the generated shortage info resulting from allocation onto the DB. 
		try
		{
			wShortageConn.commit();
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM722391
	/**
	 * Describe mandatory processes to be executed when closing the allocation process or when error occurs.<BR>
	 * Rollback the connection for shortage process and close it. <BR>
	 * 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public void close() throws ReadWriteException
	{
		try
		{
			if (wShortageConn != null)
			{
				wShortageConn.rollback();
				wShortageConn.close();
			}
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM722392
	// Protected methods ---------------------------------------------

	//#CM722393
	// Private methods -----------------------------------------------
	
}
//#CM722394
//end of class
