// $Id: Id0831Process.java,v 1.2 2006/12/07 09:00:07 suresh Exp $
package jp.co.daifuku.wms.storage.rft;

//#CM576280
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM576281
/**
 * This is the inventory check result send process from RFT<BR>
 * Using Id0831Operate class functions, create new inventory work info<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/19</TD><TD>E.Takeda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:07 $
 * @author  $Author: suresh $
 */

public class Id0831Process extends IdProcess
{
	//#CM576282
	// Class fields----------------------------------------------------
	//#CM576283
	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "Id0831Process";

	//#CM576284
	//Class variables -----------------------------------------------
	
	//#CM576285
	//Class methods -------------------------------------------------
	
	//#CM576286
	/**
	 * Return the version of this class
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/12/07 09:00:07 $";
	}
	
	//#CM576287
	//Constructors --------------------------------------------------
	//#CM576288
	//Public methods ------------------------------------------------
	
	//#CM576289
	/**
	 * This is the Inventory result send (ID0831) process<BR>
	 * Fetch receive telegraph data as a byte array and create (ID5831) byte string<BR>
	 * @param  rdt  Receive buffer
	 * @param  sdt  Send buffer
	 * @throws Exception Throw all exceptions
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM576290
		//Create receive telegraph instance
		RFTId0831 rftId0831 = null;
		
		//#CM576291
		//Create instance to send telegraph data
		RFTId5831 rftId5831 = null;
		
		try
		{
			//#CM576292
			// Create instance to receive telegraph data
			rftId0831 = (RFTId0831) PackageManager.getObject("RFTId0831");
			rftId0831.setReceiveMessage(rdt);

			//#CM576293
			// Create instance to send telegraph data
			rftId5831 = (RFTId5831) PackageManager.getObject("RFTId5831");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*831", e.getMessage());
			throw e;
		}

		//#CM576294
		//Variable to store response flag
		String ansCode = RFTId5831.ANS_CODE_NORMAL;
		
		//#CM576295
		//Variable to store error details
		String errDetails = RFTId5831.ErrorDetails.NORMAL;
		
		//#CM576296
		//Object to store Inventory result data
		InventoryCheck resultData = new InventoryCheck();
		
		//#CM576297
		//Use RFT0830 and fetch Worker code
		resultData.setWorkerCode(rftId0831.getWorkerCode());
		
		//#CM576298
		//Use RFT0830 and fetch RFT No.
		resultData.setTerminalNo(rftId0831.getRftNo());
		
		//#CM576299
		//Use RFT0830 and fetch Consignor code
		resultData.setConsignorCode(rftId0831.getConsignorCode());
		
		//#CM576300
		//Use RFT0830 and fetch Consignor name
		resultData.setConsignorName(rftId0831.getConsignorName());
		
		//#CM576301
		//Use RFT0830 and fetch Area no.
		resultData.setAreaNo(rftId0831.getAreaNo());
		
		//#CM576302
		//Use RFT0830 and fetch Inventory location
		resultData.setLocationNo(rftId0831.getInventoryLocation());
		
		//#CM576303
		//Use RFT0830 and fetch JAN code
		resultData.setItemCode(rftId0831.getJANCode());
		
		//#CM576304
		//Use RFT0830 and fetch Bundle ITF
		resultData.setBundleItf(rftId0831.getBundleITF());
		
		//#CM576305
		//Use RFT0830 and fetch Case ITF
		resultData.setItf(rftId0831.getITF());
		
		//#CM576306
		//Use RFT0830 and fetch Item name
		resultData.setItemName1(rftId0831.getItemName());
		
		//#CM576307
		//Use RFT0830 and fetch load
		resultData.setCasePieceFlag(rftId0831.getItemForm().trim());
		
		//#CM576308
		//Use RFT0830 and fetch Expiry date
		resultData.setUseByDate(rftId0831.getUseByDate().trim());
		
		//#CM576309
		//Use RFT0830 and fetch create type
		String enableCreateNewStock = rftId0831.getEnableCreateNewStock();
			
		//#CM576310
		//Use RFT0830 and fetch completion flag
		String completionFlag = rftId0831.getCompletionFlag().trim();
		
		try
		{
			if(DisplayText.isPatternMatching(rftId0831.getConsignorCode()))
			{
				throw new InvalidDefineException("CONSIGNOR_CODE[" + rftId0831.getConsignorCode() +"]");
			}
			if(DisplayText.isPatternMatching(rftId0831.getInventoryLocation()))
			{
				throw new InvalidDefineException("LOCATION_NO[" + rftId0831.getInventoryLocation() +"]");
			}
			if(DisplayText.isPatternMatching(rftId0831.getJANCode()))
			{
				throw new InvalidDefineException("JAN_CODE[" + rftId0831.getJANCode() +"]");
			}
			if(DisplayText.isPatternMatching(resultData.getUseByDate()))
			{
				throw new InvalidDefineException("USE_BY_DATE[" + resultData.getUseByDate() +"]");
			}

			//#CM576311
			//Use RFT0830 and fetch Qty per bundle
			int bundleEnteringQty = rftId0831.getBundleEnteringQty();
			resultData.setBundleEnteringQty(bundleEnteringQty);
			
			//#CM576312
			//Use RFT0830 and fetch Qty per case
			int enteringQty = rftId0831.getEnteringQty();
			resultData.setEnteringQty(enteringQty);
			
			//#CM576313
			//Use RFT0830 and fetch Stock qty
			int stockQty = rftId0831.getStockQty();
			resultData.setStockQty(stockQty);
			
			//#CM576314
			//Use RFT0830 and fetch Inventory qty
			int originalInventoryQty = rftId0831.getOriginalInventoryQty();
			
			//#CM576315
			//Use RFT0830 and fetch Inventory result qty
			int inventoryResultQty = rftId0831.getInventoryResultQty();
			resultData.setResultStockQty(inventoryResultQty);
			
			//#CM576316
			//Use RFT0830 and fetch work time
			int workTime = rftId0831.getWorkSeconds();
			
			//#CM576317
			// Create Id0831Operate instance
			Id0831Operate id0831Operate =
			    (Id0831Operate) PackageManager.getObject("Id0831Operate");
			id0831Operate.setConnection(wConn);

			ansCode = id0831Operate.doComplete(resultData,
			        							originalInventoryQty,
			        							completionFlag,
			        							enableCreateNewStock,
			        							workTime);
			errDetails = id0831Operate.getErrDetails();
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "Id0831Operate", e.getMessage());

			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			ansCode = RFTId5831.ANS_CODE_ERROR;
			errDetails = RFTId5831.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (NumberFormatException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);

			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			errDetails = RFTId5831.ErrorDetails.PARAMETER_ERROR;
			ansCode = RFTId5831.ANS_CODE_ERROR;
		}
		catch (InvalidDefineException e)
		{
			//#CM576318
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
			//#CM576319
			// Response flag : Error
			ansCode = RFTId5831.ANS_CODE_ERROR;
			errDetails =RFTId5831.ErrorDetails.PARAMETER_ERROR; 
		}

		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR, CLASS_NAME, e);

			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			
			//#CM576320
			// Response flag : Error
			errDetails = RFTId5831.ErrorDetails.INTERNAL_ERROR;
			ansCode = RFTId5831.ANS_CODE_ERROR;
		}
		
		//#CM576321
		//Create send telegraph data using RFTId1094 instance
		//#CM576322
		//STX
		rftId5831.setSTX();

		//#CM576323
		//SEQ
		rftId5831.setSEQ(0);

		//#CM576324
		//ID
		rftId5831.setID(RFTId5831.ID);

		//#CM576325
		//Handy send time
		rftId5831.setRftSendDate(rftId0831.getRftSendDate());

		//#CM576326
		//Server send time
		rftId5831.setServSendDate();

		//#CM576327
		//RFT no.
		rftId5831.setRftNo(resultData.getTerminalNo());
		
		//#CM576328
		//Worker code
		rftId5831.setWorkerCode(resultData.getWorkerCode());
		
		//#CM576329
		//Response flag
		rftId5831.setAnsCode(ansCode);
		
		//#CM576330
		// Error details
		rftId5831.setErrDetails(errDetails);    
		
		//#CM576331
		//ETX
		rftId5831.setETX();
		
		//#CM576332
		//Acquire telegraph data
		rftId5831.getSendMessage(sdt);
		
	}
	
	//#CM576333
	//Package methods -----------------------------------------------

	//#CM576334
	//Protected methods ---------------------------------------------
	
	//#CM576335
	//Private methods -----------------------------------------------
	
}
//#CM576336
//end of class
