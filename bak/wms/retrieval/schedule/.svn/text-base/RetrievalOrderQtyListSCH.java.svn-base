package jp.co.daifuku.wms.retrieval.schedule;

//#CM724127
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ResultViewHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.retrieval.report.RetrievalOrderQtyWriter;

//#CM724128
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 *
 * Allow the <CODE>RetrievalOrderQtyListSCH</CODE> class to print the Order Picking Result data report. <BR>
 * Receive the contents entered via screen as a parameter, and execute the process to print the Order Picking Result Report. <BR>
 * Allow each method contained in this class to receive the Connection object and execute the process for updating the database, but
 * disable to commit nor roll-back the transaction. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind</CODE> method) 
 * <DIR>
 * 	Search for the Consignor Code through the Result info (Picking with Order No.). If only the same Consignor Code exists, 
 * 	return the Consignor Code. <BR>
 * 	 Return null if two or more data of Consignor Code exist. <BR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Print" button (<CODE>startSCH</CODE> method) <BR>
 * <DIR>
 * 	 Receive the contents entered via screen as a parameter, and using it as a condition, print the Order Picking Result Report. <BR>
 * 	 Return true when the process normally completed. <BR>
 * 	 Return false when error occurs during printing the report. <BR>
 * 	Use <CODE>getMessage()</CODE> method to refer to the content of the error.<BR>
 * <BR>
 * 	<Parameter> *Mandatory Input<BR>
 * <BR>
 * 	Consignor Code*<BR>
 * 	Start Picking Date<BR>
 * 	End Picking Date<BR>
 * 	Customer Code<BR>
 * 	Order No.<BR>
 * 	Case/Piece division<BR>
 * <BR>
 * 	 <Print-out Process> <BR>
 * 	1.Set the value set for a parameter in the <CODE>RetrievalSupportParameter</CODE> class. <BR>
 * 	2.Print the Order Picking Result Report using the <CODE>RetrievalWriter</CODE> class.<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/20</TD><TD>K.Matsuda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:20:00 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderQtyListSCH extends AbstractRetrievalSCH
{
	
	//#CM724129
	// Class method --------------------------------------------------

	//#CM724130
	/**
	 * Execute the process for initial display. <BR>
	 * Search for the Consignor Code through the Result info (Picking with Order No.). If only the same Consignor Code exists, 
	 * set the Consignor Code in the <CODE>RetrievalSupportParameter</CODE> class and return it. <BR>
	 * @param	conn Database connection object<BR>
	 * @param	searchParam <CODE>RetrievalSupportParameter</CODE> Class <BR>
	 * @return	 <CODE>RetrievalSupportParameter</CODE> class that contains search result.<BR>
	 * @throws	ReadWriteException Announce when error occurs on the database connection.<BR>
	 * @throws	ScheduleException Announce it when unexpected exception occurs in the process of checking. <BR>
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		//#CM724131
		// Translate the Parameter into RetrievalSupportParameter. 
		RetrievalSupportParameter retrievalParameter = new RetrievalSupportParameter();
		
		//#CM724132
		// Generate instance of Handlers. 
		ResultViewHandler resultViewHandler = new ResultViewHandler(conn);
		ResultViewSearchKey resultViewSearchKey = new ResultViewSearchKey();
		ResultView[] resultView = null;
		
		//#CM724133
		// Set Search Condition
		//#CM724134
		// Work type: Picking
		resultViewSearchKey.setJobType(ResultView.JOB_TYPE_RETRIEVAL);
		//#CM724135
		// Order No. != NULL
		resultViewSearchKey.setOrderNo("", "IS NOT NULL");

		resultViewSearchKey.setConsignorCodeCollect("");
		resultViewSearchKey.setConsignorCodeGroup(1);
		
		//#CM724136
		// If the result count is 1, search for it and set it for the retrievalParameter. 
		if(resultViewHandler.count(resultViewSearchKey) == 1)
		{
			resultView = (ResultView[])resultViewHandler.find(resultViewSearchKey);
			retrievalParameter.setConsignorCode(resultView[0].getConsignorCode());
		}
		
		return retrievalParameter;
	}

	//#CM724137
	/**
	 * Receive the contents entered via screen as a parameter, and execute the process to print it. 
	 * If the print process succeeded, return true. <BR>
	 * Return false if the print process failed due to condition error or other causes. <BR>
	 * In such case, use <CODE>getMessage</CODE> method to obtain the content. 
	 * @param	conn		Database connection object
	 * @param	startParams	 Contents entered via screen 
	 * @return	Return true if the schedule process completed normally, or return false if the schedule process failed. 
	 * @throws	ReadWriteException	Announce when error occurs on the database connection.
	 * @throws	ScheduleException	 Announce it when unexpected exception occurs in the process of scheduling. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter retrievalParameter = null;
		
		//#CM724138
		// Enable to pass only one parameter. Accordingly, obtain the leading data. 
		retrievalParameter = (RetrievalSupportParameter) startParams[0];
		
		//#CM724139
		// Generate a Writer class instance 
		RetrievalOrderQtyWriter retrievalOrderQtyWriter = createWriter(conn, retrievalParameter);

		//#CM724140
		// Set the message for every print result. 
		if(retrievalOrderQtyWriter.startPrint())
		{
			//#CM724141
			// 6001010=Print completed successfully. 
			wMessage = "6001010";
			return true;
		}
		else
		{
			wMessage = retrievalOrderQtyWriter.getMessage();
			return false;
		}

	}
	
	//#CM724142
	/**
	 * Obtain the Count of print targets based on the info entered via screen. <BR>
	 * If no target data found, or if input error found, return 0 (count). <BR>
	 * When count is 0, use <CODE>getMessage</CODE> in the process by the invoking source, and obtain the error message. <BR>
	 * @param conn Database connection object
	 * @param countParam <CODE>Parameter</CODE> object that includes search conditions. 
	 * @return Count of print targets 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	public int count(Connection conn, Parameter countParam) throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter param = (RetrievalSupportParameter) countParam;
		
		//#CM724143
		// Set the search conditions and generate the print process class. 
		RetrievalOrderQtyWriter writer = createWriter(conn, param);
		//#CM724144
		// Obtain the count of target data. 
		int result = writer.count();
		if (result == 0)
		{
			//#CM724145
			// 6003010=No print data found.
			wMessage = "6003010";
		}
		
		return result;
	
	}
	
	//#CM724146
	// Protected methods ------------------------------------------------
	
	//#CM724147
	/**
	 * Set the Information entered via screen for a print-out process class and generate the print-out process class. <BR>
	 * @param conn Database connection object
	 * @param retrievalParameter Parameter object that includes search conditions 
	 * @return A class to execute the Print process. 
	 */
	protected RetrievalOrderQtyWriter createWriter(Connection conn, RetrievalSupportParameter retrievalParameter)
	{
		RetrievalOrderQtyWriter retrievalOrderQtyWriter = new RetrievalOrderQtyWriter(conn);
		
		//#CM724148
		// Set the value for Writer class of the parameter. 
		retrievalOrderQtyWriter.setConsignorCode(retrievalParameter.getConsignorCode());
		retrievalOrderQtyWriter.setFromWorkDate(retrievalParameter.getFromRetrievalDate());
		retrievalOrderQtyWriter.setToWorkDate(retrievalParameter.getToRetrievalDate());
		retrievalOrderQtyWriter.setCustomerCode(retrievalParameter.getCustomerCode());
		retrievalOrderQtyWriter.setOrderNo(retrievalParameter.getOrderNo());
		//#CM724149
		// Case/Piece division (Work Type) 
		retrievalOrderQtyWriter.setCasePieceFlag(retrievalParameter.getCasePieceflg());
		return retrievalOrderQtyWriter;
	}


}
