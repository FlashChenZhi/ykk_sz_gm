// $Id: Id0010Process.java,v 1.2 2006/11/14 06:09:00 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM700819
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;

//#CM700820
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * The class to process Worker code Inquiry(ID0010). <BR>
 * Succeed to <CODE>IdProcess</CODE> class, and mount necessary processing. <BR>
 * Acquire Search condition from Receiving telegram, retrieve the worker and RFT administrative information, and generate Response telegram. <BR>
 * Retrieval processing uses the Base Operate class. <BR>
 * <BR>
 * Worker code Inquiry processing(<CODE>processReceivedId()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Receive reception telegram as a parameter, and generate response telegram. <BR>
 *   Acquire Worker code and RFT machine No from reception telegram, and confirm whether a pertinent worker is working in the other end,<BR>
 *   Assume Acquisition Data to be Search condition and acquire the Worker name while not working. <BR>
 *   Generate Normal Response telegram when you can acquire the Consignor name. <BR>
 *   Distinguish the cause, and generate abnormal Response telegram when you cannot acquire the Consignor Name. <BR>
 *   Or, Generate response telegram of It is working in the other end when a pertinent worker is It is working in the other end. <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:00 $
 * @author  $Author: suresh $
 */
public class Id0010Process extends IdProcess
{
	//#CM700821
	// Class fields----------------------------------------------------
	//#CM700822
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0010Process";

	//#CM700823
	// Class variables -----------------------------------------------

	//#CM700824
	// Class method --------------------------------------------------
	//#CM700825
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:00 $";
	}

	//#CM700826
	// Constructors --------------------------------------------------
	//#CM700827
	/**
	 * Generate the instance. 
	 */
	public Id0010Process()
	{
		super();
	}

	//#CM700828
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public Id0010Process(Connection conn)
	{
		super();
		wConn = conn;
	}

	//#CM700829
	// Public methods ------------------------------------------------
	//#CM700830
	/**
	 * Inquire Worker code and process it. 
	 * Retrieve Worker name of received Worker code, and set the retrieval result in Worker name. 
	 *  (Do not check whether to work with another Terminal. ) 
	 * @param  rdt  Receiving buffer
	 * @param  sdt  Sending buffer
	 * @throws Exception It reports on all exceptions.  
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM700831
		// The instance for reception telegram analysis is generated. 
		RFTId0010 rftId0010 = null;
		//#CM700832
		// The instance for sending telegram making is generated. 
		RFTId5010 rftId5010 = null;
		try
		{
			//#CM700833
			// The instance for reception telegram analysis is generated. 
			rftId0010 = (RFTId0010) PackageManager.getObject("RFTId0010");
			rftId0010.setReceiveMessage(rdt);

			//#CM700834
			// The instance for sending telegram making is generated. 
			rftId5010 = (RFTId5010) PackageManager.getObject("RFTId5010");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}

		//#CM700835
		// Variable which maintains Response flag
		String ansCode = RFTId5010.ANS_CODE_NORMAL;

		//#CM700836
		// Variable which maintains detailed error
		String errDetails = RFTId5010.ErrorDetails.NORMAL;

		//#CM700837
		// Variable which maintains Worker name
		String workerName = "";

		//#CM700838
		// RFT machine  No. is acquired from Receiving telegram. 
		String receiveRftNo = rftId0010.getRftNo();

		//#CM700839
		// Worker code is acquired from Receiving telegram. 
		String workerCode = rftId0010.getWorkerCode();

		try
		{
			//#CM700840
			// The instance of BaseOperate is generated. 
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);

			//#CM700841
			// The Worker name name is acquired from Id0004Operate. 
			workerName = baseOperate.getWorkerName(workerCode);

		}
		//#CM700842
		// When you cannot acquire information from the WorkerOperate instance
		catch (NotFoundException e)
		{
			//#CM700843
			// Response flag : There is no pertinent worker. 
			ansCode = RFTId5010.ANS_CODE_NULL;
		}
		//#CM700844
		// When there is Other Error
		catch (ReadWriteException e)
		{
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM700845
			// Response flag :  Error
			ansCode = RFTId5010.ANS_CODE_ERROR;
			errDetails = RFTId5010.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (IllegalAccessException e)
		{
			//#CM700846
			// It fails in the instance generation. 
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM700847
			// Response flag :  Error
			ansCode = RFTId5010.ANS_CODE_ERROR;
			errDetails = RFTId5010.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM700848
			// Response flag :  Error
			ansCode = RFTId5010.ANS_CODE_ERROR;
			errDetails = RFTId5010.ErrorDetails.INTERNAL_ERROR;
		}

		//#CM700849
		// Response telegram making
		//#CM700850
		// STX
		rftId5010.setSTX();
		//#CM700851
		// SEQ
		rftId5010.setSEQ(0);
		//#CM700852
		// ID
		rftId5010.setID(RFTId5010.ID);
		//#CM700853
		// RFT transmission time
		rftId5010.setRftSendDate(rftId0010.getRftSendDate());
		//#CM700854
		// SERVER transmission time
		rftId5010.setServSendDate();
		//#CM700855
		// RFT machine
		rftId5010.setRftNo(receiveRftNo);

		//#CM700856
		// Worker code
		rftId5010.setWorkerCode(workerCode);
		//#CM700857
		// Worker name
		rftId5010.setWorkerName(workerName);
		//#CM700858
		// Response flag
		rftId5010.setAnsCode(ansCode);
		//#CM700859
		//  Detailed Error
		rftId5010.setErrDetails(errDetails);

		//#CM700860
		// ETX
		rftId5010.setETX();

		//#CM700861
		// Acquire response telegram. 
		rftId5010.getSendMessage(sdt);

	}

	//#CM700862
	// Package methods -----------------------------------------------

	//#CM700863
	// Protected methods ---------------------------------------------

	//#CM700864
	// Private methods -----------------------------------------------

}
//#CM700865
//end of class
