// $Id: StationModeSCH.java,v 1.2 2006/10/30 00:42:02 suresh Exp $

//#CM46464
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule ;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id42;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.Station;

//#CM46465
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda  <BR>
 * <BR>
 * Class to set the WEB Station mode. <BR>
 * Receive the content input from the screen as parameter, and do Station mode setting Processing.  <BR>
 * Do not do Comment-rollback of the transaction though each Method of this Class must use the Connection object and<BR>
 * do the update processing of the receipt data base.   <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Display button is pressed (<CODE>query()</CODE>Method)<BR><BR><DIR>
 *   Retrieve Station table (<CODE>station</CODE>) and Machine Status table (<CODE>machine</CODE>), <BR>
 *   Display Status of present Station. <BR>
 *   Return empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 * <BR>
 *   <Search condition> <BR><DIR>
 *     Retrieve corresponding data to Station No.  <BR>
 *     Data that Station No of Station management table is corresponding to Station No of Machine Status table <BR></DIR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Station No* <BR></DIR>
 * <BR>
 *   <return data><BR><DIR>
 *     StationName <BR>
 *     Machine Status  <BR>
 *     Work status <BR>
 *     Operation mode <BR>
 *     Work mode <BR>
 *     Work mode request <BR></DIR>
 * <BR>
 *   Retrieve transportation table (<CODE>carryinfo</CODE>), <BR>
 *   Acquire the number of cases of present transportation work. <BR>
 *   Return empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 * <BR>
 *   <Search condition> <BR><DIR>
 *     Data that Station No of Station table is corresponding to Transportation origin Station No of transportation information or,  <BR>
 *     Data to which transportation destination Station No of Status of transportation and Station No of Station table are corresponding <BR></DIR>
 * <BR>
 *   <return data><BR><DIR>
 *     Work qty <BR></DIR></DIR>
 * <BR>
 * 2.Processing when Submit button is pressed.(<CODE>startSCHgetParams()</CODE>Method)<BR><BR><DIR>
 *   Receive the content displayed in the filtering area as parameter, and do Station mode setting Processing. <BR>	
 *   Filtered and acquired from the data base again and return data for the area output when processing normally Ends. <BR>
 *   Return null when the schedule does not End because of the condition error etc.<BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Station No* <BR></DIR>
 * <BR>
 *   <return data> <BR><DIR>
 *     Operation mode <BR>
 *     Work mode <BR></DIR></DIR>
 * <BR>
 *   <Station Mode Processing> <BR>
 * <DIR>
 *   <Processing condition check> <BR>
 *     1.When System Status is off-line, setting it becomes impossible.  <BR>
 *     2.When Station No is not input, setting it becomes impossible.  <BR>
 *     3.Mode switch request Flag switches the mode and setting it becomes impossible when requesting.  <BR>
 *     4.Retrieve the transportation data that specified Station becomes Transportation originStation in the case of the Picking mode switch request,  <BR>
 *       It becomes improper mode switch if Status of transportation is except Start and Abnormal.  <BR>
 * <BR>
 *   <Mode Set Processing> <BR>
 *       1.Acquire Station No, Operation mode, and Work mode and the mode switch flag with parameter.  <BR>
 *       2.Retrieve Station information and acquire controller No.  <BR>
 *       3.Mode Update process <BR><DIR>
 *         -When you do Change to the automatic mode of operation <BR>
 *         Update insertion Picking mode in a neutral mode and update mode Type to the automatic mode of operation respectively.  <BR>
 *         The update condition becomes the data of corresponding Station No to parameter.  <BR>
 * <BR>
 *         -When you do Change to the manual mode of operation <BR><DIR>
 *           A.When it is switched from the automatic mode of operation to the manual mode of operation,  <BR>
 *             Only Work mode doing Change to the neutral does not do the mode switch instruction. <BR>
 *           B.When Work mode is Storage  in case of the cases of except A, work Mode switch instruction is transmitted as parameter Storage (ID-42).  <BR>
 *           C.When Work mode is Picking  in case of the cases of except A, work Mode switch instruction is transmitted as parameter Picking (ID-42).  <BR>
 * </DIR></DIR></DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/01</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:42:02 $
 * @author  $Author: suresh $
 */

public class StationModeSCH extends AbstractAsrsControlSCH
{
	//#CM46466
	// Class fields --------------------------------------------------
	private boolean TEST = false;
 
	//#CM46467
	// Class variables -----------------------------------------------

	//#CM46468
	// Class method --------------------------------------------------
	//#CM46469
	/**
	 * Class Name(Set Station Mode)
	 */
	public static String PROCESSNAME = "StationModeSCH";

	//#CM46470
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 00:42:02 $");
	}

	//#CM46471
	// Constructors --------------------------------------------------
	//#CM46472
	/**
	 * Initialize this Class. 
	 */
	public StationModeSCH()
	{
		wMessage = null;
	}

	//#CM46473
	/**
	 * Method corresponding to the operation to acquire necessary data when initial is displayed the screen. <BR>
	 * Return null when pertinent data does not exist or it exists by two or more.  <BR>
	 * Set null in < CODE>searchParam</CODE > because it does not need the search condition. 
	 * @param conn Connection object with database
	 * @param searchParam Class which succeeds to < CODE>Parameter</CODE> Class with Search condition
	 * @return Class which mounts < CODE>Parameter</CODE > interface where retrieval result is included
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		return null;
	}

	//#CM46474
	/**
	 * The content input from the screen is received as parameter, and filtered, and acquire from the database and return data for the area output.  <BR>
	 * @param conn Instance to maintain connection with data base. 
	 * @param searchParam Instance of < CODE>AsSystemScheduleParameter</CODE>Class with display data acquisition condition.  <BR>
	 *         <CODE>AsSystemScheduleParameter</CODE> ScheduleException when specified excluding the instance is slow. <BR>
	 * @return Have the retrieval result <CODE>AsSystemScheduleParameter</CODE> Array of Instances. <BR>
	 *          Return the empty array when not even one pertinent record is found.  <BR>
	 *          Return null when the error occurs in the input condition.  <BR>
	 *          When null is returned, the content of the error can be acquired as a message in <CODE>getMessage()</CODE>Method. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		AsScheduleParameter param = (AsScheduleParameter)searchParam;

		StationHandler wStHandler = new StationHandler(conn);
		StationSearchKey wStSearchKey = new StationSearchKey();
		CarryInformationHandler wCiHandler = new CarryInformationHandler(conn);
		CarryInformationSearchKey wCiSearchKey = new CarryInformationSearchKey();

		//#CM46475
		// Set Station information Search condition. 
		wStSearchKey.KeyClear();
		if (!StringUtil.isBlank(param.getStationNo()))
		{
			wStSearchKey.setStationNumber(param.getStationNo());
		}
		//#CM46476
		// Only Station where controller (AGC) No is set
		wStSearchKey.setControllerNumber(0, "!=");
		Station[] rStation = (Station[])wStHandler.find(wStSearchKey);

		if (rStation == null || rStation.length == 0)
		{
			//#CM46477
			// 6003018 = There was no object data. .
			wMessage = "6003018";
			return null;				
		}

		Vector vec = new Vector();

		for (int lc = 0; lc < rStation.length; lc++)
		{
			AsScheduleParameter dispData = new AsScheduleParameter();

			//#CM46478
			// Acquire Work qty in correspondence Station from the transportation table. 
			wCiSearchKey.KeyClear();
			wCiSearchKey.setSourceStationNumber(param.getStationNo(), "=", "(", "", "OR");
			wCiSearchKey.setDestStationNumber(param.getStationNo(), "=", "", ")", "OR");
			
			//#CM46479
			// Consolidating condition
			//#CM46480
			// Transportation Key
			wCiSearchKey.setCarryKeyCollect("DISTINCT");
			
			int rCount = wCiHandler.count(wCiSearchKey);
			
			//#CM46481
			// Station No
			dispData.setStationNo(rStation[lc].getStationNumber());
			//#CM46482
			// StationName
			dispData.setDispStationName(rStation[lc].getStationName());
			//#CM46483
			// Operation mode
			dispData.setModeType(Integer.toString(rStation[lc].getModeType()));
			//#CM46484
			// Operation modeName
			dispData.setDispModeTypeName(DisplayText.getText("STATION","MODETYPE", Integer.toString(rStation[lc].getModeType())));
			//#CM46485
			// Work mode
			int wCurrentMode = 0;
			switch(rStation[lc].getCurrentMode())
			{
				case Station.NEUTRAL:
					wCurrentMode = Station.NEUTRAL;
					break ;
				case Station.STORAGE_MODE:
					wCurrentMode = Station.STORAGE_MODE;
					break ;
				case Station.RETRIEVAL_MODE:
					wCurrentMode = Station.RETRIEVAL_MODE;
					break ;
				//#CM46486
				//<en> All others are undefined station status.</en>
				default:
					break;
			}
			
			dispData.setCurrentMode(Integer.toString(wCurrentMode));
			//#CM46487
			// Work modeName
			dispData.setDispCurrentModeName(DisplayText.getText("STATION","CURRENTMODE", Integer.toString(wCurrentMode)));
			//#CM46488
			// Machine Status Name
			dispData.setDispControllerStatusName(DisplayText.getText("STATION","STATUS", Integer.toString(rStation[lc].getStatus())));
			//#CM46489
			// Work status
			dispData.setWorkingStatus(Integer.toString(rStation[lc].getSuspend()));
			//#CM46490
			// Work statusName
			dispData.setDispWorkingStatusName(DisplayText.getText("STATION","SUSPEND", Integer.toString(rStation[lc].getSuspend())));
			//#CM46491
			// Work qty
			dispData.setWorkingCount(rCount);
			//#CM46492
			// Work mode switch request
			dispData.setWorkingModeRequest(Integer.toString(rStation[lc].getModeRequest()));
			//#CM46493
			// Work mode switch requestName
			dispData.setDispWorkingModeRequestName(DisplayText.getText("STATION","MODEREQUEST", Integer.toString(rStation[lc].getModeRequest())));

			vec.addElement(dispData);
		}

		AsScheduleParameter[] paramArray = new AsScheduleParameter[vec.size()];
		vec.copyInto(paramArray);

		//#CM46494
		// 6001013 = Displayed..
		wMessage = "6001013";
		return paramArray;

	}

	//#CM46495
	/**
	 * The content input from the screen is received as parameter, and Starts the Station mode setting schedule. <BR>
	 * Receive parameter by the array because the input of two or more of set of straightening data is assumed. <BR>
	 * Filtered and acquired from the data base again and return data for the area output when processing normally Ends. <BR>
	 * Return null when the schedule does not End because of the condition error etc.
	 * @param conn Instance to maintain connection with data base. 
	 * @param startParams Set the content <CODE>AsSystemScheduleParameter</CODE>Class Array of Instances.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		StationHandler wStHandler = new StationHandler(conn);
		StationAlterKey wStAlterKey = new StationAlterKey();
		
		//#CM46496
		// Check on Worker code and Password
		if (!checkWorker(conn, (AsScheduleParameter) startParams[0]))
		{
			 return null;
		}

		//#CM46497
		// Filtered and input is checked. 
		Station rStation = (Station)checkList(conn, startParams[0]);
		if (rStation == null)
		{
			return null;
		}

		try
		{		
			AsScheduleParameter rparam = (AsScheduleParameter) startParams[0];

			//#CM46498
			//<en> Station No</en>
			String stno = rparam.getStationNo();
			//#CM46499
			//<en> Operation mode</en>
			int modetype = Integer.parseInt(rparam.getModeType());
			//#CM46500
			//<en> Work mode</en>
			int currentmode = Integer.parseInt(rparam.getCurrentMode());

			//#CM46501
			//<en> In case of 'automatic' mode</en>
			if (modetype == Station.AUTOMATIC_MODE_CHANGE) 
			{
				//#CM46502
				//<en> The mode of Station is updated automatically. </en>
				wStAlterKey.KeyClear();
				wStAlterKey.setStationNumber(stno);
				wStAlterKey.updateModeType(Station.AUTOMATIC_MODE_CHANGE);
				
				wStHandler.modify(wStAlterKey);
				
				//#CM46503
				//<en> Present Work mode of Station is renewed to the neutral. </en>
				wStAlterKey.KeyClear();
				wStAlterKey.setStationNumber(stno);
				wStAlterKey.updateCurrentMode(Station.NEUTRAL);
				
				wStHandler.modify(wStAlterKey);

				//#CM46504
				//<en> The mode was switched to automatic. </en>
				setMessage("6011001");
			}
			//#CM46505
			//<en> For the [Manual] mode of operation</en>
			else if (modetype == Station.AWC_MODE_CHANGE) 
			{
				//#CM46506
				//<en> When it is switched from the automatic mode of operation to the manual mode of operation</en>
				//#CM46507
				//<en> Only Work mode doing Change to the neutral does not do the mode switch instruction. </en>
				if (rStation.getModeType() == Station.AUTOMATIC_MODE_CHANGE)
				{
					//#CM46508
					//<en> The mode of Station is updated to the manual operation. </en>
					wStAlterKey.KeyClear();
					wStAlterKey.setStationNumber(stno);
					wStAlterKey.updateModeType(Station.AWC_MODE_CHANGE);
				
					wStHandler.modify(wStAlterKey);
				
					//#CM46509
					//<en> Present Work mode of Station is renewed to the neutral. </en>
					wStAlterKey.KeyClear();
					wStAlterKey.setStationNumber(stno);
					wStAlterKey.updateCurrentMode(Station.NEUTRAL);
				
					wStHandler.modify(wStAlterKey);

					//#CM46510
					//<en> The mode was switched to the manual operation. </en>
					setMessage("6011022");
				}
				else
				{
					//#CM46511
					//<en> If the work mode is on 'storage'</en>
					if (currentmode == Station.STORAGE_MODE)
					{
						//#CM46512
						//<en>Work Mode switch instruction transmission</en>
						if (this.TEST)
						{
							System.out.println("入庫モード切替テキスト送信:" + stno);
						}
						else
						{
							SystemTextTransmission.id42send(rStation, Integer.toString(As21Id42.CLASS_STORAGE), conn);
						}

						//#CM46513
						//<en>Switch the Storage mode of Station and request. </en>
						wStAlterKey.KeyClear();
						wStAlterKey.setStationNumber(stno);
						wStAlterKey.updateModeRequest(Station.STORAGE_REQUEST);
						wStAlterKey.updateModeRequestTime(new Date());
				
						wStHandler.modify(wStAlterKey);

						//#CM46514
						//<en>When the switch is a success</en>
						//#CM46515
						//<en>The mode was switched and requested.</en>
						setMessage("6011002");
					}
					//#CM46516
					//<en> If the work mode is on 'retrieval'</en>
					else if (currentmode == Station.RETRIEVAL_MODE)
					{
						//#CM46517
						//<en>Work Mode switch instruction transmission</en>
						if (this.TEST)
						{
							System.out.println("出庫モード切替テキスト送信:" + stno);
						}
						else
						{
							SystemTextTransmission.id42send(rStation, Integer.toString(As21Id42.CLASS_RETRIEVAL), conn);
						}

						//#CM46518
						//<en>Switch the Picking mode of Station and demand. </en>
						wStAlterKey.KeyClear();
						wStAlterKey.setStationNumber(stno);
						wStAlterKey.updateModeRequest(Station.RETRIEVAL_REQUEST);
						wStAlterKey.updateModeRequestTime(new Date());
				
						wStHandler.modify(wStAlterKey);
						//#CM46519
						//<en> If the mode was switched successfully</en>
						//#CM46520
						//<en> mode switch was requested.</en>
						setMessage("6011002");
					}
				}
			}
		}
		catch(ReadWriteException rwe)
		{
			setMessage(rwe.getMessage());
			return null;
		}
		catch(NotFoundException nfe)
		{
			setMessage(nfe.getMessage());
			return null;
		}
		catch(InvalidDefineException ide)
		{
			setMessage(ide.getMessage());
			return null;
		}
		catch (ConnectException ce)
		{
			//#CM46521
			//<en> Cannot send; no connection with the message log server.</en>
			setMessage("6017003");
			return null;
		}
		catch (NotBoundException nbe)
		{
			//#CM46522
			//<en> Cannot send; there is no connection between AWC and AGC,  though this is </en>
			//<en> conected with the message log server.</en>
			setMessage("6017004");
			return null;
		}
		catch (Exception e)
		{
			//#CM46523
			//<en> Unexpected error occurred.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6006001, e), (String)this.getClass().getName()) ;
			//#CM46524
			//<en> Fatal error occurred. Please refer to the log.</en>
			setMessage("6017011");
			return null;
		}

		return new AsScheduleParameter[0];			
	}

	//#CM46525
	/**<en>
	 * parameterCheck Do processing. 
	 * @param conn Connection object with database
	 * @param checkParam Content of checked parameter
	 * @return Return true when there is no Abnormal in parameter, false when there is Abnormal.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the error not anticipated while checking parameter occurs. 
	 </en>*/
	public Station checkList(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException
	{
		StationHandler wStHandler = new StationHandler(conn);
		StationSearchKey wStSearchKey = new StationSearchKey();
		CarryInformationHandler wCiHandler = new CarryInformationHandler(conn);
		CarryInformationSearchKey wCiSearchKey = new CarryInformationSearchKey();
		GroupControllerHandler wGcHandler = new GroupControllerHandler(conn);
		GroupControllerSearchKey wGcSearchKey = new GroupControllerSearchKey();

		try
		{
			AsScheduleParameter param = (AsScheduleParameter)checkParam;

			//#CM46526
			// Set Station information Search condition. 
			wStSearchKey.KeyClear();
			wStSearchKey.setStationNumber(param.getStationNo());

			Station[] rStation = (Station[])wStHandler.findForUpdateNowait(wStSearchKey);

			//#CM46527
			// The update is enabled. 
			if (rStation[0].getModeRequest() == Station.STORAGE_REQUEST || 
								rStation[0].getModeRequest() == Station.RETRIEVAL_REQUEST)
			{
				//#CM46528
				//<en> The Storage mode is being switched or the Picking mode is being switched.</en>
				setMessage("6013019");
				return null;
			}
			
			//#CM46529
			//<en> It demands the Picking mode switch check on the transportation data. </en>
			//#CM46530
			//<en> Execute it only to Station of Stock confirmation setting. </en>
			if (Integer.parseInt(param.getCurrentMode()) == Station.RETRIEVAL_MODE && 
				rStation[0].getSettingType() == Station.IN_SETTING_CONFIRM)
			{
				//#CM46531
				//<en> Specified Station acquires the transportation data which is Transportation origin Station.</en>
				//#CM46532
				//<en> The mode switch is improper if Status is except Start and Abnormal. </en>
				wCiSearchKey.KeyClear();
				wCiSearchKey.setSourceStationNumber(param.getStationNo());
				wCiSearchKey.setCmdStatus(CarryInformation.CMDSTATUS_START, "=", "", "", "OR");
				wCiSearchKey.setCmdStatus(CarryInformation.CMDSTATUS_ERROR, "=", "", "", "OR");
				
				if (wCiHandler.count(wCiSearchKey) > 0)
				{
					//#CM46533
					//<en> Do not switch the mode because the Storage transportation data exists in Station which does the mode switch. </en>
					setMessage("6013216");
					return null;
				}
			}

			//#CM46534
			//<en> System Status check</en>
			wGcSearchKey.KeyClear();
			wGcSearchKey.setControllerNumber(rStation[0].getControllerNumber());
			
			GroupController[] rGroupControll = (GroupController[])wGcHandler.find(wGcSearchKey);
			
			if (rGroupControll[0].getStatus() != GroupController.STATUS_ONLINE)
			{
				//#CM46535
				//<en> Please set the system status on-line.</en>
				setMessage("6013023");
				return null;
			}
			
			return rStation[0];
		}
		catch (LockTimeOutException ex)
		{
			//#CM46536
			// 6027008=This data is not useable because it is updating it with other terminals. 
			setMessage("6027008");
			return null;
		}
	}
}
//#CM46537
//end of class
