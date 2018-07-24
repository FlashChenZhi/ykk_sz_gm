//#CM701524
//$Id: Id0023Process.java,v 1.2 2006/11/14 06:09:05 suresh Exp $
package jp.co.daifuku.wms.base.rft;
//#CM701525
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
import jp.co.daifuku.wms.base.entity.ZoneView;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM701526
/**
 * Process it to the Area list demand by RFT. 
 * Retrieve the list of Area information by using the function that the Id0023Operate class offers, 
 * and transmit to RFT. 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/04</TD><TD>etakeda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:05 $
 * @author etakeda
 **/
public class Id0023Process extends IdProcess
{

//#CM701527
//  Class fields----------------------------------------------------
	//#CM701528
	/**
	 * Field which shows class
	 */
	private static final String CLASS_NAME = "Id0023Process";
	
	//#CM701529
	// Class variables -----------------------------------------------

	//#CM701530
	// Class method --------------------------------------------------
	//#CM701531
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:05 $";
	}

	//#CM701532
	// Constructors --------------------------------------------------
	//#CM701533
	/**
	 * Generate the instance. 
	 */
	public Id0023Process()
	{
		super() ;
	}
	
	//#CM701534
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public Id0023Process(Connection conn)
	{
		super() ;
		wConn = conn ;
	}

	//#CM701535
	// Public methods ------------------------------------------------
	//#CM701536
	/**
	 * Do the Area list response processing. 
	 * Retrieve the Area list of received Area  No., and write the retrieval result in the file. <BR>
	 * Remove from Search condition when Area  No. is empty. <BR>
	 * Moreover, set AreaList file name in Transmission telegram. 
	 * @param  rdt  Receiving buffer
	 * @param  sdt  Sending buffer
	 * @throws Exception It reports on all exceptions.  
	 */
    public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
    {
		//#CM701537
		// The instance for reception telegram analysis is generated. 
		RFTId0023 rftId0023 = null;
		//#CM701538
		// The instance for sending telegram making is generated. 
		RFTId5023 rftId5023 = null;
		try
		{
			//#CM701539
			// The instance for reception telegram analysis is generated. 
			rftId0023 = (RFTId0023) PackageManager.getObject("RFTId0023");
			rftId0023.setReceiveMessage(rdt);

			//#CM701540
			// The instance for sending telegram making is generated. 
			rftId5023 = (RFTId5023) PackageManager.getObject("RFTId5023");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}

		//#CM701541
		// Title machine No is acquired from Receiving telegram. 
		String rftNo = rftId0023.getRftNo();
		
		//#CM701542
		// Worker code is acquired from Receiving telegram. 
		String workerCode = rftId0023.getWorkerCode();
		
		//#CM701543
		// Area No is acquired from Receiving telegram. 
		String areaNo = rftId0023.getAreaNo();

		
		//#CM701544
		// Variable which maintains Response flag
		String ansCode = RFTId5023.ANS_CODE_NORMAL;

		//#CM701545
		// Array which maintains acquired Area information
		ZoneView[] zoneView = null;

		//#CM701546
		// File Name is made. 
		String sendpath = WmsParam.RFTSEND; // wms/rft/send/

		//#CM701547
		// Transmission File Name
		String sendFileName = sendpath + rftNo + "\\" + Id5023DataFile.ANS_FILE_NAME;

		//#CM701548
		//  Detailed Error
		String errDetails = RFTId5023.ErrorDetails.NORMAL;

		String className = "";

		try
		{
			//#CM701549
			// The instance of Id0023Operate is generated. 
			Id0023Operate id0023Operate =
			    (Id0023Operate) PackageManager.getObject("Id0023Operate");
			id0023Operate.setConnection(wConn);

			//#CM701550
			// AreaList information is acquired. 
			zoneView = id0023Operate.getAreaList(areaNo);

			//#CM701551
			// The Consignor list file is made. 
			className = "Id5023DataFile";
			Id5023DataFile listFile = (Id5023DataFile) PackageManager.getObject(className);
			listFile.setFileName(sendFileName);
			listFile.write(zoneView);
		}
		//#CM701552
		// When Error is caused by the instance generation
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME,
					className, e.getMessage());
			//#CM701553
			// Response flag :  Error
			ansCode = RFTId5023.ANS_CODE_ERROR;
			errDetails = RFTId5023.ErrorDetails.INSTACIATE_ERROR;
		}
		//#CM701554
		// Error when information cannot be acquired from instance of Id0023Operate
		catch (NotFoundException e)
		{
			//#CM701555
			// Response flag : There is no correspondence Stock. 
			ansCode = RFTId5023.ANS_CODE_NULL;
		}		
		//#CM701556
		// When abnormality occurs by the connection with the Data base
		catch (ReadWriteException e)
		{
			//#CM701557
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM701558
			// Response flag :  Error
			ansCode = RFTId5023.ANS_CODE_ERROR;
			errDetails = RFTId5023.ErrorDetails.DB_ACCESS_ERROR;
		}
		//#CM701559
		// When Error is caused by the I/O of the file
		catch (IOException e)
		{
		    //#CM701560
		    // 6006020=File I/O error occurred. {0}
		    RftLogMessage.printStackTrace(6006020, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM701561
			// Response flag :  Error
			ansCode = RFTId5023.ANS_CODE_ERROR;
			errDetails = RFTId5023.ErrorDetails.I_O_ERROR;
		}
		//#CM701562
		// Additionally, when abnormality occurs
		catch (Exception e)
		{
			//#CM701563
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM701564
			// Response flag :  Error
			ansCode = RFTId5023.ANS_CODE_ERROR;
			errDetails = RFTId5023.ErrorDetails.INTERNAL_ERROR;
		}

		//#CM701565
		// Response telegram making
		//#CM701566
		// STX
		rftId5023.setSTX();

		//#CM701567
		// SEQ
		rftId5023.setSEQ(0);

		//#CM701568
		// ID
		rftId5023.setID(RFTId5023.ID);

		//#CM701569
		// RFT transmission time
		rftId5023.setRftSendDate(rftId0023.getRftSendDate());

		//#CM701570
		// SERVER transmission time
		rftId5023.setServSendDate();

		//#CM701571
		// RFT machine
		rftId5023.setRftNo(rftNo);

		//#CM701572
		// Person in charge code
		rftId5023.setWorkerCode(workerCode);

		//#CM701573
		// AreaList file name
		rftId5023.setTableFileName(sendFileName);

		//#CM701574
		// Number of Failure Code
		if (zoneView != null)
		{
			rftId5023.setFileRecordNumber(zoneView.length);
		}
		else
		{
			rftId5023.setFileRecordNumber(0);
		}

		//#CM701575
		// Response flag
		//#CM701576
		// The Normal flag is set when there is no value in the flag. 
		rftId5023.setAnsCode(ansCode);
		
		//#CM701577
		//  Detailed Error 
		rftId5023.setErrDetails(errDetails);

		//#CM701578
		// ETX
		rftId5023.setETX();

		//#CM701579
		// Acquire response telegram. 
		rftId5023.getSendMessage(sdt);

    }

    //#CM701580
    //Package methods -----------------------------------------------

	//#CM701581
	// Protected methods ---------------------------------------------

	//#CM701582
	// Private methods -----------------------------------------------
}
//#CM701583
//end of class
