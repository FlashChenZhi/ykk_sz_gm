// $Id: Id0831Operate.java,v 1.2 2006/12/07 09:00:08 suresh Exp $
package jp.co.daifuku.wms.storage.rft;
import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ShelfInvalidityException;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.storage.schedule.StockAllocateOperator;
//#CM576178
/**
 * Designer : Y.Taki <BR>
 * Maker : E.Takeda  <BR>
 * <BR>
 * This is the inventory result data send process from RFT<BR>
 * There are three types of process like new, overwrite, add<BR>
 * Execite the business logic from Id0831Process<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/19</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:08 $
 * @author  $Author: suresh $
 */
public class Id0831Operate extends IdOperate
{
	//#CM576179
	// Class variables -----------------------------------------------
	//#CM576180
	/**
	 * Process name (Registering process name,Last update Process name)
	 */
	protected static final String PROCESS_NAME = "ID0831";

	//#CM576181
	/**
	 * Work start Process name (Registering process name, Last update Process name use)
	 */
	protected static final String START_PROCESS_NAME = "ID0830";

	//#CM576182
	/**
	 * Class name (Log output use)
	 */
	protected static final String CLASS_NAME = "Id0831Operate";
	
	//#CM576183
	/**
	 * Stock control package check flag
	 */
	protected boolean withStockManagement = true;
	
	//#CM576184
	/**
	 * Error details
	 */
	private String errDetails = RFTId5831.ErrorDetails.NORMAL;

	//#CM576185
	// Class method --------------------------------------------------
	//#CM576186
	/**
	 * Return the version of this class
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/12/07 09:00:08 $";
	}

	//#CM576187
	// Constructors --------------------------------------------------
	//#CM576188
	// Public methods ------------------------------------------------
	//#CM576189
	/**
	 * Inventory result data [Commit process]<BR>
	 * Based on completion flag, add or update inventory info<BR>
	 * <OL>
	 *   <LI>Check whether date/time Update process or not</LI>
	 *   <LI>If stock control package exist, check whether inventory target stock exist or not</LI>
	 *   <LI>Based on completion flag, execute the following methods
	 *     <UL>
	 *       <LI>Inventory info  new record process (<CODE>createInventoryData</CODE> method)</LI>
	 *       <LI>Update process if inventory info already exist (<CODE>updateInventoryData</CODE> method)</LI>
	 *       <LI>Add process if inventory info already exist (<CODE>addInventoryQty</CODE> method)</LI>
	 *     </UL></LI>
	 * </OL>
	 * Once Inventory Commit process is complete, create worker result<BR>
	 * @param	resultData		Inventory info
	 * @param	originalInventoryQty	Original inventory qty
	 * @param	completionFlag		completion flag (0:New 1:Overwrite 2:Add)
	 * @param	enableCreateNewStock	New stock create flag (0: disable create 1: enable create)
	 * @param	workTime		work time
	 * @return	Response id             Response flag
	*/
	public String doComplete(
		InventoryCheck resultData,
		int originalInventoryQty,
		String completionFlag,
		String enableCreateNewStock,
		int workTime)
	{
	    String ansCode = "";

	    try
		{
			//#CM576190
			// Check whether date/time process or not
			//#CM576191
			// Create BaseOperate instance
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);
			
			//#CM576192
			// Check whether daily update process or not
			if (baseOperate.isLoadingDailyUpdate())
			{
				//#CM576193
				// Return status flag 5 : Daily update
				return RFTId5831.ANS_CODE_DAILY_UPDATING;
			}

			//#CM576194
			// Decide whether stock control exist or not
			withStockManagement = isWithStockManagement();
			
			//#CM576195
			// If stock control package exist, check whether inventory target stock exist or not
			StockAllocateOperator sao = null;
			Stock[] stock = null;
			if (withStockManagement)
			{
				sao = new StockAllocateOperator();
				try
				{
					stock = sao.stockSearch(
					        wConn,
					        resultData.getConsignorCode(),
					        resultData.getAreaNo(),
					        resultData.getLocationNo(),
					        resultData.getItemCode(),
					        resultData.getUseByDate());
				}
				catch (InvalidDefineException e)
				{
					//#CM576196
					// 6026022=Blank or prohibited character is included in the specified value. {0}
					RftLogMessage.print(6026022, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
					errDetails = RFTId5831.ErrorDetails.PARAMETER_ERROR;
					//#CM576197
					// Response flag : Error
					
					return RFTId5831.ANS_CODE_ERROR;	
				}
			}

			//#CM576198
			// If new stock create flag is 0 and stock does not exist
			//#CM576199
			// Return that no stock data exist
			if (withStockManagement
			        && enableCreateNewStock.equals("0")
			        && stock.length == 0 )
			{
			    return RFTId5831.ANS_CODE_NULL;
			}
			
			//#CM576200
			// If target stock exist, the inventory check stock qty
			//#CM576201
			// is set to inventory check result qty
			if (stock != null && stock.length > 0)
			{
			    int stockQty = 0;
			    for (int i = 0; i < stock.length; i ++)
			    {
			        stockQty += stock[i].getStockQty();
			    }
			    resultData.setStockQty(stockQty);
			}
			else
			{
			    //#CM576202
			    // If target stock does not exist, set 0 to stock qty
			    resultData.setStockQty(0);
			}
			
			//#CM576203
			// Fetch worker name
			BaseOperate bo = new BaseOperate(wConn);
			try
			{
				resultData.setWorkerName(bo.getWorkerName(resultData.getWorkerCode()));
			}
			catch (NotFoundException e)
			{
				//#CM576204
				// Worker start with no worker code (worker master data is not available until data request)
				//#CM576205
				// 6026019=No matching worker data was found in the Worker Master Table. Worker Code: {0}
				RftLogMessage.print(6026019, LogMessage.F_ERROR, CLASS_NAME, resultData.getWorkerCode());
				try
				{
					wConn.rollback();
				}
				catch (SQLException sqlex)
				{
					RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
				}
				//#CM576206
				// Response flag : Error
				errDetails = RFTId5831.ErrorDetails.NULL;
				return RFTId5831.ANS_CODE_ERROR;
			}
			
			//#CM576207
			// If completion flag is 0: New
			if (completionFlag.equals(RFTId0831.COMPLETION_FLAG_NEW))
			{
				//#CM576208
				// Using createInventoryData method, add new record
				ansCode = createInventoryData(resultData);
			}
			//#CM576209
			// If completion flag is 1 : overwrite
			else if (completionFlag.equals(RFTId0831.COMPLETION_FLAG_UPDATE))
			{
				//#CM576210
				// Using updateInventoryData, overwrite data
				ansCode = updateInventoryData(resultData);
			}
			//#CM576211
			// If completion flag is 2 : add
			else if (completionFlag.equals(RFTId0831.COMPLETION_FLAG_ADD))
			{
				//#CM576212
				// Using addInventoryQty method, add inventory qty
				ansCode = addInventoryQty(resultData, originalInventoryQty);
			}
			//#CM576213
			//if other than above
			else
			{
				//#CM576214
				// 6026015=Error occurred during ID process. {0}
			    String msg = "CompletionFlag = " + completionFlag;
				RftLogMessage.print(6026015, LogMessage.F_ERROR, CLASS_NAME, msg);
				errDetails = RFTId5831.ErrorDetails.PARAMETER_ERROR;
				//#CM576215
				// Error
				ansCode = RFTId5831.ANS_CODE_ERROR;
			}

			if (ansCode.equals(RFTId5831.ANS_CODE_NORMAL))
			{
				//#CM576216
				// If the process completes normally, create worker result
				updateWorkerResult(resultData.getWorkerCode(),
				        resultData.getTerminalNo(),
				        resultData.getResultStockQty(),
				        workTime);
				
				wConn.commit();
			}
			else
			{
			    wConn.rollback();
			}
		}
		//#CM576217
		// If the info is not found during search
		catch (NotFoundException e)
		{
			String errData = "[ConsignorCode:" + resultData.getConsignorCode() +
							" ItemCode:" + resultData.getItemCode() +
							" Location:" + resultData.getLocationNo() +
							" RftNo:" + resultData.getTerminalNo() +
							" WorkerCode:" + resultData.getWorkerCode() +"]";
			//#CM576218
			// 6026016=No data you try to update is found. {0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			//#CM576219
			// Response flag : Error
			errDetails = RFTId5831.ErrorDetails.NULL;
			return RFTId5831.ANS_CODE_ERROR;
		}
		//#CM576220
		// If the data is already updated in another terminal
		catch (UpdateByOtherTerminalException e)
		{
			String errData = "[ConsignorCode:" + resultData.getConsignorCode() +
							" ItemCode:" + resultData.getItemCode() +
							" Location:" + resultData.getLocationNo() +
							" RftNo:" + resultData.getTerminalNo() +
							" WorkerCode:" + resultData.getWorkerCode() +"]";
			//#CM576221
			// 6026017=Cannot update. The data you try to update was updated in other process. {0}
			RftLogMessage.print(6026017, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			//#CM576222
			// Response flag : Error
			errDetails = RFTId5831.ErrorDetails.UPDATE_FINISH;
			return RFTId5831.ANS_CODE_ERROR;
			
		}
		//#CM576223
		// If overflow error occurs during Inventory info
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + resultData.getConsignorCode() +
							" ItemCode:" + resultData.getItemCode() +
							" Location:" + resultData.getLocationNo() +
							" RftNo:" + resultData.getTerminalNo() +
							" WorkerCode:" + resultData.getWorkerCode() +"]";
			//#CM576224
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
			//#CM576225
			// Response flag : Error
			errDetails = RFTId5831.ErrorDetails.OVERFLOW;
			return RFTId5831.ANS_CODE_ERROR;
		}
		//#CM576226
		// Since there is a data access error
		catch (ReadWriteException e)
		{
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			//#CM576227
			// Response flag : Error
			errDetails = RFTId5831.ErrorDetails.DB_ACCESS_ERROR;
			return RFTId5831.ANS_CODE_ERROR;
		}
		catch (SQLException e)
        {
			//#CM576228
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
			errDetails = RFTId5831.ErrorDetails.DB_ACCESS_ERROR;
			//#CM576229
			// Response flag : Error
			return RFTId5831.ANS_CODE_ERROR;
        }
		catch (InvalidDefineException e)
        {
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			errDetails = RFTId5831.ErrorDetails.PARAMETER_ERROR;
			//#CM576230
			// Response flag : Error
			return RFTId5831.ANS_CODE_ERROR;
        }
		catch (NoPrimaryException e)
        {
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			errDetails = RFTId5831.ErrorDetails.DB_UNIQUE_KEY_ERROR;
			//#CM576231
			// Response flag : Error
			return RFTId5831.ANS_CODE_ERROR;
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

			//#CM576232
			// Response flag : Error
			errDetails = RFTId5831.ErrorDetails.DB_INVALID_VALUE;
			return RFTId5831.ANS_CODE_ERROR;
        }
        catch (IllegalAccessException e)
        {
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "BaseOperate", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			errDetails = RFTId5831.ErrorDetails.INSTACIATE_ERROR;	
			//#CM576233
			// Response flag : Error
			return RFTId5831.ANS_CODE_ERROR;
        }
		catch (ShelfInvalidityException e)
		{

		    String errData = " [LocationNo:" + resultData.getLocationNo() +
		    				 " RftNo:" + resultData.getTerminalNo() +
							 " WorkerCode:" + resultData.getWorkerCode() + "]";
		    
			//#CM576234
			// 6022039=The specified location is in automatic warehouse. Cannot enter. {0}
			RftLogMessage.print(6022039, LogMessage.F_ERROR, CLASS_NAME, errData);

			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6023443, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM576235
			// Error details
			errDetails = RFTId5831.ErrorDetails.SHELF_INVALIDITY;
			return RFTId5831.ANS_CODE_ERROR;
		}
		//#CM576236
		// Other error occurred
		catch (Exception e)
		{
			//#CM576237
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
			errDetails = RFTId5831.ErrorDetails.INTERNAL_ERROR;
			//#CM576238
			// Response flag : Error
			return RFTId5831.ANS_CODE_ERROR ;
		}
		
		return ansCode;
	}
	
	//#CM576239
	// Package methods -----------------------------------------------

	//#CM576240
	// Protected methods ---------------------------------------------
	//#CM576241
	/**
	 * Update existing Inventory info<BR>
	 * If data does not exist, assume that the data is updated via another terminal<BR>
	 * If multiple target exist, then Error<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Inventory check no.</LI>
	 *    </UL>
	 *    (Sort order)
	 *    <UL>
	 *     <LI>Process flag</LI>
	 *    </UL>
	 *    (Update contents)
	 *    <TABLE border="1">
	 *      <TR><TD>Inventory result bar code</TD>	<TD>ID0831.JAN code</TD></TR>
	 *      <TR><TD>Stock qty</TD>				<TD>ID0831.Stock qty</TD></TR>
	 *      <TR><TD>Allocation qty</TD>				<TD>0</TD></TR>
	 *      <TR><TD>Work plan qty</TD>			<TD>0</TD></TR>
	 *      <TR><TD>Inventory result qty</TD>		<TD>ID0831.Inventory result qty</TD></TR>
	 *      <TR><TD>Case piece type</TD>	<TD>ID0831.Case piece type</TD></TR>
	 *      <TR><TD>Qty per case</TD>			<TD>ID0831.Qty per case, </TD></TR>
	 *      <TR><TD>Qty per bundle</TD>			<TD>ID0831.Qty per bundle</TD></TR>
	 *      <TR><TD>terminal no.</TD>				<TD>ID0831.Handy no.</TD></TR>
	 *      <TR><TD>Last update date/time</TD>		<TD>system date/time</TD></TR>
	 *      <TR><TD>Last update Process name</TD>		<TD>"ID0831"</TD></TR>
	 *    </TABLE>
	 *   Update data with process flag = standby<BR>
	 *   If it does not match, assume being updated via another terminal<BR>
	 * </DIR>
	 * @param	inventoryCheck	Inventory info
	 * @return	If normal response flag = normal
	 * @throws NotFoundException	If there is no data for update (Delete)
	 * @throws ReadWriteException	If abnormal error occurs in database connection
	 * @throws UpdateByOtherTerminalException If in process data is updated in another terminal
	 * @throws InvalidStatusException If status outside range is set
	 */
	protected String updateInventoryData(InventoryCheck inventoryCheck)
		throws NotFoundException, ReadWriteException, InvalidStatusException, UpdateByOtherTerminalException
	{
	    //#CM576242
	    // Search inventory info based on Consignor, Area no. Location, Item code
	    //#CM576243
	    //(Delete all stock even if the expiry date is different)
	    InventoryCheckSearchKey skey = new InventoryCheckSearchKey();
	    skey.setConsignorCode(inventoryCheck.getConsignorCode());
	    skey.setAreaNo(inventoryCheck.getAreaNo());
	    skey.setLocationNo(inventoryCheck.getLocationNo());
	    skey.setItemCode(inventoryCheck.getItemCode());
	    skey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
	    
	    InventoryCheckHandler ihandler = new InventoryCheckHandler(wConn);
	    ihandler.drop(skey);

	    createNewInventoryCheckData(inventoryCheck);
	    
	    return RFTId5831.ANS_CODE_NORMAL;
	}
	
	//#CM576244
	/**
	 * Create new Inventory info<BR>
	 * If the same inventory info already exist, throw error<BR>
	 * Search AS/RS location info based on location no.If target data exist (automated warehouse location instructed)
	 * Return error response (input location not available)<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Consignor code</LI>
	 *     <LI>Area no.</LI>
	 *     <LI>Location no.</LI>
	 *     <LI>Item code</LI>
	 *     <LI>Expiry date (If expiry date control package exist)</LI>
	 *     <LI>Process flag = Standby ("0")</LI>
	 *    </UL>
	 *   If data exist for this condition, assume that is updated via another terminal<BR>
	 *   Insert data, if there is no target data<BR>
	 * </DIR>
	 * @param	inventoryCheck	Inventory info
	 * @return	If normal return response flag = normal
	 * @throws UpdateByOtherTerminalException If in process data is updated in another terminal
	 * @throws NoPrimaryException If multiple data occurs while searching with unique key
	 * @throws InvalidStatusException If status outside range is set
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws ScheduleException If abnormal error occurs during inventory check result process
	 * @throws ShelfInvalidityException If the input location is not valid
	 */
	protected String createInventoryData(InventoryCheck inventoryCheck)
			throws UpdateByOtherTerminalException, NoPrimaryException, InvalidStatusException, ReadWriteException, ScheduleException, ShelfInvalidityException
	{
		
		LocateOperator lOperator = new LocateOperator(wConn);
		if(lOperator.isAsrsLocation(inventoryCheck.getLocationNo()))
		{
			//#CM576245
			// 6023443=No.{0} The specified location is in automatic warehouse. Cannot enter.
			throw new ShelfInvalidityException();
		}
		
	    //#CM576246
	    // Search Inventory info based on Consignor, Area no., Location, Item code, Expiry date
	    InventoryCheck target = getInventoryCheckData(inventoryCheck, false);

	    //#CM576247
	    // If data with same inventory check no. exist, assume being updated via another terminal and throw error
	    if (target != null)
	    {
	        throw new UpdateByOtherTerminalException();
	    }
 
	    createNewInventoryCheckData(inventoryCheck);

	    return RFTId5831.ANS_CODE_NORMAL;
	}

	//#CM576248
	/**
	 * Add inventory qty to existing inventory<BR>
	 * If data does not exist, assume that the data is updated via another terminal<BR>
	 * If multiple target exist, then Error<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Inventory check no.</LI>
	 *    </UL>
	 *    (Sort order)
	 *    <UL>
	 *     <LI>Process flag</LI>
	 *    </UL>
	 *    (Update contents)
	 *    <TABLE border="1">
	 *      <TR><TD>Inventory result bar code</TD>	<TD>ID0831.JAN code</TD></TR>
	 *      <TR><TD>Stock qty</TD>				<TD>ID0831.Stock qty</TD></TR>
	 *      <TR><TD>Inventory result qty</TD>		<TD>(+) ID0831.Inventory result qty</TD></TR>
	 *      <TR><TD>Case piece type</TD>	<TD>ID0831.Case piece type</TD></TR>
	 *      <TR><TD>Qty per case</TD>			<TD>ID0831.Qty per case, </TD></TR>
	 *      <TR><TD>Qty per bundle</TD>			<TD>ID0831.Qty per bundle</TD></TR>
	 *      <TR><TD>terminal no.</TD>				<TD>ID0831.Handy no.</TD></TR>
	 *      <TR><TD>Last update date/time</TD>		<TD>system date/time</TD></TR>
	 *      <TR><TD>Last update Process name</TD>		<TD>"ID0831"</TD></TR>
	 *    </TABLE>
	 *   Process flag is Standby, terminal no. is current terminal, Last update Process name is "ID0831",
	 *   Inventory result qty = ID0831.Update original inventory qty<BR>
	 *   If it does not match, assume being updated via another terminal<BR>
	 * </DIR>
	 * @param	inventoryCheck	Inventory info
	 * @param	originalInventoryQty	Original inventory qty
	 * @return	If normal return response flag = normal
	 * @throws NotFoundException	If there is no data for update available
	 * @throws InvalidDefineException If the specified value is abnormal (empty, illegal characters)
	 * @throws NoPrimaryException If multiple data occurs while searching with unique key
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws InvalidStatusException If status outside range is set
	 * @throws UpdateByOtherTerminalException If data is updated via another terminal
	 * @throws OverflowException If the item count exceeds limit
	 */
	protected String addInventoryQty(InventoryCheck inventoryCheck, int originalInventoryQty)
		throws NotFoundException, InvalidDefineException, NoPrimaryException, ReadWriteException, InvalidStatusException, UpdateByOtherTerminalException, OverflowException
	{
	    //#CM576249
	    // Search Inventory info based on Consignor, Area no., Location, Item code, Expiry date
	    InventoryCheck target = getInventoryCheckData(inventoryCheck, false);
	    
	    int inventoryQty = getInventoryQty(
	            inventoryCheck.getConsignorCode(),
	            inventoryCheck.getAreaNo(),
	            inventoryCheck.getLocationNo(),
	            inventoryCheck.getItemCode());

	    //#CM576250
	    // Check original inventory qty of received data
	    if (inventoryQty != originalInventoryQty)
	    {
	        //#CM576251
	        // If inventory qty is updated from the work start itself, assume being updated via another terminal
	        throw new UpdateByOtherTerminalException();
	    }
	    
	    if (target == null)
	    {
	        //#CM576252
	        // If expiry date differs, create inventory info with newest expiry date
	        createNewInventoryCheckData(inventoryCheck);
	    }
	    else
	    {
		    //#CM576253
		    // Check the process flag of received data
		    if (! target.getStatusFlag().equals(InventoryCheck.STATUS_FLAG_NOTDECISION))
		    {
		        //#CM576254
		        // If not standby, assume that updated via another terminal
		        throw new UpdateByOtherTerminalException();
		    }

		    //#CM576255
		    // Set update value to Alter key
		    InventoryCheckAlterKey akey = new InventoryCheckAlterKey();
		    akey.setJobNo(target.getJobNo());
		    
		    akey.updateInvcheckBcr(inventoryCheck.getItemCode());
		    akey.updateStockQty(inventoryCheck.getStockQty());
		    long resultStockQty = target.getResultStockQty() + inventoryCheck.getResultStockQty();
			//#CM576256
			// Check for stock qty overflow
			if (resultStockQty > SystemParameter.MAXSTOCKQTY)
			{
				//#CM576257
				// 6026026=Cannot set. Inventory Check Qty. exceeds {0}.
				RftLogMessage.print(6026026, LogMessage.F_ERROR, CLASS_NAME, SystemParameter.DISPMAXSTOCKQTY);
				throw (new OverflowException());
			}

		    akey.updateResultStockQty((int)resultStockQty);
		    akey.updateCasePieceFlag(inventoryCheck.getCasePieceFlag());
		    akey.updateBundleEnteringQty(inventoryCheck.getBundleEnteringQty());
		    akey.updateEnteringQty(inventoryCheck.getEnteringQty());
		    akey.updateTerminalNo(inventoryCheck.getTerminalNo());
		    akey.updateLastUpdatePname(PROCESS_NAME);
		    
		    //#CM576258
		    // Update Inventory info
		    InventoryCheckHandler ihandler = new InventoryCheckHandler(wConn);
		    ihandler.modify(akey);
	    }
	    
	    return RFTId5831.ANS_CODE_NORMAL;
	}
	
	//#CM576259
	/**
	 * Create worker result<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Work date = WMSWork date</LI>
	 *     <LI>Worker code</LI>
	 *     <LI>RFTNo</LI>
	 *     <LI>Job type = Inventory check</LI>
	 *    </UL>
	 *    (Update contents)
	 *    <TABLE border="1">
	 *      <TR><TD>Work count</TD>		<TD>(+) 1</TD></TR>
	 *      <TR><TD>Work qty</TD>			<TD>(+) ID0831.Inventory result qty</TD></TR>
	 *      <TR><TD>Work complete date/time</TD>	<TD>system date/time</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * If there is no worker result, create first and then update it<BR>
	 * @param workerCode		Worker code
	 * @param rftNo			terminal no.
	 * @param inventoryQty		Inventory result qty
	 * @param workTime		work time
	 * @throws NotFoundException If there is no data for update available
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	protected void updateWorkerResult(String workerCode,
	        String rftNo,
	        int inventoryQty,
	        int workTime) throws NotFoundException, ReadWriteException
	{
		WorkerResult wr = new WorkerResult();
		BaseOperate bo = new BaseOperate(wConn);

		wr.setWorkDate(bo.getWorkingDate());
		wr.setWorkerCode(workerCode);
		wr.setTerminalNo(rftNo);
		wr.setJobType(WorkerResult.JOB_TYPE_INVENTORY);
		wr.setWorkCnt(1);
		wr.setOrderCnt(1);
		wr.setWorkQty(inventoryQty);
		wr.setWorkTime(workTime);
		wr.setRealWorkTime(workTime);

		try
		{
			bo.alterWorkerResult(wr);
		}
		catch (NotFoundException e)
		{
			String errData = "[RftNo:" + workerCode
					+ " WorkerCode:" + rftNo
					+ " JobType:" + WorkerResult.JOB_TYPE_INVENTORY + "]";
			//#CM576260
			// 6026016=No data you try to update is found. {0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
		    try
            {
                bo.createWorkerResult(wr.getJobType(), wr.getWorkerCode(), wr.getTerminalNo());
    			bo.alterWorkerResult(wr);
            }
		    catch (NotFoundException e1)
            {
				//#CM576261
				//6006002 = Database error occurred.{0}
				RftLogMessage.print(6006002, CLASS_NAME, "DnWorkerResult");
				//#CM576262
				//Throw with ReadWriteException in error message
				errDetails = RFTId5831.ErrorDetails.NULL;
		        throw new ReadWriteException("6006002");
            }
		    catch (DataExistsException e1)
            {
				//#CM576263
				//6006002 = Database error occurred.{0}
				RftLogMessage.print(6006002, CLASS_NAME, "DnWorkerResult");
				//#CM576264
				//Throw with ReadWriteException in error message
				errDetails = RFTId5831.ErrorDetails.DB_UNIQUE_KEY_ERROR;
		        throw new ReadWriteException("6006002");
            }
		}
	}
	
	//#CM576265
	/**
	 * Create new Inventory info<BR>
	 *    (create details)
	 *    <TABLE border="1">
	 *      <TR><TD>Job no.</TD>				<TD>Customization</TD></TR>
	 *      <TR><TD>Area no.</TD>			<TD>' '</TD></TR>
	 *      <TR><TD>Location no.</TD>		<TD>ID0831.Inventory checkLocation</TD></TR>
	 *      <TR><TD>Process flag</TD>			<TD>'0':Standby</TD></TR>
	 *      <TR><TD>Inventory result bar code</TD>	<TD>ID0831.JAN code</TD></TR>
	 *      <TR><TD>JAN code</TD>			<TD>ID0831.JAN code</TD></TR>
	 *      <TR><TD>Item name</TD>			<TD>ID0831.Item name</TD></TR>
	 *      <TR><TD>Stock qty</TD>				<TD>Stock info.Stock qty</TD></TR>
	 *      <TR><TD>Allocation qty</TD>				<TD>0</TD></TR>
	 *      <TR><TD>Work plan qty</TD>			<TD>0</TD></TR>
	 *      <TR><TD>Inventory result qty</TD>		<TD>ID0831.Inventory result qty</TD></TR>
	 *      <TR><TD>Case piece type</TD>	<TD>ID0831.Case piece type</TD></TR>
	 *      <TR><TD>Storage date</TD>				<TD>' '</TD></TR>
	 *      <TR><TD>Last picking date</TD>			<TD>' '</TD></TR>
	 *      <TR><TD>Expiry date</TD>			<TD>ID0831.Expiry date or ' '</TD></TR>
	 *      <TR><TD>Lot no.</TD>			<TD>' '</TD></TR>
	 *      <TR><TD>Plan info comment</TD>	<TD>' '</TD></TR>
	 *      <TR><TD>Consignor code</TD>			<TD>ID0831.Consignor code</TD></TR>
	 *      <TR><TD>Consignor name</TD>			<TD>ID0831.Consignor name</TD></TR>
	 *      <TR><TD>Supplier code</TD>		<TD>' '</TD></TR>
	 *      <TR><TD>Supplier name</TD>			<TD>' '</TD></TR>
	 *      <TR><TD>Qty per case</TD>			<TD>ID0831.Qty per case, </TD></TR>
	 *      <TR><TD>Qty per bundle</TD>			<TD>ID0831.Qty per bundle</TD></TR>
	 *      <TR><TD>Case ITF</TD>			<TD>ID0831.Case ITF</TD></TR>
	 *      <TR><TD>Bundle ITF</TD>			<TD>ID0831.Bundle ITF</TD></TR>
	 *      <TR><TD>Worker code</TD>		<TD>ID0831.Worker code</TD></TR>
	 *      <TR><TD>Worker name</TD>			<TD>ID0831.Worker name</TD></TR>
	 *      <TR><TD>terminal no.</TD>				<TD>ID0831.Handy no.</TD></TR>
	 *      <TR><TD>Registration date/time</TD>			<TD>system date/time</TD></TR>
	 *      <TR><TD>Registering process name</TD>			<TD>"ID0831"</TD></TR>
	 *      <TR><TD>Last update date/time</TD>		<TD>system date/time</TD></TR>
	 *      <TR><TD>Last update Process name</TD>		<TD>"ID0831"</TD></TR>
	 *    </TABLE>
	 * @param inventoryCheck	Inventory check work result
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws InvalidStatusException If status outside range is set
	 * @throws UpdateByOtherTerminalException	If the target data is updated via another terminal
	 */
	protected void createNewInventoryCheckData(InventoryCheck inventoryCheck) 
		throws ReadWriteException, InvalidStatusException, UpdateByOtherTerminalException
	{
		//#CM576266
		// Fetch new Inventory checkJob no.
		SequenceHandler sh = new SequenceHandler(wConn);
		String jobNo = sh.nextJobNo();
		
		//#CM576267
		// Set data to InventoryCheck data
		InventoryCheck target = new InventoryCheck();
		target.setJobNo(jobNo);
		target.setAreaNo(inventoryCheck.getAreaNo());
		target.setLocationNo(inventoryCheck.getLocationNo());
		target.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
		target.setInvcheckBcr(inventoryCheck.getItemCode());
		target.setItemCode(inventoryCheck.getItemCode());
		target.setItemName1(inventoryCheck.getItemName1());
		target.setStockQty(inventoryCheck.getStockQty());
		target.setAllocationQty(inventoryCheck.getStockQty());
		target.setPlanQty(0);
		target.setResultStockQty(inventoryCheck.getResultStockQty());
		target.setCasePieceFlag(inventoryCheck.getCasePieceFlag());
		target.setInstockDate(" ");
		target.setLastShippingDate(" ");
		target.setUseByDate(inventoryCheck.getUseByDate());
		target.setLotNo(inventoryCheck.getLotNo());
		target.setPlanInformation(" ");
		target.setConsignorCode(inventoryCheck.getConsignorCode());
		target.setConsignorName(inventoryCheck.getConsignorName());
		target.setSupplierCode(" ");
		target.setSupplierName1(" ");
		target.setEnteringQty(inventoryCheck.getEnteringQty());
		target.setBundleEnteringQty(inventoryCheck.getBundleEnteringQty());
		target.setItf(inventoryCheck.getItf());
		target.setBundleItf(inventoryCheck.getBundleItf()); 
		target.setWorkerCode(inventoryCheck.getWorkerCode());
		target.setWorkerName(inventoryCheck.getWorkerName());
		target.setTerminalNo(inventoryCheck.getTerminalNo());
		target.setRegistPname(PROCESS_NAME);
		target.setLastUpdatePname(PROCESS_NAME);
		
		try
		{
		    //#CM576268
		    // Create new Inventory info
		    InventoryCheckHandler ihandler = new InventoryCheckHandler(wConn);
		    ihandler.create(target);
		}
		catch (DataExistsException e)
		{
		    throw new UpdateByOtherTerminalException();
		}
	}
	
	//#CM576269
	/**
	 * Fetch from System definition whether stock package exist or not<BR>
	 * @return If stock control package exist true, else false
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	public boolean isWithStockManagement() throws ReadWriteException
	{
		WareNaviSystemHandler wnSystemHandler = new WareNaviSystemHandler(wConn);
		WareNaviSystemSearchKey wnSystemSearchKey = new WareNaviSystemSearchKey();
		WareNaviSystem[] wnSystem = (WareNaviSystem[])wnSystemHandler.find(wnSystemSearchKey);

		//#CM576270
		//Throw NotFoundException if system definition info can't be retrieved
		if (wnSystem == null || wnSystem.length == 0)
		{
			//#CM576271
			// 6006040 = Data mismatch occurred. {0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, "WareNavi_System");
			throw new ReadWriteException();
		}
		
		//#CM576272
		// Fetch stock package install status
		String stockManagement = wnSystem[0].getStockPack();
	    return stockManagement.equals(WareNaviSystem.PACKAGE_FLAG_ADDON);
	}
	
	//#CM576273
	/**
	 * Search inventory info with process flag = standby using Consignor code, Area no.,Location no.
	 * Item code, Expiry date (If expiry date control package exist)<BR>
	 * @param inventoryCheck	Inventory check work result
	 * @param isLock		Whether to lock the search record or not
	 * @return			Inventory info
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws NoPrimaryException	If there are multiple target data
	 */
	public InventoryCheck getInventoryCheckData(
	        InventoryCheck inventoryCheck,
	        boolean isLock) throws ReadWriteException, NoPrimaryException
	{
	    //#CM576274
	    // Search Inventory info based on Consignor, Area no., Location, Item code, Expiry date
	    InventoryCheckSearchKey skey = new InventoryCheckSearchKey();
	    skey.setConsignorCode(inventoryCheck.getConsignorCode());
	    skey.setAreaNo(inventoryCheck.getAreaNo());
	    skey.setLocationNo(inventoryCheck.getLocationNo());
	    skey.setItemCode(inventoryCheck.getItemCode());
	    if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
	    {
	        //#CM576275
	        // Add expiry date to search conditions, if expiry date control package exist
		    skey.setUseByDate(inventoryCheck.getUseByDate());
	    }
	    skey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
	    
	    InventoryCheckHandler ihandler = new InventoryCheckHandler(wConn);
	    InventoryCheck target = null;
	    if (isLock)
	    {
		    target = (InventoryCheck) ihandler.findPrimaryForUpdate(skey);
	    }
	    else
	    {
	        InventoryCheck[] buf = (InventoryCheck[]) ihandler.find(skey);
	        if (buf.length > 0)
	        {
	            target = buf[0];
	        }
	    }

	    return target;
	}

	//#CM576276
	/**
	 * Search current Inventory qty using Consignor code, Area no., Location no., Item code<BR>
	 * Target data with process flag = standby<BR>
	 * @param	consignorCode	Consignor code
	 * @param	areaNo			Area no.
	 * @param	locationNo		Location no.
	 * @param	itemCode 		Item code
	 * @return	Inventory qty
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	public int getInventoryQty(
	        String consignorCode,
	        String areaNo,
	        String locationNo,
	        String itemCode) throws ReadWriteException
	{
	    //#CM576277
	    // Search Inventory info based on Consignor, Area no., Location, Item code, Expiry date
	    InventoryCheckSearchKey skey = new InventoryCheckSearchKey();
	    skey.setConsignorCode(consignorCode);
	    skey.setAreaNo(areaNo);
	    skey.setLocationNo(locationNo);
	    skey.setItemCode(itemCode);
	    skey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
	    
	    InventoryCheckHandler ihandler = new InventoryCheckHandler(wConn);
	    InventoryCheck[] inventoryCheck = (InventoryCheck[]) ihandler.find(skey);

	    int inventoryQty = 0;
	    for (int i = 0; i < inventoryCheck.length; i ++)
	    {
	        inventoryQty += inventoryCheck[i].getResultStockQty();
	    }
	    
	    return inventoryQty;
	}
	
	//#CM576278
	/**
	 * Fetch error details
	 * @return Error details
	 */
	public String getErrDetails()
	{
		return errDetails;
	}
}
//#CM576279
//end of class
