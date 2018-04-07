// $Id: StationCreater.java,v 1.2 2006/10/30 02:52:01 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM51412
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.wms.asrs.tool.common.LogHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.communication.as21.As21MachineState;
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAs21MachineSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAs21MachineStateHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationAlterKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolTerminalAreaHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolTerminalAreaSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.TerminalArea;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;

//#CM51413
/**<en>
 * This class operates the station settigs.
 * IT inherits the AbstractCreater and implements the processes required to set up stations.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:01 $
 * @author  $Author: suresh $
 </en>*/
public class StationCreater extends AbstractCreater
{
	//#CM51414
	// Class fields --------------------------------------------------
	//#CM51415
	// Class variables -----------------------------------------------
	//#CM51416
	/**<en>
	* <CODE>ToolStationSearchKey</CODE> instance
	 </en>*/
	protected ToolStationSearchKey wStationKey = null;
	//#CM51417
	/**<en>
	 * <CODE>ToolStationAlterKey</CODE> instance
	 </en>*/
	protected ToolStationAlterKey wStationAKey = null;
	
	private boolean TRUE = true;

	private boolean FALSE = false;

	//#CM51418
	/**<en> Class name (dedicated for storage)</en>*/

	public static String CLASS_STORAGE  = "jp.co.daifuku.wms.asrs.location.StorageStationOperator" ;
	
	//#CM51419
	/**<en> Class name (dedicated for retrieva)</en>*/

	public static String CLASS_RETRIEVAL  = "jp.co.daifuku.wms.asrs.location.RetrievalStationOperator" ;
	
	//#CM51420
	/**<en> Class name (P&D stand, powered cart)</en>*/

	public static String CLASS_INOUTSTATION  = "jp.co.daifuku.wms.asrs.location.InOutStationOperator" ;
	
	//#CM51421
	/**<en> Class name  (U-shaped storage) </en>*/

	public static String CLASS_FREESTORAGE  = "jp.co.daifuku.wms.asrs.location.FreeStorageStationOperator" ;
	
	//#CM51422
	/**<en> Class name  (U-shaped retrieval)</en>*/

	public static String CLASS_FREERETRIEVAL  = "jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator" ;
	
	//#CM51423
	/**<en> Type(dedicated for storage ) </en>*/

	public static final int TYPE_STORAGE  = 0;
	
	//#CM51424
	/**<en> Type(dedicated for retrieval ) </en>*/

	public static final int TYPE_RETRIEVAL  = 1;
	
	//#CM51425
	/**<en> Type(P&D stand, powered cart)</en>*/

	public static final int TYPE_INOUTSTATION  = 2;
	
	//#CM51426
	/**<en> Type(U-shaped storage) </en>*/

	public static final int TYPE_FREESTORAGE  = 3;
	
	//#CM51427
	/**<en> Type(U-shaped retrieval) </en>*/

	public static final int TYPE_FREERETRIEVAL  = 4;
	
	//#CM51428
	/**<en> Lod height </en>*/

	private int HEIGHT = 0;
	
	//#CM51429
	/**<en> PRoduct number folder </en>*/

	public String wFilePath = "";
	
	//#CM51430
	// Class method --------------------------------------------------
	//#CM51431
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:01 $") ;
	}

	//#CM51432
	// Constructors --------------------------------------------------
	//#CM51433
	/**<en>
	 * Delete the parameter of the specified position. <BR>
	 * @param index :position of the deleting parameter
	 * @throws ScheduleException :Notifies if there are no parameters in specified position.
	 </en>*/
	public void removeParameter(Connection conn, int index) throws ScheduleException
	{
		//#CM51434
		//<en> Initialization of the message</en>
		setMessage("");
		try
		{
			wParamVec.remove(index);
			setMessage("6121003");
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	//#CM51435
	/**<en>
	 * Delete all parameters.<BR>
	 * @throws ScheduleException :Notifies if there are no parameters in specified position.
	 </en>*/
	public void removeAllParameters(Connection conn) throws ScheduleException
	{
		//#CM51436
		//<en> Initialization of the message</en>
		setMessage("");

		wParamVec.removeAllElements();
		//#CM51437
		//<en>Deleted the data.</en>
		setMessage("6121003");
	}
	
	//#CM51438
	/**<en>
	 * Initialize this class. Generate the instance of <CODE>ReStoringHandler</CODE> at the initialization.
	 * @param conn :connection object with database
	 * @param kind :process type
	 </en>*/
	public StationCreater(Connection conn, int kind )
	{
		super(conn, kind);
	}

	//#CM51439
	// Public methods ------------------------------------------------
	//#CM51440
	/**<en>
	 * Implement the process to run when the print-issue button was pressed on the display.<BR>
	 * @param <code>Locale</code> object
	 * @param listParam :schedule parameter
	 * @return :result of print job
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.<BR>
	 </en>*/
	public boolean print(Connection conn, Locale locale, Parameter listParam) throws ReadWriteException, ScheduleException
	{
		return true;
	}
	
	//#CM51441
	/**<en>
	 * Retrieve data to isplay on the screen.<BR>
	 * @param <code>Locale</code> object
	 * @param searchParam :search conditions
	 * @return :the array of schedule parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.<BR>
	 </en>*/
	public Parameter[] query(Connection conn, Locale locale, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		Station[] array = getStationArray(conn);
		//#CM51442
		//<en>Vector where the data will temporarily be stored</en>
		//#CM51443
		//<en>Set the max number of data as an initial value for entered data summary.</en>
		Vector vec = new Vector(100);
		//#CM51444
		//<en>Entity class for display</en>
		StationParameter dispData = null;
		
		//#CM51445
		//<en>File name should be set here, as it cannot be provided from external in other methods.</en>
		//#CM51446
		//<en>CMENJP7853$CM set here..</en>
		wFilePath =  ((StationParameter)searchParam).getFilePath();
		
		if(array.length > 0)
		{
			for(int i = 0; i < array.length; i++)
			{
				//#CM51447
				//<en>Only display the stations of STATION table.</en>
				//#CM51448
				//<en>Division of sedable status - 1:sendable, workshop type - 0:unspecified</en>
				if(array[i].getSendable() == TRUE && array[i].getWorkPlaceType() == Station.NOT_WORKPLACE)
				{
					dispData = new StationParameter();
					//#CM51449
					//<en>warehouse station no.</en>
					dispData.setWareHouseStationNumber(array[i].getWarehouseStationNumber());
					//#CM51450
					//<en>station no.</en>
					dispData.setNumber(array[i].getNumber());								
					//#CM51451
					//<en>station name.</en>
					dispData.setName(array[i].getName());									
					//#CM51452
					//<en>type</en>
					if(array[i].getClassName().equals(CLASS_STORAGE))
					{
						//#CM51453
						//<en>dedicated for storage</en>
						dispData.setType(TYPE_STORAGE);
					}
					else if(array[i].getClassName().equals(CLASS_RETRIEVAL))
					{
						//#CM51454
						//<en>dedicated for retrieval</en>
						dispData.setType(TYPE_RETRIEVAL);
					}
					else if(array[i].getClassName().equals(CLASS_INOUTSTATION))
					{
						//#CM51455
						//<en>P&D stand, powered cart</en>
						dispData.setType(TYPE_INOUTSTATION);
					}
					else if(array[i].getClassName().equals(CLASS_FREESTORAGE))
					{
						//#CM51456
						//<en>U-shaped (storage side)</en>
						dispData.setType(TYPE_FREESTORAGE);
					}
					else if(array[i].getClassName().equals(CLASS_FREERETRIEVAL))
					{
						//#CM51457
						//<en>U-shaped (retrieval side)</en>
						dispData.setType(TYPE_FREERETRIEVAL);
					}
					else{
					}

					//#CM51458
					//<en>aisle station no.</en>
					dispData.setAisleStationNumber(array[i].getAisleStationNumber());		
					//#CM51459
					//AGCNo.
					dispData.setControllerNumber(array[i].getGroupController().getControllerNumber());	
					//#CM51460
					//<en>set type</en>
					dispData.setSettingType(array[i].getInSettingType());					
					if(array[i].getArrivalCheck() == Station.NOT_ARRIVALCHECK){
						//#CM51461
						//<en>arrival report (no check)</en>
						dispData.setArrivalCheck(Station.NOT_ARRIVALCHECK);					
					}else{
						//#CM51462
						//<en>arrival report (to be checked)</en>
						dispData.setArrivalCheck(Station.ARRIVALCHECK);						
					}
					if(array[i].getLoadSizeCheck() == Station.NOT_LOADSIZECHECK){
						//#CM51463
						//<en>load size check (no check)</en>
						dispData.setLoadSizeCheck(Station.NOT_LOADSIZECHECK);				
					}else{
						//#CM51464
						//<en>load size check (to be checked)</en>
						dispData.setLoadSizeCheck(Station.LOADSIZECHECK);					
					}
					//#CM51465
					//<en>on-line indication</en>
					dispData.setOperationDisplay(array[i].getOperationDisplay());			
					if(array[i].getReStoringOperation() == Station.NOT_CREATE_RESTORING){
						//#CM51466
						//<en>retrieval work (unavailable)</en>
						dispData.setReStoringOperation(Station.NOT_CREATE_RESTORING);		
					}else{
						//#CM51467
						//<en>retrieval work (available)</en>
						dispData.setReStoringOperation(Station.CREATE_RESTORING);			
					}
					
					//#CM51468
					//<en>carry instruction for restorage</en>
					dispData.setReStoringInstruction(array[i].getReStoringInstruction());	
					if( array[i].isRemove() == true )
					{
						//#CM51469
						//<en>removal (available)</en>
						dispData.setRemove( Station.PAYOUT_OK );							
					}
					else
					{
						//#CM51470
						//<en>removal (unavailable)</en>
						dispData.setRemove( Station.PAYOUT_NG );							
					}
					//#CM51471
					//<en>mode switch</en>
					dispData.setModeType(array[i].getModeType());							
					//#CM51472
					//<en>number of carry instruction sendable</en>
					dispData.setMaxInstruction(array[i].getMaxInstruction());				
					//#CM51473
					//<en>number of retrieval instruction sendable</en>
					dispData.setMaxPaletteQuantity(array[i].getMaxPaletteQuantity());		
					
					//#CM51474
					//<en>parent station no.</en>
					dispData.setParentNumber(array[i].getParentStationNumber());			

					vec.addElement(dispData);
				}
			}
			StationParameter[] fmt = new StationParameter[vec.size()];
			vec.toArray(fmt);
			return fmt;
		}
		return null;
	}

	//#CM51475
	/**<en>
	 * Processes the parameter check. It will be called when adding the parameter, before the 
	 * execution of maintenance process.
	 * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
	 * <number for the modificaiton><BR>
	 *  -should be less than the restorage load quantity.
	 *  -should be less than standard load quantity to stack.
	 * @param param :parameter to check
	 * @return :returns true if there is no error with parameter, or returns false if there are any errors.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
	 </en>*/
	public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		ToolCommonChecker check = new ToolCommonChecker(conn);
		StationParameter mParameter = (StationParameter)param;
		//#CM51476
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM51477
		//<en>Registration</en>
		switch(processingKind)
		{
			case M_CREATE:
				//#CM51478
				//<en> Check to see if the data is registered in WAREHOUSE table.</en>
				ToolWarehouseHandler whhandle = new ToolWarehouseHandler(conn);
				ToolWarehouseSearchKey whkey = new ToolWarehouseSearchKey();
				if (whhandle.count(whkey) <= 0)
				{
					//#CM51479
					//<en> The information of the warehouse cannot be found. </en>
					//<en> Please register in warehouse setting screen.</en>
					setMessage("6123100");
					return false;
				}
				
				//#CM51480
				//<en> Check to see if the data is registered in GROUPCONTROLLER table.</en>
				ToolGroupControllerHandler gchandle = new ToolGroupControllerHandler(conn);
				ToolGroupControllerSearchKey gckey = new ToolGroupControllerSearchKey();
				if (gchandle.count(gckey) <= 0)
				{
					//#CM51481
					//<en> The information of the group controller cannot be found. </en>
					//<en> Please register in group controller setting screen.</en>
					setMessage("6123078");
					return false;
				}
				
				//#CM51482
				//<en> It is checked in Bean whether/not the data is registered in AISLE table.</en>
				//#CM51483
				//<en> When P&D stand is the type, mode switch 'on' must be specified.</en>
				if(mParameter.getType() == TYPE_INOUTSTATION)
				{
					if(mParameter.getModeType() == Station.NO_MODE_CHANGE)
					{
						//#CM51484
						//<en> Please designate the mode switch for P&D stand/powered cart operation.</en>
						setMessage("6123210");
						return false;
					}
				}
				
				//#CM51485
				//<en> Max. number of carry instruction sendable must be entered if the type is 'dedicated</en>
				//<en> for storage', 'P6D stand' or 'U-shaped retrieval'.</en>
				if(mParameter.getType() == TYPE_STORAGE || mParameter.getType() == TYPE_FREESTORAGE || mParameter.getType() == TYPE_INOUTSTATION)
				{
					//#CM51486
					//<en> Check the entered number of carry instruction sendable.</en>
					if(mParameter.getMaxInstruction() <= 0)
					{
						//#CM51487
						//<en> Please specify 1 or greater value for the number of carry instruction sendable.</en>
						setMessage("6123094");
						return false;
					}
				}
				//#CM51488
				//<en> Set 0 for the number of carry instruction sendable if dedicated for retrieval and U-shaped retrieval.</en>
				else
				{
					mParameter.setMaxInstruction(0);
				}

				//#CM51489
				//<en> Max. number of retrieval instruction senable must be entered if the type is 'dedicated</en>
				//<en> for retrieval', 'P&D stand' or 'U-shaped retrieval'.</en>
				if(mParameter.getType() == TYPE_RETRIEVAL || mParameter.getType() == TYPE_FREERETRIEVAL || mParameter.getType() == TYPE_INOUTSTATION)
				{
					//#CM51490
					//<en> Check the entered number of retrieval instructions sendable.</en>
					if(mParameter.getMaxPaletteQuantity() <= 0)
					{
						//#CM51491
						//<en> Please specify 1 or greater value for the number of retrieval instruction sendable.</en>
						setMessage("6123095");
						return false;
					}
				}
				//#CM51492
				//<en> Set 0 for the number of retrieval instructions sendable if dedicated for storage and U-shaped storage.</en>
				else
				{
					mParameter.setMaxPaletteQuantity(0);
				}
				
				//#CM51493
				//<en>Check the name of the station.</en>
				if(!check.checkStationName(mParameter.getName()))
				{
					//#CM51494
					//<en>Set the contents of the error.</en>
					setMessage(check.getMessage());
					return false;
				}
				
				break;
			default:
				//#CM51495
				//<en> Unexpected value has been set.{0} = {1}</en>
				String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
				RmiMsgLogClient.write( msg, (String)this.getClass().getName());
				//#CM51496
				//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
				throw new ScheduleException("6126499");	
		}
		
		return true;
	}

	//#CM51497
	/**<en>
	 * Process the inconsistency check. This will be called when generating the eAWC environment setting tool.
	 * If any error takes place, the detail will be written in the file.
	 * @param logpath :path to place the file in which the log will be written when error occurred.
	 * @param locale <code>Locale</code>object
	 * @return :true if there is no error, or false if there are any error.
	 * @throws ScheduleException :Notifies if unexpected error occurred during the parameter duplicate check.
	 </en>*/
	public boolean consistencyCheck(Connection conn, String logpath, Locale locale) throws ScheduleException, ReadWriteException
	{
		ToolCommonChecker check = new ToolCommonChecker(conn);
		//#CM51498
		//<en>True if there is no error.</en>
		boolean errorFlag = true;
		String logfile = logpath +"/"+ ToolParam.getParam("CONSTRAINT_CHECK_FILE");
		
		try
		{
			LogHandler loghandle = new LogHandler(logfile, locale);
			
			ToolStationSearchKey wstationKey = new ToolStationSearchKey();
			wstationKey.setSendable(Station.SENDABLE);
			wstationKey.setWorkPlaceType(Station.NOT_WORKPLACE);
			Station[] wArray = (Station[])getToolStationHandler(conn).find(wstationKey);
			
			//#CM51499
			//<en>If the Station has not been set,</en>
			if(wArray.length == 0)
			{
				//#CM51500
				//<en>6123181 = The station has not been set.</en>
				loghandle.write("Station", "Station Table", "6123181");
				//#CM51501
				//<en>If the station has not been set, discontinue the checks and exit.</en>
				return false;
			}
		
			//#CM51502
			//<en>*** Check the group controller no. (Aisle table) ***</en>
			//#CM51503
			//<en>Check to see if the group controller no. in STATION table exists in group controller table.</en>
			for(int i = 0; i < wArray.length; i++)
			{
				int controller = wArray[i].getGroupController().getControllerNumber();
				if(controller != 0)
				{
					if(!check.isExistControllerNo(controller))
					{
						loghandle.write("Station", "Station Table",check.getMessage());
						errorFlag = false;
					}
				}
			}

			//#CM51504
			//<en>*** Check the warehouse station no. (Warehouse table) ***</en>
			//#CM51505
			//<en>Check to see if the warehouse station no. in STATION table exists in WAREHOUSE table.</en>
			for(int i = 0; i < wArray.length; i++)
			{
				String warehouseStationNo = wArray[i].getWarehouseStationNumber();
				if(wArray[i].getWorkPlaceType() != Station.WPTYPE_FLOOR 
				 && wArray[i].getWorkPlaceType() != Station.WPTYPE_ALL)
				{
					if(!check.isExistAutoWarehouseStationNo(warehouseStationNo))
					{
						loghandle.write("Station","Station Table",check.getMessage());
						errorFlag = false;
					}
				}
			}

			//*** クローズ倉庫ステーションの払出し区分チェック(Warehouse表) ***
			//#CM51506
			//Whether Station of close Warehouse and the one other than improper disbursement Flag exist are confirmed. 
			ToolWarehouseSearchKey warehouseKey = new ToolWarehouseSearchKey() ;
			ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(conn) ;
			Warehouse[] warehouseArray = (Warehouse[])warehousehandle.find(warehouseKey) ;
			for(int i = 0; i < warehouseArray.length; i++)
			{
				if (warehouseArray[i].getEmploymentType() == Warehouse.CLOSE)
				{
					for(int j = 0; j < wArray.length; j++)
					{
						if (wArray[j].getWarehouseStationNumber().equals(warehouseArray[i].getWarehouseStationNumber()))
						{
							if (wArray[j].isRemove() && wArray[j].getSendable())
							{
								//#CM51507
								//6123286=Disbursing belonging Station({0}) cannot select Flag close operation Warehouse. 
								loghandle.write("Station", "Station Table", "6123286" + wDelim + wArray[j].getNumber());
								errorFlag = false;
							}
						}
					}					
				}
			}

			//#CM51508
			//<en>*** Check the aisle station no. (Shelf table) ***</en>
			//#CM51509
			//<en>Check to see if the aisle station no. in STATION table exists in aisle table.</en>
			for(int i = 0; i < wArray.length; i++)
			{
				String aisleStationNo = wArray[i].getAisleStationNumber();
				if (aisleStationNo != null && aisleStationNo.length() > 0)
				{
					if(!check.isExistAisleStationNo(aisleStationNo))
					{
						loghandle.write("Station", "Station Table",check.getMessage());
						errorFlag = false;
					}
				}
			}
			
			//#CM51510
			//<en>*** Check the station no. (Station table) ***</en>
			//#CM51511
			//<en>Check to see if the station no. in STATION table exists in STATIONTYPE table.</en>
			for(int i = 0; i < wArray.length; i++)
			{
				String StationNo = wArray[i].getNumber();
				if(!check.isExistStationType(StationNo))
				{
					loghandle.write("Station","Station Table",check.getMessage());
					errorFlag = false;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
	
		}
		
		return errorFlag;
	}

	//#CM51512
	/**<en>
	 * Process the parameter duplicate check.
 	 * Data of identical item code and lot no. cannot be registered in entered data summary.
	 * It checks the duplication of parameter, then returns true if there was no duplicated data 
	 * or returns false if there were any duplication.
	 * If parameter duplicate check failed, its reason can be obtained by <code>getMessage</code>.
	 * @param param :parameter to check
	 * @return :returns true if the parameter duplicate check has succeeded, or returns false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
	 </en>*/
	public boolean duplicationCheck(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		Parameter[] mArray = (Parameter[])getParameters();
		StationParameter mParam = (StationParameter)param;
		//#CM51513
		//<en>Check the identical data.</en>
		if(isSameData(mParam, mArray))
		{
			return false;
		}
		//#CM51514
		//<en>station no.</en>
		String stationNo = mParam.getNumber();
		
		//#CM51515
		//<en> Check the station no. (any station no. in STATION table cannot be used)</en>
		if(isStationSameData(conn, mParam))
		{
			return false;
		}
		
		if(getToolStationHandler(conn).isStationType(stationNo, ToolStationHandler.STATION_HANDLE))
		{
			//#CM51516
			//<en>6123122 The data is already registered. Cannot register the identical station no.</en>
			setMessage("6123122");
			return false;
		}

		//#CM51517
		//<en>Check only when modifing data.</en>
		if(getUpdatingFlag() != ScheduleInterface.NO_UPDATING)
		{
			//#CM51518
			//<en>*** Key items are not to be modifiable when modifing data. ***</en>
			//#CM51519
			//<en>warehouse station no.</en>
			String warehouseStNo = mParam.getWareHouseStationNumber();
			//#CM51520
			//<en>type</en>
			int stType = mParam.getType();
			//#CM51521
			//<en>aisle station no.</en>
			String aisleNo = mParam.getAisleStationNumber();
			//#CM51522
			//AGCNo.
			int gcNo = mParam.getControllerNumber();
			
			//#CM51523
			//<en>Key items for enterd data summary</en>
			String orgwarehouseStNo = "";
			String orgstationNo = "";
			int orgType = 0;
			String orgAisleNo = "";
			int orgGcNo = 0;
			
			Parameter[] mAllArray = (Parameter[])getAllParameters();
			for (int i = 0 ; i < mAllArray.length ; i++)
			{
				StationParameter castparam = (StationParameter)mAllArray[i];
				//#CM51524
				//<en>Key items</en>
				orgwarehouseStNo = castparam.getWareHouseStationNumber();
				orgstationNo = castparam.getNumber();
				orgType = castparam.getType();
				orgAisleNo = castparam.getAisleStationNumber();
				orgGcNo = castparam.getControllerNumber(); 

				//#CM51525
				//<en>Acceptable as the data is not modified.</en>
				if(warehouseStNo.equals(orgwarehouseStNo) && 
					stationNo.equals(orgstationNo) &&
					stType == orgType &&
					aisleNo.equals(orgAisleNo) &&
					gcNo == orgGcNo)
				{
					return true;
				}
			}

			return true;		
		}
		
		return true;
	}

	//#CM51526
	/**<en>
	 * Conduct the maintenance process.
	 * It is necessary that type of the maintentace should be determined internally according to
	 * the process type (obtained by getProcessingKind() method.)
	 * It returns true if the maintenance process succeeded, or false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :returns true if the process succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	public boolean doStart(Connection conn) throws ReadWriteException, ScheduleException
	{
		//#CM51527
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM51528
		//<en>Registration</en>
		if(processingKind == M_CREATE)
		{
			if(!create(conn))
			{
				return false;
			}
			//#CM51529
			//<en>6121004 = Edited the data.</en>
			setMessage("6121004");
			return true;
		}
		//#CM51530
		//<en>Error with the process type.</en>
		else
		{
			//#CM51531
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM51532
			//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}

	//#CM51533
	// Package methods -----------------------------------------------

	//#CM51534
	// Protected methods ---------------------------------------------
	//#CM51535
	/**<en>
	 * Retrieve the <CODE>ReStoringHandler</CODE> instance generated at the initialization of this class.
	 * @return <CODE>ReStoringHandler</CODE>
	 </en>*/
	protected ToolStationHandler getToolStationHandler(Connection conn)
	{
		return new ToolStationHandler(conn);
	}
	
	//#CM51536
	/**<en>
	 * Retrieve the <CODE>FindUtil</CODE> instance generated at the initialization of this class.
	 * @return <CODE>FindUtil</CODE>
	 </en>*/
	protected ToolFindUtil getFindUtil(Connection conn)
	{
		return new ToolFindUtil(conn);
	}
	
	//#CM51537
	/**<en>
	 * Conduct the complementarity process of parameter.<BR>
	 * - Append ReStoring instance to the parameter in order to check whether/not the data 
	 *   has been modified by other terminals.
	 * It returns the complemented parameter if the process succeeded, or returns false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @param param :parameter which is used for the complementarity process
	 * @return :returns the parameter if the process succeeded, or returns null if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the process.
	 </en>*/
	protected  Parameter complementParameter(Parameter param)throws ReadWriteException, ScheduleException
	{
		return param;
	}
	
	//#CM51538
	/**<en>
	 * Process the maintenance modifications.
	 * Modification will be made based on the key items of parameter array. 
	 * Set the work no., item code and lot no. to the AlterKey as search conditions, and updates storage quantity.
	 * It returns true if the maintenance process succeeded, or false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :returns true if the process succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	protected boolean modify() throws ReadWriteException, ScheduleException
	{
		return true;
	}

	//#CM51539
	/**<en>
	 * Process the maintenance registrations. The scheduled restorage data is not registered 
	 * in this process.
	 * It returns true if the maintenance process succeeded, or false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :returns true if the process succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	protected boolean create(Connection conn) throws ReadWriteException, ScheduleException
	{
		try
		{
			Parameter[] mArray = (Parameter[])getAllParameters();
			StationParameter castparam = null;
			ToolStationHandler tsh = getToolStationHandler(conn);
			
			if( mArray.length > 0)
			{
				//#CM51540
				//<en>Delete all data from the table.</en>
				isDropStation(conn);

				//#CM51541
				//<en>***** Update teh STATION table. *****/</en>
				Station station = new Station();
				for(int i = 0; i < mArray.length; i++)
				{
					castparam = (StationParameter)mArray[i];
					//#CM51542
					//<en>warehouse station no.</en>
					station.setWarehouseStationNumber(castparam.getWareHouseStationNumber());
					//#CM51543
					//<en>station no.</en>
					station.setStationNumber(castparam.getNumber());						
					//#CM51544
					//<en>station name.</en>
					station.setName(castparam.getName());									
					//#CM51545
					//<en>Type, class name</en>
					switch(castparam.getType())
					{
						case TYPE_STORAGE:
							//#CM51546
							//<en>storage</en>
							station.setType(Station.STATION_TYPE_IN);
							//#CM51547
							//<en>dedicated for storage</en>
							station.setClassName(CLASS_STORAGE);
							break;
						case TYPE_RETRIEVAL:
							//#CM51548
							//<en>retrieval</en>
							station.setType(Station.STATION_TYPE_OUT);
							//#CM51549
							//<en>dedicated for retrieval</en>
							station.setClassName(CLASS_RETRIEVAL);
							break;
						case TYPE_INOUTSTATION:
							
							//#CM51550
							//<en>storage/retrieval available</en>
							station.setType(Station.STATION_TYPE_INOUT);
							//#CM51551
							//<en>P&D stand, powered cart</en>
							station.setClassName(CLASS_INOUTSTATION);
							break;
						case TYPE_FREESTORAGE:
							//#CM51552
							//<en>storage</en>
							station.setType(Station.STATION_TYPE_IN);
							//#CM51553
							//<en>U-shaped (storage side)</en>
							station.setClassName(CLASS_FREESTORAGE);
							break;
						case TYPE_FREERETRIEVAL:
							//#CM51554
							//<en>retrieval</en>
							station.setType(Station.STATION_TYPE_OUT);
							//#CM51555
							//<en>U-shaped (retrieval side)</en>
							station.setClassName(CLASS_FREERETRIEVAL);
							break;
						default:
							break;
					}
					
					//#CM51556
					//<en>aisle station no.</en>
					station.setAisleStationNumber(castparam.getAisleStationNumber());		
					
					//#CM51557
					//AGCNo
					GroupController gc = new GroupController(conn, castparam.getControllerNumber());	
					station.setGroupController(gc);
					
					//#CM51558
					//<en>set type</en>
					station.setInSettingType(castparam.getSettingType());					
					if(castparam.getArrivalCheck() == Station.NOT_ARRIVALCHECK){
						//#CM51559
						//<en>arrival report(no check)</en>
						station.setArrivalCheck(FALSE);										
					}else{
						//#CM51560
						//<en>arrival report(to be checked)</en>
						station.setArrivalCheck(TRUE);										
					}
					if(castparam.getLoadSizeCheck() == Station.NOT_LOADSIZECHECK){
						//#CM51561
						//<en>load size check(no check)</en>
						station.setLoadSizeCheck(FALSE);									
					}else{
						//#CM51562
						//<en>load size check(to be checked)</en>
						station.setLoadSizeCheck(TRUE);										
					}
					//#CM51563
					//<en>On-line indication</en>
					station.setOperationDisplay(castparam.getOperationDisplay());			
					if(castparam.getReStoringOperation() == Station.NOT_CREATE_RESTORING){
						//#CM51564
						//<en>retrieval work(unavailable)</en>
						station.setReStoringOperation(FALSE);								
					}else{
						//#CM51565
						//<en>retrieval work(available)</en>
						station.setReStoringOperation(TRUE);								
					}
					//#CM51566
					//<en>restorage carry instruction</en>
					station.setReStoringInstruction(castparam.getReStoringInstruction());	
					//#CM51567
					//<en>mode switch</en>
					station.setModeType(castparam.getModeType());							
					//#CM51568
					//<en>number of carry instruction sendable</en>
					station.setMaxInstruction(castparam.getMaxInstruction());				
					//#CM51569
					//<en>number of retrieval instruction sendable</en>
					station.setMaxPaletteQuantity(castparam.getMaxPaletteQuantity());		
					if(castparam.getRemove() == Station.PAYOUT_OK)
					{
						station.setRemove(true);
					}
					else
					{
						station.setRemove(false);
					}
					//#CM51570
					//<en>division of sendable status (1:sendable)</en>
					station.setSendable(TRUE);												
					//#CM51571
					//<en>status (0:off-line)</en>
					station.setStatus(Station.STATION_NG);									
					//#CM51572
					//<en>workshop type (0:undefined)</en>
					station.setWorkPlaceType(Station.NOT_WORKPLACE);						
					//#CM51573
					//<en>suspention flag (0:unavailable)</en>
					station.setSuspend(FALSE);												
					//#CM51574
					//<en>inventory check (0:unprocessed)</en>
					station.setInventoryCheckFlag(Station.NOT_INVENTORYCHECK);				
					//#CM51575
					//<en>pallet operation (0:not in operation)</en>
					station.setPoperationNeed(FALSE);										
					//#CM51576
					//<en>current work mode(0:neutral)</en>
					station.setCurrentMode(Station.NEUTRAL);								
					//#CM51577
					//<en>mode switch type(no request for mode switch)</en>
					station.setChangeModeRequest(Station.NO_REQUEST);						
					//#CM51578
					//<en>load height(0)</en>
					station.setHeight(HEIGHT);												
					//#CM51579
					//<en>parent station no.</en>
					station.setParentStationNumber(castparam.getParentNumber());			
					
					tsh.create(station);
				}
			}
			
			//#CM51580
			//<en>If there is no data to process,</en>
			else
			{
				//#CM51581
				//<en>Delete all data from the table.</en>
				isDropStation(conn);
			}
			return true;
		}
		catch(DataExistsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch(InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch(ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch(NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM51582
	/**<en>
	 * Process the maintenance deletions.
	 * Deletion will be done based on the key items of parameter array. 
	 * Set the process type of selected item to delete to 'processed'. The actual deletion will
	 * be done in daily transactions.
	 * It returns true if the maintenance process succeeded, or false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :returns true if the process succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	protected boolean delete() throws ReadWriteException, ScheduleException
	{
		return true;
	}

	//#CM51583
	// Private methods -----------------------------------------------
	//#CM51584
	/**<en>
	 * Check whether/not the data has already been modified by other terminals.
	 * Compares the ReStoring instance which has been set to maintenance parameter with the 
	 * other ReStoring instance which has been retrieved from current DB.
	 * If the comparison resulted the both instances are equal, it regards that no modification 
	 * was made by other terminals. If the comparison resulted these instances to be different, 
	 * then it regards the other terminal already modified the data.
	 * @param ReStoringMaintenanceParameter
	 * @return :returns true if the data ghas already been modified by other terminals, or returns 
	 * false if it has not been modified.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	private boolean isAlreadyChanged(StationParameter param) throws ReadWriteException
	{
		return false;
	}
	
	//#CM51585
	/**<en>
	 * Implement the check in order to see that the identical data has been selected when chosing data
	 * from the list box to edit.
	 * In the station setting, it checks whether/not the storage type of appending parameter exists 
	 * in the entered data summary.
	 * @param param :the parameter which will be appended in this process
	 * @param array :entered data summary (pool)
	 * @return      :return true if identical data exists.
	 </en>*/ 
	private boolean isSameData(StationParameter param, 
								Parameter[] array)
	{
		//#CM51586
		//<en>Key to compare</en>
		//#CM51587
		//<en>Value which is unused in normal processes</en> 
		String newStationNo = "99999"; 
		//#CM51588
		//<en>Value which is unused in normal processes</en> 
		String orgStationNo = "99999"; 
		
		//#CM51589
		//<en>If there is the entered data summary,</en>
		if(array.length > 0)
		{
			//#CM51590
			//<en>Compare by the storage type appended in this process.</en>
			newStationNo = param.getNumber();
			
			for (int i = 0 ; i < array.length ; i++)
			{
				StationParameter castparam = (StationParameter)array[i];
				//#CM51591
				//<en>Key for the entered data summary</en>
				orgStationNo = castparam.getNumber();
				//#CM51592
				//<en>Check the identical station no.</en>
				if(newStationNo.equals(orgStationNo))
				{
					//#CM51593
					//<en>6123016 = The data is already entered. Cannot input the identical station no.</en>
					setMessage("6123016");
					return true;
				}
			}
		}
		
		return false;
	}

	//#CM51594
	/**<en>
	 * Delete only the stations from STATION table. (Dummy stations, workshops and main stations 
	 * should not be deleted.)
	 * @return  :returns true if deletion succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private void isDropStation(Connection conn) throws ReadWriteException,NotFoundException
	{
		wStationKey = new ToolStationSearchKey();
		//#CM51595
		//<en>dicision of sendable status (1:sendable)</en>
		wStationKey.setSendable(Station.SENDABLE);	
		//#CM51596
		//<en>workshop type (0:unspecified)</en>
		wStationKey.setWorkPlaceType(Station.NOT_WORKPLACE);	
		Station[] array = (Station[])getToolStationHandler(conn).find(wStationKey);
		
		getToolStationHandler(conn).drop(wStationKey);		
	}
	
	//#CM51597
	/**<en>
	 * As the station no. is used in other screens, retrieve station no. other than stationss,
	 * then implements checks for data comparison.
	 * @param param  :the parameter which will be appended in this process
	 * @return       :return true if identical data exists.
	 </en>*/
	private boolean isStationSameData(Connection conn, StationParameter param) throws ReadWriteException
	{
		wStationKey = new ToolStationSearchKey();
		wStationKey.setNumber(param.getNumber());
		Station[] array = (Station[])getToolStationHandler(conn).find(wStationKey);

		for (int i = 0 ; i < array.length ; i++)
		{
			//#CM51598
			//<en> Unacceptable if identical station no. of workshop type :anything other than 0 and </en>
			//<en> sendable status: other than 1 exist.</en>
			if(array[i].getWorkPlaceType() != Station.NOT_WORKPLACE || array[i].getSendable() != TRUE)
			{
				//#CM51599
				//<en>6123016 = The data is already entered. Cannot input the identical station no.</en>
				setMessage("6123016");
				return true;
			}
		}
		
		return false;
	}
	
	//#CM51600
	/**<en>
	 * Determine whether/not the data is modifiable.
	 * Conditions to check when modifing the stations
     *  -data should not exist in carry route (route.txt).
     *  -data should not exist in machine information (MACHINE table).
     *  -data should not exist in terminal area (TERMINALAREA table).
     *  -the workshop should be unsest.
	 * @param <code>StationParameter</code>
	 * @return :returns true if the data is modifiable.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private boolean isChangeable(Connection conn, StationParameter param) throws ScheduleException
	{
		int reason = 0;
		reason = deletable(conn, param);
		String stationNo = param.getNumber();
		
		if(reason == 0)
		{
			return true;
		}
		else if(reason == 1)
		{
			//#CM51601
			//<en>Cannot delete the data; the Station ({0}) exists in RouteText.</en>
			setMessage("6123163" + wDelim + stationNo);
			return false;
		}
		else if(reason == 2)
		{
			//#CM51602
			//<en>Cannot delete the data; the Station ({0}) exists in MACHINE table.</en>
			setMessage("6123161" + wDelim + stationNo);
			return false;
		}
		else if(reason == 3)
		{
			//#CM51603
			//<en>Cannot delete the data; the Station ({0}) exists in TERMINALAREA table.</en>
			setMessage("6123162" + wDelim + stationNo);
			return false;
		}
		else if(reason == 4)
		{
			//#CM51604
			//<en>Cannot modify the stations which has been set in workshop.</en>
			setMessage("6123178" + wDelim + stationNo);
			return false;
		}
		else if(reason == 5)
		{
			//#CM51605
			//<en>Cannot delete the data; the station ({0}) exists in AREARANGE table.</en>
			setMessage("6123258" + wDelim + stationNo);
			return false;
		}
		
		return true;
	}
	
	//#CM51606
	/**<en>
	 * Determines whether/not the data is deletable.
	 * Conditions to check when deleting stations
     *  -The data should not exist in varry route(route.txt).
     *  -The data should not exist in machine information(MACHINE table).
     *  -The data should not exist in terminal area (TERMINALAREA table).
     *  -Workshop should be unset.
	 * @param <code>StationParameter</code>
	 * @return :return true if the data is deletable.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private boolean isDeletable(Connection conn, StationParameter param) throws ScheduleException
	{
		int reason = 0;
		reason = deletable(conn, param);
		String stationNo = param.getNumber();
		
		if(reason == 0)
		{
			return true;
		}
		else if(reason == 1)
		{
			//#CM51607
			//<en>Cannot delete the data; the station ({0}) exists in RouteText.</en>
			setMessage("6123163" + wDelim + stationNo);
			return false;
		}
		else if(reason == 2)
		{
			//#CM51608
			//<en>Cannot delete the data; the station ({0}) exists in MACHINE table.</en>
			setMessage("6123161" + wDelim + stationNo);
			return false;
		}
		else if(reason == 3)
		{
			//#CM51609
			//<en>Cannot delete the data; the station ({0}) exists in TERMINALAREA table.</en>
			setMessage("6123162" + wDelim + stationNo);
			return false;
		}
		else if(reason == 4)
		{
			//#CM51610
			//<en>Cannot modify the station which has been set as workshop.</en>
			setMessage("6123178" + wDelim + stationNo);
			return false;
		}
		else if(reason == 5)
		{
			//#CM51611
			//<en>Cannot delete the data; the station ({0}) exists in AREARANGE table.</en>
			setMessage("6123258" + wDelim + stationNo);
			return false;
		}
		
		return true;
	}
	
	//#CM51612
	//<en>Determine if deletion/modification of data is possible.</en>
	//<en>Deletion/modification of data can be processed if the return value is 0.</en>
	//<en>For all other case, appropriate messages will be set respectively by the call resource.</en>
	//#CM51613
	//<en>CMENJP8011$CM When the return value is 0, Deletion and the correction become possible. </en>
	//#CM51614
	//<en>Set the message which corresponds respectively </en>
	//#CM51615
	//<en>besides on the call side.</en>
private int deletable(Connection conn, StationParameter param) throws ScheduleException
	{
		try
		{	
			String stationNo = param.getNumber();
			String filepath = param.getFilePath();

			//#CM51616
			//<en>Cannot delete the data if it exists in Route.txt.</en>
			ToolCommonChecker commChecker = new ToolCommonChecker(conn);
			if(commChecker.isExistStationNo_RouteText(wFilePath, stationNo))
			{
				return 1;
			}
						
			ToolAs21MachineSearchKey machineKey = new ToolAs21MachineSearchKey();
			ToolAs21MachineStateHandler machineHandle = new ToolAs21MachineStateHandler(conn);
			machineKey.setStationNumber(stationNo);
			As21MachineState[] machineArray = (As21MachineState[])machineHandle.find(machineKey);
			//#CM51617
			//<en>Cannot delete the data if it exists in Machine table.</en>
			if(machineArray.length > 0)
			{
				return 2;
			}
			
			ToolTerminalAreaSearchKey terminalKey = new ToolTerminalAreaSearchKey();
			ToolTerminalAreaHandler terminalHandle = new ToolTerminalAreaHandler(conn);
			terminalKey.setStationNumber(stationNo);
			TerminalArea[] terminalArray = (TerminalArea[])terminalHandle.find(terminalKey);
			//#CM51618
			//<en>Cannot delete the data if it exists in Terminalarea table.</en>
			if(terminalArray.length > 0)
			{
				return 3;
			}
			
			ToolStationSearchKey stationKey = new ToolStationSearchKey();
			ToolStationHandler stationHandle = new ToolStationHandler(conn);
			stationKey.setNumber(stationNo);
			Station[] stationArray = (Station[])stationHandle.find(stationKey);
			//#CM51619
			//<en>Cannot delete the station if it set as workshop.</en>
			if(stationArray.length > 0)
			{
				if(!stationArray[0].getParentStationNumber().equals(""))
				{
					return 4;
				}
			}

			return 0;
		}
		catch(ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM51620
	/**<en>
	 * Retrieve the Station instance.
	 * @return :the array of <code>Station</code> object
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private Station[] getStationArray(Connection conn) throws ReadWriteException
	{
		ToolStationSearchKey stationKey  = new ToolStationSearchKey();
		stationKey.setNumberOrder(1,true);
		
		//#CM51621
		//<en>*** Retrieve the Station isntance ***</en>
		Station[] array = (Station[])getToolStationHandler(conn).find(stationKey);
		return array;
	}
}
//#CM51622
//end of class

