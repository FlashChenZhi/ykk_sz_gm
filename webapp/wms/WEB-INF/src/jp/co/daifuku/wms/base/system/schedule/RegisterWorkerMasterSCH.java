//#CM698593
//$Id: RegisterWorkerMasterSCH.java,v 1.2 2006/11/13 06:03:06 suresh Exp $

//#CM698594
/*
 * Created on 2004/08/05
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkerHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerSearchKey;
import jp.co.daifuku.wms.base.entity.Worker;

//#CM698595
/**
 * Designer : suresh kayamboo<BR>
 * Maker 	: suresh kayamboo<BR> 
 *
 * Allow the <CODE>RegisterWorkerMasterSCH</CODE> class to execute the process for adding workers.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 
 * 1. Process by clicking "Add" button (<CODE>startSCH()</CODE> method) <BR><BR>
 * <DIR>
 *   Receive the content displayed in the Preset area as a parameter and execute the process for adding the worker. <BR>
 *   Return true if the process completed normally. Or, return false when failed to schedule completely due to condition error or other causes. <BR>
 *   Use the <CODE>getMessage()</CODE> method to refer the content of error. <BR> 
 * <DIR>
 *   <Parameter>* Mandatory Input<BR>
 * <DIR>
 * 	Worker Code* (WorkerCode*)<BR>
 *  Person Name* (WorkerName*)<BR>
 *  Phonetic transcriptions in kana (Furigana)<BR>
 *  Sex (Sex)<BR>
 *  Job Title (JobType) <BR>
 *  Access Privileges (AccessLevel) <BR>
 *  Password* (Password*) <BR>
 *  Memo 1 (Memo1) <BR>
 *  Memo 2 (Memo2) <BR>
 * </DIR>
 * </DIR>
 * </DIR>
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/16</TD><TD>suresh kayamboo</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:06 $
 * @author  $Author: suresh $
 */
public class RegisterWorkerMasterSCH extends AbstractSystemSCH
{
	//#CM698596
	// Class variables -----------------------------------------------

	//#CM698597
	/**
	 * Class Name (Submit/Add Worker)
	 */
	private static final String wProcessName = "RegisterWorkerMasterSCH";

	//#CM698598
	/**
	 * Return the version of this class.
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $. $Date: 2004/08/16 ");
	}

	//#CM698599
	/**
	 * Initialize this class.
	 */
	public RegisterWorkerMasterSCH()
	{
		wMessage = null;
	}

	//#CM698600
	/**
	 * Check for duplicate Worker Code.
	 * Return false if no data is duplicated. Or, set a message and return true if duplicate data exist.
	 * 
	 * @param conn       Database connection object
	 * @param checkParam This parameter class includes contents to be checked for input.
	 * @return Return false if two or more data does not occupy a single position. Return true if two or more data occupies a single position.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean check(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException
	{
		boolean resultFlag = false;

		//#CM698601
		// Translate the data type of checkParam.
		SystemParameter sysParam = (SystemParameter) checkParam;

		WorkerHandler workerHandler = new WorkerHandler(conn);
		WorkerSearchKey workerSearchkey = new WorkerSearchKey();

		workerSearchkey.setWorkerCode(sysParam.getWorkerCode());

		if (workerHandler.count(workerSearchkey) > 0)
		{
			wMessage = "6007007";
		}
		else
		{
			resultFlag = true;
		}

		return resultFlag;
	}

	//#CM698602
	/**
	 * Start the schedule.Check for input and add the Worker info to the Worker Information table.
	 * 
	 * @param conn        Database connection object
	 * @param startParams A parameter class that contains the contents entered via screen.
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		boolean isOk = false;

		//#CM698603
		// Translate the type of startParams.
		SystemParameter[] sysParams = (SystemParameter[]) startParams;

		//#CM698604
		// Check for entered Worker Code and password.
		if (!checkWorker(conn, sysParams[1], false))
		{
			return false;
		}

		if (check(conn, sysParams[0]))
		{
			//#CM698605
			// Generate an instance for Worker info.
			Worker worker = new Worker();

			WorkerHandler workerHandler = new WorkerHandler(conn);

			worker.setWorkerCode(sysParams[0].getWorkerCode());
			worker.setName(sysParams[0].getWorkerName());

			//#CM698606
			// Determine the job title.
			if (sysParams[0]
				.getSelectWorkerJobType()
				.equals(SystemParameter.SELECTWORKERJOBTYPE_ADMINISTRATOR))
			{
				worker.setWorkerJobType(Worker.JOB_TYPE_ADMINISTRATOR);
			}
			else if (
				sysParams[0].getSelectWorkerJobType().equals(
					SystemParameter.SELECTWORKERJOBTYPE_WORKER))
			{
				worker.setWorkerJobType(Worker.JOB_TYPE_WORKER);
			}

			worker.setFurigana(sysParams[0].getFurigana());

			//#CM698607
			// Determine the Sex.
			if (sysParams[0].getSelectSex().equals(SystemParameter.SELECTSEX_MALE))
			{
				worker.setSex(Worker.MALE);
			}
			else if (sysParams[0].getSelectSex().equals(SystemParameter.SELECTSEX_FEMALE))
			{
				worker.setSex(Worker.FEMALE);
			}

			//#CM698608
			// Determine the Access Privileges.
			if (sysParams[0]
				.getSelectAccessAuthority()
				.equals(SystemParameter.SELECTACCESSAUTHORITY_ADMINISTRATOR))
			{
				worker.setAccessAuthority(Worker.ACCESS_AUTHORITY_ADMINISTRATOR);
			}
			else if (
				sysParams[0].getSelectAccessAuthority().equals(
					SystemParameter.SELECTACCESSAUTHORITY_SYSTEMADMINISTRATOR))
			{
				worker.setAccessAuthority(Worker.ACCESS_AUTHORITY_SYSTEMADMINISTRATOR);
			}
			else if (
				sysParams[0].getSelectAccessAuthority().equals(
					SystemParameter.SELECTACCESSAUTHORITY_WORKER))
			{
				worker.setAccessAuthority(Worker.ACCESS_AUTHORITY_WORKER);
			}

			worker.setPassword(sysParams[0].getPassword());
			worker.setMemo1(sysParams[0].getMemo1());
			worker.setMemo2(sysParams[0].getMemo2());

			if (sysParams[0].getSelectStatus() != null)
			{
				if (sysParams[0].getSelectStatus().equals(SystemParameter.SELECTSTATUS_ENABLE))
				{
					worker.setDeleteFlag(Worker.DELETE_FLAG_OPERATION);
				}
				else if (
					sysParams[0].getSelectStatus().equals(SystemParameter.SELECTSTATUS_DISABLE))
				{
					worker.setDeleteFlag(Worker.DELETE_FLAG_SUSPEND);
				}
			}
			else
			{
				worker.setDeleteFlag(Worker.DELETE_FLAG_OPERATION);
			}

			worker.setRegistPname(wProcessName);
			worker.setLastUpdatePname(wProcessName);
			
			try
			{
				workerHandler.create(worker);
				wMessage = "6001003";
				isOk = true;
			}
			catch (DataExistsException de)
			{
				throw new ScheduleException();
			}
		}

		return isOk;
	}
}
//#CM698609
//end of class
