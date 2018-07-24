// $Id: Id0741Operate.java,v 1.2 2006/12/07 09:00:10 suresh Exp $
package jp.co.daifuku.wms.storage.rft;
//#CM575820
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
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ShelfInvalidityException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.MovementAlterKey;
import jp.co.daifuku.wms.base.dbhandler.MovementHandler;
import jp.co.daifuku.wms.base.dbhandler.MovementSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.storage.schedule.MovementCompleteOperator;
import jp.co.daifuku.wms.storage.schedule.StockAllocateOperator;


//#CM575821
/**
 * Designer : K.Shimizu <BR>
 * Maker : K.Shimizu <BR>
 * <BR>
 * This is the Relocation storage result data send process from RFT<BR>
 * Execute relocation work info update and stock info update<BR>
 * Execute the business logic from Id0741Process<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/11</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:10 $
 * @author  $Author: suresh $
 */
public class Id0741Operate extends IdOperate
{
	//#CM575822
	// Class variables -----------------------------------------------
	//#CM575823
	/**
	 * Process name
	 */
	private static final String PROCESS_NAME = "ID0741";
	//#CM575824
	/**
	 * Previous process name (data request process)
	 */
	private static final String START_PROCESS_NAME = "ID0740";
	//#CM575825
	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "Id0741Operate";
	
	//#CM575826
	/**
	 * Error details
	 */
	private String errDetails = RFTId5741.ErrorDetails.NORMAL;

	//#CM575827
	// Class method --------------------------------------------------
	//#CM575828
	/**
	 * Return the version of this class
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/12/07 09:00:10 $";
	}
	//#CM575829
	// Constructors --------------------------------------------------
	//#CM575830
	// Public methods ------------------------------------------------
	//#CM575831
	/**
	 * Relocation storage result data (ID0741) process<BR>
	 * Based on the telegraph data's completion flag, decide commit or cancel process<BR>
	 * <BR>
	 * <DIR>
	 *      <UL>
	 *      <LI>completion flag 0:Normal			[Commit]</LI>
	 *      <LI>completion flag 9:cancel [cancel process]</LI>
	 *      </UL>
	 * </DIR>
	 * @param	collectJobNo	Collect Job no.
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @param	areaNo			Relocation origin area
	 * @param	locationNo		Origin location
	 * @param	consignorCode		Consignor code
	 * @param	janCode			JAN code
	 * @param	useByDate		Expiry date
	 * @param	resultQty		Actual relocation storage result qty
	 * @param	resAreaNo		Destination area
	 * @param	resLocationNo		Destination location
	 * @param	completionFlag		completion flag
	 * @param	workTime		work time
	 * @return	Response id Response flag
	 */
	public String doComplete(
		String collectJobNo,
		String workerCode,
		String rftNo,
		String areaNo,
		String locationNo,
		String consignorCode,
		String janCode,
		String useByDate,
		int    resultQty,
		String resAreaNo,
		String resLocationNo,
		String completionFlag,
		int    workTime)
	{
		try
		{
			//#CM575832
			// If the flag received is 9 : cancel
			if(completionFlag.equals(RFTId0741.COMPLETION_FLAG_CANCEL))
			{
				//#CM575833
				// Update status flag
				moveStorageCancel(collectJobNo, workerCode, rftNo);
			}
			//#CM575834
			// If the flag received is other than cancel
			else
			{

				if(DisplayText.isPatternMatching(resAreaNo))
				{
					throw new InvalidDefineException("AreaNo[" + resAreaNo +"]");
				}
				if(DisplayText.isPatternMatching(resLocationNo))
				{
					throw new InvalidDefineException("LocationNo[" + resLocationNo +"]");
				}

				//#CM575835
				// Completion process
				moveStorageComplete(
						collectJobNo,
						workerCode,
						rftNo,
						areaNo,
						locationNo,
						consignorCode,
						janCode,
						useByDate,
						resultQty,
						resAreaNo,
						resLocationNo,
						workTime);
			}
		}
		catch (LockTimeOutException e)
		{			
			//#CM575836
			// SELECT FOR UPDATE timeout
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				//#CM575837
				// 6006002=Database error occurred.{0}
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM575838
			// Response flag:Under maintenance in another terminal
			return RFTId5741.ANS_CODE_MAINTENANCE ;
		}
		//#CM575839
		// If the info is not found during search
		catch (NotFoundException e)
		{
			String errData = "[Collect_Job_No:" + collectJobNo +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			//#CM575840
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

			//#CM575841
			// Response flag : Error
			errDetails = RFTId5741.ErrorDetails.NULL;
			return RFTId5741.ANS_CODE_ERROR ;
		}
		//#CM575842
		// If the data is already updated in another terminal
		catch (UpdateByOtherTerminalException e)
		{
			String errData = "[Collect_Job_No:" + collectJobNo +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			//#CM575843
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

			//#CM575844
			// Response flag : Error
			errDetails = RFTId5741.ErrorDetails.UPDATE_FINISH;
			return RFTId5741.ANS_CODE_ERROR ;
		}
		//#CM575845
		// Since there is a data access error
		catch (ReadWriteException e)
		{
			//#CM575846
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

			//#CM575847
			// Response flag : Error
			errDetails = RFTId5741.ErrorDetails.DB_ACCESS_ERROR;
			return RFTId5741.ANS_CODE_ERROR ;
		}
		//#CM575848
		// If overflow error occurs during stock creation
		catch (OverflowException e)
		{
			String errData = "[Collect_Job_No:" + collectJobNo +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			//#CM575849
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

			//#CM575850
			// Response flag : Error
			errDetails = RFTId5741.ErrorDetails.OVERFLOW;
			return RFTId5741.ANS_CODE_ERROR ;
		}
		//#CM575851
		// Other error occurred
		catch (InvalidDefineException e)
		{
			//#CM575852
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
			//#CM575853
			// Response flag : Error
			errDetails = RFTId5741.ErrorDetails.PARAMETER_ERROR;
			return RFTId5741.ANS_CODE_ERROR;
		}
		catch (DataExistsException e)
        {
			//#CM575854
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
			errDetails = RFTId5741.ErrorDetails.DB_UNIQUE_KEY_ERROR;
			return RFTId5741.ANS_CODE_ERROR;
        }
		catch (ScheduleException e)
		{
			//#CM575855
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
			//#CM575856
			// Response flag : Error
			errDetails = RFTId5741.ErrorDetails.SCHEDULE_ERROR;
			return RFTId5741.ANS_CODE_ERROR ;
			
		}
		catch (ShelfInvalidityException e)
		{

		    String errData = " [LocationNo:" + resLocationNo +
		    				 " RftNo:" + rftNo +
							 " WorkerCode:" + workerCode + "]";
		    
			//#CM575857
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
			//#CM575858
			// Error details
			errDetails = RFTId5741.ErrorDetails.SHELF_INVALIDITY;
			return RFTId5741.ANS_CODE_ERROR;
		}
		catch (Exception e)
		{
			//#CM575859
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

			//#CM575860
			// Response flag : Error
			errDetails = RFTId5741.ErrorDetails.INTERNAL_ERROR;
			return RFTId5741.ANS_CODE_ERROR ;
		}
		
		return RFTId5741.ANS_CODE_NORMAL;
	}

	//#CM575861
	/**
	 * Relocation storage result data [Commit process]<BR>
	 * The following commit process is done for relocation storage result info<BR>
	 * <BR>
	 * Lock target data in stock info (exclusion process)<BR>
	 * If time out error occurs during lock (in case of redundant access),
	 * Error response (allocation process in another terminal)<BR>
	 * Based on destination location no., search AS/RS location info. If target data exist (automated warehouse location instructed)
	 * Return error response (input location not available)<BR>
	 * relocation work info update. Search corresponding data and change status flag to complete. Set values like destination location no.<BR>
	 * If Work plan qty > Work result qty, make Work plan qty = pending qty and create new data (data division)<BR>
	 * After creating new data, update work plan qty of relocation work info to work result qty<BR>
	 * Make Relocation work info completion data as Work plan qty=Work result qty<BR>
	 * Update Stock info. Calcualte relocation destination stock qty, origin stock qty and subtract from Allocation qty<BR>
	 * (If stock already exist in the destination, append to it. Else create new stock)<BR>
	 * Create result send info. Once Relocation storage is complete, create result<BR>
	 * Update worker result. Search the target data and add work qty, work count<BR>
	 * <BR>
	 * Stock info lock (Relocation origin)<BR>
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
	 * Stock info lock (Relocation destination)<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *    <LI>Location no.=Destination location of telegram data</LI>
	 *    <LI>Item code=JAN code of telegram data</LI>
	 *    <LI>Stock status=2:Center stock</LI>
	 *    <LI>Expiry date=Expiry date of telegram data</LI>
	 *    <LI>Consignor code=Consignor code of telegram data</LI>
	 *    </UL>
	 * </DIR>
	 * <BR>
	 * relocation work info update<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Collect Job no.(Product identification code of telegraph data)</LI>
	 *     <LI>Status flag=2:In process</LI>
	 * 	   <LI>Worker code=Worker code(Worker code of telegraph data)</LI>
	 * 	   <LI>terminal no.=RFT no.(RFT no. of telegraph data)</LI>
	 * 	   <LI>Last update Process name="ID0741"</LI>
	 *    </UL>
	 *    (Sort order)
	 *    <UL>
	 *     <LI>Job no.</LI>
	 *    </UL>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Status flag</TD>				<TD>Complete:4</TD></TR>
	 *      <TR><TD>Work plan qty</TD>				<TD>Actual relocation storage result qty from telegram data<BR>
	 *											(if Work plan qty>Actual relocation storage result qty)</TD></TR>
	 *      <TR><TD>Work result qty</TD>				<TD>Actual relocation storage result qty from telegram data</TD></TR>
	 *      <TR><TD>Work result location</TD>	<TD>Destination location of telegram data</TD></TR>
	 *      <TR><TD>Last update Process name</TD>			<TD>"ID0741"</TD></TR>
	 *    </TABLE>
	 *   Consider the probability of multiple records<BR>
	 *   Divide actual Relocation storage from corresponding work result qty. The qty to be divided is Work plan qty<BR>
	 *   In case of surplus, divide the last record<BR>
	 * </DIR>
	 * <BR>
	 * Create relocation work info (Work plan qty > Actual relocation storage result qty)<BR>
	 * Actual relocation storage result qty is short of Work plan qty, divide Relocation Work info<BR>
	 * Relocation Work info before division --- Update Work result qty with Work plan qty<BR>
	 * Relocation Work info after division --- Create a new Work plan qty like (Work plan qty-Work result qty)<BR>
	 * <DIR>
	 *    (create details)
	 *    <TABLE>
	 *      <TR><TD>Job no.</TD>							<TD>Customization (record position)</TD></TR>
	 *      <TR><TD>Status flag</TD>						<TD>1:Waiting for storage</TD></TR>
	 *      <TR><TD>Work plan qty</TD>						<TD>qty before division (Work plan qty-Work result qty)</TD></TR>
	 *      <TR><TD>Work result qty</TD>						<TD>0</TD></TR>
	 *      <TR><TD>Work pending qty</TD>						<TD>0</TD></TR>
	 *      <TR><TD>Work result area no.</TD>				<TD>(Empty)</TD></TR>
	 *      <TR><TD>Work result location</TD>			<TD>(Empty)</TD></TR>
	 *      <TR><TD>Report flag</TD>					<TD>(Empty)</TD></TR>
	 *      <TR><TD>Worker code</TD>					<TD>(Empty)</TD></TR>
	 *      <TR><TD>Terminal no, RFT no.</TD>					<TD>(Empty)</TD></TR>
	 *      <TR><TD>Registration date/time</TD>						<TD>system date/time</TD></TR>
	 *      <TR><TD>Registering process name</TD>						<TD>ID0741</TD></TR>
	 *      <TR><TD>Last update date/time</TD>					<TD>system date/time</TD></TR>
	 *      <TR><TD>Last update Process name</TD>					<TD>ID0741</TD></TR>
	 *    </TABLE>
	 *   Fetch any other field items from Work info before division
	 * </DIR>
	 * <BR>
	 * Update stock info<BR>
	 * Create result send info<BR>
	 * <DIR>
	 *    Relocation Use storage complete method<BR>
	 *    Relocation Update stock info and Create result send info with Storage completion method.<BR>
	 * </DIR>
	 * <BR>
	 * Update worker result info<BR>
	 * <DIR>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Work complete date/time</TD>	<TD>Current system date</TD></TR>
	 *      <TR><TD>Job type</TD>		<TD>12:Relocation storage</TD></TR>
	 *      <TR><TD>Work qty</TD>		<TD>+Actual relocation storage result qty</TD></TR>
	 *      <TR><TD>Work count</TD>		<TD>+1 </TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * @param	collectJobNo	Collect Job no.
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @param	fmAreaNo		Relocation origin area
	 * @param	fmLocationNo	Origin location
	 * @param	consignorCode	Consignor code
	 * @param	janCode			JAN code
	 * @param	useByDate		Expiry date
	 * @param	resultQty		Actual relocation storage result qty
	 * @param	resAreaNo		Destination area
	 * @param	resLocationNo	Destination location
	 * @param  workTime		work time
	 * @throws NotFoundException If target data does not exist for update
	 * @throws InvalidDefineException If the specified value is abnormal (empty, illegal characters)
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws DataExistsException If similar data already exist while trying to add relocation info
	 * @throws UpdateByOtherTerminalException If in process data is updated in another terminal
	 * 			There are cases that target data can't be found based on update and NotFoundException is thrown
	 * @throws LockTimeOutException If the database lock is not removed after specified time
	 * @throws OverflowException If the item count exceeds limit
	 * @throws ShelfInvalidityException If the input location is not valid
	 * @throws ScheduleException If there is any abnormal error in Relocation storage completion process
	 */
	public void moveStorageComplete(
			String collectJobNo,
			String workerCode,
			String rftNo,
			String fmAreaNo,
			String fmLocationNo,
			String consignorCode,
			String janCode,
			String useByDate,
			int    resultQty,
			String resAreaNo,
			String resLocationNo,
			int workTime)
		throws 
			NotFoundException, 
			InvalidDefineException, 
			ReadWriteException, 
			DataExistsException, 
			UpdateByOtherTerminalException,
			LockTimeOutException,
			OverflowException, 
			ScheduleException,
			ShelfInvalidityException
	{
		try
		{
			LocateOperator lOperator = new LocateOperator(wConn);
			if(lOperator.isAsrsLocation(resLocationNo))
			{
				//#CM575862
				// 6023443=No.{0} The specified location is in automatic warehouse. Cannot enter.
				throw new ShelfInvalidityException();
			}
			
			//#CM575863
			// Stock info lock (relocation origin, relocation destination)
			String[] locationNo = { fmLocationNo, resLocationNo };
			StockAllocateOperator stockAllocateOperator = 
				new StockAllocateOperator(WmsParam.WMS_DB_LOCK_TIMEOUT);
			stockAllocateOperator.stockSearchForUpdate(
					wConn,
					consignorCode,
					locationNo,
					janCode,
					useByDate );
			
			//#CM575864
			// relocation work info update (storage complete process)
			updateMovement(collectJobNo,workerCode,rftNo, useByDate, resultQty,resAreaNo,resLocationNo);
			
			//#CM575865
			// worker result update (create)
			createWorkerResult( workerCode, rftNo, resultQty, workTime );
		}
		catch (LockTimeOutException e)
		{
			//#CM575866
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DMSTOCK");
			throw e;
		}
		catch (InvalidDefineException e)
		{
			//#CM575867
			// if the value is abnormal
			String errData =
				"<ConsignorCode[" + consignorCode
					+ "] fromAreaNo[" + fmAreaNo
					+ "] toAreaNo[" + resAreaNo
					+ "] fromLocationNo[" + fmLocationNo
					+ "] toLocationNo[" + resLocationNo
					+ "] itemCode[" + janCode + "]>";
			throw new InvalidDefineException(errData);
		}
	}
	
	//#CM575868
	/**
	 * Relocation storage result data [cancel process] <BR>
	 * Relocation Execute the following cancel process on storage result info<BR>
	 * <BR>
	 * Lock corresponding Relocation Work info data (exclusion process)<BR>
	 * Relocation work info update. Search corresponding data, and update status flag to "Waiting for storage" and terminal no., RFTNo and Worker code to Empty<BR>
	 * <BR>
	 * Lock Relocation Work info<BR>
	 * <DIR>
	 *    Work info<BR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Collect Job no.(Product identification code of telegraph data)</LI>
	 *     <LI>Status flag=2:in storage</LI>
	 *    </UL>
	 * </DIR>
	 * <BR>
	 * relocation work info update<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Collect Job no.(Product identification code of telegraph data)</LI>
	 *     <LI>Status flag=2:in storage</LI>
	 * 	   <LI>Worker code=Worker code(Worker code of telegraph data)</LI>
	 * 	   <LI>terminal no.=RFT no.(RFT no. of telegraph data)</LI>
	 * 	   <LI>Last update Process name='ID0085'</LI>
	 *    </UL>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Status flag</TD>		<TD>1:Waiting for storage</TD></TR>
	 *      <TR><TD>Worker code</TD>	<TD>Empty</TD></TR>
	 *      <TR><TD>worker name</TD>		<TD>Empty</TD></TR>
	 *      <TR><TD>terminal no.</TD>			<TD>Empty</TD></TR>
	 *      <TR><TD>Last update Process name</TD>	<TD>"ID0086"</TD></TR>
	 *    </TABLE>
	 *   Consider the probability of multiple records<BR>
	 * </DIR>
	 * <BR>
	 * @param	collectJobNo	Collect Job no.
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws NotFoundException If target data does not exist for update
	 * @throws InvalidDefineException If the specified value is abnormal (empty, illegal characters)
	 * @throws LockTimeOutException If the database lock is not removed after specified time
	 */
	public void moveStorageCancel(
			String collectJobNo,
			String workerCode,
			String rftNo)
		throws ReadWriteException, NotFoundException, InvalidDefineException, LockTimeOutException
	{
		//#CM575869
		// lock relocation work info
		MovementSearchKey skey = new MovementSearchKey();
		skey.setCollectJobNo( collectJobNo );
		skey.setStatusFlag( Movement.STATUSFLAG_NOWWORKING );
		MovementHandler handler = new MovementHandler(wConn);
		try{
			handler.findForUpdate( skey ,WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM575870
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNMOVEMENT");
			throw e;
		}

		//#CM575871
		// relocation work info update
		MovementAlterKey akey = new MovementAlterKey();
		akey.setCollectJobNo( collectJobNo );
		akey.setStatusFlag( Movement.STATUSFLAG_NOWWORKING );
		akey.setWorkerCode( workerCode );
		akey.setTerminalNo( rftNo );
		akey.setLastUpdatePname( START_PROCESS_NAME );
		akey.updateStatusFlag( Movement.STATUSFLAG_UNSTART );
		akey.updateWorkerCode("");
		akey.updateWorkerName("");
		akey.updateTerminalNo("");
		akey.updateLastUpdatePname( PROCESS_NAME );
		handler.modify( akey );		
	}

	//#CM575872
	// Package methods -----------------------------------------------

	//#CM575873
	// Protected methods ---------------------------------------------
	//#CM575874
	/**
	 * Update Work info <BR>
	 * Search corresponding data and update status flag to complete and set result qty, relocation destination location no. etc.,<BR>
	 * If work plan qty > work result qty, create new data with work plan qty = pending qty. (data division)<BR>
	 * After creating new data, set previous relocation work info's work plan qty to work result qty<BR>
	 * Relocation work info's completion data should be work plan qty = work result qty<BR>
	 * <BR>
	 * relocation work info update<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Collect Job no.(Product identification code of telegraph data)</LI>
	 *     <LI>Status flag=2:In process</LI>
	 * 	   <LI>Worker code=Worker code(Worker code of telegraph data)</LI>
	 * 	   <LI>terminal no.=RFT no.(RFT no. of telegraph data)</LI>
	 * 	   <LI>Last update Process name="ID0741"</LI>
	 *    </UL>
	 *    (Sort order)
	 *    <UL>
	 *     <LI>Job no.</LI>
	 *    </UL>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Status flag</TD>				<TD>Complete:4</TD></TR>
	 *      <TR><TD>Work plan qty</TD>				<TD>Actual relocation storage result qty from telegram data<BR>
	 *											(If Work plan qty > Actual relocation storage result qty)</TD></TR>
	 *      <TR><TD>Work result qty</TD>				<TD>Actual relocation storage result qty from telegram data</TD></TR>
	 *      <TR><TD>Work result location</TD>	<TD>Destination location of telegram data</TD></TR>
	 *      <TR><TD>Last update Process name</TD>			<TD>"ID0741"</TD></TR>
	 *    </TABLE>
	 *   Consider the probability of multiple records<BR>
	 *   Divide the actual Relocation storage from corresponding data's work result qty. The divided qty is work plan qty<BR>
	 *   In case of surplus, divide the last record<BR>
	 * </DIR>
	 * <BR>
	 * Create relocation work info(If Work plan qty > Actual relocation storage result qty)<BR>
	 * If Actual relocation storage result qty is less than work plan qty, divide relocation work info<BR>
	 * Relocation work info before division --- Update work plan qty with work result qty<BR>
	 * Relocation work info after division --- Create Work plan qty (Work plan qty - Work result qty) <BR>
	 * <DIR>
	 *    (create details)
	 *    <TABLE>
	 *      <TR><TD>Job no.</TD>							<TD>Customization (record position)</TD></TR>
	 *      <TR><TD>Status flag</TD>						<TD>1:Waiting for storage</TD></TR>
	 *      <TR><TD>Work plan qty</TD>						<TD>(Work plan qty-Work result qty) before division</TD></TR>
	 *      <TR><TD>Work result qty</TD>						<TD>0</TD></TR>
	 *      <TR><TD>Work pending qty</TD>						<TD>0</TD></TR>
	 *      <TR><TD>Work result area no.</TD>				<TD>(Empty)</TD></TR>
	 *      <TR><TD>Work result location</TD>			<TD>(Empty)</TD></TR>
	 *      <TR><TD>Report flag</TD>					<TD>(Empty)</TD></TR>
	 *      <TR><TD>Worker code</TD>					<TD>(Empty)</TD></TR>
	 *      <TR><TD>Terminal no, RFT no.</TD>					<TD>(Empty)</TD></TR>
	 *      <TR><TD>Registration date/time</TD>						<TD>system date/time</TD></TR>
	 *      <TR><TD>Registering process name</TD>						<TD>ID0741</TD></TR>
	 *      <TR><TD>Last update date/time</TD>					<TD>system date/time</TD></TR>
	 *      <TR><TD>Last update Process name</TD>					<TD>ID0741</TD></TR>
	 *    </TABLE>
	 *   Fetch any items other than the above from work info before division
	 * </DIR>
	 * <BR>
	 * Update stock info<BR>
	 * Create result send info<BR>
	 * <DIR>
	 *    Relocation Use storage complete method<BR>
	 *    Relocation Update stock info and Create result send info with Storage completion method.<BR>
	 * </DIR>
	 * <BR>
	 * @param	collectJobNo	Collect Job no.
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @param	useByDate		Expiry date
	 * @param	rftResultQty	Actual relocation storage result qty
	 * @param	resAreaNo		Destination area
	 * @param	resLocationNo	Destination location
	 * @throws NotFoundException If target data does not exist for update
	 * @throws InvalidDefineException If the specified value is abnormal (empty, illegal characters)
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws DataExistsException If similar data already exist while trying to add relocation info
	 * @throws UpdateByOtherTerminalException If in process data is updated in another terminal
	 * 			There are cases that target data can't be found based on update and NotFoundException is thrown
	 * @throws OverflowException If the item count exceeds limit
	 * @throws LockTimeOutException If the database lock is not removed after specified time
	 * @throws ScheduleException If any abnormal error occurs in Relocation storage completion process
	 */
	protected void updateMovement(
		String collectJobNo,
		String workerCode,
		String rftNo,
		String useByDate,
		int    rftResultQty,
		String resAreaNo,
		String resLocationNo)
	throws 
		NotFoundException, 
		InvalidDefineException, 
		ReadWriteException, 
		DataExistsException, 
		UpdateByOtherTerminalException,
		OverflowException,
		ScheduleException,
		LockTimeOutException
	{
		//#CM575875
		// Search Relocation Work info
		MovementSearchKey skey = new MovementSearchKey();
		skey.setCollectJobNo(collectJobNo);
		skey.setStatusFlag(Movement.STATUSFLAG_NOWWORKING);
		skey.setWorkerCode(workerCode);
		skey.setTerminalNo(rftNo);

		skey.setJobNoOrder(1, true);
		
		MovementHandler handler = new MovementHandler(wConn);
		Movement[] mv ;
		try{
			mv = (Movement[]) handler.findForUpdate(skey ,WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM575876
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNMOVEMENT");
			throw e;
		}

		if (mv.length <= 0)
		{
			throw new UpdateByOtherTerminalException();
		}
		
		//#CM575877
		// Check whether the data is not updated from another terminal
		for (int j = 0; j < mv.length; j++)
		{
			if (! mv[j].getStatusFlag().equals(Movement.STATUSFLAG_NOWWORKING))
			{
				throw new UpdateByOtherTerminalException();
			}

			if (! mv[j].getLastUpdatePname().equals(START_PROCESS_NAME))
			{
				throw new UpdateByOtherTerminalException();
			}
		}

		//#CM575878
		// Save actual relocation storage result qty
		int workQty = rftResultQty;

		MovementAlterKey akey = new MovementAlterKey();
		//#CM575879
		// Relocation storage completion process (Update stock info, create result send info)
		MovementCompleteOperator mvCompleteOperator = new MovementCompleteOperator();
		
		for (int j = 0; j < mv.length; j++)
		{
			akey.KeyClear();
			akey.setJobNo(mv[j].getJobNo());
			akey.setTerminalNo(rftNo);
			akey.setWorkerCode(workerCode);
			
			if( workQty > 0 )
			{
				//#CM575880
				// Work info entity in case of division
				Movement newMovement = null;

				int resultQty = workQty;
				//#CM575881
				// set the value to update
				//#CM575882
				// If next data exist and if the actual relocation storage result qty exceeds work plan qty
				if (j < mv.length - 1 && resultQty > mv[j].getPlanQty())
				{
					//#CM575883
					// Set result work plan qty
					resultQty = mv[j].getPlanQty();
				}
				workQty -= resultQty;

				//#CM575884
				// Result qty
				akey.updateResultQty(resultQty);
				mv[j].setResultQty(resultQty);
				//#CM575885
				// Status flag
				akey.updateStatusFlag(Movement.STATUSFLAG_COMPLETION);

				if (mv[j].getResultQty() < mv[j].getPlanQty())
				{
					//#CM575886
					// If work plan qty is greater than result qty
					//#CM575887
					// Fetch pending qty
					int restQty = mv[j].getPlanQty() - mv[j].getResultQty();

					//#CM575888
					// Update Work plan qty to Result qty
					akey.updatePlanQty(mv[j].getResultQty());

					//#CM575889
					// Set the new record value to divide
					newMovement = (Movement)mv[j].clone();
					//#CM575890
					// New Job no.
					SequenceHandler sh = new SequenceHandler(wConn);
					newMovement.setJobNo(sh.nextJobNo());
					newMovement.setStatusFlag(Movement.STATUSFLAG_UNSTART);
					newMovement.setPlanQty(restQty);
					newMovement.setResultQty(0);
					newMovement.setTerminalNo("");
					newMovement.setWorkerCode("");
					newMovement.setWorkerName("");
					newMovement.setRegistPname(PROCESS_NAME);
					newMovement.setLastUpdatePname(PROCESS_NAME);
				}

				//#CM575891
				// Expiry date (work result)
				akey.updateResultUseByDate(useByDate);
				//#CM575892
				// Work result area
				akey.updateResultAreaNo (resAreaNo);
				//#CM575893
				// Work result location
				akey.updateResultLocationNo(resLocationNo);
				//#CM575894
				// Last update Process name
				akey.updateLastUpdatePname(PROCESS_NAME);
				//#CM575895
				// update DB
				handler.modify(akey);

				//#CM575896
				// storage complete process
				mvCompleteOperator.complete( wConn, mv[j].getJobNo(), PROCESS_NAME );
				
				if (newMovement != null)
				{
					//#CM575897
					// create split record
					handler.create(newMovement);
				}
			}
			else
			{
				//#CM575898
				// If there is no qty to divide
				
				//#CM575899
				// call cancel process
				akey.updateStatusFlag(Movement.STATUSFLAG_UNSTART);
				akey.updateWorkerCode("");
				akey.updateWorkerName("");
				akey.updateTerminalNo("");
				akey.updateLastUpdatePname(PROCESS_NAME);
				//#CM575900
				// update DB
				handler.modify(akey);
			}
		}			
	}
	
	//#CM575901
	// Private methods -----------------------------------------------
	//#CM575902
	/**
	 * Update worker result<BR>
	 * <DIR>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Work complete date/time</TD>	<TD>Current system date</TD></TR>
	 *      <TR><TD>Work qty</TD>		<TD>+Work completed result qty</TD></TR>
	 *      <TR><TD>Work count</TD>		<TD>+1 </TD></TR>
	 *      <TR><TD>Work count (Order qty) <TD>+1 </TD></TR>
	 *      <TR><TD>work time			<TD>+work time of telegraph data</TD></TR>
	 *      <TR><TD>result work time			<TD>+work time of telegraph data</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @param	resultQty		Actual relocation storage result qty
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	private void createWorkerResult(
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
			//#CM575903
			// If work date can't be retrieved from system define info
			//#CM575904
			// throw ReadWriteException
			throw (new ReadWriteException());
		}

		//#CM575905
		// update result by worker
		WorkerResult wr = new WorkerResult();
		wr.setWorkDate(workingDate);
		wr.setWorkerCode(workerCode);
		wr.setTerminalNo(rftNo);
		wr.setJobType(WorkerResult.JOB_TYPE_MOVEMENT_STORAGE);
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
			//#CM575906
			// If target worker result does not exist, create new
			//#CM575907
			// (If daily update is in process in RFT)
			//#CM575908
			// 6022004=No matching Worker Result Data is found. A new data will be created. {0}
			String errData = "[WorkDate:" + workingDate +
			 				 " JobType:" + WorkerResult.JOB_TYPE_MOVEMENT_STORAGE +
							 " RftNo:" + rftNo +
							 " WorkerCode:" + workerCode +"]";
			RftLogMessage.print(6022004, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				//#CM575909
				// create worker result
				bo.createWorkerResult(WorkerResult.JOB_TYPE_MOVEMENT_STORAGE, workerCode, rftNo);
				//#CM575910
				// update worker result
				bo.alterWorkerResult(wr);
			}
			catch (Exception e1)
			{
				//#CM575911
				// If exception occurs throw ReadWriteException
				throw (new ReadWriteException());
			}
		}
	}
	
	//#CM575912
	/**
	 * Fetch error details
	 * @return	Error details
	 */
	public String getErrorDetails()
	{
		return errDetails;
	}

}
//#CM575913
//end of class
