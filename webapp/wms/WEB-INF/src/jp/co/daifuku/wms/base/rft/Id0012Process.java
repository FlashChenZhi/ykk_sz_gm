// $Id: Id0012Process.java,v 1.2 2006/11/14 06:09:02 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701048
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
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;

//#CM701049
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * The class to process Consignor Code Inquiry(ID0012). <BR>
 * Succeed to <CODE>IdProcess</CODE> class, and mount necessary processing. <BR>
 * Acquire Search condition from Receiving telegram, retrieve Consignor  information, and generate Response telegram. <BR>
 * Retrieval processing uses the Id0012Operate class. <BR>
 * <BR>
 * Consignor Code Inquiry processing(<CODE>processReceivedId()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Receive reception telegram as a parameter, and generate response telegram. <BR>
 *   Acquire Work flag, Consignor Code, and Work plan date from Receiving telegram, and acquire Consignor Name as Search condition. <BR>
 *   Generate Normal Response telegram when you can acquire the Consignor name. <BR>
 *   Distinguish the cause, and generate abnormal Response telegram when you cannot acquire the Consignor Name. <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:02 $
 * @author  $Author: suresh $
 */
public class Id0012Process extends IdProcess
{
	//#CM701050
	// Class fields----------------------------------------------------
	//#CM701051
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0012Process";

	//#CM701052
	// Class variables -----------------------------------------------

	//#CM701053
	// Class method --------------------------------------------------
	//#CM701054
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:02 $";
	}

	//#CM701055
	// Constructors --------------------------------------------------
	//#CM701056
	/**
	 * Generate the instance. 
	 */
	public Id0012Process()
	{
		super();
	}

	//#CM701057
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public Id0012Process(Connection conn)
	{
		super();
		wConn = conn;
	}

	//#CM701058
	// Public methods ------------------------------------------------
	//#CM701059
	/**
	 * Inquire Consignor Code and process it. 
	 * Retrieve Consignor Name of received Consignor Code, and set the retrieval result in Consignor Name. 
	 * @param  rdt  Receiving buffer
	 * @param  sdt  Sending buffer
	 * @throws Exception It reports on all exceptions.  
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM701060
		// The instance for reception telegram analysis is generated. 
		RFTId0012 rftId0012 = null;
		//#CM701061
		// The instance for sending telegram making is generated. 
		RFTId5012 rftId5012 = null;
		try
		{
			//#CM701062
			// The instance for reception telegram analysis is generated. 
			rftId0012 = (RFTId0012) PackageManager.getObject("RFTId0012");
			rftId0012.setReceiveMessage(rdt);

			//#CM701063
			// The instance for sending telegram making is generated. 
			rftId5012 = (RFTId5012) PackageManager.getObject("RFTId5012");
		}
		catch (IllegalAccessException e)
		{
        	//#CM701064
        	// 6006003=Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*012");
			throw e;
		}

		//#CM701065
		// Variable which maintains Response flag
		String ansCode = RFTId5012.ANS_CODE_NORMAL;

		//#CM701066
		// Variable which maintains detailed error
		String errDetails = RFTId5012.ErrorDetails.NORMAL;

		//#CM701067
		// Variable which maintains Consignor Name
		String consignorName = "";

		//#CM701068
		// Consignor Code is acquired from Receiving telegram. 
		String consignorCode = rftId0012.getConsignorCode();

		//#CM701069
		// Work plan date is acquired from Receiving telegram. 
		String planDate = rftId0012.getPlanDate();

		try
		{
			if(DisplayText.isPatternMatching(consignorCode))
			{
				errDetails = RFTId5012.ErrorDetails.NULL;
				ansCode = RFTId5012.ANS_CODE_ERROR;
			}
			else
			{
				//#CM701070
				// The instance of Id0012Operate is generated. 
				Id0012Operate id0012Operate =
				    (Id0012Operate) PackageManager.getObject("Id0012Operate");
				id0012Operate.setConnection(wConn);
	
				//#CM701071
				// Consignor Name is acquired from Id0012Operate. 
				consignorName =
					id0012Operate.getConsignorName(
				        rftId0012.getWorkType(),
				        rftId0012.getWorkDetails(),
				        consignorCode,
				        planDate,
				        rftId0012.getRftNo(),
				        rftId0012.getWorkerCode());
			}
		}
		//#CM701072
		// When you cannot acquire information from Id0012Operate
		catch (NotFoundException e)
		{
			//#CM701073
			// Response flag : There is no pertinent Consignor . 
			ansCode = RFTId5012.ANS_CODE_NULL;
		}
		//#CM701074
		// When abnormality occurs by the connection with the Data base
		catch (ReadWriteException e)
		{
			//#CM701075
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM701076
			// Response flag :  Error
			ansCode = RFTId5012.ANS_CODE_ERROR;
			errDetails = RFTId5012.ErrorDetails.DB_ACCESS_ERROR;
		}
		//#CM701077
		// In case of illegal Work flag
		catch (IllegalArgumentException e)
		{
			//#CM701078
			// 6006016=Definition error occurred. {1} was set in Item={0}.
			RftLogMessage.print(
				6006016,
				LogMessage.F_ERROR,
				CLASS_NAME,
				"WorkType",
				e.getMessage());
			//#CM701079
			// Response flag :  Error
			ansCode = RFTId5012.ANS_CODE_ERROR;
			errDetails = RFTId5012.ErrorDetails.PARAMETER_ERROR;
		}
		//#CM701080
		// When there is Other Error
		catch (IllegalAccessException e)
		{
        	//#CM701081
        	// 6006003=Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*012");
			//#CM701082
			// Response flag :  Error
			ansCode = RFTId5012.ANS_CODE_ERROR;
			errDetails = RFTId5012.ErrorDetails.INSTACIATE_ERROR;
		}
		
		catch (ScheduleException e) 
		{
			//#CM701083
			// 6006001=Unexpected error occurred.{0}{0}
			RftLogMessage.print(6006001, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			//#CM701084
			// Response flag :  Error
			ansCode = RFTId5012.ANS_CODE_ERROR;
			errDetails = RFTId5012.ErrorDetails.SCHEDULE_ERROR;
		}
		catch (Exception e)
		{
			//#CM701085
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM701086
			// Response flag :  Error
			ansCode = RFTId5012.ANS_CODE_ERROR;
			errDetails = RFTId5012.ErrorDetails.INTERNAL_ERROR;
		}

		//#CM701087
		// Response telegram making
		//#CM701088
		// STX
		rftId5012.setSTX();

		//#CM701089
		// SEQ
		rftId5012.setSEQ(0);

		//#CM701090
		// ID
		rftId5012.setID(RFTId5012.ID);

		//#CM701091
		// RFT transmission time
		rftId5012.setRftSendDate(rftId0012.getRftSendDate());

		//#CM701092
		// SERVER transmission time
		rftId5012.setServSendDate();

		//#CM701093
		// RFT machine
		rftId5012.setRftNo(rftId0012.getRftNo());

		//#CM701094
		// Worker code
		rftId5012.setWorkerCode(rftId0012.getWorkerCode());

		//#CM701095
		// Consignor Code
		rftId5012.setConsignorCode(consignorCode);

		//#CM701096
		// Consignor Name
		rftId5012.setConsignorName(consignorName);

		//#CM701097
		// Response flag
		rftId5012.setAnsCode(ansCode);

		//#CM701098
		//  Detailed Error
		rftId5012.setErrDetails(errDetails);

		//#CM701099
		// ETX
		rftId5012.setETX();

		//#CM701100
		// Acquire response telegram. 
		rftId5012.getSendMessage(sdt);

	}

	//#CM701101
	// Package methods -----------------------------------------------

	//#CM701102
	// Protected methods ---------------------------------------------

	//#CM701103
	// Private methods -----------------------------------------------

}
//#CM701104
//end of class
