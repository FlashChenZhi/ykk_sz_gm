// $Id: Id0022Process.java,v 1.2 2006/11/14 06:09:04 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701446
/*
 * Copyright 2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.sql.Connection;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.RftConsignor;

//#CM701447
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * The class to do Consignor  list request (ID0022) processing. <BR>
 * Succeed to <CODE>IdProcess</CODE> class, and mount necessary processing. <BR>
 * Acquisition of Search condition from Receiving telegram and retrieval of Consignor List information<BR>
 * Make Consignor list (Consignor Code ascending order) file, and generate Response telegram. <BR>
 * The retrieval and the file making processing use the Id0022Operate class. <BR>
 * <BR>
 * Consignor list demand processing(<CODE>processReceivedId()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Receive reception telegram as a parameter, and generate response telegram. <BR>
 *   Acquire Work flag and Work plan date from Receiving telegram, and acquire Consignor List information as Search condition. <BR>
 *   Make the Consignor list file, and generate NormalResponse telegram when you can acquire Consignor List information. <BR>
 *   Distinguish the cause, and generate abnormal Response telegram when you cannot acquire Consignor List information. <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:04 $
 * @author  $Author: suresh $
 */
public class Id0022Process extends IdProcess
{
	//#CM701448
	// Class fields----------------------------------------------------
	//#CM701449
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0022Process";

	//#CM701450
	// Class variables -----------------------------------------------
	//#CM701451
	// Class method --------------------------------------------------
	//#CM701452
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:04 $";
	}

	//#CM701453
	// Constructors --------------------------------------------------
	//#CM701454
	/**
	 * Generate the instance. 
	 */
	public Id0022Process()
	{
		super();
	}

	//#CM701455
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	//#CM701456
	// Unused
	public Id0022Process(Connection conn)
	{
		this();
		wConn = conn;
	}

	//#CM701457
	// Public methods ------------------------------------------------
	//#CM701458
	/**
	 * Do the Consignor list request processing.
	 * Retrieve Consignor list of received Work type, and write the retrieval result in the file. 
	 * Moreover, set Consignor List file name in Transmission telegram. 
	 * @param  rdt  Receiving buffer
	 * @param  sdt  Sending buffer
	 * @throws Exception It reports on all exceptions.  
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM701459
		// The instance for reception telegram analysis is generated. 
		RFTId0022 rftId0022 = null;
		//#CM701460
		// The instance for sending telegram making is generated. 
		RFTId5022 rftId5022 = null;
		try
		{
			//#CM701461
			// The instance for reception telegram analysis is generated. 
			rftId0022 = (RFTId0022) PackageManager.getObject("RFTId0022");
			rftId0022.setReceiveMessage(rdt);

			//#CM701462
			// The instance for sending telegram making is generated. 
			rftId5022 = (RFTId5022) PackageManager.getObject("RFTId5022");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}

		//#CM701463
		// Work plan date is acquired from Receiving telegram. 
		String planDate = rftId0022.getPlanDate();

		//#CM701464
		// Work type is acquired from Receiving telegram. 
		String workType = rftId0022.getWorkType();

		//#CM701465
		// Detail Work type is acquired from Receiving telegram. 
		String workDetails = rftId0022.getWorkDetails();

		//#CM701466
		// RFT machine is acquired from Receiving telegram. 
		String rftNo = rftId0022.getRftNo();

		//#CM701467
		// Variable which maintains Response flag
		String ansCode = RFTId5022.ANS_CODE_NORMAL;

		//#CM701468
		// Variable which maintains detailed error
		String errDetails = RFTId5022.ErrorDetails.NORMAL;

		//#CM701469
		// Array which maintains acquired Consignor information
		RftConsignor[] consignor = null;

		//#CM701470
		// File Name is made. 
		String sendpath = WmsParam.RFTSEND; // wms/rft/send/

		//#CM701471
		// Transmission File Name
		String sendFileName = sendpath + rftNo + "\\" + Id5022DataFile.ANS_FILE_NAME;

		String className = "";

		try
		{
			//#CM701472
			// The instance of Id0111Operate is generated. 
		    className = "Id0022Operate";
			Id0022Operate id0022Operate =
			    (Id0022Operate) PackageManager.getObject(className);
			id0022Operate.setConnection(wConn);

			//#CM701473
			// Consignor List information is acquired. 
			consignor = id0022Operate.findConsignorCode(planDate, workType, workDetails, rftNo, rftId0022.getWorkerCode());

			//#CM701474
			// The Consignor list file is made. 
			className = "Id5022DataFile";
			Id5022DataFile listFile = (Id5022DataFile) PackageManager.getObject(className);
			listFile.setFileName(sendFileName);
			listFile.write(consignor);
		}
		//#CM701475
		// Error when information cannot be acquired from instance of Id0111Operate
		catch (NotFoundException e)
		{
			//#CM701476
			// Response flag : There is no pertinent Consignor . 
			ansCode = RFTId5022.ANS_CODE_NULL;
		}
		//#CM701477
		// When abnormality occurs by the connection with the Data base
		catch (ReadWriteException e)
		{
			//#CM701478
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM701479
			// Response flag :  Error
			ansCode = RFTId5022.ANS_CODE_ERROR;
			errDetails = RFTId5022.ErrorDetails.DB_ACCESS_ERROR;
		}
		//#CM701480
		// In case of illegal Work flag
		catch (IllegalArgumentException e)
		{
			//#CM701481
			// 6006016=Definition error occurred. {1} was set in Item={0}.
			RftLogMessage.print(
				6006016,
				LogMessage.F_ERROR,
				CLASS_NAME,
				"WorkType",
				e.getMessage());
			//#CM701482
			// Response flag :  Error
			ansCode = RFTId5022.ANS_CODE_ERROR;
			errDetails = RFTId5022.ErrorDetails.PARAMETER_ERROR;
		}
		//#CM701483
		// Additionally, when abnormality occurs
		catch (IllegalAccessException e)
		{
		    //#CM701484
		    // 6006003 = Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, className, e.getMessage());
			//#CM701485
			// Response flag :  Error
			ansCode = RFTId5022.ANS_CODE_ERROR ;
			errDetails = RFTId5022.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (IOException e)
		{
		    //#CM701486
		    // 6006020 = File I/O error occurred. {0}
			RftLogMessage.print(6006020, LogMessage.F_ERROR, CLASS_NAME, sendFileName);
			//#CM701487
			// Response flag :  Error
			ansCode = RFTId5022.ANS_CODE_ERROR ;
			errDetails = RFTId5022.ErrorDetails.I_O_ERROR;
		}
		catch (ScheduleException e)
		{
			//#CM701488
			// 6006001=Unexpected error occurred.{0}{0}
			RftLogMessage.print(6006001, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			//#CM701489
			// Response flag :  Error
			ansCode = RFTId5022.ANS_CODE_ERROR ;
			errDetails = RFTId5022.ErrorDetails.SCHEDULE_ERROR;
		}
		catch (Exception e)
		{
			//#CM701490
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM701491
			// Response flag :  Error
			ansCode = RFTId5022.ANS_CODE_ERROR;
			errDetails = RFTId5022.ErrorDetails.INTERNAL_ERROR;
		}

		//#CM701492
		// Response telegram making
		//#CM701493
		// STX
		rftId5022.setSTX();

		//#CM701494
		// SEQ
		rftId5022.setSEQ(0);

		//#CM701495
		// ID
		rftId5022.setID(RFTId5022.ID);

		//#CM701496
		// RFT transmission time
		rftId5022.setRftSendDate(rftId0022.getRftSendDate());

		//#CM701497
		// SERVER transmission time
		rftId5022.setServSendDate();

		//#CM701498
		// RFT machine
		rftId5022.setRftNo(rftNo);

		//#CM701499
		// Worker code
		rftId5022.setWorkerCode(rftId0022.getWorkerCode());

		//#CM701500
		// Consignor List file name
		rftId5022.setListFileName(sendFileName);

		//#CM701501
		// Number of Failure Code
		if (consignor != null)
		{
			rftId5022.setFileRecordNumber(consignor.length);
		}
		else
		{
			rftId5022.setFileRecordNumber(0);
		}

		//#CM701502
		// Response flag
		rftId5022.setAnsCode(ansCode);

		//#CM701503
		//  Detailed Error
		rftId5022.setErrDetails(errDetails);

		//#CM701504
		// ETX
		rftId5022.setETX();

		//#CM701505
		// Acquire response telegram. 
		rftId5022.getSendMessage(sdt);

	}

	//#CM701506
	// Package methods -----------------------------------------------

	//#CM701507
	// Protected methods ---------------------------------------------

	//#CM701508
	// Private methods -----------------------------------------------
}
//#CM701509
//end of class
