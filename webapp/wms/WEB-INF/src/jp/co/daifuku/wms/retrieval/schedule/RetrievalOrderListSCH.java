package jp.co.daifuku.wms.retrieval.schedule;

//#CM723884
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.report.RetrievalOrderWriter;

//#CM723885
/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida  <BR>
 * <BR>
 * Allow this class to invoke the process for printing WEB Order Picking Work report. <BR>
 * Receive the contents entered via screen as a parameter and invoke the class to execute the process for printing Order Picking Work. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind()</CODE> method)  <BR><BR><DIR>
 *   For Work Status Info with only one Consignor Code exists, return the corresponding Consignor Code.  <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
 * <BR>
 *   <Search conditions> <BR><DIR>
 *     Work type: Picking. (3)  <BR>
 *     Status flag:other than "Deleted" (9)  <BR>
 *     Start Work flag: "Started" (1) <BR>
 *     Order No.: other than NULL  </DIR></DIR>
 * <BR>
 * 2. Process by clicking "Print" button (<CODE>startSCH()</CODE> method)  <BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter, and pass the parameter to the class for the process to print the Order Picking Work report. <BR>
 *   Allow the Writer class to search for the printing contents.<BR>
 *   Receive true if printing succeeded or false if failed, from the class for the process of printing a work report and return the result. <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Consignor Code* <BR>
 *     Planned Picking Date* <BR>
 *     Order No.* <BR>
 *     Case/Piece division* <BR>
 * 	   Work status:*</DIR>
 * <BR>
 *   <Print-out Process>  <BR>
 * <DIR>
 * 	   1.Set the value set for a parameter in the <CODE>RetrievalOrderStartWriter</CODE> class. <BR>
 * 	   2.Print the Order Picking Work report using the <CODE>RetrievalOrderStartWriter</CODE> class <BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>S.Yoshida</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:58 $
 * @author  $Author: suresh $
 */
public class RetrievalOrderListSCH extends AbstractRetrievalSCH
{
	//#CM723886
	// Class variables -----------------------------------------------

	//#CM723887
	// Class method --------------------------------------------------
	//#CM723888
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:58 $");
	}
	//#CM723889
	// Constructors --------------------------------------------------
	//#CM723890
	/**
	 * Initialize this class. 
	 */
	public RetrievalOrderListSCH()
	{
		wMessage = null;
	}

	//#CM723891
	// Public methods ------------------------------------------------
	//#CM723892
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * For Work Status Info with only one Consignor Code exists, return the corresponding Consignor Code. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. 
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be displayed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * @return Class that implements the <CODE>Parameter</CODE> interface that contains the search result. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		//#CM723893
		// Set the corresponding Consignor code. 
		RetrievalSupportParameter wParam = new RetrievalSupportParameter();
		
		//#CM723894
		// Generate instance of Work Status Info handlers. 
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation[] wWorkinfo = null;
		
		try
		{
			//#CM723895
			// Set the search conditions. 
			//#CM723896
			// Work type=Picking(3)
			wKey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			//#CM723897
			// Status flag! = "Deleated"(9)
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
			//#CM723898
			// Start Work flag="Started" (1)
			wKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			//#CM723899
			// Order No. = other than NULL 
			wKey.setOrderNo("", "IS NOT NULL");
			//#CM723900
			// Set the Consignor Code for grouping condition. 
			wKey.setConsignorCodeGroup(1);
			wKey.setConsignorCodeCollect("DISTINCT");
			//#CM723901
			// Search for data other than ASRS work. 
			wKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
			//#CM723902
			// Obtain the corresponding Consignor Code. 
			wWorkinfo = (WorkingInformation[]) wObj.find(wKey);
			
			//#CM723903
			// If the search result count is one: 
			if (wWorkinfo != null && wWorkinfo.length == 1)
			{
				//#CM723904
				// Obtain the corresponding Consignor Code and set it for the return parameter. 
				wParam.setConsignorCode(wWorkinfo[0].getConsignorCode());
			}
		
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
		return wParam;
	}
	
	
	//#CM723905
	/**
	 * Receive the contents entered via screen as a parameter, and execute the process to print it. 
	 * If the print process succeeded, return true. <BR>
	 * Return false if the print process failed due to condition error or other causes. <BR>
	 * In this case, use <CODE>getMessage()</CODE> method to obtain the contents. 
	 * @param	conn Database connection object
	 * @param  startParams <CODE>RetrievalSupportParameter</CODE> class instance with conditions for obtaining the data to be printed<BR>
	 *         Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException. <BR>
	 * @throws	ReadWriteException Announce when error occurs on the database connection.
	 * @throws	ScheduleException Announce it when unexpected exception occurs in the process of scheduling. 
	 * @return	Return true if the schedule process completed normally, or return false if the schedule process failed. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		
		RetrievalSupportParameter wParam = (RetrievalSupportParameter) startParams[0];
		
		//#CM723906
		// Check Input Area. 
		if (wParam == null)
		{
			wMessage = "" ;
			return false;
		}
		
		//#CM723907
		// Generate instance of Work Status Info handlers. 
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation[] wWorkinfo = null;

		//#CM723908
		// Work No. (vector) 
		Vector wJobNo = new Vector();
		
		//#CM723909
		// Obtain the parameter value. 
			
		//#CM723910
		// Set the search conditions. 
		if (!StringUtil.isBlank(wParam.getConsignorCode()))
		{
			//#CM723911
			// Consignor Code
			wKey.setConsignorCode(wParam.getConsignorCode());
		}
		if (!StringUtil.isBlank(wParam.getRetrievalPlanDate()))
		{
			//#CM723912
			// Planned Picking Date
			wKey.setPlanDate(wParam.getRetrievalPlanDate());
		}
		//#CM723913
		// Order No.
		if (!StringUtil.isBlank(wParam.getOrderNo()))
		{
			wKey.setOrderNo(wParam.getOrderNo());
		}
		else
		{
			wKey.setOrderNo("", "IS NOT NULL");
		}
		if (!StringUtil.isBlank(wParam.getCasePieceflg()) && 
		    !wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
		{
			//#CM723914
			// Case/Piece division (Work Type) 
			if(wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				//#CM723915
				// Case 
				wKey.setWorkFormFlag(SystemDefine.CASEPIECE_FLAG_CASE);
			}
			else if(wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				//#CM723916
				// Piece 
				wKey.setWorkFormFlag(SystemDefine.CASEPIECE_FLAG_PIECE);
			}
			else if(wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM723917
				// None 
				wKey.setWorkFormFlag(SystemDefine.CASEPIECE_FLAG_NOTHING);
			}
		}
		
		//#CM723918
		// Obtain the Work Status Info. 
		wWorkinfo = (WorkingInformation[])wObj.find(wKey);
	

		//#CM723919
		// Obtain the Work No. unique to the Work Status Info. 
		for (int i = 0; i < wWorkinfo.length; i ++)
		{
			wJobNo.addElement(wWorkinfo[i].getJobNo());
		}
			
		//#CM723920
		// Set the parameter value to the Writer class. 
		RetrievalOrderWriter writer = createWriter(conn, wParam, wJobNo);
		
		if(wWorkinfo.length != 0)
		{
			//#CM723921
			// Print 
			if (writer.startPrint())
			{
				//#CM723922
				// 6001010=Print completed successfully. 
				wMessage = "6001010";
				return true;
			}
			else
			{
				wMessage = writer.getMessage();
				return false;
			}
		}
		else
		{
			//#CM723923
			//	6003010=No print data found.
			wMessage = "6003010";
			return false;
		}
	}
	
	//#CM723924
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
		RetrievalSupportParameter wParam = (RetrievalSupportParameter) countParam;
		
		//#CM723925
		// Generate instance of Work Status Info handlers. 
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation[] wWorkinfo = null;

		//#CM723926
		// Work No. (vector) 
		Vector wJobNo = new Vector();
		
		//#CM723927
		// Obtain the parameter value. 
			
		//#CM723928
		// Set the search conditions. 
		if (!StringUtil.isBlank(wParam.getConsignorCode()))
		{
			//#CM723929
			// Consignor Code
			wKey.setConsignorCode(wParam.getConsignorCode());
		}
		if (!StringUtil.isBlank(wParam.getRetrievalPlanDate()))
		{
			//#CM723930
			// Planned Picking Date
			wKey.setPlanDate(wParam.getRetrievalPlanDate());
		}
		//#CM723931
		// Order No.
		if (!StringUtil.isBlank(wParam.getOrderNo()))
		{
			wKey.setOrderNo(wParam.getOrderNo());
		}
		else
		{
			wKey.setOrderNo("", "IS NOT NULL");
		}
		if (!StringUtil.isBlank(wParam.getCasePieceflg()) && 
			!wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
		{
			//#CM723932
			// Case/Piece division (Work Type) 
			if(wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				//#CM723933
				// Case 
				wKey.setWorkFormFlag(SystemDefine.CASEPIECE_FLAG_CASE);
			}
			else if(wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				//#CM723934
				// Piece 
				wKey.setWorkFormFlag(SystemDefine.CASEPIECE_FLAG_PIECE);
			}
			else if(wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM723935
				// None 
				wKey.setWorkFormFlag(SystemDefine.CASEPIECE_FLAG_NOTHING);
			}
		}
		
		//#CM723936
		// Obtain the Work Status Info. 
		wWorkinfo = (WorkingInformation[])wObj.find(wKey);
	

		//#CM723937
		// Obtain the Work No. unique to the Work Status Info. 
		for (int i = 0; i < wWorkinfo.length; i ++)
		{
			wJobNo.addElement(wWorkinfo[i].getJobNo());
		}
		
		//#CM723938
		// Set the search conditions and generate the print process class. 
		RetrievalOrderWriter writer = createWriter(conn, wParam, wJobNo);
		
		int result = 0;
		//#CM723939
		// Obtain the count of target data. 
		if (wWorkinfo.length != 0)
		{
			result = writer.count();
			if (result == 0)
			{
				//#CM723940
				// 6003010=No print data found.
				wMessage = "6003010";
			}
		}
		else
		{
			//#CM723941
			//	6003010=No print data found.
			wMessage = "6003010";
		}
		
		return result;
	
	}
		
	//#CM723942
	// Package methods -----------------------------------------------

	//#CM723943
	// Protected methods ---------------------------------------------
	
	//#CM723944
	/**
	 * Set the Information entered via screen for a print process class and
	 * generate a print process class. <BR>
	 * 
	 * @param conn Database connection object
	 * @param wParam Parameter object that includes search conditions 
	 * @param wJobNo Work No.
	 * @return A class to execute the Print process. 
	 */
	protected RetrievalOrderWriter createWriter(
		Connection conn,
		RetrievalSupportParameter wParam,
		Vector wJobNo)
	{
		//#CM723945
		// Generate a Writer class instance 
		RetrievalOrderWriter iWriter = new RetrievalOrderWriter(conn);
		
		//#CM723946
		// Work No.
		iWriter.setJobNo(wJobNo);
		
		//#CM723947
		// Division 
		if(!StringUtil.isBlank(wParam.getCasePieceflg()))
		{
			if(wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				iWriter.setCasePieceFlag(RetrievalSupportParameter.CASEPIECE_FLAG_CASE) ;
			}
			else if(wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				iWriter.setCasePieceFlag(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE) ;
			}
			else if(wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				iWriter.setCasePieceFlag(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING) ;
			}
			else if(wParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
			{
				iWriter.setCasePieceFlag(RetrievalSupportParameter.CASEPIECE_FLAG_ALL) ;
			}
			    
		}
		//#CM723948
		// Work status:
		if(!StringUtil.isBlank(wParam.getWorkStatus()))
		{					
			iWriter.setWorkStatus(wParam.getWorkStatus());	
		}
		return iWriter;
	}

	//#CM723949
	// Private methods -----------------------------------------------
}
//#CM723950
//end of class
