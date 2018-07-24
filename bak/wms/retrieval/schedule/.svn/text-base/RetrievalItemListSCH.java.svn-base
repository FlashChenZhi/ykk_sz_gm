package jp.co.daifuku.wms.retrieval.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.report.RetrievalItemWriter;

//#CM723081
/*
 * Created on 2004/10/20
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
//#CM723082
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * Allow this class to invoke the process for printing WEB Item Picking Work report. <BR>
 * Receive the contents entered via screen as a parameter and invoke the class to execute the process for printing Item Picking Work. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind()</CODE> method)  <BR><BR><DIR>
 *   For Work Status Info with only one Consignor Code exists, return the corresponding Consignor Code.  <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
 * <BR>
 *   <Search conditions> <BR><DIR>
 *     Work type: Picking. (3)  <BR>
 *     Status flag:other than "Deleted" (9)  <BR>
 *     Order No.: NULL  </DIR></DIR>
 * <BR>
 * 2. Process by clicking "Print" button (<CODE>startSCH()</CODE> method)  <BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter, and pass the parameter to the class for the process to print the Item Picking Work report. <BR>
 *   Allow the Writer class to search for the printing contents.<BR>
 *   Receive true if printing succeeded or false if failed, from the class for the process of printing a work report and return the result. <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Consignor Code* <BR>
 *     Planned Picking Date* <BR>
 *     Item Code <BR>
 *     Case/Piece division* <BR>
 * 	   Work status:*<BR></DIR>
 * <BR>
 *   <Print-out Process>  <BR>
 * <DIR>
 * 	   1.Set the value, which is set for a parameter, in the <CODE>RetrievalItemStartWriter</CODE> class. <BR>
 * 	   2.Print the Item Picking Work report using the <CODE>RetrievalItemStartWriter</CODE> class <BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>S.Yoshida</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:54 $
 * @author  $Author: suresh $
 */
public class RetrievalItemListSCH extends AbstractRetrievalSCH
{
    //#CM723083
    //Constants---------------------------------
    
    //#CM723084
    //Attributes--------------------------------
	//#CM723085
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

    //#CM723086
    //Static------------------------------------
    
    //#CM723087
    //Constructors------------------------------
	//#CM723088
	/**
	 * Initialize this class. 
	 */
	public RetrievalItemListSCH()
	{
		wMessage = null;
	}
    
    //#CM723089
    //Public------------------------------------
	//#CM723090
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:54 $");
	}
    
	//#CM723091
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * For Work Status Info with only one Consignor Code exists, return the corresponding Consignor Code. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. 
	 * @param conn Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions. 
	 * @return Class that implements the <CODE>Parameter</CODE> interface that contains the search result. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
	    WorkingInformationSearchKey sKey = new WorkingInformationSearchKey() ;
        
        RetrievalSupportParameter param = null;
        //#CM723092
        // Any status other than "Deleted" 
        sKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE,"<>") ;
        sKey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL) ;

        //#CM723093
        // Any value other than Order as this is an inquiry for Item Picking Plan 
        sKey.setOrderNo("", "IS NULL");
        //#CM723094
        // Set the COLLECT clause. 
        sKey.setConsignorCodeCollect("");
        //#CM723095
        // Set the GROUP BY clause. 
        sKey.setConsignorCodeGroup(1);
		//#CM723096
		// Search for data other than ASRS work. 
		sKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
        WorkingInformationHandler wHandler = new WorkingInformationHandler(conn);

        if (wHandler.count(sKey) == 1)
        {
            WorkingInformation[] result = (WorkingInformation[]) wHandler.find(sKey);
            param = new RetrievalSupportParameter();
            param.setConsignorCode(result[0].getConsignorCode());
        }
        return param;

	}

	//#CM723097
	/**
	 * Receive the contents entered via screen as a parameter, and <BR>
	 * execute the process to print the Item Picking Work report using RetrievalItemStartWriter class.  <BR>    
	 * @param conn Instance to maintain database connection. 
	 * @param startParams Array of the <CODE>RetrievalSupportParameter</CODE> class instance that has setting contents
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> instance throws ScheduleException.  <BR>
	 *         Use <CODE>getMessage()</CODE> method to refer to the content of the error 
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result. <BR>
	 *          If no corresponding record is found, return the array with number of elements equal to 0.  <BR>
	 *          Return null when input condition error occurs.  <BR>
	 *          Returning null enables to obtain the error content as a message using the <CODE>getMessage()</CODE> method. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			
			RetrievalSupportParameter param = (RetrievalSupportParameter) startParams[0];
			
			//#CM723098
			// Check Input Area. 
			if (param == null)
			{
			    wMessage = "" ;
				return false;
			}
		
			//#CM723099
			// Generate a Writer class instance 
			RetrievalItemWriter retrievalWriter = createWriter(conn, param);
			
			//#CM723100
			// Set the message for every print result. 
			if(retrievalWriter.startPrint())
			{
				wMessage = "6001010";
				return true;
			}
			else
			{
				wMessage = retrievalWriter.getMessage();
				return false;
			}
		}
		catch(Exception e)
		{
			//#CM723101
			// Write the error detail into the log file. 
			RmiMsgLogClient.write(new TraceHandler(6027005, e), "RetrievalItemListReprintSCH");
			//#CM723102
			// Set the message.  6027005 = Internal error occurred. See log. 
			wMessage = "6027005";
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM723103
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
		
		//#CM723104
		// Set the search conditions and generate the print process class. 
		RetrievalItemWriter writer = createWriter(conn, param);
		//#CM723105
		// Obtain the count of target data. 
		int result = writer.count();
		if (result == 0)
		{
			//#CM723106
			// 6003010=No print data found.
			wMessage = "6003010";
		}
		
		return result;
	
	}
	

    //#CM723107
    //Protected---------------------------------
    
	//#CM723108
	/**
	 * Set the Information entered via screen for a print process class and
	 * generate a print process class. <BR>
	 * 
	 * @param conn Database connection object
	 * @param param Parameter object that includes search conditions 
	 * @return A class to execute the Print process. 
	 */    
	protected RetrievalItemWriter createWriter(Connection conn, RetrievalSupportParameter param)
	{
		RetrievalItemWriter retrievalWriter = new RetrievalItemWriter(conn);
		
		//#CM723109
		// Set the value for Writer class of the parameter. 
		//#CM723110
		// Consignor Code
		if(!StringUtil.isBlank(param.getConsignorCode()))
		{
			retrievalWriter.setConsignorCode(param.getConsignorCode());
		}
		
		//#CM723111
		// Planned Picking Date
		if(!StringUtil.isBlank(param.getRetrievalPlanDate()))
		{
			retrievalWriter.setRetrievalPlanDate(param.getRetrievalPlanDate()) ;
		}
		
		//#CM723112
		// Item Code
		if(!StringUtil.isBlank(param.getItemCode()))
		{
			retrievalWriter.setItemCode(param.getItemCode()) ;
		}
		
		//#CM723113
		// Division 
		if(!StringUtil.isBlank(param.getCasePieceflg()))
		{
			if(param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				retrievalWriter.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_CASE) ;
			}
			else if(param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				retrievalWriter.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_PIECE) ;
			}
			else if(param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				retrievalWriter.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_NOTHING) ;
			}
			else if(param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
			{
				retrievalWriter.setCasePieceFlag(RetrievalSupportParameter.CASEPIECE_FLAG_ALL) ;
			}
		    
		}			
		//#CM723114
		// Work status:
		if(!StringUtil.isBlank(param.getWorkStatus()))
		{
			retrievalWriter.setWorkStatus(param.getWorkStatus()) ;
		}
		return retrievalWriter;
	}

	//#CM723115
	//Private-----------------------------------
	
    
    //#CM723116
    //InnerClasses------------------------------
    
    

}
