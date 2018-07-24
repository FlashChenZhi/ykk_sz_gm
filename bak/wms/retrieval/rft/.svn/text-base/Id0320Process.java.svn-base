// $Id: Id0320Process.java,v 1.3 2007/02/07 04:19:38 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

//#CM720379
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.sql.Connection;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.instockreceive.rft.RFTId5121;

//#CM720380
/**
 * Execute the process responding the request from RFT for Picking Order list.
 * Search through the Picking Order list using functions provided by Id0320Operate class, and 
 * send it to RFT.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:38 $
 * @author  $Author: suresh $
 */
public class Id0320Process extends IdProcess
{
	//#CM720381
	// Class fields----------------------------------------------------
	private static final String CLASS_NAME = "Id0320Process";

	//#CM720382
	// Class variables -----------------------------------------------

	//#CM720383
	// Class method --------------------------------------------------
	//#CM720384
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $,$Date: 2007/02/07 04:19:38 $";
	}

	//#CM720385
	// Constructors --------------------------------------------------
	//#CM720386
	/**
	 * Generate an instance.
	 */
	public Id0320Process()
	{
		super() ;
	}
	
	//#CM720387
	/**
	 * Designate <code>Connection</code> for database connection and generate the instance.
	 * @param conn Connection for database connection 
	 */
	public Id0320Process(Connection conn)
	{
		super() ;
		wConn = conn ;
	}

	//#CM720388
	// Public methods ------------------------------------------------
	//#CM720389
	/**
	 * Execute the process for inquiring the Picking Order.
	 * Search for Picking Order View using the received Planned Work Date and the received Consignor Code, and write the search result to file.
	 * Set the Picking Order list file name in the Sending electronic statement.
	 * @param  rdt  Received buffer
	 * @param  sdt  Buffer from which sends data
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM720390
		// Generate the instance for analyzing the Received electronic statement.
		RFTId0320 rftId0320 = null;

		//#CM720391
		// Generate an instance for generating a sending electronic statement
		RFTId5320 rftId5320 = null;

		try
		{
			//#CM720392
			// Generate the instance for analyzing the Received electronic statement.
			rftId0320 = (RFTId0320) PackageManager.getObject("RFTId0320");
			rftId0320.setReceiveMessage(rdt);

			//#CM720393
			// Generate an instance for generating a sending electronic statement
			rftId5320 = (RFTId5320) PackageManager.getObject("RFTId5320");
		}
		catch (IllegalAccessException e)
		{
        	//#CM720394
        	// 6006003=Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*320");
			throw e;
		}
		//#CM720395
		// Obtain the RFT machine from the Received electronic statement.
		String rftNo = rftId0320.getRftNo();
		
		//#CM720396
		// Obtain the Worker code from the Received electronic statement.
		String workerCode = rftId0320.getWorkerCode();
		
		//#CM720397
		// Obtain the Planned Work Date from the received electronic statement.
		String planDate = rftId0320.getPlanDate();
		
		//#CM720398
		// Obtain the Consignor code from the Received electronic statement.
		String consignorCode = rftId0320.getConsignorCode();
		
		//#CM720399
		// Obtain the Case/Piece division from the received electronic statement.
		String workForm = rftId0320.getWorkForm();

		//#CM720400
		// Variable for maintaining the Response flag
		String ansCode = RFTId5320.ANS_CODE_NORMAL;
		
		//#CM720401
		// Variable for maintaining the detail of the error.
		String errDetails = RFTId5320.ErrorDetails.NORMAL;
		
		//#CM720402
		// Array to store the obtained Picking Order info.
		WorkingInformation[] workinfo = new WorkingInformation[0];

		//#CM720403
		// Name of the file to be sent
		String sendFileName = WmsParam.RFTSEND + rftNo + "\\" + Id5320DataFile.ANS_FILE_NAME;
		
		String className = "";
		try
		{
			//#CM720404
			// Generate the Id0320Operate instance.
		    className = "Id0320Operate";
			Id0320Operate id0320Operate = (Id0320Operate) PackageManager.getObject(className);
			id0320Operate.setConnection(wConn);

			//#CM720405
			// Obtain the Order list info.
			workinfo = id0320Operate.getOrderList(planDate, consignorCode, workForm, workerCode, rftNo);

			//#CM720406
			// Generate a Order View file.
			className = "Id5320DataFile";
			Id5320DataFile dataFile = (Id5320DataFile) PackageManager.getObject(className);
			dataFile.setFileName(sendFileName);
			dataFile.write(workinfo);
		}
		//#CM720407
		// Trigger error if failed to obtain information from WorkingInformationOperate instance.
		catch (NotFoundException e)
		{
			//#CM720408
			// Response flag: No corresponding data
			ansCode = RFTId5320.ANS_CODE_NULL;
		}
		//#CM720409
		// If any other error occurs:
		catch (ReadWriteException e)
		{
			//#CM720410
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM720411
			// Response flag: Error
			ansCode = RFTId5320.ANS_CODE_ERROR;
			errDetails = RFTId5320.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (IllegalAccessException e)
        {
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, className, e.getMessage());
			//#CM720412
			// Response flag: Error
			ansCode = RFTId5320.ANS_CODE_ERROR ;
			errDetails = RFTId5320.ErrorDetails.INSTACIATE_ERROR;
        }
		catch (IOException e)
        {
		    //#CM720413
		    // 6006020=File I/O error occurred. {0}
			RftLogMessage.print(6006020, LogMessage.F_ERROR, CLASS_NAME, sendFileName);
			//#CM720414
			// Response flag: Error
			ansCode = RFTId5320.ANS_CODE_ERROR ;
			errDetails = RFTId5320.ErrorDetails.I_O_ERROR;
        }
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM720415
			// Response flag: Error
			ansCode = RFTId5320.ANS_CODE_ERROR;
			errDetails = RFTId5320.ErrorDetails.INTERNAL_ERROR;
		}

		//#CM720416
		// Generate Response Electronic Statement.
		//#CM720417
		// STX
		rftId5320.setSTX();
		
		//#CM720418
		// SEQ
		rftId5320.setSEQ(0);
		
		//#CM720419
		// ID
		rftId5320.setID(RFTId5320.ID);
		
		//#CM720420
		// Period for sending by RFT
		rftId5320.setRftSendDate(rftId0320.getRftSendDate());
		
		//#CM720421
		// Period for sending by SERVER
		rftId5320.setServSendDate();
		
		//#CM720422
		// RFT Machine
		rftId5320.setRftNo(rftNo);

		//#CM720423
		// Personnel Code
		rftId5320.setWorkerCode(rftId0320.getWorkerCode());
		
		//#CM720424
		// Picking Order list file name
		rftId5320.setTableFileName(sendFileName) ;
		
		//#CM720425
		// Number of records in a file.
		if (ansCode.equals(RFTId5121.ANS_CODE_NORMAL))
		{
			rftId5320.setFileRecordNumber(workinfo.length);
		}
		else
		{
			rftId5320.setFileRecordNumber(0);
		}
		
		//#CM720426
		// Response flag
		rftId5320.setAnsCode(ansCode);
		
		//#CM720427
		// Details of error
		rftId5320.setErrDetails(errDetails);
		
		//#CM720428
		// ETX
		rftId5320.setETX() ;

		//#CM720429
		// Obtain the response electronic statement.
		rftId5320.getSendMessage(sdt);

	}

	//#CM720430
	// Package methods -----------------------------------------------

	//#CM720431
	// Protected methods ---------------------------------------------

	//#CM720432
	// Private methods -----------------------------------------------
}
//#CM720433
//end of class
