// $Id: Id0006Process.java,v 1.2 2006/11/14 06:09:00 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM700770
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;

//#CM700771
/**
 * Designer : E.Takeda <BR>
 * Maker :  E.Takeda <BR>
 * <BR>
 * The class to do work Data report (ID0006) processing. <BR>
 * Succeed to <CODE>IdProcess</CODE> class, and mount necessary processing. <BR>
 * Update correspondence Data of Working data preservation information with Working data which has been sent. 
 * Transmit Response telegram. 
 * <BR>
 * Work Data report processing(<CODE>processReceivedId()</CODE> Method)<BR>
 * <DIR>
 *   Acquire Working data from Receiving telegram. <BR>
 *   Retrieve Working data preservation information, and update correspondence Data with Content Data of telegram. <BR>
 *   <DIR>Working data preservation information Update process<BR>
 *   <BR>
 *   [Update condition]
 *   <UL><LI>RFT machine No  : Acquisition from parameter</LI>
 *       <LI>File Name : Acquisition from parameter(Remove when you contain the passing part) </LI>
 *       <LI>Row No       : Acquisition from parameter</LI>
 *   </UL>
 *   [Content of update]
 *   <UL><LI>Data : Acquisition from parameter</LI>
 *   </UL>
 *   </DIR> 
 *      
 *   When Error is generated while updating it, the following values are set in Error and Detailed Error and Transmit Response telegram to response flag.  <BR>
 *   <UL type="square">
 *   <LI>When correspondence Data does not exist           : 20 Object Data none</LI>
 *   <LI>For Error with Data base Update process   : 32 DB access Error</LI>
 *   <LI>When Row No in Data and Row No of telegram are different : 57 Row No is illegal. </LI>
 *   </UL>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:00 $
 * @author  $Author: suresh $
 */

public class Id0006Process extends IdProcess
{
	//#CM700772
	//Class fields --------------------------------------------------
	//#CM700773
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0006Process";
	
	//#CM700774
	//Class variables -----------------------------------------------
	//#CM700775
	//Class methods -------------------------------------------------
	//#CM700776
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:00 $";
	}

	//#CM700777
	//Constructors --------------------------------------------------
	//#CM700778
	/**
	 * Generate the instance. 
	 */
	public Id0006Process()
	{
		super();
	}

	//#CM700779
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public Id0006Process(Connection conn)
	{
		super();
		wConn = conn;
	}

	//#CM700780
	// Public methods -----------------------------------------------
	//#CM700781
	/**
	 * Process work Data report. 
	 * @param  rdt  Receiving buffer
	 * @param  sdt  Sending buffer
	 * @throws Exception It reports on all exceptions.  
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM700782
		// For receiving telegram decomposition
		RFTId0006 rftId0006 = null;
		//#CM700783
		// For sending telegram decomposition
		RFTId5006 rftId5006 = null;
		try
		{
			//#CM700784
			// The instance for reception telegram analysis is generated. 
			rftId0006 = (RFTId0006) PackageManager.getObject("RFTId0006");
			rftId0006.setReceiveMessage(rdt);

			//#CM700785
			// The instance for sending telegram making is generated. 
			rftId5006 = (RFTId5006) PackageManager.getObject("RFTId5006");
		}
		catch (IllegalAccessException e)
		{
        	//#CM700786
        	// 6006003=Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*006");
			throw e;
		}

		//#CM700787
		// Variable for response flag
		String ansCode = RFTId5006.ANS_CODE_NORMAL;

		//#CM700788
		// Variable which maintains detailed error
		String errDetails = RFTId5006.ErrorDetails.NORMAL;

		//#CM700789
		// RFT machine  No. acquisition from receiving telegram
		String rftNo = rftId0006.getRftNo();

		//#CM700790
		// Person in charge code acquisition from Receiving telegram
		String workerCode = rftId0006.getWorkerCode();
				
		//#CM700791
		// Acquire Working data from Receiving telegram. 
		byte[] workingData = rftId0006.getDataContents();

		int index = -1;

		try
		{
			//#CM700792
			// Acquire Row No from Receiving telegram. 
			index = rftId0006.getLineNo();
			//#CM700793
			// Write Working data. 
			WorkDataFile.updateWorkingData(workingData, rftNo, rftId0006.getWorkFileName(), index, wConn);
			wConn.commit();
		}
		catch (ReadWriteException e)
		{
			//#CM700794
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
					CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5006.ANS_CODE_ERROR;
			errDetails = RFTId5006.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (NotFoundException e)
		{
			String errData = "[RftNo:" + rftNo + "LineNo:" + index + "]";
			//#CM700795
			// 6026016=No data you try to update is found. {0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME,
			errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5006.ANS_CODE_ERROR;
			errDetails = RFTId5006.ErrorDetails.NULL;
		}
		catch (InvalidDefineException e)
		{
			//#CM700796
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
			ansCode = RFTId5006.ANS_CODE_ERROR;
			errDetails = RFTId5006.ErrorDetails.PARAMETER_ERROR;
		}
		catch (SQLException e)
		{
			//#CM700797
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
					CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5006.ANS_CODE_ERROR;
			errDetails = RFTId5006.ErrorDetails.DB_ACCESS_ERROR;

		}
		catch(NumberFormatException e)
		{
			//#CM700798
			// Other Error
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			//#CM700799
			// Response flag :  Error
			ansCode = RFTId5006.ANS_CODE_ERROR;
			errDetails = RFTId5006.ErrorDetails.PARAMETER_ERROR;
			
		}
		catch (InvalidStatusException e)
		{
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			//#CM700800
			// Make response flag telegram Error when Row No is not corresponding. 
			ansCode = RFTId5006.ANS_CODE_ERROR;
			errDetails = RFTId5006.ErrorDetails.LINE_NO_INCONSISTENCY;
		}
		catch (Exception e)
		{
			//#CM700801
			// Other Error
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM700802
			// Response flag :  Error
			ansCode = RFTId5006.ANS_CODE_ERROR;
			errDetails = RFTId5006.ErrorDetails.INTERNAL_ERROR;
		}
		
		//#CM700803
		//Make Response telegram by using the RFTId5006 instance. 

		//#CM700804
		// STX
		rftId5006.setSTX();

		//#CM700805
		// SEQ
		rftId5006.setSEQ(0);

		//#CM700806
		// ID
		rftId5006.setID(RFTId5006.ID);

		//#CM700807
		// Handy transmission time
		rftId5006.setRftSendDate(rftId0006.getRftSendDate());

		//#CM700808
		// Server transmission time
		rftId5006.setServSendDate();

		//#CM700809
		// RFT machine
		rftId5006.setRftNo(rftNo);

		//#CM700810
		// Person in charge code
		rftId5006.setWorkerCode(workerCode);

		//#CM700811
		// Response flag
		rftId5006.setAnsCode(ansCode);

		//#CM700812
		//  Detailed Error
		rftId5006.setErrDetails(errDetails);

		//#CM700813
		// ETX
		rftId5006.setETX();

		//#CM700814
		// Acquisition of telegram
		rftId5006.getSendMessage(sdt);
	}

	//#CM700815
	// Package methods ----------------------------------------------

	//#CM700816
	// Protected methods --------------------------------------------

	//#CM700817
	// Private methods ---------------------------------------------
}
//#CM700818
//end of class
