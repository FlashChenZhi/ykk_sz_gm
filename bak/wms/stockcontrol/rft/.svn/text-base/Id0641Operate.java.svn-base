// $Id: Id0641Operate.java,v 1.2 2006/09/27 03:00:38 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;
//#CM9702
/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.rft.AllocateException;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.master.operator.AreaOperator;


//#CM9703
/**
 * Designer : K.Shimizu <BR>
 * Maker : E.Takeda <BR>
 * <BR>
 * Execute processing for Unplanned Picking Result send from RFT.<BR>
 * Create stock allocate process and Result data inquiry.<BR>
 * Implement Business logic invoked from Id0153Process.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/15</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:38 $
 * @author  $Author: suresh $
 */
public class Id0641Operate extends IdOperate
{
	//#CM9704
	// Class variables -----------------------------------------------
	//#CM9705
	/**
	 * Process name
	 */
	private static final String PROCESS_NAME = "ID0641";
	//#CM9706
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0641Operate";
	
	//#CM9707
	/**
	 * error detail
	 */
	private String errorDetail = RFTId5641.ErrorDetails.NORMAL;
	
	//#CM9708
	/**
	 * picking possible Stock qty
	 */
	private int storageQty = 0;
	//#CM9709
	// Class method --------------------------------------------------
	//#CM9710
	/**
	 * Return the version of this class.<BR>
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/09/27 03:00:38 $";
	}
	//#CM9711
	// Constructors --------------------------------------------------
	//#CM9712
	// Public methods ------------------------------------------------
	//#CM9713
	/**
	 * Execute the process for Unplanned Picking Result send (ID0641).<BR>
	 * Determine if it is in date/time update process.<BR>
	 * If date/time update is in process<BR>
	 * Determine the corresponding stock from an electronic statement and execute the picking process<BR>
	 * Output Consignor code
	 * <BR>
	 * If stockcontrol package is available
	 * Lock corresponding data of Stock info. (For exclusion process)<BR>
	 * In the case Time-out is generated on lock (In such case when duplicated with allocate of other process)<BR>
	 * Return the response for the error (Allocation in process at other terminal).<BR>
	 * Update Stock info (picking process), Decrease stock qty of the corresponding stock.<BR>
	 * Generate Result data inquiry of the Unplanned Picking work.<BR>
	 * Search and update corresponding data of the Worker Result data inquiry.<BR>
	 * <BR>
	 * If stockcontrol package is not available
	 * Generate Result data inquiry of the Unplanned Picking work.<BR>
	 * Search and update corresponding data of the Worker Result data inquiry.<BR>
	 * <BR>
	 * @param	workerCode		Worker Code
	 * @param	rftNo			RFT number
	 * @param	resutlData		Object that store the electronic statement in StockEntity
	 * @param	resultQty		picking Result file
	 * @param	resultUseByDate Result Expiry Date
	 * @param  workTime		    Work time
	 * @return					BallITFResponse flag
	*/
	public String doComplete(
		String	workerCode,
		String	rftNo,
		Stock	resultData,
		int	resultQty,
		String  resultUseByDate,
		int workTime
		)
	{
		try
		{
			//#CM9714
			// Check if in date/time process
			//#CM9715
			// Generate BaseOperate instance
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);
			
			//#CM9716
			// Check if in daily process
			if (baseOperate.isLoadingDailyUpdate())
			{
				//#CM9717
				// Retunr status flag 5: under daily update processing
				return RFTId5641.ANS_CODE_DAILY_UPDATING;
			}

			//#CM9718
			// Processes to complete the Unplanned picking.
			completeNoPlanRetrieval(
					 workerCode,
					 rftNo,
					 resultData,
					 resultQty,
					 resultUseByDate,
					 workTime
					 ) ;

			
			wConn.commit();
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

			//#CM9719
			// Response flag：error
			errorDetail = RFTId5641.ErrorDetails.INSTACIATE_ERROR;
			return RFTId5641.ANS_CODE_ERROR;
		}
		catch (NotFoundException e)
		{
			String errData = "[ConsignorCode:" +resultData.getConsignorCode() +
							 " LocationNo:" + resultData.getLocationNo() +
							 " ItemCode:" + resultData.getItemCode() +
							 " UseByDate:" + resultData.getUseByDate() +"]";
			//#CM9720
			// 6026016=No data you try to update is found.{0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);			
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				//#CM9721
				// 6006002=Database error occurred.{0}{0}
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9722
			// Response flag：No correspondent stock

			return RFTId5641.ANS_CODE_NULL ;			
		}
		catch (AllocateException e)
		{
			String errData = "[ConsignorCode:" + resultData.getConsignorCode() +
			 				 " LocationNo:" + resultData.getLocationNo() +
							 " ItemCode:" + resultData.getItemCode() +
							 " UseByDate:" + resultData.getUseByDate() +"]";
			//#CM9723
			// 6026016=No data you try to update is found.{0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);			
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				//#CM9724
				// 6006002=Database error occurred.{0}{0}
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9725
			// Response flag：exceed Relocatable qty
			return RFTId5641.ANS_CODE_RETRIEVAL_OVER ;			
		}
		catch (LockTimeOutException e)
		{			
			//#CM9726
			// SELECT FOR UPDATE Time-out
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				//#CM9727
				// 6006002=Database error occurred.{0}{0}
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9728
			// Response flag：maintaining in process at other work stationit
			return RFTId5641.ANS_CODE_MAINTENANCE ;
		}
		//#CM9729
		// In the case error is generated in data access
		catch (ReadWriteException e)
		{
			//#CM9730
			// 6006002=Database error occurred.{0}{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9731
			// error detail
			if(errorDetail.equals(RFTId5641.ErrorDetails.NORMAL))
			{
				errorDetail = RFTId5641.ErrorDetails.DB_ACCESS_ERROR;
			}
			//#CM9732
			// Response flag：error
			return RFTId5641.ANS_CODE_ERROR ;
		}
		catch (InvalidDefineException e)
		{
			//#CM9733
			// 6026022=Blank or prohibited character is included in the specified value.{0}
			RftLogMessage.print(6026022, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9734
			// error detail
			errorDetail = RFTId5641.ErrorDetails.PARAMETER_ERROR;
			//#CM9735
			// Response flag：error
			return RFTId5641.ANS_CODE_ERROR;
		}
		catch (DataExistsException e)
		{
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9736
			// error detail
			errorDetail = RFTId5641.ErrorDetails.DB_UNIQUE_KEY_ERROR;
			//#CM9737
			// Response flag：error
			return RFTId5641.ANS_CODE_ERROR;
		}
		catch (SQLException e)
		{	
			//#CM9738
			// 6006002=Database error occurred.{0}{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9739
			// error detail
			errorDetail = RFTId5641.ErrorDetails.DB_ACCESS_ERROR;
			return RFTId5641.ANS_CODE_ERROR;
		}
		//#CM9740
		// In the case other error exists
		catch (Exception e)
		{
			//#CM9741
			// 6026015=Error occurred during ID process.{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);

			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9742
			// error detail
			errorDetail = RFTId5641.ErrorDetails.INTERNAL_ERROR;
			//#CM9743
			// Response flag：error
			return RFTId5641.ANS_CODE_ERROR ;
		}

		
		return RFTId5641.ANS_CODE_NORMAL;
	}

	//#CM9744
	/**
	 * Execute Unplanned Picking Result (ID0641) processing.<BR>
	 * Determine the corresponding stock from an electronic statement and execute the picking process and the result data inquiry generation process.<BR>
	 * <OL>
	 * <LI>picking Executes processing.
	 * <OL>
	 * <LI>Lock corresponding data of Stock info (For exclusion process)<BR>
	 * <LI>In the case Time-out is generated on lock (In such case when duplicated with allocate of other process)<BR>
	 * <LI>Return the response for the error (Allocation in process at other terminal).<BR>
	 * <LI>Update Stock info. Decrease stock qty of the corresponding stock.
	 * </OL>
	 * <LI>Generate Result data inquiry of the Unplanned Picking work.<BR>
	 * <LI>Search and update the corresponding data of the Worker Result data inquiry<BR>
	 * </OL>
	 * Obtain the Stock info and lock it.
	 * <DIR>
	 *    (search conditions)
	 *    <UL>
	 *    <LI>Location No.=relocation source location of the electronic statement.</LI>
	 *    <LI>Item code=JAN code of the electronic statement.</LI>
	 *    <LI>Stock status=2:Center Inventory</LI>
	 *    <LI>Expiry Date=Expiry Date of the electronic statement.</LI>
	 *    <LI>Consignor code=Consignor code of the electronic statement.</LI>
	 *    <LI>Load size=Load size of the electronic statement.</LI>
	 *    </UL>
	 * </DIR>
	 * <BR>
	 * Stock decrease process
	 * Allocatable qty decrease process
	 * <P>
	 * Check for the stock possible for picking (stock qty- allocated qty) obtained in the above process for obtaining.
	 * Decrease stock qty when possible picking qty is larger than picking qty.
	 * Return error if the picking qty is smaller.
	 * </P>
	 * Create Result data inquiry (Call createResltData)<BR>
	 * <BR>
	 * Update the Worker Result data inquiry (Call createWorkerResult)<BR>
	 * <BR>
	 * @param	workerCode		Worker Code
	 * @param	rftNo			RFT number
	 * @param	stock      	    Stock infoEntity
	 * @param	resultQty		picking Result file
	 * @param	resultUseByDate	Result Expiry Date
	 * @param	workTime		Work time
	 * @throws InvalidDefineException Announce this when specified value is abnormal (Blank, including prohibited characters).
	 * @throws LockTimeOutException Announce this when database lock is not released for specified time.
	 * @throws ReadWriteException Announce this when error generated in connection with database
	 * @throws DataExistsException Announce this when trying to add the same Information already added.
	 * @throws NotFoundException Announce this when update target data is not found.
	 * @throws AllocateException Announce this when allocate more qty than possible allocation number (executing move picking more qty than possible allocation).
	 * @throws InvalidStatusException InvalidStatusException Announce this when any discrepancy is found in the setting value of table update.
	 * @throws ScheduleException Announce this when unexpected exception occurs in the check Process.
	*/
	public void completeNoPlanRetrieval(
			String workerCode,
			String rftNo,
			Stock  stock,
			int resultQty,
			String resultUseByDate,
			int workTime
			) 
		throws 
			InvalidDefineException, 
			LockTimeOutException, 
			ReadWriteException, 
			DataExistsException, 
			NotFoundException,
			AllocateException, InvalidStatusException, ScheduleException
	{
		try
		{
			WareNaviSystemHandler wmsHandler = new WareNaviSystemHandler(wConn);
			WareNaviSystem[] wms = (WareNaviSystem[])wmsHandler.find(new WareNaviSystemSearchKey());

			//#CM9745
			// Checks whether stock package is installed or not.
			if (wms[0].getStockPack().equals(WareNaviSystem.PACKAGE_FLAG_ADDON))
			{
			    //#CM9746
			    // Generate info instance of handlers.
				StockHandler stockHandler = new StockHandler(wConn);
				StockSearchKey searchKey = new StockSearchKey();
				
				//#CM9747
				// Set search condition
				//#CM9748
				// Location No.
				searchKey.setLocationNo(stock.getLocationNo());
				//#CM9749
				// Item code
				searchKey.setItemCode(stock.getItemCode());
				//#CM9750
				// Stock status＝Center Inventory
				searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
				//#CM9751
				// Expiry Date
				if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
				{
					if (!StringUtil.isBlank(stock.getUseByDate()))
					{
						searchKey.setUseByDate(stock.getUseByDate());
					}
					else
					{
						searchKey.setUseByDate("", "");
					}
				}
				//#CM9752
				// Consignor code
				searchKey.setConsignorCode(stock.getConsignorCode());
				//#CM9753
				// Case/Piece division(Load size)
				//#CM9754
				// Not Designated
				if(stock.getCasePieceFlag().equals(Stock.CASEPIECE_FLAG_NOTHING))
				{
					searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
				}
				//#CM9755
				// Case
				else if(stock.getCasePieceFlag().equals(Stock.CASEPIECE_FLAG_CASE))
				{
					searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
				}
				//#CM9756
				// Piece
				else if(stock.getCasePieceFlag().equals(Stock.CASEPIECE_FLAG_PIECE))
				{
					searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
				}

				//#CM9757
				// Lock the Stock info and obtain it.
				Stock[] target = null;
				int timeout = WmsParam.WMS_DB_LOCK_TIMEOUT;
				target=(Stock[])stockHandler.findForUpdate(searchKey, timeout);
				
				//#CM9758
				// Throw NotFoundException when Stock info is not obtained.
				if (target.length == 0)
				{
					throw new NotFoundException();
				}
			
				//#CM9759
				// Check possible picking qty.
				//#CM9760
				// （Stock qty - allocated qty > picking Result file）
				if (target[0].getAllocationQty() < resultQty)
				{
					//#CM9761
					// Throw AllocateException when possible picking qty is short.
					storageQty = target[0].getAllocationQty();
					throw (new AllocateException());
				}		
				
				//#CM9762
				// Picking Process
				
				//#CM9763
				// Generate Instance of handlers for inventory information.			
				StockAlterKey alterKey = new StockAlterKey();
				alterKey.setLocationNo(target[0].getLocationNo());	// stockからtarget[0]に変更↓
				alterKey.setItemCode(target[0].getItemCode());
				alterKey.setCasePieceFlag(target[0].getCasePieceFlag());
				alterKey.setConsignorCode(target[0].getConsignorCode());
				alterKey.setStatusFlag(target[0].getStatusFlag());
				alterKey.setUseByDate(target[0].getUseByDate());
				
				//#CM9764
				// Delete Stock info when Stock qty (Current stock qty - picking Result file）is 0.
				if (target[0].getStockQty() - resultQty == 0)
				{
					searchKey.KeyClear();
					searchKey.setStockId(target[0].getStockId());
					stockHandler.drop(searchKey);
				}
				else
				{
					//#CM9765
					// Stock qty (Current stock qty - picking Result file）
					alterKey.updateStockQty(target[0].getStockQty() - resultQty);
					//#CM9766
					// Allocatable Qty
					alterKey.updateAllocationQty(target[0].getAllocationQty() - resultQty);
					//#CM9767
					// last Update process name
					alterKey.updateLastUpdatePname(PROCESS_NAME);

					//#CM9768
					// Update the data.
					stockHandler.modify(alterKey);
				}
				
				//#CM9769
				// Assign target[0] including search result to the stock.
				stock = target[0];
				
			
			}
			//#CM9770
			// of the location where Update picking is made
			try
			{
				LocateOperator lOperator = new LocateOperator(wConn);
				lOperator.modifyLocateStatus(stock.getLocationNo(), PROCESS_NAME);
			}
			catch (ScheduleException e)
			{
				throw new ReadWriteException(e.getMessage());
			}
		
			//#CM9771
			// Create sending Result Info
			createResultData( workerCode, rftNo, stock, resultUseByDate, resultQty);
	
			//#CM9772
			// Update Worker result(create)
			createWorkerResult( workerCode, rftNo, resultQty, workTime);
			
		}
		catch (LockTimeOutException e)
		{
			//#CM9773
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DMSTOCK");
			throw e;
		}
		catch (InvalidDefineException e)
		{
			//#CM9774
			// When specified value generates error
			String errData =
				"<ConsignorCode[" + stock.getConsignorCode()
					+ "] locationNo[" + stock.getLocationNo()
					+ "] itemCode[" + stock.getItemCode()+ "]>";
			throw new InvalidDefineException(errData);
		}
	}

	//#CM9775
	// Package methods -----------------------------------------------

	//#CM9776
	// Protected methods ---------------------------------------------
	//#CM9777
	/**
	 * Generate sending Result Info.<BR>
	 * <DIR>
	 *    (Content of form)
	 *    <TABLE>
	 *      <TR><TD>Work Date</TD>						<TD>System definition</TD></TR>
	 *      <TR><TD>Work No.</TD>						<TD>Numbering</TD></TR>
	 *      <TR><TD>Work division</TD>					<TD>22:Unplanned picking</TD></TR>
	 *      <TR><TD>AggregationWork No.</TD>					<TD>Numbering</TD></TR>
	 *      <TR><TD>Status flag</TD>					<TD>4:Complete</TD></TR>
	 *      <TR><TD>Start Work flag</TD>				<TD>1:Started</TD></TR>
	 *      <TR><TD>Stock ID</TD>						<TD>Picking Stock ID of the stock（Empty if without stockcontrol）</TD></TR>
	 *      <TR><TD>Location No</TD>				<TD>Location No of the electronic statement.</TD></TR>
	 *      <TR><TD>Consignor code</TD>					<TD>Consignor code of the electronic statement.</TD></TR>
	 *      <TR><TD>Consignor name</TD>					<TD>Consignor name of the electronic statement.</TD></TR>
	 *      <TR><TD>Item code</TD>					<TD>Item code of the electronic statement.</TD></TR>
	 *      <TR><TD>Item name</TD>					<TD>Item name of the electronic statement.</TD></TR>
	 *      <TR><TD>Host Planned Qty</TD>					<TD>0</TD></TR>
	 *      <TR><TD>Planned work qty</TD>					<TD>0</TD></TR>
	 *      <TR><TD>possible Work Qty</TD>					<TD>0</TD></TR>
	 *      <TR><TD>Work Result file</TD>					<TD>Result file of the electronic statement.</TD></TR>
	 *      <TR><TD>work shortage qty</TD>					<TD>0</TD></TR>
	 *      <TR><TD>Pending Qty</TD>						<TD>0</TD></TR>
	 *      <TR><TD>Packed qty per case</TD>					<TD>Packed qty per case of the electronic statement.</TD></TR>
	 *      <TR><TD>packed qty per bundle</TD>					<TD>Packed qty per bundle of the electronic statement.</TD></TR>
	 *      <TR><TD>Load size</TD>						<TD>Load size of the electronic statement.</TD></TR>
	 *      <TR><TD>Work type</TD>					<TD>Load sizeConsignor code of the electronic statement.</TD></TR>
	 *      <TR><TD>Case ITF</TD>					<TD>Case ITF of the electronic statement.</TD></TR>
	 *      <TR><TD>Bundle ITF</TD>					<TD>Bundle ITF of the electronic statement.</TD></TR>
	 *      <TR><TD>TC/DC division</TD>					<TD>0:DC</TD></TR>
	 *      <TR><TD>Workresult expiry date</TD>				<TD>Expiry Date of the electronic statement.</TD></TR>
	 *      <TR><TD>Work Result Location</TD>			<TD>Location of the electronic statement.</TD></TR>
	 *      <TR><TD>Work report flag</TD>				<TD>0:Standby sending</TD></TR>
	 *      <TR><TD>unitNo</TD>					<TD>Numbering（Storage planned batch No）</TD></TR>
	 *      <TR><TD>Worker Code</TD>					<TD>Worker Code of the electronic statement.</TD></TR>
	 *      <TR><TD>Worker Name</TD>					<TD>Obtain from the Worker Code of the electronic statement.</TD></TR>
	 *      <TR><TD>Terminal No.,RFTNo</TD>				<TD>Handiy machine in electronic statementNo</TD></TR>
	 *      <TR><TD>Added Date/Time</TD>					<TD>System date</TD></TR>
	 *      <TR><TD>Submit/Add process name</TD>					<TD>ID0641</TD></TR>
	 *      <TR><TD>last update date/time</TD>					<TD>System date</TD></TR>
	 *      <TR><TD>last Update process name</TD>				<TD>ID0641</TD></TR>
	 *    </TABLE>
	 *   Make the item (count) blank except the above.
	 * </DIR>
	 * 
	 * @param workerCode		Worker Code
	 * @param rftNo				RFTNo
	 * @param stock				allocationStock info table
	 * @param resultUseByDate 	Result Expiry Date
	 * @param resultQty 		picking Result file
	 * @throws ReadWriteException Announce this when error generated in connection with database
	 * @throws DataExistsException Announce this when trying to add the same Information already added.
	 * @throws InvalidStatusException Announce this when any discrepancy is found in the setting value of table update.
	 * @throws ScheduleException  Announce this when unexpected exception occurs in the schedule.
	 */
	protected void createResultData(String workerCode, String rftNo, Stock stock, String resultUseByDate, int resultQty) 
		throws  ReadWriteException, DataExistsException, InvalidStatusException, ScheduleException
	{
		BaseOperate bo = new BaseOperate(wConn);
		//#CM9778
		// Obtain the System work date.
		String workingDate = "";
		try
		{
			workingDate = bo.getWorkingDate();
		}
		catch (NotFoundException e)
		{
			//#CM9779
			// Throw ReadWriteException if failed to obtain the work date from the System definition info.
			throw (new ReadWriteException());
		}
		//#CM9780
		// Obtain the Worker name.
		String workerName = "";
		try
		{
			workerName = bo.getWorkerName(workerCode);
		}
		catch (NotFoundException e1)
		{
			//#CM9781
			// Create log and execute normal process when worker name cannot be obtained.
			//#CM9782
			// 6004500=Unregistered user ID is entered: User ID={0}
			String errData = "[" + workerCode + "]";
			RftLogMessage.print(6004500, LogMessage.F_WARN, CLASS_NAME, errData);
		}

		//#CM9783
		// Generate sending Result Info.

		//#CM9784
		// Generate instance for sending Result Info handlers.
		HostSendHandler hostsendHandler = new HostSendHandler(wConn);
		HostSend hostsend = new HostSend();
		
		//#CM9785
		// Obtain each unique key needed to add.
		SequenceHandler sequence = new SequenceHandler(wConn);
		//#CM9786
		// Work No.
		String job_seqno = sequence.nextJobNo();
		//#CM9787
		// Work Date
		hostsend.setWorkDate(workingDate);
		//#CM9788
		// Work No.
		hostsend.setJobNo(job_seqno);
		//#CM9789
		// Work division
		hostsend.setJobType(HostSend.JOB_TYPE_EX_RETRIEVAL);
		//#CM9790
		// Aggregation Work No. (equal to Work No.)
		hostsend.setCollectJobNo(job_seqno);
		//#CM9791
		// Status flag (Completed)
		hostsend.setStatusFlag(HostSend.STATUS_FLAG_COMPLETION);
		//#CM9792
		// Start Work flag (Already Started)
		hostsend.setBeginningFlag(HostSend.BEGINNING_FLAG_STARTED);
		//#CM9793
		// Plan unique key
		hostsend.setPlanUkey("");
		//#CM9794
		// Stock ID
		hostsend.setStockId(stock.getStockId());
		//#CM9795
		// Area No.
		hostsend.setAreaNo(stock.getAreaNo());
		//#CM9796
		// Location No.
		hostsend.setLocationNo(stock.getLocationNo());
		//#CM9797
		// Planned date
		hostsend.setPlanDate(workingDate);
		//#CM9798
		// Consignor code
		hostsend.setConsignorCode(stock.getConsignorCode());
		//#CM9799
		// Consignor name
		hostsend.setConsignorName(stock.getConsignorName());
		//#CM9800
		// Supplier code
		hostsend.setSupplierCode("");
		//#CM9801
		// Supplier name
		hostsend.setSupplierName1("");
		//#CM9802
		// Receiving ticketNo.
		hostsend.setInstockTicketNo("");
		//#CM9803
		// Receiving ticketLine No.
		hostsend.setInstockLineNo(0);
		//#CM9804
		// Customer code
		hostsend.setCustomerCode("");
		//#CM9805
		// Customer name
		hostsend.setCustomerName1("");
		//#CM9806
		// Shipping Ticket No
		hostsend.setShippingTicketNo("");
		//#CM9807
		// Shipping ticket line No.
		hostsend.setShippingLineNo(0);
		//#CM9808
		// Item code
		hostsend.setItemCode(stock.getItemCode());
		//#CM9809
		// Item name
		hostsend.setItemName1(stock.getItemName1());
		//#CM9810
		// Planned work qty(Host Planned Qty)
		hostsend.setHostPlanQty(0);
		//#CM9811
		// Planned work qty
		hostsend.setPlanQty(0);
		//#CM9812
		// possible Work Qty
		hostsend.setPlanEnableQty(0);
		//#CM9813
		// Work Result file
		hostsend.setResultQty(resultQty);
		//#CM9814
		// work shortage qty
		hostsend.setShortageCnt(0);
		//#CM9815
		// Pending Qty
		hostsend.setPendingQty(0);
		//#CM9816
		// Packed qty per case
		hostsend.setEnteringQty(stock.getEnteringQty());
		//#CM9817
		// packed qty per bundle
		hostsend.setBundleEnteringQty(stock.getBundleEnteringQty());
		//#CM9818
		// Case/Piece division(Load size)
		hostsend.setCasePieceFlag(stock.getCasePieceFlag());
		//#CM9819
		// Case/Piece division(Work type)
		hostsend.setWorkFormFlag(stock.getCasePieceFlag());
		//#CM9820
		// Case ITF
		hostsend.setItf(stock.getItf());
		//#CM9821
		// Bundle ITF
		hostsend.setBundleItf(stock.getBundleItf());
		//#CM9822
		// TC/DC division
		hostsend.setTcdcFlag(HostSend.TCDC_FLAG_DC);
		//#CM9823
		// Expiry Date
		hostsend.setUseByDate(stock.getUseByDate());
		//#CM9824
		// Lot No.
		hostsend.setLotNo("");
		//#CM9825
		// Plan information Comment
		hostsend.setPlanInformation("");
		//#CM9826
		// Order No.
		hostsend.setOrderNo("");
		//#CM9827
		// Order Date
		hostsend.setOrderingDate("");
		//#CM9828
		// result expiry date
		hostsend.setResultUseByDate(resultUseByDate);
		//#CM9829
		// Lot No.
		hostsend.setResultLotNo("");
		//#CM9830
		// Work Result Location
		hostsend.setResultLocationNo(stock.getLocationNo());
		//#CM9831
		// Work Report flag (Standby reporting)
		hostsend.setReportFlag(HostSend.REPORT_FLAG_NOT_SENT);
		//#CM9832
		// Batch No(Schedule No.)
        String batch_seqno = sequence.nextNoPlanBatchNo();
		hostsend.setBatchNo(batch_seqno);

		AreaOperator areaOpe = new AreaOperator(wConn);
		try
		{
			//#CM9833
			// System distinct key
			hostsend.setSystemDiscKey(Integer.parseInt(areaOpe.getAreaType(stock.getAreaNo())));
		}
		catch (NumberFormatException e)
		{
			throw (new ScheduleException());
		}
		//#CM9834
		// Worker Code
		hostsend.setWorkerCode(workerCode);
		//#CM9835
		// Worker Name
		hostsend.setWorkerName(workerName);
		//#CM9836
		// Terminal No.,RFTNo.
		hostsend.setTerminalNo(rftNo);
		//#CM9837
		// Plan information Added Date/Time
		hostsend.setPlanRegistDate(null);
		//#CM9838
		// Added Date/Time
		hostsend.setRegistDate(new Date());
		//#CM9839
		// Submit/Add process name
		hostsend.setRegistPname(PROCESS_NAME);
		//#CM9840
		// last update date/time
		hostsend.setLastUpdateDate(new Date());
		//#CM9841
		// last Update process name
		hostsend.setLastUpdatePname(PROCESS_NAME);

		//#CM9842
		// Add data
		hostsendHandler.create(hostsend);
	}
	

	//#CM9843
	/**
	 * Update Worker result
	 * <DIR>
	 *    (Contents of update)
	 *    <TABLE>
	 *      <TR><TD>Work completion date</TD>	<TD>Current system date</TD></TR>
	 *      <TR><TD>Work division</TD>		<TD>22:Unplanned picking</TD></TR>
	 *      <TR><TD>Work Quantity</TD>		<TD>+Unplanned picking result sending No.</TD></TR>
	 *      <TR><TD>Work No.</TD>		<TD>+1 </TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * When Worker result is not found(in such case when Unplanned Picking processing is daily processed at RFT)<BR>
	 * Create Worker Result data inquiry<BR>
	 * 
	 * @param	workerCode		Worker Code
	 * @param	rftNo			RFT number
	 * @param	resultQty		Unplanned pickingResult file
	 * @param  workTime			Work time
	 * @throws ReadWriteException Announce this when error generated in connection with database
	 */
	protected void createWorkerResult(
			String workerCode, 
			String rftNo, 
			int resultQty,
			int workTime) throws ReadWriteException
	{
		BaseOperate bo = new BaseOperate(wConn);
		String workingDate = "";
		try
		{
			workingDate = bo.getWorkingDate();
		}
		catch (NotFoundException e)
		{
			//#CM9844
			// Failing to obtain Work Date from the System definition info
			//#CM9845
			// Throw ReadWriteException
			errorDetail = RFTId5641.ErrorDetails.NULL;
			throw (new ReadWriteException());
		}

		//#CM9846
		// Update result per Worker
		WorkerResult wr = new WorkerResult();
		wr.setWorkDate(workingDate);
		wr.setWorkerCode(workerCode);
		wr.setTerminalNo(rftNo);
		wr.setJobType(WorkerResult.JOB_TYPE_EX_RETRIEVAL);
		wr.setWorkCnt(1);
		wr.setOrderCnt(1);
		wr.setWorkQty(resultQty);
		wr.setWorkTime(workTime);
		wr.setRealWorkTime(workTime);
		try
		{
			bo.alterWorkerResult(wr);
		}
		catch (NotFoundException e)
		{
			//#CM9847
			// Create new, when update target Worker result is not found.
			//#CM9848
			// （In such case that daily update is processed during RFT work）
			//#CM9849
			// 6022004=No matching Worker Result Data is found. A new data will be created. {0}
			String errData = "[WorkDate:" + workingDate +
			 				 " JobType:" + WorkerResult.JOB_TYPE_EX_RETRIEVAL +
							 " RftNo:" + rftNo +
							 " WorkerCode:" + workerCode +"]";
			RftLogMessage.print(6022004, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				//#CM9850
				// Worker result create
				bo.createWorkerResult(WorkerResult.JOB_TYPE_EX_RETRIEVAL, workerCode, rftNo);
				//#CM9851
				// Worker result update
				bo.alterWorkerResult(wr);
			}
			catch(NotFoundException e1)
			{
				errorDetail = RFTId5641.ErrorDetails.NULL;
				throw (new ReadWriteException());
			}
			catch (Exception e1)
			{
				//#CM9852
				// Throw ReadWriteException when exception occurs.
				throw (new ReadWriteException());
			}
		}
	}
	

	//#CM9853
	// Private methods -----------------------------------------------
	//#CM9854
	/**
	 * Obtain error detail.
	 * 
	 * @return	error detail
	 */
	public String getErrorDetails()
	{
		return errorDetail;
	}
	
	//#CM9855
	/**
	 * Obtain the stock qty possible for picking.
	 * 
	 * @return picking possible Stock qty
	 */
	public int getStorageQty()
	{
		return storageQty;
	}
	
}
//#CM9856
//end of class
