// $Id: MachineCreater.java,v 1.2 2006/10/30 02:52:03 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM51159
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
import jp.co.daifuku.wms.asrs.tool.communication.as21.As21MachineState;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAs21MachineSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAs21MachineStateHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;


//#CM51160
/**<en>
 * This class operates the setting of machine information.
 * It inherits the AbstractCreater, and implements the processes requried for the setting
 * of machine information.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:03 $
 * @author  $Author: suresh $
 </en>*/
public class MachineCreater extends AbstractCreater
{
	//#CM51161
	// Class fields --------------------------------------------------
	//#CM51162
	// Class variables -----------------------------------------------

	//#CM51163
	// Class method --------------------------------------------------
	//#CM51164
	 /**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:03 $") ;
	}

	//#CM51165
	// Constructors --------------------------------------------------
	//#CM51166
	/**<en>
	 * Initialize this class. Generate the instance of <CODE>ToolAs21MachineStateHandler</CODE>
	 * at the initialization.
	 * @param kind :process type
	 </en>*/
	public MachineCreater(Connection conn, int kind )
	{
		super(conn, kind);
	}

	//#CM51167
	// Public methods ------------------------------------------------
	//#CM51168
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
	//#CM51169
	/**<en>
	 * Retrieve the data to display on the screen.<BR>
	 * @param <code>Locale</code> object
	 * @param searchParam :search conditions
	 * @return :the array of schedule parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.<BR>
	 </en>*/
	public Parameter[] query(Connection conn, Locale locale, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		As21MachineState[] array = getAs21MachineStateArray(conn);
		//#CM51170
		//<en>Vector where the data will temporarily be stored</en>
		//#CM51171
		//<en>Set the max number of data as an initial value for entered data summary.</en>
		Vector vec = new Vector(100);	
		//#CM51172
		//<en>Entity class for display</en>
		MachineParameter dispData = null;

		if(array.length > 0)
		{
			for(int i = 0; i < array.length; i++)
			{
				dispData = new MachineParameter();
				dispData.setControllerNumber(array[i].getControllerNumber());
				dispData.setMachineType(array[i].getType());
				dispData.setMachineNumber(array[i].getNumber());
				dispData.setStationNumber(array[i].getStationNumber());
				dispData.setStationName(getFindUtil(conn).getStationName(array[i].getStationNumber()));
				vec.addElement(dispData);
			}
			MachineParameter[] fmt = new MachineParameter[vec.size()];
			vec.toArray(fmt);
			return fmt;
		}
		return null;
	}
	//#CM51173
	/**<en>
	 * Return the parameter array.
	 * @return :parameter array
	 </en>*/
//#CM51174
/*	public Parameter[] getParameters()
	{
		ReStoringMaintenanceParameter[] mArray 
			= new ReStoringMaintenanceParameter[wParamVec.size()];
		wParamVec.copyInto(mArray);
		return mArray;
	}
*/	
	//#CM51175
	/**<en>
	 * Processes the parameter check. It will be called when adding the parameter, before the 
	 * execution of maintenance process.
	 * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
	 * @param param :parameter to check
	 * @return :returns true if there is no error with parameter, or returns false if there are any errors.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
	 </en>*/
	public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		ToolCommonChecker check = new ToolCommonChecker(conn);
		MachineParameter mParameter = (MachineParameter)param;
		//#CM51176
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM51177
		//<en>Registration</en>
		switch(processingKind)
		{
			case M_CREATE:
				//#CM51178
				//<en> Check to see if the data is registered in GROUPCONTROLLER table.</en>
				ToolGroupControllerHandler gchandle = new ToolGroupControllerHandler(conn);
				ToolGroupControllerSearchKey gckey = new ToolGroupControllerSearchKey();

				if (gchandle.count(gckey) <= 0)
				{
					//#CM51179
					//<en> The information of the group controller cannot be found. </en>
					//<en> Please register in group controller setting screen.</en>
					setMessage("6123078");
					return false;
				}

				//#CM51180
				//<en> Check to see if the data is registered in station table.</en>
				ToolStationHandler sthandle = new ToolStationHandler(conn);
				ToolStationSearchKey stkey = new ToolStationSearchKey();

				if (sthandle.count(stkey) <= 0)
				{
					//#CM51181
					//<en> The information of the station cannot be found. </en>
					//<en> Please register in station setting screen.</en>
					setMessage("6123079");
					return false;
				}

				//#CM51182
				//<en> Check to see if the data is registered in aisle table.</en>
				ToolAisleHandler ahandle = new ToolAisleHandler(conn);
				ToolAisleSearchKey akey = new ToolAisleSearchKey();

				if (ahandle.count(akey) <= 0)
				{
					//#CM51183
					//<en> The information of the aisler cannot be found. </en>
					//<en> Please register in aisle setting screen.</en>
					setMessage("6123098");
					return false;
				}
				
				//#CM51184
				//<en>Check the station no.</en>
				if(!check.checkStationNumber(mParameter.getStationNumber()))
				{
					//#CM51185
					//<en>Set the contents of the error.</en>
					setMessage(check.getMessage());
					return false;
				}

				//#CM51186
				//<en> Check to see if the Station which was entered exists.</en>
				stkey.setNumber(mParameter.getStationNumber());
				if (sthandle.count(stkey) <= 0)
				{
					akey.setNumber(mParameter.getStationNumber());
					if (ahandle.count(stkey) <= 0)
					{
						//#CM51187
						//<en> The station no. which was jsut entered does not exist.</en>
						setMessage("6123080");
						return false;
					}
				}

				//#CM51188
				//<en> The machine no. of 0 or smaller value are invalid.</en>
				if (mParameter.getMachineNumber() <= 0)
				{
					//#CM51189
					//<en>Please enter 1 or greater value for the mahine no.</en>
					setMessage("6123211");
					return false;
				}
				
				//#CM51190
				//<en>Check to see if the AGCNo. and the station no. function as in tables</en>
				//#CM51191
				//<en>Check the STATION table.</en>
				ToolStationHandler stationhandle = new ToolStationHandler(conn) ;
				ToolStationSearchKey stationkey = new ToolStationSearchKey() ;
				stationkey.setNumber( mParameter.getStationNumber() ) ;
				stationkey.setControllerNumber( mParameter.getControllerNumber() ) ;
				//#CM51192
				//<en>Check the AISLE table.</en>
				ToolAisleHandler ailehandle = new ToolAisleHandler(conn) ;
				ToolAisleSearchKey ailekey = new ToolAisleSearchKey() ;
				ailekey.setNumber( mParameter.getStationNumber() ) ;
				ailekey.setControllerNumber( mParameter.getControllerNumber() ) ;
				if( stationhandle.count(stationkey) <= 0 && ailehandle.count(ailekey) <=0 )
				{
					//#CM51193
					//<en>AGC No.={0} and the station no. ={1} do not corporate properly.</en>
					setMessage("6123270" + wDelim + mParameter.getControllerNumber() + wDelim + mParameter.getStationNumber() );
					return false ;
				}

				

				break;
			default:
				//#CM51194
				//<en> Unexpected value has been set.{0} = {1}</en>
				String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
				RmiMsgLogClient.write( msg, (String)this.getClass().getName());
				//#CM51195
				//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
				throw new ScheduleException("6126499");	
		}

		return true;
	}

	//#CM51196
	/**<en>
	 * Process the inconsistency check. This will be called when generating the eAWC environment setting tool.
	 * If any error takes place, the detail will be written in the file which is specified by filename.
	 * @param filename :name of the file the log will be written in when error occurred.
	 * @param locale <code>Locale</code> object
	 * @return :true if there is no error, or false if there are any error.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public boolean consistencyCheck(Connection conn, String filename, Locale locale) throws ScheduleException, ReadWriteException
	{
		ToolCommonChecker check = new ToolCommonChecker(conn);
		//#CM51197
		//<en>True if there is no error.</en>
		boolean errorFlag = true;
		try
		{
			String checkfile = ToolParam.getParam("CONSTRAINT_CHECK_FILE");
			LogHandler loghandle = new LogHandler(filename + "/" +checkfile, locale);

			ToolAs21MachineSearchKey machineKey = new ToolAs21MachineSearchKey();

			int count = getToolAs21MachineStateHandler(conn).count(machineKey);
			//#CM51198
			//<en> If the machine information has not been set,</en>
			if(count <= 0)
			{
				//#CM51199
				//<en>The machine information has not been set.</en>
				loghandle.write("Machine", "Machine Table", "6123216");
				//#CM51200
				//<en> If the machine information has not been set, discontinue the checks and exit.</en>
				return false;
			}

			//#CM51201
			//<en>*** Check the group controller no. (Machine table) ***</en>
			//<en>Check to see if the group controller no. in Machine table exists </en>
			//<en>in the group controller table.</en>
			//#CM51202
			//<en>CMENJP7669$CM Whether controller No of the group in the Machine table exists in the table of the controller of the group is confirmed. </en>
		As21MachineState[] machineArray = (As21MachineState[])getToolAs21MachineStateHandler(conn).find(machineKey);
			for(int i = 0; i < machineArray.length; i++)
			{
				if(!check.isExistControllerNo(machineArray[i].getControllerNumber()))
				{
					loghandle.write("Machine", "Machine Table",check.getMessage());
					errorFlag = false;
				}
			}
			
			//#CM51203
			//<en>*** Check the station no. (Machine table) ***</en>
			//#CM51204
			//<en>Check to see if the station no. in Machine table exists in station table or in aisle table.</en>
			for(int i = 0; i < machineArray.length; i++)
			{
				String stationNo = machineArray[i].getStationNumber();
//#CM51205
// 2004.12.20 T.Yamashita UPD Start
//#CM51206
//				if(!check.isExistAllStationNo(stationNo))
				if(!check.isExistAllMachiniStationNo(stationNo))
//#CM51207
// 2004.12.20 T.Yamashita UPD End
				{
					loghandle.write("Machine", "Machine Table",check.getMessage());
					errorFlag = false;
				}
				if(!check.isExistStationType(stationNo))
				{
					loghandle.write("Machine", "Machine Table", check.getMessage());
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

	//#CM51208
	/**<en>
	 * Process the parameter duplicate check.
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
		MachineParameter mParam = (MachineParameter)param;

		//#CM51209
		//<en>Check the duplciate data.</en>
		if(isSameData(mParam, mArray))
		{
			return false;
		}
		return true;
	}

	//#CM51210
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
		//#CM51211
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM51212
		//<en>Registration</en>
		if(processingKind == M_CREATE)
		{
			if(!create(conn))
			{
				return false;
			}
			//#CM51213
			//<en>6121004 = Edited the data.</en>
			setMessage("6121004");
			return true;
		}
		//#CM51214
		//<en>Error with the process type.</en>
		else
		{
			//#CM51215
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM51216
			//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}

	//#CM51217
	// Package methods -----------------------------------------------

	//#CM51218
	// Protected methods ---------------------------------------------
	//#CM51219
	/**<en>
	 * Retrieve the <CODE>ReStoringHandler</CODE> instance generated at the initialization of this class.
	 * @return <CODE>ReStoringHandler</CODE>
	 </en>*/
	protected ToolAs21MachineStateHandler getToolAs21MachineStateHandler(Connection conn)
	{
		return new ToolAs21MachineStateHandler(conn);
	}
	
	//#CM51220
	/**<en>
	 * Retrieve the <CODE>FindUtil</CODE> instance generated at the initialization of this class.
	 * @return <CODE>FindUtil</CODE>
	 </en>*/
	protected ToolFindUtil getFindUtil(Connection conn)
	{
		return new ToolFindUtil(conn);
	}
	//#CM51221
	/**<en>
	 * Conduct the complementarity process of parameter.<BR>
	 * - Append ReStoring instance to the parameter in ordder to check whether/not the data 
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
	//#CM51222
	/**<en>
	 * Process the maintenance modifications.
	 * Modification will be made based on the key items of parameter array. 
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

	//#CM51223
	/**<en>
	 * Process the maintenance registrations.
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
			MachineParameter castparam = null;
			if( mArray.length > 0)
			{
				//#CM51224
				//<en>Delete all data from the table.</en>
				getToolAs21MachineStateHandler(conn).truncate();
				As21MachineState machine = new As21MachineState();
				for(int i = 0; i < mArray.length; i++)
				{
					castparam = (MachineParameter)mArray[i];
					machine.setControllerNumber(castparam.getControllerNumber());
					machine.setType(castparam.getMachineType());
					machine.setNumber(castparam.getMachineNumber());
					machine.setStationNumber(castparam.getStationNumber());
					machine.setState(As21MachineState.STATE_ACTIVE);

					getToolAs21MachineStateHandler(conn).create(machine);
				}
				return true;
			}
			//#CM51225
			//<en>If there is no data to process,</en>
			else
			{
				//#CM51226
				//<en>Delete all data from the table.</en>
				getToolAs21MachineStateHandler(conn).truncate();
				return true;
			}
		}
		catch(DataExistsException e)
		{
			//#CM51227
			//<en>6123016 = The data is already entered. Cannot input the identical station no.</en>
			setMessage("6123016");
			return false;
		}
	}

	//#CM51228
	/**<en>
	 * Process the maintenance deletions.
	 * Deletion will be done based on the key items of parameter array. 
	 * Set the process type of selected item to delete to 'processed'. The acrual deletion will 
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

	//#CM51229
	// Private methods -----------------------------------------------
	//#CM51230
	/**<en>
	 * Check whether/not thd data has already been modified by other terminals.
	 * Compares the ReStoring instance which has been set to maintenance parameter with the
	 * other Restoring instance whic has been retrieved from current DB.
	 * If the comparison resulted the both instances are equal, it regards that no modification 
	 * was made by other terminals. If the comparison resulted these instances to be different, then 
	 * it regards the other terminal already modified the data.
	 * @param ReStoringMaintenanceParameter
	 * @return :returns true if the data ghas already been modified by other terminals, or returns 
	 * false if it has not been modified.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	private boolean isAlreadyChanged(MachineParameter param) throws ReadWriteException
	{
		//#CM51231
		//2003/02/11 13:47:25
		//#CM51232
		//<en>In this version, the check for data updates made by terminals is not included in process. Kawasima</en>
		return false;
	}
	
	
	//#CM51233
	/**<en>
	 * Implement the check in order to see that the identical data has been selected when chosing 
	 * data from the list box to edit.
	 * In the warehouse setting, it checks whether/not the storage type of appending parameter exists 
	 * in the entered data summary.
	 * @param param  :the parameter which will be appended in this process
	 * @param array  :entered data summary (pool)
	 * @return    :return true if identical data exists.
	 </en>*/
	private boolean isSameData(MachineParameter param, Parameter[] array)
	{
		//#CM51234
		//<en>Key to compare</en>
		String newKey = "";
		String orgKey = "";

		//#CM51235
		//<en>If there is the entered data summary,</en>
		if(array.length > 0)
		{
			//#CM51236
			//<en>Compare by the storage type appended in this process.</en>
			newKey = Integer.toString(param.getMachineType())
				   + Integer.toString(param.getMachineNumber())
				   + Integer.toString(param.getControllerNumber());

			for (int i = 0 ; i < array.length ; i++)
			{
				MachineParameter castparam = (MachineParameter)array[i];
				//#CM51237
				//<en>Key for the entered data summary</en>
				orgKey = Integer.toString(castparam.getMachineType())
					   + Integer.toString(castparam.getMachineNumber())
					   + Integer.toString(castparam.getControllerNumber());
				
				//#CM51238
				//<en>Check the identical user name</en>
				if(newKey.equals(orgKey))
				{
					//#CM51239
					//<en>6123192 = The data is already entered. Cannot input the identical AGCNo.</en>
					//<en>machine code and machine no.</en>
					setMessage("6123192");
					return true;
				}
			}
		}

		return false;
	}

	//#CM51240
	/**<en>
	 * Retrieve the As21MachineState instance.
	 * @return :the array of <code>As21MachineState</code> object
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private As21MachineState[] getAs21MachineStateArray(Connection conn) throws ReadWriteException
	{
		ToolAs21MachineSearchKey machineKey  = new ToolAs21MachineSearchKey();
		machineKey.setControllerNumberOrder(1,true);
		machineKey.setTypeOrder(2,true);
		machineKey.setNumberOrder(3,true);

		//#CM51241
		//<en>*** Retrieve the As21MachineState of instance  ***</en>
		As21MachineState[] array = (As21MachineState[])getToolAs21MachineStateHandler(conn).find(machineKey);
		return array;
	}
}
//#CM51242
//end of class

