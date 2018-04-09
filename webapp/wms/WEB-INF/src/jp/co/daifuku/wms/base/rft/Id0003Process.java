//#CM700625
//$Id: Id0003Process.java,v 1.2 2006/11/14 06:08:59 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM700626
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.WorkerResult;

//#CM700627
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * The class to do work Completed report (ID0003) processing. <BR>
 * Succeed to <CODE>IdProcess</CODE> class, and mount necessary processing. <BR>
 * Acquire the condition from Receiving telegram, renew RFT administrative information, and generate response telegram. <BR>
 * Update process uses the BaseOperate class. <BR>
 * <BR>
 * Work Completed report processing(<CODE>processReceivedId()</CODE> Method)<BR>
 * <BR>
 *   <DIR>
 *   Receive reception telegram as a parameter, and generate response telegram. <BR>
 *   Acquire Work type, Worker code, and RFT machine No from Receiving telegram and renew RFT administrative information (initialization). <BR>
 *   Moreover, renew Work End date of Worker results information. <BR>
 *   <BR>
 *     <DIR>
 *     RFT administrative information update processing<BR>
 *     [Update condition]<BR>
 *       <DIR>
 *       RFT machine No<BR>
 *       </DIR>
 *     [Content of update]<BR>
 *       <DIR>
 *       Worker code   : ""(Initialization)<BR>
 *       Work type       : "00"  (Initialization)<BR>
 *       Last updated date and time   : System date  DATE type<BR>
 *       Last update processing name : "ID0003"<BR>
 *       </DIR>
 *       <BR>
 *     Worker results informationUpdate process<BR>
 *     [Update condition]<BR>
 *       <DIR>
 *       Work date<BR>
 *       Worker code<BR>
 *       RFTNo<BR>
 *       Work flag<BR>
 *       Work Start date(The maximum value)<BR>
 *       </DIR>
 *     [Content of update]<BR>
 *       <DIR>
 *       Work End date : System date <BR>
 *       Work qty     : Add to a present value. <BR>
 *       Work frequency     : Add to a present value. <BR>
 *       </DIR>
 *     </DIR>
 *   </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:08:59 $
 * @author  $Author: suresh $
 */

public class Id0003Process extends IdProcess
{
	//#CM700628
	//Class fields --------------------------------------------------
	//#CM700629
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0003Process";

	//#CM700630
	//Class variables -----------------------------------------------
	//#CM700631
	//Class methods -------------------------------------------------
	//#CM700632
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:08:59 $";
	}

	//#CM700633
	//Constructors --------------------------------------------------
	//#CM700634
	/**
	 * Generate the instance. 
	 */
	public Id0003Process()
	{
		super();
	}

	//#CM700635
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public Id0003Process(Connection conn)
	{
		super();
		wConn = conn;
	}

	//#CM700636
	// Public methods -----------------------------------------------
	//#CM700637
	/**
	 * Process work Completed report. 
	 * @param  rdt  Receiving buffer
	 * @param  sdt  Sending buffer
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM700638
		//For receiving telegram decomposition
		RFTId0003 rftId0003 = null;
		//#CM700639
		//For sending telegram decomposition
		RFTId5003 rftId5003 = null;
		try
		{
			//#CM700640
			// The instance for reception telegram analysis is generated. 
			rftId0003 = (RFTId0003) PackageManager.getObject("RFTId0003");
			rftId0003.setReceiveMessage(rdt);

			//#CM700641
			// The instance for sending telegram making is generated. 
			rftId5003 = (RFTId5003) PackageManager.getObject("RFTId5003");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*003");
			throw e;
		}

		//#CM700642
		//Variable for response flag
		String ansCode = RFTId5003.ANS_CODE_NORMAL;

		//#CM700643
		// Variable which maintains detailed error
		String errDetails = RFTId5003.ErrorDetails.NORMAL;

		//#CM700644
		//Person in charge code Variable for Initialization
		String formatWorkerCode = "";

		//#CM700645
		//Work type Variable for Initialization
		String formatWorkType = WorkerResult.JOB_TYPE_UNSTART;

		//#CM700646
		//RFT No. acquisition from Receiving telegram
		String rftNo = rftId0003.getRftNo();

		try
		{
			//#CM700647
			// BaseOperate instance making
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);

			//#CM700648
			// RFT administrative information update
			baseOperate.alterRft(rftNo, formatWorkerCode, formatWorkType, "ID0003");

			//#CM700649
			// Worker results update(Work End date only)
			WorkerResult workerResult = new WorkerResult();
			//#CM700650
			// Set Update condition
			//#CM700651
			// Work date
			workerResult.setWorkDate(baseOperate.getWorkingDate());
			//#CM700652
			// Worker code
			workerResult.setWorkerCode(rftId0003.getWorkerCode());
			//#CM700653
			// RFT machine No
			workerResult.setTerminalNo(rftNo);
			//#CM700654
			// Work flag
			workerResult.setJobType(rftId0003.getWorkType());
			//#CM700655
			// Because the Work qty frequency is not updated in update value set Completed report, 0 is set. 
			workerResult.setWorkQty(0);
			workerResult.setWorkCnt(0);

			//#CM700656
			// Update process
			try
			{
				baseOperate.alterWorkerResult(workerResult);
			}
			catch (NotFoundException e)
			{
				//#CM700657
				// When there are no object worker results in Update process, it is not done as Error, and assumes NormalCompleted. 
				//#CM700658
				// There is a possibility of receiving only the Completed report after it updates it on the day (Work date update). 
				//#CM700659
				// (Because power supply OFF can do even if the RFT screen is in any state)
				//#CM700660
				// In that case, the worker results of Work date after updating have not been made yet. 
			}

			//#CM700661
			// Comment
			wConn.commit();
		}
		catch (IllegalAccessException e)
		{
			//#CM700662
			// It fails in the instance generation. 
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "BaseOperate");
			//#CM700663
			// Rollback (Put for attention though this rollback might be originally unnecessary because it is not thought, except when the generation of the instance of BaseOperate fails. )
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM700664
			//Response flag :  Error
			ansCode = RFTId5003.ANS_CODE_ERROR;
			errDetails = RFTId5003.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (ReadWriteException e)
		{
			//#CM700665
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
			ansCode = RFTId5003.ANS_CODE_ERROR;
			errDetails = RFTId5003.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (NotFoundException e)
		{
			//#CM700666
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
			ansCode = RFTId5003.ANS_CODE_ERROR;
			errDetails = RFTId5003.ErrorDetails.NULL;
		}
		catch (InvalidDefineException e)
		{
			//#CM700667
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

			//#CM700668
			// Response flag :  Error
			ansCode = RFTId5003.ANS_CODE_ERROR;
			errDetails = RFTId5003.ErrorDetails.PARAMETER_ERROR;
		}
		catch (SQLException e)
		{
			//#CM700669
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
			ansCode = RFTId5003.ANS_CODE_ERROR;
			errDetails = RFTId5003.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (Exception e)
		{
			//#CM700670
			// Other Error
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM700671
			// Rollback
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM700672
			//Response flag :  Error
			ansCode = RFTId5003.ANS_CODE_ERROR;
			errDetails = RFTId5003.ErrorDetails.INTERNAL_ERROR;
		}

		//#CM700673
		// Make Response telegram by using the RFTid5003 instance. 
		//#CM700674
		// STX
		rftId5003.setSTX();
		//#CM700675
		// SEQ
		rftId5003.setSEQ(0);
		//#CM700676
		// ID
		rftId5003.setID(RFTId5003.ID);
		//#CM700677
		// Handy transmission time
		rftId5003.setRftSendDate(rftId0003.getRftSendDate());
		//#CM700678
		// Server transmission time
		rftId5003.setServSendDate();
		//#CM700679
		// RFT machine
		rftId5003.setRftNo(rftNo);
		//#CM700680
		// Person in charge code
		rftId5003.setWorkerCode(formatWorkerCode);
		//#CM700681
		// Work type
		rftId5003.setWorkType(formatWorkType);
		//#CM700682
		// Response flag
		rftId5003.setAnsCode(ansCode);
		//#CM700683
		//  Detailed Error
		rftId5003.setErrDetails(errDetails);
		//#CM700684
		// ETX
		rftId5003.setETX();

		//#CM700685
		//Acquisition of telegram
		rftId5003.getSendMessage(sdt);
	}

	//#CM700686
	// Package methods ----------------------------------------------

	//#CM700687
	// Protected methods --------------------------------------------

	//#CM700688
	// Private methods ----------------------------------------------
}
//#CM700689
//end of class
