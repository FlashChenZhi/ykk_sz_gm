// $Id: Id0731Operate.java,v 1.2 2006/12/07 09:00:11 suresh Exp $
package jp.co.daifuku.wms.storage.rft;
//#CM575433
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.MovementHandler;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.rft.AllocateException;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.storage.schedule.StockAllocateOperator;


//#CM575434
/**
 * Designer : K.Shimizu <BR>
 * Maker : K.Shimizu <BR>
 * <BR>
 * This is the Relocation picking result data send process from RFT<BR>
 * Create Relocation Work info and stock allocation<BR>
 * Execute business logic called from Id0731Process<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/11</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:11 $
 * @author  $Author: suresh $
 */
public class Id0731Operate extends IdOperate
{
	//#CM575435
	// Class variables -----------------------------------------------
	//#CM575436
	/**
	 * Process name
	 */
	private static final String PROCESS_NAME = "ID0731";
	
	//#CM575437
	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "Id0731Operate";
	
	//#CM575438
	/**
	 * Error details
	 */
	private String errorDetails = RFTId5731.ErrorDetails.NORMAL;
	
	//#CM575439
	/**
	 * Stock qty available for picking
	 */
	private int storageQty = 0;
	//#CM575440
	// Class method --------------------------------------------------
	//#CM575441
	/**
	 * Return the version of this class<BR>
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:11 $");
	}
	//#CM575442
	// Constructors --------------------------------------------------

	//#CM575443
	// Public methods ------------------------------------------------
	//#CM575444
	/**
	 * This is the relocation picking result data process (ID0731)<BR>
	 * Decide target stock from telegraph data, call allocation process and create Work info<BR>
	 * <BR>
	 * If stock package exist<BR>
	 * <DIR>
	 * Lock target data in stock info (exclusion process)<BR>
	 * If time out error occurs during lock (in case of redundant access)<BR>
	 * Error response (allocation process in another terminal)<BR>
	 * Update stock info (allocation process). Based on allocation order, decide allocation qty of the traget stock <BR>
	 * Create relocation work info. Create relocation work info. for allocation result<BR>
	 * Update worker result. Search the target data and add work qty, work count<BR>
	 * </DIR>
	 * If stock package does not exist<BR>
	 * <DIR>
	 * Create relocation work info. Create relocation work info from telegraph data<BR>
	 * Update worker result. Search the target data and add work qty, work count<BR>
	 * </DIR>
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @param	consignorCode		Consignor code
	 * @param	consignorName		Consignor name
	 * @param	locationNo		Origin Location
	 * @param	janCode			JAN code
	 * @param	itemName1		Item name
	 * @param	enteringQty		Qty per case
	 * @param	bundleEntQty		Qty per bundle
	 * @param	itf			Case ITF
	 * @param	bundleItf		Bundle ITF
	 * @param	useByDate		Expiry date
	 * @param	resultQty		Relocation picking result qty
	 * @param	workTime		work time
	 * @return	Response id Response flag
	*/
	public String doComplete(
		String workerCode,
		String rftNo,
		String consignorCode,
		String consignorName,
		String locationNo,
		String janCode,
		String itemName1,
		int    enteringQty,
		int    bundleEntQty,
		String itf,
		String bundleItf,
		String useByDate,
		int    resultQty,
		int    workTime)
	{
		try
		{

			//#CM575445
			// Check whether date/time process or not
			//#CM575446
			// Create BaseOperate instance
			BaseOperate baseOperate = (BaseOperate)PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);
			
			//#CM575447
			// Check whether daily update process or not
			if (baseOperate.isLoadingDailyUpdate())
			{
				//#CM575448
				// Return status flag 5 : Daily update
				return RFTId5731.ANS_CODE_DAILY_UPDATING;
			}

			WareNaviSystemHandler wmsHandler = new WareNaviSystemHandler(wConn);
			WareNaviSystem[] wms = (WareNaviSystem[])wmsHandler.find(new WareNaviSystemSearchKey());

			if (wms[0].getStockPack().equals(WareNaviSystem.PACKAGE_FLAG_ADDON))
			{
				//#CM575449
				// If stock package exist
				CompleteWithStockManagement(
						workerCode,
						rftNo,
						consignorCode,
						locationNo,
						janCode,
						useByDate,
						resultQty,
						workTime);
			}
			else
			{
				//#CM575450
				// If stock package does not exist
				CompleteWithOutStockManagement(
						workerCode,
						rftNo,
						consignorCode,
						consignorName,
						locationNo,
						janCode,
						itemName1,
						enteringQty,
						bundleEntQty,
						itf,
						bundleItf,
						useByDate,
						resultQty,
						workTime);
			}
						
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

			//#CM575451
			// Response flag : Error
			errorDetails = RFTId5731.ErrorDetails.INSTACIATE_ERROR;
			return RFTId5731.ANS_CODE_ERROR;
		}
		catch (NotFoundException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							 " LocationNo:" + locationNo +
							 " JANCode:" + janCode +
							 " UseByDate:" + useByDate +"]";
			//#CM575452
			// 6026016=No data you try to update is found. {0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);			
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				//#CM575453
				// 6006002=Database error occurred.{0}
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM575454
			// Response flag:No corresponding stock
			return RFTId5731.ANS_CODE_NULL ;			
		}
		catch (AllocateException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
			 				 " LocationNo:" + locationNo +
							 " JANCode:" + janCode +
							 " UseByDate:" + useByDate +"]";
			//#CM575455
			// 6026016=No data you try to update is found. {0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);			
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				//#CM575456
				// 6006002=Database error occurred.{0}
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM575457
			// Response flag:Relocation possible qty exceeds
			return RFTId5731.ANS_CODE_MOVEMENT_OVER ;			
		}
		catch (LockTimeOutException e)
		{			
			//#CM575458
			// SELECT FOR UPDATE timeout
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				//#CM575459
				// 6006002=Database error occurred.{0}
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM575460
			// Response flag:Under maintenance in another terminal
			return RFTId5731.ANS_CODE_MAINTENANCE ;
		}
		//#CM575461
		// Since there is a data access error
		catch (ReadWriteException e)
		{
			//#CM575462
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
			//#CM575463
			// Error details
			if(errorDetails.equals(RFTId5731.ErrorDetails.NORMAL))
			{
				errorDetails = RFTId5731.ErrorDetails.DB_ACCESS_ERROR;
			}

			//#CM575464
			// Response flag : Error
			return RFTId5731.ANS_CODE_ERROR ;
		}
		catch (InvalidDefineException e)
		{
			//#CM575465
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
			//#CM575466
			// Error details
			errorDetails = RFTId5731.ErrorDetails.PARAMETER_ERROR;
			//#CM575467
			// Response flag : Error
			return RFTId5731.ANS_CODE_ERROR;
		}
		catch (DataExistsException e)
		{
			//#CM575468
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
			errorDetails = RFTId5731.ErrorDetails.DB_UNIQUE_KEY_ERROR;
			//#CM575469
			// Response flag : Error
			return RFTId5731.ANS_CODE_ERROR;
			
		}
		catch (SQLException e)
		{
			//#CM575470
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
			errorDetails = RFTId5731.ErrorDetails.DB_ACCESS_ERROR;
			//#CM575471
			// Response flag : Error
			return RFTId5731.ANS_CODE_ERROR;
			
		}
		//#CM575472
		// Other error occurred
		catch (Exception e)
		{
			//#CM575473
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
			//#CM575474
			// Error details
			errorDetails = RFTId5731.ErrorDetails.INTERNAL_ERROR;
			//#CM575475
			// Response flag : Error
			return RFTId5731.ANS_CODE_ERROR ;
		}
		
		return RFTId5731.ANS_CODE_NORMAL;
	}

	//#CM575476
	/**
	 * This is the relocation picking result data (ID0731) process. [Stock package exists]<BR>
	 * Decide target stock from telegraph data, call allocation process and create Work info<BR>
	 * <BR>
	 * Lock target data in stock info (exclusion process)<BR>
	 * If time out error occurs during lock (in case of redundant access)<BR>
	 * Error response (allocation process in another terminal)<BR>
	 * Update stock info (allocation process). Based on allocation order, decide allocation qty of the traget stock <BR>
	 * Create relocation work info. Create relocation work info. for allocation result<BR>
	 * Update worker result. Search the target data and add work qty, work count<BR>
	 * <BR>
	 * Stock info lock<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *    <LI>Location no.=Origin location of telegram data</LI>
	 *    <LI>Item code=JAN code of telegram data</LI>
	 *    <LI>Stock status=2:Center stock</LI>
	 *    <LI>Expiry date=Expiry date of telegram data</LI>
	 *    <LI>Consignor code=Consignor code of telegram data</LI>
	 *    </UL>
	 * </DIR>
	 * <BR>
	 * Stock info allocation process<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *    <LI>Location no.=Origin location of telegram data</LI>
	 *    <LI>Item code=JAN code of telegram data</LI>
	 *    <LI>Stock status=2:Center stock</LI>
	 *    <LI>Consignor code=Consignor code of telegram data</LI>
	 *    </UL>
	 *    (Sort order) Allocation order
	 *    <UL>
	 *    <LI>Case piece type</LI>
	 *    <LI>Expiry date</LI>
	 *    <LI>Storage date</LI>
	 *    </UL>
	 *    Allocate stock in the above order, keeping the allocation possible qty (stock qty - allocation qty)<BR>
	 *    divide result qty and adjust allocation qty.
	 * </DIR>
	 * <BR>
	 * Create relocation work info<BR>
	 * <BR>
	 * Update worker result info<BR>
	 * <DIR>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Work complete date/time</TD>	<TD>Current system date</TD></TR>
	 *      <TR><TD>Job type</TD>		<TD>11:Relocation picking</TD></TR>
	 *      <TR><TD>Work qty</TD>		<TD>+Relocation picking result qty</TD></TR>
	 *      <TR><TD>Work count</TD>		<TD>+1 </TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * If worker result does not exist (If daily update in process, etc),
	 * create new worker result info<BR>
	 * <BR>
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @param	consignorCode	Consignor code
	 * @param	locationNo		Origin location
	 * @param	janCode			JAN code
	 * @param	useByDate		Expiry date
	 * @param	resultQty		Relocation picking result qty
	 * @param  workTime		work time
	 * @throws InvalidDefineException If the specified value is abnormal (empty, illegal characters)
	 * @throws LockTimeOutException If the database lock is not removed after specified time
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws DataExistsException If similar data already exist while trying to add relocation info
	 * @throws NotFoundException If target data does not exist for update
	 * @throws AllocateException If allocation is done more than allocatable qty
	*/
	public void CompleteWithStockManagement(
			String workerCode,
			String rftNo,
			String consignorCode,
			String locationNo,
			String janCode,
			String useByDate,
			int    resultQty,
			int    workTime) 
		throws 
			InvalidDefineException, 
			LockTimeOutException, 
			ReadWriteException, 
			DataExistsException, 
			NotFoundException,
			AllocateException
	{
		try
		{
			StockAllocateOperator stockAllocateOperator = 
				new StockAllocateOperator(WmsParam.WMS_DB_LOCK_TIMEOUT);
	
			//#CM575477
			// Stock info lock
			String[] checkLocationNo = { locationNo };
			Stock[] stock0 = stockAllocateOperator.stockSearchForUpdate(
					wConn,
					consignorCode,
					checkLocationNo,
					janCode,
					useByDate );
			//#CM575478
			// Throw NotFoundException if stock info can't be found
			if( stock0.length == 0 )
			{
				throw (new NotFoundException());
			}
			
			//#CM575479
			// Stock allocation process
			Stock[] stock = stockAllocateOperator.stockMovementAllocate(
					wConn,
					consignorCode,
					locationNo,
					janCode,
					useByDate,
					(long)resultQty,
					PROCESS_NAME );
			
			//#CM575480
			// Throw AllocateException if there is shortage in allocatable qty
			if( stock.length == 0 )
			{

				for(int i = 0; i < stock0.length; i++)
				{
					storageQty += stock0[i].getAllocationQty();
				}
				throw (new AllocateException());
			}		
		
			//#CM575481
			// Work info create
			createMovement( workerCode, rftNo, stock );
	
			//#CM575482
			// worker result update(create)
			createWorkerResult( workerCode, rftNo, resultQty ,workTime);
		}
		catch (LockTimeOutException e)
		{
			//#CM575483
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DMSTOCK");
			throw e;
		}
		catch (InvalidDefineException e)
		{
			//#CM575484
			// if the value is abnormal
			String errData =
				"<ConsignorCode[" + consignorCode
					+ "] locationNo[" + locationNo
					+ "] itemCode[" + janCode + "]>";
			throw new InvalidDefineException(errData);
		}
	}

	//#CM575485
	/**
	 * This is the relocation picking result data (ID0731) process. [Stock package does not exist]<BR>
	 * Decide target stock from telegraph data, call allocation process and create Work info<BR>
	 * <BR>
	 * Create relocation work info. Create relocation work info from telegraph data<BR>
	 * Update worker result. Search the target data and add work qty, work count<BR>
	 * <BR>
	 * Create relocation work info<BR>
	 * <BR>
	 * Update worker result info<BR>
	 * <DIR>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Work complete date/time</TD>	<TD>Current system date</TD></TR>
	 *      <TR><TD>Job type</TD>		<TD>11:Relocation picking</TD></TR>
	 *      <TR><TD>Work qty</TD>		<TD>+Relocation picking result qty</TD></TR>
	 *      <TR><TD>Work count</TD>		<TD>+1 </TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * If worker result does not exist (daily update in process, etc) ,
	 * Create worker result info<BR>
	 * <BR>
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @param	consignorCode		Consignor code
	 * @param	consignorName		Consignor name
	 * @param	locationNo		Origin location
	 * @param	janCode			JAN code
	 * @param	itemName1		Item name
	 * @param	enteringQty		Qty per case
	 * @param	bundleEntQty		Qty per bundle
	 * @param	itf			Case ITF
	 * @param	bundleItf		Bundle ITF
	 * @param	useByDate		Expiry date
	 * @param	resultQty		Relocation picking result qty
	 * @param	workTime		work time
	 * @throws DataExistsException If similar data already exist while trying to add relocation info
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	public void CompleteWithOutStockManagement(
			String workerCode,
			String rftNo,
			String consignorCode,
			String consignorName,
			String locationNo,
			String janCode,
			String itemName1,
			int    enteringQty,
			int    bundleEntQty,
			String itf,
			String bundleItf,
			String useByDate,
			int    resultQty,
			int    workTime) throws DataExistsException, ReadWriteException
	{		
		//#CM575486
		// Create relocation work info
		createMovement(
				workerCode,
				rftNo,
				consignorCode,
				consignorName,
				locationNo,
				janCode,
				itemName1,
				enteringQty,
				bundleEntQty,
				itf,
				bundleItf,
				useByDate,
				resultQty);

		//#CM575487
		// worker result update(create)
		createWorkerResult( workerCode, rftNo, resultQty, workTime );
	}

	//#CM575488
	// Package methods -----------------------------------------------

	//#CM575489
	// Protected methods ---------------------------------------------
	//#CM575490
	/**
	 * Create new relocation work info record<BR>
	 * Use, if stock package exist<BR>
	 * <DIR>
	 *    (create details)
	 *    <TABLE>
	 *      <TR><TD>Job no.</TD>							<TD>Customization (record position)</TD></TR>
	 *      <TR><TD>Job type</TD>						<TD>12:Relocation storage</TD></TR>
	 *      <TR><TD>Collect Job no.</TD>						<TD>Customization Work position</TD></TR>
	 *      <TR><TD>Stock id</TD>							<TD>Allocation stock id</TD></TR>
	 *      <TR><TD>Status flag</TD>						<TD>1:Waiting for storage</TD></TR>
	 *      <TR><TD>Work start flag</TD>					<TD>1:Started</TD></TR>
	 *      <TR><TD>Work date</TD>							<TD>System definition</TD></TR>
	 *      <TR><TD>Work plan qty</TD>						<TD>Allocation qty</TD></TR>
	 *      <TR><TD>Work result qty</TD>						<TD>0</TD></TR>
	 *      <TR><TD>Work pending qty</TD>						<TD>0</TD></TR>
	 *      <TR><TD>Work result area no.</TD>				<TD>(Empty)</TD></TR>
	 *      <TR><TD>Work result location</TD>			<TD>(Empty)</TD></TR>
	 *      <TR><TD>Work not reported flag</TD>				<TD>0:Not send</TD></TR>
	 *      <TR><TD>Report flag</TD>					<TD>(Empty)</TD></TR>
	 *      <TR><TD>Batch no.</TD>						<TD>(Empty)</TD></TR>
	 *      <TR><TD>Worker code</TD>					<TD>(Empty)</TD></TR>
	 *      <TR><TD>Terminal no, RFT no.</TD>					<TD>(Empty)</TD></TR>
	 *      <TR><TD>Picking worker code</TD>				<TD>Worker code from telegraph data</TD></TR>
	 *      <TR><TD>Picking worker name</TD>					<TD>Fetch worker code in telegraph data</TD></TR>
	 *      <TR><TD>Picking terminal no., RFT no.</TD>				<TD>Handy no. from telegraph id</TD></TR>
	 *      <TR><TD>Registration date/time</TD>						<TD>system date/time</TD></TR>
	 *      <TR><TD>Registering process name</TD>						<TD>ID0731</TD></TR>
	 *      <TR><TD>Last update date/time</TD>					<TD>system date/time</TD></TR>
	 *      <TR><TD>Last update Process name</TD>					<TD>ID0731</TD></TR>
	 *    </TABLE>
	 *   Fetch any other items other than above from allocation stock info
	 *   Based on allocation result, create multiple records (same as allocation stock record)<BR>
	 * </DIR>
	 * @param workerCode	Worker code
	 * @param rftNo		RFTNo
	 * @param stock		allocation stock info list
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws DataExistsException If similar data already exist while trying to add relocation info
	 */
	protected void createMovement(String workerCode, String rftNo, Stock[] stock) throws  ReadWriteException, DataExistsException
	{
		SequenceHandler sh = new SequenceHandler(wConn);
		String collectJobNo = "";
		MovementHandler movementHandler = new MovementHandler(wConn);

		BaseOperate bo = new BaseOperate(wConn);
		//#CM575491
		// Fetch system work date
		String workingDate = "";
		try
		{
			workingDate = bo.getWorkingDate();
		}
		catch (NotFoundException e)
		{
			errorDetails = RFTId5731.ErrorDetails.NULL;
			//#CM575492
			// If work date can't be retrieved from system define infothrow ReadWriteException
			throw (new ReadWriteException());
		}
		//#CM575493
		// Fetch worker name
		String workerName = "";
		try
		{
			workerName = bo.getWorkerName(workerCode);
		}
		catch (NotFoundException e1)
		{
			//#CM575494
			// If worker name can't be retrieved, create log and proceed
			//#CM575495
			// 6004500=Unregistered user ID is entered: User ID={0}
			String errData = "[" + workerCode + "]";
			RftLogMessage.print(6004500, LogMessage.F_WARN, CLASS_NAME, errData);
		}
		
		for( int i=0; i < stock.length; i++ )
		{
			Movement movement = new Movement();
			movement.setJobNo( sh.nextJobNo() );
			if( i == 0 )
			{
				collectJobNo = movement.getJobNo();
			}
			movement.setJobType( Movement.JOB_TYPE_MOVEMENT_STORAGE );
			movement.setCollectJobNo( collectJobNo );
			movement.setStockId( stock[i].getStockId() );
			movement.setAreaNo( stock[i].getAreaNo() );
			movement.setLocationNo( stock[i].getLocationNo() );
			movement.setStatusFlag( Movement.STATUSFLAG_UNSTART );
			movement.setBeginningFlag( Movement.BEGINNING_FLAG_STARTED );
			movement.setWorkDate( workingDate );
			movement.setConsignorCode( stock[i].getConsignorCode() );
			movement.setConsignorName( stock[i].getConsignorName() );
			movement.setSupplierCode( stock[i].getSupplierCode() );
			movement.setSupplierName1( stock[i].getSupplierName1() );
			movement.setItemCode( stock[i].getItemCode() );
			movement.setItemName1( stock[i].getItemName1() );
			movement.setPlanQty( stock[i].getAllocationQty() );
			movement.setResultQty( 0 );
			movement.setShortageCnt( 0 );
			movement.setEnteringQty( stock[i].getEnteringQty() );
			movement.setBundleEnteringQty( stock[i].getBundleEnteringQty() );
			movement.setCasePieceFlag( stock[i].getCasePieceFlag() );
			movement.setWorkFormFlag( stock[i].getCasePieceFlag() );
			movement.setItf( stock[i].getItf() );
			movement.setBundleItf( stock[i].getBundleItf() );
			movement.setUseByDate( stock[i].getUseByDate() );
			movement.setLotNo( stock[i].getLotNo() );
			movement.setPlanInformation( stock[i].getPlanInformation() );
			movement.setReportFlag( Movement.REPORT_FLAG_NOT_SENT );
			movement.setRetrievalWorkerCode( workerCode );
			movement.setRetrievalWorkerName( workerName );
			movement.setRetrievalTerminalNo( rftNo );
			movement.setRegistPname( PROCESS_NAME );
			movement.setLastUpdatePname( PROCESS_NAME );
			
			movementHandler.create( movement );
		}
	}

	//#CM575496
	/**
	 * Create new relocation work info record<BR>
	 * Use if stock package does not exist<BR>
	 * <DIR>
	 *    (create details)
	 *    <TABLE>
	 *      <TR><TD>Job no.</TD>							<TD>Customization (record position)</TD></TR>
	 *      <TR><TD>Job type</TD>						<TD>12:Relocation storage</TD></TR>
	 *      <TR><TD>Collect Job no.</TD>						<TD>CustomizationWork position</TD></TR>
	 *      <TR><TD>Location no.</TD>					<TD>Fetch from telegraph data</TD></TR>
	 *      <TR><TD>Status flag</TD>						<TD>1:Waiting for storage</TD></TR>
	 *      <TR><TD>Work start flag</TD>					<TD>1:Started</TD></TR>
	 *      <TR><TD>Work date</TD>							<TD>System definition</TD></TR>
	 *      <TR><TD>Consignor code</TD>						<TD>Fetch from telegraph data</TD></TR>
	 *      <TR><TD>Consignor name</TD>						<TD>Fetch from telegraph data</TD></TR>
	 *      <TR><TD>Item code</TD>						<TD>Fetch from telegraph data</TD></TR>
	 *      <TR><TD>Item name</TD>						<TD>Fetch from telegraph data</TD></TR>
	 *      <TR><TD>Work plan qty</TD>						<TD>Fetch from telegraph data</TD></TR>
	 *      <TR><TD>Work result qty</TD>						<TD>0</TD></TR>
	 *      <TR><TD>Work pending qty</TD>						<TD>0</TD></TR>
	 *      <TR><TD>Qty per case</TD>						<TD>Fetch from telegraph data</TD></TR>
	 *      <TR><TD>Qty per bundle</TD>						<TD>Fetch from telegraph data</TD></TR>
	 *      <TR><TD>Case ITF</TD>						<TD>Fetch from telegraph data</TD></TR>
	 *      <TR><TD>Bundle ITF</TD>						<TD>Fetch from telegraph data</TD></TR>
	 *      <TR><TD>Expiry date</TD>						<TD>Fetch from telegraph data</TD></TR>
	 *      <TR><TD>Expiry date(Result)</TD>				<TD>Fetch from telegraph data</TD></TR>
	 *      <TR><TD>Work not reported flag</TD>				<TD>0:Not send</TD></TR>
	 *      <TR><TD>Picking worker code</TD>				<TD>Worker code from telegraph data</TD></TR>
	 *      <TR><TD>Picking worker name</TD>					<TD>Fetch worker code in telegraph data</TD></TR>
	 *      <TR><TD>Picking terminal no., RFT no.</TD>				<TD>Handy no. from telegraph id</TD></TR>
	 *      <TR><TD>Registration date/time</TD>						<TD>system date/time</TD></TR>
	 *      <TR><TD>Registering process name</TD>						<TD>ID0731</TD></TR>
	 *      <TR><TD>Last update date/time</TD>					<TD>system date/time</TD></TR>
	 *      <TR><TD>Last update Process name</TD>					<TD>ID0731</TD></TR>
	 *    </TABLE>
	 *   Make items other than above, empty
	 * </DIR>
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @param	consignorCode		Consignor code
	 * @param	consignorName		Consignor name
	 * @param	locationNo		Origin location
	 * @param	janCode			JAN code
	 * @param	itemName1		Item name
	 * @param	enteringQty		Qty per case
	 * @param	bundleEntQty		Qty per bundle
	 * @param	itf			Case ITF
	 * @param	bundleItf		Bundle ITF
	 * @param	useByDate		Expiry date
	 * @param	resultQty		Relocation picking result qty
	 * @throws DataExistsException If similar data already exist while trying to add relocation info
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	protected void createMovement(
			String workerCode,
			String rftNo,
			String consignorCode,
			String consignorName,
			String locationNo,
			String janCode,
			String itemName1,
			int    enteringQty,
			int    bundleEntQty,
			String itf,
			String bundleItf,
			String useByDate,
			int    resultQty) throws DataExistsException, ReadWriteException
	{
		SequenceHandler sh = new SequenceHandler(wConn);
		BaseOperate bo = new BaseOperate(wConn);
		//#CM575497
		// Fetch system work date
		String workingDate = "";
		try
		{
			workingDate = bo.getWorkingDate();
		}
		catch (NotFoundException e)
		{
			errorDetails = RFTId5731.ErrorDetails.NULL;
			//#CM575498
			// If work date can't be retrieved from system define infothrow ReadWriteException
			throw (new ReadWriteException());
		}
		//#CM575499
		// Fetch worker name
		String workerName = "";
		try
		{
			workerName = bo.getWorkerName(workerCode);
		}
		catch (NotFoundException e1)
		{
			//#CM575500
			// If worker name can't be retrieved, create log and proceed
			//#CM575501
			// 6004500=Unregistered user ID is entered: User ID={0}
			String errData = "[" + workerCode + "]";
			RftLogMessage.print(6004500, LogMessage.F_WARN, CLASS_NAME, errData);
		}
		
		Movement movement = new Movement();
		movement.setJobNo( sh.nextJobNo() );
		movement.setJobType( Movement.JOB_TYPE_MOVEMENT_STORAGE );
		movement.setCollectJobNo( movement.getJobNo() );
		movement.setAreaNo(Area.Area_VACANTSEARCHTYPE_BANKHORIZONTAL);
		movement.setLocationNo( locationNo );
		movement.setStatusFlag( Movement.STATUSFLAG_UNSTART );
		movement.setBeginningFlag( Movement.BEGINNING_FLAG_STARTED );
		movement.setWorkDate( workingDate );
		movement.setConsignorCode( consignorCode );
		movement.setConsignorName( consignorName );
		movement.setItemCode( janCode );
		movement.setItemName1( itemName1 );
		movement.setPlanQty( resultQty );
		movement.setResultQty( 0 );
		movement.setShortageCnt( 0 );
		movement.setEnteringQty( enteringQty );
		movement.setBundleEnteringQty( bundleEntQty );
		movement.setCasePieceFlag( Movement.CASEPIECE_FLAG_NOTHING );
		movement.setWorkFormFlag( Movement.CASEPIECE_FLAG_NOTHING );
		movement.setItf( itf );
		movement.setBundleItf( bundleItf );
		movement.setUseByDate( useByDate );
		movement.setReportFlag( Movement.REPORT_FLAG_NOT_SENT );
		movement.setRetrievalWorkerCode( workerCode );
		movement.setRetrievalWorkerName( workerName );
		movement.setRetrievalTerminalNo( rftNo );
		movement.setRegistPname( PROCESS_NAME );
		movement.setLastUpdatePname( PROCESS_NAME );
		
		MovementHandler movementHandler = new MovementHandler(wConn);
		movementHandler.create( movement );
	}

	//#CM575502
	/**
	 * Update worker result<BR>
	 * If target worker result does not exist, create new<BR>
	 * <DIR>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Work complete date/time</TD>		<TD>Current system date</TD></TR>
	 *      <TR><TD>Work qty</TD>			<TD>+Work completed result qty</TD></TR>
	 *      <TR><TD>Work count</TD>			<TD>+1 </TD></TR>
	 *      <TR><TD>Work count (Order qty)	</TD><TD>+1</TD></TR>
	 *      <TR><TD>work time</TD>			<TD>+work time of parameter</TD></TR>
	 *      <TR><TD>result work time</TD>			<TD>+work time of parameter</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @param	resultQty		Relocation picking result qty
	 * @param	workTime		work time
	 * @throws ReadWriteException If abnormal error occurs in database connection
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
			errorDetails = RFTId5731.ErrorDetails.NULL;
			//#CM575503
			// If work date can't be retrieved from system define info
			//#CM575504
			// throw ReadWriteException
			throw (new ReadWriteException());
		}

		//#CM575505
		// update result by worker
		WorkerResult wr = new WorkerResult();
		wr.setWorkDate(workingDate);
		wr.setWorkerCode(workerCode);
		wr.setTerminalNo(rftNo);
		wr.setJobType(WorkerResult.JOB_TYPE_MOVEMENT_RETRIEVAL);
		wr.setWorkCnt(1);
		wr.setOrderCnt(1);
		wr.setWorkTime(workTime);
		wr.setRealWorkTime(workTime);
		wr.setWorkQty(resultQty);
		try
		{
			bo.alterWorkerResult(wr);
		}
		catch (NotFoundException e)
		{
			//#CM575506
			// If target worker result does not exist, create new
			//#CM575507
			// (If daily update is in process in RFT)
			//#CM575508
			// 6022004=No matching Worker Result Data is found. A new data will be created. {0}
			String errData = "[WorkDate:" + workingDate +
			 				 " JobType:" + WorkerResult.JOB_TYPE_MOVEMENT_RETRIEVAL +
							 " RftNo:" + rftNo +
							 " WorkerCode:" + workerCode +"]";
			RftLogMessage.print(6022004, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				//#CM575509
				// create worker result
				bo.createWorkerResult(WorkerResult.JOB_TYPE_MOVEMENT_RETRIEVAL, workerCode, rftNo);
				//#CM575510
				// update worker result
				bo.alterWorkerResult(wr);
			}
			catch(NotFoundException e1)
			{
				errorDetails = RFTId5731.ErrorDetails.NULL;
				throw (new ReadWriteException());
			}
			catch (Exception e1)
			{
				//#CM575511
				// If exception occurs throw ReadWriteException
				throw (new ReadWriteException());
			}
		}
	}
	
	//#CM575512
	/**
	 * Fetch error details
	 * @return	Error details
	 */
	public String getErrorDetails()
	{
		return errorDetails;
	}
	
	//#CM575513
	/**
	 * Fetch Stock qty available for picking
	 * @return Stock qty available for picking
	 */
	public int getStorageQty()
	{
		return storageQty;
	}
	//#CM575514
	// Private methods -----------------------------------------------
	
}
//#CM575515
//end of class
