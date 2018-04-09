// $Id: DummyStationCreater.java,v 1.2 2006/10/30 02:52:05 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM50517
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
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
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationAlterKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Station;

//#CM50518
/**<en>
 * This class sets up the dummy station.
 * It inherits the AbstractCreater and implements the processes required in setting stations.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:05 $
 * @author  $Author: suresh $
 </en>*/
public class DummyStationCreater extends AbstractCreater
{
	//#CM50519
	// Class fields --------------------------------------------------

	//#CM50520
	// Class variables -----------------------------------------------
	//#CM50521
	/**<en>
	* <CODE>ToolStationSearchKey</CODE> instance
	 </en>*/
	protected ToolStationSearchKey wStationKey = null;
	//#CM50522
	/**<en>
	 * <CODE>ToolStationAlterKey</CODE> instance
	 </en>*/
	protected ToolStationAlterKey wStationAKey = null;
	
	private boolean TRUE = true;

	private boolean FALSE = false;

	//#CM50523
	/**<en> Class name (dedicated for storage)</en>*/

	public static String CLASS_STORAGE  = "jp.co.daifuku.wms.asrs.location.StorageStationOperator" ;
	
	//#CM50524
	/**<en> Class name (dedicated for retrieva)</en>*/

	public static String CLASS_RETRIEVAL  = "jp.co.daifuku.wms.asrs.location.RetrievalStationOperator" ;
	
	//#CM50525
	/**<en> Class name (P&D stand, powered cart)</en>*/

	public static String CLASS_INOUTSTATION  = "jp.co.daifuku.wms.asrs.location.InOutStationOperator" ;
	
	//#CM50526
	/**<en> Class name  (U-shaped storage) </en>*/

	public static String CLASS_FREESTORAGE  = "jp.co.daifuku.wms.asrs.location.FreeStorageStationOperator" ;
	
	//#CM50527
	/**<en> Class name  (U-shaped retrieval)</en>*/

	public static String CLASS_FREERETRIEVAL  = "jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator" ;
	
	//#CM50528
	/**<en> load height </en>*/

	private int HEIGHT = 0;
	
	//#CM50529
	/**<en> product number folder </en>*/

	public String wFilePath = "";
	
	//#CM50530
	// Class method --------------------------------------------------
	//#CM50531
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:05 $") ;
	}

	//#CM50532
	// Constructors --------------------------------------------------
	//#CM50533
	/**<en>
	 * Delete the parameter of the specified position.<BR>
	 * @param index :position of the parameter to be deleted
	 * @throws ScheduleException :Notifies if there are no parameters in specified position.
	 </en>*/
	public void removeParameter(Connection conn, int index) throws ScheduleException
	{
		//#CM50534
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
	//#CM50535
	/**<en>
	 * Delete all the parameters.<BR>
	 * @throws ScheduleException :Notifies if there are no parameters in specified position.
	 </en>*/
	public void removeAllParameters(Connection conn) throws ScheduleException
	{
		//#CM50536
		//<en> Initialization of the message</en>
		setMessage("");

		wParamVec.removeAllElements();
		//#CM50537
		//<en>Deleted the data.</en>
		setMessage("6121003");
	}
	//#CM50538
	/**<en>
	 * Initialize this class. Generate the instance of <CODE>ToolStationHandler</CODE> at the initialization.
	 * @param conn :connection object with database
	 * @param kind :process type
	 </en>*/
	public DummyStationCreater(Connection conn, int kind )
	{
		super(conn, kind);
	}

	//#CM50539
	// Public methods ------------------------------------------------
	//#CM50540
	/**<en>
	 * Implement the process to run when the print-issue button was pressed on the display.<BR>
	 * @param <code>Locale</code> object
	 * @param listParam :schedule parameter
	 * @return :result of print job
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.<BR>
	 </en>*/
	public boolean print(Locale locale, Parameter listParam) throws ReadWriteException, ScheduleException
	{
		return true;
	}

	//#CM50541
	/**<en>
	 * Retrieve data to isplay on the screen.<BR>
	 * @param conn :Connection to connet with database
	 * @param locale <code>Locale</code> object
	 * @param searchParam :search conditions
	 * @return :the array of schedule parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.<BR>
	 </en>*/
	public Parameter[] query(Connection conn, Locale locale, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		Station[] array = getDummyStationArray(conn);
		//#CM50542
		//<en>Vector where the data will temporarily be stored</en>
		//#CM50543
		//<en>Set the max number of data as an initial value for entered data summary.</en>Vector vec = new Vector(100);	
		Vector vec = new Vector(100);	
		
		//#CM50544
		//<en>The file name should be set here, as it cannot be provided from external </en>
		//#CM50545
		//<en>by other methods.</en>
		wFilePath =  ((StationParameter)searchParam).getFilePath();

		if(array.length > 0)
		{
			for(int i = 0; i < array.length; i++)
			{
				//#CM50546
				//<en>Entity class for display</en>
				StationParameter dispData = new StationParameter() ;
				//#CM50547
				//<en>warehouse station no.</en>
				dispData.setWareHouseStationNumber(array[i].getWarehouseStationNumber());	
				//#CM50548
				//<en>station no.</en>
				dispData.setNumber(array[i].getNumber());									
				//#CM50549
				//<en>station name</en>
				dispData.setName(array[i].getName());										
				//#CM50550
				//AGC No.
				dispData.setControllerNumber(array[i].getGroupController().getControllerNumber()) ; 

				vec.addElement(dispData);
			}
			StationParameter[] fmt = new StationParameter[vec.size()];
			vec.toArray(fmt);
			return fmt;
		}
		return null;
	}

	//#CM50551
	/**<en>
	 * Processes the paramter check. It will be called when adding the parameter, before the 
	 * execution of maintenance process.
	 * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
	 * <storage type><BR>
	 *  whether/not the data exists in WAREHOUSE table.
	 * <AGC No.><BR>
	 *  whether/not the data exists in GROUPCONTROLLER table.<BR>
	 * <station no.><BR>
	 *  whether/not the unacceptable letters and symbols are used.<BR>
	 * <station name><BR>
	 *  whether/not the unacceptable letters and symbols are used.<BR>
	 * @param conn  :connection to connect with database
	 * @param param :parameter to check
	 * @return :returns true if there is no error with parameter, or returns false if there are any errors.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
	 </en>*/
	public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		ToolCommonChecker check = new ToolCommonChecker(conn);
		StationParameter mParameter = (StationParameter)param;
		//#CM50552
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM50553
		//<en>Registration</en>
		switch(processingKind)
		{
			case M_CREATE:
				//#CM50554
				//<en> Check to see if the data is registered in the warehouse table.</en>
				ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(conn);
				ToolWarehouseSearchKey warehosuekey = new ToolWarehouseSearchKey();
	
				if (warehousehandle.count(warehosuekey) <= 0)
				{
					//#CM50555
					//<en> 6123117 = There is no warehous control data. Please register in the setting </en>
					//<en> display of warehouses.</en>
					setMessage("6123117");
					return false;
				}
				//#CM50556
				//<en> Check to see of the data is registered in GROUPCONTROLLER table.</en>
				ToolGroupControllerHandler gchandle = new ToolGroupControllerHandler(conn);
				ToolGroupControllerSearchKey gckey = new ToolGroupControllerSearchKey();
	
				if (gchandle.count(gckey) <= 0)
				{
					//#CM50557
					//<en> There is no group controller data. Please register in the setting diplay of </en>
					//<en> group controller.</en>
					setMessage("6123078");
					return false;
				}

				//#CM50558
				//<en>Check the station no. (unacceptable letters and symbols)</en>
				if(!check.checkStationNumber(mParameter.getNumber()))
				{
					//#CM50559
					//<en>Set the contents of error.</en>
					setMessage(check.getMessage());
					return false;
				}

				//#CM50560
				//<en>Check the station name. (unacceptable letters and symbols)</en>
				if(!check.checkStationName(mParameter.getName()))
				{
					//#CM50561
					//<en>Set the contents of error.</en>
					setMessage(check.getMessage());
					return false;
				}
				break ;
				
			default :
				//#CM50562
				//<en> Unexpected value has been set.{0} = {1}</en>
				String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
				RmiMsgLogClient.write( msg, (String)this.getClass().getName());
				//#CM50563
				//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
				throw new ScheduleException("6126499") ;	
		}
		
		return true ;
	}

	//#CM50564
	/**<en>
	 * Process the inconsistency check. This will be called when generating the eAWC environment setting tool.
	 * If any error takes place, the detail will be written in the file which is specified by filename.
	 * @param conn    :Connection object to connect with database
	 * @param logpath :Path to place teh file the log will be written in when error occurred.
	 * @param locale <code>Locale</code> object
	 * @return :true if there is no error, or false if there are any error.
	 * @throws ScheduleException :Notifies if unexpected error occurred during the parameter duplicate check.
	 </en>*/
	public boolean consistencyCheck(Connection conn, String logpath, Locale locale) throws ScheduleException, ReadWriteException
	{
		ToolCommonChecker check = new ToolCommonChecker(conn);
		//#CM50565
		//<en>True if there is no error.</en>
		boolean errorFlag = true;
		String logfile = logpath +"/"+ ToolParam.getParam("CONSTRAINT_CHECK_FILE");
		
		try
		{
			LogHandler loghandle = new LogHandler(logfile, locale);
			
			ToolStationSearchKey wstationKey = new ToolStationSearchKey();
			Station[] wArray = (Station[])getToolStationHandler(conn).find(wstationKey);
			//#CM50566
			//<en>*** If Station has not been set ***</en>
			if(wArray.length == 0)
			{
				//#CM50567
				//<en>6123181 = Station has not been set.</en>
				loghandle.write("DummyStation", "Station Table", "6123181");
				//#CM50568
				//<en>If the station has not been set, discontinue the check and exit.</en>
				return false;
			}

			ToolStationSearchKey stationKey = new ToolStationSearchKey();
			stationKey.setStationType(Station.STATION_TYPE_OTHER);
			stationKey.setSendable(Station.NOT_SENDABLE);
			Station[] stArray = (Station[])getToolStationHandler(conn).find(stationKey);
			
			//#CM50569
			//<en>*** Check the storage type (Warehouse table) ***</en>
			//#CM50570
			//<en>Check if the warehouse station no. of the STATION table exists in WAREHOUSE table.</en>
			for(int i = 0; i < stArray.length; i++)
			{
				String warehouseStationNo = stArray[i].getWarehouseStationNumber();
				if(!check.isExistAutoWarehouseStationNo(warehouseStationNo))
				{
					loghandle.write("DummyStation","Station Table",check.getMessage());
					errorFlag = false;
				}
			}
			//#CM50571
			//<en>*** Check the station no. (Station table) ***</en>
			//#CM50572
			//<en>Check if the station no. of the STATION table exists in STATIONTYPE table.</en>
			for(int i = 0; i < stArray.length; i++)
			{
				String StationNo = stArray[i].getNumber();
				if(!check.isExistStationType(StationNo))
				{
					loghandle.write("DummyStation","Station Table",check.getMessage());
					errorFlag = false;
				}
			}
			//#CM50573
			//<en>*** Check the AGC No. (GroupController table) ***</en>
			//#CM50574
			//<en>Check if AGC no. of the STATION table exists in GROUPCONTROLLER table.</en>
			for(int i = 0; i < stArray.length; i++)
			{
				int AgcNo = stArray[i].getGroupController().getControllerNumber();
				if(!check.isExistControllerNo(AgcNo))
				{
					loghandle.write("DummyStation","GroupController Table",check.getMessage());
					errorFlag = false;
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
	
		}
		return errorFlag;
	}

	//#CM50575
	/**<en>
	 * Process the parameter duplicate check.<BR>
	 * Identical station no. cannot be registered. 
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

		//#CM50576
		//<en>Chec if the identical data exists in STATIONTYPE table.</en>
		String stationNo = mParam.getNumber();
		if(getToolStationHandler(conn).isStationType(stationNo, ToolStationHandler.STATION_HANDLE))
		{
			//#CM50577
			//<en>6123122 The data is already registered. Cannot register the identical station no.</en>
			setMessage("6123122");
			return false;
		}

		//#CM50578
		//<en>Check if the identical data exists in STATION table.</en>
		if(isStationSameData(conn, mParam))
		{
			return false;
		}

		//#CM50579
		//<en>Check the identical data(entered data summary).</en>
		if(isSameData(mParam, mArray))
		{
			return false;
		}

		//#CM50580
		//<en>Check only when modifing data.</en>
		if(getUpdatingFlag() != ScheduleInterface.NO_UPDATING)
		{
			//#CM50581
			//<en>*** Key items are regarder not modifiable when modifing data. ***</en>
			//#CM50582
			//<en> warehouse station no.</en>
			String warehouseStNo = mParam.getWareHouseStationNumber();		
			//#CM50583
			//<en> station no.</en>
			String stNo = mParam.getNumber();								
			//#CM50584
			//<en>Key items for enterd data summary</en>
			String orgwarehouseStNo = "";
			String orgstationNo = "";
			//#CM50585
			//<en>Parameter which is modified</en>
			StationParameter mparam = (StationParameter)getUpdatingParameter();
			
			Parameter[] mAllArray = (Parameter[])getAllParameters();
			for (int i = 0 ; i < mAllArray.length ; i++)
			{
				StationParameter castparam = (StationParameter)mAllArray[i];
				//#CM50586
				//<en>Key items</en>
				orgwarehouseStNo = castparam.getWareHouseStationNumber();
				orgstationNo = castparam.getNumber();
				
				if(warehouseStNo.equals(orgwarehouseStNo) && 
					stNo.equals(orgstationNo))
				{
					return true;
				}
			}

			return true;
		}
		return true;
	}

	//#CM50587
	/**<en>
	 * Conduct the maintenance process.
	 * It is necessary that type of the maintentace should be determined internally according to
	 * the process type (obtained by getProcessingKind() method.)
	 * It returns true if the maintenance process succeeded, or false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @param conn :connection object to connect with database
	 * @return :returns true if the process succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	public boolean doStart(Connection conn) throws ReadWriteException, ScheduleException
	{
		//#CM50588
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM50589
		//<en>Registration</en>
		if(processingKind == M_CREATE)
		{
			if(!create(conn))
			{
				return false;
			}
			//#CM50590
			//<en>6121004 = Edited the data.</en>
			setMessage("6121004");
			return true;
		}
		//#CM50591
		//<en>Error with the process type.</en>
		else
		{
			//#CM50592
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM50593
			//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}

	//#CM50594
	// Package methods -----------------------------------------------

	//#CM50595
	// Protected methods ---------------------------------------------
	//#CM50596
	/**<en>
	 * Retrieve the <CODE>ToolStationHandler</CODE> instance generated at the initialization of this class.
	 * @return <CODE>ToolStationHandler</CODE>
	 </en>*/
	protected ToolStationHandler getToolStationHandler(Connection conn)
	{
		return new ToolStationHandler(conn);
	}
	
	//#CM50597
	/**<en>
	 * Retrieve the <CODE>FindUtil</CODE> instance generated at the initialization of this class.
	 * @return <CODE>FindUtil</CODE>
	 </en>*/
	protected ToolFindUtil getFindUtil(Connection conn)
	{
		return new ToolFindUtil(conn);
	}
	
	//#CM50598
	/**<en>
	 * Conduct the complementarity process of parameter.<BR>
	 * - Append StationParameter instance to the parameter in ordder to check whether/not the data 
	 *   has been modified by other terminals.
	 * It returns the complemented parameter if the process succeeded, or returns false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @param param :parameter which is used for the complementarity process
	 * @return :returns the parameter if the process succeeded, ro returns null if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the process.
	 </en>*/
	protected  Parameter complementParameter(Parameter param)throws ReadWriteException, ScheduleException
	{
		return param;
	}
	
	//#CM50599
	/**<en>
	 * Process the maintenance modifications.
	 * Modification will be made based on the key items of parameter array. 
	 * Sets the work no., item code and lot no. to the AlterKey as search conditions, and updates storage quantity.
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

	//#CM50600
	/**<en>
	 * Process the maintenance registrations. The scheduled restoring data will not be registered.
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
			String space = " ";

			if( mArray.length > 0)
			{
				//#CM50601
				//<en>Delete all data from the table.</en>
				isDropDummyStation(conn);

				//#CM50602
				//<en>***** Update process of STATION table *****/</en>
				Station station = new Station();
				for(int i = 0; i < mArray.length; i++)
				{
					castparam = (StationParameter)mArray[i];

					//#CM50603
					//<en>the entered data via screen (station no.)</en>
					//#CM50604
					//<en>station no.</en>
					station.setStationNumber(castparam.getNumber());						
					//#CM50605
					//<en>number of carry instruction sendable(0)</en>
					station.setMaxInstruction(0);											
					//#CM50606
					//<en>number of retrieva instruction sendable(0)</en>
					station.setMaxPaletteQuantity(0);										
					//#CM50607
					//<en>division of sendability(0:not sendable)</en>
					station.setSendable(FALSE);												
					//#CM50608
					//<en>status (0:off-line)</en>
					station.setStatus(Station.STATION_NG);									
					//#CM50609
					//<en>the entered data via screen(AGC No.)</en>
					//#CM50610
					//AGCNo(0)
					GroupController gc = new GroupController(conn, castparam.getControllerNumber() );						
					//#CM50611
					//<en>AGC No. entered via screen</en>
					station.setGroupController(gc);											
					//#CM50612
					//<en>station type (0:other)</en>
					station.setType(Station.STATION_TYPE_OTHER);							
					//#CM50613
					//<en>setting type(0)</en>
					station.setInSettingType(0);											
					//#CM50614
					//<en>workshop type(0:unspecified)</en>
					station.setWorkPlaceType(Station.NOT_WORKPLACE);						
					//#CM50615
					//<en>on-line indication(0:no displat)</en>
					station.setOperationDisplay(Station.NOT_OPERATIONDISPLAY);				
					//#CM50616
					//<en>the entered data via screen (station name)</en>
					//#CM50617
					//<en>station name</en>
					station.setName(castparam.getName());									
					//#CM50618
					//<en>suspention flag (0:available)</en>
					station.setSuspend(FALSE);												
					//#CM50619
					//<en>arrival report(no check)</en>
					station.setArrivalCheck(FALSE);											
					//#CM50620
					//<en>load size checker (no check)</en>
					station.setLoadSizeCheck(FALSE);										
					//#CM50621
					//<en>removal (0:available)</en>
					station.setRemove(TRUE);												
					//#CM50622
					//<en>inventory checking (0:unprocessed)</en>
					station.setInventoryCheckFlag(Station.NOT_INVENTORYCHECK);				
					//#CM50623
					//<en>restorage work (none)</en>
					station.setReStoringOperation(FALSE);									
					//#CM50624
					//<en>requriements for carry instruction</en>
					//<en>for restorage (0:not necessary)</en>
					station.setReStoringInstruction(0);										
					//#CM50625
					//<en>pallet operation (0:unused)</en>
					station.setPoperationNeed(FALSE);										
					//#CM50626
					//<en>the entered data via screen (warehouse station no. retrieved from the storage type)</en>
					//#CM50627
					//<en>warehouse station no.</en>
					station.setWarehouseStationNumber(castparam.getWareHouseStationNumber());
					//#CM50628
					//<en>parent station no.(space)</en>
					station.setParentStationNumber("");										
					//#CM50629
					//<en>aisle station no. (space)</en>
					station.setAisleStationNumber("");										
					//#CM50630
					//<en>mode switch (no seitch)</en>
					station.setModeType(Station.NO_MODE_CHANGE);							
					//#CM50631
					//<en>current work mode(0:neutral)</en>
					station.setCurrentMode(Station.NEUTRAL);								
					//#CM50632
					//<en>request of mode switch(no request)</en>
					station.setChangeModeRequest(Station.NO_REQUEST);						
					//#CM50633
					//<en>load height(0)</en>
					station.setHeight(HEIGHT);												
					//#CM50634
					//<en>class name (space)</en>
					station.setClassName("");												

					getToolStationHandler(conn).create(station);
				}
				return true;
			}
			//#CM50635
			//<en>If there is no data to process,</en>
			else
			{
				//#CM50636
				//<en>Delete all data from the table.</en>
				isDropDummyStation(conn);
				return true;
			}
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

	//#CM50637
	/**<en>
	 * Process the maintenance deletions.
	 * Deletion will be done based on the key items of parameter array. 
	 * Set the process type of selected item to delete to 'processed'. The acrual 
	 * deletion will be done in daily transactions.
	 * It returns true if the maintenance process succeeded, or false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :returns true if the process succeeded, or returns false if it failed. 
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	protected boolean delete() throws ReadWriteException, ScheduleException
	{
		return true;
	}

	//#CM50638
	// Private methods -----------------------------------------------
	//#CM50639
	/**<en>
	 * Check whether/not thd data has already been modified by other terminals.
	 * Compares the Station instance which has been set to maintenance parameter with the 
	 * other Station instance retrieved from current DB.
	 * If the comparison resulted the both instances are equal, it regards that no modification was 
	 * made by other terminals. If the comparison resulted these instances to be different, then it 
	 * regards the other terminal already modified the data.
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
	
	//#CM50640
	/**<en>
	 * Implement the check in order to see that the identical data has been selected when chosing data
	 * to edit from the listbox. 
	 * In the station setting, it checks whether/not the storage type of appending parameter exists in 
	 * the entered data summary.
	 * @param param :parameter which wil be appended in this process
	 * @param array :entered data summary
	 * @return      :return true if identical data exists.
	 </en>*/
	private boolean isSameData(StationParameter param, 
								Parameter[] array)
	{
		//#CM50641
		//<en>Key to compare</en>
		//#CM50642
		//<en>the value which normally will not be used</en>
		String newStationNo = "99999"; 
		//#CM50643
		//<en>the value which normally will not be used</en>
		String orgStationNo = "99999"; 
		
		//#CM50644
		//<en>If there is the entered data summary,</en>
		if(array.length > 0)
		{
			//#CM50645
			//<en>Compare by the station no. appended in this process.</en>
			newStationNo = param.getNumber();
			
			for (int i = 0 ; i < array.length ; i++)
			{
				StationParameter castparam = (StationParameter)array[i];
				//#CM50646
				//<en>Key for the entered data summary</en>
				orgStationNo = castparam.getNumber();
				//#CM50647
				//<en>Check the identical station no.</en>
				if(newStationNo.equals(orgStationNo))
				{
					//#CM50648
					//<en>6123016 = The data is already enterd. Cannot input the identical station numbers.</en>
					setMessage("6123016");
					return true;
				}
			}
		}
		return false;
	}

	//#CM50649
	/**<en>
	 * Check whether/not the identical station no. is registered in STATION table.
	 * Return true if it is registered, or false if not.
	 * Station type other than '0' should be the targeted, as station type 0 may cause incorrect process
	 * e.g., it may determine that own station no. cannot be entered when modifing data, etc.
	 * @param param :the parameter which will be appended in this process
	 * @return      true:Identical data is found, false:There is no identical data.
	 </en>*/
	private boolean isStationSameData(Connection conn, StationParameter param) throws ReadWriteException
	{
		wStationKey = new ToolStationSearchKey();
		wStationKey.setNumber(param.getNumber());
		Station[] array = (Station[])getToolStationHandler(conn).find(wStationKey);
		for (int i = 0 ; i < array.length ; i++)
		{
			//#CM50650
			//<en> Unacceptable if station type is other than 0 and idetntical station no. exists.</en>
			if(array[i].getType() != Station.STATION_TYPE_OTHER )
			{
				//#CM50651
				//<en>6123016 = The data is already registered. Cannot entere the identical station no.</en>
				setMessage("6123016");
				return true;
			}
		}
		return false;
	}

	//#CM50652
	/**<en>
	 * Determine whether/not the data is modifiable.
	 * Conditions to check when deleting stations
     *  -If the data does not exist in route.txt.
     *  -If the data does not exist in MACHINE table.
	 * @param <code>StationParameter</code>
	 * @return :return true if data is modifiable.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private boolean isChangeable(Connection conn, StationParameter param) throws ScheduleException
	{
		String stationNo = param.getNumber();

		//#CM50653
		//<en>Cannot delte data if it exists in Route.txt.</en>
		ToolCommonChecker commChecker = new ToolCommonChecker(conn);
		if(commChecker.isExistStationNo_RouteText(wFilePath, stationNo))
		{
			//#CM50654
			//<en>Cannot delete the data; Station ({0})exists in RouteText.</en>
			setMessage("6123163" + wDelim + stationNo);
			return false;
		}
		
		return true;
	}

	//#CM50655
	/**<en>
	 * Retrieve the Station instance.
	 * @return :the array of <code>Station</code> object
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private Station[] getDummyStationArray(Connection conn) throws ReadWriteException
	{
		ToolStationSearchKey stationKey  = new ToolStationSearchKey();
		//#CM50656
		//<en> Station type 0: search other data</en>
		stationKey.setStationType(Station.STATION_TYPE_OTHER);
		stationKey.setSendable(Station.NOT_SENDABLE);
		//#CM50657
		//<en> order of data display : station no.</en>
		stationKey.setNumberOrder(1,true);
		//#CM50658
		//<en>*** Retrieve the Station instance ***</en>
		Station[] array = (Station[])getToolStationHandler(conn).find(stationKey);
		return array;
	}
	
	//#CM50659
	/**<en>
	 * Delete the stations of the type 'other' and not sendable.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private void isDropDummyStation(Connection conn) throws ReadWriteException, NotFoundException
	{
		wStationKey = new ToolStationSearchKey();
		wStationKey.setStationType(Station.STATION_TYPE_OTHER);
		wStationKey.setSendable(Station.NOT_SENDABLE);
		getToolStationHandler(conn).drop(wStationKey);
	}
	
}
//#CM50660
//end of class

