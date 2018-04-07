//#CM643679
//$Id: WorkOperator.java,v 1.2 2006/11/07 06:04:25 suresh Exp $
package jp.co.daifuku.wms.base.common;

import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;

//#CM643680
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM643681
/**
 * Designer : T.Konishi <BR>
 * Maker : T.Konishi <BR>
 * <BR>
 * Use it as a template of the Java source program while making new.<BR>
 * When you develop the Java source program related to eWareNavi
 * Develop according to the coding rule with this template. <BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:04:25 $
 * @author  $Author: suresh $
 */
public abstract class WorkOperator
{
	//#CM643682
	// Class fields --------------------------------------------------
	//#CM643683
	/**
	 * Class name
	 */
	public static final String CLASS_NAME = "WorkOperate";

	//#CM643684
	// Class variables -----------------------------------------------
	//#CM643685
	/**
	 * Class name
	 */
	protected String className = CLASS_NAME;
	//#CM643686
	/**
	 * Processing name
	 */
	protected String processName = className;
	//#CM643687
	/**
	 * Beginning update processing name
	 */
	protected String startProcessName[] = {""};
    //#CM643688
    /**
	 * Connection with data base
	 */
	protected Connection conn = null;
    //#CM643689
    /**
	 * Work flag
	 */	
	protected String jobType = "";
    //#CM643690
    /**
	 * Flag which shows whether it is cancellation by maintenance screen
	 */	
	protected boolean isMaintenanceCancel = false;
    //#CM643691
    /**
     * Flag indicates whether Stock Management exists or not
     */
    protected boolean WithStockManagement = true;

	//#CM643692
	// Class method --------------------------------------------------
	//#CM643693
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/07 06:04:25 $";
	}

	//#CM643694
	// Constructors --------------------------------------------------
	//#CM643695
	/**
	 * Construct <CODE>WorkOperator</CODE>. 
	 */
	public WorkOperator()
	{
		super();
	}

	//#CM643696
	// Public methods ------------------------------------------------
	//#CM643697
	/**
	 * Set <code>Connection</code> for data base connection. <BR>
	 * 
	 * @param c For data base connection Connection
	 */
    public void setConnection(Connection c)
    {
        conn = c;
    }

    //#CM643698
    /**
     * Set Update processing name. 
     * 
     * @param name		Update processing name
     */
    public void setProcessName(String name)
    {
        processName = name;
    }
    
    //#CM643699
    /**
     * Set Beginning update processing name. 
     * 
     * @param name		Beginning update processing name
     */
    public void setStartProcessName(String[] name)
    {
        startProcessName = name;
    }

    //#CM643700
    /**
     * Set Beginning update processing name. 
     * 
     * @param name		Beginning update processing name
     */
    public void setStartProcessName(String name)
    {
    	String[] pname = {name};
        startProcessName = pname;
    }

    //#CM643701
    /**
	 * Initialize the instance. <BR>
	 * Set data base connection <code>Connection</code><BR>
	 * Set the flag of the stock control existence. <BR>
	 * 
	 * @param c For data base connection Connection
	 * @param jobType	Work type
	 * @param isMaintenanceCancel	Flag which shows whether it is cancellation by maintenance screen
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
    public void initialize(Connection c, String jobType, boolean isMaintenanceCancel) throws ReadWriteException
    {
        conn = c;

        this.jobType = jobType;
        
        this.isMaintenanceCancel = isMaintenanceCancel;
        
        SystemParameter.setConnection(conn);
        WithStockManagement = SystemParameter.withStockManagement();
    }

    //#CM643702
    /**
	 * Results data [Cancel Processing]<BR>
	 * Process it as follows as the cancellation processing of work. 
	 * 
	 * <OL>
	 *  <LI>Lock data for the update. 
	 * ({@link #lockUpdateData(WorkingInformation[],String,String,boolean) lockUpdateData()})</LI>
	 *  <LI>Update pertinent data of Work information in the state of Stand by. 
	 * ({@link #updateWorkingInformation(String,String,WorkingInformation[]) updateWorkingInformation()})</LI>
	 *  <LI>Update schedule information after data is canceled appropriately. 
	 * ({@link #updateCompletionStatus(String[]) updateCompletionStatus()})</LI>
	 * </OL>
	 * 
	 * <DIR>
	 * Renewal of Work information<BR>
	 *    (Search condition)
	 *    <UL>
	 *     <LI>Status flag=2:Working</LI>
	 *     <LI>Plan date</LI>
	 *     <LI>Consignor Code</LI>
	 * 	   <LI>Terminal No = RFT number</LI>
	 *    </UL>
	 *    (Content of update)
	 *    <TABLE>
	 *      <TR><TD>Status flag</TD>		<TD>Stand by:0</TD></TR>
	 *      <TR><TD>Terminal No</TD>			<TD>Blank</TD></TR>
	 *      <TR><TD>Worker Code</TD>	<TD>Blank</TD></TR>
	 *    </TABLE>
	 *   It is necessary to consider the thing that two or more records correspond. <BR>
	 * </DIR>
	 * 
	 * @param	resultData		Work results
	 * 							(Update the work form in the value of renewed Work information. )
	 * @param	workerCode		Worker Code
	 * @param	rftNo			RFT number
	 * @throws NotFoundException  It is notified when data for the update does not exist. 
	 * @throws InvalidDefineException It is notified when the specified value is abnormal(The blank and the prohibition character are included). 
	 * @throws UpdateByOtherTerminalException It is notified when pertinent data is updated to Working in another. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws LockTimeOutException It is notified when the lock of the data base is not released for a fixed time. 
	 * @throws InvalidStatusException Notify when there is no adjustment in a set value of the table update. 
	*/
	public void cancel(
		WorkingInformation[] resultData,
		String workerCode,
		String rftNo)
		throws NotFoundException, InvalidDefineException, UpdateByOtherTerminalException, ReadWriteException, LockTimeOutException, InvalidStatusException
	{
	    //#CM643703
	    // Lock data for the update. 
		String[] planUkeyList = lockUpdateData(
			resultData,
			rftNo,
			workerCode,
			true);
		
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationAlterKey akey = new WorkingInformationAlterKey();

		for (int i = 0; i < resultData.length; i ++)
		{
		    skey.KeyClear();
			skey.setCollectJobNo(resultData[i].getCollectJobNo());
			skey.setTerminalNo(rftNo);
			if (workerCode != null)
			{
				skey.setWorkerCode(workerCode);
			}
			skey.setJobType(jobType);
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			if (! isMaintenanceCancel)
			{
				skey.setLastUpdatePname(startProcessName);
			}
			
			WorkingInformationHandler handler = new WorkingInformationHandler(conn);
			WorkingInformation[] wi = (WorkingInformation[])handler.find(skey);
			
			for (int j = 0; j < wi.length; j ++)
			{
			    akey.KeyClear();
				akey.setJobNo(wi[j].getJobNo());
				
				//#CM643704
				// Return it based on Consolidating work No. (Return to work No.)
				akey.updateCollectJobNo(wi[j].getJobNo());
				akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
				akey.updateWorkerCode("");
				akey.updateWorkerName("");
				akey.updateTerminalNo("");
				akey.updateLastUpdatePname(processName);
				handler.modify(akey);
			}
		}

		//#CM643705
		// Return it based on the state of Work information based on the state of schedule information. 
		updateCompletionStatus(planUkeyList);
	}

	//#CM643706
	/**
	 * Work results data [Final processing]<BR>
	 * Execute the following final processing about Work information. <BR>
	 * 
	 * <OL>
	 *  <LI>Lock data for the update. 
	 * ({@link #lockUpdateData(WorkingInformation[],String,String,boolean) lockUpdateData()})</LI>
	 *  <LI>Renew Work information in the status of Finished. 
	 * Make and update schedule information, the inventory information, and results transmission information which relates to it. 
	 * ({@link #updateWorkingInformation(String,String,WorkingInformation[]) updateWorkingInformation()})</LI>
	 *  <LI>Update schedule information after data is fixed appropriately. 
	 * ({@link #updateCompletionStatus(String[]) updateCompletionStatus()})</LI>
	 *  <LI>Update Worker results information. 
	 * ({@link #updateWorkerResult(String,String,WorkingInformation[]) updateWorkerResult()})</LI>
	 *  <LI>Execute the stock shift processing. (Unloading)</LI>
	 * </OL>
	 * 
	 * <BR>
	 * @param	resultData		Work results information(Array of work information entity)
	 * @param	workerCode		Worker Code
	 * @param	rftNo			RFT number
	 * @param  isShortage		Shortage flag
	 * @throws LockTimeOutException It is notified when the lock of the data base is not released for a fixed time. 
	 * @throws InvalidStatusException Notify when there is no adjustment in a set value of the table update. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException  It is notified when data for the update does not exist. 
	 * @throws InvalidDefineException It is notified when the specified value is abnormal(The blank and the prohibition character are included). 
	 * @throws UpdateByOtherTerminalException It is notified when pertinent data is updated to Working in another. 
	 * @throws DataExistsException It had already been notified when same information had registered when movement information was registered. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	*/
	public void completeWorkingData(
			jp.co.daifuku.wms.base.rft.WorkingInformation[] resultData,
			String workerCode,
			String rftNo,
			boolean isShortage)
		throws LockTimeOutException, InvalidStatusException, ReadWriteException, NotFoundException, InvalidDefineException, DataExistsException, UpdateByOtherTerminalException, ScheduleException
	{
	    //#CM643707
	    // Lock data for the update. 
		String[] planUkeyList = lockUpdateData(
			resultData,
			rftNo,
			workerCode,
			false);

		//#CM643708
		// Make and update relating Work information and plan information, inventory information, and results transmission information. 
		updateWorkingInformation(
			rftNo,
			workerCode,
			resultData,
			isShortage);

		//#CM643709
		// Update the status of plan information. 
		updateCompletionStatus(planUkeyList);
		
		//#CM643710
		// Make Worker results. 
		updateWorkerResult(workerCode, rftNo, resultData, ! isShortage);
	}
	
	//#CM643711
	/**
	 * Final processing for PCart<BR>
	 * Do this method in override and mount processing about the function that the PCart supports it. <BR>
	 * Execute the following final processing about Work information. <BR>
	 * 
	 * <OL>
	 *  <LI>Lock data for the update. 
	 * ({@link #lockUpdateData(WorkingInformation[],String,String,boolean) lockUpdateData()})</LI>
	 *  <LI>Renew Work information in the status of Finished. 
	 * Make and update schedule information, the inventory information, and results transmission information which relates to it. 
	 * ({@link #updateWorkingInformation(String,String,WorkingInformation[]) updateWorkingInformation()})</LI>
	 *  <LI>Update schedule information after data is fixed appropriately. 
	 * ({@link #updateCompletionStatus(String[]) updateCompletionStatus()})</LI>
	 *  <LI>Update Worker results information. 
	 * ({@link #updateWorkerResult(String,String,WorkingInformation[]) updateWorkerResult()})</LI>
	 *  <LI>Execute the stock shift processing. (Unloading)</LI>
	 * </OL>
	 * 
	 * <BR>
	 * @param	resultData		Work results information(Array of work information entity)
	 * @param	workerCode		Worker Code
	 * @param	rftNo			RFT number
	 * @param  isShortage		Shortage flag
	 * @throws LockTimeOutException It is notified when the lock of the data base is not released for a fixed time. 
	 * @throws InvalidStatusException Notify when there is no adjustment in a set value of the table update. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException  It is notified when data for the update does not exist. 
	 * @throws InvalidDefineException It is notified when the specified value is abnormal(The blank and the prohibition character are included). 
	 * @throws UpdateByOtherTerminalException It is notified when pertinent data is updated to Working in another. 
	 * @throws DataExistsException It had already been notified when same information had registered when movement information was registered. 
	*/
	public void completePcWorkingData(
	        WorkingInformation[] resultData,
			String workerCode,
			String rftNo,
			boolean isShortage)
		throws LockTimeOutException, InvalidStatusException, ReadWriteException, NotFoundException, InvalidDefineException, DataExistsException, UpdateByOtherTerminalException
	{
	}

	//#CM643712
	/**
	 * Renew Work information. 
	 * 
	 * @param rftNo RFTTerminal No.
	 * @param workerCode Worker Code
	 * @param workinfo Work information
	 * @param isShortage Shortage flag
	 * @throws InvalidStatusException Notify when there is no adjustment in a set value of the table update. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException  It is notified when data for the update does not exist. 
	 * @throws InvalidDefineException It is notified when the specified value is abnormal(The blank and the prohibition character are included). 
	 * @throws DataExistsException It had already been notified when same information had registered when data was registered. 
	 * @throws UpdateByOtherTerminalException It is notified when pertinent data is updated to Working in another. 
	 */
	public abstract void updateWorkingInformation(String rftNo,
													 String workerCode,
													 WorkingInformation[] workinfo,
													 boolean isShortage)
		throws InvalidStatusException,
				ReadWriteException,
				NotFoundException,
				InvalidDefineException,
				DataExistsException,
				UpdateByOtherTerminalException;
	
	//#CM643713
	/**
	 * Update schedule information based on renewed Work information. <BR>
	 * However, do not renew Status flag in this method. <BR>
	 * (Renew Status flag by <CODE>updateCompletionStatus() </CODE> method. )<BR>
	 * <DIR>
	 *    (Search condition)
	 *    <UL>
	 *     <LI>Plan information . Plan unique key = Work information.Plan unique key (There is one schedule information corresponding to Work information. )
	 *     <LI>Status flag != Deletion
	 *    </UL>
	 *    (Content of update)
	 *    <TABLE>
	 *      <TR><TD>Work actual number</TD>		<TD>(+) ID0037.Actual qty</TD></TR>
	 *      <TR><TD>Work Shortage qty</TD>		<TD>(+) ID0037.Shortage qty</TD></TR>
	 *      <TR><TD>Last update processing name</TD>	<TD>"ID0037" or "ID0037"</TD></TR>
	 *    </TABLE>
	 *   The actual qty, the actual qty of corresponding Work information in Shortage qty, and add Shortage qty. <BR>
	 *   Do not renew Status flag here. <BR>
	 * </DIR>
	 * 
	 * @param wi	Work results data to be updated
	 * @throws InvalidStatusException Notify when there is no adjustment in a set value of the table update. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException  It is notified when data for the update does not exist. 
	 * @throws InvalidDefineException It is notified when the specified value is abnormal(The blank and the prohibition character are included). 
	 */
	public abstract void updatePlanInformation(
	        WorkingInformation wi)
		throws
			InvalidStatusException,
			ReadWriteException,
			NotFoundException,
			InvalidDefineException;
	
    //#CM643714
    /**
     * The status locks the one other than Deletion among schedule information with Plan unique key specified by the argument. 
     * 
     * @param	planUKeyList[]	Array of Plan unique key
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws LockTimeOutException It is notified when the lock of the data base is not released for a fixed time. 
     */
	protected abstract void lockPlanInformation(String[] planUKeyList) throws ReadWriteException, LockTimeOutException ;
	
	//#CM643715
	/**
	 * Lock the inventory information to be updated.<BR>
	 * Generate Array of Stock ID from Array of Work information specified by the argument. 
	 * Lock the corresponding inventory information. 
	 * <DIR>
	 *    (Search condition)
	 *    <UL>
	 *     <LI>Stock ID</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param	workinfo		Array of Work information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws LockTimeOutException It is notified when the lock of the data base is not released for a fixed time. 
	 */
	protected void lockStockData(WorkingInformation[] workinfo)
		throws ReadWriteException, LockTimeOutException
	{
	    String[] stockIdList = new String[workinfo.length];
	    for (int i = 0; i < workinfo.length; i ++)
	    {
            stockIdList[i] = workinfo[i].getStockId();
	    }
		StockSearchKey skey = new StockSearchKey();
		skey.setStockId(stockIdList);
		
		StockHandler shandler = new StockHandler(conn);
		try
		{
			shandler.findForUpdate(skey, WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM643716
			// 6026018=After the fixed time had passed, the lock of Database was not released. Table name:{0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DMSTOCK");
			throw e;
		}
		
	}
	
	//#CM643717
	/**
	 * Lock data for the update, and return List of plan unique key of the data. <BR>
	 * The target table for the update is Work information, and schedule information. 
	 * The inventory information (When only there are Storage and Picking and a stock control and it is not a cancellation). 
	 * <DIR>
	 *    (Search condition)
	 *    <UL>
	 *     <LI>Consolidating work No</LI>
	 *     <LI>Work flag</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param	workingInformation	Consolidating work No
	 * @param	rftNo			RFT number
	 * @param	workerCode		Worker Code
	 * @param	isCancel		Flag whether it is cancellation of final processing or not
	 * 							(Do not lock the stock when canceling. )
	 * @return					Array of PickingPlan unique key which corresponds to condition
	 * @throws UpdateByOtherTerminalException It is notified when pertinent data is updated to Working in another. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws LockTimeOutException It is notified when the lock of the data base is not released for a fixed time. 
	 */
	protected String[] lockUpdateData(
		WorkingInformation[] workingInformation,
		String rftNo,
		String workerCode,
		boolean isCancel) throws UpdateByOtherTerminalException, ReadWriteException, LockTimeOutException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationHandler handler = new WorkingInformationHandler(conn);

		String[] collectJobNoList = new String[workingInformation.length];
		for (int i = 0; i < workingInformation.length; i ++)
		{
		    collectJobNoList[i] = workingInformation[i].getCollectJobNo();
		}

		skey.setCollectJobNo(collectJobNoList);
		skey.setJobType(jobType);
		
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION, "!=");
		skey.setCollectJobNoOrder(1, true);
		skey.setJobNoOrder(2, true);

		//#CM643718
		// Lock Working Information
		WorkingInformation[] wi = null;
		try
		{
			wi = (WorkingInformation[])handler.findForUpdate(skey, WmsParam.WMS_DB_LOCK_TIMEOUT);
		}
		catch (LockTimeOutException e)
		{
			//#CM643719
			// 6026018=After the fixed time had passed, the lock of Database was not released. Table name:{0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNWORKINFO");
			throw e;
		}
		
		if (! isMaintenanceCancel)
		{
			//#CM643720
			// Check whether to be updated in the other end. 
			if (wi.length <= 0)
			{
		        throw new UpdateByOtherTerminalException();
			}
			
			for (int i = 0; i < wi.length; i ++)
			{
			    //#CM643721
			    // When the terminal is not data made Working
			    if (! rftNo.equals(wi[i].getTerminalNo()))
			    {
			        throw new UpdateByOtherTerminalException();
			    }

			    //#CM643722
			    // When Worker made Working is different
			    if (! workerCode.equals(wi[i].getWorkerCode()))
			    {
			        throw new UpdateByOtherTerminalException();
			    }
			    
			    //#CM643723
			    // When the status is not Working
			    if (! wi[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
			    {
			        throw new UpdateByOtherTerminalException();
			    }

				//#CM643724
				// When it is not data request processing that corresponds to Last update processing name
				Vector tempVec = new Vector();
				for (int j = 0; j < startProcessName.length; j++)
				{
					tempVec.addElement(startProcessName[j]);
				}
				if (!tempVec.contains(wi[i].getLastUpdatePname()))
				{
					throw new UpdateByOtherTerminalException();
				}
			}
		}
		
		//#CM643725
		// Generate List of plan unique key. 
		String[] planUkeyList = getPlanUkeyList(collectJobNoList, rftNo, workerCode);

		//#CM643726
		// Lock schedule information. 
		lockPlanInformation(planUkeyList);
		
		//#CM643727
		// Lock the inventory information to be updated. 
		if (WithStockManagement && ! isCancel)
		{
		    //#CM643728
		    // Addition processing which locks the stock when only there are Storage and Picking and a stock control and it is not cancellation processing. 
		    lockStockData(wi);
		}
		
		return planUkeyList;
	}

	//#CM643729
	/**
	 * Acquire List of plan unique key of data for the update. 
	 * <DIR>
	 *    (Search condition)
	 *    <UL>
	 *     <LI>Consolidating work No</LI>
	 *     <LI>Worker Code</LI>
	 *     <LI>RFTNo</LI>
	 *     <LI>Work flag</LI>
	 *     <LI>Status flag = Working</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param	collectJobNoList	Consolidating work No list
	 * @param	rftNo				RFT number
	 * @param	workerCode			Worker Code
	 * @return						Array of Plan unique key which corresponds to condition
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	protected String[] getPlanUkeyList(
		String[] collectJobNoList,
		String rftNo,
		String workerCode)
		throws ReadWriteException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setCollectJobNo(collectJobNoList);
		skey.setTerminalNo(rftNo);
		if (workerCode != null)
		{
			skey.setWorkerCode(workerCode);
		}
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		skey.setJobType(jobType);
		skey.setPlanUkeyCollect("DISTINCT");
		skey.setPlanUkeyOrder(1, true);
		WorkingInformationHandler wHandler = new WorkingInformationHandler(conn);
		WorkingInformation[] workinfo = (WorkingInformation[]) wHandler.find(skey);

		String[] planUkey = new String[workinfo.length];
		for (int i = 0; i < workinfo.length; i++)
		{
			planUkey[i] = workinfo[i].getPlanUkey();
		}
		return planUkey;
	}

	//#CM643730
	/**
	 * Renew Status flag of schedule information based on Plan unique key of renewed Work information. 
	 * <DIR>
	 *    (Search condition)
	 *    <UL>
	 *     <LI>Plan information . Plan unique key = Work information.Plan unique key (There is one schedule information corresponding to Work information. )</LI>
	 *     <LI>Status flag != Deletion</LI>
	 *    </UL>
	 *    (Content of update)
	 *    <TABLE>
	 *      <TR><TD>Status flag</TD>		<TD>It depends on the state of Work information. </TD></TR>
	 *      <TR><TD>Last update processing name</TD>	<TD>Class name or identification number</TD></TR>
	 *    </TABLE>
	 *   Renew the status flag. <BR>
	 *   Make it to Finished in case of AllFinished. <BR>
	 *   Make it to Working if there is as much as one Working. <BR>
	 *   Otherwise make it to Partially finished.  <BR>
	 * </DIR>
	 * 
	 * @param	planUkey				List of plan unique key
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException It is notified when data for the update does not exist. 
	 * @throws InvalidDefineException It is notified when the specified value is abnormal(The blank and the prohibition character are included). 
	 */
	protected abstract void updateCompletionStatus(String[] planUkey)
		throws ReadWriteException, NotFoundException, InvalidDefineException;

	//#CM643731
	/**
	 * Making of results transmission information<BR>
	 * <DIR>
	 *   Acquire Work date from WareNavi_System, and set it in Work date. <BR>
	 *   Set the registration processing name. <BR>
	 *   Acquire from Work information and set other items. <BR>
	 * </DIR>
	 * 
	 * @param wi			Work information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public void createResultData(WorkingInformation wi) throws ReadWriteException
	{
		HostSend result;
		BaseOperate baseOperate = new BaseOperate(conn);

		try
		{
			result = new HostSend(wi,
								  baseOperate.getWorkingDate(),
								  processName);
			HostSendHandler handler = new HostSendHandler(conn);
			handler.create(result);
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw new ReadWriteException();
		}
	}

	//#CM643732
	/**
	 * Make Worker results. 
	 * <DIR>
	 *    (Search condition)
	 *    <UL>
	 *     <LI>Work date = WMSWork date</LI>
	 *     <LI>Worker Code</LI>
	 *     <LI>RFTNo</LI>
	 *     <LI>Work flag</LI>
	 *    </UL>
	 *    (Content of update)
	 *    <TABLE border="1">
	 *      <TR><TD>Work frequency</TD>		<TD>(+) Data qty</TD></TR>
	 *      <TR><TD>Work qty</TD>			<TD>(+) Total of actual qty</TD></TR>
	 *      <TR><TD>Work end date and time</TD>	<TD>System date and time</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * Update it again after made when the corresponding Worker results do not exist. 
	 * 
	 * @param	workerCode			Worker Code
	 * @param	rftNo				Terminal No
	 * @param	workinfo		Work results information(Array of work information entity)
	 * @param	isPending		Flag whether it is Reserved or not
	 * @throws NotFoundException It is notified when data for the update does not exist. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	protected void updateWorkerResult(
			String workerCode, 
			String rftNo, 
			jp.co.daifuku.wms.base.rft.WorkingInformation[] workinfo,
			boolean isPending) throws ReadWriteException, NotFoundException
	{
		updateWorkerResult(workerCode, rftNo, 0, 0, workinfo, isPending);
	}

	public void updateWorkerResult(
			String workerCode, 
			String rftNo, 
			int workTime,
			int missScanCnt,
			jp.co.daifuku.wms.base.rft.WorkingInformation[] workinfo,
			boolean isPending) throws ReadWriteException, NotFoundException
	{
		updateWorkerResult(workerCode, rftNo, workTime, missScanCnt, 1, workinfo, isPending);
	}

	public void updateWorkerResult(
			String workerCode, 
			String rftNo, 
			int workTime,
			int missScanCnt,
			int orderCnt,
			jp.co.daifuku.wms.base.rft.WorkingInformation[] workinfo,
			boolean isPending) throws ReadWriteException, NotFoundException
	{
		int workQty = 0;
		int workCnt = 0;
		int realWorkTime = 0;
		for (int i = 0; i < workinfo.length; i ++)
		{
			workQty += workinfo[i].getResultQty();
			realWorkTime += workinfo[i].getWorkTime();
			if (isPending)
			{
				//#CM643733
				// Do not sum up All qtyReserved to Work frequency for Reserved. 
				if (workinfo[i].getResultQty() > 0)
				{
					workCnt++;
				}
			}
			else
			{
				workCnt++;
			}
		}

		if (! isPending)
		{
		    //#CM643734
		    // When it is not Reserved, the work number is assumed to be Work frequency as they are. 
		    workCnt = workinfo.length;
		}
		
		WorkerResult wr = new WorkerResult();
		BaseOperate bo = new BaseOperate(conn);

		wr.setWorkDate(bo.getWorkingDate());
		wr.setWorkerCode(workerCode);
		wr.setTerminalNo(rftNo);
		wr.setJobType(jobType);
		wr.setWorkCnt(workCnt);
		wr.setWorkQty(workQty);

		wr.setWorkTime(workTime);
		wr.setRealWorkTime(realWorkTime);
		if (workQty > 0 || ! isPending)
		{
			//#CM643735
			// Set mis-frequency if All qty are not Reserved. 
			wr.setMissScanCnt(missScanCnt);
		}
		wr.setOrderCnt(orderCnt);

		try
		{
			bo.alterWorkerResult(wr);
		}
		catch (NotFoundException e)
		{
			String errData = "[RftNo:" + workerCode
							+ " WorkerCode:" + rftNo
							+ " JobType:" + jobType + "]";
			//#CM643736
			// 6026016=Data for the update is not found. {0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
		    try
            {
                bo.createWorkerResult(wr.getJobType(), wr.getWorkerCode(), wr.getTerminalNo());
    			bo.alterWorkerResult(wr);
            }
		    catch (NotFoundException e1)
            {
				//#CM643737
				// 6006002 = The data base error occurred. {0}
				RftLogMessage.print(6006002, CLASS_NAME, "DnWorkerResult");
				//#CM643738
				// Here, throw ReadWriteException with error Message. 
		        throw new ReadWriteException("6006002");
            }
		    catch (DataExistsException e1)
            {
				//#CM643739
				// 6006002 = The data base error occurred. {0}
				RftLogMessage.print(6006002, CLASS_NAME, "DnWorkerResult");
				//#CM643740
				// Here, throw ReadWriteException with error Message. 
		        throw new ReadWriteException("6006002");
            }
		}
	}

	//#CM643741
	/**
	 * Change the status of Worker results from Working to Finished.
	 * 
	 * @param workerCode	Worker Code
	 * @param rftNo			Rft Title machine No
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException It is notified when data for the update does not exist. 
	 */
	public void closeWorkerResult(String workerCode, String rftNo) throws ReadWriteException, NotFoundException
	{
		BaseOperate bo = new BaseOperate();
		bo.setConnection(conn);

		WorkerResult wr = new WorkerResult();
		wr.setJobType(jobType);
		wr.setWorkerCode(workerCode);
		wr.setWorkDate(bo.getWorkingDate());
		wr.setTerminalNo(rftNo);
		wr.setWorkCnt(0);
		wr.setWorkQty(0);
		
		bo.alterWorkerResult(wr);
	}
	
	//#CM643742
	// Package methods -----------------------------------------------

	//#CM643743
	// Protected methods ---------------------------------------------

	//#CM643744
	// Private methods -----------------------------------------------

}
//#CM643745
//end of class
