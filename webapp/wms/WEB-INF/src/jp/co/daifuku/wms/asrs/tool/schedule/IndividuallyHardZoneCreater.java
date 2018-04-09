// $Id: IndividuallyHardZoneCreater.java,v 1.2 2006/10/30 02:52:03 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM51038
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolHardZoneHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolHardZoneSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfAlterKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;

//#CM51039
/**<en>
 * This class opreates the individual hard zone settings.
 * IT inherits the AbstractCreater, and implements the processes requried for 
 * the individual hard zone setting.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>okamura</TD><TD>Deleted unnecessary method.<BR>
 * write,read,getSeparatedItem,getArray,isExistSameData,unsetZoneidLocation,getText
 * </TD></TR>
 * <TR><TD></TD><TD></TD><TD>Deleted unnecessary import.</TD></TR>	
 * <TR><TD></TD><TD></TD><TD>Corected the comment for setZoneidLocation.</TD></TR>
 * <TR><TD></TD><TD></TD><TD>Deleted unnecessary check in 'create' and catch.</TD></TR>
 * <TR><TD></TD><TD></TD><TD>Deleted all descrption concerning Hashtable.</TD></TR>
 * <TR><TD></TD><TD></TD><TD>Added restricted location checking in 'check'.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:03 $
 * @author  $Author: suresh $
 </en>*/
public class IndividuallyHardZoneCreater extends AbstractCreater
{
	//#CM51040
	// Class fields --------------------------------------------------
	
	//#CM51041
	// Class variables -----------------------------------------------
	
	//#CM51042
	// Class method --------------------------------------------------
	//#CM51043
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:03 $") ;
	}

	//#CM51044
	// Constructors --------------------------------------------------
	//#CM51045
	/**<en>
	 * Initialize this class. Generate the instance of <CODE>ReStoringHandler</CODE> at the initialization.
	 * @param conn :connection object with database
	 * @param kind :process type
	 </en>*/
	public IndividuallyHardZoneCreater(Connection conn, int kind )
	{
		super(conn, kind);
	}

	//#CM51046
	// Public methods ------------------------------------------------
	
	
	
	//#CM51047
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
	//#CM51048
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
		Shelf[] array = getShelfArray(conn);
		//#CM51049
		//<en>Vector where the data will temporarily be stored</en>
		//#CM51050
		//<en>Set the max number of data as an initial value for entered data summary.</en>
		Vector vec = new Vector(100);	
		//#CM51051
		//<en>Entity class for display</en>
		IndividuallyHardZoneParameter dispData = null;
		if(array.length > 0)
		{
			for(int i = 0; i < array.length; i++)
			{
				dispData = new IndividuallyHardZoneParameter();
				dispData.setZoneID(array[i].getHardZone());
				dispData.setWarehouseNumber(getFindUtil(conn).getWarehouseNumber(array[i].getWarehouseStationNumber()));
				dispData.setBank(array[i].getBank());
				dispData.setBay(array[i].getBay());
				dispData.setLevel(array[i].getLevel());

				vec.addElement(dispData);
			}
			IndividuallyHardZoneParameter[] fmt = new IndividuallyHardZoneParameter[vec.size()];
			vec.toArray(fmt);
			return fmt;
		}
		return null;
	}
	//#CM51052
	/**<en>
	 * Return the parameter array.
	 * @return :parameter array
	 </en>*/
//#CM51053
/*	public Parameter[] getParameters()
	{
		ReStoringMaintenanceParameter[] mArray 
			= new ReStoringMaintenanceParameter[wParamVec.size()];
		wParamVec.copyInto(mArray);
		return mArray;
	}
*/	
	//#CM51054
	/**<en>
	 * Processes the parameter check. It will be called when adding the parameter, before the
	 * execution of maintenance process.
	 * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
	 * < number used in modification ><BR>
	 *  -the number should be less than the restoring quantity.
	 *  -the number should be less than standard load quantity.
	 * @param param :parameter to check
	 * @return :returns true if there is no error with parameter, or returns false if there are any errors.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
	 </en>*/
	public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		IndividuallyHardZoneParameter mParam 
			= (IndividuallyHardZoneParameter)param;
		//#CM51055
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM51056
		//<en>Registration</en>
		if(processingKind == M_CREATE)
		{
			//#CM51057
			//<en> Check if the data is registered in warehouse table.</en>
			ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(conn);
			ToolWarehouseSearchKey warehosuekey = new ToolWarehouseSearchKey();
			if (warehousehandle.count(warehosuekey) <= 0)
			{
				//#CM51058
				//<en> 6123117 = There is no information of the warehouse. Please register in warehouse setting screen.</en>
				setMessage("6123117");
				return false;
			}
			//#CM51059
			//<en> Check if the data is registered in hard zone table.</en>
			ToolHardZoneHandler hardzonehandle = new ToolHardZoneHandler(conn) ;
			ToolHardZoneSearchKey hardzonekey = new ToolHardZoneSearchKey() ;
			if( hardzonehandle.count(hardzonekey) <= 0 )
			{
				//#CM51060
				//<en> 6123267 = There is no zone information. Please register in zone (range) setting.</en>
				setMessage("6123267");
				return false ;
			}
			//#CM51061
			//<en>Check to see if specified shelf exists in the warehouse.</en>
			if(!getFindUtil(conn).isExistShelf(mParam.getWarehouseNumber(),
											mParam.getBank(), 
											mParam.getBay(), 
											mParam.getLevel()))
			{
				//#CM51062
				//<en>6123037 Specified location no. does not exist in the warehouse.</en>
				setMessage("6123037");
				return false;
			}
//#CM51063
/* 2003/12/11  INSERT  okamura  START */

			//#CM51064
			//<en>Check to see if the specified location is unusable.</en>
			ToolShelfSearchKey shelfkey = new ToolShelfSearchKey();
			shelfkey.setWarehouseStationNumber( getFindUtil(conn).getWarehouseStationNumber(mParam.getWarehouseNumber()) );
			shelfkey.setBank(mParam.getBank());
			shelfkey.setBay(mParam.getBay());
			shelfkey.setLevel(mParam.getLevel());
			Shelf[] shelf = (Shelf[])getToolShelfHandler(conn).find(shelfkey);

			if(shelf[0].isAccessNgFlag())
			{
				//#CM51065
				//<en>6123274 = Since it is set as the use improper shelf, specified shelf No. cannot be set up.</en>
				setMessage("6123274");
				return false;
			}
//#CM51066
/* 2003/12/11  INSERT  okamura  END */			

			return true;
		}
		//#CM51067
		//<en>Error with the process type.</en>
		else
		{
			//#CM51068
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM51069
			//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}

	//#CM51070
	/**<en>
	 * Process the parameter duplicate check.
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
		IndividuallyHardZoneParameter mParam = (IndividuallyHardZoneParameter)param;
		//#CM51071
		//<en>*** This process is originally to be implemented by 'check', </en>
		//<en>*** however implemented here since checking is needed only when </en>
		//<en>*** the entere buttone was pressed.</en>
		//#CM51072
		//<en>CMENJP7577$CM*** Because I want to do this check, it mounts here. </en>
	
		//#CM51073
		//<en>Check the identical data.</en>
		if(isSameData(mParam, mArray))
		{
			return false;
		}
		return true;
	}

	//#CM51074
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
		//#CM51075
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM51076
		//<en>Registration</en>
		if(processingKind == M_CREATE)
		{
			if(!create(conn))
			{
				return false;
			}
			//#CM51077
			//<en>6121004 = Edited the data.</en>
			setMessage("6121004");
			return true;
		}
		//#CM51078
		//<en>Error with the process type.</en>
		else
		{
			//#CM51079
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM51080
			//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}

	//#CM51081
	// Package methods -----------------------------------------------

	//#CM51082
	// Protected methods ---------------------------------------------
	//#CM51083
	/**<en>
	 * Retrieve the <CODE>ToolShelfHandler</CODE> instance generated at the initialization of this class.
	 * @return <CODE>ToolShelfHandler</CODE>
	 </en>*/
	protected ToolShelfHandler getToolShelfHandler(Connection conn)
	{
		return new ToolShelfHandler(conn);
	}
	
	//#CM51084
	/**<en>
	 * Retrieve the <CODE>ToolFindUtil</CODE> instance generated at the initialization of this class.
	 * @return <CODE>ToolFindUtil</CODE>
	 </en>*/
	protected ToolFindUtil getFindUtil(Connection conn)
	{
		return new ToolFindUtil(conn);
	}
	//#CM51085
	/**<en>
	 * Conduct the complementarity process of parameter.<BR>
	 * - Append ReStoring instance to the parameter in ordder to check whether/not the data 
	 *   has been modified by other terminals.
	 * It returns the complemented parameter if the process succeeded, or returns false if it failed.
	 * It returns true if the maintenance process succeeded, or false if it failed.
	 * @param param :parameter which is used for the complementarity process
	 * @return :returns the parameter if the process succeeded, ro returns null if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the process.
	 </en>*/
	protected  Parameter complementParameter(Parameter param)throws ReadWriteException, ScheduleException
	{
		return param;
	}
	//#CM51086
	/**<en>
	 * Process the maintenance modifications.
	 * Modification will be made based on the key items of parameter array. 
	 * Sets the work no., item code and lot no. to the AlterKey as search conditions, and updates storage quantity.
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

	//#CM51087
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
			IndividuallyHardZoneParameter castparam = null;
			
			if( mArray.length > 0)
			{
				for(int i = 0; i < mArray.length; i++)
				{
					castparam = (IndividuallyHardZoneParameter)mArray[i];
					//#CM51088
					//<en> data newly entered:</en>
					int warehouseNumber = castparam.getWarehouseNumber();
					int zoneid = castparam.getZoneID();
					int bank = castparam.getBank();
					int bay = castparam.getBay();
					int level = castparam.getLevel();

//#CM51089
/* 2003/12/11 DELETE okamura START */

//#CM51090
//					ToolShelfSearchKey shelfKey = new ToolShelfSearchKey();
//#CM51091
//<en>					//warehouse station no.</en>
//#CM51092
//					String whstno = getFindUtil(conn).getWarehouseStationNumber(warehouseNumber);
//#CM51093
//					shelfKey.setWarehouseStationNumber(whstno);
//#CM51094
//					shelfKey.setBank(bank);
//#CM51095
//					shelfKey.setBay(bay);
//#CM51096
//					shelfKey.setLevel(level);
//#CM51097
//					Shelf[] shelf = (Shelf[])getToolShelfHandler(conn).find(shelfKey);
//#CM51098
//					
//#CM51099
//<en>					//If there is no corresponding location,</en>
//#CM51100
//					if(shelf==null || shelf.length == 0)
//#CM51101
//					{
//#CM51102
//<en>						//6123037 = The location specified does not exist in warehouse.</en>
//#CM51103
//						setMessage("6123037");
//#CM51104
//						return false;
//#CM51105
//					}

//#CM51106
/* 2003/12/11 DELETE okamura END */


					//#CM51107
					//<en>Set the location unavailable.</en>
					setZoneidLocation(conn, warehouseNumber,	bank, bay, level, zoneid);
				}
			}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM51108
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

	//#CM51109
	// Private methods -----------------------------------------------
	//#CM51110
	/**<en>
	 * Check whether/not thd data has already been modified by other terminals.
	 * Compares the ReStoring instance which has been set to maintenance parameter with the other
	 * Restoring instance which has been retrieved from current DB.
	 * If the comparison resulted the both instances are equal, it regards that no modification was
	 * made by other terminals. If the comparison resulted these instances to be different, then it 
	 * regards the other terminal already modified the data.
	 * @param ReStoringMaintenanceParameter
	 * @return :returns true if the data ghas already been modified by other terminals, or 
	 * returns false if it has not been modified.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	private boolean isAlreadyChanged(WarehouseParameter param) throws ReadWriteException
	{
		//#CM51111
		//<en>In this version, the check for data updates made by terminals is not included in process. Kawasima</en>
		//#CM51112
		//<en>CMENJP7598$CM In this release, do not do the update check of the other end. Kawashima</en>
		return false;
	}
	
	
	//#CM51113
	/**<en>
	 * Implement the check in order to see that the identical data has been selected when 
	 * chosing data from the list box to edit.
	 * In warehouse setting, it checks whether/not the storage type of appending parameter exists in 
	 * the entered data summary.
	 * @param param  :the parameter which will be appended in this process
	 * @param array  :entered data summary (pool)
	 * @return       :return true if identical data exists.
	 </en>*/
	private boolean isSameData(IndividuallyHardZoneParameter param, 
								Parameter[] array)
	{
		//#CM51114
		//<en>Key to compare</en>
		int warehousenumber = 99999;
		//#CM51115
		//<en>Value which is unused in normal processes</en> 
		int bank = 0;
		//#CM51116
		//<en>Value which is unused in normal processes</en> 
		int bay = 0; 
		//#CM51117
		//<en>Value which is unused in normal processes</en> 
		int level = 0; 

		int orgwarehousenumber = 99999;
		int orgbank = 0;
		int orgbay = 0; 
		int orglevel = 0;
		
		//#CM51118
		//<en>If there is the entered data summary,</en>
		if(array.length > 0)
		{
			//#CM51119
			//<en>Compare by the location appended in this process.</en>
			warehousenumber = param.getWarehouseNumber();
			bank = param.getBank();
			bay = param.getBay();
			level = param.getLevel();
			
			for (int i = 0 ; i < array.length ; i++)
			{
				IndividuallyHardZoneParameter castparam = (IndividuallyHardZoneParameter)array[i];
				//#CM51120
				//<en>Key for the entered data summary</en>
				orgwarehousenumber = castparam.getWarehouseNumber();
				orgbank = castparam.getBank();
				orgbay = castparam.getBay();
				orglevel = castparam.getLevel();

				//#CM51121
				//<en>Check the identical lcoations</en>
				if ( warehousenumber == orgwarehousenumber && bank == orgbank && bay == orgbay && level == orglevel )
				{
					//#CM51122
					//<en>6123033 = The data is already entered. Cannot input the identical location no.</en>
					setMessage("6123033");
					return true;
				}
			}
		}
		return false;
	}
	
	//#CM51123
	/**<en>
	 * Set the shelf of the specified location 'unavailable'.
	 * The location which is defined in StationType table will also be deleted at the same time.
	 * @param warehousenumber :storage type
	 * @param bank  :bank
	 * @param bay   :bay
	 * @param level :level
	 * @param zoneid :zone ID
	 </en>*/
	private void setZoneidLocation(Connection conn, int warehousenumber, int bank, int bay, int level, int zoneid) throws ScheduleException
	{
		try
		{
			ToolShelfAlterKey alterkey = new ToolShelfAlterKey();
			//#CM51124
			//<en>warehouse station no.</en>
			String whstno = getFindUtil(conn).getWarehouseStationNumber(warehousenumber);
			alterkey.setWHNumber(whstno);
			alterkey.setNBank(bank);
			alterkey.setNBay(bay);
			alterkey.setNLevel(level);
			alterkey.updateHardZone(zoneid);
			getToolShelfHandler(conn).modify(alterkey);
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		catch(NotFoundException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		catch(InvalidDefineException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM51125
	/**<en>
	 * Retrieve the Shelf instance.
	 * @return :the array of <code>Shelf</code> object
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private Shelf[] getShelfArray(Connection conn) throws ReadWriteException
	{
		ToolShelfSearchKey shelfKey  = new ToolShelfSearchKey();
		shelfKey.setNumberOrder(1,true);
		//#CM51126
		//<en>*** Retrieve the Shelf isntance ***</en>
		Shelf[] array = (Shelf[])getToolShelfHandler(conn).find(shelfKey);
		return array;
	}
}
//#CM51127
//end of class

