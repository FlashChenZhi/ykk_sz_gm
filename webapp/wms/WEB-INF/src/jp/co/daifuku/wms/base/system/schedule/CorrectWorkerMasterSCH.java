//#CM695668
//$Id: CorrectWorkerMasterSCH.java,v 1.2 2006/11/13 06:03:14 suresh Exp $

//#CM695669
/*
 * Created on 2004/08/05
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkerAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkerHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerSearchKey;
import jp.co.daifuku.wms.base.entity.Worker;

//#CM695670
/**
 * Designer : suresh kayamboo<BR>
 * Maker 	: suresh kayamboo<BR> 
 *
 * Allow the <CODE>CorrectWorkerMasterSCH</CODE> class to modify or delete a Worker data.<BR>
 * Receive the content entered via screen as a parameter and execute the process for modifying or deleting the Worker master. <BR>
 * <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process by clicking "Next" button<BR>
 * <DIR>
 * <Parameter>*Mandatory Input<BR>
 * <DIR>
 * 		Worker Code*<BR>
 * 		Password*<BR>
 * 		Modified Worker Code*<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <DIR>
 *   	Receive the contents entered via screen as a parameter and obtain a data to be modified from database and return it.<BR>
 *  	If no corresponding data is found, return <CODE>Parameter</CODE> array with array with number of elements equal to 0. Return null when condition error occurs.<BR>
 *   	Use the <CODE>getMessage()</CODE> method to refer the content of error.<BR>
 * 		<DIR>
 *      <Parameter> Returned data<BR>
 * 		<DIR>
 * 			Worker Code<BR>
 * 			Person Name<BR>
 * 			Phonetic transcriptions in kana<BR>
 * 			Sex<BR>
 * 			Job Title<BR>
 * 			Access Privileges<BR>
 * 			Password<BR>
 * 			Memo 1<BR>
 * 			Memo 2<BR>
 * 			Add ON/OFF Status<BR>
 * 			Last Update Date<BR>
 * 		</DIR>
 * 		</DIR>
 * </DIR>
 * 2. Process by clicking "Modify/Add" button<BR>
 * <DIR>
 *  Receive the entered data via screen as a parameter and execute the process for modify and add the worker master. <BR>
 *  Return null when the schedule failed to complete due to condition error or other causes. <BR>
 *  Use the <CODE>getMessage()</CODE> method to refer the content of error. <BR>
 * 	Execute <CODE>startSCHgetParams</CODE>. Allow users to pass the added parameter to <CODE>startParams</CODE>.<BR>
 * 	Synchronize the Last update date/time displayed on the screen with the Last update date/time that actually exists in the DMWORKER table.<BR>
 * 	Update the DMWORKER table if acceptable for checking.<BR>
 * </DIR>
 * <DIR>
 * <Parameter>*Mandatory Input<BR>
 * <DIR>
 * 		Person Name*<BR>
 * 		Phonetic transcriptions in kana<BR>
 * 		Sex<BR>
 * 		Job Title<BR>
 * 		Access Privileges<BR>
 * 		Password*<BR>
 * 		Memo 1<BR>
 * 		Memo 2<BR>
 * 		Add ON/OFF Status*<BR>	
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>suresh kayamboo</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:14 $
 * @author  $Author: suresh $
 */
public class CorrectWorkerMasterSCH extends AbstractSystemSCH
{

	//#CM695671
	// Class variables -----------------------------------------------

	//#CM695672
	/**
	 * Class Name (Modify/Delete Worker Master)
	 */
	private static final String LAST_UPDATE_PNAME = "CorrectWorkerMasterSCH";

	//#CM695673
	// Class method --------------------------------------------------
	//#CM695674
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $. $Date: 2004/08/16 ");
	}

	//#CM695675
	/**
	 * Initialize this class.
	 */
	public CorrectWorkerMasterSCH()
	{
		wMessage = null;
	}

	//#CM695676
	/**
	 * This method obtains the data required for initial display.<BR>
	 * Search conditions passes a class that inherits the<CODE>Parameter</CODE> class.<BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{

		//#CM695677
		// Translate the type of searchParam.
		SystemParameter sysParam = (SystemParameter) searchParam;

		//#CM695678
		// Obtain the Worker code.
		String workerCode = sysParam.getWorkerCode();

		if ((workerCode != null) && !workerCode.equals(""))
		{
			WorkerSearchKey wSkey = new WorkerSearchKey();
			WorkerHandler wHandler = new WorkerHandler(conn);

			//#CM695679
			// Set the search conditions.
			wSkey.setWorkerCode(workerCode);

			Worker[] worker = (Worker[]) wHandler.find(wSkey);

			sysParam.setWorkerCode(worker[0].getWorkerCode());
			sysParam.setWorkerName(worker[0].getName());
			sysParam.setFurigana(worker[0].getFurigana());

			//#CM695680
			// Determine the Sex.
			if (worker[0].getSex().equals(Worker.MALE))
			{
				sysParam.setSelectSex(SystemParameter.SELECTSEX_MALE);
			}
			if (worker[0].getSex().equals(Worker.FEMALE))
			{
				sysParam.setSelectSex(SystemParameter.SELECTSEX_FEMALE);
			}

			//#CM695681
			// Determine the job title.
			if (worker[0].getWorkerJobType().equals(Worker.JOB_TYPE_ADMINISTRATOR))
			{
				sysParam.setSelectWorkerJobType(SystemParameter.SELECTWORKERJOBTYPE_ADMINISTRATOR);
			}
			else if (worker[0].getWorkerJobType().equals(Worker.JOB_TYPE_WORKER))
			{
				sysParam.setSelectWorkerJobType(SystemParameter.SELECTWORKERJOBTYPE_WORKER);
			}

			//#CM695682
			// Determine the Access Privileges.
			if (worker[0].getAccessAuthority().equals(Worker.ACCESS_AUTHORITY_ADMINISTRATOR))
			{
				sysParam.setSelectAccessAuthority(
					SystemParameter.SELECTACCESSAUTHORITY_ADMINISTRATOR);
			}
			else if (
				worker[0].getAccessAuthority().equals(Worker.ACCESS_AUTHORITY_SYSTEMADMINISTRATOR))
			{
				sysParam.setSelectAccessAuthority(
					SystemParameter.SELECTACCESSAUTHORITY_SYSTEMADMINISTRATOR);
			}
			else if (worker[0].getAccessAuthority().equals(Worker.ACCESS_AUTHORITY_WORKER))
			{
				sysParam.setSelectAccessAuthority(SystemParameter.SELECTACCESSAUTHORITY_WORKER);
			}

			sysParam.setPassword(worker[0].getPassword());
			sysParam.setMemo1(worker[0].getMemo1());
			sysParam.setMemo2(worker[0].getMemo2());

			//#CM695683
			// Determine the Add ON/OFF Status.
			if (worker[0].getDeleteFlag().equals(Worker.DELETE_FLAG_OPERATION))
			{
				sysParam.setSelectStatus(SystemParameter.SELECTSTATUS_ENABLE);
			}
			else if (worker[0].getDeleteFlag().equals(Worker.DELETE_FLAG_SUSPEND))
			{
				sysParam.setSelectStatus(SystemParameter.SELECTSTATUS_DISABLE);
			}

			sysParam.setRegistDate(worker[0].getRegistDate());
			sysParam.setLastUpdateDate(worker[0].getLastUpdateDate());
		}

		return sysParam;
	}

	//#CM695684
	/**
	 * Receive the contents entered via screen as a parameter and obtain a data to be displayed from database and return it.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 **/
	public Parameter[] query(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		//#CM695685
		// Translate the type of searchParam.
		SystemParameter sysParam = (SystemParameter) searchParam;

		SystemParameter[] sysParamArray = new SystemParameter[1];

		if ((sysParam != null) && !sysParam.equals(""))
		{
			//#CM695686
			// Obtain the Worker code.
			String workerCode = sysParam.getWorkerCode();

			WorkerSearchKey wSkey = new WorkerSearchKey();
			WorkerHandler wHandler = new WorkerHandler(conn);

			//#CM695687
			// Set the search conditions.
			wSkey.setWorkerCode(workerCode);

			Worker[] worker = (Worker[]) wHandler.find(wSkey);

			if ((worker == null) || worker.length == 0)
			{
				wMessage = "";
			}
			else
			{
				sysParamArray[0] = new SystemParameter();
				sysParamArray[0].setWorkerCode(worker[0].getWorkerCode());
				sysParamArray[0].setWorkerName(worker[0].getName());
				sysParamArray[0].setFurigana(worker[0].getFurigana());

				//#CM695688
				// Determine the Sex.
				if (worker[0].getSex().equals(Worker.MALE))
				{
					sysParamArray[0].setSelectSex(SystemParameter.SELECTSEX_MALE);
				}
				if (worker[0].getSex().equals(Worker.FEMALE))
				{
					sysParamArray[0].setSelectSex(SystemParameter.SELECTSEX_FEMALE);
				}

				//#CM695689
				// Determine the job title.
				if (worker[0].getWorkerJobType().equals(Worker.JOB_TYPE_ADMINISTRATOR))
				{
					sysParamArray[0].setSelectWorkerJobType(
						SystemParameter.SELECTWORKERJOBTYPE_ADMINISTRATOR);
				}
				else if (worker[0].getWorkerJobType().equals(Worker.JOB_TYPE_WORKER))
				{
					sysParamArray[0].setSelectWorkerJobType(
						SystemParameter.SELECTWORKERJOBTYPE_WORKER);
				}

				//#CM695690
				// Determine the Access Privileges.
				if (worker[0].getAccessAuthority().equals(Worker.ACCESS_AUTHORITY_ADMINISTRATOR))
				{
					sysParamArray[0].setSelectAccessAuthority(
						SystemParameter.SELECTACCESSAUTHORITY_ADMINISTRATOR);
				}
				else if (
					worker[0].getAccessAuthority().equals(
						Worker.ACCESS_AUTHORITY_SYSTEMADMINISTRATOR))
				{
					sysParamArray[0].setSelectAccessAuthority(
						SystemParameter.SELECTACCESSAUTHORITY_SYSTEMADMINISTRATOR);
				}
				else if (worker[0].getAccessAuthority().equals(Worker.ACCESS_AUTHORITY_WORKER))
				{
					sysParamArray[0].setSelectAccessAuthority(
						SystemParameter.SELECTACCESSAUTHORITY_WORKER);
				}

				sysParamArray[0].setPassword(worker[0].getPassword());
				sysParamArray[0].setMemo1(worker[0].getMemo1());
				sysParamArray[0].setMemo2(worker[0].getMemo2());

				//#CM695691
				// Determine the division of Add ON/OFF.
				if (worker[0].getDeleteFlag().equals(Worker.DELETE_FLAG_OPERATION))
				{
					sysParamArray[0].setSelectStatus(SystemParameter.SELECTSTATUS_ENABLE);
				}
				else if (worker[0].getDeleteFlag().equals(Worker.DELETE_FLAG_SUSPEND))
				{
					sysParamArray[0].setSelectStatus(SystemParameter.SELECTSTATUS_DISABLE);
				}

				sysParamArray[0].setRegistDate(worker[0].getRegistDate());
				sysParamArray[0].setRegistDate(worker[0].getLastUpdateDate());
				sysParamArray[0].setLastUpdateDate(worker[0].getLastUpdateDate());
				sysParamArray[0].setLastUpdatePName(worker[0].getLastUpdatePname());
			}

		}
		return sysParamArray;
	}

	//#CM695692
	/**
	 * Receive the contents entered via screen as a parameter and return the result of check for Worker code, password, and presence of corresponding data. <BR>
	 * Return true if corresponding data exist and the contents of Worker code and password are correct.<BR>
	 * Check the contents of the parameter for its properness.According to the contents set for the parameter designated in <CODE>checkParam</CODE>,<BR>
	 * execute the process for checking the input in the parameter.Implement of the check process depends on the class that implements this interface.<BR>
	 * Use the <CODE>getMessage()</CODE> method to obtain the contents .<BR>
	 * Allow this method to implement the check for input to shift from the original screen to the second screen between two screens.
	 * @param conn Database connection object
	 * @param checkParam This parameter class includes contents to be checked for input.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean nextCheck(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException
	{

		boolean nextOk = false;

		//#CM695693
		// Translate the data type of checkParam.
		SystemParameter sysParam = (SystemParameter) checkParam;

		//#CM695694
		// Check for entered Worker Code and password.
		if (!checkWorker(conn, sysParam, false))
		{
			return false;
		}

		//#CM695695
		// Obtain the Worker code.
		String workerCode = sysParam.getWorkerCodeB();

		if ((workerCode != null) && !workerCode.equals(""))
		{
			WorkerHandler wHandler = new WorkerHandler(conn);
			WorkerSearchKey wSkey = new WorkerSearchKey();

			//#CM695696
			// Set the search conditions.
			wSkey.setWorkerCode(workerCode);

			if (wHandler.count(wSkey) > 0)
			{
				nextOk = true;
			}
			else
			{
				//#CM695697
				// 6003018=No target data was found.
				wMessage = "6003018";
			}
		}

		return nextOk;
	}

	//#CM695698
	/**
	 * Receive the contents entered via screen as a parameter and start the schedule for modifying or adding the Worker master.<BR>
	 * Return true if the schedule normally completed, or return false if failed.
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Database connection object
	 * @param startParams Database connection object
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{

		//#CM695699
		// Translate the type of startParams.
		SystemParameter[] sysParamArray = (SystemParameter[]) startParams;

		WorkerHandler workerHandler = new WorkerHandler(conn);
		WorkerSearchKey workerSearchKey = new WorkerSearchKey();

		//#CM695700
		// Set the search conditions.
		workerSearchKey.setWorkerCode(sysParamArray[0].getWorkerCode());
		Worker[] workerTempArray = (Worker[]) workerHandler.find(workerSearchKey);

		if ((workerTempArray == null) || workerTempArray.length == 0)
		{
			//#CM695701
			// 6003016=Data could not be modified.
			wMessage = "6003016";
			return null;
		}

		//#CM695702
		/**
		 * Execute if the date has not yet been changed to the update value.
		 */
		if (workerTempArray[0]
			.getLastUpdateDate()
			.toString()
			.equals(sysParamArray[0].getWLastUpdateDateString()))
		{
			try
			{
				//#CM695703
				//Generate the AlterKey.
				WorkerAlterKey workerAkey = new WorkerAlterKey();

				//#CM695704
				//Set the Worker Code.
				workerAkey.setWorkerCode(sysParamArray[0].getWorkerCode());

				//#CM695705
				//Worker Name to be updated.
				workerAkey.updateName(sysParamArray[0].getWorkerName());
				//#CM695706
				//Set the "phonetic transcriptions in kana" to be updated.
				workerAkey.updateFurigana(sysParamArray[0].getFurigana());

				//#CM695707
				//Set the Sex to be updated.
				if (sysParamArray[0].getSelectSex().equals(SystemParameter.SELECTSEX_MALE))
				{
					workerAkey.updateSex(Worker.MALE);
				}
				else if (sysParamArray[0].getSelectSex().equals(SystemParameter.SELECTSEX_FEMALE))
				{
					workerAkey.updateSex(Worker.FEMALE);
				}

				//#CM695708
				//Set the work type to be updated.
				if (sysParamArray[0]
					.getSelectWorkerJobType()
					.equals(SystemParameter.SELECTWORKERJOBTYPE_ADMINISTRATOR))
				{
					workerAkey.updateWorkerJobType(Worker.JOB_TYPE_ADMINISTRATOR);
				}
				else if (
					sysParamArray[0].getSelectWorkerJobType().equals(
						SystemParameter.SELECTWORKERJOBTYPE_WORKER))
				{
					workerAkey.updateWorkerJobType(Worker.JOB_TYPE_WORKER);
				}

				//#CM695709
				//Set the Access Privileges to be updated.
				if (sysParamArray[0]
					.getSelectAccessAuthority()
					.equals(SystemParameter.SELECTACCESSAUTHORITY_ADMINISTRATOR))
				{
					workerAkey.updateAccessAuthority(Worker.ACCESS_AUTHORITY_ADMINISTRATOR);
				}
				else if (
					sysParamArray[0].getSelectAccessAuthority().equals(
						SystemParameter.SELECTACCESSAUTHORITY_SYSTEMADMINISTRATOR))
				{
					workerAkey.updateAccessAuthority(Worker.ACCESS_AUTHORITY_SYSTEMADMINISTRATOR);
				}
				else if (
					sysParamArray[0].getSelectAccessAuthority().equals(
						SystemParameter.SELECTACCESSAUTHORITY_WORKER))
				{
					workerAkey.updateAccessAuthority(Worker.ACCESS_AUTHORITY_WORKER);
				}

				//#CM695710
				//Set the password to be updated.
				workerAkey.updatePassword(sysParamArray[0].getPassword());
				//#CM695711
				//Set Memo 1 to be updated.
				workerAkey.updateMemo1(sysParamArray[0].getMemo1());
				//#CM695712
				//Set Memo 2 to be updated.
				workerAkey.updateMemo2(sysParamArray[0].getMemo2());
				//#CM695713
				//Set the Add ON/OFF Status to be updated.
				if (sysParamArray[0].getSelectStatus().equals(SystemParameter.SELECTSTATUS_ENABLE))
				{
					workerAkey.updateDeleteFlag(Worker.DELETE_FLAG_OPERATION);
				}
				else if (
					sysParamArray[0].getSelectStatus().equals(
						SystemParameter.SELECTSTATUS_DISABLE))
				{
					workerAkey.updateDeleteFlag(Worker.DELETE_FLAG_SUSPEND);
				}

				//#CM695714
				//Set Updated Program Name
				workerAkey.updateLastUpdatePname(LAST_UPDATE_PNAME);

				//#CM695715
				//Modify.
				workerHandler.modify(workerAkey);

				//#CM695716
				// 6001004=Modified.
				wMessage = "6001004";

				//#CM695717
				// Obtain a new data from the DMWORKER table.
				sysParamArray = (SystemParameter[]) query(conn, sysParamArray[0]);

			}
			catch (NotFoundException ne)
			{
				throw new ScheduleException();
			}
			catch (InvalidDefineException ie)
			{
				throw new ScheduleException();
			}
		}
		else
		{
			//#CM695718
			// Set Error Message
			//#CM695719
			// 6003016=Data could not be modified.
			wMessage = "6003016";
			return null;
		}

		return sysParamArray;
	}

	//#CM695720
	// private method --------------------------------------------------

}
//#CM695721
//end of class
