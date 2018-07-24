// $Id: WorkPlaceCreater.java,v 1.2 2006/10/30 02:51:58 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM52083
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.wms.asrs.tool.common.LogHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationAlterKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolTerminalAreaHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolTerminalAreaSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWorkPlaceHandler;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.TerminalArea;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;

//#CM52084
/**<en>
 * This class is used when setting the stations.
 * It inherits the AbstractCreater, and implements processes requried for station setting.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:51:58 $
 * @author  $Author: suresh $
 </en>*/
public class WorkPlaceCreater extends AbstractCreater
{
	//#CM52085
	// Class fields --------------------------------------------------
	//#CM52086
	// Class variables -----------------------------------------------
	//#CM52087
	/**<en>
	* <CODE>ToolStationSearchKey</CODE> instance
	 </en>*/
	protected ToolStationSearchKey wStationKey = null;
	//#CM52088
	/**<en>
	 * <CODE>ToolStationAlterKey</CODE> instance 
	 </en>*/
	protected ToolStationAlterKey wStationAKey = null;

	
	private boolean TRUE = true;

	private boolean FALSE = false;

	//#CM52089
	/**<en> Class name (dedicated for storage)</en>*/

	public static String CLASS_STORAGE  = "jp.co.daifuku.wms.asrs.location.StorageStationOperator" ;
	
	//#CM52090
	/**<en> Class name (dedicated for retrieva)</en>*/

	public static String CLASS_RETRIEVAL  = "jp.co.daifuku.wms.asrs.location.RetrievalStationOperator" ;
	
	//#CM52091
	/**<en> Class name (P&D stand, powered cart)</en>*/

	public static String CLASS_INOUTSTATION  = "jp.co.daifuku.wms.asrs.location.InOutStationOperator" ;
	
	//#CM52092
	/**<en> Class name  (U-shaped storage) </en>*/

	public static String CLASS_FREESTORAGE  = "jp.co.daifuku.wms.asrs.location.FreeStorageStationOperator" ;
	
	//#CM52093
	/**<en> Class name  (U-shaped retrieval)</en>*/

	public static String CLASS_FREERETRIEVAL  = "jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator" ;
	
	//#CM52094
	/**<en> Load height</en>*/

	private int HEIGHT = 0;
	
	//#CM52095
	/**<en> Main station  </en>*/

	private int MAINSTATION = 1;
	
	//#CM52096
	/**<en> Main station  </en>*/

	private int WORKPLACE = 0;
	
	//#CM52097
	/**<en> Pdoduct number holder </en>*/

	public String wFilePath = "";
	
	//#CM52098
	// Class method --------------------------------------------------
	//#CM52099
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:51:58 $") ;
	}

	//#CM52100
	// Constructors --------------------------------------------------
	//#CM52101
	/**<en>
	 * Delete the parameter of the specified position. <BR>
	 * @param index :position of the deleting parameter
	 * @throws ScheduleException :Notifies if there are no parameters in specified position.
	 </en>*/
	public void removeParameter(Connection conn, int index) throws ScheduleException
	{
		//#CM52102
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
	//#CM52103
	/**<en>
	 * Delete all parameters.<BR>
	 * @throws ScheduleException :Notifies if there are no parameters in specified position.
	 </en>*/
	public void removeAllParameters(Connection conn) throws ScheduleException
	{
		//#CM52104
		//<en> Initialization of the message</en>
		setMessage("");

		wParamVec.removeAllElements();
		//#CM52105
		//<en>Deleted the data.</en>
		setMessage("6121003");
	}
	
	//#CM52106
	/**<en>
	 * Initialize this class. Generate the <CODE>ReStoringHandler</CODE> instance at the 
	 * initialization.
	 * @param conn : connetion object with database
	 * @param kind : process type
	 </en>*/
	public WorkPlaceCreater(Connection conn, int kind )
	{
		super(conn, kind);
	}

	//#CM52107
	// Public methods ------------------------------------------------
	//#CM52108
	/**<en>
	 * Implement the process to run when the print-issue button was pressed on the display.
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
	
	//#CM52109
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
		//#CM52110
		//<en>Vector where the data will temporarily be stored</en>
		//#CM52111
		//<en>Set the max number of data as an initial value for entered data summary.</en>
		Vector vec = new Vector(100);
		//#CM52112
		//<en>Entity class for display</en>
		WorkPlaceParameter dispData = null;
		
		//#CM52113
		//<en>Retrieve the search conditions.</en>
		WorkPlaceParameter wp = (WorkPlaceParameter)searchParam;
		String whNo = wp.getWareHouseStationNumber();
		String parentstNo = wp.getParentNumber();
		int mainST = wp.getMainStation();
		//#CM52114
		//<en>File name should be set here, as it cannot be provided from external in other methods.</en>
		//#CM52115
		//<en>CMENJP8415$CM set here..</en>
		wFilePath = wp.getFilePath();

		if(array.length > 0)
		{
			for(int i = 0; i < array.length; i++)
			{
				//#CM52116
				//<en>Display the data which has same parent station no. and the warehouse.</en>
				if(array[i].getParentStationNumber().equals(parentstNo) && array[i].getWarehouseStationNumber().equals(whNo))
				{
					dispData = new WorkPlaceParameter();
					//#CM52117
					//<en>warehouse station no.</en>
				 	dispData.setWareHouseStationNumber(array[i].getWarehouseStationNumber());
				 	//#CM52118
				 	//<en>workshop no.</en>
				 	dispData.setParentNumber(array[i].getParentStationNumber());
				 	//#CM52119
				 	//<en>workshop type</en>
				 	dispData.setWorkPlaceType(array[i].getWorkPlaceType());
				 	//#CM52120
					//<en>station no.</en>
					dispData.setNumber(array[i].getNumber());
					//#CM52121
					//<en>station name.</en>
					dispData.setName(array[i].getName());
					//#CM52122
					//<en>number of retrieval insruction sendable</en>
					dispData.setMaxPaletteQuantity(array[i].getMaxPaletteQuantity());
					//#CM52123
					//<en>number of carry insruction sendable</en>
					dispData.setMaxInstruction(array[i].getMaxInstruction());
					//#CM52124
					//AGCNo.
					dispData.setControllerNumber(array[i].getGroupController().getControllerNumber());
					//#CM52125
					//<en>type</en>
					dispData.setType(array[i].getType());
					//#CM52126
					//<en>aisle station no.</en>
					dispData.setAisleNumber(array[i].getAisleStationNumber());
					//#CM52127
					//<en>class name</en>
					dispData.setClassName(array[i].getClassName());
					//#CM52128
					//<en>main station</en>
					if(mainST == MAINSTATION)
					{
						dispData.setMainStation(MAINSTATION);
					}
					//#CM52129
					//<en>workshop</en>
					else if(mainST == WORKPLACE)
					{
						dispData.setMainStation(WORKPLACE);
					}
					
					vec.addElement(dispData);
				}
			}
			WorkPlaceParameter[] fmt = new WorkPlaceParameter[vec.size()];
			vec.toArray(fmt);
			return fmt;
		}
		return null;
	}

	//#CM52130
	/**<en>
	 * Processes the parameter check. It will be called when adding the parameter, before the 
	 * execution of maintenance process.
	 * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
	 * <number for modification><BR>
	 *  -the number should be less than restorage quantity.
	 *  -the number should be less than standard load quantity to stack.
	 * @param param :parameter to check
	 * @return :returns true if there is no error with parameter, or returns false if there are any errors.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		ToolCommonChecker check = new ToolCommonChecker(conn);
		WorkPlaceParameter mParameter = (WorkPlaceParameter)param;
		//#CM52131
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		
		String parentstationNo = mParameter.getParentNumber();
		
		//#CM52132
		//<en>Registration</en>
		switch(processingKind)
		{
			case M_CREATE:
				//#CM52133
				//<en> The process will not pass here if deleting all data.</en>
				if(!mParameter.getNumber().equals(""))
				{
					//#CM52134
					//<en> Check if the station No. /workshop no. is registered in STATION table.</en>
					ToolWorkPlaceHandler wphandle = new ToolWorkPlaceHandler(conn);
					ToolStationSearchKey stkey = new ToolStationSearchKey();
					stkey.setNumber(mParameter.getNumber());
					Station[] station = (Station[])wphandle.find(stkey);
					
					Parameter[] Array = (Parameter[])getParameters();
					//#CM52135
					//<en>Key to compare</en>
					//#CM52136
					//<en>Value which is unused in normal processes</en> 
					String newWarehouse = "99999";
					//#CM52137
					//<en>Value which is unused in normal processes</en> 
					String orgWarehouse = "99999";
					//#CM52138
					//<en> Check the identical user name</en>
					if(Array.length > 0)
					{
						//#CM52139
						//<en>Compare with tje storage type of the station which is appended this time.</en>
						newWarehouse = station[0].getWarehouseStationNumber();
						WorkPlaceParameter castparam = (WorkPlaceParameter)Array[0];
						//#CM52140
						//<en>Key for the entered data summary</en>
						orgWarehouse = castparam.getWareHouseStationNumber();
						//#CM52141
						//<en>Check the identical storage type</en>
						if(!newWarehouse.equals(orgWarehouse))
						{
							//#CM52142
							//<en>6123145 = Cannot set up the stations of different storage types.</en>
							setMessage("6123145");
							return false;
						}
					}
					
					//#CM52143
					//<en> Dummy station cannot be selected. </en>
					if(station[0].getType() == Station.STATION_TYPE_OTHER)
					{
						//#CM52144
						//<en> 6123139 = Dummy station cannot be selected in workshop setting process.</en>
						setMessage("6123139");
						return false;
					}
					//#CM52145
					//<en>*** In case of main station, ***</en>
					if(mParameter.getWorkPlaceType() == Station.MAIN_STATIONS)
					{
						Parameter[] mArray = (Parameter[])getParameters();
						//#CM52146
						//<en>Key to compare</en>
						//#CM52147
						//<en>Value which is unused in normal processes</en> 
						String newAStationNo = "99999"; 
						//#CM52148
						//<en>Value which is unused in normal processes</en>
						String orgAStationNo = "99999"; 
						//#CM52149
						//<en>Value which is unused in normal processes</en>
						String newClassName = "99999"; 
						//#CM52150
						//<en>Value which is unused in normal processes</en>
						String orgClassName = "99999"; 
						//#CM52151
						//<en>Value which is unused in normal processes</en>
						String newControllerNumber = "99999";
						//#CM52152
						//<en>Value which is unused in normal processes</en>
						String orgControllerNumber = "99999";
						
						//#CM52153
						//<en> If there is the entered data summary,</en>
						if(mArray.length > 0)
						{
							//#CM52154
							//<en>Compare with this station being added, and with items of entered data sumary.</en>
							newAStationNo = station[0].getAisleStationNumber();
							newClassName = station[0].getClassName();
							newControllerNumber = Integer.toString(station[0].getGroupController().getControllerNumber());
							for (int i = 0 ; i < station.length ; i++)
							{
								WorkPlaceParameter castparam = (WorkPlaceParameter)mArray[i];
								//#CM52155
								//<en>Key for the entered data summary</en>
								orgAStationNo = castparam.getAisleNumber();
								orgClassName = castparam.getClassName();
								orgControllerNumber = Integer.toString(castparam.getControllerNumber());
								//#CM52156
								//<en>Check the identical aisle station no.</en>
								//#CM52157
								//<en>Cannot set the station of different aisle station no. as the same main station.</en>
								if(!newAStationNo.equals(orgAStationNo))
								{
									//#CM52158
									//<en>6123130 = Cannot set the stations of different aisle station no.</en>
									setMessage("6123130");
									return false;
								}
								//#CM52159
								//<en>Check the identical class name.</en>
								if(!newClassName.equals(orgClassName))
								{
									//#CM52160
									//<en>6123141 = Cannot set the stations of different station type.</en>
									setMessage("6123141");
									return false;
								}
								//#CM52161
								//<en>Check the identical group controller no.</en>
								if(!newControllerNumber.equals(orgControllerNumber))
								{
									//#CM52162
									//<en>6123143 = Cannot set the stations of different AGCNo.</en>
									setMessage("6123143");
									return false;
								}
							}
						}
					}
					
					//#CM52163
					//<en>Check thestation no.</en>
					if(!check.checkStationNumber(mParameter.getNumber()))
					{
						//#CM52164
						//<en>Set the contents of the error.</en>
						setMessage(check.getMessage());
						return false;
					}
				}				
				break;
			default:
				//#CM52165
				//<en> Unexpected value has been set.{0} = {1}</en>
				String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
				RmiMsgLogClient.write( msg, (String)this.getClass().getName());
				//#CM52166
				//<en> 6126499 =  Fatal error occurred. Please refer to the log.</en>
				throw new ScheduleException("6126499");	
		}
		
		return true;
	}

	//#CM52167
	/**<en>
	 * Process the inconsistency check. This will be called when generating the eAWC environment setting tool.
	 * If any error takes place, the detail will be written in the file which is specified by filename.
	 * @param filename :name of the file the log will be written in when error occurred.
	 * @param locale <code>Locale</code> object
	 * @return :true if there is no error, or false if there are any error.
	 * @throws ScheduleException :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	public boolean consistencyCheck(Connection conn, String logpath, Locale locale) throws ScheduleException, ReadWriteException
	{
		ToolCommonChecker check = new ToolCommonChecker(conn);
		//#CM52168
		//<en>True if there is no error.</en>
		boolean errorFlag = true;
		String logfile = logpath +"/"+ ToolParam.getParam("CONSTRAINT_CHECK_FILE");
		
		try
		{
			LogHandler loghandle = new LogHandler(logfile, locale);
			
			//#CM52169
			//<en>*** Check the group controller no. (Aisle table) ***</en>
			//#CM52170
			//<en>Check whether/not the group controller no. of STATION table exist in group controller table.</en>
			int[] workptype = {
								Station.STAND_ALONE_STATIONS,
								Station.AISLE_CONMECT_STATIONS,
								Station.MAIN_STATIONS
								};
			ToolStationSearchKey gstationKey = new ToolStationSearchKey();
			gstationKey.setWorkPlaceType(workptype);
			Station[] gArray = (Station[])getToolWorkPlaceHandler(conn).find(gstationKey);
			for(int i = 0; i < gArray.length; i++)
			{
				int controller = gArray[i].getGroupController().getControllerNumber();
				if(controller != 0)
				{
					if(!check.isExistControllerNo(controller))
					{
						loghandle.write("WorkPlace", "Station Table",check.getMessage());
						errorFlag = false;
					}
				}
			}

			//#CM52171
			//<en>*** Check the warehouse station no. (Warehouse table) ***</en>
			//#CM52172
			//<en>Check if the warehouse station no. in STATION table exists in WAREHOUSE table.</en>
			for(int i = 0; i < gArray.length; i++)
			{
				String warehouseStationNo = gArray[i].getWarehouseStationNumber();
				if(gArray[i].getWorkPlaceType() != Station.WPTYPE_FLOOR 
				 && gArray[i].getWorkPlaceType() != Station.WPTYPE_ALL)
				{
					if(!check.isExistAutoWarehouseStationNo(warehouseStationNo))
					{
						loghandle.write("WorkPlace","Station Table",check.getMessage());
						errorFlag = false;
					}
				}
			}

			//#CM52173
			//<en>*** Check the aisle station no. (Shelf table) ***</en>
			//#CM52174
			//<en>Check if the aisle station no. of STATION table exist in aisle table.</en>
			for(int i = 0; i < gArray.length; i++)
			{
				String aisleStationNo = gArray[i].getAisleStationNumber();
				if (aisleStationNo != null && aisleStationNo.length() > 0)
				{
					if(!check.isExistAisleStationNo(aisleStationNo))
					{
						loghandle.write("WorkPlace", "Station Table",check.getMessage());
						errorFlag = false;
					}
				}
			}
			
			//#CM52175
			//<en>*** Check the station no. (Station table) ***</en>
			//#CM52176
			//<en>Check if station no. of STATION table exist in STATIONTYPE table.</en>
			for(int i = 0; i < gArray.length; i++)
			{
				String StationNo = gArray[i].getNumber();
				if(!check.isExistStationType(StationNo))
				{
					loghandle.write("WorkPlace","Station Table",check.getMessage());
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

	//#CM52177
	/**<en>
	 * Process the parameter duplicate check.
 	 * Data of identical item code and lot no. cannot be registered in entered data summary.
	 * It checks the duplication of parameter, then returns true if there was no duplicated data or 
	 * returns false if there were any duplication.
	 * If parameter duplicate check failed, its reason can be obtained by <code>getMessage</code>.
	 * @param param :parameter to check
	 * @return :returns true if the parameter duplicate check has succeeded, or returns false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
	 </en>*/
	public boolean duplicationCheck(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		Parameter[] mArray = (Parameter[])getParameters();
		WorkPlaceParameter mParam = (WorkPlaceParameter)param;

		//#CM52178
		//<en>Check the identical data.</en>
		if(isSameData(mParam, mArray))
		{
			return false;
		}
		
		return true;
	}

	//#CM52179
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
		//#CM52180
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM52181
		//<en>Registration</en>
		if(processingKind == M_CREATE)
		{
			if(!create(conn))
			{
				return false;
			}
			//#CM52182
			//<en>6121004 = Edited the data.</en>
			setMessage("6121004");
			return true;
		}
		//#CM52183
		//<en>Error with the process type.</en>
		else
		{
			//#CM52184
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM52185
			//<en> 6126499 =  Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}

	//#CM52186
	// Package methods -----------------------------------------------

	//#CM52187
	// Protected methods ---------------------------------------------
	//#CM52188
	/**<en>
	 * Retrieve the <CODE>ReStoringHandler</CODE> instance generated at the initialization of this class.
	 * @return <CODE>ReStoringHandler</CODE>
	 </en>*/
	protected ToolWorkPlaceHandler getToolWorkPlaceHandler(Connection conn)
	{
		return new ToolWorkPlaceHandler(conn);
	}
	
	//#CM52189
	/**<en>
	 * Retrieve the <CODE>FindUtil</CODE> instance generated at the initialization of this class.
	 * @return <CODE>FindUtil</CODE>
	 </en>*/
	protected ToolFindUtil getFindUtil(Connection conn)
	{
		return new ToolFindUtil(conn);
	}
	//#CM52190
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
	
	//#CM52191
	/**<en>
	 * Process the maintenance modifications.
	 * Modification will be made based on the key items of parameter array. 
	 * Set the work no., item code and lot no. to the AlterKey as search conditions, and updates storage quantity.
	 * It returns true if the maintenance process succeeded, or false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :returns true if the process succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	protected boolean modify() throws ReadWriteException, ScheduleException
	{
		return true;
	}

	//#CM52192
	/**<en>
	 * Process the maintenance registrations. The scheduled restorage data is not registered in this process.
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
			WorkPlaceParameter headparam = null;
			WorkPlaceParameter castparam = null;
			WorkPlaceParameter workparam = null;
			WorkPlaceParameter wkparam = null;
			ToolWorkPlaceHandler tsh = getToolWorkPlaceHandler(conn);
			String space = " ";
			ToolStationAlterKey alterkey = null;

			if( mArray.length > 0)
			{
				headparam = (WorkPlaceParameter)mArray[0];

				ToolStationSearchKey wStation = new ToolStationSearchKey();
				wStation.setNumber(headparam.getParentNumber());
				
				//#CM52193
				//<en>*** Update of stations ***</en>
				//#CM52194
				//<en>If the entered workshop exists, </en>
				if (tsh.count(wStation) > 0)
				{
					//#CM52195
					//<en>Enter blank in the parent station no. of the station which has been set</en>
					//<en>with entered workshop.</en>
					alterkey = new ToolStationAlterKey();
					alterkey.setParentStationNumber(headparam.getParentNumber());
					alterkey.updateParentStationNumber(space);
					tsh.modify(alterkey);
				}
				
				//#CM52196
				//<en>If there are any entered data summary, </en>
				if(!headparam.getNumber().equals(""))
				{
					for(int i = 0; i < mArray.length; i++)
					{
						castparam = (WorkPlaceParameter)mArray[i];
						//#CM52197
						//<en>Set the workshop for the parent station of the station found in </en>
						//<en>entered data summary.</en>
						alterkey = new ToolStationAlterKey();
						alterkey.setNumber(castparam.getNumber());
						alterkey.updateParentStationNumber(castparam.getParentNumber());
						tsh.modify(alterkey);
					}
				
					//#CM52198
					//<en>***Creation/modificaiton of the workshop/main station***</en>
					//#CM52199
					//<en>***In case the entered workshop does not exist,***</en>
					//#CM52200
					//<en>Newly create the workshop data.</en>
					if (tsh.count(wStation) <= 0)
					{
						Station station = new Station();
						//#CM52201
						//<en>warehouse station no.</en>
						station.setWarehouseStationNumber(castparam.getWareHouseStationNumber());
						//#CM52202
				 		//<en>workshop no.</en>
						station.setStationNumber(castparam.getParentNumber());					
						//#CM52203
				 		//<en>workshop name</en>
						station.setName(castparam.getParentName());								
						
						//#CM52204
						//<en>workshop type (set 1 or 2 for workshop, 3 for main station)</en>
						//#CM52205
						//<en>workshop</en>
						if(castparam.getMainStation() == WORKPLACE)
						{
							station.setWorkPlaceType(castparam.getWorkPlaceType());	
						}
						//#CM52206
						//<en>main station</en>
						else if	(castparam.getMainStation() == MAINSTATION)
						{
							station.setWorkPlaceType(Station.MAIN_STATIONS);
						}
						
						//#CM52207
						//<en>AGCNo. (set 0 for workshop, or set AGCNo. of the station for main station)</en>
						//#CM52208
						//<en>Workshop</en>
						if(castparam.getMainStation() == WORKPLACE)
						{
							GroupController gc = new GroupController(conn, 0);
							station.setGroupController(gc);
						}
						//#CM52209
						//<en>Main station</en>
						else if	(castparam.getMainStation() == MAINSTATION)
						{
							GroupController gc = new GroupController(conn, castparam.getControllerNumber());
							station.setGroupController(gc);
						}
						
						//#CM52210
						//<en>Class name (set blank for workshop, or set class name for main station)</en>
						//#CM52211
						//<en>Workshop</en>
						if(castparam.getMainStation() == WORKPLACE)
						{
							station.setClassName("");
						}
						//#CM52212
						//<en>Main station</en>
						else if	(castparam.getMainStation() == MAINSTATION)
						{
							station.setClassName(castparam.getClassName());
						}
						
						//#CM52213
						//<en>For aisle station no., set the aisle station of the station.</en>
						//<en>If setting the same workshop for station/workshop of different asile station no.,</en>
						//<en> set blank.</en>
						//#CM52214
						//<en>CMENJP8510$CM set blank when Aisle Station No. is different from Station and Workshop is same.</en>
					String aisle = castparam.getAisleNumber();
						int fg = 0;
						for(int i = 0; i < mArray.length; i++)
						{
							wkparam = (WorkPlaceParameter)mArray[i];
							if(!wkparam.getAisleNumber().equals(aisle))
							{
								fg = 1;
							}
						}
						if(fg == 1)
						{
							station.setAisleStationNumber("");
						}
						else if(fg == 0)
						{
							station.setAisleStationNumber(castparam.getAisleNumber());
						}
						
						//#CM52215
						//<en>Determine the type according to the station type.(All storage, all retrieval,</en>
						//<en>all storage/retrieval available,of different types)</en>
						int type = castparam.getType();
						int flg = 0;
						for(int i = 0; i < mArray.length; i++)
						{
							workparam = (WorkPlaceParameter)mArray[i];
							//#CM52216
							//<en>If the type is different,</en>
							if(type	== Station.STATION_TYPE_IN || type == Station.STATION_TYPE_OUT)
							{
								if(type != workparam.getType())
								{
									flg = 3;
								}
							}
							//#CM52217
							//<en>All stations are available of storage/retrieval </en>
							else if(type == Station.STATION_TYPE_INOUT)
							{
								flg = 3;
							}
						}
						if(flg == 0)
						{
							//#CM52218
							//<en>All storage</en>
							if(type == Station.STATION_TYPE_IN)
							{
								station.setType(Station.STATION_TYPE_IN);
							}
							//#CM52219
							//<en>All retrieval</en>
							else if(type == Station.STATION_TYPE_OUT)
							{
								station.setType(Station.STATION_TYPE_OUT);
							}
						}
						//#CM52220
						//<en>All available of storage/retrieval, of different types</en>
						else if(flg == 3)
						{
							station.setType(Station.STATION_TYPE_INOUT);
						}
						
						//#CM52221
						//<en>set type (set 0 for workshop)</en>
						station.setInSettingType(0);											
						//#CM52222
						//<en>arrival report (0: no check)</en>
						station.setArrivalCheck(FALSE);											
						//#CM52223
						//<en>load size check (0: no check)</en>
						station.setLoadSizeCheck(FALSE);										
						//#CM52224
						//<en>on-line indication(0)</en>
						station.setOperationDisplay(0);											
						//#CM52225
						//<en>restorage  (0:Restorage work (0:no restorage))</en>
						station.setReStoringOperation(FALSE);									
						//#CM52226
						//<en>carry isntruction for restorage(0)</en>
						station.setReStoringInstruction(0);										
						//#CM52227
						//<en>mode switch(0)</en>
						station.setModeType(0);													
						
						//#CM52228
						//<en>Number of carry instruction sendable (0 for workshop, total of unde classes for main station)</en>
						//#CM52229
						//<en>Workshop</en>
						if(castparam.getMainStation() == WORKPLACE)
						{
							station.setMaxInstruction(0);
						}
						//#CM52230
						//<en>Main station</en>
						else if(castparam.getMainStation() == MAINSTATION)
						{
							int isum = 0;
							for(int i = 0; i < mArray.length; i++)
							{
								castparam = (WorkPlaceParameter)mArray[i];
								isum += castparam.getMaxInstruction();
							}
							station.setMaxInstruction(isum);
						}
						
						//#CM52231
						//<en>Number of retrieval instruction sendable (0 for workshop, total of unde classes for main station)</en>
						//#CM52232
						//<en>Workshop</en>
						if(castparam.getMainStation() == WORKPLACE)
						{
							station.setMaxPaletteQuantity(0);
						}
						//#CM52233
						//<en>Main station</en>
						else if(castparam.getMainStation() == MAINSTATION)
						{
							int psum = 0;
							for(int i = 0; i < mArray.length; i++)
							{
								castparam = (WorkPlaceParameter)mArray[i];
								psum += castparam.getMaxInstruction();
							}
							station.setMaxPaletteQuantity(psum);
						}
						
						//#CM52234
						//<en>Division of sendable (0:unsendable for workshop, 1:sendable for main statio)</en>
						//#CM52235
						//<en>Workshop</en>
						if(castparam.getMainStation() == WORKPLACE)
						{
							station.setSendable(FALSE);
						}
						//#CM52236
						//<en>Main station</en>
						else if(castparam.getMainStation() == MAINSTATION)
						{
							station.setSendable(TRUE);
						}
						
						//#CM52237
						//<en>status (0:off-line)</en>
						station.setStatus(Station.STATION_NG);									
						//#CM52238
						//<en>suspention flag (0:available)</en>
						station.setSuspend(FALSE);												
						//#CM52239
						//<en>removal (0:available)</en>
						station.setRemove(TRUE);												
						//#CM52240
						//<en>inventory check (0:unprocessed)</en>
						station.setInventoryCheckFlag(Station.NOT_INVENTORYCHECK);				
						//#CM52241
						//<en>pallet operation(0:unused)</en>
						station.setPoperationNeed(FALSE);										
						//#CM52242
						//<en>current work mode (0: neutral)</en>
						station.setCurrentMode(Station.NEUTRAL);								
						//#CM52243
						//<en>mode switch request type(no request)</en>
						station.setChangeModeRequest(Station.NO_REQUEST);						
						//#CM52244
						//<en>load height(0)</en>
						station.setHeight(HEIGHT);												
						
						getToolWorkPlaceHandler(conn).create(station);
					}
					//#CM52245
					//<en>***If the entered workshop exists,***</en>
					else
					{
						//#CM52246
						//<en>Type will be determined based on the station type. (all storage, all retrieval, all available</en>
						//<en>for sotrage/retrieval, of different types)</en>
						int stype = castparam.getType();
						int flag = 0;
						for(int i = 0; i < mArray.length; i++)
						{
							workparam = (WorkPlaceParameter)mArray[i];
							//#CM52247
							//<en>If the types are different,</en>
							if(stype == Station.STATION_TYPE_IN || stype == Station.STATION_TYPE_OUT)
							{
								if(stype != workparam.getType())
								{
									flag = 3;
								}
							}
							//#CM52248
							//<en>all available of storage/retrieval</en>
							else if(stype == Station.STATION_TYPE_INOUT)
							{
								flag = 3;
							}
						}
						alterkey = new ToolStationAlterKey();
						alterkey.setNumber(headparam.getParentNumber());
						if(flag == 0)
						{
							//#CM52249
							//<en>all storage case</en>
							if(stype == Station.STATION_TYPE_IN)
							{
								alterkey.updateStationType(Station.STATION_TYPE_IN);
								tsh.modify(alterkey);
							}
							//#CM52250
							//<en>all retrieval case</en>
							else if(stype == Station.STATION_TYPE_OUT)
							{
								alterkey.updateStationType(Station.STATION_TYPE_OUT);
								tsh.modify(alterkey);
							}
						}
						//#CM52251
						//<en>If both storage and retrieval are inxluded, if storage/retrieval available</en>
						else if(flag == 3)
						{
							alterkey.updateStationType(Station.STATION_TYPE_INOUT);
							tsh.modify(alterkey);
						}
						
						//#CM52252
						//<en>For aisle station no., set the aisle station of the station.</en>
						//<en>If setting the same workshop for station/workshop of different asile station no.,</en>
						//<en> set blank.</en>
						//#CM52253
						//<en>CMENJP8548$CM set blank when Aisle Station No. is different from Station and Workshop is same.</en>
					String aisle = castparam.getAisleNumber();
						int fg = 0;
						for(int i = 0; i < mArray.length; i++)
						{
							wkparam = (WorkPlaceParameter)mArray[i];
							if(!wkparam.getAisleNumber().equals(aisle))
							{
								fg = 1;
							}
						}
						alterkey = new ToolStationAlterKey();
						alterkey.setNumber(castparam.getParentNumber());
						if(fg == 1)
						{
							alterkey.updateAisleStationNumber("");
							tsh.modify(alterkey);
						}
						else if(fg == 0)
						{
							alterkey.updateAisleStationNumber(castparam.getAisleNumber());
							tsh.modify(alterkey);
						}
						
						//#CM52254
						//<en>Number of carry instruction sendable (0 for workshop, total of unde classes for main station)</en>
						alterkey = new ToolStationAlterKey();
						alterkey.setNumber(headparam.getParentNumber());
						//#CM52255
						//<en>Workshop</en>
						if(castparam.getMainStation() == WORKPLACE)
						{
							alterkey.updateMaxInstruction(0);
							tsh.modify(alterkey);
						}
						//#CM52256
						//<en>Main station</en>
						else if(castparam.getMainStation() == MAINSTATION)
						{
							int isum = 0;
							for(int i = 0; i < mArray.length; i++)
							{
								castparam = (WorkPlaceParameter)mArray[i];
								isum += castparam.getMaxInstruction();
							}
							alterkey.updateMaxInstruction(isum);
							tsh.modify(alterkey);
						}
						
						//#CM52257
						//<en>Number of retrieval instruction sendable (0 for workshop, total of unde classes for main station)</en>
						//#CM52258
						//<en>Workshop</en>
						if(castparam.getMainStation() == WORKPLACE)
						{
							alterkey.updateMaxPaletteQuantity(0);
							tsh.modify(alterkey);
						}
						//#CM52259
						//<en>Main station</en>
						else if(castparam.getMainStation() == MAINSTATION)
						{
							int psum = 0;
							for(int i = 0; i < mArray.length; i++)
							{
								castparam = (WorkPlaceParameter)mArray[i];
								psum += castparam.getMaxInstruction();
							}
							alterkey.updateMaxPaletteQuantity(psum);
							tsh.modify(alterkey);
						}
						
						//#CM52260
						//<en>AGCNo. (set 0 for workshop, or set AGCno. of the station for main station)</en>
						//#CM52261
						//<en>workshop</en>
						if(castparam.getMainStation() == WORKPLACE)
						{
							alterkey.updateControllerNumber(0);
							tsh.modify(alterkey);
						}
						//#CM52262
						//<en>Main station</en>
						else if	(castparam.getMainStation() == MAINSTATION)
						{
							alterkey.updateControllerNumber(castparam.getControllerNumber());
							tsh.modify(alterkey);
						}

						//#CM52263
						//<en>Class name (set blank for workshop, or set class name of the station for main station)</en>
						//#CM52264
						//<en>Workshop</en>
						if(castparam.getMainStation() == WORKPLACE)
						{
							alterkey.updateClassName("");
							tsh.modify(alterkey);
						}
						//#CM52265
						//<en>Main station</en>
						else if	(castparam.getMainStation() == MAINSTATION)
						{
							alterkey.updateClassName(castparam.getClassName());
							tsh.modify(alterkey);
						}
					}
				}
			}
			
			//#CM52266
			//<en>***Delete workshop and main station which are not set to parent stations.***</en>
			//#CM52267
			//<en>Retrieve data in STATION table.</en>
			ToolStationSearchKey wStation = new ToolStationSearchKey();
			Station[] array = (Station[])tsh.find(wStation);

			//#CM52268
			//<en>Retrieve data of workshop and main station.</en>
			ToolStationSearchKey wStation2 = new ToolStationSearchKey();
			int[] temp_workplace = {Station.STAND_ALONE_STATIONS, Station.AISLE_CONMECT_STATIONS, Station.MAIN_STATIONS};
			wStation2.setWorkPlaceType(temp_workplace);
			Station[] array2 = (Station[])tsh.find(wStation2);

			if (array2.length > 0)
			{
				for(int i = 0; i < array2.length; i++)
				{
					String wk2 = array2[i].getNumber();
					int flg = 0;
					for(int j = 0; j < array.length; j++)
					{
						String wk = array[j].getParentStationNumber();
						if(wk2.equals(wk))
						{
							flg = 1;
						}
						
					}
					if(flg == 0)
					{
						wStation2.setNumber(wk2);
						tsh.drop(wStation2);
					}
				}
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
		catch(InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM52269
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

	//#CM52270
	// Private methods -----------------------------------------------
	//#CM52271
	/**<en>
	 * Check whether/not the data has already been modified by other terminals.
	 * Compares the ReStoring instance which has been set to maintenance parameter with this
	 * other ReStoring instance which has been retrieved from current DB.
	 * If the comparison resulted the both instances are equal, it regards that no modification was 
	 * made by other terminals. If the comparison resulted these instances to be different, then it 
	 * regards the other terminal already modified the data.
	 * @param ReStoringMaintenanceParameter
	 * @return :returns true if the data ghas already been modified by other terminals, or 
	 * returns false if it has not been modified.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	private boolean isAlreadyChanged(WorkPlaceParameter param) throws ReadWriteException
	{
		return false;
	}
	
	//#CM52272
	/**<en>
	 * Implement the check in order to see that the identical data has been selected when 
	 * chosing data from the list box to edit.
	 * In the setting of stations, it checks whether/not the storage type of appending parameter
	 * exists in the entered data summary.
	 * @param param  :the parameter which will be appended in this process
	 * @param array  :entered data summary (pool)
	 * @return       :return true if identical data exists.
	 </en>*/
	private boolean isSameData(WorkPlaceParameter param, 
								Parameter[] array)
	{
		//#CM52273
		//<en>Key to compare</en>
		//#CM52274
		//<en>Value which is unused in normal processes</en> 
		String newStationNo = "99999"; 
		//#CM52275
		//<en>Value which is unused in normal processes</en> 
		String orgStationNo = "99999"; 
		
		//#CM52276
		//<en>If there is the entered data summary,</en>
		if(array.length > 0)
		{
			//#CM52277
			//<en>Compare by the storage type appended in this process.</en>
			newStationNo = param.getNumber();
			
			for (int i = 0 ; i < array.length ; i++)
			{
				WorkPlaceParameter castparam = (WorkPlaceParameter)array[i];
				//#CM52278
				//<en>Key for the entered data summary</en>
				orgStationNo = castparam.getNumber();
				//#CM52279
				//<en>Check the identical station no.</en>
				if(newStationNo.equals(orgStationNo))
				{
					//#CM52280
					//<en>6123016 = The data is already entered. Cannot input the identical station no.</en>
					setMessage("6123016");
					return true;
				}
			}
		}
		
		return false;
	}

	//#CM52281
	/**<en>
	 * Determine whether/not the data is modifiable.
	 * Conditions to check when deleting workshop/main station
     * In case of workshop, 
     *  - The data should not exist in terminal area (TERMINALAREA table)
     * In case of main station, 
     *  - The data should not exist in carry route (route.txt)
     *  - The data should not exist in terminal area (TERMINALAREA table)
	 * @param <code>WorkPlaceParameter</code>
	 * @return :return true if the data is modifiable.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private boolean isDeletable(Connection conn, WorkPlaceParameter param) throws ScheduleException
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
			//#CM52282
			//<en>Cannot modify the entered workshop as it exists in TERMINALAREA table.</en>
			setMessage("6123185");
			return false;
		}
		else if(reason == 2)
		{
			//#CM52283
			//<en>Cannot modify the entered main station as it exists in TERMINALAREA table.</en>
			setMessage("6123187");
			return false;
		}
		else if(reason == 3)
		{
			//#CM52284
			//<en>Cannot modify the entered main station as it exists in RouteText.</en>
			setMessage("6123186");
			return false;
		}
		
		return true;
	}
	
	//#CM52285
	//<en>Determine if deletion/modification of data is possible.</en>
	//<en>Deletion/modification of data can be processed if the return value is 0.</en>
	//<en>For all other cases, appropriate messages will be set respectively by the call resource.</en>
	//#CM52286
	//<en>When the CMENJP8579$CM return value is 0, Deletion and the correction become possible. </en>
	//#CM52287
	//<en>Set the message which corresponds respectively </en>
	//#CM52288
	//<en>besides on the call side. </en>
private int deletable(Connection conn, WorkPlaceParameter param) throws ScheduleException
	{
		try
		{	
			String parentstationNo = param.getParentNumber();
			
			ToolTerminalAreaSearchKey terminalKey = new ToolTerminalAreaSearchKey();
			ToolTerminalAreaHandler terminalHandle = new ToolTerminalAreaHandler(conn);
			terminalKey.setStationNumber(parentstationNo);
			TerminalArea[] terminalArray = (TerminalArea[])terminalHandle.find(terminalKey);
			
			//#CM52289
			//<en>***In case of workshop***</en>
			if(param.getMainStation() == WORKPLACE)
			{
				//#CM52290
				//<en>Cannot delete data if it exists in Terminalarea table.</en>
				if(terminalArray.length > 0)
				{
					return 1;
				}
			}
			//#CM52291
			//<en>*** In case of main station, ***</en>
			else if(param.getMainStation() == MAINSTATION)
			{
				//#CM52292
				//<en>Cannot delete data if it exists in Terminalarea table.</en>
				if(terminalArray.length > 0)
				{
					return 2;
				}
				
				//#CM52293
				//<en>Cannot delete data if it exists in Route.txt.</en>
				ToolCommonChecker commChecker = new ToolCommonChecker(conn);
				if(commChecker.isExistStationNo_RouteText(wFilePath, parentstationNo))
				{
					return 3;
				}
			}
			return 0;
		}
		catch(ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM52294
	/**<en>
	 * Retrieve the Station instance.
	 * @return :the array of <code>Station</code> object
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private Station[] getStationArray(Connection conn) throws ReadWriteException
	{
		ToolStationSearchKey stationKey  = new ToolStationSearchKey();
		stationKey.setNumberOrder(1,true);
		
		//#CM52295
		//<en>*** Retrieve the Station instance.***</en>
		Station[] array = (Station[])getToolWorkPlaceHandler(conn).find(stationKey);
		return array;
	}	
}
//#CM52296
//end of class

