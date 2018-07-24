//#CM44387
//$Id: AsDailyUpdateSCH.java,v 1.2 2006/10/30 01:04:34 suresh Exp $

//#CM44388
/*
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ASInventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.ASInventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InOutResultHandler;
import jp.co.daifuku.wms.base.dbhandler.InOutResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.system.schedule.DailyUpdateSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM44389
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 *
 * Class which does the next update processing of the day only for the ASRS package. <BR>
 * Process it in this Class as follows. <BR>
 * < CODE>DailyUpdateSCH</CODE>Class is succeeded to, and only necessary processing
 * doing override is used in the ASRS package.
 * <BR>
 * 1.Check processing (<CODE>check(Connection conn, Parameter checkParam)</CODE>Method)<BR>
 * <BR>
 *     <DIR>
 *     <processing outline>
 *        <DIR>
 *        Check whether the next update of parameter and the day can be done. <BR>
 *        </DIR>
 * <BR>
 *     <processing details>
 *        <DIR>
 *        1.Execute the check processing of super-Class. <BR>
 *        2.Process it only for the ASRS package. <BR>
 *          <DIR>
 *          Status of group Control information is off-line. <BR>
 *          </DIR>
 *        </DIR>
 *     </DIR>
 * <BR>
 * 2.Processing when Start button is pressed(<CODE>startSCH(Connection conn, Parameter[] checkParam)</CODE>Method)<BR>
 * <BR>
 *     <DIR>
 *     Update of Next day processing<BR>
 *     <processing outline>
 *        <DIR>
 *        1.Unnecessary data in data base of Next day update of super-Class deletion processing<BR>
 *        2.Unnecessary data in data base only for ASRS package deletion processing<BR>
 *          <DIR>
 *          The processing of super-Class is executed excluding the above-mentioned. <BR>
 *          </DIR>
 *        </DIR>
 * <BR>
 *     <processing details>
 *        <DIR>
 *        1.Unnecessary data in data base of super-Class deletion processing<BR>
 *        2.Unnecessary data in data base special of ASRS package deletion processing<BR>
 *          <DIR>
 *          2-1.Delete work information (No plan storage, schedule going out warehouse, and stock confirmation). <BR>
 *            <DIR>
 *            Work Flag = No plan storage, No plan retrieval, Stock confirmation<BR>
 *            Plan Date <= Work date of warenavi_system information and<BR>
 *            Status flag = End, Deletion<BR>
 *            </DIR>
 * <BR>
 *          2-2.Deletion of ASRS operation Results information. <BR>
 *            <DIR>
 *            Delete all records having Registration day < Present date - Results maintenance days of warenavi_system information<BR>
 *            </DIR>
 * <BR>
 *          2-3.Deletion does ASRS Stock confirmation information. <BR>
 *            <DIR>
 *            Do the data that Status has processed (Stock confirmation unwork) and do all Deletion. <BR>
 *            </DIR>
 *          </DIR>
 * <BR>
 *        3.Resetting the counter<BR>
 *          <DIR>
 *          Work NoResetting the counter<BR>
 *            <DIR>
 *            3-1.Resetting the schedule sequence<BR>
 *            3-2.Resetting the Work no sequence<BR>
 *            3-3.Resetting the Work No(For Storage) Sequence<BR>
 *            3-4.Resetting the Work No(For Retrieval) Sequence<BR>
 *            </DIR>
 *          </DIR>
 * <BR>
 *        </DIR>
 *     </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/03/28</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:04:34 $
 * @author  $Author: suresh $
 */
public class AsDailyUpdateSCH extends DailyUpdateSCH
{
	//#CM44390
	// Class fields --------------------------------------------------

	//#CM44391
	// Class variables -----------------------------------------------

	//#CM44392
	/**
	 * Class Name(Update of Next day processing)
	 */
	private final String wProcessName = "AsDailyUpdateSCH";

	//#CM44393
	// Class method --------------------------------------------------

	//#CM44394
	// Constructors --------------------------------------------------
	//#CM44395
	/**
	 * Constructor
	 */
	public AsDailyUpdateSCH()
	{
	}

	//#CM44396
	// Public methods ------------------------------------------------

	//#CM44397
	/**
	 * Check whether the content of parameter is correct. Do the parameter input content check processing according to the content set in
	 * parameter specified by < CODE>checkParam</CODE >. Mounting the check processing is different according to Class which mounts this interface. <BR>
	 * Return true when the content of parameter is correct. <BR>
	 * Return false when there is a problem in the content of parameter. The content can be acquired by using Method <CODE>getMessage()</CODE>.
	 * @param conn Connection object with database
	 * @param checkParam ParameterClass where content which does input check is included
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	public boolean check(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		if (!super.check(conn, checkParam))
		{
			return false;
		}

		//#CM44398
		/* Logic only for ASRS package */

		//#CM44399
		// It is not possible to set it for 'Online' Status of group Control information. 
		GroupControllerHandler grpHandle = new GroupControllerHandler(conn);
		GroupControllerSearchKey grpSearchKey = new GroupControllerSearchKey();
		
		grpSearchKey.setStatus(GroupController.STATUS_ONLINE);
		if (grpHandle.count(grpSearchKey) > 0)
		{
			//#CM44400
			// 6023480 = The system cannot be set because of online. 
			wMessage = "6023480";
			return false;
		}

		//#CM44401
		/* Logic only for ASRS package */

		//#CM44402
		/**
		 * Check Next day update of other additions. As for this Method, override is done by Class which succeeds to this Class. 
		 * When this Class is executed directly, nothing is done in extendSCH. 
		 * When this Class is made an instance directly, true is always returned. 
		 **/
		if (extendCheck(conn) == false)
		{
			return false;
		}

		return true;
	}

	//#CM44403
	/**
	 * Delete unnecessary plan and the work data. 
	 * @param conn Connection object with database
	 * @param param Schedule parameter array
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected void deleteWorkingData(Connection conn, SystemParameter[] param)
														throws ReadWriteException, ScheduleException
	{
		super.deleteWorkingData(conn, param);

		String workday = wWareNaviSystem.getWorkDate();

		//#CM44404
		/*************************************
		 * Delete Work information(No plan storage, No plan retrieval, Stock confirmation). 
		 *************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start deleteASRSWorkinfo ****");
		deleteAsrsWorkinfo(conn, workday);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   deleteASRSWorkinfo ****");

		//#CM44405
		/*************************************
		 * Delete ASRS operation Results information data.
		 *************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start deleteInOutResult ****");
		deleteInOutResult(conn, wDelResultDate);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   deleteInOutResult ****");

		//#CM44406
		/*************************************
		 * Delete ASRS Stock confirmation data.
		 *************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Start deleteAsInventoryCheck ****");
		deleteAsInventoryCheck(conn, wDelResultDate);
DEBUG.MSG("SCHEDULE", wProcessName + "  **** End   deleteAsInventoryCheck ****");

		//#CM44407
		/**
		 * Do Update of Next day processing of other additions.
		 * As for this Method, override is done by Class which succeeds to this Class. 
		 * When this Class is executed directly, nothing is done in extendSCH. 
		 **/
		extendSCH(conn, workday);
	}

	//#CM44408
	/**
	 * Initialize Sequence.
	 * @param conn Connection object with database
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	protected void resetSequence(Connection conn) throws ReadWriteException, ScheduleException
	{
		SequenceHandler sHandler = new SequenceHandler(conn);

		//#CM44409
		/**********************************************
		 * Reset Schedule number Sequence.
		 **********************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Reset SCHNO_SEQ ****");
		sHandler.ResetSchedulerNumber();

		//#CM44410
		/**********************************************
		 * Reset Work No Sequence.
		 **********************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Reset WORKNO_SEQ ****");
		sHandler.ResetWorkNumber();

		//#CM44411
		/**********************************************
		 * Reset Work No(For Storage) sequence.
		 **********************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Reset STORAGE_WORKNO_SEQ ****");
		sHandler.ResetStorageWorkNumber();

		//#CM44412
		/**********************************************
		 * Reset Work No(For Retrieval)sequence.
		 **********************************************/
DEBUG.MSG("SCHEDULE", wProcessName + "  **** Reset RETRIEVAL_WORKNO_SEQ ****");
		sHandler.ResetRetrievalWorkNumber();
	}

	//#CM44413
	/**
	 * Check the Next day update condition of the addition. 
	 * True in case of possible Next day update, False for the Next day update load.
	 * It is executed in checkMethod, and do override if necessary and use this Method. 
	 * Nothing is done in checkMethod when this Class is made directly to the instance and executed and always return true.   
	 * @param conn Database connection
	 * @return True in case of possible Next day update, False in case of Next day update load.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	public boolean extendCheck(Connection conn) throws ReadWriteException
	{
		//#CM44414
		// Do not do anything in AsDailyUpdateSCHClass. 
		//#CM44415
		// Return true always. 
		return true;
	}

	//#CM44416
	/**
	 * Do Update of Next day processing of the addition. 
	 * It is executed in startSCHMethod, and do override if necessary and use this Method. 
	 * When this Class is made directly to the instance and executed, nothing is done in extendSCHMethod. 
	 * @param conn Database connection
	 * @param workDate Work date
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	public void extendSCH(Connection conn, String workDate) throws ReadWriteException
	{
		//#CM44417
		// Do not do anything in AsDailyUpdateSCHClass. 
	}
	
	//#CM44418
	// Package methods -----------------------------------------------

	//#CM44419
	// Protected methods ---------------------------------------------

	//#CM44420
	// Private methods -----------------------------------------------
	//#CM44421
	/**
	 * Work informationDeletion processing<BR>
	 * Deletes Plan data and work info (No plan storage, No plan retrieval, Stock confirmation)<BR>
	 * @param conn Database connection
	 * @param workDate Work date
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected void deleteAsrsWorkinfo(Connection conn, String workDate) throws ReadWriteException
	{
		//#CM44422
		// Work information Deletion
		//#CM44423
		// Deletes Plan data and work info (No plan storage, No plan retrieval, Stock confirmation)
		WorkingInformationHandler workingInformationHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey WorkingInformationSearckKey = new WorkingInformationSearchKey();

		//#CM44424
		// Work Flag:No plan storage(21), No plan retrieval(22), Stock confirmation(50)
		WorkingInformationSearckKey.setJobType(WorkingInformation.JOB_TYPE_EX_STORAGE, "=", "((", "", "or");
		WorkingInformationSearckKey.setJobType(WorkingInformation.JOB_TYPE_EX_RETRIEVAL, "=", "", "", "or");
		WorkingInformationSearckKey.setJobType(WorkingInformation.JOB_TYPE_ASRS_INVENTORY_CHECK, "=", "", ")", "and");
		//#CM44425
		//	 ( ( Status flag = End or Deletion ) and Plan Date <= Work date of WareNavi System )
		WorkingInformationSearckKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION, "=", "(", "", "or");
		WorkingInformationSearckKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "=", "", ")", "and");
		WorkingInformationSearckKey.setPlanDate(workDate, "<=", "", ")", "or");
		
		try 
		{
			int count = workingInformationHandler.count(WorkingInformationSearckKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (deleteWorkingInformation) --> WorkingInformation Delete Data Count(" + count + ")");
			if(count > 0)
			{	
				workingInformationHandler.drop(WorkingInformationSearckKey);
			}
		}
		catch (NotFoundException e)
		{
		}
	}

	//#CM44426
	/**
	 * Deletes the ASRS operation Results information. <BR>
	 * @param conn Database connection
	 * @param deleteDate Deleting Boundary date
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private void deleteInOutResult(Connection conn, String deleteDate) throws ReadWriteException
	{
		//#CM44427
		// Deletes ASRS operation Results information
		//#CM44428
		// Deletes when Result maintenance date is exceeded
		InOutResultHandler inoutResultHandler = new InOutResultHandler(conn);
		InOutResultSearchKey inoutResultSearckKey = new InOutResultSearchKey();

		inoutResultSearckKey.setStoreDate(WmsFormatter.toDate(deleteDate), "<=");
		
		try 
		{
			int count = inoutResultHandler.count(inoutResultSearckKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (deleteInOutResult) --> InOutResult Delete Data Count(" + count + ")");
			if(count > 0)
			{	
				inoutResultHandler.drop(inoutResultSearckKey);
			}
		}
		catch (NotFoundException e)
		{
		}
	}

	//#CM44429
	/**
	 * Delete ASRS Stock confirmation Information.<BR>
	 * @param conn Database connection
	 * @param deleteDate Delete boundary date
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private void deleteAsInventoryCheck(Connection conn, String deleteDate) throws ReadWriteException
	{
		//#CM44430
		// Delete ASRS Stock confirmation Information
		//#CM44431
		// Delete if Status flag is [Processed (Stock confirmation Stand by)]
		ASInventoryCheckHandler asInventoryCheckHandler = new ASInventoryCheckHandler(conn);
		ASInventoryCheckSearchKey asInventoryCheckSearckKey = new ASInventoryCheckSearchKey();

		asInventoryCheckSearckKey.setStatus(Aisle.NOT_INVENTORYCHECK);
		
		try
		{
			int count = asInventoryCheckHandler.count(asInventoryCheckSearckKey);
DEBUG.MSG("SCHEDULE", wProcessName + "    (deleteAsInventoryCheck) --> AsInventoryCheck Delete Data Count(" + count + ")");
			if(count > 0)
			{
				asInventoryCheckHandler.drop(asInventoryCheckSearckKey);
			}
		}
		catch (NotFoundException e)
		{
		}
	}
}
//#CM44432
//end of class
