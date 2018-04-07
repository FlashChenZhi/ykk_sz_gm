package jp.co.daifuku.wms.retrieval.schedule;
//#CM724038
/*
 * Created on Oct 20, 2004
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
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.retrieval.report.RetrievalOrderPlanWriter;
//#CM724039
/**
 * Designer :   Muneendra <BR>
 * Author :   Muneendra <BR>
 * <BR>
 * Allow this class to invoke the process for printing Order Picking Plan report. <BR>
 * Receive the contents entered via screen as a parameter and invoke the class to execute the process for printing Order Picking Plan report. <BR>
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
 *     Order No.: other than NULL <BR>
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
 * 		Consignor Code*<BR>
 * 		Start Planned Picking Date<BR>
 * 		End Planned Picking Date<BR>
 * 		Case Order No.<BR>
 * 		Piece Order No.<BR>
 * 		 Work status:<BR>
 *   </DIR>
 *   <BR>
 *   <Check Process Condition> 
 *   <DIR>
 *     None <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/20</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:59 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderPlanListSCH extends AbstractRetrievalSCH
{
		
	//#CM724040
	// Class method --------------------------------------------------
	//#CM724041
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:59 $");
	}
	//#CM724042
	// Constructors --------------------------------------------------
	//#CM724043
	/**
	 * Initialize this class. 
	 */
	public RetrievalOrderPlanListSCH()
	{
		wMessage = null;
	}

	//#CM724044
	// Public methods ------------------------------------------------
	//#CM724045
	/**
	 * 
	 * @param conn Connection Database connection object
	 * @param searchParam Parameter Class that inherits <CODE>Parameter</CODE> class with search conditions. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 * @return Class that implements the <CODE>Parameter</CODE> interface that contains the search result. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		RetrievalPlanHandler retrievalHandle = new RetrievalPlanHandler(conn);
		RetrievalPlanSearchKey searchKey = new RetrievalPlanSearchKey();
		//#CM724046
		// Set the WHERE condition. 
		searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE,"<>") ;
		searchKey.setCaseOrderNo("", "IS NOT NULL", "(", "", "OR");
		searchKey.setPieceOrderNo("", "IS NOT NULL", "", ")", "AND");
		//#CM724047
		// Set the GROUP BY condition. 
		searchKey.setConsignorCodeGroup(1);
		//#CM724048
		// Set the COLLECTION condition. 
		searchKey.setConsignorCodeCollect("");

		
		RetrievalSupportParameter parameter = null;

		if (retrievalHandle.count(searchKey) == 1)
		{
			RetrievalPlan [] retrieval = (RetrievalPlan[]) retrievalHandle.find(searchKey);

			//#CM724049
			// Return null if no corresponding data found, or two or more corresponding data exist. 
			if (retrieval.length == 1)
			{
				parameter = new RetrievalSupportParameter();
				parameter.setConsignorCode(retrieval[0].getConsignorCode());
			}
		}
		return parameter;
	}
	//#CM724050
	/**
	 * Receive the contents entered via screen as a parameter, and pass the parameter to the class for the process to print the Order Picking Plan report. <BR>
	 * Disables to print if no print data. <BR>
	 * Receive true from the class for the process of printing a plan list if succeeded in printing, or receive false when failed, and
	 * return the result. <BR>
	 * Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents
	 *         Designating any instance other than RetrievalSupportParameter instance throws <CODE>ScheduleException</CODE>. <BR>
	 *         Use <CODE>getMessage()</CODE> method to refer to the content of the error 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
	    //#CM724051
	    // Cast the Paramter Class to the RetrievalSupportParameter Class.
		RetrievalSupportParameter retrievalParameter = (RetrievalSupportParameter) startParams[0];

		if (retrievalParameter == null)
		{
			wMessage = "6027005";
			throw new ScheduleException();
		}

		//#CM724052
		// Set a value in the RetrievalOrderPlanWriter.  
		RetrievalOrderPlanWriter writer = createWriter(conn, retrievalParameter);
		
		//#CM724053
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
	
	//#CM724054
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
		
		//#CM724055
		// Set the search conditions and generate the print process class. 
		RetrievalOrderPlanWriter writer = createWriter(conn, param);
		//#CM724056
		// Obtain the count of target data. 
		int result = writer.count();
		if (result == 0)
		{
			//#CM724057
			// 6003010=No print data found.
			wMessage = "6003010";
		}
		
		return result;
	
	}
	
	//#CM724058
	// Protected methods ------------------------------------------------
	
	//#CM724059
	/**
	 * Set the Information entered via screen for a print process class and
	 * generate a print process class. <BR>
	 * 
	 * @param conn Database connection object
	 * @param retrievalParameter Parameter object that includes search conditions 
	 * @return A class to execute the Print process. 
	 */
	protected RetrievalOrderPlanWriter createWriter(
		Connection conn,
		RetrievalSupportParameter retrievalParameter)
	{
		RetrievalOrderPlanWriter writer = new RetrievalOrderPlanWriter(conn);
		
		writer.setConsignorCode(retrievalParameter.getConsignorCode());
		writer.setFromRetrievalDate(retrievalParameter.getFromRetrievalPlanDate());
		writer.setToRetrievalDate(retrievalParameter.getToRetrievalPlanDate());
		writer.setCustomerCode(retrievalParameter.getCustomerCode());
		writer.setCaseOrderNo(retrievalParameter.getCaseOrderNo());
		writer.setPieceOrderNo(retrievalParameter.getPieceOrderNo());
		writer.setWorkStatus(retrievalParameter.getWorkStatus());
		
		return writer;
	}
		
	
}
