// $Id: Id0230Process.java,v 1.2 2006/12/07 09:00:13 suresh Exp $
package jp.co.daifuku.wms.storage.rft;

import java.sql.Connection;
import java.sql.SQLException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM575107
/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM575108
/**
 * Designer : Y.Taki<BR>
 * Maker : Y.Taki<BR>
 * <BR>
 * Process Storage start request from RFT (ID0230) <BR>
 * Use Id0230Operate class function to search for storage data from Work info
 * and create the telegraph id to be sent to RFT<BR>
 * <BR>
 * Storage start request process (<CODE>processReceivedId</CODE> method)<BR>
 * <BR>
 * <DIR>
 *   Fetch the required info from received data<BR>
 *   Search Work info using ID0230Operate class functions and fetch storage data<BR>
 *   Create the telegraph id and set the text values to the send buffer<BR>
 *   If the response flag is 0 : normal, update RFT Work Info using the telegraph id contents<BR>
 *   Write to log if error occurs during update<BR>
 *   [Update conditions]
 *   <UL><LI>RFTNo</LI></UL>
 *   [Update contents]
 *   <UL><LI>Response id: created response id</LI>
 *       <LI>Last update date/time:system date/time</LI>
 *       <LI>Last update Process name:ID0230</LI>
 *   </UL>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:13 $
 * @author  $Author: suresh $
 */
public class Id0230Process extends IdProcess
{
	//#CM575109
	// Class fields----------------------------------------------------
	//#CM575110
	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "Id0230Process";
	
	//#CM575111
	/**
	 * Process name
	 */
	private static final String PROCESS_NAME = "ID0230";

	//#CM575112
	// Class variables -----------------------------------------------

	//#CM575113
	// Class method --------------------------------------------------
	//#CM575114
	/**
	 * Return the version of this class
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:13 $");
	}

	//#CM575115
	// Constructors --------------------------------------------------
	//#CM575116
	/**
	 * Constructor
	 */
	public Id0230Process()
	{
		super();
	}

	//#CM575117
	/**
	 * Pass Db connection info to the constructor
	 * @param conn DBConnection info
	 */
	public Id0230Process(Connection conn)
	{
		super();
		wConn = conn;
	}

	//#CM575118
	// Public methods ------------------------------------------------
	//#CM575119
	/**
	 * Storage start request process (ID0230)<BR>
	 * Fetch the telegraph id as byte array, and create (ID5230)<BR>
	 * <BR>
	 * Ensure that daily update process is not underway in the beginning<BR>
	 * In  case of daily update return 5: Daily update<BR>
	 * Next confirm that target record exists in worker result<BR>
	 * If not, create a new record<BR>
	 * <BR>
	 * Search Work info using ID0230Operate class for the request work<BR>
	 * If there is work possible data, fetch it<BR>
	 * If there are multiple target data, Operate class will do Collect operation<BR>
	 * Create send telegraph id and write to the buffer<BR>
	 * If the response flag is 0 : normal, update RFT Work Info using the telegraph id contents<BR>
	 *   [Update conditions]
	 *   <UL><LI>RFTNo</LI></UL>
	 *   [Update contents]
	 *   <UL><LI>Response id: created response id</LI>
	 *       <LI>Last update date/time:system date/time</LI>
	 *       <LI>Last update Process name:ID0230</LI>
	 *   </UL>
	 * Write to log if error occurs during update<BR>
	 * <BR>
	 * @param  rdt  Receive buffer
	 * @param  sdt  Send buffer
	 * @throws Exception Throw all exceptions
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		RFTId0230 rftId0230 = null;
		RFTId5230 rftId5230 = null;
		try
		{
			//#CM575120
			// Create instance to receive telegraph data
			rftId0230 = (RFTId0230) PackageManager.getObject("RFTId0230");
			rftId0230.setReceiveMessage(rdt);

			//#CM575121
			// Create instance to send telegraph data
			rftId5230 = (RFTId5230) PackageManager.getObject("RFTId5230");
		}
		catch (IllegalAccessException e)
		{
			//#CM575122
			// 6006003=Error occurred during ID process. {0}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*230", e.getMessage());
			throw e;
		}

		//#CM575123
		// Fetch RFT no. from telegraph data
		String rftNo = rftId0230.getRftNo();

		//#CM575124
		// Fetch worker code from telegraph data
		String workerCode = rftId0230.getWorkerCode();

		//#CM575125
		// Fetch consignor code from telegraph data
		String consignorCode = rftId0230.getConsignorCode();

		//#CM575126
		// Fetch storage plan date from telegraph data
		String planDate = rftId0230.getPlanDate();

		//#CM575127
		// Fetch storage item code from telegraph data
		String itemCode = rftId0230.getScanItemCode1();
		
		//#CM575128
		// Fetch storage item code 2 from telegraph data
		String convertedJanCode = rftId0230.getScanItemCode2();

		//#CM575129
		// Fetch storage case piece flag from telegraph data
		String casePieceFlag = rftId0230.getCasePieceFlag();

		//#CM575130
		// Variable to store work form (case piece) (used in response)
		String workFormFlag = "";

		//#CM575131
		// Variable to store consignor name
		String consignorName = "";

		//#CM575132
		// Variable for Storage location area
		String storageLocationArea = "";
		
		//#CM575133
		// Variable for Storage location no.
		String storageLocationNo = "";

		//#CM575134
		// Variable for Collect Job no.
		String setCollectJobNo = "";

		//#CM575135
		// Variable for Bundle ITF
		String bundleITF = "";

		//#CM575136
		// Variable for Case ITF
		String ITF = "";

		//#CM575137
		// Variable for Item name
		String itemName = "";

		//#CM575138
		// Variable for qty per bundle
		int bundleEnteringQty = 0;

		//#CM575139
		// Variable for qty per case
		int enteringQty = 0;

		//#CM575140
		// Variable for expiry date
		String useByDate = "";

		//#CM575141
		// Variable for storage plan qty
		int storagePlanQty = 0;

		//#CM575142
		// Variable for storage complete qty
		int storageCompletionQty = 0;

		//#CM575143
		// Variable for total storage qty
		int totalStorageCnt = 0;

		//#CM575144
		// Variable for pending storage qty
		int remainingStorageCnt = 0;

		//#CM575145
		// Relocation enable/disable flag
		String mobileRackFlag = RFTId5230.NO_MOBILE_RACK_FLAG;

		//#CM575146
		// Variable for response flag
		String ansCode = RFTId5230.ANS_CODE_NORMAL;
		
		//#CM575147
		// Variable for error details
		String errDetails = RFTId5230.ErrorDetails.NORMAL;

		try
		{
			if(DisplayText.isPatternMatching(itemCode))
			{
				throw new NotFoundException(RFTId5230.ANS_CODE_NULL);
			}
			if(DisplayText.isPatternMatching(convertedJanCode))
			{
				throw new NotFoundException(RFTId5230.ANS_CODE_NULL);
			}

			//#CM575148
			// Create BaseOperate instance
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);

			//#CM575149
			//-----------------
			//#CM575150
			// Daily update process check
			//#CM575151
			//-----------------
			if (baseOperate.isLoadingDailyUpdate())
			{
				//#CM575152
				// Return status flag 5 : Daily update
				ansCode = RFTId5230.ANS_CODE_DAILY_UPDATING;
			}
			else
			{
				//#CM575153
				//-----------------
				//#CM575154
				// Check if exist in worker result
				//#CM575155
				//-----------------
				WorkerResult[] workerResult =
					baseOperate.getWorkerResult(
						WorkingInformation.JOB_TYPE_STORAGE,
						workerCode,
						rftNo);
				if (workerResult.length == 0)
				{
					//#CM575156
					// Create worker result
					baseOperate.createWorkerResult(
						WorkingInformation.JOB_TYPE_STORAGE,
						workerCode,
						rftNo);
					//#CM575157
					// commit
					wConn.commit();
				}

				//#CM575158
				// Create Id0230Operate instance
				Id0230Operate id0230Operate =
					(Id0230Operate) PackageManager.getObject("Id0230Operate");
				id0230Operate.setConnection(wConn);

				//#CM575159
				// Check whether IDM package exist or not
				if (baseOperate.isExistIdmPackage(wConn))
				{
					mobileRackFlag = RFTId5230.MOBILE_RACK_FLAG;
				}
				
				
				//#CM575160
				// Fetch storage plan info using Id0230Operate
				WorkingInformation storageWorkData =
					id0230Operate.startStorage(
						consignorCode,
						planDate,
						itemCode,
						convertedJanCode,
						casePieceFlag,
						rftNo,
						workerCode);

				StorageOperate storageOperate = null;

				//#CM575161
				// Create StorageOperate instance
				storageOperate = (StorageOperate)PackageManager.getObject("StorageOperate");
				storageOperate.setConnection(wConn);

				//#CM575162
				//Get data to set in response id
				//#CM575163
				// Set JAN code
				itemCode = storageWorkData.getItemCode();
				//#CM575164
				// Set Consignor code
				consignorName = storageWorkData.getConsignorName();
				//#CM575165
				// Set storage location
				storageLocationNo = storageWorkData.getLocationNo();
				//#CM575166
				// Set Collect Job no.
				setCollectJobNo = storageWorkData.getCollectJobNo();
				//#CM575167
				// Set Bundle ITF
				bundleITF = storageWorkData.getBundleItf();
				//#CM575168
				// Set Case ITF
				ITF = storageWorkData.getItf();
				//#CM575169
				// Set Item Name
				itemName = storageWorkData.getItemName1();
				//#CM575170
				// Set packed qty per bundle
				bundleEnteringQty = storageWorkData.getBundleEnteringQty();
				//#CM575171
				// Set packed qty per case
				enteringQty = storageWorkData.getEnteringQty();
				//#CM575172
				// Set expiry date 
				useByDate = storageWorkData.getUseByDate();
				//#CM575173
				// Set Storage plan qty
				storagePlanQty = storageWorkData.getPlanEnableQty();
				//#CM575174
				// Set Storage complete qty
				storageCompletionQty = storageWorkData.getResultQty();
				//#CM575175
				// Set work form (case piece flag)
				workFormFlag = storageWorkData.getWorkFormFlag();
				//#CM575176
				// Fetch storage work plan qty from StorageOperator
				totalStorageCnt = storageOperate.countStorageDataOfAll(consignorCode, planDate, casePieceFlag);
				//#CM575177
				// Fetch storage work pending qty from StorageOperator
				remainingStorageCnt =storageOperate.countStorageDataOfWorkable(consignorCode,planDate,casePieceFlag,rftNo,workerCode)-1;
				//#CM575178
				// commit
				wConn.commit();

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
			ansCode = e.getMessage();
			if (! RFTId5230.checkAnsCode(ansCode))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				ansCode = RFTId5230.ANS_CODE_ERROR;
				errDetails = RFTId5230.ErrorDetails.NULL;
			}
			else if (ansCode.equals(RFTId5230.ANS_CODE_ERROR))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				errDetails = RFTId5230.ErrorDetails.NULL;
			}
		}
		//#CM575179
		// When data lock is not removed on time
		catch (LockTimeOutException e)
		{
			//#CM575180
			// SELECT FOR UPDATE timeout
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5230.ANS_CODE_MAINTENANCE;
		}
		//#CM575181
		// If overflow occurs
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" PlanDate:" + planDate +
							" casePieceFlag:" + casePieceFlag +
							" ItemCode:" + itemCode +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			//#CM575182
			// 6026027=Length overflow. Process was aborted. {0}
			RftLogMessage.print(6026027, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5230.ANS_CODE_ERROR;
			errDetails = RFTId5230.ErrorDetails.COLLECTION_OVERFLOW;
		}
		catch (ReadWriteException e)
		{
			//#CM575183
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
			//#CM575184
			// Response flag : Error
			ansCode = RFTId5230.ANS_CODE_ERROR;
			errDetails = RFTId5230.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (IllegalAccessException e) {
        	//#CM575185
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
			//#CM575186
			// Response flag : Error
			ansCode = RFTId5230.ANS_CODE_ERROR;
			errDetails = RFTId5230.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (DataExistsException e) {
			//#CM575187
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
			//#CM575188
			// Response flag : Error
			ansCode = RFTId5230.ANS_CODE_ERROR;
			errDetails = RFTId5230.ErrorDetails.DB_UNIQUE_KEY_ERROR;
		}
		catch (SQLException e) {
			//#CM575189
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
			//#CM575190
			// Response flag : Error
			ansCode = RFTId5230.ANS_CODE_ERROR;
			errDetails = RFTId5230.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (InvalidDefineException e) {
			//#CM575191
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

			//#CM575192
			// Response flag : Error
			ansCode = RFTId5230.ANS_CODE_ERROR;
			errDetails = RFTId5230.ErrorDetails.PARAMETER_ERROR;
		}
		catch (ScheduleException e)
		{
			//#CM575193
			// 6006001=Unexpected error occurred.{0}
			RftLogMessage.print(6006001, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			//#CM575194
			// Response flag : Error
			ansCode = RFTId5230.ANS_CODE_ERROR;
			errDetails = RFTId5230.ErrorDetails.SCHEDULE_ERROR;
		}
		
		//#CM575195
		// Other error occurred
		catch (Exception e)
		{
			//#CM575196
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
			ansCode = RFTId5230.ANS_CODE_ERROR;
			errDetails = RFTId5230.ErrorDetails.INTERNAL_ERROR;
		}
		
		//#CM575197
		// Create response id
		//#CM575198
		// STX
		rftId5230.setSTX();
		//#CM575199
		// SEQ
		rftId5230.setSEQ(0);
		//#CM575200
		// ID
		rftId5230.setID(RFTId5230.ID);
		//#CM575201
		// RFT send time
		rftId5230.setRftSendDate(rftId0230.getRftSendDate());
		//#CM575202
		// Server send time
		rftId5230.setServSendDate();
		//#CM575203
		// RFT no.
		rftId5230.setRftNo(rftNo);
		//#CM575204
		// Worker code
		rftId5230.setWorkerCode(workerCode);
		//#CM575205
		// Consignor code
		rftId5230.setConsignorCode(consignorCode);
		//#CM575206
		// Set Consignor code
		rftId5230.setConsignorName(consignorName);
		//#CM575207
		// Storage plan date
		rftId5230.setPlanDate(planDate);
		//#CM575208
		// Selected case piece type
		rftId5230.setSelectCasePiece(casePieceFlag);
		//#CM575209
		// Storage instructed area no.
		rftId5230.setStorageLocationNo(storageLocationArea);
		//#CM575210
		// Set storage location
		rftId5230.setStorageLocationNo(storageLocationNo);
		//#CM575211
		// Set Collect Job no.
		rftId5230.setCollectJobNo(setCollectJobNo);
		//#CM575212
		// Item code
		rftId5230.setItemCode("");
		//#CM575213
		// JAN code
		rftId5230.setJANCode(itemCode);
		//#CM575214
		// Set Bundle ITF
		rftId5230.setBundleITF(bundleITF);
		//#CM575215
		// Set Case ITF
		rftId5230.setITF(ITF);
		//#CM575216
		// Set Item Name
		rftId5230.setItemName(itemName);
		//#CM575217
		// Set packed qty per bundle
		rftId5230.setBundleEnteringQty(bundleEnteringQty);
		//#CM575218
		// Set packed qty per case
		rftId5230.setEnteringQty(enteringQty);
		//#CM575219
		// Position
		rftId5230.setUnit("");
		//#CM575220
		// Lot no.
		rftId5230.setLotNo("");
		//#CM575221
		// Set expiry date
		rftId5230.setUseByDate(useByDate);
		//#CM575222
		// Production day
		rftId5230.setManufactureDate("");
		//#CM575223
		// Set Storage plan qty
		rftId5230.setStoragePlanQty(storagePlanQty);
		//#CM575224
		// Set Storage complete qty
		rftId5230.setStorageCompletionQty(storageCompletionQty);	
		//#CM575225
		// Work form (Case piece type)
		rftId5230.setCasePieceFlag(workFormFlag);
		//#CM575226
		// Set Total storage qty
		rftId5230.setTotalStorageQty(totalStorageCnt);
		//#CM575227
		// Set Pending storage qty
		rftId5230.setRemainingStorageQty(remainingStorageCnt);
		//#CM575228
		// Relocation enable/disable flag
		rftId5230.setMobileRackFlag(mobileRackFlag);
		//#CM575229
		// Response flag
		rftId5230.setAnsCode(ansCode);
		//#CM575230
		// Error details
		rftId5230.setErrDetails(errDetails);
		//#CM575231
		// ETX
		rftId5230.setETX();
		//#CM575232
		// Acquire response id
		rftId5230.getSendMessage(sdt);

		//#CM575233
		// If the response flag is 0: normal, save the response id to file
		try
		{
			if (ansCode.equals(RFTId5230.ANS_CODE_NORMAL))
			{
				rftId5230.saveResponseId(rftNo, PROCESS_NAME, wConn);
				wConn.commit();
			}
		}
		catch (Exception e)
		{
			//#CM575234
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

		}

	}

	//#CM575235
	// Package methods -----------------------------------------------

	//#CM575236
	// Protected methods ---------------------------------------------

	//#CM575237
	// Private methods -----------------------------------------------

}
//#CM575238
//end of class
