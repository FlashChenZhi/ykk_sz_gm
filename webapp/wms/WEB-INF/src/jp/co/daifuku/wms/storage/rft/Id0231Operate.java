// $Id: Id0231Operate.java,v 1.2 2006/12/07 09:00:12 suresh Exp $
package jp.co.daifuku.wms.storage.rft;
//#CM575239
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ShelfInvalidityException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.storage.schedule.StockAllocateOperator;
import jp.co.daifuku.wms.storage.schedule.StorageCompleteOperator;


//#CM575240
/**
 * Designer : K.Shimizu <BR>
 * Maker : K.Shimizu <BR>
 * <BR>
 * Storage result data send process from RFT<BR>
 * There are three types of process namely normal, shortage and cancel<BR>
 * The business logic called from Id0231Process is executed<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/11</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:12 $
 * @author  $Author: suresh $
 */
public class Id0231Operate extends IdOperate
{
	//#CM575241
	// Class variables -----------------------------------------------
	//#CM575242
	/**
	 * Process name
	 */
	private static final String PROCESS_NAME = "ID0231";
	//#CM575243
	/**
	 * Previous process name (data request process)
	 */
	private static final String START_PROCESS_NAME = "ID0230";
	//#CM575244
	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "Id0231Operate";
	
	//#CM575245
	// String when grouping key is Item code. Otherwise [grouping key : Item code + ITF + Bundle ITF]
	final int STR_ITEMCODE = 1;

	//#CM575246
	/**
	 * Error details
	 */
	private String errDetails = RFTId5231.ErrorDetails.NORMAL;
	
	//#CM575247
	// Class method --------------------------------------------------
	//#CM575248
	/**
	 * Return the version of this class
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:12 $");
	}
	//#CM575249
	// Constructors --------------------------------------------------
	//#CM575250
	/**
	 * Constructor<BR>
	 */
	public Id0231Operate()
	{
		super();
	}
	//#CM575251
	/**
	 * Pass Db connection info to the constructor<BR>
	 * @param conn DBConnection info
	 */
	public Id0231Operate(Connection conn)
	{
		super();
		wConn = conn;
	}
	//#CM575252
	// Public methods ------------------------------------------------
	//#CM575253
	/**
	 * Storage result data(ID0231) Process<BR>
	 * Decide the process from the telegraph completion flag and execute commit or cancel process<BR>
	 * <BR>
	 * <DIR>
	 *      <UL>
	 *      <LI>completion flag 0:Normal, 1:Shortage [Commit process]</LI>
	 *      <LI>completion flag 9:Cancel   [cancel process]</LI>
	 *      </UL>
	 * </DIR>
	 * @param	collectJobNo	Collect Job no.
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @param	useByDate		Expiry date
	 * @param  planQty			Storage plan qty
	 * @param	resultQty		Storage complete qty
	 * @param	locationNo		Storage complete location 
	 * @param	workTime		work time
	 * @param	missScanCnt		miss count
	 * @param	completionFlag	completion flag
	 * @return Response id Response flag
	*/
	public String doComplete(
		String collectJobNo,
		String workerCode,
		String rftNo,
		String useByDate,
		int    planQty,
		int    resultQty,
		String locationNo,
		int    workTime,
		int    missScanCnt,
		String completionFlag)
	{
		try
		{
			//#CM575254
			// If the flag received is 9 : cancel
			if(completionFlag.equals(RFTId0231.COMPLETION_FLAG_CANCEL))
			{
				//#CM575255
				// Update status flag
				storageCancelOnJAN( collectJobNo, workerCode, rftNo);
			}
			//#CM575256
			// If the flag received is other than cancel
			else
			{
				if(DisplayText.isPatternMatching(locationNo))
				{
					throw new InvalidDefineException("LocationNo[" + locationNo +"]");
				}

				//#CM575257
				// Execute complete process in case of normal, additional delivery, shortage 
				storageCompleteOnJAN(
					collectJobNo,
					workerCode,
					rftNo,
					useByDate,
					planQty,
					resultQty,
					locationNo,
					workTime,
					missScanCnt,
					completionFlag );
			}
		}
		catch (LockTimeOutException e)
		{			
			//#CM575258
			// SELECT FOR UPDATE timeout
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				//#CM575259
				// 6006002=Database error occurred.{0}
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM575260
			// Response flag:Under maintenance in another terminal
			return RFTId5231.ANS_CODE_MAINTENANCE ;
		}
		//#CM575261
		// If the info is not found during search
		catch (NotFoundException e)
		{
			String errData = "[Collect_Job_No:" + collectJobNo +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			//#CM575262
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
			//#CM575263
			// Response flag : Error
			errDetails = RFTId5231.ErrorDetails.NULL;
			return RFTId5231.ANS_CODE_ERROR ;
		}
		//#CM575264
		// If the data is already updated in another terminal
		catch (UpdateByOtherTerminalException e)
		{
			String errData = "[Collect_Job_No:" + collectJobNo +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			//#CM575265
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

			//#CM575266
			// Response flag : Error
			errDetails = RFTId5231.ErrorDetails.UPDATE_FINISH;
			return RFTId5231.ANS_CODE_ERROR ;
		}
		//#CM575267
		// Since there is a data access error
		catch (ReadWriteException e)
		{
			//#CM575268
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
			//#CM575269
			// Response flag : Error
			errDetails = RFTId5231.ErrorDetails.DB_ACCESS_ERROR;
			return RFTId5231.ANS_CODE_ERROR ;
		}
		//#CM575270
		// Since there is a data access error
		catch (DataExistsException e)
		{
			//#CM575271
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

			//#CM575272
			// Response flag : Error
			errDetails = RFTId5231.ErrorDetails.DB_UNIQUE_KEY_ERROR;
			return RFTId5231.ANS_CODE_ERROR ;
		}
		//#CM575273
		// If overflow error occurs during stock creation
		catch (OverflowException e)
		{
			String errData = "[Collect_Job_No:" + collectJobNo +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			//#CM575274
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
			//#CM575275
			// Response flag : Error
			errDetails = RFTId5231.ErrorDetails.OVERFLOW;
			return RFTId5231.ANS_CODE_ERROR;
		}	
		catch (InvalidDefineException e)
		{
			//#CM575276
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
			//#CM575277
			// Response flag : Error
			errDetails = RFTId5231.ErrorDetails.PARAMETER_ERROR;
			return RFTId5231.ANS_CODE_ERROR;
		}
		catch (InvalidStatusException e) 
		{
			//#CM575278
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
			//#CM575279
			// Response flag : Error
			errDetails = RFTId5231.ErrorDetails.DB_INVALID_VALUE;
			return RFTId5231.ANS_CODE_ERROR;
		} 
		catch (ScheduleException e) 
		{
			//#CM575280
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
			//#CM575281
			// Response flag : Error
			errDetails = RFTId5231.ErrorDetails.SCHEDULE_ERROR;
			return RFTId5231.ANS_CODE_ERROR ;
		}
		catch (ShelfInvalidityException e)
		{
		    
		    String errData = " [LocationNo:" + locationNo +
		    				 " RftNo:" + rftNo +
							 " WorkerCode:" + workerCode + "]";
		    
			//#CM575282
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
			//#CM575283
			// Error details
			errDetails = RFTId5231.ErrorDetails.SHELF_INVALIDITY;
			return RFTId5231.ANS_CODE_ERROR;
		}
		//#CM575284
		// Other error occurred
		catch (Exception e)
		{
			//#CM575285
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

			//#CM575286
			// Response flag : Error
			errDetails = RFTId5231.ErrorDetails.INTERNAL_ERROR;
			return RFTId5231.ANS_CODE_ERROR ;
		} 	
		return RFTId5231.ANS_CODE_NORMAL;
	}

	//#CM575287
	/**
	 * Storage result data [Complete process (normal, shortage)]<BR>
	 * The following process is executed for storage result info<BR>
	 * <BR>
	 * Lock Work info, Storage plan info target data (exclusive process use)<BR>
	 * Search AS/RS location info based on location no.If target data exist (automated warehouse location instructed)
	 * Return error response (input location not available)<BR>
	 * Update work info.Search target data, change status flag to complete and set result qty etc.,<BR>
	 * Update Storage plan info. Search target data, change status flag (decide from work info) and set result qty etc.<BR>
	 * Update Stock info. Search target data, calculate stock qty and reduce from Storage plan qty<BR>
	 *  (If target data exist, increase the stock qty. else create new stock)<BR>
	 * Create result send info. Create storage completeion result<BR>
	 * Update worker result. Search the target data and add work qty, work count<BR>
	 * <BR>
	 * Work info,Storage plan info lock<BR>
	 * <DIR>
	 *    Work info<BR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Collect Job no. (Product identification code of telegraph data)</LI>
	 *     <LI>Job type=02:Storage</LI>
	 *     <LI>Status flag=2:In process</LI>
	 * 	   <LI>Worker code=Worker code (Worker code of telegraph data)</LI>
	 * 	   <LI>terminal no.=RFT no.(RFT no. of telegraph data)</LI>
	 *    </UL>
	 *    Storage plan info<BR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Storage plan unique key=Fetch from Work info</LI>
	 *     <LI>Status flag!=9:Delete</LI>
	 *    </UL>
	 * </DIR>
	 * <BR>
	 * Search AS/RS location info<BR>
	 * <DIR>
	 *    AS/RS location info<BR>
	 *    (Search conditions)
	 *    <UL>
	 *      <LI>Station no. =Storage complete location of telegraph data </LI>
	 *    </UL>
	 * </DIR>
	 * <BR>
	 * Update Work info <BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Collect Job no.(Product identification code of telegraph data)</LI>
	 *     <LI>Job type=02:Storage</LI>
	 *     <LI>Status flag=2:In process</LI>
	 * 	   <LI>Worker code=Worker code(Worker code of telegraph data)</LI>
	 * 	   <LI>terminal no.=RFT no.(RFT no. of telegraph data)</LI>
	 * 	   <LI>Last update Process name='ID0231'</LI>
	 *    </UL>
	 *    (Sort order)
	 *    <UL>
	 *     <LI>Job no.</LI>
	 *    </UL>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Status flag</TD>		<TD>Complete:4</TD></TR>
	 *      <TR><TD>Work result qty</TD>		<TD>Storage complete qty of telegraph data</TD></TR>
	 *      <TR><TD>Work pending qty</TD>		<TD>If shortage occurs in Storage complete qty, set the shortage qty</TD></TR>
	 *      <TR><TD>Expiry date</TD>		<TD>Expiry date of telegram data</TD></TR>
	 *      <TR><TD>Last update Process name</TD>	<TD>"ID0231"</TD></TR>
	 *    </TABLE>
	 *   Consider the probability of multiple records<BR>
	 *   The actual storage qty will be the work result qty. The division qty is [available work qty] <BR>
	 *   In case of surplus, divide the last record<BR>
	 * </DIR>
	 * <BR>
	 * update Storage plan info<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Storage plan unique key=Fetch from Work info</LI>
	 *     <LI>Status flag!=9:Delete</LI>
	 *    </UL>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Status flag</TD>		<TD>2:In process 3:Partial completion 4:Complete</TD></TR>
	 *      <TR><TD>Work result qty</TD>		<TD>Result qty of telegraph data</TD></TR>
	 *      <TR><TD>Work pending qty</TD>		<TD>If shortage occurs, set that qty</TR>
	 *      <TR><TD>Expiry date</TD>		<TD>Expiry date of telegram data</TD></TR>
	 *      <TR><TD>Last update Process name</TD>	<TD>"ID0231"</TD></TR>
	 *    </TABLE>
	 *   Add work result qty, work pending qty of work info based on storage result qty, storage shortage qty<BR>
	 *   Update status flag<BR>
	 *   <DIR>
	 *         Search Work info using the target data's storage plan unique key<BR>
	 *         <UL>
	 *         <LI>Update to 4:Complete if all the work is completed</LI>
	 *         <LI>If atleast one record of Work info is in process, update to 2: In process</LI>
	 *         <LI>If records of Work info is a mixture of "Standby" and "Completed" update to 3:Partial completion</LI>
	 *         </UL>
	 *   </DIR>
	 * </DIR>
	 * <BR>
	 * Update stock info<BR>
	 * Create result send info<BR>
	 * <DIR>
	 *    Use storage complete method<BR>
	 *    Update stock info and Create result send info with Storage completion method.<BR>
	 * </DIR>
	 * <BR>
	 * Update worker result info<BR>
	 * <DIR>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Work complete date/time</TD>	<TD>Current system date</TD></TR>
	 *      <TR><TD>Work qty</TD>		<TD>+Work completed result qty</TD></TR>
	 *      <TR><TD>Work count</TD>		<TD>+1 </TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * <BR>
	 * @param	collectJobNo	Collect Job no.
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @param	useByDate		Expiry date
	 * @param	planQty			Storage plan qty
	 * @param	resultQty		Storage complete qty
	 * @param	locationNo		Storage complete location 
	 * @param	workTime		work time
	 * @param	missScanCnt		miss count
	 * @param	completionFlag	completion flag
	 * @throws InvalidStatusException If status outside range is set
	 * @throws NotFoundException  If target data does not exist for update
	 * @throws InvalidDefineException If the defined parameter value differs (restricted characters)
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws UpdateByOtherTerminalException If in process data is updated in another terminal
	 * 			There are cases that target data can't be found based on update and NotFoundException is thrown
	 * @throws LockTimeOutException If the database lock is not removed after specified time
	 * @throws ScheduleException If exception occurs during storage complete process
	 * @throws OverflowException If the item count exceeds limit
	 * @throws DataExistsException If similar data already exist
	 * @throws ShelfInvalidityException If the input location is not valid
	*/
	public void storageCompleteOnJAN(
		String collectJobNo,
		String workerCode,
		String rftNo,
		String useByDate,
		int    planQty,
		int    resultQty,
		String locationNo,
		int    workTime,
		int    missScanCnt,
		String completionFlag)
		throws
			InvalidStatusException,
			NotFoundException,
			InvalidDefineException,
			ReadWriteException,
			UpdateByOtherTerminalException,
			LockTimeOutException,
			ScheduleException,
			OverflowException,
			DataExistsException, 
			ShelfInvalidityException
	{
		LocateOperator lOperator = new LocateOperator(wConn);
		if(lOperator.isAsrsLocation(locationNo))
		{
			//#CM575288
			// 6023443=No.{0} The specified location is in automatic warehouse. Cannot enter.
			throw new ShelfInvalidityException();
		}		
		
		//#CM575289
		// Work info,Storage plan info lock
		String[] planUkeyList = lockUpdateData( collectJobNo, workerCode, rftNo );

		//#CM575290
		// Update Work info, Storage plan info update (exclude status flag),
		//#CM575291
		// Update Stock(storage complete process),update worker result
		updateWorkingInformation( 
			collectJobNo,
			workerCode,
			rftNo,
			useByDate,
			planQty,
			resultQty,
			locationNo,
			completionFlag );

		//#CM575292
		// Storage plan info update (Status flag)
		updateCompletionStatus(planUkeyList);

		//#CM575293
		// update worker result
		createWorkerResult( workerCode, rftNo, workTime, missScanCnt,resultQty );
	}

	//#CM575294
	/**
	 * Storage result data [cancel process]<BR>
	 * Execute the following cancel process on storage result info<BR>
	 * <BR>
	 * Lock Work info, Storage plan info target data (exclusive process use)<BR>
	 * Update work info. Search target data, update status flag to standby, terminal no, and set empty values to RFT no and worker code<BR>
	 * Update Storage plan info. Search target data, update status flag (decide from work info)<BR>
	 * <BR>
	 * Work info,Storage plan info lock<BR>
	 * <DIR>
	 *    Work info<BR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Collect Job no.(Product identification code of telegraph data)</LI>
	 *     <LI>Status flag=2:In process</LI>
	 * 	   <LI>Worker code=Worker code(Worker code of telegraph data)</LI>
	 * 	   <LI>terminal no.=RFT no.(RFT no. of telegraph data)</LI>
	 *    </UL>
	 *    Storage plan info<BR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Storage plan unique key=Fetch from Work info</LI>
	 *     <LI>Status flag!=9:Delete</LI>
	 *    </UL>
	 * </DIR>
	 * <BR>
	 * Update Work info <BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Collect Job no.(Product identification code of telegraph data)</LI>
	 *     <LI>Status flag=2:In process</LI>
	 * 	   <LI>Worker code=Worker code(Worker code of telegraph data)</LI>
	 * 	   <LI>terminal no.=RFT no.(RFT no. of telegraph data)</LI>
	 * 	   <LI>Last update Process name="ID0231"</LI>
	 *    </UL>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Status flag</TD>		<TD>Standby:0</TD></TR>
	 *      <TR><TD>Worker code</TD>	<TD>Empty</TD></TR>
	 *      <TR><TD>worker name</TD>		<TD>Empty</TD></TR>
	 *      <TR><TD>terminal no.</TD>			<TD>Empty</TD></TR>
	 *      <TR><TD>Last update Process name</TD>	<TD>"ID0231"</TD></TR>
	 *    </TABLE>
	 *   Consider the probability of multiple records<BR>
	 * </DIR>
	 * <BR>
	 * update Storage plan info<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Storage plan unique key=Fetch from Work info</LI>
	 *     <LI>Status flag!=9:Delete</LI>
	 *    </UL>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Status flag</TD>		<TD>0:Incomplete 2:In process 3:Partial completion</TD></TR>
	 *      <TR><TD>Last update Process name</TD>	<TD>"ID0231"</TD></TR>
	 *    </TABLE>
	 *   Add work result qty, work pending qty of work info based on storage result qty, storage shortage qty<BR>
	 *   Update status flag<BR>
	 *   Search Work info using the target data's storage plan unique key<BR>
	 *   <UL>
	 *   <LI>If all the target data is "Standby", update status to 0:Standby</LI>
	 *   <LI>If atleast one record of Work info is in process, update to 2: In process</LI>
	 *   <LI>If records of Work info is a mixture of "Standby" and "Completed" update to 3:Partial completion</LI>
	 *   </UL>
	 * </DIR>
	 * @param	collectJobNo	Collect Job no.
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws NotFoundException If target data does not exist for update
	 * @throws InvalidDefineException If the defined parameter value differs (restricted characters)
	 * @throws LockTimeOutException If the database lock is not removed after specified time
	 * @throws UpdateByOtherTerminalException If In process target data is updated in another terminal
	 */
	public void storageCancelOnJAN(
			String collectJobNo,
			String workerCode,
			String rftNo)
		throws ReadWriteException, NotFoundException, InvalidDefineException, LockTimeOutException, UpdateByOtherTerminalException
	{
		//#CM575295
		// Work info/Storage plan info lock
		String[] planUkeyList = lockUpdateData( collectJobNo, workerCode, rftNo );

		//#CM575296
		// Search work info
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setCollectJobNo(collectJobNo);
		skey.setWorkerCode(workerCode);
		skey.setTerminalNo(rftNo);
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		skey.setLastUpdatePname(START_PROCESS_NAME);

		WorkingInformationHandler handler = new WorkingInformationHandler(wConn);
		WorkingInformation[] wi = (WorkingInformation[])handler.find(skey);

		for(int i=0; i < wi.length; i++)
		{
			WorkingInformationAlterKey akey = new WorkingInformationAlterKey();
			akey.setJobNo( wi[i].getJobNo() );

			//#CM575297
			// reverse Collect Job no. to original(return to Job no.)
			akey.updateCollectJobNo( wi[i].getJobNo() );
			akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			akey.updateWorkerCode("");
			akey.updateWorkerName("");
			akey.updateTerminalNo("");
			akey.updateLastUpdatePname(PROCESS_NAME);
			handler.modify(akey);
		}

		//#CM575298
		// Revert Storage plan to original based on Work info status
		updateCompletionStatus(planUkeyList);
	}

	//#CM575299
	/**
	 * Fetch error details
	 * @return	Error details
	 */
	public String getErrorDetails()
	{
		return errDetails;
	}
	
	//#CM575300
	// Package methods -----------------------------------------------

	//#CM575301
	// Protected methods ---------------------------------------------
	//#CM575302
	/**
	 * Update Work info <BR>
	 *Search target data, change status flag to complete and set result qty etc.,<BR>
	 * Update Storage plan info (<CODE>updateStoragePlan</CODE> method)<BR>
	 * Update Stock info (<CODE>updateStockQty</CODE> method)<BR>
	 * Create result send info (<CODE>createResultData</CODE> method)<BR>
	 * <BR>
	 * Update Work info <BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Collect Job no.(Product identification code of telegraph data)</LI>
	 *     <LI>Status flag=2:In process</LI>
	 * 	   <LI>Worker code=Worker code(Worker code of telegraph data)</LI>
	 * 	   <LI>terminal no.=RFT no.(RFT no. of telegraph data)</LI>
	 * 	   <LI>Last update Process name="ID0231"</LI>
	 *    </UL>
	 *    (Sort order)
	 *    <UL>
	 *     <LI>Job no.</LI>
	 *    </UL>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Status flag</TD>				<TD>Complete:4</TD></TR>
	 *      <TR><TD>Work result qty</TD>				<TD>Storage complete qty of telegraph data</TD></TR>
	 *      <TR><TD>Work pending qty</TD>				<TD>If shortage occurs in Storage complete qty, set the shortage qty</TD></TR>
	 *      <TR><TD>Expiry date</TD>				<TD>Expiry date of telegram data</TD></TR>
	 *      <TR><TD>Work result location</TD>	<TD>Storage complete location of telegraph data </TD></TR>
	 *      <TR><TD>Last update Process name</TD>	<TD>"ID0231"</TD></TR>
	 *    </TABLE>
	 *   Consider the probability of multiple records<BR>
	 *   The actual storage qty will be the work result qty. The division qty is [available work qty] <BR>
	 *   In case of surplus, divide the last record<BR>
	 * </DIR>
	 * <BR>
	 * @param	collectJobNo	Collect Job no.
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @param	useByDate		Expiry date
	 * @param	planQty			Storage plan qty
	 * @param	rftResultQty	Storage complete qty
	 * @param	locationNo		Storage complete location 
	 * @param	completionFlag	Shortage or Pending flag
	 * @throws InvalidStatusException If status outside range is set
	 * @throws NotFoundException If target data does not exist for update
	 * @throws InvalidDefineException If the defined parameter value differs (restricted characters)
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws UpdateByOtherTerminalException If in process data is updated in another terminal
	 * 			There are cases that target data can't be found based on update and NotFoundException is thrown
	 * @throws LockTimeOutException If the database lock is not removed after specified time
	 * @throws ScheduleException If exception occurs during storage complete process
	 * @throws OverflowException If the item count exceeds limit
	 * @throws DataExistsException If similar data already exist
	 */
	protected void updateWorkingInformation(
		String collectJobNo,
		String workerCode,
		String rftNo,
		String useByDate,
		int    planQty,
		int    rftResultQty,
		String locationNo,
		String completionFlag)
		throws
			InvalidStatusException, 
			NotFoundException, 
			InvalidDefineException, 
			ReadWriteException, 
			UpdateByOtherTerminalException, 
			LockTimeOutException,
			ScheduleException,
			OverflowException,
			DataExistsException
	{
		//#CM575303
		// Search the target data in Work info
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setCollectJobNo(collectJobNo);
		skey.setWorkerCode(workerCode);
		skey.setTerminalNo(rftNo);
		skey.setPlanUkeyOrder(1, true);
		skey.setJobNoOrder(2, true);

		WorkingInformationHandler handler = new WorkingInformationHandler(wConn);
		WorkingInformation[] wi = (WorkingInformation[]) handler.find(skey);

		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		
		if (handler.count(skey) <= 0)
		{
			throw new NotFoundException();
		}
		
		//#CM575304
		// Check if the gathered data is updated in another terminal or not
		//#CM575305
		// If updated, UpdateByOtherTerminalException is
		//#CM575306
		// thrown
		checkCollectData(planQty, wi);
		
		//#CM575307
		// Store result qty of Storage result info
		int workQty = rftResultQty;

		AreaOperator areaOpe = new AreaOperator(wConn);
		LocateOperator locOpe = new LocateOperator(wConn);
		for (int j = 0; j < wi.length; j++)
		{
			//#CM575308
			// If either divisible qty exists or shortage completion =====================================
			if(workQty >0 || completionFlag.equals(RFTId0231.COMPLETION_FLAG_LACK))
			{
				//#CM575309
				// Fetch target data to update qty
				int resultQty = workQty;
				//#CM575310
				// If next data exist and if storage result qty is greater than possible work qty 
				if (j < wi.length - 1 && resultQty > wi[j].getPlanEnableQty())
				{
					//#CM575311
					// set work possible qty to result qty
					resultQty = wi[j].getPlanEnableQty();
				}
				//#CM575312
				// Fetch next data subtracting from completed
				workQty -= resultQty;

				//#CM575313
				// Set update info
				WorkingInformationAlterKey akey = new WorkingInformationAlterKey();
				akey.setJobNo( wi[j].getJobNo() );
				akey.updateCollectJobNo( wi[j].getJobNo() );
				akey.updateResultQty( resultQty );
				akey.updateLastUpdatePname(PROCESS_NAME);
				akey.updateResultUseByDate( useByDate );
				akey.updateResultLocationNo( locationNo );
				String areaNo = locOpe.getAreaNo(locationNo);
				akey.updateAreaNo(areaNo);
				akey.updateSystemDiscKey(Integer.parseInt(areaOpe.getAreaType(areaNo)));
				akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);

				//#CM575314
				// Set update info to work info entity
				//#CM575315
				// used for plan info update, to store required values for storage complete process
				wi[j].setResultQty(resultQty);
				wi[j].setResultUseByDate( useByDate );
				wi[j].setResultLocationNo( locationNo );
				wi[j].setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);

				//#CM575316
				// If result qty is lesser than plan qty, shortage or additional delivery process
				if (wi[j].getResultQty() < wi[j].getPlanEnableQty())
				{
					//#CM575317
					// If shortage occurs, sum up values
					if (completionFlag.equals(RFTId0231.COMPLETION_FLAG_LACK))
					{
						akey.updateShortageCnt(wi[j].getPlanEnableQty() - wi[j].getResultQty());
						wi[j].setShortageCnt(wi[j].getPlanEnableQty() - wi[j].getResultQty());
					}
					//#CM575318
					// In case of additional delivery, calculate pending qty and create a new work
					else
					{
						akey.updatePendingQty(wi[j].getPlanEnableQty() - wi[j].getResultQty());
						wi[j].setPendingQty(wi[j].getPlanEnableQty() - wi[j].getResultQty());
						//#CM575319
						// Create additional delivery work
						createWorkInfo(wi[j]);
					}
				}

				//#CM575320
				// update DB
				handler.modify(akey);

				//#CM575321
				// Update storage plan info qty
				updateStoragePlan(wi[j]);

				//#CM575322
				// storage complete process (Update Stock, create result send info)
				updateStockQty(wi[j]);
			}
			//#CM575323
			// If there is no quantity for division =====================================		
			else
			{
				//#CM575324
				// Set update info
				WorkingInformationAlterKey akey = new WorkingInformationAlterKey();
				akey.setJobNo( wi[j].getJobNo() );
				akey.updateCollectJobNo( wi[j].getJobNo() );
				akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
				akey.updateWorkerCode("");
				akey.updateWorkerName("");
				akey.updateTerminalNo("");
				akey.updateLastUpdatePname(PROCESS_NAME);
				//#CM575325
				// update DB
				handler.modify(akey);
			}
		}			
	}
	
	//#CM575326
	/**
	 * Update Storage plan info based on updated Work info<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Storage plan unique key=Fetch from Work info</LI>
	 *     <LI>Status flag!=9:Delete</LI>
	 *    </UL>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Work result qty</TD>		<TD>Result qty of telegraph data</TD></TR>
	 *      <TR><TD>Work pending qty</TD>		<TD>Set shortage qty if shortage occurs</TD></TR>
	 *      <TR><TD>Expiry date</TD>		<TD>Expiry date of telegram data</TD></TR>
	 *      <TR><TD>Last update Process name</TD>	<TD>"ID0231"</TD></TR>
	 *    </TABLE>
	 *   Add work result qty, work pending qty of work info based on storage result qty, storage shortage qty<BR>
	 * </DIR>
	 * @param wi Work info target to update
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws NotFoundException  If target data does not exist for update
	 * @throws InvalidStatusException If multiple data occurs while searching with unique key
	 * @throws InvalidDefineException If the defined parameter value differs (restricted characters)
	 */
	protected void updateStoragePlan(WorkingInformation wi)
		throws 	InvalidStatusException, ReadWriteException, NotFoundException,InvalidDefineException
	{
		try
		{
			//#CM575327
			// Search corresponding storage plan info
			StoragePlanSearchKey skey = new StoragePlanSearchKey();
			skey.setStoragePlanUkey( wi.getPlanUkey() );
			skey.setStatusFlag( StoragePlan.STATUS_FLAG_DELETE, "!=" );
			StoragePlanHandler handler = new StoragePlanHandler(wConn);
			StoragePlan plan = (StoragePlan) handler.findPrimaryForUpdate( skey );

			//#CM575328
			// Update storage plan info
			StoragePlanAlterKey akey = new StoragePlanAlterKey();
			akey.setStoragePlanUkey( plan.getStoragePlanUkey() );
			akey.updateResultQty( plan.getResultQty() + wi.getResultQty() );
			akey.updateShortageCnt( plan.getShortageCnt() + wi.getShortageCnt() );
			akey.updateLastUpdatePname( PROCESS_NAME );
			handler.modify( akey );
		}
		catch (NoPrimaryException e)
		{
			String errString = "[Table:DnStoragePlan" + 
								" STORAGE_PLAN_UKEY = " + wi.getPlanUkey() +"]";
			//#CM575329
			// 6026020=Multiple records are found in a search by the unique key. {0}
			RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
			//#CM575330
			// Change to appropriate exception
			throw new InvalidStatusException();
		}
	}
	
	//#CM575331
	/**
	 * Update storage plan info status flag based on the modified work info's plan unique key<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Storage plan unique key=Work info.Plan unique key (There is one storage plan info for each corresponding work info)
	 *     <LI>Status flag!=9:Delete</LI>
	 *    </UL>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Status flag</TD>		<TD>0:Standby 2:In process 3:Partial completion 4:Complete</TD></TR>
	 *      <TR><TD>Last update Process name</TD>	<TD>"ID0231"</TD></TR>
	 *    </TABLE>
	 *   Update status flag<BR>
	 *   Search Work info using the target data's storage plan unique key<BR>
	 *   <UL>
	 *   <LI>If all the target data is "Standby", update status to 0:Standby</LI>
	 *   <LI>If atleast one record of Work info is in process, update to 2: In process</LI>
	 *   <LI>If records of Work info is a mixture of "Standby" and "Completed" update to 3:Partial completion</LI>
	 *   <LI>Update to 4:Complete if all the work is completed</LI>
	 *   </UL>
	 * </DIR>
	 * @param planUkey Plan unique key list
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws NotFoundException  If target data does not exist for update
	 * @throws InvalidDefineException If the defined parameter value differs (restricted characters)
	 */
	protected void updateCompletionStatus(String[] planUkey) 
		throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		for (int i = 0; i < planUkey.length; i++)
		{
			WorkingInformationSearchKey skey =
				new WorkingInformationSearchKey();
			skey.setPlanUkey(planUkey[i]);

			WorkingInformation[] workinfo;
			WorkingInformationHandler wHandler =
				new WorkingInformationHandler(wConn);
			workinfo = (WorkingInformation[]) wHandler.find(skey);

			StoragePlanHandler sHandler = new StoragePlanHandler(wConn);
			StoragePlanAlterKey akey = new StoragePlanAlterKey();
			akey.setStoragePlanUkey(planUkey[i]);
			akey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");

			boolean existCompleteData = false;
			boolean existUnstartData = false;
			boolean existWorkingData = false;
			for (int j = 0; j < workinfo.length; j++)
			{
				String status = workinfo[j].getStatusFlag();
				if (status.equals(WorkingInformation.STATUS_FLAG_COMPLETION))
				{
					existCompleteData = true;
				}
				else if (status.equals(WorkingInformation.STATUS_FLAG_UNSTART))
				{
					existUnstartData = true;
				}
				else
				{
					existWorkingData = true;
					break;
				}
			}
			if (existWorkingData || (! existCompleteData && ! existUnstartData))
			{
				//#CM575332
				// If in process, complete or standby data exist
				//#CM575333
				// Don't update status flag
				continue;
			}
			if (existCompleteData)
			{
				if (existUnstartData)
				{
					//#CM575334
					// If complete data and standby data both exist update status to partially completed
					akey.updateStatusFlag(
						StoragePlan.STATUS_FLAG_COMPLETE_IN_PART);
				}
				else
				{
					//#CM575335
					// If complete data alone exist update status to completed
					akey.updateStatusFlag(
						StoragePlan.STATUS_FLAG_COMPLETION);
				}
			}
			else
			{
				//#CM575336
				// If standby data alone exist update status to standby
				akey.updateStatusFlag(StoragePlan.STATUS_FLAG_UNSTART);
			}
			akey.updateLastUpdatePname(PROCESS_NAME);
			sHandler.modify(akey);
		}
	}
	
	//#CM575337
	/**
	 * Update Stock info based on modified Work info and create result send info<BR>
	 * <DIR>
	 *    Use storage complete method<BR>
	 *    Update stock info and Create result send info with Storage completion method.<BR>
	 * </DIR>
	 * @param wi Work info target to update
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws LockTimeOutException Stock info If time out occurs during lock
	 * @throws ScheduleException If exception occurs during storage complete process
	 * @throws InvalidDefineException If the paramter value is abnormal
	 * @throws OverflowException If the item count exceeds limit
	 */
	protected void updateStockQty(WorkingInformation wi)
		throws ReadWriteException, LockTimeOutException, ScheduleException, InvalidDefineException, OverflowException
	{
		try
		{
			//#CM575338
			// Stock info lock
			StockAllocateOperator stockAllocateOperator = new StockAllocateOperator(WmsParam.WMS_DB_LOCK_TIMEOUT);
			String[] checkLocationNo = { wi.getResultLocationNo() };
			stockAllocateOperator.stockSearchForUpdate(
					wConn,
					wi.getConsignorCode(),
					checkLocationNo,
					wi.getItemCode(),
					wi.getResultUseByDate() );
		}
		catch (InvalidDefineException e)
		{
			//#CM575339
			// if the value is abnormal
			String errData =
				"<ConsignorCode[" + wi.getConsignorCode()
					+ "] locationNo[" + wi.getResultLocationNo()
					+ "] itemCode[" + wi.getItemCode() + "]>";
			throw new InvalidDefineException(errData);
		}

		//#CM575340
		// storage complete process (Update stock info, result send info)
		StorageCompleteOperator storageCompleteOperator = new StorageCompleteOperator();
		storageCompleteOperator.complete( wConn, wi.getJobNo(), PROCESS_NAME );
	}
	
	//#CM575341
	/**
	 * Fetch storage plan unique list from update work info target data<BR>
	 * <DIR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Collect Job no.(Product identification code of telegraph data)</LI>
	 *     <LI>Status flag=2:In process</LI>
	 * 	   <LI>Worker code=Worker code(Worker code of telegraph data)</LI>
	 * 	   <LI>terminal no.=RFT no.(RFT no. of telegraph data)</LI>
	 *    </UL>
	 *    (Sort order)
	 *    <UL>
	 *     <LI>Plan unique key</LI>
	 *    </UL>
	 *    Remove repetition data of plan unique key<BR>
	 * </DIR>
	 * @param	collectJobNo	Collect Job no.
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @return	Storage plan unique key array
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	protected String[] getPlanUkeyList(
		String collectJobNo,
		String workerCode,
		String rftNo)
		throws ReadWriteException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setCollectJobNo(collectJobNo);
		skey.setWorkerCode(workerCode);
		skey.setTerminalNo(rftNo);
		skey.setPlanUkeyCollect("DISTINCT");
		skey.setPlanUkeyOrder(1, true);
		WorkingInformationHandler wHandler =
			new WorkingInformationHandler(wConn);
		WorkingInformation[] workinfo =
			(WorkingInformation[]) wHandler.find(skey);

		String[] planUkey = new String[workinfo.length];
		for (int i = 0; i < workinfo.length; i++)
		{
			planUkey[i] = workinfo[i].getPlanUkey();
		}
		return planUkey;
	}
	
	//#CM575342
	/**
	 * Update worker result
	 * <DIR>
	 *    (Update contents)
	 *    <TABLE>
	 *      <TR><TD>Work complete date/time</TD>	<TD>Current system date</TD></TR>
	 *      <TR><TD>Work qty</TD>		<TD>+Work completed result qty</TD></TR>
	 *      <TR><TD>Work count</TD>		<TD>+1 </TD></TR>
	 * 		<TR><TD>miss count</TD>		<TD>miss count of telegraph data</TD></TR>
	 * 		<TR><TD>work time</TD>		<TD>work time of telegraph data</TD></TR>
	 * 		<TR><TD>result work time</TD>		<TD>work time of telegraph data</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @param	workTime		work time
	 * @param	missScanCnt		miss count
	 * @param	resultQty		Storage complete qty
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	protected void createWorkerResult(
			String workerCode, 
			String rftNo, 
			int workTime,
			int missScanCnt,
			int resultQty) throws ReadWriteException
	{

		BaseOperate bo = new BaseOperate(wConn);
		String workingDate = "";
		try
		{
			workingDate = bo.getWorkingDate();
		}
		catch (NotFoundException e)
		{
			//#CM575343
			// If work date can't be retrieved from system define info
			//#CM575344
			// throw ReadWriteException
			throw (new ReadWriteException());
		}

		//#CM575345
		// update result by worker
		WorkerResult wr = new WorkerResult();
		wr.setWorkDate(workingDate);
		wr.setWorkerCode(workerCode);
		wr.setTerminalNo(rftNo);
		wr.setJobType(WorkerResult.JOB_TYPE_STORAGE);
		wr.setOrderCnt(1);
		wr.setWorkCnt(1);
		wr.setWorkQty(resultQty);
		wr.setMissScanCnt(missScanCnt);
		wr.setWorkTime(workTime);
		wr.setRealWorkTime(workTime);
		try
		{
			bo.alterWorkerResult(wr);
		}
		catch (NotFoundException e)
		{
			//#CM575346
			// If target worker result does not exist, create new
			//#CM575347
			// (If daily update is in process in RFT)
			//#CM575348
			// 6022004=No matching Worker Result Data is found. A new data will be created. {0}
			String errData = "[WorkDate:" + workingDate +
			 				 " JobType:" + WorkerResult.JOB_TYPE_STORAGE +
							 " RftNo:" + rftNo +
							 " WorkerCode:" + workerCode +"]";
			RftLogMessage.print(6022004, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				//#CM575349
				// create worker result
				bo.createWorkerResult(WorkerResult.JOB_TYPE_STORAGE, workerCode, rftNo);
				//#CM575350
				// update worker result
				bo.alterWorkerResult(wr);
			}
			catch (Exception e1)
			{
				//#CM575351
				// If exception occurs throw ReadWriteException
				throw (new ReadWriteException());
			}
		}
	}
	
	//#CM575352
	/**
	 * Lock target data and fetch plan unique key list<BR>
	 * Work info,Storage plan info lock<BR>
	 * <DIR>
	 *    Work info<BR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Collect Job no.(Product identification code of telegraph data)</LI>
	 *     <LI>Job type=02:Storage</LI>
	 *     <LI>Status flag=2:In process</LI>
	 * 	   <LI>Worker code=Worker code(Worker code of telegraph data)</LI>
	 * 	   <LI>terminal no.=RFT no.(RFT no. of telegraph data)</LI>
	 *    </UL>
	 *    Storage plan info<BR>
	 *    (Search conditions)
	 *    <UL>
	 *     <LI>Storage plan unique key=Fetch from Work info</LI>
	 *     <LI>Status flag!=9:Delete</LI>
	 *    </UL>
	 * </DIR>
	 * @param	collectJobNo	Collect Job no.
	 * @param	workerCode		Worker code
	 * @param	rftNo			RFT no.
	 * @return	Plan unique key list
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws LockTimeOutException If the database lock is not removed after specified time
	 * @throws UpdateByOtherTerminalException If in process data is updated in another terminal
	 * @throws NotFoundException If target data does not exist for update
	 */
	protected String[] lockUpdateData(
		String collectJobNo,
		String workerCode,
		String rftNo) throws NotFoundException, ReadWriteException, LockTimeOutException, UpdateByOtherTerminalException
	{
		//#CM575353
		// Lock WorkingInformation
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setCollectJobNo(collectJobNo);
		skey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		skey.setCollectJobNoOrder(1, true);
		skey.setJobNoOrder(2, true);

		WorkingInformationHandler handler =	new WorkingInformationHandler(wConn);

		WorkingInformation[] wi = null;
		
		try
		{
			wi = (WorkingInformation[])handler.findForUpdate(skey,WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM575354
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNWORKINFO");
			throw e;
		}
		
		//#CM575355
		// Check whether updated from another terminal
		if (wi.length <= 0)
		{
	        throw new UpdateByOtherTerminalException();
		}
		
		for (int i = 0; i < wi.length; i ++)
		{
		    //#CM575356
		    // If in process data does not exist in this terminal
		    if (! rftNo.equals(wi[i].getTerminalNo()))
		    {
		        throw new UpdateByOtherTerminalException();
		    }

		    //#CM575357
		    // If worker code in process differs
		    if (! workerCode.equals(wi[i].getWorkerCode()))
		    {
		        throw new UpdateByOtherTerminalException();
		    }
		    
		    //#CM575358
		    // If the status is not "in process"
		    if (! wi[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
		    {
		        throw new UpdateByOtherTerminalException();
		    }

		    //#CM575359
		    // If other than Last update process
		    if (! START_PROCESS_NAME.equals(wi[i].getLastUpdatePname()))
		    {
		        throw new UpdateByOtherTerminalException();
		    }
		}

		String[] planUkeyList = getPlanUkeyList(collectJobNo, workerCode, rftNo);

		//#CM575360
		// Lock Storage plan info
		StoragePlanHandler sHandler = new StoragePlanHandler(wConn);
		StoragePlanSearchKey sskey = new StoragePlanSearchKey();

		sskey.setStoragePlanUkey(planUkeyList);
		sskey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");		
		try
		{
			sHandler.findForUpdate(sskey,WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM575361
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNSTORAGEPLAN");
			throw e;
		}

		return planUkeyList;
	}

	//#CM575362
	/**
	 * Create new work info record<BR>
	 * Used during additional delivery<BR>
	 * @param pWorkInfo <CODE>WorkingInformation</CODE> class instance that stores setting info
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws InvalidStatusException If status outside range is set
	 * @throws DataExistsException If similar data already exist
	 */
	protected void createWorkInfo(
				WorkingInformation pWorkInfo) throws ReadWriteException, InvalidStatusException, DataExistsException

	{
		SequenceHandler sequence = new SequenceHandler(wConn);

		WorkingInformation winfo = new WorkingInformation();

		//#CM575363
		// Job no..
		winfo.setJobNo(sequence.nextJobNo());
		//#CM575364
		// Job type:Storage
		winfo.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		//#CM575365
		// Collect Job no..
		winfo.setCollectJobNo(winfo.getJobNo());
		winfo.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		winfo.setBeginningFlag(pWorkInfo.getBeginningFlag());
		winfo.setPlanUkey(pWorkInfo.getPlanUkey());
		winfo.setStockId(pWorkInfo.getStockId());
		winfo.setAreaNo(pWorkInfo.getAreaNo());
		winfo.setLocationNo(pWorkInfo.getLocationNo());
		winfo.setPlanDate(pWorkInfo.getPlanDate());
		winfo.setConsignorCode(pWorkInfo.getConsignorCode());
		winfo.setConsignorName(pWorkInfo.getConsignorName());
		winfo.setSupplierCode(pWorkInfo.getSupplierCode());
		winfo.setSupplierName1(pWorkInfo.getSupplierName1());
		winfo.setInstockTicketNo(pWorkInfo.getInstockTicketNo());
		winfo.setCustomerCode(pWorkInfo.getCustomerCode());
		winfo.setCustomerName1(pWorkInfo.getCustomerName1());
		winfo.setShippingTicketNo(pWorkInfo.getShippingTicketNo());
		winfo.setShippingLineNo(pWorkInfo.getShippingLineNo());
		winfo.setItemCode(pWorkInfo.getItemCode());
		winfo.setItemName1(pWorkInfo.getItemName1());
		winfo.setHostPlanQty(pWorkInfo.getHostPlanQty());
		winfo.setPlanQty(pWorkInfo.getPlanEnableQty() - pWorkInfo.getResultQty());
		winfo.setPlanEnableQty(pWorkInfo.getPlanEnableQty() - pWorkInfo.getResultQty());
		winfo.setResultQty(0);
		winfo.setShortageCnt(0);
		winfo.setPendingQty(0);
		winfo.setEnteringQty(pWorkInfo.getEnteringQty());
		winfo.setBundleEnteringQty(pWorkInfo.getBundleEnteringQty());
		winfo.setCasePieceFlag(pWorkInfo.getCasePieceFlag());
		winfo.setWorkFormFlag(pWorkInfo.getWorkFormFlag());
		winfo.setItf(pWorkInfo.getItf());
		winfo.setBundleItf(pWorkInfo.getBundleItf());
		winfo.setTcdcFlag(pWorkInfo.getTcdcFlag());
		winfo.setUseByDate(pWorkInfo.getUseByDate());
		winfo.setLotNo(pWorkInfo.getLotNo());
		winfo.setPlanInformation(pWorkInfo.getPlanInformation());
		winfo.setOrderNo(pWorkInfo.getOrderNo());
		winfo.setOrderingDate(pWorkInfo.getOrderingDate());
		winfo.setResultUseByDate("");
		winfo.setResultLotNo("");
		winfo.setResultLocationNo("");
		winfo.setReportFlag(pWorkInfo.getReportFlag());
		winfo.setBatchNo(pWorkInfo.getBatchNo());
		winfo.setWorkerCode("");
		winfo.setWorkerName("");
		winfo.setTerminalNo("");
		winfo.setPlanRegistDate(pWorkInfo.getPlanRegistDate());
		winfo.setRegistPname(PROCESS_NAME);
		winfo.setLastUpdatePname(PROCESS_NAME);

		WorkingInformationHandler workHandler = new WorkingInformationHandler(wConn);

		workHandler.create(winfo);

	}

	//#CM575366
	/**
	 * Check whether the target data is upated via another terminal<BR>
	 * Search Work info data using Plan qty, Collect Job no.,
	 * and decide plan qty<BR>
	 * When plan qty differs, consider as being updated from another terminal and
	 * throw throw UpdateByOtherTerminalException<BR>
	 * @param collectDataOnRft Data stored by RFT
	 * @param collectDataOnDB	Current Work info
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 * @throws UpdateByOtherTerminalException If In process target data is updated in another terminal
	 */
	protected void checkCollectData(int collectDataOnRft, WorkingInformation[] collectDataOnDB) 
		throws UpdateByOtherTerminalException, ReadWriteException
	{
		//#CM575367
		// Calculate total plan qty from database
		int count = 0;
		for(int i=0; i < collectDataOnDB.length; i++)
		{
			count += collectDataOnDB[i].getPlanEnableQty();
		}
		
		//#CM575368
		// If the plan qty does not match with the RFT data, it has been updated by another terminal
		if(collectDataOnRft != count)
		{
			throw new UpdateByOtherTerminalException();
		}
		
		//#CM575369
		// Check status
		//#CM575370
		// If all the work is "in process", use Last update process name from this class
		for (int j = 0; j < collectDataOnDB.length; j++)
		{
			if (! collectDataOnDB[j].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
			{
				throw new UpdateByOtherTerminalException();
			}

			if (! collectDataOnDB[j].getLastUpdatePname().equals(START_PROCESS_NAME))
			{
				throw new UpdateByOtherTerminalException();
			}
		}
	}
	
	//#CM575371
	// Private methods -----------------------------------------------


}
//#CM575372
//end of class
