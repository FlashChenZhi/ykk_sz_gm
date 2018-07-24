// $Id: RouteCreater.java,v 1.2 2006/10/30 02:52:02 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM51271
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import jp.co.daifuku.wms.asrs.tool.common.LogHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAisleSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.IniFileOperation;


//#CM51272
/**<en>
 * This class operates the carry route setting.
 * It inherits the AbstractCreater, and implements the processes required for the setting 
 * of carry route.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:02 $
 * @author  $Author: suresh $
 </en>*/
public class RouteCreater extends AbstractCreater
{
	//#CM51273
	// Class fields --------------------------------------------------
	//#CM51274
	/**<en>
	 * Delimiter for sending station no.
	 </en>*/
	public static final String wSeparate = ":";

	//#CM51275
	// Class variables -----------------------------------------------
	//#CM51276
	/**<en>
	 * Path of Route.txt
	 </en>*/
	public String wFileName = "";

	//#CM51277
	// Class method --------------------------------------------------
	//#CM51278
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:02 $") ;
	}

	//#CM51279
	// Constructors --------------------------------------------------
	//#CM51280
	/**<en>
	 * Initialize this class. Generate the instance of <CODE>ToolAs21MachineStateHandler</CODE> at 
	 * the initialization.
	 * @param conn :connection object with database
	 * @param kind :process type
	 </en>*/
	public RouteCreater(Connection conn, int kind )
	{
		super(conn, kind);
	}

	//#CM51281
	// Public methods ------------------------------------------------
	//#CM51282
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
	//#CM51283
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
		RouteParameter param = (RouteParameter)searchParam;
		wFileName = param.getRouteTextPath();

		IniFileOperation ifo = null;
		try
		{
			ifo = new IniFileOperation( wFileName, wSeparate );
		}
		catch(ReadWriteException e)
		{
			return null;
		}

		String[] fromStations = ifo.getKeys();
		String[] toStations = ifo.getValues();
		
		//#CM51284
		//<en>Vector where the data will temporarily be stored</en>
		//#CM51285
		//<en>Set the max number of data as an initial value for entered data summary.</en>
		Vector vec = new Vector(100);	
		//#CM51286
		//<en>Entity class for display</en>
		RouteParameter dispData = null;

		if(fromStations == null) return null;

		if(fromStations.length > 0)
		{
			for(int i = 0; i < fromStations.length; i++)
			{
				dispData = new RouteParameter();
				dispData.setStationNumber(fromStations[i]);
				dispData.setConnectStNumber(toStations[i]);
				vec.addElement(dispData);
			}
			RouteParameter[] fmt = new RouteParameter[vec.size()];
			vec.toArray(fmt);
			return fmt;
		}
		return null;
	}
	//#CM51287
	/**<en>
	 * Return the parameter array.
	 * @return  :parameter array
	 </en>*/
//#CM51288
/*	public Parameter[] getParameters()
	{
		ReStoringMaintenanceParameter[] mArray 
			= new ReStoringMaintenanceParameter[wParamVec.size()];
		wParamVec.copyInto(mArray);
		return mArray;
	}
*/	
	//#CM51289
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
		RouteParameter mParameter = (RouteParameter)param;
		//#CM51290
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM51291
		//<en>Registration</en>
		switch(processingKind)
		{
			case M_CREATE:
				//#CM51292
				//<en> Check to see if data is registered in station table.</en>
				ToolStationHandler sthandle = new ToolStationHandler(conn);
				ToolStationSearchKey stkey = new ToolStationSearchKey();

				if (sthandle.count(stkey) <= 0)
				{
					//#CM51293
					//<en> The information of the station cannot be found. Please register in station setting screen.</en>
					setMessage("6123079");
					return false;
				}

				//#CM51294
				//<en> Check to see data is registered in aisle table.</en>
				ToolAisleHandler ahandle = new ToolAisleHandler(conn);
				ToolAisleSearchKey akey = new ToolAisleSearchKey();

				if (ahandle.count(akey) <= 0)
				{
					//#CM51295
					//<en> The information of the aisle cannot be found. Please register in aisle setting screen.</en>
					setMessage("6123098");
					return false;
				}

				//#CM51296
				//<en> Check the connected station no.</en>
				if(isPatternMatching(mParameter.getConnectStNumber()))
				{
					//#CM51297
					//<en>6123009 = The station no. contains the unacceptable letters in system.</en>
					setMessage("6123009");
					return false;
				}

				//#CM51298
				//<en> Sending station no.</en>
				String SourceStation = mParameter.getStationNumber();

				//#CM51299
				//<en> Check to see if the sending station no. exists in STATION table and AISLE table.</en>
				if (!check.isExistAllStationNo(SourceStation)) 
				{
					//#CM51300
					//<en> The station no. which was jsut entered does not exist.</en>
					setMessage("6123080");
					return false;
				}

				//#CM51301
				//<en> Retrieve the connected station no.</en>
				StringTokenizer deftoken = new StringTokenizer(mParameter.getConnectStNumber(), ",", false) ;

				//#CM51302
				//<en> The station no. has not been entered.</en>
				int count = deftoken.countTokens();
				
				//#CM51303
				//<en> The station no. has not been entered.</en>
				if (count <= 0)
				{
					//#CM51304
					//<en> Please enter the connected station no.</en>
					setMessage("6123096");
					return false;
				}

				//#CM51305
				//<en> Generate the instance which regards the comma as a token.</en>
				StringTokenizer delimstoken = new StringTokenizer(mParameter.getConnectStNumber(), ",", true) ;
				int delimscount = delimstoken.countTokens();
				
				//#CM51306
				//<en> Check to see if the entry of comma is invalid.</en>
				if((delimscount - count) > (count - 1))
				{
					//#CM51307
					//<en> Entry of the comma is invalid.</en>
					setMessage("6123188");
					return false;
				}

				//#CM51308
				//<en> Store the connected station no.</en>
				String[] toStations = new String[count];
				for (int i = 0; i < count; i++)
				{
					toStations[i] = deftoken.nextToken();
				}

				//#CM51309
				//<en> Retrieve the warehouse station no. of the sending station.</en>
				String whstno = getWHStationNumber(conn, SourceStation);
				if (whstno == null)
				{
					//#CM51310
					//<en> The station no. has not been entered.</en>
					setMessage("6123080");
					return false;
				}

				//#CM51311
				//<en> Check to see of the connect station no. exists in STATION table </en>
				//<en> and in AISLE table.</en>
				for (int loopcnt = 0; loopcnt < count; loopcnt++)
				{
					if (!check.isExistAllStationNo(toStations[loopcnt])) 
					{
						//#CM51312
						//<en> The station no. has not been entered.</en>
						setMessage("6123080");
						return false;
					}

					//#CM51313
					//<en> workshop</en>
//#CM51314
//					int types[] = {Station.STAND_ALONE_STATIONS, Station.AISLE_CONMECT_STATIONS};
//#CM51315
//					ToolStationSearchKey stwkey = new ToolStationSearchKey();
//#CM51316
//					stwkey.setWorkPlaceType(types);
//#CM51317
//					stwkey.setNumber(toStations[loopcnt]);
//#CM51318
//					//<en> WHether/not it is a workshop:</en>
//#CM51319
//					if(sthandle.count(stwkey) > 0)
//#CM51320
//					{
//#CM51321
//						//<en> Cannot set the workshops.</en>
//#CM51322
//						setMessage("6123189");
//#CM51323
//						return false;
//#CM51324
//					}
					int types[] = {Station.STAND_ALONE_STATIONS, Station.AISLE_CONMECT_STATIONS, 
									Station.MAIN_STATIONS, Station.WPTYPE_FLOOR, Station.WPTYPE_ALL};
					ToolStationSearchKey stwkey = new ToolStationSearchKey();
					stwkey.setWorkPlaceType(types);
					stwkey.setNumber(toStations[loopcnt]);
					//#CM51325
					//<en> WHether/not it is a workshop:</en>
					if(sthandle.count(stwkey) > 0)
					{
						//#CM51326
						//<en> Cannot set the workshops.</en>
						setMessage("6123189");
						return false;
					}

					//#CM51327
					//<en> Check to see if sending station no. is set in connect stations.</en>
					if(SourceStation.equals(toStations[loopcnt]))
					{
						//#CM51328
						//<en> Sending station no. cannot be set in connect station no.</en>
						setMessage("6123208");
						return false;
					}

					//#CM51329
					//<en> Check to see if same stations are set in connect stations.</en>
					for (int stcnt = 0; stcnt < count; stcnt++)
					{
						if (loopcnt != stcnt)
						{
							if (toStations[loopcnt].equals(toStations[stcnt]))
							{
								//#CM51330
								//<en> Cannot set the identical stations in the connect station no.</en>
								setMessage("6123191");
								return false;
							}
						}
					}

					//#CM51331
					//<en> Retrieve the warehouse station no. in the connect stations.</en>
					String connectwhst = getWHStationNumber(conn, toStations[loopcnt]);
					if (connectwhst == null)
					{
						//#CM51332
						//<en> The station no. has not been entered.</en>
						setMessage("6123080");
						return false;
					}

					//#CM51333
					//<en> Check to see if the storage type of sending station and the </en>
					//<en> connect stations are the same.</en>
					if (!whstno.equals(connectwhst))
					{
						//#CM51334
						//<en> The storage type of the sending station no. and the connect </en>
						//<en> station no. are different.</en>
						setMessage("6123215");
						return false;
					}
				}

				break;
			default:
				//#CM51335
				//<en> Unexpected value has been set.{0} = {1}</en>
				String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
				RmiMsgLogClient.write( msg, (String)this.getClass().getName());
				//#CM51336
				//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
				throw new ScheduleException("6126499");	
		}

		return true;
	}

	//#CM51337
	/**<en>
	 * Process the inconsistency check. This will be called when generating the eAWC environment setting tool.
	 * If any error takes place, the detail will be written in the file which is specified by filename.
	 * @param filename :name of the file the log will be written in when error occurred.
	 * @param locale <code>Locale</code> object
	 * @return :true if there is no error, or false if there are any error.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public boolean consistencyCheck(Connection conn, String filename, Locale locale) throws ScheduleException, ReadWriteException
	{
		ToolCommonChecker check = new ToolCommonChecker(conn);
		//#CM51338
		//<en>True if there is no error.</en>
		boolean errorFlag = true;
		try
		{
			String checkfile = ToolParam.getParam("CONSTRAINT_CHECK_FILE");
			LogHandler loghandle = new LogHandler(filename + "/" + checkfile, locale);

			//#CM51339
			//<en> Retrieve the default path of Route.txt from ToolParam.</en>
			String defaultRouteText = ToolParam.getParam("DEFAULT_ROUTETEXT_PATH");
			File routepath = new File(defaultRouteText);

			IniFileOperation ifo = null;
			try
			{
				ifo = new IniFileOperation( filename + "/" + routepath.getName(), wSeparate );
			}
			catch(ReadWriteException e)
			{
				//#CM51340
				//<en> 6123146 = Cannot set; the file cannot be found.</en>
				loghandle.write("Route", "RouteText", "6123146");
				return false;
			}
			String[] fromStations = ifo.getKeys();
			String[] toStations = ifo.getValues();
			
			if(fromStations == null || fromStations.length <= 0)
			{
				//#CM51341
				//<en> The carry route has not been set.</en>
				loghandle.write("Route", "RouteText", "6123190");
				return false;
			}

			//#CM51342
			//<en> CHeck to see if the stations defined in route.txt exist in STATION table and AISLE table.</en>
			for(int i = 0; i < fromStations.length; i++)
			{
//#CM51343
// 2004.12.20 T.Yamashita UPD Start
//#CM51344
//				if (!check.isExistAllStationNo(fromStations[i])) 
				if (!check.isExistAllRoutStationNo(fromStations[i])) 
//#CM51345
// 2004.12.20 T.Yamashita UPD End
				{
					loghandle.write("Route", "RouteText", check.getMessage());
					errorFlag = false;
				}
				if(!check.isExistStationType(fromStations[i]))
				{
					loghandle.write("Route", "RouteText", check.getMessage());
					errorFlag = false;
				}

				//#CM51346
				//<en> Retrieve the connect station no.</en>
				StringTokenizer deftoken = new StringTokenizer(toStations[i], ",", false) ;
				int count = deftoken.countTokens();

				//#CM51347
				//<en> Check to see if the connect stations exist in STATION table and AISLE table.</en>
				for (int loopcnt = 0; loopcnt < count ; loopcnt++)
				{
					String toStation = deftoken.nextToken();
//#CM51348
// 2004.12.20 T.Yamashita UPD Start
//#CM51349
//					if (!check.isExistAllStationNo(toStation)) 
					if (!check.isExistAllRoutStationNo(toStation)) 
//#CM51350
// 2004.12.20 T.Yamashita UPD End
					{
						loghandle.write("Route", "RouteText", check.getMessage());
						errorFlag = false;
					}
					if(!check.isExistStationType(toStation))
					{
						loghandle.write("Route", "RouteText", check.getMessage());
						errorFlag = false;
					}
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

	//#CM51351
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
		RouteParameter mParam = (RouteParameter)param;

		//#CM51352
		//<en>Check the duplicate data.</en>
		if(isSameData(mParam, mArray))
		{
			return false;
		}
		return true;
	}

	//#CM51353
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
		//#CM51354
		//<en>Retrieve the process type.</en>
		int processingKind = getProcessingKind();
		//#CM51355
		//<en>Registration</en>
		if(processingKind == M_CREATE)
		{
			if(!create())
			{
				return false;
			}
			//#CM51356
			//<en>6121004 = Edited the data.</en>
			setMessage("6121004");
			return true;
		}
		//#CM51357
		//<en>Error with the process type.</en>
		else
		{
			//#CM51358
			//<en> Unexpected value has been set.{0} = {1}</en>
			String msg = "6126010" + wDelim + "processingKind" + wDelim + Integer.toString(processingKind);
			RmiMsgLogClient.write( msg, (String)this.getClass().getName());
			//#CM51359
			//<en> 6126499 = Fatal error occurred. Please refer to the log.</en>
			throw new ScheduleException("6126499");	
		}
	}

	//#CM51360
	// Package methods -----------------------------------------------

	//#CM51361
	// Protected methods ---------------------------------------------
	//#CM51362
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
	//#CM51363
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

	//#CM51364
	/**<en>
	 * Process the maintenance registrations.
	 * It returns true if the maintenance process succeeded, or false if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :returns true if the process succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	protected boolean create() throws ReadWriteException, ScheduleException
	{
		//#CM51365
		//<en> Whether/not the newly created file was Opened - true:open</en>
		boolean openflag = false ;
		PrintWriter writer = null;
		//#CM51366
		//<en> Original route.txt</en>
		File orgFile = null;
		File newFile = null;

		try
		{
			Parameter[] mArray = (Parameter[])getAllParameters();
			RouteParameter castparam = null;
			//#CM51367
			//<en> Original route.txt</en>
			orgFile = new File(wFileName);

			if( mArray.length > 0)
			{
				//#CM51368
				//<en> Newly create the Route.txt by a differenet name.</en>
				String newfileName = wFileName + "new";
				writer = new PrintWriter(new FileWriter(newfileName, true));
				//#CM51369
				//<en> The file to newly create</en>
				newFile = new File(newfileName);
				openflag = true;

				for(int i = 0; i < mArray.length; i++)
				{
					castparam = (RouteParameter)mArray[i];
					writer.println(castparam.getStationNumber() + wSeparate + castparam.getConnectStNumber());
				}
				writer.close();

				//#CM51370
				//<en> Delete the original Route.txt.</en>
				orgFile.delete();
				//#CM51371
				//<en> Rename the newly created file.</en>
				newFile.renameTo(orgFile);
			}
			//#CM51372
			//<en>If there is no data to process,</en>
			else
			{
				orgFile.delete();
				orgFile.createNewFile();
			}

			return true;
		}
		catch(Exception e)
		{
			if (openflag)
			{
				writer.close();
				newFile.delete();
			}
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM51373
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

	//#CM51374
	// Private methods -----------------------------------------------
	//#CM51375
	/**<en>
	 * Check whether/not the data has already been modified by other terminals.
	 * Compares the ReStoring instance which has been set to maintenance parameter with the 
	 * ReStoring instance which has been retrieved from current DB.
	 * If the comparison resulted the both instances are equal, it regards that no modification was 
	 * made by other terminals. If the comparison resulted these instances to be different, then it 
	 * regards the other terminal already modified the data.
	 * @param ReStoringMaintenanceParameter
	 * @return :returns true if the data ghas already been modified by other terminals, or 
	 * returns false if it has not been modified.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private boolean isAlreadyChanged(RouteParameter param) throws ReadWriteException
	{
		//#CM51376
		//2003/02/11 13:47:25
		//#CM51377
		//<en>In this version, the check for data updates made by terminals is not included in process. Kawasima</en>
		return false;
	}
	
	
	//#CM51378
	/**<en>
	 * Implement the check in order to see that the identical data has been selected when chosing 
	 * data from the list box to edit.
	 * In the warehouse setting, it checks whether/not the storage type of appending parameter
	 * exists in the entered data summary.
	 * @param param  :the parameter which will be appended in this process
	 * @param array  :entered data summary (pool)
	 * @return       :return true if identical data exists.
	 </en>*/
	private boolean isSameData(RouteParameter param, Parameter[] array)
	{

		//#CM51379
		//<en>Key to compare</en>
		String newKey = "";
		String orgKey = "";

		//#CM51380
		//<en>If there is the entered data summary,</en>
		if(array.length > 0)
		{

			String station = param.getStationNumber();
			String alreadyst = "";
			for(int i=0; i<array.length; i++)
			{
				RouteParameter castparam = (RouteParameter)array[i];
				alreadyst = castparam.getStationNumber();
				if(alreadyst.equals(station))
				{
					//#CM51381
					//<en>6123209 = The data is already entered. Cannot input the identical sending station no.</en>
					//#CM51382
					//<en>Cannot set the sending station no. as a connect station no.</en>
					setMessage("6123209");
					return true;
				}
			}

//#CM51383
/*			
			
			//<en>Compare by the storage type appended in this process.</en>
			newKey = param.getStationNumber() + param.getConnectStNumber();
			
			for (int i = 0 ; i < array.length ; i++)
			{
				RouteParameter castparam = (RouteParameter)array[i];
				//<en>Key for the entered data summary</en>
				orgKey = castparam.getStationNumber() + castparam.getConnectStNumber();
				
				//<en>Check the identical user name</en>
				if(newKey.equals(orgKey))
				{
					//<en>6123209 = The data is already entered. Cannot input the identical</en>
					//<en>sending station no.</en>
					setMessage("6123209");
					return true;
				}
			}
*/
/*			
			
			//<en>Compare by the storage type appended in this process.</en>
			newKey = param.getStationNumber() + param.getConnectStNumber();
			
			for (int i = 0 ; i < array.length ; i++)
			{
				RouteParameter castparam = (RouteParameter)array[i];
				//<en>Key for the entered data summary</en>
				orgKey = castparam.getStationNumber() + castparam.getConnectStNumber();
				
				//<en>Check the identical user name</en>
				if(newKey.equals(orgKey))
				{
					//<en>6123209 = The data is already entered. Cannot input the identical</en>
					//<en>sending station no.</en>
					setMessage("6123209");
					return true;
				}
			}
*/
		}

		return false;
	}

	
	//#CM51384
	/**<en>
	 * Return the warehouse station no. of the specified station no.
	 * @param stno :station no.
	 * @return     :return the warehouse station no.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	private String getWHStationNumber(Connection conn, String stno) throws ReadWriteException
	{
		//#CM51385
		//<en> Check to see if the station table is registered.</en>
		ToolStationHandler sthandle = new ToolStationHandler(conn);
		ToolStationSearchKey stkey = new ToolStationSearchKey();
		ToolAisleHandler ahandle = new ToolAisleHandler(conn);
		ToolAisleSearchKey akey = new ToolAisleSearchKey();
		Station[] stations = null;

		//#CM51386
		//<en> Serach in Station table.</en>
		stkey.setNumber(stno);
		stations = (Station[])sthandle.find(stkey);

		if (stations != null && stations.length > 0)
		{
			return stations[0].getWarehouseStationNumber();
		}

		//#CM51387
		//<en> If the data is not found in Station, search in Aisle table.</en>
		akey.setNumber(stno);
		stations = (Station[])ahandle.find(akey);

		if (stations != null && stations.length > 0)
		{
			return stations[0].getWarehouseStationNumber();
		}

		return null;
	}
	
	//#CM51388
	/**<en>
	 * Examines data to see if the specified string contains the unacceptable letters and symbols in system.
	 * The definition of unacceptable letters and symbols is specified in CommonParam.
	 * In this checking of connect station no. of carry route, the cumma is not included in unacceptable letters and symbols.
	 * @param pattern :specifies the target string.
	 * @return :true if unacceptable letters and symbols are included in the string, or false if not.
	 </en>*/
	private static boolean isPatternMatching(String pattern)
	{
		String ng_chars = "*%\"';+";
		
		if (pattern != null && !pattern.equals(""))
		{
			for (int i = 0; i < ng_chars.length() ; i++)
			{
				if (pattern.indexOf(ng_chars.charAt(i)) > -1)
				{
					return true ;
				}
			}
		}
		return false ;
	}
	
}
//#CM51389
//end of class

