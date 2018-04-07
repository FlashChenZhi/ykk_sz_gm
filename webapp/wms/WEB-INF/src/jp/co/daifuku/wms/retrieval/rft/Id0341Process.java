// $Id: Id0341Process.java,v 1.3 2007/02/07 04:19:41 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

//#CM720981
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.base.rft.WorkingInformation;

//#CM720982
/**
 * Execute the process for sending Item Picking Result data from RFT.
 * Execute the process to complete the Item Picking using the functions provided by Id0341Operate class.
 * For data with Response flag "0: Normal", update the electronic statement field item of the RFT Work Status to NULL.
 *    [UpdateContent] 
 *   <UL><LI>the response electronic statement: NULL</LI>
 *       <LI>Last update date/time: Date/time on system</LI>
 *       <LI>Last update process name: ID0341</LI>
 *   </UL>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:41 $
 * @author  $Author: suresh $
 */
public class Id0341Process extends IdProcess
{
	//#CM720983
	// Class fields----------------------------------------------------
	//#CM720984
	/**
	 * A field that represents a class.
	 */
	private static final String CLASS_NAME = "Id0341Process";
	
	//#CM720985
	/**
	 * Name of Process for updating
	 */
	private static final String PROCESS_NAME = "ID0341";

	//#CM720986
	// Class variables -----------------------------------------------

	//#CM720987
	// Class method --------------------------------------------------
	//#CM720988
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $,$Date: 2007/02/07 04:19:41 $";
	}

	//#CM720989
	// Constructors --------------------------------------------------
	//#CM720990
	// Public methods ------------------------------------------------
	//#CM720991
	/**
	 * Execute the process for sending Item Picking Result data.
	 * Execute the process to complete the Item Picking using the functions provided by Id0341Operate class.
	 * If the Process completed normally, Delete Sending/Received electronic statement file.
	 * For data with Response flag "0: Normal", update the electronic statement field item of the RFT Work Status to NULL. <BR>
	 *    [UpdateContent] 
	 *   <UL><LI>the response electronic statement: NULL</LI>
	 *       <LI>Last update date/time: Date/time on system</LI>
	 *       <LI>Last update process name: ID0341</LI>
	 *   </UL>
	 * @param  rdt  Received buffer
	 * @param  sdt  Buffer from which sends data
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM720992
		// Generate the instance for analyzing the Received electronic statement.
		RFTId0341 rftId0341 = null;

		//#CM720993
		// Generate an instance for generating a sending electronic statement
		RFTId5341 rftId5341 = null;

		try
		{
			//#CM720994
			// Generate the instance for analyzing the Received electronic statement.
			rftId0341 = (RFTId0341) PackageManager.getObject("RFTId0341");
			rftId0341.setReceiveMessage(rdt);

			//#CM720995
			// Generate an instance for generating a sending electronic statement
			rftId5341 = (RFTId5341) PackageManager.getObject("RFTId5341");
		}
		catch (IllegalAccessException e)
		{
        	//#CM720996
        	// 6006003=Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*341", e.getMessage());
			throw e;
		}
		WorkingInformation resultData = new WorkingInformation();
		
		//#CM720997
		// Obtain the RFT machine from the Received electronic statement.
		resultData.setTerminalNo(rftId0341.getRftNo());

		//#CM720998
		// Obtain the Personnel Code from the received electronic statement.
		resultData.setWorkerCode(rftId0341.getWorkerCode());

		//#CM720999
		// Obtain the Consignor code from the Received electronic statement.
		resultData.setConsignorCode(rftId0341.getConsignorCode());

		//#CM721000
		// Obtain the Planned Picking Date from the received electronic statement.
		resultData.setPlanDate(rftId0341.getPickingPlanDate());

		//#CM721001
		// Obtain the JAN code from the Received electronic statement.
		resultData.setItemCode(rftId0341.getJANCode());
		
		//#CM721002
		// Obtain the Case/Piece division from the received electronic statement.
		resultData.setWorkFormFlag(rftId0341.getCasePieceFlag());

		//#CM721003
		// Obtain the Aggregation Work No. ().
		resultData.setCollectJobNo(rftId0341.getItemId());

		//#CM721004
		// Obtain the location from the received electronic statement.
		resultData.setLocationNo(rftId0341.getLocation());

		//#CM721005
		// Obtain the expiry date from the Received electronic statement.
		resultData.setResultUseByDate(rftId0341.getUseByDate());

		//#CM721006
		// Obtain the Completion class from the received electronic statement.
		String completionFlag = rftId0341.getCompletionFlag() ;
		
		//#CM721007
		// Variable for maintaining the Response flag
		String ansCode = RFTId5341.ANS_CODE_NORMAL ;
		
		//#CM721008
		// Variable for maintaining the detail of the error.
		String errorDetail = RFTId5341.ErrorDetails.NORMAL;

		try
		{
			//#CM721009
			/* 2006/6/20 v2.6.0 START K.Mukai Checking for prohibited characters used in Expiry Date was added. */

			if(!completionFlag.equals(RFTId0341.COMPLETION_FLAG_CANCEL) && DisplayText.isPatternMatching(resultData.getResultUseByDate()))
			{
				throw new InvalidDefineException("USE_BY_DATE[" + resultData.getResultUseByDate() +"]");
			}
			//#CM721010
			/* 2006/6/20 END */

			
			//#CM721011
			// Obtain the Planned Picking Qty from the received electronic statement.
			int planQty = rftId0341.getPickingInstructionQty();
			resultData.setPlanEnableQty(planQty);
			//#CM721012
			// Obtain the Result qty from the received electronic statement.
			int resultQty = rftId0341.getPickingResultQty();
			resultData.setResultQty(resultQty);
			
			//#CM721013
			// Obtain the work time from the Received electronic statement.
			int workTime = rftId0341.getWorkSeconds();
			
			//#CM721014
			// Obtain the count of mis-scanning from the received electronic statement.
			int missScanCnt = rftId0341.getMissScanCnt();

			//#CM721015
			// Generate the Id0341Operate instance.
			Id0341Operate id0341Operate = (Id0341Operate) PackageManager.getObject("Id0341Operate");
			id0341Operate.setConnection(wConn);
			
			ansCode = id0341Operate.doComplete(resultData, completionFlag, workTime, missScanCnt);
			errorDetail = id0341Operate.getErrorDetails();
		    if(ansCode.equals(RFTId5341.ANS_CODE_NORMAL))
		    {
			    //#CM721016
			    // For data with Response flag "0: Normal", delete the processing data.
			    RFTId5341.deleteWorkingData(rftId0341.getRftNo(), PROCESS_NAME, wConn);
			    wConn.commit();
			}
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "Id0341Operate", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			//#CM721017
			// Response flag: Error
			ansCode = RFTId5341.ANS_CODE_ERROR;
			errorDetail = RFTId5341.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (ReadWriteException e)
		{
			//#CM721018
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			errorDetail = RFTId5341.ErrorDetails.DB_ACCESS_ERROR;
			ansCode = RFTId5341.ANS_CODE_ERROR;
		}
		catch (InvalidDefineException e)
		{
			//#CM721019
			// 6026022=Blank or prohibited character is included in the specified value. {0}
			//#CM721020
			/* 2006/6/20 v2.6.0 START K.Mukai Checking for prohibited characters used in Expiry Date was added. */

			RftLogMessage.print(6026022, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			//#CM721021
			/* 2006/6/20 END */

			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			errorDetail = RFTId5341.ErrorDetails.PARAMETER_ERROR;
			ansCode = RFTId5341.ANS_CODE_ERROR;

		}
		catch (SQLException e)
		{
			//#CM721022
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			errorDetail = RFTId5341.ErrorDetails.DB_ACCESS_ERROR;
			ansCode = RFTId5341.ANS_CODE_ERROR;
		}
		catch(NumberFormatException e)
		{			
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM721023
			// Response flag: Error
			errorDetail = RFTId5341.ErrorDetails.PARAMETER_ERROR;
			ansCode = RFTId5341.ANS_CODE_ERROR;			
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM721024
			// Response flag: Error
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			errorDetail = RFTId5341.ErrorDetails.INTERNAL_ERROR;
			ansCode = RFTId5341.ANS_CODE_ERROR;
		}
		
		//#CM721025
		// Generate Response Electronic Statement.
		//#CM721026
		// STX
		rftId5341.setSTX();
		//#CM721027
		// SEQ
		rftId5341.setSEQ(0);
		//#CM721028
		// ID
		rftId5341.setID(RFTId5341.ID);
		//#CM721029
		// Period for sending by RFT
		rftId5341.setRftSendDate(rftId0341.getRftSendDate());
		//#CM721030
		// Period for sending by SERVER
		rftId5341.setServSendDate() ;
		//#CM721031
		// RFT Machine
		rftId5341.setRftNo(rftId0341.getRftNo());

		//#CM721032
		// Worker Code
		rftId5341.setWorkerCode(resultData.getWorkerCode());

		//#CM721033
		// Response flag
		rftId5341.setAnsCode(ansCode) ;
		
		//#CM721034
		// Details of error
		rftId5341.setErrDetails(errorDetail);
		
		//#CM721035
		// ETX
		rftId5341.setETX() ;
		
		//#CM721036
		// Obtain the response electronic statement.
		rftId5341.getSendMessage(sdt) ;
	}

	//#CM721037
	// Package methods -----------------------------------------------

	//#CM721038
	// Protected methods ---------------------------------------------

	//#CM721039
	// Private methods -----------------------------------------------

}
//#CM721040
//end of class

