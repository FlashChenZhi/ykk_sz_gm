// $Id: Id0021Process.java,v 1.2 2006/11/14 06:09:04 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701285
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
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

//#CM701286
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * The class to do list request (ID0021) processing of the date. <BR>
 * Succeed to <CODE>IdProcess</CODE> class, and mount necessary processing. <BR>
 * Acquisition of Search condition from Receiving telegram and retrieval of Date List information<BR>
 * Make list (Date ascending order) file of the date and generate Response telegram. <BR>
 * The retrieval and the file making processing use the Id0021Operate class. <BR>
 * <BR>
 * List request processing of date(<CODE>processReceivedId()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Receive reception telegram as a parameter, and generate response telegram. <BR>
 *   Acquire Work flag and Consignor Code from Receiving telegram, and acquire Date List information as Search condition. <BR>
 *   Make the list file of the date, and generate NormalResponse telegram when you can acquire Date List information. <BR>
 *   Distinguish the cause, and generate abnormal Response telegram when you cannot acquire Date List information. <BR>
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
public class Id0021Process extends IdProcess
{
	//#CM701287
	// Class fields----------------------------------------------------
	//#CM701288
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0021Process";

	//#CM701289
	// Class variables -----------------------------------------------
	//#CM701290
	// Class method --------------------------------------------------
	//#CM701291
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:04 $";
	}

	//#CM701292
	// Constructors --------------------------------------------------
	//#CM701293
	/**
	 * Generate the instance. 
	 */
	public Id0021Process()
	{
		super();
	}

	//#CM701294
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public Id0021Process(Connection conn)
	{
		this();
		wConn = conn;
	}

	//#CM701295
	// Public methods ------------------------------------------------
	//#CM701296
	/**
	 * Do the List request processing of date.
	 * Retrieve the date list of received Work type, and write the retrieval result in the file. 
	 * Moreover, set date List file name in Transmission telegram. 
	 * @param  rdt  Receiving buffer
	 * @param  sdt  Sending buffer
	 * @throws Exception It reports on all exceptions.  
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM701297
		// The instance for reception telegram analysis is generated. 
		RFTId0021 rftId0021 = null;
		//#CM701298
		// The instance for sending telegram making is generated. 
		RFTId5021 rftId5021 = null;
		try
		{
			//#CM701299
			// The instance for reception telegram analysis is generated. 
			rftId0021 = (RFTId0021) PackageManager.getObject("RFTId0021");
			rftId0021.setReceiveMessage(rdt);

			//#CM701300
			// The instance for sending telegram making is generated. 
			rftId5021 = (RFTId5021) PackageManager.getObject("RFTId5021");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*021", e.getMessage());
			throw e;
		}

		//#CM701301
		// Work type is acquired from Receiving telegram. 
		String workType = rftId0021.getWorkType();

		//#CM701302
		// Detail Work type is acquired from Receiving telegram. 
		String workDetails = rftId0021.getWorkDetails();

		//#CM701303
		// Consignor Code is acquired from Receiving telegram. 
		String consignorCode = rftId0021.getConsignorCode();

		//#CM701304
		// RFT machine is acquired from Receiving telegram. 
		String rftNo = rftId0021.getRftNo();

		//#CM701305
		// Variable which maintains Response flag
		String ansCode = RFTId5021.ANS_CODE_NORMAL;

		//#CM701306
		// Variable which maintains detailed error
		String errDetails = RFTId5021.ErrorDetails.NORMAL;

		//#CM701307
		// Array which maintains Work plan date written in file
		String[] planDate = null;

		//#CM701308
		// File Name is made. 
		String sendpath = WmsParam.RFTSEND; // wms/rft/send/

		//#CM701309
		// Transmission File Name
		String sendFileName = sendpath + rftNo + "\\" + Id5021DataFile.ANS_FILE_NAME;

		String className = "";

		try
		{
			//#CM701310
			// The instance of Id0110Operate is generated. 
		    className = "Id0021Operate";
			Id0021Operate id0021Operate = (Id0021Operate) PackageManager.getObject(className);
			id0021Operate.setConnection(wConn);

			//#CM701311
			// The list at the date is acquired. 
			planDate = id0021Operate.findPlanDate(
					consignorCode,
					workType,
					workDetails,
					rftNo,
					rftId0021.getWorkerCode());

			//#CM701312
			// The Plan date list file is made. 
			className = "Id5021DataFile";
			Id5021DataFile listFile = (Id5021DataFile) PackageManager.getObject(className);
			listFile.setFileName(sendFileName);
			listFile.write(planDate);
		}
		//#CM701313
		// Error when information cannot be acquired
		catch (NotFoundException e)
		{
			//#CM701314
			// Response flag : There is no correspondence Data. 
			ansCode = RFTId5021.ANS_CODE_NULL;
		}
		//#CM701315
		// When there is Other Error
		catch (ReadWriteException e)
		{
			//#CM701316
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM701317
			// Response flag :  Error
			ansCode = RFTId5021.ANS_CODE_ERROR;
			errDetails = RFTId5021.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (IllegalAccessException e)
		{
		    //#CM701318
		    // 6006003 = Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, className, e.getMessage());
			//#CM701319
			// Response flag :  Error
			ansCode = RFTId5021.ANS_CODE_ERROR ;
			errDetails = RFTId5021.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (IOException e)
		{
		    //#CM701320
		    // 6006020 = File I/O error occurred. {0}
			RftLogMessage.print(6006020, LogMessage.F_ERROR, CLASS_NAME, sendFileName);
			//#CM701321
			// Response flag :  Error
			ansCode = RFTId5021.ANS_CODE_ERROR ;
			errDetails = RFTId5021.ErrorDetails.I_O_ERROR;
		}
		catch (Exception e)
		{
			//#CM701322
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM701323
			// Response flag :  Error
			ansCode = RFTId5021.ANS_CODE_ERROR;
			errDetails = RFTId5021.ErrorDetails.INTERNAL_ERROR;
		}

		//#CM701324
		// Response telegram making
		//#CM701325
		// STX
		rftId5021.setSTX();

		//#CM701326
		// SEQ
		rftId5021.setSEQ(0);

		//#CM701327
		// ID
		rftId5021.setID(RFTId5021.ID);

		//#CM701328
		// RFT transmission time
		rftId5021.setRftSendDate(rftId0021.getRftSendDate());

		//#CM701329
		// SERVER transmission time
		rftId5021.setServSendDate();

		//#CM701330
		// RFT machine
		rftId5021.setRftNo(rftNo);

		//#CM701331
		// Worker code
		rftId5021.setWorkerCode(rftId0021.getWorkerCode());

		//#CM701332
		// Plan dateList file name
		rftId5021.setTableFileName(sendFileName);

		//#CM701333
		// Number of Failure Code
		if (planDate == null)
		{
			rftId5021.setFileRecordNumber(0);
		}
		else
		{
			rftId5021.setFileRecordNumber(planDate.length);
		}

		//#CM701334
		// Response flag
		rftId5021.setAnsCode(ansCode);

		//#CM701335
		//  Detailed Error
		rftId5021.setErrDetails(errDetails);

		//#CM701336
		// ETX
		rftId5021.setETX();

		//#CM701337
		// Acquire response telegram. 
		rftId5021.getSendMessage(sdt);

	}

	//#CM701338
	// Package methods -----------------------------------------------

	//#CM701339
	// Protected methods ---------------------------------------------

	//#CM701340
	// Private methods -----------------------------------------------
}
//#CM701341
//end of class
