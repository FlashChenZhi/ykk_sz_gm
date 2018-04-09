// $Id: UnavailableLocationCreater.java,v 1.2 2006/10/30 02:51:59 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM51813
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import jp.co.daifuku.wms.asrs.tool.common.LogHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfAlterKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolWarehouseSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;

//#CM51814
/**<en>
 * This class sets up the restricted locations.
 * IT inherits the AbstractCreater, and implements the processes requried to set the restricted 
 * locations. 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>okamura</TD><TD>disabled the writing with texts via this display.<BR>
 * Through this screen, ACCESSNGFLAG of Shelf table for the restricted locations can be updated.<BR>
 * The writing of the texts will be done at DataOperator.java.<BR>
 * Also it reads text when transfering to this screen and create locations which have been deleted
 * from SHELF table.
 * </TD></TR>
 * <TR><TD>2003/12/19</TD><TD>okamura</TD><TD>Modified method of setUnavailableLocation<BR>
 * The conditions for displaying at the time of screen changes are added.
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:51:59 $
 * @author  $Author: suresh $
 </en>*/
public class UnavailableLocationCreater extends AbstractCreater
{
	//#CM51815
	// Class fields --------------------------------------------------
	//#CM51816
	/**<en> Name of the text which saves the shelf information. </en>*/

	public final String SHELFTEXT_NAME = "UnUnavailableLoc.txt";

	//#CM51817
	/**<en>
	 * Delimiter
	 </en>*/
	public static final String DELIM = ",";

	//#CM51818
	// Class variables -----------------------------------------------
	//#CM51819
	/**<en>
	 * The text which preserves shelves that have been set as unavailable.
	 </en>*/
	public String wFileName = "";

//#CM51820
/* 2003/12/12 INSERT okamura START */

	private boolean TRUE = true;

	private boolean FALSE = false;
//#CM51821
/* 2003/12/12 INSERT okamura END   */


	//#CM51822
	// Class method --------------------------------------------------
	//#CM51823
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:51:59 $") ;
	}

	//#CM51824
	// Constructors --------------------------------------------------
	//#CM51825
	/**<en>
	 * Initialize this class. Generate the instance of <CODE>ReStoringHandler</CODE> at the initialization.
	 * @param conn :connection object with database
	 * @param kind :process type
	 </en>*/
	public UnavailableLocationCreater(Connection conn, int kind )
	{
		super(conn, kind);
	}

	//#CM51826
	// Public methods ------------------------------------------------
	//#CM51827
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

	//#CM51828
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
		String filepath = ((UnavailableLocationParameter)searchParam).getFileName();
		//#CM51829
		//<en>File name should be set here, as it cannot be provided from external by Create method.</en>
		//#CM51830
		//<en>CMENJP8180$CM set here..</en>
		wFileName = filepath + "/" + SHELFTEXT_NAME;

		//#CM51831
		//<en>Vector where the data will temporarily be stored</en>
		//#CM51832
		//<en>Set the max number of data as an initial value for entered data summary.</en>
		Vector vec = new Vector(100);

		//#CM51833
		//<en>*** Read the unavailable location settings ***</en>
		String[] textArray = read(wFileName);
		if(textArray != null)
		{
			for(int i = 0; i < textArray.length; i++)
			{
				//#CM51834
				//<en>Create the SHELF table according to the contents of the text.</en>
				setUnavailableLocation(conn, textArray[i]);
			}
		}

		//#CM51835
		//<en>Create the entity class for display.</en>
		UnavailableLocationParameter dispData = null;
		Shelf[] array = getShelfArray(conn);
		if(array.length > 0)
		{
			for(int disp = 0; disp < array.length; disp++)
			{
				dispData = new UnavailableLocationParameter();
				int whNumber = getFindUtil(conn).getWarehouseNumber(array[disp].getWarehouseStationNumber());
				dispData.setWarehouseNumber( whNumber );
				dispData.setBank(array[disp].getBank());
				dispData.setBay(array[disp].getBay());
				dispData.setLevel(array[disp].getLevel());
				vec.addElement(dispData);
			}
			UnavailableLocationParameter[] fmt = new UnavailableLocationParameter[vec.size()];
			vec.toArray(fmt);
			return fmt;
		}
		return null ;

	}

	//#CM51836
	/**<en>
	 * Processes the parameter check. It will be called when adding the parameter, before the 
	 * execution of maintenance process.
	 * If there are any error with parameter, the reason can be obtained by <code>getMessage</code>.
	 * Check to see of the specified location exists in the warehouse.
	 * @param param :parameter to check
	 * @return :returns true if there is no error with parameter, or returns false if there are any errors.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	public boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		UnavailableLocationParameter mParam 
			= (UnavailableLocationParameter)param;
		//#CM51837
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM51838
		//<en>Registration</en>
		if(processingKind == M_CREATE)
		{
			//#CM51839
			//<en> Check to see if the specified data is registered in warehouse table.</en>
			ToolWarehouseHandler warehousehandle = new ToolWarehouseHandler(conn);
			ToolWarehouseSearchKey warehosuekey = new ToolWarehouseSearchKey();
			if (warehousehandle.count(warehosuekey) <= 0)
			{
				//#CM51840
				//<en> 6123117 = The information of the warehouse cannot be found. </en>
				//<en> Please register in warehouse setting screen.</en>
				setMessage("6123117");
				return false;
			}
			//#CM51841
			//<en>Check if the specified data is registered in warehouse table.</en>
			if(!getFindUtil(conn).isExistShelf(mParam.getWarehouseNumber(),
											mParam.getBank(), 
											mParam.getBay(), 
											mParam.getLevel()))
			{
				//#CM51842
				//<en>6123037 The location no. specified does not exist in the warehouse.</en>
				setMessage("6123037");
				return false;
			}
			return true;
		}
		//#CM51843
		//<en>Error with the process type.</en>
		else
		{
			//#CM51844
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM51845
			//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}

	//#CM51846
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
		//#CM51847
		//<en>True if there is no error.</en>
		boolean errorFlag = true;
		String logfile = logpath + ToolParam.getParam("CONSTRAINT_CHECK_FILE");
		try
		{
			LogHandler loghandle = new LogHandler(logfile, locale);

			Shelf[] shelfArray = getShelfArray(conn);
			if(shelfArray.length == 0)
			{
				//#CM51848
				//<en>If the unavailable setting has not been done withe locations, discontinue</en>
				//<en>the checks and exit.</en>
				return true ;
			}
			else
			{
				for( int i = 0; i < shelfArray.length; i++ )
				{
					String whStationNumber = shelfArray[i].getWarehouseStationNumber() ;
					int bank  = shelfArray[i].getBank();
					int bay   = shelfArray[i].getBay();
					int level = shelfArray[i].getLevel();
					 
					String location = Integer.toString(bank) + Integer.toString(bay) + Integer.toString(level) ;
					String errorMsg = "";
				
					int bayRange[] = getToolShelfHandler(conn).getBayRange(whStationNumber, bank);
//#CM51849
/* 2003/12/19 MODIFY okamura START */

					//#CM51850
					//<en>Check the bay range.</en>
					if(bayRange[0] > bay || bay > bayRange[1])
					{
						//#CM51851
						//<en>6123142=It does not exist in current location range. LocationNo={0}</en>
						errorMsg = "6123142" + wDelim + location;
						loghandle.write("UnavailableLocation", "UnavailableLocationText", errorMsg);
						errorFlag = false;
					}
					int levelRange[] = getToolShelfHandler(conn).getLevelRange(whStationNumber, bank);
					//#CM51852
					//<en>Check the level range.</en>
					if(levelRange[0] > level || level > levelRange[1])
//#CM51853
/* 2003/12/19 MODIFY okamura END   */

					{
						//#CM51854
						//<en>6123142=It does not exist in current location range. LocationNo={0}</en>
						errorMsg = "6123142" + wDelim + location;
						loghandle.write("UnavailableLocation", "UnavailableLocationText", errorMsg);
						errorFlag = false;
					}
					
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}			
		return errorFlag;
	}
	
	//#CM51855
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
		UnavailableLocationParameter mParam = (UnavailableLocationParameter)param;
		//#CM51856
		//<en>*** Though this process should be basically implemented by 'check', </en>
		//<en>*** it is implemented here in this method as this checking is needed </en>
		//<en>*** only when the entere button was pressed.</en>
		//#CM51857
		//<en>CMENJP8204$CM*** Because I want to do this check, it mounts here. </en>

		//#CM51858
		//<en>Check the identical data.</en>
		if(isSameData(mParam, mArray))
		{
			return false;
		}
		return true;
	}

	//#CM51859
	/**<en>
	 * Conduct the maintenance process.
	 * It is necessary that type of the maintentace should be determined internally according to
	 * the process type (obtained by getProcessingKind() method.)
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :returns true if the process succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	public boolean doStart(Connection conn) throws ReadWriteException, ScheduleException
	{
		//#CM51860
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM51861
		//<en>Registration</en>
		if(processingKind == M_CREATE)
		{
			if(!create(conn))
			{
				return false;
			}
			//#CM51862
			//<en>6121004 = Edited the data.</en>
			setMessage("6121004");
			return true;
		}
		//#CM51863
		//<en>Error with the process type.</en>
		else
		{
			//#CM51864
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM51865
			//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}

	//#CM51866
	// Package methods -----------------------------------------------

	//#CM51867
	// Protected methods ---------------------------------------------
	//#CM51868
	/**<en>
	 * Retrieve the <CODE>ToolShelfHandler</CODE> isntance which was generated at the
	 * generation of this class.
	 * @return <CODE>ToolShelfHandler</CODE>
	 </en>*/
	protected ToolShelfHandler getToolShelfHandler(Connection conn)
	{
		return new ToolShelfHandler(conn);
	}
	
	//#CM51869
	/**<en>
	 * Retrieve the <CODE>ToolFindUtil</CODE>isntance which was generated at the
         * generation of this class.
	 </en>*/
	protected ToolFindUtil getFindUtil(Connection conn)
	{
		return new ToolFindUtil(conn);
	}
	
	//#CM51870
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
	//#CM51871
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

	//#CM51872
	/**<en>
	 * Process the maintenance registrations.
	 * It returns true if the maintenance process succeeded, or false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :returns true if the process succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	protected boolean create(Connection conn) throws ReadWriteException, ScheduleException
	{
		try
		{
			Parameter[] mArray = (Parameter[])getParameters();
			UnavailableLocationParameter castparam = null;

			ToolShelfAlterKey shelfAlt = new ToolShelfAlterKey();

			if( mArray.length > 0)
			{
				setAccessNgFlag(conn);
				for(int i = 0; i < mArray.length; i++)
				{
					castparam = (UnavailableLocationParameter)mArray[i];

					//#CM51873
					//<en>Retrieve the warehouse station no.</en>
					String whstno = getFindUtil(conn).getWarehouseStationNumber(castparam.getWarehouseNumber());
					shelfAlt.setWHNumber(whstno);
					shelfAlt.setNBank(castparam.getBank());
					shelfAlt.setNBay(castparam.getBay());
					shelfAlt.setNLevel(castparam.getLevel());
					//#CM51874
					//<en> inaccessible (set restriction)</en>
					shelfAlt.updateAccessNgFlag(TRUE);	

					getToolShelfHandler(conn).modify(shelfAlt);
				}
				return true;
			}
			//#CM51875
			//<en>If there is no data to process,</en>
			else
			{
				setAccessNgFlag(conn);
				return true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM51876
	/**<en>
	 * Process the deletion of maintenance.
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

	//#CM51877
	// Private methods -----------------------------------------------
	//#CM51878
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
	private boolean isAlreadyChanged(WarehouseParameter param) throws ReadWriteException
	{
		//#CM51879
		//2003/02/11 13:47:25
		//#CM51880
		//<en>In this version, the check for data updates made by terminals is not included in process. Kawasima</en>
		return false;
	}
	
	//#CM51881
	/**<en>
	 * Implement the check in order to see that the identical data has been selected when chosing data
	 * from the list box to edit. In the setting of restricted locations, it checks whether/not the 
	 * identical data as apprending parameter (storage type, bank, level) exists in the entered data summary.
	 * @param param  :the parameter which will be appended in this process
	 * @param array  :entered data summary (pool)
	 * @return       :return true if identical data exists.
	 </en>*/
	private boolean isSameData(UnavailableLocationParameter param, 
								Parameter[] array)
	{
		//#CM51882
		//<en>Key to compare</en>
		int warehousenumber = 99999;
		//#CM51883
		//<en>Value which is unused in normal processes</en> 
		int bank = 0; 
		//#CM51884
		//<en>Value which is unused in normal processes</en> 
		int bay = 0; 
		//#CM51885
		//<en>Value which is unused in normal processes</en> 
		int level = 0; 

		int orgwarehousenumber = 99999;
		int orgbank = 0;
		int orgbay = 0; 
		int orglevel = 0;
		
		//#CM51886
		//<en>If there is the entered data summary,</en>
		if(array.length > 0)
		{
			//#CM51887
			//<en>Compare by the location appended in this process.</en>
			warehousenumber = param.getWarehouseNumber();
			bank = param.getBank();
			bay = param.getBay();
			level = param.getLevel();
			
			for (int i = 0 ; i < array.length ; i++)
			{
				UnavailableLocationParameter castparam = (UnavailableLocationParameter)array[i];
				//#CM51888
				//<en>Key for the entered data summary</en>
				orgwarehousenumber = castparam.getWarehouseNumber();
				orgbank = castparam.getBank();
				orgbay = castparam.getBay();
				orglevel = castparam.getLevel();

				//#CM51889
				//<en>Check the identical location no.</en>
				if ( warehousenumber == orgwarehousenumber && bank == orgbank && bay == orgbay && level == orglevel )
				{
					//#CM51890
					//<en>6123033 = The data has already been entered. Cannot enter the identical location no.</en>
					setMessage("6123033");
					return true;
				}
			}
		}
		return false;
	}

	//#CM51891
	/**<en>
	 * Create the SHELF table based on the information unavailable location informaion which was 
	 * retrieved from the text.
	 * If the location data already exists in the SHELF table, no processing will be done.
	 * @param conn :Connection to connect with database
	 * @param shelfText 	:anavailable location information retrieed from the text
	 * @throws ScheduleException :Notifies if unexpected exception occurred during the execution of 
     * the schedule processing.
	 </en>*/
	private void setUnavailableLocation(Connection conn, String shelfText) throws ScheduleException
	{
		try
		{
			String[] param = getSeparatedItem(shelfText);
			
			String locationNo = param[0];

			ToolShelfSearchKey shelfKey = new ToolShelfSearchKey();
			shelfKey.setNumber(locationNo);
			if( getToolShelfHandler(conn).count(shelfKey) <= 0 )
			{
//#CM51892
/* 2003/12/19 MODIFY okamura START */

				String whStationNumber = param[4];
				int bank  = Integer.parseInt(param[1]);
				int bay   = Integer.parseInt(param[2]);
				int level = Integer.parseInt(param[3]);
				 
				String location = Integer.toString(bank) + Integer.toString(bay) + Integer.toString(level) ;
				int bayRange[] = getToolShelfHandler(conn).getBayRange(whStationNumber, bank);
				//#CM51893
				//<en>Check the bay range</en>
				if(bayRange[0] > bay || bay > bayRange[1])
				{
					return ;
				}
				int levelRange[] = getToolShelfHandler(conn).getLevelRange(whStationNumber, bank);
				//#CM51894
				//<en>Check the level range</en>
				if(levelRange[0] > level || level > levelRange[1])
				{
					return ;
				}
//#CM51895
/* 2003/12/19 MODIFY okamura END   */

				//#CM51896
				//<en>station no. (location no.)</en>
				Shelf shelf = new Shelf(locationNo);				
				//#CM51897
				//<en>bank</en>
				shelf.setBank(Integer.parseInt(param[1]));			
				//#CM51898
				//<en>bay</en>
				shelf.setBay(Integer.parseInt(param[2]));			
				//#CM51899
				//<en>level</en>
				shelf.setLevel(Integer.parseInt(param[3]));			
				//#CM51900
				//<en>warehouse station no.</en>
				shelf.setWarehouseStationNumber(param[4]);			
				//#CM51901
				//<en>status (available)</en>
				shelf.setStatus(Integer.parseInt(param[5]));		
				//#CM51902
				//<en>load presence</en>
				shelf.setPresence(Integer.parseInt(param[6]));		
				//#CM51903
				//Hard Zone
				shelf.setHardZone(Integer.parseInt(param[7]));		
				//#CM51904
				//Soft Zone
				shelf.setSoftZone(Integer.parseInt(param[8]));		
				//#CM51905
				//<en>parent station</en>
				shelf.setParentStationNumber(param[9]);				
				//#CM51906
				//<en>unavailable location</en>
				if(param[10].equals(Integer.toString(Shelf.ACCESS_NG)))	
				{
					shelf.setAccessNgFlag(TRUE);
				}
				else
				{
					shelf.setAccessNgFlag(FALSE);
				}
				//#CM51907
				//<en>Create the location data.</en>
				getToolShelfHandler(conn).create(shelf);
			}
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
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

	//#CM51908
	/**<en>
	 * Read the specified file, and return the result in form of String array.
	 * @param file :<CODE>File</CODE> instance of external data to read
	 * @return :the array of String created
	 * @throws FileNotFoundException :if the specified file cannot be found
	 * @throws IllegalArgumentException :this will be thrown to indicate that incorrect or improper parameter
	 * has been passed to the method.
	 * @throws IOException :in case input/output error occurred.
	 </en>*/
	private String[] read(String filename) throws ScheduleException
	{
		try
		{
			File file = new File(filename);
			//#CM51909
			//<en>If the file exists,</en>
			if(file.exists())
			{
				//#CM51910
				//<en>Read the file.</en>
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				Vector vec = new Vector(10);
				String buf = "";
				while(( buf = br.readLine())!=null)
				{
					vec.add(buf);
				}
				fr.close();
				br.close();
				String[] array = new String[vec.size()];
				vec.copyInto(array);
			
				return array;
			}
			//#CM51911
			//<en>If the file cannot be found, return null.</en>
			else
			{
				return null;
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
		catch(IOException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM51912
	/**<en>
	 * Split the String array, which was specified through parameter, with specified delimiter then return them
	 * in form on the String array.
	 * Also it automartically delete the blanks that follow the string, if there are any, by DisplayText.trim method.
	 * Example<BR>
	 * "AAA,BBB   ,CCC" => ret[0] = "AAA", ret[1] = "BBB", ret[2] = "CCC"<BR>
	 * @param buf :string which will be split up
	 * @return    :the array of the created String
	 </en>*/
	private String[] getSeparatedItem(String buf)
	{
		Vector bufVec = new Vector();

		//#CM51913
		//<en>If there are cosecutive delimiters in the string, insert spaces of 1 byte.</en>
		buf = DisplayText.DelimiterCheck(buf,DELIM);

		StringTokenizer stk = new StringTokenizer(buf, DELIM, false) ;
		while ( stk.hasMoreTokens() )
		{
			bufVec.addElement(DisplayText.trim((String)stk.nextToken()));
		}
		String[] array = new String[bufVec.size()];
		bufVec.copyInto(array);
		return array;
	}

	//#CM51914
	/**<en>
	 * Retrieve the SHELF instance which are set as unavailable locations sorted in order of 
	 * warehouse and locations.
	 * @return :the array of <code>Shelf</code> object
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private Shelf[] getShelfArray(Connection conn) throws ReadWriteException
	{
		ToolShelfSearchKey shelfKey  = new ToolShelfSearchKey();
		shelfKey.setAccessNgFlag(Shelf.ACCESS_NG);
		shelfKey.setWHNumberOrder(1, TRUE);
		shelfKey.setBankOrder(2, TRUE);
		shelfKey.setBayOrder(3, TRUE);
		shelfKey.setLevelOrder(4, true);
		//#CM51915
		//<en>*** Retrieve the Shelf instance. ***</en>
		Shelf[] array = (Shelf[])getToolShelfHandler(conn).find(shelfKey);
		return array;
	}

	//#CM51916
	/**<en>
	 * Renew the status of the location, which is set unavailable, to 'available'.
	 * @param conn :Connection to connect with database
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if the target data for update cannot be found in database.
	 * @throws InvalidDefineException :Notifies if the contents of update has not been set.
	 </en>*/
	private void setAccessNgFlag(Connection conn) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		ToolShelfSearchKey shelfKey = new ToolShelfSearchKey() ;
		shelfKey.setAccessNgFlag(Shelf.ACCESS_NG);
		if(getToolShelfHandler(conn).count(shelfKey) > 0 )
		{
			ToolShelfAlterKey shelfAlt = new ToolShelfAlterKey();
			shelfAlt.setAccessNgFlag(Shelf.ACCESS_NG);
			//#CM51917
			//<en> accessible (set as available)</en>
			shelfAlt.updateAccessNgFlag(FALSE);	
			getToolShelfHandler(conn).modify(shelfAlt);
		}
	}

}
//#CM51918
//end of class

