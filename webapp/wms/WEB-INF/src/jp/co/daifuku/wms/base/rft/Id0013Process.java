// $Id: Id0013Process.java,v 1.2 2006/11/14 06:09:03 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701151
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM701152
/**
 * Designer : T.Konishi<BR>
 * Maker : <BR>
 * <BR>
 * Process it to Item information inquiry request (ID0013) by RFT. <BR>
 * Acquire Item information from Stock information by using the function that the Id0013Operate class offers. <BR>
 * Generate Response telegram transmitted to RFT. <BR>
 * <BR>
 * Item information inquiry request processing(<CODE>processReceivedId()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *  Acquire necessary information from reception Data. <BR>
 *  Retrieve Stock information by the function of the Id0013Operate class, and acquire information on Item to which Consignor Code+Item Code is corresponding. <BR>
 *  The corresponding Storage date for this data is to assume Storage item as New data.<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:03 $
 * @author  $Author: suresh $
 */
public class Id0013Process extends IdProcess
{
	//#CM701153
	// Class fields----------------------------------------------------
	//#CM701154
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0013Process";
	
	//#CM701155
	// Class variables -----------------------------------------------
	
	//#CM701156
	// Class method --------------------------------------------------
	//#CM701157
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:03 $";
	}

	//#CM701158
	// Constructors --------------------------------------------------
	//#CM701159
	/**
	 * Generate the instance. 
	 */
	public Id0013Process()
	{
		super() ;
	}
	
	//#CM701160
	/**
	 * Pass Constructor DBConnection information. 
	 * @param conn		DBConnection information
	 */
	public Id0013Process(Connection conn)
	{
		super() ;
		wConn = conn ;
	}

	//#CM701161
	// Public methods ------------------------------------------------

	//#CM701162
	/**
	 * Process it to Item information inquiry request (ID0013). <BR>
	 * Receive telegram by byte row, and make response (ID5013) by byte row. <BR>
	 * <BR>
	 * Confirm there is no next day processing.
	 * In case of Next day processing, 5:Return Error of the next day processing.<BR>
	 * Confirm there is object Record of results according to the worker. Do New making when it is not.<BR>
	 * Retrieve Stock information by using the ID0013Operate class. <BR>
	 * One the most much leakage Storage and sets information on new Stock on the day in Transmission telegram. <BR>
	 * <BR>
	 * @param  rdt  Receiving buffer
	 * @param  sdt  Sending buffer
	 * @throws Exception It reports on all exceptions.  
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		RFTId0013 rftId0013 = null;
		RFTId5013 rftId5013 = null;
		try
		{
			//#CM701163
			// The instance for reception telegram analysis is generated. 
			rftId0013 = (RFTId0013) PackageManager.getObject("RFTId0013");
			rftId0013.setReceiveMessage(rdt);

			//#CM701164
			// The instance for sending telegram making is generated. 
			rftId5013 = (RFTId5013) PackageManager.getObject("RFTId5013");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}

		//#CM701165
		// RFT machine is acquired from Receiving telegram. 
		String rftNo = rftId0013.getRftNo();

		//#CM701166
		// Worker code is acquired from Receiving telegram. 
		String workerCode = rftId0013.getWorkerCode();
		
		//#CM701167
		// Consignor Code is acquired from Receiving telegram. 
		String consignorCode = rftId0013.getConsignorCode();
		
		//#CM701168
		// Scanning Item Code is acquired from Receiving telegram. 
		String itemCode = rftId0013.getScanItemCode1();
		
		//#CM701169
		// Scan Item Code2 is acquired from Receiving telegram. 
		String convertedJanCode = rftId0013.getScanItemCode2();
		
		//#CM701170
		// Mode of packing is acquired from Receiving telegram. 
		String itemForm = rftId0013.getItemForm();
			
		//#CM701171
		// Variable which maintains Consignor Name
		String consignorName = "";

		//#CM701172
		// Variable which maintains Area
		String areaNo = "";

		//#CM701173
		// Variable which maintains Location
		String locationNo = "";

		//#CM701174
		// Variable which maintains JAN Code
		String janCode = "";

		//#CM701175
		// Variable which maintains Item identification Code
		String itemId = "";
	
		//#CM701176
		// Variable which maintains Bundle ITF
		String bundleItf = "";

		//#CM701177
		// Variable which maintains Case ITF
		String caseItf = "";

		//#CM701178
		// Variable which maintains Item Name
		String itemName = "";

		//#CM701179
		// Variable which maintains Packed qty per bundle
		int bundleEnteringQty = 0;

		//#CM701180
		// Variable which maintains Packed qty per case
		int enteringQty = 0;

		//#CM701181
		// Variable which maintains Expiry date
		String useByDate = "";
	
		//#CM701182
		// Movement rack existence flag
		String mobileRackFlag = RFTId5013.NO_MOBILE_RACK_FLAG;

		//#CM701183
		// Variable which maintains Response flag
		String ansCode = RFTId5013.ANS_CODE_NORMAL;
		
		//#CM701184
		// Variable which maintains detailed error
		String errDetails = RFTId5013.ErrorDetails.NORMAL;
		
		try
		{
			if(DisplayText.isPatternMatching(itemCode))
			{
				errDetails = RFTId5013.ErrorDetails.NULL;
				throw new NotFoundException(RFTId5013.ANS_CODE_ERROR);
			}
			if(DisplayText.isPatternMatching(convertedJanCode))
			{
				errDetails = RFTId5013.ErrorDetails.NULL;
				throw new NotFoundException(RFTId5013.ANS_CODE_ERROR);
			}

			//#CM701185
			// The instance of BaseOperate is generated. 
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);
					
			//#CM701186
			//-----------------
			//#CM701187
			// Check during the next day processing
			//#CM701188
			//-----------------
			if (baseOperate.isLoadingDailyUpdate())
			{
				//#CM701189
				// Response flag is 5:The return in next day Update process. 
				ansCode = RFTId5013.ANS_CODE_DAILY_UPDATING;
			}
			else
			{
				//#CM701190
				//-----------------
				//#CM701191
				// Existence check of results according to worker
				//#CM701192
				//-----------------
				WorkerResult[] workerResult =
					baseOperate.getWorkerResult(
						WorkingInformation.JOB_TYPE_EX_STORAGE,
						workerCode,
						rftNo);
				if (workerResult.length == 0)
				{
					//#CM701193
					// New making of worker results
					baseOperate.createWorkerResult(
						WorkingInformation.JOB_TYPE_EX_STORAGE,
						workerCode,
						rftNo);
					//#CM701194
					// Comment
					wConn.commit();
				}

				//#CM701195
				// The instance of Id0013Operate is generated. 
				Id0013Operate id0013Operate = (Id0013Operate) PackageManager.getObject("Id0013Operate");
				id0013Operate.setConnection(wConn);
				
				
				//#CM701196
				// Is the movement rack package introduced?
				if (baseOperate.isExistIdmPackage(wConn))
				{
					mobileRackFlag = RFTId5013.MOBILE_RACK_FLAG;
				}
				
				
				//#CM701197
				//-----------------
				//#CM701198
				// Data is acquired from Stock information. 
				//#CM701199
				//-----------------				
				Stock stockInfo = id0013Operate.startNoPlanStorage(
					consignorCode,
					itemCode,
					convertedJanCode,
					itemForm,
					rftNo,
					workerCode);

				if (stockInfo == null)
				{
					//#CM701200
					// -Supplementation- NotFoundException is executed when there is no movement Data and neither slow nor this processing are executed. 
					//#CM701201
					// Response flag is 8:Return without correspondence Data
					ansCode = RFTId5013.ANS_CODE_NULL;
				}
				else
				{
					//#CM701202
					// Data which can be acquired from StockData is acquired, and it sets it in the maintenance variable. 
					consignorName = stockInfo.getConsignorName() ;
					locationNo = stockInfo.getLocationNo();
					areaNo = stockInfo.getAreaNo();
					itemId = stockInfo.getStockId();
					janCode = stockInfo.getItemCode();
					bundleItf = stockInfo.getBundleItf();
					caseItf = stockInfo.getItf();
					itemName = stockInfo.getItemName1();
					bundleEnteringQty = stockInfo.getBundleEnteringQty();
					enteringQty = stockInfo.getEnteringQty();
					useByDate = stockInfo.getUseByDate();
					ansCode = RFTId5013.ANS_CODE_NORMAL;
				}
			}

		}
		//#CM701203
		// Error when information cannot be acquired
		catch(NotFoundException e)
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
			if (ansCode == null || ansCode == "")
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				errDetails = RFTId5013.ErrorDetails.NULL;
				ansCode = RFTId5013.ANS_CODE_ERROR;		
			}
		}
		//#CM701204
		// When there is Other Error
		catch(ReadWriteException e)
		{
			//#CM701205
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
			//#CM701206
			// Response flag :  Error
			ansCode = RFTId5013.ANS_CODE_ERROR ;
			errDetails = RFTId5013.ErrorDetails.DB_ACCESS_ERROR;
		} 
		catch (IllegalAccessException e) {
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*013",e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM701207
			// Response flag :  Error
			ansCode = RFTId5013.ANS_CODE_ERROR;
			errDetails = RFTId5013.ErrorDetails.INSTACIATE_ERROR;
		} 
		catch (DataExistsException e) {
			//#CM701208
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

			//#CM701209
			// Response flag :  Error
			ansCode = RFTId5013.ANS_CODE_ERROR;
			errDetails = RFTId5013.ErrorDetails.DB_UNIQUE_KEY_ERROR;
		} 
		catch (SQLException e) {
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM701210
			// Response flag :  Error
			ansCode = RFTId5013.ANS_CODE_ERROR;
			errDetails = RFTId5013.ErrorDetails.DB_ACCESS_ERROR;
		} 

		catch (ScheduleException e)
		{
			//#CM701211
			// 6006001=Unexpected error occurred.{0}{0}
			RftLogMessage.print(6006001, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			//#CM701212
			// Response flag :  Error
			ansCode = RFTId5013.ANS_CODE_ERROR;
			errDetails = RFTId5013.ErrorDetails.SCHEDULE_ERROR;
		}
		//#CM701213
		// Other Error
		catch(Exception e)
		{
			//#CM701214
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
			//#CM701215
			// Response flag :  Error
			ansCode = RFTId5013.ANS_CODE_ERROR ;
			errDetails = RFTId5013.ErrorDetails.INTERNAL_ERROR;
		}


		//#CM701216
		// Response telegram making
		//#CM701217
		// STX
		rftId5013.setSTX();
		//#CM701218
		// SEQ
		rftId5013.setSEQ(0);
		//#CM701219
		// ID
		rftId5013.setID(RFTId5013.ID);
		//#CM701220
		// RFT transmission time
		rftId5013.setRftSendDate(rftId0013.getRftSendDate());
		//#CM701221
		// SERVER transmission time
		rftId5013.setServSendDate();
		//#CM701222
		// RFT machine
		rftId5013.setRftNo(rftNo);

		//#CM701223
		// Person in charge code
		rftId5013.setWorkerCode(workerCode);
		
		//#CM701224
		// Consignor Code
		rftId5013.setConsignorCode(consignorCode);
		
		//#CM701225
		// Set Consignor Name
		rftId5013.setConsignorName(consignorName);
		
		//#CM701226
		// Set Area No.
		rftId5013.setAreaNo(areaNo);

		//#CM701227
		// Set Location No.
		rftId5013.setLocationNo(locationNo);
		
		//#CM701228
		// Set Item identification Code
		rftId5013.setItemId(itemId);
		
		//#CM701229
		// JANCode
		rftId5013.setJANCode(janCode);
		
		//#CM701230
		// Set Bundle ITF
		rftId5013.setBundleITF(bundleItf);
		
		//#CM701231
		// Set Case ITF
		rftId5013.setITF(caseItf);
		
		//#CM701232
		// Set Item Name
		rftId5013.setItemName(itemName);
		
		//#CM701233
		// Set Packed qty per bundle
		rftId5013.setBundleEnteringQty(bundleEnteringQty);
		
		//#CM701234
		// Set Packed qty per case
		rftId5013.setEnteringQty(enteringQty);

		//#CM701235
		// Set Expiry date
		rftId5013.setUseByDate(useByDate);

		//#CM701236
		// Set Mode of packing
		rftId5013.setItemForm(itemForm);
		
		//#CM701237
		// Movement rack existence flag
		rftId5013.setMobileRackFlag(mobileRackFlag);
		
		//#CM701238
		// Response flag
		rftId5013.setAnsCode(ansCode);
		
		//#CM701239
		//  Detailed Error
		rftId5013.setErrDetails(errDetails);
		
		//#CM701240
		// ETX
		rftId5013.setETX();

		//#CM701241
		// Acquire response telegram. 
		rftId5013.getSendMessage(sdt) ;

	}

	//#CM701242
	// Package methods -----------------------------------------------

	//#CM701243
	// Protected methods ---------------------------------------------

	//#CM701244
	// Private methods -----------------------------------------------

}
//#CM701245
//end of class
