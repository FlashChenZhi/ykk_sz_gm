// $Id: DataOperator.java,v 1.2 2006/10/30 04:07:23 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.toolmenu.dataoperate ;

//#CM52528
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.util.BackupUtils;
import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.converter.Converter;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolTerminalAreaHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolTerminalAreaSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.TerminalArea;
import jp.co.daifuku.wms.asrs.tool.schedule.AisleCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.Creater;
import jp.co.daifuku.wms.asrs.tool.schedule.DummyStationCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.GroupControllerCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.HardZoneCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.MachineCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.RouteCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.StationCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.UnavailableLocationCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.WarehouseCreater;
import jp.co.daifuku.wms.asrs.tool.schedule.WorkPlaceCreater;


//#CM52529
/**<en>
 * This class is a collection of methods, for example to be used when creating the TEMP table at log-in,
 * wrieting the existing data in TEMP table, or used when writing the data in TEMP table in the text file
 * when gfenerating the system definition file.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/12</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/10</TD><TD>hondou</TD><TD>Modified method of check.<BR>
 * In the place which should call consisytencyCheck of UnavailableLocationCreater and FloorAllCreater, 
 * since consisytencyCheck of AisleCreater and DummyStation was called, it corrected.</TD></TR>
 * <TR><TD>2003/12/12</TD><TD>okamura</TD><TD>Modified method of createTextFiles<BR>
 * Modified to create the textFile of UnavailableLocationCreater, and delete the data from SHELF table and STATIONTYPE table.<BR>
 * append the method of deleteShelf, getText and write.</TD></TR>
 * <TR><TD>2003/12/19</TD><TD>okamura</TD><TD>Modified method of deleteShelf.<BR>
 * Modified to do nothing when the restricted locations aren't exist SHELF table.</TD></TR>
 * <TR><TD>2004/04/14</TD><TD>kubo</TD><TD>The classification name of system reservation is acquired from a resource.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:07:23 $
 * @author  $Author: suresh $
 </en>*/
public class DataOperator extends Object
{
	//#CM52530
	// Class fields --------------------------------------------------
	//#CM52531
	/**<en>
	 * Classification name
	 </en>*/

	//#CM52532
	/**<en> The name of the text for saving the restricted locations  </en>*/

	public final String SHELFTEXT_NAME = "UnUnavailableLoc.txt";
	
	//#CM52533
	/**<en>
	 * Delimita
	 </en>*/
	public static final String DELIM = ",";

	//#CM52534
	// Class private fields ------------------------------------------
	//#CM52535
	/**<en> <CODE>Connection</CODE> </en>*/

	private Connection wConn = null ;

	//#CM52536
	/**
	 * Store a detailed message of the problem which occurred when processing is executed. 
	 */
	protected String wMessage = "";

	//#CM52537
	// Class method --------------------------------------------------
	//#CM52538
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 04:07:23 $") ;
	}

	//#CM52539
	// Constructors --------------------------------------------------
	//#CM52540
	/**<en>
	 * Newly create the <code>DataOperator</code> instance.
	 * @param conn <CODE>Connection</CODE>
	 </en>*/
	public DataOperator(Connection conn)
	{
		wConn = conn ;
	}

	//#CM52541
	// Public methods ------------------------------------------------

	//#CM52542
	/**<en>
	 * Initial process for log-in.<BR>
	 * In case of new job number, it newly creates the job number file and creates TEMP table 
	 * in database.<BR>
	 * Then get a copy of default resource file in the ob number folder.<BR>
	 * In case of existing job number, it reads the text data in the job number folder, then register 
	 * in database.<BR>
	 * Also the path of the job number folder will be preserved along with user information by session.
	 * How to retrieve the job number folder<BR>
	 * Retrieve the <code>DaifukuUserInformation</code> instance from the session, then<BR>
	 * execute the getUserProperty("WorkFolder") method. <-as it is preserved as "WorkFolder".
	 * @param  request HttpServletRequest
	 * @param  folder :input data folder
	 * @return succeeded/failed
	 * @throws ReadWriteException :Notifies if error occurred in connection with database.
	 </en>*/
	public boolean login(HttpServletRequest request, String folder) throws ReadWriteException
	{
		//#CM52543
		//<en> Retrieve the path of job number file folder from the resource.</en>
		String currentFolder = ToolParam.getParam("EAWC_ITEM_ROUTE_PATH") + "/" + folder;
		//#CM52544
		//<en> Preserving the work folder in session.</en>
		request.getSession().setAttribute("WorkFolder", currentFolder);
		//#CM52545
		//<en> Default ASRSParam</en>
		String defaultASRSParam = ToolParam.getParam("DEFAULT_ASRSPARAM_PATH");
		//#CM52546
		//<en> Default route.txt</en>
		String defaultRouteText = ToolParam.getParam("DEFAULT_ROUTETEXT_PATH");
		//#CM52547
		//<en> Retrieve the file list.</en>
		File file = new File(currentFolder);
		//#CM52548
		//<en> Whether/not the specified path exists.</en>
		if (!file.exists())
		{
			try
			{
				if (file.mkdirs())
				{
					System.out.println("The folder was created.");
				}
				else
				{
					System.out.println("Creation of folder has failed.");
					//#CM52549
					//<en>Failed to create a folder.</en>
					setMessage("6127003");
					return false;
				}
				//#CM52550
				//<en> Copy the resource file to the job number file. AWCParam, Route</en>
				ToolFindUtil.copyFile(defaultRouteText, currentFolder + "/"+ "route.txt");

				//#CM52551
				// Set up data to WMSTOOL user's table about the table 
				//#CM52552
				// related to USER/MENU after it makes it to the text 
				//#CM52553
				// file by using WMS user's data as it is. 
				//#CM52554
				// The object is DMAREA and TERMINAL table. 
				UserMenuTableToTextFromWMS(currentFolder);

				//#CM52555
				//<en> Path of default table creation command.</en>
				String command = ToolParam.getParam("DEFAULT_TABLE_CREATOR_PATH");
				Runtime runtime = Runtime.getRuntime();
				Process	pr = runtime.exec(command);

			    InputStream is = pr.getInputStream();
			    BufferedReader br = new BufferedReader(new InputStreamReader(is));
			    String line;
			    while ((line = br.readLine()) != null) 
			    {
					//#CM52556
					//<en> Output of the screen.</en>
					System.out.println(line);
			    }

				//#CM52557
				// DMAREA and the TERMINAL table made a file and do INSERT to WMSTOOL. 
				UserMenuTextToTable(currentFolder);
				
				//#CM52558
				// After INSERT, the file does Deletion because it is unnecessary. 
				deleteFile(currentFolder);
				
				//#CM52559
				//<en> Fix the transaction.</en>
				wConn.commit();
				
			    return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				try
				{
					wConn.rollback();
				}
				catch (SQLException se)
				{
					//#CM52560
					//<en> Database error occurred.{0}</en>
					RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName()) ;
				}
				//#CM52561
				//<en> Failed to create the table.</en>
				setMessage("6127002");
				return false;
			}
		}
		//#CM52562
		//<en> If the job number folder already exists,</en>
		else
		{
			try
			{
				//#CM52563
				//<en> Path of the default table creation command</en>
				String command = ToolParam.getParam("DEFAULT_TABLE_CREATOR_PATH");;
				Runtime runtime = Runtime.getRuntime();
				Process	pr = runtime.exec(command);
			    InputStream is = pr.getInputStream();
			    BufferedReader br = new BufferedReader(new InputStreamReader(is));
			    String line;
			    while ((line = br.readLine()) != null) {
					//#CM52564
					//<en> Output of the screen.</en>
					System.out.println(line);
			    }
			    
				//#CM52565
				//<en> Retrieve the table list from the resource.</en>
				String tablenames[] = ToolParam.getParamArray("TABLE_NAMES");
				//#CM52566
				//<en> Path of job number folder</en>
				String crrentfolder = (String)request.getSession().getAttribute("WorkFolder");

				//#CM52567
				// Set up data to WMSTOOL user's table about the table 
				//#CM52568
				// related to USER/MENU after it makes it to the text 
				//#CM52569
				// file by using WMS user's data as it is. 
				UserMenuTableToTextFromWMS(currentFolder);
					
				for (int i = 0 ; i < tablenames.length ; i++)
				{
					//#CM52570
					//<en> Clear the table before insertion.</en>
					//<en> It reads the text file in job number file, then write in the table.</en>
					//<en> The table name which will be written will be derermined by getTableName().</en>
					//<en> Currently it adds "TEMP_" to the head.</en>
					//#CM52571
					//<en>Write it in the table reading the text file which exists in the turn made of CMENJP8905$CM folder. </en>
					//#CM52572
					//<en> "TEMP _" attaches to the head as for the written table name now at the time of be decided by getTableName(). </en>
				Converter.textToTable(wConn, getTableName(tablenames[i]), crrentfolder + "/" + tablenames[i] + ".txt");
				}
				
				//#CM52573
				// DMAREA and the TERMINAL table made a file and do INSERT to WMSTOOL. 
				UserMenuTextToTable(currentFolder);
				
				//#CM52574
				// After INSERT, the file does Deletion because it is unnecessary. 
				deleteFile(currentFolder);
				
				//#CM52575
				//<en> Fix the transaction.</en>
				wConn.commit();
				return true;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				try
				{
					wConn.rollback();
				}
				catch (SQLException e)
				{
					//#CM52576
					//<en> Database error occurred.{0}</en>
					RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName()) ;
				}
				//#CM52577
				//<en> Failed to write data in database.</en>
				setMessage("6127001");
				
				deleteFile(currentFolder);
				
//#CM52578
//<en> The screen will move to the next even with the incomplete file.</en>
System.out.println("The screen will move to the next even with the incomplete file.");
				return true;
			}
		}
	}
	//#CM52579
	/**<en>
	 * Process the creation of system data.
	 * It writes data from TEMP table into the text data.
	 * The data will be checked for consistency before writing into text file.
	 * If any error is  found, the log will be recorded.
	 * @param  request HttpServletRequest
	 * @return succeeded/failed
	 * @throws ReadWriteException :Notifies if error occurred in connection with database.
	 </en>*/
	public boolean createTextFiles(HttpServletRequest request) throws ReadWriteException
	{
		return createTextFiles(request, true);
	}
	
	//#CM52580
	/**<en>
	 * Process the creation of system data.
	 * It writes data from TEMP table into the text data.
	 * The data will be checked for consistency before writing into text file.
	 * If any error is  found, the log will be recorded.
	 * @param  request HttpServletRequest
	 * @param  check -consistency check ito be done at data creation:true, other case:false
	 * @return succeeded/failed
	 * @throws ReadWriteException :Notifies if error occurred in connection with database.
	 </en>*/
	public boolean createTextFiles(HttpServletRequest request, boolean check) throws ReadWriteException
	{
		try
		{
			//#CM52581
			//<en> Path of job number folder</en>
			String crrentfolder = (String)request.getSession().getAttribute("WorkFolder");
			//#CM52582
			/*****************************************************************************************
			//<en> Data will be checked here whether/not the data which have been registered in TEMP table</en>
			//<en> sustains consistency.</en>
			//<en> If error is found as a result of check, log will be recorded.</en>
			//<en> location setting[group controller]</en>
			//<en> location setting[warehouse]</en>
			//<en> location setting[aisle]</en>
			//<en> location setting[unavailable location]</en>
			//<en> hard zone setting[range]</en>
			//<en> station setting[station]</en>
			//<en> station setting[dummy station]</en>
			//<en> station setting[workshop]</en>
			//<en> station setting[carry route]</en>
			//<en> machine information setting</en>
		********************************************************************************************/
			/*****************************************************************************************
			//<en> Data will be checked here whether/not the data which have been registered in TEMP table</en>
			//<en> sustains consistency.</en>
			//<en> If error is found as a result of check, log will be recorded.</en>
			//<en> location setting[group controller]</en>
			//<en> location setting[warehouse]</en>
			//<en> location setting[aisle]</en>
			//<en> location setting[unavailable location]</en>
			//<en> hard zone setting[range]</en>
			//<en> station setting[station]</en>
			//<en> station setting[dummy station]</en>
			//<en> station setting[workshop]</en>
			//<en> station setting[carry route]</en>
			//<en> machine information setting</en>
		********************************************************************************************/
			if (check)
			{
	System.out.println("Check Start");
				if (!check(crrentfolder, request.getLocale()))
				{
					//#CM52583
					//<en> Error occurred during the data consistency check. Please see log.</en>
					setMessage("6127004");
					return false;
				}
			}

			//#CM52584
			//<en> Create the textFile of UnavailableLocationCreater, and delete the data from SHELF table and STATIONTYPE table.</en>
			deleteShelf(crrentfolder);

			updateLoadSizeCheck();
			
			//#CM52585
			// Set terminal area information from terminal information and Station information again. 
			createTerminalAreaTable();
			
			
			//#CM52586
			//<en> Retrieve the list of tables from the resource.</en>
			String tablenames[] = ToolParam.getParamArray("TABLE_NAMES");
			for (int i = 0 ; i < tablenames.length ; i++)
			{
				//#CM52587
				//<en> Record the contents of database in text.</en>
				Converter.tableToText(wConn, getTableName(tablenames[i]), crrentfolder+"/"+tablenames[i]+".txt");
			}
			wConn.commit();

System.out.println("Writing of the text has completed.");
			setMessage("6121004");
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//#CM52588
			//<en> Fatal error</en>
			setMessage("6127006");
			return false;
		}
	}
	
	//#CM52589
	/**<en>
	 * Create the name of TEMP table based on the specified table name.
	 * @param  tbl :actual table name
	 * @return :name of TEMP table
	 </en>*/
	public String getTableName(String  tbl) 
	{
		return ("TEMP_"+tbl);
	}

	//#CM52590
	/**<en>
	 * Return the message set in message storage area.
	 </en>*/
	public String getMessage()
	{
		return wMessage;
	}

	//#CM52591
	/**<en>
	 * Sets the specified message into the message storage area.
	 * @param msg :message
	 </en>*/
	protected void setMessage(String msg)
	{
		wMessage = msg;
	}

	//#CM52592
	/**<en>
	 * Truncate the database table.
	 * @param  tbl :name of the table to be truncated
	 * @throws ReadWriteException :Notifies if error occurred in connection with database.
	 </en>*/
	protected void deleteTable(String  tbl) throws ReadWriteException
	{
		Statement stmt        = null;
		try
		{
			stmt = wConn.createStatement();
			String sql = "delete from "+tbl;
			stmt.executeUpdate(sql);
		}
		catch(SQLException e)
		{
			//#CM52593
			//<en>6126001 = Database error occurred.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "createlogininfo" ) ;
			//#CM52594
			//<en>ReadWriteException should be thrown here with error message.</en>
			//#CM52595
			//<en>6126020 = Could not delete the data.</en>
			throw (new ReadWriteException("6126020"));
		}
		finally
		{
			try
			{
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException e)
			{
				//#CM52596
				//<en>6126001 = Database error occurred.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "createlogininfo" ) ;
				//#CM52597
				//<en>ReadWriteException should be thrown here with error message.</en>
				//#CM52598
				//<en>6126020 = Could not delete the data.</en>
				throw (new ReadWriteException("6126020"));
			}
		}
	}
	//#CM52599
	// Package methods -----------------------------------------------

	//#CM52600
	// Protected methods ---------------------------------------------
	//#CM52601
	/**<en>
	 * Check the data consistency.<BR>
	 * If any error is found with consistency, log for the error message will be recorded.
	 * @param  conn :database connection
	 * @param  folder :file path to record the log
	 * @param  locale Locale
	 * @return succeeded/failed
	 * @throws ReadWriteException :Notifies if error occurred in connection with database.
	 </en>*/
	protected boolean check(String folder, Locale locale) throws ReadWriteException
	{
		try
		{

System.out.println("GroupControllerCreater");
			GroupControllerCreater gcreater = new GroupControllerCreater(wConn,Creater.M_CREATE);
			if (!gcreater.consistencyCheck(wConn, folder,locale))
				return false;
System.out.println("WarehouseCreater");
			WarehouseCreater wcreater = new WarehouseCreater(wConn,Creater.M_CREATE);
			if (!wcreater.consistencyCheck(wConn, folder,locale))
				return false;
System.out.println("AisleCreater");
			AisleCreater acreater = new AisleCreater(wConn,Creater.M_CREATE);
			if (!acreater.consistencyCheck(wConn, folder,locale))
				return false;
System.out.println("UnavailableLocationCreater");
			UnavailableLocationCreater ulcreater = new UnavailableLocationCreater(wConn,Creater.M_CREATE);
			if (!ulcreater.consistencyCheck(wConn, folder,locale))
				return false;
System.out.println("HardZoneCreater");
			HardZoneCreater hzcreater = new HardZoneCreater(wConn,Creater.M_CREATE);
			if (!hzcreater.consistencyCheck(wConn, folder,locale))
				return false;
System.out.println("StationCreater");
			StationCreater stcreater = new StationCreater(wConn,Creater.M_CREATE);
			if (!stcreater.consistencyCheck(wConn, folder,locale))
				return false;
System.out.println("DummyStationCreater");
			DummyStationCreater dcreater = new DummyStationCreater(wConn,Creater.M_CREATE);
			if (!dcreater.consistencyCheck(wConn, folder,locale))
				return false;
System.out.println("WorkPlaceCreater");
			WorkPlaceCreater wpcreater = new WorkPlaceCreater(wConn,Creater.M_CREATE);
			if (!wpcreater.consistencyCheck(wConn, folder,locale))
				return false;
System.out.println("RouteCreater");
			RouteCreater rcreater = new RouteCreater(wConn,Creater.M_CREATE);
			if (!rcreater.consistencyCheck(wConn, folder,locale))
				return false;

System.out.println("MachineCreater");
			MachineCreater mcreater = new MachineCreater(wConn,Creater.M_CREATE);
			if (!mcreater.consistencyCheck(wConn, folder,locale))
				return false;
		}
		catch(ScheduleException e)
		{
			return false;
		}

		return true;
	}
	
	//#CM52602
	/**<en>
	 * Register the data which has been reserved to the terminal area table with the system. 
	 * The terminal area tables are Deletion all as for information being registered now. 
	 * Retrieve terminal information newly, and set the area and Station in all terminal information again. 
	 * @throws ReadWriteException It is notified when the data base error occurs. 
	 </en>*/
	protected void createTerminalAreaTable() throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM52603
			// Acquisition of Workshop of all areas and Station list
			ToolStationHandler sthandl = new ToolStationHandler(wConn);
			Station[] starray = sthandl.getStationInArea();

			//#CM52604
			// Acquisition of all terminal lists
			String[] tnoarray = sthandl.getTerminalNumbers();
			
			
			//#CM52605
			// Register terminal area information again by using the terminal area information handler. 
			ToolTerminalAreaHandler tahandl = new ToolTerminalAreaHandler(wConn);
			for (int i = 0 ; i < tnoarray.length ; i++)
			{
System.out.println("tnoarray.length:" + tnoarray.length);
				//#CM52606
				// Terminal information is registered again in Deletion existing terminal area information. 
				ToolTerminalAreaSearchKey tskey = new ToolTerminalAreaSearchKey();
				tskey.setTerminalNumber(tnoarray[i].trim());	
				tahandl.drop(tskey);
			
				for (int j = 0 ; j < starray.length ; j++)
				{					
System.out.println("starray.length:" + starray.length);
					TerminalArea tentity = new TerminalArea();
					tentity.setTerminalNumber(tnoarray[i].trim());
					//#CM52607
					// Conversion for Area ID of terminal area information is a necessary for a numeric type. 
					//#CM52608
					// Since Warehouse Station No. of automatic warehouse becomes Area ID, certainly it becomes a number.
					tentity.setAreaId(Integer.parseInt(starray[j].getWarehouseStationNumber()));
					tentity.setStationNumber(starray[j].getNumber());
					tahandl.create(tentity);
				}
			}			
		}
		catch(DataExistsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
		
   //#CM52609
   /**
	* User/Menu setting data text making processing
	* Data is pulled out from User/Menu setting tool table, and Entry in the text data. 
	 * @param filepath File path
	* @throws SQLException,IOException
	*/
	protected void UserMenuTableToTextFromWMS(String filepath) throws SQLException, IOException
	{
		//#CM52610
		// Acquire not WMSTOOL user's data but WMS user's user definition information. 
		Connection conn = jp.co.daifuku.wms.base.common.WmsParam.getConnection();
		String[] tables = {"TERMINAL", "DMAREA"};
		for(int i=0; i<tables.length; i++)
		{
			 BackupUtils.mkFile(filepath+"/", tables[i], conn);
		}
		conn.close();
	}

	//#CM52611
	/**
	 * User/Menu setting data text restoration processing
	 * Restore data from the text of the specified folder to User/Menu setting tool table. 
	  * @param filepath File path
	 * @throws FileNotFoundException,SQLException,IOException
	 */
	protected void UserMenuTextToTable(String filepath) throws FileNotFoundException, SQLException, IOException 
	{
		String[] tables = {"TERMINAL", "DMAREA"};
		for(int i=0; i<tables.length; i++)
		{
			BackupUtils.ldFile(filepath + "/", tables[i], wConn);
		}
	}

	//#CM52612
	// Private methods -----------------------------------------------

	//#CM52613
	/**<en>
	 * Create the textFile of UnavailableLocationCreater, and delete the data from SHELF table and STATIONTYPE table.<BR>
	 * @param path file path
	 * @param throws ReadWriteException :Notifies if error occurred in connection with database.
	 * @param throws ScheduleException :Notifies if unexpected exception occurred during the check process.
	 </en>*/
	private void deleteShelf(String filepath) throws ReadWriteException, ScheduleException, NotFoundException
	{
		ToolShelfHandler shelfHandle = new ToolShelfHandler(wConn);
		ToolShelfSearchKey shelfKey  = new ToolShelfSearchKey();
		shelfKey.setAccessNgFlag(Shelf.ACCESS_NG);
		Shelf[] ngShelf = (Shelf[])shelfHandle.find(shelfKey);

		//#CM52614
		//<en>In case the shelf has been set the restricted locations in SHELF table.</en>
		if( ngShelf.length > 0 )
		{
			String[] shelftext = new String[ngShelf.length];
			for(int i=0; i<ngShelf.length; i++)
			{
				shelftext[i] = getText(ngShelf[i]) ;
				//#CM52615
				//<en> Delete from SHELF table and STATIONTYPE table.</en>
				shelfHandle.drop(ngShelf[i]);
			}
			write( filepath+"/"+ SHELFTEXT_NAME, shelftext );
		}
		else
		{
		}

	}

	//#CM52616
	/**<en>
	 * The instance of Shelf is changed into a character sequence.
	 * @param shelf The instance of Shelf that is changed into a charcter sequence.
	 * @return That has been changed into a charcter sequence.
	 </en>*/
	private String getText(Shelf shelf)
	{
		String stationNo = shelf.getNumber();
		String bank = Integer.toString(shelf.getBank());
		String bay = Integer.toString(shelf.getBay());
		String level = Integer.toString(shelf.getLevel());
		String warehouseStationNo = shelf.getWarehouseStationNumber();
		String status = Integer.toString(shelf.getStatus());
		String presence = Integer.toString(shelf.getPresence());
		String hardZone = Integer.toString(shelf.getHardZone());
		String softZone = Integer.toString(shelf.getSoftZone());
		String parentStationNo = shelf.getParentStationNumber();
		String accessNgFlag = "";
		//#CM52617
		//<en> accsess Status</en>
		if (shelf.isAccessNgFlag())
		{
			accessNgFlag = Integer.toString(Shelf.ACCESS_NG) ;
		}
		else
		{
			accessNgFlag = Integer.toString(Shelf.ACCESS_OK) ;
		}
		String text = stationNo + DELIM
					+ bank + DELIM
					+ bay + DELIM
					+ level + DELIM
					+ warehouseStationNo + DELIM
					+ status + DELIM
					+ presence + DELIM
					+ hardZone + DELIM
					+ softZone + DELIM
					+ parentStationNo + DELIM
					+ accessNgFlag ;
		return text;
	}

	//#CM52618
	/**<en>
	 * It writes in a text file.
	 * @param filename It's a text file that is write.
	 * @param shelf The charcter sequence that is writes in a text file.
	 </en>*/
	private void write(String filename,  String[] array) throws ScheduleException
	{
		try
		{
			FileWriter dos = new FileWriter(filename);
			
			for(int i = 0; i < array.length; i++)
			{
				dos.write(array[i]);
				dos.write("\n");
			}
			dos.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM52619
	/**
	 * Update the Mode of packing check of STATION which belongs to the aisle at the double deep operation the Mode of packing check and to exist. <BR>
	 * @throws ReadWriteException Notify when abnormality occurs by the connection with the data base. 
	 */
	private void updateLoadSizeCheck() throws ReadWriteException
	{
		ToolStationHandler staionHandle = new ToolStationHandler(wConn);
		staionHandle.modifyLoadSizeCheck();
	}
	
	//#CM52620
	/**<en>
	 * The file that became unnecessary is deleted. 
	 * @param filepath filepath.
	 </en>*/
	private void deleteFile(String filepath)
	{
		String[] tablename = {"TERMINAL", "DMAREA"};
		File delfile;
		
		for (int i = 0; i < tablename.length; i++ ){
			delfile = new File(filepath + "/" + tablename[i] + ".sql");
			delfile.delete();
		}
	}
}
//#CM52621
//end of class

