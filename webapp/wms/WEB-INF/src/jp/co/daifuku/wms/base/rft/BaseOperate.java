// $Id: BaseOperate.java,v 1.2 2006/11/14 06:08:56 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM700060
/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.RftAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkerHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.Worker;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.utility.WareNaviSystemManager;


//#CM700061
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * Base package ID processing common class. <BR>
 * Succeed to <CODE>IdOperate</CODE> class, and mount necessary processing. <BR>
 * <DIR>
 *  Acquire the work day from system definition information. <BR>
 *  Receive the worker code as a parameter, and confirm it whether not working with another RFT. <BR>
 *  Receive RFT title machine No, the worker code or the work type as a parameter,<BR>
 *  Update the worker code or the work type of the RFT administrative information. <BR>
 *  Receive the worker code as a parameter, and acquire the worker name from worker information. <BR>
 * </DIR>
 * <BR>
 * Acquisition of work date(<CODE>getWorkingDate()</CODE> Method)<BR>
 * <DIR>
 *   Acquire the work date from system definition information (warenavi_system). <BR>
 * </DIR>
 * <BR>
 * <BR>
 * Worker work status confirmation(<CODE>checkWorker()</CODE> Method)<BR>
 * <DIR>
 *   Receive worker code and RFT machine No as a parameter, and confirm work with RFT. <BR>
 *   Return "False" if a pertinent worker is working in another RFT, "True" while not working. 
 * </DIR>
 * <BR>
 * <BR>
 * Worker work status confirmation(<CODE>checkWorkerForUpdate()</CODE> Method)<BR>
 * <DIR>
 *   Receive worker code and RFT machine No as a parameter, and confirm work with RFT after 
 *   locking all data of the RFT administrative information. <BR>
 *   Return "False" if a pertinent worker is working in another RFT, "True" while not working. 
 * </DIR>
 * <BR>
 * <BR>
 * RFT administrative information update(<CODE>alterRft()</CODE> Method)<BR>
 * <DIR>
 *   Update RFT administrative information (dnrft) specifying RFT title machine No. <BR>
 * </DIR>
 * <BR>
 * <BR>
 * Worker name(<CODE>getWorkerName()</CODE> Method)<BR>
 * <DIR>
 *   Acquire the worker name from worker information (dmworker) specifying the worker code. <BR>
 * </DIR>
 * <BR>
 * <BR>
 * Worker results update(<CODE>alterWorkerResult()</CODE> Method)<BR>
 * <DIR>
 *   Update worker results information (dnworkerresult). <BR>
 * </DIR>
 * <BR>
 * <BR>
 * Worker results making(<CODE>createWorkerResult()</CODE> Method)<BR>
 * <DIR>
 *   Receive work flag, worker code, and RFT machine No as a parameter, and make the worker results newly. <BR>
 *   <BR>
 *   [Registration data]<BR>
 * <DIR>
 *   Work date        : Acquisition from system definition information<BR>
 *   Work Start date  : System date<BR>
 *   Work End date  : System date<BR>
 *   Worker code  : Parameter value<BR>
 *   Worker name      : Acquisition from worker information<BR>
 *   RFTNo         : Parameter value<BR>
 *   Work flag      : Parameter value<BR>
 *   Work qty      : "0"<BR>
 *   Work frequency      : "0"<BR>
 *   Working time      : "0"<BR>
 *   Idle time      : "0"<BR>
 *   Actual Working time    : "0"<BR>
 *   Number of Miss scannings: "0"<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * Worker results retrieval(<CODE>getWorkerResult()</CODE> Method)<BR>
 * <DIR>
 *   Retrieve worker results information (dnworkerresult). <BR>
 * </DIR>
 * <BR>
 * <BR>
 * Confirmation during the next day processing(<CODE>isLoadingDailyUpdate()</CODE> Method)<BR>
 * <DIR>
 *   Confirm it during the next day processing from system definition information (warenavi_system). <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:08:56 $
 * @author  $Author: suresh $
 */
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * Base package ID processing common class. <BR>
 * Succeed to <CODE>IdOperate</CODE> class, and mount necessary processing. <BR>
 * <DIR>
 *  Acquire the work day from system definition information. <BR>
 *  Receive the worker code as a parameter, and confirm it whether not working with another RFT. <BR>
 *  Receive RFT title machine No, the worker code or the work type as a parameter,<BR>
 *  Update the worker code or the work type of the RFT administrative information. <BR>
 *  Receive the worker code as a parameter, and acquire the worker name from worker information. <BR>
 * </DIR>
 * <BR>
 * Acquisition of work date(<CODE>getWorkingDate()</CODE> Method)<BR>
 * <DIR>
 *   Acquire the work date from system definition information (warenavi_system). <BR>
 * </DIR>
 * <BR>
 * <BR>
 * Worker work status confirmation(<CODE>checkWorker()</CODE> Method)<BR>
 * <DIR>
 *   Receive worker code and RFT machine No as a parameter, and confirm work with RFT. <BR>
 *   Return "False" if a pertinent worker is working in another RFT, "True" while not working. 
 * </DIR>
 * <BR>
 * <BR>
 * Worker work status confirmation(<CODE>checkWorkerForUpdate()</CODE> Method)<BR>
 * <DIR>
 *   Receive worker code and RFT machine No as a parameter, and confirm work with RFT after 
 *   locking all data of the RFT administrative information. <BR>
 *   Return "False" if a pertinent worker is working in another RFT, "True" while not working. 
 * </DIR>
 * <BR>
 * <BR>
 * RFT administrative information update(<CODE>alterRft()</CODE> Method)<BR>
 * <DIR>
 *   Update RFT administrative information (dnrft) specifying RFT title machine No. <BR>
 * </DIR>
 * <BR>
 * <BR>
 * Worker name(<CODE>getWorkerName()</CODE> Method)<BR>
 * <DIR>
 *   Acquire the worker name from worker information (dmworker) specifying the worker code. <BR>
 * </DIR>
 * <BR>
 * <BR>
 * Worker results update(<CODE>alterWorkerResult()</CODE> Method)<BR>
 * <DIR>
 *   Update worker results information (dnworkerresult). <BR>
 * </DIR>
 * <BR>
 * <BR>
 * Worker results making(<CODE>createWorkerResult()</CODE> Method)<BR>
 * <DIR>
 *   Receive work flag, worker code, and RFT machine No as a parameter, and make the worker results newly. <BR>
 *   <BR>
 *   [Registration data]<BR>
 * <DIR>
 *   Work date        : Acquisition from system definition information<BR>
 *   Work Start date  : System date<BR>
 *   Work End date  : System date<BR>
 *   Worker code  : Parameter value<BR>
 *   Worker name      : Acquisition from worker information<BR>
 *   RFTNo         : Parameter value<BR>
 *   Work flag      : Parameter value<BR>
 *   Work qty      : "0"<BR>
 *   Work frequency      : "0"<BR>
 *   Working time      : "0"<BR>
 *   Idle time      : "0"<BR>
 *   Actual Working time    : "0"<BR>
 *   Number of Miss scannings: "0"<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * Worker results retrieval(<CODE>getWorkerResult()</CODE> Method)<BR>
 * <DIR>
 *   Retrieve worker results information (dnworkerresult). <BR>
 * </DIR>
 * <BR>
 * <BR>
 * Confirmation during the next day processing(<CODE>isLoadingDailyUpdate()</CODE> Method)<BR>
 * <DIR>
 *   Confirm it during the next day processing from system definition information (warenavi_system). <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:08:56 $
 * @author  $Author: suresh $
 */
public class BaseOperate extends IdOperate
{
	//#CM700062
	// Class fields----------------------------------------------------
	private static final String CLASS_NAME = "rft.BaseOperate";

	//#CM700063
	// Class variables -----------------------------------------------
	//#CM700064
	// Constructors --------------------------------------------------
	//#CM700065
	/**
	 * Generate the instance. 
	 */
	public BaseOperate()
	{
		super();
	}

	//#CM700066
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public BaseOperate(Connection conn)
	{
		wConn = conn ;
	}


	
	//#CM700067
	// Class method --------------------------------------------------
	//#CM700068
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/14 06:08:56 $");
	}

	//#CM700069
	// Public methods ------------------------------------------------
	//#CM700070
	/**
	 * Acquire Work date from system definition information. <BR>
	 * @return String Work date(YYYYMMDD)
	 * @throws NotFoundException  It is notified when system definition information is not found. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public String getWorkingDate() throws NotFoundException, ReadWriteException
	{
		WareNaviSystemHandler wnSystemHandler = new WareNaviSystemHandler( wConn );
		WareNaviSystemSearchKey wnSystemSearchKey = new WareNaviSystemSearchKey();
		WareNaviSystem[] wnSystem = (WareNaviSystem[])wnSystemHandler.find( wnSystemSearchKey );

		//#CM700071
		// When system definition information cannot be acquired, throws NotFoundException. 
		if( wnSystem == null || wnSystem.length == 0 )
		{
			throw (new NotFoundException());
		}
		//#CM700072
		// Return Work date. 
		return wnSystem[0].getWorkDate();
	}

	//#CM700073
	/**
	 * Acquire a plan date which is the nearer Work date than system definition information. <BR>
	 * Retrieve the schedule data that is possible to work before WMSWork date in the descending order. 
	 * 
	 *   <DIR>
	 *   [Acquisition item]<BR>
	 *     <DIR>
	 *     Plan date<BR>
	 *     </DIR>
	 *   [Search condition]<BR>
	 *     <DIR>
	 *     Work flag      :Acquisition from parameter<BR>
	 *     Work plan date    :<= WMSWork date<BR>
	 *     Status flag    :Thing done while Stand by or automatic terminal and self worker are working<BR>
	 *     Work start flag:Started<BR>
	 *     TC/DC flag     :DC or Cross TC (When work type is 01 and a work detailed type is only 1 or 2) <BR>
	 *     TC/DC flag     :TC (When work type is 01 and a work detailed type is only 3) <BR>
	 *     Order No.   :The one which is not empty (Only when work type is 03 and work detailed type is only 1) <BR>
	 *     Order No.   :The one which is empty (Only when work type is 03 and work detailed type is only 2) <BR>
	 *     </DIR>
	 *   </DIR>
	 * 
	 * Return the first Plan date when the schedule data that is possible to work is found. 
	 * Retrieve the schedule data of WMSWork date in the future in ascending order when there is no schedule data that is possible to work. 
	 * 
	 *   <DIR>
	 *   [Acquisition item]<BR>
	 *     <DIR>
	 *     Plan date<BR>
	 *     </DIR>
	 *   [Search condition]<BR>
	 *     <DIR>
	 *     Work flag      :Acquisition from parameter<BR>
	 *     Work plan date    :> WMSWork date<BR>
	 *     Status flag    :Thing done while Stand by or automatic terminal and self worker are working<BR>
	 *     Work start flag:Started<BR>
	 *     TC/DC flag     :DC or Cross TC (When work type is 01 and a work detailed type is only 1 or 2) <BR>
	 *     TC/DC flag     :TC (When work type is 01 and a work detailed type is only 3) <BR>
	 *     Order No.   :The one which is not empty (Only when work type is 03 and work detailed type is only 1) <BR>
	 *     Order No.   :The one which is empty (Only when work type is 03 and work detailed type is only 2) <BR>
	 *     </DIR>
	 *   </DIR>
	 * 
	 * Return the first Plan date when the schedule data that is possible to work is found. 
	 * Return WMSWork date when the schedule data that is possible to work is not found. 
	 * 
	 * @param workType Work flag
	 * @param workDetails Work type
	 * @param rftNo RFT machine No
	 * @param workerCode Worker code
	 * @return String Plan date which can work(YYYYMMDD)
	 * @throws NotFoundException  It is notified when system definition information is not found. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public String getPlanDate(
			String workType,
			String workDetails,
			String rftNo,
			String workerCode) throws NotFoundException, ReadWriteException
	{
		String wmsDate = getWorkingDate();

		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

		//#CM700074
		// Retrieve Plan date before WMS Work date. 
		
		//#CM700075
		//-----------------
		//#CM700076
		// Set Acquisition item
		//#CM700077
		//-----------------
		//#CM700078
		// Plan date
		skey.setPlanDateCollect("DISTINCT");

		//#CM700079
		//-----------------
		//#CM700080
		// Set Search condition
		//#CM700081
		//-----------------
		//#CM700082
		// Work flag
		skey.setJobType(workType);
		//#CM700083
		// Work plan date
		skey.setPlanDate(wmsDate, "<=");
		//#CM700084
		// Status flag
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		skey.setWorkerCode(workerCode);
		skey.setTerminalNo(rftNo, "=", "", "))", "AND");
		//#CM700085
		// Work start flag
		skey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);

		if (workType.equals(WorkingInformation.JOB_TYPE_INSTOCK))
		{
			if (workDetails.equals(WorkDetails.INSTOCK_ITEM)
					|| workDetails.equals(WorkDetails.INSTOCK_SUPPLIER))
			{
				//#CM700086
				// Retrieve DC and crossing TC of the unit of the supplier or each item. 
				skey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC, "=", "(", "", "OR");
				skey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC, "=", "", ")", "AND");
			}
			else if (workDetails.equals(WorkDetails.INSTOCK_CUSTOMER))
			{
				//#CM700087
				// Retrieve TC for each and every customer
				skey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
			}
		}
		else if (workType.equals(WorkingInformation.JOB_TYPE_RETRIEVAL))
		{
			if (workDetails.equals(WorkDetails.RETRIEVAL_ORDER))
			{
				//#CM700088
				// Retrieve data where order No is set at the order picking. 
				skey.setOrderNo("", "IS NOT NULL");
			}
			else if (workDetails.equals(WorkDetails.RETRIEVAL_ITEM))
			{
				//#CM700089
				// Order No retrieves empty data at the item picking. 
				skey.setOrderNo("", "=");
			}
		}
		
		//#CM700090
		// Sort it in the descending order of Plan date. 
		skey.setPlanDateOrder(1, false);
		
		//#CM700091
		//-----------------
		//#CM700092
		// Retrieval processing
		//#CM700093
		//-----------------
		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(wConn);
		WorkingInformation[] workInfo = (WorkingInformation[]) workInfoHandler.find(skey);

		if (workInfo != null && workInfo.length > 0)
		{
			return workInfo[0].getPlanDate();
		}


		//#CM700094
		// Retrieve Plan date in the future when there is no corresponding work information. 
		skey.KeyClear();

		//#CM700095
		//-----------------
		//#CM700096
		// Set Acquisition item
		//#CM700097
		//-----------------
		//#CM700098
		// Plan date
		skey.setPlanDateCollect("DISTINCT");

		//#CM700099
		//-----------------
		//#CM700100
		// Set Search condition
		//#CM700101
		//-----------------
		//#CM700102
		// Work flag
		skey.setJobType(workType);
		//#CM700103
		// Work plan date
		skey.setPlanDate(wmsDate, ">");
		//#CM700104
		// Status flag
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		skey.setWorkerCode(workerCode);
		skey.setTerminalNo(rftNo, "=", "", "))", "AND");
		//#CM700105
		// Work start flag
		skey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);

		if (workType.equals(WorkingInformation.JOB_TYPE_INSTOCK))
		{
			if (workDetails.equals(WorkDetails.INSTOCK_ITEM)
					|| workDetails.equals(WorkDetails.INSTOCK_SUPPLIER))
			{
				//#CM700106
				// Retrieve DC and crossing TC of the unit of the supplier or each item. 
				skey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC, "=", "(", "", "OR");
				skey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC, "=", "", ")", "AND");
			}
			else
			{
				//#CM700107
				// Retrieve TC for each and every customer
				skey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
			}
		}
		else if (workType.equals(WorkingInformation.JOB_TYPE_RETRIEVAL))
		{
			if (workDetails.equals(WorkDetails.RETRIEVAL_ORDER))
			{
				//#CM700108
				// Retrieve data where order No is set at the order picking. 
				skey.setOrderNo("", "IS NOT NULL");
			}
			else
			{
				//#CM700109
				// Order No retrieves empty data at the item picking. 
				skey.setOrderNo("", "=");
			}
		}
		
		//#CM700110
		// Sort it in ascending order of Plan date. 
		skey.setPlanDateOrder(1, true);
		
		//#CM700111
		//-----------------
		//#CM700112
		// Retrieval processing
		//#CM700113
		//-----------------
		workInfo = (WorkingInformation[]) workInfoHandler.find(skey);

		if (workInfo != null && workInfo.length > 0)
		{
			return workInfo[0].getPlanDate();
		}

		//#CM700114
		// Return the WMS date when it is not found even if the schedule for the future is retrieved. 
		return wmsDate;
	}

	//#CM700115
	/**
	 * Receive worker code and RFT machine No as a parameter, and confirm work with RFT. <BR>
	 * Acquire RFT machine No working from RFT administrative information (dnrft) by corresponding Worker code. <BR>
	 * Confirm whether the parameter is same as RFT machine No.<BR>
	 * Return "OK" if it is the same or "Working in the other end" in case of different title machine No.<BR>
	 * Return "OK" when you cannot acquire RFT machine No from the RFT administrative information. <BR>
	 * 
	 * @param  workerCode Worker code
	 * @param  rftNo      RFT machine No
	 * @return true:OK  false:It is working in the other end.
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public boolean checkWorker( String workerCode, String rftNo ) throws ReadWriteException
	{
		//#CM700116
		//-----------------
		//#CM700117
		// Set Search condition
		//#CM700118
		//-----------------
		RftSearchKey rftSearchKey = new RftSearchKey();
		//#CM700119
		// Worker code
		rftSearchKey.setWorkerCode( workerCode.trim() );

		//#CM700120
		//-----------------
     	//#CM700121
     	// Retrieval processing
		//#CM700122
		//-----------------
		RftHandler rftHandler = new RftHandler( wConn );
		Rft[] rft = (Rft[])rftHandler.find( rftSearchKey );
		
		//#CM700123
		//-----------------
     	//#CM700124
     	// Processing Check
		//#CM700125
		//-----------------
		if( rft != null && rft.length > 0 )
		{
			//#CM700126
			// Check RFT machine No when RFT information can be acquired. 
			for( int i = 0; i < rft.length; i++ )
			{
				if( !rft[i].getRftNo().equals( rftNo ) )
				{
					//#CM700127
					// "It is working in the other end." when RFT machine No differs.
					return false;
				}
			}
		}		
		return true;
	}

	//#CM700128
	/**
	 * Receive worker code and RFT machine No as a parameter, and confirm work with RFT. <BR>
	 * Acquire worker information from worker information (dmworker) with corresponding Worker code and the deletion flag.  <BR>
	 * Return NotFoundException when you cannot acquire Worker code from worker information. <BR>
	 * When the RFT administrative information is acquired from RFT administrative information (dnrft) in corresponding RFT machine No, and corresponding RFT machine No exists<BR>
	 * Return "OK" if Work flag is Stand by, "It is working in the other end. " when Worker code is different,<BR>
	 * "OK" when RFT machine No exists excluding the above-mentioned condition, and when RFT machine No does not exist,<BR>
	 * Return ReadWriteException.<BR>
	 * @param  workerCode Worker code
	 * @param  rftNo      RFT machine No
	 * @return true:OK false:"It is working in the other end."
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NoPrimaryException It is notified, except when information acquired in Search condition is one. 
	 * @throws InvalidStatusException It is notified when it sets outside the range for the object with the state. 
	 * @throws NotFoundException Worker information is notified when a pertinent worker is not found. 
	 */
	public boolean checkRft( String workerCode, String rftNo ) throws ReadWriteException, NoPrimaryException, NotFoundException
	{
		//#CM700129
		//-----------------
		//#CM700130
		// Set Search condition
		//#CM700131
		//-----------------
		WorkerSearchKey workerSearchKey = new WorkerSearchKey();
		//#CM700132
		// Worker code is set. 
		workerSearchKey.setWorkerCode(workerCode);
		//#CM700133
		// The deletion flag is set. 
		workerSearchKey.setDeleteFlag(Worker.DELETE_FLAG_OPERATION);
		//#CM700134
		//-----------------
     	//#CM700135
     	// Retrieval processing
		//#CM700136
		//-----------------
		WorkerHandler workerHandler = new WorkerHandler( wConn );
		
		//#CM700137
		//Change data which is Entity to the Worker type. 
		Worker[] worker = (Worker[])workerHandler.find(workerSearchKey);
		
		//#CM700138
		//-----------------
     	//#CM700139
     	// Processing Check
		//#CM700140
		//-----------------
		
		//#CM700141
		// When Worker code does not exist
		if( worker == null || worker.length == 0)
		{
			throw (new NotFoundException());
		}	
	
		//#CM700142
		//-----------------
		//#CM700143
		// Set Search condition
		//#CM700144
		//-----------------
		RftSearchKey rftSearchKey = new RftSearchKey();
		//#CM700145
		// RFT machine No is set. 
		rftSearchKey.setRftNo(rftNo);
		
		//#CM700146
		//-----------------
     	//#CM700147
     	// Retrieval processing
		//#CM700148
		//-----------------
		RftHandler rftHandler = new RftHandler( wConn );
		
		//#CM700149
		//Change data which is Entity to the RFT type. 
		Rft rft = (Rft)rftHandler.findPrimary( rftSearchKey );
		
		//#CM700150
		//-----------------
     	//#CM700151
     	// Processing Check
		//#CM700152
		//-----------------
		
		//#CM700153
		// When RFT machine No exists
		if( rft != null)
		{
			//#CM700154
			// Check Work flag. 
			if(rft.getJobType().equals(SystemDefine.JOB_TYPE_UNSTART))
			{
				//#CM700155
				// "Normal" when Work flag is Stand by
				return true;
			}	
			else if( !rft.getWorkerCode().equals( workerCode ) )
			{
				//#CM700156
				// "It is working in the other end." when Work flag is other than Stand by
				return false;
			}
			else
			{
				//#CM700157
				// "Normal" When RFT machine No exists
				return true;
			}	
		}
		//#CM700158
		// When the RFT title machine does not exist
		throw (new ReadWriteException());
	}
	
	//#CM700159
	/**
	 * Receive worker code and RFT machine No as a parameter, and confirm work with RFT. <BR>
	 * Acquire RFT machine No working by correspondence Worker code after locking all data of RFT administrative information (dnrft). <BR>
	 * Confirm whether the parameter is same as RFT machine No.<BR>
	 * Return "OK" if it is the same or "Working in the other end" in case of different title machine No.<BR>
	 * Return "OK" when you cannot acquire RFT machine No from the RFT administrative information. <BR>
	 * @param  workerCode Worker code
	 * @param  rftNo      RFT machine No
	 * @return true:OK  false:It is working in the other end.
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public boolean checkWorkerForUpdate( String workerCode, String rftNo ) throws ReadWriteException
	{		
		//#CM700160
		// Lock the RFT administrative information all data. 
		RftHandler rftHandler = new RftHandler( wConn );
		rftHandler.findForUpdate( new RftSearchKey() );
		
		return checkWorker( workerCode, rftNo );
	}
	
	//#CM700161
	/**
	 * Receive worker code and RFT machine No as a parameter, and confirm work with RFT. <BR> 
	 * After locking all data of RFT administrative information (dnrft)
	 * The RFT administrative information is acquired, and it corresponds by correspondence RFT machine No When RFT machine No exists. <BR>
	 * Return "OK" if Work flag is Stand by, "It is working in the other end. " when Worker code is different,<BR>
	 * "OK" when RFT machine No exists excluding the above-mentioned condition, and when RFT machine No does not exist,<BR>
	 * Return ReadWriteException.<BR>
	 * @param  workerCode Worker code
	 * @param  rftNo      RFT machine No
	 * @return true:OK  false:It is working in the other end.
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException It is notified when system definition information is not found. 
	 * @throws NoPrimaryException It is notified, except when information acquired in Search condition is one. 
	 * @throws InvalidStatusException It is notified when it sets outside the range for the object with the state. 
	 */
	public boolean checkRftForUpdate( String workerCode, String rftNo) 
		throws ReadWriteException,NotFoundException,NoPrimaryException,InvalidStatusException
	{
		//#CM700162
		// Lock the RFT administrative information. 
		RftHandler rftHandler = new RftHandler( wConn );
		rftHandler.findForUpdate( new RftSearchKey() );
	
		return checkRft(workerCode,rftNo);
	}
	//#CM700163
	/**
	 * Renew Work type and Worker code of RFT administrative information (dnrft) specifying RFT machine No. <BR>
	 * Commit it in this Method call origin. <BR>
	 * <BR>
	 * <DIR>
	 * [Update condition]<BR>
	 * <DIR>
	 *    RFT machine No<BR>
	 * </DIR>
	 * [Update row]<BR>
	 * <DIR>
	 *    Worker code<BR>
	 *    Work type<BR>
	 *    Last updated date and time<BR>
	 *    Last update processing name<BR>
	 * </DIR>
	 * </DIR>
	 * @param  rftNo      RFT machine No
	 * @param  workerCode Worker code
	 * @param  workType   Work type
	 * @param  pname      Update processing name
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException  It is notified when RFT information to be updated is not found. 
	 * @throws InvalidDefineException It is notified when the content of the update is not set. 
	 */
     public void alterRft( String rftNo, String workerCode, String workType, String pname ) throws ReadWriteException, NotFoundException, InvalidDefineException
     {
     	RftAlterKey rftAlterKey = new RftAlterKey();
		//#CM700164
		//-----------------
     	//#CM700165
     	// Set Update condition
		//#CM700166
		//-----------------
     	rftAlterKey.setRftNo( rftNo );

     	//#CM700167
     	//-----------------
     	//#CM700168
     	// Set Update content
		//#CM700169
		//-----------------
     	//#CM700170
     	// Worker code
     	rftAlterKey.updateWorkerCode( workerCode.trim() );
     	//#CM700171
     	// Work type
     	rftAlterKey.updateJobType( workType );
     	//#CM700172
     	// Last update processing name
     	rftAlterKey.updateLastUpdatePname( pname );
     	
     	RftHandler rftHandler = new RftHandler( wConn );
		//#CM700173
		//-----------------
     	//#CM700174
     	// Update process
		//#CM700175
		//-----------------
     	rftHandler.modify( rftAlterKey );
     }
	
	//#CM700176
	/**
	 * Acquire the Worker name from worker information (dmworker) by specifying name in the condition.<BR>
	 * The deletion flag acquires the Worker name name of correspondence Worker code from the worker information data which can be used. <BR>
	 * @param  workerCode Worker code
	 * @return String     Worker name
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException  It is notified when worker information is not found. 
	 */
	public String getWorkerName( String workerCode ) throws ReadWriteException, NotFoundException
	{
		//#CM700177
		//-----------------
		//#CM700178
		// Set Search condition
		//#CM700179
		//-----------------
		WorkerSearchKey workerSearchKey = new WorkerSearchKey();
		//#CM700180
		// Worker code
		workerSearchKey.setWorkerCode( workerCode.trim() );
		//#CM700181
		// Deletion flag
		workerSearchKey.setDeleteFlag( Worker.DELETE_FLAG_OPERATION );
		
		//#CM700182
		//-----------------
		//#CM700183
		// Retrieval processing
		//#CM700184
		//-----------------
		WorkerHandler workerHandler = new WorkerHandler( wConn );
		Worker[] worker = (Worker[])workerHandler.find( workerSearchKey );
		
		//#CM700185
		// When worker information cannot be acquired, throws NotFoundException. 
		if( worker == null || worker.length == 0 )
		{
			throw (new NotFoundException());
		}
		
		return worker[0].getName();
	}
	

	//#CM700186
	/**
	 * Update worker results information (dnworkerresult). <BR>
	 * Commit it in this Method call origin. <BR>
	 * <BR>
	 * <DIR>
	 * [Update condition]<BR>
	 * <DIR>
	 *    Work date(Acquisition from parameter)<BR>
	 *    Worker code(Acquisition from parameter)<BR>
	 *    RFTNo(Acquisition from parameter)<BR>
	 *    Work flag(Acquisition from parameter)<BR>
	 *    Work Start date(The maximum value)<BR>
	 * </DIR>
	 * [Update row]<BR>
	 * <DIR>
	 *    Work End date   :System date (SYSDATE)<BR>
	 *    Work qty       :Add to a present value. <BR>
	 *    Work detail qty     :Add to a present value. <BR>
	 *    Work order qty :Add to a present value. <BR>
	 *    Working time       :Add to a present value. <BR>
	 *    Idle time       :Add to a present value. <BR>
	 *    Actual Working time     :Add to a present value. <BR>
	 * </DIR>
	 * </DIR>
	 * @param  workerResult Worker results information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException  It is notified when Worker results information is not found. 
	 */
	public void alterWorkerResult(WorkerResult workerResult) throws ReadWriteException, NotFoundException
	{
		Statement stmt      = null;
	 	try
	 	{
	 		//#CM700187
	 		// Set Update condition
	 		WorkerResultSearchKey workerResultSearchKey = new WorkerResultSearchKey();
	 		//#CM700188
	 		// Work date
	 		workerResultSearchKey.setWorkDate( workerResult.getWorkDate() );
	 		//#CM700189
	 		// Worker code
	 		workerResultSearchKey.setWorkerCode( workerResult.getWorkerCode().trim() );
	 		//#CM700190
	 		// RFTNo
	 		workerResultSearchKey.setTerminalNo( workerResult.getTerminalNo() );
	 		//#CM700191
	 		// Work flag
	 		workerResultSearchKey.setJobType( workerResult.getJobType() );
	 		
	 		//#CM700192
	 		// Descending order at beginning time
			workerResultSearchKey.setWorkStartTimeOrder(1, false);
	 		
			WorkerResultHandler handler = new WorkerResultHandler(wConn);
			WorkerResult[] wr = (WorkerResult[]) handler.find(workerResultSearchKey);
			if ( wr.length > 0 )
			{
				//#CM700193
				// Check on overflow
				if(wr[0].getWorkQty() + workerResult.getWorkQty() > SystemParameter.MAXSTOCKQTY)
				{
					workerResult.setWorkQty(SystemParameter.MAXSTOCKQTY - wr[0].getWorkQty());
					//#CM700194
					// 6023361=Worked Qty. in Worker Result exceeds the Max. value. {0} was set in the Worked Qty.
					RftLogMessage.print(6023361, LogMessage.F_NOTICE, CLASS_NAME, SystemParameter.DISPMAXSTOCKQTY);
				}
				if(wr[0].getWorkCnt() + workerResult.getWorkCnt() > SystemParameter.MAXSTOCKQTY)
				{
					workerResult.setWorkCnt(SystemParameter.MAXSTOCKQTY - wr[0].getWorkCnt());
					//#CM700195
					// 6023362=No. of Work in Worker Result exceeds the Max. value. {0} was set in the No. of Work.
					RftLogMessage.print(6023362, LogMessage.F_NOTICE, CLASS_NAME, SystemParameter.DISPMAXSTOCKQTY);
				}
			}
			
			String sqlstring = "UPDATE dnworkerresult SET "
									+ " work_end_time = SYSDATE,"
									+ " work_qty = work_qty +" + String.valueOf(workerResult.getWorkQty()) + ","
									+ " work_cnt = work_cnt +" + String.valueOf(workerResult.getWorkCnt()) + ","
									+ " order_cnt = order_cnt +" + String.valueOf(workerResult.getOrderCnt()) + ","
									+ " work_time = work_time +" + String.valueOf(workerResult.getWorkTime()) + ","
									+ " rest_time = rest_time +" + String.valueOf(workerResult.getRestTime()) + ","
									+ " real_work_time = real_work_time +" + String.valueOf(workerResult.getRealWorkTime()) + ","
									+ " miss_scan_cnt = miss_scan_cnt +" + String.valueOf(workerResult.getMissScanCnt())
							+ " WHERE "
									+ workerResultSearchKey.getReferenceCondition()
							+ " AND "
									+ " work_start_time IN "
									+ "("
									+	" SELECT MAX(work_start_time) FROM dnworkerresult "
									+   " WHERE " + workerResultSearchKey.getReferenceCondition()
									+ ")";
			
			stmt = wConn.createStatement();
			if ( stmt.executeUpdate(sqlstring) == 0 )
			{
				//#CM700196
				// 6026016=No data you try to update is found. {0}
				RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, sqlstring);
				throw (new NotFoundException( "6006005" + MessageResource.DELIM + "DnWorkerResult" ));
			}
		}
		catch(SQLException e)
		{
			//#CM700197
			// 6006002 = Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM700198
			//Here, throw ReadWriteException with the error message. 
			//#CM700199
			// 6025023 = Can't modify.
			throw (new ReadWriteException("6025023"));
		}
		finally
		{
			try
			{
				if (stmt != null)
				{
					stmt.close();
					stmt = null ;
				}
			}
			catch(SQLException e)
			{
				//#CM700200
				// 6006002 = Database error occurred.{0}
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
				//#CM700201
				//Here, throw ReadWriteException with the error message. 
				//#CM700202
				// 6025023 = Can't modify.
				throw (new ReadWriteException("6025023"));
			}
		}
		
	}
	
	//#CM700203
	/**
	 * Receive work flag, worker code, and RFT machine No as a parameter, and make the worker results newly. <BR>
	 * Commit it in this Method call origin. <BR>
	 * <BR>
	 * <DIR>
	 *   [Registration data]<BR>
	 * <DIR>
	 *   Work date        : Acquisition from system definition information<BR>
	 *   Work Start date  : System date<BR>
	 *   Work End date  : System date<BR>
	 *   Worker code  : Parameter value<BR>
	 *   Worker name      : Acquisition from worker information<BR>
	 *   RFTNo         : Parameter value<BR>
	 *   Work flag      : Parameter value<BR>
	 *   Work qty      : "0"<BR>
	 *   Work detail qty    : "0"<BR>
	 *   Work order qty: "0"<BR>
	 *   Working time      : "0"<BR>
	 *   Idle time      : "0"<BR>
	 *   Actual Working time    : "0"<BR>
	 * </DIR>
	 * </DIR>
	 * @param  jobType    Work flag
	 * @param  workerCode Worker code
	 * @param  rftNo      RFT machine No
	 * @throws NotFoundException   It is notified when system definition information or a pertinent worker is not found by worker information. 
	 * @throws ReadWriteException  It is notified when abnormality occurs by the connection with the data base. 
	 * @throws DataExistsException It had already been notified when same information had registered when information was registered. 
	 */
	public void createWorkerResult( String jobType, String workerCode, String rftNo ) throws NotFoundException, ReadWriteException, DataExistsException
	{
		WorkerResult workerResult = new WorkerResult();
		//#CM700204
		// Work date
		workerResult.setWorkDate(getWorkingDate());
		//#CM700205
		// Work Start date
		workerResult.setWorkStartTime(new java.util.Date());
		//#CM700206
		// Work End date
		workerResult.setWorkEndTime(new java.util.Date());
		//#CM700207
		// Worker code
		workerResult.setWorkerCode(workerCode.trim());
		//#CM700208
		// Worker name
		workerResult.setWorkerName(getWorkerName(workerCode));
		//#CM700209
		// RFTNo
		workerResult.setTerminalNo(rftNo);
		//#CM700210
		// Work flag
		workerResult.setJobType(jobType);
		//#CM700211
		// Work qty
		workerResult.setWorkQty(0);
		//#CM700212
		// Work frequency (Detail qty) 
		workerResult.setWorkCnt(0);
		//#CM700213
		// Work frequency (Order qty) 
		workerResult.setOrderCnt(0);
		//#CM700214
		// Working time
		workerResult.setWorkTime(0);
		//#CM700215
		// Idle time
		workerResult.setRestTime(0);
		//#CM700216
		// Actual Working time
		workerResult.setRealWorkTime(0);
		//#CM700217
		// Number of Miss scannings
		workerResult.setMissScanCnt(0);

		WorkerResultHandler workerResultHandler = new WorkerResultHandler( wConn );
		workerResultHandler.create( workerResult );		
	}

	//#CM700218
	/**
	 * Work flag, Worker code, and RFT machine No are received as a parameter, and Retrieve worker results information (dnworkerresult).  <BR>
	 * <BR>
	 * <DIR>
	 * [Search condition]<BR>
	 * <DIR>
	 *    Work date(Acquisition from system definition information)<BR>
	 *    Worker code(Acquisition from parameter)<BR>
	 *    RFTNo(Acquisition from parameter)<BR>
	 *    Work flag(Acquisition from parameter)<BR>
	 * </DIR>
	 * </DIR>
	 * @param  jobType    Work flag
	 * @param  workerCode Worker code
	 * @param  rftNo      RFT machine No
	 * @return workerResult[] Worker results information Array
	 * @throws NotFoundException   System definition information or corresponding is notified when Worker results information is not found. 
	 * @throws ReadWriteException  It is notified when abnormality occurs by the connection with the data base. 
	 */
	public WorkerResult[] getWorkerResult( String jobType, String workerCode, String rftNo ) throws NotFoundException, ReadWriteException
	{
		//#CM700219
		//-----------------
		//#CM700220
		// Set Search condition
		//#CM700221
		//-----------------
		WorkerResultSearchKey workerResultSearchKey = new WorkerResultSearchKey();
		//#CM700222
		// Work date
		workerResultSearchKey.setWorkDate( getWorkingDate() );
		//#CM700223
		//Work flag
		workerResultSearchKey.setJobType( jobType );
		//#CM700224
		// RFTNo.
		workerResultSearchKey.setTerminalNo( rftNo );
		//#CM700225
		// Worker code
		workerResultSearchKey.setWorkerCode( workerCode.trim() );
		
		//#CM700226
		//-----------------
		//#CM700227
		// Retrieval processing
		//#CM700228
		//-----------------
		WorkerResultHandler workerResultHandler = new WorkerResultHandler( wConn );
		WorkerResult[] workerResult = (WorkerResult[])workerResultHandler.find( workerResultSearchKey );
				
		return workerResult;
		
	}

	//#CM700229
	/**
	 * Confirm it while processing it on the next day from system definition information.<BR>
	 * @return True : Processing on the next day  false : Not Processing on the next day
	 * @throws NotFoundException  It is notified when system definition information is not found. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public boolean isLoadingDailyUpdate() throws NotFoundException, ReadWriteException
	{
		WareNaviSystemHandler wnSystemHandler = new WareNaviSystemHandler( wConn );
		WareNaviSystemSearchKey wnSystemSearchKey = new WareNaviSystemSearchKey();
		WareNaviSystem[] wnSystem = (WareNaviSystem[])wnSystemHandler.find( wnSystemSearchKey );

		//#CM700230
		// When system definition information cannot be acquired, throws NotFoundException. 
		if( wnSystem == null || wnSystem.length == 0 )
		{
			throw (new NotFoundException());
		}
		//#CM700231
		// Check the next day processing.
		if (wnSystem[0].getDailyUpdate().equals(WareNaviSystem.DAILYUPDATE_LOADING))
		{
			return  true;
		}
		else
		{
			return  false;
		}
	}

	//#CM700232
	/**
	 * Acquire whether the movement rack package is referring to system definition table (WARENAVI_SYSTEM). <BR>
	 * Return true when there is a movement rack package and return false in case of none. 
	 * 
	 * @param conn Connection object with data base
	 * @return It is none of movement rack package
	 * @throws ScheduleException It is notified when the exception not anticipated in Processing Check is generated. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public boolean isExistIdmPackage(Connection conn) throws ScheduleException, ReadWriteException
	{
		WareNaviSystemManager wWmsManager = new WareNaviSystemManager(conn);	
		return wWmsManager.isIdmPack();
	}

	//#CM700233
	// Package methods -----------------------------------------------
	//#CM700234
	// Protected methods ---------------------------------------------
	//#CM700235
	// Private methods -----------------------------------------------
}
//#CM700236
//end of class
