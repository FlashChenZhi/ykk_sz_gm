// $Id: ToolParam.java,v 1.2 2006/10/30 01:40:53 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.common ;

//#CM46931
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
import java.util.StringTokenizer;
import java.util.Vector;

import jp.co.daifuku.common.text.DisplayText;

//#CM46932
/**<en>
 * This class is used to retrieve the parameters of AWC system from the resource.
 * Default resource name is <code>ASRSParam</code>.
 * It is also possible to retrieve <code>Connection</code> for the connection with database.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:40:53 $
 * @author  $Author: suresh $
 </en>*/
public class ToolParam extends Object
{
	//#CM46933
	// Class fields --------------------------------------------------
	//#CM46934
	/**<en>
	 * Error which occurs in case the data of database error code (for Oracle use)
	 * already exists.
	 </en>*/
	//#CM46935
	// Unique constraint violation
	public static final int DATAEXISTS = 1 ;

	//#CM46936
	/**<en>
	 * Result Flag : INSERT,UPDATE,DELETE SUCCESS
	 </en>*/
	public final static int RESULT_SUCCESS = 1;

	//#CM46937
	/**<en>
	 * Result Flag : INSERT,UPDATE,DELETE FAILED
	 </en>*/
	public final static int RESULT_FAILED  = 0;

	//#CM46938
	/**<en>
	 * Search Flag :normal search
	 </en>*/
	public final static int SEARCH_NORMAL = 1;

	//#CM46939
	/**<en>
	 * Search Flag : temporary search
	 </en>*/
	public final static int SEARCH_TEMPORARILY = 0;
	
	//#CM46940
	/**<en>
	 * Delimiter used when reading the file.
	 </en>*/
	public final static String wSeparator = ",";

	//#CM46941
	/**<en>
	 * Default resource name
	 </en>*/
	public static final String DEFAULT_RESOURCE = "ToolParam" ;

	//#CM46942
	// Class private fields ------------------------------------------
	//#CM46943
	/**<en>
	 * JDBC prefix
	 </en>*/
	private static final String THIN_DRIVER = "jdbc:oracle:thin:@" ;

	//#CM46944
	// Class method --------------------------------------------------
	//#CM46945
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:40:53 $") ;
	}

	//#CM46946
	// Constructors --------------------------------------------------
	//#CM46947
	// No Constructors! all of method is static.

	//#CM46948
	// Public methods ------------------------------------------------
	//#CM46949
	/**<en>
	 * Retrieve the contents of parameter based on the key.
	 * @param key  :key of the retrieveing parameter
	 * @return     :string representation of parameter
	 </en>*/
	public static String getParam(String key)
	{
		ResourceBundle rb = getBundle(DEFAULT_RESOURCE, Locale.getDefault()) ;
		return (rb.getString(key)) ;
	}
	
	//#CM46950
	/**<en>
	 * Retrieve the contents of parameter in form of array based on the key.
	 * Also if there are any space after the string due to DisplayText.trim method, 
	 * the spaces should be deleted automatically.
	 * Example <BR>
	 * "AAA,BBB   ,CCC" => ret[0] = "AAA", ret[1] = "BBB", ret[2] = "CCC"<BR>
	 * @param key  :key of the retrieveing parameter
	 * @return     :string representation of parameter
	 </en>*/
	public static String[] getParamArray(String key)
	{
		ResourceBundle rb = getBundle(DEFAULT_RESOURCE, Locale.getDefault()) ;
		String buf = rb.getString(key);
		
		Vector bufVec = new Vector();

		//#CM46951
		//<en>If there are consecutive delimiters, insert a space of 1 byte.</en>
		buf = DisplayText.DelimiterCheck(buf,wSeparator);

		StringTokenizer stk = new StringTokenizer(buf, wSeparator, false) ;
		while ( stk.hasMoreTokens() )
		{
			bufVec.addElement(DisplayText.trim((String)stk.nextToken()));
		}
		String[] array = new String[bufVec.size()];
		bufVec.copyInto(array);
		return array;
	}
	
	//#CM46952
	/**<en>
	 * Retrieve the <code>Connection</code> for the connection with Oracle DB.
	 * @param server    :name of the database server host, or IP address
	 * @param port      :connection port of the database server
	 * @param id        :name of the database instance
	 * @param user      :database user name
	 * @param passwd    :user and the password
	 * @return   <code>Connection</code>
	 </en>*/
	public static Connection getConnection(String server, String port, String id, String user, String passwd) throws SQLException
	{
		//#CM46953
		// Loading JDBC driver
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver()) ;

		//#CM46954
		// make connect string
		String connString = THIN_DRIVER + server + ":" + port + ":" + id ;

		//#CM46955
		// create connection
		Connection conn = DriverManager.getConnection( connString , user, passwd);
		//#CM46956
		// Auto Commit Off
		conn.setAutoCommit(false) ;

		return (conn) ;
	}
	
	//#CM46957
	/**<en>
	 * Retrieve the <code>Connection</code> for the connection with Oracle DB.
	 * @param user      :database user name
	 * @param passwd    :user password
	 * @param host      :host connected to (service name that oracle registered)
	 * @return   <code>Connection</code>
	 </en>*/
	public static Connection getConnection(String user, String passwd, String host) throws SQLException
	{
		//#CM46958
		// getting parameter from resource
		String port =	getParam("AWC_DB_PORT") ;
		String id =		getParam("AWC_DB_SID") ;

		return getConnection(host, port, id, user, passwd) ;
	}
	
	//#CM46959
	/**<en>
	 * Retrieve the <code>Connection</code> for the connection with Oracle DB.
	 * @param user      :name of databse user
	 * @param passwd    :user password
	 * @return   <code>Connection</code>
	 </en>*/
	public static Connection getConnection(String user, String passwd) throws SQLException
	{
		//#CM46960
		// getting parameter from resource
		String server =	getParam("AWC_DB_HOST") ;
		String port =	getParam("AWC_DB_PORT") ;
		String id =		getParam("AWC_DB_SID") ;

		return (getConnection(server, port, id, user, passwd)) ;
	}

	//#CM46961
	/**<en>
	 * Retrieve the <code>Connection</code> for DB connection.
	 * The connected to, user and the password will be retrieved from the parameter resource.
	 * <pre>
	 * Parameter key (definition is required in the resource)
	 * AWC_DB_HOST : connected host
	 * AWC_DB_PORT : connected port
	 * AWC_DB_SID  : SID
	 * AWC_DB_USER : connection user
	 * AWC_DB_PW   : User, password
	 * </pre>
	 * @return   <code>Connection</code>
	 </en>*/
	public static Connection getConnection() throws SQLException
	{
		//#CM46962
		// getting parameter from resource
		String server =	getParam("AWC_DB_HOST") ;
		String port =	getParam("AWC_DB_PORT") ;
		String id =		getParam("AWC_DB_SID") ;
		String user =	getParam("AWC_DB_USER") ;
		String passwd =	getParam("AWC_DB_PW") ;
System.out.println("server="+server);
System.out.println("user="+user);
System.out.println("id="+id);
		return (getConnection(server, port, id, user, passwd)) ;
	}

	//#CM46963
	// Package methods -----------------------------------------------

	//#CM46964
	// Protected methods ---------------------------------------------

	//#CM46965
	// Private methods -----------------------------------------------
	private static ResourceBundle getBundle(String res, Locale locale)
	{
		return (ResourceBundle.getBundle(res, locale)) ;
	}

	//#CM46966
	// debug methods -----------------------------------------------
	//#CM46967
	/*
	public static void main(String[] argv)
	{
		String[] keys = { "AWC_DB_USER"
						,"AWC_DB_PW"
						,"AWC_DB_HOST"
						,"AWC_DB_PORT"
						,"AWC_DB_SID"
			} ;
		for (int i=0; i < keys.length; i++)
		{
			String param = getParam(keys[i]) ;
		}


		try
		{
			Connection conn = getConnection() ;
			// conn = getConnection("linux1", "1521", "ORCLA", "awc", "awc") ;
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
		}
	}
	*/
	/*
	public static void main(String[] argv)
	{
		String[] keys = { "AWC_DB_USER"
						,"AWC_DB_PW"
						,"AWC_DB_HOST"
						,"AWC_DB_PORT"
						,"AWC_DB_SID"
			} ;
		for (int i=0; i < keys.length; i++)
		{
			String param = getParam(keys[i]) ;
		}


		try
		{
			Connection conn = getConnection() ;
			// conn = getConnection("linux1", "1521", "ORCLA", "awc", "awc") ;
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
		}
	}
	*/
}
//#CM46968
//end of class

