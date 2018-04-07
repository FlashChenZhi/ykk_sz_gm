// $Id: Id0005Process.java,v 1.2 2006/11/14 06:09:00 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM700722
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.sql.Connection;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;

//#CM700723
/**
 * Designer : E.Takeda <BR>
 * Maker :  E.Takeda <BR>
 * <BR>
 * The class to do request (ID0005) of sending the response again processing. <BR>
 * Succeed to <CODE>IdProcess</CODE> class, and mount necessary processing. <BR>
 * Retrieve RFTWork information, and acquire response telegram. <BR>
 * In the acquired content when Data preservation information is retrieved while working, and correspondence Data exists
 * Make work Data file for the recv folder. <BR>
 * 
 * <BR>
 * Request of sending response again processing(<CODE>processReceivedId()</CODE> Method)<BR>
 *   <OL><LI>Acquire telegram of RFTWork information, and generate response telegram. <BR>
 *   <BR>
 *   <DIR>RFTWork informationRetrieval processing<BR>
 *   [Search condition]
 *     <UL><LI>RFT machine No  : Acquisition from parameter</LI>
 *     </UL>
 *    [Content of acquisition]
 *     <UL><LI>telegram</LI>
 *     </UL>
 *    </DIR>
 *   <BR>
 *   <LI>Generate work File in the recv folder with the file name of working Data preservation information when 
 *   working Data preservation information is retrieved and correspondence Data exists. <BR>
 *   <BR>
 *   <DIR>Data preservation information Retrieval processing when being working<BR>
 *   [Search condition]
 *   <UL><LI>RFT machine No : Acquisition from parameter</LI>
 *   </UL>
 *    [Content of acquisition]
 *    <UL><LI>RFT machine No</LI>
 *        <LI>File Name</LI>
 *        <LI>Row No</LI>
 *        <LI>Data</LI>
 *    </UL>
 *    </DIR>
 *    </OL>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:00 $
 * @author  $Author: suresh $
 */

public class Id0005Process extends IdProcess
{
	//#CM700724
	//Class fields --------------------------------------------------
	//#CM700725
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0005Process";
	
	//#CM700726
	//Class variables -----------------------------------------------
	//#CM700727
	//Class methods -------------------------------------------------
	//#CM700728
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:00 $";
	}

	//#CM700729
	//Constructors --------------------------------------------------
	//#CM700730
	/**
	 * Generate the instance. 
	 */
	public Id0005Process()
	{
		super();
	}

	//#CM700731
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public Id0005Process(Connection conn)
	{
		super();
		wConn = conn;
	}

	//#CM700732
	// Public methods -----------------------------------------------
	//#CM700733
	/**
	 * Process the work beginning report. 
	 * @param  rdt  Receiving buffer
	 * @param  sdt  Sending buffer
	 * @throws Exception It reports on all exceptions.  
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM700734
		//For receiving telegram decomposition
		RFTId0005 rftId0005 = null;
		//#CM700735
		//Transmission telegram
		SendIdMessage sendMessage = null;
		try
		{
			//#CM700736
			// The instance for reception telegram analysis is generated. 
			rftId0005 = (RFTId0005) PackageManager.getObject("RFTId0005");
			rftId0005.setReceiveMessage(rdt);
		}
		catch (IllegalAccessException e)
		{
        	//#CM700737
        	// 6006003=Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*005");
			throw e;
		}

		//#CM700738
		// Variable for response flag
		String ansCode = SendIdMessage.ANS_CODE_NORMAL;

		//#CM700739
		// Variable which maintains detailed error
		String errDetails = SendIdMessage.ErrorDetails.NORMAL;

		//#CM700740
		// RFT machine  No. acquisition from receiving telegram
		String rftNo = rftId0005.getRftNo();

		try
		{
			 //#CM700741
			 // Preservation response telegram acquisition
			 sendMessage = (SendIdMessage)IdMessage.loadMessageFile(rftNo, wConn);
			if(sendMessage != null)	
			{
				 //#CM700742
				 // ID of preservation response telegram is acquired. 
				 String responseIdNo = sendMessage.getID();
	
				if (IdMessage.hasWorkDataFile(responseIdNo))
				{
					//#CM700743
					// Restore the Working Data file in the send folder.
					WorkDataFile.restoreDataFile(rftNo, true, wConn);
				}
			}
		}
		catch (IllegalAccessException e)
		{
			//#CM700744
			// It fails in the instance generation. 
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "WorkDataFile");
			//#CM700745
			// Response flag :  Error
			ansCode = SendIdMessage.ANS_CODE_ERROR;
			errDetails = SendIdMessage.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (IOException e)
		{
			//#CM700746
			// 6006020=File I/O error occurred. {0}
			RftLogMessage.printStackTrace(6006020, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM700747
			//Response flag :  Error
			ansCode = SendIdMessage.ANS_CODE_ERROR;
			errDetails = SendIdMessage.ErrorDetails.I_O_ERROR;
		}
		catch (ReadWriteException e)
		{
			//#CM700748
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM700749
			// Response flag :  Error
			ansCode = SendIdMessage.ANS_CODE_ERROR;
			errDetails = SendIdMessage.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (NoPrimaryException e)
		{
			//#CM700750
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			ansCode = SendIdMessage.ANS_CODE_ERROR;
			errDetails = SendIdMessage.ErrorDetails.DB_UNIQUE_KEY_ERROR;
		}
		catch (NotFoundException e)
		{
			//#CM700751
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			ansCode = SendIdMessage.ANS_CODE_ERROR;
			errDetails = SendIdMessage.ErrorDetails.NULL;
		}
		catch (Exception e)
		{
			//#CM700752
			// Other Error
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM700753
			// Response flag :  Error
			ansCode = SendIdMessage.ANS_CODE_ERROR;
			errDetails = SendIdMessage.ErrorDetails.INTERNAL_ERROR;
		}
		
		//#CM700754
		// Make response telegram by using the instance of the read response telegram class. 
		if(sendMessage == null)
		{
			sendMessage = (RFTId5005)PackageManager.getObject("RFTId5005");
			//#CM700755
			// Response telegram making
			//#CM700756
			// STX
			sendMessage.setSTX();
			
			//#CM700757
			// SEQ
			sendMessage.setSEQ(0);
			
			//#CM700758
			// ID
			sendMessage.setID(RFTId5005.ID);
			
			//#CM700759
			// RFT transmission time
			sendMessage.setRftSendDate(rftId0005.getRftSendDate());
			
			//#CM700760
			// SERVER transmission time
			sendMessage.setServSendDate();
			
			//#CM700761
			// RFT machine
			sendMessage.setRftNo(rftNo);
			
			//#CM700762
			// ETX
			sendMessage.setETX() ;
			
			ansCode = RFTId5005.ANS_CODE_ERROR;
			errDetails = RFTId5005.ErrorDetails.CANNOT_GET_RESPONSE_ID;

		}
		//#CM700763
		// Response flag
		sendMessage.setAnsCode(ansCode);

		//#CM700764
		//  Detailed Error
		sendMessage.setErrDetails(errDetails);

		//#CM700765
		// Acquisition of telegram
		sendMessage.getSendMessage(sdt);
	}

	//#CM700766
	// Package methods ----------------------------------------------

	//#CM700767
	// Protected methods --------------------------------------------

	//#CM700768
	// Private methods ---------------------------------------------
}
//#CM700769
//end of class
