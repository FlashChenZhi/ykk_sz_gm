//#CM700436
//$Id: Id0001Operate.java,v 1.2 2006/11/14 06:08:58 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM700437
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.dbhandler.RftAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkerResult;

//#CM700438
/**
 * Designer : E.Takeda <BR>
 * Maker :   <BR>
 * <BR>
 * The class to do system report (ID0001) processing. <BR>
 * Succeed to <CODE>IdOperate</CODE> class, and mount necessary processing. <BR>
 * Receive terminal title machine No., Person in charge code, and report flag as a parameter, and do 
 * each processing by report flag. <BR>
 * System report processing(<CODE>processSystemReport()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Report flag  : Acquired from parameter doing each processing in each flag. <BR>
 *   Report flag  0 (Start):Update the RFT administrative information when the worker results of correspondence Work date, the worker, and title machine No. do not exist. <BR>
 *   Report flag  1(Completed):Update the RFT administrative information. <BR>
 *   Report flag  3(Rest):Renew the RFT administrative information and Worker results information. <BR>
 *   Report flag  4(Restart):Do the update of the RFT administrative information and New making of the worker results. <BR>
 * <BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:08:58 $
 * @author  $Author: suresh $
 */
/**
 * Designer : E.Takeda <BR>
 * Maker :   <BR>
 * <BR>
 * The class to do system report (ID0001) processing. <BR>
 * Succeed to <CODE>IdOperate</CODE> class, and mount necessary processing. <BR>
 * Receive terminal title machine No., Person in charge code, and report flag as a parameter, and do 
 * each processing by report flag. <BR>
 * System report processing(<CODE>processSystemReport()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Report flag  : Acquired from parameter doing each processing in each flag. <BR>
 *   Report flag  0 (Start):Update the RFT administrative information when the worker results of correspondence Work date, the worker, and title machine No. do not exist. <BR>
 *   Report flag  1(Completed):Update the RFT administrative information. <BR>
 *   Report flag  3(Rest):Renew the RFT administrative information and Worker results information. <BR>
 *   Report flag  4(Restart):Do the update of the RFT administrative information and New making of the worker results. <BR>
 * <BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:08:58 $
 * @author  $Author: suresh $
 */
public class Id0001Operate extends IdOperate {
	//#CM700439
	// Class variables -----------------------------------------------
	//#CM700440
	/**
	 * Process Name
	 */
	private static final String PROCESS_NAME = "ID0001";	
	
	//#CM700441
	// Constructors --------------------------------------------------
	//#CM700442
	/**
	 * Generate the instance. 
	 */
	public Id0001Operate()
	{
		super();
	}

	//#CM700443
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public Id0001Operate(Connection conn)
	{
		wConn = conn;
	}

	//#CM700444
	// Class method --------------------------------------------------
	//#CM700445
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:08:58 $";
	}
	
	//#CM700446
	// Public methods ------------------------------------------------

	//#CM700447
	/**
	 * Renew Status flag of RFT administrative information (dnrft), wireless Status flag, Last updated date and time, 
	 * and Last update processing name specifying RFT machine No. <BR>
	 * Report flag is 0 Update terminal flag  and terminal Internet Protocol address at (Start). <BR>
	 * 
	 * <BR>
	 * <DIR>
	 * [Update condition]<BR>
	 * <DIR>
	 *    RFT machine No<BR>
	 * </DIR>
	 * <DIR>
 	 *   [0:Start]<BR>
 	 *     <DIR>
 	 *     Update the RFT administrative information. <BR>
 	 * <BR>
 	 *    Status flag	:1<BR>
	 *    Terminal flag 		:Acquisition from parameter<BR>
	 *    Terminal IP Address:Acquisition from parameter<BR>
	 *    Wireless Status flag:0<BR>
	 *    Last updated date and time	:System date<BR>
	 *    Last update processing name:ID0001<BR>
	 * <BR>
 	 *     </DIR>
 	 *   [1:Completed]<BR>
 	 *     <DIR>
 	 *     Update the RFT administrative information. <BR>
 	 * <BR>
 	 *    Status flag	:0<BR>
	 *    Wireless Status flag:0<BR>
	 *    Last updated date and time	:System date<BR>
	 *    Last update processing name:ID0001<BR>
	 * <BR>
 	 *     </DIR>
 	 *   [3:Rest]<BR>
 	 *     <DIR>
 	 *     Update the RFT administrative information. <BR>
 	 * <BR>
 	 * 	  [RFT administrative information]<BR>
 	 *    Rest flag	:1<BR>
 	 *    Rest start time	:System date <BR>
	 *    Last updated date and time	:System date <BR>
	 *    Last update processing name:ID0001<BR>
	 * <BR>
 	 *     </DIR>
 	 *   [4:Restart]<BR>
 	 *     <DIR>
 	 *     Do the renewal of RFT administrative information and New making of Worker results information. <BR>
 	 * <BR>
 	 * 	  [RFT administrative information]<BR>
 	 *    Rest flag	:0<BR>
 	 *    Rest start time	:null<BR>
	 *    Last updated date and time	:System date<BR>
	 *    Last update processing name:ID0001<BR>
	 * <BR>
	 *    [Worker results information]<BR>
	 * 	  Work date:System definition<BR>
     *    Work Start date :System date<BR>
     *    Work End date :System date<BR>
     *    Worker code :RFTWorker code<BR>
     *    Worker name     :Worker name is acquired from worker information. <BR>
     *    TerminalNo,RFTNo:RFTNo.<BR>
     *    Work flag     :03 Picking <BR>
     *    Work qty     :0<BR>
	 *	  Work frequency     :0<BR>
	 *	<BR>
 	 *     </DIR>
 	 *   </DIR>
	 * </DIR>
	 * @param  rftNo	RFT machine No
	 * @param  reportFlag	Report flag 
	 * @param  terminalType Terminal flag 
	 * @param  ipAddress    Terminal IP Address
	 * @throws InvalidDefineException It is notified when the content of the update is not set. 
	 * @throws IllegalAccessException It is notified when failing in the generation of the instance. 
	 * @throws NotFoundException It is notified when update object Data does not exist. 
	 * @throws DataExistsException It had already been notified when same information had registered when Data was registered. 
	 * @throws InvalidStatusException Notify when there is no adjustment in a set value of the table update. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NoPrimaryException It is notified when definition information is abnormal. 
	 */
	public String alterRft(String rftNo, String reportFlag, String terminalType, String ipAddress)
		throws InvalidDefineException, IllegalAccessException, NotFoundException, DataExistsException, InvalidStatusException, ReadWriteException, NoPrimaryException
	{
		String workType = null;
		Date restStartTime = null;
		String workerCode = null;
		String rv = RFTId5001.ErrorDetails.NORMAL;
		
		//#CM700448
		// Check the state of present Terminal. 
		//#CM700449
		//-----------------
		//#CM700450
		// Set Search condition
		//#CM700451
		//-----------------
		RftSearchKey skey = new RftSearchKey();
		//#CM700452
		// RFT machine No is set. 
		skey.setRftNo(rftNo);
		
		//#CM700453
		//-----------------
     	//#CM700454
     	// Retrieval processing
		//#CM700455
		//-----------------
		RftHandler rftHandler = new RftHandler(wConn);
		
		//#CM700456
		// Change data which is Entity to the RFT type. 
		Rft rft = (Rft) rftHandler.findPrimary(skey);

		if (rft == null)
		{
			throw new NotFoundException();
		}
		
		if (reportFlag.equals(RFTId0001.REPORT_FLAG_REST)
				&& rft.getRestFlag().equals(SystemDefine.REST_FLAG_REST))
		{
			//#CM700457
			// Already do not do anything for the Rest inside when having reported on Rest. 
			return RFTId5001.ErrorDetails.ALREADY_RESTED;
		}
		else if (reportFlag.equals(RFTId0001.REPORT_FLAG_REPENDING)
				&& rft.getRestFlag().equals(Rft.REST_FLAG_NOTREST))
		{
			//#CM700458
			// When reporting on Restart, it reports on the error when the state is not Rest. 
			throw new InvalidStatusException();
		}
		else if (reportFlag.equals(RFTId0001.REPORT_FLAG_START)
				&& rft.getStatusFlag().equals(Rft.RFT_STATUS_FLAG_START))
		{
			rv = RFTId5001.ErrorDetails.ALREADY_STARTED;
		}
		else if (reportFlag.equals(RFTId0001.REPORT_FLAG_END)
				&& rft.getStatusFlag().equals(Rft.RFT_STATUS_FLAG_STOP))
		{
			rv = RFTId5001.ErrorDetails.NON_START_FINISH;
		}

		workType = rft.getJobType();
		workerCode = rft.getWorkerCode();
		try
		{
			restStartTime = rft.getRestStartTime();
		}
		catch (ClassCastException e)
		{
			//#CM700459
			// To try to do Cast to the Date type after it converts it into ""in the entity when Idle time is null
			//#CM700460
			// The exception is generated. Therefore, return it to null here. 
			restStartTime = null;
		}

		RftAlterKey rftAlterKey = new RftAlterKey();
		//#CM700461
		//-----------------
     	//#CM700462
     	// Set Update condition
		//#CM700463
		//-----------------
     	rftAlterKey.setRftNo(rftNo);

     	//#CM700464
     	//-----------------
     	//#CM700465
     	// Set Update content
		//#CM700466
		//-----------------
     	//#CM700467
     	// Status flag
     	rftAlterKey.updateStatusFlag (SystemDefine.RFT_STATUS_FLAG_START);
     	//#CM700468
     	// Last update processing name
     	rftAlterKey.updateLastUpdatePname(PROCESS_NAME);
     	
     	//#CM700469
     	// The report flag:  0 When the worker results of Start, correspondence Work date, and the worker and title machine No. do not exist
     	if (reportFlag.equals(RFTId0001.REPORT_FLAG_START))
     	{
         	//#CM700470
         	// Empty Worker code when it reports on Start. 
     		rftAlterKey.updateStatusFlag (SystemDefine.RFT_STATUS_FLAG_START);	// 状態フラグ：1 起動中
     		rftAlterKey.updateRadioFlag(SystemDefine.RADIO_FLAG_IN);		// 無線状態フラグ：0 エリア内
     		rftAlterKey.updateTerminalType(terminalType);	// 端末区分
     		rftAlterKey.updateIpAddress(ipAddress);			// 端末IPアドレス
     	}
     	//#CM700471
     	// Report flag : 1 Completed
     	else if (reportFlag.equals(RFTId0001.REPORT_FLAG_END))
     	{
     		rftAlterKey.updateStatusFlag (SystemDefine.RFT_STATUS_FLAG_STOP);	// 状態フラグ：0 停止中
     		rftAlterKey.updateRadioFlag(SystemDefine.RADIO_FLAG_IN);		// 無線状態フラグ：0 エリア内
     	}
     	//#CM700472
     	// Report flag : 3 Rest
     	else if (reportFlag.equals(RFTId0001.REPORT_FLAG_REST))
     	{
     		rftAlterKey.updateRestFlag(SystemDefine.REST_FLAG_REST);	// 休憩フラグ：1 休憩
     		rftAlterKey.updateRestStartTime(new Date());				// 休憩開始時刻：現在時刻
     	}
     	//#CM700473
     	// Report flag : 4 Restart
     	else if (reportFlag.equals(RFTId0001.REPORT_FLAG_REPENDING))
     	{
     		rftAlterKey.updateRestFlag(SystemDefine.REST_FLAG_NOTREST);	// 休憩フラグ：2 休憩解除
     		rftAlterKey.updateRestStartTime(null);						// 休憩開始時間：null
     	}

		//#CM700474
		//-----------------
     	//#CM700475
     	// Update process
		//#CM700476
		//-----------------
     	rftHandler.modify(rftAlterKey);
     	
     	//#CM700477
     	// Renewal of Worker results information
     	BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
     	baseOperate.setConnection(wConn);
     	if (reportFlag.equals(RFTId0001.REPORT_FLAG_REPENDING))
     	{
     		WorkerResult wr = new WorkerResult();
     		wr.setWorkDate(baseOperate.getWorkingDate());
     		wr.setWorkerCode(workerCode);
     		wr.setTerminalNo(rftNo);
     		int restTime = (int) (new Date().getTime() - restStartTime.getTime()) / 1000;
     		wr.setRestTime(restTime);
     		if (workType != null)
     		{
         		wr.setJobType(workType);
     		}
     		try
			{
         		baseOperate.alterWorkerResult(wr);
			}
     		catch (NotFoundException e)
			{
     			//#CM700478
     			// Do New making when there are no corresponding worker results. 
         		baseOperate.createWorkerResult(workType,workerCode,rftNo);
			}
     	}
     	
     	return rv;
	}
}
