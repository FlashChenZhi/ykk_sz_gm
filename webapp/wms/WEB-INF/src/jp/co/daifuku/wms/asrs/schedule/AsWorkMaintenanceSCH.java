// $Id: AsWorkMaintenanceSCH.java,v 1.2 2006/10/30 00:45:50 suresh Exp $

//#CM45882
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule ;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.asrs.location.StationOperatorFactory;
import jp.co.daifuku.wms.asrs.location.ShelfOperator;
import jp.co.daifuku.wms.asrs.report.WorkMaintenanceWriter;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.asrs.control.CarryCompleteOperator;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM45883
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda  <BR>
 * <BR>
 * Class to do WEB work Maintenance.  <BR>
 * Receive the content input from the screen as parameter and do work Maintenance processing.  <BR>
 * Do not do Comment-rollback of the transaction though each Method of this Class must use the Connection object and<BR>
 * do the update processing of the receipt data base.   <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Display button is pressed (<CODE>query()</CODE>Method)<BR><BR><DIR>
 * <BR>
 * <BR>
 *     Retrieve transportation information (<CODE>carryinfo</CODE>) and Work information(<CODE>dnworkinfo</CODE>), and acquire the display item. <BR>
 *     Return empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *     It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 * <BR>
 *     <Parameter> *Mandatory Input <BR><DIR>
 *       Transportation Key* <BR></DIR>
 * <BR>
 *     <return data> <BR><DIR>
 *       Transportation originStation No <BR>
 *       At the transportation destinationStation No <BR>
 *       Location No. <BR>
 *       Transportation Flag <BR>
 *       The delivery instruction is detailed.  <BR>
 *       Work type <BR>
 *       Status of transportation <BR>
 *       Work No <BR>
 *       Transportation Key <BR></DIR></DIR>
 * <BR>
 * 2.Processing when Submit button is pressed.(<CODE>startSCHgetParams()</CODE>Method) <BR><BR><DIR>
 *   Receive the content displayed in the filtering area as parameter, and do work Maintenance processing.  <BR>
 *   Store Process Flag specified from the screen by the setting this time and pass it to the schedule processing as parameter.  <BR>
 *   Method of the execution processing divides from selected Process Flag.  <BR>
 *   Filtered and acquired from the data base again and return data for the area output when processing normally Ends. <BR>
 *   Return null when the schedule does not End because of the condition error etc.<BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Process Flag* <BR>
 *     Transportation Key* <BR></DIR>
 * <BR>
 *   <return data> <BR><DIR>
 *     true or false <BR></DIR></DIR>
 * <BR>
 *   <Work Maintenance processing> <BR>
 *     1.Retrieve transportation information (<CODE>carryinfo</CODE>), and acquire the item of transportation information.  <BR>
 * <BR>
 *     2.Kicks off processing from Process Flag.  <BR><DIR>
 *       -[Unspecified] processing(<CODE>uninstruct</CODE>) <BR><DIR>
 *         <Check processing> <BR>
 *           -Processing is enabled when data is [Storage] or [Going directly]. <BR>
 *           -Processing is enabled when Status of transportation is [Drawing], [Start], [Load Pick-up Complete], [End], [Arrival] or [Abnormal]. <BR>
 * <BR>
 *         <Unspecified process> (X)Processing is enabled when Status of transportation is [Response] or [Instructed]. <BR>
 *           -Set while demanding the data cancellation of parameter, and process transportation Data Cancel demand transmission (ID-4).  <BR>
 *       </DIR>
 * <BR>
 *       -[End]Processing(<CODE>complete</CODE>) <BR><DIR>
 *         <Check processing> <BR>
 *           -Do Deletion unconditionally if there is Work display data (<CODE>operationdisplay</CODE>).  <BR>
 *           -Status of transportation enables for Storage and only the data of [Arrival], [Instructed], and [Load Pick-up Complete] enables [Force Stop].  <BR>
 *           -When Going directly, Abort is enabled only if Status of transportation is the data of either [Arrival],  [Instructed] or [Load Pick-up complete]. <BR>
 *           -When Picking, Abort is enabled only if Status of transportation is [End]. <BR>
 * <BR>
 *         <End Processing> (X)Do following Processing only for the transportation data which corresponds to the above-mentioned condition.  <BR>
 *           -Do Storage End Processing with complete Storage Method for Storage. <BR><DIR>
 *              1.Release the reservation of Storage shelf, and turn on the stock.  <BR>
 *              2.Delete Carry Information (Deletion is done at the same time as for WorkInformation. ). <BR>
 *              3.Confirm whether other Drawing to Palette exists. (Do 4.5 Processing when there is no Drawing of other Palette and, in some cases, do 6 Processing. ) <BR>
 *              4.Do Change to an abnormal shelf if Item code is Item code for double storage. Do Change to Real shelf besides.  <BR>
 *              5.Change the Drawing flag to Not Drawing.  <BR>
 *              6.Change the Stock Status to Picking reservation.  <BR></DIR>
 * <BR>
 *           -When Going directly, Do Going directly End Processing with complete Storage Method. <BR><DIR>
 *              1.Execute arrival Processing of Station Class. <BR></DIR>
 * </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/04</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:45:50 $
 * @author  $Author: suresh $
 */

public class AsWorkMaintenanceSCH extends AbstractSCH
{
	//#CM45884
	// Class fields --------------------------------------------------

	//#CM45885
	/**<en>
	 * Field which shows Process Flag (Return it to the Not Instructed.)
	 </en>*/
	public static final String  KIND_UNINSTRUCT = "0" ;

	//#CM45886
	/**<en>
	 * Field which shows Process Flag (Force Stop)
	 </en>*/
	public static final String  KIND_COMPLETE = "2" ;

	//#CM45887
	/**<en>
	 * Field which shows Process Flag (tracking Deletion)
	 </en>*/
	public static final String  KIND_TRACKINGDEL= "3" ;

	//#CM45888
	// Class variables -----------------------------------------------
	//#CM45889
	/**
	 * Processing name
	 */
	private final String CLASS_NAME = "AsWorkMaintenanceSCH";

	//#CM45890
	// Class method --------------------------------------------------
	//#CM45891
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 00:45:50 $");
	}

	//#CM45892
	// Constructors --------------------------------------------------
	//#CM45893
	/**
	 * Initialize this Class. 
	 */
	public AsWorkMaintenanceSCH()
	{
		wMessage = null;
	}

	//#CM45894
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

	//#CM45895
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
		try
		{
			AsScheduleParameter workparam = (AsScheduleParameter) searchParam;

			//#CM45896
			//<en> Acquire Transportation key from parameter. </en>
			String carrykey = workparam.getCarryKey();
			CarryInformationHandler cryHandler = new CarryInformationHandler(conn);
			CarryInformationSearchKey cryKey = new CarryInformationSearchKey();
			cryKey.setCarryKey(carrykey);
			CarryInformation ci = (CarryInformation) cryHandler.findPrimary(cryKey);
			if (ci == null)
			{
				//#CM45897
				//<en> There was no transportation data which became an object. </en>
				wMessage = "6027010";
				return null;
			}

			AsScheduleParameter qyparam = new AsScheduleParameter();

			//#CM45898
			// Shelf No, Transportation origin Station No, transportation destination Station No, Station No are judged from Transportation Flag.
			switch (ci.getCarryKind())
			{
				//#CM45899
				// Transportation at Storage from Station to shelf
				//#CM45900
				// Set Aisle in the transportation destination. 
				case CarryInformation.CARRYKIND_STORAGE :
					qyparam.setLocationNo(ci.getDestStationNumber());
					qyparam.setFromStationNo(ci.getSourceStationNumber());
					qyparam.setToStationNo(ci.getAisleStationNumber());
					qyparam.setStationNo(ci.getSourceStationNumber());
					break;
					
				//#CM45901
				// Transportation at Picking time from shelf to Station
				//#CM45902
				// Set Aisle in Transportation origin. 
				case CarryInformation.CARRYKIND_RETRIEVAL :
					String locationno = null;
					//#CM45903
					// Set Retrieval Location No in case of Stock confirmation.
					if(ci.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK)
					{
						locationno = ci.getRetrievalStationNumber();
					}
					//#CM45904
					// Transportation originStation is set, except for Stock confirmation. 
					else
					{
						locationno = ci.getSourceStationNumber();
					}
					
					if (!StringUtil.isBlank(locationno))
					{
						qyparam.setLocationNo(locationno);
					}
					else
					{
						qyparam.setLocationNo("");
					}
					qyparam.setFromStationNo(ci.getAisleStationNumber());;
					qyparam.setToStationNo(ci.getDestStationNumber());
					qyparam.setStationNo(ci.getDestStationNumber());
					break;
					
				//#CM45905
				// Movement from shelf to shelf while movement from Rack to rack
				//#CM45906
				// Do not display Location No on the screen at all.
				//#CM45907
				// Transportation originSet Aisle in the transportation destination. 
				//#CM45908
				// Do not set Station at all. 
				case CarryInformation.CARRYKIND_RACK_TO_RACK :
					if (!StringUtil.isBlank(ci.getRetrievalStationNumber()))
					{
						qyparam.setLocationNo(ci.getRetrievalStationNumber());
					}
					else
					{
						qyparam.setLocationNo("");
					}
					qyparam.setFromStationNo(ci.getSourceStationNumber());;
					qyparam.setToStationNo(ci.getDestStationNumber());
					qyparam.setStationNo("");
					break;
					
				//#CM45909
				// Movement from Station to Station while Going directly.
				//#CM45910
				// Do not display it in Location No at all. 
				//#CM45911
				// Set each Station No to Transportation origin and Transportation destination. 
				//#CM45912
				// Do not display it in Station No on the screen at all. 
				case CarryInformation.CARRYKIND_DIRECT_TRAVEL :
					qyparam.setLocationNo("");
					qyparam.setFromStationNo(ci.getSourceStationNumber());
					qyparam.setToStationNo(ci.getDestStationNumber());
					qyparam.setStationNo("");
					break;
					
				default :
					qyparam.setLocationNo("");
					qyparam.setFromStationNo("");
					qyparam.setToStationNo("");
					qyparam.setStationNo("");
					break;
			}
			
			//#CM45913
			// Work Flag
			qyparam.setJobType(Integer.toString(ci.getCarryKind()));
			qyparam.setJobTypeName(DisplayText.getText("CARRYINFO","CARRYKIND", qyparam.getJobType()));
			ASFindUtil util = new ASFindUtil(conn);			
			//#CM45914
			// Transportation originName
			qyparam.setFromStationName(util.getAsStationName(qyparam.getFromStationNo()));
			//#CM45915
			// At the transportation destinationName
			qyparam.setToStationName(util.getAsStationName(qyparam.getToStationNo()));
			//#CM45916
			// The delivery instruction is detailed. 
			if (ci.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_PICKING || 
			    ci.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_UNIT)
			{
				qyparam.setDispRetrievalDetail(DisplayText.getText("CARRYINFO", "RETRIEVALDETAIL", Integer.toString(ci.getRetrievalDetail())));
			}
			else
			{
				qyparam.setDispRetrievalDetail(DisplayText.getText("CARRYINFO", "RETRIEVALDETAIL", Integer.toString(CarryInformation.RETRIEVALDETAIL_UNKNOWN)));
			}

			//#CM45917
			// Work type
			qyparam.setWorkingType(ci.getWorkType());
			if( !StringUtil.isBlank(qyparam.getWorkingType()) )
			{
				qyparam.setDispWorkType(DisplayText.getText("CARRYINFO","WORKTYPE", qyparam.getWorkingType()));
			}

			//#CM45918
			// Work status(Status of transportation)
			qyparam.setCarringStatus(new Integer(ci.getCmdStatus()).toString());
			qyparam.setDispCarringStatusName(DisplayText.getText("CARRYINFO","CMDSTATUS", qyparam.getCarringStatus()));

			//#CM45919
			// Work No
			qyparam.setWorkingNo(ci.getWorkNumber());

			//#CM45920
			// Transportation Key
			qyparam.setCarryKey(carrykey);

			AsScheduleParameter[] rparam = new AsScheduleParameter[1];
			rparam[0] = qyparam;
			
			return rparam;
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM45921
	/**
	 * Receive the content input from the screen as parameter, and work Maintenance. <BR>
	 * Return true when Maintenance is ended normally, false when failing.
	 * @param conn Instance to maintain connection with data base. 
	 * @param startParams Array of instance of < CODE>AsScheduleParameter</CODE>Class with set content. 
	 * @return Return true when Maintenance is ended normally, false when failing.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		AsScheduleParameter workparam = (AsScheduleParameter) startParams[0];

		//#CM45922
		//Output work Maintenance List to the printer of the terminal which works. 
		String terminalNumber = workparam.getTerminalNumber();

		//#CM45923
		// Acquire Worker code and Password from parameter. .
		String workerCode = workparam.getWorkerCode();
		String password = workparam.getPassword();
		if( !correctWorker(conn, workerCode, password) )
		{
			return false;
		}

		CarryInformation ci = find(conn, workparam.getCarryKey());
		if (ci == null)
		{
			//#CM45924
			//<en> There was no transportation data which became an object. </en>
			wMessage = "6027010";
			return false;
		}

		//#CM45925
		//<en> Do Processing of each Process Flag selected on the screen. </en>
		if( workparam.getProcessStatus().equals(KIND_UNINSTRUCT) )
		{
			//#CM45926
			//<en> Return it as Not Instructed.</en>
			if (uninstruct(conn, ci, terminalNumber))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if( workparam.getProcessStatus().equals(KIND_COMPLETE) )
		{
			//#CM45927
			//<en> Force Stop</en>
			if (complete(conn, ci))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if( workparam.getProcessStatus().equals(KIND_TRACKINGDEL) )
		{
			//#CM45928
			//<en> Delete Tracking</en>
			if (delete(conn, ci, terminalNumber))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			//#CM45929
			//<en> 6026029= The value not anticipated was set. {0}={1}</en>
			String msg = "6026029" + wDelim + "getProcessingKind()" + wDelim + workparam.getProcessStatus();
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM45930
			//<en> 6017011= A fatal error occurred. Refer to the log.</en>
			throw new ScheduleException("6017011");
		}
	}
	
	//#CM45931
	/**
	 * Do parameterCheck processing. It is called before MaintenanceProcessing is executed. 
	 * This Method is <code>AbstractSCH</code> because the parameter check is different depending on the kind of Maintenance.
	 * It is mounted by Class which succeeds to Class. Return True when succeeding in parameter check, 
	 * false when failing.
	 * The reason can be acquired in <code>getMessage</code> when failing in the parameter check. 
	 * @param conn Connection object with database
	 * @param param Content of checked parameter
	 * @return Return True when succeeding in parameter check, false when failing.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the error not anticipated while checking parameter occurs. 
	 */
	public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		AsScheduleParameter workparam = (AsScheduleParameter)param;

		//#CM45932
		// Acquire Worker code and Password from parameter. .
		String workerCode = workparam.getWorkerCode();
		String password = workparam.getPassword();
		if( !correctWorker(conn, workerCode, password) )
		{
			return false;
		}

		//#CM45933
		// Next day update Processing check
		if (isDailyUpdate(conn))
		{
			return false;
		}

		//#CM45934
		//<en> Acquire Transportation key from parameter. </en>
		String carrykey = workparam.getCarryKey();
		//#CM45935
		//<en> Specify Transportation key and acquire the transportation data. </en>
		CarryInformation ci = find(conn, carrykey);

		if (ci == null)
		{
			//#CM45936
			//<en> There was no transportation data which became an object. </en>
			wMessage = "6027010";
			return false;
		}
		//#CM45937
		//<en> System Status Check</en>
		if (!checkStatus(conn, ci))
		{
			//#CM45938
			//<en> Make system Status online. .</en>
			wMessage = "6013023";
			return false;
		}
		if (!checkStation(conn, ci))
		{
			//#CM45939
			//<en> Maintenance cannot be done while StationStatus is not interrupting. </en>
			wMessage = "6023422";
			return false;
		}

		return true;
	}



	//#CM45940
	// Protected methods ---------------------------------------------
	
	//#CM45941
	// Private methods -----------------------------------------------

	//#CM45942
	/**
	 * Retrieve wCarryKey from the CarryInfo table.
	 * @param conn Connection object with database.
	 * @param carrykey Transportation key
	 * @return Retrieval result of CarryInfo
	 * @throws ScheduleException  It is notified when there is no object data. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private CarryInformation find(Connection conn, String carrykey) throws ScheduleException, ReadWriteException
	{
		CarryInformation[] ci = null;
		CarryInformationHandler CIh  = new CarryInformationHandler(conn);
		CarryInformationSearchKey CIkey 	 = new CarryInformationSearchKey();

		CIkey.setCarryKey(carrykey);
		ci = (CarryInformation[])CIh.find(CIkey);

		//#CM45943
		//<en> The object data is not found.</en>
		if(ci == null || ci.length <= 0)
		{
			return null;
		}

		return (ci[0]) ;
	}

	//#CM45944
	/**
	 * Check it while interrupting Station. 
	 * @param conn Connection object with database.
	 * @param ci    CarryInformation instance
	 * @return Return True while interrupting. 
	 * @throws ScheduleException  It is notified when there is no object data. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private boolean checkStation(Connection conn, CarryInformation ci) throws ScheduleException, ReadWriteException
	{
		//#CM45945
		//<en> No check in Rack to rack movement. </en>
		if (ci.getCarryKind() == CarryInformation.CARRYKIND_RACK_TO_RACK)
		{
			return true;
		}

		//#CM45946
		//<en> Work Station acquisition</en>
		String workstation = "";
	    if ( ci.getCarryKind() == CarryInformation.CARRYKIND_STORAGE ||
	    		ci.getCarryKind() == CarryInformation.CARRYKIND_DIRECT_TRAVEL )
	    {
			//#CM45947
			//<en> Storage</en>
			workstation = ci.getSourceStationNumber();
		}
		else if ( ci.getCarryKind() == CarryInformation.CARRYKIND_RETRIEVAL )
		{
			//#CM45948
			//<en> Picking</en>
			workstation = ci.getDestStationNumber();
		}

		//#CM45949
		//<en> Work Station acquisition.</en>
		StationHandler stHandler = new StationHandler(conn);
		StationSearchKey sKey = new StationSearchKey();
		sKey.setStationNumber(workstation);
		Station[] sts = (Station[]) stHandler.find(sKey);
		return sts[0].isSuspend();
	}

	//#CM45950
	/**
	 * Execute work Maintenance Correction processing (Return the Not Instructed). 
	 * @param conn           Connection object with database
	 * @param ci             CarryInformation instance
	 * @param terminalNumber Terminal No.
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ScheduleException  It is notified when there is no object data. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private boolean uninstruct(Connection conn, CarryInformation ci, String terminalNumber)
														throws ScheduleException, ReadWriteException
	{
		try
		{
			//#CM45951
			//<en> When Transportation Flag is Storage and Going directly, cancel and release the drawing.</en>
			if (ci.getCarryKind() == CarryInformation.CARRYKIND_STORAGE
				|| ci.getCarryKind() == CarryInformation.CARRYKIND_DIRECT_TRAVEL)
			{
				//#CM45952
				//<en> Processing cannot be executed and Return it as Not Instructed and Drawing when Transportation Flag is Storage or Going directly. </en>
				wMessage = "6023423";
				return false;
			}

			//#CM45953
			//<en> Confirm the Status of transportation</en>
			switch(ci.getCmdStatus())
			{
				//#CM45954
				//<en> Response waiting</en>
				case CarryInformation.CMDSTATUS_WAIT_RESPONSE:
				//#CM45955
				//<en> Instructed</en>
				case CarryInformation.CMDSTATUS_INSTRUCTION:
					//#CM45956
					//<en> Set while demanding the data cancellation. (When Return it as Not Instructed.)</en>
					alterCarryInfoCancelRequest(conn, ci.getCarryKey(), CarryInformation.CANCELREQUEST_CANCEL);
					//#CM45957
					//2004/09/07 [Standard correction]:Print Processing Change of Work Maintenance List 
					alterCarryInfoMaintenanceTerminal(conn, ci.getCarryKey(), terminalNumber);
					//#CM45958
					//<en> Do transportation Data Cancel demand from As21Id04. </en>
					SystemTextTransmission.id04send(ci, conn);
					//#CM45959
					//<en> Transportation Data Cancel demand was done. </en>
					wMessage = "6021020";
					return true;
				//#CM45960
				//<en> Drawing</en>
				//#CM45961
				//<en> Start</en>
				//#CM45962
				//<en> Load Pick-up Complete</en>
				//#CM45963
				//<en> PickingEnd</en>
				//#CM45964
				//<en> Arrival</en>
				//#CM45965
				//<en> Abnormal</en>
				case CarryInformation.CMDSTATUS_ALLOCATION:
				case CarryInformation.CMDSTATUS_START:
				case CarryInformation.CMDSTATUS_PICKUP:
				case CarryInformation.CMDSTATUS_COMP_RETRIEVAL:
				case CarryInformation.CMDSTATUS_ARRIVAL:
				case CarryInformation.CMDSTATUS_ERROR:
					//#CM45966
					//<en> It cannot be Returned as Not Instructed in present Status of transportation.</en>
					wMessage = "6023433";
					return false;
				default:
					//#CM45967
					//<en> 6026029=The value not anticipated was set. {0}={1}</en>
					String msg = "6026029" + wDelim + "ci.getCmdStatus()" + wDelim + ci.getCmdStatus();
					RmiMsgLogClient.write( msg, (String)this.getClass().getName());
					//#CM45968
					//<en> A fatal error occurred.</en>
					throw new ScheduleException("6017011");
			}
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (Exception e)
		{
			//#CM45969
			//<en> The error not anticipated occurred. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6006001, e), (String)this.getClass().getName() ) ;
			//#CM45970
			//<en> A fatal error occurred.</en>
			wMessage = "6017011";
			return false;
		}
	}

	//#CM45971
	/**
	 * Execute work Maintenance Correction processing (Delete Tracking). 
	 * @param conn           Connection object with database
	 * @param ci             CarryInformation instance
	 * @param terminalNumber Terminal No.
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ScheduleException  It is notified when there is no object data. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private boolean delete(Connection conn, CarryInformation ci, String terminalNumber)
												throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM45972
			//<en> When Status is transportation data of Picking or Rack to rack movement or Drawing or Start, </en>
			//#CM45973
			//<en> the transportation data which becomes an object Changes to Delete Tracking.  </en>
			if (ci.getCarryKind() == CarryInformation.CARRYKIND_RETRIEVAL
			 || ci.getCarryKind() == CarryInformation.CARRYKIND_RACK_TO_RACK)
			{
				if (ci.getCmdStatus() == CarryInformation.CMDSTATUS_START
				 || ci.getCmdStatus() == CarryInformation.CMDSTATUS_ALLOCATION)
				{
					//#CM45974
					//<en> Work status cannot execute Delete Tracking for the Picking </en>
					//#CM45975
					//<en> transportation data of Drawing or Start.</en>
					wMessage = "6023426";
					return false;
				}
			}

			//#CM45976
			//<en> Printing conditions of work MaintenanceList and Terminal  No. are set. </en>
			WorkMaintenanceWriter writer = new WorkMaintenanceWriter(conn);
			writer.setCarrykey(ci.getCarryKey());
			writer.setCmdStatus(ci.getCmdStatus());
			writer.setWorkType(ci.getWorkType());
			//#CM45977
			//<en> Delete Tracking</en>
			writer.setJob("LBL-W0538");

			//#CM45978
			//<en> Do Deletion unconditionally if there is Work display data. </en>
			OperationDisplayHandler odhandle = new OperationDisplayHandler(conn);
			OperationDisplaySearchKey odkey = new OperationDisplaySearchKey();
			odkey.setCarryKey(ci.getCarryKey());
			if (odhandle.count(odkey) > 0)
			{
				try
				{
					odhandle.drop(odkey);
				}
				//#CM45979
				//<en> NotFoundException is not generated. </en>
				catch (NotFoundException e)
				{}
			}

			//#CM45980
			//<en> Confirm the Status of transportation</en>
			switch(ci.getCmdStatus())
			{
				//#CM45981
				//<en> Response waiting</en>
				//#CM45982
				//<en> Instructed</en>
				//#CM45983
				//<en> Load Pick-up Complete</en>
				//#CM45984
				//<en> PickingEnd</en>
				//#CM45985
				//<en> Arrival</en>
				case CarryInformation.CMDSTATUS_WAIT_RESPONSE:
				case CarryInformation.CMDSTATUS_INSTRUCTION:
				case CarryInformation.CMDSTATUS_PICKUP:
				case CarryInformation.CMDSTATUS_COMP_RETRIEVAL:
				case CarryInformation.CMDSTATUS_ARRIVAL:
					//#CM45986
					//<en> Do not print here so that work MaintenanceList may do Print when it is possible
					//#CM45987
					//  to cancel by the cancellation response. </en>

					//#CM45988
					//<en> Set while demanding Stock data Deletion.</en>
					//#CM45989
					//CarryInformationHandler ciHandler = new CarryInformationHandler(conn);
					
					alterCarryInfoCancelRequest(conn, ci.getCarryKey(), CarryInformation.CANCELREQUEST_DROP);
					//#CM45990
					//2004/09/07 [Standard correction]:Print Processing Change of Work Maintenance List 
					alterCarryInfoMaintenanceTerminal(conn, ci.getCarryKey(), terminalNumber);
					if (ci.getCarryKind() == CarryInformation.CARRYKIND_STORAGE|| ci.getCarryKind() == CarryInformation.CARRYKIND_DIRECT_TRAVEL)
					{
						//#CM45991
						//<en> Clear CARRYKEY of Transportation originStation, Mode of packing information, and the BC data.</en>
						Station st = StationFactory.makeStation(conn, ci.getSourceStationNumber());
						if (st.isArrivalCheck())
						{
							StationOperator sop = StationOperatorFactory.makeOperator(conn, st.getStationNumber(), st.getClassName());
							sop.dropArrival();
						}
					}
					//#CM45992
					//<en> Do transportation Data Cancel demand from As21Id04. </en>
					SystemTextTransmission.id04send(ci, conn);
					//#CM45993
					//<en> Transportation Data Cancel demand was done. </en>
					wMessage = "6021020";
					return true;
				//#CM45994
				//<en> Drawing</en>
				//#CM45995
				//<en> Start</en>
				//#CM45996
				//<en> Abnormal</en>
				case CarryInformation.CMDSTATUS_ALLOCATION:
				case CarryInformation.CMDSTATUS_START:
				case CarryInformation.CMDSTATUS_ERROR:
					//#CM45997
					//<en> Print execution</en>
					if (writer.startPrint())
					{
						//#CM45998
						//<en> Deleted.</en>
						wMessage = "6001005";
					}
					else
					{
						//#CM45999
						//<en> After Deletion, It failed in the print. Refer to the log. </en>
						wMessage = "6007043";
					}

					//#CM46000
					//<en> Work deletion of CARRY, PALETTE, STOCK</en>
					CarryCompleteOperator ciop = new CarryCompleteOperator();
					ciop.setClassName(CLASS_NAME);
					ciop.drop(conn, ci, false);

					return true;
				default:
					//#CM46001
					//<en> 6026029=The value not anticipated was set. {0}={1}</en>
					String msg = "6026029" + wDelim + "ci.getCmdStatus()" + wDelim + ci.getCmdStatus();
					RmiMsgLogClient.write( msg, (String)this.getClass().getName());
					//#CM46002
					//<en> 6017011=A fatal error occurred.Refer to the log. .</en>
					throw new ScheduleException("6017011");
			}
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (Exception e)
		{
			//#CM46003
			//<en> The error not anticipated occurred. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6006001, e), (String)this.getClass().getName()) ;
			//#CM46004
			//<en> A fatal error occurred.</en>
			wMessage = "6017011";
			return false;
		}
	}

	//#CM46005
	/**<en>
	 * Execute work Maintenance Correction processing (Force Stop). 
	 * @param conn Connection object with database
	 * @param param CarryInformation instance
	 * @throws ScheduleException  It is notified when there is no object data. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 </en>*/
	private boolean complete(Connection conn, CarryInformation ci)
												throws ReadWriteException, ScheduleException
	{
		//#CM46006
		// Printing conditions of work Maintenance List are set.
		WorkMaintenanceWriter writer = new WorkMaintenanceWriter(conn);
		writer.setCarrykey(ci.getCarryKey());
		writer.setCmdStatus(ci.getCmdStatus());
		writer.setWorkType(ci.getWorkType());
		//#CM46007
		//<en> Force Stop</en>
		writer.setJob("LBL-W0606");

		//#CM46008
		//<en> Do Deletion unconditionally if there is Work display data. </en>
		OperationDisplayHandler odhandle = new OperationDisplayHandler(conn);
		OperationDisplaySearchKey odkey = new OperationDisplaySearchKey();
		odkey.setCarryKey(ci.getCarryKey());
		if (odhandle.count(odkey) > 0)
		{
			try{
				odhandle.drop(odkey);
			}
			//#CM46009
			//<en> NotFoundException is not generated. </en>
			catch (NotFoundException e)
			{}
		}

		//#CM46010
		//<en> As for Confirm the Status of transportation doing Storage, Force Stop does the one of Picking End to Instructed and Picking. </en>
		switch (ci.getCarryKind())
		{
			//#CM46011
			//<en> Storage</en>
			case CarryInformation.CARRYKIND_STORAGE:
				//#CM46012
				//<en> Status of transportation is Arrival for Storage.    Only the data of Instructed and Load Pick-up Complete is possible for Force Stop. </en>
				if ((ci.getCmdStatus() == CarryInformation.CMDSTATUS_INSTRUCTION)
				 || (ci.getCmdStatus() == CarryInformation.CMDSTATUS_PICKUP)
				 || (ci.getCmdStatus() == CarryInformation.CMDSTATUS_ARRIVAL))
				{
					if (writer.startPrint())
					{
						//#CM46013
						//<en> Force Stoped.</en>
						wMessage = "6001007";
					}
					else
					{
						//#CM46014
						//<en> After submitting, It failed in the print. Refer to the log. </en>
						wMessage = "6007042";
					}
					
					completeStorage(conn, ci);
					return true;
				}
				else
				{
					//#CM46015
					//<en> Status of transportation is good when Transportation Flag is Storage and only one of Instructed is good at Force Stop.  </en>
					wMessage = "6023424";
					return false;

				}
			//#CM46016
			//<en> direct travel</en>
			case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
				//#CM46017
				//<en> Status of transportation is Arrival for Going directly.    Only the data of Instructed and Load Pick-up Complete is possible for Force Stop.</en>
				if ((ci.getCmdStatus() == CarryInformation.CMDSTATUS_INSTRUCTION)
				 || (ci.getCmdStatus() == CarryInformation.CMDSTATUS_PICKUP)
				 || (ci.getCmdStatus() == CarryInformation.CMDSTATUS_ARRIVAL))
				{

					if (writer.startPrint())
					{
						//#CM46018
						//<en> Force Stoped.</en>
						wMessage = "6001007";
					}
					else
					{
						//#CM46019
						//<en> After submitting, It failed in the print. Refer to the log. </en>
						wMessage = "6007042";
					}
					completeDirectTravel(conn, ci);
					return true;
				}
				else
				{
					//#CM46020
					//<en> Status of transportation is good when Transportation Flag is Storage and only one of Instructed is good at Force Stop.  </en>
					wMessage = "6023424";
					return false;

				}
			//#CM46021
			//<en> Picking</en>
			//#CM46022
			//<en> Rack to rack movement</en>
			case CarryInformation.CARRYKIND_RETRIEVAL:
				//#CM46023
				//<en> Only the data of Picking End is possible for Force Stop.</en>
				if (ci.getCmdStatus() == CarryInformation.CMDSTATUS_COMP_RETRIEVAL)
				{
					//#CM46024
					//<en> Print execution</en>
					if (writer.startPrint())
					{
						//#CM46025
						//<en> Force Stoped.</en>
						wMessage = "6001007";
					}
					else
					{
						//#CM46026
						//<en> After submitting, It failed in the print. Refer to the log. </en>
						wMessage = "6007042";
					}
					completeRetrieval(conn, ci);
					return true;
				}
				else
				{
					//#CM46027
					//<en> Status of transportation is good when Transportation Flag is Picking and only the one of PickingEnd is good at Force Stop.  </en>
					wMessage = "6023425";
					return false;
				}
			//#CM46028
			// Rack to rack movement
			case CarryInformation.CARRYKIND_RACK_TO_RACK:
				//#CM46029
				// For Rack to rack movement, only the Instructed Status of transportation data of End is possible for Force Stop.
				if ((ci.getCmdStatus() == CarryInformation.CMDSTATUS_INSTRUCTION)
				 || (ci.getCmdStatus() == CarryInformation.CMDSTATUS_COMP_RETRIEVAL))
				{
					//#CM46030
					// Print execution
					if (writer.startPrint())
					{
						//#CM46031
						//<en> Force Stoped.</en>
						wMessage = "6001007";
					}
					else
					{
						//#CM46032
						//<en> After submitting, It failed in the print. Refer to the log. </en>
						wMessage = "6007042";
					}
					completeRacktoRack(conn, ci);
					return true;
				}
				else
				{
					//#CM46033
					// Status of transportation is good when Transportation Flag is Going directly and only the one of Instructed is good at Force Stop.
					wMessage = "6023424";
					return false;

				}

			default:
				//#CM46034
				//<en> 6026029=The value not anticipated was set. {0}={1}</en>
				String msg = "6026029" + wDelim + "ci.getCarryKind()" + wDelim + ci.getCarryKind();
				RmiMsgLogClient.write( msg, (String)this.getClass().getName());
				//#CM46035
				//<en> A fatal error occurred.</en>
				throw new ScheduleException("6017011");
		}
	}
	//#CM46036
	/**<en>
	 * Do Storage End Processing. 
	 * @param conn Connection object with database
	 * @param ci Force Stop object work data
	 * @throws ScheduleException  It is notified when there is no object data. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 </en>*/
	private void completeStorage(Connection conn, CarryInformation ci) throws ScheduleException, ReadWriteException
	{
		try
		{
			//#CM46037
			//<en> Definition of variables -------------------------------------</en>
			CarryInformationHandler wCIHandler = new CarryInformationHandler(conn) ;
			CarryInformationSearchKey wCIKey = new CarryInformationSearchKey() ;

			//#CM46038
			//<en> Processing Start -------------------------------------</en>
			//#CM46039
			//<en> Instance acquisition of destination Station</en>
			ShelfHandler slfHandler = new ShelfHandler(conn);
			ShelfSearchKey slfKey = new ShelfSearchKey();
			slfKey.setStationNumber(ci.getDestStationNumber());
			Shelf toShelf = (Shelf) slfHandler.findPrimary(slfKey);

			//#CM46040
			//<en> Actual End Processing ---------------------------------</en>
			//#CM46041
			//<en> Release the reservation of Storage shelf, and turn on the stock. </en>
			ShelfOperator sop = new ShelfOperator(conn, toShelf.getStationNumber());
			sop.alterPresence(Shelf.PRESENCE_STORAGED);

			//#CM46042
			//<en> Delete Carry Information</en>
			wCIKey.setCarryKey(ci.getCarryKey());
			wCIHandler.drop(wCIKey);

			//#CM46043
			//<en> The present place of Palette is Changed in the destination.  </en>
			PaletteHandler palHandler = new PaletteHandler(conn);
			PaletteSearchKey palkey = new PaletteSearchKey();
			palkey.setPaletteId(ci.getPaletteId());
			Palette pl = (Palette) palHandler.findPrimary(palkey);
			alterPaletteCurrentStationNumber(conn, pl.getPaletteId(), toShelf.getStationNumber());
			//#CM46044
			//<en> Confirmed whether other Drawing to Palette exists. </en>
			wCIKey.KeyClear();
			wCIKey.setPaletteId(pl.getPaletteId()) ;
			if (wCIHandler.count(wCIKey) == 0)
			{
				//#CM46045
				//<en> Change the Abnormal shelf if Item code is Item code for double storage. </en>
				StockHandler stkHandler = new StockHandler(conn);
				StockSearchKey stkKey = new StockSearchKey();
				stkKey.setPaletteid(pl.getPaletteId());
				Stock[] stks = (Stock[]) stkHandler.find(stkKey);
				if (stks[0].getItemCode().equals(AsrsParam.IRREGULAR_ITEMKEY))
				{
					//#CM46046
					//<en> Change Status to the Abnormal shelf. </en>
					alterPaletteStatus(conn, pl.getPaletteId(), Palette.IRREGULAR);
				}
				else
				{
					//#CM46047
					//<en> Change Status to Real shelf.</en>
					alterPaletteStatus(conn, pl.getPaletteId(), Palette.REGULAR);
				}
				//#CM46048
				//<en> The Drawing flag is Change in unDrawing.  </en>
				alterPaletteAllocation(conn, pl.getPaletteId(), Palette.NOT_ALLOCATED);
			}
			else
			{
				//#CM46049
				//<en> Status is Change to the Picking reservation.  </en>
				alterPaletteStatus(conn, pl.getPaletteId(), Palette.RETRIEVAL_PLAN);
			}

			//#CM46050
			// When all the Stock confirmation data is done in End in Force Stop,  
			//#CM46051
			// the flag of Aisle and AsInventoryCheck turns it off. 
			CarryCompleteOperator co = new CarryCompleteOperator();
			co.setClassName(CLASS_NAME);
			co.updateInventoryCheckInfo(conn, ci);
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM46052
	/**<en>
	 * Do Processing of Force Stop(Arrival report) of Picking. 
	 * Retrieve < code>CarryInformation</code > of MC Key, and do the following Palette Arrival Processing. 
	 * Correspond Data retrieval, generate CarryInformation instance with the content, and update Mode of packing and present location. 
	 * Execute ArrivalProcessing of StationClass at the end, and do transportation data Update process corresponding to Station. 
	 * Moreover, this work of Storage End Processing to the following conditions when  
	 * Transportation origin is possible to store Station excluding Unit Picking. 
	 *   1.Present location is possible to store Station of Palette
	 *   2.Return Storage work of Partial Picking is automatically done on the AGC side. 
	 * @param conn Connection object with database
	 * @param ci Carry Information instance of Force Stop object
	 * @throws ScheduleException  It is notified when there is no object data. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 </en>*/
	private void completeRetrieval(Connection conn, CarryInformation ci)  throws ScheduleException, ReadWriteException
	{
		try
		{
			//#CM46053
			//<en> ArrivalStation is made. </en>
			Station st = StationFactory.makeStation(conn, ci.getDestStationNumber()) ;
			//#CM46054
			//<en> Do another Station Arrival Processing. </en>
			StationOperator sop = StationOperatorFactory.makeOperator(conn, st.getStationNumber(), st.getClassName());
			sop.arrival(ci, new Palette());
			//#CM46055
			//<en> Transportation origin is possible to store excluding Unit Picking this work.  When becoming Station, </en>
			//#CM46056
			//<en> Do the Storage End Processing while agreeing to the following conditions. </en>
			//#CM46057
			//<en> 1.The present location of Palette is possible to store.</en>
			//#CM46058
			//<en> 2.Return Storage work of Partial Picking is automatically done on the AGC side. </en>
			if (ci.getRetrievalDetail() != CarryInformation.RETRIEVALDETAIL_UNIT)
			{
				Station frst = StationFactory.makeStation(conn, ci.getSourceStationNumber());
				if ((frst.getStationType() == Station.STATION_TYPE_IN)
				  ||(frst.getStationType() == Station.STATION_TYPE_INOUT))
				{
					if (frst.getReStoringInstruction() == Station.AGC_STORAGE_SEND)
					{
						completeStorage(conn, ci);
					}
				}
			}
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM46059
	/**<en>
	 * Do Processing of Force Stop(Arrival report) of Going directly. 
	 * Retrieve < code>CarryInformation</code > of MC Key, and do the following Palette Arrival Processing. 
	 * Correspond Data retrieval, generate Carry Information instance with the content, and update present location. 
	 * Execute ArrivalProcessing of StationClass at the end, and do transportation data Update process corresponding to Station. 
	 * @param conn Connection object with database
	 * @param ci Carry Information instance of Force Stop object
	 * @throws ScheduleException  It is notified when there is no object data. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 </en>*/
	private void completeDirectTravel(Connection conn, CarryInformation ci)  throws ScheduleException, ReadWriteException
	{
		try
		{
			//#CM46060
			//<en> ArrivalStation is made. </en>
			Station st = StationFactory.makeStation(conn, ci.getDestStationNumber()) ;

			//#CM46061
			//<en> Present location is updated. </en>
			alterPaletteCurrentStationNumber(conn, ci.getPaletteId(), st.getStationNumber());

			//#CM46062
			//<en> Do another Station Arrival Processing. </en>
			StationOperator sop = StationOperatorFactory.makeOperator(conn, st.getStationNumber(), st.getClassName());
			sop.arrival(ci, new Palette());
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM46063
	/**
	 * Do the Force Stop Processing of Rack to rack movement.
	 * @param conn Connection object with database
	 * @param ci Force Stop object work data
	 * @throws ScheduleException  It is notified when there is no object data. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private void completeRacktoRack(Connection conn, CarryInformation ci) throws ScheduleException, ReadWriteException
	{
		try
		{
			//#CM46064
			// Definition of variables -------------------------------------
			CarryInformationHandler wCIHandler = new CarryInformationHandler(conn) ;
			CarryInformationSearchKey wCIKey = new CarryInformationSearchKey() ;

			//#CM46065
			// Start Processing
			//#CM46066
			// Acquisition of instance of the transportation destination Station.
			Shelf toShelf = (Shelf)StationFactory.makeStation(conn, ci.getDestStationNumber());
			//#CM46067
			// Acquisition of instance of the transportation origin Station.
			Shelf frmShelf = (Shelf)StationFactory.makeStation(conn, ci.getSourceStationNumber());

			//#CM46068
			// Actual End Processing
			//#CM46069
			// Release the reservation of the transportation destination shelf, and turn on the stock. 
			ShelfOperator sop = new ShelfOperator(conn, toShelf.getStationNumber());
			sop.alterPresence(Shelf.PRESENCE_STORAGED);

			//#CM46070
			// Release the reservation of the transportation origin shelf, and turn on the stock. 
			sop = new ShelfOperator(conn, frmShelf.getStationNumber());
			sop.alterPresence(Shelf.PRESENCE_EMPTY) ;

			//#CM46071
			// Delete Carry Information
			wCIKey.setCarryKey(ci.getCarryKey());
			wCIHandler.drop(wCIKey);
			//#CM46072
			// End Work Information
			WorkingInformationHandler wiHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey wiSKey = new WorkingInformationSearchKey();
			wiSKey.setSystemConnKey(ci.getCarryKey());
			if(wiHandler.count(wiSKey) > 0)
			{
				WorkingInformationAlterKey wiAKey = new WorkingInformationAlterKey();
				wiAKey.setSystemConnKey(ci.getCarryKey());
				wiAKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
				wiHandler.modify(wiAKey);
			}

			//#CM46073
			// The present place of Palette is Changed in the destination.  
			//#CM46074
			//<en> Present location is updated. </en>
			alterPaletteCurrentStationNumber(conn, ci.getPaletteId(), toShelf.getStationNumber());
			//#CM46075
			// Confirmed whether other Drawing to Palette exists. 
			wCIKey.KeyClear();
			wCIKey.setPaletteId(ci.getPaletteId()) ;
			if (wCIHandler.count(wCIKey) == 0)
			{
				//#CM46076
				// Change the Abnormal shelf if Item code is Item code for double storage. 
				StockHandler stkHandler = new StockHandler(conn);
				StockSearchKey stkKey = new StockSearchKey();
				stkKey.setPaletteid(ci.getPaletteId());
				Stock[] stks = (Stock[]) stkHandler.find(stkKey);
				if (stks[0].getItemCode().equals(AsrsParam.IRREGULAR_ITEMKEY))
				{
					//#CM46077
					// Change Status to the Abnormal shelf. 
					alterPaletteStatus(conn, ci.getPaletteId(), Palette.IRREGULAR);
				}
				else
				{
					//#CM46078
					// Change Status to Real shelf
					alterPaletteStatus(conn, ci.getPaletteId(), Palette.REGULAR);
				}
				//#CM46079
				// The Drawing flag is Change in unDrawing.  
				alterPaletteAllocation(conn, ci.getPaletteId(), Palette.NOT_ALLOCATED);
			}
			else
			{
				//#CM46080
				// Status is Change to the Picking reservation.  
				alterPaletteStatus(conn, ci.getPaletteId(), Palette.RETRIEVAL_PLAN);
			}
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM46081
	/**<en>
	 * Do Online and Offline check of System Status.
	 * @param conn Connection object with database
	 * @param ci Force Stop object work data
	 * @return true ONLINE
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the error not anticipated while checking parameter occurs. 
	 </en>*/
	private boolean checkStatus(Connection conn, CarryInformation ci) throws ReadWriteException, ScheduleException
	{
		try
		{
			PaletteHandler palHandler = new PaletteHandler(conn);
			PaletteSearchKey palkey = new PaletteSearchKey();
			palkey.setPaletteId(ci.getPaletteId());
			Palette pl = (Palette) palHandler.findPrimary(palkey);
			Station st = StationFactory.makeStation(conn, pl.getCurrentStationNumber());
			if (st instanceof Shelf)
			{
				st = StationFactory.makeStation(conn, st.getParentStationNumber());
			}
			GroupControllerHandler gcHandler = new GroupControllerHandler(conn);
			GroupControllerSearchKey gcKey = new GroupControllerSearchKey();
			gcKey.setControllerNumber(st.getControllerNumber());
			GroupController[] agc = (GroupController[])gcHandler.find(gcKey);
			if (agc[0].getStatus() == GroupController.STATUS_ONLINE)
			{
				return true;
			}
			return false;
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM46082
	/**<en>
	 * Do Change the request Flag that the cancellation request flag of AS/RS transportation information is specified. 
	 * @param conn Connection object with database
	 * @param carryKey Transportation Key
	 * @param req Request flag
	 * @throws InvalidStatusException Notify when the content of req is outside the range. 
	 * @throws InvalidDefineException Notify when there is No adjustment in the data update condition. 
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 * @throws NotFoundException      Notify when Carry Information to be renewed is not found. 
	 </en>*/
	private void alterCarryInfoCancelRequest(Connection conn, String carryKey, int req) throws
											InvalidStatusException,
											InvalidDefineException,
											ReadWriteException,
											NotFoundException
	{
		CarryInformationHandler caHandler = new CarryInformationHandler(conn);
		CarryInformationAlterKey cakey = new CarryInformationAlterKey();
		cakey.setCarryKey(carryKey);
		cakey.updateCancelRequest(req);
		cakey.updateCancelRequestDate(new Date());
		caHandler.modify(cakey);
	}

	//#CM46083
	/**<en>
	 * Change the Station for which Maintenance terminal No of AS/RS transportation information is specified. 
	 * @param conn Connection object with database
	 * @param carryKey Transportation Key
	 * @param no PickingStation
	 * @throws InvalidStatusException Notify when the content of req is outside the range. 
	 * @throws InvalidDefineException Notify when there is No adjustment in the data update condition. 
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 * @throws NotFoundException      Notify when Carry Information to be renewed is not found. 
	 </en>*/
	private void alterCarryInfoMaintenanceTerminal(Connection conn, String carryKey, String no) throws
											InvalidStatusException,
											InvalidDefineException,
											ReadWriteException,
											NotFoundException
	{
		CarryInformationHandler caHandler = new CarryInformationHandler(conn);
		CarryInformationAlterKey cakey = new CarryInformationAlterKey();
		cakey.setCarryKey(carryKey);
		cakey.updateMaintenanceTerminal(no);
		caHandler.modify(cakey);
	}

	//#CM46084
	/**<en>
	 * Change the Station No for which current Station No of AS/RSPalette information is specified. 
	 * @param conn Connection object with database
	 * @param pid  Palette ID
	 * @param stno CurrentStation No
	 * @throws InvalidDefineException Notify when there is No adjustment in the data update condition. 
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 * @throws NotFoundException      Notify when Palette to be renewed is not found. 
	 </en>*/
	private void alterPaletteCurrentStationNumber(Connection conn, int pid, String stno) throws
											InvalidDefineException,
											ReadWriteException,
											NotFoundException
	{
		PaletteHandler palHandler = new PaletteHandler(conn);
		PaletteAlterKey palkey = new PaletteAlterKey();
		palkey.setPaletteId(pid);
		palkey.updateCurrentStationNumber(stno);
		palkey.updateRefixDate(new Date());
		palHandler.modify(palkey);
	}

	//#CM46085
	/**<en>
	 * Change the Drawing qty for which Drawing qty of AS/RSPalette information is specified. 
	 * @param conn Connection object with database
	 * @param pid  Palette ID
	 * @param alloc Drawing qty
	 * @throws InvalidStatusException Notify when the content of alloc is outside the range. 
	 * @throws InvalidDefineException Notify when there is No adjustment in the data update condition. 
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 * @throws NotFoundException      Notify when Palette to be renewed is not found. 
	 </en>*/
	private void alterPaletteAllocation(Connection conn, int pid, int alloc) throws
											InvalidStatusException,
											InvalidDefineException,
											ReadWriteException,
											NotFoundException
	{
		PaletteHandler palHandler = new PaletteHandler(conn);
		PaletteAlterKey palkey = new PaletteAlterKey();
		palkey.updateAllocation(alloc);
		palkey.setPaletteId(pid);
		palHandler.modify(palkey);
	}

	//#CM46086
	/**<en>
	 * Change the Status for which Status of AS/RSPalette information is specified. 
	 * @param conn Connection object with database
	 * @param pid  Palette ID
	 * @param status Stock Status
	 * @throws InvalidDefineException Notify when there is No adjustment in the data update condition. 
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 * @throws NotFoundException      Notify when Palette to be renewed is not found. 
	 * @throws InvalidStatusException Notify when the content of status is outside the range. 
	 </en>*/
	private void alterPaletteStatus(Connection conn, int pid, int status) throws
											InvalidDefineException,
											ReadWriteException,
											NotFoundException,
											InvalidStatusException
	{
		PaletteHandler palHandler = new PaletteHandler(conn);
		PaletteAlterKey palkey = new PaletteAlterKey();
		palkey.updateStatus(status);
		palkey.updateRefixDate(new java.util.Date());
		palkey.setPaletteId(pid);
		palHandler.modify(palkey);
	}
}
//#CM46087
//end of class
