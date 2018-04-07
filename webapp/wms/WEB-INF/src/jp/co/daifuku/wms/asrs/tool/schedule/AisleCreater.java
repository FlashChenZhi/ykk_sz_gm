// $Id: AisleCreater.java,v 1.2 2006/10/30 02:52:07 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM50153
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
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.communication.as21.As21MachineState;
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAs21MachineSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAs21MachineStateHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolBankHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolGroupControllerSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Aisle;
import jp.co.daifuku.wms.asrs.tool.location.Bank;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ScheduleInterface;

//#CM50154
/**<en>
 * This class processes the setting of the aisle.
 * It inherits the AbstractCreater and implements rewuried process to set the warehouse.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>okamura</TD><TD>Changed the value to set as soft zone IDs.<BR>
 * Modified the former process of setting "0" to current "-1" as soft zone ID.
 * <TR><TD></TD><TD></TD><TD>Added the inconsistency checks.<BR>
 * If the soft zone ID has not been set to the location, the inconsistency check will 
 * result unacceptable due to this change.
 * </TD></TR>
 * <TR><TD>2003/12/16</TD><TD>okamura</TD><TD>Changed the modifiable/deletabl conditions.<BR>
 * Correted so that the conditions are modifiable/deletable even if the zone ID us set in SHELF.
 * </TD></TR>
 * <TR><TD></TD><TD></TD><TD>Deleted the nurequired method.<BR>
 * Deleted the setEndLocation and setStartLocation methods that are no longer in use.<BR>
 * Also deleted import that is not necessary.
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:07 $
 * @author  $Author: suresh $
 </en>*/
public class AisleCreater extends AbstractCreater
{
	//#CM50155
	// Class fields --------------------------------------------------
	//#CM50156
	// Class variables -----------------------------------------------
	//#CM50157
	/**<en>
	 * Product no. folder
	 </en>*/
	public String wFilePath = "";

	//#CM50158
	// Class method --------------------------------------------------
	//#CM50159
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:07 $") ;
	}

	//#CM50160
	// Constructors --------------------------------------------------
	//#CM50161
	/**<en>
	 * Delete the parameter of the specified position. <BR>
	 * @param index :position of the parameter to delete
	 * @throws ScheduleException :Notifies if there are no parameters in specified position.
	 </en>*/
	public void removeParameter(Connection conn, int index) throws ScheduleException
	{
		//#CM50162
		//<en> Initialization of the messages</en>
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
	//#CM50163
	/**<en>
	 * Delete all parameters.<BR>
	 * @throws ScheduleException :Notifies if there are no parameters in specified position.
	 </en>*/
	public void removeAllParameters(Connection conn) throws ScheduleException
	{
		//#CM50164
		//<en> Initialization of the messages</en>
		setMessage("");

		wParamVec.removeAllElements();
		//#CM50165
		//<en>Deleted.</en>
		setMessage("6121003");
	}

	//#CM50166
	/**<en>
	 * Initialize this class. Generate the <CODE>ReStoringHandler</CODE> instance at the initialization.
	 * @param conn :connection object with database
	 * @param kind :process type
	 </en>*/
	public AisleCreater(Connection conn, int kind )
	{
		super(conn, kind);
	}

	//#CM50167
	// Public methods ------------------------------------------------
	//#CM50168
	/**<en>
	 * Implements the process to run when the print-issue button was pressed on the display.<BR>
	 * @param <code>Locale</code> object
	 * @param listParam :schedule parameters
	 * @return :result print job
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
	 </en>*/
	public boolean print(Locale locale, Parameter listParam) throws ReadWriteException, ScheduleException
	{
		return true;
	}
	
	//#CM50169
	/**<en>
	 * Retrieve the data to display on the screen.<BR>
	 * @param <code>Locale</code> object
	 * @param searchParam :sesarch conditions
	 * @return :the array of the schedule parameters
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
	 </en>*/
	public Parameter[] query(Connection conn, Locale locale, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		Aisle[] array = getAisleArray(conn);
		//#CM50170
		//<en>:Vector where the data will temporarily be stored</en>
		//#CM50171
		//<en>Set the max. data to enter in entered data summary as initial value.</en>
		Vector vec = new Vector(100);	
		//#CM50172
		//<en>entity class to display</en>
		AisleParameter dispData = null;
		//#CM50173
		//<en>range</en>
		int[] range = new int[2];
		//#CM50174
		//<en>Set the file name here as the file names cannot be passed from external.</en>
		//#CM50175
		//<en>CMENJP6861$CM set here..</en>
		wFilePath =  ((AisleParameter)searchParam).getFilePath();
		
		if(array.length > 0)
		{
			for(int i = 0; i < array.length; i++)
			{
				dispData = new AisleParameter();
				String warehouseStationNo = array[i].getWarehouseStationNumber();
				int warehouseNo = getFindUtil(conn).getWarehouseNumber(warehouseStationNo);
				dispData.setWarehouseNumber(warehouseNo);
				dispData.setAisleStationNumber(array[i].getNumber());
				dispData.setAisleNumber(array[i].getAisleNumber());

				GroupController gc = array[i].getGroupController();
				dispData.setAGCNumber(gc.getNumber());
				//#CM50176
				//<en>Bank range</en>
				range = getToolShelfHandler(conn).getBankRange(warehouseStationNo, array[i].getAisleStationNumber());
				dispData.setSBank(range[0]);
				dispData.setEBank(range[1]);

				//#CM50177
				//<en>BAY range</en>
				range = getToolShelfHandler(conn).getBayRange(warehouseStationNo, array[i].getAisleStationNumber());
				dispData.setSBay(range[0]);
				dispData.setEBay(range[1]);

				//#CM50178
				//<en>LEVEL range</en>
				range = getToolShelfHandler(conn).getLevelRange(warehouseStationNo, array[i].getAisleStationNumber());
				dispData.setSLevel(range[0]);
				dispData.setELevel(range[1]);

				//#CM50179
				//Aisle position
				if (array[i].getDoubleDeepKind() == Aisle.DOUBLE_DEEP)
				{
					range = getToolShelfHandler(conn).getAislePostion(warehouseStationNo, array[i].getAisleStationNumber());
					dispData.setSAislePosition(range[0]);
					dispData.setEAislePosition(range[1]);
				}
				vec.addElement(dispData);
			}
			AisleParameter[] fmt = new AisleParameter[vec.size()];

			vec.toArray(fmt);
			return fmt;
		}
		
		return null;
	}
	
	//#CM50180
	/**<en>
	 * Processes the paramter check. It will be called when adding the parameter, before the execution 
	 * of maintenance process.
	 * If parameter check failed, its reason can be obtained by <code>getMessage</code>.
	 * @param param :parameter to check
	 * @return :returns true if there is no error with parameter, or returns false if there are any errors.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		ToolCommonChecker check = new ToolCommonChecker(conn);
		AisleParameter mParameter 
			= (AisleParameter)param;
		//#CM50181
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM50182
		//<en>Registeration</en>
		if(processingKind == M_CREATE)
		{
			//#CM50183
			//<en> Check to see if the data is registered in the warehouse table.</en>
			ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(conn);
			ToolWarehouseSearchKey warehosuekey = new ToolWarehouseSearchKey();

			if (warehousehandle.count(warehosuekey) <= 0)
			{
				//#CM50184
				//<en> 6123117 = There is no warehous control data. Please register in the setting </en>
				//<en> display of warehouses.</en>
				setMessage("6123117");
				return false;
			}
			//#CM50185
			//<en>Check the aisle no.</en>
			if(!check.checkAisleNo(mParameter.getAisleNumber()))
			{
				//#CM50186
				//<en>Set the contents of error.</en>
				setMessage(check.getMessage());
				return false;
			}
			//#CM50187
			//<en> Check to see of the data is registered in GROUPCONTROLLER table.</en>
			ToolGroupControllerHandler gchandle = new ToolGroupControllerHandler(conn);
			ToolGroupControllerSearchKey gckey = new ToolGroupControllerSearchKey();

			if (gchandle.count(gckey) <= 0)
			{
				//#CM50188
				//<en> There is no group controller data. Please register in the setting diplay of </en>
				//<en> group controller.</en>
				setMessage("6123078");
				return false;
			}
			
			//#CM50189
			//<en>Check the bank.</en>
			if(!check.checkBank(mParameter.getSBank(), mParameter.getEBank()))
			{
				//#CM50190
				//<en>Set the contents of error.</en>
				setMessage(check.getMessage());
				return false;
			}
			//#CM50191
			//<en>Check the bay.</en>
			if(!check.checkBay(mParameter.getSBay(), mParameter.getEBay()))
			{
				//#CM50192
				//<en>Set the contents of error.</en>
				setMessage(check.getMessage());
				return false;
			}
			//#CM50193
			//<en>Check the level.</en>
			if(!check.checkLevel(mParameter.getSLevel(), mParameter.getELevel()))
			{
				//#CM50194
				//<en>Set the contents of error.</en>
				setMessage(check.getMessage());
				return false;
			}

			//#CM50195
			//Check on range of Bank
			if(mParameter.getEBank() - mParameter.getSBank() > 3)
			{
				//#CM50196
				//6123279 = 4Bank or more cannot be set within the range of Bank. 
				setMessage("6123279");
				return false;
			}
			
			//#CM50197
			//In case of Double deep
			if (isDoubleDeep(mParameter.getSBank(), mParameter.getEBank()))
			{				
				//#CM50198
				//<en>Check the aisle position.</en>
				if(!check.checkAislePosition(mParameter.getSAislePosition(), mParameter.getEAislePosition()))
				{
					//#CM50199
					//<en>Set the contents of error.</en>
					setMessage(check.getMessage());
					return false;
				}
				
				//#CM50200
				//Whether Aisle position is set within the range of Bank is checked. 
				if(mParameter.getSAislePosition() < mParameter.getSBank()
						|| mParameter.getSAislePosition() > mParameter.getEBank())
				{
					//#CM50201
					//6123281= The input of Aisle position is illegal. 
					setMessage("6123281");
					return false;				
				}
				if(mParameter.getEAislePosition() < mParameter.getSBank()
						|| mParameter.getEAislePosition() > mParameter.getEBank())
				{
					//#CM50202
					//6123281= The input of Aisle position is illegal. 
					setMessage("6123281");
					return false;				
				}
				
				if(mParameter.getSAislePosition() - mParameter.getSBank() > 1)
				{
					//#CM50203
					//6123281= The input of Aisle position is illegal. 
					setMessage("6123281");
					return false;				
				}

				if(mParameter.getEBank() - mParameter.getEAislePosition() > 1)
				{
					//#CM50204
					//6123281= The input of Aisle position is illegal. 
					setMessage("6123281");
					return false;				
				}
				
			}			
			
			return true;
		}
		//#CM50205
		//<en>Error in process type.</en>
		else
		{
			//#CM50206
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM50207
			//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}	
	
	//#CM50208
	/**<en>
	 * Process the inconsistency check. This will be called when generating the setting tool for eAWC.
	 * If there are any errors, the detail will be written into the file.
	 * @param logpath :path to place the file to erite the log when error occurred.
	 * @param locale <code>Locale</code>object
	 * @return :returns true if there are no errors, or false if there is 1 or more errors.
	 * @throws ScheduleException :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	public boolean consistencyCheck(Connection conn, String logpath, Locale locale) throws ScheduleException, ReadWriteException
	{
		ToolCommonChecker check = new ToolCommonChecker(conn);
		//#CM50209
		//<en>True will be given if there is no error.</en>
		boolean errorFlag = true;
		String logfile = logpath +"/"+ ToolParam.getParam("CONSTRAINT_CHECK_FILE");
		
		try
		{
			LogHandler loghandle = new LogHandler(logfile, locale);

			ToolAisleSearchKey aisleKey = new ToolAisleSearchKey();
			Aisle[] aisleArray = (Aisle[])getToolAisleHandler(conn).find(aisleKey);
			GroupController controller = null;

			//#CM50210
			//<en>In case the Aisle has not been set:</en>
			if(aisleArray.length == 0)
			{
				//#CM50211
				//<en>6123179 = The aisle has not been set.</en>
				loghandle.write("Aisle", "Aisle Table", "6123179");
				//#CM50212
				//<en>If the aisle has not been set, it discontinue the check and exits the process.</en>				
				return false;
			}

			//#CM50213
			//<en>*** Check for the group controller no. (in Aisle table) ***</en>
			//<en>Check to see if the group controller no. in the AISLE table exists in </en>
			//<en>the group controller table.</en>
			//#CM50214
			//<en>CMENJP6890$CM Whether controller No of the group in the AISLE table exists in the table of the controller of the group is confirmed. </en>
		for(int i = 0; i < aisleArray.length; i++)
			{
				controller = aisleArray[i].getGroupController();
				if(!check.isExistControllerNo(controller.getNumber()))
				{
					loghandle.write("Aisle", "Aisle Table",check.getMessage());
					errorFlag = false;
				}
			}

			//#CM50215
			//<en>*** Check for warehouse station no. (in Aisle table) ***</en>
			//<en>Check to see if the warehouse station no. in the AISLE table exists in </en>
			//<en>the WAREHOUSE table and the STATIONTYPE table.</en>
			//#CM50216
			//<en>CMENJP6891$CM Whether Warehouse Station No in the AISLE table exists in the WAREHOUSE table and the STATIONTYPE table is confirmed. </en>
		for(int i = 0; i < aisleArray.length; i++)
			{
				String warehouseStationNo = aisleArray[i].getWarehouseStationNumber();
				if(!check.isExistAutoWarehouseStationNo(warehouseStationNo))
				{
					loghandle.write("Aisle", "Aisle Table", check.getMessage());
					errorFlag = false;
				}
				
				if(!check.isExistStationType(warehouseStationNo))
				{
					loghandle.write("Aisle", "Aisle Table", check.getMessage());
					errorFlag = false;
				}
			}
			
			//#CM50217
			//<en>Check to see if the warehouse station no. in the SHELF table exists in </en>
			//#CM50218
			//<en>the WAREHOUSE table.</en>
			ToolShelfSearchKey shelfKey = new ToolShelfSearchKey();
			Shelf[] shelfArray = (Shelf[])getToolShelfHandler(conn).find(shelfKey);
			String Last_warehouseStationNo = "";
			for(int i = 0; i < shelfArray.length; i++)
			{
				String warehouseStationNo = shelfArray[i].getWarehouseStationNumber();
				if ( ! Last_warehouseStationNo.equals(warehouseStationNo) ) 
				{
					if(!check.isExistWarehouseStationNo(warehouseStationNo))
					{
						loghandle.write("Aisle", "Shelf Table",check.getMessage());
						errorFlag = false;
					}
					if(!check.isExistStationType(warehouseStationNo))
					{
						loghandle.write("Aisle", "Shelf Table warehouse", check.getMessage());
						errorFlag = false;
					}
				Last_warehouseStationNo=warehouseStationNo;
				}
			}

			//#CM50219
			//<en>*** Check for the aisle station no. (in Shelf table) ***</en>
			//#CM50220
			//<en>Check to see if the parent station no. in the Shelf table exists in </en>
			//<en>the Aisle table.</en>
			String Last_parentStationNo = "";
			for(int i = 0; i < shelfArray.length; i++)
			{
				String parentStationNo = shelfArray[i].getParentStationNumber();
				if ( ! Last_parentStationNo.equals(parentStationNo) ) 
				{
					if(!check.isExistAisleStationNo(parentStationNo))
					{
						loghandle.write("Aisle", "Shelf Table",check.getMessage());
						errorFlag = false;
					}
					if(!check.isExistStationType(parentStationNo))
					{
						loghandle.write("Aisle", "Shelf Table", check.getMessage());
						errorFlag = false;
					}
				Last_parentStationNo=parentStationNo;
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

	//#CM50221
	/**<en>
	 * Processes the parameter duplicate check.
 	 * Data of identicla aisle station no. and aisle no. cannot be registered in entered data summary.
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
		AisleParameter mParam = (AisleParameter)param;
		//#CM50222
		//<en>Identical data check</en>
		if(isSameData(mParam, mArray))
		{
			return false;
		}
		String stationNo = mParam.getAisleStationNumber();
		
		if(getToolAisleHandler(conn).isStationType(stationNo, ToolAisleHandler.AISLE_HANDLE))
		{
			//#CM50223
			//<en>6123122 The data is already registered. Cannot register teh identical station no.</en>
			setMessage("6123122");
			return false;
		}

		//#CM50224
		//<en>Check only when modifing the data.</en>
		if(getUpdatingFlag() != ScheduleInterface.NO_UPDATING)
		{
			//#CM50225
			//<en>*** In the modification process, key items are regarded fixed and not able to change. ***</en>
			int warehouseNo = mParam.getWarehouseNumber();
			int aisleNo = mParam.getAisleNumber();
			int gcNo = mParam.getAGCNumber();
			int sbank = mParam.getSBank();
			int sbay = mParam.getSBay();
			int slevel = mParam.getSLevel();
			int ebank = mParam.getEBank();
			int ebay = mParam.getEBay();
			int elevel = mParam.getELevel();
						
			//#CM50226
			//<en>Key items in the entered data summary</en>
			int orgWarehouseNo = 0;
			String orgAisleStationNo = "";
			int orgAisleNo = 0;
			int orgGcNo = 0;
			int orgSbank = 0;
			int orgSbay = 0;
			int orgSlevel = 0;
			int orgEbank = 0;
			int orgEbay = 0;
			int orgElevel = 0;

			Parameter[] mAllArray = (Parameter[])getAllParameters();
			for (int i = 0 ; i < mAllArray.length ; i++)
			{
				AisleParameter castparam = (AisleParameter)mAllArray[i];
				//#CM50227
				//<en>Key items</en>
				orgWarehouseNo = castparam.getWarehouseNumber();
				orgAisleStationNo = castparam.getAisleStationNumber();
				orgAisleNo = castparam.getAisleNumber();
				orgGcNo = castparam.getAGCNumber(); 
				//#CM50228
				//<en>Accetable as they are not modified.</en>
				if(warehouseNo == orgWarehouseNo && 
					stationNo.equals(orgAisleStationNo) &&
					aisleNo == orgAisleNo &&
					gcNo == orgGcNo)
				{
					return true;
				}
			}

			return true;
		}
		return true;
	}

	//#CM50229
	/**<en>
	 * Conducts the maintenance process.
	 * It is necessary that type of the maintentace should be determined internally according to
	 * the process type (obtained by getProcessingKind() method.)
	 * Return true if maintenance process succeeded, or false if failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :true if maintenance process succeeded, or false if failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	public boolean doStart(Connection conn) throws ReadWriteException, ScheduleException
	{
		//#CM50230
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM50231
		//<en>Registeration</en>
		if(processingKind == M_CREATE)
		{
			if(!create(conn))
			{
				return false;
			}
			//#CM50232
			//<en>6121004 = Teh data is edited.</en>
			setMessage("6121004");
			return true;
		}
		//#CM50233
		//<en>Error is given as the process type.</en>
		else
		{
			//#CM50234
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM50235
			//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}

	//#CM50236
	// Package methods -----------------------------------------------

	//#CM50237
	// Protected methods ---------------------------------------------
	//#CM50238
	/**<en>
	 * Retrieve the <CODE>ToolShelfHandler</CODE> instance which has been generated 
	 * at the initialization of this class.
	 * @return :CODE>ToolShelfHandler</CODE>
	 </en>*/
	protected ToolShelfHandler getToolShelfHandler(Connection conn)
	{
		return new ToolShelfHandler(conn);
	}
	//#CM50239
	/**<en>
	 * Retrieve the <CODE>ToolAisleHandler</CODE> instance which has been generated 
	 * at the initialization of this class.
	 * @return <CODE>ToolAisleHandler</CODE>
	 </en>*/
	protected ToolAisleHandler getToolAisleHandler(Connection conn)
	{
		return new ToolAisleHandler(conn);
	}
	
	//#CM50240
	/**<en>
	 * Retrieve the <CODE>FindUtil</CODE> instance which has been generated 
	 * at the initialization of this class.
	 * @return <CODE>FindUtil</CODE>
	 </en>*/
	protected ToolFindUtil getFindUtil(Connection conn)
	{
		return new ToolFindUtil(conn);
	}
	//#CM50241
	/**<en>
	 * Conducts the complementarity process of parameter.<BR>
	 * - Add ReStoring instance to the parameter in ordder to check whether/not the data 
	 *   has been modified by other terminals.
	 * It returns the complemented parameter if the process succeeded, or returns false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @param param : parameter which is used for the complementarity process
	 * @return :returns the parameter if the process succeeded, ro returns null if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the process.
	 </en>*/
	protected  Parameter complementParameter(Parameter param)throws ReadWriteException, ScheduleException
	{
		return param;
	}
	//#CM50242
	/**<en>
	 * Processes the maintenance modifications.
	 * Modification will be made based on the key items of parameter array. 
	 * Sets the work no., item code and lot no. to the AlterKey as update values.
	 * It returns true if the maintenance process succeeded, or false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :true if the process succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	protected boolean modify() throws ReadWriteException, ScheduleException
	{
		return true;
	}

	//#CM50243
	/**<en>
	 * Processe the maintenance registrations. The rscheduled restorage data will not be registered.
	 * Return true if the maintenance process succeeded, or false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :true if the process succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	protected boolean create(Connection conn) throws  ScheduleException
	{
		try
		{
			Parameter[] mArray = (Parameter[])getAllParameters();
			if( mArray.length > 0)
			{
				//#CM50244
				//<en>Update the Aisle table.</en>
				updateAilseTable(conn, mArray);
				//#CM50245
				//<en>Update the Shelf table.</en>
				updateShelfTable(conn, mArray);
				//#CM50246
				//<en>Update the BankSelect table.</en>
				updateBankSelectTable(conn, mArray);
				return true;
			}
			//#CM50247
			//<en>If there is no data to process,</en>
			else
			{
				//#CM50248
				//<en>Delete all data from the table.</en>
				getToolAisleHandler(conn).truncate();
				//#CM50249
				//<en>Delete all data in Shelf table.</en>
				getToolShelfHandler(conn).truncate();
				
				ToolBankHandler bankHandler = new ToolBankHandler(conn);
				bankHandler.truncate();
			
				return true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM50250
	/**<en>
	 * Process the maintenance deletions.
	 * Deletion will be done based on teh key items of parameter array. 
	 * Set the process type of selected item to delete to 'processed'. The acrual deletion will be
	 * done in daily transactions.
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

	//#CM50251
	// Private methods -----------------------------------------------
	
	//#CM50252
	/**<en>
	 * Checks whether/not thd data has already been modified by other terminals.
	 * Compares the ReStoring instance which has been set to maintenance parameter with the 
	 * other ReStoring instance retrieved from current DB.
	 * If the comparison resulted the both instances are equal, it regards that no modification 
	 * was made by other terminals. If the comparison resulted these instances to be different, 
	 * then it regards the other terminal already modified the data.
	 * @param ReStoringMaintenanceParameter
	 * @return :returns true if the data ghas already been modified by other terminals, or returns 
	 * false if it has not been modified.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	private boolean isAlreadyChanged(WarehouseParameter param) throws ReadWriteException
	{
		return false;
	}
	
	//#CM50253
	/**<en>
	 * Update the AISLE table.
	 * @param array :entered data summary
	 </en>*/
	private void updateAilseTable(Connection conn, Parameter[] mArray) throws ReadWriteException, ScheduleException
	{
		try
		{
			AisleParameter castparam = null;
			//#CM50254
			//<en>Delete all data in Aisle table.</en>
			getToolAisleHandler(conn).truncate();
			
			for(int i = 0; i < mArray.length; i++)
			{
				castparam = (AisleParameter)mArray[i];

				//#CM50255
				//<en>Aisle station no.</en>
				Aisle aisle = new Aisle(castparam.getAisleStationNumber());
				//#CM50256
				//<en>Aisle no.</en>
				aisle.setAisleNumber(castparam.getAisleNumber());
				//#CM50257
				//<en>Warehouse station no.</en>
				String whstno = getFindUtil(conn).getWarehouseStationNumber(castparam.getWarehouseNumber());
				aisle.setWarehouseStationNumber(whstno);
				//#CM50258
				//<en>AGCNo</en>
				GroupController gc = new GroupController(conn, castparam.getAGCNumber());
				aisle.setGroupController(gc);

				//#CM50259
				//<en>Double deep type </en>
				int doubledeepKind = Aisle.SINGLE_DEEP;

				if (isDoubleDeep(castparam.getSBank(), castparam.getEBank()))
				{
					doubledeepKind = Aisle.DOUBLE_DEEP;
				}
				aisle.setDoubleDeepKind(doubledeepKind);
				//#CM50260
				//<en>Status (unavailable)</en>
				aisle.setStatus(Station.STATION_NG);
				//#CM50261
				//<en>inventory checking flag (unprocessed)</en>
				aisle.setInventoryCheckFlag(Station.NOT_INVENTORYCHECK);

				//#CM50262
				//<en>Create</en>
				getToolAisleHandler(conn).create(aisle);
			}
		}
		catch(InvalidStatusException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		catch(DataExistsException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM50263
	/**<en>
	 * Update the SHELF table.
	 * @param array :entered data summary
	 </en>*/
	private void updateShelfTable(Connection conn, Parameter[] mArray) throws ReadWriteException, ScheduleException
	{
		try
		{
			AisleParameter castparam = null;
			//#CM50264
			//<en>Delete all data in Shelf table.</en>
			getToolShelfHandler(conn).truncate();

			//#CM50265
			//<en>Loop for pooling entered data</en>
			for(int i = 0; i < mArray.length; i++)
			{
				castparam = (AisleParameter)mArray[i];
				
				int[] bankArray = getLocationArray(castparam.getSBank(), castparam.getEBank());
				int[] bayArray = getLocationArray(castparam.getSBay(), castparam.getEBay());
				int[] levelArray = getLocationArray(castparam.getSLevel(), castparam.getELevel());
				
				for(int j = 0; j<bankArray.length; j++)
				{
					int priority = 0;
					int side = Bank.NEAR;
					int pairBank = 0;
					boolean doubledeepKind = isDoubleDeep(castparam.getSBank(), castparam.getEBank());
					//#CM50266
					//In case of Double deep
					if (doubledeepKind)
					{
						//#CM50267
						//The priority order acquisition
						priority = getPriority(bankArray[j], castparam.getSBank(), castparam.getEBank(), castparam.getSAislePosition(), castparam.getEAislePosition());
						
						//#CM50268
						//Units of location
						if (bankArray[j] != castparam.getSAislePosition() 
									&& bankArray[j] != castparam.getEAislePosition())
						{
							//#CM50269
							//Interior
							side = Bank.FAR;
						}
						
						//#CM50270
						//Bank of Bay Location is acquired. 
						pairBank = getPairBank(bankArray[j], castparam.getSBank(), castparam.getEBank(), castparam.getSAislePosition(), castparam.getEAislePosition());					
					}

					for(int k = 0; k<bayArray.length; k++)
					{
						for(int l = 0; l<levelArray.length; l++)
						{
							int warehouseNo = castparam.getWarehouseNumber();
							String locationNo = Shelf.getNumber(warehouseNo, bankArray[j], bayArray[k], levelArray[l]);
							
							Shelf shelf = new Shelf(locationNo);
							shelf.setBank(bankArray[j]);
							shelf.setBay(bayArray[k]);
							shelf.setLevel(levelArray[l]);
							//#CM50271
							//<en>Warehouse station no.</en>
							String whstno = getFindUtil(conn).getWarehouseStationNumber(castparam.getWarehouseNumber());
							shelf.setWarehouseStationNumber(whstno);
							//#CM50272
							//<en>Status (available)</en>
							shelf.setStatus(Station.STATION_OK);
							//#CM50273
							//<en>Load presence</en>
							shelf.setPresence(Shelf.PRESENCE_EMPTY);
							//#CM50274
							//<en>Hard Zone</en>
							shelf.setHardZone(0);
							//#CM50275
							//<en>Soft Zone</en>
							shelf.setSoftZone(Shelf.UN_SETTING);
							//#CM50276
							//<en>Parent station</en>
							shelf.setParentStationNumber(castparam.getAisleStationNumber());
							//#CM50277
							//<en>Inaccesible flag</en>
							shelf.setAccessNgFlag(false);
							
							//#CM50278
							// In case of Double deep
							if (doubledeepKind)
							{
								//#CM50279
								//Priority level
								shelf.setPriority(priority);
								//#CM50280
								//Units of location
								shelf.setSide(side);
								//#CM50281
								//Pair Station No
								if (pairBank != 0)
								{
									shelf.setPairStationNumber(Shelf.getNumber(warehouseNo, pairBank, bayArray[k], levelArray[l]));							

									//#CM50282
									//<en>Create</en>
									getToolShelfHandler(conn).createDoubleDeep(shelf);
								}
								else
								{
									//#CM50283
									//<en>Create</en>
									getToolShelfHandler(conn).create(shelf);
								}
							}
							//#CM50284
							// In case of Single deep
							else
							{
								//#CM50285
								//Units of location(This side)
								shelf.setSide(Bank.NEAR);

								//#CM50286
								//<en>Create</en>
								getToolShelfHandler(conn).create(shelf);
							}
						}
					}
				}
			}
		}
		catch(InvalidStatusException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		catch(DataExistsException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
	}


	//#CM50287
	/**<en>
	 * Update the BANKSELECT table.
	 * @param array :entered data summary
	 </en>*/
	private void updateBankSelectTable(Connection conn, Parameter[] mArray) throws ReadWriteException, ScheduleException
	{

		try
		{
			AisleParameter castparam = null;
			ToolBankHandler bankHandler = new ToolBankHandler(conn);
			
			//#CM50288
			//<en>Delete all data in BANKSELECT table.</en>
			bankHandler.truncate();
			
			//#CM50289
			//<en>Loop for pooling entered data</en>
			for(int i = 0; i < mArray.length; i++)
			{
				castparam = (AisleParameter)mArray[i];
				
				int[] bankArray = getLocationArray(castparam.getSBank(), castparam.getEBank());
				
				boolean doubledeepKind = isDoubleDeep(castparam.getSBank(), castparam.getEBank());

				for(int j = 0; j<bankArray.length; j++)
				{
					String warehouseStationNo = getFindUtil(conn).getWarehouseStationNumber(castparam.getWarehouseNumber());

					Bank bank = new Bank();
					
					bank.setWareHouseStationNumber(warehouseStationNo);
					bank.setAisleStationNumber(castparam.getAisleStationNumber());
					bank.setBank(bankArray[j]);
					bank.setPairBank(0);
					
					//#CM50290
					//In case of Double deep
					if (doubledeepKind)
					{
						if (bankArray[j] == castparam.getSAislePosition()
								|| bankArray[j] == castparam.getEAislePosition())
						{
							//#CM50291
							//This side
							bank.setSide(Bank.NEAR);							
						}
						else
						{
							//#CM50292
							//Interior
							bank.setSide(Bank.FAR);							
						}
						//#CM50293
						// Acquire and register PairBank. 
						bank.setPairBank(getPairBank(bankArray[j], castparam.getSBank(), castparam.getEBank(),
								castparam.getSAislePosition(), castparam.getEAislePosition()));	
					}
					//#CM50294
					//In case of Single deep
					else
					{
						//#CM50295
						//This side
						bank.setSide(Bank.NEAR);
					}
				
					//#CM50296
					//<en>Create</en>
					bankHandler.create(bank);
				}
			}
		}
		catch(DataExistsException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM50297
	/**<en>
	 * Check the overlapped bank range of the identical storage type. 
	 * Return false if the range overlapped.
	 * @param bank   :int array of the bank to compare
	 * @param bankOrg:int array of the bank to compare
	 * @param array  :entered data summary
	 * @return    :return false if the data range overlapped.
	 </en>*/
	private boolean checkBankRange(int[] bank, int[] bankOrg)
	{
		for(int i = 0; i < bank.length; i++)
		{
			for(int j = 0; j < bankOrg.length; j++)
			{
				if(bank[i] == bankOrg[j])
				{
					return false;
				}
			}
		}
		return true;
	}

	//#CM50298
	/**<en>
	 * At the warehouse seetting:
	 * Check whether/not the storage type of adding parameter exists in entered data summary.
	 * Return false if the bank range overlapped within the identical storage type.
	 * Identical aisle no. cannot be assigned when storage type is the same.
	 * Identical aisle station no. of system cannot be assigned.
	 * @param param :the parameter being added in this process
	 * @param array :entered data summary
	 * @return      :returns true if identical data exists.
	 </en>*/
	private boolean isSameData(AisleParameter param, 
								Parameter[] array)
	{
		//#CM50299
		//<en>Keys to compare;</en>
		//#CM50300
		//<en>the value normally unused</en>
		int newWarehouseNo = 99999;
		//#CM50301
		//<en>the value normally unused</en>
		int orgWarehouseNo = 99999; 
		String newAisleStationNo = "";
		String orgAisleStationNo = "";
		//#CM50302
		//<en>the value normally unused</en>
		int newAisleNo = 99999; 
		//#CM50303
		//<en>the value normally unused</en>
		int orgAisleNo = 99999; 

		int newSBank = 0;
		int newEBank = 0;
		int orgSBank = 0;
		int orgEBank = 0;

		//#CM50304
		//<en>If there are any entered data summary,</en>
		if(array.length > 0)
		{
			newWarehouseNo = param.getWarehouseNumber();
			newAisleStationNo = param.getAisleStationNumber();
			newAisleNo = param.getAisleNumber();

			newSBank = param.getSBank();
			newEBank = param.getEBank();
			
			//#CM50305
			//<en>Retrieve the index of the updating parmaeter.</en>
			int index = getUpdatingFlag() ;
			for (int i = 0 ; i < array.length ; i++)
			{
				AisleParameter castparam = (AisleParameter)array[i];
				//#CM50306
				//<en>Key for entered data summary</en>
				orgWarehouseNo = castparam.getWarehouseNumber();
				orgAisleStationNo = castparam.getAisleStationNumber();
				orgAisleNo = castparam.getAisleNumber();
				orgSBank = castparam.getSBank();
				orgEBank = castparam.getEBank();

				//#CM50307
				//<en>Check the identical aisle station no.</en>
				if( newAisleStationNo.equals(orgAisleStationNo) )
				{
					//#CM50308
					//<en>6123016 = The data is already entered. Cannot enter identical station no.</en>
					setMessage("6123016");
					return true;
				}
				
				//#CM50309
				//<en>If the storage type is identical,</en>
				if(newWarehouseNo == orgWarehouseNo)
				{
					//#CM50310
					//<en>Check the identical aisle no.</en>
					if( newAisleNo == orgAisleNo )
					{
						//#CM50311
						//<en>6123053 = The data is already entered. Cannot enter identical station no.</en>
						setMessage("6123053");
						return true;
					}
					//#CM50312
					//<en>Check the overlap of bank range.</en>
					int[] bankArray = getLocationArray(newSBank, newEBank);
					int[] bankArrayOrg = getLocationArray(orgSBank, orgEBank);
					if(!checkBankRange(bankArray, bankArrayOrg))
					{
						//#CM50313
						//<en>6123099=Cannot enter the overlapped bank range of identical storage type.</en>
						setMessage("6123099");
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	
	//#CM50314
	/**<en>
	 * Create the array of int style based on the starting location and the ending location.
	 * 
	 * @param start :starting location
	 * @param end   :ending location
	 * @return      :int style array
	 </en>*/
	private int[] getLocationArray(int start, int end)
	{
		int count = Math.abs(end - start) + 1;
		
		int[] retArray = new int[count];
		for(int i = 0; i < count; i++)
		{
			retArray[i] = start + i;
		}
		return retArray;
	}

	//#CM50315
	/**<en>
	 * Determine whether/not the data is modifiable.
	 * Conditions to check when modifing the aisle:
	 *  -If the data cannot be found in the SHELF table
	 *  -If the data cannot be found in the STATION table
	 * @param <code>WarehouseParameter</code>
	 * @return :Return true if data is deletable.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private boolean isChangeable(Connection conn, AisleParameter param) throws ScheduleException
	{
		int reason = 0;
		reason = deletable(conn, param);
		String aisleStationNo = param.getAisleStationNumber();
		
		if(reason == 0)
		{
			return true;
		}
		else if(reason == 1)
		{
			//#CM50316
			//<en>Cannot modify data; the aisle ({0}) exists in STATION table.</en>
			setMessage("6123154" + wDelim + aisleStationNo);
			return false;
		}
		else if(reason == 2)
		{
			//#CM50317
			//<en>Cannot modify data; the aisle ({0}) exists in RouteText.</en>
			setMessage("6123171" + wDelim + aisleStationNo);
			return false;
		}
		else if(reason == 3)
		{
			//#CM50318
			//<en>Cannot modify data; the aisle ({0}) exists in MACHINE table.</en>
			setMessage("6123213" + wDelim + aisleStationNo);
			return false;
		}

		return true;
	}
	
	//#CM50319
	/**<en>
	 * Determine whether/not the data is deletable.
	 * Conditions to check when deleting the aisle:
     *  -If the data cannot be found in RouteText.
     *  -If the data cannot be found in STATION table
     *  -If the data cannot be found in MACHINE table
	 * @param <code>WarehouseParameter</code>
	 * @return :Return true if data is deletable.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private boolean isDeletable(Connection conn, AisleParameter param) throws ScheduleException
	{
		int reason = 0;
		reason = deletable(conn, param);
		String aisleStationNo = param.getAisleStationNumber();
		
		if(reason == 0)
		{
			return true;
		}
		else if(reason == 1)
		{
			//#CM50320
			//<en>Cannot modify data; the aisle ({0}) exists in STATION table.</en>
			setMessage("6123154" + wDelim + aisleStationNo);
			return false;
		}
		else if(reason == 2)
		{
			//#CM50321
			//<en>Cannot modify data; the aisle ({0}) exists in RouteText.</en>
			setMessage("6123171" + wDelim + aisleStationNo);
			return false;
		}
		else if(reason == 3)
		{
			//#CM50322
			//<en>Cannot modify data; the aisle ({0}) exists in MACHINE table.</en>
			setMessage("6123213" + wDelim + aisleStationNo);
			return false;
		}

		return true;
	}

	//#CM50323
	//<en>Determines whether/not the modification/deletion of data can be processed.</en>
	//<en>The modification and deletion of data is possible when return value is 0.</en>
	//<en>For other cases, appropriate messages will be set respectively by the call resource.</en>
	//#CM50324
	//<en>CMENJP6978$CMWhen the return value is 0, Deletion and the correction become possible. </en>
	//#CM50325
	//<en>Set the message which corresponds respectively</en>
	//#CM50326
	//<en> besides on the call side. </en>
private int changeable(Connection conn, AisleParameter param) throws ScheduleException
	{
		try
		{	
			String aisleStationNo = param.getAisleStationNumber();

			ToolStationSearchKey stationKey = new ToolStationSearchKey();
			stationKey.setAisleStationNumber(aisleStationNo);
			ToolStationHandler stationHandle = new ToolStationHandler(conn);
			Station[] stationArray = (Station[])stationHandle.find(stationKey);
			//#CM50327
			//<en>Cannot delete data if it exists in Station.</en>
			if(stationArray.length > 0)
			{
				return 1;
			}
			//#CM50328
			//<en>Cannot delete data if it exists in RouteText.</en>
			ToolCommonChecker commChecker = new ToolCommonChecker(conn);
			if(commChecker.isExistStationNo_RouteText(wFilePath, aisleStationNo))
			{
				return 2;
			}
			ToolAs21MachineSearchKey machineKey = new ToolAs21MachineSearchKey();
			ToolAs21MachineStateHandler machineHandle = new ToolAs21MachineStateHandler(conn);
			
			//#CM50329
			//<en>Cannot delete data if it exists in Machine.</en>
			machineKey.setStationNumber(aisleStationNo);
			As21MachineState[] machineArray = (As21MachineState[])machineHandle.find(machineKey);
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

	//#CM50330
	//<en>Determines whether/not the modification/deletion of data can be processed.</en>
	//<en>The modification and deletion of data is possible when return value is 0.</en>
	//<en>For other cases, appropriate messages will be set respectively by the call resource.</en>
	//#CM50331
	//<en>CMENJP6982$CMWhen the return value is 0, Deletion and the correction become possible. </en>
	//#CM50332
	//<en>Set the message which corresponds respectively</en>
	//#CM50333
	//<en> besides on the call side. </en>
private int deletable(Connection conn, AisleParameter param) throws ScheduleException
	{
		try
		{	
			String aisleStationNo = param.getAisleStationNumber();

			ToolStationSearchKey stationKey = new ToolStationSearchKey();
			stationKey.setAisleStationNumber(aisleStationNo);
			ToolStationHandler stationHandle = new ToolStationHandler(conn);
			Station[] stationArray = (Station[])stationHandle.find(stationKey);
			//#CM50334
			//<en>Cannot delete data if it exists in Station.</en>
			if(stationArray.length > 0)
			{
				return 1;
			}
			//#CM50335
			//<en>Cannot delete data if it exists in RouteText.</en>
			ToolCommonChecker commChecker = new ToolCommonChecker(conn);
			if(commChecker.isExistStationNo_RouteText(wFilePath, aisleStationNo))
			{
				return 2;
			}
			ToolAs21MachineSearchKey machineKey = new ToolAs21MachineSearchKey();
			ToolAs21MachineStateHandler machineHandle = new ToolAs21MachineStateHandler(conn);
			
			//#CM50336
			//<en>Cannot delete data if it exists in Machine.</en>
			machineKey.setStationNumber(aisleStationNo);
			As21MachineState[] machineArray = (As21MachineState[])machineHandle.find(machineKey);
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

	//#CM50337
	/**<en>
	 * Retireve the Aisle instance.
	 * @return <code>Aisle</code> object array
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private Aisle[] getAisleArray(Connection conn) throws ReadWriteException
	{
		ToolAisleSearchKey aisleKey  = new ToolAisleSearchKey();
		//#CM50338
		//<en>*** Retrieve the Aisle instance ***</en>
		aisleKey.setNumberOrder(1, true);
		aisleKey.setWareHouseStationNumberOrder(2,true);
		Aisle[] array = (Aisle[])getToolAisleHandler(conn).find(aisleKey);
		return array;
	}

	//#CM50339
	/**
	 * It is judged whether the specified range of Bank is double deep. 
	 * @param startBank Specify Start Bank position. 
	 * @param endBank   Specify End Bank position. 
	 * @return Return true at the double deep setting. 
	 */
	private boolean isDoubleDeep(int startBank, int endBank)
	{
		if (endBank - startBank > 1)
		{
			return true;
		}
			
		return false;
	}	

	//#CM50340
	/**
	 * Acquire the order of priority of specified Bank. 
	 * @param bank Priority order acquisition object Bank
	 * @param startBank  Specify Start Bank position. 
	 * @param endBank    Specify End Bank position. 
	 * @param startAisle Specify Start Aisle position. 
	 * @param End Aisle   Specify End Aisle position. 
	 * @return Priority level
	 */
	private int getPriority(int bank, int startBank, int endBank, int startAisle, int endAisle)
	{
		int priority = 0; 

		if (bank == startBank)
		{
			priority = 1;	
		}
		else if (bank == startAisle)
		{
			priority = 2;
		}
		else if (bank == endAisle)
		{
			priority = endBank - startBank + 1;
		}
		else if (bank == endBank)
		{
			priority = endBank - startBank ;
		}
		
		return priority;
	}

	//#CM50341
	/**
	 * Acquire Bank which becomes the pair shelf of specified Bank. 
	 * Return 0 when specified Bank is single deep.  
	 * @param bank Bank
	 * @param startBank  Specify Start Bank position. 
	 * @param endBank    Specify End Bank position. 
	 * @param startAisle Specify Start Aisle position. 
	 * @param End Aisle   Specify End Aisle position. 
	 * @return Bank of Bay Location
	 */
	private int getPairBank(int bank, int startBank, int endBank, int startAisle, int endAisle)
	{
		if (bank == startBank)
		{
			if (bank != startAisle)
			{
				return  bank + 1;	
			}
		}
		else if (bank == startAisle)
		{
			return  bank - 1;	
		}
		else if (bank == endAisle)
		{
			if (bank != endBank)
			{
				return  bank + 1;	
			}
		}
		else if (bank == endBank)
		{
			return  bank - 1;	
		}
		
		return 0;
	}
}
//#CM50342
//end of class

