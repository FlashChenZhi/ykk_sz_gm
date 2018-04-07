// $Id: WmsParam.java,v 1.2 2006/11/07 06:03:09 suresh Exp $
package jp.co.daifuku.wms.base.common ;

//#CM643469
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import jp.co.daifuku.common.CommonParam;

//#CM643470
/**
 * The class to acquire the parameter of WareNaviSystem from Resource. 
 * The default of the Resource name is <code>WMSParam</code>
 * Moreover, the acquisition of <code>Connection</code> for the Database connection is possible. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>Kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:03:09 $
 * @author  $Author: suresh $
 */
public class WmsParam
{
	//#CM643471
	// Class fields --------------------------------------------------
	//#CM643472
	/**
	 * Database Error Code (for Oracle) Error when data already exists. 
	 */
	public static final int DATAEXISTS = 1 ;
	//#CM643473
	/**
	 * ResultFlag : INSERT,UPDATE,DELETE SUCCESS
	 */
	public final static int RESULT_SUCCESS = 1;
	//#CM643474
	/**
	 * Result Flag : INSERT,UPDATE,DELETE FAILED
	 */
	public final static int RESULT_FAILED  = 0;
	//#CM643475
	/**
	 *  Flag : Retrieval usually
	 */
	public final static int SEARCH_NORMAL = 1;
	//#CM643476
	/**
	 *  Flag : Temporary retrieval
	 */
	public final static int SEARCH_TEMPORARILY = 0;
	//#CM643477
	/**
	 * Default resource
	 */
	public static final String WMS_DEFAULT_RESOURCE = "WMSParam" ;
	//#CM643478
	/**
	 * Range of drawing release at Shortage:Details
	 */
	public static final int SHORTAGE_RECOVERY_RANGE_LINE = 1;
	//#CM643479
	/**
	 * Range of drawing release at Shortage:One setting All
	 */
	public static final int SHORTAGE_RECOVERY_RANGE_ALL = 2;

	//*** [COMMON INFORMATION] ***//	
	//#CM643480
	/**
	 * Path of DAIDATA (RFT use C:\\daifuku\\wms\\terminal\\)
	 */
	public static final String DAIDATA = getParam("DAIDATA");
	
	//#CM643481
	/**
	 * Path of environmental information file
	 */
	public static final String ENVIRONMENT = getParam("ENVIRONMENT");
	
	//#CM643482
	/**
	 * Path of RFT SEND data
	 */
	public static final String RFTSEND = getParam("RFTSEND");
	
	//#CM643483
	/**
	 * Path of RFT RECV data
	 */
	public static final String RFTRECV = getParam("RFTRECV");

	//*** [DATABASE CONNECTION INFORMATION] ***//	
	//#CM643484
	/**
	 * When Read and Write are done, User definition information's (text file) delimiter. 
	 */
	public static final String USERINFO_FIELD_SEPARATOR = getParam("USERINFO_FIELD_SEPARATOR");
	
	//#CM643485
	/**
	 * The timer addition and when SELECT FOR UPDATE is done, the time-out time of Database. (Seconds)
	 */
	public static final int WMS_DB_LOCK_TIMEOUT = getIntParam("WMS_DB_LOCK_TIMEOUT");
	

	//*** [REPORT INFORMATION] ***//	
	//#CM643486
	/**
	 * Report image file maintenance days
	 */
	public static final String PRINTHISTORY_KEEP_DAYS = getParam("PRINTHISTORY_KEEP_DAYS");
	
	//#CM643487
	/**
	 * Data file path for report
	 */
	public static final String DATA_FILE_PATH = getParam("DATA_FILE_PATH");
	
	//#CM643488
	/**
	 * Path of backup file for report
	 */
	public static final String BACKUP_DATA_FILE_PATH = getParam("BACKUP_DATA_FILE_PATH");
	
	//#CM643489
	/**
	 * Automatic print file path for Report
	 */
	public static final String AUTOPRINT_FILE_PATH = getParam("AUTOPRINT_FILE_PATH");
	
	//#CM643490
	/**
	 * Host name of Universal Connect/X server(Or, Internet Protocol address)
	 */
	public static final String REPORT_HOST = getParam("REPORT_HOST");
	
	//#CM643491
	/**
	 * Port number of Universal Connect/X server
	 */
	public static final int REPORT_PORT = getIntParam("REPORT_PORT");
	
	//*** [LOG FILES INFORMATION] ***//	
	//#CM643492
	/**
	 * Directories of Log files of Message log and trace log, etc.
	 */
	public static final String WMS_LOGS_PATH = getParam("WMS_LOGS_PATH");
	
	//#CM643493
	/**
	 * RFT communication trace file name
	 */
	public static final String RFT_TRACE_NAME = getParam("RFT_TRACE_NAME");
	
	//#CM643494
	/**
	 * 2005/09/09 Okamura
	 * Do not erase it so that there is a possibility of mounting though it is unused now in the future. 
	 * 
	 * RFT communication trace pointer file name
	 */
	public static final String RFT_TRACE_POINTER_NAME = getParam("RFT_TRACE_POINTER_NAME");
	
	//#CM643495
	/**
	 * RFT communication trace ON/OFF
	 */
	public static final boolean RFT_TRACE_ON = getBoolParam("RFT_TRACE_ON");
	
	//#CM643496
	/**
	 * Report image file maintenance days
	 */
	public static final String STACKTRACE_COMMENT = getParam("STACKTRACE_COMMENT");
	
	//#CM643497
	/**
	 * Path of Tomcat Log file
	 */
	public static final String TOMCAT_LOGS_PATH = getParam("TOMCAT_LOGS_PATH");
	
	//#CM643498
	/**
	 * TomcatLog file maintenance days
	 */
	public static final String TOMCATLOGS_KEEP_DAYS = getParam("TOMCATLOGS_KEEP_DAYS");
	
	//#CM643499
	/**
	 * LogBackUp file maintenance days
	 */
	public static final String WMS_LOGFILE_KEEP_DAYS = getParam("WMS_LOGFILE_KEEP_DAYS");
	
	//#CM643500
	/**
	 * Path of IIS(FTP) log file
	 */
	public static final String IIS_LOGS_PATH = getParam("IIS_LOGS_PATH");
	
	//#CM643501
	/**
	 * IIS(FTP) log file maintenance days
	 */
	public static final String IIS_LOGFILE_KEEP_DAYS = getParam("IIS_LOGFILE_KEEP_DAYS");
	
	//#CM643502
	/**
	 * History preservation passing of FTP work file
	 */
	public static final String FTP_FILE_HISTORY_PATH = getParam("FTP_FILE_HISTORY_PATH");
	
	//#CM643503
	/**
	 * History maintenance days of FTP work file
	 */
	public static final String FTP_FILE_HISTORY_KEEP_DAYS = getParam("FTP_FILE_HISTORY_KEEP_DAYS");
	
	//#CM643504
	/**
	 * Path of other log files
	 */
	public static final String ETC_LOGS_PATH = getParam("ETC_LOGS_PATH");
	
	//#CM643505
	/**
	 * Other log file maintenance days
	 */
	public static final String ETC_LOGFILE_KEEP_DAYS = getParam("ETC_LOGFILE_KEEP_DAYS");
	
	//#CM643506
	/**
	 * Number of trace size maximum bytes
	 */
	public static final int TRACE_MAX_SIZE = getIntParam("TRACE_MAX_SIZE");
	
	//*** [DISPLAY INFORMATION] ***//	
	//#CM643507
	/**
	 * List cell(Filtering area)The maximum display qty
	 */ 
	public static final int MAX_NUMBER_OF_DISP = getIntParam("MAX_NUMBER_OF_DISP");
	
	//#CM643508
	/**
	 * LISTBOX retrieval maximum number
	 */
	public static final int MAX_NUMBER_OF_DISP_LISTBOX = getIntParam("MAX_NUMBER_OF_DISP_LISTBOX");

	//#CM643509
	/**
	 * List box retrieval result display number
	 */
	public static final int LISTBOX_SEARCH_COUNT = getIntParam("LISTBOX_SEARCH_COUNT");

	//#CM643510
	/**
	 * TRACE_LOG Display upper bound value
	 */
	public static final int MAX_NUMBER_OF_DISP_TRACE_LOG = getIntParam("MAX_NUMBER_OF_DISP_TRACE_LOG");

	//#CM643511
	/**
	 * MESSAGE_LOG Display upper bound value
	 */
	public static final int MAX_NUMBER_OF_DISP_MESSAGE_LOG = getIntParam("MAX_NUMBER_OF_DISP_MESSAGE_LOG");

	//*** [SQL INFORMATION] ***//
	//#CM643512
	/**
	 * The maximum qty acquired when SELECT sentence is printed by using HANDLER
	 */
	public static final int MAX_NUMBER_OF_SQL_FIND = getIntParam("MAX_NUMBER_OF_SQL_FIND");

	//*** [HOST INFORMATION] ***//	
	//#CM643513
	/**
	 * Passing of backup file which can be done by executing external data taking
	 */
	public static final String HISTRY_HOSTDATA_PATH = getParam("HISTRY_HOSTDATA_PATH");

	//#CM643514
	/**
	 * External data backup file maintenance days
	 */
	public static final String HOSTDATA_KEEP_DAYS = getParam("HOSTDATA_KEEP_DAYS");
	
	//#CM643515
	/**
	 * Class name which does external data loading(Receiving)
	 */
	public static final String HOSTDATA_LOADER_INSTOCK = getParam("HOSTDATA_LOADER_INSTOCK");
	
	//#CM643516
	/**
	 * Class name which does external data loading(Storage)
	 */
	public static final String HOSTDATA_LOADER_STORAGE = getParam("HOSTDATA_LOADER_STORAGE");
	
	//#CM643517
	/**
	 * Class name which does external data loading(Picking)
	 */
	public static final String HOSTDATA_LOADER_RETRIEVAL = getParam("HOSTDATA_LOADER_RETRIEVAL");
	
	//#CM643518
	/**
	 * Class name which does external data loading(Sorting)
	 */
	public static final String HOSTDATA_LOADER_PICKING = getParam("HOSTDATA_LOADER_PICKING");
	
	//#CM643519
	/**
	 * Class name which does external data loading(Shipping)
	 */
	public static final String HOSTDATA_LOADER_SHIPPING = getParam("HOSTDATA_LOADER_SHIPPING");
	
	//#CM643520
	/**
	 * Class name which does external data loading(Consignor Master)
	 */
	public static final String HOSTDATA_LOADER_CONSIGNOER = getParam("HOSTDATA_LOADER_CONSIGNOER");
	
	//#CM643521
	/**
	 * Class name which does external data loading(Supplier Master)
	 */
	public static final String HOSTDATA_LOADER_SUPPLIER = getParam("HOSTDATA_LOADER_SUPPLIER");
	
	//#CM643522
	/**
	 * Class name which does external data loading(Customer Master)
	 */
	public static final String HOSTDATA_LOADER_CUSTOMER = getParam("HOSTDATA_LOADER_CUSTOMER");
	
	//#CM643523
	/**
	 * Class name which does external data loading(Item Master)
	 */
	public static final String HOSTDATA_LOADER_ITEM = getParam("HOSTDATA_LOADER_ITEM");

	//*** [SCHEDULE INFORMATION] ***//	
	//#CM643524
	/**
	 * After the socket is received and the thread is generated, the SLEEP time until the following socket reception processing waiting. Unit:ms
	 */
	public static final int RFT_SLEEP_SEC = getIntParam("RFT_SLEEP_SEC");

	//#CM643525
	/**
	 * Port No. of RFT Server.
	 */
	public static final int RFT_SERVER_PORT = getIntParam("RFT_SERVER_PORT");

	//#CM643526
	/**
	 * Living confirmation(Does : True Doesnt : False)(The interval is disregarded when not doing. )
	 */
	public static final boolean RFT_KEEP_ALIVE_POLLING_ENABLE = getBoolParam("RFT_KEEP_ALIVE_POLLING_ENABLE");

	//#CM643527
	/**
	 *  Living confirmation interval
	 */
	public static final int RFT_KEEP_ALIVE_POLLING_TIME = getIntParam("RFT_KEEP_ALIVE_POLLING_TIME");

	//#CM643528
	/**
	 * The maximum number of lines of FTP file(When Hit is done voluminously, the file making is done by this number of cases. )
	 */
	public static final int RFT_FTP_FILE_MAX_RECORD = getIntParam("RFT_FTP_FILE_MAX_RECORD");

	//#CM643529
	/**
	 * Do not consolidate work with the ReceivingRFT server. (Does : True Doesnt : False)
	 */
	public static final boolean INSTOCK_JOBCOLLECT = getBoolParam("INSTOCK_JOBCOLLECT");
	//#CM643530
	/**
	 * Key when work is consolidated with ReceivingRFT server [ 1:Item code, 2:Item code+ITF+Bundle ITF ]
	 */
	public static final int INSTOCK_JOBCOLLECT_KEY = getIntParam("INSTOCK_JOBCOLLECT_KEY");

	//#CM643531
	/**
	 * Do not consolidate work with the ShippingRFT server. (Does : True Doesnt : False)
	 */
	public static final boolean SHIPPING_JOBCOLLECT = getBoolParam("SHIPPING_JOBCOLLECT");
	//#CM643532
	/**
	 * Key when work is consolidated with ShippingRFT server [ 1:Item code, 2:Item code+ITF+Bundle ITF ]
	 */
	public static final int SHIPPING_JOBCOLLECT_KEY = getIntParam("SHIPPING_JOBCOLLECT_KEY");

	//#CM643533
	/**
	 * Do not consolidate work with the StorageRFT server. (Does : True Doesnt : False)
	 */
	public static final boolean STORAGE_JOBCOLLECT = getBoolParam("STORAGE_JOBCOLLECT");
	//#CM643534
	/**
	 * Key when work is consolidated with StorageRFT server [ 1:Item code, 2:Item code+ITF+Bundle ITF ]
	 */
	public static final int STORAGE_JOBCOLLECT_KEY = getIntParam("STORAGE_JOBCOLLECT_KEY");

	//#CM643535
	/**
	 * Do not consolidate work with the PickingRFT server. (Does : True Doesnt : False)
	 */
	public static final boolean RETRIEVAL_JOBCOLLECT = getBoolParam("RETRIEVAL_JOBCOLLECT");
	//#CM643536
	/**
	 * Key when work is consolidated with Picking RFT server [ 1:Item code, 2:Item code+ITF+Bundle ITF ]
	 */
	public static final int RETRIEVAL_JOBCOLLECT_KEY = getIntParam("RETRIEVAL_JOBCOLLECT_KEY");

	//#CM643537
	/**
	 * Do not consolidate work with the SortingRFT server. (Does:true Doesnt:false)
	 */
	public static final boolean SORTING_JOBCOLLECT = getBoolParam("SORTING_JOBCOLLECT");

	//*** [EXTENDED PACKAGE INFORMATION] ***//	
	//#CM643538
	/**
	 * Used for Product No development. Use it if necessary. 
	 */
	public static final String WMS_EXTENDED_PACKAGE = getParam("WMS_EXTENDED_PACKAGE");

	//*** [STOCK INFORMATION] ***//	
	//#CM643539
	/**
	 * The maximum value of total number of rows
	 */
	public static final int MAX_TOTAL_QTY = getIntParam("MAX_TOTAL_QTY");

	//#CM643540
	/**
	 * The maximum value of Work qty of Worker results
	 */
	public static final int WORKER_MAX_TOTAL_QTY = getIntParam("WORKER_MAX_TOTAL_QTY");

	//#CM643541
	/**
	 * Maximum value of Stock qty
	 */
	public static final int MAX_STOCK_QTY = getIntParam("MAX_STOCK_QTY");

	//#CM643542
	/**
	 * Management at expiry date Available / Not available(Available:true Not available:false)
	 */
	public static final boolean IS_USE_BY_DATE_UNIQUEKEY = getBoolParam("USE_BY_DATE_STOCK_UNIQUE");

	//*** [AREA INFORMATION] ***//	
	//#CM643543
	/**
	 * Area No of Movement Rack
	 */
	public static final String IDM_AREA_NO = getParam("IDM_AREA_NO");

	//#CM643544
	/**
	 * Area No of FLOOR
	 */
	public static final String FLOOR_AREA_NO = getParam("FLOOR_AREA_NO");

	//#CM643545
	/**
	 * Class name of order No decision processing inherited class for area specification
	 */
	public static final String AREA_PICKING_ORDER_SCHEDULER = getParam("AREA_PICKING_ORDER_SCHEDULER");

	//*** [AS/RS INFORMATION] ***//	
	//#CM643546
	/**
	 * Empty palette Consignor Code
	 */
	public static final String EMPTYPB_CONSIGNORCODE = getParam("EMPTYPB_CONSIGNORCODE");

	//#CM643547
	/**
	 * Abnormal shelf Consignor Code
	 */
	public static final String IRREGULAR_CONSIGNORCODE = getParam("IRREGULAR_CONSIGNORCODE");

	//#CM643548
	/**
	 * All Station
	 */
	public static final String ALL_STATION = getParam("ALL_STATION");
	
	//#CM643549
	/**
	 * Maximum number of consolidation to one palette
	 */
	public static final int MAX_MIXED_PALETTE = getIntParam("MAX_MIXED_PALETTE");
	
	//#CM643550
	/**
	 * Direction of empty shelf retrieval
	 */
	public static Hashtable DIRECTION = getHashParam("DIRECTION");

	//*** [ALLOCATE INFORMATION] ***//	
	//#CM643551
	/**
	 * Range of drawing release at Shortage
	 */
	public static final int SHORTAGE_RECOVERY_RANGE = getIntParam("SHORTAGE_RECOVERY_RANGE");

	//*** CommonParam ***//	
	//#CM643552
	/**
	 * Log file folder(CommonParam)
	 */
	public static final String LOGS_PATH = CommonParam.getParam("LOGS_PATH");

	//#CM643553
	/**
	 * Message log file name(CommonParam)
	 */
	public static final String MESSAGELOG_FILE = CommonParam.getParam("MESSAGELOG_FILE");

	//#CM643554
	/**
	 * Prohibited character(CommonParam)
	 */
	public static final String NG_PARAMETER_TEXT = CommonParam.getParam("NG_PARAMETER_TEXT");

	//#CM643555
	/**
	 * Wild-card character for retrieval(CommonParam)
	 */
	public static final String PATTERNMATCHING = CommonParam.getParam("PATTERNMATCHING_CHAR");

	//#CM643556
	/**
	 * Length of WarehouseNo which displays it on screen
	 */
	public static final int WAREHOUSE_DISP_LENGTH = Integer.parseInt(CommonParam.getParam("WAREHOUSE_LENGTH"));
	//#CM643557
	/**
	 * Length of bank which displays it on screen
	 */
	public static final int BANK_DISP_LENGTH = Integer.parseInt(CommonParam.getParam("BANK_LENGTH"));
	//#CM643558
	/**
	 * Length of bay displayed on screen
	 */
	public static final int BAY_DISP_LENGTH = Integer.parseInt(CommonParam.getParam("BAY_LENGTH"));
	//#CM643559
	/**
	 * Length at level displayed on screen
	 */
	public static final int LEVEL_DISP_LENGTH = Integer.parseInt(CommonParam.getParam("LEVEL_LENGTH"));
	//#CM643560
	/**
	 * Length of sublocation displayed on screen
	 */
	public static final int AREA_DISP_LENGTH = Integer.parseInt(CommonParam.getParam("AREA_LENGTH"));
	
	//#CM643561
	/**
	 * Length of WarehouseNo maintained with StationNumber of SHELF
	 */
	public static final int WAREHOUSE_DB_LENGTH = Integer.parseInt(CommonParam.getParam("WAREHOUSE_DB_LENGTH"));
	//#CM643562
	/**
	 * Length of bank which maintains it with StationNumber of SHELF
	 */
	public static final int BANK_DB_LENGTH = Integer.parseInt(CommonParam.getParam("BANK_DB_LENGTH"));
	//#CM643563
	/**
	 * Length of bay maintained with StationNumber of SHELF
	 */
	public static final int BAY_DB_LENGTH = Integer.parseInt(CommonParam.getParam("BAY_DB_LENGTH"));
	//#CM643564
	/**
	 * Length at level maintained with StationNumber of SHELF
	 */
	public static final int LEVEL_DB_LENGTH = Integer.parseInt(CommonParam.getParam("LEVEL_DB_LENGTH"));
	//#CM643565
	/**
	 * Length of sublocation maintained with StationNumber of SHELF
	 */
	public static final int AREA_DB_LENGTH = Integer.parseInt(CommonParam.getParam("AREA_DB_LENGTH"));
	
	//#CM643566
	// Class private fields ------------------------------------------
	//#CM643567
	/**
	 * JDBC initial letter
	 */
	private static final String THIN_DRIVER = "jdbc:oracle:thin:@" ;

	//#CM643568
	// Class method --------------------------------------------------
	//#CM643569
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:03:09 $") ;
	}

	//#CM643570
	// Constructors --------------------------------------------------
	//#CM643571
	// No Constructors! all of method is static.

	//#CM643572
	// Public methods ------------------------------------------------
	//#CM643573
	/**
	 * Oracle Acquire <code>Connection</code> for the DB connection. 
	 * @param server    Database - Host name or Internet Protocol address of server
	 * @param port      Database - Connection destination Port of the Server
	 * @param id        Database - Instance Name
	 * @param user      Database - User Name
	 * @param passwd    User - Password
	 * @return   <code>Connection</code>
	 * @throws SQLException It is notified when the data base access error or other errors occur. 
	 */
	public static Connection getConnection(String server, String port, String id, String user, String passwd) throws SQLException
	{
		//#CM643574
		// Load JDBC driver
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver()) ;

		//#CM643575
		// Make connection string
		String connString = THIN_DRIVER + server + ":" + port + ":" + id ;

		//#CM643576
		// Acquire Connection.
		Connection conn = DriverManager.getConnection( connString , user, passwd);
		//#CM643577
		// Set Auto Commit false.
		conn.setAutoCommit(false) ;

		return (conn) ;
	}
	
	//#CM643578
	/**
	 * Oracle Acquire <code>Connection</code> for the DB connection. 
	 * @param user      Database - User Name
	 * @param passwd    User - Password
	 * @param host      The connection destination Host(Service name of registration of Oracle)
	 * @return   <code>Connection</code>
	 * @throws SQLException It is notified when the data base access error or other errors occur. 
	 */
	public static Connection getConnection(String user, String passwd, String host) throws SQLException
	{
		//#CM643579
		// The parameter is acquired from resource. 
		String port =	getParam("WMS_DB_PORT") ;
		String id =		getParam("WMS_DB_SID") ;

		return getConnection(host, port, id, user, passwd) ;
	}
	
	//#CM643580
	/**
	 * Oracle Acquire <code>Connection</code> for the DB connection. 
	 * @param user      Database - User Name
	 * @param passwd    User - Password - 
	 * @return   <code>Connection</code>
	 * @throws SQLException It is notified when the data base access error or other errors occur. 
	 */
	public static Connection getConnection(String user, String passwd) throws SQLException
	{
		//#CM643581
		// The parameter is acquired from resource. 
		String server =	getParam("WMS_DB_HOST") ;
		String port =	getParam("WMS_DB_PORT") ;
		String id =		getParam("WMS_DB_SID") ;

		return (getConnection(server, port, id, user, passwd)) ;
	}

	//#CM643582
	/**
	 * Acquire <code>Connection</code> for the DB connection. 
	 * Acquire the connection destination, User, and Password from the parameter and Resource. 
	 * <pre>
	 * Parameter key (It is necessary to define it in Resource. )
	 * WMS_DB_HOST : At the connection destination Host
	 * WMS_DB_PORT : At the connection destination Port
	 * WMS_DB_SID  : SID
	 * WMS_DB_USER : Connection User
	 * WMS_DB_PW   : User - Password
	 * </pre>
	 * @return   <code>Connection</code>
	 * @throws SQLException It is notified when the data base access error or other errors occur. 
	 */
	public static Connection getConnection() throws SQLException
	{
		//#CM643583
		// The parameter is acquired from resource. 
		String server =	getParam("WMS_DB_HOST") ;
		String port =	getParam("WMS_DB_PORT") ;
		String id =		getParam("WMS_DB_SID") ;
		String user =	getParam("WMS_DB_USER") ;
		String passwd =	getParam("WMS_DB_PW") ;

		return (getConnection(server, port, id, user, passwd)) ;
	}

	//#CM643584
	// Package methods -----------------------------------------------

	//#CM643585
	// Protected methods ---------------------------------------------

	//#CM643586
	// Private methods -----------------------------------------------
	//#CM643587
	/**
	 * Acquire the content of the parameter from the key in the Character string expression. 
	 * @param key  Key to acquire parameter
	 * @return   Expression of character string of parameter
	 */
	private static String getParam(String key)
	{
		try
		{
			ResourceBundle rb = getBundle(WMS_DEFAULT_RESOURCE, Locale.getDefault()) ;
			if (rb != null)
			{
				return (rb.getString(key));
			}
		}
		catch(Exception e)
		{
		}
		return "";
		
	}

	//#CM643588
	/**
	 * Acquire the content of the parameter from the key in the numeric representation. 
	 * @param key  Key to acquire parameter
	 * @return   Numeric representation of parameter
	 */
	private static int getIntParam(String key)
	{
		try
		{
			return Integer.parseInt(getParam(key)) ;
		}
		catch (Exception e)
		{
		}
		return -1;
	}

	//#CM643589
	/**
	 * Maintain the content of the parameter from the key in HashTable and acquire it. <BR>
	 * Delimit Key and Value by _ (underscore). 
	 * Delimit the set of Key and Value. <BR>
	 * <B>It does not operate normally other than this format. </B><BR>
	 * Ex.:<BR>
	 * Value of WmsParam  9001_1,9002_2<BR>
	 *  key 9001 value 1<BR>
	 *  key 9002 value 2<BR>
	 * @param key  Key to acquire parameter
	 * @return   Parameter maintained in HashTable
	 */
	private static Hashtable getHashParam(String key)
	{
		Hashtable retHash = new Hashtable();
		try
		{
			//#CM643590
			// Acquire the value from WmsParam. 
			//#CM643591
			// 9100_1,9200_2
			StringTokenizer st = new StringTokenizer(getParam(key), ",");
			StringTokenizer st2 = null;
			while(st.hasMoreTokens())
			{
				//#CM643592
				// 9100_1
				String whParam = st.nextToken();
				st2 = new StringTokenizer(whParam, "_");
				while(st2.hasMoreTokens())
				{
					retHash.put(st2.nextToken().trim(), st2.nextToken().trim());
				}
			}
			
		}
		catch (Exception e)
		{
		}
		return retHash;
	}

	//#CM643593
	/**
	 * Acquire the content of the parameter from the key in the truth value expression. 
	 * @param key  Key to acquire parameter
	 * @return   Expression of truth value of parameter
	 */
	private static boolean getBoolParam(String key)
	{
		try
		{
			return Boolean.valueOf(getParam(key)).booleanValue();
		}
		catch (Exception e)
		{
		}
		return false;
		
	}

	
	//#CM643594
	/**
	 * Acquire Resource bundle.
	 * @param res  Resource
	 * @param locale LocaleObject
	 * @return Resource Bundle
	 */
	private static ResourceBundle getBundle(String res, Locale locale)
	{
		return (ResourceBundle.getBundle(res, locale)) ;
	}


}
//#CM643595
//end of class

