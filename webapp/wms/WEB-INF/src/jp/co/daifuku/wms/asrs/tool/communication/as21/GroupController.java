// $Id: GroupController.java,v 1.2 2006/10/30 01:46:09 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.communication.as21 ;

//#CM47018
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;

//#CM47019
/**<en>
 * This class controls the information of the group controller.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:46:09 $
 * @author  $Author: suresh $
 </en>*/
public class GroupController extends Object
{
	//#CM47020
	// Class fields --------------------------------------------------
	//#CM47021
	/**<en>
	 * Field of the status of group controller (unknown)
	 </en>*/
	public static final int STATUS_UNKNOWN = 0 ;
	
	//#CM47022
	/**<en>
	 * Field of the status of group controller (on-line)
	 </en>*/
	public static final int STATUS_ONLINE = 1 ;
	
	//#CM47023
	/**<en>
	 * Field of the status of group controller (off-line/connected)
	 </en>*/
	public static final int STATUS_OFFLINE = 2 ;
	
	//#CM47024
	/**<en>
	 * Field of the status of group controller (reserved for termination)
	 </en>*/
	public static final int STATUS_END_RESERVATION = 3 ;
	
	//#CM47025
	/**<en>
	 * Field of default group controller
	 </en>*/
	public static final int DEFAULT_AGC_NUMBER = 1 ;

	//#CM47026
	// Class variables -----------------------------------------------
	//#CM47027
	/**<en>
	 * Variables that preserve the connection with database.
	 * It will be provided externally; the transaction will not be operated in this class.
	 </en>*/
	private Connection wConn ;

	//#CM47028
	/**<en>
	 * Variables which preserve the group controller no.
	 </en>*/
	private int wGCNumber = -1 ;
	
	//#CM47029
	/**<en>
	 * Variables which preserve the IP address of the group controllers
	 </en>*/
	private InetAddress wGCIP ;
	
	//#CM47030
	/**<en>
	 * Variables which preserve the port no. of the group controller.
	 </en>*/
	private int wGCPort ;
	
	//#CM47031
	/**<en>
	 * Variables which preserve the status of the group controller.
	 </en>*/
	private int wGCStatus ;
	

	//#CM47032
	/**<en>
	 * Variables which preserve the group controller no. for the table search.
	 </en>*/
	private int wSelNumber ;

	//#CM47033
	/**<en>
	 * Preserve the name of the tables.
	 </en>*/
	private static String wTableName = "TEMP_GROUPCONTROLLER" ;
	
	//#CM47034
	/**<en>
	 * This will be used in LogWrite when Exception occurs.
	 </en>*/
	public StringWriter wSW = new StringWriter();

	//#CM47035
	/**<en>
	 * This will be used in LogWrite when Exception occurs.
	 </en>*/
	public PrintWriter  wPW = new PrintWriter(wSW);

	//#CM47036
	/**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM47037
	// Class method --------------------------------------------------
	//#CM47038
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:46:09 $") ;
	}
	
	//#CM47039
	/**<en>
	 * Create the <code>GroupController</code> instance based on the group controller no.,
	 * then return.
	 * @param conn  :the transaction of <code>Connection</code> for database connection is not
	 * controled internally; it is necessary that it should be committed externally.
	 * @param gno :number of <code>GroupController</code> creating
	 * @return    :<code>GroupController</code> object which will be created based on the parameter.
	 </en>*/
	public static GroupController getInstance(Connection conn, int gno)
	{
		GroupController gc = new GroupController(conn, gno) ;
		return(gc) ;
	}
	
	//#CM47040
	/**<en>
	 * Create the <code>GroupController</code> instance of all grop controllers.
	 * @param conn  :the transaction of <code>Connection</code> for database connection is not
	 * controled internally; it is necessary that it should be committed externally.
	 * @return :the array of <code>GroupController</code> object created based on the parameter
	 * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
	 </en>*/
	public static GroupController[] getInstances(Connection conn) throws ReadWriteException
	{
		//#CM47041
		//<en> Retrieve all the group controller no.</en>
		int[] gcids = getGCNo(conn) ;

		//#CM47042
		//<en> Create the array of group controller.</en>
		GroupController[] gcs = new GroupController[gcids.length] ;

		//#CM47043
		//<en> Generate the instance of all group controllers.</en>
		for (int i=0; i < gcids.length; i++)
		{
			gcs[i] = new GroupController(conn, gcids[i]) ;
		}
		
		return(gcs) ;
	}

	//#CM47044
	/**<en>
	 * Create the <code>GroupController</code> instance based on the group controller no;
	 * return true if the status is on-line.
	 * @param conn  :the transaction of <code>Connection</code> for database connection is not
	 * controled internally; it is necessary that it should be committed externally.
	 * @param gno :number of <code>GroupController</code> creating
	 * @return:if the status is online     : true
	 *         if the status is anything othere than on-line : false
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
	 </en>*/
	public static boolean isOnLine(Connection conn, int gno) throws ReadWriteException
	{
		GroupController gc = new GroupController(conn, gno) ;
		if ( gc.getStatus() == STATUS_ONLINE )
		{
			return true ;
		}
		
		return false ;
	}

	//#CM47045
	// Constructors --------------------------------------------------
	//#CM47046
	/**<en>
	 * Create the instance by specifing the group controller ID.
	 * @param conn  the transaction of <code>Connection</code> for database connection is not
	 * controled internally; it is necessary that it should be committed externally.
	 * @param gno : number of <code>GroupController</code> to create
	 </en>*/
	public GroupController(Connection conn, int gno)
	{
		wConn = conn ;
		wSelNumber = gno ;
	}

	//#CM47047
	// Public methods ------------------------------------------------
	//#CM47048
	/**<en>
	 * Set the name of the table. This method should be used when the modification is required.
	 * Refer to default TEMP_GROUPCONTROLLER.
	 * @param arg TableName to set
	 </en>*/
	public void setTableName(String arg)
	{
		wTableName = arg;
	}

	//#CM47049
	/**<en>
	 * Retrieve the group controller no.
	 * @return    :the group controller no.
	 </en>*/
	public int getNumber()
	{
		return(wSelNumber) ;
	}

	//#CM47050
	/**<en>
	 * Set the IP address of the group controller.
	 * @param ip :IP address of the group controller
	 * @see java.net.InetAddress
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
	 </en>*/
	public void setIP(InetAddress ip) throws ReadWriteException
	{
		//#CM47051
		//<en> Lock DB</en>
		loadGCinfo(true) ;
		
		//#CM47052
		//<en> DB update</en>
		wGCIP = ip ;
		updateGCinfo() ;
	}
	
	//#CM47053
	/**<en>
	 * Retrieve the IP address of the group controller.
	 * @return    :IP address of the group controller
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
	 </en>*/
	public InetAddress getIP() throws ReadWriteException
	{
		//#CM47054
		//<en> Get the data of group controller from DB</en>
		loadGCinfo(false) ;

		return(wGCIP) ;
	}

	//#CM47055
	/**<en>
	 * Set the port no. of the group controller.
	 * @param port :port no. of the group controller
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
	 </en>*/
	public void setPort(int port) throws ReadWriteException
	{
		//#CM47056
		//<en> Lock DB</en>
		loadGCinfo(true) ;

		//#CM47057
		//<en> DB update</en>
		wGCPort = port ;
		updateGCinfo() ;
	}
	
	//#CM47058
	/**<en>
	 * Retrieve the port no. of the group controller.
	 * @return    :port no. of the group controller
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
	 </en>*/
	public int getPort() throws ReadWriteException
	{
		//#CM47059
		//<en> Get the data of group controller from DB</en>
		loadGCinfo(false) ;
		
		return(wGCPort) ;
	}

	//#CM47060
	//----------------------------------------------------------------
	//#CM47061
	//<en> Accessor method in dynamic state</en>
	//#CM47062
	//----------------------------------------------------------------
	//#CM47063
	/**<en>
	 * Set the status of the group controller.<BR>
	 * The list of status is defined as a field of this class.
	 * @param sts  :the status of the group controller
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
	 </en>*/
	public void setStatus(int sts) throws ReadWriteException
	{
		//#CM47064
		//<en> Lock DB</en>
		loadGCinfo(true) ;
		
		//#CM47065
		//<en> DB update</en>
		wGCStatus = sts ;
		updateGCinfo() ;
	}
	
	//#CM47066
	/**<en>
	 * Retrieve the the status of the group controller.<BR>
	 * he list of status is defined as a field of this class.
	 * @return    :the the status of the group controller
     * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
     * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
	 </en>*/
	public int getStatus() throws ReadWriteException
	{
		//#CM47067
		//<en> Get the data of group controller from DB</en>
		loadGCinfo(false) ;

		return(wGCStatus) ;
	}

	//#CM47068
	/**<en>
	 * Return the string representation of the group controller.
	 * @return    string representation of the group controller
	 </en>*/
	public String toString()
	{
		StringBuffer buf = new StringBuffer(100) ;
		try
		{
			buf.append("\nGroup Controller Number:" + Integer.toString(getNumber())) ;
			buf.append("\nGroup Controller IP:" + (getIP().toString())) ;
			buf.append("\nGroup Controller Port:" + Integer.toString(getPort())) ;
			buf.append("\nGroup Controller Status:" + Integer.toString(getStatus())) ;
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
		}
		
		return (buf.toString()) ;
	}

	//#CM47069
	// Package methods -----------------------------------------------

	//#CM47070
	// Protected methods ---------------------------------------------

	//#CM47071
	// Private methods -----------------------------------------------
	//#CM47072
	/**<en>
	 * Retrieve the information of the group controller from database.
	 * Group controller number will be the key.
	 * @param   locked :Specify true if locaking the searched data, or false if not locking data.
	 * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
	 * @throws ReadWriteException :Notifies if the IP address of the host was not found which was
     * specified by the group controller information.
	 </en>*/
	private void loadGCinfo(boolean lockd) throws ReadWriteException
	{
		//#CM47073
		//-------------------------------------------------
		//#CM47074
		//<en> variable define</en>
		//#CM47075
		//-------------------------------------------------
		String tablename = wTableName;
		
		String sqltmpl = "SELECT" +
						" controllernumber" +
						", status" +
						", ipaddress" +
						", port " +
						" FROM " + tablename +
						" WHERE controllernumber = {0}" ;

		String sqllocktmpl = "SELECT" +
						" controllernumber" +
						", status" +
						", ipaddress" +
						", port " +
						" FROM " + tablename +
						" WHERE controllernumber = {0} FOR UPDATE" ;

		Statement stmt = null ;
		ResultSet rset = null ;

		Object[] fmtObj = new Object[1] ;
		String sqlstring ;

		//#CM47076
		//-------------------------------------------------
		//#CM47077
		//<en> process start</en>
		//#CM47078
		//-------------------------------------------------
		try 
		{
			fmtObj[0] = Integer.toString(wSelNumber) ;

			//#CM47079
			//<en> create actual SQL string</en>
			if (lockd)
			{
				//#CM47080
				//<en> SQL which locks the selected line.</en>
				sqlstring = SimpleFormat.format(sqllocktmpl, fmtObj) ;
			}
			else
			{
				//#CM47081
				//<en> SQL which will not lock the selected line.</en>
				sqlstring = SimpleFormat.format(sqltmpl, fmtObj) ;
			}
			stmt = wConn.createStatement();
			rset = stmt.executeQuery(sqlstring);

			//#CM47082
			//<en> fetch to first row</en>
			if (rset.next() == false)
			{
				Object[] tObj = new Object[1] ;
				tObj[0] = tablename;
				//#CM47083
				//<en> 6026033=There is no data corresponding to the search. Table:{0}</en>
				RmiMsgLogClient.write(6124002, LogMessage.F_ERROR, "GroupController", tObj);
				throw (new ReadWriteException()) ;
			}

			//#CM47084
			//<en> getting column data</en>
			wGCNumber = rset.getInt("controllernumber") ;
			wGCStatus = rset.getInt("status") ;
			wGCPort = rset.getInt("port") ;
			try
			{
				wGCIP = InetAddress.getByName(DBFormat.replace(rset.getString("ipaddress"))) ;
			}
			catch (UnknownHostException ue)
			{
				String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
				//#CM47085
				//<en>Record the error in the log file.</en>
				ue.printStackTrace(wPW) ;
				Object[] tObj = new Object[2] ;
				tObj[0] = DBFormat.replace(rset.getString("ipaddress"));
				tObj[1] = stcomment + wSW.toString() ;
				//#CM47086
				//<en> 6026036=Cannot find the IP address of the designated host. Host:{0}</en>
				RmiMsgLogClient.write(6126002, LogMessage.F_ERROR, "GroupController", tObj);
				throw (new ReadWriteException()) ;
			}
		}
		catch (SQLException se)
		{
			String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
			//#CM47087
			//<en>Record the error in the log file.</en>
			se.printStackTrace(wPW) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(se.getErrorCode()) ;
			tObj[1] = stcomment + wSW.toString() ;
			//#CM47088
			//<en> 6007030=Database error occured. error code={0}</en>
			RmiMsgLogClient.write(6126001, LogMessage.F_ERROR, "GroupController", tObj);
			throw (new ReadWriteException("6126001" + wDelim + tObj[0])) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException se)
			{
				String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
				//#CM47089
				//<en>Recording the errors in the log file.</en>
				se.printStackTrace(wPW) ;
				Object[] tObj = new Object[2] ;
				tObj[0] = new Integer(se.getErrorCode()) ;
				tObj[1] = stcomment + wSW.toString() ;
				//#CM47090
				//<en> 6007030=Database error occured. error code={0}</en>
				RmiMsgLogClient.write(6126001, LogMessage.F_ERROR, "GroupController", tObj);
				throw (new ReadWriteException("6126001" + wDelim + tObj[0])) ;
			}
		}
	}

	//#CM47091
	/**<en>
	 * Reflect the information of group controller to database.
	 * Key is the Group controller number.
	 * @throws ReadWriteException :Notifies if exception occurred in connetion with database.
	 </en>*/
	private void updateGCinfo() throws ReadWriteException
	{
		//#CM47092
		//-------------------------------------------------
		//#CM47093
		//<en> variable define</en>
		//#CM47094
		//-------------------------------------------------
		String tablename = wTableName;
		String sqltmpl = "UPDATE "+ tablename +" set " +
						"status = {1}" +
						", ipaddress = {2}" +
						", port = {3}" +
						" WHERE controllernumber = {0}" ;

		Statement stmt = null ;
		ResultSet rset = null ;

		Object[] fmtObj = new Object[4] ;
		String sqlstring ;

		//#CM47095
		//-------------------------------------------------
		//#CM47096
		//<en> process start</en>
		//#CM47097
		//-------------------------------------------------
		try
		{
			fmtObj[0] = Integer.toString(wGCNumber) ;
			fmtObj[1] = Integer.toString(wGCStatus) ;
			fmtObj[2] = "'"+ wGCIP.getHostName() +"'" ;
			fmtObj[3] = Integer.toString(wGCPort) ;
			
			//#CM47098
			//<en> create actual SQL string</en>
			sqlstring = SimpleFormat.format(sqltmpl, fmtObj) ;

			stmt = wConn.createStatement();
			stmt.executeUpdate(sqlstring);
		}
		catch (SQLException se)
		{
			String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
			//#CM47099
			//<en>Record the error in the log file.</en>
			se.printStackTrace(wPW) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(se.getErrorCode()) ;
			tObj[1] = stcomment + wSW.toString() ;
			//#CM47100
			//<en> 6007030=Database error occured. error code={0}</en>
			RmiMsgLogClient.write(6126001, LogMessage.F_ERROR, "GroupController", tObj);
			throw (new ReadWriteException("6126001" + wDelim + tObj[0])) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException se)
			{
				String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
				//#CM47101
				//<en>Record the error in the log file.</en>
				se.printStackTrace(wPW) ;
				Object[] tObj = new Object[2] ;
				tObj[0] = new Integer(se.getErrorCode()) ;
				tObj[1] = stcomment + wSW.toString() ;
				//#CM47102
				//<en> 6007030=Database error occured. error code={0}</en>
				RmiMsgLogClient.write(6126001, LogMessage.F_ERROR, "GroupController", tObj);
				throw (new ReadWriteException("6126001" + wDelim + tObj[0])) ;
			}
		}
	}

	//#CM47103
	/**<en>
	 * Get number of all group controller out of database.
	 * @param conn  :As transaction control for <code>Connection</code>, for the use of database connection, 
	 * is not done internally, it requires the external commitment.
	 * @return Array of group controller number
	 * @throws ReadWriteException : Notifies if exception occured during the database connection.
	 </en>*/
	private static int[] getGCNo(Connection conn) throws ReadWriteException
	{
		//#CM47104
		//<en> This will be used in LogWrite when Exception occurs.</en>
		StringWriter wsw = new StringWriter();
		//#CM47105
		//<en> This will be used in LogWrite when Exception occurs.</en>
		PrintWriter  wpw = new PrintWriter(wsw);
		//#CM47106
		//<en> Delimiter</en>
		String wDelim = MessageResource.DELIM ;
		String tablename = wTableName;
		
		//#CM47107
		//-------------------------------------------------
		//#CM47108
		//<en> variable define</en>
		//#CM47109
		//-------------------------------------------------
		String sqlstring = "SELECT " +
						" CONTROLLERNUMBER" +
						" FROM " + tablename +
						" ORDER BY CONTROLLERNUMBER";

		Statement stmt = null ;
		ResultSet rset = null ;

		Vector wgcidVect = new Vector(10) ;
		int[] regGCNo ;

		//#CM47110
		//-------------------------------------------------
		//#CM47111
		//<en> process start</en>
		//#CM47112
		//-------------------------------------------------
		try
		{
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sqlstring);

			//#CM47113
			//<en> fetch and set to vector</en>
			while (rset.next())
			{
				//#CM47114
				//<en> getting column data</en>
				wgcidVect.add(new Integer(rset.getInt("CONTROLLERNUMBER"))) ;
			}

			//#CM47115
			//<en> set to array of int (for return value)</en>
			regGCNo = new int[wgcidVect.size()] ;
			for (int i=0; i < regGCNo.length; i++)
			{
				Integer wi = (Integer)wgcidVect.remove(0) ;
				regGCNo[i] = wi.intValue() ;
			}
		}
		catch (SQLException se)
		{
			String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
			//#CM47116
			//<en>Record the error in the log file.</en>
			se.printStackTrace(wpw) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(se.getErrorCode()) ;
			tObj[1] = stcomment + wsw.toString() ;
			//#CM47117
			//<en> 6007030=Database error occured. error code={0}</en>
			RmiMsgLogClient.write(6126001, LogMessage.F_ERROR, "GroupController", tObj);
			throw (new ReadWriteException("6126001" + wDelim + tObj[0])) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException se)
			{
				String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
				//#CM47118
				//<en>Record the error in the log file.</en>
				se.printStackTrace(wpw) ;
				Object[] tObj = new Object[2] ;
				tObj[0] = new Integer(se.getErrorCode()) ;
				tObj[1] = stcomment + wsw.toString() ;
				//#CM47119
				//<en> 6007030=Database error occured. error code={0}</en>
				RmiMsgLogClient.write(6126001, LogMessage.F_ERROR, "GroupController", tObj);
				throw (new ReadWriteException("6126001" + wDelim + tObj[0])) ;
			}
		}
		return (regGCNo) ;
	}
	
	//#CM47120
	/**<en>
	 * Set the number of group controller.
	 * @param arg : number of group controller
	 </en>*/
	public void setControllerNumber(int arg)
	{
		wSelNumber = arg;
	}

	//#CM47121
	/**<en>
	 * Retrieve the number of group controller.
	 * @return number of group controller
	 </en>*/
	public int getControllerNumber()
	{
		return wSelNumber;
	}
}
//#CM47122
//end of class

