// $Id: WarehouseCreater.java,v 1.2 2006/10/30 02:51:59 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM51947
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

import jp.co.daifuku.wms.asrs.tool.common.LogHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Aisle;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ScheduleInterface;

//#CM51948
/**<en>
 * This class operates the settings of the warehouse.
 * It inherits the AbstractCreater. and implements the processes required to set up the warehouses.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:51:59 $
 * @author  $Author: suresh $
 </en>*/
public class WarehouseCreater extends AbstractCreater
{
	//#CM51949
	// Class fields --------------------------------------------------
	//#CM51950
	// Class variables -----------------------------------------------
	
	private Locale wLocale = null;

	//#CM51951
	/**<en>
	 * Product number folder
	 </en>*/
	public String wFilePath = "";

	//#CM51952
	// Class method --------------------------------------------------
	//#CM51953
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:51:59 $") ;
	}

	//#CM51954
	// Constructors --------------------------------------------------
	//#CM51955
	/**<en>
	 * Initialize this class. Generate the instance of <CODE>ReStoringHandler</CODE> at the initialization
	 * @param conn :connection object with database
	 * @param kind :process type
	 </en>*/
	public WarehouseCreater(Connection conn, int kind )
	{
		super(conn, kind);
	}

	//#CM51956
	// Public methods ------------------------------------------------
	//#CM51957
	/**<en>
	 * Delete the parameter of the specified position. <BR>
	 * @param conn  :connetion object to connect with database.
	 * @param index :position of the deleting parameter
	 * @throws ScheduleException :Notifies if there are no parameters in specified position.
	 </en>*/
	public void removeParameter(Connection conn, int index) throws ScheduleException
	{
		//#CM51958
		//<en> Initialization of the message</en>
		setMessage("");
		try
		{
			wParamVec.remove(index);
			//#CM51959
			//<en> Deleted the data.</en>
			setMessage("6121003");
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM51960
	/**<en>
	 * Delete all parameters.<BR>
	 * @param conn :connection object with database
	 * @throws ScheduleException :Notifies if there are no parameters in specified position.
	 </en>*/
	public void removeAllParameters(Connection conn) throws ScheduleException
	{
		//#CM51961
		//<en> Initialization of the message</en>
		setMessage("");

		wParamVec.removeAllElements();
		//#CM51962
		//<en>Deleted the data.</en>
		setMessage("6121003");
	}

	//#CM51963
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
	
	//#CM51964
	/**<en>
	 * Retrieve data to isplay on the screen.<BR>
	 * @param conn :connection object to connect with database
	 * @param <code>Locale</code> object
	 * @param searchParam :search conditions
	 * @return :the array of schedule parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.<BR>
	 </en>*/
	public Parameter[] query(Connection conn, Locale locale, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		//#CM51965
		//<en>Conduct the preservation of locale.</en>
		wLocale = locale;
		//#CM51966
		//<en>File name should be set here, as it cannot be provided from external in other methods.</en>
		//#CM51967
		//<en>CMENJP8288$CM set here..</en>
		wFilePath =  ((WarehouseParameter)searchParam).getFilePath();
		
		Warehouse[] array = getWarehouseArray(conn);

		//#CM51968
		//<en>Vector where the data will temporarily be stored</en>
		//#CM51969
		//<en>Set the max number of data as an initial value for entered data summary.</en>
		Vector vec = new Vector(100);
		//#CM51970
		//<en>Create the entity class for display.</en>
		WarehouseParameter dispData = null;
		if(array.length > 0)
		{
			for(int i = 0; i < array.length; i++)
			{
				dispData = new WarehouseParameter();
				dispData.setWarehouseNumber(array[i].getWarehouseNumber());
				dispData.setStationNumber(array[i].getNumber());
				dispData.setWarehouseName(array[i].getName());
				dispData.setWarehouseType(array[i].getWarehouseType());
				dispData.setEmploymentType(array[i].getEmploymentType());
				dispData.setMaxMixedQuantity(array[i].getMaxMixedPalette());
				vec.addElement(dispData);
			}
			WarehouseParameter[] fmt = new WarehouseParameter[vec.size()];
			vec.toArray(fmt);
			return fmt;
		}
		return null;
	}

	//#CM51971
	/**<en>
	 * Processes the parameter check. It will be called when adding the parameter, before the 
	 * execution of maintenance process.
	 * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
	 * @param conn  :connection object to connect with database
	 * @param param :parameter to check
	 * @return :returns true if there is no error with parameter, or returns false if there are any errors.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		ToolCommonChecker check = new ToolCommonChecker(conn);
		WarehouseParameter mParameter 
			= (WarehouseParameter)param;
		//#CM51972
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM51973
		//<en>Registration</en>
		if(processingKind == M_CREATE)
		{
			//#CM51974
			//<en> Check the station no. (unacceptable letters and symbols)</en>
			if(!check.checkStationNumber(mParameter.getStationNumber()))
			{
				//#CM51975
				//<en>Set the contents of the error.</en>
				setMessage(check.getMessage());
				return false;
			}

			//#CM51976
			//<en> Check the warehouse name.</en>
			if(!check.checkWarehouseName(mParameter.getWarehouseName()))
			{
				//#CM51977
				//<en>Set the contents of the error.</en>
				setMessage(check.getMessage());
				return false;
			}
			
			//#CM51978
			//<en> Check the warehouse type.</en>
			if( mParameter.getWarehouseType() != Warehouse.AUTOMATID_WAREHOUSE 
			 && mParameter.getWarehouseType() != Warehouse.CONVENTIONAL_WAREHOUSE )
			 {
			 	//#CM51979
			 	//<en> The warehouse type ={0} is incorrect.</en>
			 	setMessage( "6123259" + wDelim + mParameter.getWarehouseType() );
			 	return false ;
			 }

			//#CM51980
			//<en> Check the operation type of automated warehouse.</en>
			if( mParameter.getWarehouseType() == Warehouse.AUTOMATID_WAREHOUSE )
			{
				if( mParameter.getEmploymentType() != Warehouse.OPEN 
				 && mParameter.getEmploymentType() != Warehouse.CLOSE )
				 {
					//#CM51981
					//<en> The operation type of automated warehouse ={0} is incorrect.</en>
					setMessage( "6123260" + wDelim + mParameter.getEmploymentType() );
					return false ;
				 }
			}
	
			//#CM51982
			//<en> Check the max. mix-load quantity.</en>
			if(!check.checkMaxMixedQuantity(mParameter.getMaxMixedQuantity()))
			{
				//#CM51983
				//<en>Set the contents of the error.</en>
				setMessage(check.getMessage());
				return false;
			}


			return true;
		}
		//#CM51984
		//<en>Error with the process type.</en>
		else
		{
			//#CM51985
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM51986
			//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}

	//#CM51987
	/**<en>
	 * Process the inconsistency check. This will be called when generating the eAWC environment setting tool.
	 * If any error takes place, the detail will be written in the file.
	 * @param logpath :path to place the file in which the log will be written when error occurred.
	 * @param locale <code>Locale</code> object
	 * @return :true if there is no error, or false if there are any error.
	 * @throws ScheduleException :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	public boolean consistencyCheck(Connection conn, String logpath, Locale locale) throws ScheduleException, ReadWriteException
	{
		ToolCommonChecker check = new ToolCommonChecker(conn);
		//#CM51988
		//<en>True if there is no error.</en>
		boolean errorFlag = true;
		String logfile = logpath +"/"+ ToolParam.getParam("CONSTRAINT_CHECK_FILE");
		
		try
		{
			LogHandler loghandle = new LogHandler(logfile, locale);


			ToolWarehouseSearchKey warehouseKey = new ToolWarehouseSearchKey() ;
			ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(conn) ;
			Warehouse[] warehouseArray = (Warehouse[])warehousehandle.find(warehouseKey) ;
			//#CM51989
			//<en>In case the Warehouse has not been set,</en>
			if(warehouseArray.length == 0)
			{
				//#CM51990
				//<en>6123182 = The warehouse has not been set.</en>
				loghandle.write("Warehouse", "Warehouse Table", "6123182");
				return false;
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

	//#CM51991
	/**<en>
	 * Process the parameter duplicate check.
 	 * Each storage type and the station no. must be unique data.
	 * It checks the duplication of parameter, then returns true if there was no duplicated data or 
	 * returns false if there were any duplication.<BR>
	 * Compare the data with ented data summary and with STATIOYTYPE table in order to check if there
	 * are no identical storage types or station no.<BR>
	 * In STATIONTYPE table checking, search is conducted over data excluded of specified station no. and 
	 * specified hander class in order to enable the data input in case of data modificaiton.<BR>
	 * If parameter duplicate check failed, its reason can be obtained by <code>getMessage</code>.
	 * @param conn  :Connection to connect with database
	 * @param param :parameter to check
	 * @return :returns true if the parameter duplicate check has succeeded, or returns false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
	 </en>*/
	public boolean duplicationCheck(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		Parameter[] mArray = (Parameter[])getParameters();
		WarehouseParameter mParam = (WarehouseParameter)param;

		//#CM51992
		//<en> Check the identical data.</en>
		if(isSameData(mParam, mArray))
		{
			//#CM51993
			//<en> Failure if any identical data is found.</en>
			return false;
		}

		//#CM51994
		//<en> Check to see if there are no identical data in STATIONTYPE table.</en>
		String newStationNo = mParam.getStationNumber();
		if(getToolWarehouseHandler(conn).isStationType( newStationNo,ToolWarehouseHandler.WAREHOUSE_HANDLE))
		{
			//#CM51995
			//<en>6123122 The data is laready registered; Cannot register the identical station no. </en>
			setMessage("6123122");
			return false;
		}
	
		//#CM51996
		//<en>Check only when modifing data.</en>
		if(getUpdatingFlag() != ScheduleInterface.NO_UPDATING)
		{
			//#CM51997
			//<en>*** Key items are not to be modifiable when modifing data. ***</en>
			int newWarehouseNo = mParam.getWarehouseNumber() ;
			int newWarehouseType = mParam.getWarehouseType() ;
			int newEmploymentType = mParam.getEmploymentType() ;

			//#CM51998
			//<en>Key items for enterd data summary (storage type, station no., warehouse type, </en>
			//<en>operation type of automated warehouse)</en>
			int orgWarehouseNo = 0;
			String orgStationNo = "";
			int orgWarehouseType = 0 ;
			int orgEmploymentType = 0 ;

			Parameter[] mAllArray = (Parameter[])getAllParameters();
			for (int i = 0 ; i < mAllArray.length ; i++)
			{
				WarehouseParameter castparam = (WarehouseParameter)mAllArray[i];
				//#CM51999
				//<en>Set the value of Key items preserved in entered data summary.</en>
				orgWarehouseNo = castparam.getWarehouseNumber();
				orgStationNo = castparam.getStationNumber();
				orgWarehouseType = castparam.getWarehouseType();
				orgEmploymentType = castparam.getEmploymentType();
				
				if( newWarehouseNo == orgWarehouseNo 
				 && newStationNo.equals(orgStationNo) 
				 && newWarehouseType == orgWarehouseType 
				 && newEmploymentType == orgEmploymentType )
				{
					return true;
				}
			}
		}
		return true;
	}

	//#CM52000
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
		//#CM52001
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM52002
		//<en>Registration</en>
		if(processingKind == M_CREATE)
		{
			if(!create(conn))
			{
				return false;
			}
			//#CM52003
			//<en>6121004 = Edited the data.</en>
			setMessage("6121004");
			return true;
		}
		//#CM52004
		//<en>Error with the process type.</en>
		else
		{
			//#CM52005
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM52006
			//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}
	
	//#CM52007
	// Package methods -----------------------------------------------

	//#CM52008
	// Protected methods ---------------------------------------------
	//#CM52009
	/**<en>
	 * Retrieve the <CODE>ToolWarehouseHandler</CODE> instance generated at the initialization of this class.
	 * @param conn :Connection to connect with database
	 * @return <CODE>ToolWarehouseHandler</CODE>
	 </en>*/
	protected ToolWarehouseHandler getToolWarehouseHandler(Connection conn)
	{
		return new ToolWarehouseHandler(conn);
	}
	
	//#CM52010
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
	//#CM52011
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

	//#CM52012
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
			WarehouseParameter castparam = null;
			if( mArray.length > 0)
			{
				//#CM52013
				//<en>Delete all data from the table. (from WAREHOUSE table and STATIONTYPE table)</en>
				getToolWarehouseHandler(conn).truncate();
				Warehouse warehouse = new Warehouse();
				for(int i = 0; i < mArray.length; i++)
				{
					castparam = (WarehouseParameter)mArray[i];

					warehouse.setWarehouseNumber(castparam.getWarehouseNumber());
					warehouse.setStationNumber(castparam.getStationNumber());
					warehouse.setName(castparam.getWarehouseName());
					warehouse.setWarehouseType(castparam.getWarehouseType());
					if( castparam.getWarehouseType() == Warehouse.AUTOMATID_WAREHOUSE )
					{
						//#CM52014
						//<en> Set up the input valud for the display in case of automated warehouse operation.</en>
						warehouse.setEmploymentType(castparam.getEmploymentType());
					}
					else
					{
						//#CM52015
						//<en> Set the specified value (1) for anything other than the automated warehouse.</en>
						warehouse.setEmploymentType( Warehouse.OPEN );
					}
					warehouse.setMaxMixedPalette(castparam.getMaxMixedQuantity());
					getToolWarehouseHandler(conn).create(warehouse);
				}
				return true;
			}
			//#CM52016
			//<en>If there is no data to process,</en>
			else
			{
				//#CM52017
				//<en>Delete all data from the table. (from WAREHOUSE table and STATIONTYPE table)</en>
				getToolWarehouseHandler(conn).truncate();
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
	}

	//#CM52018
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

	//#CM52019
	// Private methods -----------------------------------------------
	//#CM52020
	/**<en>
	 * Set the process type of selected item to delete to 'processed'. The actual deletion will 
	 * be done in daily transactions.
	 * Compares the ReStoring instance which has been set to maintenance parameter with this
	 * other ReStoring instance which has been retrieved from current DB.
	 * If the comparison resulted the both instances are equal, it regards that no modification was 
	 * made by other terminals. If the comparison resulted these instances to be different, then it 
	 * regards the other terminal already modified the data.
	 * @param ReStoringMaintenanceParameter
	 * @return :returns true if the data ghas already been modified by other terminals, or returns false
	 * if  it has not been modified.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	private boolean isAlreadyChanged(WarehouseParameter param) throws ReadWriteException
	{
		return false;
	}
	
	//#CM52021
	/**<en>
	 * Chech whether/not the storage type and the station no. to append exist in entered data summary.
	 * @param param  :the parameter which will be appended in this process
	 * @param array  :entered data summary (pool)
	 * @return       ;whether/not the identical data exist. (true: identical exist, false: it does not exist)
	 </en>*/
	private boolean isSameData(WarehouseParameter param, 
								Parameter[] array)
	{
		//#CM52022
		//<en>Key to compare</en>
		//#CM52023
		//<en>Value which is unused in normal processes</en> 
		int newWarehouseNo = 99999; 
		//#CM52024
		//<en>Value which is unused in normal processes</en> 
		int orgWarehouseNo = 99999; 
		String newStationNo = "";
		String orgStationNo = "";

		//#CM52025
		//<en>If there is the entered data summary,</en>
		if(array.length > 0)
		{
			//#CM52026
			//<en>Compare the dtorage type which is papended in this process.</en>
			newWarehouseNo = param.getWarehouseNumber();
			newStationNo = param.getStationNumber();
			
			for (int i = 0 ; i < array.length ; i++)
			{
				WarehouseParameter castparam = (WarehouseParameter)array[i];
				//#CM52027
				//<en>Key for the entered data summary</en>
				orgWarehouseNo = castparam.getWarehouseNumber();
				orgStationNo = castparam.getStationNumber();
				//#CM52028
				//<en>Check the identical storage type.</en>
				if ( newWarehouseNo == orgWarehouseNo )
				{
					//#CM52029
					//<en>6123015 = The data is already entered; Cannot enter the identical storage type.</en>
					setMessage("6123015");
					return true;
				}
				//#CM52030
				//<en>Check the identical station no. </en>
				if(newStationNo.equals(orgStationNo))
				{
					//#CM52031
					//<en>6123016 = The data is already entered; Cannot enter the identical station no.</en>
					setMessage("6123016");
					return true;
				}
			}
		}
		return false;
	}
	
	//#CM52032
	/**<en>
	 * Determine whether/not the data is modifiable.
	 * Conditions to check when modifing the warehouse.
	 *  - The data should not exist in STATION table.
     *  - The data should not exist in AISLE table.
     *  - The data should not exist in SHELF table.
     * @param conn :Connection to connect with database
	 * @param <code>WarehouseParameter</code>
	 * @return :return true if the data is modifiable.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private boolean isDeletable(Connection conn, WarehouseParameter param) throws ScheduleException
	{
		try
		{
			//#CM52033
			//<en> Retrieve the station no.</en>
			String warehouseStationNo = param.getStationNumber();

			//#CM52034
			//<en> Whether/not the data exists in STATION table.</en>
			ToolStationSearchKey stationKey = new ToolStationSearchKey();
			stationKey.setWareHouseStationNumber(warehouseStationNo);
			ToolStationHandler stationHandle = new ToolStationHandler(conn);
			Station[] stationArray = (Station[])stationHandle.find(stationKey);
			//#CM52035
			//<en> Cannot delete data if the Station exists.</en>
			if(stationArray.length > 0)
			{
				//#CM52036
				//<en> Cannot modify the warehouse({0}) as it exists in STATION table.</en>
				setMessage("6123150" + wDelim + warehouseStationNo);
				return false;
			}

			//#CM52037
			//<en> Whether/not the data exists in AISLE table.</en>
			ToolAisleSearchKey aisleKey = new ToolAisleSearchKey();
			aisleKey.setWareHouseStationNumber(warehouseStationNo);
			ToolAisleHandler aisleHandle = new ToolAisleHandler(conn);
			Aisle[] aisleArray = (Aisle[])aisleHandle.find(aisleKey);
			//#CM52038
			//<en> Cannot modify the data if the Station exists.</en>
			if(aisleArray.length > 0)
			{
				//#CM52039
				//<en> Cannot modify the warehouse({0}) as it exists in AISLE table.</en>
				setMessage("6123151" + wDelim + warehouseStationNo);
				return false;
			}

			//#CM52040
			//<en> Whether/not the data exists in SHELF table.</en>
			ToolShelfSearchKey shlefKey = new ToolShelfSearchKey();
			shlefKey.setWarehouseStationNumber(warehouseStationNo);
			ToolShelfHandler shelfHandle = new ToolShelfHandler(conn);
			Shelf[] shelfArray = (Shelf[])shelfHandle.find(shlefKey);
			//#CM52041
			//<en> Cannot modify the data if the Station exists.</en>
			if(shelfArray.length > 0)
			{
				//#CM52042
				//<en> Cannot modify the warehouse({0}) as it exists in SHELF table.</en>
				setMessage("6123152" + wDelim + warehouseStationNo);
				return false;
			}
			
			return true;
		}
		catch(ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM52043
	/**<en>
	 * Retrieve the Warehouse instance which were sorted in order of warehouse and warehouse station no..
	 * @return :the array of <code>Warehouse</code> object
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private Warehouse[] getWarehouseArray(Connection conn) throws ReadWriteException
	{
		ToolWarehouseSearchKey warehouseKey  = new ToolWarehouseSearchKey();
		warehouseKey.setWarehouseNumberOrder(1, true);
		warehouseKey.setWarehouseStationNumberOrder(2, true);
		//#CM52044
		//<en>*** Retrieve the Warehouse instance. ***</en>
		Warehouse[] array = (Warehouse[])getToolWarehouseHandler(conn).find(warehouseKey);
		return array;
	}
}
//#CM52045
//end of class

