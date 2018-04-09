// $Id: Id0921Process.java,v 1.2 2006/09/27 03:00:36 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;

//#CM10140
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
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM10141
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * This is a class to execute stock list request (ID0118) process.<BR>
 * Inherit <CODE>IdProcess</CODE> class and implement the required processes.<BR>
 * Obtain the search conditions from the received electronic statement and search through the stock list info available for picking and<BR>
 * Generate stock list (item code ascending order) file<BR>
 * search and file create process use Id0118Operate class.<BR>
 * <BR>
 * Stock table requesting process(<CODE>processReceivedId()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Receive electronic statement as parameter and create the responsing electronic statement.<BR>
 *   Obtain the Consignor code, Location No., Load size from the electronic statement received and create responce electronic statement.<BR>
 *   Create stock list file when stock list info can be obtained and create responce normal electronic statement.<BR>
 *   Identify the reason when stock list info cannot be obtained and create responce abnormal electronic statement.<BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:36 $
 * @author  $Author: suresh $
 */
public class Id0921Process extends IdProcess
{
	//#CM10142
	// Class fields----------------------------------------------------
	//#CM10143
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0921Process";

	//#CM10144
	// Class variables -----------------------------------------------
	//#CM10145
	// Class method --------------------------------------------------
	//#CM10146
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/09/27 03:00:36 $";
	}

	//#CM10147
	// Constructors --------------------------------------------------
	//#CM10148
	/**
	 * Generate Instance.
	 */
	public Id0921Process()
	{
		super();
	}

	//#CM10149
	/**
	 * Generate Designate <code>Connection</code> for database connection and generate the instance.
	 * @param conn For database connection Connection
	 */
	//#CM10150
	// Not available
	public Id0921Process(Connection conn)
	{
		super();
		wConn = conn;
	}

	//#CM10151
	// Public methods ------------------------------------------------
	//#CM10152
	/**
	 * stock list request Executes processing.
	 * Search and receive Consignor code
	 * Set the stock list file name for the sending electronic statement.
	 * @param  rdt  Receiving buffer
	 * @param  sdt  sending  buffer
	 * @throws Exception Report all exceptions.
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM10153
		// Generate instance for analyzing the received electronic statement.
		RFTId0921 rftId0921 = null;
		//#CM10154
		// Generate instance for generating a sending electronic statement
		RFTId5921 rftId5921 = null;
		try
		{
			//#CM10155
			// Generate instance for analyzing the received electronic statement.
			rftId0921 = (RFTId0921) PackageManager.getObject("RFTId0921");
			rftId0921.setReceiveMessage(rdt);

			//#CM10156
			// Generate instance for generating a sending electronic statement
			rftId5921 = (RFTId5921) PackageManager.getObject("RFTId5921");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}

		//#CM10157
		// Obtain the Consignor code from the received electronic statement.
		String consignorCode = rftId0921.getConsignorCode();
		
		//#CM10158
		// Obtain the Area No. from the received electronic statement.
		String areaNo = rftId0921.getAreaNo();

		//#CM10159
		// Obtain the Location No. from the received electronic statement.
		String locationNo = rftId0921.getLocationNo();

		//#CM10160
		// Obtain the RFT machine from the received electronic statement.
		String rftNo = rftId0921.getRftNo();
		
		//#CM10161
		// Obtain the Load Size from the received electronic statement.
		String itemForm = rftId0921.getItemForm();

		//#CM10162
		// Variable to maintain the Response flag
		String ansCode = RFTId5921.ANS_CODE_NORMAL;

		//#CM10163
		// Variable to maintain the detail of the error.
		String errDetails = RFTId5921.ErrorDetails.NORMAL;

		//#CM10164
		// This array maintains the obtained Stock info.
		Stock[] selectStock = null;

		//#CM10165
		// Create file name
		String sendpath = WmsParam.RFTSEND; // wms/rft/send/

		//#CM10166
		// sending file name
		String sendFileName = sendpath + rftNo + "\\" + Id5921DataFile.ANS_FILE_NAME;

		String className = "";
		
		try
		{
			if(DisplayText.isPatternMatching(areaNo))
			{
				throw new NotFoundException();
			}
			if(DisplayText.isPatternMatching(locationNo))
			{
				throw new NotFoundException();
			}

			//#CM10167
			// Generate Id0921Operate instance
			className = "Id0921Operate";
			Id0921Operate id0921Operate =
			    (Id0921Operate) PackageManager.getObject(className);
			id0921Operate.setConnection(wConn);

			//#CM10168
			// Obtain possible picking stock list info.
			selectStock = id0921Operate.findStockData(consignorCode, areaNo ,locationNo, itemForm);

			//#CM10169
			// Create possible picking list file
			className = "Id5921DataFile";
			Id5921DataFile listFile = (Id5921DataFile) PackageManager.getObject(className);
			listFile.setFileName(sendFileName);
			listFile.write(selectStock);
		}
		
		catch (NotFoundException e)
		{
			//#CM10170
			// Response flag：No corresponding stock
			ansCode = RFTId5921.ANS_CODE_NULL;
		}
		//#CM10171
		// when error generated in connection with database
		catch (ReadWriteException e)
		{
			//#CM10172
			// 6006002=Database error occurred.{0}{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM10173
			// Response flag：error
			ansCode = RFTId5921.ANS_CODE_ERROR;
			errDetails = RFTId5921.ErrorDetails.DB_ACCESS_ERROR;
		}
		//#CM10174
		// If the work category is improper
		catch (IllegalArgumentException e)
		{
			//#CM10175
			// 6006016=Definition error occurred. {1} was set in Item={0}.
			RftLogMessage.print(
				6006016,
				LogMessage.F_ERROR,
				CLASS_NAME,
				"WorkType",
				e.getMessage());
			//#CM10176
			// Response flag：error
			ansCode = RFTId5921.ANS_CODE_ERROR;
			errDetails = RFTId5921.ErrorDetails.PARAMETER_ERROR;
		}
		//#CM10177
		// Obtaining no info from Id0921perate instance regards as error.
		catch (IllegalAccessException e)
		{
		    //#CM10178
		    // 6006003=Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, className, e.getMessage());
			//#CM10179
			// Response flag：error
			ansCode = RFTId5921.ANS_CODE_ERROR ;
			errDetails = RFTId5921.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (OverflowException e)
		{
			//#CM10180
			// 6026027=Length overflow. Process was aborted.{0}
			String errData = "[ConsignorCode:" + consignorCode +
			" LocationNo:" + areaNo +
			" LocationNo:" + locationNo +
			" RftNo:" + rftNo +
			" WorkerCode:" + rftId0921.getWorkerCode() +"]";
			RftLogMessage.print(6026027, LogMessage.F_ERROR, CLASS_NAME, errData);
			ansCode = RFTId5921.ANS_CODE_ERROR;
			errDetails = RFTId5921.ErrorDetails.COLLECTION_OVERFLOW;
		}
		catch (IOException e)
		{
		    //#CM10181
		    // 6006020=File I/O error occurred. {0}
			RftLogMessage.print(6006020, LogMessage.F_ERROR, CLASS_NAME, sendFileName);
			//#CM10182
			// Response flag：error
			ansCode = RFTId5921.ANS_CODE_ERROR ;
			errDetails = RFTId5921.ErrorDetails.I_O_ERROR;
		}
		catch (ScheduleException e)
		{
			//#CM10183
			// 6006001=Unexpected error occurred.{0}{0}
			RftLogMessage.print(6006001, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			//#CM10184
			// Response flag：error
			ansCode = RFTId5921.ANS_CODE_ERROR ;
			errDetails = RFTId5921.ErrorDetails.SCHEDULE_ERROR;
		}
		//#CM10185
		// when other error generated
		catch (Exception e)
		{
			//#CM10186
			// 6026015=Error occurred during ID process.{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM10187
			// Response flag：error
			ansCode = RFTId5921.ANS_CODE_ERROR;
			errDetails = RFTId5921.ErrorDetails.INTERNAL_ERROR;
		}

		//#CM10188
		// Create the response electronic statement
		//#CM10189
		// STX
		rftId5921.setSTX();

		//#CM10190
		// SEQ
		rftId5921.setSEQ(0);

		//#CM10191
		// ID
		rftId5921.setID(RFTId5921.ID);

		//#CM10192
		// RFTTransmission time
		rftId5921.setRftSendDate(rftId0921.getRftSendDate());

		//#CM10193
		// SERVERTransmission time
		rftId5921.setServSendDate();

		//#CM10194
		// RFT machine
		rftId5921.setRftNo(rftNo);

		//#CM10195
		// 
		rftId5921.setWorkerCode(rftId0921.getWorkerCode());

		//#CM10196
		// Stock Table File Name
		rftId5921.setTableFileName(sendFileName);

		//#CM10197
		// File record Qty
		if (selectStock != null)
		{
			rftId5921.setFileRecordNumber(selectStock.length);
		}
		else
		{
			rftId5921.setFileRecordNumber(0);
		}

		//#CM10198
		// Response flag
		rftId5921.setAnsCode(ansCode);

		//#CM10199
		// error detail
		rftId5921.setErrDetails(errDetails);

		//#CM10200
		// ETX
		rftId5921.setETX();

		//#CM10201
		// Obtain the response electronic statement
		rftId5921.getSendMessage(sdt);

	}

	//#CM10202
	// Package methods -----------------------------------------------

	//#CM10203
	// Protected methods ---------------------------------------------

	//#CM10204
	// Private methods -----------------------------------------------
}
//#CM10205
//end of class
