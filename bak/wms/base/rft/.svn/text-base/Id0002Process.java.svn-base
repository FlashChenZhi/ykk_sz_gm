// $Id: Id0002Process.java,v 1.2 2006/11/14 06:08:58 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM700540
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
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
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;

//#CM700541
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * The class to do work beginning report (ID0002) processing. <BR>
 * Succeed to <CODE>IdProcess</CODE> class, and mount necessary processing. <BR>
 * Acquire the condition from reception telegram, make the renewal of RFT administrative information and the worker results, and generate response telegram. <BR>
 * Processing uses Id0002Operate and the BaseOperate class. <BR>
 * <BR>
 * Work beginning report processing (<CODE>processReceivedId()</CODE> Method)<BR>
 * <DIR>
 *   Receive reception telegram as a parameter, and generate response telegram. <BR>
 *   With RFT machine No acquired from Receiving telegram,
 *   Retrieve RFTWork information, and check whether response telegramData with same Terminal exists. <BR>
 *   <BR>
 *   <DIR>RFT administrative informationRetrieval processing<BR>
 *   <BR>
 *     [Search condition]<BR>
 *     <DIR>RFT machine No    : Acquisition from Receiving telegram<BR>
 *     </DIR>
 *     [Content of acquisition]<BR>
 *     <DIR>telegram<BR>
 *     </DIR>
 *   </DIR>
 *   <BR>  
 *      
 *   Acquire Work flag from the identification number of the file name when there is a value in the telegram item. <BR>
 *   Set it in Work flag of response telegram. <BR>
 *   Moreover, return response flag and return response telegram by being the Data file (7) while working. <BR>
 *   Confirm whether Worker code and RFT machine No are used and a pertinent worker is is working in the other end.<BR>
 *   Make the renewal of RFT administrative information and the worker results subject to acquisition Data while not working. <BR>
 *   Generate response telegram of It is working in the other end when a pertinent worker is It is working in the other end. <BR>
 * <BR>
 * <DIR>
 *   RFT administrative information update processing<BR>
 * <BR>
 *   [Update condition]<BR>
 * <DIR>
 *     RFT machine No<BR>
 * </DIR>
 *   [Content of update]<BR>
 * <DIR>
 *     Worker code:   Acquisition from Receiving telegram<BR>
 *     Work type:       Acquisition from Receiving telegram<BR>
 *     Last updated date and time:   System date  DATE type<BR>
 *     Last update processing name: "ID0002"<BR>
 * </DIR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:08:58 $
 * @author  $Author: suresh $
 */

public class Id0002Process extends IdProcess
{
	//#CM700542
	//Class fields --------------------------------------------------
	//#CM700543
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0002Process";
	
	//#CM700544
	//Class variables -----------------------------------------------
	//#CM700545
	//Class methods -------------------------------------------------
	//#CM700546
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:08:58 $";
	}

	//#CM700547
	//Constructors --------------------------------------------------
	//#CM700548
	/**
	 * Generate the instance. 
	 */
	public Id0002Process()
	{
		super();
	}

	//#CM700549
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public Id0002Process(Connection conn)
	{
		super();
		wConn = conn;
	}

	//#CM700550
	// Public methods -----------------------------------------------
	//#CM700551
	/**
	 * Process the work beginning report. 
	 * @param  rdt  Receiving buffer
	 * @param  sdt  Sending buffer
	 * @throws Exception It reports on all exceptions.  
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM700552
		// For receiving telegram decomposition
		RFTId0002 rftId0002 = null;
		//#CM700553
		// For sending telegram decomposition
		RFTId5002 rftId5002 = null;
		try
		{
			//#CM700554
			// The instance for reception telegram analysis is generated. 
			rftId0002 = (RFTId0002) PackageManager.getObject("RFTId0002");
			rftId0002.setReceiveMessage(rdt);

			//#CM700555
			// The instance for sending telegram making is generated. 
			rftId5002 = (RFTId5002) PackageManager.getObject("RFTId5002");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*002");
			throw e;
		}

		//#CM700556
		// Variable for response flag
		String ansCode = RFTId5002.ANS_CODE_NORMAL;

		//#CM700557
		// Variable which maintains detailed error
		String errDetails = RFTId5002.ErrorDetails.NORMAL;

		//#CM700558
		// Variable for system Work date
		String planDate = "";
		
		//#CM700559
		// Worker name
		String workerName = "";
		
		//#CM700560
		// Variable for ID when being working
		String workingId = "";

		//#CM700561
		// RFT machine  No. acquisition from receiving telegram
		String rftNo = rftId0002.getRftNo();

		//#CM700562
		// Person in charge code acquisition from Receiving telegram
		String workerCode = rftId0002.getWorkerCode();

		//#CM700563
		// Work type acquisition from Receiving telegram
		String workType = rftId0002.getWorkType();
		
		//#CM700564
		// Work type details acquisition from Receiving telegram
		String workDetails = rftId0002.getWorkDetails();

		//#CM700565
		//Variable for server date
		String serverDate = "";

		try
		{
			//#CM700566
			// Check registered Terminal. 
			RftSearchKey skey = new RftSearchKey();
			skey.setRftNo(rftNo);
			RftHandler rftHandler = new RftHandler(wConn);

			if (rftHandler.findPrimary(skey) != null)
			{
				//#CM700567
				// Acquisition of server date
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				serverDate = df.format(new Date());
	
				//#CM700568
				// The instance of BaseOperate is generated. 
				BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
				baseOperate.setConnection(wConn);
	
				try
				{
					if(DisplayText.isPatternMatching(workerCode))
					{
						throw new NotFoundException();
					}
					//#CM700569
					// The Worker name name is acquired from Id0004Operate. 
					workerName = baseOperate.getWorkerName(workerCode);
				}
				catch (NotFoundException e)
				{
					ansCode = RFTId5002.ANS_CODE_NULL;
				}

				//#CM700570
				// Only when it is possible to acquire it, the Worker name processes it to Normal as follows. 
				if (ansCode.equals(RFTId5002.ANS_CODE_NORMAL))
				{
					//#CM700571
					// Check whether there is response telegram file. 
				    //#CM700572
				    // Acquire the name of the folder with the file. 
					workingId = IdMessage.getWorkingDataId(rftNo, wConn);
					if (! workingId.equals(""))
					{
					    //#CM700573
					    // Return Working Data when the file and response telegram working file exist.
					    ansCode = RFTId5002.ANS_CODE_WORKING_DATAFILE_EXISTS;

					}
									
					//#CM700574
					// Work is confirmed from worker information and RFT administrative information. 
					if (baseOperate.checkRftForUpdate(workerCode, rftNo))
					{
						//#CM700575
						// Whether a pertinent worker is working in the other end is confirmed. 
						if (baseOperate.checkWorker(workerCode, rftNo))
						{
							//#CM700576
							// Acquire Plan date which can work near system Work date. 
							planDate = baseOperate.getPlanDate(workType, workDetails, rftNo, workerCode);
	
							if(ansCode.equals(RFTId5002.ANS_CODE_NORMAL))
							{
								//#CM700577
								// RFT administrative information update
								baseOperate.alterRft(rftNo, workerCode, workType, "ID0002");
			
								//#CM700578
								// New making of worker results
								baseOperate.createWorkerResult(workType, workerCode, rftNo);
							}
							//#CM700579
							// Comment
							wConn.commit();
						}
						else
						{
							//#CM700580
							// Rollback
							wConn.rollback();
	
							//#CM700581
							// Response flag : It is working in the other end.
							ansCode = RFTId5002.ANS_CODE_WORKING;
							errDetails = RFTId5002.ErrorDetails.WORKING_OTHER_TERMINAL;
						}
					}
					else
					{
						//#CM700582
						// Rollback
						wConn.rollback();
	
						//#CM700583
						// Response flag : It is working in the other end.				
						ansCode = RFTId5002.ANS_CODE_WORKING;
						errDetails = RFTId5002.ErrorDetails.WORKING_OTHER_WORKER;
					}
				}
			}
			else
			{
				wConn.rollback();
				//#CM700584
				// Response flag :  Error , Detailed Error:The Terminal title No. is illegal. 
				ansCode = RFTId5001.ANS_CODE_ERROR;
				errDetails = RFTId5001.ErrorDetails.TERMINAL_UNREGISTRATION;
			}
		}
		catch (IllegalAccessException e)
		{
			//#CM700585
			// It fails in the instance generation. 
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "BaseOperate");
			//#CM700586
			// Rollback(Put for attention though this rollback might be originally unnecessary because it is not thought, except when the generation of the instance of BaseOperate fails. )
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM700587
			// Response flag :  Error
			ansCode = RFTId5002.ANS_CODE_ERROR;
			errDetails = RFTId5002.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (ReadWriteException e)
		{
			//#CM700588
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
			ansCode = RFTId5002.ANS_CODE_ERROR;
			errDetails = RFTId5002.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (NotFoundException e)
		{
			//#CM700589
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

			//#CM700590
			// Response flag :  Error
			ansCode = RFTId5002.ANS_CODE_ERROR;
			errDetails = RFTId5002.ErrorDetails.NULL;
		}
		catch (NoPrimaryException e)
		{
			//#CM700591
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

			//#CM700592
			// Response flag :  Error
			ansCode = RFTId5002.ANS_CODE_ERROR;
			errDetails = RFTId5002.ErrorDetails.DB_UNIQUE_KEY_ERROR;
		}
		catch (InvalidStatusException e)
		{
			//#CM700593
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
			//#CM700594
			// Response flag :  Error
			ansCode = RFTId5002.ANS_CODE_ERROR;
			errDetails = RFTId5002.ErrorDetails.DB_INVALID_VALUE;
		}
		catch (InvalidDefineException e)
		{
			//#CM700595
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

			//#CM700596
			// Response flag :  Error
			ansCode = RFTId5002.ANS_CODE_ERROR;
			errDetails = RFTId5002.ErrorDetails.PARAMETER_ERROR;
		}
		catch (DataExistsException e)
		{
			//#CM700597
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

			//#CM700598
			// Response flag :  Error
			ansCode = RFTId5002.ANS_CODE_ERROR;
			errDetails = RFTId5002.ErrorDetails.DB_UNIQUE_KEY_ERROR;
		}
		catch (SQLException e)
		{
			//#CM700599
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
			ansCode = RFTId5002.ANS_CODE_ERROR;
			errDetails = RFTId5002.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (Exception e)
		{
			//#CM700600
			// Other Error
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM700601
			// Rollback
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM700602
			// Response flag :  Error
			ansCode = RFTId5002.ANS_CODE_ERROR;
			errDetails = RFTId5002.ErrorDetails.INTERNAL_ERROR;
		}

		//#CM700603
		// Make response telegram by using the RFTId5002 instance. 

		//#CM700604
		// STX
		rftId5002.setSTX();

		//#CM700605
		// SEQ
		rftId5002.setSEQ(0);

		//#CM700606
		// ID
		rftId5002.setID(RFTId5002.ID);

		//#CM700607
		// Handy transmission time
		rftId5002.setRftSendDate(rftId0002.getRftSendDate());

		//#CM700608
		// Server transmission time
		rftId5002.setServSendDate();

		//#CM700609
		// RFT machine
		rftId5002.setRftNo(rftNo);

		//#CM700610
		// Person in charge code
		rftId5002.setWorkerCode(workerCode);

		//#CM700611
		// Work type
		rftId5002.setWorkType(workType);

		//#CM700612
		// Detail Work type
		rftId5002.setWorkDetails(workDetails);

		//#CM700613
		// System Work date
		rftId5002.setPlanDate(planDate);
		
		//#CM700614
		// Worker name
		rftId5002.setWorkerName(workerName);
		
		//#CM700615
		// Server date and time
		rftId5002.setServerDate(serverDate);

		//#CM700616
		// Working ID
		rftId5002.setWorkingId(workingId);

		//#CM700617
		// Response flag
		rftId5002.setAnsCode(ansCode);

		//#CM700618
		//  Detailed Error
		rftId5002.setErrDetails(errDetails);

		//#CM700619
		// ETX
		rftId5002.setETX();

		//#CM700620
		// Acquisition of telegram
		rftId5002.getSendMessage(sdt);
	}

	//#CM700621
	// Package methods ----------------------------------------------

	//#CM700622
	// Protected methods --------------------------------------------

	//#CM700623
	// Private methods ---------------------------------------------
}
//#CM700624
//end of class
