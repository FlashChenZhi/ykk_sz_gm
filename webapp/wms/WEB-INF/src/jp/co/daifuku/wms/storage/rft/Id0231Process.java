// $Id: Id0231Process.java,v 1.2 2006/12/07 09:00:12 suresh Exp $

package jp.co.daifuku.wms.storage.rft;

//#CM575373
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM575374
/**
 * Designer : K.Shimizu <BR>
 * Maker : K.Shimizu <BR>
 * <BR>
 * Execute RFT storage result data (ID0231) process<BR>
 * Update storage data that corresponds to Work info using Id0231Operate class,
 * and create Response id to be sent to RFT<BR>
 * <BR>
 * Storage result data process(<CODE>processReceivedId</CODE> method)<BR>
 * <BR>
 * <DIR>
 *   Fetch the required info from received data<BR>
 *   Using ID0231Operate class function, call shortage, cancel or commit process and fetch response flag<BR>
 *   If Response flag 0:normal, update the RFT Work info telegraph data with null<BR>
 *   <BR>
 *   [Update conditions]
 *   <UL><LI>RFTNo</LI></UL>
 *   [Update contents]
 *   <UL><LI>Response id:NULL</LI>
 *       <LI>Last update date/time:system date/time</LI>
 *       <LI>Last update Process name:ID0231</LI>
 *   </UL>
 *  Create send telegraph data and set it to send buffer<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:12 $
 * @author  $Author: suresh $
 */
public class Id0231Process extends IdProcess
{

	//#CM575375
	//Class fields --------------------------------------------------

	//#CM575376
	//Class variables -----------------------------------------------
	//#CM575377
	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "Id0231Process";
	
	//#CM575378
	/**
	 * Process name
	 */
	private static final String PROCESS_NAME = "ID0231";

	//#CM575379
	//Class methods -------------------------------------------------
	//#CM575380
	/**
	 * Return the version of this class
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/12/07 09:00:12 $");
	}

	//#CM575381
	//Constructors --------------------------------------------------
	//#CM575382
	/**
	 * Constructor
	 */
	public Id0231Process()
	{
		super() ;
	}

	//#CM575383
	/**
	 * Pass Db connection info to the constructor
	 * @param conn DBConnection info
	 */
	public Id0231Process( Connection conn )
	{
		super() ;
		wConn = conn ;
	}

	//#CM575384
	//Public methods ------------------------------------------------
	//#CM575385
	/**
	 * Execute Storage result data(ID0231) process<BR>
	 * Fetch the telegraph data as byte array and create (ID5231) byte string<BR>
	 * Call cancel, shortage or commit process based on completion flag and create result text<BR>
	 * @param  rdt  Receive buffer
	 * @param  sdt  Send buffer
	 * @throws Exception Throw all exceptions
	 */
	public void processReceivedId( byte[] rdt, byte[] sdt ) throws Exception
	{

		//#CM575386
		// Create instance to receive telegraph data
		RFTId0231 rftId0231 = null;

		//#CM575387
		// Create instance to send telegraph data
		RFTId5231 rftId5231 = null;

		try
		{
			//#CM575388
			// Create instance to receive telegraph data
			rftId0231 = (RFTId0231)PackageManager.getObject("RFTId0231");
			rftId0231.setReceiveMessage(rdt);

			//#CM575389
			// Create instance to send telegraph data
			rftId5231 = (RFTId5231)PackageManager.getObject("RFTId5231");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*231", e.getMessage());
			throw e;
		}

		//#CM575390
		// variable to store Response flag
		String ansCode = RFTId5231.ANS_CODE_NORMAL ;

		//#CM575391
		// Fetch RFT no. using RFT0231
		String rftNo = rftId0231.getRftNo() ;

		//#CM575392
		// Fetch worker code using RFT0231
		String workerCode = rftId0231.getWorkerCode() ;

		//#CM575393
		// Fetch product identification code using RFT0231
		String itemId = rftId0231.getItemId() ;
		
		//#CM575394
		//Fetch storage plan qty using RFT0231
		String storagePlanQty = rftId0231.getStragePlanQty();

		//#CM575395
		// Fetch storage result using RFT0231
		String storageCompletionCnt = rftId0231.getStorageCompletionQty() ;
		
		//#CM575396
		// Fetch expiry date using RFT0231
		String useByDate = rftId0231.getUseByDate() ;

		//#CM575397
		// Fetch stroage completion location using RFT0231
		String storageCompletionLocationNo = rftId0231.getStrorageCompletionLocationNo() ;

		//#CM575398
		//Fetch completion flag using RFT0231
		String completionFlag = rftId0231.getCompletionFlag() ;

		//#CM575399
		// Variable for error details
		String errDetails = RFTId5231.ErrorDetails.NORMAL;

		Id0231Operate id0231Operate = null;
		
		try
		{

			if(!completionFlag.equals(RFTId0231.COMPLETION_FLAG_CANCEL) && DisplayText.isPatternMatching(useByDate))
			{
				throw new InvalidDefineException("USE_BY_DATE[" + useByDate +"]");
			}
			
			//#CM575400
			// Fetch work time using RFT0231
			int workTime = rftId0231.getWorkSeconds();

			//#CM575401
			// Fetch miss count using RFT0231
			int missScanCnt = rftId0231.getMissCount();
			
			//#CM575402
			// Create Id0231Operate instance
			id0231Operate = (Id0231Operate)PackageManager.getObject("Id0231Operate");
			id0231Operate.setConnection(wConn);

			ansCode = id0231Operate.doComplete(
							itemId,
							workerCode, 
							rftNo, 
							useByDate,
							Integer.parseInt(storagePlanQty),
							Integer.parseInt(storageCompletionCnt), 
							storageCompletionLocationNo, 
							workTime,
							missScanCnt,
							completionFlag);
			
			if(ansCode.equals(RFTId5231.ANS_CODE_NORMAL))
			{
			    //#CM575403
			    // If the response flag is normal, delete in process data
			    RFTId5231.deleteWorkingData(rftNo, PROCESS_NAME, wConn);
			    wConn.commit();	
			}	
		}
		catch (IllegalAccessException e)
		{
			//#CM575404
			// 6006003=Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "BaseOperate");
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			//#CM575405
			// Response flag : Error
			ansCode = RFTId5231.ANS_CODE_ERROR;
			errDetails = RFTId5230.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (ReadWriteException e)
		{
			//#CM575406
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
			//#CM575407
			// Response flag : Error
			ansCode = RFTId5231.ANS_CODE_ERROR;
			errDetails = RFTId5231.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (InvalidDefineException e)
		{
			//#CM575408
			// 6026022=Blank or prohibited character is included in the specified value. {0}
			RftLogMessage.print(6026022, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());

			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM575409
			// Response flag : Error
			ansCode = RFTId5231.ANS_CODE_ERROR;
			errDetails = RFTId5231.ErrorDetails.PARAMETER_ERROR;

		}
		catch (SQLException e)
		{
			//#CM575410
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
			//#CM575411
			// Response flag : Error
			ansCode = RFTId5231.ANS_CODE_ERROR;
			errDetails = RFTId5231.ErrorDetails.DB_ACCESS_ERROR;

		}
		catch(NumberFormatException e)
		{
			//#CM575412
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			//#CM575413
			// Response flag : Error
			ansCode = RFTId5230.ANS_CODE_ERROR;
			errDetails = RFTId5230.ErrorDetails.PARAMETER_ERROR;
			
		}
		//#CM575414
		// Other error occurred
		catch (Exception e)
		{
			//#CM575415
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			//#CM575416
			// Response flag : Error
			ansCode = RFTId5230.ANS_CODE_ERROR;
			errDetails = RFTId5230.ErrorDetails.INTERNAL_ERROR;
		}

		//#CM575417
		//Create send data using RFTId5231 instance
		//#CM575418
		//STX
		rftId5231.setSTX() ;

		//#CM575419
		//SEQ
		rftId5231.setSEQ(0) ;

		//#CM575420
		//ID
		rftId5231.setID(RFTId5231.ID) ;

		//#CM575421
		//Handy send time
		rftId5231.setRftSendDate( rftId0231.getRftSendDate() ) ;

		//#CM575422
		//Server send time
		rftId5231.setServSendDate() ;

		//#CM575423
		//RFT no.
		rftId5231.setRftNo( rftId0231.getRftNo() ) ;

		//#CM575424
		//Worker code
		rftId5231.setWorkerCode( workerCode ) ;

		//#CM575425
		//Response flag
		rftId5231.setAnsCode( ansCode ) ;

		//#CM575426
		// Error details
		rftId5231.setErrDetails(errDetails);
		if (ansCode.equals(RFTId5231.ANS_CODE_ERROR)
				&& errDetails.equals(RFTId5231.ErrorDetails.NORMAL))
		{
			errDetails = id0231Operate.getErrorDetails();
		}
		rftId5231.setErrDetails(errDetails);
		
		//#CM575427
		//ETX
		rftId5231.setETX() ;
		
		//#CM575428
		//Acquire telegraph data
		rftId5231.getSendMessage( sdt ) ;
		
	}
	
	//#CM575429
	//Package methods -----------------------------------------------

	//#CM575430
	//Protected methods ---------------------------------------------
	
	//#CM575431
	//Private methods -----------------------------------------------
	
}
//#CM575432
//end of class

