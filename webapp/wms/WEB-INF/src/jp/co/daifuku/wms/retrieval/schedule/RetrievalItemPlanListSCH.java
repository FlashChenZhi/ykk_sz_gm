package jp.co.daifuku.wms.retrieval.schedule;

//#CM723203
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
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.retrieval.report.RetrievalItemPlanWriter;

//#CM723204
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this class to invoke the process for printing Item Picking Plan report. <BR>
 * Receive the contents entered via screen as a parameter and invoke the class to execute the process for printing Item Picking Plan. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind()</CODE> method) <BR>
 * <DIR>
 *   Return the Consignor Code, if only one Consignor Code exists in the Picking Plan Info. <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR>
 * <BR>
 *   <Search conditions>
 *   <DIR>
 *     Status flag: other than Deleted <BR>
 *     Order No.: Blank 
 *   </DIR>
 * </DIR>
 * 
 * 2. Process by clicking "Print" button (<CODE>startSCH()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and pass the parameter to the class for printing the plan list. <BR>
 *   Allow the Writer class to search for the printing contents.<BR>
 *   Receive true from the class for the process of printing a plan list if succeeded in printing, or receive false when failed. <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR>
 *   <DIR>
 *     Consignor Code* <BR>
 *     Start Planned Picking Date <BR>
 *     End Planned Picking Date <BR>
 *     Item Code <BR>
 *     Case/Piece division  <BR>
 *     Work status: <BR>
 *   </DIR>
 *   <BR>
 *   <Check Process Condition> 
 *   <DIR>
 *     None <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/06</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:54 $
 * @author  $Author: suresh $
 */
public class RetrievalItemPlanListSCH extends AbstractRetrievalSCH
{

	//#CM723205
	// Class variables -----------------------------------------------
	
	//#CM723206
	// Class method --------------------------------------------------
	//#CM723207
	/**
	 * Return the version of this class.
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:54 $");
	}
	//#CM723208
	// Constructors --------------------------------------------------
	//#CM723209
	/**
	 * Initialize this class. 
	 */
	public RetrievalItemPlanListSCH()
	{
		wMessage = null;
	}

	//#CM723210
	// Public methods ------------------------------------------------
	//#CM723211
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * Return the Consignor Code, if only one Consignor Code exists in the Picking Plan Info. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for searchParam.  <BR>
	 * <BR>
	 * @param conn Connection Database connection object
	 * @param searchParam Parameter Class that inherits <CODE>Parameter</CODE> class with search conditions. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 * @return Class that implements the <CODE>Parameter</CODE> interface that contains the search result. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		RetrievalPlanHandler retrievalHandler = new RetrievalPlanHandler(conn);
		RetrievalPlanSearchKey retrievalKey = new RetrievalPlanSearchKey();
		RetrievalSupportParameter param = new RetrievalSupportParameter();

		try
		{
			//#CM723212
			// Status flag: other than Deleted 
			retrievalKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
			//#CM723213
			// Order No. is the blank one. 
			retrievalKey.setCaseOrderNo("", "");
			retrievalKey.setPieceOrderNo("", "");
			//#CM723214
			// Use consignor code as GROUP BY condition. 
			retrievalKey.setConsignorCodeGroup(1);
			retrievalKey.setConsignorCodeCollect("");

			if (retrievalHandler.count(retrievalKey) == 1)
			{
				//#CM723215
				// Search for the Consignor Code. 
				RetrievalPlan workInfo = (RetrievalPlan) retrievalHandler.findPrimary(retrievalKey);

				//#CM723216
				// Set the search result for the return value. 
				param.setConsignorCode(workInfo.getConsignorCode());
			}

		}
		catch (NoPrimaryException pe)
		{
			return param;
		}
		return param;

	}

	
	//#CM723217
	/**
	 * Receive the contents entered via screen as a parameter, and pass the parameter to the class for the process to print the Item Picking Plan report. <BR>
	 * Disables to print if no print data. <BR>
	 * Receive true from the class for the process of printing a plan list if succeeded in printing, or receive false when failed, and
	 * return the result. <BR>
	 * Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
	 * @param conn Connection Database connection object
	 * @param startParams Parameter []  Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents<BR>
	 * Designating any instance other than <CODE>RetrievalSupportParameter</CODE> instance throws <CODE>ScheduleException</CODE>. 
	 * 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling. 
	 * 
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter param = null;
		param = (RetrievalSupportParameter) startParams[0];

		//#CM723218
		// Set the print condition input via screen for print process class. 
		RetrievalItemPlanWriter writer = createWriter(conn, param);

		//#CM723219
		// Start the printing process. 
		if (writer.startPrint())
		{
			//#CM723220
			// 6001010=Print completed successfully. 
			wMessage = "6001010";
			return true;
		}
		else
		{
			//#CM723221
			// Display the error message. 
			wMessage = writer.getMessage();
			return false;
		}

	}
	
	//#CM723222
	/**
	 * Obtain the Count of print targets based on the info entered via screen. <BR>
	 * If no target data found, or if input error found, return 0 (count). <BR>
	 * When count is 0, use <CODE>getMessage</CODE> in the process by the invoking source, and
	 * obtain the error message. <BR>
	 * 
	 * @param conn Database connection object
	 * @param countParam <CODE>Parameter</CODE> object that includes search conditions. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 * @return Count of print targets 
	 */
	public int count(Connection conn, Parameter countParam) throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter param = (RetrievalSupportParameter) countParam;
		
		//#CM723223
		// Set the search conditions and generate the print process class. 
		RetrievalItemPlanWriter writer = createWriter(conn, param);
		//#CM723224
		// Obtain the count of target data. 
		int result = writer.count();
		if (result == 0)
		{
			//#CM723225
			// 6003010=No print data found.
			wMessage = "6003010";
		}
		
		return result;
	
	}
	
	//#CM723226
	// Package methods -----------------------------------------------

	//#CM723227
	// Protected methods ---------------------------------------------

	//#CM723228
	/**
	 * Set the Information entered via screen for a print process class and
	 * generate a print process class. <BR>
	 * 
	 * @param conn Database connection object
	 * @param param Parameter object that includes search conditions 
	 * @return A class to execute the Print process. 
	 */	
	protected RetrievalItemPlanWriter createWriter(Connection conn, RetrievalSupportParameter param)
	{
		RetrievalItemPlanWriter writer = new RetrievalItemPlanWriter(conn);
		writer.setConsignorCode(param.getConsignorCode());
		writer.setFromPlanDate(param.getFromRetrievalPlanDate());
		writer.setToPlanDate(param.getToRetrievalPlanDate());
		writer.setItemCode(param.getItemCode());
		//#CM723229
		// Work Status (If All is designated, set nothing.) 
		if(param.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
		{
			writer.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
		}
		else if(param.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
		{
			writer.setStatusFlag(RetrievalPlan.STATUS_FLAG_START);
		}
		else if(param.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
		{
			writer.setStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
		}
		else if(param.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
		{
			writer.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART);
		}
		else if(param.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
		{
			writer.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
		}
		return writer;
	}

	//#CM723230
	// Private methods -----------------------------------------------

}
//#CM723231
//end of class
