// $Id: Id0011Process.java,v 1.2 2006/11/14 06:09:01 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM700901
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;

//#CM700902
/**
 * Designer : T.Konishi <BR>
 * Maker :   <BR>
 * <BR>
 * The class to process Plan date Inquiry(ID0011). <BR>
 * Succeed to <CODE>IdProcess</CODE> class, and mount necessary processing. <BR>
 * Acquire Search condition from Receiving telegram, retrieve Work plan date, and generate Response telegram. <BR>
 * Retrieval processing uses the Id0011Operate class. <BR>
 * <BR>
 * Plan date Inquiry processing(<CODE>processReceivedId()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Receive reception telegram as a parameter, and generate response telegram. <BR>
 *   Acquire Consignor Code, Work type, and Detail Work type from Receiving telegram, use as Search condition, and acquire Work plan date. <BR>
 *   Generate NormalResponse telegram when you can acquire Work plan date. <BR>
 *   Distinguish the cause, and generate abnormal Response telegram when you cannot acquire Work plan date. <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/24</TD><TD>T.Konishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:01 $
 * @author  $Author: suresh $
 */
public class Id0011Process extends IdProcess
{
	//#CM700903
	// Class fields----------------------------------------------------
	//#CM700904
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0011Process";

	//#CM700905
	// Class variables -----------------------------------------------

	//#CM700906
	// Class method --------------------------------------------------
	//#CM700907
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:01 $";
	}

	//#CM700908
	// Constructors --------------------------------------------------
	//#CM700909
	/**
	 * Generate the instance. 
	 */
	public Id0011Process()
	{
		super();
	}

	//#CM700910
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public Id0011Process(Connection conn)
	{
		super();
		wConn = conn;
	}

	//#CM700911
	// Public methods ------------------------------------------------
	//#CM700912
	/**
	 * Processes Plan date inquiry. 
	 * Retrieve Consignor Name of received Consignor Code, and set the retrieval result in Consignor Name. 
	 * @param  rdt  Receiving buffer
	 * @param  sdt  Sending buffer
	 * @throws Exception It reports on all exceptions.  
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM700913
		// The instance for reception telegram analysis is generated. 
		RFTId0011 rftId0011 = null;
		//#CM700914
		// The instance for sending telegram making is generated. 
		RFTId5011 rftId5011 = null;
		try
		{
			//#CM700915
			// The instance for reception telegram analysis is generated. 
			rftId0011 = (RFTId0011) PackageManager.getObject("RFTId0011");
			rftId0011.setReceiveMessage(rdt);

			//#CM700916
			// The instance for sending telegram making is generated. 
			rftId5011 = (RFTId5011) PackageManager.getObject("RFTId5011");
		}
		catch (IllegalAccessException e)
		{
        	//#CM700917
        	// 6006003=Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*011");
			throw e;
		}

		//#CM700918
		// Variable which maintains Response flag
		String ansCode = RFTId5011.ANS_CODE_NORMAL;

		//#CM700919
		// Variable which maintains detailed error
		String errDetails = RFTId5011.ErrorDetails.NORMAL;

		//#CM700920
		// Consignor Code is acquired from Receiving telegram. 
		String consignorCode = rftId0011.getConsignorCode();

		//#CM700921
		// Work plan date is acquired from Receiving telegram. 
		String planDate = rftId0011.getPlanDate();

		try
		{
			//#CM700922
			// The instance of Id0011Operate is generated. 
			Id0011Operate id0011Operate =
			    (Id0011Operate) PackageManager.getObject("Id0011Operate");
			id0011Operate.setConnection(wConn);

			//#CM700923
			// The Consignor Name is acquired from Id0011Operate. 
			boolean rv =
				id0011Operate.checkPlanDate(
				        consignorCode,
				        rftId0011.getWorkType(),
				        rftId0011.getWorkDetails(),
				        planDate,
				        rftId0011.getRftNo(),
				        rftId0011.getWorkerCode());
			if (! rv)
			{
				//#CM700924
				// Do not Return correspondence Data when there is no corresponding Work information. 
				ansCode = RFTId5011.ANS_CODE_NULL;
			}
		}
		//#CM700925
		// When abnormality occurs by the connection with the Data base
		catch (ReadWriteException e)
		{
			//#CM700926
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM700927
			// Response flag :  Error
			ansCode = RFTId5011.ANS_CODE_ERROR;
			errDetails = RFTId5011.ErrorDetails.DB_ACCESS_ERROR;
		}
		//#CM700928
		// In case of illegal Work flag
		catch (IllegalArgumentException e)
		{
			//#CM700929
			// 6006016=Definition error occurred. {1} was set in Item={0}.
			RftLogMessage.print(
				6006016,
				LogMessage.F_ERROR,
				CLASS_NAME,
				"WorkType",
				e.getMessage());
			//#CM700930
			// Response flag :  Error
			ansCode = RFTId5011.ANS_CODE_ERROR;
			errDetails = RFTId5011.ErrorDetails.PARAMETER_ERROR;
		}
		//#CM700931
		// When there is Other Error
		catch (IllegalAccessException e)
		{
        	//#CM700932
        	// 6006003=Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "Id0011Operate");
			ansCode = RFTId5011.ANS_CODE_ERROR;
			errDetails = RFTId5011.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (Exception e)
		{
			//#CM700933
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM700934
			// Response flag :  Error
			ansCode = RFTId5011.ANS_CODE_ERROR;
			errDetails = RFTId5011.ErrorDetails.INTERNAL_ERROR;
		}

		//#CM700935
		// Response telegram making
		//#CM700936
		// STX
		rftId5011.setSTX();

		//#CM700937
		// SEQ
		rftId5011.setSEQ(0);

		//#CM700938
		// ID
		rftId5011.setID(RFTId5011.ID);

		//#CM700939
		// RFT transmission time
		rftId5011.setRftSendDate(rftId0011.getRftSendDate());

		//#CM700940
		// SERVER transmission time
		rftId5011.setServSendDate();

		//#CM700941
		// RFT machine
		rftId5011.setRftNo(rftId0011.getRftNo());

		//#CM700942
		// Worker code
		rftId5011.setWorkerCode(rftId0011.getWorkerCode());

		//#CM700943
		// Consignor Code
		rftId5011.setConsignorCode(consignorCode);

		//#CM700944
		// Work plan date
		rftId5011.setPlanDate(planDate);

		//#CM700945
		// Response flag
		rftId5011.setAnsCode(ansCode);

		//#CM700946
		//  Detailed Error
		rftId5011.setErrDetails(errDetails);

		//#CM700947
		// ETX
		rftId5011.setETX();

		//#CM700948
		// Acquire response telegram. 
		rftId5011.getSendMessage(sdt);

	}

	//#CM700949
	// Package methods -----------------------------------------------

	//#CM700950
	// Protected methods ---------------------------------------------

	//#CM700951
	// Private methods -----------------------------------------------

}
//#CM700952
//end of class
