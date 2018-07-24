package jp.co.daifuku.wms.retrieval.schedule;

//#CM723295
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
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ResultViewHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.retrieval.report.RetrievalItemQtyWriter;

//#CM723296
/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * Allow this class to execute the process for printing Item Picking Result Report.  <BR>
 * Receive the contents entered via screen as a parameter, and execute the process to print the Item Picking Result Report.  <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind()</CODE> method)  <BR> 
 * <BR>
 * <DIR>
 *   Return the Consignor Code, if only one Consignor Code exists in the Result info <CODE>(ResultView)</CODE>.  <BR> 
 *   Return null if no corresponding data found, or two or more corresponding data exist.  <BR> 
 *   <BR>
 *   [search conditions]  <BR> 
 *   <BR>
 *   Data with work type "Picking"  <BR>
 *   Order No. is a blank.  <BR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Print" button (<CODE>startSCH()</CODE> method)  <BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter, and <BR>
 *   if one or more print target data exist, <BR>
 *   execute the process to print the Item Picking Result Report using <CODE>RetrievalItemQtyWriter</CODE> class. <BR>
 *   <BR>
 *   [Parameter]  *Mandatory Input <BR>
 *   <BR>
 *   Consignor Code* <BR>
 *   Start Picking Date <BR>
 *   End Picking Date <BR>
 *   Item Code <BR>
 *   Case/Piece division (Work Type)  <BR>
 *   <BR>
 *   [Returned data]  <BR>
 *   <BR>
 *   Count of the search results  <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/19</TD><TD>Y.Kubo</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:55 $
 * @author  $Author: suresh $
 */
public class RetrievalItemQtyListSCH extends AbstractRetrievalSCH
{
	
	//#CM723297
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:55 $");
	}
	
	//#CM723298
	// Constructors --------------------------------------------------
	//#CM723299
	/**
	 * Initialize this class. 
	 */
	public RetrievalItemQtyListSCH()
	{
		wMessage = null;
	}

	//#CM723300
	// Public methods ------------------------------------------------

	//#CM723301
	/**
	 * Allow this method to support the operation to obtain the data required for initial display.  <BR>
	 * Return the Consignor Code, if only one Consignor Code exists in the Result info <CODE>(ResultView)</CODE>.  <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.  
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. 
	 * @param conn Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions. 
	 * @return Class that implements the <CODE>Parameter</CODE> interface that contains the search result. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		ResultViewHandler resultviewHandler = new ResultViewHandler(conn);
		ResultViewSearchKey searchKey = new ResultViewSearchKey();

		//#CM723302
		// Search Data 
		//#CM723303
		// Work type (Picking) 
		searchKey.setJobType(ResultView.JOB_TYPE_RETRIEVAL);
		searchKey.setOrderNo("", "");
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");
						
		RetrievalSupportParameter dispData = new RetrievalSupportParameter();

		if (resultviewHandler.count(searchKey) == 1)
		{
			try
			{
				ResultView[] resultview = (ResultView[])resultviewHandler.find(searchKey);
				dispData.setConsignorCode(resultview[0].getConsignorCode());
			}
			catch (Exception e)
			{
				return new RetrievalSupportParameter();
			}
		}
		return dispData;

	}

	//#CM723304
	/**
	 * Receive the contents entered via screen as a parameter, and <BR>
	 * execute the process to print the Item Picking Result Report using RetrievalItemQtyWriter class.  <BR>    
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> instance throws ScheduleException.  <BR>
	 *         Use <CODE>getMessage()</CODE> method to refer to the content of the error 
	 * @return Return true if the schedule process completed normally, or return false if failed. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			RetrievalSupportParameter param = (RetrievalSupportParameter) startParams[0];
			
			//#CM723305
			// Check Input Area. 
			if (!check(conn, startParams[0]))
			{
				return false;
			}
		
			//#CM723306
			// Generate a Writer class instance 
			RetrievalItemQtyWriter retrievaWriter = createWriter(conn, param);
			
			//#CM723307
			// Set the message for every print result. 
			if(retrievaWriter.startPrint())
			{
				wMessage = "6001010";
				return true;
			}
			else
			{
				wMessage = retrievaWriter.getMessage();
				return false;
			}
		}
		catch(Exception e)
		{
			//#CM723308
			// Write the error detail into the log file. 
			RmiMsgLogClient.write(new TraceHandler(6027005, e), "RetrievalItemQtyListSCH");
			//#CM723309
			// Set the message.  6027005 = Internal error occurred. See log. 
			wMessage = "6027005";
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM723310
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
		
		//#CM723311
		// Set the search conditions and generate the print process class. 
		RetrievalItemQtyWriter writer = createWriter(conn, param);
		//#CM723312
		// Obtain the count of target data. 
		int result = writer.count();
		if (result == 0)
		{
			//#CM723313
			// 6003010=No print data found.
			wMessage = "6003010";
		}
		
		return result;
	
	}	

	//#CM723314
	/**
	 * Check the contents of the parameter for its properness. According to the contents set for the parameter designated in <CODE>searchParam</CODE>,<BR>
	 * execute the process for checking the input in the parameter. Implement of the check process depends on the class that implements this interface. <BR>
	 * Return true if the contents of the parameter is correct. <BR>
	 * Return false if there is problem with the contents of parameter. Enable to obtain the contents using <CODE>getMessage()</CODE> method. 
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * @param  conn               Database connection object
	 * @param  searchParam        Parameter class that contains info to be checked for input. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling. 
	 * @return Return true if the content of parameter is normal, or return false if abnormal. 
	 */
	public boolean check(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter param = (RetrievalSupportParameter)searchParam;

		//#CM723315
		// Obtain the Start Date and the End Date from parameter.
		String fromretrievaldate = param.getFromRetrievalDate();
		String toretrievaldate = param.getToRetrievalDate();
		
		//#CM723316
		// Check the value of Picking Date for precedence. 
		if (!StringUtil.isBlank(fromretrievaldate) && !StringUtil.isBlank(toretrievaldate))
		{
			if (fromretrievaldate.compareTo(toretrievaldate) > 0)
			{
				//#CM723317
				// 6023107=Starting picking date must precede the end picking date. 
				wMessage = "6023107";
				return false;
			}
		}
		return true;
	}
	
	//#CM723318
	// Protected methods ------------------------------------------------

	//#CM723319
	/**
	 * Set the Information entered via screen for a print process class and
	 * generate a print process class. <BR>
	 * 
	 * @param conn Database connection object
	 * @param param Parameter object that includes search conditions 
	 * @return A class to execute the Print process. 
	 */
	protected RetrievalItemQtyWriter createWriter(Connection conn, RetrievalSupportParameter param)
	{
		RetrievalItemQtyWriter retrievaWriter = new RetrievalItemQtyWriter(conn);
		
		//#CM723320
		// Set the value for Writer class of the parameter. 
		//#CM723321
		// Consignor Code
		retrievaWriter.setConsignorCode(param.getConsignorCode());
		//#CM723322
		// Start Picking Date
		retrievaWriter.setFromWorkDate(param.getFromRetrievalDate());
		//#CM723323
		// End Picking Date
		retrievaWriter.setToWorkDate(param.getToRetrievalDate());
		//#CM723324
		// Item Code
		retrievaWriter.setItemCode(param.getItemCode());
		//#CM723325
		// Case/Piece division (Work Type) 
		retrievaWriter.setCasePieceFlag(param.getCasePieceflg());
					
		return retrievaWriter;
	}

}
//#CM723326
//end of class
