// $Id: AsrsParam.java,v 1.2 2006/10/24 08:23:18 suresh Exp $
package jp.co.daifuku.wms.asrs.common ;

//#CM28524
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import jp.co.daifuku.wms.base.common.WmsParam;

//#CM28525
/**
 * This class is to get parameters of AS/RS system out of resource.
 * Default resource name is <code>ASRSParam</code>.
 * It also enables to get <code>Connection</code> for database connections.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/24 08:23:18 $
 * @author  $Author: suresh $
 */
public class AsrsParam extends WmsParam
{
	//#CM28526
	// Class fields --------------------------------------------------
	//#CM28527
	/**
	 * Errors when data of error codes for database (for Oracle) already exists
	 */
	public static final int DATAEXISTS = 1 ;	
	
	//#CM28528
	/**
	 * Result Flag : INSERT,UPDATE,DELETE SUCCESS
	 */
	public final static int RESULT_SUCCESS = 1;
	
	//#CM28529
	/**
	 * Result Flag : INSERT,UPDATE,DELETE FAILED
	 */
	public final static int RESULT_FAILED  = 0;
	
	//#CM28530
	/**
	 * Search Flag : Normal search
	 */
	public final static int SEARCH_NORMAL = 1;
	
	//#CM28531
	/**
	 * Search Flag : Temporary search
	 */
	public final static int SEARCH_TEMPORARILY = 0;
	
	//#CM28532
	/**
	 * Default resource name
	 */
	public static final String DEFAULT_RESOURCE = "ASRSParam" ;

	//*** [AS21 Communication Information] ***//	
	//#CM28533
	/**
	 * Receive trace file name
	 */
	public static final String AS21_RECEIVE_TRACE_NAME = AsrsParam.getParam("AS21_RECEIVE_TRACE_NAME");
	
	//#CM28534
	/**
	 * Receive trace Write on/off
	 */
	public static final boolean AS21_RECEIVE_TRACE_ON = AsrsParam.getBoolParam("AS21_RECEIVE_TRACE_ON");
	
	//#CM28535
	/**
	 * Send trace file name
	 */
	public static final String AS21_SEND_TRACE_NAME = AsrsParam.getParam("AS21_SEND_TRACE_NAME");
	
	//#CM28536
	/**
	 * Send trace Write on/off
	 */
	public static final boolean AS21_SEND_TRACE_ON = AsrsParam.getBoolParam("AS21_SEND_TRACE_ON");
	
	//#CM28537
	/**
	 * Trace file maximum size (Byte)
	 */
	public static final int TRACE_MAX_SIZE = AsrsParam.getIntParam("TRACE_MAX_SIZE");
	
	//#CM28538
	/**
	 * Sleep time (ms) for retry when telegraph transfer returns error.
	 */
	public static final int CONTROL_SLEEP_SEC = AsrsParam.getIntParam("CONTROL_SLEEP_SEC");

	//#CM28539
	/**
	 * Sleep time to retry when there is no data in all the stations for conveyance and picking instruction
	 */
	public static final int INSTRUCT_CONTROL_SLEEP_SEC = AsrsParam.getIntParam("INSTRUCT_CONTROL_SLEEP_SEC");
	
	//#CM28540
	/**
	 * Sleep time to retry when there is data for conveyance, picking instruction but not in sending status
	 */
	public static final int INSTRUCT_CONTROL_SLEEP_SEC_DATA_EXIST = AsrsParam.getIntParam("INSTRUCT_CONTROL_SLEEP_SEC_DATA_EXIST");
	
	//#CM28541
	/**
	 * Sleep time to reconnect when AS21 communication process turns abnormal
	 */
	public static final int AS21_SLEEP_SEC = AsrsParam.getIntParam("AS21_SLEEP_SEC");


	//*** [Layout Information] ***//	
	//#CM28542
	/**
	 * Path of route file(route.txt)
	 */
	public static final String ROUTE_FILE = AsrsParam.getParam("ROUTE_FILE");
	
	//*** [Location Information] ***//	
	//#CM28543
	/**
	 * If "All Station" is selected from the screen
	 */
	public static final String ALL_RETRIEVAL_STATION = AsrsParam.getParam("ALL_RETRIEVAL_STATION");
	
	//#CM28544
	/**
	 * If "ALL SR" is selected from the screen
	 */
	public static final String ALL_AISLE_NO = AsrsParam.getParam("ALL_AISLE_NO");
	
	//#CM28545
	/**
	 * If "All Warehouse" is selected from the screen
	 */
	public static final String ALL_WH_NO = AsrsParam.getParam("ALL_WH_NO");
	
	//#CM28546
	/**
	 * If "All Bank" is selected from the screen
	 */
	public static final String ALL_BANK_NO = AsrsParam.getParam("ALL_BANK_NO");
	
	//#CM28547
	/**
	 * If "All SRC" is selected from the screen
	 */
	public static final String ALL_GROUPCONTROLLER_NO = AsrsParam.getParam("ALL_GROUPCONTROLLER_NO");
	
	//*** [STORAGE INFORMATION] ***//	
	//#CM28548
	/**
	 * Abnormal shelf ItemKey
	 */
	public static final String IRREGULAR_ITEMKEY = AsrsParam.getParam("IRREGULAR_ITEMKEY");
	
	//#CM28549
	/**
	 * Empty palette use ItemKey
	 */
	public static final String EMPTYPB_ITEMKEY = AsrsParam.getParam("EMPTYPB_ITEMKEY");
	
	//#CM28550
	/**
	 * Empty shelf confirmation
	 */
	public static final String FREESHELF_ITEMKEY = AsrsParam.getParam("FREESHELF_ITEMKEY");
	
	//#CM28551
	/**
	 * Dummy CarryKey
	 */
	public static final String DUMMY_MCKEY = AsrsParam.getParam("DUMMY_MCKEY");
	
	//#CM28552
	// Class private fields ------------------------------------------
	//#CM28553
	/**
	 * JDBC prefix
	 */
	private static final String THIN_DRIVER = "jdbc:oracle:thin:@" ;

	//#CM28554
	// Class method --------------------------------------------------
	//#CM28555
	/**
	 * Returns version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/24 08:23:18 $") ;
	}

	//#CM28556
	// Constructors --------------------------------------------------
	//#CM28557
	// No Constructors! all of method is static.

	//#CM28558
	// Public methods ------------------------------------------------
	//#CM28559
	/**
	 * Getting <code>Connection</code> for the connection with Oracle DB.
	 * @param server    Host name or IP address of database server
	 * @param port      Port of the database server to connect to
	 * @param id        Instance name of the database
	 * @param user      Name of the databse user
	 * @param passwd    User's password
	 * @return   <code>Connection</code>
	 * @throws SQLException :Notifies of the exceptions as they are that occured in connection with database.
	 */
	public static Connection getConnection(String server, String port, String id, String user, String passwd) throws SQLException
	{
		//#CM28560
		// Loading JDBC driver
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver()) ;

		//#CM28561
		// make connect string
		String connString = THIN_DRIVER + server + ":" + port + ":" + id ;

		//#CM28562
		// create connection
		Connection conn = DriverManager.getConnection( connString , user, passwd);
		//#CM28563
		// Auto Commit Off
		conn.setAutoCommit(false) ;

		return (conn) ;
	}
	
	//#CM28564
	/**
	 * Getting <code>Connection</code> for the connection with Oracle DB.
	 * @param user      Name of the database user
	 * @param passwd    User's password
	 * @param host      Connecting host (Registered service name of Oracle)
	 * @return   <code>Connection</code>
	 * @throws SQLException :Notifies of the exceptions as they are that occured in connection with database.
	 */
	public static Connection getConnection(String user, String passwd, String host) throws SQLException
	{
		//#CM28565
		// getting parameter from resource
		String port =	getParam("AWC_DB_PORT") ;
		String id =		getParam("AWC_DB_SID") ;

		return getConnection(host, port, id, user, passwd) ;
	}
	
	//#CM28566
	/**
	 * Getting <code>Connection</code> for the connection with Oracle DB.
	 * @param user      Name of the database user
	 * @param passwd    User's password
	 * @return   <code>Connection</code>
	 * @throws SQLException :Notifies of the exceptions as they are that occured in connection with database.
	 */
	public static Connection getConnection(String user, String passwd) throws SQLException
	{
		//#CM28567
		// getting parameter from resource
		String server =	getParam("AWC_DB_HOST") ;
		String port =	getParam("AWC_DB_PORT") ;
		String id =		getParam("AWC_DB_SID") ;

		return (getConnection(server, port, id, user, passwd)) ;
	}

	//#CM28568
	/**
	 * Getting <code>Connection</code>for the connection with DB.
	 * Connected to, user and password will be obtained from parameter resource.
	 * <pre>
	 * Parameter key (Definition is required in resource.)
	 * AWC_DB_HOST : Host connected to
	 * AWC_DB_PORT : Port connected to 
	 * AWC_DB_SID  : SID
	 * AWC_DB_USER : Connected user
	 * AWC_DB_PW   : User's password
	 * </pre>
	 * @return   <code>Connection</code>
	 * @throws SQLException :Notifies of the exceptions as they are that occured in connection with database.
	 */
	public static Connection getConnection() throws SQLException
	{
		//#CM28569
		// getting parameter from resource
		String server =	getParam("AWC_DB_HOST") ;
		String port =	getParam("AWC_DB_PORT") ;
		String id =		getParam("AWC_DB_SID") ;
		String user =	getParam("AWC_DB_USER") ;
		String passwd =	getParam("AWC_DB_PW") ;

		return (getConnection(server, port, id, user, passwd)) ;
	}

	//#CM28570
	// Package methods -----------------------------------------------

	//#CM28571
	// Protected methods ---------------------------------------------
	//#CM28572
	/**
	 * Get the parameter key contents as a string
	 * @param key  key to retrieve parameter contents
	 * @return   parameter contents as string
	 */
	protected static String getParam(String key)
	{
		try
		{
			ResourceBundle rb = getBundle(DEFAULT_RESOURCE, Locale.getDefault()) ;
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

	//#CM28573
	/**
	 * Get the parameter key contents as number
	 * @param key  key to retrieve parameter contents
	 * @return   paramter contents as number
	 */
	protected static int getIntParam(String key)
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

	//#CM28574
	/**
	 * Get the parameter key content as true or false
	 * @param key  key to retrieve parameter contents
	 * @return   true or false
	 */
	protected static boolean getBoolParam(String key)
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

	//#CM28575
	// Private methods -----------------------------------------------
    //#CM28576
    /**
     * Get the resource bundle based on current locale
     * 
     * @param   res      resource handle, base class name
     * @param   locale   locale for resource bundle
     * @return  resource bundle with refer to the locale
     */
	private static ResourceBundle getBundle(String res, Locale locale)
	{
		return (ResourceBundle.getBundle(res, locale)) ;
	}

}
//#CM28577
//end of class
