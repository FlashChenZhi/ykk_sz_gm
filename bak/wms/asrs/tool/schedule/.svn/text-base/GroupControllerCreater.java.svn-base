// $Id: GroupControllerCreater.java,v 1.2 2006/10/30 02:52:05 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM50661
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
import jp.co.daifuku.wms.asrs.tool.location.Aisle;
import jp.co.daifuku.wms.asrs.tool.location.GroupControllerInformation;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ScheduleInterface;

//#CM50662
/**<en>
 * This class operates the group controller.
 * It inherits the AbstractCreater, and implements the processes requried for group controller.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:05 $
 * @author  $Author: suresh $
 </en>*/
public class GroupControllerCreater extends AbstractCreater
{
	//#CM50663
	// Class fields --------------------------------------------------
	//#CM50664
	// Class variables -----------------------------------------------
	
	//#CM50665
	/**<en> Status (off-line) </en>*/

	private int DEFAULTSTATUS = 2;
	
	//#CM50666
	/**<en> Port no.  </en>*/

	private int DEFAULTPORT = 2000;

	//#CM50667
	// Class method --------------------------------------------------
	//#CM50668
	/**<en>
	 * Retunrs the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:05 $") ;
	}

	//#CM50669
	// Constructors --------------------------------------------------
	//#CM50670
	/**<en>
	 * Delete parameter of the specified position.<BR>
	 * @param index :position of the deleting parameter
	 * @throws ScheduleException :Notifies if there are no parameters in specified position.
	 </en>*/
	public void removeParameter(Connection conn, int index) throws ScheduleException
	{
		//#CM50671
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
	//#CM50672
	/**<en>
	 * Delete all parameters.<BR>
	 * @throws ScheduleException :Notifies if there are no parameters in specified position.
	 </en>*/
	public void removeAllParameters(Connection conn) throws ScheduleException
	{
		//#CM50673
		//<en> Initialization of the message</en>
		setMessage("");

		wParamVec.removeAllElements();
		//#CM50674
		//<en>Deleted the data.</en>
		setMessage("6121003");
	}
	
	//#CM50675
	/**<en>
	 * Initialize this class. Generate the instance of <CODE>ReStoringHandler</CODE> at the initialization.
	 * @param conn :connection object with database
	 * @param kind :process type
	 </en>*/
	public GroupControllerCreater(Connection conn, int kind )
	{
		super(conn, kind);
	}

	//#CM50676
	// Public methods ------------------------------------------------
	//#CM50677
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
	
	//#CM50678
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
		GroupControllerInformation[] array = getGroupControllerArray(conn);
		//#CM50679
		//<en>Vector where the data will temporarily be stored</en>
		//#CM50680
		//<en>Set the max number of data as an initial value for entered data summary.</en>	
		Vector vec = new Vector(100);	
		//#CM50681
		//<en>Entity class for display</en>
		GroupControllerParameter dispData = null;
			if(array.length > 0)
			{
				for(int i = 0; i < array.length; i++)
				{
					dispData = new GroupControllerParameter();
					dispData.setControllerNumber(array[i].getControllerNumber());
					dispData.setIPAddress(array[i].getIPAddress());
					vec.addElement(dispData);
				}
				GroupControllerParameter[] fmt = new GroupControllerParameter[vec.size()];
				vec.toArray(fmt);
				return fmt;
			}
			return null;
	}

	//#CM50682
	/**<en>
	 * Processes the paramter check. It will be called when adding the parameter, before the 
	 * execution of maintenance process.
	 * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
	 * <number for the modification><BR>
	 *  -the number should be less than restorage quantity.
	 *  -the number should be less than standard load quantity.
	 * @param param :parameter to check
	 * @return :returns true if there is no error with parameter, or returns false if there are any errors.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
	 </en>*/
	public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		ToolCommonChecker check = new ToolCommonChecker(conn);
		GroupControllerParameter mParameter 
			= (GroupControllerParameter)param;
		//#CM50683
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM50684
		//<en>Registration</en>
		if(processingKind == M_CREATE)
		{
			//#CM50685
			//<en>Check the AGCNo.</en>
			if(!check.checkAgcNo(mParameter.getControllerNumber()))
			{
				//#CM50686
				//<en>Set the contents of the error.</en>
				setMessage(check.getMessage());
				return false;
			}
			//#CM50687
			//<en>Check teh host name.</en>
			if(!check.checkHostName(mParameter.getIPAddress()))
			{
				//#CM50688
				//<en>Set the contents of the error.</en>
				setMessage(check.getMessage());
				return false;
			}
			return true;
		}
		//#CM50689
		//<en>Error with the process type.</en>
		else
		{
			//#CM50690
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM50691
			//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}
	
	//#CM50692
	/**<en>
	 * Process the inconsistency check. This will be called when generating the eAWC environment setting tool.
	 * If any error takes place, the detail will be written in the file.
	 * @param logpath :path to place the file in which the log will be written when error occurred.
	 * @param locale <code>Locale</code> object
	 * @return :true if there is no error, or false if there are any error.
	 * @throws ScheduleException :Notifies if unexpected error occurred during the parameter duplicate check.
	 </en>*/
	public boolean consistencyCheck(Connection conn, String logpath, Locale locale) throws ScheduleException, ReadWriteException
	{
		//#CM50693
		//<en>True if there is no error.</en>
		boolean errorFlag = true;
		String logfile = logpath +"/"+ ToolParam.getParam("CONSTRAINT_CHECK_FILE");
		
		try
		{
			LogHandler loghandle = new LogHandler(logfile, locale);

			ToolGroupControllerSearchKey gcKey = new ToolGroupControllerSearchKey();
			GroupControllerInformation[] gcArray = (GroupControllerInformation[])getToolGroupControllerHandler(conn).find(gcKey);

			//#CM50694
			//<en>If GroupController has not been set,</en>
			if(gcArray.length == 0)
			{
				//#CM50695
				//<en>6123180 = The group controller has not been set.</en>
				loghandle.write("GroupController", "GroupController Table", "6123180");
				return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
	
		}
		return errorFlag;
	}

	//#CM50696
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
		GroupControllerParameter mParam = (GroupControllerParameter)param;
		//#CM50697
		//<en>Check the identical data.</en>
		if(isSameData(mParam, mArray))
		{
			return false;
		}
		
		//#CM50698
		//<en>Check only when modifing data.</en>
		if(getUpdatingFlag() != ScheduleInterface.NO_UPDATING)
		{
			//#CM50699
			//<en>*** Key items are not to be modifiable when modifing data. ***</en>
			int gcNo = mParam.getControllerNumber();
			
			//#CM50700
			//<en>Key items for enterd data summary</en>
			int orgGcNo = 0;
			
			Parameter[] mAllArray = (Parameter[])getAllParameters();
			for (int i = 0 ; i < mAllArray.length ; i++)
			{
				GroupControllerParameter castparam = (GroupControllerParameter)mAllArray[i];
				//#CM50701
				//<en>Key items</en>
				orgGcNo = castparam.getControllerNumber(); 
				//#CM50702
				//<en>Acceptable as the data is not modified.</en>
				if(gcNo == orgGcNo)
				{
					return true;
				}
			}

			return true;
		}
		return true;
	}

	//#CM50703
	/**<en>
	 * Conduct the maintenance process.
	 * It is necessary that type of the maintentace should be determined internally according to
	 * the process type (obtained by getProcessingKind() method.)
	 * It returns true if the maintenance process succeeded, or false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :returns true if the process succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	public boolean doStart(Connection conn) throws ReadWriteException, ScheduleException
	{
		//#CM50704
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM50705
		//<en>Registration</en>
		if(processingKind == M_CREATE)
		{
			if(!create(conn))
			{
				return false;
			}
			//#CM50706
			//<en>6121004 = Edited the data.</en>
			setMessage("6121004");
			return true;
		}
		//#CM50707
		//<en>Error with the process type.</en>
		else
		{
			//#CM50708
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM50709
			//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}

	//#CM50710
	// Package methods -----------------------------------------------

	//#CM50711
	// Protected methods ---------------------------------------------
	//#CM50712
	/**<en>
	 * Retrieve the <CODE>ReStoringHandler</CODE> instance generated at the initialization of this class.
	 * @return <CODE>ReStoringHandler</CODE>
	 </en>*/
	protected ToolGroupControllerHandler getToolGroupControllerHandler(Connection conn)
	{
		return new ToolGroupControllerHandler(conn);
	}
	
	//#CM50713
	/**<en>
	 * Retrieve the <CODE>FindUtil</CODE> instance generated at the initialization of this class.
	 * @return <CODE>FindUtil</CODE>
	 </en>*/
	protected ToolFindUtil getFindUtil(Connection conn)
	{
		return new ToolFindUtil(conn);
	}
	//#CM50714
	/**<en>
	 * Conduct the complementarity process of parameter.<BR>
	 *  - Append ReStoring instance to the parameter in ordder to check whether/not the data 
	 *    has been modified by other terminals.
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
	
	//#CM50715
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

	//#CM50716
	/**<en>
	 * Process the maintenance registrations. The scheduled restorage data is not registered in this process.
	 * Returns true if the maintenance process succeeded, or false if it failed.
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
			GroupControllerParameter castparam = null;
			if( mArray.length > 0)
			{
				//#CM50717
				//<en>Delete all data from the table.</en>
				getToolGroupControllerHandler(conn).truncate();
				GroupControllerInformation gcinfo = new GroupControllerInformation();
				for(int i = 0; i < mArray.length; i++)
				{
					castparam = (GroupControllerParameter)mArray[i];
					gcinfo.setControllerNumber(castparam.getControllerNumber());
					gcinfo.setStatus(DEFAULTSTATUS);
					gcinfo.setIPAddress(castparam.getIPAddress());
					gcinfo.setPort(DEFAULTPORT);
					getToolGroupControllerHandler(conn).create(gcinfo);
				}
				return true;
			}
			//#CM50718
			//<en>If there is no data to process,</en>
			else
			{
				//#CM50719
				//<en>Delete all data from the table.</en>
				getToolGroupControllerHandler(conn).truncate();
				return true;
			}
		}
		catch(DataExistsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM50720
	/**<en>
	 * Process the maintenance deletions.
	 * Deletion will be done based on the key items of parameter array. 
	 * Set the process type of selected item to delete to 'processed'. The acrual deletion will 
	 * be done in daily transactions.
	 * Returns true if the maintenance process succeeded, or false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :returns true if the process succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	protected boolean delete() throws ReadWriteException, ScheduleException
	{
		return true;
	}

	//#CM50721
	// Private methods -----------------------------------------------
	//#CM50722
	/**<en>
	 * Check whether/not thd data has already been modified by other terminals.
	 * Compares the ReStoring instance which has been set to maintenance parameter with the
	 * AWCUSER instance retrieved from current DB.
	 * If the comparison resulted the both instances are equal, it regards that no modification 
	 * was made by other terminals. If the comparison resulted these instances to be different, 
	 * then it regards the other terminal already modified the data.
	 * @param ReStoringMaintenanceParameter
	 * @return :returns true if the data ghas already been modified by other terminals, or returns false 
	 * if it has not been modified.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	private boolean isAlreadyChanged(GroupControllerParameter param) throws ReadWriteException
	{
		return false;
	}
	
	//#CM50723
	/**<en>
	 * Implement the check in order to see that the identical data has been selected when chosing data
	 * from the list box to edit.
	 * In the group contrller, it checks whether/not the storage type of the appending parameter
	 * exists in the entered data summary.
	 * @param param :the parameter which will be appended in this process
	 * @param array :entered data summary (pool)
	 * @return      :return true if identical data exists.
	 </en>*/
	private boolean isSameData(GroupControllerParameter param, 
								Parameter[] array)
	{
		//#CM50724
		//<en>Key to compare</en>
		//#CM50725
		//<en>Value which is unused in normal processes</en> 
		int newAgcNo = 99999; 
		//#CM50726
		//<en>Value which is unused in normal processes</en> 
		int orgAgcNo = 99999; 
		
		//#CM50727
		//<en>If there is the entered data summary,</en>
		if(array.length > 0)
		{
			//#CM50728
			//<en>Compare by the storage type appended in this process.</en>
			newAgcNo = param.getControllerNumber();
			
			for (int i = 0 ; i < array.length ; i++)
			{
				GroupControllerParameter castparam = (GroupControllerParameter)array[i];
				//#CM50729
				//<en>Key for the entered data summary</en>
				orgAgcNo = castparam.getControllerNumber();
				//#CM50730
				//<en>Check the identical AGCNo.</en>
				if ( newAgcNo == orgAgcNo )
				{
					//#CM50731
					//<en>6123046 = The data is already entered. Cannot input the identical AGC no.</en>
					setMessage("6123046");
					return true;
				}
			}
		}
		
		return false;
	}

	//#CM50732
	/**<en>
	 * Determine whether/not the data is modifiable.
	 * Conditions to check when modifing the group controller
     *  - The data should not exist in AISLE table.
     *  - The data should not exist in STATION table.
     *  - The data should not exist in MACHINE table.
	 * @param <code>GroupControllerParameter</code>
	 * @return :return true if the data is modifiable.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private boolean isChangeable(Connection conn, GroupControllerParameter param) throws ScheduleException
	{
		int reason = 0;
		reason = deletable(conn, param);
		int controllerNo = param.getControllerNumber();
		
		if(reason == 0)
		{
			return true;
		}
		else if(reason == 1)
		{
			//#CM50733
			//<en>Cannot delete data; the AGCNo.({0})exists in AISLE table.</en>
			setMessage("6123155" + wDelim + controllerNo);
			return false;
		}
		else if(reason == 2)
		{
			//#CM50734
			//<en>Cannot delete data; the AGCNo.({0})exists in STATION table.</en>
			setMessage("6123156" + wDelim + controllerNo);
			return false;
		}
		else if(reason == 3)
		{
			//#CM50735
			//<en>Cannot delete data; the AGCNo.({0})exists in MACHINE table.</en>
			setMessage("6123157" + wDelim + controllerNo);
			return false;
		}
		
		return true;
	}
	
	//#CM50736
	/**<en>
	 * Determines whether/not the data is deletable.
	 * Conditions to check when deleting the group controller
     *  -The data should not exist in AISLE table
     *  -The data should not not exist in STATION table
     *  -The data should not not exist in MACHINE table
	 * @param <code>GroupControllerParameter</code>
	 * @return :return true if the data is deletable.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private boolean isDeletable(Connection conn, GroupControllerParameter param) throws ScheduleException
	{
		int reason = 0;
		reason = deletable(conn, param);
		int controllerNo = param.getControllerNumber();
		
		if(reason == 0)
		{
			return true;
		}
		else if(reason == 1)
		{
			//#CM50737
			//<en>Cannot delete data; the AGCNo.({0})exists in AISLE table.</en>
			setMessage("6123155" + wDelim + controllerNo);
			return false;
		}
		else if(reason == 2)
		{
			//#CM50738
			//<en>Cannot delete data; the AGCNo.({0})exists in STATION table.</en>
			setMessage("6123156" + wDelim + controllerNo);
			return false;
		}
		else if(reason == 3)
		{
			//#CM50739
			//<en>Cannot delete data; the AGCNo.({0})exists in MACHINE table.</en>
			setMessage("6123157" + wDelim + controllerNo);
			return false;
		}
		
		return true;
	}
	
	//#CM50740
	//<en>Determine if deletion/modification of data is possible.</en>
	//<en>Deletion/modification of data can be processed if the return value is 0.</en>
	//<en>For all other case, appropriate messages will be set respectively by the call resource.</en>
	//#CM50741
	//<en>CMENJP7324$CMWhen the return value is 0, Deletion and the correction become possible. </en>
	//#CM50742
	//<en>Set the message which corresponds respectively </en>
	//#CM50743
	//<en>besides on the call side. </en>
private int deletable(Connection conn, GroupControllerParameter param) throws ScheduleException
	{
		try
		{	
			int controllerNo = param.getControllerNumber();

			ToolAisleSearchKey aisleKey = new ToolAisleSearchKey();
			ToolAisleHandler aisleHandle = new ToolAisleHandler(conn);
			aisleKey.setControllerNumber(controllerNo);
			Aisle[] aisleArray = (Aisle[])aisleHandle.find(aisleKey);
			//#CM50744
			//<en>Cannot delete data if it sexists in Aisle table.</en>
			if(aisleArray.length > 0)
			{
				return 1;
			}
			
			ToolStationSearchKey stationKey = new ToolStationSearchKey();
			ToolStationHandler stationHandle = new ToolStationHandler(conn);
			stationKey.setControllerNumber(controllerNo);
			Station[] stationArray = (Station[])stationHandle.find(stationKey);
			//#CM50745
			//<en>Cannot delete data if it exists in Station table.</en>
			if(stationArray.length > 0)
			{
				return 2;
			}
			
			ToolAs21MachineSearchKey machineKey = new ToolAs21MachineSearchKey();
			ToolAs21MachineStateHandler machineHandle = new ToolAs21MachineStateHandler(conn);
			machineKey.setControllerNumber(controllerNo);
			As21MachineState[] machineArray = (As21MachineState[])machineHandle.find(machineKey);
			//#CM50746
			//<en>Cannot delete data if it exists in Machine table.</en>
			if(machineArray.length > 0)
			{
				return 3;
			}
			
			return 0;
		}
		catch(ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM50747
	/**<en>
	 * Retrieve the GroupController instance.
	 * @return :the array of <code>GroupController</code> object
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private GroupControllerInformation[] getGroupControllerArray(Connection conn) throws ReadWriteException
	{
		ToolGroupControllerSearchKey GroupControllerKey  = new ToolGroupControllerSearchKey();
		GroupControllerKey.setControllerNumberOrder(1,true);
		
		//#CM50748
		//<en>*** Retrieve the GroupController instance ***</en>
		GroupControllerInformation[] array = (GroupControllerInformation[])getToolGroupControllerHandler(conn).find(GroupControllerKey);
		return array;
	}
}
//#CM50749
//end of class

