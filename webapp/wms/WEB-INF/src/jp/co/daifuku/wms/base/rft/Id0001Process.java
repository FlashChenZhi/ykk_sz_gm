//#CM700479
//$Id: Id0001Process.java,v 1.2 2006/11/14 06:08:58 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM700480
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.entity.Rft;

//#CM700481
/**
 * Designer : E.Takeda <BR>
 * Maker :   <BR>
 * <BR>
 * The class to do system report (ID0001) processing. <BR>
 * Succeed to <CODE>IdProcess</CODE> class, and mount necessary processing. <BR>
 * Acquire the condition from reception telegram, make the renewal of RFT administrative information and the worker results, and generate response telegram. <BR>
 * <BR>
 * System report processing(<CODE>processReceivedId()</CODE> Method)<BR>
 * <DIR>
 *   Receive reception telegram as a parameter, and generate response telegram. <BR>
 *   Acquire Worker code and RFT machine No from reception telegram, and confirm whether a pertinent worker is working in the other end,<BR>
 *   Make the renewal of RFT administrative information and the worker results subject to acquisition Data while not working. <BR>
 *   Generate response telegram of It is working in the other end when a pertinent worker is It is working in the other end. <BR>
 * </DIR>
 * <BR>
 * Acquire Report flag from reception telegram, and do the following processing in each flag. 
 *<DIR>
 *  [Report flag  : 0 Start]
 * <BR>
 *  Renew RFT administrative information when the worker results of correspondence Work date, the worker, and title machine No. do not exist. <BR>
 * <BR>
 * <DIR>
 * [Update condition]<BR>
 * RFT machine No.<BR>
 * <BR>
 * [Content of update]<BR>
 *  Status flag : 1(Start)<BR>
 *  Terminal flag :Acquisition from parameter<BR>
 *  Terminal IP Address : Acquisition from parameter<BR>
 *  Wireless Status flag : 0	       <BR>
 *  Last updated date and time : System date <BR>
 *  Last update processing name : ID0001     <BR>
 * <BR>
 * </DIR>
 *  [Report flag  : 1(Completed)]
 * <BR>
 * Renew RFT administrative information. <BR>
 * <BR>
 * <DIR>
 * [Update condition]<BR>
 * RFT machine No.<BR>
 * <BR>
 * [Content of update]<BR>
 *  Status flag : 0(Pause)<BR>
 *  Wireless Status flag : 0<BR>
 *  Last updated date and time : System date <BR>
 *  Last update processing name : ID0001     <BR>
 * <BR>
 * </DIR>
 *  [Report flag  : 3(Rest)]
 * <BR>
 * Do RFT administrative information and Renewal of Worker results information. <BR>
 * <BR>
 * <DIR>
 * [Update condition : RFT administrative information]<BR>
 * RFT machine No.<BR>
 * <BR>
 * [Content of update : RFT administrative information]<BR>
 *  Rest flag : 1(Resting)<BR>
 *  Wireless Status flag : 0<BR>
 *  Rest Start time : System date <BR>
 *  Last updated date and time : System date <BR>
 *  Last update processing name : ID0001     <BR>
 * <BR>
 * [Update condition : Worker results information]<BR>
 * Work date<BR>
 * Worker code<BR>
 * RFT machine No.<BR>
 * Work flag<BR>
 * <BR>
 * [Content of update : Worker results information]<BR>
 * The final Completed date:System date<BR>
 * </DIR>
 *  <BR>
 *  [Report flag  : 4(Restart)]
 * <BR>
 * Make Worker results information with the renewal of RFT administrative information. <BR>
 * <BR>
 * <DIR>
 * [Update condition:RFT administrative information]<BR>
 * RFT machine No.<BR>
 * <BR>
 * [Content of update:RFT administrative information]<BR>
 *  Rest flag : 0<BR>
 *  Wireless Status flag : 0<BR>
 *  Rest Start time : null <BR>
 *  Last updated date and time : System date <BR>
 *  Last update processing name : ID0001     <BR>
 * <BR>
 * [Making condition : Worker results information]<BR>
 * Work date<BR>
 * Worker code<BR>
 * RFTTerminalNo.<BR>
 * Work flag<BR>
 * <BR>
 * [Content of making : Worker results information]<BR>
 * Work date : System definition<BR>
 * Work Start date : System date<BR>
 * Work End date : System date<BR>
 * Worker code : RFTWorker code<BR>
 * Worker name : Worker name<BR>
 * RFTTerminalNo. : RFTNo.<BR>
 * Work flag : 03 : Picking 	<BR>
 * Work qty : 0<BR>
 * Work frequency : 0<BR>
 * <BR>
 * </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:08:58 $
 * @author  $Author: suresh $
 */

public class Id0001Process extends IdProcess
{
	//#CM700482
	//Class fields --------------------------------------------------
	//#CM700483
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0001Process";

	//#CM700484
	//Class variables -----------------------------------------------
	//#CM700485
	//Class methods -------------------------------------------------
	//#CM700486
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:08:58 $";
	}

	//#CM700487
	//Constructors --------------------------------------------------
	//#CM700488
	/**
	 * Generate the instance. 
	 */
	public Id0001Process()
	{
		super();
	}

	//#CM700489
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public Id0001Process(Connection conn)
	{
		super();
		wConn = conn;
	}

	//#CM700490
	// Public methods -----------------------------------------------
	//#CM700491
	/**
	 * Process the work beginning report. 
	 * @param  rdt  Receiving buffer
	 * @param  sdt  Sending buffer
	 * @throws Exception It reports on all exceptions.  
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM700492
		// For receiving telegram decomposition
		RFTId0001 rftId0001 = null;
		//#CM700493
		// For sending telegram decomposition
		RFTId5001 rftId5001 = null;
		try
		{
			//#CM700494
			// The instance for reception telegram analysis is generated. 
			rftId0001 = (RFTId0001) PackageManager.getObject("RFTId0001");
			rftId0001.setReceiveMessage(rdt);

			//#CM700495
			// The instance for sending telegram making is generated. 
			rftId5001 = (RFTId5001) PackageManager.getObject("RFTId5001");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*001");
			throw e;
		}
		

		//#CM700496
		// Variable for server date
		String serverDate = "";

		//#CM700497
		// Variable for response flag
		String ansCode = "";

		//#CM700498
		// Variable which maintains detailed error
		String errDetails = RFTId5002.ErrorDetails.NORMAL;

		//#CM700499
		// RFT machine  No. acquisition from receiving telegram
		String rftNo = rftId0001.getRftNo();

		//#CM700500
		// Report flag acquisition from Receiving telegram
		String reportFlag = rftId0001.getReportFlag();
		
		//#CM700501
		// Terminal flag acquisition from Receiving telegram
		String terminalType = rftId0001.getTerminalType() ;
		
		//#CM700502
		// Terminal IP Address is acquired from Receiving telegram. 
		String ipAddress = rftId0001.getIpAddress() ;
		
		try
		{
			//#CM700503
			// Check registered Terminal. 
			RftSearchKey skey = new RftSearchKey();
			skey.setRftNo(rftNo);
			RftHandler rftHandler = new RftHandler(wConn);
			Rft rft = (Rft) rftHandler.findPrimary(skey);
			if (rft != null)
			{
				//#CM700504
				// Acquisition of server date
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				serverDate=df.format(new Date());
				//#CM700505
				// Response flag
				ansCode = RFTId5001.ANS_CODE_NORMAL;
				Id0001Operate id0001Operate = (Id0001Operate) PackageManager.getObject("Id0001Operate");
				id0001Operate.setConnection(wConn);
				errDetails = id0001Operate.alterRft(rftNo, reportFlag, terminalType, ipAddress);
				//#CM700506
				// Comment
				wConn.commit();
			}
			else
			{
				wConn.rollback();
				//#CM700507
				// Response flag :  Error , Detailed Error:The Terminal title No. is illegal. 
				ansCode = RFTId5001.ANS_CODE_ERROR;
				errDetails = RFTId5001.ErrorDetails.TERMINAL_UNREGISTRATION;
			}
		}
		catch (NotFoundException e)
		{
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM700508
			// Response flag : Normal
			ansCode = RFTId5001.ANS_CODE_ERROR;
			errDetails = RFTId5001.ErrorDetails.NULL;
		}
		catch (IllegalAccessException e)
		{
			//#CM700509
			// It fails in the instance generation. 
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "", e.getMessage());
			//#CM700510
			// Rollback (Put for attention though this rollback might be originally unnecessary because it is not thought, except when the generation of the instance of BaseOperate fails. )
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM700511
			//Response flag :  Error
			ansCode = RFTId5001.ANS_CODE_ERROR;
			errDetails = RFTId5001.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (SQLException e)
		{
			//#CM700512
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
			//#CM700513
			// Response flag :  Error
			ansCode = RFTId5001.ANS_CODE_ERROR;
			errDetails = RFTId5001.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (ReadWriteException e)
		{
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM700514
			// Response flag :  Error
			ansCode = RFTId5001.ANS_CODE_ERROR;
			errDetails = RFTId5001.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (InvalidDefineException e)
		{
			//#CM700515
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
			//#CM700516
			//Response flag :  Error
			ansCode = RFTId5001.ANS_CODE_ERROR;
			errDetails = RFTId5001.ErrorDetails.ERROR_NON_REST_RESTART;
		}
		catch (DataExistsException e)
		{
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM700517
			// Response flag :  Error
			ansCode = RFTId5001.ANS_CODE_ERROR;
			errDetails = RFTId5001.ErrorDetails.DB_UNIQUE_KEY_ERROR;
		}
		catch (InvalidStatusException e)
		{
			//#CM700518
			// Rest report reception at Rest
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM700519
			// Response flag :  Error
			ansCode = RFTId5001.ANS_CODE_ERROR;
			errDetails = RFTId5001.ErrorDetails.ERROR_NON_REST_RESTART;
		}
		catch (NoPrimaryException e)
		{
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM700520
			// Response flag :  Error
			ansCode = RFTId5001.ANS_CODE_ERROR;
			errDetails = RFTId5001.ErrorDetails.DB_UNIQUE_KEY_ERROR;
		}
		catch (Exception e)
		{
			//#CM700521
			// Other Error
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM700522
			// Rollback
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM700523
			//Response flag :  Error
			ansCode = RFTId5001.ANS_CODE_ERROR;
			errDetails = RFTId5001.ErrorDetails.INTERNAL_ERROR;
		}
		
		
		
		//#CM700524
		// Make response telegram by using the RFTId1001 instance. 

		//#CM700525
		// STX
		rftId5001.setSTX();

		//#CM700526
		// SEQ
		rftId5001.setSEQ(0);

		//#CM700527
		// ID
		rftId5001.setID(RFTId5001.ID);

		//#CM700528
		// Handy transmission time
		rftId5001.setRftSendDate(rftId0001.getRftSendDate());

		//#CM700529
		// Server transmission time
		rftId5001.setServSendDate();

		//#CM700530
		// RFT machine
		rftId5001.setRftNo(rftNo);

		//#CM700531
		// Server date and time
		rftId5001.setServerDate(serverDate);

		//#CM700532
		// Response flag
		rftId5001.setAnsCode(ansCode);

		//#CM700533
		//  Detailed Error
		rftId5001.setErrDetails(errDetails);

		//#CM700534
		// ETX
		rftId5001.setETX();

		//#CM700535
		// Acquisition of telegram
		rftId5001.getSendMessage(sdt);
	}

	//#CM700536
	// Package methods ----------------------------------------------

	//#CM700537
	// Protected methods --------------------------------------------

	//#CM700538
	// Private methods ---------------------------------------------
}
//#CM700539
//end of class
