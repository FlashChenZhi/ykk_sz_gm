//#CM699026
//$Id: RFTStateSCH.java,v 1.3 2006/11/21 04:22:40 suresh Exp $

//#CM699027
/*
 * Created on Aug 16, 2004
 *
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.io.IOException;
import java.sql.Connection;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WorkOperator;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.dbhandler.MovementAlterKey;
import jp.co.daifuku.wms.base.dbhandler.MovementHandler;
import jp.co.daifuku.wms.base.dbhandler.RftAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkerHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.Worker;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdMessage;
import jp.co.daifuku.wms.base.rft.SendIdMessage;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.base.rft.WorkDataFile;

//#CM699028
/**
 * Designer : Muneendra <BR>
 * Author : Muneendra <BR>
 * <BR>
 * <BR>
 * 1 <CODE>Query()</CODE> method: Allow the XXXBusiness class to invoke this method to obtain the RFT status.<BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and obtain a data to be displayed from database and return it.<BR>
 *   If no corresponding data is found, return CODE>Parameter</CODE> array with number of elements equal to 0. Return null when condition error occurs.
 *   Allow the <CODE>getMessage()</CODE> method to refer the content of error.<BR>
 * <BR>
 *   <Parameter> Data input<BR>
 * <DIR>
 *     Worker Code* <BR>
 *     Password* <BR>
 * </DIR>
 *  <Parameter> Returned data <BR>
 * <DIR>
 *     RFTNo <BR>
 *     Worker Code <BR>
 *     Worker Name<BR>
 *     Status of RFT<BR>
 * </DIR>
 * <BR> For a worker code that can be obtained from DNWORKINFO corresponding to a worker name, obtain the worker code from the WMS_USER Table.
 * </DIR>
 * <BR>
 * 2<CODE>startSCHgetParams()</CODE>method :  Clicking on "Commit" button starts the process.<BR>
 *
 * <BR>
 * <DIR>
 *   Assume to change the status of RFT code to the status "Cancelled" in the DNRFT table.<BR>
 * 	 Obtain a new value from database and return it in the form of an array of Parameter object.<BR>
 *   <CODE>updateWorkStatus()</CODE> method: Update the work status.<BR>
 *   <OL><LI>Process for submitting:<BR>
 *           <UL><LI>Search through the RFT Work Status using Machine No. as a condition and obtain the electronic response statement.</LI>
 *               <LI>Obtain the Work ID from the obtained response statement.</LI>
 *               <LI>If the work with the obtained ID uses a work file (ID0132, ID0140, ID0330, ID430, or ID0532),<BR>
 *                   Search for the information of storing the processing data using Machine No. as a condition. If the corresponding data is found, obtain the work data.</LI>
 *               <LI>Execute the process for submitting via the obtained response statement (work data).</LI>
 *               <LI>When the process completed normally, update the corresponding field item of the RFT Work Status in the response statement to NULL.</LI>
 *               <LI>Delete the relevant records of the processing data in the stored information, to process for using the processing data.</LI>
 *           </UL>
 *        <LI>Process for cancel:<BR>
 *           <UL><LI>Restore the data with status "Processing" to the status "Not Worked".</LI>
 *               <LI>Search through the RFT Work Status using Machine No. as a condition if the process completed normally, and then obtain the corresponding electronic response statement.</LI>
 *               <LI>Obtain the Work ID from the obtained response statement.</LI>
 *               <LI>If the work with the obtained ID uses a work file (ID0132, ID0140, ID0330, ID430, or ID0532),<BR>
 *                   Search for the information of storing the processing data using Machine No. as a condition. If the corresponding data is found, delete its record.</LI>
 *               <LI>Search for the RFT Work Status using Machine No. as a condition and update the corresponding field item in the response statement to NULL.</LI>
 *           </UL>
 *   </OL>
 *   Return true if the process completed normally. Otherwise, return false.<BR>
 *   Allow the <CODE>getMessage()</CODE> method to refer the content of error.<BR>
 * <BR>
 *   <Parameter> Data input <BR>
 *   <DIR>
 * 			RFTNo <br>
 *   </DIR>
 *  <Parameter> Returned data<BR>
 *   <DIR>
 *     RFTNo <BR>
 *     Worker Code <BR>
 *     Worker Name<BR>
 * 	   Status of RFT<BR>
 *   </DIR>
 * </DIR>
 *
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:22:40 $
 * @author  $Author: suresh $
 */
public class RFTStateSCH extends AbstractSystemSCH
{

	//#CM699029
	// Class variables -----------------------------------------------
	//#CM699030
	/**
	 * Electric statement ID list of starting RFT work.
	 */
	private final String[][] startProcessList = {{"ID0130", "ID0132", "ID0140"},
	        									   {"ID0230"},
	        									   {"ID0330", "ID0340"},
	        									   {"ID0430"},
	        									   {"ID0530", "ID0532"}};
	//#CM699031
	/**
	 * Class Name (RFT)
	 */
	private final String LAST_UPDATE_PNAME = "RFTStateSCH";

	//#CM699032
	// Constructors --------------------------------------------------
	//#CM699033
	/**
	 * Initialize this class.
	 */
	public RFTStateSCH()
	{
		wMessage = null;
	}

	//#CM699034
	//	 Public methods ------------------------------------------------
	//#CM699035
	/**
	 * Receive the entered data via screen as a parameter and obtain the data for the Preset area from database.<BR>
	 * If no corresponding record is found, return array with number of elements equal to 0<BR>
	 * Return null if the count of records exceed 1000, or if error occurs in the input conditions. <BR>
	 * Returning array with element qty 0 (zero) or null allows to obtain the error contents as a message using <CODE>getMessage()</CODE> method.
	 * @param conn        Database connection object
	 * @param searchParam An instance of the <CODE>SystemParameter</CODE> class that contains display data for obtaining conditions.<BR>
	 * @return Array of <CODE>Parameters</CODE> instance that contains search results. <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce when unexpected exception occurs during the process of checking.
	 */
	public Parameter[] query(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		SystemParameter systemParameter[] = null;
		try
		{

			RftSearchKey rftSearchkey = new RftSearchKey();
			RftHandler rftHandler = new RftHandler(conn);
			rftSearchkey.setRftNoOrder(1, true);
			Rft rft[] = (Rft[]) rftHandler.find(rftSearchkey);

			if (rft == null || rft.length == 0)
			{
				//#CM699036
				// 6003018=No target data was found.
				wMessage = "6003018";
				return null;
			}

			systemParameter = new SystemParameter[rft.length];
			WorkerHandler workerHandler = new WorkerHandler(conn);

			for (int i = 0; i < rft.length; ++i)
			{
				systemParameter[i] = new SystemParameter();

				try
				{
					//#CM699037
					// RFT status "Standby":
					if (!SystemDefine.JOB_TYPE_UNSTART.equals(rft[i].getJobType()))
					{

						WorkerSearchKey workerSearchKey = new WorkerSearchKey();
						workerSearchKey.setWorkerCode(rft[i].getWorkerCode());
						Worker worker = (Worker) workerHandler.findPrimary(workerSearchKey);
						//#CM699038
						// Check for presence of worker code.
						if (worker == null)
						{
							//#CM699039
							// 6006003=Failed to generate the instance. Class name={0} {1}
							wMessage = "6006003";
							return null;
						}
						//#CM699040
						// Set the Worker Code.
						systemParameter[i].setDisplayWorkerCode(rft[i].getWorkerCode());
						//#CM699041
						// Set the Worker Name.
						systemParameter[i].setWorkerName(worker.getName());
					}

				}
				catch (NoPrimaryException noPrimaryException)
				{
					throw new ScheduleException(noPrimaryException.getMessage());
				}
				//#CM699042
				// Set the RFT No.
				systemParameter[i].setRftNo(rft[i].getRftNo());
				//#CM699043
				// Set the Terminal Type.
				systemParameter[i].setTerminalType(DisplayUtil.getTerminalType(rft[i].getTerminalType()));
				//#CM699044
				// Set the RFT status.
				systemParameter[i].setRftStatus(DisplayUtil.getJobType(rft[i]));
				//#CM699045
				// Set the Work division.
				systemParameter[i].setJobType(rft[i].getJobType());

				//#CM699046
				// 6001013=Data is shown.
				wMessage = "6001013";

			}

		}
		catch (ReadWriteException readWrite)
		{
			wMessage = readWrite.getMessage();
			throw new ScheduleException(wMessage);
		}
		return systemParameter;
	}

	//#CM699047
	/**
	 * Start the schedule. According to the contents set in the parameter array designated in the <CODE>startParams</CODE>,<BR>
	 * execute the process for the schedule. Implement a scheduling process depending on the class implementing this interface.<BR>
	 * Use this method to display the content displayed on screen again based on the schedule result.
	 * Return null if failed to schedule due to condition error or other causes.<BR>
	 * In this case, use the <CODE>getMessage()</CODE> method to obtain the contents.
	 * @param conn        Database connection object
	 * @param startParams Database connection object
	 * @return Return true if a schedule process completed normally. Return false if failed to process a schedule.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{

		//#CM699048
		// Translate the type of startParams.
		SystemParameter sparam[] = (SystemParameter[]) startParams;

		if (sparam == null || sparam.length == 0)
		{
			wMessage = "6023024";
			return null;
		}

		//#CM699049
		// Check for Worker code and password.
		if (!checkWorker(conn, sparam[0], true))
		{
			wMessage = getMessage();
			return null;
		}

		//#CM699050
		// Check the Daily Update Processing.
		if (isDailyUpdate(conn))
		{
			wMessage = getMessage();
			return null;
		}

		String rftNumber = sparam[0].getRftNo();

		RftHandler rftHandler = new RftHandler(conn);

		try
		{
			RftSearchKey searchKey = new RftSearchKey();
			searchKey.setRftNo(rftNumber);
			Rft rft = (Rft) rftHandler.findPrimary(searchKey);
			if (rft == null)
			{
				wMessage = "6003018";
				return null;
			}

			sparam[0].setJobType(rft.getJobType());
		}
		catch (NoPrimaryException primaryKeyException)
		{
			throw new ScheduleException(primaryKeyException.getMessage());
		}

		try
		{
			//#CM699051
			// Update the record.
			RftAlterKey alterKey = new RftAlterKey();
			alterKey.setRftNo(rftNumber);
			alterKey.updateJobType(Rft.JOB_TYPE_UNSTART);
			alterKey.updateRestFlag(Rft.REST_FLAG_NOTREST);
			alterKey.updateStatusFlag(Rft.RFT_STATUS_FLAG_STOP);
			alterKey.updateRadioFlag(Rft.RADIO_FLAG_IN);

			alterKey.updateWorkerCode("");

			alterKey.updateLastUpdatePname(LAST_UPDATE_PNAME);

			rftHandler.modify(alterKey);

			//#CM699052
			// Update the Work status plan info.
			updateWorkStatus(conn, sparam);
			sparam = (SystemParameter[]) query(conn, null);

			wMessage = "6001018";

		}
		catch (InvalidDefineException invalidDefine)
		{
			throw new ScheduleException(invalidDefine.getMessage());
		}
		catch (NotFoundException notFound)
		{
			throw new ScheduleException(notFound.getMessage());
		}

		return sparam;
	}

	//#CM699053
	/**
	 * Update the Work Status.
	 *
	 * @param conn		DB Connection
	 * @param param	Array of Parameter entered via screen.
	 * @return Return true if a schedule process completed normally. Return false if failed to process a schedule.
	 * @throws ReadWriteException Announce when error occurs on the connection to DB.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean updateWorkStatus(Connection conn, SystemParameter[] param)
		throws ReadWriteException, ScheduleException
	{
		PackageManager.initialize(conn);

		try
		{
			jp.co.daifuku.wms.base.rft.WorkingInformation[] workinfo = null;

			if (param[0].getWorkOnTheWay().equals(SystemParameter.RFT_WORK_CANSEL))
			{
				//#CM699054
				// For canceling a process,
				forceCancel(conn, param[0].getRftNo());
			}
			else
			{
				//#CM699055
				// or submitting a process,
				WorkOperator wo = workOperatorFactory(param[0].getJobType());
				if (wo != null)
				{
					wo.initialize(conn, param[0].getJobType(), true);
					wo.setProcessName(LAST_UPDATE_PNAME);
				}
				else
				{
					//#CM699056
					// or executing any process for data with no processing data, close the process.
					return true;
				}

				if (IdMessage.checkResponseId(param[0].getRftNo(), conn))
				{
					//#CM699057
					// Obtain the stored response statement.
					IdMessage responseMessage = (SendIdMessage)IdMessage.loadMessageFile(param[0].getRftNo(), conn);

					//#CM699058
					// Obtain the array of the processing data from the processing data file.
				    WorkDataFile dataFile = WorkDataFile.loadWorkingDataFile(param[0].getRftNo(), conn);
				    workinfo = (jp.co.daifuku.wms.base.rft.WorkingInformation[]) dataFile.getWorkingData();
				    dataFile.setRequestInfo(workinfo, responseMessage);

					//#CM699059
					// Set a flag to show wheter shortage or not.
				    boolean isShortage = param[0].getLackAmount().equals(SystemParameter.LACK_AMOUNT_SHORTAGE);

				    //#CM699060
				    // Generate a class to process the work.
				    wo.completeWorkingData(workinfo, param[0].getDisplayWorkerCode(), param[0].getRftNo(), isShortage);
				}

				try
				{
					//#CM699061
					// Change the status of the Worker Result to End.
					wo.closeWorkerResult(param[0].getDisplayWorkerCode(), param[0].getRftNo());
				}
				catch (NotFoundException e)
				{
				}
			}

			//#CM699062
			// Invoke the Delete method for the ID Message class and
			//#CM699063
			// delete the processing data.
			IdMessage.deleteWorkingData(param[0].getRftNo(), LAST_UPDATE_PNAME, conn);
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (UpdateByOtherTerminalException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (LockTimeOutException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
        catch (IOException e)
        {
			throw new ScheduleException(e.getMessage());
        }
        catch (IllegalAccessException e)
        {
			throw new ScheduleException(e.getMessage());
        }
        catch (DataExistsException e)
        {
			throw new ScheduleException(e.getMessage());
        }
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}

		return true;
	}

	//#CM699064
	/**
	 * Obtain the data with status Processing of the designated machine.
	 *
	 * @param conn		DB Connection
	 * @param rftNo	RFT Machine No.
	 * @param jobType	Work Type
	 * @return Work Status
	 * @throws ReadWriteException Announce when error occurs on the connection to DB.
	 */
	public Entity[] getWorkingData(Connection conn, String rftNo, String jobType) throws ReadWriteException
	{
		jp.co.daifuku.wms.base.rft.WorkingInformation[] workinfo = null;
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

		skey.setTerminalNo(rftNo);
		skey.setJobType(jobType);
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);

		WorkingInformationHandler handler = new jp.co.daifuku.wms.base.rft.WorkingInformationHandler(conn);
		workinfo = (jp.co.daifuku.wms.base.rft.WorkingInformation[]) handler.find(skey);

		return workinfo;
	}

	//#CM699065
	/**
	 * Restore all the designated machine data with status "Processing" to the status "Not Worked".<BR>
	 * Return all the work status of relocation work other than Relocation for Storage to Not Worked.<BR>
	 * Update the status of each Plan info except for Relocation for Storage.<BR>
	 *
	 * @param conn		DB Connection
	 * @param rftNo	RFT Machine No.
	 * @throws ReadWriteException 		Announce when error occurs on the connection to DB.
	 * @throws NotFoundException		Announce when no target data was found.
	 * @throws InvalidDefineException	Announce when setting no updated content.
	 * @throws ScheduleException 		Announce it when unexpected exception occurs in the process of scheduling.
	 * @throws UpdateByOtherTerminalException Announce when the target work data was updated via other work station.
	 * @throws LockTimeOutException 	Announce when failed to lock the data within the designated time.
	 * @throws InvalidStatusException 	Announce when setting improper value for entity.
	 */
	protected void forceCancel(Connection conn,
			String rftNo) throws ReadWriteException, NotFoundException, InvalidDefineException, ScheduleException, UpdateByOtherTerminalException, LockTimeOutException, InvalidStatusException
	{
		String[] jobTypes = {WorkingInformation.JOB_TYPE_INSTOCK,
				WorkingInformation.JOB_TYPE_SHIPINSPECTION,
				WorkingInformation.JOB_TYPE_SORTING,
				WorkingInformation.JOB_TYPE_STORAGE,
				WorkingInformation.JOB_TYPE_RETRIEVAL};

		jp.co.daifuku.wms.base.rft.WorkingInformation[] workinfo = null;

		for (int i = 0; i < jobTypes.length; i ++)
		{
			String jobType = jobTypes[i];

			//#CM699066
			// Obtain the array of the processing data.
			workinfo = (jp.co.daifuku.wms.base.rft.WorkingInformation[]) getWorkingData(conn,
									  rftNo,
									  jobType);

			if (workinfo != null && workinfo.length > 0)
			{
				WorkOperator wo = workOperatorFactory(jobType);
				if (wo != null)
				{
					wo.initialize(conn, jobType, true);
					wo.setProcessName(LAST_UPDATE_PNAME);

					//#CM699067
					// Invoke the process for canceling the process class.
					wo.cancel(workinfo, null, rftNo);
				}
			}
		}

		//#CM699068
		// Cancel the Relocation work status.
		cancelStockMoving(conn, rftNo);
	}

	//#CM699069
	/**
	 * Generate an instance of the class designated to execute the completing process by work type.<BR>
	 * Require to initialize the package manager before invoking this method.<BR>
	 *
	 * @param jobType	Work Type
	 * @return	An instance of a class to execute the completing process by work type.<BR>
	 *  			Return null if the designated work type has no work with status Processing.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	protected WorkOperator workOperatorFactory(String jobType) throws ScheduleException
	{
		WorkOperator wo = null;

		String className = "";
		String[] startProcessName = null;
		//#CM699070
		// Generate an instance for process by work type.
		if (jobType.equals(WorkingInformation.JOB_TYPE_INSTOCK))
		{
			//#CM699071
			// Receiving
			className = "InstockReceiveWorkOperator";
			startProcessName = startProcessList[0];
		}
		else if (jobType.equals(WorkingInformation.JOB_TYPE_STORAGE))
		{
			//#CM699072
			// Storage
			className = "StorageWorkOperator";
			startProcessName = startProcessList[1];
		}
		else if (jobType.equals(WorkingInformation.JOB_TYPE_RETRIEVAL))
		{
			//#CM699073
			// Picking
			className = "RetrievalWorkOperator";
			startProcessName = startProcessList[2];
		}
		else if (jobType.equals(WorkingInformation.JOB_TYPE_SORTING))
		{
			//#CM699074
			// Sorting
			className = "SortingWorkOperator";
			startProcessName = startProcessList[3];
		}
		else if (jobType.equals(WorkingInformation.JOB_TYPE_SHIPINSPECTION))
		{
			//#CM699075
			// Shipping
			className = "ShippingWorkOperator";
			startProcessName = startProcessList[4];
		}
		else
		{
		    //#CM699076
		    // Close the process for data with no work status "Processing".
		    return null;
		}

		try
		{
			//#CM699077
			// Generate a class to process the work.
			wo = (WorkOperator) PackageManager.getObject(className);
			wo.setStartProcessName(startProcessName);
		}
		catch (IllegalAccessException e)
		{
			throw new ScheduleException(e.getMessage());
		}

		return wo;
	}

	//#CM699078
	/**
	 * Restore the status "Storage In Process" of Inventory relocation data to "Standby Storage".<BR>
	 *
	 * [Update Conditions]<BR>
	 * Status flag：Storage In Process<BR>
	 * Terminal No.：Parameter<BR>
	 * <BR>
	 * [Updated Value]<BR>
	 * Status flag」Standby Storage<BR>
	 * Worker Code：NULL<BR>
	 * Worker Name：NULL<BR>
	 * Terminal No.：NULL<BR>
	 * Last update process name："RFTStateSCH"<BR>
	 *
	 * @param conn		Connection for database connection
	 * @param rftNo	RFT Machine No.
	 * @throws ReadWriteException Announce when error occurs on the connection to DB.
	 * @throws InvalidDefineException	Announce when setting no updated content.
	 */
	public void cancelStockMoving(Connection conn, String rftNo)
		throws ReadWriteException, InvalidDefineException
	{
		MovementHandler handler = new MovementHandler(conn);

		//#CM699079
		// Update the Relocation work status.
		MovementAlterKey akey = new MovementAlterKey();
		akey.setStatusFlag(Movement.STATUSFLAG_NOWWORKING);
		akey.setTerminalNo(rftNo);
		akey.updateStatusFlag(Movement.STATUSFLAG_UNSTART);
		akey.updateWorkerCode("");
		akey.updateWorkerName("");
		akey.updateTerminalNo("");
		akey.updateLastUpdatePname(LAST_UPDATE_PNAME);
		try
		{
			handler.modify(akey);
		}
		catch (NotFoundException e)
		{
		}
	}
}
//#CM699080
//end of class
