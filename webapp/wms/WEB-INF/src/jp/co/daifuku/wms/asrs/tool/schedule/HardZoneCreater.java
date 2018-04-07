// $Id: HardZoneCreater.java,v 1.2 2006/10/30 02:52:04 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM50769
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.awt.Rectangle;
import java.io.IOException;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.wms.asrs.tool.common.LogHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolHardZoneHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolHardZoneSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.HardZone;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ScheduleInterface;
import jp.co.daifuku.common.text.StringUtil;

//#CM50770
/**<en>
 * This class processes the zone maintenance.
 * It inherits the AbstractCreater, and implements the processes required for zone setting.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/11/21</TD><TD> K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:04 $
 * @author  $Author: suresh $
 </en>*/
public class HardZoneCreater extends AbstractCreater
{
	//#CM50771
	// Class fields --------------------------------------------------
	
	//#CM50772
	// Class variables -----------------------------------------------
	//#CM50773
	/**<en>
	 * The Hashtable which preserves the max quantities of the bank, bay and level in warehouse.
	 </en>*/
	private Hashtable wMaxShelfRange = null;


	//#CM50774
	/**<en>
	 * <CODE>ToolZoneSearchKey</CODE> instance
	 </en>*/
	protected ToolHardZoneSearchKey wZoneKey = null;
 
	//#CM50775
	// Class method --------------------------------------------------
	//#CM50776
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:04 $") ;
	}

	//#CM50777
	// Constructors --------------------------------------------------
	//#CM50778
	/**<en>
	 * Delete the parameter of the specified position. <BR>
	 * @param index :position of the deleting parameter
	 * @throws ScheduleException :Notifies if there are no parameters in specified position.
	 </en>*/
	public void removeParameter(int index) throws ScheduleException
	{
		//#CM50779
		//<en> Initialization of the message</en>
		setMessage("");
		try
		{
			Parameter[] mArray = (Parameter[])getAllParameters();
			HardZoneParameter castparam = (HardZoneParameter)mArray[index];
			
			//#CM50780
			//<en>Delete only when the data is deletable.</en>
			if(isChangeable(castparam, mArray))
			{
				wParamVec.remove(index);
				setMessage("6121003");
			}
			else
			{
				return;
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM50781
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
	//#CM50782
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
		HardZone[] array = getHardZoneArray(conn);
		//#CM50783
		//<en>Vector where the data will temporarily be stored</en>
		//#CM50784
		//<en>Set the max number of data as an initial value for entered data summary.</en>
		Vector vec = new Vector(100);	
		//#CM50785
		//<en>Entity class for display</en>
		HardZoneParameter dispData = null;
		if(array.length > 0)
		{
			for(int i = 0; i < array.length; i++)
			{
				dispData = new HardZoneParameter();
				dispData.setZoneID(array[i].getHardZoneID());
				dispData.setZoneName(array[i].getName());
				dispData.setHeight(array[i].getHeight());
				dispData.setWareHouseStationNumber(array[i].getWareHouseStationNumber());
				dispData.setWareHouseName(array[i].getWareHouseStationNumber());
				dispData.setStartBank(array[i].getStartBank());
				dispData.setStartBay(array[i].getStartBay());
				dispData.setStartLevel(array[i].getStartLevel());
				dispData.setEndBank(array[i].getEndBank());
				dispData.setEndBay(array[i].getEndBay());
				dispData.setEndLevel(array[i].getEndLevel());
				dispData.setPriority(array[i].getPriority());
				vec.addElement(dispData);
			}
			HardZoneParameter[] fmt = new HardZoneParameter[vec.size()];
			vec.toArray(fmt);
			return fmt;
		}
		return null;
	}
	//#CM50786
	/**<en>
	 * Initialize this class.
	 * @param conn :connetion object with database
	 * @param kind :process type
	 </en>*/
	public HardZoneCreater(Connection conn, int kind)
	{
		super(conn, kind);
		wMaxShelfRange = new Hashtable();

	}

	//#CM50787
	// Public methods ------------------------------------------------
	//#CM50788
	/**<en>
	 * Conduct the complementarity process of parameter.<BR>
	 *  -If the warehouse name is blank, set the name.<BR>
	 *  -Append Zone instance to the parameter in order to check whether/not the data
	 *   has been modified by other terminals.<BR>
	 * It returns the complemented parameter if the process succeeded, or returns false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @param param  :the parameter to set the instance
	 * @return :returns the parameter if the process succeeded, ro returns null if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the process.
	 </en>*/
	protected  Parameter complementParameter(Parameter param)throws ReadWriteException, ScheduleException
	{
		HardZoneParameter mParameter = (HardZoneParameter)param;
		//#CM50789
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();

		//#CM50790
		//<en>**** Set the name of warehouse if it is left blank. ****</en>
		if(StringUtil.isBlank(mParameter.getWareHouseName()))
		{
//#CM50791
//			FindUtil fu = new FindUtil(wConn);
//#CM50792
//			mParameter.setWareHouseName(fu.getWareHouseName(mParameter.getWareHouseStationNumber()));
		}

//#CM50793
//2003/02/11 14:53:06
//#CM50794
//<en>  In this version, the check for data updates made by terminals is not included in process. Kawasima</en>
//#CM50795
//<en>CMENJP7354$CM Therefore, the following parts are assumed to be a comment. </en>
		return mParameter;
		
//#CM50796
//<en>		//If registring data, the instance will not be preserved.</en>
//#CM50797
//		if(processingKind == M_CREATE)
//#CM50798
//		{
//#CM50799
//			return mParameter;
//#CM50800
//		}
//#CM50801
//<en>		//If modifing or deleting,</en>
//#CM50802
//		else if(processingKind == M_DELETE || processingKind == M_MODIFY)
//#CM50803
//		{
//#CM50804
//<en>			//**** Set the Zone instance to the parameter. ****</en>
//#CM50805
//			int selialNo = mParameter.getSerialNumber();
//#CM50806
//			wZoneKey = new ZoneSearchKey();
//#CM50807
//			wZoneKey.setSerialNumber(selialNo);
//#CM50808
//			Zone[] zoneArray = (Zone[])getZoneHandler().find(wZoneKey);
//#CM50809
//<en>			//If there is no data in DB;</en>
//#CM50810
//			if(zoneArray.length == 0)
//#CM50811
//			{
//#CM50812
//<en>				//6123277 = Cannot process the data; it has been updated by other terminal.</en>
//#CM50813
//				setMessage("6123277");
//#CM50814
//				return null;
//#CM50815
//			}
//#CM50816
//<en>			//Set the instance to the parameter.</en>
//#CM50817
//			mParameter.setInstance(zoneArray[0]);
//#CM50818
//			return mParameter;
//#CM50819
//		}
//#CM50820
//<en>		//Error with the process type.</en>
//#CM50821
//		else
//#CM50822
//		{
//#CM50823
//<en>			// Unexpected value has been set.{0} = {1}</en>
//#CM50824
//			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
//#CM50825
//			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
//#CM50826
//<en>			// 6127006 = Fatal error occurred. Please refer to the log.</en>
//#CM50827
//			throw new ScheduleException("6127006");	
//#CM50828
//		}
	}

	//#CM50829
	/**<en>
	 * Processes the paramter check. It will be called when adding the parameter, before the 
	 * execution of maintenance process.
	 * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
	 * <Zone name><BR>
	 *  -Unacceptable letters and symbols<BR>
	 * <Zone range><BR>
	 *  -Startinf location bay <= ending location bay<BR>
	 *  -Starting location level <= ending location level<BR>
	 *  -Zone range should be contained within teh specified warehouse range.<BR>
	 * @param param : contents of the parameter to check
	 * @return :returns true if there is no error with parameter, or returns false if there are any errors.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
	 </en>*/
	public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		ToolCommonChecker check = new ToolCommonChecker(conn);
		//#CM50830
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		HardZoneParameter mParameter = (HardZoneParameter)param;

		//#CM50831
		//<en>In case of registration,</en>
		if(processingKind == M_CREATE)
		{
			//#CM50832
			//<en> Check whether/not the data is registered in WAREHOUSE table.</en>
			ToolWarehouseHandler whhandle = new ToolWarehouseHandler(conn);
			ToolWarehouseSearchKey whkey = new ToolWarehouseSearchKey();
			if (whhandle.count(whkey) <= 0)
			{
				//#CM50833
				//<en> There is no information for the warehouse. Please register in screen for the wareshouse setting.</en>
				setMessage("6123100");
				return false;
			}

			//#CM50834
			//<en> CHeck whether/not it is registered in SHELF table.</en>
			ToolShelfHandler ahandle = new ToolShelfHandler(conn);
			ToolShelfSearchKey akey = new ToolShelfSearchKey();
			akey.setWarehouseStationNumber(mParameter.getWareHouseStationNumber());
			if (ahandle.count(akey) <= 0)
			{
				//#CM50835
				//<en> The location control information cannot be found. Please register in screen for aisle setting.</en>
				setMessage("6123113");
				return false;
			}

			//#CM50836
			//<en>Name of the load size</en>
			if(!check.checkLoadName(mParameter.getZoneName()))
			{
				//#CM50837
				//<en>Set the contents of the error.</en>
				setMessage(check.getMessage());
				return false;
			}
			//#CM50838
			//<en>Check the zone range.</en>
			if(!checkZoneRange(conn, mParameter))
			{
				return false;
			}
			return true;
		}
		//#CM50839
		//<en>Error with the process type.</en>
		else
		{
			//#CM50840
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM50841
			//<en> 6127006 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6127006");	
		}
	}

	//#CM50842
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
		ToolCommonChecker check = new ToolCommonChecker(conn);
		//#CM50843
		//<en>True if there is no error.</en>
		boolean errorFlag = true;
		String logfile = logpath +"/"+ ToolParam.getParam("CONSTRAINT_CHECK_FILE");
		
		try
		{
			LogHandler loghandle = new LogHandler(logfile, locale);

			HardZoneParameter zone		= new HardZoneParameter();

			ToolHardZoneSearchKey zoneKey = new ToolHardZoneSearchKey();
			HardZone[] znArray = (HardZone[])getToolHardZoneHandler(conn).find(zoneKey);

			//#CM50844
			//<en>If the HardZone has not been set,</en>
			if(znArray.length == 0)
			{
				//#CM50845
				//<en>6123184 = The hard zone has not been set.</en>
				loghandle.write("HardZone", "Zone Table", "6123184");
				//#CM50846
				//<en>If the ard zone has not been set, discontinue the checks and exit.</en>
				return false;
			}

			//#CM50847
			//<en>*** Check the warehouse station no. (Zone table) ***</en>
			//#CM50848
			//<en>Check whether/not the warehouse station no. in ZONE table exists in WAREHOUSE table.</en>

			for(int i = 0; i < znArray.length; i++)
			{
				String warehouseStationNo = znArray[i].getWareHouseStationNumber();
				if(!check.isExistAutoWarehouseStationNo(warehouseStationNo))
				{
					loghandle.write("HardZone","Warehouse Table",check.getMessage());
					errorFlag = false;
				}
			}
			//#CM50849
			//<en>*** Check of zone ID (Zone table) ***</en>
			//#CM50850
			//<en>Check whether/not the zone ID. in ZONE table exists in SHELF table.</en>

			for(int i = 0; i < znArray.length; i++)
			{
				int ZoneID = znArray[i].getHardZoneID();
				if(!check.isExistShelf(ZoneID, Shelf.HARD))
				{
					loghandle.write("HardZone","Shelf Table",check.getMessage());
					errorFlag = false;
				}
			}

			//#CM50851
			//<en>*** Check for starting/ending banks, starting/ending bays and starting/ending levels (Zone table) ***</en>
			//<en> 1/ Check the values of starting/ending banks, starting/ending bays and starting/ending levels.</en>
			//<en>    The values must be 0 or greater.</en>
			//<en> 2/ Check the numeric numbers to see if that of starting point is aways smaller than ending point. </en>
			//#CM50852
			//<en>CMENJP7386$CM (1) Value check (More than 0 or not) StartBank-EndBank, StartBay-EndBay, StartLevel-EndLevel</en>
			//#CM50853
			//<en> (2) Range check of StartBank-EndBank, StartBay-EndBay, StartLevel-EndLevel</en>

			for(int i = 0; i < znArray.length; i++)
			{
				int Stbank = znArray[i].getStartBank();
				int Edbank = znArray[i].getEndBank();
				int Stbay = znArray[i].getStartBay();
				int Edbay = znArray[i].getEndBay();
				int Stlevel = znArray[i].getStartLevel();
				int Edlevel = znArray[i].getEndLevel();

				if(!check.checkBank(Stbank,Edbank))
				{
					loghandle.write("HardZone","Zone Table",check.getMessage());
					errorFlag = false;
				}
				if(!check.checkBay(Stbay,Edbay))
				{
					loghandle.write("HardZone","Zone Table",check.getMessage());
					errorFlag = false;
				}
				if(!check.checkLevel(Stlevel,Edlevel))
				{
					loghandle.write("HardZone","Zone Table",check.getMessage());
					errorFlag = false;
				}
			}

			//#CM50854
			//<en>*** Check the zone setting range. (Shelf table) ***</en>
			//#CM50855
			//<en>Check the range of starting/ending banks, starting/ending bays and starting/ending levels </en>
			//#CM50856
			//<en>in ZONE table. Check to see if they exist in Shelf table.</en>

			for(int i = 0; i < znArray.length; i++)
			{
				int ZoneID = znArray[i].getHardZoneID();
				//#CM50857
				//<en> warehouse station no.</en>
				zone.setWareHouseStationNumber(znArray[i].getWareHouseStationNumber());		
				//#CM50858
				//<en> starting bank</en>
				zone.setStartBank(znArray[i].getStartBank());								
				//#CM50859
				//<en> ending bank</en>
				zone.setEndBank(znArray[i].getEndBank());									
				//#CM50860
				//<en> starting bay</en>
				zone.setStartBay(znArray[i].getStartBay());									
				//#CM50861
				//<en> ending bay</en>
				zone.setEndBay(znArray[i].getEndBay());										
				//#CM50862
				//<en> starting level</en>
				zone.setStartLevel(znArray[i].getStartLevel());								
				//#CM50863
				//<en> ending level</en>
				zone.setEndLevel(znArray[i].getEndLevel());									

				if(!checkZoneRange(conn, zone))
				{
					loghandle.write("HardZone","Shelf Table",this.getMessage());
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

	//#CM50864
	/**<en>
	 * Process the parameter duplicate check.
	 * < Registration ><BR>
	 * -Check to see that the setting of the identical zone range does not exist in entered data summary or in ZONE table.<BR>
	 * < Modification ><BR>
	 * -Check to see that identical data has not been selected from the listbox.<BR>
	 * -Check to see that the setting of the identical zone range does not exist in entered data summary or in ZONE table.<BR>
	 * < Deletion ><BR>
	 * -Check to see that identical data has not been selected from the listbox.<BR>
	 * It checks the duplication of parameter, then returns true if there was no duplicated data or 
	 * returns false if there were any duplication.
	 * If parameter duplicate check failed, its reason can be obtained by <code>getMessage</code>.
	 * @param param :contents of the parameter to check
	 * @return :returns true if the parameter duplicate check has succeeded, or returns false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
	 </en>*/
	public boolean duplicationCheck(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		Parameter[] mArray = (Parameter[])getParameters();
		HardZoneParameter mParam = (HardZoneParameter)param;

		//#CM50865
		//<en>Check the identical data.</en>
		if(isSameData(mParam, mArray))
		{
			return false;
		}
		
		//#CM50866
		//<en>Check the min. range.</en>
		if(isZeroSameData(mParam))
		{
			return false;
		}
		
		//#CM50867
		//<en>Check the zone range.</en>
		if(!checkZoneRange(conn, mParam, mArray))
		{
			return false;
		}
		
		//#CM50868
		//<en>Check the priority.</en>
		if(isPrioritySameData(mParam))
		{
			return false;
		}

		//#CM50869
		//<en>Check only when modifing data.</en>
		if(getUpdatingFlag() != ScheduleInterface.NO_UPDATING)
		{
			//#CM50870
			//<en>*** Key items are not to be modifiable when modifing data. ***</en>
			String warehouseStNo = mParam.getWareHouseStationNumber();
			int zoneNo = mParam.getZoneID();

			//#CM50871
			//<en>Key items for enterd data summary</en>
			String orgWarehouseStNo = "";
			int orgZoneId = 0;
			//#CM50872
			//<en>Parameter which is modified</en>
			HardZoneParameter mparam = (HardZoneParameter)getUpdatingParameter();
			
			Parameter[] mAllArray = (Parameter[])getAllParameters();
			for (int i = 0 ; i < mAllArray.length ; i++)
			{
				HardZoneParameter castparam = (HardZoneParameter)mAllArray[i];
				//#CM50873
				//<en>Key items</en>
				orgWarehouseStNo = castparam.getWareHouseStationNumber();
				orgZoneId = castparam.getZoneID();

				//#CM50874
				//<en>Acceptable as the data is not modified.</en>
				if(warehouseStNo.equals(orgWarehouseStNo) && 
					zoneNo == orgZoneId)
				{
					return true;
				}
			}
			//#CM50875
			//<en>In case the parameter is not modifiable,</en>
			if(!isChangeable(mparam,mAllArray))
			{
				return false;
			}
			return true;
		}
		return true;
	}

	//#CM50876
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
		//#CM50877
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();

		//#CM50878
		//<en>Registration</en>
		if(processingKind == M_CREATE)
		{
			if(!create(conn))
			{
				return false;
			}
			//#CM50879
			//<en>6121004 = Edited the data.</en>
			setMessage("6121004");
			return true;
		}
		//#CM50880
		//<en>Error with the process type.</en>
		else
		{
			//#CM50881
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM50882
			//<en> 6127006 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}

	//#CM50883
	// Package methods -----------------------------------------------

	//#CM50884
	// Protected methods ---------------------------------------------
	//#CM50885
	/**<en>
	 * Retrieve the <CODE>ZoneHandler</CODE> instance generated at the initialization of this class.
	 * @return <CODE>ZoneHandler</CODE>
	 </en>*/
	protected ToolHardZoneHandler getToolHardZoneHandler(Connection conn)
	{
		return new ToolHardZoneHandler(conn);
	}

	//#CM50886
	/**<en>
	 * Process the maintenance modifications.
	 * Modification will be made based on the key items of parameter array. 
	 * Modification of ZONEINFO will not be done here.
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

	//#CM50887
	/**<en>
	 * Process the maintenance registrations.
	 * If registering a new zone ID, append the ID data in ZONEINFO table and ZONE table. 
	 * And if it is an additional registration, register only in ZONE table.
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

			ToolShelfHandler shelfHandle = new ToolShelfHandler(conn);

			if( mArray.length > 0)
			{

				//#CM50888
				//<en>Delete all data from the table.</en>
				isDropZone(conn);
				isZeroUpdateShelf(conn);

				//#CM50889
				//<en>***** Update of ZONE table *****/</en>
				HardZone zone = new HardZone();
				for(int i = 0; i < mArray.length; i++)
				{
					HardZoneParameter castparam = (HardZoneParameter)mArray[i];

					String tmpWHStno = castparam.getWareHouseStationNumber();
					int tmpzoneid = castparam.getZoneID();
					int tmpfbank = castparam.getStartBank();
					int tmpfbay  = castparam.getStartBay();
					int tmpflevel  = castparam.getStartLevel();
					int tmptbank = castparam.getEndBank();
					int tmptbay  = castparam.getEndBay();
					int tmptlevel  = castparam.getEndLevel();

					//#CM50890
					//<en>***** Update of SHELFO table *****/</en>
					shelfHandle.modifyHardZone(tmpfbank,tmpfbay,tmpflevel,tmptbank,tmptbay,tmptlevel,tmpzoneid,tmpWHStno);
					//#CM50891
					//<en>***** Update of SHELFO table ends here. *****/</en>

					//#CM50892
					//<en>***** Update of ZONE table *****/</en>
					zone.setHardZoneID(tmpzoneid);
					zone.setName(castparam.getZoneName());
					zone.setWareHouseStationNumber(tmpWHStno);
					zone.setHeight(castparam.getHeight());
					zone.setStartBank(tmpfbank);
					zone.setStartBay(tmpfbay);
					zone.setStartLevel(tmpflevel);
					zone.setEndBank(tmptbank);
					zone.setEndBay(tmptbay);
					zone.setEndLevel(tmptlevel);

					//#CM50893
					//<en>The serial number will not be set in case of registration.</en>
					//#CM50894
					//<en>zone.setSerialNumber(castparam.getSerialNumber());</en>
					//#CM50895
					//<en>Priority</en>
					zone.setPriority(castparam.getPriority());
					getToolHardZoneHandler(conn).create(zone);
					//#CM50896
					//<en>***** Update of ZONE table ends here. *****/</en>
				}
				//#CM50897
				//<en>***** Update of ZONE table and ZONEINFO table end here. *****/</en>
				return true;
			}
			//#CM50898
			//<en>If there is no data to process,</en>
			else
			{
				//#CM50899
				//<en>Delete all data from the table.</en>
				isDropZone(conn);
				isZeroUpdateShelf(conn);
				return true;
			}
		}
		catch(DataExistsException e)
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

	//#CM50900
	/**<en>
	 * Process the maintenance deletions.
	 * Deletion will be done based on the key items of parameter array. If all data is deleted
	 * from ZONE table, the corresponding data in ZONEINFO table will be deleted as well.
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

	//#CM50901
	// Private methods -----------------------------------------------

	//#CM50902
	/**<en>
	 * Check whether/not thd data has already been modified by other terminals.
	 * Compares the Zone instance which has been set to maintenance parameter with other Zone instance 
	 * retrieved from current DB.
	 * If the comparison resulted the both instances are equal, it regards that no modification was 
	 * made by other terminals. If the comparison resulted these instances to be different, then it 
	 * regards the other terminal already modified the data.
	 * @param ZoneMaintenanceParameter
	 * @return :returns true if the data ghas already been modified by other terminals, or returns false 
	 * if it has not been modified.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	private boolean isAlreadyChanged(HardZoneParameter param) throws ReadWriteException
	{
		return false;
	}

	//#CM50903
	/**<en>
	 * Check the specified location range that has been given in zone setting. 
	 * In this method, the following are checked.<br>
	 * 1: starting bay is equal to or smaller than ending bay <BR>
	 * 2: starting level is equal to or smaller than ending level<BR>
	 * Then return true if these are checked no problems.
	 * @param ZoneMaintenanceParameter
	 * @return    :returns the check results. Return True if all are correct.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private boolean checkZoneRange(Connection conn, HardZoneParameter param) throws ReadWriteException
	{
		//#CM50904
		//<en> starting bank</en>
		int stBank = param.getStartBank();			
		//#CM50905
		//<en> starting bay</en>
		int stBay = param.getStartBay();			
		//#CM50906
		//<en> starting level</en>
		int stLevel = param.getStartLevel();		
		//#CM50907
		//<en> ending bank</en>
		int edBank = param.getEndBank();			
		//#CM50908
		//<en> ending bay</en>
		int edBay = param.getEndBay();				
		//#CM50909
		//<en> ending level</en>
		int edLevel = param.getEndLevel();			

		//#CM50910
		//<en>Ending bank, bay and level should be greater than the starting ones.</en>
		if(stBank <= edBank && stBay <= edBay && stLevel <= edLevel)
		{
			String whstno = param.getWareHouseStationNumber();
			int zoneid = param.getZoneID();

			ToolShelfHandler toolshelfHandle = new ToolShelfHandler(conn);
			//#CM50911
			//<en>Retrieve the warehouse range in int array.</en>
			int[] range = toolshelfHandle.getShelfRange(whstno,stBank,edBank);
			//#CM50912
			//<en>Register the warehouse range in HashTable, using the warehouse station no. as a key.</en>
			wMaxShelfRange.put(whstno, range);
	 		//#CM50913
	 		//<en>Retrieve the warehouse range from the HashTable.</en>
	 		int[] shelfRange = (int[])wMaxShelfRange.get(whstno);
			//#CM50914
			//<en>Number of banks</en>
	 		int maxBank = shelfRange[0];
			//#CM50915
			//<en>Number of bays</en>
	 		int maxBay = shelfRange[1];
	 		//#CM50916
	 		//<en>Number of levels</en>
	 		int maxLevel = shelfRange[2];

			//#CM50917
			//<en>Check whether/not teh specified range is included in location range.</en>
			if(edBank > maxBank || edBay > maxBay || edLevel > maxLevel)
			{
		 		//#CM50918
		 		//<en>6123092 = The zone ID ({0}), with its bank/bay/level range, exceeded the range of </en>
		 		//<en>the warehouse size.</en>
		 		setMessage("6123092" + wDelim + zoneid);
		 		return false;
	 		}
			ToolShelfHandler toolshelfHdle = new ToolShelfHandler(conn);
			if(toolshelfHdle.count(stBank, stBay, stLevel, edBank, edBay, edLevel, whstno) <= 0)
			{
		 		//#CM50919
		 		//<en>6123204 = There is no location information in input range.</en>
		 		setMessage("6123204");
		 		return false;
			}
 			return true;
		}
		else
		{
			//#CM50920
			//<en>6123076 = The range of strating location and ending location is invalid;</en>
			//<en>Please set the greater value for ending location than the starting location.</en>
			setMessage("6123076");
			return false;
		}
	}

	//#CM50921
	 /**<en>
	 * Check whether/not there is the zone range overlapping the entered data summary and 
	 * ZONE table.
	 * Return false if data is found which has been set over duplicate zone range.
	 * @param param :the parameter which will be appended in this process
	 * @param array :entered data summary (pool)
	 * @return      :returns false if there is data which has been set over duplicate zone range.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private boolean checkZoneRange(Connection conn, HardZoneParameter param, 
									Parameter[] array) throws ReadWriteException
	{
System.out.println( ">> HardZoneCreater.java  checkZoneRange(Connection conn, ZoneParameter param, Parameter[] array)" );

		int zoneID = param.getZoneID();
		String whStationNo = param.getWareHouseStationNumber();

		//#CM50922
		//<en> starting bank</en>
		int stBank = param.getStartBank() -1;			
		//#CM50923
		//<en> starting bay</en>
		int stBay = param.getStartBay() -1;			
		//#CM50924
		//<en> starting level</en>
		int stLevel = param.getStartLevel() -1;		
		//#CM50925
		//<en> ending bank</en>
		int edBank = param.getEndBank() -1;			
		//#CM50926
		//<en> ending bay</en>
		int edBay = param.getEndBay() -1;				
		//#CM50927
		//<en> ending level</en>
		int edLevel = param.getEndLevel() -1;			

		int x;
		int y;
		int z;
		int x_min;
		int x_max;
		int y_min;
		int y_max;
		int z_min;
		int z_max;
		int zone_id;

		//#CM50928
		//<en>*** Check with entered data summary. ***</en>
		//#CM50929
		//<en>If there is the entered data summary,</en>
		if(array.length > 0)
		{

			Rectangle r01 = new Rectangle(stBank, stLevel, edBank - stBank +1, edLevel - stLevel +1);
			Rectangle r02 = new Rectangle(stBay, stLevel, edBay - stBay +1, edLevel - stLevel +1);
			Rectangle r11;
			Rectangle r12;

			if(false) 
			{
				ToolShelfHandler toolshelfHandle = new ToolShelfHandler(conn);
				//#CM50930
				//<en>Get the warehouse range using the int array.</en>
				int[] range = toolshelfHandle.getShelfRange(whStationNo, stBank, edBank);
				//#CM50931
				//<en>Register the warehouse range in HashTable, using the warehouse station No. as a key.</en>
				wMaxShelfRange.put(whStationNo, range);
				//#CM50932
				//<en>Get the warehouse range from HashTable.</en>
				int[] shelfRange = (int[])wMaxShelfRange.get( whStationNo );
				//#CM50933
				//<en>Number of banks</en>
				int maxBank = shelfRange[0];
				//#CM50934
				//<en>Number of bays</en>
				int maxBay = shelfRange[1];
				//#CM50935
				//<en>Number of levels</en>
				int maxLevel = shelfRange[2];

				int shf[][][] = new int[maxBank+1][maxBay][maxLevel];
				for( x=0; x < maxBank; ++x ) 
				{
					for( y=0; y < maxBay; ++y ) 
					{
						for( z=0; z < maxLevel; ++z ) 
						{
							shf[x][y][z] = 0;
						}
					}
				}

				for(int i = 0; i < array.length; i++)
				{
					HardZoneParameter castparam = (HardZoneParameter)array[i];
					//#CM50936
					//<en>*** Check the range. ***</en>
					if( whStationNo.equals(castparam.getWareHouseStationNumber()))
					{
						zone_id = castparam.getZoneID();
						if( zoneID != zone_id ) {
							x_min = castparam.getStartBank() -1;
							x_max = castparam.getEndBank() -1;
							y_min = castparam.getStartBay() -1;
							y_max = castparam.getEndBay() -1;
							z_min = castparam.getStartLevel() -1;
							z_max = castparam.getEndLevel() -1;

							for( x=x_min; x <= x_max; ++x ) 
							{
								for( y=y_min; y <= y_max; ++y ) 
								{
									for( z=z_min; z <= z_max; ++z ) 
									{
										shf[x][y][z] = zone_id;
									}
								}
							}
						}
					}
				}

				for( x=stBank; x <= edBank; ++x ) 
				{
					for( y=stBay; y <= edBay; ++y ) 
					{
						for( z=stLevel; z <= edLevel; ++z ) 
						{
							if( shf[x][y][z] != 0 ) 
							{
								//#CM50937
								//<en>6123077 = Cannot set; the zone range overlaps.</en>
								setMessage("6123077");
System.out.println( "<< HardZoneCreater.java  checkZoneRange(Connection conn, ZoneParameter param, Parameter[] array)  !001" );
								return false;
							}
						}
					}
				}
			}
			else 
			{
				for(int i = 0; i < array.length; i++)
				{
					HardZoneParameter castparam = (HardZoneParameter)array[i];
					//#CM50938
					//<en>*** Check the range. ***</en>
					//#CM50939
					//<en>Identical warehouses</en>
					if( whStationNo.equals(castparam.getWareHouseStationNumber()))
					{
						if( zoneID != castparam.getZoneID() ) {

							x_min = castparam.getStartBank() -1;
							x_max = castparam.getEndBank() -1;
							y_min = castparam.getStartBay() -1;
							y_max = castparam.getEndBay() -1;
							z_min = castparam.getStartLevel() -1;
							z_max = castparam.getEndLevel() -1;

							r11 = new Rectangle(x_min, z_min, x_max - x_min +1, z_max - z_min +1);
							r12 = new Rectangle(y_min, z_min, y_max - y_min +1, z_max - z_min +1);

							if( r01.intersects( r11 ) && r02.intersects( r12 ) ) 
							{
								//#CM50940
								//<en>6123077 = Cannot set; the zone range overlaps.</en>
								setMessage("6123077");
								return false;
							}
						}
					}
				}
			}

			if(false) 
			{
				for(int i = 0; i < array.length; i++)
				{
					HardZoneParameter castparam = (HardZoneParameter)array[i];
					//#CM50941
					//<en>*** Check the range.***</en>
					//#CM50942
					//<en>Identical warehouses</en>
					if( whStationNo.equals(castparam.getWareHouseStationNumber()))
					{
						if( zoneID != castparam.getZoneID() ) 
						{
System.out.println( "(HardZoneCreater.java):ZONE CHECK  zoneID="+zoneID+",castparam.getZoneID()="+castparam.getZoneID() );
System.out.println( "(HardZoneCreater.java):BANK CHECK  stBank="+stBank+",edBank="+edBank+",castparam.getStartBank()="+castparam.getStartBank()+",castparam.getEndBank()="+castparam.getEndBank() );
							//#CM50943
							//<en>Direction of banks is included in the range.</en>
							if((stBank <= castparam.getStartBank() && edBank >= castparam.getStartBank()) ||
							(stBank <= castparam.getEndBank() && edBank >= castparam.getEndBank()))
							{
System.out.println( "(HardZoneCreater.java):BAY CHECK   stBay="+stBay+",edBay="+edBay+",castparam.getStartBay()="+castparam.getStartBay()+",castparam.getEndBay()="+castparam.getEndBay() );
								//#CM50944
								//<en>Direction of bays is included in the range.</en>
								if((stBay <= castparam.getStartBay() && edBay >= castparam.getStartBay()) ||
								(stBay <= castparam.getEndBay() && edBay >= castparam.getEndBay()))
								{
		System.out.println( "(HardZoneCreater.java):LEVEL CHECK stLevel="+stLevel+",edLevel="+edLevel+",castparam.getStartLevel()="+castparam.getStartLevel()+",castparam.getEndLevel()="+castparam.getEndLevel() );
									//#CM50945
									//<en>Direction of levels is included in the range.</en>
									if((stLevel <= castparam.getStartLevel() && edLevel >= castparam.getStartLevel()) ||
									(stLevel <= castparam.getEndLevel() && edLevel >= castparam.getEndLevel()))
									{
										//#CM50946
										//<en>6123077 = Cannot set; the zone range overlaps.</en>
										setMessage("6123077");

		System.out.println( "<< HardZoneCreater.java  checkZoneRange(Connection conn, ZoneParameter param, Parameter[] array)  !001" );
										return false;
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println( "<< HardZoneCreater.java  checkZoneRange(Connection conn, ZoneParameter param, Parameter[] array)  !002" );
		return true;
	}

	//#CM50947
	/**<en>
	 * Implement the check in order to see that the identical data has been selected when chosing 
	 * data from the list box to edit.
	 * In zone maintenance process, it checks whether/not the serial no. of appending parameter
	 * exists in the entered data summary.
	 * @param param :the parameter which will be appended in this process
	 * @param array :entered data summary (pool)
	 * @return      :return true if identical data exists.
	 </en>*/
	private boolean isSameData(HardZoneParameter param, 
								Parameter[] array)
	{
		//#CM50948
		//<en>Key to compare</en>
		int orgZoneID = 0;
		int newZoneID = 0;

		//#CM50949
		//<en>If there is the entered data summary,</en>
		if(array.length > 0)
		{
			//#CM50950
			//<en>The zone ID of the parameter being appended in this process</en>
			newZoneID = param.getZoneID();
			for (int i = 0 ; i < array.length ; i++)
			{
				HardZoneParameter castparam = (HardZoneParameter)array[i];
				orgZoneID = castparam.getZoneID();
				//#CM50951
				//<en>Check for identical zone IDs</en>
				if ( newZoneID == orgZoneID)
				{
					//#CM50952
					//<en> 6123003 = Cannot edit the identical data.</en>
					setMessage("6123003");
					return true;
				}
			}
		}
		return false;
	}

	//#CM50953
	/**<en>
	 * Retrieve the Zone isntance.
	 * @return :the array of <code>Zone</code> object
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private HardZone[] getHardZoneArray(Connection conn) throws ReadWriteException
	{
		ToolHardZoneSearchKey hardzoneKey  = new ToolHardZoneSearchKey();
		hardzoneKey.setHardZoneIDOrder(1,true);
		//#CM50954
		//<en>*** Retrieve the zone isntance ***</en>
		HardZone[] array = (HardZone[])getToolHardZoneHandler(conn).find(hardzoneKey);
		return array;
	}

	//#CM50955
	/**<en>
	 * Delete data from HARDZONE table.
	 * @return   :returns true if deletion succeeded, or false if failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private void isDropZone(Connection conn) throws ReadWriteException,NotFoundException
	{
		wZoneKey = new ToolHardZoneSearchKey();
		//#CM50956
		/* 2004/11/29 MODIFY INOUE START*/

		//#CM50957
		// Call drop Method only when the Deletion line exists.  
		if (getToolHardZoneHandler(conn).count(wZoneKey) > 0)
		{
			getToolHardZoneHandler(conn).drop(wZoneKey);
		}
		//#CM50958
		/* 2004/11/29 MODIFY INOUE END*/

	}

	//#CM50959
	/**<en>
	 * Update the all hard zone items in SHELF table to '0'.
	 * @return   :returns true if update succeeded, or false if failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private void isZeroUpdateShelf(Connection conn) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		ToolShelfHandler toolshelfHandle = new ToolShelfHandler(conn);
		//#CM50960
		// Call drop Method only when the Deletion line exists.  
		ToolShelfSearchKey key = new ToolShelfSearchKey();
		if (toolshelfHandle.count(key) > 0)
		{
			//#CM50961
			//<en> Set 0 for hard zone items in Shelf table.</en>
			toolshelfHandle.modifyHardZone(0);
		}
	}

	//#CM50962
	/**<en>
	 * Implements the checks to see if the input value for bank/bay/level is 0 or greater.
	 * @param param  :the parameter which will be appended in this process
	 * @return       :return true if the data less than 0 is found.
	 </en>*/
	private boolean isZeroSameData(HardZoneParameter param)
	{
		if(param.getStartBank() <= 0 || param.getStartBay() <= 0 || param.getStartLevel() <= 0 
		|| param.getEndBank() <= 0   || param.getEndBay() <= 0   || param.getEndLevel() <= 0)
		{
			//#CM50963
			//<en> 6123071 = Please specify values which is greater than 1 (inclusive) for the range.</en>
			setMessage("6123071");
			return true;
		}
		return false;
	}

	//#CM50964
	/**<en>
	 * Implements checks to see if the same zone ID is included in priority.
	 * Different message will be used depending the zone ID which is found: the own ID or the other zone ID. 
	 * @param param  :the parameter which will be appended in this process
	 * @return       :return true if the data less than 0 is found.
	 </en>*/
	private boolean isPrioritySameData(HardZoneParameter param)
	{
		String priority = "";
		String nextpriority = "";
		String pri = param.getPriority();

		//#CM50965
		//<en> No check will be done if there only is the own zone ID.</en>
		if (pri.length() == 1) 	return false;

		for(int i = 0; i < pri.length() ; i++)
		{
			priority = pri.substring(i,i + 1);
			//#CM50966
			//<en>Check starting from (i + 1byte).</en>
			for(int l = i + 1; l < pri.length() ; l++)
			{
				nextpriority = pri.substring(l,l + 1);
				//#CM50967
				//<en> 1st data includes the own ID.</en>
				if(nextpriority.equals(priority))
				{
					//#CM50968
					//<en> Own zone ID for the 1st data.</en>
					if(i == 0)
					{
						//#CM50969
						//<en> 6123174 = Own zone ID is included in priority.</en>
						setMessage("6123174");
					}
					else
					{
						//#CM50970
						//<en> 6123175 = Input zone ID ({0}) is duplicated in priority.</en>
						setMessage("6123175" + wDelim + priority);
					}
					return true;
				}
			}
		}
		return false;
	}

	//#CM50971
	/**<en>
	 * Check whether/not the zone ID which was used in priority within entered data summary exists.
	 * Return true if the data is found which does not exist.
	 * @param param :the parameter which will be appended in this process
	 * @param array :entered data summary (pool)
	 * @return      :return true if data which does not exist is found.
	 </en>*/
	private boolean isPrioritySameData(HardZoneParameter param, 
									Parameter[] array)
	{

		String pri = param.getPriority();
		String priority = "";
		boolean trueflg = true;

		//#CM50972
		//<en>*** Check with entered data summary. ***</en>
		//#CM50973
		//<en>If there is the entered data summary,</en>
		if(array.length > 0)
		{
			for(int l = 1; l < pri.length(); l++)
			{
				//#CM50974
				//<en> Retrieve data per 1byte. (no processing for header, as it shuold be the own zone ID)</en>
				priority = pri.substring(l,l + 1);
				trueflg = false;
				for(int i = 0; i < array.length; i++)
				{
					HardZoneParameter castparam = (HardZoneParameter)array[i];
					//#CM50975
					//<en>Identical zone ID</en>
					if( castparam.getZoneID() == Integer.parseInt(priority))
					{
						trueflg = true;
					}
				}
				//#CM50976
				//<en> If there was no identical zone ID,</en>
				if (!trueflg)
				{
					//#CM50977
					//<en> 6123176 = The zone ID entered does not exist in priority.</en>
					setMessage("6123176" + wDelim + priority);
					return trueflg;
				}
			}
		}
		else
		{
			if(pri.length() > 1)
			{
				//#CM50978
				//<en> 6123176 = The zone ID entered ({0}) does not exist in priority.</en>
				setMessage("6123176" + wDelim + pri.substring(1,2));
				return false;
			}
		}
		return trueflg;
	}

	//#CM50979
	/**<en>
	 * Determine whether/not the data is modifiable.
	 * Conditions to check when deleting the hard zone 
	 * @param <code>ZoneParameter</code>
	 * @param array :entered data summary (pool)
	 * @return      :returns true if the data is modificable.
	 </en>*/
	private boolean isChangeable(HardZoneParameter param,Parameter[] array) throws ScheduleException
	{
		int zoneid = param.getZoneID();

		//#CM50980
		//<en>If there is the entered data summary,</en>
		if(array.length > 0)
		{
			for(int i = 0; i < array.length; i++)
			{
				HardZoneParameter castparam = (HardZoneParameter)array[i];
				if(zoneid != castparam.getZoneID())
				{
					String pri = castparam.getPriority();
					String priority = "";
					for(int l = 0 ; l < pri.length() ; l++)
					{
						priority = pri.substring(l,l + 1);
						if( zoneid == Integer.parseInt(priority))
						{
							//#CM50981
							//<en>Cannot delete the data; it has been set prioritized in other hard zone.</en>
							setMessage("6123166");
							return false;
						}
					}
				}
			}

		}
		return true;
	}
}
//#CM50982
//end of class


