// $Id: Id0830Process.java,v 1.2 2006/12/07 09:00:08 suresh Exp $
package jp.co.daifuku.wms.storage.rft;

import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ShelfInvalidityException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM576063
/**
 * Designer : Y.Taki<BR>
 * Maker : E.Takeda<BR>
 * <BR>
 * This processes simple Inventory item inquiry request from RFT<BR>
 * Fetch stock data from stock info using Id0830Operate class functions<BR>
 * Check whether inventory in process work exist by searching inventory info<BR>
 * It creates the response id to be send to RFT based on search data<BR>
 * <BR>
 * inventory item inquiry request process (<CODE>processReceivedId</CODE> method)<BR>
 * <BR>
 * <DIR>
 *  Fetch the required info from received data<BR>
 *  Check whether stock package exist or not from system info<BR>
 *  If stock package exist, search stock info using Id0830Operate class functions and fetch stock data<BR>
 *  And search inventory info and fetch inventory data<BR>
 *  Set the response flag based on stock, inventory data availability<BR>
 *  Create telegraph send data and set the text values<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/19</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:08 $
 * @author  $Author: suresh $
 */
public class Id0830Process extends IdProcess
{
	//#CM576064
	// Class fields----------------------------------------------------
	//#CM576065
	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "Id0830Process";

	//#CM576066
	// Class variables -----------------------------------------------

	//#CM576067
	// Class method --------------------------------------------------
	//#CM576068
	/**
	 * Return the version of this class
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:08 $");
	}

	//#CM576069
	// Constructors --------------------------------------------------
	//#CM576070
	// Public methods ------------------------------------------------
	//#CM576071
	/**
	 * Inventory item inquiry request process (ID0830)<BR>
	 * Based on the received telegraph data as a byte array, create (ID5830) byte string<BR>
	 * <BR>
	 * Ensure that daily update is not in process
	 * In daily update is in progress, return error 5 : Daily update in progress<BR>
	 * Confirm whether worker result record exist or not.If not create new<BR>
	 * <BR>
	 * Using WareNaviSystem class, check whether stock package exist or not<BR>
	 * If stock package exist, using ID0830Operate class, search for the request conditions in stock info<BR>
	 * If target dta exist, set it to send telegraph data with stock flag = 1: [stock data exists.] (Fetch stock data by grouping)<BR>
	 * If stock does not exist, set status flag = 2: [stock does not exist]<BR>
	 * If stock package does not exist set status flag = 0: stock package does not exist.<BR>
	 * <BR>
	 * Use ID0830Operate class and search inventory info for the request conditions<BR>
	 * If there is no inventory data, set Response flag = 0 [New inventory]<BR>
	 * If there is inventory data, set Response flag = 2 [Inventory exist]<BR>
	 * <BR>
	 * Create telegraph send data and set the text values<BR>
	 * <BR>
	 * @param  rdt  Receive buffer
	 * @param  sdt  Send buffer
	 * @throws Exception Throw all exceptions
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		RFTId0830 rftId0830 = null;
		RFTId5830 rftId5830 = null;
		try
		{
			//#CM576072
			// Create instance to receive telegraph data
			rftId0830 = (RFTId0830) PackageManager.getObject("RFTId0830");
			rftId0830.setReceiveMessage(rdt);

			//#CM576073
			// Create instance to send telegraph data
			rftId5830 = (RFTId5830) PackageManager.getObject("RFTId5830");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}

		//#CM576074
		// Fetch RFT no. from telegraph data
		String rftNo = rftId0830.getRftNo();
		//#CM576075
		// Fetch worker code from telegraph data
		String workerCode = rftId0830.getWorkerCode();
		//#CM576076
		// Fetch consignor code from telegraph data
		String consignorCode = rftId0830.getConsignorCode();
		//#CM576077
		// Fetch Area no. from telegraph data
		String areaNo = rftId0830.getAreaNo();
		//#CM576078
		// Fetch Inventory location from telegraph data
		String inventoryLocation = rftId0830.getInventryLocation();
		//#CM576079
		// Fetch Scan Item code1 from telegraph data
		String scanCode1 = rftId0830.getScanCode1();
		//#CM576080
		// Fetch storage item code 2 from telegraph data
		String scanCode2 = rftId0830.getScanCode2();
		//#CM576081
		// Variable to store consignor name
		String consignorName = "";
		//#CM576082
		// Variable to store JAN code
		String JANCode = "";
		//#CM576083
		// Variable to store Product identification code
		String itemId = "";
		//#CM576084
		// Variable to store Item code
		String itemCode = "";
		//#CM576085
		// Variable for Bundle ITF
		String bundleITF = "";
		//#CM576086
		// Variable for Case ITF
		String ITF = "";
		//#CM576087
		// Variable for Item name
		String itemName = "";
		//#CM576088
		// Variable for qty per bundle
		int bundleEnteringQty = 0;
		//#CM576089
		// Variable for qty per case
		int enteringQty = 0;
		//#CM576090
		// Variable to store stock qty
		int stockQty = 0;
		//#CM576091
		// Variable to store inventory qty
		int inventoryCheckQty = 0;
		//#CM576092
		// Variable to store case piece flag
		String casePieceFlag = SystemDefine.CASEPIECE_FLAG_NOTHING ;
		//#CM576093
		// Variable for expiry date
		String useByDate = "";
		//#CM576094
		// Variable to store stock flag
		String stockFlag = "";
		//#CM576095
		// Variable for response flag
		String ansCode = "";
		//#CM576096
		// Variable for error details
		String errDetails = RFTId5830.ErrorDetails.NORMAL;
		try
		{
			if(DisplayText.isPatternMatching(areaNo))
			{
				throw new NotFoundException(RFTId5830.ANS_CODE_ERROR);
			}
			if(DisplayText.isPatternMatching(inventoryLocation))
			{
				throw new NotFoundException(RFTId5830.ANS_CODE_ERROR);
			}
			if(DisplayText.isPatternMatching(scanCode1))
			{
				throw new NotFoundException(RFTId5830.ANS_CODE_ERROR);
			}
			if(DisplayText.isPatternMatching(scanCode2))
			{
				throw new NotFoundException(RFTId5830.ANS_CODE_ERROR);
			}

			//#CM576097
			// Create BaseOperate instance
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);

			//#CM576098
			//-----------------
			//#CM576099
			// Daily update process check
			//#CM576100
			//-----------------
			if (baseOperate.isLoadingDailyUpdate())
			{
				//#CM576101
				// Set response flag = 5 [Daily update in process] and return
				ansCode = RFTId5830.ANS_CODE_DAILY_UPDATING;
			}
			else
			{
				//#CM576102
				//-----------------
				//#CM576103
				// Check if exist in worker result
				//#CM576104
				//-----------------
				WorkerResult[] workerResult =
					baseOperate.getWorkerResult(
						WorkingInformation.JOB_TYPE_INVENTORY,
						workerCode,
						rftNo);
				if (workerResult.length == 0)
				{
					//#CM576105
					// Create worker result
					baseOperate.createWorkerResult(
						WorkingInformation.JOB_TYPE_INVENTORY,
						workerCode,
						rftNo);
					//#CM576106
					// commit
					wConn.commit();
				}

				//#CM576107
				// Create Id0830Operate instance
				Id0830Operate id0830Operate = (Id0830Operate)PackageManager.getObject("Id0830Operate");
				id0830Operate.setConnection(wConn);

				//#CM576108
				//-----------------
				//#CM576109
				// Search stock info
				//#CM576110
				//-----------------
				Stock stock = null;

				//#CM576111
				// Create WareNaviSystemHandler instance
				WareNaviSystemHandler wareNaviSystemHandler = new WareNaviSystemHandler(wConn);

				//#CM576112
				//	Search stock control package
				WareNaviSystem[] wareNaviSystem =
					(WareNaviSystem[]) wareNaviSystemHandler.find(new WareNaviSystemSearchKey());

				//#CM576113
				//If stock control package does not exist
				if (wareNaviSystem[0].getStockPack().equals("0"))
				{
					JANCode = scanCode1;
					stockFlag = RFTId5830.STOCK_FLAG_STOCK_CONTROL_OFF;
					stock = null;
				}
				//#CM576114
				//If stock control package exist
				else
				{
					//#CM576115
					//Search stock info and fetch
					stock =	id0830Operate.getStockDataOfInventoryCheck(
							consignorCode,
							areaNo,
							inventoryLocation,
							scanCode1,
							scanCode2);
					//#CM576116
					// If stock data does not exist
					if (stock == null)
					{
						stockFlag = RFTId5830.STOCK_FLAG_STOCK_NULL;
						//#CM576117
						// If same item exist in another location, set that item info. (Consignor name, Item name, Qty per case, etc.,)
						Stock[] stockList = id0830Operate.getStockDataList(consignorCode, scanCode1, scanCode2);
						if (stockList.length > 0)
						{
							consignorName = stockList[0].getConsignorName();
							JANCode = stockList[0].getItemCode();
							bundleITF = stockList[0].getBundleItf();
							ITF = stockList[0].getItf();
							itemName = stockList[0].getItemName1();
							bundleEnteringQty = stockList[0].getBundleEnteringQty();
							enteringQty = stockList[0].getEnteringQty();
						}
						else
						{
							//#CM576118
							// Put Scan code 1 in JAN code
							JANCode = scanCode1;
						}
					}
					//#CM576119
					// If stock data exist
					else
					{
						stockFlag = RFTId5830.STOCK_FLAG_STOCK_ON;
						//#CM576120
						// Fetch possible stock data and set to variable
						consignorName = stock.getConsignorName();
						JANCode = stock.getItemCode();
						bundleITF = stock.getBundleItf();
						ITF = stock.getItf();
						itemName = stock.getItemName1();
						bundleEnteringQty = stock.getBundleEnteringQty();
						enteringQty = stock.getEnteringQty();
						stockQty = stock.getStockQty();
						useByDate = stock.getUseByDate();
						itemId = stock.getStockId();
					
					}
				}

				//#CM576121
				//-----------------
				//#CM576122
				// Search inventory info
				//#CM576123
				//-----------------
				InventoryCheck inventoryCheck =
					id0830Operate.getInventoryCheckData(
						consignorCode,
						areaNo,
						inventoryLocation,
						JANCode);

				
				//#CM576124
				//If inventory data does not exist
				if (inventoryCheck == null)
				{
					ansCode = RFTId5830.ANS_CODE_NORMAL;
				}
				//#CM576125
				// If there is only one inventory data (check in case of multiple items)
				else
				{
					//#CM576126
					// Fetch possible work from inventory data and set it to variable
					consignorName = inventoryCheck.getConsignorName();
					bundleITF = inventoryCheck.getBundleItf();
					ITF = inventoryCheck.getItf();
					itemName = inventoryCheck.getItemName1();
					bundleEnteringQty = inventoryCheck.getBundleEnteringQty();
					enteringQty = inventoryCheck.getEnteringQty();
					inventoryCheckQty = inventoryCheck.getResultStockQty();
					useByDate = inventoryCheck.getUseByDate();
					
					ansCode = RFTId5830.ANS_CODE_COMPLETION;
				}
			}
		}
		catch (NotFoundException e)
		{
			ansCode = e.getMessage();
			errDetails = RFTId5830.ErrorDetails.NULL;
			if (ansCode == null || ansCode.equals(""))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				ansCode = RFTId5830.ANS_CODE_ERROR;
				errDetails = RFTId5830.ErrorDetails.NULL;
			}
		}

		//#CM576127
		// If overflow occurs
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" InventoryLocation:" + inventoryLocation +
							" ItemCode:" + JANCode +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			//#CM576128
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
	
			ansCode = RFTId5830.ANS_CODE_ERROR;
			//#CM576129
			// Group by overflow
			errDetails = RFTId5830.ErrorDetails.COLLECTION_OVERFLOW;
		}
		catch (IllegalAccessException e) {
        	//#CM576130
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
	
			//#CM576131
			// Response flag : Error
			ansCode = RFTId5830.ANS_CODE_ERROR;
			errDetails = RFTId5830.ErrorDetails.INSTACIATE_ERROR;
		}
		//#CM576132
		// Other error occurred
		catch (ReadWriteException e)
		{
			//#CM576133
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
	
			//#CM576134
			// Response flag : Error
			ansCode = RFTId5830.ANS_CODE_ERROR;
			errDetails = RFTId5830.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (DataExistsException e)
		{
			//#CM576135
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
			//#CM576136
			// Response flag : Error
			ansCode = RFTId5830.ANS_CODE_ERROR;
			errDetails = RFTId5830.ErrorDetails.DB_UNIQUE_KEY_ERROR;

		}
		catch (SQLException e)
		{
			//#CM576137
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
			//#CM576138
			// Response flag : Error
			ansCode = RFTId5830.ANS_CODE_ERROR;
			errDetails = RFTId5830.ErrorDetails.DB_ACCESS_ERROR;

		}
		catch (ScheduleException e)
		{
			//#CM576139
			// 6006001=Unexpected error occurred.{0}
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM576140
			// Response flag : Error
			ansCode = RFTId5830.ANS_CODE_ERROR;
			errDetails = RFTId5830.ErrorDetails.SCHEDULE_ERROR;

		}
		catch (ShelfInvalidityException e)
		{

		    String errData = " [LocationNo:" + inventoryLocation +
		    				 " RftNo:" + rftNo +
							 " WorkerCode:" + workerCode + "]";
		    
			//#CM576141
			// 6022039=The specified location is in automatic warehouse. Cannot enter. {0}
			RftLogMessage.print(6022039, LogMessage.F_ERROR, CLASS_NAME, errData);

			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM576142
			// Response flag : Error
			ansCode = RFTId5830.ANS_CODE_ERROR;
			errDetails = RFTId5830.ErrorDetails.SHELF_INVALIDITY;

		}
		catch (Exception e)
		{
			//#CM576143
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
			//#CM576144
			// Response flag : Error
			ansCode = RFTId5830.ANS_CODE_ERROR;
			errDetails = RFTId5830.ErrorDetails.INTERNAL_ERROR;
		}


		//#CM576145
		// Create response id
		//#CM576146
		// STX
		rftId5830.setSTX();
		//#CM576147
		// SEQ
		rftId5830.setSEQ(0);
		//#CM576148
		// ID
		rftId5830.setID(RFTId5830.ID);
		//#CM576149
		// RFT send time
		rftId5830.setRftSendDate(rftId0830.getRftSendDate());
		//#CM576150
		// Server send time
		rftId5830.setServSendDate();
		//#CM576151
		// RFT no.
		rftId5830.setRftNo(rftNo);
		//#CM576152
		// Worker code
		rftId5830.setWorkerCode(workerCode);
		//#CM576153
		// Consignor code
		rftId5830.setConsignorCode(consignorCode);
		//#CM576154
		// Set Consignor code
		rftId5830.setConsignorName(consignorName);
		//#CM576155
		// Set Area no.
		rftId5830.setAreaNo(areaNo);
		//#CM576156
		// Inventory location
		rftId5830.setInventoryLocation(inventoryLocation);
		//#CM576157
		// Product identification code
		rftId5830.setItemId(itemId);
		//#CM576158
		// Set Item code
		rftId5830.setItemCode(itemCode);
		//#CM576159
		// JAN code
		rftId5830.setJANCode(JANCode);
		//#CM576160
		// Set Bundle ITF
		rftId5830.setBundleITF(bundleITF);
		//#CM576161
		// Set Case ITF
		rftId5830.setITF(ITF);
		//#CM576162
		// Set Item Name
		rftId5830.setItemName(itemName);
		//#CM576163
		// Set packed qty per bundle
		rftId5830.setBundleEnteringQty(bundleEnteringQty);
		//#CM576164
		// Set packed qty per case
		rftId5830.setEnteringQty(enteringQty);
		//#CM576165
		// Stock qty
		rftId5830.setStockQty(stockQty);
		//#CM576166
		// Inventory qty
		rftId5830.setInventoryCheckQty(inventoryCheckQty);
		//#CM576167
		// Load (Case piece) type
		rftId5830.setItemForm(casePieceFlag) ;
		//#CM576168
		// Expiry date
		rftId5830.setUseByDate(useByDate);
		//#CM576169
		// Stock flag
		rftId5830.setStockFlag(stockFlag);
		//#CM576170
		// Response flag
		rftId5830.setAnsCode(ansCode);
		//#CM576171
		// Error details
		rftId5830.setErrDetails(errDetails);
		//#CM576172
		// ETX
		rftId5830.setETX();

		//#CM576173
		// Acquire response id
		rftId5830.getSendMessage(sdt);

	}

	//#CM576174
	// Package methods -----------------------------------------------

	//#CM576175
	// Protected methods ---------------------------------------------

	//#CM576176
	// Private methods -----------------------------------------------

}
//#CM576177
//end of class
